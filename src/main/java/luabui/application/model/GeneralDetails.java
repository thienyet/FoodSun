package luabui.application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class GeneralDetails extends BaseEntity{
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 45)
    private String phoneNo;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "create_date")
    private Date createDate;
}
