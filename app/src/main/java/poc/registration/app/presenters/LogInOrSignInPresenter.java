package poc.registration.app.presenters;

import javax.inject.Inject;

import poc.registration.RegistrationBackEndFlow;
import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.LoginOrSingInView;

public class LogInOrSignInPresenter extends Presenter<LoginOrSingInView> {

    @Inject
    RegistrationBackEndFlow backEndFlow;

    @Inject
    FrontEndFlow frontEndFlow;

    @Inject
    SchedulersManager schedulers;

    public void loginOrSignIn(String username, String password) {

        backEndFlow.auth(username, password)
                .compose(schedulers.single())
                .subscribe(
                        event -> frontEndFlow.handleEvent(event),
                        throwable -> view.showError()
                );
    }
}