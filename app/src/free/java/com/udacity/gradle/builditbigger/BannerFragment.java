package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class BannerFragment extends Fragment implements InterstitialAdDisplay {
  private static final String TAG = BannerFragment.class.getSimpleName();
  
  public BannerFragment() {} // Unused default constructor.
  
  private InterstitialAd mInterstitialAd;
  private InterstitialAdsRequester mRequester;
  
  
  private static AdRequest createAdRequest() {
    // Create an ad request. Check logcat output for the hashed device ID to
    // get test ads on a physical device. e.g.
    // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
    return new AdRequest.Builder()
      .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
      .build();
  }
  
  
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    
    // Prepare interstitial ad if our master activity requires it.
    if (getActivity() instanceof InterstitialAdsRequester) {
    
      // Register ourselves as interstitial ads display.
      mRequester = (InterstitialAdsRequester)getActivity();
      mRequester.registerInterstitialAdDisplay(this);
    
      // Prepare interstitial ad only if we registered.
      mInterstitialAd = new InterstitialAd(getContext());
      mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
      mInterstitialAd.setAdListener(new AdListener() {
        @Override public void onAdClosed() {
          Log.d(TAG, "onAdClosed()");
          mInterstitialAd.loadAd(createAdRequest()); // Load new ad for next time.
          mRequester.onInterstitialAdFinished();
        }
        @Override public void onAdLoaded()                    { Log.d(TAG, "onAdLoaded()"); }
        @Override public void onAdFailedToLoad(int errorCode) { Log.d(TAG, "onAdFailedToLoad(): " + errorCode); }
        @Override public void onAdOpened()                    { Log.d(TAG, "onAdOpened()"); }
        @Override public void onAdLeftApplication()           { Log.d(TAG, "onAdLeftApplication()"); }
        @Override public void onAdClicked()                   { Log.d(TAG, "onAdClicked()"); }
        @Override public void onAdImpression()                { Log.d(TAG, "onAdImpression()"); }
      });
      mInterstitialAd.loadAd(createAdRequest());
    }
  }
  
  
  @Override
  public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    
    // Inflate layout.
    View root = inflater.inflate(R.layout.fragment_banner, container, false);
  
    // Prepare banner.
    {
      AdView mAdView = root.findViewById(R.id.adView);
      if (mAdView != null) mAdView.loadAd(createAdRequest());
    }
    
    return root;
  }
  
  
  /**
   * Called by the activity to display interstitial ads.
   * Idea is to completely remove all code that works with ads in paid version.
   * This achieved because in paid version this fragment class does not exists and never registers
   * this callback in main activity.
   */
  @Override
  public void showInterstitialAd() {
    if (mInterstitialAd.isLoaded()) {
      mInterstitialAd.show();
    } else {
      Log.w(TAG, "The interstitial wasn't loaded yet.");
      mRequester.onInterstitialAdFinished();
    }
  
  }
}
