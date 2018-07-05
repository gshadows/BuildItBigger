package com.udacity.gradle.builditbigger;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jokesandroid.JokeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
  
  @Rule public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);
  
  private IdlingResource mIdlingResource;
  
  
  @Before public void before() {
    mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
    IdlingRegistry.getInstance().register(mIdlingResource);
  }
  
  
  @After public void unregisterIdlingResource() {
    if (mIdlingResource != null) IdlingRegistry.getInstance().unregister(mIdlingResource);
  }
  
  
  @Test public void clickRecipe_CheckCorrectRecipeOpened() {
    
    onView(withId(R.id.tell_joke_button)).perform(click());
    
    // Check non-empty intent extra (joke string) passed.
    intended(allOf(
        isInternal(),
        hasComponent(JokeActivity.class.getName()),
        hasExtras(hasEntry(JokeActivity.EXTRA_JOKE, allOf(
            notNullValue(),
            not(equalTo(""))
        )))
    ));
    
    // Check joke activity opened and joke displayed.
    onView(withId(R.id.joke_tv)).check(matches(allOf(
        notNullValue(),
        not(withText(""))
    )));
    
  }
  
  
}
