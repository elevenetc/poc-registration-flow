package poc.registration.app.presenters;

import javax.inject.Inject;

import poc.registration.RegistrationBackEndFlow;
import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.SecretWordView;

public class SecretWordPresenter extends Presenter<SecretWordView> {

    @Inject
    FrontEndFlow frontEndFlow;

    @Inject
    RegistrationBackEndFlow backEndFlow;

    @Inject
    SchedulersManager schedulers;

    public void setSecretWord(String secretWord) {
        backEndFlow
                .setSecretWord(secretWord)
                .compose(schedulers.single())
                .subscribe(secretWordSet -> frontEndFlow.handleEvent(secretWordSet));
    }
}