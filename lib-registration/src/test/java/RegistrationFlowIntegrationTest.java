import org.junit.Test;

import java.util.LinkedList;

import io.reactivex.observers.TestObserver;
import poc.registration.RegistrationFlow;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.*;
import poc.registration.impl.RegistrationFlowImpl;
import utils.TestClassSubscriber;

import static org.junit.Assert.assertTrue;
import static utils.Values.password;
import static utils.Values.secretWord;
import static utils.Values.username;

public class RegistrationFlowIntegrationTest {
    @Test
    public void successRegistration() {

        Database database = new InMemoryDatabase();
        BackendApi api = new SuccessBackendApi(false);
        TestObserver<Event> observer = new TestObserver<>();
        TestClassSubscriber subscriber = new TestClassSubscriber<Event>();

        RegistrationFlow registrationFlow = new RegistrationFlowImpl(database, api);

        registrationFlow.eventsObservable().subscribe(subscriber);

        registrationFlow
                .sendEvent(new NewAuthRequest(username, password))
                .andThen(registrationFlow.sendEvent(new CreateSecretWord(secretWord)))
                .andThen(registrationFlow.sendEvent(new AgreeWithTermsRequest()))
                .subscribe(observer);

        subscriber.assertClassSequence(new LinkedList<Class>() {{
            add(NewUserCreated.class);
            add(SecretWordCreated.class);
            add(RegistrationPassed.class);
        }});
        observer.assertComplete();
        assertTrue(database.isRegistrationPassed());
    }

}