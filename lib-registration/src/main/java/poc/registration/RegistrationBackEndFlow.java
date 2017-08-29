package poc.registration;

import io.reactivex.Single;
import poc.registration.events.Event;
import poc.registration.events.RegistrationPassed;
import poc.registration.events.SecretWordSet;

public interface RegistrationBackEndFlow {

    Single<Boolean> isRegistrationPassed();

    Single<RegistrationPassed> agreeWithTerms();

    Single<SecretWordSet> setSecretWord(String secretWord);

    Single<Event> auth(String username, String password);
}