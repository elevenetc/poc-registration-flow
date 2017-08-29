import org.junit.Before;
import org.junit.Test;

import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import poc.registration.RegistrationBackEndFlow;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.*;
import poc.registration.exceptions.InvalidSecretWordException;
import poc.registration.exceptions.InvalidUsernameOrPassword;
import poc.registration.exceptions.UnsupportedEventException;
import poc.registration.impl.RegistrationBackEndFlowImpl;
import poc.registration.models.AgreeWithTermsResponse;
import poc.registration.models.AuthResponse;
import poc.registration.models.SecretWordResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.MockitoUtils.once;
import static utils.Values.password;
import static utils.Values.secretWord;
import static utils.Values.token;
import static utils.Values.username;

public class RegistrationBackEndFlowUnitTests {

    Database database;
    RegistrationBackEndFlow registrationBackEndFlow;
    BackendApi api;
    TestSubscriber<Event> eventsSubscriber;
    TestObserver<Event> resultObserver;
    InvalidSecretWordException invalidSecretWordException = new InvalidSecretWordException();
    InvalidUsernameOrPassword invalidUsernameOrPassword = new InvalidUsernameOrPassword();

    @Before
    public void before() {
        database = mock(Database.class);
        api = mock(BackendApi.class);
        registrationBackEndFlow = new RegistrationBackEndFlowImpl(database, api);
        eventsSubscriber = TestSubscriber.create();
        resultObserver = new TestObserver<>();
    }

    @Test
    public void testUnsupportedEvent() {
        registrationBackEndFlow.sendEvent(new UnsupportedEvent()).subscribe(resultObserver);
        resultObserver.assertError(UnsupportedEventException.class);
    }

    @Test
    public void testAuthAlreadyRegistered() {

        when(api.auth(username, password)).thenReturn(new AuthResponse(token, true));

        registrationBackEndFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationBackEndFlow.sendEvent(new NewAuthRequest(username, password)).subscribe(resultObserver);

        eventsSubscriber.assertValue(event -> event instanceof UserRegisteredAlready);
        resultObserver.assertComplete();
        verify(database, once()).storeToken(token);
    }

    @Test
    public void testAuthNewUserCreated() {

        when(api.auth(username, password)).thenReturn(new AuthResponse(token, false));

        registrationBackEndFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationBackEndFlow.sendEvent(new NewAuthRequest(username, password)).subscribe(resultObserver);

        eventsSubscriber.assertValue(event -> event instanceof NewUserCreated);
        resultObserver.assertComplete();
        verify(database, once()).storeToken(token);
    }

    @Test
    public void testInvalidUsernameOrPassword() {

        when(api.auth(username, password)).thenThrow(invalidUsernameOrPassword);

        registrationBackEndFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationBackEndFlow.sendEvent(new NewAuthRequest(username, password)).subscribe(resultObserver);

        resultObserver.assertError(invalidUsernameOrPassword);
    }

    @Test
    public void testSecretWordCreationSuccess() {

        when(api.createSecretWord(secretWord, token)).thenReturn(new SecretWordResponse());
        when(database.getToken()).thenReturn(token);

        registrationBackEndFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationBackEndFlow.sendEvent(new SetSecretWord(secretWord)).subscribe(resultObserver);

        eventsSubscriber.assertValue(event -> event instanceof SecretWordSet);
        resultObserver.assertComplete();
    }

    @Test
    public void testTermsAgreed() {

        when(database.getToken()).thenReturn(token);
        when(api.agreeWithTerms(token)).thenReturn(new AgreeWithTermsResponse());

        registrationBackEndFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationBackEndFlow.sendEvent(new AgreeWithTermsRequest()).subscribe(resultObserver);

        eventsSubscriber.assertValue(event -> event instanceof RegistrationPassed);
        resultObserver.assertComplete();
    }

    @Test
    public void testSecretWordCreationFail() {

        when(api.createSecretWord(secretWord, token)).thenThrow(invalidSecretWordException);
        when(database.getToken()).thenReturn(token);

        registrationBackEndFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationBackEndFlow.sendEvent(new SetSecretWord(secretWord)).subscribe(resultObserver);

        resultObserver.assertError(invalidSecretWordException);
    }
}