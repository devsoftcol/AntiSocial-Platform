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
    <c:url value="/css/bootstrap.min.css" var="bootstrapCss"/>
    <c:url value="/css/font-awesome.min.css" var="fontAwesomeCss"/>
    <c:url value="/css/admin.css" var="adminCss"/>
    <link rel="stylesheet" href="${styleCss}"/>
    <link rel="stylesheet" href="${bootstrapCss}"/>
    <link rel="stylesheet" href="${fontAwesomeCss}"/>
    <link rel="stylesheet" href="${adminCss}"/>
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
                <div class="admin-panel">

                    <div class="slidebar">
                        <ul>
                            <li><a href="" name="tab1"><i class="fa fa-user"></i>Users</a></li>
                            <li><a href="" name="tab2"><i class="fa fa-picture-o"></i>Categories</a></li>
                            <li><a href="" name="tab3"><i class="fa fa-pencil"></i>Domain</a></li>
                            <li><a href="" name="tab4"><i class="fa fa-tachometer"></i>Performance</a></li>
                            <li><a href="" name="tab5"><i class="fa fa-wrench"></i>Advanced</a></li>
                        </ul>
                    </div>

                    <div class="result">
                        <div id="tab1" class="thumbnail">
                                <input id="search-admin" class="form-control" placeholder="Type in User SsoId">
                                <form:form action="/admin/user/update" method="post" modelAttribute="userDTO">
                                    <label for="ssoId">Username</label>
                                    <form:input cssClass="form-control" path="ssoId" placeholder="Username" readonly="true"/>
                                        <label for="name">Full Name</label>
                                        <form:input cssClass="form-control" path="name" placeholder="Full Name"/>
                                        <label for="email">Email</label>
                                        <form:input cssClass="form-control" path="email" placeholder="E-mail"/>
                                    <label for="state">State</label>
                                    <form:input cssClass="form-control" path="state" placeholder="State" disabled="true"/>
                                    <form:select cssClass="form-control" path="state" placeholder="State">
                                        <form:options type="text" items="${accountStates}"/>
                                    </form:select>
                                        <label for="type">Type</label>
                                        <form:input cssClass="form-control" path="type" placeholder="Type"/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <button class="btn btn-success" type="submit" value="Submit">Update</button>
                                 </form:form>


                        </div>
                        <div id="tab2" class="thumbnail">
                            <ul>
                                <li>
                                <form action="/admin/category/create" method="POST">
                                    <input name="categoryNew" class="form-control" type="text" placeholder="New category name"/>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button class="btn btn-success" value="Submit">Create</button>
                                </form>
                                </li>
                            <c:forEach items="${categories}" var="category">
                                <li>
                                <form:form modelAttribute="categoryDTO" action="/admin/category/update" method="POST">
                                    <form:input cssClass="form-control" path="categoryNew" value="${category.categoryName}"/>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="categoryName" id="categoryName" value="${category.categoryName}"/>
                                    <form:button class="btn btn-default" value="Submit">Update</form:button>
                                </form:form>
                                    <form:form action="/admin/category/update_img?categoryName=${category.categoryName}" method="post" enctype="multipart/form-data"  modelAttribute="fileUpload" id="form_${category.categoryName}">
                                        <form:input path="file" type="file" class="btn btn-primary ct" id="${category.categoryName}" style="width: 15% !important;" />
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form:form>
                                </li>
                            </c:forEach>
                            </ul>
                        </div>
                        <div id="tab3"><h2 class="header">Not supported yet.</h2></div>
                        <div id="tab4"><h2 class="header">Not supported yet</h2></div>
                        <div id="tab5"><h2 class="header">Not supported yet</h2></div>
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
<script src="/js/admin.js"></script>
<script>$("#loader").fadeOut("slow");</script>
</body>
</html>
