<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <html lang="en">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
        <head>

            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <meta name="description" content="">
            <meta name="author" content="">

            <title>Out Of Memory: Solution for your error</title>

            <!-- Bootstrap core CSS -->
            <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet"> 

            <!-- Custom styles for this template -->
            <link href="css/simple-sidebar.css" rel="stylesheet">
            <!-- <link href="menu_submenu.css" rel="stylesheet"> -->
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                li:hover {
                    background-color: lightgrey;
                }
                
                #more {
                    display: none;
                }
                
                .recommendedPostsDiv li {
   					list-style: none;
    				/* margin: 0 -40px!important; */
  					margin-bottom: 4px!important;
 				}
  				.recommendedPostsDiv li a {
    				display: block;
    				padding: 0 40px;
    				color: #105803;
    				font-size: 16px;
    				background-color: white;
    			}
    			.nextprev a:link {
 					 color: green;
  					 background-color: transparent;
 					 text-decoration: none;
				}
				.nextprev a:hover {
 					color: green;
  					background-color: transparent;
  					text-decoration: underline;
				}
				.nextprev span {
 					background-color: #e0dcdc;
 					color: black;
 					padding: 0px 4px;
				}
            </style>
        </head>

        <body>

            <div class="d-flex" id="wrapper">
                <!-- Sidebar -->
                <div class="bg-light border-right" id="sidebar-wrapper">
                    <div class="sidebar-heading">
                        <h2>
					<a href="${pageContext.request.contextPath}/home"><img src="img/logo.JPG" id="logo" height="28px" />
					</a>
				</h2>

                    </div>

                    <ul style="padding-left: 0">
                        <div class="list-group list-group-flush" style="width: 14rem; border-style: groove; border-width: thin; border-color: rgba(0,0,0,.125);">
                            <c:forEach var="sm" items="${blogInfo.subMenus}">
                                <a href="${pageContext.request.contextPath}/submenu?menuid=${sm.menu_ref}&submenuid=${sm.submenu_id}" class="list-group-item list-group-item-action bg-light" style="padding-left: 10px; padding-top: 5px; padding-bottom: 5px; font-size: 16px; font-weight: 500;">${sm.submenu_name}</a>
                            </c:forEach>
                        </div>
                    </ul>
                </div>
                <!-- /#sidebar-wrapper -->

                <!-- Page Content -->
                <div id="page-content-wrapper" style="background: whitesmoke;">

                    <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
                            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                                <span class="navbar-toggler-icon"></span>
                            </button>

                            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                                <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
                                    <c:if test="${logedIn}">
                                        <li class="nav-item active"><a class="nav-link" href="${pageContext.request.contextPath}/settings" style="font-weight: 600;">Settings</a></li>
                                    </c:if>
                                    <c:forEach var="menu" items="${blogInfo.menuList}">
                                    	<c:if test="${!menu.hidden}">
                                    		<c:if test="${menu.menu_id ne 'miscellaneous'}">
                                       		  <li class="nav-item active"><a class="nav-link" href="${pageContext.request.contextPath}/menu?menuid=${menu.menu_id}" style="font-weight: 600;">${menu.menu_name}</a>
                                      		  </li>
                                      		</c:if>
                                      		 <c:if test="${menu.menu_id eq 'miscellaneous'}">
                                      			 <li class="nav-item active"><a class="nav-link" href="${pageContext.request.contextPath}/myblogindex" target="_blank" style="font-weight: 600;">${menu.menu_name}</a>
                                      			 </li>
                                      		 </c:if>
                                      	</c:if>
                                    </c:forEach>
                                    <c:if test="${!logedIn}">
                                        <li class="nav-item active"><a class="nav-link" href="${pageContext.request.contextPath}/login?page=tutorial" style="font-weight: 600;">Login</a></li>
                                    </c:if>
                                    <c:if test="${logedIn}">
                                        <li class="nav-item active"><a class="nav-link" href="${pageContext.request.contextPath}/logout" style="font-weight: 600;">Logout</a></li>
                                    </c:if>

                                </ul>
                            </div>
                    </nav>
                    <div class="container-fluid" style="width: 80%; float: left; border-style: outset; border-width: initial; background-color: white;">
                            <c:if test="${settings}">
                                <form action="addmenu" id="menuForm" method="post" name="menuForm" enctype="multipart/form-data">
                                    <div class="container" style="float: left; background-color: antiquewhite;">
                                        <h3>Add Menu or SubMenu</h3>
                                        <hr style="border: 1px solid #272525;">
                                        <label for="menuid"><b>Select Type: </b></label>
                                        <select name="selectType" id="selectType" onchange="showfield(this.options[this.selectedIndex].value)">
                                            <option selected="selected">Select</option>
                                            <option value="Menu">Add Menu</option>
                                            <option value="Sub Menu">Add Sub Menu</option>
                                            <option value="Misc">Add Blog</option>
                                            <option value="Hide Menu">Hide/Show Menu</option>
                                            <option value="Hide Sub Menu">Hide/Show Sub Menu</option>
                                        </select>
                                        <br> 
                              <span id="menuspan" style="display: none;"> <label
								for="menuid"><b>Menu Id:</b></label> <input type="text"
								placeholder="Menu Id" name="menuid"><br> <label
								for="menuName"><b>Menu Name:</b></label> <input type="text"
								placeholder="Menu Name" name="menuName"><br>

							 </span>
                             <span id="submenuspan" style="display: none;"> <label
								for="submenuid"><b>Sub Menu Id:</b></label> <input type="text"
								placeholder="Sub Menu Id" name="submenuid"><br> <label
								for="submenuName"><b>Sub Menu Name:</b></label> <input
								type="text" placeholder="Sub Menu Name" name="submenuName"><br>

								<label for="menuRef"><b>Menu Reference:</b></label> <select
								name="menuselect" id="menuselect">
									<option selected="selected">Select</option>
									<c:forEach var="menu" items="${blogInfo.menuList}">
										<option value="${menu.menu_id}">${menu.menu_name}</option>
									</c:forEach>
							  </select><br>
							  </span>

                              <span id="misccontaintspan" style="display: none;"> 
							   <label for="submenuRef"><b>Blog Type:</b></label> 
							   <select
								name="submenuselect" id="submenuselect" style="background: #f1f1f1;">
									<option selected="selected">Select</option>
									<c:forEach var="submenu" items="${blogInfo.subMenus}">
										<option value="${submenu.submenu_id}">${submenu.submenu_name}</option>
									</c:forEach>
							   </select><br>
							   <label
								for="contentid"><b>Blog Id:</b></label> <input type="text"
								placeholder="Blog Id" name="contentid"><br>
								<b>Header:</b>
								<br>
								<textarea name="contentHeader" style="background: rgb(241, 241, 241);height: 100px;width: 700px;"></textarea>
								<br>
								<b>Header Tag:</b>
								<br>
								<textarea name="contentHeaderTag" style="background: rgb(241, 241, 241);height: 100px;width: 700px;"></textarea>
								<br> 
								<b>Theme Image:</b>
								<input type="file" name="themeImage" accept="image/*" />
								<br>
								<label for="submenuName">
								<b>Body:</b>
								</label> <br>
								<textarea name="contentBody" style="background: rgb(241, 241, 241);height: 500px;width: 700px;"></textarea>
								<br>
								<label for="postedBy">
								<b>Posted By:</b>
								</label> <br>
								<textarea name="postedBy" style="background: rgb(241, 241, 241);height: 200px;width: 500px;"></textarea>
								<br>
							 </span>
                             <span id="hidemenu" style="display: none;"> 
							    <label for="menuRef1"><b>Menu Reference:</b></label> <select
								name="menuRefToHide" id="menuRefToHide">
									<option selected="selected">Select</option>
									<c:forEach var="menu" items="${blogInfo.menuList}">
										<c:if test="${!(menu.menu_id eq 'miscellaneous')}">
											<option value="${menu.menu_id}">${menu.menu_name}</option>
										</c:if>
									</c:forEach>
							  </select><br>
							  <label for="menuHidden"><b>Disabled:</b></label> <select
								name="menuHidden" id="menuHidden">
										<option selected="selected">Select</option>
										<option value="true">True</option>
										<option value="false">False</option>
							  </select><br>
							 </span>
							<!-- Need to implement for sub menu -->
							<!-- TODO -->
							<!-- Need to implement for sub menu -->
                            <span id="sortSpan" style="display: none;"> <label
								for="sortOrder"><b>Sort:</b></label> <input type="text"
								placeholder="Sort" name="sortOrder"><br>
						  	 </span>

                                        <hr style="border: 1px solid #272525;">
                                        <button type="submit" class="addbtn" id="addbtn" style="display: none;">Save</button>
                                    </div>

                                </form>
                            </c:if>
                            <c:if test="${editMode && !settings}">
                                <form action="saveContent" method="post" id="saveContent" enctype="multipart/form-data">
                                    <textarea rows="30" cols="122" name="updateContent">
                                        <c:forEach var="con" items="${blogInfo.subMenuContents}">
                                             ${con.content}
                                        </c:forEach>
                                    </textarea>
                                    <input type="hidden" value="${blogInfo.content_id}" name="content_id">
                                    <input type="hidden" value="${blogInfo.subMenuId}" name="subMenuId">
                                    <input type="hidden" value="${blogInfo.menuId}" name="menuId">
                                    <input type="hidden" value="false" name="isBlog">
                                    <br> Image:
                                    <input type="file" name="image" id="image">
                                    <br>
                                    <button type="submit" form="saveContent" value="Save">Save</button>
                                </form>
                            </c:if>
                            <c:if test="${!editMode && !settings}">
                                <%-- <c:choose>
                                    <c:when test="${blogInfo.menuId eq 'miscellaneous'}">
                                        <c:forEach var="con" items="${blogInfo.subMenuContents}">
                                            <h2>${con.content_header}</h2>
                                            <p>
                                                ${con.up_content}
                                                <span id="${con.conetent_id}_dots" style="display: inline">...</span>
                                                <span id="${con.conetent_id}_more" style="display: none">${con.down_content}</span>
                                                <br>
                                                <a href="#" id="${con.conetent_id}_myBtn" onclick="myFunction('${con.conetent_id}_dots','${con.conetent_id}_more','${con.conetent_id}_myBtn')">
                                                			Read more
                                                </a>
                                            </p>
                                            <c:if test="${logedIn}">
                                                <a href="${pageContext.request.contextPath}/editInfo?menuid=${con.menu_ref}&subMenuid=${con.submenu_ref}">Edit</a>
                                            </c:if>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise> --%>
                                        <c:forEach var="con" items="${blogInfo.subMenuContents}">
                                            ${con.content}
                                        </c:forEach>
                                        <!--Next Prev Post  --> 
                    					 <div class="nextprev">
                   							<h5>
                    						 <c:if test="${blogInfo.prevSubMenuId ne ''}">
												<span style="float: left;">Previous</span>
												<a style="float: left;padding-left: 7px;" href="${pageContext.request.contextPath}/submenu?menuid=${blogInfo.prevMenuId}&submenuid=${blogInfo.prevSubMenuId}">${blogInfo.prevSubMenuName}</a>
											</c:if>
											<c:if test="${blogInfo.nextSubMenuId ne ''}">
												<span style="float: right;">Next</span>
												<a style="float: right;padding-right: 7px;" href="${pageContext.request.contextPath}/submenu?menuid=${blogInfo.nextMenuId}&submenuid=${blogInfo.nextSubMenuId}">${blogInfo.nextSubMenuName}</a>
											</c:if>
											</h5>
										</div>			
                      
                      					<br>
                      					<br> 
                      					<br>     
                     					<!--Recommended Post  -->
                     					<c:if test="${fn:length(blogInfo.subMenus) gt 1}">       
					 					<div class="recommendedPostsDiv">
    									<h2 style="font-size: 20px; color: #838383;">Recommended Posts:</h2>
   											 <ul>
        										 <c:forEach var="sm" items="${blogInfo.subMenus}">
        										 	<c:if test="${sm.submenu_id ne blogInfo.subMenuId}">
        					  					 		 <li>
        					   							 	<h5>
                              	 							  <a href="${pageContext.request.contextPath}/submenu?menuid=${sm.menu_ref}&submenuid=${sm.submenu_id}">${sm.submenu_name}</a>
                              	 							</h5>
                                						</li>
                               						 </c:if>
                          						  </c:forEach>								
    										</ul>
										</div>
										</c:if>
                                    <%-- </c:otherwise>
                                </c:choose> --%>
                                <c:if test="${logedIn and !(blogInfo.menuId eq 'miscellaneous')}">
                                    <a href="${pageContext.request.contextPath}/editInfo?menuid=${blogInfo.menuId}&subMenuid=${blogInfo.subMenuId}&isBlog=false">Edit</a>
                                </c:if>
                            </c:if>
                            
                    </div>
                    
                </div>
                <!-- /#page-content-wrapper -->
            </div>
            <!-- /#wrapper -->

            <!-- Bootstrap core JavaScript -->
            <script src="vendor/jquery/jquery.min.js"></script>
            <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

            <!-- Menu Toggle Script -->
            <script>
                function showfield(name) {
                    if (name == 'Menu') {
                        document.getElementById('menuspan').style.display = "block";
                        document.getElementById('submenuspan').style.display = "none";
                        document.getElementById('sortSpan').style.display = "block";
                        document.getElementById('misccontaintspan').style.display = "none";
                        document.getElementById('hidemenu').style.display = "none";
                        document.getElementById('addbtn').style.display = "block";
                    } else if (name == 'Sub Menu') {
                        document.getElementById('menuspan').style.display = "none";
                        document.getElementById('submenuspan').style.display = "block";
                        document.getElementById('sortSpan').style.display = "block";
                        document.getElementById('misccontaintspan').style.display = "none";
                        document.getElementById('hidemenu').style.display = "none";
                        document.getElementById('addbtn').style.display = "block";
                    } else if (name == 'Misc') {
                        document.getElementById('menuspan').style.display = "none";
                        document.getElementById('submenuspan').style.display = "none";
                        document.getElementById('sortSpan').style.display = "none";
                        document.getElementById('misccontaintspan').style.display = "block";
                        document.getElementById('hidemenu').style.display = "none";
                        document.getElementById('addbtn').style.display = "block";
                    } else if (name == 'Hide Menu') {
                        document.getElementById('menuspan').style.display = "none";
                        document.getElementById('submenuspan').style.display = "none";
                        document.getElementById('sortSpan').style.display = "none";
                        document.getElementById('misccontaintspan').style.display = "none";
                        document.getElementById('hidemenu').style.display = "block";
                        document.getElementById('addbtn').style.display = "block";
                    } else {
                        document.getElementById('menuspan').style.display = "none";
                        document.getElementById('submenuspan').style.display = "none";
                        document.getElementById('sortSpan').style.display = "none";
                        document.getElementById('misccontaintspan').style.display = "none";
                        document.getElementById('hidemenu').style.display = "none";
                        document.getElementById('addbtn').style.display = "none";
                    }
                }

                /* function myFunction(dots, more, myBtn) {
                    var dots = document.getElementById(dots);
                    var moreText = document.getElementById(more);
                    var btnText = document.getElementById(myBtn);

                    if (dots.style.display === "none") {
                        dots.style.display = "inline";
                        btnText.innerHTML = "Read more";
                        moreText.style.display = "none";
                    } else {
                        dots.style.display = "none";
                        btnText.innerHTML = "Read less";
                        moreText.style.display = "inline";
                    }
                } */
                
            </script>

        </body>
        

        </html>