package com.example.minimaltodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class DeleteTodo extends ItemTouchHelper.SimpleCallback {
    private DeleteTodoListener listener;

    public DeleteTodo(int dragDirs, int swipeDirs, DeleteTodoListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
    }

    public interface DeleteTodoListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
