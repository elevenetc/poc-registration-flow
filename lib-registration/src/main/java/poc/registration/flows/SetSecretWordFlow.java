package poc.registration.flows;

import io.reactivex.Single;
import poc.registration.events.SecretWordSet;

public interface SetSecretWordFlow {

    Single<SecretWordSet> setSecretWord(String secretWord);
}
