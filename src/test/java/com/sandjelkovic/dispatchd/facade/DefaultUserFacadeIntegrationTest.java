package com.sandjelkovic.dispatchd.facade;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.dto.TvShowDto;
import com.sandjelkovic.dispatchd.data.dto.UserDto;
import com.sandjelkovic.dispatchd.data.entities.Season;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.entities.UserFollowingTvShow;
import com.sandjelkovic.dispatchd.data.repositories.*;
import com.sandjelkovic.dispatchd.exception.UserDoesntFollowTvShowException;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import com.sandjelkovic.dispatchd.helper.ChronoHelper;
import com.sandjelkovic.dispatchd.service.UserService;
import com.sandjelkovic.dispatchd.trakt.importer.exception.ShowAlreadyExistsImporterException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {DispatchdApplication.class})
public class DefaultUserFacadeIntegrationTest extends BaseIntegrationTest {

	public static final String INTEGRATION_TEST_USER_USERNAME = "integrationTestUser";
	public static final String INTEGRATION_TEST_USER_EMAIL = "integration@saanx.com";
	public static final String INTEGRATION_TEST_USER_PASSWORD = "testPassword";
	public static final boolean INTEGRATION_TEST_USER_APPROVED = true;
	public static final boolean INTEGRATION_TEST_USER_ENABLED = true;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	private TvShowRepository tvShowRepository;

	@Autowired
	private SeasonRepository seasonRepository;

	@Autowired
	private EpisodeRepository episodeRepository;

	@Autowired
	private UserFollowingTvShowRepository followingRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ImporterFacade importer;

	@Autowired
	private UserFacade facade;

	private User testUser;
	private TvShow starTrekTNG;
	private UserDto userDto;
	private TvShowDto tvShowDto;

	@Before
	public void setUp() throws Exception {
		testUser = new User();
		testUser.setUsername(INTEGRATION_TEST_USER_USERNAME);
		testUser.setApproved(INTEGRATION_TEST_USER_APPROVED);
		testUser.setEnabled(INTEGRATION_TEST_USER_ENABLED);
		testUser.setEmail(INTEGRATION_TEST_USER_EMAIL);
		testUser.setAuthorities(Stream.of(Constants.DEFAULT_USER_ROLES).collect(Collectors.toSet()));
		testUser.setPassw(INTEGRATION_TEST_USER_PASSWORD);
		testUser = userRepository.save(testUser);

		setupTestShows();
		userDto = getUserDtoFromTestUser();
		tvShowDto = getTvShowDtoFromTestTvShow();
	}

	private void setupTestShows() {
		setupStarTrekTNG();
		importShield();
	}

	private void importShield() {
		UriComponents uri = UriComponentsBuilder.fromHttpUrl("https://trakt.tv/shows/marvel-s-agents-of-s-h-i-e-l-d").build().encode();
		try{
			importer.importFromUriComponents(uri);
		} catch (ShowAlreadyExistsImporterException e) {}
	}

	private void setupStarTrekTNG() {
		starTrekTNG = new TvShow();
		starTrekTNG.setTitle("Star Trek TNG");
		starTrekTNG.setYear(1988);
		starTrekTNG.setStatus(TvShow.Status.FINISHED.toString());
		starTrekTNG.setLastLocalUpdate(ChronoHelper.timestampFromNullable(Instant.now().minus(Duration.ofDays(10))));
		starTrekTNG.setDescription("Best Star Trek. Ever.");
		starTrekTNG.setTraktId("653");
		starTrekTNG.setImdbId("tt0092455");
		starTrekTNG.setTvdbId("71470");
		starTrekTNG = tvShowRepository.save(starTrekTNG);
		Season s1 = new Season();
		s1.setTvShow(starTrekTNG);
		s1.setNumber("1");
		s1.setEpisodesCount(26);
		s1.setDescription("New generation is unbound in this new Star Trek Show.");
		s1.setTraktId("2204");
		s1.setTvdbId("2298");
		s1.setEpisodesAiredCount(26);
		s1.setAirdate(Timestamp.valueOf("1987-09-29 01:00:00"));
		Season s2 = new Season();
		s2.setTvShow(starTrekTNG);
		s2.setNumber("2");
		s2.setEpisodesCount(22);
		s2.setDescription("");
		s2.setTraktId("2205");
		s2.setTvdbId("2290");
		s2.setEpisodesAiredCount(22);
		s2.setAirdate(Timestamp.valueOf("1988-11-22 02:00:00"));
		seasonRepository.save(s1);
		seasonRepository.saveAndFlush(s2);
		starTrekTNG = refreshTvShowAndInitialise(starTrekTNG.getId());

	}

	private TvShow refreshTvShowAndInitialise(Long id) {
		TvShow show = tvShowRepository.findOne(id);
		show.setSeasons(seasonRepository.findByTvShow(show));
		show.setEpisodes(episodeRepository.findByTvShow(show));
		return show;
	}

	@After
	public void tearDown() throws Exception {
		userRepository.delete(testUser);
		episodeRepository.delete(starTrekTNG.getEpisodes());
		seasonRepository.delete(starTrekTNG.getSeasons());
		starTrekTNG.setEpisodes(null);
		starTrekTNG.setSeasons(null);
		tvShowRepository.delete(starTrekTNG);
	}

	@Test
	public void testFindUser() throws Exception {
	}

	@Test
	public void testDisableUserViaUsername() throws Exception {
		facade.disableUser(testUser.getUsername());
		User user = userRepository.findOneByUsername(testUser.getUsername())
				.orElseThrow(UserNotFoundException::new);
		assertFalse(user.isEnabled());
	}

	@Test
	public void testEnableUserViaUsername() throws Exception {
		testUser.setEnabled(false);
		userRepository.save(testUser);

		facade.enableUser(testUser.getUsername());

		User user = userRepository.findOneByUsername(testUser.getUsername())
				.orElseThrow(UserNotFoundException::new);
		;
		assertTrue(user.isEnabled());
	}

	@Test
	public void testRegister() throws Exception {

	}

	@Test
	public void testFollowTvShow() throws Exception {

		facade.followTvShow(userDto, tvShowDto);

		UserFollowingTvShow followingRetrieved = followingRepository.findOneByUserAndTvShow(testUser, starTrekTNG);
		assertNotNull(followingRetrieved);
		followingRepository.delete(followingRetrieved);
	}


	@Test
	public void testUnfollowTvShow() throws Exception {
		UserFollowingTvShow following = new UserFollowingTvShow(testUser, starTrekTNG);
		followingRepository.save(following);
		UserDto userDto = getUserDtoFromTestUser();
		TvShowDto tvShowDto = getTvShowDtoFromTestTvShow();

		facade.unfollowTvShow(userDto, tvShowDto);

		UserFollowingTvShow followingRetrieved = followingRepository.findOneByUserAndTvShow(testUser, starTrekTNG);
		assertNull(followingRetrieved);
	}

	@Test
	public void testFollowTvShowWithNotificationDelayAlreadyExisting() {
		Duration delay = Duration.ofHours(16);
		followingRepository.saveAndFlush(new UserFollowingTvShow(testUser, starTrekTNG));

		facade.followTvShow(userDto, tvShowDto, delay);

		UserFollowingTvShow following = followingRepository.findOneByUserAndTvShow(testUser, starTrekTNG);
		followingRepository.delete(following);

		assertNotNull(following);
		Duration duration = convertTimeToDuration(following.getUserPickedRelativeTimeToNotify());
		assertEquals(delay, duration);
	}

	@Test
	public void testFollowTvShowWithNotificationDelayNotExisting() {
		Duration delay = Duration.ofHours(16);

		facade.followTvShow(userDto, tvShowDto, delay);

		UserFollowingTvShow following = followingRepository.findOneByUserAndTvShow(testUser, starTrekTNG);
		followingRepository.delete(following);

		assertNotNull(following);
		Duration duration = convertTimeToDuration(following.getUserPickedRelativeTimeToNotify());
		assertEquals(delay, duration);
	}

	@Test
	public void testEnableNotifications() {
		BigInteger delay = BigInteger.valueOf(40000);
		UserFollowingTvShow following = new UserFollowingTvShow(testUser, starTrekTNG);
		following.setNotify(true);
		following.setUserPickedRelativeTimeToNotify(delay);

		followingRepository.save(following);

		facade.enableNotificationsFor(userDto, tvShowDto, convertTimeToDuration(delay));

		UserFollowingTvShow retrievedFollowing = followingRepository.findOneByUserAndTvShow(testUser, starTrekTNG);
		followingRepository.delete(following);

		assertNotNull(retrievedFollowing);
		assertTrue(retrievedFollowing.getNotify());
		assertEquals(delay, retrievedFollowing.getUserPickedRelativeTimeToNotify());
	}

	@Test(expected = UserDoesntFollowTvShowException.class)
	public void testEnableNotificationsWithoutFollowing() {
		BigInteger delay = BigInteger.valueOf(40000);

		facade.enableNotificationsFor(userDto, tvShowDto, convertTimeToDuration(delay));
	}

	@Test
	public void testDisableNotifications() {
		BigInteger delay = BigInteger.valueOf(40000);
		UserFollowingTvShow following = new UserFollowingTvShow(testUser, starTrekTNG);
		following.setNotify(true);
		following.setUserPickedRelativeTimeToNotify(delay);

		followingRepository.save(following);

		facade.disableNotificationsFor(userDto, tvShowDto);

		UserFollowingTvShow retrievedFollowing = followingRepository.findOneByUserAndTvShow(testUser, starTrekTNG);
		followingRepository.delete(following);

		assertNotNull(retrievedFollowing);
		assertFalse(retrievedFollowing.getNotify());
		assertEquals(delay, retrievedFollowing.getUserPickedRelativeTimeToNotify());
	}

	@Test(expected = UserDoesntFollowTvShowException.class)
	public void testDisableNotificationsWithoutFollowing() {

		facade.disableNotificationsFor(userDto, tvShowDto);

		assertTrue(false);
	}

	private Duration convertTimeToDuration(BigInteger userPickedRelativeTimeToNotify) {
		return Duration.ofMinutes(userPickedRelativeTimeToNotify.longValueExact());
	}

	private TvShowDto getTvShowDtoFromTestTvShow() {
		TvShowDto tvShowDto = new TvShowDto();
		tvShowDto.setId(starTrekTNG.getId());
		tvShowDto.setIds(Collections.EMPTY_MAP);
		tvShowDto.setLastUpdatedAt(Instant.now());
		tvShowDto.setYear(starTrekTNG.getYear());
		return tvShowDto;
	}

	private UserDto getUserDtoFromTestUser() {
		UserDto userDto = new UserDto();
		userDto.setUsername(testUser.getUsername());
		userDto.setId(testUser.getId());
		return userDto;
	}
}