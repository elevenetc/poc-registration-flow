package poc.registration.app.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import poc.registration.app.R;
import poc.registration.app.presenters.InitPresenter;

import static poc.registration.app.di.DIHelper.presentersComponent;

public class InitActivity extends AppCompatActivity implements InitView {

    private InitPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        presenter = presentersComponent()
                .inject(new InitPresenter())
                .onViewCreated(this);

        findViewById(R.id.btn_login_or_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginOrSignIn();
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroyed();
        super.onDestroy();
    }
}