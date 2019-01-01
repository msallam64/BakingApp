package com.example.mohamedsallam.project_3.appUi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mohamedsallam.project_3.idlingResourceTest.MyIdling;
import com.example.mohamedsallam.project_3.R;
import com.example.mohamedsallam.project_3.models.Recipe;
import com.example.mohamedsallam.project_3.myAdapters.Adapter;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements Adapter.ListItemClickListener {

    static String selected_recipe = "Recipes";
    static String selected_steps = "Steps";
    static String selected_index = "Index";
    @Nullable
    private MyIdling mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new MyIdling();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_view);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Baking App");
        getIdlingResource();
    }

    @Override
    public void onListItemClick(Recipe selectedItemIndex) {
        Bundle selectedBundle = new Bundle();
        ArrayList<Recipe> modelModelRecipe = new ArrayList<>();
        modelModelRecipe.add(selectedItemIndex);
        selectedBundle.putParcelableArrayList(selected_recipe, modelModelRecipe);
        final Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtras(selectedBundle);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
