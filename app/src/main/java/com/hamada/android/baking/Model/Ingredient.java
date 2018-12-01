package com.hamada.android.baking.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hamada.android.baking.Utils;

public class Ingredient implements Parcelable {
    @SerializedName("quantity")
    @Expose
    private float quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public Ingredient() {
    }

    public Ingredient(float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
    public String getDoseStr(){
        return Utils.fmt(quantity)  + " " +measure;
    }

    public static final Creator<Ingredient> CREATOR=new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);

        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(this.quantity);
        dest.writeString(this.ingredient);
        dest.writeString(this.measure);
    }
    protected Ingredient (Parcel in){

        this.quantity=in.readFloat();
        this.ingredient=in.readString();
        this.measure=in.readString();
    }
}
