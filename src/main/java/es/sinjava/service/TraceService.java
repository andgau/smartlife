package es.sinjava.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.sinjava.bean.TracedItem;

@RepositoryRestResource(collectionResourceRel = "traceItems", path = "traces")
public interface TraceService extends PagingAndSortingRepository<TracedItem, Long> {


}
