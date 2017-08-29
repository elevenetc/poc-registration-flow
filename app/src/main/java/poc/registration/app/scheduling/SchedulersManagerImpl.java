package poc.registration.app.scheduling;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SchedulersManagerImpl implements SchedulersManager {

    private final SingleTransformer uiAndBackSingle =
            upstream -> upstream.subscribeOn(background()).observeOn(ui());

    private final CompletableTransformer uiAndBackCompletable =
            upstream -> upstream.subscribeOn(background()).observeOn(ui());

    private final FlowableTransformer uiAndBackFlowable =
            upstream -> upstream.subscribeOn(background()).observeOn(ui());

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler background() {
        return Schedulers.io();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> SingleTransformer<T, T> single() {
        return uiAndBackSingle;
    }

    @Override
    public <T> FlowableTransformer<T, T> flowable() {
        return uiAndBackFlowable;
    }

    @Override
    public CompletableTransformer completable() {
        return uiAndBackCompletable;
    }

}

