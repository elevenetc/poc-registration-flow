package poc.registration.cache;

import io.reactivex.Completable;

public interface Database {

    void storeToken(String token);

    String getToken();

    void setRegistrationPassed();

    boolean isRegistrationPassed();

    Completable logout();
}