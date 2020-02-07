package xyz.nhblog.paydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.nhblog.paydemo.dataobject.ProductCategory;
import xyz.nhblog.paydemo.dataobject.ProductInfo;

import java.util.List;

public interface ProductinfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
