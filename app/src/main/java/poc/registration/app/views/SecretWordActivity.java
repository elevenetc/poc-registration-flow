package poc.registration.app.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import poc.registration.app.R;
import poc.registration.app.presenters.SecretWordPresenter;

import static poc.registration.app.di.DIHelper.presentersComponent;

public class SecretWordActivity extends AppCompatActivity implements SecretWordView {


    private SecretWordPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_word);

        presenter = presentersComponent()
                .inject(new SecretWordPresenter(
                                presentersComponent().registrationFlow()
                        )
                );
        presenter.onViewCreated(this);

        EditText editSecretWord = (EditText) findViewById(R.id.edit_secret_word);

        findViewById(R.id.btn_create_secret_word).setOnClickListener(
                v -> presenter.setSecretWord(editSecretWord.getText().toString())
        );
    }

    @Override
    public void showError() {

    }
}
