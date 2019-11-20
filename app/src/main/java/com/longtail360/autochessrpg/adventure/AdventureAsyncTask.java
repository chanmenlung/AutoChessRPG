package com.longtail360.autochessrpg.adventure;

import android.os.AsyncTask;

import com.longtail360.autochessrpg.activity.HomeActivity;

public class AdventureAsyncTask extends AsyncTask<String, Integer, Long> {
    AdvEngine advEngine;
    public HomeActivity homeActivity;
    @Override
    protected Long doInBackground(String... urls) {
        advEngine = new AdvEngine();
        publishProgress(2);
        return 1l;
    }

    @Override
    protected void onPostExecute(Long result) {

    }
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        homeActivity.progressBar.setProgress(values[0]);
    }

}
