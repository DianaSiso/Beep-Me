import 'dart:async';
import 'dart:convert';
import 'dart:io';

//import 'package:awesome_notifications/awesome_notifications.dart';
import 'package:flutter/gestures.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';
import 'elements/elements.dart';
import 'dart:developer';
import 'package:fl_chart/fl_chart.dart';
import 'package:qr_code_scanner/qr_code_scanner.dart';

import 'elements/orderCard.dart';
import 'models/Order.dart';
import "package:dart_amqp/dart_amqp.dart";

void main() {
  runApp(const MyApp());
}

Map<int, Color> color = {
  50: const Color.fromRGBO(4, 131, 184, .1),
  100: const Color.fromRGBO(4, 131, 184, .2),
  200: const Color.fromRGBO(4, 131, 184, .3),
  300: const Color.fromRGBO(4, 131, 184, .4),
  400: const Color.fromRGBO(4, 131, 184, .5),
  500: const Color.fromRGBO(4, 131, 184, .6),
  600: const Color.fromRGBO(4, 131, 184, .7),
  700: const Color.fromRGBO(4, 131, 184, .8),
  800: const Color.fromRGBO(4, 131, 184, .9),
  900: const Color.fromRGBO(4, 131, 184, 1),
};

final ThemeData theme = ThemeData(fontFamily: 'Poppins');

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Beep Me',
      theme: theme.copyWith(
          colorScheme: theme.colorScheme.copyWith(
              secondary: const Color(0xff0D1821),
              primary: const Color(0xffBFCC94))),
      home: const HomePage(title: 'Beep Me'),
    );
  }
}

class HomePage extends StatefulWidget {
  const HomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> with TickerProviderStateMixin {
  int page = 0;
  List<Color> gradientColors = [
    const Color(0xff23b6e6),
    const Color(0xff02d39a),
  ];

  String code = '';
  final qrKey = GlobalKey(debugLabel: 'QR');
  Barcode? barcode;
  bool codeRead = false;

  List ordersCode = [];
  List orders = [];
  List ordersCodeNotified = [];
  Client client =
      Client(settings: ConnectionSettings(host: "deti-engsoft-02.ua.pt"));
  String queueTag = "notifications";
  late Consumer consumer;

  QRViewController? qrViewController;

  bool showAvg = false;
  String dropdownValue = "Mcdonalds";
  List<String> restaurantes = [
    "Mcdonalds",
    "KFC",
    "BurgerKing",
    "PizzaHut",
    "Pans",
    "Kebab"
  ];

  void _changePage(int index) {
    setState(() {
      page = index;
      if (index == 1) {
        codeRead = false;
      }
    });
  }

  Widget _displayPage(BuildContext context) {
    switch (page) {
      case 0:
        {
          return homePage();
        }
      case 1:
        {
          if (codeRead) {
            return homePage();
          } else {
            return qrcodePage(context);
          }
        }
      case 2:
        {
          return chartPage();
        }
      default:
        {
          return homePage();
        }
    }
  }

  @override
  void initState() {
    super.initState();
    //initMessageQueue();
    getCachedOrders();
    Timer.periodic(const Duration(seconds: 1), (Timer t) {
      setState(() {
        getOrders();
        writeOrders();
        getNotification();
      });
    });
  }

  void initMessageQueue() async {
    Channel channel = await client.channel();
    Queue queue = await channel.queue(queueTag);
    Consumer consumer = await queue.consume();
    consumer.listen((AmqpMessage event) {
      log(event.payloadAsString);
      log("Running!");
      showDialog(
          context: context,
          builder: (context) => AlertDialog(
                title: const Text("Order Ready"),
                content: Text(event.payloadAsString),
                actions: [
                  TextButton(
                      onPressed: () {
                        Navigator.pop(context);
                      },
                      child: const Text(
                        "OK!",
                        style: TextStyle(color: Colors.black, fontSize: 18),
                      )),
                ],
              ));
    });
    client.close();
  }

  void getNotification() async {
    Channel channel = await client.channel();
    Queue queue = await channel.queue(queueTag, durable: true);
    Consumer consumer = await queue.consume();
    consumer.listen((AmqpMessage event) {
      Map<String, dynamic> order = event.payloadAsJson as Map<String, dynamic>;
      if (!ordersCodeNotified.contains(order["code"])) {
        ordersCodeNotified.toList();
        ordersCodeNotified.add(order["code"]);
        String state = order["state"];
        state = "${state[0].toUpperCase()}${state.substring(1).toLowerCase()}";
        String restName = order["restaurantName"];
        restName =
            "${restName[0].toUpperCase()}${restName.substring(1).toLowerCase()}";
        showDialog(
            context: context,
            builder: (context) => AlertDialog(
                  title: const Text("Your order is ready!"),
                  content: Container(
                    height: MediaQuery.of(context).size.height * 0.25,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Container(
                          width: MediaQuery.of(context).size.width,
                          padding: EdgeInsets.all(20),
                          decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(30),
                              color: Colors.blueGrey[400],
                              boxShadow: [
                                BoxShadow(
                                  color: Colors.grey.withOpacity(0.5),
                                  spreadRadius: 5,
                                  blurRadius: 15,
                                  offset: const Offset(
                                      0, 3), // changes position of shadow
                                ),
                              ]),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Container(
                                child: Text(
                                  restName,
                                  style: TextStyle(
                                      fontSize: restName.length > 10 ? 15 : 25),
                                ),
                              ),
                              Container(
                                  child: Text(
                                order["orderID"].toString(),
                                style: TextStyle(
                                    fontSize: restName.length > 10 ? 15 : 20),
                              ))
                            ],
                          ),
                        ),
                        Container(
                          width: MediaQuery.of(context).size.width,
                          padding: EdgeInsets.all(10),
                          decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(30),
                              color: Colors.green[300],
                              boxShadow: [
                                BoxShadow(
                                  color: Colors.grey.withOpacity(0.5),
                                  spreadRadius: 5,
                                  blurRadius: 15,
                                  offset: const Offset(
                                      0, 3), // changes position of shadow
                                ),
                              ]),
                          child: Align(
                              alignment: Alignment.center,
                              child: Text(state.toString())),
                        )
                      ],
                    ),
                  ),
                  actions: [
                    Align(
                      alignment: Alignment.center,
                      child: TextButton(
                          onPressed: () {
                            Navigator.pop(context);
                          },
                          child: const Text(
                            "OK",
                            style: TextStyle(color: Colors.black, fontSize: 18),
                          )),
                    ),
                  ],
                ));
      }
    });
    client.close();
  }

  void getOrders() {
    if (ordersCode.isNotEmpty) {
      for (String code in ordersCode) {
        fetchOrderInfo(code).then((result) {
          if (result != null) {
            int index = orders
                .indexWhere((element) => element.getCode() == result.getCode());
            if (index == -1) {
              if (!(result.getState() == "NON_DELIVERED")) {
                orders = orders.toList();
                orders.add(result);
              } else {
                ordersCode.remove(code);
                showDialog(
                    context: context,
                    builder: (context) => AlertDialog(
                          title: const Text("Order passed the delivery time!"),
                          content: Column(
                            children: [
                              Text("Restaurant: " +
                                  result.getRestaurant()[0].toUpperCase() +
                                  result
                                      .getRestaurant()
                                      .substring(1)
                                      .toLowerCase()),
                              Text("Order code: " + result.getCode()),
                            ],
                          ),
                          actions: [
                            TextButton(
                                onPressed: () {
                                  Navigator.pop(context);
                                },
                                child: const Text(
                                  "OK!",
                                  style: TextStyle(
                                      color: Colors.black, fontSize: 18),
                                )),
                          ],
                        ));
              }
            } else {
              orders[index] = result;
            }
          } else {
            ordersCode.remove(code);
          }
        });
      }
    }
  }

  Future<void> getCachedOrders() async {
    if (await Permission.storage.request().isGranted) {
      Directory appDocDir = await getApplicationDocumentsDirectory();
      String appDocPath = appDocDir.path;
      File file = File(appDocPath + "/cahedOrders.txt");
      bool exists = await file.exists();
      if (exists) {
        List<String> codes = await file.readAsLines();
        for (String order in codes) {
          if (!ordersCode.contains(order)) {
            ordersCode.toList();
            ordersCode.add(order);
          }
        }
      } else {
        file.create();
      }
    }
  }

  Future<void> writeOrders() async {
    if (await Permission.storage.request().isGranted) {
      Directory appDocDir = await getApplicationDocumentsDirectory();
      String appDocPath = appDocDir.path;
      // FileInfo? cache;
      File file = File(appDocPath + "/cahedOrders.txt");

      List<String> codes = await file.readAsLines();
      // for (String order in codes) {
      //   log(order);
      // }

      for (String code in ordersCode) {
        if (!codes.contains(code)) {
          file.writeAsString(code + '\n', mode: FileMode.append);
        }
      }
    }
  }

  @override
  void dispose() {
    qrViewController?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
        preferredSize: const Size.fromHeight(70.0),
        child: appBar(widget.title),
      ),
      body: _displayPage(context),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          _changePage(1);
        },
        tooltip: 'Add Order',
        child: const Icon(Icons.add, size: 35),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation
          .endFloat, // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  Widget homePage() {
    return Padding(
        padding: const EdgeInsets.only(top: 20),
        child: Column(
          children: [
            //shoopingNameCard(),
            const Padding(
              padding: EdgeInsets.only(left: 30, bottom: 5),
              child: Align(
                alignment: Alignment.centerLeft,
                child: Text(
                  "Orders",
                  style: TextStyle(fontSize: 30),
                ),
              ),
            ),
            Expanded(
              child: ListView.builder(
                itemCount: orders.length,
                itemBuilder: (BuildContext context, int index) {
                  Order order = orders[index];
                  return Container(
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(30),
                          color: const Color(0xff344966),
                          // gradient: const LinearGradient(
                          //   begin: Alignment.bottomLeft,
                          //   end: Alignment.topRight,
                          //   colors: [
                          //     Color(0xffB4CDED),
                          //     Color(0xff0D1821),
                          //   ],
                          // )
                          boxShadow: [
                            BoxShadow(
                              color: Colors.grey.withOpacity(0.5),
                              spreadRadius: 5,
                              blurRadius: 15,
                              offset: const Offset(
                                  0, 3), // changes position of shadow
                            ),
                          ]),
                      margin: const EdgeInsets.only(
                        top: 15,
                        left: 20,
                        right: 20,
                      ),
                      height: 200,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Padding(
                            padding: const EdgeInsets.only(top: 25),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                titleNameRestaurant(order.getRestaurant()),
                                numOrder(order.getOrderNumber().toString())
                              ],
                            ),
                          ),
                          // Padding(
                          //   padding: const EdgeInsets.symmetric(horizontal: 10),
                          //   child: Container(
                          //     child: const LinearProgressIndicator(
                          //       value: m,
                          //       valueColor:
                          //           AlwaysStoppedAnimation<Color>(Colors.deepOrange),
                          //     ),
                          //   ),
                          // ),
                          Padding(
                            padding: const EdgeInsets.only(bottom: 20),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                stateOrder(order.getState()),
                                Container(
                                    margin: const EdgeInsets.only(right: 20),
                                    decoration: BoxDecoration(
                                        color: const Color(0xffB4CDED),
                                        borderRadius:
                                            BorderRadius.circular(30)),
                                    child: IconButton(
                                      onPressed: () {
                                        _removeOrder(index);
                                      },
                                      icon: const Icon(Icons.done_rounded),
                                    ))
                              ],
                            ),
                          )
                        ],
                      ));
                },
              ),
            )
          ],
        ));
  }

  void removeFromCache() async {
    Directory appDocDir = await getApplicationDocumentsDirectory();
    String appDocPath = appDocDir.path;
    // FileInfo? cache;
    File file = File(appDocPath + "/cahedOrders.txt");

    List<String> codes = await file.readAsLines();
    // for (String order in codes) {
    //   log(order);
    // }
    file.delete();
    for (String code in ordersCode) {
      file.writeAsString(code + '\n', mode: FileMode.append);
    }
  }

  void _removeOrder(int index) {
    showDialog(
        context: context,
        builder: (context) => AlertDialog(
              title: const Text("Removing order..."),
              content:
                  const Text("Are you sure you want to remove this order?"),
              actions: [
                TextButton(
                    onPressed: () {
                      setState(() {
                        Order order = orders.removeAt(index);
                        ordersCode.removeWhere(
                            (element) => order.getCode() == element);
                        removeFromCache();
                      });
                      Navigator.pop(context);
                    },
                    child: Text(
                      "YES",
                      style: TextStyle(color: Colors.red[400], fontSize: 18),
                    )),
                TextButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    child: Text(
                      "NO",
                      style: TextStyle(color: Colors.green[400], fontSize: 18),
                    ))
              ],
            ));
  }

  Widget qrcodePage(BuildContext context) {
    return Stack(
      alignment: Alignment.center,
      children: <Widget>[
        qrcode(context),
        Positioned(
          bottom: 10,
          child: buildResult(),
        )
      ],
    );
  }

  Widget buildResult() => Container(
      padding: EdgeInsets.all(12),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(8), color: Colors.white24),
      child: Text(
        barcode != null ? 'Result :  ${barcode!.code}' : 'Scan a code!',
        maxLines: 3,
      ));

  Widget qrcode(BuildContext context) => QRView(
        key: qrKey,
        onQRViewCreated: onQRViewCreated,
        overlay: QrScannerOverlayShape(
            borderWidth: 10,
            borderRadius: 10,
            borderLength: 20,
            cutOutSize: MediaQuery.of(context).size.width * 0.8),
      );

  void onQRViewCreated(QRViewController controller) {
    setState(() {
      qrViewController = controller;
    });
    qrViewController?.scannedDataStream.listen((barcode) {
      if (!ordersCode.contains(barcode.code!)) {
        ordersCode = ordersCode.toList();
        ordersCode.add(barcode.code!);
      }
      setState(() {
        codeRead = true;
        getOrders();
      });
    });
  }

  Widget bottomAppBar() {
    return BottomAppBar(
      color: const Color(0xffBFCC94),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          IconButton(
              icon: const Icon(
                Icons.show_chart_rounded,
                size: 45,
              ),
              onPressed: () {
                _changePage(2);
              }),
          const SizedBox(),
          IconButton(
              icon: const Icon(Icons.restaurant, size: 40),
              onPressed: () {
                _changePage(3);
              }),
        ],
      ),
    );
  }

  Widget chartPage() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        const Align(
          alignment: Alignment.center,
          child: Text(
            "Shooping Information",
            style: TextStyle(fontSize: 30),
          ),
        ),
        Row(
          children: [
            const Padding(
              padding: EdgeInsets.only(left: 25),
              child: Text(
                "Restaurant:",
                style: TextStyle(fontSize: 15),
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(left: 10),
              child: DropdownButton<String>(
                value: dropdownValue,
                icon: const Icon(Icons.arrow_downward),
                iconSize: 20,
                elevation: 16,
                underline: Container(height: 2, color: Colors.black),
                //style: const TextStyle(fontSize: 15),
                onChanged: (String? newValue) {
                  setState(() {
                    dropdownValue = newValue!;
                  });
                },
                items:
                    restaurantes.map<DropdownMenuItem<String>>((String value) {
                  return DropdownMenuItem<String>(
                    value: value,
                    child: Text(value),
                  );
                }).toList(),
              ),
            ),
          ],
        ),
        Stack(
          children: <Widget>[
            AspectRatio(
              aspectRatio: 1.50,
              child: Container(
                margin: const EdgeInsets.symmetric(horizontal: 15),
                decoration: const BoxDecoration(
                    borderRadius: BorderRadius.all(
                      Radius.circular(18),
                    ),
                    color: Color(0xff232d37)),
                child: Padding(
                  padding: const EdgeInsets.only(
                      right: 18.0, left: 12.0, top: 34, bottom: 12),
                  child: LineChart(
                    showAvg ? avgData() : mainData(),
                  ),
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(20),
              child: SizedBox(
                width: 60,
                height: 34,
                child: TextButton(
                  onPressed: () {
                    setState(() {
                      showAvg = !showAvg;
                    });
                  },
                  child: Text(
                    'avg',
                    style: TextStyle(
                        fontSize: 12,
                        color: showAvg
                            ? Colors.white.withOpacity(0.5)
                            : Colors.white),
                  ),
                ),
              ),
            ),
          ],
        ),
      ],
    );
  }

  LineChartData mainData() {
    return LineChartData(
      gridData: FlGridData(
        show: true,
        drawVerticalLine: true,
        getDrawingHorizontalLine: (value) {
          return FlLine(
            color: const Color(0xff37434d),
            strokeWidth: 1,
          );
        },
        getDrawingVerticalLine: (value) {
          return FlLine(
            color: const Color(0xff37434d),
            strokeWidth: 1,
          );
        },
      ),
      titlesData: FlTitlesData(
        show: true,
        rightTitles: SideTitles(showTitles: false),
        topTitles: SideTitles(showTitles: false),
        bottomTitles: SideTitles(
          showTitles: true,
          reservedSize: 22,
          interval: 1,
          getTextStyles: (context, value) => const TextStyle(
              color: Color(0xff68737d),
              fontWeight: FontWeight.bold,
              fontSize: 16),
          getTitles: (value) {
            switch (value.toInt()) {
              case 2:
                return 'MAR';
              case 5:
                return 'JUN';
              case 8:
                return 'SEP';
            }
            return '';
          },
          margin: 8,
        ),
        leftTitles: SideTitles(
          showTitles: true,
          interval: 1,
          getTextStyles: (context, value) => const TextStyle(
            color: Color(0xff67727d),
            fontWeight: FontWeight.bold,
            fontSize: 15,
          ),
          getTitles: (value) {
            switch (value.toInt()) {
              case 1:
                return '10k';
              case 3:
                return '30k';
              case 5:
                return '50k';
            }
            return '';
          },
          reservedSize: 32,
          margin: 12,
        ),
      ),
      borderData: FlBorderData(
          show: true,
          border: Border.all(color: const Color(0xff37434d), width: 1)),
      minX: 0,
      maxX: 11,
      minY: 0,
      maxY: 6,
      lineBarsData: [
        LineChartBarData(
          spots: const [
            FlSpot(0, 3),
            FlSpot(2.6, 2),
            FlSpot(4.9, 5),
            FlSpot(6.8, 3.1),
            FlSpot(8, 4),
            FlSpot(9.5, 3),
            FlSpot(11, 4),
          ],
          isCurved: true,
          colors: gradientColors,
          barWidth: 5,
          isStrokeCapRound: true,
          dotData: FlDotData(
            show: false,
          ),
          belowBarData: BarAreaData(
            show: true,
            colors:
                gradientColors.map((color) => color.withOpacity(0.3)).toList(),
          ),
        ),
      ],
    );
  }

  LineChartData avgData() {
    return LineChartData(
      lineTouchData: LineTouchData(enabled: false),
      gridData: FlGridData(
        show: true,
        drawHorizontalLine: true,
        getDrawingVerticalLine: (value) {
          return FlLine(
            color: const Color(0xff37434d),
            strokeWidth: 1,
          );
        },
        getDrawingHorizontalLine: (value) {
          return FlLine(
            color: const Color(0xff37434d),
            strokeWidth: 1,
          );
        },
      ),
      titlesData: FlTitlesData(
        show: true,
        bottomTitles: SideTitles(
          showTitles: true,
          reservedSize: 22,
          getTextStyles: (context, value) => const TextStyle(
              color: Color(0xff68737d),
              fontWeight: FontWeight.bold,
              fontSize: 16),
          getTitles: (value) {
            switch (value.toInt()) {
              case 2:
                return 'MAR';
              case 5:
                return 'JUN';
              case 8:
                return 'SEP';
            }
            return '';
          },
          margin: 8,
          interval: 1,
        ),
        leftTitles: SideTitles(
          showTitles: true,
          getTextStyles: (context, value) => const TextStyle(
            color: Color(0xff67727d),
            fontWeight: FontWeight.bold,
            fontSize: 15,
          ),
          getTitles: (value) {
            switch (value.toInt()) {
              case 1:
                return '10k';
              case 3:
                return '30k';
              case 5:
                return '50k';
            }
            return '';
          },
          reservedSize: 32,
          interval: 1,
          margin: 12,
        ),
        topTitles: SideTitles(showTitles: false),
        rightTitles: SideTitles(showTitles: false),
      ),
      borderData: FlBorderData(
          show: true,
          border: Border.all(color: const Color(0xff37434d), width: 1)),
      minX: 0,
      maxX: 11,
      minY: 0,
      maxY: 6,
      lineBarsData: [
        LineChartBarData(
          spots: const [
            FlSpot(0, 3.44),
            FlSpot(2.6, 3.44),
            FlSpot(4.9, 3.44),
            FlSpot(6.8, 3.44),
            FlSpot(8, 3.44),
            FlSpot(9.5, 3.44),
            FlSpot(11, 3.44),
          ],
          isCurved: true,
          colors: [
            ColorTween(begin: gradientColors[0], end: gradientColors[1])
                .lerp(0.2)!,
            ColorTween(begin: gradientColors[0], end: gradientColors[1])
                .lerp(0.2)!,
          ],
          barWidth: 5,
          isStrokeCapRound: true,
          dotData: FlDotData(
            show: false,
          ),
          belowBarData: BarAreaData(show: true, colors: [
            ColorTween(begin: gradientColors[0], end: gradientColors[1])
                .lerp(0.2)!
                .withOpacity(0.1),
            ColorTween(begin: gradientColors[0], end: gradientColors[1])
                .lerp(0.2)!
                .withOpacity(0.1),
          ]),
        ),
      ],
    );
  }

  Widget appBar(String title) {
    return AppBar(
      // Here we take the value from the HomePage object that was created by
      // the App.build method, and use it to set our appbar title.
      title: Padding(
        padding: const EdgeInsets.only(top: 10),
        child: Align(
            alignment: Alignment.center,
            child: RichText(
              text: TextSpan(
                recognizer: TapGestureRecognizer()
                  ..onTap = () {
                    _changePage(0);
                  },
                text: title,
                style: const TextStyle(
                    color: Colors.black, fontSize: 35, fontFamily: "Poppins"),
                children: const <TextSpan>[
                  TextSpan(
                      text: '.',
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: 35,
                          fontFamily: "Poppins")),
                ],
              ),
            )),
      ),
    );
  }
}
