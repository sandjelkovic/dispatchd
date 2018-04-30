package com.sandjelkovic.dispatchd.content.trakt.provider;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.config.Constants;
import com.sandjelkovic.dispatchd.content.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowUpdateTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.TvShowTrakt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author ${sandjelkovic}
 * @date 5.2.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@IfProfileValue(name = "tests.profiles", values = {"third-party"})
@SpringBootTest(classes = {DispatchdApplication.class})
public class TraktMediaProviderTest {
	private static final String ID_KEY_TRAKT = "trakt";
	private static final String ID_KEY_TVDB = "tvdb";
	private static final String ID_KEY_IMDB = "imdb";

	@Autowired
	@Qualifier("defaultTraktMediaProvider")
	private TraktMediaProvider provider;

	@Test
	public void getUpdates() throws Exception {
		List<ShowUpdateTrakt> updates = provider.getUpdates(LocalDate.now().minusDays(7));
		assertThat(updates.size(), greaterThanOrEqualTo(2));
	}

	@Test
	public void getShow() throws Exception {
		TvShowTrakt tvShow = provider.getTvShow(Constants.STAR_TREK_TNG_SLUG);
		assertThat(tvShow, notNullValue());
		assertThat(tvShow.getTitle(), is("Star Trek: The Next Generation"));
		assertThat(tvShow.getOverview(), notNullValue());
		assertThat(tvShow.getUpdatedAt(), notNullValue());
		assertThat(tvShow.getIds(), hasKey(ID_KEY_TRAKT));
		assertThat(tvShow.getIds(), anyOf(hasKey(ID_KEY_IMDB), hasKey(ID_KEY_TVDB)));
	}

	@Test
	public void getSeasons() throws Exception {
		List<SeasonTrakt> seasons = provider.getSeasons(Constants.STAR_TREK_TNG_SLUG);
		assertThat(seasons, notNullValue());
		assertThat(seasons, not(empty()));
		assertThat(seasons.size(), greaterThanOrEqualTo(7)); // at the time of writing, Trakt is reporting 8 seasons, with season 0 having 1 episode. Why Trakt, why...
		seasons.forEach(season -> {
			assertThat(season.getNumber(), not(isEmptyOrNullString()));
			assertThat(season.getAiredEpisodes(), anyOf(greaterThan(20), is(1))); // season 0 -> 1 episode...
			assertThat(season.getIds(), hasKey(ID_KEY_TRAKT));
		});
	}

	@Test
	public void getShowEpisodes() throws Exception {
		List<EpisodeTrakt> showEpisodes = provider.getShowEpisodes(Constants.STAR_TREK_TNG_SLUG);
		assertThat(showEpisodes, notNullValue());
		assertThat(showEpisodes, not(empty()));
		showEpisodes.forEach(episode -> {
			assertThat(episode.getTitle(), not(isEmptyOrNullString()));
			assertThat(episode.getOverview(), not(isEmptyOrNullString()));
			assertThat(episode.getNumber(), notNullValue());
			assertThat(episode.getUpdatedAt(), notNullValue());
			assertThat(episode.getFirstAired(), notNullValue());
			assertThat(episode.getSeason(), not(isEmptyOrNullString()));
			assertThat(episode.getIds(), hasKey(ID_KEY_TRAKT));
		});
	}
}
