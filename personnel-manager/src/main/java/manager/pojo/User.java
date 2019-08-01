package manager.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xiaohuo
 * @data 2019/7/15 12:14
 * @description
 */
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String name;
    private String jobNumber;
    private Long cid;//所属部门
    private String author;//权限
    private String password;
    private Long typeId;//人员类型
    private String position;
    private String sex;//性别,1为男，2为女
    private Long degreeId;
    private Long jobTitle;
    private Long educationId;//最高学历
    private String identityCard;
    private String telephone;
    private String address;
    private Date graduateTime;
    private Date startTime;
    private Date endTime;
    private String audit;//审核是否通过，0为不通过，1为通过
    private String complete;//证书是否全部上传，0为没有，1为有
    private Date birthday;
    private String status;
    private Long entryStatusId;//入职状态
}
