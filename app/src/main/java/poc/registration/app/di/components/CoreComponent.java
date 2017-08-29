package poc.registration.app.di.components;

import javax.inject.Singleton;

import dagger.Component;
import poc.registration.app.di.modules.CoreModule;
import poc.registration.app.presenters.LaunchPresenter;
import poc.registration.app.presenters.LogInOrSignInPresenter;
import poc.registration.app.presenters.SecretWordPresenter;
import poc.registration.app.presenters.TermsPresenter;

@Singleton
@Component(modules = {CoreModule.class})
public interface CoreComponent {
    LaunchPresenter inject(LaunchPresenter presenter);

    SecretWordPresenter inject(SecretWordPresenter presenter);

    LogInOrSignInPresenter inject(LogInOrSignInPresenter presenter);

    TermsPresenter inject(TermsPresenter presenter);
}