package poc.registration.events;

public class CreateSecretWord extends Event {

    public final String secretWord;

    public CreateSecretWord(String secretWord) {

        this.secretWord = secretWord;
    }
}
