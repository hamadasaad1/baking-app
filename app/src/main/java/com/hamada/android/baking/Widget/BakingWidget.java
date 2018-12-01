package com.hamada.android.baking.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.hamada.android.baking.MainActivity;
import com.hamada.android.baking.Model.BakingResponse;
import com.hamada.android.baking.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        BakingResponse mRecipe=pref.loadRecipe(context);
        if (mRecipe !=null){
            //to open main activity when click
            Intent intent=new Intent(context,MainActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
            views.setTextViewText(R.id.widget_TextView,mRecipe.getName());
            //to set click for widget
            views.setOnClickPendingIntent(R.id.widget_TextView,pendingIntent);
            // Initialize the list view
            Intent intent2 = new Intent(context, AppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            // Bind the remote adapter
            views.setRemoteAdapter(R.id.recipe_widget_listview, intent2);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_widget_listview);

        }



    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds){

        for (int appWidgetId :appWidgetIds){
            updateAppWidgets(context,appWidgetManager,appWidgetIds);
        }
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

