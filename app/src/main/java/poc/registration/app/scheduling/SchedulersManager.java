package poc.registration.app.scheduling;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;

public interface SchedulersManager {

    Scheduler ui();

    Scheduler background();

    <T> SingleTransformer<T, T> uiAndBackSingle();

    <T> FlowableTransformer<T, T> uiAndBackFlowable();

    CompletableTransformer uiAndBackCompletable();

}
