package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


class JokeRetrieverAsyncTask extends AsyncTask<JokeRetrieverAsyncTask.OnJokeReceivedCallback, Void, String> {
  public static final String TAG = JokeRetrieverAsyncTask.class.getSimpleName();
  
  public interface OnJokeReceivedCallback { void onJokeReceived (String joke); }
  
  //public static final String ROOT_URL = "http://10.0.2.2:8080/_ah/api/"; // 10.0.2.2 is localhost's IP address in Android emulator
  public static final String ROOT_URL = "http://192.168.0.2:8080/_ah/api/";
  
  private static MyApi mApiService = null;
  private OnJokeReceivedCallback mCallback;
  
  
  static private void createApiService() {
    if (mApiService != null) return;
    
    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
        new AndroidJsonFactory(), null)
        .setRootUrl(ROOT_URL)
        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
          @Override
          public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
            // Turn off compression when running against local devappserver
            abstractGoogleClientRequest.setDisableGZipContent(true);
          }
        });
    
    mApiService = builder.build();
  }
  
  
  @Override
  protected String doInBackground (OnJokeReceivedCallback... params) {
    createApiService();  // Only do this once
  
    mCallback = params[0];
    
    try {
      return mApiService.getJoke().execute().getData();
    } catch (IOException e) {
      Log.e(TAG, e.getMessage());
      return null;
    }
  }
  
  
  @Override
  protected void onPostExecute (String result) {
    if (mCallback != null) mCallback.onJokeReceived(result);
  }
}
