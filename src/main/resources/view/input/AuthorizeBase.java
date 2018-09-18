package /packageName/.web.view.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created by leosoft on 2018/8/13.
 */
@Getter
@Setter
public class AuthorizeBase {

    @NotBlank(message = "appId是必须的")
    private String  appId;

    private String  appSecurity;

    private String  timestamp;
}
