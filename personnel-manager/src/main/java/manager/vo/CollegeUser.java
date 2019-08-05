package manager.vo;

import lombok.Data;
import manager.pojo.User;

import java.util.Date;
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
public class CollegeUser{
    private Long collegeUserId;
    private String JobNumber;
    private String collegeUserName;
    private String IdentityCard;
    private Date birthday;
    private String sex;
    private String telephone;
    private String education;//最高学历
    private String jobTitle;//最高职称
    private String college;//所属部门
    private String position;//全部职位
    private String type;//人员类型
    private String degree;//最高学位
    private String address;
    private Date graduateTime;//毕业时间
    private Date startTime;
    private Date endTime;
    private String commonComplete;//常规证书状态
    private String entryComplete;//入职证书状态
    private String stageComplete;//阶段性证书状态
}
