package com.hamada.android.baking;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hamada.android.baking.Adapter.RecipeAdapter;
import com.hamada.android.baking.FragmentPager.ViewPagerAdapter;
import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.Widget.AppWidgetService;

public class DetailsViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private BakingResponse mBaking;
    private String recipeName;
    private FloatingActionButton fab;



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
        fab = findViewById(R.id.fab);
        tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent=getIntent();
        if (intent.hasExtra(RecipeAdapter.RECIPE_EXTRA)) {
            mBaking = getIntent().getParcelableExtra(RecipeAdapter.RECIPE_EXTRA);
            recipeName = mBaking.getName(); //to get name

            setTitle(recipeName);

        }
        //this button to add in widget
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                AppWidgetService.updateWidget(getApplicationContext(), mBaking);
            }
        });
    }
}
