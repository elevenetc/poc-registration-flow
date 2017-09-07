package poc.registration.app.presenters;

import javax.inject.Inject;

import poc.registration.RegistrationBackEndFlow;
import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.views.TermsView;

public class TermsPresenter extends Presenter<TermsView> {

    @Inject
    RegistrationBackEndFlow backEndFlow;

    @Inject
    FrontEndFlow frontEndFlow;

    @Inject
    SchedulersManager schedulers;

    public TermsPresenter() {

    }

    public TermsPresenter(
            FrontEndFlow frontEndFlow,
            RegistrationBackEndFlow backEndFlow,
            SchedulersManager schedulers) {
        this.frontEndFlow = frontEndFlow;
        this.backEndFlow = backEndFlow;
        this.schedulers = schedulers;
    }

    /**
     * Signs terms and passes result event to {@link FrontEndFlow}
     */
    public void signTerms() {
        backEndFlow
                .agreeWithTerms()
                .compose(schedulers.single())
                .subscribe(
                        frontEndFlow::handleEvent
                );
    }
}
