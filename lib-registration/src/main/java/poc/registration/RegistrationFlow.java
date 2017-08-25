package poc.registration;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import poc.registration.events.Event;

public interface RegistrationFlow {

    Flowable<Event> eventsObservable();

    Completable sendEvent(Event event);

}