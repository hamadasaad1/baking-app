package com.hamada.android.baking.internet.Api;

import com.google.gson.JsonArray;
import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.internet.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET(Routes.END_URL)
    Call<JsonArray> getJson();
}
