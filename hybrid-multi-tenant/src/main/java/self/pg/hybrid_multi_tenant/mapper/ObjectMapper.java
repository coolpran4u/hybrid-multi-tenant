package self.pg.hybrid_multi_tenant.mapper;

import com.github.dozermapper.core.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapper {
    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    public <S, D> D convert(S source, Class<D> desitnationClass) {
        return dozerBeanMapper.map(source, desitnationClass);
    }


}
