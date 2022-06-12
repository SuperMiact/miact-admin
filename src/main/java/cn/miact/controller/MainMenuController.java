package cn.miact.controller;

import cn.miact.domain.entity.MainMenuDO;
import cn.miact.service.MainMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    private MainMenuService mainMenuService;

    @RequestMapping("/getMenu")
    public List<MainMenuDO> getMenu(){
        return mainMenuService.getMenu();
    }

    @RequestMapping("/addMenu")
    public int addMenu(@RequestBody MainMenuDO mainMenuDO){
        return mainMenuService.addMenu(mainMenuDO);
    }

    @RequestMapping("/updateMenu")
    public int updateMenu(@RequestBody MainMenuDO mainMenuDO){
        return mainMenuService.updateMenu(mainMenuDO);
    }

    @RequestMapping("/delMenu")
    public int delMenu(@RequestBody MainMenuDO mainMenuDO){
        return mainMenuService.delMenu(mainMenuDO);
    }

}
