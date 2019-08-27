package manager.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
* @Description:    离线分析后获得到的数据
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/27 16:39
* @UpdateUser:
* @UpdateDate:     2019/8/27 16:39
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Data
@Table(name = "educational_ratio")
public class EducationRatio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userCount;
    private Integer juniorhighSchoolSum;//没有学历的人数
    private Integer seniorsighSchoolSum;//初中人数
    private Integer technicalSecondarySchoolSum;//中专人数
    private Integer juniorCollegeSum;//高中人数
    private Integer bachelorDegreeSum;//大学本科
    private Integer masterSum;//硕士人数
    private Integer doctorSum;//博士人数
    private Integer postdoctorSum;//博士后人数
}
