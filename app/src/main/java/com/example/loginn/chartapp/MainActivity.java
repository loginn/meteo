package com.example.loginn.chartapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.loopj.android.http.*;
import org.json.*;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    BarChart tempChart;
    BarChart pressureChart;

    ArrayList<BarEntry> pressureData = new ArrayList<>();
    ArrayList<BarEntry> tempData = new ArrayList<>();

    BarDataSet pressureSet;
    BarDataSet tempSet;

    ArrayList<IBarDataSet> pressureChartData = new ArrayList<>();
    ArrayList<IBarDataSet> tempChartData = new ArrayList<>();

    TextView outcome;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pressureChart = (BarChart) findViewById(R.id.pressureChart);
        tempChart = (BarChart) findViewById(R.id.tempChart);

        outcome = (TextView) findViewById(R.id.outcome);

        this.run();
    }

    protected void run() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                AsyncHttpClient client = new AsyncHttpClient();
                while (true) {
                    client.get("https://api.thingspeak.com/channels/270077/feeds.json", new AsyncHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            // called before request is started
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            int lastPressure = 0;
                            int lastTemp = 0;

                            try {
                                JSONObject object = new JSONObject(new String(response));
                                JSONArray feeds = object.getJSONArray("feeds");

                                for (; i < feeds.length(); i++) {
                                    JSONObject j = feeds.getJSONObject(i);

                                    BarEntry ep = new BarEntry(i, Integer.parseInt(j.getString("field1")));
                                    BarEntry et = new BarEntry(i, Integer.parseInt(j.getString("field2")));

                                    lastPressure = Integer.parseInt(j.getString("field1"));
                                    lastTemp = Integer.parseInt(j.getString("field2"));

                                    pressureData.add(ep);
                                    tempData.add(et);
                                }

                                if (lastPressure < 995 && lastTemp < 10) {
                                    outcome.setText("Rain is likely");
                                } else if (lastPressure < 995 && lastTemp < 0) {
                                    outcome.setText("Snow is likely");
                                } else {
                                    outcome.setText("Probably no rain");
                                }


                                pressureSet = new BarDataSet(pressureData, "Pressure");
                                pressureSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                                tempSet = new BarDataSet(tempData, "Temperature");
                                tempSet.setAxisDependency(YAxis.AxisDependency.LEFT);

                                pressureChartData.add(pressureSet);
                                tempChartData.add(tempSet);

                                BarData pressureData = new BarData(pressureChartData);
                                BarData tempData = new BarData(tempChartData);

                                pressureChart.setData(pressureData);
                                pressureChart.invalidate();
                                tempChart.setData(tempData);
                                tempChart.invalidate();

                                try {
                                    Thread.sleep(30000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            System.out.println("Could not get the data");
                        }

                        @Override
                        public void onRetry(int retryNo) {
                            // called when request is retried
                        }
                    });
                }
            }
        });
    }
}
