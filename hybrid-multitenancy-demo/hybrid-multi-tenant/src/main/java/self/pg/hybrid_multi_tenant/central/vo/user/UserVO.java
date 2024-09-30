package self.pg.hybrid_multi_tenant.central.vo.user;

import com.github.dozermapper.core.Mapping;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserVO {
    private String userId;
    @Mapping("emailAddress")
    private String email;
    private String partnerId;
    private String name;
}
