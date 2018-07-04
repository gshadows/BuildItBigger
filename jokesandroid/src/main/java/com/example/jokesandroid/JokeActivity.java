package com.example.jokesandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class JokeActivity extends AppCompatActivity {
  
  public static final String EXTRA_JOKE = "joke";
  
  
  @Override
  protected void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_joke);
    
    String joke = null;
    Intent intent = getIntent();
    if (intent != null) joke = intent.getStringExtra(EXTRA_JOKE);
    if (joke == null) {
      // No joke passed in an intent.
      Toast.makeText(this, R.string.no_joke_received, Toast.LENGTH_SHORT).show();
      finish();
      return;
    }
    
    TextView jokeTV = findViewById(R.id.joke_tv);
    if (jokeTV == null) {
      // No joke passed in an intent.
      Toast.makeText(this, R.string.int_err_bad_layout, Toast.LENGTH_SHORT).show();
      finish();
      return;
    }
    
    jokeTV.setText(joke);
  }
}
