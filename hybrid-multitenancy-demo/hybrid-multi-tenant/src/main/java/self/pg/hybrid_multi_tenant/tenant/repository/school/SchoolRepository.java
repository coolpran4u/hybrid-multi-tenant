package self.pg.hybrid_multi_tenant.tenant.repository.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import self.pg.hybrid_multi_tenant.tenant.entity.school.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
}
