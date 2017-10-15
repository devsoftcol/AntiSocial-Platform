<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <spring:url value="/resources/css/login.css" var="loginCss"/>
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <spring:url value="/resources/css/font-awesome.min.css" var="fontAwesomeCss"/>
    <link rel="stylesheet" href="${loginCss}"/>
    <link rel="stylesheet" href="${bootstrapCss}"/>
    <link rel="stylesheet" href="${fontAwesomeCss}"/>
    <noscript>
        <style type="text/css">
            .login-page {
                display: none;
            }
        </style>
        <div>
            You don't have javascript enabled. Please enable javascript to use this site.
        </div>
    </noscript>
</head>
<body>
<div class="background-image">

</div>
<div class="login-page">
    <div class="form">

        <c:url var="registerUrl" value="/register"/>
        <form:form class="register-form" action="${registerUrl}" method="post" modelAttribute="userDTO">
            <form:input path="ssoId" placeholder="Username" required="true" maxlength="14"/>
            <i id="validSsoId" class="fa fa-check-square valid-icon" aria-hidden="true"></i>
            <i id="errorSsoId" class="fa fa-minus-square error-icon" aria-hidden="true"></i>
            <form:input type="password" path="password" placeholder="Password" required="true" maxlength="100"/>
            <i id="validPassword" class="fa fa-check-square valid-icon" aria-hidden="true"></i>
            <i id="errorPassword" class="fa fa-minus-square error-icon " aria-hidden="true"></i>
            <form:input type="password" path="passwordRepeat" placeholder="Repeat Password" required="true"/>
            <i id="validPasswordRepeat" class="fa fa-check-square valid-icon" aria-hidden="true"></i>
            <i id="errorPasswordRepeat" class="fa fa-minus-square error-icon " aria-hidden="true"></i>
            <form:input path="email" placeholder="E-mail" required="true" maxlength="100"/>
            <i id="validEmail" class="fa fa-check-square valid-icon" aria-hidden="true"></i>
            <i id="errorEmail" class="fa fa-minus-square error-icon " aria-hidden="true"></i>
            <form:input path="name" placeholder="Full Name" required="true" maxlength="26"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" value="Submit">Register</button>
            <p class="message">Already registered? <a href="#">Sign In</a></p>
        </form:form>

        <c:url var="loginUrl" value="/login"/>
        <form action="${loginUrl}" method="post" class="login-form">
            <c:if test="${param.success != null}">
                <div class="alert alert-success">
                    <p>You have successfully signed up!</p>
                </div>
            </c:if>
            <c:if test="${param.error != null}">
                <div class="alert alert-danger">
                    <p>${SPRING_SECURITY_LAST_EXCEPTION}</p>
                </div>
            </c:if>
            <c:if test="${param.logout != null}">
                <div class="alert alert-success">
                    <p>You have been logged out successfully.</p>
                </div>
            </c:if>
            <c:if test="${param.registerError != null}">
                <c:if test="${errorBr != null}">
                    <c:forEach items="${errorBr}" var="errors">
                        <div class="alert alert-danger">
                            <p>${errors.getDefaultMessage()}</p>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${errorSv != null}">
                    <div class="alert alert-danger">
                        <p>${errorSv}</p>
                    </div>
                </c:if>

            </c:if>
            <input type="text" class="form-control" id="username" name="ssoId" placeholder="Enter Username" required>
            <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password"
                   required>
            <div class="remember-me">
                <input type="checkbox" id="rememberme" name="remember-me">
                <label style="padding-bottom: 5px" for="rememberme">Remember me</label>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" value="Submit">LOGIN</button>
            <p class="message">Not registered? <a href="#">Create an account</a></p>
        </form>
    </div>
</div>

<script src="../../resources/js/jquery-3.2.1.min.js"></script>
<script src="../../resources/js/login.js"></script>
<script>
    $('.message a').click(function () {
        $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
    });
</script>
</body>
</html>