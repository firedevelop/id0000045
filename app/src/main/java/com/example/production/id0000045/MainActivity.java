package com.example.production.id0000045;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button btnStart;
    MyTask objMyTask;

    private static final int SLEEP_TIME = ((60 * 100) / 100);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnstart);
        btnStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objMyTask = new MyTask();
                objMyTask.execute();
            }
        });
    }

    class MyTask extends AsyncTask<Void, Integer, Void> {

        Dialog dialog;
        ProgressBar progressBar;
        TextView tvLoading, tvPer;
        Button btnCancel;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.activity_main);

            progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            tvLoading = (TextView) dialog.findViewById(R.id.tv1);
            tvPer = (TextView) dialog.findViewById(R.id.tvper);
            btnCancel = (Button) dialog.findViewById(R.id.btncancel);

            btnCancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    objMyTask.cancel(true);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for (int i = 1; i < 100; i++) {
                if (isCancelled()) {
                    break;
                } else {
                    System.out.println(i);
                    publishProgress(i);
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            tvLoading.setText("Loading...  " + values[0] + " %");
            tvPer.setText(values[0] + " %");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            dialog.dismiss();

            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();

            alert.setTitle("Completed!!!");
            alert.setMessage("Your Task is Completed SuccessFully!!!");
            alert.setButton("Dismiss", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            alert.show();
        }
    }

}