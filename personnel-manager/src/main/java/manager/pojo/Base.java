package manager.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author xiaohuo
 * @data 2019/7/15 11:24
 * @description 字典表
 */
@Data
@Table(name = "base")
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;//'字典表ID'
    private String itemType;//'字典类别'
    private String itemName;//字典名称
    private String itemValue;//字典值
    private String status;//字段状态：0为不可用，1为可用
    private Integer sort;//用来辅助排序
}
