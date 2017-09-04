package poc.registration.app.utils;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;
import io.reactivex.schedulers.TestScheduler;
import poc.registration.app.scheduling.SchedulersManager;

public class TestSchedulers implements SchedulersManager {

    private TestScheduler ui = new TestScheduler();
    private TestScheduler backend = new TestScheduler();

    @Override
    public Scheduler ui() {
        return ui;
    }

    @Override
    public Scheduler background() {
        return ui;
    }

    @Override
    public <T> SingleTransformer<T, T> single() {
        return upstream -> upstream.subscribeOn(backend).observeOn(ui);
    }

    @Override
    public <T> FlowableTransformer<T, T> flowable() {
        return upstream -> upstream.subscribeOn(backend).observeOn(ui);
    }

    @Override
    public CompletableTransformer completable() {
        return upstream -> upstream.subscribeOn(backend).observeOn(ui);
    }
}
