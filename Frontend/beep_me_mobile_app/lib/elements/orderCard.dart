import 'dart:convert';
import 'dart:developer';

import 'package:beep_me_mobile_app/models/Order.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'elements.dart';

Widget createOrderCard(
  String code,
) {
  return FutureBuilder(
    builder: (context, orderRet) {
      print(orderRet.data);
      if (orderRet.hasData) {
        Order order = orderRet.data as Order;
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
                    offset: const Offset(0, 3), // changes position of shadow
                  ),
                ]),
            margin: const EdgeInsets.only(
              top: 15,
              left: 20,
              right: 20,
            ),
            height: 150,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Padding(
                  padding: const EdgeInsets.only(top: 15),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      titleNameRestaurant(order.getRestaurant()),
                      numOrder(order.getOrderNumber().toString())
                    ],
                  ),
                ),
                // Padding(
                //   padding: const EdgeInsets.only(bottom: 20),
                //   child: Row(
                //     mainAxisAlignment: MainAxisAlignment.spaceBetween,
                //     children: [
                //       Expanded(
                //         child: Padding(
                //           padding: const EdgeInsets.all(20),
                //           child: LinearProgressIndicator(
                //             value: progressBarController.value,
                //           ),
                //         ),
                //       ),
                //       Container(
                //           margin: const EdgeInsets.only(right: 20),
                //           decoration: BoxDecoration(
                //               color: const Color(0xffB4CDED),
                //               borderRadius: BorderRadius.circular(30)),
                //           child: const Padding(
                //             padding: EdgeInsets.all(10.0),
                //             child: Icon(Icons.done_rounded, color: Colors.white),
                //           ))
                //     ],
                //   ),
                // )
              ],
            ));
      }
      return Container();
    },
    future: fetchOrderInfo(code),
  );

  ;
}

Future<Order?> fetchOrderInfo(String? code) async {
  final response = await http.get(
      Uri.parse('http://deti-engsoft-02.ua.pt:8080/orders/code?code=' + code!));

  Map<String, dynamic> order = jsonDecode(response.body);
  log(response.body);
  int orderNumber = order["id"];
  String finalCode = order["code"];
  String deliveryDate = order["possibleDeliveryTime"];
  DateTime parsedDate = DateTime.parse(deliveryDate);

  Order orderObj = new Order(finalCode, orderNumber, "none", 1, parsedDate);

  return orderObj;
}
