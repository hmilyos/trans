package com.hmily.trans.springdtxjpadb.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {



    @Bean(name = "userDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.ds_user")
    public DataSource userDataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.ds_order")
    public DataSource orderDataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean
    public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource")DataSource orderDataSource){
        return new JdbcTemplate(orderDataSource);
    }

//    ---------------配置链式事务-----------------------
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false); //数据库创建好表了就不要置为 true

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan("com.hmily.trans.springdtxjpadb");
        factoryBean.setDataSource(userDataSource());
        return factoryBean;
    }
    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager userTM = new JpaTransactionManager();
        userTM.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        DataSourceTransactionManager orderTM = new DataSourceTransactionManager(orderDataSource());
        ChainedTransactionManager tm = new ChainedTransactionManager(userTM, orderTM);
        return tm;
    }
    //    ---------------配置链式事务--------end---------------
}
