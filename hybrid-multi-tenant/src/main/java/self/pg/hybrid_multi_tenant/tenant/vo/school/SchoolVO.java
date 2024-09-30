package self.pg.hybrid_multi_tenant.tenant.vo.school;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchoolVO {
    @Mapping("schoolId")
    private String id;
    private String name;
    private String type;
}
