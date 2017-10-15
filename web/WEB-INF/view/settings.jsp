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
    <spring:url value="/resources/css/settings.css" var="settingsCss"/>
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <spring:url value="/resources/css/font-awesome.min.css" var="fontAwesomeCss"/>
    <link rel="stylesheet" href="${styleCss}"/>
    <link rel="stylesheet" href="${settingsCss}"/>
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
                <div class="panel-body">
                    <div class="thumbnail panel-settings">
                    <c:if test="${param.success != null}">
                        <div class="alert alert-success">
                            <p>You have successfully updated your profile!</p>
                        </div>
                    </c:if>
                    <c:if test="${param.updateError != null}">
                        <c:if test="${updateError != null}">
                            <c:forEach items="${updateError}" var="errors">
                                <div class="alert alert-danger">
                                    <p>${errors.getDefaultMessage()}</p>
                                </div>
                            </c:forEach>
                        </c:if>
                    </c:if>
                    <form:form action="/user/update" method="post" modelAttribute="userSettingsDTO">
                        <div class="form-group">
                            <label for="ssoId">Username</label>
                        <form:input cssClass="form-control" path="ssoId" placeholder="Username" readonly="true"/>
                        </div>
                        <div class="form-group">
                            <label for="userBio">User Bio</label>
                        <form:input cssClass="form-control" path="userBio" placeholder="User bio" maxlength="240"/>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                        <form:input cssClass="form-control" type="password" path="password" placeholder="Password"/>
                        </div>
                        <div class="form-group">
                        <label for="passwordChange">Password Repeat</label>
                        <form:input cssClass="form-control" type="password" path="passwordChange" placeholder="Repeat Password"/>
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                        <form:input cssClass="form-control" path="email" placeholder="E-mail" maxlength="100"/>
                        </div>
                        <div class="form-group">
                            <label for="name">Full Name</label>
                        <form:input cssClass="form-control" path="name" placeholder="Full Name" maxlength="26"/>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-success" type="submit" value="Submit">Update</button>
                    </form:form>

                        <button id="change-theme" class="btn btn-primary">Change Theme</button>
                    </div>

                </div>
            </div>
    </div>


</div>


<script src="../../resources/js/jquery-3.2.1.min.js"></script>
<script src="../../resources/js/bootstrap.min.js"></script>
<script src="../../resources/js/navbar.js"></script>
<script src="../../resources/js/js.cookie.js"></script>
<script>

    $('#change-theme').on('click',function () {
        let theme = Cookies.get('theme');
        if(theme === 'default'){
            Cookies.set('theme','dark');
            $('body').css('background-color','#323232');
        }else if(theme === 'dark'){
            Cookies.set('theme','default');
            $('body').css('background-color','#f6f6f6');
        }
    });

    $("#loader").fadeOut("slow");
</script>
</body>
</html>
