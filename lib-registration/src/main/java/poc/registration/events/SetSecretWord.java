package poc.registration.events;

public class SetSecretWord extends Event {

    public final String secretWord;

    public SetSecretWord(String secretWord) {

        this.secretWord = secretWord;
    }
}
