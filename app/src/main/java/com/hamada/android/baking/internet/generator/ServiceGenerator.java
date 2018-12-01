package com.hamada.android.baking.internet.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hamada.android.baking.BuildConfig;
import com.hamada.android.baking.internet.Routes;

import java.text.DateFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static <S> S createService(Class<S> serviceClass){
        String mTakon="";
        //build json
        Gson gson=new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat(DateFormat.LONG)
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();
        //build retrofit
        Retrofit.Builder builder=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Routes.BASE_URL);


        OkHttpClient.Builder httpClient=new OkHttpClient.Builder()
                .readTimeout(90,TimeUnit.SECONDS)
                .connectTimeout(90,TimeUnit.SECONDS)
                .writeTimeout(90,TimeUnit.SECONDS)
                .cache(null);
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.addInterceptor(interceptor);

        }
        builder.client(httpClient.build());
        Retrofit retrofit=builder.build();
        return  retrofit.create(serviceClass);

    }
}
