import poc.registration.cache.Database;

public class InMemoryDatabase implements Database {

    boolean isRegistrationPassed = false;
    String token;

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
        return false;
    }
}
