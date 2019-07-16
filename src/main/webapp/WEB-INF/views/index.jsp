<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Out Of Memory - Solution for your error</title>

<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom fonts for this template -->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic'
	rel='stylesheet' type='text/css'>
<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>

<!-- Custom styles for this template -->
<link href="css/clean-blog.min.css" rel="stylesheet">

</head>

<body>

	<%@include file="header.jsp"%>

	<!-- Page Header -->
	<header class="masthead" style="background-image: url('img/oom.jpg')">
		<div class="overlay"></div>
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-md-10 mx-auto">
					<div class="site-heading">
						<h1>Out Of Memory</h1>
						<span class="subheading">by Debi Prasad Pradhan</span>
					</div>
				</div>
			</div>
		</div>
	</header>

	<!-- Main Content -->
	<div class="container">
		<div class="row">
			<div class="col-lg-8 col-md-10 mx-auto">
				<c:forEach var="con" items="${blogInfo.subMenuContents}">
					<div class="post-preview">
						<a href="${pageContext.request.contextPath}/blogPost?blogId=${con.conetent_id}&loginId=${loginId}">
							<h2 class="post-title">${con.content_header}</h2>
							<h3 class="post-subtitle">${con.contentHeaderTag}</h3>
						</a>
							<p class="post-meta">
							<a href="${pageContext.request.contextPath}/myblogindex">${con.submenu_ref} </a>
								Posted by <a href="${pageContext.request.contextPath}/aboutme">${con.postedBy} </a>${con.date}
							</p>
					</div>
					<hr>
				</c:forEach>
				<!-- Pager -->
				<div class="clearfix">
				<c:if test="${blogInfo.newPost}">
					<a class="btn btn-primary float-left" href="${pageContext.request.contextPath}/newpost">&larr; New Posts
						</a>
				</c:if>
				<c:if test="${blogInfo.oldPost}">
					<a class="btn btn-primary float-right" href="${pageContext.request.contextPath}/olderpost">Older Posts
						&rarr;</a>	
				</c:if>
				</div>
			</div>
		</div>
	</div>

	<hr>

	<!-- Footer -->
	<%@include file="footer.jsp"%>

	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Custom scripts for this template -->
	<script src="js/clean-blog.min.js"></script>
</body>

</html>
