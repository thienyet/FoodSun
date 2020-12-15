package luabui.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "admin", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Admin extends GeneralDetails{

}
