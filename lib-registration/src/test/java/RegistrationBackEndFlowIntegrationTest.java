import org.junit.Before;
import org.junit.Test;

import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.NewUserCreated;
import poc.registration.events.RegistrationPassed;
import poc.registration.events.SecretWordSet;
import poc.registration.flows.RegistrationBackEndFlow;
import poc.registration.flows.RegistrationBackEndFlowImpl;
import values.InMemoryDatabase;
import values.SuccessBackendApi;
import values.TestClassSubscriber;

import static io.reactivex.Single.concat;
import static org.junit.Assert.assertTrue;
import static values.Values.password;
import static values.Values.secretWord;
import static values.Values.username;

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

        concat(
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