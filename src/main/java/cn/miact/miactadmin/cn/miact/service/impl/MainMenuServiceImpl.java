package cn.miact.miactadmin.cn.miact.service.impl;

import cn.miact.miactadmin.cn.miact.dao.IMainMenuMapper;
import cn.miact.miactadmin.cn.miact.entity.MainMenu;
import cn.miact.miactadmin.cn.miact.service.IMainMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : mw
 * @Classname :MainMenuServiceImpl
 * @createDate : 2022-05-31 17:45:19
 * @Description :
 */
@Service
public class MainMenuServiceImpl extends ServiceImpl<IMainMenuMapper, MainMenu> implements IMainMenuService {

    @Override
    public List<MainMenu> getMenu(){
        List<MainMenu> allMenu = baseMapper.selectList(null);//查出全部菜单
        return allMenu.stream()
                .filter(item -> item.getPid().equals("0"))
                .map(item -> item.setChildNode(getChild(item.getId(), allMenu)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSortOrder() == null ? 0 : menu.getSortOrder())))
                .collect(Collectors.toList());
    }

    private List<MainMenu> getChild(String id, List<MainMenu> allMenu) {
        return allMenu.stream()
                .filter(item -> item.getPid().equals(id))
                .map(item -> item.setChildNode(getChild(item.getId(), allMenu)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSortOrder() == null ? 0 : menu.getSortOrder())))
                .collect(Collectors.toList());
    }

}
