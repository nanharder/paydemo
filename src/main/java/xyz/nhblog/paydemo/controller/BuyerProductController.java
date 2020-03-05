package xyz.nhblog.paydemo.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.nhblog.paydemo.VO.ProductInfoVO;
import xyz.nhblog.paydemo.VO.ProductVO;
import xyz.nhblog.paydemo.VO.ResultVO;
import xyz.nhblog.paydemo.dataobject.ProductCategory;
import xyz.nhblog.paydemo.dataobject.ProductInfo;
import xyz.nhblog.paydemo.service.CategoryService;
import xyz.nhblog.paydemo.service.ProductService;
import xyz.nhblog.paydemo.service.RankService;
import xyz.nhblog.paydemo.utils.ResultVOUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RankService rankService;

    @GetMapping("/list")
    //@Cacheable(cacheNames = "product", key = "123")
    public ResultVO list() {
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //2.查询类目
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //3.数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    @GetMapping("/rank")
    public ResultVO rank() {
        List<ProductInfo> productInfoList = rankService.getRank();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        for (ProductInfo productInfo: productInfoList) {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(productInfo, productInfoVO);
            productInfoVOList.add(productInfoVO);
        }
        return ResultVOUtil.success(productInfoVOList);
    }

}
