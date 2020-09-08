package org.debugroom.sample.aws.xray.backend.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "org.debugroom.sample.aws.xray.backend.service.domain.repository.jpa"
)
@Configuration
public class JpaConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception{
        return new JpaTransactionManager();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setPackagesToScan(
                "org.debugroom.sample.aws.xray.backend.service.domain.model.jpa");
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(adapter);
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);

        return localContainerEntityManagerFactoryBean;

    }
}
