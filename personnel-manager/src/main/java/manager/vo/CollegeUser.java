package manager.vo;

import lombok.Data;
import manager.pojo.User;
/**
* @Description:    user信息扩展类
* @Author:         2571169797   yang meng bo
* @CreateDate:     2019/8/2 0002 下午 18:50
* @UpdateUser:
* @UpdateDate:     2019/8/2 0002 下午 18:50
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
/**
 * Created by Administrator on 2019/8/2 0002.
 */
@Data
public class CollegeUser extends User{
    private String commonComplete;
    private String entryComplete;
    private String stageComplete;
}
