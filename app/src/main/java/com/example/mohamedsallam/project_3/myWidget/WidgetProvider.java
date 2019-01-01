package com.example.mohamedsallam.project_3.myWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.mohamedsallam.project_3.R;
import com.example.mohamedsallam.project_3.appUi.DetailActivity;

import java.util.ArrayList;

import static com.example.mohamedsallam.project_3.myWidget.UpdateService.ACTIVITY_LIST;

public class WidgetProvider extends AppWidgetProvider {
    static ArrayList<String> stringArrayList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);
        Intent appIntent = new Intent(context, DetailActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_gridview, appPendingIntent);
        Intent intent = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.widget_gridview, intent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        final String action = intent.getAction();
        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
            stringArrayList = intent.getExtras().getStringArrayList(ACTIVITY_LIST);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_gridview);
            WidgetProvider.updateBakingWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }
    }

}

