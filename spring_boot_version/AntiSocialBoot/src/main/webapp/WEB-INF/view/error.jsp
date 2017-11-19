
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
    <meta charset="utf-8">
    <title>AntiSocial</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <c:url value="/img/apple-touch-icon.png" var="appleTouchIcon"/>
    <c:url value="/img/favicon-32x32.png" var="favicon32"/>
    <c:url value="/img/favicon-16x16.png" var="favicon16"/>
    <c:url value="/img/manifest.json" var="manifestJson"/>
    <c:url value="/img/safari-pinned-tab.svg" var="safariPinnedTab"/>
    <link rel="apple-touch-icon" sizes="120x120" href="${appleTouchIcon}">
    <link rel="icon" type="image/png" sizes="32x32" href="${favicon32}">
    <link rel="icon" type="image/png" sizes="16x16" href="${favicon16}">
    <link rel="manifest" href="${manifestJson}">
    <link rel="mask-icon" href="${safariPinnedTab}" color="#5bbad5">
    <meta name="theme-color" content="#ffffff">
    <c:url value="/css/style.css" var="styleCss" />
    <c:url value="/css/bootstrap.min.css" var="bootstrapCss" />
    <c:url value="/css/font-awesome.min.css" var="fontAwesomeCss"/>
    <link rel="stylesheet" href="${styleCss}" />
    <link rel="stylesheet" href="${bootstrapCss}" />
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
            <div class="panel panel-default">
                <div style="height: 80vh" class="panel-body">
                    <div style="text-align: center" class="error">
                    <h1 style="font-size: 6em">Whoops</h1>
                    <h3>${error}</h3>
                    </div>
                </div>
            </div>
    </div>



</div>


<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/navbar.js"></script>
<script src="/js/js.cookie.js"></script>
</body>
</html>
