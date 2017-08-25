import org.junit.Test;

import poc.registration.RegistrationFlow;
import poc.registration.api.BackendApi;
import poc.registration.cache.Database;
import poc.registration.impl.RegistrationFlowImpl;

public class RegistrationFlowIntegrationTest {
    @Test
    public void successForNotRegistered() {
        Database database = new InMemoryDatabase();
        BackendApi api = new SuccessBackendApi(false);
        RegistrationFlow registrationFlow = new RegistrationFlowImpl(database, api);

        //registrationFlow.sendEvent(new NewAuthRequest())
    }
}
