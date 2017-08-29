package poc.registration.app.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import poc.registration.app.R;
import poc.registration.app.di.DIHelper;
import poc.registration.app.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPresenter presenter = DIHelper.coreComponent().inject(new MainPresenter());

        findViewById(R.id.btn_logout).setOnClickListener(v -> presenter.logOut());
    }
}
