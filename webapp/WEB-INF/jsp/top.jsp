<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- logo -->
<div class="col-sm-5 logo">
	<a href="index"><img src="" height="30" width="100" alt="cms"/></a><span class="system-name">SNS系统</span>
</div>   
<!-- end logo -->
<div class="col-sm-7">
	<div class="row">
		<div class="col-sm-12">
			<div class="top-bar-right">
				<button type="button" id="start-tour" class="btn btn-link"><i class="fa fa-refresh"></i> 自动介绍</button>
				<button type="button" id="global-volume" class="btn btn-link btn-global-volume"><i class="fa"></i></button>
				<div class="notifications">
					<ul>
						<!-- notification: inbox -->
						<li class="notification-item inbox">
							<div class="btn-group">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">
									<i class="fa fa-envelope"></i><span class="count"></span>
									<span class="circle"></span>
								</a>

								<ul class="dropdown-menu" role="menu">
									<li class="notification-header">
										<em>你有2条未读信息</em>
									</li>
									<li class="inbox-item clearfix">
										<a href="#">
											<div class="media">
												<div class="media-left">
													<img class="media-object" src="common/img/user1.png"  alt="Antonio">
												</div>
												<div class="media-body">
													<h5 class="media-heading name">Antonius</h5>
													<p class="text">The problem just happened this morning. I can't see ...</p>
													<span class="timestamp">4 minutes ago</span>
												</div>
											</div>
										</a>
									</li>
									<li class="inbox-item unread clearfix">
										<a href="#">
											<div class="media">
												<div class="media-left">
													<img class="media-object" src="common/img/user2.png"  alt="Antonio">
												</div>
												<div class="media-body">
													<h5 class="media-heading name">Michael</h5>
													<p class="text">Hey dude, cool theme!</p>
													<span class="timestamp">2 hours ago</span>
												</div>
											</div>
										</a>
									</li>
									<li class="inbox-item unread clearfix">
										<a href="#">
											<div class="media">
												<div class="media-left">
													<img class="media-object" src="common/img/user3.png"  alt="Antonio">
												</div>
												<div class="media-body">
													<h5 class="media-heading name">Stella</h5>
													<p class="text">Ok now I can see the status for each item. Thanks! :D</p>
													<span class="timestamp">Oct 6</span>
												</div>
											</div>
										</a>
									</li>
									<li class="inbox-item clearfix">
										<a href="#">
											<div class="media">
												<div class="media-left">
													<img class="media-object" src="common/img/user4.png"  alt="Antonio">
												</div>
												<div class="media-body">
													<h5 class="media-heading name">Jane Doe</h5>
													<p class="text"><i class="fa fa-reply"></i> Please check the status of your ...</p>
													<span class="timestamp">Oct 2</span>
												</div>
											</div>
										</a>
									</li>
									<li class="inbox-item clearfix">
										<a href="#">
											<div class="media">
												<div class="media-left">
													<img class="media-object" src="common/img/user5.png"  alt="Antonio">
												</div>
												<div class="media-body">
													<h5 class="media-heading name">John Simmons</h5>
													<p class="text"><i class="fa fa-reply"></i> I've fixed the problem :)</p>
													<span class="timestamp">Sep 12</span>
												</div>
											</div>
										</a>
									</li>
									<li class="notification-footer">
										<a href="#">View All Messages</a>
									</li>
								</ul>
							</div>
						</li>
						<!-- end notification: inbox -->

						<!-- notification: general -->
						<li class="notification-item general">
							<div class="btn-group">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">
									<i class="fa fa-bell"></i><span class="count"></span>
									<span class="circle"></span>
								</a>
								<ul class="dropdown-menu" role="menu">
									<li class="notification-header">
										<em>你有8个通知</em>
									</li>
									<li>
										<a href="#">
											<i class="fa fa-comment green-font"></i>
											<span class="text">New comment on the blog post</span>
											<span class="timestamp">1 minute ago</span>
										</a>
									</li>
									<li>
										<a href="#">
											<i class="fa fa-user green-font"></i>
											<span class="text">New registered user</span>
											<span class="timestamp">12 minutes ago</span>
										</a>
									</li>
									<li>
										<a href="#">
											<i class="fa fa-comment green-font"></i>
											<span class="text">New comment on the blog post</span>
											<span class="timestamp">18 minutes ago</span>
										</a>
									</li>
									<li>
										<a href="#">
											<i class="fa fa-shopping-cart red-font"></i>
											<span class="text">4 new sales order</span>
											<span class="timestamp">4 hours ago</span>
										</a>
									</li>
									<li>
										<a href="#">
											<i class="fa fa-edit yellow-font"></i>
											<span class="text">3 product reviews awaiting moderation</span>
											<span class="timestamp">1 day ago</span>
										</a>
									</li>
									<li>
										<a href="#">
											<i class="fa fa-comment green-font"></i>
											<span class="text">New comment on the blog post</span>
											<span class="timestamp">3 days ago</span>
										</a>
									</li>
									<li>
										<a href="#">
											<i class="fa fa-comment green-font"></i>
											<span class="text">New comment on the blog post</span>
											<span class="timestamp">Oct 15</span>
										</a>
									</li>
									<li>
										<a href="#">
											<i class="fa fa-warning red-font"></i>
											<span class="text red-font">Low disk space!</span>
											<span class="timestamp">Oct 11</span>
										</a>
									</li>
									<li class="notification-footer">
										<a href="#">View All Notifications</a>
									</li>
								</ul>
							</div>
						</li>
						<!-- end notification: general -->
					</ul>
				</div>

				<!-- logged user and the menu -->
				<div class="logged-user">
					<div class="btn-group">
						<a href="#" class="btn btn-link dropdown-toggle" data-toggle="dropdown">
							<img src="common/img/user-avatar.png"  alt="User Avatar" />
							<span class="name">${user.userCname}</span> <span class="caret"></span>
						</a>
						<ul class="dropdown-menu" role="menu">
							<li>
								<a href="#">
									<i class="fa fa-user"></i>
									<span class="text">个人设置</span>
								</a>
							</li>
							<li>
								<a href="javascript:void(0);" onclick="preModifyPassword()">
									<i class="fa fa-lock"></i>
									<span class="text">密码修改</span>
								</a>
							</li>
							<li>
								<a href="#" class="switcher-toggle toggle-hide">
									<i class="fa fa-cog"></i>
									<span class="text">系统设置</span>
								</a>
							</li>
							<li>
								<a href="${ctx}/logout">
									<i class="fa fa-power-off"></i>
									<span class="text">安全退出</span>
								</a>
							</li>
						</ul>
					</div>
				</div>
				<!-- end logged user and the menu -->
			</div>
			<!-- /top-bar-right -->
		</div>
	</div>
	<!-- /row -->
</div>