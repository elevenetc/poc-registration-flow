package utils;

import io.reactivex.Completable;
import poc.registration.cache.Database;

public class InMemoryDatabase implements Database {

    public boolean isRegistrationPassed = false;
    public String token;

    @Override
    public void storeToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setRegistrationPassed() {
        isRegistrationPassed = true;
    }

    @Override
    public boolean isRegistrationPassed() {
        return isRegistrationPassed;
    }

    @Override
    public Completable logout() {
        return Completable.fromAction(() -> {
            isRegistrationPassed = false;
            token = null;
        });
    }
}
