<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>用户登录界面</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/camera.css" />
<link rel="stylesheet" href="/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/css/matrix-login.css" />
<link href="/css/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="/js/jquery-1.5.1.min.js"></script>


</head>
<body>

	<div
		style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
		<div id="loginbox">
			<form id="formlogin" method="post" onsubmit="return false;">
				<div class="control-group normal_text">
					<h3>
						<span class="add-on" style="color: red;font-size: 7ex;">用</span>
						<span class="add-on" style="color: yellow;font-size: 7ex;">户</span>
						<span class="add-on" style="color: blue;font-size: 7ex;">登</span>
						<span class="add-on" style="color: green;font-size: 7ex;">录</span><!-- 
						<span class="add-on" style="color: orange;font-size: 7ex;">页</span>
						<span class="add-on" style="color: purple;font-size: 7ex;">面</span> -->
					</h3>
				</div>
				<!-- 用户名 -->
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span  style="color: red;font-size: 4ex;width: 20%;height: 25px;">用户名：</span>
							<input type="text" name="username" id="loginname" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				<!-- 密码 -->
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span style="color: red;font-size: 4ex;width: 20%;height: 25px;">密  码：</span>
							<input type="password" name="password" id="password" placeholder="请输入密码" value="" />
						</div>
					</div>
				</div>
				<!-- <div style="float:right;padding-right:10%;">
					<div style="float: left;margin-top:3px;margin-right:2px;">
						<font color="white">记住密码</font>
					</div>
					<div style="float: left;">
						<input name="form-field-checkbox" id="saveid" type="checkbox"
							onclick="savePaw();" style="padding-top:0px;" />
					</div>
				</div> -->
				<div class="form-actions">
					<div style="width:86%;padding-left:8%;">
						<input type="button"  style="background-color: blue; padding-right:3%; width: 200px; height: 40px; float: left;" class="pull-left" id="loginsubmit" value="登录"/>
						<input type="button" style="background-color: blue; padding-right:3%; width: 200px; height: 40px;" class="pull-right"  id="registsubmit" value="去注册" />
                </div>
				</div>

			</form>


			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr" style="color: blue;font-size: 3ex;width: 20%;height: 25px;">设计者：14级计科一班-吴鉴锋</span></font>
				</div>
			</div>
		</div>
	</div>
	<!-- 背景图 -->
	<div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<div data-src="/images/banner_slide_01.jpg"></div>
			<div data-src="/images/banner_slide_02.jpg"></div>
			<div data-src="/images/banner_slide_03.jpg"></div>
		</div>
		<!-- #camera_wrap_3 -->
	</div>
<script type="text/javascript">
	var redirectUrl = "${redirect}";
	var LOGIN = {
			checkInput:function() {
				if ($("#loginname").val() == "") {
					alert("用户名不能为空");
					$("#loginname").focus();
					return false;
				}
				if ($("#nloginpwd").val() == "") {
					alert("密码不能为空");
					$("#nloginpwd").focus();
					return false;
				}
				return true;
			},
			doLogin:function() {
				$.post("/user/login", $("#formlogin").serialize(),function(data){
					if (data.status == 200) {
						alert("登录成功！");
						if (redirectUrl == "") {
							location.href = "http://localhost:8082";
						} else {
							location.href = redirectUrl;
						}
					} else {
						alert("登录失败，原因是：" + data.msg);
						$("#loginname").select();
					}
				});
			},
			login:function() {
				if (this.checkInput()) {
					this.doLogin();
				}
			}
		
	};
	$(function(){
		$("#registsubmit").click(function(){
			window.location.href="/page/register"; 
		});
	});
	$(function(){
		$("#loginsubmit").click(function(){
			LOGIN.login();
		});
	});
</script>
<script src="/js/bootstrap.min.js"></script>
	<script src="/js/jquery-1.7.2.js"></script>
	<script src="/js/jquery.easing.1.3.js"></script>
	<script src="/js/jquery.mobile.customized.min.js"></script>
	<script src="/js/camera.min.js"></script>
	<script src="/js/templatemo_script.js"></script>
	<script type="text/javascript" src="/js/jquery.tips.js"></script>
	<script type="text/javascript" src="/js/jquery.cookie.js"></script>
</body>
</html>