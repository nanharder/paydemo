package xyz.nhblog.paydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.nhblog.paydemo.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
