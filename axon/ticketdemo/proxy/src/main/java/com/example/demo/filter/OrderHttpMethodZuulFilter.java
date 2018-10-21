package com.example.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;


@Component
public class OrderHttpMethodZuulFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(OrderHttpMethodZuulFilter.class);

    private static final String REQUEST_PATH = "/order";
    private static final String TARGET_SERVICE = "order-query";

    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

//    读写分离，get 请求就转发到 order-query
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        return "GET".equals(method.toUpperCase()) && requestURI.startsWith(REQUEST_PATH);
    }

    @Override
    public Object run() {
        LOG.info("Route GET order requests to order-query service.");
        RequestContext context = RequestContext.getCurrentContext();
        context.set("serviceId", TARGET_SERVICE);
        context.setRouteHost(null);
        return null;
    }
}
