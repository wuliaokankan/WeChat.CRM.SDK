$(function() {
	$('#uploadButton1').click(function(){
		$('#fileToUpload1').click()
	});

	$('#fileToUpload1').change(function(){
		
		// 得到项目根路径
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0, index + 1);
		
		var formdata = new FormData();
		url = result + "/upload/upload270.do";
		var fileObj = $(this).get(0).files;
		formdata.append("myfile", fileObj[0]);
		jQuery.ajax({
			url : url,
			type : 'post',
			data : formdata,
			cache : false,
			contentType : false,
			processData : false,
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					$("#url1").attr("src",data.url); 
					$("#imgPath1").val(data.url);
					alert("上传成功!");
				}else if(data.code == 1){
					alert("上传失败:上传的文件必须是图片!");
				}else if(data.code == 2){
					alert("上传失败:图片像素不符合要求，请上传规定像素的图片!");
				}else{
					alert(data.code);
					alert("上传失败!");
				}
			}
		});
	})
})

$(function() {
	$('#uploadButton2').click(function(){
		$('#fileToUpload2').click()
	});

	$('#fileToUpload2').change(function(){
		
		// 得到项目根路径
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0, index + 1);
		
		var formdata = new FormData();
		url = result + "/upload/upload100.do";
		var fileObj = $(this).get(0).files;
		formdata.append("myfile", fileObj[0]);
		jQuery.ajax({
			url : url,
			type : 'post',
			data : formdata,
			cache : false,
			contentType : false,
			processData : false,
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					$("#url2").attr("src",data.url); 
					$("#imgPath2").val(data.url);
					alert("上传成功!");
				}else if(data.code == 1){
					alert("上传失败:上传的文件必须是图片!");
				}else if(data.code == 2){
					alert("上传失败:图片像素不符合要求，请上传规定像素的图片!");
				}else{
					alert(data.code);
					alert("上传失败!");
				}
			}
		});
	})
})


$(function() {
	$('#uploadButton3').click(function(){
		$('#fileToUpload3').click()
	});

	$('#fileToUpload3').change(function(){
		
		// 得到项目根路径
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0, index + 1);
		
		var formdata = new FormData();
		url = result + "/upload/upload.do";
		var fileObj = $(this).get(0).files;
		formdata.append("myfile", fileObj[0]);
		jQuery.ajax({
			url : url,
			type : 'post',
			data : formdata,
			cache : false,
			contentType : false,
			processData : false,
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					$("#url3").attr("src",data.url); 
					$("#imgPath3").val(data.url);
					alert("上传成功!");
				}else if(data.code == 1){
					alert("上传失败:上传的文件必须是图片!");
				}else{
					alert(data.code);
					alert("上传失败!");
				}
			}
		});
	})
})

$(function() {
	$('#uploadButton4').click(function(){
		$('#fileToUpload4').click()
	});

	$('#fileToUpload4').change(function(){
		
		// 得到项目根路径
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0, index + 1);
		
		var formdata = new FormData();
		url = result + "/upload/upload.do";
		var fileObj = $(this).get(0).files;
		formdata.append("myfile", fileObj[0]);
		jQuery.ajax({
			url : url,
			type : 'post',
			data : formdata,
			cache : false,
			contentType : false,
			processData : false,
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					$("#url4").attr("src",data.url); 
					$("#imgPath4").val(data.url);
					alert("上传成功!");
				}else if(data.code == 1){
					alert("上传失败:上传的文件必须是图片!");
				}else{
					alert(data.code);
					alert("上传失败!");
				}
			}
		});
	})
})


