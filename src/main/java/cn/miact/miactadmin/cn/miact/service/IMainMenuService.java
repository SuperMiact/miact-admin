package cn.miact.miactadmin.cn.miact.service;

import cn.miact.miactadmin.cn.miact.entity.MainMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author : mw
 * @Classname :IMainMenuService
 * @createDate : 2022-05-31 17:44:17
 * @Description :
 */
public interface IMainMenuService extends IService<MainMenu> {

    List<MainMenu> getMenu();

}
