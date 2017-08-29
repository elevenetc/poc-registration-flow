import org.junit.Test;

import io.reactivex.observers.TestObserver;
import poc.registration.RegistrationBackEndFlow;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.*;
import poc.registration.impl.RegistrationBackEndFlowImpl;
import utils.TestClassSubscriber;

import static org.junit.Assert.assertTrue;
import static utils.Values.password;
import static utils.Values.secretWord;
import static utils.Values.username;

public class RegistrationBackEndFlowIntegrationTest {
    @Test
    public void successRegistration() {

        Database database = new InMemoryDatabase();
        BackendApi api = new SuccessBackendApi(false);
        TestObserver<Event> observer = new TestObserver<>();
        TestClassSubscriber subscriber = new TestClassSubscriber<Event>();

        RegistrationBackEndFlow registrationBackEndFlow = new RegistrationBackEndFlowImpl(database, api);

        registrationBackEndFlow.eventsObservable().subscribe(subscriber);

        registrationBackEndFlow
                .sendEvent(new NewAuthRequest(username, password))
                .andThen(registrationBackEndFlow.sendEvent(new SetSecretWord(secretWord)))
                .andThen(registrationBackEndFlow.sendEvent(new AgreeWithTermsRequest()))
                .subscribe(observer);

        subscriber.assertTypeSequence(
                NewUserCreated.class,
                SecretWordSet.class,
                RegistrationPassed.class
        );

        observer.assertComplete();
        assertTrue(database.isRegistrationPassed());
    }

}