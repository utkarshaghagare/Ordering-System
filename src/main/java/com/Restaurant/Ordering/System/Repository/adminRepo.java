package com.Restaurant.Ordering.System.Repository;

import com.Restaurant.Ordering.System.Entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface adminRepo extends CrudRepository<Admin, String> {

}
