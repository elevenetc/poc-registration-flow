package poc.registration.app.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import poc.registration.app.R;
import poc.registration.app.presenters.MainPresenter;

import static poc.registration.app.di.DIHelper.frontEndComponent;
import static poc.registration.app.di.DIHelper.presentersComponent;

public class MainActivity extends AppCompatActivity implements MainView {

    private Screens screens;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPresenter presenter = presentersComponent().inject(new MainPresenter());
        screens = frontEndComponent().screens();

        findViewById(R.id.btn_logout).setOnClickListener(v -> presenter.logOut());
        findViewById(R.id.btn_change_secret_word).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screens.goToCreateSecretWord();
            }
        });
    }
}
