package self.pg.hybrid_multi_tenant.central.service.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.pg.hybrid_multi_tenant.central.entity.partner.PartnerDetail;
import self.pg.hybrid_multi_tenant.central.repository.partner.PartnerDetailRepository;
import self.pg.hybrid_multi_tenant.central.vo.partner.PartnerVO;
import self.pg.hybrid_multi_tenant.config.MultiTenantDataSourceConfiguration;
import self.pg.hybrid_multi_tenant.mapper.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PartnerDetailRepository partnerDetailRepository;
    @Autowired
    MultiTenantDataSourceConfiguration multiTenantDataSourceConfiguration;

    @Override
    @Transactional("centralTransactionManager")
    public PartnerVO registerPartner(PartnerVO partner) {
        PartnerDetail partnerDetail = mapper.convert(partner, PartnerDetail.class);
        partnerDetail.setPartnerID("PTNR-"+ UUID.randomUUID());
        partnerDetail.setTenantID(UUID.randomUUID().toString());
        var savedPartnerDetail = partnerDetailRepository.save(partnerDetail);
        var partnerJdbcUrl = String.format("jdbc:mysql://%s:%d/%s?createDatabaseIfNotExist=true",partnerDetail.getDbHost(), partnerDetail.getDbPort(), partnerDetail.getDatabaseName());
        var datasource = multiTenantDataSourceConfiguration.createDataSource(partnerJdbcUrl, partnerDetail.getDbUsername(), partnerDetail.getDbPassword());
        multiTenantDataSourceConfiguration.addTenantDataSource(partnerDetail.getTenantID(), datasource);
        return mapper.convert(savedPartnerDetail, PartnerVO.class);
    }

    @Override
    @Transactional("centralTransactionManager")
    public List<PartnerVO> getPartners() {
        var partnerDetailList = partnerDetailRepository.findAll();
        List<PartnerVO> partners = new ArrayList<>();
        partnerDetailList.forEach(partner -> {
            partners.add(mapper.convert(partner, PartnerVO.class));
        });
        return partners;
    }

    @Override
    @Transactional("centralTransactionManager")
    public PartnerVO getPartner(int id) {
        var partner = partnerDetailRepository.findById(id);
        return mapper.convert(partner, PartnerVO.class);
    }


}
