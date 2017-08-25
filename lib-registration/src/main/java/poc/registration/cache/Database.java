package poc.registration.cache;

public interface Database {

    void storeToken(String token);

    String getToken();

    void setRegistrationPassed();

    boolean isRegistrationPassed();

}