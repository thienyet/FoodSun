package luabui.application.vo.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String account;
    private String role;

    public JwtResponse(String token, String account, String role) {
        this.account = account;
        this.token = token;
        this.role = role;
    }
}
