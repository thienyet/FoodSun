package luabui.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDTO extends BaseEntityDTO{

    @NotNull(message = "Category Name cannot be null.")
    private String name;

    private String description;

    private Set<Long> restaurantIds = new HashSet<>();
}
