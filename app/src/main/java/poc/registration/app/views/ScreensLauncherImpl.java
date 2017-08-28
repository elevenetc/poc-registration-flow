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
        app.startActivity(new Intent(app, AuthActivity.class));
    }

    @Override
    public void goToSecretWord() {

    }
}