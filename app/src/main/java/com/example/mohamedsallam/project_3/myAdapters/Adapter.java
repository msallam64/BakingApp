package com.example.mohamedsallam.project_3.myAdapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamedsallam.project_3.R;
import com.example.mohamedsallam.project_3.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.RecyclerViewHolder> {
    ArrayList<Recipe> modelModelRecipes;
    Context mContext;
    final private ListItemClickListener listItemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(Recipe clickedItemIndex);
    }

    public Adapter(ListItemClickListener listener) {
        listItemClickListener = listener;
    }

    public void setRecipeData(ArrayList<Recipe> recipesIn, Context context) {
        modelModelRecipes = recipesIn;
        mContext = context;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.cardview_items;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.textRecyclerView.setText(modelModelRecipes.get(position).getName());
        String imageUrl = modelModelRecipes.get(position).getImage();

        if (imageUrl != "") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(mContext).load(builtUri).into(holder.imageRecyclerView);
        }

    }

    @Override
    public int getItemCount() {
        return modelModelRecipes != null ? modelModelRecipes.size() : 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textRecyclerView;
        ImageView imageRecyclerView;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            textRecyclerView = (TextView) itemView.findViewById(R.id.recipe_title);
            imageRecyclerView = (ImageView) itemView.findViewById(R.id.recipe_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClick(modelModelRecipes.get(clickedPosition));
        }

    }
}
