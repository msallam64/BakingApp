package com.example.mohamedsallam.project_3.appUi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.example.mohamedsallam.project_3.idlingResourceTest.MyIdling;
import com.example.mohamedsallam.project_3.R;
import com.example.mohamedsallam.project_3.models.Recipe;
import com.example.mohamedsallam.project_3.myAdapters.Adapter;
import com.example.mohamedsallam.project_3.networking.IRecipe;
import com.example.mohamedsallam.project_3.networking.RetrofitBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mohamedsallam.project_3.appUi.DetailActivity.RECIPES;

public class RecipeFragment extends Fragment {
    public RecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView mrecyclerView;
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);
        mrecyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_recyclerview);
        Adapter adapter = new Adapter((RecipeActivity) getActivity());
        mrecyclerView.setAdapter(adapter);
        if (rootView.getTag() != null && rootView.getTag().equals("phone-land")) {
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
            mrecyclerView.setLayoutManager(mLayoutManager);
        } else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mrecyclerView.setLayoutManager(mLayoutManager);
        }
        IRecipe iRecipe = RetrofitBuilder.Retrieve();
        Call<ArrayList<Recipe>> recipe = iRecipe.getRecipe();
        MyIdling idlingResource = (MyIdling) ((RecipeActivity) getActivity()).getIdlingResource();
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Log.v("status code: ", statusCode.toString());
                ArrayList<Recipe> modelModelRecipes = response.body();
                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(RECIPES, modelModelRecipes);
                adapter.setRecipeData(modelModelRecipes, getContext());
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
            }
        });

        return rootView;
    }


}
