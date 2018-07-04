package com.example.libjokes;

import java.util.Random;


public class JokeTeller {
  
  private static final String[] mJokesArray = {
      "- How many programmers does it take to change a lightbulb?\n- None. That is a hardware problem.",
      "There are ten kinds of people in this world:\nthose who understand binary and those who don't.",
      "The QA engineer orders a beer.\nOrders 0 beers.\nOrders 999999999 beers.\nOrders a lizard.\nOrders -1 beers.\nOrders a sfdeljknesv.",
  };
  
  private static final Random random = new Random();
  
  
  public static String TellMeAJoke() {
    return mJokesArray[random.nextInt(mJokesArray.length)];
  }
  
}
