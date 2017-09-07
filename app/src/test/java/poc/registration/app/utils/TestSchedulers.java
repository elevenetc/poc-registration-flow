package poc.registration.app.utils;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;
import io.reactivex.schedulers.TestScheduler;
import poc.registration.app.scheduling.SchedulersManager;

public class TestSchedulers implements SchedulersManager {

    private Scheduler scheduler = new TestScheduler();

    @Override
    public Scheduler ui() {
        return scheduler;
    }

    @Override
    public Scheduler background() {
        return scheduler;
    }

    @Override
    public <T> SingleTransformer<T, T> single() {
        return upstream -> upstream;
    }

    @Override
    public <T> FlowableTransformer<T, T> flowable() {
        return upstream -> upstream;
    }

    @Override
    public CompletableTransformer completable() {
        return upstream -> upstream;
    }
}
