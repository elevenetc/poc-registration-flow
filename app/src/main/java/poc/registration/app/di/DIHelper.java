package poc.registration.app.di;

import poc.registration.app.App;
import poc.registration.app.di.components.CoreComponent;
import poc.registration.app.di.components.DaggerCoreComponent;
import poc.registration.app.di.modules.CoreModule;

public class DIHelper {

    private static CoreComponent coreComponent;

    public static void init(App application) {
        coreComponent = DaggerCoreComponent.builder()
                .coreModule(new CoreModule(application))
                .build();
    }

    public static CoreComponent coreComponent() {
        return coreComponent;
    }
}
