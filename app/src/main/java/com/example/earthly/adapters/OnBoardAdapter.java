package com.example.earthly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earthly.R;
import com.example.earthly.models.OnBoardItem;

import java.util.List;

public class OnBoardAdapter extends RecyclerView.Adapter<OnBoardAdapter.ViewHolder>{

    Context context;
    List<OnBoardItem>list;

    public OnBoardAdapter(Context context, List<OnBoardItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.onboard_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OnBoardItem item = list.get(position);
        holder.textView.setText(item.getTitle());
        holder.imageView.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.onboard_item_description);
            imageView = itemView.findViewById(R.id.onboard_item_poster);
        }
    }
}