package poc.registration.app.di;

import poc.registration.app.App;
import poc.registration.app.di.components.DaggerFrontEndComponent;
import poc.registration.app.di.components.DaggerPresentersComponent;
import poc.registration.app.di.components.FrontEndComponent;
import poc.registration.app.di.components.PresentersComponent;
import poc.registration.app.di.modules.BackEndModule;
import poc.registration.app.di.modules.CommonModule;
import poc.registration.app.di.modules.FrontEndModule;

public class DIHelper {

    private static PresentersComponent presentersComponent;
    private static FrontEndComponent frontendComponent;

    public static void init(App application) {

        FrontEndModule frontEndModule = new FrontEndModule(application);
        BackEndModule backEndModule = new BackEndModule();
        CommonModule commonModule = new CommonModule();

        presentersComponent = DaggerPresentersComponent
                .builder()
                .backEndModule(backEndModule)
                .frontEndModule(frontEndModule)
                .commonModule(commonModule)
                .build();

        frontendComponent = DaggerFrontEndComponent
                .builder()
                .frontEndModule(frontEndModule)
                .build();
    }

    public static PresentersComponent presentersComponent() {
        return presentersComponent;
    }

    public static FrontEndComponent frontEndComponent() {
        return frontendComponent;
    }
}
