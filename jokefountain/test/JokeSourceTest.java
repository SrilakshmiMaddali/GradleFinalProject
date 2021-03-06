import com.stevenberdak.jokefountain.repos.ichdj.IchdjDownloadSeed;
import com.stevenberdak.jokefountain.JokeSource;
import com.stevenberdak.jokefountain.models.JokeData;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class JokeSourceTest {

    @Test
    public void nextJoke_HasData() {
        JokeSource jokeSource = new JokeSource();

        JokeData testJokeData = jokeSource.nextJoke(new IchdjDownloadSeed());

        assertTrue(testJokeData.jokeBody.length() > 0);

        assertThat(testJokeData.statusCode, not(JokeData.STATUS_ERROR));

        System.out.println(testJokeData.toString());
    }
}
