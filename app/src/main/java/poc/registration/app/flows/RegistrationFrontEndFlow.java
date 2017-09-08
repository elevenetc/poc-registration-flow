package poc.registration.app.flows;

import poc.registration.app.views.Screens;
import poc.registration.events.*;

public class RegistrationFrontEndFlow implements FrontEndFlow {

    private Screens screens;

    public RegistrationFrontEndFlow(Screens screens) {

        this.screens = screens;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof RegistrationPassed) {
            //
            screens.goToMain();
        } else if (event instanceof StartLoginOrSingIn) {
            screens.goToLogInOrSignIn();
        } else if (event instanceof UserRegisteredAlready) {
            screens.goToMain();
        } else if (event instanceof NewUserCreated) {
            screens.goToCreateSecretWord();
        } else if (event instanceof SecretWordSet) {
            screens.gotoTerms();
        }
    }
}