<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维护客户信息</title>
<!--公共的js与css文件-->
<#include "/common/common.ftl">
</head>
<body>
	<#include "/user/common/top.ftl">
	<!-- 要于顶部有距离-->
    <div class="projects-header page-header" style="margin: 30px 0 22px;" > </div>
	<div class="container-fluid">
       <div class="row">
	        <#include "/user/common/leftmenu.ftl">
            <div class="col-sm-9 col-md-10  main">
          <ol class="breadcrumb" style="margin-top: 3px;margin-bottom: 10px">
	      <li class="glyphicon glyphicon-home" aria-hidden="true"></li>
		  <li class="active">维护操作员账号</li>
	  </ol>
	<div class="row">
	 
  <div class="projects-header page-header" style="margin: 3px 0 6px;"></div>
  
<div class="panel panel-default">
<div class="panel-body">
   <div style="padding: 8px;margin: 8px" class="row">
								 
		<form novalidate="novalidate" method="post"  id="form" role="form" class="order-form">
			    <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 操作员账号</span></div>
					   <div class="col-md-5">
					     <input type="text" id="account" value="${entity.account!}" name="account" aria-describedby="basic-addon1" placeholder="填写操作员账号,建议以客户简称开头避免重复,如jbkj001" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div> 
			    <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 操作员账号密码</span></div>
					   <div class="col-md-5">
					     <input type="text" id="password" name="password" value="${entity.password!}" aria-describedby="basic-addon1" placeholder="填写操作员账号密码" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			   <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 操作员账号昵称</span></div>
					   <div class="col-md-5">
					     <input type="text" id="nickname" value="${entity.nickname!}" name="nickname" aria-describedby="basic-addon1" placeholder="填写操作员账号昵称" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>  
			   
			   <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
                       <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 账号状态</span></div>
                       <div class="col-md-5">
                                <select class="form-control" name="state" >
									<option value="1" <#if entity?? && entity.state?? && entity.state==1> selected</#if> >正常</option>
									<option value="2" <#if entity?? && entity.state?? && entity.state==2> selected</#if> >禁用</option>
								</select>
                       </div>
                       <div class="col-md-3"></div>
               </div>
               
                
               <div class="col-md-12">
                        <input type="hidden" id="id" name="id" value="${entity.id!}"/>
                        <div class="col-md-4"></div>
			            <div class="col-md-4" id="flabel"></div>
			            <div class="col-md-4"></div>
               </div>
			    
			   <div style="padding: 8px;padding-bottom: 10px;margin: 8px">
			        <div style="text-align: center;" class="col-md-12">
				        <button   type="button" onclick="submitForm()" class="btn btn-success">提交</button> 
				        <button   type="button" onclick="javascript:history.go(-1)" style="margin-left:30px" class="btn btn-default">返回</button>
				    </div>
			   </div>
			   
		</form>
	</div>
     
</div></div>
 
 <script>
 function submitForm(){
     if($('#id').val()){
	     $('#form').attr("action","/user/account/edit"); 
     }else{
         $('#form').attr("action","/user/account/add"); 
     }
    
     if($('#account').val() &&  $('#password').val() &&  $('#nickname').val()  ){
		$('#form').submit();
	 }else{
	    $("#flabel").html("<font color='red'>带星号的都不能为空!</font>");
	    return;
	 }
     
 }
 </script>
</body>
</html>