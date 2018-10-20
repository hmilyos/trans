package com.hmily.transaxon.springaxonbasic.dao;

import com.hmily.transaxon.springaxonbasic.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, String> {

}
