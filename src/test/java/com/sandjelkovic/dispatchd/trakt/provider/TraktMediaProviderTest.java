package com.sandjelkovic.dispatchd.trakt.provider;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.trakt.dto.ShowUpdateTrakt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * @author ${sandjelkovic}
 * @date 5.2.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(classes = {DispatchdApplication.class})
public class TraktMediaProviderTest {
	@Autowired
	private TraktMediaProvider provider;

	@Test
	public void getUpdates() throws Exception {
		List<ShowUpdateTrakt> updates = provider.getUpdates(LocalDate.now().minusDays(7));
		assertThat(updates.size(), greaterThanOrEqualTo(2));
	}

}
