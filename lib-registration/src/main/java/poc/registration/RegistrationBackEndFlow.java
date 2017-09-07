package poc.registration;

import io.reactivex.Single;
import poc.registration.events.*;

public interface RegistrationBackEndFlow {

    Single<Boolean> isRegistrationPassed();

    Single<RegistrationPassed> agreeWithTerms();

    Single<SecretWordSet> setSecretWord(String secretWord);

    /**
     * @return two type of events:
     * {@link UserRegisteredAlready} or {@link NewUserCreated}
     */
    Single<Event> auth(String username, String password);
}