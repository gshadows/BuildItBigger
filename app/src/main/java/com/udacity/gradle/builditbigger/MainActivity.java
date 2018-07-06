package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jokesandroid.JokeActivity;


public class MainActivity extends AppCompatActivity implements
    JokeRetrieverAsyncTask.OnJokeReceivedCallback,
    InterstitialAdsRequester
{
  // In free version set by an ad fragment to allow interstitial ads display.
  // In paid version stays null because no ads should exist.
  private InterstitialAdDisplay mInterstitialAdDisplay;
  
  private SimpleIdlingResource mIdlingResource;
  
  
  /**
   * In free version called by the fragment to register itself as interstitial ads provider (display).
   * In free version nobody calls it because ads fragment and all it's code does not exists.
   * @param display Interstitial ads display (provider) interface.
   */
  @Override
  public void registerInterstitialAdDisplay (InterstitialAdDisplay display) {
    mInterstitialAdDisplay = display;
  }
  
  @VisibleForTesting
  public IdlingResource getIdlingResource() {
    if (mIdlingResource == null) mIdlingResource = new SimpleIdlingResource();
    return mIdlingResource;
  }
  
  
  @Override
  protected void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getIdlingResource();
  }
  
  
  @Override
  public boolean onCreateOptionsMenu (Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }
  
  
  @Override
  public boolean onOptionsItemSelected (MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    
    return super.onOptionsItemSelected(item);
  }
  
  
  public void tellJoke (View view) {
    
    mIdlingResource.setIdleState(false);
    
    // Try to show interstitial ad first.
    if (mInterstitialAdDisplay != null) {
      // Free app shows ad.
      mInterstitialAdDisplay.showInterstitialAd();
    } else {
      // Paid version continues immediately.
      onInterstitialAdFinished();
    }
    
  }
  
  
  @Override
  public void onInterstitialAdFinished() {
    // Start loading joke.
    new JokeRetrieverAsyncTask().execute(this);
  }
  
  
  @Override
  public void onJokeReceived (String joke) {
    mIdlingResource.setIdleState(true);
    
    if (joke == null) {
      Toast.makeText(this, getString(R.string.failed_retrieve_joke), Toast.LENGTH_SHORT).show();
      return;
    }
    
    Intent intent = new Intent(this, JokeActivity.class);
    intent.putExtra(JokeActivity.EXTRA_JOKE, joke);
    startActivity(intent);
  }
}
