<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
    <title>武夷山市育人书店图书网上销售系统</title>
    <link href="css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
    
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.js"></script>
	<script type="text/javascript" src='js/outlook2.js'> </script>
    <script type="text/javascript">
	 var _menus = {"menus":[
						{"menuid":"1","icon":"icon-sys","menuname":"图书管理",
							"menus":[/* {"menuname":"新增图书","icon":"icon-add","url":"book-add"}, */
									{"menuname":"图书列表","icon":"icon-role","url":"book-list"}
								]
						},{"menuid":"8","icon":"icon-sys","menuname":"图书分类管理",
							"menus":[{"menuname":"图书分类管理","icon":"icon-nav","url":"book-car-list"}
								]
						},{"menuid":"15","icon":"icon-sys","menuname":"图书广告内容管理",
							"menus":[{"menuname":"广告内容分类管理","icon":"icon-nav","url":"content-category"},
									{"menuname":"广告内容管理","icon":"icon-nav","url":"content"}
								]
						},{"menuid":"22","icon":"icon-sys","menuname":"用户管理",
							"menus":[{"menuname":"用户管理","icon":"icon-nav","url":"user-list"}
								]
						},{"menuid":"29","icon":"icon-sys","menuname":"订单管理",
							"menus":[{"menuname":"订单管理","icon":"icon-nav","url":"order-list"}
								]
						},{"menuid":"36","icon":"icon-sys","menuname":"dubbo索引库管理",
							"menus":[{"menuname":"导入索引库","icon":"icon-nav","url":"import-index"}
							]
					}
				]};
        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false
            });
        }
        //关闭登录窗口
        function close() {
            $('#w').window('close');
        }

        

        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
                $newpass.val('');
                $rePass.val('');
                close();
            })
            
        }

        $(function() {

            openPwd();
            //
            $('#editpass').click(function() {
                $('#w').window('open');
            });

            $('#btnEp').click(function() {
                serverLogin();
            })

           

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                    if (r) {
                        location.href = '/ajax/loginout.ashx';
                    }
                });

            })
			
			
			
        });
		
		

    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>
   <!--  <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">欢迎 16素材 <a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; "><img src="images/blocks.gif" width="20" height="20" align="absmiddle" /> 16素材网  www.16sucai.com</span>
    </div> -->
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">设计者：  14计科一班 吴鉴锋</div>
    </div>
    <div region="west" split="true" title="导航菜单" style="width:180px;" id="west">
<div class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
				
			</div>

    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
			<div style="  position:fixed; top:20%; left:15%;width:100%; ">
      <p style="font:bold 5em 'MicroSoft YaHei'; color:#1296db;">欢迎进入武夷山市育人书店图书网上销售系统后台</p>
       <div style="font:bold 3em 'MicroSoft YaHei';position:absolute; left: 30%" id="show"></div></h2>
  </div>

  <script type="text/javascript">
     window.onload = function() {
      var show = document.getElementById("show");
      var time = new Date();
       // 程序计时的月从0开始取值后+1
       var m = time.getMonth() + 1;
       var t = "当前时间： " + time.getFullYear() + "-" + m + "-"
         + time.getDate() + " " + time.getHours() + ":"
         + time.getMinutes() + ":" + time.getSeconds();
       show.innerHTML = t;
      setInterval(function() {
       var time = new Date();
       // 程序计时的月从0开始取值后+1
       var m = time.getMonth() + 1;
       var t = "当前时间： " + time.getFullYear() + "-" + m + "-"
         + time.getDate() + " " + time.getHours() + ":"
         + time.getMinutes() + ":" + time.getSeconds();
       show.innerHTML = t;
      }, 1000);
     };
    </script> 
			</div>
		</div>
    </div>
    

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>

	</div>


</body>
</html>