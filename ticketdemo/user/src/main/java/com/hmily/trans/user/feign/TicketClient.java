package com.hmily.trans.user.feign;

import com.hmily.trans.common.dto.TicketDTO;
import com.hmily.trans.common.service.ITicketCompositeService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "ticket", path = "/api/ticket")
public interface TicketClient extends ITicketCompositeService {

    @GetMapping("/{customerId}")
    List<TicketDTO> getMyTickets(@PathVariable(name = "customerId") Long customerId);

}
