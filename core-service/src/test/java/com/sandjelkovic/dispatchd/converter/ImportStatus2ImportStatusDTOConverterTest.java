package com.sandjelkovic.dispatchd.converter;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.domain.data.entity.ImportProgressStatus;
import com.sandjelkovic.dispatchd.domain.data.entity.ImportStatus;
import com.sandjelkovic.dispatchd.gateway.api.dto.ImportStatusDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {DispatchdApplication.class})
@Transactional
public class ImportStatus2ImportStatusDTOConverterTest {

	@Autowired
	private ImportStatus2ImportStatusDtoConverter importStatus2ImportStatusDtoConverter;

	@Test
	public void convertTest() {
		ZonedDateTime finishTime = ZonedDateTime.now().minusMinutes(1);
		ZonedDateTime initiationTime = ZonedDateTime.now().minusMinutes(5);
		ImportStatus status = new ImportStatus()
				.id(5L)
				.finishTime(finishTime)
				.initiationTime(initiationTime)
				.mediaUrl("https://www.example.com/")
				.status(ImportProgressStatus.SUCCESS);
		ImportStatusDTO convertedDto = importStatus2ImportStatusDtoConverter.convert(status);
		assertThat(convertedDto, notNullValue());
		assertThat(convertedDto.getId(), is(status.getId()));
		assertThat(convertedDto.getMediaUrl(), is(status.getMediaUrl()));
		assertThat(convertedDto.getStatus(), is(status.getStatus()));
		assertThat(convertedDto.getFinishTime(), is(status.getFinishTime()));
		assertThat(convertedDto.getInitiationTime(), is(status.getInitiationTime()));
	}

}
