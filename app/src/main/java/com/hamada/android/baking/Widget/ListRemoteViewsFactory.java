package com.hamada.android.baking.Widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.R;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    BakingResponse mRecipe;

    public ListRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipe=pref.loadRecipe(context);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
//        if (mRecipe==null)return 0;
        return mRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.baking_recipes_app_widget_list_item);

        row.setTextViewText(R.id.ingredient_item_text, mRecipe.getIngredients().get(position).getIngredient());

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
