package poc.registration.api;

import poc.registration.models.AgreeWithTermsResponse;
import poc.registration.models.AuthResponse;
import poc.registration.models.SecretWordResponse;

public interface BackendApi {

    AuthResponse auth(String username, String password);

    SecretWordResponse createSecretWord(String secretWord, String token);

    String getTerms(String token);

    AgreeWithTermsResponse agreeWithTerms(String token);

}