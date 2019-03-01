package com.example.scanqr;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder>{
    List<data> listItemsArrayList;
    Context context;
   public MyAdapter(List<data>listItemsArrayList, Context context){
       this.listItemsArrayList=listItemsArrayList;
       this.context=context;
   }
    @NonNull
    @Override
    public MyAdapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,parent,false);

        return new MyAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
       data listitem=listItemsArrayList.get(position);
       holder.text.setText(listitem.getmtype());
       holder.text2.setText(listitem.getMcode());
        Linkify.addLinks(holder.text2,Linkify.ALL);



    }

    @Override
    public int getItemCount() {
        return listItemsArrayList.size();
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {
       TextView text,text2;
       CardView cardView;
        public MyAdapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.text);
            text2=(TextView)itemView.findViewById(R.id.text2);
            cardView=(CardView)itemView.findViewById(R.id.cardview);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String type=listItemsArrayList.get(getAdapterPosition()).getmtype();
                    Intent i=new Intent();
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_TEXT,type);
                    i.setType("text/plain");
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
