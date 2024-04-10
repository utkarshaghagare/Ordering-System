package com.Restaurant.Ordering.System.Service;

import com.Restaurant.Ordering.System.Entity.Admin;
import com.Restaurant.Ordering.System.Entity.MenuItem;
import com.Restaurant.Ordering.System.Model.MenuItemModel;
import com.Restaurant.Ordering.System.Repository.MenuItemRepository;
import com.Restaurant.Ordering.System.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private com.Restaurant.Ordering.System.Repository.adminRepo adminRepo;


//    public void addMenuItem(MenuItem menuItem) {
//       // boolean item_exists= menuItemRepository.existsByItemName(menuItem.getItemName());
////        if(item_exists) {
////            System.out.println("Item Already Exists");
////        }
////        else{
//            menuItemRepository.save(menuItem);
//       // }
//    }
    public MenuItem addMenuItem(MenuItem menuItem1) throws Exception{
        boolean item_exists= menuItemRepository.existsByItemName(menuItem1.getItemName());
        if(item_exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item already exists.");
        }
        else{
           //byte[] image1= ImageUtils.compressImage(image.getBytes());
           // Blob blob = new javax.sql.rowset.serial.SerialBlob(image1);
//            MenuItem menuItem= new MenuItem();
//            menuItem.setItemName(menuItem1.getItemName());
//            menuItem.setCategory( menuItem1.getCategory());
//            menuItem.setDescription(menuItem1.getDescription());
//            menuItem.setPrice(menuItem1.getPrice());
//           // menuItem.setImageUrl(blob);
//            menuItem.setAvailable(menuItem1.isAvailable());
//            menuItem.setServingTime(menuItem1.getServingTime());
           menuItemRepository.save(menuItem1);
           return menuItem1;
        }

    }
    public MenuItem updateMenuItem(MenuItem menuItem) {
        boolean item_exists= findMenuItem(menuItem);
        if(item_exists) {
           return menuItemRepository.save(menuItem);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Item with id found.");
        }
    }

    private boolean findMenuItem(MenuItem menuItem) {
        boolean existing_menuItem= menuItemRepository.existsById(menuItem.getId());
        return existing_menuItem;
    }

    public MenuItem deleteMenuItem(Long id) {

  //  MenuItem existing_menuItem= new MenuItem();
        Optional<MenuItem> existing_menuItem = menuItemRepository.findById(id);
        if (existing_menuItem.isPresent()) {
            MenuItem menuItem = existing_menuItem.get();
             menuItemRepository.delete(menuItem);
             return menuItem;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Item with id found.");
        }
    }

//    public void deleteMenuItem(Long id) {
//        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);
//        if (menuItemOptional.isPresent()) {
//            MenuItem existingMenuItem = menuItemOptional.get();
//            menuItemRepository.delete(existingMenuItem);
//        } else {
//            System.out.println("No menu item found with ID: " + id);
//            // You might want to throw an exception or log an error message here instead of just printing to console
//        }
//    }
    public List<MenuItem> getAvailableMenu() {
        return menuItemRepository.findByAvailable(true);
    }

    public MenuItem makeItemUnavailable(Long id) {
        Optional<MenuItem> existing_menuItem = menuItemRepository.findById(id);
        if (existing_menuItem.isPresent()) {
            MenuItem menuItem = existing_menuItem.get();
           menuItem.setAvailable(false);
           menuItemRepository.save(menuItem);
           return menuItem;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Item with id found.");

    }

    public MenuItem makeItemAvailable(Long id) {
        MenuItem existing_menuItem = menuItemRepository.findById(id).get();
        if (existing_menuItem!=null) {
           // MenuItem menuItem = existing_menuItem.get();
           existing_menuItem.setAvailable(true);
            return menuItemRepository.save(existing_menuItem);
          //  return menuItem;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Item with id found.");

    }

    private String getEncodedPassword(String userPassword) {
        return passwordEncoder.encode(userPassword);
    }

    public void initRoleAndUser() {

        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setUserPassword(getEncodedPassword("admin@pass"));
        adminRepo.save(admin);
    }
}
