package util;

import lombok.Data;

/**
* @Description:    自定义异常类
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 15:05
* @UpdateUser:
* @UpdateDate:     2019/8/1 15:05
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Data
public class PException extends RuntimeException{

    private Integer code;
    private String message;

    public PException(Integer code,String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
