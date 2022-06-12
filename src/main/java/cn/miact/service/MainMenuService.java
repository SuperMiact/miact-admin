package cn.miact.service;

import cn.miact.domain.entity.MainMenuDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author : mw
 * @Classname :IMainMenuService
 * @createDate : 2022-05-31 17:44:17
 * @Description :
 */
public interface MainMenuService extends IService<MainMenuDO> {

    List<MainMenuDO> getMenu();

    int addMenu(MainMenuDO mainMenuDO);

    int updateMenu(MainMenuDO mainMenuDO);

    int delMenu(MainMenuDO mainMenuDO);

}
