package cn.miact.service.impl;

import cn.miact.mapper.MenuMapper;
import cn.miact.domain.entity.MenuDO;
import cn.miact.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : mw
 * @Classname :MainMenuServiceImpl
 * @createDate : 2022-05-31 17:45:19
 * @Description :
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuService {

    @Override
    public List<MenuDO> getMenu(){
        List<MenuDO> allMenu = baseMapper.selectList(null);//查出全部菜单
        return allMenu.stream()
                .filter(item -> Objects.equals(item.getPid(),0))
                .map(item -> item.setChildNode(getChild(item.getId(), allMenu)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSortOrder() == null ? 0 : menu.getSortOrder())))
                .collect(Collectors.toList());
    }

    private List<MenuDO> getChild(Integer id, List<MenuDO> allMenu) {
        return allMenu.stream()
                .filter(item -> Objects.equals(item.getPid(), id))
                .map(item -> item.setChildNode(getChild(item.getId(), allMenu)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSortOrder() == null ? 0 : menu.getSortOrder())))
                .collect(Collectors.toList());
    }


    public int addMenu(MenuDO menuDO){
        return baseMapper.insert(menuDO);
    }

    public int updateMenu(MenuDO menuDO){
        return baseMapper.updateById(menuDO);
    }

    public int delMenu(MenuDO menuDO){
        return baseMapper.deleteById(menuDO);
    }



}
