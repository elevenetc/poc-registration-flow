package poc.registration.app.presenters;

import javax.inject.Inject;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import poc.registration.RegistrationFlow;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.ScreensLauncher;
import poc.registration.events.Event;
import poc.registration.events.NewAuthRequest;
import poc.registration.events.NewUserCreated;
import poc.registration.events.UserRegisteredAlready;

public class AuthPresenter {

    @Inject
    RegistrationFlow registrationFlow;

    @Inject
    SchedulersManager schedulers;

    @Inject
    ScreensLauncher screensLauncher;

    public void auth(String username, String password) {

        registrationFlow.eventsObservable()
                .filter(event -> event instanceof UserRegisteredAlready)
                .compose(schedulers.uiAndBackFlowable())
                .subscribe(this::onAlreadyRegistered);

        registrationFlow.eventsObservable()
                .filter(event -> event instanceof NewUserCreated)
                .compose(schedulers.uiAndBackFlowable())
                .subscribe(this::onNewUserCreated);

        registrationFlow.sendEvent(new NewAuthRequest(username, password))
                .compose(schedulers.uiAndBackCompletable())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void onCreate() {

    }

    public void onDestroy() {

    }

    private void onAlreadyRegistered(Event event) {
        screensLauncher.goToMain();
    }

    private void onNewUserCreated(Event event) {
        screensLauncher.goToSecretWord();
    }
}