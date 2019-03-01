package com.example.scanqr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    MyAdapter myAdapter;
 ArrayList<data>arrayList;
 DbHelper helper;
   View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        helper = new DbHelper(this);
        arrayList = helper.getAllInformation();

        if (arrayList.size() > 0) {
            myAdapter = new MyAdapter(arrayList, this);
            recyclerView.setAdapter(myAdapter);
        } else {
            Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
        }

        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position= viewHolder.getAdapterPosition();
                data listitem=arrayList.get(position);

                helper.deleteRow(listitem.getid());
                arrayList.remove(position);
                myAdapter.notifyItemRemoved(position);
                myAdapter.notifyItemRangeChanged(position,arrayList.size());

            }
        }).attachToRecyclerView(recyclerView);

        final IntentIntegrator intentIntegrator=new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCameraId(0);

        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentIntegrator.initiateScan();
            }
        });
    }
@Override
    protected  void onActivityResult(int requestcode, int resultcode, Intent data1){
    IntentResult result=IntentIntegrator.parseActivityResult(requestcode,requestcode,data1);
    if(result!=null){
        if(result.getContents()==null){
            Toast.makeText(getApplicationContext(),"no result ", Toast.LENGTH_SHORT).show();
        }
        else {
            boolean isInsetred=helper.insertData(result.getFormatName(),result.getContents());
            if(isInsetred){
                arrayList.clear();
                arrayList=helper.getAllInformation();
                myAdapter=new MyAdapter(arrayList,this);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

            }

        }
    }
    else{
        super.onActivityResult(requestcode,resultcode,data1);

    }
}



}