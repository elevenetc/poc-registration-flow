package poc.registration.app.di.components;

import javax.inject.Singleton;

import dagger.Component;
import poc.registration.app.di.modules.BackEndModule;
import poc.registration.app.di.modules.CommonModule;
import poc.registration.app.di.modules.FrontEndModule;
import poc.registration.app.presenters.*;

@Singleton
@Component(modules = {
        CommonModule.class,
        FrontEndModule.class,
        BackEndModule.class
})
public interface PresentersComponent {
    LaunchPresenter inject(LaunchPresenter presenter);

    SecretWordPresenter inject(SecretWordPresenter presenter);

    LogInOrSignInPresenter inject(LogInOrSignInPresenter presenter);

    TermsPresenter inject(TermsPresenter presenter);

    MainPresenter inject(MainPresenter presenter);
}