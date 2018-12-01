package com.hamada.android.baking.Model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BakingResponse implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients ;
    @SerializedName("steps")
    @Expose
    private List<Step> steps ;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public BakingResponse(Integer id, String name, List<Ingredient> ingredients,
                          List<Step> steps, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }
    public static final Creator<BakingResponse> CREATOR=new Creator<BakingResponse>() {
        @Override
        public BakingResponse createFromParcel(Parcel in) {
            return new BakingResponse(in);
        }

        @Override
        public BakingResponse[] newArray(int size) {
            return new BakingResponse[size];
        }
    };

    public BakingResponse() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeValue(this.servings);
        dest.writeString(this.image);

    }
    protected BakingResponse (Parcel in){
        this.id= (Integer) in.readValue(Integer.class.getClassLoader());
        this.name=in.readString();
        this.ingredients=new ArrayList<Ingredient>();
        in.readList(this.ingredients,Ingredient.class.getClassLoader());
        this.steps=new ArrayList<Step>();
        in.readList(this.steps,Step.class.getClassLoader());
        this.servings= (Integer) in.readValue(Integer.class.getClassLoader());
        this.image=in.readString();

    }
}
