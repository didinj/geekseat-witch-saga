package com.djamware.witchsaga.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

public class Helper {
    /*
        The rules that witches decided to kill villagers each year were based on the Fibonacci sequence.
        The number of years is limited by the birth of given person.
        This method will return as a number of killed villagers as BigInteger type.
    */
    public static BigInteger getKilledOnYear(int birth) {
        // initial variable that hold a number of killed villagers
        BigInteger killedVillagers = new BigInteger("0");

        // initial value for generating fibonacci sequence
        long firstTerm = 1, secondTerm = 1;

        // check if birth year less than the year of witch take control
        if(birth < 1 || birth > 94)
            return new BigInteger("-1");

        // loop to generate fibonacci sequence then sum the killed villagers
        for (int i = 1; i <= birth; i++) {
            if (i == 1) {
                killedVillagers = killedVillagers.add(new BigInteger(String.valueOf(firstTerm)));
            } else if (i == 2) {
                killedVillagers = killedVillagers.add(new BigInteger(String.valueOf(secondTerm)));
            } else {
                long nextTerm = firstTerm + secondTerm;
                firstTerm = secondTerm;
                secondTerm = nextTerm;
                killedVillagers = killedVillagers.add(new BigInteger(String.valueOf(nextTerm)));
            }
//            System.out.println(i + ": " + killedVillagers);
        }

        // maximum year is 94, otherwise it will show error numbers of killed villagers
        return killedVillagers;
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

    // find average killed by birth year of two people whose age of death and year
    // of death are known in biginteger type
    public static BigDecimal averageKilled(BigInteger firstKilledNumbers, BigInteger secondKilledNumbers) {
        if (firstKilledNumbers.signum() == -1 || secondKilledNumbers.signum() == -1)
            return BigDecimal.valueOf(-1);
        BigDecimal fkn = new BigDecimal(firstKilledNumbers);
        BigDecimal skn = new BigDecimal(secondKilledNumbers);
        BigDecimal avg = fkn.add(skn).divide(BigDecimal.valueOf(2));

        return avg;
    }
}
