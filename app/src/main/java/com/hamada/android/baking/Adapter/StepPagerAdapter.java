package com.hamada.android.baking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamada.android.baking.Model.Step;
import com.hamada.android.baking.R;
import com.hamada.android.baking.StepActivity;
import com.hamada.android.baking.stepFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepPagerAdapter extends RecyclerView.Adapter<StepPagerAdapter.ViewHolder> {

    private List<Object> list;
    public boolean isTwoPane;
    public static final String EXTRASTEP="steps";
    Context context;

    public StepPagerAdapter(List<Object> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StepPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_holder_step,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepPagerAdapter.ViewHolder holder, int position) {

        Step step= (Step) list.get(position);
        if (step !=null){
            holder.shortDescription.setText(step.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.description)
        TextView deccription;
        @BindView(R.id.short_description) TextView shortDescription;
        @BindView(R.id.videoUri) TextView VideoUrl;
        @BindView(R.id.stepImage)
        ImageView imageViewSetp;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if (position !=RecyclerView.NO_POSITION){
                        if (isTwoPane) {
                            Step clickItem = (Step) list.get(position);
                            Bundle argument = new Bundle();
                            argument.putParcelable(EXTRASTEP, clickItem);
                            //create fragment when activity is tablet
                            stepFragment fragment=new stepFragment();
                            fragment.setArguments(argument);
                            //getSupportFragmentManager().beginTransaction().replace(R.id.item_details_container,
                            //       fragment);

                        }else {
                            Context context=v.getContext();
                            Step clickItem = (Step) list.get(position);
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
}
