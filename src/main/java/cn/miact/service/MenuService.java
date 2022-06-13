package cn.miact.service;

import cn.miact.domain.entity.MenuDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author : mw
 * @Classname :IMainMenuService
 * @createDate : 2022-05-31 17:44:17
 * @Description :
 */
public interface MenuService extends IService<MenuDO> {

    List<MenuDO> getMenu();

    int addMenu(MenuDO menuDO);

    int updateMenu(MenuDO menuDO);

    int delMenu(MenuDO menuDO);

}
