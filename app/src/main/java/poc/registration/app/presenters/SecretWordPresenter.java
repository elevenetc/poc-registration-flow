package poc.registration.app.presenters;

import javax.inject.Inject;

import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.SecretWordView;
import poc.registration.flows.RegistrationBackEndFlow;
import poc.registration.flows.SetSecretWordFlow;

public class SecretWordPresenter extends Presenter<SecretWordView> {

    @Inject
    FrontEndFlow frontEndFlow;

    @Inject
    SchedulersManager schedulers;

    SetSecretWordFlow secretWordFlow;

    public SecretWordPresenter(SetSecretWordFlow secretWordFlow) {
        this.secretWordFlow = secretWordFlow;
    }

    public SecretWordPresenter(
            FrontEndFlow frontEndFlow,
            RegistrationBackEndFlow secretWordFlow,
            SchedulersManager schedulers) {
        this.frontEndFlow = frontEndFlow;
        this.secretWordFlow = secretWordFlow;
        this.schedulers = schedulers;
    }

    /**
     * Sets secret word at {@link RegistrationBackEndFlow}
     * and passes event to {@link FrontEndFlow}
     */
    public void setSecretWord(String secretWord) {
        secretWordFlow
                .setSecretWord(secretWord)
                .compose(schedulers.single())
                .subscribe(secretWordSet -> frontEndFlow.handleEvent(secretWordSet));
    }
}