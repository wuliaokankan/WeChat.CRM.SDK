/**
 * Created by liuchao on 2016/8/24.
 */
$(function(){
    //产品搜索点击样式修改
    $(".tag1").click(function(){
        $(this).addClass("search-active");
        $(this).siblings().removeClass("search-active");
        filterSearch();
    });
    //价格分类排序按钮
    $(".btn-default").click(function(){
        $(this).addClass("btn-active");
        $(this).siblings().removeClass("btn-active");
        filterSearch();
    });
    
    //给说有的 a标签添加点击事件
    //$("#searchDiv").find("a").click(search);
    
});

/**
 * 通过分类过滤搜索
 */
function filterSearch(){

	var priceOrder= $("#priceOrder").find(".btn-active").first().val();
	var timeOrder=  $("#timeOrder").find(".btn-active").first().val(); 
	var url = getSearchParam()  +"&priceOrder="+priceOrder+"&timeOrder="+timeOrder
    //$("#pform")[0].action = url;
    //$("#pform")[0].submit();
	location.href = url + "";
};

function getSearchParam(){
	// 得到项目根路径
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	
	var param = result+"/product-page-info.html?";
	
	 $("#searchDiv").find(".search-active").each(function(){
		
		 param=param+$(this).attr("name")+"="+$(this).attr("value")+"&"
		
	})
	param = param.substring(0,param.length-1) ;
	return param;
}


 

