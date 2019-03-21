<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <html lang="en">

        <head>

            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <meta name="description" content="">
            <meta name="author" content="">

            <title>Tech Info Tutorial</title>

            <!-- Bootstrap core CSS -->
            <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

            <!-- Custom styles for this template -->
            <link href="css/simple-sidebar.css" rel="stylesheet">
            <link href="menu_submenu.css" rel="stylesheet">
            <style>
				li:hover {
 					 background-color: lightgrey;
				}
			</style>
        </head>

        <body>

            <div class="d-flex" id="wrapper">
                <!-- Sidebar -->
                <div class="bg-light border-right" id="sidebar-wrapper">
                    <div class="sidebar-heading">
                        <h2>
                        <a href="/home"><img src="img/logo.JPG" id="logo" height="28px" />
							</a>
                        </h2>

                    </div>

                    <ul style="padding-left: 0">
                        <div class="list-group list-group-flush" style="width: 14rem;">
                            <a href="/dashboard?menuid=${blogInfo.menuId}" class="list-group-item list-group-item-action bg-light" id="${blogInfo.menuId}" style="padding-left: 10px;padding-top: 5px;padding-bottom: 5px;font-size: 16px;font-weight: 500;">Dashboard</a>
                            <c:forEach var="sm" items="${blogInfo.subMenus}">
                                <a href="/submenu?menuid=${sm.menu_ref}&submenuid=${sm.submenu_id}" class="list-group-item list-group-item-action bg-light" style="padding-left: 10px;padding-top: 5px;padding-bottom: 5px;font-size: 16px;font-weight: 500;">${sm.submenu_name}</a>
                            </c:forEach>
                        </div>
                    </ul>
                </div>
                <!-- /#sidebar-wrapper -->

                <!-- Page Content -->
                <div id="page-content-wrapper" style="background: whitesmoke;">

                    <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
                        <!-- <button class="btn" id="menu-toggle">
                            <img src="img/hamburger.png" id="hamburger" height="20px" />
                        </button> -->
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>

                            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                                <span class="navbar-toggler-icon"></span>
                            </button>

                            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                                <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
                             	    <c:if test="${logedIn}">
                                   		 <li class="nav-item active"><a class="nav-link" href="/settings" style="font-weight: 600;">Settings</a></li>
                                    </c:if>
                                    <c:forEach var="menu" items="${blogInfo.menuList}">
                                        <li class="nav-item active">
                                            <a class="nav-link" href="/menu?menuid=${menu.menu_id}" style="font-weight: 600;">${menu.menu_name}</a>

                                        </li>
                                    </c:forEach>
                                   <!--  <li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> Dropdown </a>
                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                                            <a class="dropdown-item" href="#">Action</a> <a class="dropdown-item" href="#">Another action</a>
                                            <div class="dropdown-divider"></div>
                                            <a class="dropdown-item" href="#">Something else here</a>
                                        </div>
                                    </li> -->
                                    <c:if test="${!logedIn}">
                                    <li class="nav-item active">
                                 	   <a class="nav-link" href="/login" style="font-weight: 600;">Login</a>
                                 	</li>
                                 	</c:if>
                                 	<c:if test="${logedIn}">
                                 	<li class="nav-item active">
                                 	   <a class="nav-link" href="/logout" style="font-weight: 600;">Logout</a>
                                 	</li>
                                 	</c:if>
                                 	
                                </ul>
                            </div>
                    </nav>
                    <div class="container-fluid" style="width: 80%; float: left; border-style: outset; border-width: initial; background-color: white;height: 1000px;">
                        <%-- ${blogInfo.subMenuId} --%>
                            <c:if test="${settings}">
                                <form action="/addmenu" id="menuForm" method="post" name="menuForm">
                                    <div class="container" style="float: left;background-color: antiquewhite;">
                                        <h3>Add Menu or SubMenu</h3>
                                        <hr style="border: 1px solid #272525;">
                                        <label for="menuid"><b>Select Type: </b></label>
                                        <select name="selectType" id="selectType" onchange="showfield(this.options[this.selectedIndex].value)">
                                            <option selected="selected">Select</option>
                                            <option value="Menu">Menu</option>
                                            <option value="Sub Menu">Sub Menu</option>
                                        </select>

                                        <br>
                                        <span id="menuspan" style="display: none;">
                                        <label for="menuid"><b>Menu Id:</b></label>
                                        <input type="text" placeholder="Menu Id" name="menuid"><br>

                                        <label for="menuName"><b>Menu Name:</b></label>
                                        <input type="text" placeholder="Menu Name" name="menuName"><br>
                                        </span>
                                        <span id="submenuspan" style="display: none;">
                                        <label for="submenuid"><b>Sub Menu Id:</b></label>
                                        <input type="text" placeholder="Sub Menu Id" name="submenuid"><br>

                                        <label for="submenuName"><b>Sub Menu Name:</b></label>
                                        <input type="text" placeholder="Sub Menu Name" name="submenuName"><br>

                                        <label for="menuRef"><b>Menu Reference:</b></label>
                                        <select name="menuselect" id="menuselect">
  											<option selected="selected">Select</option>
										   	 <c:forEach var="menu" items="${blogInfo.menuList}">
                                           		 <option value="${menu.menu_id}">${menu.menu_name}</option>
                                   			 </c:forEach>
  										</select>

  										</span>
                                        <hr style="border: 1px solid #272525;">
                                        <button type="submit" class="addbtn">Add</button>
                                    </div>

                                </form>
                            </c:if>
                            <c:if test="${editMode && !settings}">
                                <form action="/saveContent" method="post" id="saveContent">
                                    <textarea rows="20" cols="100" name="updateContent">
                                        <c:forEach var="con" items="${blogInfo.subMenuContents}">
                                            ${con.content}
                                        </c:forEach>
                                    </textarea>
                                    <input type="hidden" value="${blogInfo.content_id}" name="content_id">
                                    <input type="hidden" value="${blogInfo.subMenuId}" name="subMenuId">
                                    <input type="hidden" value="${blogInfo.menuId}" name="menuId">
                                    <button type="submit" form="saveContent" value="Save">Save</button>
                                </form>
                            </c:if>
                            <c:if test="${!editMode && !settings}">
                                <c:choose>
                                    <c:when test="${blogInfo.subMenuId eq 'dashboard'}">
                                        <p>DashBoard static page for all screen Need to update in JSP</p>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="con" items="${blogInfo.subMenuContents}">
                                            ${con.content}
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${logedIn}">
                               	 <a href="/editInfo?menuid=${blogInfo.menuId}&subMenuid=${blogInfo.subMenuId}">Edit</a>
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
                /* $("#menu-toggle").click(function(e) {
                    e.preventDefault();
                    $("#wrapper").toggleClass("toggled");
                }); */
                
                function showfield(name) {
                    if (name == 'Menu') {
                        document.getElementById('menuspan').style.display = "block";
                        document.getElementById('submenuspan').style.display = "none";
                    } else if (name == 'Sub Menu') {
                        document.getElementById('menuspan').style.display = "none";
                        document.getElementById('submenuspan').style.display = "block";
                    } else {
                        document.getElementById('menuspan').style.display = "none";
                        document.getElementById('submenuspan').style.display = "none";
                    }
                }
            </script>

        </body>

        </html>