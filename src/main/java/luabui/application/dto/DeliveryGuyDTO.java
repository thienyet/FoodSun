package luabui.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryGuyDTO extends GeneralDetailsDTO {

    private Boolean isBusy;
    Set<Long> orderIds = new HashSet<>();
}
