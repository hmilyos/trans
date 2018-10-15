package com.imooc.example.axon.order.query;

import com.imooc.example.axon.order.Order;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mavlarn on 2018/5/29.
 */
@Component
public class OrderQueryHandler {


    @Autowired
    AxonConfiguration axonConfiguration;

    @QueryHandler
    public Order query(OrderId orderId) {
        // WARN: 强烈不建议使用这种方式将聚合数据暴露给外界，而应该使用物化视图的方式将保存的视图数据显示出来。
        // 这里这样做，只是用于debug，有时候，可能写的代码有问题，导致聚合数据跟视图数据不一致。
        final Order[] theOrder = new Order[1];
        Repository<Order> orderRepository = axonConfiguration.repository(Order.class);
        orderRepository.load(orderId.toString()).execute(order -> {
            theOrder[0] = order;
        });

        return theOrder[0];
    }
}
