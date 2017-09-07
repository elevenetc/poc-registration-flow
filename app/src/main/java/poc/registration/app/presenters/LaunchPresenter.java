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
    public RegistrationBackEndFlow backEndFlow;

    @Inject
    public FrontEndFlow frontEndFlow;

    @Inject
    public SchedulersManager schedulers;

    public LaunchPresenter() {

    }

    /**
     * Checks if registration passed:
     * true: triggers {@link RegistrationPassed} event
     * false: triggers {@link StartLoginOrSingIn} event
     */
    public LaunchPresenter onViewCreated(LaunchView view) {

        super.onViewCreated(view);

        backEndFlow
                .isRegistrationPassed()
                .compose(schedulers.single())
                .subscribe(passed -> {
                    //do some ui animation based on result
                    if (passed) {
                        frontEndFlow.handleEvent(new RegistrationPassed());
                    } else {
                        frontEndFlow.handleEvent(new StartLoginOrSingIn());
                    }
                });

        return this;
    }

    /**
     * Triggers {@link StartLoginOrSingIn} event
     */
    public void loginOrSignIn() {
        frontEndFlow.handleEvent(new StartLoginOrSingIn());
    }
}