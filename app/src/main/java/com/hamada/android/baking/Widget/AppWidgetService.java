package com.hamada.android.baking.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.hamada.android.baking.Model.BakingResponse;

public class AppWidgetService extends RemoteViewsService {

    public static void updateWidget(Context context, BakingResponse recipe) {
        pref.saveRecipe(context, recipe);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingWidget.class));
        BakingWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new ListRemoteViewsFactory(getApplicationContext());
    }
}
