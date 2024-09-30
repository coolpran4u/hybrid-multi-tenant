package self.pg.hybrid_multi_tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = "self.pg.hybrid_multi_tenant")
@SpringBootApplication
public class HybridMultiTenantApplication {

	public static void main(String[] args) {
		SpringApplication.run(HybridMultiTenantApplication.class, args);
	}

}
