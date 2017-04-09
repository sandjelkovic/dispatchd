package com.sandjelkovic.dispatchd.domain.data.repository;

import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate2TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate2TvShowPK;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author sandjelkovic
 * @date 23.3.17.
 */
public interface ReportTemplate2TvShowRelationRepository extends PagingAndSortingRepository<ReportTemplate2TvShow, ReportTemplate2TvShowPK> {
}
