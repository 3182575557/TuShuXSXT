<div class="w" clstag="homepage|keycount|home2013|38a">
	<#include "footer-links.ftl"/>
</div>
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery-extend.js"></script>
<script type="text/javascript" src="/js/lib-v1.js" charset="utf-8"></script>
<script type="text/javascript" src="/js/taotao.js" charset="utf-8"></script>
<script type="text/javascript"> (function(){
var A="<strong>热门搜索：</strong><a href='search.html?q=大数据' style='color:#ff0000' clstag='homepage|keycount|home2013|03b1'>大数据</a><a href='search.html?q=英语四六级模拟卷' >英语四六级模拟卷</a><a href='search.html?q=斗罗大陆' >斗罗大陆<a href='search.html?q=淘宝技术这十年' >淘宝技术这十年</a><a href='search.html?q=java编程思想'>java编程思想</a>";
var B=["java","漫画","教材","古诗","百科全书","十万个为什么"];
B=pageConfig.FN_GetRandomData(B);
$("#hotwords").html(A);
var _searchValue = "${query!}";
if(_searchValue.length == 0){
	_searchValue = B;
}
$("#key").val(_searchValue).bind("focus",function(){if (this.value==B){this.value="";this.style.color="#333"}}).bind("blur",function(){if (!this.value){this.value=B;this.style.color="#999"}});
})();
</script>