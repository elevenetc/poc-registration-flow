package utils;

import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

public class MockitoUtils {
    public static VerificationMode once() {
        return Mockito.times(1);
    }
}
