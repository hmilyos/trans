package com.imooc.example.user.feign;

import com.imooc.example.dto.OrderDTO;
import com.imooc.example.service.OrderCompositeService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by mavlarn on 2018/2/14.
 */
@FeignClient(value = "order", path = "/api/order")
public interface OrderClient extends OrderCompositeService {

    @GetMapping("/{customerId}")
    List<OrderDTO> getMyOrder(@PathVariable(name = "customerId") Long id);
}
