package com.Restaurant.Ordering.System.Service;

import com.Restaurant.Ordering.System.Entity.Admin;
import com.Restaurant.Ordering.System.Entity.ImageModel;
import com.Restaurant.Ordering.System.Entity.MenuItem;
import com.Restaurant.Ordering.System.Model.MenuItemModel;
import com.Restaurant.Ordering.System.Repository.ImageModelRepository;
import com.Restaurant.Ordering.System.Repository.MenuItemRepository;
import com.Restaurant.Ordering.System.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
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

    @Autowired
    private ImageModelRepository imageModelRepository;


//    public void addMenuItem(MenuItem menuItem) {
//       // boolean item_exists= menuItemRepository.existsByItemName(menuItem.getItemName());
////        if(item_exists) {
////            System.out.println("Item Already Exists");
////        }
////        else{
//            menuItemRepository.save(menuItem);
//       // }
//    }
    public MenuItem addMenuItem(MenuItem menuItem1) {
        boolean item_exists= menuItemRepository.existsByItemName(menuItem1.getItemName());
        if(item_exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item already exists.");
        }
        else{
           menuItemRepository.save(menuItem1);
           return menuItem1;
        }

    }
    public MenuItem updateMenuItem(MenuItem menuItem, MultipartFile imageFile, Long id) throws IOException {
        MenuItem item_exists= menuItemRepository.findById(id).get();

        if(item_exists!= null) {
            // Retrieve the associated ImageModel object
          ImageModel imageModel = item_exists.getItemImage();
            if (imageModel == null) {
                imageModel = new ImageModel();
            }

            // Update the properties of the ImageModel object
            if (imageFile != null && !imageFile.isEmpty()) {
                imageModel.setName(imageFile.getOriginalFilename());
                imageModel.setType(imageFile.getContentType());
                imageModel.setPicByte(imageFile.getBytes());
            }

            menuItem.setItemImage(imageModel);


            item_exists.setItemName(menuItem.getItemName());
            item_exists.setPrice(menuItem.getPrice());
            item_exists.setCategory(menuItem.getCategory());
            item_exists.setDescription(menuItem.getDescription());
            item_exists.setServingTime(menuItem.getServingTime());
            item_exists.setAvailable(menuItem.isAvailable());

            // Save both MenuItem and ImageModel objects
           imageModelRepository.save(imageModel);
           return menuItemRepository.save(item_exists);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Item with id found.");
        }
    }


    public void deleteMenuItem(Long id) {

  //  MenuItem existing_menuItem= new MenuItem();
        Optional<MenuItem> existing_menuItem = menuItemRepository.findById(id);
        if (existing_menuItem.isPresent()) {
            MenuItem menuItem = existing_menuItem.get();
            ImageModel imageModel = menuItem.getItemImage();


             menuItemRepository.delete(menuItem);
            if (imageModel != null) {
                imageModelRepository.delete(imageModel);
            }
            // return menuItem;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Item with id found.");
        }
    }

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

    public List<MenuItem> getByCategory(String category) {
        return menuItemRepository.findAllByCategory(category);
    }
}
