package com.imooc.example.springdtx.service;

import com.imooc.example.springdtx.dao.CustomerRepository;
import com.imooc.example.springdtx.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;

/**
 * Created by mavlarn on 2018/1/20.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    public void initCustomer() {
        Customer customer = new Customer();
        customer.setUsername("admin");
        customer.setPassword("111111");
        customer.setRole("ADMIN");
        customerRepository.save(customer);
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("imooc").password("111111").roles("USER").build());
//        manager.createUser(User.withUsername("admin").password("111111").roles("ADMIN").build());
//        return manager;
//    }

    @Bean
    public UserDetailsService userDetailsService2() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Customer customer = customerRepository.findOneByUsername(username);
                if (customer != null) {
                    return User.withUsername(customer.getUsername()).password(customer.getPassword()).roles(customer.getRole()).build();
                } else {
                    throw new UsernameNotFoundException("用户不存在 '" + username + "'");
                }
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/api/customer/**").hasRole("ADMIN")
            .and()
                .formLogin()
            .and()
                .httpBasic()
            .and()
                .csrf().disable();
    }
}
