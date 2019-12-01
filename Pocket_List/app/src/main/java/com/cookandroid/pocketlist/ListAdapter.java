package com.cookandroid.pocketlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements OnListItemClickListener { // 어댑터 클래스가 새로 정의한 리스너 인터페이스 구현하도록 하기
    ArrayList<List> items = new ArrayList<List>();
    OnListItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.recycler_item, viewGroup, false); // 인플레이션을 통해 뷰 객체 만들기

        return new ViewHolder(itemView, this); // 뷰홀더 객체를 생성하면서 뷰 객체를 전달하고 그 뷰홀더 객체 반환
    }

    public void setOnItemClickListener(OnListItemClickListener listener){ // 외부에서 리스너를 설정할 수 있도록 메서드 추가
        this.listener = listener;
    }

    @Override
    public  void onItemClick(ViewHolder holder, View view, int position){
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        List item = items.get(i);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
    TextView bucketName, bucketInfo, bucketDate;
    ImageView picture, star1, star2, star3, star4, star5;

        public ViewHolder(View itemView, final OnListItemClickListener listener) { // 뷰홀더 생성자로 전달되는 뷰 객체 참조하기
            super(itemView);

            picture = itemView.findViewById(R.id.picture);
            bucketName = itemView.findViewById(R.id.bucketName);
            bucketInfo = itemView.findViewById(R.id.bucketInfo);
            bucketDate = itemView.findViewById(R.id.bucketDate);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position); // 아이템 뷰 클릭시 미리 정의한 다른 리스너의 메소드 호출하기
                    }
                }
            });
        }

        public void setItem(List item){
            picture.setImageResource(R.drawable.picture);
            bucketName.setText(item.getName());
            bucketInfo.setText(item.getInfo());
            bucketDate.setText(item.getYear() + "년 " + item.getMonth() + "월 " + item.getDay() + "일");
            for(int i = 1; i <= item.getStar(); i++) {
                if (i == 2)
                    star2.setImageResource(R.drawable.star);
                if (i == 3)
                    star3.setImageResource(R.drawable.star);
                if (i == 4)
                    star4.setImageResource(R.drawable.star);
                if (i == 5)
                    star5.setImageResource(R.drawable.star);
            }
        }
    }

    public void addItem(List item){
        items.add(item);
    }

    public  void setItems(ArrayList<List> items){
        this.items = items;
    }

    public List getItem(int position){
        return items.get(position);
    }

    public List setItem(int position, List item){
        return items.set(position, item);
    }
}
