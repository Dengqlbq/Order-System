<html xmlns="http://www.w3.org/1999/html">>
    <#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">
    <!-- 导航栏 -->
    <#include "../common/nav.ftl">

    <!-- 具体内容 -->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-hover table-bordered">
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
                                    <td><img height="100" width="100" src="${productInfo.productIcon}"></td>
                                    <td>${productInfo.productPrice}</td>
                                    <td>${productInfo.productStock}</td>
                                    <td>${productInfo.productDescription}</td>
                                    <td>${productInfo.categoryType}</td>
                                    <td>${productInfo.createTime}</td>
                                    <td>${productInfo.updateTime}</td>
                                    <td><a href="/order_system/seller/product/index?productId=${productInfo.productId}">修改</a> </td>
                                    <td>
                                        <#if productInfo.getProductStatusEnum().getMessage() == "商品上架">
                                            <a href="/order_system/seller/product/down?productId=${productInfo.productId}">下架</a>
                                        <#else>
                                            <a href="/order_system/seller/product/up?productId=${productInfo.productId}">上架</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>

                <!-- 分页 -->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">

                    <#if currentPage == 1>
                        <li class="disabled"><a href="#">Prev</a></li>
                    <#else>
                        <li><a href="/order_system/seller/order/list?page=${currentPage - 1}">Prev</a></li>
                    </#if>

                    <#list 1..totalPages as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a> </li>
                        <#else>
                            <li><a href="/order_system/seller/order/list?page=${index}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage == totalPages>
                        <li class="disabled"><a href="#">Next</a></li>
                    <#else>
                        <li><a href="/order_system/seller/order/list?page=${currentPage + 1}">Next</a></li>
                    </#if>

                    </ul>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>