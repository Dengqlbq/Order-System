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
                    <form role="form" method="post" action="/order_system/seller/category/save">
                        <div class="form-group">
                            <label>名称</label><input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>type</label><input name="categoryType" type="number" class="form-control" value="${(category.categoryType)!''}"/>
                        </div>
                        <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>