<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="utf-8">
    <title>AntiSocial</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="apple-touch-icon" sizes="120x120" href="/resources/img/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/img/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/resources/img/favicon-16x16.png">
    <link rel="manifest" href="/resources/img/manifest.json">
    <link rel="mask-icon" href="/resources/img/safari-pinned-tab.svg" color="#5bbad5">
    <meta name="theme-color" content="#ffffff">
    <spring:url value="/resources/css/style.css" var="styleCss"/>
    <spring:url value="/resources/css/article.css" var="articleCss"/>
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <spring:url value="/resources/css/font-awesome.min.css" var="fontAwesomeCss"/>
    <link rel="stylesheet" href="${styleCss}"/>
    <link rel="stylesheet" href="${articleCss}"/>
    <link rel="stylesheet" href="${bootstrapCss}"/>
    <link rel="stylesheet" href="${fontAwesomeCss}"/>
    <noscript>
        <style type="text/css">
            .main {display:none;}
        </style>
        <div>
            You don't have javascript enabled.  Please enable javascript to use this site.
        </div>
    </noscript>
</head>
<body>


<div class="main">
    <%@ include file="navbar" %>
    <div class="fade-black"></div>
    <div class="wrapper">
            <div id="articles">
                <div class="loader" id="loader"></div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            </div>
    </div>




</div>


<script src="../../resources/js/jquery-3.2.1.min.js"></script>
<script src="../../resources/js/bootstrap.min.js"></script>
<script src="../../resources/js/append_data.js"></script>
<script type="text/javascript">
    let scrollUrl="${scrollUrl}";
</script>
<script src="../../resources/js/infinitescroll.js"></script>
<script src="../../resources/js/navbar.js"></script>
<script src="../../resources/js/js.cookie.js"></script>
</body>
</html>
