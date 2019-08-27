package manager.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Description:    满足关键字搜索:名称、工号、身份证号、电话
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/27 11:17
* @UpdateUser:
* @UpdateDate:     2019/8/27 11:17
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {
    private String author;
    private String keyword;
}
