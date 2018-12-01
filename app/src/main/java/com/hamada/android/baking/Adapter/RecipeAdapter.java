package com.hamada.android.baking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hamada.android.baking.DetailsActivity;
import com.hamada.android.baking.DetailsViewPagerActivity;
import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHOlder> {

    public static final String RECIPE_EXTRA="recipe_extra";
    private List<BakingResponse> mListe=new ArrayList<>();
    private Context mContext;

    public RecipeAdapter( Context mContext,List<BakingResponse> mListe) {
        this.mListe = mListe;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view=LayoutInflater.from(parent.getContext())
              .inflate(R.layout.main_list_view, parent, false);

        return new RecipeViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHOlder holder, int position) {

        holder.name.setText(mListe.get(position).getName());

    }

    @Override
    public int getItemCount() {
      if (mListe==null) return 0;
      return mListe.size();
    }
    public class RecipeViewHOlder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewRecipe)
        TextView name;
        public RecipeViewHOlder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();

                    if (position !=RecyclerView.NO_POSITION){
                        BakingResponse clickItem=mListe.get(position);
                        Intent intent=new Intent(mContext,DetailsViewPagerActivity.class);
                        intent.putExtra(RECIPE_EXTRA,clickItem);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(mContext, "Clicked "+clickItem.getName()
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }
}
