package com.example.mohamedsallam.project_3.networking;

import com.example.mohamedsallam.project_3.models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRecipe {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}