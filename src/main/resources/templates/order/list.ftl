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
                            <td><a href="/order_system/seller/order/detail?orderId=${OrderDTO.getOrderId()}">详情</a></td>
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
            </div>
        </div>

        <!-- websocket提示框 -->

        <div class="modal fade" id="dingdong" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            提醒
                        </h4>
                    </div>
                    <div class="modal-body">
                        你有新的订单 !
                    </div>
                    <div class="modal-footer">
                        <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button onclick="location.href=redirect" type="button" class="btn btn-primary">查看</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 音乐 -->
        <audio id="notice" loop="loop">
            <source src="/order_system/mp3/song.mp3" type="audio/mpeg">
        </audio>

        <!-- websocket通信 -->
        <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script type="text/javascript">
            var websocket = null
            var redirect = '/order_system/seller/order/detail?orderId='

            websocket = new WebSocket('ws://localhost:8080/order_system/webSocket');


            websocket.onopen = function (event) {
                console.log('建立连接')
            }

            websocket.onclose = function (event) {
                console.log('断开连接')
            }

            websocket.onmessage = function (event) {
                console.log('收到消息: ' + event.data)

                redirect = redirect + event.data
                // 提示框
                $('#dingdong').modal('show')
                // 音乐
                document.getElementById('notice').play()
            }

            websocket.onerror = function (event) {
                alert('websocket通信发生错误')
            }

            websocket.onbeforeunload = function (event) {
                websocket.close()
            }
        </script>
    </body>
</html>