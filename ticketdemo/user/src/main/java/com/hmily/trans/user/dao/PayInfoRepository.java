package com.hmily.trans.user.dao;

import com.hmily.trans.user.domain.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayInfoRepository extends JpaRepository<PayInfo, Long> {

    PayInfo findOneByOrderId(Long orderId);
}
