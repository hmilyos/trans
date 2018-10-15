package com.imooc.example.axon.account.query;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mavlarn on 2018/5/22.
 */
public interface AccountEntityRepository extends JpaRepository<AccountEntity, String> {
}
