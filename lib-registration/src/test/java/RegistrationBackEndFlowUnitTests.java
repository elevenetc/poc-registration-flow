import org.junit.Before;
import org.junit.Test;

import io.reactivex.observers.TestObserver;
import poc.registration.RegistrationBackEndFlow;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.*;
import poc.registration.exceptions.InvalidSecretWordException;
import poc.registration.exceptions.InvalidUsernameOrPassword;
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

/**
 * Unit tests for BackEndFlow
 */
public class RegistrationBackEndFlowUnitTests {

    Database database;
    RegistrationBackEndFlow backEndFlow;
    BackendApi api;
    TestObserver<Event> testSubscriber;
    InvalidSecretWordException invalidSecretWordException = new InvalidSecretWordException();
    InvalidUsernameOrPassword invalidUsernameOrPassword = new InvalidUsernameOrPassword();

    @Before
    public void before() {
        database = mock(Database.class);
        api = mock(BackendApi.class);
        backEndFlow = new RegistrationBackEndFlowImpl(database, api);
        testSubscriber = new TestObserver<>();
    }

    @Test
    public void testAuthAlreadyRegistered() {

        when(api.auth(username, password)).thenReturn(new AuthResponse(token, true));

        backEndFlow.auth(username, password).subscribe(testSubscriber);

        testSubscriber.assertValue(event -> event instanceof UserRegisteredAlready);
        testSubscriber.assertComplete();
        verify(database, once()).storeToken(token);
    }

    @Test
    public void testAuthNewUserCreated() {

        when(api.auth(username, password)).thenReturn(new AuthResponse(token, false));

        backEndFlow.auth(username, password).subscribe(testSubscriber);

        testSubscriber.assertValue(event -> event instanceof NewUserCreated);
        testSubscriber.assertComplete();
        verify(database, once()).storeToken(token);
    }

    @Test
    public void testInvalidUsernameOrPassword() {

        when(api.auth(username, password)).thenThrow(invalidUsernameOrPassword);

        backEndFlow.auth(username, password).subscribe(testSubscriber);

        testSubscriber.assertError(invalidUsernameOrPassword);
    }

    @Test
    public void testSecretWordCreationSuccess() {

        when(api.setSecretWord(secretWord, token)).thenReturn(new SecretWordResponse());
        when(database.getToken()).thenReturn(token);

        backEndFlow.setSecretWord(secretWord).subscribe(testSubscriber);

        testSubscriber.assertValue(event -> event instanceof SecretWordSet);
        testSubscriber.assertComplete();
    }

    @Test
    public void testTermsAgreed() {

        when(database.getToken()).thenReturn(token);
        when(api.agreeWithTerms(token)).thenReturn(new AgreeWithTermsResponse());

        backEndFlow.agreeWithTerms().subscribe(testSubscriber);

        testSubscriber.assertValue(event -> event instanceof RegistrationPassed);
        testSubscriber.assertComplete();
    }

    @Test
    public void testSecretWordCreationFail() {

        when(api.setSecretWord(secretWord, token)).thenThrow(invalidSecretWordException);
        when(database.getToken()).thenReturn(token);

        backEndFlow.setSecretWord(secretWord).subscribe(testSubscriber);

        testSubscriber.assertError(invalidSecretWordException);
    }
}