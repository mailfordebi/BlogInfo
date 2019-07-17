<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Out Of Memory - Solution for your error</title>
<link rel="icon" href="https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-100x100.png" sizes="32x32">
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
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-light fixed-top"
		id="mainNav">
		<div class="container container-class">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/myblogindex">Out Of Memory
			</a>
			<button class="navbar-toggler navbar-toggler-right" type="button"
				data-toggle="collapse" data-target="#navbarResponsive"
				aria-controls="navbarResponsive" aria-expanded="false"
				aria-label="Toggle navigation">
				Menu <i class="fas fa-bars"></i>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<%-- <li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/myblogindex">HOME</a></li> --%>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/myblogindex">TECHNOLOGY</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/myblogindex">SCIENCE</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/myblogindex">SECURITY</a></li>	
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/myblogindex">TOP LIST</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/myblogindex">HOW-TO</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/myblogindex">GADGETS</a></li>
					<%-- <li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/about">About</a></li> --%>
					<%-- <li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/blogPost">Sample Post</a>
					</li> --%>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/contact">CONTACT</a></li>
					<c:if test="${!logedIn}">	
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/login?page=blog">Login</a></li>
					</c:if>
					<c:if test="${logedIn}">
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/logoutblog">Logout</a></li>
					</c:if>	
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>