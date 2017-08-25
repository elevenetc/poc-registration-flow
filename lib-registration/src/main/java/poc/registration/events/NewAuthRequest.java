package poc.registration.events;

public class NewAuthRequest extends Event {
    public String username;
    public String password;

    public NewAuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}