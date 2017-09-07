package rx;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;

public class RxUtils {
    public static Completable complete() {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {
                s.onComplete();
            }
        };
    }
}
