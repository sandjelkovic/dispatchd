package com.sandjelkovic.dispatchd.helpers;

import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportRepeatType;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.Season;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow.Status;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.helper.ChronoHelper;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ${sandjelkovic}
 * @date 2.1.17.
 */
public class TestDataGenerator {

	public ReportTemplate getTemplateWithIdForUser(Long id, User user) {
		return new ReportTemplate()
				.id(id)
				.user(user)
				.active(true)
				.description("[Description] for the test template")
				.name("Test report template [name]")
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatDayOfMonth(2)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.NOON);
	}

	public ReportTemplateDTO getTemplateDTOWithoutId() {
		return new ReportTemplateDTO()
				.active(true)
				.description("[Description] for the test template")
				.name("Test report template [name]")
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatDayOfMonth(2)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.NOON);
	}

	public User createUser(String username, String password) {
		return new User()
				.username(username)
				.approved(true)
				.enabled(true)
				.passw(password)
				.email(username + "@example.com")
				.authorities(Stream.of(Constants.DEFAULT_USER_ROLES)
						.collect(Collectors.toSet()));
	}

	public TvShow createStarTrekTNG() {
		TvShow tng = new TvShow();
		tng.setTitle("Star Trek TNG");
		tng.setYear(1988);
		tng.setStatus(Status.FINISHED.toString());
		tng.setLastLocalUpdate(ChronoHelper.timestampFromNullable(Instant.now().minus(Duration.ofDays(10))));
		tng.setDescription("Star Trek with Picard.");
		tng.setTraktId("653");
		tng.setImdbId("tt0092455");
		tng.setTvdbId("71470");
		Season s1 = createSeason(tng, "1", 26, "New generation is unbound in this new Star Trek Show.", "2204", "2298", Timestamp.valueOf("1987-09-29 01:00:00"));
		Season s2 = createSeason(tng, "2", 22, "", "2205", "2290", Timestamp.valueOf("1988-11-22 02:00:00"));
		tng.getSeasons().add(s1);
		tng.getSeasons().add(s2);
		return tng;
	}

	public TvShow createAgentsOfShield() {
		TvShow aos = new TvShow();
		aos.setTitle("Marvel's Agents of S.H.I.E.L.D.");
		aos.setYear(2013);
		aos.setStatus(Status.ONGOING.toString());
		aos.setLastLocalUpdate(ChronoHelper.timestampFromNullable(Instant.now().minus(Duration.ofDays(10))));
		aos.setDescription("The missions of the Strategic Homeland Intervention Enforcement and Logistics Division.");
		aos.setTraktId("1394");
		aos.setImdbId("tt2364582");
		aos.setTvdbId("263365");
		Season s1 = createSeason(aos, "1", 22, "Season one Shield", "3990", "503426", Timestamp.valueOf("2013-09-25 02:00:00"));
		Season s2 = createSeason(aos, "2", 22, "Season two Shield", "3991", "591138", Timestamp.valueOf("2014-09-24 02:00:00"));
		aos.getSeasons().add(s1);
		aos.getSeasons().add(s2);
		return aos;
	}

	public Season createSeason(TvShow show, String number, int episodesCount, String description, String traktId, String tvdbId, Timestamp airdate) {
		Season s1 = new Season();
		s1.setTvShow(show);
		s1.setNumber(number);
		s1.setEpisodesCount(episodesCount);
		s1.setDescription(description);
		s1.setTraktId(traktId);
		s1.setTvdbId(tvdbId);
		s1.setEpisodesAiredCount(episodesCount);
		s1.setAirdate(airdate);
		return s1;
	}
}
