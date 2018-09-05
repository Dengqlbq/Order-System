<html>
    <head>
        <meta charset="utf-8"/>
        <title>错误提示</title>
        <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet"/>
    </head>

    <body>
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    <strong>${message} !</strong>
                </h4>3s后自动跳转  <a href="${redirectUrl}#" class="alert-link">立即跳转</a>
            </div>
        </div>
    </body>
</html>

<script>
    setTimeout('location.href="${redirectUrl}"', 3000)
</script>