package com.antisocial.system;

import java.beans.PropertyVetoException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value= {"classpath:resources/application.properties"})
public class HibernateConfiguration {

    /**
     * Hibernate Configuration
     *
     * Configures:
     * C3P0 Pooling
     * MySQL Database connection
     * Hibernate Transaction Manager
     *
     * Uses:
     * application.properties
     *
     *  @author Ant Kaynak - Github/Exercon
     * */

    @Value("${db.driver}")
    private String DB_DRIVER;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Value("${db.url}")
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${hibernate.format_sql}")
    private String HIBERNATE_FORMAT_SQL;

    @Value("${hibernate.cache.use_query_cache}")
    private String HIBERNATE_USE_QUERY_CACHE;

    @Value("${hibernate.cache.use_second_level_cache}")
    private String HIBERNATE_USER_SECOND_LEVEL_CACHE;

    @Value("${hibernate.cache.region.factory_class}")
    private String HIBERNATE_REGION_FACTORY;

    @Value("${hibernate.acquireIncrement}")
    private String ACQUIRE_INCREMENT;

    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Value("${hibernate.c3p0.max_size}")
    private String CONN_POOL_MAX_SIZE;

    @Value("${hibernate.c3p0.min_size}")
    private String CONN_POOL_MIN_SIZE;

    @Value("${hibernate.c3p0.idle_test_period}")
    private String CONN_POOL_IDLE_PERIOD;


    @Bean
    public ComboPooledDataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(DB_DRIVER);
        } catch (PropertyVetoException pve){
            System.out.println("Cannot load datasource driver (" + DB_DRIVER +") : " + pve.getMessage());
            return null;
        }
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUser(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setMinPoolSize(Integer.parseInt(CONN_POOL_MIN_SIZE));
        dataSource.setMaxPoolSize(Integer.parseInt(CONN_POOL_MAX_SIZE));
        dataSource.setMaxIdleTime(Integer.parseInt(CONN_POOL_IDLE_PERIOD));
        dataSource.setAcquireIncrement(Integer.parseInt(ACQUIRE_INCREMENT));

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());

        return sessionFactoryBean;
    }


    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", HIBERNATE_DIALECT);
        properties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        properties.put("hibernate.format_sql", HIBERNATE_FORMAT_SQL);
        properties.put("hibernate.cache.use_second_level_cache", HIBERNATE_USER_SECOND_LEVEL_CACHE);
        properties.put("hibernate.cache.region.factory_class", HIBERNATE_REGION_FACTORY);
        properties.put("hibernate.cache.use_query_cache", HIBERNATE_USE_QUERY_CACHE);
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
       HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
       return hibernateTransactionManager;
    }
}