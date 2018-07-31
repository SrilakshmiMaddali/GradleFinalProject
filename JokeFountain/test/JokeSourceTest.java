import com.stevenberdak.jokefountain.JokeSource;
import com.stevenberdak.jokefountain.LibStrings;
import com.stevenberdak.jokefountain.Models.Joke;

import org.junit.Test;

import com.stevenberdak.jokefountain.Icanhazdadjoke.IcanhazdadjokeDownloadSeed;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class JokeSourceTest {

    @Test
    public void nextJoke_HasData() {
        JokeSource jokeSource = new JokeSource();

        Joke testJoke = jokeSource.nextJoke(new IcanhazdadjokeDownloadSeed());

        assertTrue(testJoke.jokeBody.length() > 0);

        assertThat(testJoke.jokeBody, not(LibStrings.JOKE_ERROR));

        System.out.println(testJoke.toString());
    }
}
