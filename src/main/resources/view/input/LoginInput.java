package /packageName/.web.view.input;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Created by leosoft on 2018/8/3.
 */
@Getter
@Setter
public class LoginInput {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Length(message = "密码长度至少为6",min = 6)
    private String password;

    public LoginInput() {
        System.out.println(this.userName);
    }
}
