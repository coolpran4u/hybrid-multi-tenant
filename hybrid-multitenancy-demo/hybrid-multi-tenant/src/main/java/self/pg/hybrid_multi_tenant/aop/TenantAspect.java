package self.pg.hybrid_multi_tenant.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import self.pg.hybrid_multi_tenant.config.TenantContext;

import java.util.Objects;

@Aspect
@Component
public class TenantAspect {

    @Before("execution(* self.pg.hybrid_multi_tenant.tenant.controller..*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void setTenantContext(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String tenantId = request.getHeader("X-Tenant-ID");
        TenantContext.setTenantId(tenantId);
    }

    @After("execution(* self.pg.hybrid_multi_tenant.tenant.controller..*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void clearTenantContext() {
        TenantContext.clear();
    }
}


