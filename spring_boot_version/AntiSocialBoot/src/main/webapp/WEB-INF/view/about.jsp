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

    <c:url value="/css/style.css" var="styleCss"/>
    <c:url value="/css/about.css" var="aboutCss"/>
    <c:url value="/css/bootstrap.min.css" var="bootstrapCss"/>
    <c:url value="/css/font-awesome.min.css" var="fontAwesomeCss"/>
    <link rel="stylesheet" href="${styleCss}"/>
    <link rel="stylesheet" href="${aboutCss}"/>
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

            <div class="loader" id="loader"></div>
            <div class="panel panel-default">
                <div class="panel-body" style="min-height: 40vw">
                    <c:if test="${empty userName}">
                        <h1>Hello , Guest!</h1>
                    </c:if>
                    <c:if test="${!empty userName}">
                    <h1>Hello , <c:out value="${userName}"/> !</h1>
                    </c:if>

                    <div class="accordion">
                        <div class="tab">
                            <input id="tab-one" type="checkbox" name="tabs">
                            <label for="tab-one">What is AntiSocial?</label>
                            <div class="tab-content">
                                <p>AntiSocial is a forum-like site developed and maintained by a student called Ant.</p>
                            </div>
                        </div>
                        <div class="tab">
                            <input id="tab-two" type="checkbox" name="tabs">
                            <label for="tab-two">What's the point?</label>
                            <div class="tab-content">
                                <p>To accomplishment goals and learn. <br> This site is a demo and it is neither used for commercial use nor making profit.</p>
                            </div>
                        </div>
                        <div class="tab">
                            <input id="tab-three" type="checkbox" name="tabs">
                            <label for="tab-three">Which language/technologies used in this site?</label>
                            <div class="tab-content">
                                <p>Purely Spring MVC in the backend and classic HTML, CSS, JS ( Jquery ) in the front-end. <br> You can find more about this in our github page!</p>
                            </div>
                        </div>
                        <div class="tab">
                            <input id="tab-four" type="checkbox" name="tabs">
                            <label for="tab-four">Are the source codes available?</label>
                            <div class="tab-content">
                                <p>This site is developed under MIT License and open source on github.com/Exercon</p>
                            </div>
                        </div>
                        <div class="tab">
                            <input id="tab-five" type="checkbox" name="tabs">
                            <label for="tab-five">I am having trouble with something, what to do?</label>
                            <div class="tab-content">
                                <p>There is no active support at the moment as this is a demo site. <br> But you can always mail me at antkaynak1@gmail.com and get support or feedback your thoughts.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

    </div>


</div>


<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/navbar.js"></script>
<script src="/js/js.cookie.js"></script>
<script>
    $("#loader").fadeOut("slow");
</script>

</body>
</html>
