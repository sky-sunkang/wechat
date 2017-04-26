<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="js/jquery-3.2.1.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/ajaxfileupload.js"></script>
    <script >

        /*ajax上传*/
        function ajaxFile(){
            var fileName="test";
            $.ajaxFileUpload({
                url:"testAjaxFileUpload",
                data:{fileName:fileName},
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'ajaxFile', //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                    console.log(data);
                },
                error: function (data, xml,status, e)//服务器响应失败处理函数
                {
                    alert(e);
                }

            });
        }
    </script>
</head>
    <body>
<form class="form-horizontal" role="form" action="fileUpload" enctype="multipart/form-data" method="post">
    <div class="form-group">
        <label for="name" class="col-sm-1 control-label">媒体文件名称</label>
        <div class="col-sm-11">
            <input type="text" class="form-control" id="name" name="name">
        </div>
    </div>
    <div class="form-group">
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
            <input type="file" id="exampleImageFile" name="media"/>
        </div>
    </div>
    <div class="col-sm-offset-1 col-sm-11">
        <button type="submit" class="btn btn-default">提交</button>
    </div>
</form>
<br><br><br><br><br>
    <div class="form-group">
        <div class="col-sm-10">
            <input type="file" id="ajaxFile" name="media"/>
        </div>
        <div class="col-sm-2">
            <button type="button" class="btn btn-default" onclick="ajaxFile()">上传</button>
        </div>
    </div>
<br><br><br><br><br>
<form class="form-horizontal" role="form" action="fileDown"  method="post">
<div class="form-group" >
    <div class="col-sm-10">
        <input type="text" id="downFileName" name="downFileName" class="form-control" placeholder="请输入要下载的文件名"/>
    </div>
    <div class="col-sm-2">
        <button type="submit" class="btn btn-default" >下载</button>
    </div>
</div>
    <a href="fileDown?downFileName=haha" >下载</a>
</form>
</body>

</html>
