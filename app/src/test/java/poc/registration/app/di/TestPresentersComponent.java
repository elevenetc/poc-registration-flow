package poc.registration.app.di;

import javax.inject.Singleton;

import dagger.Component;
import poc.registration.app.di.components.PresentersComponent;
import poc.registration.app.di.modules.BackEndModule;
import poc.registration.app.di.modules.CommonModule;
import poc.registration.app.di.modules.FrontEndModule;

@Singleton
@Component(
        modules = {
                CommonModule.class,
                FrontEndModule.class,
                BackEndModule.class
        }
)
public interface TestPresentersComponent extends PresentersComponent {

}
