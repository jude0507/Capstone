package com.example.learnmoto.Model;

public class MathModel {
    String operator;
    int dice1;
    int dice2;
    int outputDice;

    public MathModel(int dice1, String operator, int dice2) {
        this.dice1 = dice1;
        this.operator = operator;
        this.dice2 = dice2;
        //this.outputDice = outputDice;
    }

    public MathModel(){}

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getDice1() {
        return dice1;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

//    public int getOutputDice() {
//        return outputDice;
//    }
//
//    public void setOutputDice(int outputDice) {
//        this.outputDice = outputDice;
//    }
}