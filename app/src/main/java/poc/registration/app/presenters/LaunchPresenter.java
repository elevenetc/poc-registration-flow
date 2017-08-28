package poc.registration.app.presenters;

import javax.inject.Inject;

import poc.registration.RegistrationFlow;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.ScreensLauncher;

public class LaunchPresenter {

    @Inject
    RegistrationFlow registrationFlow;

    @Inject
    ScreensLauncher screensLauncher;

    @Inject
    SchedulersManager schedulers;

    public LaunchPresenter() {

    }

    public LaunchPresenter onCreate() {

        registrationFlow
                .isRegistrationPassed()
                .compose(schedulers.uiAndBackSingle())
                .subscribe(passed -> {
                    if (passed) screensLauncher.goToMain();
                });

        return this;
    }

    public void loginOrSignin() {
        screensLauncher.goToLogInOrSignIn();
    }

    public void onDestroy() {

    }
}