package com.sandjelkovic.dispatchd.domain.facade;

import com.sandjelkovic.dispatchd.api.dto.ImportStatusDto;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

@Service
public interface ImporterFacade {
	ImportStatusDto importFromUriComponents(UriComponents uriComponents);

	ImportStatusDto getImportStatus(Long id);
}
