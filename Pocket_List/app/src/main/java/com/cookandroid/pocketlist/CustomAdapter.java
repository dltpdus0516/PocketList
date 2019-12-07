package com.cookandroid.pocketlist;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<List> arrayList;
    private Context context;
    private  OnListListener mOnListListener;

    public CustomAdapter(ArrayList<List> arrayList, Context context, OnListListener onListListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mOnListListener = onListListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view, mOnListListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPicture())
                .apply(new RequestOptions().circleCrop())
                .into(holder.picture);

        holder.name.setText(arrayList.get(position).getName());
        holder.info.setText(arrayList.get(position).getInfo());
        holder.date.setText(arrayList.get(position).getDate());
        if(arrayList.get(position).getComplete() == 1){
            holder.stars.setVisibility(View.GONE);
            holder.completes.setVisibility(View.VISIBLE);
        }
        else {
            for (int i = 1; i <= arrayList.get(position).getStar(); i++) {
                if(i == 1){
                    holder.star2.setImageResource(R.drawable.star_empty);
                    holder.star3.setImageResource(R.drawable.star_empty);
                    holder.star4.setImageResource(R.drawable.star_empty);
                    holder.star5.setImageResource(R.drawable.star_empty);
                }
                if (i == 2) {
                    holder.star2.setImageResource(R.drawable.star);
                    holder.star3.setImageResource(R.drawable.star_empty);
                    holder.star4.setImageResource(R.drawable.star_empty);
                    holder.star5.setImageResource(R.drawable.star_empty);
                }
                if (i == 3) {
                    holder.star3.setImageResource(R.drawable.star);
                    holder.star4.setImageResource(R.drawable.star_empty);
                    holder.star5.setImageResource(R.drawable.star_empty);
                }
                if (i == 4) {
                    holder.star4.setImageResource(R.drawable.star);
                    holder.star5.setImageResource(R.drawable.star_empty);
                }
                if (i == 5)
                    holder.star5.setImageResource(R.drawable.star);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView picture, star1, star2, star3, star4, star5;
        TextView name, info, date;
        LinearLayout stars, completes;
        OnListListener onListListener;

        public CustomViewHolder(@NonNull View itemView,OnListListener onListListener) {
            super(itemView);

            stars = itemView.findViewById(R.id.stars);
            completes = itemView.findViewById(R.id.completes);
            picture = itemView.findViewById(R.id.picture);
            name = itemView.findViewById(R.id.name);
            info = itemView.findViewById(R.id.info);
            date = itemView.findViewById(R.id.date);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
            this.onListListener = onListListener;

            // 아이템 클릭 이벤트 처리.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListListener.onListClick(getAdapterPosition());
        }
    }

    public interface OnListListener {
        void onListClick(int position) ;
    }

}
