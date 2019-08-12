package com.example.minimaltodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    ArrayList<TodoItem> list;


    public RecyclerAdapter(ArrayList<TodoItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ItemViewHolder holder, int position) {
        holder.listTitle.setText(list.get(position).getTitle());
        holder.listDate.setText(list.get(position).getDate());
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView listTitle , listDate;
        ImageView listImage;

        ItemViewHolder(View itemView){
            super(itemView);

            listTitle = itemView.findViewById(R.id.listTitle);
            listDate = itemView.findViewById(R.id.listDate);
            listImage = itemView.findViewById(R.id.listImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(TodoItem item, int position){
        list.add(position, item);
        notifyItemInserted(position);
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

}
