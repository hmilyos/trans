package com.imooc.example.user.feign;

import com.imooc.example.dto.TicketDTO;
import com.imooc.example.service.TicketCompositeService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by mavlarn on 2018/4/5.
 */
@FeignClient(value = "ticket", path = "/api/ticket")
public interface TicketClient extends TicketCompositeService {

    @GetMapping("/{customerId}")
    List<TicketDTO> getMyTickets(@PathVariable(name = "customerId") Long customerId);

}
