package com.example.mohamedsallam.project_3.myWidget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.mohamedsallam.project_3.R;

import java.util.List;

import static com.example.mohamedsallam.project_3.myWidget.WidgetProvider.stringArrayList;

public class WidgetService extends RemoteViewsService {
    List<String> remoteViewList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteFactory(this.getApplicationContext(), intent);
    }

    class GridRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
        Context mContext = null;

        public GridRemoteFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            remoteViewList = stringArrayList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            return remoteViewList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);

            views.setTextViewText(R.id.widget_item, remoteViewList.get(position));

            Intent fillInIntent = new Intent();
            //fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

            return views;
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


}

