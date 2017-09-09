package poc.registration.app.di.components;

import javax.inject.Singleton;

import dagger.Component;
import poc.registration.app.di.modules.BackEndModule;
import poc.registration.app.di.modules.CommonModule;
import poc.registration.app.di.modules.FrontEndModule;
import poc.registration.app.presenters.*;
import poc.registration.flows.ChangeSecretWord;
import poc.registration.flows.RegistrationBackEndFlow;

@Singleton
@Component(modules = {
        CommonModule.class,
        FrontEndModule.class,
        BackEndModule.class
})
public interface PresentersComponent {

    InitPresenter inject(InitPresenter presenter);

    SecretWordPresenter inject(SecretWordPresenter presenter);

    LogInOrSignInPresenter inject(LogInOrSignInPresenter presenter);

    TermsPresenter inject(TermsPresenter presenter);

    MainPresenter inject(MainPresenter presenter);

    RegistrationBackEndFlow registrationFlow();

    ChangeSecretWord changeSecretWord();
}