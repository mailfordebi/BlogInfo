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
                    <div class="container post-content">
                        <div class="row">
                            <div class="col-lg-8 col-md-10 mx-auto">
                                <c:if test="${!editMode}">
                                    <p>${blogInfo.subMenuContents[0].content}</p>
                                    <div class="post-content1">
                                        <h2 class="post-content2">Related
								News</h2>
                                        <ul class="post-content3">
                                            <c:forEach items="${blogInfo.relatedBlogs}" var="related">
                                                <li>
                                                <strong>
                                                <a href="${pageContext.request.contextPath}/blogPost?blogId=${related.conetent_id}&loginId=${loginId}" class="post-content4">${related.content_header}</a>
                                                </strong>
                                                </li>
                                                <%-- <li>
                                                    <div>
                                                       <img src="data:image/jpeg;base64,${related.indivisualThemeimage}" alt="oom" style="width:100%">
                                                       <a href="${pageContext.request.contextPath}/blogPost?blogId=${related.conetent_id}&loginId=${loginId}">
                                                            <h4>${related.content_header}</h4>
                                                       </a>
                                                    </div>
                                                    </li> --%>
                                            </c:forEach>
                                        </ul>
                                        <h2 class="post-content5">Latest
								News</h2>
                                        <ul class="post-content6">
                                            <c:forEach items="${blogInfo.latestBlogs}" var="latest">
                                                <li>
                                                <strong>
                                                <a href="${pageContext.request.contextPath}/blogPost?blogId=${latest.conetent_id}&loginId=${loginId}" class="post-content7">${latest.content_header}</a>
                                                </strong>
                                                </li>
                                                	<%-- <li>
                                                    <div>
                                                       <img src="data:image/jpeg;base64,${latest.indivisualThemeimage}" alt="oom" style="width:100%">
                                                       <a href="${pageContext.request.contextPath}/blogPost?blogId=${related.conetent_id}&loginId=${loginId}">
                                                            <h4>${latest.content_header}</h4>
                                                       </a>
                                                    </div>
                                                    </li> --%>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <div class="post-content8">
                                        <h2 class="post-content9">Comments</h2>
                                        <c:forEach var="com" items="${blogInfo.comments}">
                                            <div class="post-content10">
                                                <img src="img/comment_icon.png" class="post-content11">
                                                <span class="post-content12">
											<strong>
											${com.name} 
											</strong>
											</span>
                                                <span class="post-content13">
												${com.date}
											 </span>
                                                <br>
                                                <p class="post-content14">${com.comment}</p>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <div>
                                        <button type="button" data-toggle="modal" data-target="#myModal" class="post-content15">
                                            Post Comment</button>
                                        <div class="modal fade" id="myModal" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="post-content16">
                                                        <h4 class="modal-title">Add Comment</h4>
                                                    </div>

                                                    <form action="postComment" method="post" id="postComment" enctype="multipart/form-data" onsubmit="return formValidate()">
                                                        <div class="modal-body">
                                                            <div class="form-group">
                                                                <textarea class="form-control" rows="5" id="comment" name="comment" placeholder="Comment:*"></textarea>
                                                                <div id="commentMsg" class="post-content17"></div>
                                                            </div>
                                                            <div class="form-group">
                                                                <input type="text" class="form-control" id="name" name="name" placeholder="Name:*">
                                                                <div id="nameMsg" class="post-content18"></div>
                                                            </div>
                                                            <div class="form-group">
                                                                <input type="text" class="form-control" id="email" name="email" placeholder="Email:*">
                                                                <div id="emailMsg" class="post-content19"></div>
                                                                <div id="emailValidationMsg" class="post-content20"></div>
                                                            </div>
                                                            <div class="form-group">
                                                                <input type="text" class="form-control" id="website" name="website" placeholder="Website:">
                                                                <input type="hidden" id="blogId" name="blogId" value="${blogInfo.subMenuContents[0].conetent_id}">
                                                            </div>
                                                        </div>
                                                        <div class="post-content21">
                                                            <button type="submit" class="post-content22" form="postComment" value="Post">Post</button>
                                                            <button type="button" data-dismiss="modal" class="post-content23">Cancel</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${logedIn}">
                                        <a href="${pageContext.request.contextPath}/editInfo?contentId=${blogInfo.subMenuContents[0].conetent_id}&isBlog=true">Edit</a>
                                    </c:if>
                                </c:if>
                                <c:if test="${editMode}">
                                    <form action="saveContent" method="post" id="saveContent" enctype="multipart/form-data">
                                   	  <div>
                                   	  	<h3>Edit Content</h3>
                                        <textarea rows="30" cols="122" name="updateContent" class="post-content24" id="blogContent">
                                            ${blogInfo.subMenuContents[0].content}
                                        </textarea>
                                        <input type="hidden" value="${blogInfo.subMenuContents[0].conetent_id}" name="content_id">
                                        <input type="hidden" value="true" name="isBlog">
                                        <br> Image:
                                        <input type="file" name="image" id="image">
                                        <br><br>
                                        <button type="submit" form="saveContent" value="Save">Save</button>
                                        <button type="button" value="Format" onclick="formatXML()">Format</button>
                                      </div>
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
                            var isValidate = true;

                            comment = document.getElementById("comment").value;
                            name = document.getElementById("name").value;
                            email = document.getElementById("email").value;
                            if (comment.length == 0) {
                                text = "Comment can not be empty.";
                                document.getElementById("commentMsg").innerHTML = text;
                                document.getElementById("commentMsg").style.display = "block";
                                isValidate = false;
                            } else {
                                document.getElementById("commentMsg").style.display = "none";
                                isValidate = true;
                            }
                            if (name.length == 0) {
                                text = "Name can not be empty.";
                                document.getElementById("nameMsg").innerHTML = text;
                                document.getElementById("nameMsg").style.display = "block";
                                isValidate = false;
                            } else {
                                document.getElementById("nameMsg").style.display = "none";
                                isValidate = true;
                            }

                            var mailformat = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                            if (email.length == 0) {
                                text = "Email can not be empty.";
                                document.getElementById("emailMsg").innerHTML = text;
                                document.getElementById("emailMsg").style.display = "block";
                                isValidate = false;
                            } else {
                                document.getElementById("emailMsg").style.display = "none";
                                if (mailformat.test(email) == false) {
                                    text = "Please enter valid email";
                                    document.getElementById("emailValidationMsg").innerHTML = text;
                                    document.getElementById("emailValidationMsg").style.display = "block";
                                    isValidate = false;
                                } else {
                                    document.getElementById("emailValidationMsg").style.display = "none";
                                    isValidate = true;
                                }
                            }
                            return isValidate;
                        }
                        
                        function formatXML() {
                        	debugger;
                        	  var div = document.createElement('div');
                        	  div.innerHTML = document.getElementById("blogContent").value.trim();
                        	  document.getElementById("blogContent").value=format(div, 0).innerHTML;
                        }
                        
                        function format(node, level) {
                            var indentBefore = new Array(level++ + 1).join('  '),
                                indentAfter  = new Array(level - 1).join('  '),
                                textNode;

                            for (var i = 0; i < node.children.length; i++) {
                                textNode = document.createTextNode(indentBefore);
                                node.insertBefore(textNode, node.children[i]);
                                format(node.children[i], level);
                                if (node.lastElementChild == node.children[i]) {
                                    textNode = document.createTextNode(indentAfter);
                                    node.appendChild(textNode);
                                }
                            }
                            return node;
                        }
                    </script>
        </body>

        </html>