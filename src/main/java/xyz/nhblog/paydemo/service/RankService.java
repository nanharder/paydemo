package xyz.nhblog.paydemo.service;

import xyz.nhblog.paydemo.dataobject.OrderDetail;
import xyz.nhblog.paydemo.dataobject.ProductInfo;
import xyz.nhblog.paydemo.dto.CartDTO;

import java.util.List;

public interface RankService {

    boolean addSales(List<CartDTO> cartDTOList);

    boolean subSales(List<CartDTO> cartDTOList);

    List<ProductInfo> getRank();
}
