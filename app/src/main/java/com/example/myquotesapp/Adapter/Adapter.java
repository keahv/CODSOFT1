package com.example.myquotesapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquotesapp.Favorite;
import com.example.myquotesapp.Model.Model;
import com.example.myquotesapp.R;
import com.example.myquotesapp.Utils.DBHelper;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private DBHelper myDB;
   private List<Model> mList;
   private Favorite activity;

    public Adapter(DBHelper myDB, Favorite activity) {
        this.myDB = myDB;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.favoriteview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Model item = mList.get(position);
        holder.favQuote.setText(item.getFavoriteQuote());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "Delete", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               deletQuote(position);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Context getContext(){
        return activity;
    }

    public void setQuotes(List<Model> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deletQuote(int position){
        Model item = mList.get(position);
        myDB.deleteQuote(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView favQuote;
    ImageView imageView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        favQuote = itemView.findViewById(R.id.favQuote);
        imageView = itemView.findViewById(R.id.imageView);
    }
}
}