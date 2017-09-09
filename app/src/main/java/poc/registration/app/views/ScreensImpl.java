package poc.registration.app.views;

import android.content.Intent;

import poc.registration.app.App;

public class ScreensImpl implements Screens {

    private App app;

    public ScreensImpl(App app) {

        this.app = app;
    }

    @Override
    public void goToMain() {
        app.startActivity(new Intent(app, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
        );
    }

    @Override
    public void goToLogInOrSignIn() {
        app.startActivity(new Intent(app, LoginOrSingInActivity.class));
    }

    @Override
    public void goToCreateSecretWord() {
        app.startActivity(SecretWordActivity.registrationStep(app));
    }

    @Override
    public void gotToChangeSecretWord() {
        app.startActivity(SecretWordActivity.change(app));
    }

    @Override
    public void gotoTerms() {
        app.startActivity(new Intent(app, TermsActivity.class));
    }

    @Override
    public void gotoInit() {
        app.startActivity(new Intent(app, InitActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
        );
    }
}