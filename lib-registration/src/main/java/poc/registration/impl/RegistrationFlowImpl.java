package poc.registration.impl;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.processors.PublishProcessor;
import poc.registration.RegistrationFlow;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.events.*;
import poc.registration.exceptions.UnsupportedEventException;
import poc.registration.models.AuthResponse;

public class RegistrationFlowImpl implements RegistrationFlow {

    private final Database database;
    private final BackendApi backendApi;

    PublishProcessor<Event> outBus = PublishProcessor.create();

    public RegistrationFlowImpl(Database database, BackendApi backendApi) {

        this.database = database;
        this.backendApi = backendApi;
    }

    @Override
    public Flowable<Event> eventsObservable() {
        return outBus;
    }

    @Override
    public Completable sendEvent(Event event) {

        if (event instanceof NewAuthRequest) {
            return authAndStoreToken((NewAuthRequest) event);
        } else if (event instanceof CreateSecretWord) {
            return createSecretWord((CreateSecretWord) event);
        } else if (event instanceof AgreeWithTermsRequest) {
            return agreeWithTerms();
        }

        return Completable.error(new UnsupportedEventException(event));
    }

    @Override
    public Single<Boolean> isRegistrationPassed() {
        return Single.fromCallable(database::isRegistrationPassed);
    }

    private Completable agreeWithTerms() {
        return Completable.fromAction(() -> {
            backendApi.agreeWithTerms(database.getToken());
            database.setRegistrationPassed();
            outBus.onNext(new RegistrationPassed());
        });
    }

    private Completable createSecretWord(CreateSecretWord event) {
        return Completable.create(completable -> {
            String token = database.getToken();
            backendApi.createSecretWord(event.secretWord, token);
            completable.onComplete();

            outBus.onNext(new SecretWordCreated());
        });
    }

    private Completable authAndStoreToken(NewAuthRequest event) {
        return Completable.create(completable -> {
            AuthResponse response = backendApi.auth(event.username, event.password);
            database.storeToken(response.token);

            completable.onComplete();

            if (response.alreadyRegistered) {
                outBus.onNext(new UserRegisteredAlready());
            } else {
                outBus.onNext(new NewUserCreated());
            }
        });
    }
}