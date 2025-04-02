package com.roomx.infrastructure.persistence.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableQueryService<F, R> {
    public Page<R> filterPageBranchs(F filter, Pageable pageable, boolean typeCompare);

}
