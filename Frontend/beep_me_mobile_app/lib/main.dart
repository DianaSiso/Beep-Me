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
      default:
        {
          return homePage();
        }
    }
  }

  @override
  void initState() {
    super.initState();
    getCachedOrders();
    Timer.periodic(const Duration(seconds: 1), (Timer t) {
      setState(() {
        getOrders();
        writeOrders();
        getNotification();
      });
    });
  }

  void getNotification() async {
    Channel channel = await client.channel();
    Queue queue = await channel.queue(queueTag, durable: true);
    Consumer consumer = await queue.consume();
    consumer.listen((AmqpMessage event) {
      Map<String, dynamic> order = event.payloadAsJson as Map<String, dynamic>;
      if (!ordersCodeNotified.contains(order["code"]) &&
          orders.contains(order["code"])) {
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
      floatingActionButton: page == 1
          ? Container()
          : FloatingActionButton(
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
            bottom: 20,
            left: 0,
            child: IconButton(
              icon: const Icon(
                Icons.chevron_left_rounded,
                size: 50,
                color: Colors.white,
              ),
              onPressed: () {
                setState(() {
                  codeRead = true;
                  _changePage(0);
                });
              },
            ))
      ],
    );
  }

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
