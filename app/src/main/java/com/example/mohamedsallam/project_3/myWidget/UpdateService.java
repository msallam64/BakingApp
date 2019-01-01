package com.example.mohamedsallam.project_3.myWidget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class UpdateService extends IntentService {
    public static String ACTIVITY_LIST = "FROM_ACTIVITY_INGREDIENTS_LIST";

    public UpdateService() {
        super("UpdateService");
    }

    public static void startBakingService(Context context, ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(ACTIVITY_LIST, fromActivityIngredientsList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras().getStringArrayList(ACTIVITY_LIST);
            handleActionUpdateBakingWidgets(fromActivityIngredientsList);

        }
    }

    private void handleActionUpdateBakingWidgets(ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(ACTIVITY_LIST, fromActivityIngredientsList);
        sendBroadcast(intent);
    }

}
