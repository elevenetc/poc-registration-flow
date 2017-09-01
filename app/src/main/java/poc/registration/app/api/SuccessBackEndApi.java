package poc.registration.app.api;

import poc.registration.api.BackendApi;
import poc.registration.models.AgreeWithTermsResponse;
import poc.registration.models.AuthResponse;
import poc.registration.models.SecretWordResponse;

public class SuccessBackEndApi implements BackendApi {
    @Override
    public AuthResponse auth(String username, String password) {
        return new AuthResponse("", false);
    }

    @Override
    public SecretWordResponse setSecretWord(String secretWord, String token) {
        return new SecretWordResponse();
    }

    @Override
    public String getTerms(String token) {
        return "terms";
    }

    @Override
    public AgreeWithTermsResponse agreeWithTerms(String token) {
        return new AgreeWithTermsResponse();
    }
}
