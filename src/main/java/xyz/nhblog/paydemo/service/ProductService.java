package xyz.nhblog.paydemo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.nhblog.paydemo.dataobject.ProductInfo;
import xyz.nhblog.paydemo.dto.CartDTO;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //商品上架
    ProductInfo onSale(String ProductId);

    //商品下架
    ProductInfo offSale(String ProductId);
}
