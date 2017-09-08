package poc.registration.app.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import poc.registration.api.BackendApi;
import poc.registration.app.api.SuccessBackEndApi;
import poc.registration.cache.Database;
import poc.registration.flows.ChangeSecretWord;
import poc.registration.flows.RegistrationBackEndFlow;
import poc.registration.flows.RegistrationBackEndFlowImpl;

@Singleton
@Module
public class BackEndModule {

    @Provides
    @Singleton
    public RegistrationBackEndFlow provideRegistrationFlow(BackendApi backendApi, Database database) {
        return new RegistrationBackEndFlowImpl(database, backendApi);
    }

    @Provides
    @Singleton
    public ChangeSecretWord provideChangeSecretWord(BackendApi backendApi, Database database) {
        return new ChangeSecretWord(backendApi, database);
    }

    @Provides
    @Singleton
    public BackendApi getBackEndApi() {
        return new SuccessBackEndApi();
    }
}
