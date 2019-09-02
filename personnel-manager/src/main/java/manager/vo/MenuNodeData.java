package manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuNodeData {
    private String name;
    private String url;
    private List<MenuNodeData> data;
}
