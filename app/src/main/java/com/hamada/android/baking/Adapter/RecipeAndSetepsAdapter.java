package com.hamada.android.baking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamada.android.baking.Model.Ingredient;
import com.hamada.android.baking.Model.Step;
import com.hamada.android.baking.R;
import com.hamada.android.baking.StepActivity;
import com.hamada.android.baking.stepFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAndSetepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> dataset;
    private final static int STEPS=1;
    private final static int INGRENDIENT=0;
    public boolean isTwoPane;
    public final String EXTRASTEP="steps";

    public RecipeAndSetepsAdapter(List<Object> dataset, boolean isTwoPane) {
        this.dataset = dataset;
        this.isTwoPane = isTwoPane;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        switch (viewType){
            case INGRENDIENT:
                View viewIngrendient=layoutInflater.inflate(R.layout.ingedent_holder,parent,false);
                viewHolder= new ingentendViewHolder(viewIngrendient);
                break;
            case STEPS:
                View viewStep=layoutInflater.inflate(R.layout.layout_holder_step,parent,false);
                viewHolder=new StepViewHolder(viewStep);
                break;
                default:
                    View view=layoutInflater.inflate(R.layout.layout_holder_step,parent,false);
                    viewHolder=new StepViewHolder(view);
                    break;
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case INGRENDIENT:
                ingentendViewHolder ingentendHolder= (ingentendViewHolder) holder;
                setupIngrendientViewHolder(ingentendHolder,position);
                break;
            case STEPS:
                StepViewHolder stepViewHolder= (StepViewHolder) holder;
                setupStepViewHolder(stepViewHolder,position);
                break;
        }

    }
    private void setupIngrendientViewHolder(ingentendViewHolder viewHolder,int position){
        Ingredient ingredient= (Ingredient) dataset.get(position);
        if (ingredient !=null){
            viewHolder.getRecipeIngentent().setText(ingredient.getIngredient());
            viewHolder.getmQuantity().setText(ingredient.getQuantity()+"");
            viewHolder.getmMeasure().setText(ingredient.getMeasure());
        }
    }
    private void setupStepViewHolder(StepViewHolder viewHolder,int position){
        Step step= (Step) dataset.get(position);
        if (step !=null){
            viewHolder.shortDescription.setText(step.getShortDescription());
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (dataset.get(position) instanceof Ingredient){
            return INGRENDIENT;
        }else if (dataset.get(position) instanceof Step){
            return STEPS;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class StepViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.description)
        TextView deccription;
        @BindView(R.id.short_description) TextView shortDescription;
        @BindView(R.id.videoUri) TextView VideoUrl;
        @BindView(R.id.stepImage)
        ImageView imageViewSetp;
        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if (position !=RecyclerView.NO_POSITION){
                        if (isTwoPane) {
                            Step clickItem = (Step) dataset.get(position);
                            Bundle argument = new Bundle();
                            argument.putParcelable(EXTRASTEP, clickItem);
                            //create fragment when activity is tablet
                            stepFragment fragment=new stepFragment();
                            fragment.setArguments(argument);

                            //getSupportFragmentManager().beginTransaction().replace(R.id.item_details_container,
                            //       fragment);
                        }else {
                            Context context=v.getContext();
                            Step clickItem = (Step) dataset.get(position);
                            Intent intent=new Intent(context,StepActivity.class);
                            intent.putExtra(EXTRASTEP,clickItem);
                            context.startActivity(intent);

                        }
                    }
                }
            });
        }

        public TextView getDeccription() {
            return deccription;
        }

        public void setDeccription(TextView deccription) {
            this.deccription = deccription;
        }

        public TextView getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(TextView shortDescription) {
            this.shortDescription = shortDescription;
        }

        public TextView getVideoUrl() {
            return VideoUrl;
        }

        public void setVideoUrl(TextView videoUrl) {
            VideoUrl = videoUrl;
        }
    }
    public class ingentendViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipeIngred)
        TextView recipeIngentent;
        @BindView(R.id.textViewquantity)
        TextView mQuantity;
        @BindView(R.id.textViewMeasure)
        TextView mMeasure;
        public ingentendViewHolder(View itemView) {
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
