package com.bearm.owlbotdictionary.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bearm.owlbotdictionary.Model.Word;
import com.bearm.owlbotdictionary.Model.WordEntry;
import com.bearm.owlbotdictionary.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public ArrayList<WordEntry> mDataset;
    public Context context;
    private String example;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tvDefinition;
        TextView tvType;
        TextView tvExample;
        ImageView ivImage;

        public MyViewHolder(View v) {
            super(v);
            tvDefinition = v.findViewById(R.id.tv_definition);
            tvType = v.findViewById(R.id.tv_type);

            tvExample = v.findViewById(R.id.tv_example);
            ivImage = v.findViewById(R.id.iv_image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<WordEntry> myDataset, Context myContext) {
        mDataset = myDataset;
        context = myContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvDefinition.setText(mDataset.get(position).definition);
        holder.tvType.setText(mDataset.get(position).type);
        example = mDataset.get(position).example;
        if(!example.equals("null")){
            holder.tvExample.setVisibility(View.VISIBLE);
            holder.tvExample.setText(Html.fromHtml(example, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.tvExample.setVisibility(View.GONE);
        }

        if(mDataset.get(position).image != null) {
            Picasso.with(context)
                    .load(mDataset.get(position).image)
                    .into(holder.ivImage);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}