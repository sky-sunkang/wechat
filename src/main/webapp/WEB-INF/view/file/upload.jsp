<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="js/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
    <form class="form-horizontal" role="form" action="fileUpload" enctype="multipart/form-data" method="post">
        <div class="form-group" >
            <label for="name" class="col-sm-1 control-label">文件名称</label>
            <div class="col-sm-11">
                <input type="text" id="name" name="name" />
            </div>
        </div>
        <div class="form-group" >
            <label for="fileType" class="col-sm-1 control-label">文件类型</label>
            <div class="col-sm-11">
                <select class="form-control" id="fileType" name="type">
                    <option>image</option>
                    <option>voice</option>
                    <option>video</option>
                    <option>thumb</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="exampleImageFile" class="col-sm-1 control-label">上传文件</label>
            <div class="col-sm-11">
                <%--accept="image/jpeg"--%>
                <input type="file" id="exampleImageFile" name="media" />
            </div>
        </div>
        <div class="col-sm-offset-1 col-sm-11">
            <button type="submit" class="btn btn-default">提交</button>
        </div>
        </div>
    </form>
</body>
</html>
