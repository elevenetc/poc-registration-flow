package poc.registration.app.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import poc.registration.RegistrationFlow;
import poc.registration.app.R;
import poc.registration.app.presenters.LaunchPresenter;

import static poc.registration.app.di.DIHelper.coreComponent;

public class LaunchActivity extends AppCompatActivity {

    @Inject
    RegistrationFlow registrationFlow;
    private LaunchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        presenter = coreComponent()
                .inject(new LaunchPresenter())
                .onCreate();


    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
