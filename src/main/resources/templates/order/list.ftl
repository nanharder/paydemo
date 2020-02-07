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
                                <th>订单id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list orderDTOPage.content as orderDTO>
                                <tr>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                    <td>${orderDTO.getOrderStatusEnum().getMsg()}</td>
                                    <td>${orderDTO.getPayStatusEnum().getMsg()}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                    <td>
                                        <#if orderDTO.getOrderStatusEnum().getMsg() == "新订单">
                                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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
                            <li><a href="/sell/seller/order/list?page=1&size=${size}">首页</a></li>
                            <#if current_page lte 1>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${current_page - 1}&size=${size}">上一页</a></li>
                            </#if>

                            <#if (current_page - 3) gt 1>
                                <#list (current_page - 3)..<current_page as index>
                                    <#if index == current_page>
                                        <li class="disabled"><a href="#">${index}</a></li>
                                    <#else>
                                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                    </#if>
                                </#list>
                            <#else>
                                <#list 1..<current_page as index>
                                    <#if index == current_page>
                                        <li class="disabled"><a href="#">${index}</a></li>
                                    <#else>
                                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                    </#if>
                                </#list>
                            </#if>

                            <#if (current_page + 3) lt orderDTOPage.getTotalPages()>
                                <#list current_page..(current_page + 3) as index>
                                    <#if index == current_page>
                                        <li class="disabled"><a href="#">${index}</a></li>
                                    <#else>
                                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                    </#if>
                                </#list>
                            <#else>
                                <#list current_page..orderDTOPage.getTotalPages() as index>
                                    <#if index == current_page>
                                        <li class="disabled"><a href="#">${index}</a></li>
                                    <#else>
                                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                    </#if>
                                </#list>
                            </#if>

                            <#if current_page gte orderDTOPage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${current_page + 1}&size=${size}">下一页</a></li>
                            </#if>
                            <li><a href="/sell/seller/order/list?page=${orderDTOPage.getTotalPages()}&size=${size}">末页</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>

</html>

