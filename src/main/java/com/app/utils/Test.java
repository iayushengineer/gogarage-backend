package com.app.utils;

public class Test {
  public static void main(String args[]) {

    double dis, amount, markedprice, s;

    markedprice = 2000;

    dis = 2; // 25 mean 25%

    System.out.println("markedprice= " + markedprice);

    System.out.println("discount rate=" + dis);

    s = 100 - dis;

    amount = (s * markedprice) / 100;

    System.out.println("amount after discount=" + amount);

  }
}
