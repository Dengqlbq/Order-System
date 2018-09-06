<html xmlns="http://www.w3.org/1999/html">
    <#include "../common/header.ftl">

    <body>
        <div id="wrapper" class="toggled">
            <!-- 导航栏 -->
            <#include "../common/nav.ftl">

            <!-- 具体内容 -->
            <div id="page-content-wrapper">
                <<div class="container">
                <div class="row clearfix">
                    <!-- 订单概况 -->
                    <div class="col-md-4 column">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>订单编号</th>
                                <th>订单金额</th>
                            </tr>
                            </thead>

                            <tbody>
                            <tr>
                                <td>${orderDTO.getOrderId()}</td>
                                <td>${orderDTO.getOrderAmount()}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- 订单详情 -->
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>商品id</th>
                                <th>商品名称</th>
                                <th>价格</th>
                                <th>数量</th>
                                <th>总额</th>
                            </tr>
                            </thead>
                            <tbody>
                        <#list orderDTO.getOrderDetailList() as orderDetail>
                        <tr>
                            <td>${orderDetail.getOrderId()}</td>
                            <td>${orderDetail.getProductName()}</td>
                            <td>${orderDetail.getProductPrice()}</td>
                            <td>${orderDetail.getProductQuantity()}</td>
                            <td>${orderDetail.getProductPrice() * orderDetail.getProductQuantity()}</td>
                        </tr>
                        </#list>
                            </tbody>
                        </table>
                    </div>

                    <!-- 操作 -->
                    <div class="col-md-12 column">
                <#if orderDTO.getOrderStatusEnum().getMessage() == "新订单">
                    <a href="/order_system/seller/order/finish?orderId=${orderDTO.getOrderId()}" type="button" class="btn btn-default btn-primary">完结订单</a>
                    <a href="/order_system/seller/order/cancel?orderId=${orderDTO.getOrderId()}" type="button" class="btn btn-default btn-danger">取消订单</a>
                </#if>
                    </div>
                </div>
            </div>
            </div>
        </div>
    </body>
</html>