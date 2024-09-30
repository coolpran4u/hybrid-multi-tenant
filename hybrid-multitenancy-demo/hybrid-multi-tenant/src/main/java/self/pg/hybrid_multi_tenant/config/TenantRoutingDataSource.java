package self.pg.hybrid_multi_tenant.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import self.pg.hybrid_multi_tenant.central.service.TenantDataSourceService;

import javax.sql.DataSource;
import java.util.Map;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    @Autowired
    private TenantDataSourceService tenantDataSourceService;

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenantId();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        String tenantId = (String) determineCurrentLookupKey();
        if(StringUtils.isBlank(tenantId)) {
            tenantId = "self";
        }
        Map<Object, Object> dataSourceMap = tenantDataSourceService.getDataSourceForTenant(tenantId);
        var dataSource = dataSourceMap.get(tenantId);
        if (dataSource == null) {
            throw new RuntimeException("No data source found for tenant " + tenantId);
        }
        this.setTargetDataSources(dataSourceMap);
        return (DataSource) dataSourceMap.get(tenantId);
    }
}

