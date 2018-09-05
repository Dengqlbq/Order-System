<html>
    <head>
        <meta charset="utf-8"/>
        <title>订单列表</title>
        <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet"/>
    </head>
    <body>
        <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <table class="table table-hover table-bordered">
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
                        <#list orderDTOPage.content as OrderDTO>
                            <tr>
                                <td>${OrderDTO.orderId}</td>
                                <td>${OrderDTO.buyerName}</td>
                                <td>${OrderDTO.buyerPhone}</td>
                                <td>${OrderDTO.buyerAddress}</td>
                                <td>${OrderDTO.orderAmount}</td>
                                <td>${OrderDTO.getOrderStatusEnum().message}</td>
                                <td>${OrderDTO.getPayStatusEnum().message}</td>
                                <td>${OrderDTO.createTime}</td>
                                <td><a href="/order_system/seller/order/detail?orderId=${OrderDTO.getOrderId()}">详情</a> </td>
                                <td>
                                    <#if OrderDTO.getOrderStatusEnum().getMessage() == "新订单">
                                        <a href="/order_system/seller/order/cancel?orderId=${OrderDTO.getOrderId()}">取消</a>
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
    </body>
</html>