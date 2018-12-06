package com.sheygam.masa_2018_g1_06_12_18;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button clickBtn, stopBtn;
    private ProgressBar myProgress;
    private TextView countTxt;
    private MyTask myTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickBtn = findViewById(R.id.clickBtn);
        stopBtn = findViewById(R.id.stopBtn);
        countTxt = findViewById(R.id.countTxt);
        myProgress = findViewById(R.id.myProgress);
        stopBtn.setEnabled(false);
        stopBtn.setOnClickListener(this);
        clickBtn.setOnClickListener(this);
//        foo("Str");
//        int[] arr = {1,2,3,4,5};
//        foo("str",45,367,37);
//        foo("String",arr);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.clickBtn){
//            myTask = new MyTask();
//            myTask.execute(100,100);
//            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,100,100);
            for(int i = 0; i < 10; i++){
                if(i%2 == 0){
                    new MyTask("Task #" +i).execute(50,50);
                }else{
                    new MyTask("Task #" +i).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,50,50);
                }
            }
        }else if(v.getId() == R.id.stopBtn){
            myTask.cancel(true);
        }
    }

//    private void foo(String str, int... params){
//        int[] arr = params;
//    }

    class MyTask extends AsyncTask<Integer,Integer,String>{
        String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        protected void onPreExecute() {
            myProgress.setVisibility(View.VISIBLE);
//            clickBtn.setEnabled(false);
//            stopBtn.setEnabled(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            countTxt.setText(String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(Integer... params) {
            int count = params[0];
            int sleepTime = params[1];
            for (int i = 0; i < count; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d("MY_TAG", name + " doInBackground: " + i );

                if(isCancelled()){
                    return "Canceled!";
                }
            }
            return "All done";
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            myProgress.setVisibility(View.INVISIBLE);
//            stopBtn.setEnabled(false);
//            clickBtn.setEnabled(true);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            myProgress.setVisibility(View.INVISIBLE);
//            stopBtn.setEnabled(false);
//            clickBtn.setEnabled(true);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
