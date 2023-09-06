package com.example.myquotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myquotesapp.Model.Model;
import com.example.myquotesapp.Utils.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FetchAdviceTask.OnAdviceFetchedListener{


    private DBHelper myDB;
     Button share,favQuote,moveToFavorite;
    private TextView quote;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        share = findViewById(R.id.btnShare);
        quote = findViewById(R.id.txtquote);
        favQuote = findViewById(R.id.btnfavorite);
       moveToFavorite = findViewById(R.id.moveToFavorite);
        myDB =new DBHelper(MainActivity.this);
       // getSupportActionBar().hide();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sharetxt = quote.getText().toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT , sharetxt);
                startActivity(sendIntent);
            }
        });

        //Fetching New Quote from API
        FetchAdviceTask fetchAdviceTask = new FetchAdviceTask(this);
        fetchAdviceTask.execute();

        //Save the Quote as Favorite Quote
        favQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String textQuote = quote.getText().toString();
                Model item = new Model();
                item.setFavoriteQuote(textQuote);
                Toast.makeText(MainActivity.this, " Added to favorite Successfully", Toast.LENGTH_SHORT).show();
                myDB.insertTask(item);
            }
        });

        //Move from Mainactivity To Saved Favorite Quotes(Favorite)
        moveToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Favorite.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onAdviceFetched(String adviceJson) {
        try {
            JSONObject adviceObject = new JSONObject(adviceJson);
            JSONObject slipObject = adviceObject.getJSONObject("slip");
            String advice = slipObject.getString("advice");
            quote.setText(advice);
            // Now you have the advice, you can display it in your app
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}