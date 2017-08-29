package poc.registration.app.utils;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class AbstractCompletableObserver implements CompletableObserver {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
