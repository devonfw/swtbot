package com.devonIde.setup;

/**
 * @author rrohitku
 *
 */
public class test {

  /**
   * @param args
   */
  public static void main(String[] args) {

    System.out.println(System.getProperty("os.name").toLowerCase());

    if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
      System.out.println("window");
    }

  }

}
