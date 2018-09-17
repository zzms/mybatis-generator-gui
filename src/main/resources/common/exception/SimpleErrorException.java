package /packageName/.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by leosoft on 2018/8/3.
 */
public class SimpleErrorException extends  RuntimeException {

    @Getter@Setter
    private ErrorCode errorCode;

    public SimpleErrorException(String message){
        super(message);
    }

    public SimpleErrorException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
}
