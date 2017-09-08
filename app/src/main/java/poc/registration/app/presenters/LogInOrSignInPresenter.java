package poc.registration.app.presenters;

import javax.inject.Inject;

import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.LoginOrSingInView;
import poc.registration.flows.RegistrationBackEndFlow;

public class LogInOrSignInPresenter extends Presenter<LoginOrSingInView> {

    @Inject
    public RegistrationBackEndFlow backEndFlow;

    @Inject
    public FrontEndFlow frontEndFlow;

    @Inject
    public SchedulersManager schedulers;

    public LogInOrSignInPresenter() {
        //DI Constructor
    }

    public LogInOrSignInPresenter(
            FrontEndFlow frontEndFlow,
            RegistrationBackEndFlow backEndFlow,
            SchedulersManager schedulers
    ) {
        this.frontEndFlow = frontEndFlow;
        this.backEndFlow = backEndFlow;
        this.schedulers = schedulers;
    }

    /**
     * Tries to {@link RegistrationBackEndFlow#auth(String, String)}
     * and passes result event to {@link FrontEndFlow}
     * <p>
     * In case of error informs view
     */
    public void loginOrSignIn(String username, String password) {

        backEndFlow
                .auth(username, password)
                .compose(schedulers.single())
                .subscribe(
                        event -> frontEndFlow.handleEvent(event),
                        throwable -> view.showError()
                );
    }
}