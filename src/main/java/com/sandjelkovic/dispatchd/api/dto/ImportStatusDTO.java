package com.sandjelkovic.dispatchd.api.dto;

import com.sandjelkovic.dispatchd.domain.data.entity.ImportProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ImportStatusDTO {
	private Long id;

	@URL
	@Length(max = 500)
	private String mediaUrl;

	@NotNull
	private ZonedDateTime initiationTime;

	private ZonedDateTime finishTime;

	private ImportProgressStatus status;

}
