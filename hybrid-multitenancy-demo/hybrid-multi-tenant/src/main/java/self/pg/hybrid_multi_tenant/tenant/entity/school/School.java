package self.pg.hybrid_multi_tenant.tenant.entity.school;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SCHOOLS")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHOOL_ID")
    private String schoolId;
    @Column(name = "SCHOOL_NAME")
    private String name;
    @Column(name = "SCHOOL_TYPE")
    private String type;
}