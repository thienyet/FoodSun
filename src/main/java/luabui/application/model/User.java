package luabui.application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends BaseEntity {
    @Column(name = "email", nullable = false, length = 255)
    String email;
    String password;
    Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

}
