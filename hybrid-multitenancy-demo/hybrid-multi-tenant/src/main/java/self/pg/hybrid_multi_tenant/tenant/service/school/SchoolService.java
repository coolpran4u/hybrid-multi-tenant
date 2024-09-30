package self.pg.hybrid_multi_tenant.tenant.service.school;

import self.pg.hybrid_multi_tenant.tenant.vo.school.SchoolVO;

import java.util.List;

public interface SchoolService {
    SchoolVO registerSchool(SchoolVO school);

    List<SchoolVO> getSchools();

    SchoolVO getSchool(int id);
}
