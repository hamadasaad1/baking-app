package com.hamada.android.baking.FragmentPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hamada.android.baking.Adapter.RecipeAdapter;
import com.hamada.android.baking.Adapter.StepPagerAdapter;
import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.Model.Ingredient;
import com.hamada.android.baking.Model.Step;
import com.hamada.android.baking.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentStep extends Fragment {


    public BakingResponse mBaking;
    public List<Step> recipeStep;
    public String recipeName;
    public ArrayList<Object> bakingObject;

    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_details, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bakingObject = new ArrayList<>();
        //intent to get value
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(RecipeAdapter.RECIPE_EXTRA)) {
            mBaking = getActivity().getIntent().getParcelableExtra(RecipeAdapter.RECIPE_EXTRA);
            recipeStep = mBaking.getSteps(); //to get steps
            recipeName = mBaking.getName(); //to get name
            bakingObject.addAll(recipeStep);
        }else {
            Toast.makeText(getActivity(), "not data", Toast.LENGTH_SHORT).show();
        }
        recyclerView =view.findViewById(R.id.item_list_recycler_view);
        StepPagerAdapter adapter=new StepPagerAdapter(bakingObject);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
