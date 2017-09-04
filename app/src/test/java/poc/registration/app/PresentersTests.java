package poc.registration.app;

import org.junit.Before;
import org.junit.Test;

import poc.registration.RegistrationBackEndFlow;
import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.presenters.LaunchPresenter;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.utils.TestSchedulers;
import poc.registration.app.views.LaunchView;
import poc.registration.events.StartLoginOrSingIn;

import static io.reactivex.Single.just;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresentersTests {

    SchedulersManager schedulers;
    FrontEndFlow frontEndFlow;
    private RegistrationBackEndFlow registrationFlow;

    @Before
    public void before() {
        schedulers = new TestSchedulers();
        frontEndFlow = mock(FrontEndFlow.class);
        registrationFlow = mock(RegistrationBackEndFlow.class);
    }

    @Test
    public void testLaunch() {
        LaunchPresenter presenter = new LaunchPresenter();
        LaunchView view = mock(LaunchView.class);
        presenter.frontEndFlow = frontEndFlow;
        presenter.backEndFlow = registrationFlow;
        presenter.schedulers = schedulers;

        when(registrationFlow.isRegistrationPassed()).thenReturn(just(false));

        presenter.onViewCreated(view);
        presenter.loginOrSignIn();

        verify(frontEndFlow).handleEvent(new StartLoginOrSingIn());
    }

    @Test
    public void sampleTest() {

    }
}