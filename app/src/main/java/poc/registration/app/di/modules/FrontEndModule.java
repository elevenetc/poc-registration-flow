package poc.registration.app.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import poc.registration.app.App;
import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.flows.RegistrationFrontEndFlow;
import poc.registration.app.views.Screens;
import poc.registration.app.views.ScreensImpl;

@Singleton
@Module
public class FrontEndModule {

    private App application;

    public FrontEndModule(App application) {

        this.application = application;
    }

    @Provides
    @Singleton
    public FrontEndFlow provideFrontEndFlow(Screens screens) {
        return new RegistrationFrontEndFlow(screens);
    }

    @Provides
    @Singleton
    public Screens provideScreensLauncher() {
        return new ScreensImpl(application);
    }
}
