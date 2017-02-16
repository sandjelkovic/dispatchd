package com.sandjelkovic.dispatchd.domain.service.impl;

import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.UserEpisodeNotificationEvent;
import com.sandjelkovic.dispatchd.domain.data.entity.UserFollowingTvShow;
import com.sandjelkovic.dispatchd.domain.data.repository.EpisodeRepository;
import com.sandjelkovic.dispatchd.domain.data.repository.UserEpisodeNotificationEventRepository;
import com.sandjelkovic.dispatchd.domain.data.repository.UserRepository;
import com.sandjelkovic.dispatchd.domain.service.UserEpisodeNotificationEventHelperService;
import com.sandjelkovic.dispatchd.helper.ChronoHelper;
import com.sandjelkovic.dispatchd.helper.DomainChronoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

@Component
public class DefaultUserEpisodeNotificationEventHelperService implements UserEpisodeNotificationEventHelperService {

	@Autowired
	private EpisodeRepository episodeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserEpisodeNotificationEventRepository notificationEventRepository;

	@Override
	public void refreshEventTimes(UserFollowingTvShow following) {
		TvShow tvShow = following.getTvShow();
		Map<Boolean, List<UserEpisodeNotificationEvent>> notifications = getEpisodesForTvShow(tvShow).stream()
				.map(episode -> notificationEventRepository.findByEpisodeAndUser(episode, following.getUser()))
				.flatMap(Collection::stream)
				.map(getUpdateTimestamp(following.getUserPickedRelativeTimeToNotify()))
				.collect(partitioningBy(event -> event.getNotifyTime().toInstant().isAfter(Instant.now())));

		notificationEventRepository.delete(notifications.get(false));
		notificationEventRepository.save(notifications.get(true));
	}

	@Override
	public void deleteNotifications(UserFollowingTvShow following) {
		List<UserEpisodeNotificationEvent> events = getEpisodesForTvShow(following.getTvShow()).stream()
				.map(episode -> notificationEventRepository.findByEpisodeAndUser(episode, following.getUser()))
				.flatMap(Collection::stream)
				.collect(toList());
		notificationEventRepository.delete(events);
	}

	@Override
	public void generateFutureNotification(UserFollowingTvShow following) {
		List<UserEpisodeNotificationEvent> events = getEpisodesForTvShow(following.getTvShow()).stream()
				.filter(episode -> episode.getAirdate() != null)
				.filter(episode -> episode.getAirdate().toInstant().isAfter(Instant.now()))
				.map(getEpisodeToNotificationEventMapper(following))
				.collect(toList());
		notificationEventRepository.save(events);
	}

	private Function<? super Episode, UserEpisodeNotificationEvent> getEpisodeToNotificationEventMapper(UserFollowingTvShow following) {
		return episode -> {
			return EpisodeToNotificationEventMapper.map(episode, following);
		};
	}

	private Function<? super UserEpisodeNotificationEvent, UserEpisodeNotificationEvent> getUpdateTimestamp(BigInteger delayBigInteger) {
		Duration delay = Duration.ofMinutes(delayBigInteger.longValue());
		return event -> {
			Instant airdate = event.getEpisode().getAirdate().toInstant();
			Instant notificationDate = airdate.plus(delay);
			event.setNotifyTime(ChronoHelper.timestampFromNullable(notificationDate));
			return event;
		};
	}

	private List<Episode> getEpisodesForTvShow(TvShow tvShow) {
		return tvShow.getEpisodes();
	}

	private static class EpisodeToNotificationEventMapper {

		public static UserEpisodeNotificationEvent map(Episode episode, UserFollowingTvShow following) {
			UserEpisodeNotificationEvent event = new UserEpisodeNotificationEvent();
			event.setEpisode(episode);
			event.setUser(following.getUser());
			event.setNotified(false);
			event.setTitle(generateNotificationTitle(following));
			event.setDescription(generateNotificationDescription(episode, following));
			event.setNotifyTime(generateNotificationNotifyTime(episode, following));
			return event;
		}

		private static String generateNotificationTitle(UserFollowingTvShow following) {
			// todo internationalise
			return "Episode from " + following.getTvShow().getTitle();
		}

		private static String generateNotificationDescription(Episode episode, UserFollowingTvShow following) {
			// todo internationalise
			return "New episode called " + episode.getTitle() + " of the " + following.getTvShow().getTitle() + " is out!";
			//return "" + episode.getTitle();
		}

		private static Timestamp generateNotificationNotifyTime(Episode episode, UserFollowingTvShow following) {
			Duration delay = DomainChronoHelper.fromUserDelayTime(following.getUserPickedRelativeTimeToNotify());
			return ChronoHelper.timestampFromNullable(episode.getAirdate().toInstant().plus(delay));
		}
	}
}
