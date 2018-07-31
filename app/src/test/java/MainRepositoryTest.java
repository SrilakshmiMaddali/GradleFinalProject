import android.test.mock.MockContext;

import com.udacity.gradle.builditbigger.repository.MainRepository;

import org.junit.Before;
import org.junit.Test;

public class MainRepositoryTest {

    private MockContext mMockContext;
    private MainRepository mMainRepository;

    @Before
    void setup() {
        mMockContext = new MockContext();
        mMainRepository = new MainRepository();
    }

    @Test
    void getNextJoke_ReceivesBroadcast() {
        mMainRepository.registerReceiver(mMockContext);
        mMainRepository.getNextJoke(mMockContext);
        mMainRepository.getNextJoke(mMockContext);



        mMainRepository.deregisterReceiver(mMockContext);
    }
}
