package com.example.mohamedsallam.project_3.myAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamedsallam.project_3.R;
import com.example.mohamedsallam.project_3.models.Recipe;
import com.example.mohamedsallam.project_3.models.Step;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.RecyclerViewHolder> {
    List<Step> modelModelSteps;
    private String name;
    final private ListItemClickListener listItemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> stepsOut, int clickedItemIndex, String recipeName);
    }

    public DetailAdapter(ListItemClickListener listener) {
        listItemClickListener = listener;
    }

    public void setMasterRecipeData(List<Recipe> recipesIn, Context context) {
        //lSteps = recipesIn;
        modelModelSteps = recipesIn.get(0).getSteps();
        name=recipesIn.get(0).getName();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.detail_cardview_items;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.textRecyclerView.setText(modelModelSteps.get(position).getId() + ". " + modelModelSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {

        return modelModelSteps != null ? modelModelSteps.size() : 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textRecyclerView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            textRecyclerView = (TextView) itemView.findViewById(R.id.description_recipe);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClick(modelModelSteps, clickedPosition, name);
        }

    }
}
