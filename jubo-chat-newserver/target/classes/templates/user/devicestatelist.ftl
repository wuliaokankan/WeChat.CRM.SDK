<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设备状态信息信息</title>
<!--公共的js与css文件-->
<#include "/common/common.ftl">
</head>
<body>
    <#include "/user/common/top.ftl">
	<!-- 要于顶部有距离-->
	<div class="projects-header page-header" style="margin: 30px 0 22px;">
	</div>
	<div class="container-fluid">
		<div class="row">
            <#include "/user/common/leftmenu.ftl">
			<div class="col-sm-9  col-md-10  main">
				<ol class="breadcrumb" style="margin-top: 3px; margin-bottom: 10px">
					<li class="glyphicon glyphicon-home" aria-hidden="true"></li>
					<li class="active">设备状态列表</li>
				</ol>
				<div class="row">
					<form action="/user/device/list" id="form" method="post">
					    <div class="col-md-2">
							<input type="text" name="deviceid" value="${info.deviceid!}"
								class="form-control" placeholder="设备号"
								aria-describedby="basic-addon1">
						</div>
						
						<div class="col-md-2">
							<input type="text" name="wechatid" value="${info.wechatid!}"
								class="form-control" placeholder="wechatid"
								aria-describedby="basic-addon1">
						</div>
						
					    <div class="col-md-2">
							 <select class="form-control" name="isonline" >
							   <option value="" <#if info?? && info.isonline??> <#else> selected</#if> > 全部 </option>
							   <option value="0" <#if info?? && info.isonline?? && info.isonline == 0 > selected</#if> >已上线</option>
							   <option value="1" <#if info?? && info.isonline?? && info.isonline == 1 > selected</#if> >已下线</option>
							</select>
						</div>
					    <div class="col-md-2">
							<select class="form-control" name="islogined" >
							   <option value="" <#if info?? && info.islogined??> <#else> selected</#if> > 全部 </option>
							   <option value="0" <#if info?? && info.islogined?? && info.islogined == 0 > selected</#if> >已登录</option>
							   <option value="1" <#if info?? && info.islogined?? && info.islogined == 1 > selected</#if> >已登录</option>
							</select>
						</div>
					   
                        <div class="col-md-2">
							<select class="form-control" onchange="selectOnchang(this)">
								<option value="10" <#if pageBean.pageSize==10> selected</#if> >10条</option>
								<option value="30" <#if pageBean.pageSize==30> selected</#if> >30条</option>
								<option value="50" <#if pageBean.pageSize==50> selected</#if> >50条</option>
								<option value="100" <#if pageBean.pageSize==100> selected</#if> >100条</option>
							</select>
						</div>
						 
						<div class="col-md-1">
							<button type="submit" class="btn btn-default">查询</button>
						</div>
						 
                        
                        <div class="col-md-3">
                           <input type="hidden" id="pageNo" name="pageNo">
                           <input type="hidden" id="pageSize" name="pageSize" value="${pageBean.pageSize!}">
                        </div>
                        
					</form>
				</div>
				<div class="projects-header page-header" style="margin: 3px 0 6px;"></div>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
							    <th>设备号</th>
								<th>微信id</th>
								<th>微信号</th>
								<th>是否在线</th>
								<th>是否登录</th>
								<th>所属客户</th>
								<th>登录账号</th>
                                <th>登录时间</th>
							</tr>
						</thead>
						<tbody>
						 
                         <#if pageBean?exists>  
                            <#list pageBean.resultList as user>
								<tr>
								    <td>${user.deviceid!}</td>
									<td>${user.wechatid!}</td>
									<td>${user.wechatno!}</td>
									<td>${(user.isonline==0)?string('<font color="green">已上线</font>','<font color="red">已下线</font>')}</td>
									<td>${(user.islogined==0)?string('<font color="green">已登录</font>','<font color="red">未登录</font>')}</td>
									<td>${user.cname!}</td>
									<td>${user.accountname!}</td>
                                    <td>${user.login_time?string('yyyy-MM-dd HH:mm:ss')!}</td>
								</tr>
							</#list>
                         </#if>  
						</tbody>
					</table>
				</div>
				<div class="projects-header page-header" style="margin: 0px 0 0px;"></div>
				<div align="center">
					<nav>
						  <#include "/common/page.ftl">
					</nav>
				</div>
			</div>
		</div>
	</div>

</body>
 <script type="text/javascript">
  function gotoPage(pageNo){
     $('#pageNo').val(pageNo)
     $('#pageSize').val()
	 $('#form').submit();
  }
  
 
  function selectOnchang(obj){
	  var value = obj.options[obj.selectedIndex].value;
	  $('#pageSize').val(value);
  }
  
  
</script>
</html>