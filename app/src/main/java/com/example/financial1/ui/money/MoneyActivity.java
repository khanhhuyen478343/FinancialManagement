package com.example.financial1.ui.money;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financial1.R;
import com.example.financial1.adapter.ExchangeRateAdapter;
import com.example.financial1.dao.ExchangeRate_json;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MoneyActivity extends AppCompatActivity {

    ListView list_view;
    ExchangeRateAdapter exchangeRateAdapter;
    ArrayList<ExchangeRate_json> listExchangeRate;
    FloatingActionButton fab_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_rate_fragment);
        addControls();
//        fab_exit = findViewById(R.id.fab_exit);
//        fab_exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MoneyActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    private void addControls() {
        list_view = findViewById(R.id.list_view);
        listExchangeRate = new ArrayList<>();
        exchangeRateAdapter = new ExchangeRateAdapter(
                MoneyActivity.this, R.layout.money_item, listExchangeRate
        );
        list_view.setAdapter(exchangeRateAdapter);
        ExchangeRateTask exchangeRateTask = new ExchangeRateTask();
        exchangeRateTask.execute();
    }

    class ExchangeRateTask extends AsyncTask<Void, Void, List<ExchangeRate_json>> {

        @Override
        protected List<ExchangeRate_json> doInBackground(Void... voids) {
            ArrayList<ExchangeRate_json> list = new ArrayList<>();
            try {
                URL url = new URL("https://www.dongabank.com.vn/exchange/export");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0(compatible)");
                connection.setRequestProperty("Accept", "*/*");

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                StringBuilder stringBuilder = new StringBuilder();
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                inputStreamReader.close();
                String stringjson = stringBuilder.toString();

                stringjson = stringjson.replace("(", "");
                stringjson = stringjson.replace(")", "");
                JSONObject jsonObject = new JSONObject(stringjson);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    ExchangeRate_json exchangeRate_json = new ExchangeRate_json();
                    exchangeRate_json.setType(item.getString("type"));

                    if (item.has("imageurl")) {
                        exchangeRate_json.setImageurl(item.getString("imageurl"));
                        URL imageUrl = new URL(exchangeRate_json.getImageurl());
                        HttpURLConnection imageConnection = (HttpURLConnection) imageUrl.openConnection();
                        imageConnection.setRequestMethod("GET");
                        imageConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                        imageConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible)");
                        imageConnection.setRequestProperty("Accept", "*/*");
                        Bitmap bitmap = BitmapFactory.decodeStream(imageConnection.getInputStream());
                        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 120, 150, true);
                        exchangeRate_json.setBitmap(bitmapScaled);
                    }

                    exchangeRate_json.setBuycash(item.optString("muatienmat", ""));
                    exchangeRate_json.setBuytransfer(item.optString("muack", ""));
                    exchangeRate_json.setSellcash(item.optString("bantienmat", ""));
                    exchangeRate_json.setSelltransfer(item.optString("banck", ""));
                    list.add(exchangeRate_json);
                }

            } catch (Exception e) {
                Log.e("Error:", e.toString());
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<ExchangeRate_json> exchangeRate_jsons) {
            super.onPostExecute(exchangeRate_jsons);
            exchangeRateAdapter.clear();
            exchangeRateAdapter.addAll(exchangeRate_jsons);
            exchangeRateAdapter.notifyDataSetChanged();
        }
    }
}
