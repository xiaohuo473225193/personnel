package util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaohuo
 * @data 2019/7/15 12:27
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private Boolean flag;
    private T data;

    public Result(T data) {
        this.code = Code.success;
        this.flag = true;
        this.data = data;
    }
}
