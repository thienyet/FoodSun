package luabui.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantDTO extends GeneralDetailsDTO {
    private Set<Long> foodItemIds = new HashSet<>();
    private Set<Long> orderIds = new HashSet<>();
}
