import 'package:beep_me_mobile_app/models/Order.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

Widget? createOrderCard(String code) {
  return null;
}

Future<Order?> fetchOrderInfo(String code) async {
  final response = await http.get(Uri.parse(
      'http://deti-engsoft-02.ua.pt:8080/orders/restaurant?rest_id=1'));

  return null;
}
