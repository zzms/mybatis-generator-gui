package /packageName/.common.exception;

/**
 * Created by leosoft on 2018/8/3.
 */
public enum  ErrorCode {

    SUCCESS("200","OK")
    ,ERROR_SIMPLE("1000","请求错误")
    ,ERROR_RESOURCE_NONEXISTENT("1001","不存在的资源或请求方法不正确")
    ,ERROR_JSON_SERIALIZE("1002","json序列化异常")
    ,ERROR_MEDIA_TYPE_NOT_SUPPORTED("1003","不支持的参数格式")


    ,ERROR_SYSTEM_ARG("1100","系统级参数异常")
    ,ERROR_SYSTEM_CONSUMER_NONEXISTENT("1110","不存在的非法用户")
    ,ERROR_SYSTEM_INTERFACE("1140","没有权限访问接口")


    /**
     * todo 根据模块编写错误
     */

    //用户模块1200+
    ,ERROR_LOGIN("1200","用户名或密码错误")









    ;
    private String code;
    private String message;
    ErrorCode(String code,String message){
        this.code = code;
        this.message = message;
    }
    ErrorCode(String message){
        this.code = null;
        this.message = message;
    }
    public String getCode(){
        return this.code;
    }
    public String getMessage(){
        return this.message;
    }


}
