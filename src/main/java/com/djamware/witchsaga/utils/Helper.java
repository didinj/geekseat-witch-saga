package com.djamware.witchsaga.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Helper {
    /*
        The rules that witches decided to kill villagers each year were based on the Fibonacci sequence.
        The number of years is limited by the last year when villagers solve the problem.
        This method will return as a hashmap type.
    */
    public static HashMap<Integer, Integer> generateYearAndKilledVillagers() {
        // the initial variable of fibonacci sequence
        int firstTerm = 1, secondTerm = 1;
        // collecting the villagers who are killed every year
        List<Integer> tempNumbers = new ArrayList<Integer>();
        // collecting year and the sum of killed villagers each year
        HashMap<Integer, Integer> yearAndKilledVillagers = new HashMap<Integer, Integer>();

        // loop through the set maximum year
        for (int i = 1; i <= 100; ++i) {
            /* add the initial first term as the first number
               and the second term as the second number in the sequence */
            if (i == 1) {
                tempNumbers.add(firstTerm);
            } else if (i == 2) {
                tempNumbers.add(secondTerm);
            } else {
                // the sum of the first term and a second term that will be added to the sequence
                int nextTerm = firstTerm + secondTerm;
                firstTerm = secondTerm; // this is how Fibonacci works, the first term replaced with the second term
                secondTerm = nextTerm; // and the second term replace with the sum of both
                tempNumbers.add(nextTerm);
            }

            // sums the numbers inside the sequence so it will be the sum of killed villagers in a year
            int sums = tempNumbers.stream().mapToInt(Integer::intValue).sum();
            // insert the year and the sum of killed villagers to the hashmap
            yearAndKilledVillagers.put(i, sums);
        }

        return yearAndKilledVillagers;
    }

    // calculate the birth year by subtracting death year with age
    public static Integer getBirthYear(int age, int death) {
        return death - age;
    }

    // get the number of killed villagers from the hashmap by the birth year
    public static Integer getKilledNumberByBirth(Integer birth, HashMap<Integer, Integer> yearAndVillagers) {
        // check if the birth less than the first year of witches kill the villagers then return -1 if less
        if (birth < 1)
            return -1;

        // method to get value by a key which the birth year is the key
        return yearAndVillagers.get(birth);
    }

    // find average killed by birth year of two people whose age of death and year of death are known
    public static Double averageKilled(double firstKilledNumbers, double secondKilledNumbers) {
        if (firstKilledNumbers < 1 || secondKilledNumbers < 1)
            return -1.0;
        return (double) ((firstKilledNumbers + secondKilledNumbers) / 2);
    }
}
