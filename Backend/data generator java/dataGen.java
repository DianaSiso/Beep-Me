import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static void main(String[] args) {
        Random rand = new Random();
        HashMap<String, Character> restaurants = new HashMap<String, String>();
        restaurants.put("H3", 'C');
        restaurants.put("KFC", 'B');
        restaurants.put("McDonald's", 'A');
        restaurants.put("Taco Bell", 'B');
        restaurants.put("Vitaminas", 'C');
        restaurants.put("A Gula do Prego", 'C');
        restaurants.put("Aki Grill", 'D');
        restaurants.put("Ali Kebab", 'C');
        restaurants.put("Oita", 'D');
        restaurants.put("Dom Franguito", 'B');
        restaurants.put("Alicarius", 'C');
        restaurants.put("Bica da Ria", 'D');
        restaurants.put("Caffelato", 'E');
        restaurants.put("Caseiro e Bom", 'D');
        restaurants.put("Hummy", 'D');
        restaurants.put("Italian Republic", 'C');
        restaurants.put("Malguinhas e Pregos", 'D');
        restaurants.put("Mania Poké Bowls", 'C');
        restaurants.put("Patrono Pizza", 'B');
        restaurants.put("Pão Divino", 'D');
        restaurants.put("Sical", 'E');
        restaurants.put("Sun Bufê", 'D');
        restaurants.put("Tasquinha do Bacalhau", 'D');
        restaurants.put("Zé da Tripa", 'E');
        try {
            FileWriter f = new FileWriter("data_info.txt");
            for (int d=0; d<30; d++) {
                f.write("Dia: " + d + "\n"); 
                for (String r : restaurants.keySet()) {
                    ArrayList<String> code = new ArrayList<String>();

                    f.write("Restaurante: " + r + "\n");
                    
                    if (restaurants.get(r) == 'A') {
                        String[] orders = {{30, 40}, {100, 150}, {40, 90}, {100, 180}};
                    } else if (restaurants.get(r) == 'B') {
                        String[] orders = {{20, 30}, {80, 120}, {20, 60}, {70, 150}};
                    } else if (restaurants.get(r) == 'C') {
                        String[] orders = {{10, 30}, {70, 110}, {10, 40}, {60, 130}};
                    } else if (restaurants.get(r) == 'D') {
                        String[] orders = {{10, 20}, {50, 90}, {10, 30}, {50, 110}};
                    } else {
                        String[] orders = {{10, 40}, {50, 150}, {10, 60}, {50, 180}};
                    }

                    for (int h=0; h<4; h++) {
                        int min = orders[h][0];
                        int max = orders[h][1];
                        int orders_t = (int) Math.random()*(max-min+1)+min;   

                        for (int i=0; i<orders_t; i++) {
                            do {
                                String c = RandomString.getAlphaNumericString(8);
                            } while (code.contains(c));
                            System.out.println(c);
                            code.add(c);
                            int i_hour = 0;

                            if (h == 0)
                                i_hour = (int) Math.random()*(11-9+1)+9;  
                            else if (h == 1)
                                i_hour = (int) Math.random()*(15-12+1)+12;  
                            else if (h == 2)
                                i_hour = (int) Math.random()*(18-16+1)+16;  
                            else
                                i_hour = (int) Math.random()*(23-19+1)+19;  

                            int i_min = (int) Math.random()*(59-0+1)+0;
                            int i_sec = (int) Math.random()*(59-0+1)+0;
                            int p_time = 0;

                            if (restaurants.get(r) == 'A' || restaurants.get(r) == 'B')
                                p_time = (int) Math.random()*(6-2+1)+2;  
                            else if (restaurants.get(r) == 'C' || restaurants.get(r) == 'D')
                                p_time = (int) Math.random()*(20-7+1)+7;  
                            else
                                p_time = (int) Math.random()*(5-1+1)+1;  

                                f.write("\t Código: " + c +  "\t Iniciado: " + i_hour + ":" + i_min + ":" + i_sec + " \t\t Tempo previsto: " + p_time + "\n");
                        }
                    }
                }

            }      
            f.close();
        }

        catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
