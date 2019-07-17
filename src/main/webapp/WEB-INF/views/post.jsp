<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Out Of Memory - Solution for your error</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

  <!-- Custom styles for this template -->
  <link href="css/clean-blog.min.css" rel="stylesheet">

</head>

<body>

  <%@include file="header.jsp" %>

  <!-- Page Header -->
  <header class="masthead" style="background-image: url('data:image/jpeg;base64,${blogInfo.themeimage}')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="post-heading">
            <h1 class="heading">${blogInfo.subMenuContents[0].content_header}</h1>
            <%-- <h2 class="subheading">${blogInfo.subMenuContents[0].contentHeaderTag}</h2> --%>
            <span class="meta">Posted by
              <%-- <a href="${pageContext.request.contextPath}/aboutme">${blogInfo.subMenuContents[0].postedBy} </a> --%>
              <a href="https://www.linkedin.com/in/debiprasadpradhan/" target="_blank">${blogInfo.subMenuContents[0].postedBy} </a>
              on ${blogInfo.subMenuContents[0].date}</span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Post Content -->
  <article>
    <div class="container" style="margin-top: -60px">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
         <c:if test="${!editMode}">
          <p>${blogInfo.subMenuContents[0].content}</p>
          <c:if test="${logedIn}">
          	<a href="${pageContext.request.contextPath}/editInfo?menuid=${blogInfo.subMenuContents[0].conetent_id}&subMenuid=null&isBlog=true">Edit</a>
          </c:if>
          </c:if>
          <c:if test="${editMode}">
          	<form action="saveContent" method="post" id="saveContent" enctype="multipart/form-data">
                <textarea rows="30" cols="122" name="updateContent" style="margin-left: -50px;width: 120%;">
                     ${blogInfo.subMenuContents[0].content}
                </textarea>
                <input type="hidden" value="${blogInfo.subMenuContents[0].conetent_id}" name="content_id">
                <input type="hidden" value="null" name="subMenuId">
                <input type="hidden" value="null" name="menuId">
                <input type="hidden" value="true" name="isBlog">
                <br> Image:
                <input type="file" name="image" id="image">
                <br>
                <button type="submit" form="saveContent" value="Save">Save</button>
            </form>
          </c:if>
        </div>
      </div>
    </div>
  </article>

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
