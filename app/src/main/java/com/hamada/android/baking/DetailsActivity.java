package com.hamada.android.baking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hamada.android.baking.Adapter.RecipeAdapter;
import com.hamada.android.baking.Adapter.RecipeAndSetepsAdapter;
import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.Model.Ingredient;
import com.hamada.android.baking.Model.Step;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    public BakingResponse mBaking;
    public List<Ingredient> recipeIngredient;
    public List<Step> recipeStep;
    public String recipeName;
    public ArrayList<Object> bakingObject;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


     inilialData();

    }
    private void inilialData(){
        bakingObject=new ArrayList<>();
        //intent to get value
        Intent intent=getIntent();
        if (intent.hasExtra(RecipeAdapter.RECIPE_EXTRA)){
            mBaking=getIntent().getParcelableExtra(RecipeAdapter.RECIPE_EXTRA);
            recipeIngredient=mBaking.getIngredients();//to get ingredients
            recipeStep=mBaking.getSteps(); //to get steps
            recipeName=mBaking.getName(); //to get name
            bakingObject.addAll(recipeIngredient); //
            bakingObject.addAll(recipeStep);

            setTitle(recipeName);
        }else {
            Toast.makeText(this, "the Data Not Available "
                    , Toast.LENGTH_SHORT).show();
        }
        if (findViewById(R.id.item_details_container) !=null){
            isTwoPane=true;
        }
        View recyclerView =findViewById(R.id.item_list_recycler_view);
        assert recyclerView !=null;
        setupRecyclerView((RecyclerView) recyclerView);

    }
    private void setupRecyclerView (RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecipeAndSetepsAdapter(bakingObject,isTwoPane));
    }
}
