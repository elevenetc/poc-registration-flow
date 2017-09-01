package poc.registration.app.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import poc.registration.RegistrationBackEndFlow;
import poc.registration.app.api.SuccessBackEndApi;
import poc.registration.cache.Database;
import poc.registration.impl.RegistrationBackEndFlowImpl;

@Singleton
@Module
public class BackEndModule {
    @Provides
    @Singleton
    public RegistrationBackEndFlow provideRegistrationFlow(Database database) {
        return new RegistrationBackEndFlowImpl(database, new SuccessBackEndApi());
    }
}
