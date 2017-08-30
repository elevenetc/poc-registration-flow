package poc.registration.app.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import poc.registration.RegistrationBackEndFlow;
import poc.registration.api.BackendApi;
import poc.registration.app.App;
import poc.registration.app.database.InMemoryDatabase;
import poc.registration.app.flows.FrontEndFlow;
import poc.registration.app.flows.RegistrationFrontEndFlow;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.scheduling.SchedulersManagerImpl;
import poc.registration.app.views.ScreensLauncher;
import poc.registration.app.views.ScreensLauncherImpl;
import poc.registration.cache.Database;
import poc.registration.impl.RegistrationBackEndFlowImpl;
import poc.registration.models.AgreeWithTermsResponse;
import poc.registration.models.AuthResponse;
import poc.registration.models.SecretWordResponse;

@Singleton
@Module
public class CoreModule {

    private App application;

    public CoreModule(App application) {

        this.application = application;
    }

    @Provides
    @Singleton
    public FrontEndFlow provideFrontEndFlow(ScreensLauncher screensLauncher) {
        return new RegistrationFrontEndFlow(screensLauncher);
    }


    @Provides
    @Singleton
    public SchedulersManager provideSchedulersManager() {
        return new SchedulersManagerImpl();
    }

    @Provides
    @Singleton
    public RegistrationBackEndFlow provideRegistrationFlow(Database database) {
        return new RegistrationBackEndFlowImpl(database, getBackendApi());
    }

    @Provides
    @Singleton
    public Database provideDatabase() {
        return new InMemoryDatabase();
    }

    @Provides
    @Singleton
    public ScreensLauncher provideScreensLauncher() {
        return new ScreensLauncherImpl(application);
    }

    private BackendApi getBackendApi() {
        return new BackendApi() {

            @Override
            public AuthResponse auth(String username, String password) {
                return new AuthResponse("", false);
            }

            @Override
            public SecretWordResponse setSecretWord(String secretWord, String token) {
                return new SecretWordResponse();
            }

            @Override
            public String getTerms(String token) {
                return "terms";
            }

            @Override
            public AgreeWithTermsResponse agreeWithTerms(String token) {
                return new AgreeWithTermsResponse();
            }
        };
    }
}