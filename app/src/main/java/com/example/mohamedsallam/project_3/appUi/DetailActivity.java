package com.example.mohamedsallam.project_3.appUi;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mohamedsallam.project_3.R;
import com.example.mohamedsallam.project_3.models.Recipe;
import com.example.mohamedsallam.project_3.models.Step;
import com.example.mohamedsallam.project_3.myAdapters.DetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity
        implements DetailAdapter.ListItemClickListener, StepFragment.ListItemClickListener {
    static String RECIPES = "All_Recipes";
    static String selected_recipe = "Recipes";
    static String selected_steps = "Steps";
    static String selected_index = "Index";
    static String STACK_DETAIL = "STACK_DETAIL";
    static String STACK_STEP_DETAIL = "STACK_STEP_DETAIL";
    private ArrayList<Recipe> mrecipe;
    String mrecipeName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if (savedInstanceState == null) {
            Bundle selectedRecipeBundle = getIntent().getExtras();
            mrecipe = new ArrayList<>();
            mrecipe = selectedRecipeBundle.getParcelableArrayList(selected_recipe);
            mrecipeName = mrecipe.get(0).getName();
            final DetailFragment fragment = new DetailFragment();
            fragment.setArguments(selectedRecipeBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_detail_container, fragment).addToBackStack(STACK_DETAIL)
                    .commit();
            if (findViewById(R.id.my_recipe_detail_linearlayout).getTag() != null
                    && findViewById(R.id.my_recipe_detail_linearlayout).getTag().equals("tablet-land")) {
                final StepFragment fragment2 = new StepFragment();
                fragment2.setArguments(selectedRecipeBundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_detail_container2, fragment2).addToBackStack(STACK_STEP_DETAIL)
                        .commit();
            }
        } else {
            mrecipeName = savedInstanceState.getString("Title");
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_view);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mrecipeName);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                if (findViewById(R.id.fragment_detail_container2) == null) {
                    if (fm.getBackStackEntryCount() > 1) {
                        fm.popBackStack(STACK_DETAIL, 0);
                    } else if (fm.getBackStackEntryCount() > 0) {
                        finish();
                    }
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void onListItemClick(List<Step> stepsOut, int selectedItemIndex, String rName) {
        final StepFragment fragment = new StepFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportActionBar().setTitle(rName);
        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(selected_steps, (ArrayList<Step>) stepsOut);
        stepBundle.putInt(selected_index, selectedItemIndex);
        stepBundle.putString("Title", rName);
        fragment.setArguments(stepBundle);
        if (findViewById(R.id.my_recipe_detail_linearlayout).getTag() != null && findViewById(R.id.my_recipe_detail_linearlayout).getTag().equals("tablet-land")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_detail_container2, fragment).addToBackStack(STACK_STEP_DETAIL)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_detail_container, fragment).addToBackStack(STACK_STEP_DETAIL)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title", mrecipeName);
    }


}
