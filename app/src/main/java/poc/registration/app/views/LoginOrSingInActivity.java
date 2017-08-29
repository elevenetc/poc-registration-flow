package poc.registration.app.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import poc.registration.app.R;
import poc.registration.app.presenters.LogInOrSignInPresenter;

import static poc.registration.app.di.DIHelper.coreComponent;

public class LoginOrSingInActivity
        extends AppCompatActivity
        implements LoginOrSingInView {

    private LogInOrSignInPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        presenter = coreComponent().inject(new LogInOrSignInPresenter());
        presenter.onViewCreated(this);

        EditText editUsername = (EditText) findViewById(R.id.edit_username);
        EditText editPassword = (EditText) findViewById(R.id.edit_password);
        findViewById(R.id.btn_login_or_signin).setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            presenter.loginOrSignIn(username, password);
        });
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroyed();
        super.onDestroy();
    }

    @Override
    public void showError() {

    }
}
