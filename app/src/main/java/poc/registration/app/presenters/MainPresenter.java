package poc.registration.app.presenters;

import javax.inject.Inject;

import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.ScreensLauncher;
import poc.registration.cache.Database;

public class MainPresenter {

    @Inject
    Database database;

    @Inject
    SchedulersManager schedulers;

    @Inject
    ScreensLauncher screensLauncher;

    public void logOut() {
        database
                .logout()
                .compose(schedulers.completable())
                .subscribe(() -> screensLauncher.gotoLauncher());
    }
}
