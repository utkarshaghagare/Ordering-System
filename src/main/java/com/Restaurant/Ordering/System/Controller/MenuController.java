package com.Restaurant.Ordering.System.Controller;

import com.Restaurant.Ordering.System.Entity.ImageModel;
import com.Restaurant.Ordering.System.Entity.JwtRequest;
import com.Restaurant.Ordering.System.Entity.JwtResponse;
import com.Restaurant.Ordering.System.Entity.MenuItem;
import com.Restaurant.Ordering.System.Model.MenuItemModel;
import com.Restaurant.Ordering.System.Service.JwtService;
import com.Restaurant.Ordering.System.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private  JwtService jwtService;

    @PostConstruct
    public void initRoleAndUser() {
        menuService.initRoleAndUser();
    }
    @PostMapping("/login")
    @ResponseBody
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
    @PostMapping(value="/addMenuItem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuItem addMenuItem1( @RequestPart MenuItem menuItem,
                                  @RequestPart("image") MultipartFile[] image) throws Exception{
        try {
            Set<ImageModel> images = uploadImage(image);
            menuItem.setItemImage(images);
            return menuService.addMenuItem(menuItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public Set uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();
        for (MultipartFile file : multipartFiles) {
            ImageModel imageModel = new ImageModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            imageModels.add(imageModel);
        }
        return imageModels;
    }

    @PutMapping("/updateMenuItem")
    @ResponseBody
    public void updateMenuItem1(@RequestBody MenuItem menuItem){
        menuService.updateMenuItem(menuItem);
    }

    @DeleteMapping("/deleteMenuItem")
    @ResponseBody
    public void deleteMenuItem(@RequestParam("id") Long id){
        menuService.deleteMenuItem(id);
    }
//    @DeleteMapping("/deleteMenuItem")
//    public void deleteMenuItem(@RequestBody Long id){
//        menuService.deleteMenuItem(id);
//    }

    @GetMapping("/getMenu")
    @ResponseBody
    public List<MenuItem> getAvailableMenu(){
        return menuService.getAvailableMenu();
    }

    @GetMapping("/makeItemUnavailable/{id}")
    @ResponseBody
    public MenuItem makeItemUnavailable(@PathVariable Long id){
        return menuService.makeItemUnavailable(id);
    }

    @GetMapping("/makeItemAvailable/{id}")
    public MenuItem makeItemAvailable(@PathVariable Long id){
       return menuService.makeItemAvailable(id);
    }
}
