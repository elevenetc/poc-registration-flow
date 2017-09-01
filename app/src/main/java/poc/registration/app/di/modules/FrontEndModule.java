package poc.registration.app.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import poc.registration.app.App;
import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.flows.RegistrationFrontEndFlow;
import poc.registration.app.views.ScreensLauncher;
import poc.registration.app.views.ScreensLauncherImpl;

@Singleton
@Module
public class FrontEndModule {

    private App application;

    public FrontEndModule(App application) {

        this.application = application;
    }

    @Provides
    @Singleton
    public FrontEndFlow provideFrontEndFlow(ScreensLauncher screensLauncher) {
        return new RegistrationFrontEndFlow(screensLauncher);
    }

    @Provides
    @Singleton
    public ScreensLauncher provideScreensLauncher() {
        return new ScreensLauncherImpl(application);
    }
}
