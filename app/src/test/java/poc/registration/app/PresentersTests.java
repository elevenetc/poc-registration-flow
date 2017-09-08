package poc.registration.app;

import org.junit.Before;
import org.junit.Test;

import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.presenters.*;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.utils.TestSchedulers;
import poc.registration.app.views.InitView;
import poc.registration.app.views.LoginOrSingInView;
import poc.registration.app.views.Screens;
import poc.registration.cache.Database;
import poc.registration.events.RegistrationPassed;
import poc.registration.events.SecretWordSet;
import poc.registration.events.StartLoginOrSingIn;
import poc.registration.flows.RegistrationBackEndFlow;

import static io.reactivex.Single.just;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.RxUtils.complete;
import static values.Values.password;
import static values.Values.secretWord;
import static values.Values.username;

public class PresentersTests {

    SchedulersManager schedulers;
    FrontEndFlow frontEndFlow;
    RegistrationBackEndFlow backEndFlow;
    Database database;
    Screens screens;

    @Before
    public void before() {
        screens = mock(Screens.class);
        database = mock(Database.class);
        schedulers = new TestSchedulers();
        frontEndFlow = mock(FrontEndFlow.class);
        backEndFlow = mock(RegistrationBackEndFlow.class);
    }

    @Test
    public void testLaunch() {

        when(backEndFlow.isRegistrationPassed()).thenReturn(just(false));

        new InitPresenter(frontEndFlow, backEndFlow, schedulers)
                .onViewCreated(mock(InitView.class));

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

    @Test
    public void testSecretWordPresenter() {
        SecretWordSet event = new SecretWordSet();

        when(backEndFlow.setSecretWord(secretWord)).thenReturn(just(event));

        new SecretWordPresenter(frontEndFlow, backEndFlow, schedulers).setSecretWord(secretWord);

        verify(frontEndFlow).handleEvent(event);
    }

    @Test
    public void testTermsPresenter() {
        RegistrationPassed event = new RegistrationPassed();

        when(backEndFlow.agreeWithTerms()).thenReturn(just(event));

        new TermsPresenter(frontEndFlow, backEndFlow, schedulers).signTerms();

        verify(frontEndFlow).handleEvent(event);
    }

    @Test
    public void testMainPresenter() {
        when(database.logout()).thenReturn(complete());
        new MainPresenter(database, screens, schedulers).logOut();
        verify(screens).gotoInit();
    }
}