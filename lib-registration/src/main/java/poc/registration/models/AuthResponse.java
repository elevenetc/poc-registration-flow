package poc.registration.models;

public class AuthResponse {

    public String token;
    public boolean alreadyRegistered;

    public AuthResponse(String token, boolean alreadyRegistered) {
        this.token = token;
        this.alreadyRegistered = alreadyRegistered;
    }

    public AuthResponse() {

    }
}