package com.example.loginn.chartapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pressureChart = (BarChart) findViewById(R.id.pressureChart);
        tempChart = (BarChart) findViewById(R.id.tempChart);

        this.run();
    }

    protected void run() {


        BarEntry ep = new BarEntry(0, 1000);
        BarEntry et = new BarEntry(0, 10);

        pressureData.add(ep);
        tempData.add(et);

        ep = new BarEntry(1, 1100);
        et = new BarEntry(1, 12);

        pressureData.add(ep);
        tempData.add(et);

        ep = new BarEntry(2, 1050);
        et = new BarEntry(2, 25);

        pressureData.add(ep);
        tempData.add(et);
        ep = new BarEntry(3, 1100);
        et = new BarEntry(3, 30);

        pressureData.add(ep);
        tempData.add(et);

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

/*
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("https://api.thingspeak.com/channels/270077/feeds.json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {
                    JSONObject object = new JSONObject(new String(response));
                    JSONArray feeds = object.getJSONArray("feeds");

                    for (; i < feeds.length() ; i++) {
                        JSONObject j = feeds.getJSONObject(i);

                        BarEntry ep = new BarEntry(i, Integer.parseInt(j.getString("field1")));
                        BarEntry et = new BarEntry(i, Integer.parseInt(j.getString("field2")));

                        pressureData.add(ep);
                        tempData.add(et);
                    }

                    BarEntry ep = new BarEntry(i, 1000);
                    BarEntry et = new BarEntry(i, 10);

                    pressureData.add(ep);
                    tempData.add(et);

                    ep = new BarEntry(i, 1100);
                    et = new BarEntry(i, 12);

                    pressureData.add(ep);
                    tempData.add(et);

                    ep = new BarEntry(i, 1050);
                    et = new BarEntry(i, 25);

                    pressureData.add(ep);
                    tempData.add(et);
                    ep = new BarEntry(i, 1100);
                    et = new BarEntry(i, 30);

                    pressureData.add(ep);
                    tempData.add(et);

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
        */
    }
}
