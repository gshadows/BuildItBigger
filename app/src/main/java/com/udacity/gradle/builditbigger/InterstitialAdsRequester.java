package com.udacity.gradle.builditbigger;

/**
 * Defines an activity that requires interstitial ads display.
 */
public interface InterstitialAdsRequester {
  
  /**
   * Registers interstitial ads display in the activity that requires this.
   * @param display 
   */
  void registerInterstitialAdDisplay (InterstitialAdDisplay display);
  
  /**
   * This called when interstitial ad closed by the user or failed to open.
   * This allows activity to proceed what it planned to do after ad displayed.
   */
  void onInterstitialAdFinished();
}
