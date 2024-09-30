package self.pg.hybrid_multi_tenant.config;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.AbstractDataSource;
import self.pg.hybrid_multi_tenant.central.repository.partner.PartnerDetailRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MultiTenantDataSourceConfiguration extends AbstractDataSource {
    private final DataSource centralDataSource;
    private final Map<String, DataSource> tenantDataSources = new ConcurrentHashMap<>();
    @Value("${spring.datasource.tenant.url}")
    private String partnerDBUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class}")
    private String className;

    @Autowired
    private PartnerDetailRepository partnerDetailRepository;

    @Autowired
    public MultiTenantDataSourceConfiguration(@Qualifier("centralDataSource") DataSource centralDataSource) {
        this.centralDataSource = centralDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        String tenantId = TenantContext.getTenantId();
        if(StringUtils.isBlank(tenantId)) {
            var selfDatasource = createSelfDataSource();
            addTenantDataSource("self", selfDatasource);
            return selfDatasource.getConnection();
        }
        DataSource dataSource = tenantDataSources.get(tenantId);
        if (dataSource == null) {
            var partnerDetail = partnerDetailRepository.findByTenantID(tenantId);
            if(partnerDetail == null) {
                throw new RuntimeException("No datasource found for tenant " + tenantId);
            }
            var partnerJdbcUrl = String.format("jdbc:mysql://%s:%d/%s?createDatabaseIfNotExist=true",partnerDetail.getDbHost(), partnerDetail.getDbPort(), partnerDetail.getDatabaseName());
            dataSource = createDataSource(partnerJdbcUrl, partnerDetail.getDbUsername(), partnerDetail.getDbPassword());
            addTenantDataSource(tenantId, dataSource);
        }
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    // Dynamically add a new tenant datasource
    public void addTenantDataSource(String tenantId, DataSource dataSource) {
        tenantDataSources.put(tenantId, dataSource);
        runLiquibaseForTenant(tenantId);
    }

    private DataSource createSelfDataSource() {
        return DataSourceBuilder.create()
                .url(partnerDBUrl)
                .username(dbUsername)
                .password(dbPassword)
                .driverClassName(className)
                .build();
    }

    public DataSource createDataSource(String partnerJdbcUrl, String partnerDbUsername, String partnerDbPassword) {
        return DataSourceBuilder.create()
                .url(partnerJdbcUrl)
                .username(partnerDbUsername)
                .password(partnerDbPassword)
                .driverClassName(className)
                .build();
    }

    private void runLiquibaseForTenant(String tenantId) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(tenantDataSources.get(tenantId));
        liquibase.setChangeLog("classpath:db/tenant/changelog/tenant-master.xml");
        try {
            liquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            throw new RuntimeException("Exception while running partner liquibase", e);
        }
    }
}

