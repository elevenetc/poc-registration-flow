package poc.registration.app;

import org.junit.Test;

import poc.registration.app.presenters.LaunchPresenter;

import static org.junit.Assert.assertTrue;

public class PresentersTests {
    @Test
    public void addition_isCorrect() {
        //DIHelper.init();
//        LaunchPresenter presenter = new LaunchPresenter();
//        presenter.onViewCreated(Mockito.mock(LaunchView.class));
//        presenter.loginOrSignIn();

        LaunchPresenter presenter = new LaunchPresenter();
        assertTrue("1" == "1");
    }

    @Test
    public void xxx() {
        int s = 0;
        assertTrue(s == 0);
    }

    interface Hello {
        Object z();
    }
}
