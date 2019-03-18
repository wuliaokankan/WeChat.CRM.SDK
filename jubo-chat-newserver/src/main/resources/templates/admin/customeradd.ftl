<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维护客户信息</title>
<!--公共的js与css文件-->
<#include "/common/common.ftl">
<script language="javascript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<#include "/admin/common/top.ftl">
	<!-- 要于顶部有距离-->
    <div class="projects-header page-header" style="margin: 30px 0 22px;" > </div>
	<div class="container-fluid">
       <div class="row">
	        <#include "/admin/common/leftmenu.ftl">
            <div class="col-sm-9 col-md-10  main">
          <ol class="breadcrumb" style="margin-top: 3px;margin-bottom: 10px">
	      <li class="glyphicon glyphicon-home" aria-hidden="true"></li>
		  <li class="active">维护客户信息</li>
	  </ol>
	<div class="row">
	 
  <div class="projects-header page-header" style="margin: 3px 0 6px;"></div>
  
<div class="panel panel-default">
<div class="panel-body">
   <div style="padding: 8px;margin: 8px" class="row">
								 
		<form novalidate="novalidate" method="post"  id="form" role="form" class="order-form">
			    <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 客户名称</span></div>
					   <div class="col-md-5">
					     <input type="text" id="suppliername" value="${entity.suppliername!}" name="suppliername" aria-describedby="basic-addon1" placeholder="填写客户名称" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div> 
			    <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 客户缩写</span></div>
					   <div class="col-md-5">
					     <input type="text" id="shortname" name="shortname" value="${entity.shortname!}" aria-describedby="basic-addon1" placeholder="填写客户缩写,如聚播科技->jbkj" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			    <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
                       <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 客户等级</span></div>
                       <div class="col-md-5">
                                <select class="form-control" name="level" >
									<option value="1" <#if entity?? && entity.level?? && entity.level==1> selected</#if> >1级</option>
									<option value="2" <#if entity?? && entity.level?? && entity.level==2> selected</#if> >2级</option>
								</select>
                       </div>
                       <div class="col-md-3"></div>
               </div>
               
               <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 最大账号数量</span></div>
					   <div class="col-md-5">
					     <input type="text" id="account_num" name="account_num" value="${entity.account_num!}" aria-describedby="basic-addon1" placeholder="填写最大账号（操作员）数量" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			    
			   <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 最大设备数量</span></div>
					   <div class="col-md-5">
					     <input type="text" id="device_num" name="device_num" value="${entity.device_num!}" aria-describedby="basic-addon1" placeholder="填写最大设备数量" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			   
			   <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 服务期限</span></div>
					   <div class="col-md-5">
					     <input type="text" id="validity" name="validity" value="${entity.validity!}" readonly onClick="WdatePicker()" aria-describedby="basic-addon1" placeholder="填写服务期限yyyy-MM-dd格式" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			   
			   <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
                       <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 客户状态</span></div>
                       <div class="col-md-5">
                                <select class="form-control" name="state" >
									<option value="1" <#if entity?? && entity.state?? && entity.state==1> selected</#if> >正常</option>
									<option value="2" <#if entity?? && entity.state?? && entity.state==2> selected</#if> >异常</option>
								</select>
                       </div>
                       <div class="col-md-3"></div>
               </div>
               
               <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 管理员账号</span></div>
					   <div class="col-md-5">
					     <input type="text" id="admin" name="admin" value="${entity.admin!}" aria-describedby="basic-addon1" placeholder="填写管理员账号" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			   
			   <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px"> 联系人姓名</span></div>
					   <div class="col-md-5">
					     <input type="text" id="contact" name="contact" value="${entity.contact!}" aria-describedby="basic-addon1" placeholder="填写联系人姓名" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			   
			   <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px"> 联系人电话</span></div>
					   <div class="col-md-5">
					     <input type="text" id="phone" name="phone" value="${entity.phone!}" aria-describedby="basic-addon1" placeholder="填写联系人电话" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			   <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px"> 备注信息</span></div>
					   <div class="col-md-5">
					     <input type="text" id="description" name="description" value="${entity.description!}" aria-describedby="basic-addon1" placeholder="填写备注信息" class="form-control"> 
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
	     $('#form').attr("action","/admin/customer/edit"); 
     }else{
         $('#form').attr("action","/admin/customer/add"); 
     }
    
     if($('#suppliername').val() 
        && $('#suppliername').val() &&  $('#shortname').val() 
        &&  $('#account_num').val() && $('#device_num').val() &&  $('#validity').val() 
        &&  $('#admin').val() ){
		$('#form').submit();
	 }else{
	    $("#flabel").html("<font color='red'>带星号的都不能为空!</font>");
	    return;
	 }
     
 }
 </script>
</body>
</html>