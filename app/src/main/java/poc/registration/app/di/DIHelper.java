package poc.registration.app.di;

import poc.registration.app.App;
import poc.registration.app.di.components.DaggerPresentersComponent;
import poc.registration.app.di.components.PresentersComponent;
import poc.registration.app.di.modules.BackEndModule;
import poc.registration.app.di.modules.CommonModule;
import poc.registration.app.di.modules.FrontEndModule;

public class DIHelper {

    private static PresentersComponent presentersComponent;

    public static void init(App application) {

        presentersComponent = DaggerPresentersComponent.builder()
                .backEndModule(new BackEndModule())
                .frontEndModule(new FrontEndModule(application))
                .commonModule(new CommonModule())
                .build();
    }

    public static PresentersComponent presentersComponent() {
        return presentersComponent;
    }
}
