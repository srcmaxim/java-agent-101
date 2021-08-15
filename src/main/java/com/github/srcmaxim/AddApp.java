package com.github.srcmaxim;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class AddApp {

  private static final Logger LOGGER = Logger.getLogger(AddApp.class.getName());

  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      add(i, i);
    }
  }

  private static int add(int a, int b) {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      // ignore
    }
    return a + b;
  }

}
