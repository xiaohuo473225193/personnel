package manager.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author xiaohuo
 * @data 2019/7/15 11:24
 * @description 部门表
 */
@Data
@Table(name = "college")
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private String name;
    private String status;//字段状态：0为不可用，1为可用
}
