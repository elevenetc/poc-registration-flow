import org.junit.Before;
import org.junit.Test;

import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import poc.registration.RegistrationFlow;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.*;
import poc.registration.exceptions.InvalidSecretWordException;
import poc.registration.exceptions.InvalidUsernameOrPassword;
import poc.registration.exceptions.UnsupportedEventException;
import poc.registration.impl.RegistrationFlowImpl;
import poc.registration.models.AgreeWithTermsResponse;
import poc.registration.models.AuthResponse;
import poc.registration.models.SecretWordResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.MockitoUtils.once;

public class RegistrationFlowUnitTests {

    final String username = "username";
    final String password = "password";
    final String token = "token";
    final String secretWord = "secretWord";

    Database database;
    RegistrationFlow registrationFlow;
    BackendApi api;
    TestSubscriber<Event> eventsSubscriber;
    TestObserver<Event> resultObserver;
    InvalidSecretWordException invalidSecretWordException = new InvalidSecretWordException();
    InvalidUsernameOrPassword invalidUsernameOrPassword = new InvalidUsernameOrPassword();

    @Before
    public void before() {
        database = mock(Database.class);
        api = mock(BackendApi.class);
        registrationFlow = new RegistrationFlowImpl(database, api);
        eventsSubscriber = TestSubscriber.create();
        resultObserver = new TestObserver<>();
    }

    @Test
    public void testUnsupportedEvent() {
        registrationFlow.sendEvent(new UnsupportedEvent()).subscribe(resultObserver);
        resultObserver.assertError(UnsupportedEventException.class);
    }

    @Test
    public void testAuthAlreadyRegistered() {

        when(api.auth(username, password)).thenReturn(new AuthResponse(token, true));

        registrationFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationFlow.sendEvent(new NewAuthRequest(username, password)).subscribe(resultObserver);

        eventsSubscriber.assertValue(event -> event instanceof UserRegisteredAlready);
        resultObserver.assertComplete();
        verify(database, once()).storeToken(token);
    }

    @Test
    public void testAuthNewUserCreated() {

        when(api.auth(username, password)).thenReturn(new AuthResponse(token, false));

        registrationFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationFlow.sendEvent(new NewAuthRequest(username, password)).subscribe(resultObserver);

        eventsSubscriber.assertValue(event -> event instanceof NewUserCreated);
        resultObserver.assertComplete();
        verify(database, once()).storeToken(token);
    }

    @Test
    public void testInvalidUsernameOrPassword() {

        when(api.auth(username, password)).thenThrow(invalidUsernameOrPassword);

        registrationFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationFlow.sendEvent(new NewAuthRequest(username, password)).subscribe(resultObserver);

        resultObserver.assertError(invalidUsernameOrPassword);
    }

    @Test
    public void testSecretWordCreationSuccess() {

        when(api.createSecretWord(secretWord, token)).thenReturn(new SecretWordResponse());
        when(database.getToken()).thenReturn(token);

        registrationFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationFlow.sendEvent(new CreateSecretWord(secretWord)).subscribe(resultObserver);

        eventsSubscriber.assertValue(event -> event instanceof SecretWordCreated);
        resultObserver.assertComplete();
    }

    @Test
    public void testTermsAgreed() {

        when(database.getToken()).thenReturn(token);
        when(api.agreeWithTerms(token)).thenReturn(new AgreeWithTermsResponse());

        registrationFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationFlow.sendEvent(new AgreeWithTermsRequest()).subscribe(resultObserver);

        eventsSubscriber.assertValue(event -> event instanceof RegistrationPassed);
        resultObserver.assertComplete();
    }

    @Test
    public void testSecretWordCreationFail() {

        when(api.createSecretWord(secretWord, token)).thenThrow(invalidSecretWordException);
        when(database.getToken()).thenReturn(token);

        registrationFlow.eventsObservable().subscribe(eventsSubscriber);
        registrationFlow.sendEvent(new CreateSecretWord(secretWord)).subscribe(resultObserver);

        resultObserver.assertError(invalidSecretWordException);
    }
}