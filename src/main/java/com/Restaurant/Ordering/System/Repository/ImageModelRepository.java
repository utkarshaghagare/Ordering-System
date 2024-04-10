package com.Restaurant.Ordering.System.Repository;

import com.Restaurant.Ordering.System.Entity.ImageModel;
import com.Restaurant.Ordering.System.Entity.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageModelRepository extends CrudRepository<ImageModel, Long> {

  //  boolean existsByItemName(String itemName);
}

