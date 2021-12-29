package com.beep.me.DataGen;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DataGen {


    private String getAlphaNumericString(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length()* Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    private ArrayList<ArrayList<Integer>> getArrayOrders(int[][] orders) {

        ArrayList<ArrayList<Integer>> orders_final = new ArrayList<>();

        for (int[] i: orders) {
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(i[0]);
            arr.add(i[1]);
            orders_final.add(arr);
        }
        return orders_final;
    }

    private HashMap<String, Double> getRestMap() {

        HashMap<String, Double> restaurants = new HashMap<>();
        restaurants.put("H3", 0.05);
        restaurants.put("KFC", 0.1);
        restaurants.put("McDonald's", 0.1);
        restaurants.put("Taco Bell", 0.05);
        restaurants.put("Vitaminas", 0.05);
        restaurants.put("A Gula do Prego", 0.03);
        restaurants.put("Aki Grill", 0.02);
        restaurants.put("Ali Kebab", 0.02);
        restaurants.put("Oita", 0.005);   
        restaurants.put("Dom Franguito", 0.015);
        restaurants.put("Alicarius", 0.005);
        restaurants.put("Bica da Ria", 0.005);
        restaurants.put("Caffelato", 0.005);
        restaurants.put("Caseiro e Bom", 0.005);
        restaurants.put("Hummy", 0.005);
        restaurants.put("Italian Republic", 0.015);
        restaurants.put("Malguinhas e Pregos", 0.015);
        restaurants.put("Mania Poké Bowls", 0.005);
        restaurants.put("Patrono Pizza", 0.015);
        restaurants.put("Pão Divino", 0.015);
        restaurants.put("Sical", 0.005);
        restaurants.put("Sun Bufê", 0.015);
        restaurants.put("Tasquinha do Bacalhau", 0.02);
        restaurants.put("Zé da Tripa", 0.02);

        double count = 0;
        for (String r: restaurants.keySet()) {
            Double v = restaurants.get(r);
            restaurants.replace(r, v + 0.0171);
            count += restaurants.get(r);
        }

        // System.out.println(count);

        return restaurants;

    }

    private HashMap<String, Double> getDurationMap() {

        HashMap<String, Double> dur = new HashMap<>();
        dur.put("10", 0.4);
        dur.put("15", 0.25);
        dur.put("20", 0.2);
        dur.put("25", 0.15);

        double count = 0;
        for (String r: dur.keySet()) {
            Double v = dur.get(r);
            count += v;
        }

        return dur;

    }


    private String chooseOnWeight(HashMap<String, Double> items) {
        double completeWeight = 0.0;
        for (String item : items.keySet())
            completeWeight += items.get(item);
        double r = Math.random() * completeWeight;
        double countWeight = 0.0;
        for (String item : items.keySet()) {
            countWeight += items.get(item);
            if (countWeight >= r)
                return item;
        }
        throw new RuntimeException("Should never be shown.");
    }

    public Order getOrder() {
        HashMap<String, Double> restaurants = getRestMap();
        HashMap<String, Double> dur = getDurationMap();

        // for (int i = 0 ; i < 23; i++) {

            
        // }
        String restaurant = chooseOnWeight(restaurants);
        String duration = chooseOnWeight(dur);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter minutesFormatter = DateTimeFormatter.ofPattern("mm");
        

        String formatDateTime = date.format(dateTimeFormatter);
        String formatDate = date.format(dateFormatter);
        String formatTime = date.format(timeFormatter);
        String formatMinutes = date.format(minutesFormatter);

        LocalDateTime pred = date.plusMinutes(Integer.parseInt(duration));
        String predString = pred.format(dateTimeFormatter);
        
        String code = getAlphaNumericString(10);

        Order order = new Order(code, restaurant, formatDateTime, predString);

        return order;
    }
    
}
