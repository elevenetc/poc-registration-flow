package poc.registration.app.views;

import android.content.Intent;

import poc.registration.app.App;

public class ScreensLauncherImpl implements ScreensLauncher {

    private App app;

    public ScreensLauncherImpl(App app) {

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
    public void goToSecretWord() {
        app.startActivity(new Intent(app, SecretWordActivity.class));
    }

    @Override
    public void gotoTerms() {
        app.startActivity(new Intent(app, TermsActivity.class));
    }

    @Override
    public void gotoLauncher() {
        app.startActivity(new Intent(app, LaunchActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
        );
    }
}