package self.pg.hybrid_multi_tenant.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "self.pg.hybrid_multi_tenant.central.repository",
        entityManagerFactoryRef = "centralEntityManagerFactory",
        transactionManagerRef = "centralTransactionManager")
@EnableTransactionManagement
public class CentralJpaRepositoryConfig {
    @Value("${spring.datasource.central.url}")
    private String centralDBUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class}")
    private String className;
    @Bean(name = "centralDataSource")
    public DataSource centralDataSource() {
        System.out.println("Data source bean");
        return DataSourceBuilder.create()
                .url(centralDBUrl)
                .username(dbUsername)
                .password(dbPassword)
                .driverClassName(className)
                .build();
    }

    @Value("${spring.jpa.hibernate.dialect}")
    private String hibernateDialect;

    @Bean(name = "centralEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean centralEntityManagerFactory(
            @Qualifier("centralDataSource") DataSource centralDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(centralDataSource);
        em.setPackagesToScan("self.pg.hybrid_multi_tenant.central.entity");

        // Set Hibernate properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", hibernateDialect);
//        properties.put("hibernate.hbm2ddl.auto", "update");

        em.setJpaPropertyMap(properties);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean(name = "centralTransactionManager")
    public PlatformTransactionManager centralTransactionManager(
            @Qualifier("centralEntityManagerFactory") EntityManagerFactory centralEntityManagerFactory) {
        return new JpaTransactionManager(centralEntityManagerFactory);
    }


}
