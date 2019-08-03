package manager.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Description:    模糊查询封装对象
* @Author:         2571169797   yang meng bo
* @CreateDate:     2019/8/2 0002 下午 18:52
* @UpdateUser:
* @UpdateDate:     2019/8/2 0002 下午 18:52
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectOptionData {
    private String jobNumber;
    private String name;
    private String identityCard;
}
