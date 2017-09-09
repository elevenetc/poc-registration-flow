package poc.registration.app.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import poc.registration.app.R;
import poc.registration.app.presenters.SecretWordPresenter;
import poc.registration.flows.SetSecretWordFlow;

import static poc.registration.app.di.DIHelper.presentersComponent;

public class SecretWordActivity extends AppCompatActivity implements SecretWordView {

    private static final int TYPE_REG = 1;
    private static final int TYPE_CHANGE = 2;
    private static String TYPE = "type";
    private SecretWordPresenter presenter;

    public static Intent registrationStep(Context context) {
        Intent result = new Intent(context, SecretWordActivity.class);
        result.putExtra(TYPE, TYPE_REG);
        return result;
    }

    public static Intent change(Context context) {
        Intent result = new Intent(context, SecretWordActivity.class);
        result.putExtra(TYPE, TYPE_CHANGE);
        return result;
    }

    @Override
    public void showError() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_word);


        SetSecretWordFlow flow;

        if (getIntent().getExtras().getInt(TYPE) == TYPE_REG) {
            flow = presentersComponent().registrationFlow();
        } else {
            flow = presentersComponent().changeSecretWord();
        }

        presenter = presentersComponent().inject(new SecretWordPresenter(flow));
        presenter.onViewCreated(this);

        EditText editSecretWord = (EditText) findViewById(R.id.edit_secret_word);

        findViewById(R.id.btn_create_secret_word).setOnClickListener(
                v -> presenter.setSecretWord(editSecretWord.getText().toString())
        );
    }
}
