package poc.registration.app.di.components;

import javax.inject.Singleton;

import dagger.Component;
import poc.registration.app.di.modules.FrontEndModule;
import poc.registration.app.views.Screens;

@Singleton
@Component(modules = FrontEndModule.class)
public interface FrontEndComponent {
    Screens screens();
}
