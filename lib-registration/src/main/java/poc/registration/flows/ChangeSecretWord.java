package poc.registration.flows;

import io.reactivex.Single;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.SecretWordSet;

public class ChangeSecretWord implements SetSecretWordFlow {

    private BackendApi api;
    private Database database;

    public ChangeSecretWord(BackendApi api, Database database) {

        this.api = api;
        this.database = database;
    }

    @Override
    public Single<SecretWordSet> setSecretWord(String secretWord) {
        return Single.fromCallable(() -> {
            api.setSecretWord(secretWord, database.getToken());
            return new SecretWordSet();
        });
    }
}
