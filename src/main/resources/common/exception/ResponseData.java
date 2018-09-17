package /packageName/.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by leosoft on 2018/8/3.
 */
@Getter
@Setter
public class ResponseData<T extends Object> {

     private T data;

     private String message;

     private String code;


    public ResponseData(/packageName/.common.exception.ErrorCode errorCode , T t) {
         this.code=errorCode.getCode();
         this.message=errorCode.getMessage();
         data=t;
    }

    public ResponseData(/packageName/.common.exception.ErrorCode errorCode) {
        this.code=errorCode.getCode();
        this.message=errorCode.getMessage();
        data=null;
    }

    public ResponseData(String  message) {
        this.code=null;
        this.message=message;
        data=null;
    }

    public ResponseData() {

    }


}
