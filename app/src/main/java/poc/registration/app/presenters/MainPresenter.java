package poc.registration.app.presenters;

import javax.inject.Inject;

import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.Screens;
import poc.registration.cache.Database;

public class MainPresenter {

    @Inject
    Database database;

    @Inject
    Screens screens;

    @Inject
    SchedulersManager schedulers;

    public MainPresenter() {
        //DI constructor
    }

    public MainPresenter(
            Database database,
            Screens screens,
            SchedulersManager schedulers) {
        this.database = database;
        this.screens = screens;
        this.schedulers = schedulers;
    }


    /**
     * Tries to logout and goes to init screen if success
     */
    public void logOut() {
        database
                .logout()
                .compose(schedulers.completable())
                .subscribe(() -> screens.gotoInit());
    }
}
