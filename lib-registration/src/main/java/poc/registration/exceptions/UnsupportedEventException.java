package poc.registration.exceptions;

import poc.registration.events.Event;

public class UnsupportedEventException extends RuntimeException {
    public UnsupportedEventException(Event event) {
        super("Event: " + event == null ? " null" : event.toString());
    }
}
