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
                    <form role="form" method="post" action="/order_system/seller/product/save">
                        <div class="form-group">
                            <label>名称</label><input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"/>
                        </div>
                                                <!-- 价格与库存超过三位数就不显示 -->
                        <div class="form-group">
                            <label>价格</label><input name="productPrice" type="number" class="form-control" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label><input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label><input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label><br>
                            <img height="300" width="300" src="${(productInfo.productIcon)!''}"><br><br>
                            <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}"/>
                        </div>
                        <div>
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                        <option value="${category.categoryType}" selected>${category.categoryName}</option>
                                    <#else>
                                        <option value="${category.categoryType}">${category.categoryName}</option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                        <br>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>