<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--边栏-->
    <#include "../common/nav.ftl">

    <#--主要内容-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-condensed table-hover">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list productInfoPage.content as productInfo>
                            <tr>
                                <td>${productInfo.productId}</td>
                                <td>${productInfo.productName}</td>
                                <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""> </td>
                                <td>${productInfo.productPrice}</td>
                                <td>${productInfo.productStock}</td>
                                <td>${productInfo.productDescription}</td>
                                <td>${productInfo.categoryType}</td>
                                <td>${productInfo.createTime}</td>
                                <td>${productInfo.updateTime}</td>
                                <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>
                                <td>
                                    <#if productInfo.getProductStatusEnum().message == "在架">
                                        <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                    <#else>
                                        <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">在架</a>
                                    </#if>
                                </td>
                            </tr>

                        </#list>
                        </tbody>
                    </table>
                </div>

                <!--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <li><a href="/sell/seller/product/list?page=1&size=${size}">首页</a></li>
                        <#if current_page lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/product/list?page=${current_page - 1}&size=${size}">上一页</a></li>
                        </#if>

                        <#if (current_page - 3) gt 1>
                            <#list (current_page - 3)..<current_page as index>
                                <#if index == current_page>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                        <#else>
                            <#list 1..<current_page as index>
                                <#if index == current_page>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                        </#if>

                        <#if (current_page + 3) lt productInfoPage.getTotalPages()>
                            <#list current_page..(current_page + 3) as index>
                                <#if index == current_page>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                        <#else>
                            <#list current_page..productInfoPage.getTotalPages() as index>
                                <#if index == current_page>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                        </#if>

                        <#if current_page gte productInfoPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/product/list?page=${current_page + 1}&size=${size}">下一页</a></li>
                        </#if>
                        <li><a href="/sell/seller/product/list?page=${productInfoPage.getTotalPages()}&size=${size}">末页</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>