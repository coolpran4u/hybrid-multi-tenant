package self.pg.hybrid_multi_tenant.central.service.partner;

import org.springframework.transaction.annotation.Transactional;
import self.pg.hybrid_multi_tenant.central.vo.partner.PartnerVO;

import java.util.List;

public interface PartnerService {
    PartnerVO registerPartner(PartnerVO partner);
    List<PartnerVO> getPartners();

    PartnerVO getPartner(int ID);
}
