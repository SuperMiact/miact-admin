package cn.miact.service.impl;

import cn.miact.mapper.MainMenuMapper;
import cn.miact.domain.entity.MainMenuDO;
import cn.miact.service.MainMenuService;
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
public class MainMenuServiceImpl extends ServiceImpl<MainMenuMapper, MainMenuDO> implements MainMenuService {

    @Override
    public List<MainMenuDO> getMenu(){
        List<MainMenuDO> allMenu = baseMapper.selectList(null);//查出全部菜单
        return allMenu.stream()
                .filter(item -> Objects.equals(item.getPid(),0))
                .map(item -> item.setChildNode(getChild(item.getId(), allMenu)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSortOrder() == null ? 0 : menu.getSortOrder())))
                .collect(Collectors.toList());
    }

    private List<MainMenuDO> getChild(Integer id, List<MainMenuDO> allMenu) {
        return allMenu.stream()
                .filter(item -> Objects.equals(item.getPid(), id))
                .map(item -> item.setChildNode(getChild(item.getId(), allMenu)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSortOrder() == null ? 0 : menu.getSortOrder())))
                .collect(Collectors.toList());
    }


    public int addMenu(MainMenuDO mainMenuDO){
        return baseMapper.insert(mainMenuDO);
    }

    public int updateMenu(MainMenuDO mainMenuDO){
        return baseMapper.updateById(mainMenuDO);
    }

    public int delMenu(MainMenuDO mainMenuDO){
        return baseMapper.deleteById(mainMenuDO);
    }



}
