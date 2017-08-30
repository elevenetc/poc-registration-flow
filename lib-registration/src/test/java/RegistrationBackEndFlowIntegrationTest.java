import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;
import poc.registration.RegistrationBackEndFlow;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.NewUserCreated;
import poc.registration.events.RegistrationPassed;
import poc.registration.events.SecretWordSet;
import poc.registration.impl.RegistrationBackEndFlowImpl;
import utils.InMemoryDatabase;
import utils.SuccessBackendApi;
import utils.TestClassSubscriber;

import static org.junit.Assert.assertTrue;
import static utils.Values.password;
import static utils.Values.secretWord;
import static utils.Values.username;

public class RegistrationBackEndFlowIntegrationTest {

    Database database;
    BackendApi api;
    TestClassSubscriber<Object> subscriber;
    RegistrationBackEndFlow backEndFlow;

    @Before
    public void before() {
        database = new InMemoryDatabase();
        api = new SuccessBackendApi(false);
        subscriber = new TestClassSubscriber();
        backEndFlow = new RegistrationBackEndFlowImpl(database, api);
    }

    @Test
    public void successRegistration() {

        Single.concat(
                backEndFlow.auth(username, password),
                backEndFlow.setSecretWord(secretWord),
                backEndFlow.agreeWithTerms()
        ).subscribe(subscriber);

        subscriber.assertTypeSequence(
                NewUserCreated.class,
                SecretWordSet.class,
                RegistrationPassed.class
        );

        subscriber.assertComplete();
        assertTrue(database.isRegistrationPassed());
    }

}