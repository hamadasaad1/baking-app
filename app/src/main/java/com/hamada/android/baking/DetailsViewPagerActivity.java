package com.hamada.android.baking;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.hamada.android.baking.Adapter.RecipeAdapter;
import com.hamada.android.baking.Adapter.StepPagerAdapter;
import com.hamada.android.baking.FragmentPager.ViewPagerAdapter;
import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.Model.Ingredient;
import com.hamada.android.baking.Widget.AppWidgetService;
import com.hamada.android.baking.Widget.BakingWidget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsViewPagerActivity extends AppCompatActivity {

     @BindView(R.id.viewPager) ViewPager viewPager;
    private ViewPagerAdapter adapter;
     @BindView(R.id.tabs) TabLayout tabLayout;
    private BakingResponse mBaking;
    private String recipeName;
    public static boolean isTwoPane;
    private SharedPreferences sharedPreferences;
     @BindView(R.id.fab) FloatingActionButton fab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view_pager);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        intialView();
        Intent intent=getIntent();
        if (intent.hasExtra(RecipeAdapter.RECIPE_EXTRA)) {
            mBaking = getIntent().getParcelableExtra(RecipeAdapter.RECIPE_EXTRA);
            recipeName = mBaking.getName(); //to get name

            setTitle(recipeName);

        }
        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        if ((sharedPreferences.getInt("ID", -1) == mBaking.getId())){

           fab.setImageResource(R.drawable.baseline_favorite_black_18dp);
        }
        //this button to add in widget
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isRecipeInWidget = (sharedPreferences.getInt(Utils.PREFERENCES_ID,
                        -1) == mBaking.getId());
                if (isRecipeInWidget){
                    Snackbar.make(view, "Remove from  Widget", Snackbar.LENGTH_LONG)
                            .show();
                    sharedPreferences.edit()
                            .remove(Utils.PREFERENCES_ID)
                            .remove(Utils.PREFERENCES_WIDGET_TITLE)
                            .remove(Utils.PREFERENCES_WIDGET_CONTENT)
                            .apply();
                    fab.setImageResource(R.drawable.favorite_border_black_18dp);
                }else {
                    sharedPreferences
                            .edit()
                            .putInt(Utils.PREFERENCES_ID, mBaking.getId())
                            .putString(Utils.PREFERENCES_WIDGET_TITLE, mBaking.getName())
                            .putString(Utils.PREFERENCES_WIDGET_CONTENT, ingredientsString())
                            .apply();
                    fab.setImageResource(R.drawable.baseline_favorite_black_18dp);
                    Snackbar.make(view,"Add to Widget",Snackbar.LENGTH_LONG).show();
                }
                ComponentName provider = new ComponentName(getApplication(), BakingWidget.class);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                int[] ids = appWidgetManager.getAppWidgetIds(provider);
                BakingWidget widget=new BakingWidget();
                widget.onUpdate(getApplicationContext(),appWidgetManager,ids);
                //AppWidgetService.updateWidget(getApplicationContext(), mBaking);
            }
        });

    }
    private String ingredientsString(){
        StringBuilder result = new StringBuilder();
        for (Ingredient ingredient :  mBaking.getIngredients()){
            result.append(ingredient.getDoseStr()).append(" ").append(ingredient.getIngredient()).append("\n");
        }
        return result.toString();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void intialView(){


        if (findViewById(R.id.item_details_container_fragment )!=null){

            isTwoPane=true;
//            Bundle bundle=new Bundle();
//            bundle.putParcelable(StepPagerAdapter.EXTRASTEP,getIntent().getParcelableExtra(StepPagerAdapter.EXTRASTEP));
//
//            StepFragment fragment=new StepFragment();
//            fragment.setArguments(bundle);
//            FragmentManager fragmentManager=getSupportFragmentManager();
//            fragmentManager.beginTransaction().add(R.id.frame_containar
//                    ,fragment)
//                    .commit();

        }else {
            isTwoPane=false;
        }
    }
}
