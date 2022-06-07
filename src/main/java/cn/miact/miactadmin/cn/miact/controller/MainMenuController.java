package cn.miact.miactadmin.cn.miact.controller;

import cn.miact.miactadmin.cn.miact.entity.MainMenu;
import cn.miact.miactadmin.cn.miact.service.IMainMenuService;
import cn.miact.miactadmin.cn.miact.utils.ArraysToTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : mw
 * @Classname :MainMenuController
 * @createDate : 2022-06-01 10:29:20
 * @Description :
 */
@RestController
@RequestMapping("/mainMenu")
public class MainMenuController {

    @Autowired
    private IMainMenuService mainMenuService;

    @RequestMapping("/getMenu")
    public List<MainMenu> getMenu(){
        return mainMenuService.getMenu();
    }

}
