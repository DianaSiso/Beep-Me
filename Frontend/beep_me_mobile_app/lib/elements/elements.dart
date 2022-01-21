import 'dart:developer';

import 'package:beep_me_mobile_app/elements/orderCard.dart';
import 'package:beep_me_mobile_app/models/Order.dart';
import 'package:flutter/material.dart';

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
            onPressed: () {}),
        const SizedBox(),
        IconButton(
            icon: const Icon(Icons.restaurant, size: 40), onPressed: () {}),
      ],
    ),
  );
}

Widget shoopingNameCard() {
  return Padding(
    padding: const EdgeInsets.only(left: 20, right: 20),
    child: Container(
      margin: const EdgeInsets.only(bottom: 20, top: 10),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(30),
        color: Colors.white,
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.4),
            spreadRadius: 5,
            blurRadius: 15,
            offset: const Offset(0, 3), // changes position of shadow
          ),
        ],
      ),
      child: Row(
        children: [
          Expanded(
            child: Container(
              height: 150,
              width: 50,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(30),
                image: const DecorationImage(
                  image:
                      AssetImage("images/undraw_Eating_together_re_ux62.png"),
                  fit: BoxFit.cover,
                ),
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: const [
                  Text("Insert your shooping name:",
                      style: TextStyle(
                        fontSize: 20,
                      )),
                  Padding(
                    padding: EdgeInsets.only(right: 20),
                    child: TextField(
                      style: TextStyle(fontSize: 15),
                      decoration: InputDecoration(
                        hintText: 'Enter the name',
                      ),
                    ),
                  )
                ],
              ),
            ),
          )
        ],
      ),
    ),
  );
}

Widget pedidosCard(int itemCount, List orders) {
  return Expanded(
    child: ListView.builder(
      itemCount: orders.length,
      itemBuilder: (BuildContext context, int index) {
        String order = orders[index];
        log(order);
        return createOrderCard(order);
      },
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

Widget titleNameRestaurant(String name) {
  return Padding(
    padding: const EdgeInsets.only(left: 20),
    child:
        Text(name, style: const TextStyle(color: Colors.white, fontSize: 25)),
  );
}

Widget numOrder(String num) {
  return Padding(
    padding: const EdgeInsets.only(right: 20),
    child: Text(num, style: const TextStyle(color: Colors.white, fontSize: 25)),
  );
}
