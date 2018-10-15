package com.imooc.example.user.dao;

import com.imooc.example.user.domain.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mavlarn on 2018/1/20.
 */
public interface PayInfoRepository extends JpaRepository<PayInfo, Long> {

    PayInfo findOneByOrderId(Long orderId);
}
