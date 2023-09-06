package com.example.myquotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myquotesapp.Adapter.Adapter;
import com.example.myquotesapp.Model.Model;
import com.example.myquotesapp.Utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DBHelper myDB;
    private List<Model> mList;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mRecyclerView = findViewById(R.id.recyclerView);
        mList = new ArrayList<>();

        myDB =new DBHelper(this);
        adapter = new Adapter(myDB,this);


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        mList = myDB.getAllQuotes();
        adapter.setQuotes(mList);
    }
}
