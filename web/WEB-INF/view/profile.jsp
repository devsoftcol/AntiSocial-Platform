<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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
    <spring:url value="/resources/css/profile.css" var="profileCss"/>
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <spring:url value="/resources/css/font-awesome.min.css" var="fontAwesomeCss"/>
    <link rel="stylesheet" href="${styleCss}"/>
    <link rel="stylesheet" href="${profileCss}"/>
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
                    <c:if test="${param.successup != null}">
                        <div class="alert alert-success alert-dismissable">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <p>You have successfully updated your profile!</p>
                        </div>
                    </c:if>
                    <c:if test="${param.uploadError != null}">
                        <c:if test="${uploadError != null}">
                            <div class="alert alert-danger alert-dismissable">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <p>${uploadError}</p>
                            </div>
                        </c:if>
                    </c:if>
            <header>
                <c:if test="${uploadOpt.equals('available')}">
                    <i class="fa fa-cog" aria-hidden="true" data-toggle="modal" data-target="#profilemodal"></i>
                <div class="modal fade" id="profilemodal" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title"><i class="fa fa-upload" aria-hidden="true"></i> Upload Image</h4>
                            </div>
                            <div class="modal-body upload-picture">
                                <h4>Profile Picture</h4>
                                <form:form action="/user/${userName}/upload?source=pp" method="post" enctype="multipart/form-data"  modelAttribute="fileUpload" cssClass="pp-form">
                                    <form:input path="file" type="file" class="btn btn-success pp"/>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form:form>

                                <h4>Profile Background Picture</h4>
                                <form:form action="/user/${userName}/upload?source=bg" method="post" enctype="multipart/form-data"  modelAttribute="fileUpload" cssClass="bg-form">
                                    <form:input path="file" type="file" class="btn btn-success bg"/>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
                </c:if>
            </header>
                <div class="row">
                    <div class="left col-lg-4">
                        <div class="photo-left">
                            <img class="photo" src="/resources/pic/${profile.ssoId}.jpg"/>
                            <c:if test="${online}">
                                <div class="status active"></div>
                            </c:if>
                            <c:if test="${!online}">
                                <div class="status offline"></div>
                            </c:if>

                        </div>
                        <h4 class="name"><c:out value="${profile.name}"/></h4>
                        <p class="info"><c:out value="${profile.userDetail.type}"/></p>
                        <p class="info"><c:out value="${profile.email}"/></p>
                        <div class="stats row">
                            <div class="stat col-xs-4">
                                <p class="number-stat"><c:out value="${articleCount}"/></p>
                                <p class="desc-stat">Articles</p>
                            </div>
                            <div class="stat col-xs-4">
                                <p class="number-stat"><c:out value="${commentCount}"/></p>
                                <p class="desc-stat">Comments</p>
                            </div>
                            <div class="stat col-xs-4">
                                <p class="number-stat">0</p>
                                <p class="desc-stat">Reports</p>
                            </div>
                        </div>
                        <p class="desc">${profile.userDetail.userBio}</p>
                        <div class="social">
                            <i class="fa fa-facebook-square" aria-hidden="true"></i>
                            <i class="fa fa-twitter-square" aria-hidden="true"></i>
                            <i class="fa fa-youtube-square" aria-hidden="true"></i>
                            <i class="fa fa-github-square" aria-hidden="true"></i>
                        </div>
                    </div>
                    <div class="right col-lg-8">
                        <ul class="nav profile-nav">
                            <li id="showarticle" class="active-tab">ARTICLES</li>
                            <li id="showcomment">COMMENTS</li>
                        </ul>
                        <span class="report" data-toggle="modal" data-target="#reportmodal">Report</span>
                        <div class="modal fade" id="reportmodal" role="dialog">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title">Error</h4>
                                    </div>
                                    <div class="modal-body">
                                        <p>Reports are not supported yet.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row gallery">
                            <div class="col-md-12">
                                <div class="thumbnail">
                                    <ol class="user-article custom-counter">
                                        <c:forEach items="${articles}" var="articles">
                                            <li>
                                                <a href="/article/${articles.ID}">
                                                <i class="fa fa-newspaper-o" aria-hidden="true"></i>
                                                <c:out value="${articles.articleHeader}"/>
                                                </a>
                                                <p class="profile-article-date">
                                                    <fmt:formatDate value="${articles.articleDate}" pattern="HH:mm dd-MM-yyyy"/>
                                                </p>
                                            </li>

                                        </c:forEach>
                                    </ol>

                                    <ol class="user-comment custom-counter" style="display: none">
                                        <c:forEach items="${comments}" var="comments">
                                            <li>
                                                <a href="/article/${comments.articleID.ID}">
                                                    <i class="fa fa-comment" aria-hidden="true"></i>
                                                    <c:out value="${comments.commentBody}"/>
                                                </a>
                                                <p class="profile-comment-date">
                                                    <fmt:formatDate value="${comments.commentDate}" pattern="HH:mm dd-MM-yyyy"/>

                                                </p>
                                            </li>
                                        </c:forEach>
                                    </ol>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
    </div>



</div>


<style>
    /* This is a simple hack to display background picture associated with the user's ssoId */
    header {
        background: #323232 url("/resources/pic/bg/${profile.ssoId}.jpg") no-repeat center;
    }
</style>
<script src="../../resources/js/jquery-3.2.1.min.js"></script>
<script src="../../resources/js/bootstrap.min.js"></script>
<script src="../../resources/js/navbar.js"></script>
<script src="../../resources/js/profile.js"></script>
<script src="../../resources/js/js.cookie.js"></script>

</body>
</html>
