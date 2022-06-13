package cn.miact.controller;

import cn.miact.domain.entity.MenuDO;
import cn.miact.service.MenuService;
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
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/getMenu")
    public List<MenuDO> getMenu(){
        return menuService.getMenu();
    }

    @RequestMapping("/addMenu")
    public int addMenu(@RequestBody MenuDO menuDO){
        return menuService.addMenu(menuDO);
    }

    @RequestMapping("/updateMenu")
    public int updateMenu(@RequestBody MenuDO menuDO){
        return menuService.updateMenu(menuDO);
    }

    @RequestMapping("/delMenu")
    public int delMenu(@RequestBody MenuDO menuDO){
        return menuService.delMenu(menuDO);
    }

}
