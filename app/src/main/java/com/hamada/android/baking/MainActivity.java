package com.hamada.android.baking;

import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hamada.android.baking.Adapter.RecipeAdapter;
import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.internet.Api.Service;
import com.hamada.android.baking.internet.generator.ServiceGenerator;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public final static String TAG=MainActivity.class.getSimpleName();
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_main)
    SwipeRefreshLayout swipeRefreshLayout;
    private RecipeAdapter mAdapter;
    private List<BakingResponse> mListRecipe=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        viewInitial();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                viewInitial();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void viewInitial(){

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerview.setHasFixedSize(true);
        if (getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
            mRecyclerview.setLayoutManager(layoutManager);
        }else {
            mRecyclerview.setLayoutManager(new GridLayoutManager(this,3));
        }

        loadJson();

    }

    private void loadJson(){
        Service service=ServiceGenerator.createService(Service.class);
        Call<JsonArray> call=service.getJson();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                Log.d(TAG,"load data ::");
                if (response.isSuccessful()){

                    if (response.body() !=null){

                        String listString=response.body().toString();
                        Type listType=new TypeToken<List<BakingResponse>>(){

                        }.getType();
                       mListRecipe=getListJsonFromt(listString,listType);
                       Log.d(TAG,"load item ::"+mListRecipe.get(0));
                       mRecyclerview.setItemAnimator(new DefaultItemAnimator());
                       mRecyclerview.setAdapter(new RecipeAdapter(getApplicationContext(),mListRecipe));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                Log.d(TAG,"Error load Data"+t.getMessage());
            }
        });
    }
    private static <T> List<T> getListJsonFromt(String jsonString,Type type){
        if(!isVaild(jsonString)){
           return null;
        }
        return new Gson().fromJson(jsonString,type);


    }
    private static boolean isVaild(String json){
        try {
            new JsonParser().parse(json);
                    return true;
        }catch (JsonSyntaxException j){
            return false;
        }
    }
}
