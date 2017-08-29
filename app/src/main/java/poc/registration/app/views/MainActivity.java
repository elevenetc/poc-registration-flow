package poc.registration.app.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import poc.registration.app.R;
import poc.registration.app.flows.View;

public class MainActivity extends AppCompatActivity implements View {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
