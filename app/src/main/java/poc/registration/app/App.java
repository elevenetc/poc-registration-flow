package poc.registration.app;

import android.app.Application;

import poc.registration.app.di.DIHelper;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DIHelper.init(this);
    }
}
