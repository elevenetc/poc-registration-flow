package poc.registration.app.di.components;

import javax.inject.Singleton;

import dagger.Component;
import poc.registration.app.di.modules.CoreModule;
import poc.registration.app.presenters.LaunchPresenter;

@Singleton
@Component(modules = {CoreModule.class})
public interface CoreComponent {
    LaunchPresenter inject(LaunchPresenter launchPresenter);
}