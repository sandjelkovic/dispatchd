package com.sandjelkovic.dispatchd.facade;

import com.sandjelkovic.dispatchd.data.dto.ImportStatusDto;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

@Service
public interface ImporterFacade {
	ImportStatusDto importFromUriComponents(UriComponents uriComponents);

	ImportStatusDto getImportStatus(Long id);
}
