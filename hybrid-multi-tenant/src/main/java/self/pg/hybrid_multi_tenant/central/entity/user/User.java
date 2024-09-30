package self.pg.hybrid_multi_tenant.central.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "USER_ID", nullable = false)
    private String userId;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PARTNER_ID")
    private int partnerId;
}
