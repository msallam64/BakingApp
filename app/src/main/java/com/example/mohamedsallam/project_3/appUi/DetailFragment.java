package com.example.mohamedsallam.project_3.appUi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mohamedsallam.project_3.R;
import com.example.mohamedsallam.project_3.models.Ingredient;
import com.example.mohamedsallam.project_3.models.Recipe;
import com.example.mohamedsallam.project_3.myAdapters.DetailAdapter;
import com.example.mohamedsallam.project_3.myWidget.UpdateService;
import java.util.ArrayList;
import java.util.List;

import static com.example.mohamedsallam.project_3.appUi.DetailActivity.selected_recipe;

public class DetailFragment extends Fragment  {
    ArrayList<Recipe> modelRecipe;
    String recipeName;
    public DetailFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;
        TextView textView;
        modelRecipe = new ArrayList<>();
        if(savedInstanceState != null) {
            modelRecipe = savedInstanceState.getParcelableArrayList(selected_recipe);

        }
        else {
            modelRecipe =getArguments().getParcelableArrayList(selected_recipe);
        }

        List<Ingredient> modelIngredients = modelRecipe.get(0).getIngredients();
        recipeName= modelRecipe.get(0).getName();

        View rootView = inflater.inflate(R.layout.detail_fragment_body, container, false);
        textView = (TextView)rootView.findViewById(R.id.recipe_detail_textview);

        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();


        modelIngredients.forEach((a) ->
            {
                textView.append("\u2022 "+ a.getIngredient()+"\n");
                textView.append("\t\t\t Quantity: "+a.getQuantity().toString()+"\n");
                textView.append("\t\t\t Measure: "+a.getMeasure()+"\n\n");

                recipeIngredientsForWidgets.add(a.getIngredient()+"\n"+
                        "Quantity: "+a.getQuantity().toString()+"\n"+
                        "Measure: "+a.getMeasure()+"\n");
            });

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recipe_detail_recyclerview);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        DetailAdapter mRecipeDetailAdapter =new DetailAdapter((DetailActivity)getActivity());
        recyclerView.setAdapter(mRecipeDetailAdapter);
        mRecipeDetailAdapter.setMasterRecipeData(modelRecipe,getContext());

        UpdateService.startBakingService(getContext(),recipeIngredientsForWidgets);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(selected_recipe, modelRecipe);
        currentState.putString("Title",recipeName);
    }


}


