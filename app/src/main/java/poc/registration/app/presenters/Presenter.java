package poc.registration.app.presenters;

public abstract class Presenter<View> {

    protected View view;

    public Presenter onViewCreated(View view) {
        this.view = view;
        return this;
    }

    public void onViewDestroyed() {
        view = null;
    }
}
