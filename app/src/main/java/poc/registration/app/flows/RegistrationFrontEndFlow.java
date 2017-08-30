package poc.registration.app.flows;

import poc.registration.app.views.ScreensLauncher;
import poc.registration.events.*;

public class RegistrationFrontEndFlow implements FrontEndFlow {

    private ScreensLauncher screensLauncher;

    public RegistrationFrontEndFlow(ScreensLauncher screensLauncher) {

        this.screensLauncher = screensLauncher;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof RegistrationPassed) {
            //
            screensLauncher.goToMain();
        } else if (event instanceof StartLoginOrSingIn) {
            screensLauncher.goToLogInOrSignIn();
        } else if (event instanceof UserRegisteredAlready) {
            screensLauncher.goToMain();
        } else if (event instanceof NewUserCreated) {
            screensLauncher.goToSecretWord();
        } else if (event instanceof SecretWordSet) {
            screensLauncher.gotoTerms();
        }
    }
}