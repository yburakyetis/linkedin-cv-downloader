package org.test.automation.browser;

import java.util.Random;

public class InteractionPacing {

  private static final Random RANDOM = new Random();

  public static void randomWait(int minSec, int maxSec) {
    int wait = minSec + RANDOM.nextInt(maxSec - minSec + 1);
    sleep(wait * 1000L);
  }

  public static void microPause() {
    sleep(300 + RANDOM.nextInt(700));
  }

  private static void sleep(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ignored) {
    }
  }

  public static void randomScrollDown(Runnable scrollAction) {
    int scrolls = 2 + RANDOM.nextInt(4);
    for (int i = 0; i < scrolls; i++) {
      scrollAction.run();
      microPause();
    }
  }
}

