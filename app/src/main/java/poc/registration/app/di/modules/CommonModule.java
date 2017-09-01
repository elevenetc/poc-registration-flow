package poc.registration.app.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import poc.registration.app.database.InMemoryDatabase;
import poc.registration.app.scheduling.SchedulersManager;
import poc.registration.app.scheduling.SchedulersManagerImpl;
import poc.registration.cache.Database;

@Singleton
@Module
public class CommonModule {

    @Provides
    @Singleton
    public SchedulersManager provideSchedulersManager() {
        return new SchedulersManagerImpl();
    }

    @Provides
    @Singleton
    public Database provideDatabase() {
        return new InMemoryDatabase();
    }

}