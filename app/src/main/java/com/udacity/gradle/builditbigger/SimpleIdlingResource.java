package com.udacity.gradle.builditbigger;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;


public class SimpleIdlingResource implements IdlingResource {
  
  @Nullable private volatile ResourceCallback mCallback;
  private AtomicBoolean mIsIdling = new AtomicBoolean(true);
  
  
  public void setIdleState (boolean isIdle) {
    mIsIdling.set(isIdle);
   if (isIdle) {
     ResourceCallback callback = mCallback;
     if(callback != null) callback.onTransitionToIdle();
   }
  }
  
  @Override
  public String getName() {
    return getClass().getSimpleName();
  }
  
  @Override
  public boolean isIdleNow() {
    return mIsIdling.get();
  }
  
  @Override
  public void registerIdleTransitionCallback (ResourceCallback callback) {
    mCallback = callback;
  }
}
