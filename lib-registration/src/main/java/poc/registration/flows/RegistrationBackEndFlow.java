package poc.registration.flows;

import io.reactivex.Single;
import poc.registration.events.Event;
import poc.registration.events.NewUserCreated;
import poc.registration.events.RegistrationPassed;
import poc.registration.events.UserRegisteredAlready;

public interface RegistrationBackEndFlow extends SetSecretWordFlow {

    Single<Boolean> isRegistrationPassed();

    Single<RegistrationPassed> agreeWithTerms();

    /**
     * @return two type of events:
     * {@link UserRegisteredAlready} or {@link NewUserCreated}
     */
    Single<Event> auth(String username, String password);
}