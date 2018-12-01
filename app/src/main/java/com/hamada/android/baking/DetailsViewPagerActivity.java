package com.hamada.android.baking;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;

import com.hamada.android.baking.Adapter.RecipeAdapter;
import com.hamada.android.baking.FragmentPager.ViewPagerAdapter;
import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.Model.Ingredient;
import com.hamada.android.baking.Model.Step;

import java.util.List;

public class DetailsViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;

    private BakingResponse mBaking;
    private String recipeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager=findViewById(R.id.viewPager);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent=getIntent();
        if (intent.hasExtra(RecipeAdapter.RECIPE_EXTRA)) {
            mBaking = getIntent().getParcelableExtra(RecipeAdapter.RECIPE_EXTRA);
            recipeName = mBaking.getName(); //to get name

            setTitle(recipeName);
        }
    }
}
