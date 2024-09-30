package self.pg.hybrid_multi_tenant.central.entity.partner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PARTNER_DETAIL")
public class PartnerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TENANT_ID")
    private String tenantID;
    @Column(name = "PARTNER_ID")
    private String partnerID;
    @Column(name = "DB_HOST")
    private String dbHost;
    @Column(name = "DB_PORT")
    private int dbPort;
    @Column(name = "DB_USER")
    private String dbUsername;
    @Column(name = "DB_PASSWORD")
    private String dbPassword;
    @Column(name = "DB_NAME")
    private String databaseName;
}
