package com.Restaurant.Ordering.System.Controller;

import com.Restaurant.Ordering.System.Entity.ImageModel;
import com.Restaurant.Ordering.System.Entity.JwtRequest;
import com.Restaurant.Ordering.System.Entity.JwtResponse;
import com.Restaurant.Ordering.System.Entity.MenuItem;
import com.Restaurant.Ordering.System.Repository.ImageModelRepository;
import com.Restaurant.Ordering.System.Service.JwtService;
import com.Restaurant.Ordering.System.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
@RestController

public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private  JwtService jwtService;
    @Autowired
    private ImageModelRepository imageModelRepository;

    @PostConstruct
    public void initRoleAndUser() {
        menuService.initRoleAndUser();
    }
    @PostMapping("/login")
    @ResponseBody
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
//
//    @PostMapping(value = { "/addMenuItem" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//    public MenuItem addMenuItem1(@RequestPart("menuItem") MenuItem menuItem,
//                                 @RequestPart("itemImage") MultipartFile[] file) {
//        System.out.println(menuItem);
//
//        try {
//            Set<ImageModel> images = uploadImage(file);
//            menuItem.setItemImage(images);
//            //MenuItem menuItem1= new MenuItem();
//            //menuItem1.setItemName(menuItem.getItemName());
//            return menuService.addMenuItem(menuItem);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//

    @PostMapping(value="/addMenuItem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuItem addMenuItem1( @RequestPart MenuItem menuItem,
                                  @RequestPart("image") MultipartFile image) throws Exception{
        try {
            ImageModel images = uploadImage(image);
            menuItem.setItemImage(images);
            return menuService.addMenuItem(menuItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PutMapping(value="/updateMenuItem/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuItem updateMenuItem( @PathVariable Long id,
                                    @RequestPart MenuItem menuItem,
                                  @RequestPart("image") MultipartFile image) throws Exception{
            return menuService.updateMenuItem(menuItem, image,id );
    }
    public ImageModel uploadImage(MultipartFile multipartFiles) throws IOException {
            ImageModel imageModel = new ImageModel(multipartFiles.getOriginalFilename(), multipartFiles.getContentType(), multipartFiles.getBytes());
            imageModelRepository.save(imageModel);
            return imageModel;
    }

    @DeleteMapping("/deleteMenuItem/{id}")
    @ResponseBody
    public void deleteMenuItem(@PathVariable Long id){
        menuService.deleteMenuItem(id);
    }

    @GetMapping("/getMenu")
    @ResponseBody
    public List<MenuItem> getAvailableMenu(){
        return menuService.getAvailableMenu();
    }

    @GetMapping("/getByCategory/{category}")
    @ResponseBody
    public List<MenuItem> getByCategory(@PathVariable String category){
        return menuService.getByCategory(category);
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



