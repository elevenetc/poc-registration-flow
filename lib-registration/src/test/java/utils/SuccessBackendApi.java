package utils;

import poc.registration.api.BackendApi;
import poc.registration.events.InvalidTokenException;
import poc.registration.models.AgreeWithTermsResponse;
import poc.registration.models.AuthResponse;
import poc.registration.models.SecretWordResponse;

public class SuccessBackendApi implements BackendApi {

    String token = "token";
    private boolean alreadyRegistered;

    public SuccessBackendApi(boolean alreadyRegistered) {

        this.alreadyRegistered = alreadyRegistered;
    }

    @Override
    public AuthResponse auth(String username, String password) {
        return new AuthResponse(token, alreadyRegistered);
    }

    @Override
    public SecretWordResponse setSecretWord(String secretWord, String token) {
        validateToken(token);
        return new SecretWordResponse();
    }

    @Override
    public String getTerms(String token) {
        validateToken(token);
        return "terms";
    }

    @Override
    public AgreeWithTermsResponse agreeWithTerms(String token) {
        validateToken(token);
        return new AgreeWithTermsResponse();
    }

    private void validateToken(String token) {
        if (!this.token.equals(token)) {
            throw new InvalidTokenException();
        }
    }
}
