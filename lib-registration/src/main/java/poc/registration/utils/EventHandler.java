package poc.registration.utils;

public interface EventHandler<E> {
    void handle(E event);
}
