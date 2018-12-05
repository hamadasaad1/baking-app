package com.hamada.android.baking.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamada.android.baking.Model.Ingredient;
import com.hamada.android.baking.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientPagerAdapter extends RecyclerView.Adapter<IngredientPagerAdapter.ViewHolder> {

    private List<Object> list;

    public IngredientPagerAdapter(List<Object> list) {
        this.list = list;

    }

    @NonNull
    @Override
    public IngredientPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingedent_holder,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientPagerAdapter.ViewHolder holder, int position) {

        Ingredient ingredient= (Ingredient) list.get(position);
        if (ingredient !=null){
            holder.getRecipeIngentent().setText(ingredient.getIngredient());
            holder.getmQuantity().setText(ingredient.getQuantity()+"");
            holder.getmMeasure().setText(ingredient.getMeasure());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipeIngred)
        TextView recipeIngentent;
        @BindView(R.id.textViewquantity)
        TextView mQuantity;
        @BindView(R.id.textViewMeasure)
        TextView mMeasure;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public TextView getmQuantity() {
            return mQuantity;
        }

        public void setmQuantity(TextView mQuantity) {
            this.mQuantity = mQuantity;
        }

        public TextView getmMeasure() {
            return mMeasure;
        }

        public void setmMeasure(TextView mMeasure) {
            this.mMeasure = mMeasure;
        }

        public TextView getRecipeIngentent() {
            return recipeIngentent;
        }

        public void setRecipeIngentent(TextView recipeIngentent) {
            this.recipeIngentent = recipeIngentent;
        }
    }
    }

