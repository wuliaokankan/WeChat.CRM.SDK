<div >
	<ul class="pagination pagination-right">
		<#if pageBean.pageNo !=1>
		   <li class="prev "><a href="javascript:gotoPage(${pageBean.pageNo - 1})">上一页</a></li>
		<#else>
		   <li class="prev disabled"><a href="javascript:void(0)">上一页</a></li>
		</#if> 
		<#if pageBean.pageNo != pageBean.pageTotal >
		   <li class="next"><a href="javascript:gotoPage(${pageBean.pageNo + 1})">下一页</a></li>
		<#else>
		   <li class="next disabled"><a href="javascript:void(0)">下一页</a></li>
		</#if>
	</ul>
</div>


