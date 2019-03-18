<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>手机信息</title>
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
					<li class="active">手机设备列表</li>
				</ol>
				<div class="row">
					<form action="/user/phone/list" id="form" method="post">
						 
						<div class="col-md-2">
							<input type="text" name="deviceid"
								value="${info.deviceid!}" class="form-control"
								placeholder="设备ID" aria-describedby="basic-addon1">
						</div>
						<div class="col-md-2">
							<input type="text" name="nickname"
								value="${info.nickname!}" class="form-control"
								placeholder="手机别名" aria-describedby="basic-addon1">
						</div>
						
						<div class="col-md-2">
							<select class="form-control" name="accountid" >
							   <option value="" <#if info?? && info.id??> <#else> selected</#if> > 全部 </option>
							   <#if userList?exists>  
					                <#list userList as user>
							           <option value="${user.id!}" <#if info.accountid?? && user?? && user.id?? && user.id == info.accountid > selected</#if> > ${user.account!} </option>
					                </#list>
								</#if>  
								 
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
						 
						<div class="col-md-1">
                            <button type="button" onclick="toAdd()" class="btn btn-default">添加</button>
                        </div>
						
						<div class="col-md-1">
                            <button type="button" onclick="dobing()" class="btn btn-default">绑定</button>
                        </div> 
                         
                        <div>
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
							    <th><input type="checkbox" id="checkAll" name="checkAll" /></th>
								<th>所属操作员</th>
								<th>设备ID</th>
								<th>手机别名</th>
								<th>手机品牌</th>
								<th>手机型号</th>
                                <th>时间</th>
								<th align="center">操作</th>
							</tr>
						</thead>
						<tbody>
						
                         <#if pageBean?exists>  
                            <#list pageBean.resultList as info>
								<tr>
								    <td>
								    <#if (info.suppliername)??><#else>
								      <input type="checkbox" id="checkAll${info.id!}" value="${info.id!}" name="checkItem" />
								    </#if>
								    </td>
								    <td>${info.account!}</td>
									<td>${info.deviceid!}</td>
									<td>${info.nickname!}</td>
									<td>${info.brand!}</td>
									<td>${info.module!}</td>
                                    <td>${info.create_time?string('yyyy-MM-dd HH:mm:ss')!}</td>
									<td><#if (info.accountid)??><#else><a  href="javascript:binding(${info.id})">绑定</a>  |</#if> <a  href="/user/phone/toedit/${info.id}">修改</a> | <a  href="javascript:deleteUser(${info.id})">删除</a></td>
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
		
		
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="myModalLabel">
							选择绑定账号
						</h4>
					</div>
					<div class="modal-body">
						    <select class="form-control" onchange="selectbindno(this)">
								 <#if userList?exists>  
								         <option>请选择</option>
	                                     <#list userList as info>
									        <option value="${info.id!}" >${info.account!}</option>
									     </#list>
                                  </#if> 
							</select>
							<input type="hidden" id ="bindno" />
							<input type="hidden" id ="ids" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
						           关闭
						</button>
						<button type="button" onclick="bindSubmit()" class="btn btn-primary">
							提交
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		
		
	</div>

</body>
 <script type="text/javascript">
  //分页 
  function gotoPage(pageNo){
     $('#pageNo').val(pageNo)
     $('#pageSize').val()
	 $('#form').submit();
  }
  //每页显示多少行
  function selectOnchang(obj){
	  var value = obj.options[obj.selectedIndex].value;
	  $('#pageSize').val(value);
  }
 
  //checkbox全选全不选
  $("#checkAll").click(function(){ 
     if(this.checked){ 
        $("input[name='checkItem']:checkbox").prop('checked',true);   
     }else{   
        $("input[name='checkItem']:checkbox").prop('checked',false);    
     }   
  }); 

  //添加
  function toAdd(){
    location.href="/user/phone/toadd";
  } 
  
  //删除
  function deleteUser(id){
      var res=confirm("真的要删除该条记录吗?")
	  if(res){
	       location.href="/user/phone/delete/"+id;
	  }
  }

  //选择所属账号
  function selectbindno(obj){
	  var value = obj.options[obj.selectedIndex].value;
	  $('#bindno').val(value);
  } 
  
  //选择账号后提交
  function bindSubmit(){
      var bindno = $('#bindno').val();
      var ids = $('#ids').val();
      if(bindno && ids){
            $.ajax({
	            type: 'POST',
	            dataType: 'json',
	            url:'/user/phone/binding',
	            data:{accountid:bindno,ids:ids},
	            success:function(result){
			         if(result==true){
			             location.href="/user/phone/list";
			             $('#myModal').modal('hide');
			         }
			    }
		    });
      }else{
         alert("请选择！");
      }
  }  
  
  //单条数据绑定
  function binding(id){
     $('#ids').val(id);
     $('#myModal').modal('show');
  }
  
  //批量绑定
  function dobing(){
        //1、勾选
        var adIds = "";  
        $("input:checkbox[name=checkItem]:checked").each(function(i){  
            if(0==i){  
                adIds = $(this).val();  
            }else{  
                adIds += (","+$(this).val());  
            }  
        });  
        if(adIds==""){
           alert("请选择！");
           return;
        }  
        //2、弹框
        binding(adIds); 
  }
   
</script>
</html>