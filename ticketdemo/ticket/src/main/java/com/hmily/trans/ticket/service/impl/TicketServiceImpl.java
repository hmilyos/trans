package com.hmily.trans.ticket.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.common.dto.TicketDTO;
import com.hmily.trans.ticket.dao.TicketRepository;
import com.hmily.trans.ticket.domain.Ticket;
import com.hmily.trans.ticket.service.ITicketService;

@Service

public class TicketServiceImpl implements ITicketService {

    private static final Logger LOG = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Autowired
    private TicketRepository ticketRepository;
    
//    生成新订单
    @Transactional
    @JmsListener(destination = "order:new", containerFactory = "msgFactory")
    public void handleTicketLock(OrderDTO msg) {
    	LOG.info("Get new order for ticket lock:{}", msg);
//    	把锁写到数据行里面
    	int lockCount = ticketRepository.lockTicket(msg.getCustomerId(), msg.getTicketNum());
    	 if (lockCount == 0) {
//    		 获取不到锁，下单失败
    		 msg.setStatus("TICKET_LOCK_FAIL");
             jmsTemplate.convertAndSend("order:fail", msg);
    	 } else {
//    		 能获取到锁，生成订单成功
    		 msg.setStatus("TICKET_LOCKED");
             jmsTemplate.convertAndSend("order:locked", msg);
		}
    }

//    解锁并把票的所有者信息写进去，锁票人和票的所有者一致
    @Transactional
    @JmsListener(destination = "order:ticket_move", containerFactory = "msgFactory")
    public void handleTicketMove(OrderDTO msg) {
        LOG.info("Get new order for ticket move:{}", msg);
        int moveCount = ticketRepository.moveTicket(msg.getCustomerId(), msg.getTicketNum());
        if (moveCount == 0) {
            LOG.info("Ticket already transferred.");
        }
        msg.setStatus("TICKET_MOVED");
        jmsTemplate.convertAndSend("order:finish", msg);
    }
    
    /**
     * 触发 error_ticket 的情况：
     *  1. 扣费失败，需要解锁票
     *  2. 订单超时，如果存在锁票就解锁，如果已经交票就撤回
     *  这时候，都已经在OrderDTO里设置了失败的原因，所以这里就不再设置原因。
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "order:ticket_error", containerFactory = "msgFactory")
    public void handleError(OrderDTO msg) {
        LOG.info("Get order error for ticket unlock:{}", msg);
        int count = ticketRepository.unMoveTicket(msg.getCustomerId(), msg.getTicketNum()); // 撤销票的转移
        if (count == 0) {
            LOG.info("Ticket already unlocked:", msg);
        }
        count = ticketRepository.unLockTicket(msg.getCustomerId(), msg.getTicketNum()); // 撤销锁票
        if (count == 0) {
            LOG.info("Ticket already unmoved, or not moved:", msg);
        }
        jmsTemplate.convertAndSend("order:fail", msg);
    }
    
    @Override
    public void init() {
        if (ticketRepository.count() > 0){
            return;
        }
        Ticket ticket = new Ticket();
        ticket.setName("No.1");
        ticket.setTicketNum(110L);
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket lockTicket(OrderDTO orderDTO) {
        Ticket ticket = ticketRepository.findOneByTicketNum(orderDTO.getTicketNum());
        ticket.setLockUser(orderDTO.getCustomerId());
        ticket = ticketRepository.save(ticket);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return ticket;
    }

    @Override
    public int lockTicket2(OrderDTO orderDTO) {
        int updateCount = ticketRepository.lockTicket(orderDTO.getCustomerId(), orderDTO.getTicketNum());
        LOG.info("Updated ticket count:{}", updateCount);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return updateCount;
    }


    @Override
    public List<TicketDTO> getMyTickets(Long customerId) {

        List<Ticket> tickets = ticketRepository.findAllByOwner(customerId);
        return tickets.stream().map(
               ticket -> {
                   TicketDTO ticketDTO = new TicketDTO();
                   ticketDTO.setTicketNum(ticket.getTicketNum());
                   ticketDTO.setId(ticket.getId());
                   ticketDTO.setLockUser(ticket.getLockUser());
                   ticketDTO.setName(ticket.getName());
                   ticketDTO.setOwner(ticket.getOwner());
                   return ticketDTO;
               }
        ).collect(Collectors.toList());
    }


}
