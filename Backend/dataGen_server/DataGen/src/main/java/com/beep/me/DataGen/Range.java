package com.beep.me.DataGen;

import java.util.ArrayList;

public class Range {

    private ArrayList<Integer> breakfast;
    private ArrayList<Integer> lunch;
    private ArrayList<Integer> snack;
    private ArrayList<Integer> dinner;

    private ArrayList<ArrayList<Integer>> timeTable;

    public Range() {
        breakfast = new ArrayList<>();
        lunch = new ArrayList<>();
        snack = new ArrayList<>();
        dinner = new ArrayList<>();

        breakfast.add(8);
        breakfast.add(10);
        breakfast.add(30);

        lunch.add(11);
        lunch.add(14);
        lunch.add(80);

        snack.add(15);
        snack.add(18);
        snack.add(30);

        dinner.add(19);
        dinner.add(23);
        dinner.add(90);

        timeTable = new ArrayList<>();
        timeTable.add(breakfast);
        timeTable.add(lunch);
        timeTable.add(snack);
        timeTable.add(dinner);
    }

    public boolean getProbability(Integer hour) {

        Double prob = null;

        for (ArrayList<Integer> meal : timeTable) {
            Integer min = meal.get(0);
            Integer max = meal.get(1);
            if (hour >= min && hour <=max) {
                System.out.println("PROB MEAL: " + (meal.get(2) / 100));
                prob = (double) (meal.get(2) / 100);
            }
        }

        if (prob == null) {
            return false;
        }

        double r = Math.random();
        System.out.println("PROB: " + prob);
        System.out.println("HOUR: " + hour);
        return r < prob;


    }

    
    
}
