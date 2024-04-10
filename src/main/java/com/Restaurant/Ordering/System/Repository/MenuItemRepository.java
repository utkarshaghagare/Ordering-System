package com.Restaurant.Ordering.System.Repository;

import com.Restaurant.Ordering.System.Entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {
    // You can define custom query methods if needed
    List<MenuItem> findByAvailable(boolean available);

   // boolean findbyitemName(String itemName);
//   @Query
//   MenuItem findById(@Param(" ") int id);
    boolean existsByItemName(String itemName);
}

