package manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaohuo
 * @data 2019/7/15 18:38
 * @description 所有的下拉框的结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectDataList {
    private Long id;
    private String name;
}
