package com.cookandroid.pocketlist;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<List> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<List> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

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
        for(int i = 1; i <= arrayList.get(position).getStar(); i++) {
            if (i == 2)
                holder.star2.setImageResource(R.drawable.star);
            if (i == 3)
                holder.star3.setImageResource(R.drawable.star);
            if (i == 4)
                holder.star4.setImageResource(R.drawable.star);
            if (i == 5)
                holder.star5.setImageResource(R.drawable.star);
        }
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView picture, star1, star2, star3, star4, star5;
        TextView name, info, date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.picture);
            name = itemView.findViewById(R.id.name);
            info = itemView.findViewById(R.id.info);
            date = itemView.findViewById(R.id.date);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
        }
    }
}
