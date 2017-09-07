package poc.registration.app;

import org.junit.Before;
import org.junit.Test;

import poc.registration.RegistrationBackEndFlow;
import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.presenters.LaunchPresenter;
import poc.registration.app.presenters.LogInOrSignInPresenter;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.utils.TestSchedulers;
import poc.registration.app.views.LaunchView;
import poc.registration.app.views.LoginOrSingInView;
import poc.registration.events.RegistrationPassed;
import poc.registration.events.StartLoginOrSingIn;

import static io.reactivex.Single.just;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.Values.password;
import static utils.Values.username;

public class PresentersTests {

    SchedulersManager schedulers;
    FrontEndFlow frontEndFlow;
    private RegistrationBackEndFlow backEndFlow;

    @Before
    public void before() {
        schedulers = new TestSchedulers();
        frontEndFlow = mock(FrontEndFlow.class);
        backEndFlow = mock(RegistrationBackEndFlow.class);
    }

    @Test
    public void testLaunch() {
        LaunchPresenter presenter = new LaunchPresenter();
        presenter.frontEndFlow = frontEndFlow;
        presenter.backEndFlow = backEndFlow;
        presenter.schedulers = schedulers;

        when(backEndFlow.isRegistrationPassed()).thenReturn(just(false));

        presenter.onViewCreated(mock(LaunchView.class));

        verify(frontEndFlow).handleEvent(any(StartLoginOrSingIn.class));
    }

    @Test
    public void testLoginOrSignIn() {
        LogInOrSignInPresenter presenter = new LogInOrSignInPresenter(frontEndFlow, backEndFlow, schedulers);
        RegistrationPassed event = new RegistrationPassed();

        when(backEndFlow.auth(username, password)).thenReturn(just(event));

        presenter.onViewCreated(mock(LoginOrSingInView.class));
        presenter.loginOrSignIn(username, password);

        verify(frontEndFlow).handleEvent(event);
    }
}