package com.sandjelkovic.dispatchd.daemon;

import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider;
import com.sandjelkovic.dispatchd.domain.facade.EpisodeNotificationFacade;
import com.sandjelkovic.dispatchd.domain.facade.ImporterFacade;
import com.sandjelkovic.dispatchd.gateway.api.dto.EpisodeNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;

@Component
public class TestingDaemon {
	private static final Logger log = LoggerFactory.getLogger(TestingDaemon.class);

	private Set<EpisodeNotificationDTO> notifications = Collections.synchronizedSet(Collections.emptySet());

	@Autowired
	private TraktMediaProvider traktProvider;

	@Autowired
	private EpisodeNotificationFacade episodeNotificationFacade;

	@Autowired
	private ImporterFacade importer;

	@Value(value = "${trakt.appId}")
	private String appId;

	@Value(value = "${trakt.appSecret}")
	private String appSecret;


/*	@Scheduled(fixedDelay = 2000)
	public void refreshExistingTVShowsList() {
		log.warn(Instant.now() + appId + "\t\t" + appSecret);
	}*/

//	@Async
//	@Scheduled(cron = "0 0 * * * ?")
//	public void loadNotifications(){
//	//	List<EpisodeNotificationDTO> notificationList = episodeNotificationFacade.findUnreadNotifications();
//
//	}

//	@Scheduled(fixedDelay = 600)
//	public void dispatchReadyNotifications() {
//
//	}

	//	@Scheduled(fixedDelay = 5000)
	public void testTvShow() {
//		TvShowTrakt show = traktProvider.getTvShow("1394");
//		log.warn(show.toString());
//		SeasonTrakt season = traktProvider.getSeason("1394", "1");
//		log.warn(season.toString());

		Instant before = Instant.now();
//		List<EpisodeTrakt> list = traktProvider.getShowEpisodes("1394");
		doSomething();
		Instant after = Instant.now();
		//list.stream().forEach(System.out::println);
		System.out.println("Completed the operation in: " + Duration.between(before, after).toString());

	}

	private void doSomething() {
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("https://trakt.tv/shows/marvel-s-agents-of-s-h-i-e-l-d/seasons/3/episodes/15").build().encode();
		importer.importFromUriComponents(uriComponents);
	}

}
