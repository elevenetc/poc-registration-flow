import poc.registration.events.Event;

public class UnsupportedEvent extends Event {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnsupportedEvent || super.equals(obj);
    }
}
