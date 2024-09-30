package self.pg.hybrid_multi_tenant.central.repository.partner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import self.pg.hybrid_multi_tenant.central.entity.partner.PartnerDetail;

@Repository
public interface PartnerDetailRepository extends JpaRepository<PartnerDetail, Integer> {
    PartnerDetail findByTenantID(String tenantId);
}
