package self.pg.hybrid_multi_tenant.central.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TenantDataSourceService {




    private final Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>();

    public Map<Object, Object> getDataSourceForTenant(String tenantId) {

        if (tenantDataSources.containsKey(tenantId)) {
            return tenantDataSources;
        }

//        if("self".equals(tenantId)) {
//            tenantDataSources.put(tenantId, createSelfDataSource());
//            return tenantDataSources;
//        }


//        Object tenantDetail = tenantDetailRepository.findByTenantId(tenantId);
//        if (tenantDetail == null) {
//            throw new RuntimeException("Tenant configuration not found for tenant " + tenantId);
//        }
//        DataSource dataSource = createTenantDataSource(tenantDetail);
//        tenantDataSources.put(tenantId, dataSource);
        return tenantDataSources;
    }

//    private DataSource createTenantDataSource(TenantDetail tenantConfig) {
//        return DataSourceBuilder.create()
//                .url(tenantConfig.getDbUrl())
//                .username(tenantConfig.getDbUsername())
//                .password(tenantConfig.getDbPassword())
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//    }


}

