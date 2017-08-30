package poc.registration.impl;

import io.reactivex.Single;
import poc.registration.RegistrationBackEndFlow;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.*;
import poc.registration.models.AuthResponse;

public class RegistrationBackEndFlowImpl implements RegistrationBackEndFlow {

    private final Database database;
    private final BackendApi backendApi;

    public RegistrationBackEndFlowImpl(Database database, BackendApi backendApi) {

        this.database = database;
        this.backendApi = backendApi;
    }

    @Override
    public Single<Boolean> isRegistrationPassed() {
        return Single.fromCallable(database::isRegistrationPassed);
    }

    @Override
    public Single<RegistrationPassed> agreeWithTerms() {
        return Single.fromCallable(() -> {
            backendApi.agreeWithTerms(database.getToken());
            database.setRegistrationPassed();
            return new RegistrationPassed();
        });
    }

    @Override
    public Single<SecretWordSet> setSecretWord(String secretWord) {

        return Single.fromCallable(() -> {
            String token = database.getToken();
            backendApi.setSecretWord(secretWord, token);
            return new SecretWordSet();
        });
    }

    @Override
    public Single<Event> auth(String username, String password) {

        return Single.fromCallable(() -> {
            AuthResponse response = backendApi.auth(username, password);
            database.storeToken(response.token);

            if (response.alreadyRegistered) {
                return new UserRegisteredAlready();
            } else {
                return new NewUserCreated();
            }
        });
    }
}