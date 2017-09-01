package poc.registration.app.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import poc.registration.app.R;
import poc.registration.app.presenters.TermsPresenter;

import static poc.registration.app.di.DIHelper.presentersComponent;

public class TermsActivity extends AppCompatActivity implements TermsView {

    private TermsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        presenter = presentersComponent().inject(new TermsPresenter());
        presenter.onViewCreated(this);

        findViewById(R.id.btn_sign_terms).setOnClickListener(
                v -> presenter.signTerms()
        );
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroyed();
        super.onDestroy();
    }
}