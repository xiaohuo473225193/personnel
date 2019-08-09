package manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import manager.pojo.Menu;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuList {
    private String menuName;
    private List<Menu> menus;
}
