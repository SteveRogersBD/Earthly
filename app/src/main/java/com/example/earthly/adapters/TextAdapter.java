package com.example.earthly.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earthly.R;
import com.example.earthly.models.ListItem;

import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder>{

    Context context;
    List<ListItem>titles;

    public TextAdapter(Context context, List<ListItem> titles) {
        this.context = context;
        this.titles = titles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = titles.get(position);
        holder.textView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title_list);
        }
    }
}
