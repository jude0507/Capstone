package com.example.learnmoto;

import java.util.ArrayList;

public class SubjectArrayClass {
    public static String [] NurserySubject = {"Nursery English", "Nursery Math", "Nursery Science", "Nursery Christian Living"};
    public static String [] KinderSubject = {"Kinder English", "Kinder Math", "Kinder Science", "Kinder Christian Living", "Kinder Filipino"};
    public static String [] Preparatory = {"Preparatory English", "Preparatory Math", "Preparatory Science",
            "Preparatory Christian Living", "Preparatory Filipino", "Preparatory Sibika at Kultura"};

    String[] result;
    public String[] Combine(String[]x, String [] y){
        result = new String[x.length + y.length];
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

//    public String CombineAll(String[] A, String[] B, String[] C)
//    {
//        // creating an empty list to store sorted numbers
//        ArrayList<String> list = new ArrayList<>();
//        int i = 0, j = 0, k = 0;
//
//        // using merge concept and trying to find
//        // smallest of three while all three arrays
//        // contains at least one element
//        while (i < A.length && j < B.length
//                && k < C.length) {
//            String a = A[i];
//            String b = B[j];
//            String c = C[k];
//            if (a.length() <= b.length() && a.length() <= c.length()) {
//                list.add(String.valueOf(a.length()));
//                i++;
//            }
//            else if (b.length() <= a.length() && b.length() <= c.length()) {
//                list.add(String.valueOf(b.length()));
//                j++;
//            }
//            else {
//                list.add(String.valueOf(c.length()));
//                k++;
//            }
//        }
//        // next three while loop is to sort two
//        // of arrays if one of the three gets exhausted
//        while (i < A.length && j < B.length) {
//            if (A[i].length() < B[j].length()) {
//                list.add(String.valueOf(A[i].length()));
//                i++;
//            }
//            else {
//                list.add(String.valueOf(B[j].length()));
//                j++;
//            }
//        }
//        while (j < B.length && k < C.length) {
//            if (B[j].length() < C[k].length()) {
//                list.add(String.valueOf(B[j].length()));
//                j++;
//            }
//            else {
//                list.add(String.valueOf(C[k].length()));
//                k++;
//            }
//        }
//        while (i < A.length && k < C.length) {
//            if (A[i].length() < C[k].length()) {
//                list.add(String.valueOf(A[i].length()));
//                i++;
//            }
//            else {
//                list.add(String.valueOf(C[k].length()));
//                k++;
//            }
//        }
//
//        // if one of the array are left then
//        // simply appending them as there will
//        // be only largest element left
//        while (i < A.length) {
//            list.add(String.valueOf(A[i].length()));
//            i++;
//        }
//        while (j < B.length) {
//            list.add(String.valueOf(B[j].length()));
//            j++;
//        }
//        while (k < C.length) {
//            list.add(String.valueOf(C[k].length()));
//            k++;
//        }
//
//        // finally print the list
//        for (String x : list)
//        return x;
//        return null;
//    } // merge3sorted closing braces


}
