package com.example.learnmoto;

import java.util.ArrayList;

public class SubjectArrayClass {

    public static String [] NurserySubject = {"Nursery English", "Nursery Math", "Nursery Science", "Nursery Christian Living"};
    public static String [] KinderSubject = {"Kinder English", "Kinder Math", "Kinder Science", "Kinder Christian Living", "Kinder Filipino"};
    public static String [] Preparatory = {"Preparatory English", "Preparatory Math", "Preparatory Science",
            "Preparatory Christian Living", "Preparatory Filipino", "Preparatory Sibika at Kultura"};
    public static String [] CombineAll = {"Nursery English", "Nursery Math", "Nursery Science", "Nursery Christian Living",
            "Kinder English", "Kinder Math", "Kinder Science", "Kinder Christian Living", "Kinder Filipino",
            "Preparatory English", "Preparatory Math", "Preparatory Science",
            "Preparatory Christian Living", "Preparatory Filipino", "Preparatory Sibika at Kultura"};

    public String[] Combine(String[]x, String [] y){
        String[] result = new String[x.length + y.length];
        //Assign `x` values to result.
        for (int i = 0; i < x.length; i++) {
            result[i] = x[i];
        }

        //Assign `y` values to result. Using resultIndex to start on the first empty position while `i` will be the index for the `y` array.
        for (int i = 0, resultIndex = x.length; i < y.length; i++, resultIndex++) {
            result[resultIndex] = y[i];
        }
        return result;

        //https://stackoverflow.com/questions/19168812/merging-two-string-arrays-in-java
    }
}
