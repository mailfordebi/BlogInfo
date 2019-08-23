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
            <link rel="icon" href="img/titleicon.png" sizes="32x32">
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
            <%@include file="header.jsp"%>
                <!-- Page Header -->
                <header class="masthead" style="background-image: url('data:image/jpeg;base64,${blogInfo.themeimage}')">
                    <div class="overlay"></div>
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-8 col-md-10 mx-auto">
                                <div class="post-heading">
                                    <h1 class="heading">${blogInfo.subMenuContents[0].content_header}</h1>
                                    <%-- <h2 class="subheading">${blogInfo.subMenuContents[0].contentHeaderTag}</h2> --%>
                                        <span class="meta">Posted by <%-- <a href="${pageContext.request.contextPath}/aboutme">${blogInfo.subMenuContents[0].postedBy} </a> --%>
							<a href="https://www.linkedin.com/in/debiprasadpradhan/"
							target="_blank">${blogInfo.subMenuContents[0].postedBy} </a> on
							${blogInfo.subMenuContents[0].date}
						</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </header>
                <!-- Post Content -->
                <article>
                    <div class="container" style="margin-top: -40px">
                        <div class="row">
                            <div class="col-lg-8 col-md-10 mx-auto">
                                <c:if test="${!editMode}">
                                    <p>${blogInfo.subMenuContents[0].content}</p>
                                    <div style="width: 710px; border: 0.5px solid #313131; padding: 10px 10px;">
                                        <h2 style="font-size: 20px; padding-bottom: 10px; text-transform: uppercase;">Related
								News</h2>
                                        <ul style="list-style: none;">
                                            <c:forEach items="${blogInfo.relatedBlogs}" var="entry">
                                                <li><a href="#" style="background: lightgrey; text-decoration: none; padding: 10px; margin-bottom: 5px; display: block; font-size: 16px; border-left: 3px solid #ed1c24;">${entry.value}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                        <h2 style="font-size: 20px; padding-bottom: 10px; text-transform: uppercase;">Latest
								News</h2>
                                        <ul style="list-style: none;">
                                            <c:forEach items="${blogInfo.latestBlogs}" var="entry">
                                                <li><a href="#" style="background: lightgrey; text-decoration: none; padding: 10px; margin-bottom: 5px; display: block; font-size: 16px; border-left: 3px solid #ed1c24;">${entry.value}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    
                                    <div style="margin-top: 70px;">
                                    <h2 style="font-size: 21px; padding-bottom: 10px;">Comments</h2>
                                    <c:forEach var="com" items="${blogInfo.comments}">
                                   		 <div style="border: 1px solid #e5e4e4;font-family: roboto regular;padding: 3px 21px 0;">
											<span>
											<strong>
											${com.name} 
											</strong>
											${com.date}
											</span> 
											<br>
											<p style="font-size: 18px;text-align: left;color: #707070;line-height: 1.7em;">${com.comment}</p>
										</div>
									</c:forEach>
                                    </div>
                                    
                                    <div>
                                        <button type="button" data-toggle="modal" data-target="#myModal" style="margin-top: 40px; float: right; margin-right: 30px; background: black; font-size: 18px; color: white; cursor: pointer;">
                                            Post Comment</button>
                                        <div class="modal fade" id="myModal" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div style="padding: 0rem 1rem; border-bottom: 1px solid #e9ecef;">
                                                        <h4 class="modal-title">Add Comment</h4>
                                                    </div>
                                                    
                                                    <form action="postComment" method="post" id="postComment" enctype="multipart/form-data" onsubmit="return formValidate()">
                                                    <div class="modal-body">
                                                    	 <div class="form-group">
                                                             <textarea class="form-control" rows="5" id="comment" name="comment" placeholder="Comment:*"></textarea>
                                                             <div id="commentMsg" style="font-size: 14px;color: red;"></div>
                                                        </div>
                                                    	<div class="form-group">
                                                        	 <input type="text" class="form-control" id="name" name="name" placeholder="Name:*">
                                                        	 <div id="nameMsg" style="font-size: 14px;color: red;"></div>
                                                    	</div>
                                                    	<div class="form-group">
                                                             <input type="text" class="form-control" id="email" name="email" placeholder="Email:*">
                                                             <div id="emailMsg" style="font-size: 14px;color: red;"></div>
                                                             <div id="emailValidationMsg" style="font-size: 14px;color: red;"></div>
                                                        </div>
                                                        <div class="form-group">
                                                             <input type="text" class="form-control" id="website" name="website" placeholder="Website:">
                                                             <input type="hidden" id="blogId" name="blogId" value="${blogInfo.subMenuContents[0].conetent_id}">
                                                        </div>
                                                    </div>
                                                    <div style="display: flex; justify-content: flex-end; border-top: 1px solid #e9ecef;">
                                                         <button type="submit" style="cursor: pointer; margin: 3px; font-size: 17px; background: lightgrey;" form="postComment" value="Post">Post</button>
                                                         <button type="button" data-dismiss="modal" style="cursor: pointer; margin: 3px; font-size: 17px; background: lightgrey;">Cancel</button>
                                                     </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${logedIn}">
                                        <a href="${pageContext.request.contextPath}/editInfo?menuid=${blogInfo.subMenuContents[0].conetent_id}&subMenuid=null&isBlog=true">Edit</a>
                                    </c:if>
                                </c:if>
                                <c:if test="${editMode}">
                                    <form action="saveContent" method="post" id="saveContent" enctype="multipart/form-data">
                                        <textarea rows="30" cols="122" name="updateContent" style="margin-left: -50px; width: 120%;">
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
                    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
                    <!-- Custom scripts for this template -->
                    <script src="js/clean-blog.min.js"></script>
                    <script>
                        function formValidate() {
                            var comment, name, email, text;
                            var isValidate=true;

                            comment = document.getElementById("comment").value;
                            name = document.getElementById("name").value;
                            email = document.getElementById("email").value;
                            if (comment.length == 0) {
                                text = "Comment can not be empty.";
                                document.getElementById("commentMsg").innerHTML = text;
                                document.getElementById("commentMsg").style.display = "block";
                                isValidate=false;
                            } else {
                                document.getElementById("commentMsg").style.display = "none";
                                isValidate=true;
                            }
                            if (name.length == 0) {
                                text = "Name can not be empty.";
                                document.getElementById("nameMsg").innerHTML = text;
                                document.getElementById("nameMsg").style.display = "block";
                                isValidate=false;
                            } else {
                                document.getElementById("nameMsg").style.display = "none";
                                isValidate=true;
                            }

                            var mailformat = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                            if (email.length == 0) {
                                text = "Email can not be empty.";
                                document.getElementById("emailMsg").innerHTML = text;
                                document.getElementById("emailMsg").style.display = "block";
                                isValidate=false;
                            } else {
                                document.getElementById("emailMsg").style.display = "none";
                                if (mailformat.test(email) == false) {
                                    text = "Please enter valid email";
                                    document.getElementById("emailValidationMsg").innerHTML = text;
                                    document.getElementById("emailValidationMsg").style.display = "block";
                                    isValidate=false;
                                } else {
                                    document.getElementById("emailValidationMsg").style.display = "none";
                                    isValidate=true;
                                }
                            }
                           return isValidate;
                        }
                    </script>
        </body>

        </html>