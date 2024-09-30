package self.pg.hybrid_multi_tenant.central.vo.partner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartnerVO {
    private int id;
    private String name;
    private String tenantID;
    private String partnerID;
    private String dbHost;
    private int dbPort;
    private String dbUsername;
    private String dbPassword;
    private String databaseName;
}
