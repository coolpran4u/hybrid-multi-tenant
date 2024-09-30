package self.pg.hybrid_multi_tenant.tenant.service.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.pg.hybrid_multi_tenant.mapper.ObjectMapper;
import self.pg.hybrid_multi_tenant.tenant.entity.school.School;
import self.pg.hybrid_multi_tenant.tenant.repository.school.SchoolRepository;
import self.pg.hybrid_multi_tenant.tenant.vo.school.SchoolVO;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SchoolRepository schoolRepository;
    @Override
    @Transactional("tenantTransactionManager")
    public SchoolVO registerSchool(SchoolVO school) {
        var schoolInfo = mapper.convert(school, School.class);
        var savedSchool = schoolRepository.save(schoolInfo);
        return mapper.convert(savedSchool, SchoolVO.class);
    }

    @Override
    @Transactional("tenantTransactionManager")
    public List<SchoolVO> getSchools() {
        var schoolList = schoolRepository.findAll();
        List<SchoolVO> schools = new ArrayList<>();
        schoolList.forEach(school -> {
            schools.add(mapper.convert(school, SchoolVO.class));
        });
        return schools;
    }

    @Override
    @Transactional("tenantTransactionManager")
    public SchoolVO getSchool(int id) {
        return mapper.convert(schoolRepository.findById(id), SchoolVO.class);
    }
}
