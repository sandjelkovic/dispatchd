package com.sandjelkovic.dispatchd.domain.facade;

import com.sandjelkovic.dispatchd.api.dto.ImportStatusDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

@Service
public interface ImporterFacade {
	ImportStatusDTO importFromUriComponents(UriComponents uriComponents);

	ImportStatusDTO getImportStatus(Long id);
}
