package poc.registration.app.presenters;

import javax.inject.Inject;

import poc.registration.RegistrationBackEndFlow;
import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.LaunchView;
import poc.registration.events.RegistrationPassed;
import poc.registration.events.StartLoginOrSingIn;

public class LaunchPresenter extends Presenter<LaunchView> {

    @Inject
    RegistrationBackEndFlow backEndFlow;

    @Inject
    FrontEndFlow frontEndFlow;

    @Inject
    SchedulersManager schedulers;

    public LaunchPresenter() {

    }

    public LaunchPresenter onViewCreated(LaunchView view) {

        super.onViewCreated(view);

        backEndFlow
                .isRegistrationPassed()
                .compose(schedulers.single())
                .subscribe(passed -> {
                    if (passed) {
                        frontEndFlow.handleEvent(new RegistrationPassed());
                    } else {
                        frontEndFlow.handleEvent(new StartLoginOrSingIn());
                    }
                });

        return this;
    }

    public void loginOrSignIn() {
        frontEndFlow.handleEvent(new StartLoginOrSingIn());
    }
}