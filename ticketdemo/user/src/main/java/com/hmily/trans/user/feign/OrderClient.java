package com.hmily.trans.user.feign;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.common.service.IOrderCompositeService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "order", path = "/api/order")
public interface OrderClient extends IOrderCompositeService {

    @GetMapping("/{customerId}")
    List<OrderDTO> getMyOrder(@PathVariable(name = "customerId") Long id);

}

