package poc.registration.app.flows;

import poc.registration.events.Event;

public interface FrontEndFlow {

    void handleEvent(Event event);

}
