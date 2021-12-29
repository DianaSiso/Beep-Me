package generation;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import generation.*;

public class dataGen {

    static String getAlphaNumericString(int n)
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

    static ArrayList<ArrayList<Integer>> getArrayOrders(int[][] orders) {

        ArrayList<ArrayList<Integer>> orders_final = new ArrayList<>();

        for (int[] i: orders) {
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(i[0]);
            arr.add(i[1]);
            orders_final.add(arr);
        }
        return orders_final;
    }

    static public HashMap<String, Double> getRestMap() {

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

    static public HashMap<String, Double> getDurationMap() {

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


    static public String chooseOnWeight(HashMap<String, Double> items) {
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

    public static void main(String[] args) {
        HashMap<String, Double> restaurants = getRestMap();
        HashMap<String, Double> dur = getDurationMap();

        int count = 0;

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

        Order order = new Order(code, restaurant, date, pred);

        String toString = order.toString();

        System.out.println(toString);
    }

    // public Order generateOrder() {

    // }

    

    // public static void main(String[] args) {
    //     Random rand = new Random();
    //     HashMap<String, Character> restaurants = new HashMap<String, Character>();
    //     restaurants.put("H3", 'C');
    //     restaurants.put("KFC", 'B');
    //     restaurants.put("McDonald's", 'A');
    //     restaurants.put("Taco Bell", 'B');
    //     restaurants.put("Vitaminas", 'C');
    //     restaurants.put("A Gula do Prego", 'C');
    //     restaurants.put("Aki Grill", 'D');
    //     restaurants.put("Ali Kebab", 'C');
    //     restaurants.put("Oita", 'D');   
    //     restaurants.put("Dom Franguito", 'B');
    //     restaurants.put("Alicarius", 'C');
    //     restaurants.put("Bica da Ria", 'D');
    //     restaurants.put("Caffelato", 'E');
    //     restaurants.put("Caseiro e Bom", 'D');
    //     restaurants.put("Hummy", 'D');
    //     restaurants.put("Italian Republic", 'C');
    //     restaurants.put("Malguinhas e Pregos", 'D');
    //     restaurants.put("Mania Poké Bowls", 'C');
    //     restaurants.put("Patrono Pizza", 'B');
    //     restaurants.put("Pão Divino", 'D');
    //     restaurants.put("Sical", 'E');
    //     restaurants.put("Sun Bufê", 'D');
    //     restaurants.put("Tasquinha do Bacalhau", 'D');
    //     restaurants.put("Zé da Tripa", 'E');
    //     try {
    //         FileWriter f = new FileWriter("data_info.txt");
    //         for (int d=0; d<30; d++) {
    //             f.write("Dia: " + d + "\n"); 
    //             for (String r : restaurants.keySet()) {
    //                 ArrayList<String> code = new ArrayList<String>();
    //                 ArrayList<ArrayList<Integer>> orders = new ArrayList<>();

    //                 f.write("Restaurante: " + r + "\n");
                    
    //                 if (restaurants.get(r) == 'A') {
    //                     int[][] orders_tmp = {{30, 40}, {100, 150}, {40, 90}, {100, 180}};
    //                     orders = getArrayOrders(orders_tmp);
    //                 } else if (restaurants.get(r) == 'B') {
    //                     int[][] orders_tmp = {{20, 30}, {80, 120}, {20, 60}, {70, 150}};
    //                     orders = getArrayOrders(orders_tmp);
    //                 } else if (restaurants.get(r) == 'C') {
    //                     int[][] orders_tmp = {{10, 30}, {70, 110}, {10, 40}, {60, 130}};
    //                     orders = getArrayOrders(orders_tmp);
    //                 } else if (restaurants.get(r) == 'D') {
    //                     int[][] orders_tmp = {{10, 20}, {50, 90}, {10, 30}, {50, 110}};
    //                     orders = getArrayOrders(orders_tmp);
    //                 } else {
    //                     int[][] orders_tmp = {{10, 40}, {50, 150}, {10, 60}, {50, 180}};
    //                     orders = getArrayOrders(orders_tmp);
    //                 }

    //                 for (int h=0; h<4; h++) {
    //                     int min = orders.get(h).get(0);
    //                     int max = orders.get(h).get(1);
    //                     int orders_t = (int) Math.random()*(max-min+1)+min;   

    //                     for (int i=0; i<orders_t; i++) {
    //                         String c = getAlphaNumericString(8);
    //                         do {
    //                             c = getAlphaNumericString(8);
    //                         } while (code.contains(c));
    //                         System.out.println(c);
    //                         code.add(c);
    //                         int i_hour = 0;

    //                         if (h == 0)
    //                             i_hour = (int) Math.random()*(11-9+1)+9;  
    //                         else if (h == 1)
    //                             i_hour = (int) Math.random()*(15-12+1)+12;  
    //                         else if (h == 2)
    //                             i_hour = (int) Math.random()*(18-16+1)+16;  
    //                         else
    //                             i_hour = (int) Math.random()*(23-19+1)+19;  
                            
                                
    //                         int i_min = (int) Math.random()*(59-0+1)+0;
    //                         System.out.println(i_min);
    //                         int i_sec = (int) Math.random()*(59-0+1)+0;
    //                         int p_time = 0;

    //                         if (restaurants.get(r) == 'A' || restaurants.get(r) == 'B')
    //                             p_time = (int) Math.random()*(6-2+1)+2;  
    //                         else if (restaurants.get(r) == 'C' || restaurants.get(r) == 'D')
    //                             p_time = (int) Math.random()*(20-7+1)+7;  
    //                         else
    //                             p_time = (int) Math.random()*(5-1+1)+1;  

    //                             f.write("\t Código: " + c +  "\t Iniciado: " + i_hour + ":" + i_min + ":" + i_sec + " \t\t Tempo previsto: " + p_time + "\n");
    //                     }
    //                 }
    //             }

    //         }      
    //         f.close();
    //     }

    //     catch (IOException e) {
    //         System.out.print(e.getMessage());
    //     }
    // }
}
