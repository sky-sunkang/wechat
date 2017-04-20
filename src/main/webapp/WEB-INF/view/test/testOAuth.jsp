<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>我的信息</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="js/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
${error}
<div class="row">
    <div class="col-xs-4">唯一标识</div>
    <div class="col-xs-8">${userInfo.openId}</div>
</div>
<div class="row">
    <div class="col-xs-4">昵称</div>
    <div class="col-xs-8">${userInfo.nickName}</div>
</div>
<div class="row">
    <div class="col-xs-4">性别</div>
    <div class="col-xs-8">
        <c:if test="${userInfo.sex==1}">
            男
        </c:if>
        <c:if test="${userInfo.sex==2}">
            女
        </c:if>
        <c:if test="${userInfo.sex==0}">
            人妖
        </c:if>
    </div>
</div>
<div class="row">
    <div class="col-xs-4">省份</div>
    <div class="col-xs-8">${userInfo.province}</div>
</div>
<div class="row">
    <div class="col-xs-4">城市</div>
    <div class="col-xs-8">${userInfo.city}</div>
</div>
<div class="row">
    <div class="col-xs-4">国家</div>
    <div class="col-xs-8">${userInfo.country}</div>
</div>
<div class="row">
    <div class="col-xs-4">头像</div>
    <div class="col-xs-8">
        <img style="width: 50px;height: 50px" src="${userInfo.headImageUrl}">
    </div>
</div>
<div class="row">
    <div class="col-xs-4">特权信息</div>
    <div class="col-xs-8">

        <c:if test="${userInfo.privilege=='[]'}">
            无特权
        </c:if>
        <c:if test="${userInfo.privilege!='[]'}">
            ${userInfo.privilege}
        </c:if>
    </div>
</div>
</body>
</html>
