package manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Description:    对树型菜单的节点
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/29 13:47
* @UpdateUser:
* @UpdateDate:     2019/8/29 13:47
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private String id;
    private String pid;
    private String name;
    private String open;
    private String url;
    private String isParent;
}
