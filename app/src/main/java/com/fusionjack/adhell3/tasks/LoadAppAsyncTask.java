package com.fusionjack.adhell3.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.fusionjack.adhell3.adapter.AppInfoAdapter;
import com.fusionjack.adhell3.db.AppDatabase;
import com.fusionjack.adhell3.db.entity.AppInfo;
import com.fusionjack.adhell3.model.AppFlag;
import com.fusionjack.adhell3.utils.AdhellFactory;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoadAppAsyncTask extends AsyncTask<Void, Void, List<AppInfo>> {

    public static final int SORTED_DISABLED = 0;
    public static final int SORTED_MOBILE_RESTRICTED = 1;
    public static final int SORTED_WIFI_RESTRICTED = 2;
    public static final int SORTED_WHITELISTED = 3;
    public static final int SORTED_COMPONENT = 4;
    public static final int SORTED_DNS = 5;

    private WeakReference<Context> contextReference;
    private String text;
    private AppFlag appFlag;
    private boolean reload;

    public LoadAppAsyncTask(String text, AppFlag appFlag, Context context) {
        this(text, appFlag, false, context);
    }

    LoadAppAsyncTask(String text, AppFlag appFlag, boolean reload, Context context) {
        this.text = text;
        this.appFlag = appFlag;
        this.reload = reload;
        this.contextReference = new WeakReference<>(context);
    }

    @Override
    protected List<AppInfo> doInBackground(Void... voids) {
        return getListFromDb();
    }

    @Override
    protected void onPostExecute(List<AppInfo> packageList) {
        Context context = contextReference.get();
        if (context != null) {
            ListView listView = ((Activity)context).findViewById(appFlag.getLoadLayout());
            if (listView != null) {
                AppInfoAdapter adapter = new AppInfoAdapter(packageList, appFlag, reload, context);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private List<AppInfo> getListFromDb() {
        AppDatabase appDatabase = AdhellFactory.getInstance().getAppDatabase();
        String filterText = '%' + text + '%';
        switch (appFlag.getSortState()) {
            case SORTED_DISABLED:
                if (text.length() == 0) {
                    return appDatabase.applicationInfoDao().getAppsInDisabledOrder();
                }
                return appDatabase.applicationInfoDao().getAppsInDisabledOrder(filterText);
            case SORTED_MOBILE_RESTRICTED:
                if (text.length() == 0) {
                    return appDatabase.applicationInfoDao().getAppsInMobileRestrictedOrder();
                }
                return appDatabase.applicationInfoDao().getAppsInMobileRestrictedOrder(filterText);
            case SORTED_WIFI_RESTRICTED:
                if (text.length() == 0) {
                    return appDatabase.applicationInfoDao().getAppsInWifiRestrictedOrder();
                }
                return appDatabase.applicationInfoDao().getAppsInWifiRestrictedOrder(filterText);
            case SORTED_WHITELISTED:
                if (text.length() == 0) {
                    return appDatabase.applicationInfoDao().getAppsInWhitelistedOrder();
                }
                return appDatabase.applicationInfoDao().getAppsInWhitelistedOrder(filterText);
            case SORTED_COMPONENT:
                if (text.length() == 0) {
                    return appDatabase.applicationInfoDao().getUserApps();
                }
                return appDatabase.applicationInfoDao().getUserApps(filterText);
            case SORTED_DNS:
                if (text.length() == 0) {
                    return appDatabase.applicationInfoDao().getAppsInDnsOrder();
                }
                return appDatabase.applicationInfoDao().getAppsInDnsOrder(filterText);
        }
        return null;
    }
}
