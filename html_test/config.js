//默认的全局变量
var baseUrl="http://localhost:8080";
//var HOST_URL="http://139.196.149.203:8085";
var token = "7a9fc1a001f7b33680bd1675782ef3b31e64f370";
$("#baseUrl").val(baseUrl);
$("#token").val(token);

//重置请求参数文本框与响应结果文本框
function reset(){
	$("#requestArea").val("");
	$("#responseArea").val("");
}
/*
从文本框中加载用户请求参数
*/
function loadUserRequest(request){
	var checked = $("#userDefined").prop("checked")
	if(checked){
		requestStr=$("#requestArea").val();
		if(!requestStr){
			throw "用户自定义参数为空";
		}
		try{
			userRequest=$.parseJSON(requestStr);
		}catch(err){
			throw "用户自定义参数格式不正确";
		}
		if(userRequest["api"]==null){
			throw "用户自定义参数中api为空";
		}
		if(userRequest["data"]==null){
			throw "用户自定义参数中data为空";
		}
		request["api"]=userRequest["api"];
		request["data"]=userRequest["data"];
	}
}

//测试get data操作
function testGetData(request){
	var token = $("#token").val();
	var baseUrl = $("#baseUrl").val();
	request["api"]=baseUrl + request["api"]+"?token="+token;

	try{
		loadUserRequest(request);
	}catch(err){
		alert(err);
		return;
	}

    var requestStr = JSON.stringify(request)
	$("#requestArea").val(requestStr);

    $.ajax({
        url: eval(JSON.stringify(request["api"])),
        data: request["data"],
        type: 'get',
        contentType: 'application/json',
        success:function(result){
            var resultStr=JSON.stringify(result);
			$("#responseArea").val(resultStr);
            console.log(resultStr);
        },
        error:function(result){
			alert("error!");
            var resultStr=JSON.stringify(result);
            console.log(resultStr);
			$("#responseArea").val(resultStr);
        }
    });
}
//测试post data操作
function testPostData(request){
	var token = $("#token").val();
	var baseUrl = $("#baseUrl").val();
	request["api"]=baseUrl + request["api"]+"?token="+token;
	
	try{
		loadUserRequest(request);
	}catch(err){
		alert(err);
		return;
	}

    var requestStr = JSON.stringify(request)
	$("#requestArea").val(requestStr);

    $.ajax({
        url: eval(JSON.stringify(request["api"])),
        data: JSON.stringify(request["data"]),
        type: 'post',
        contentType: 'application/json',
        success:function(result){
            var resultStr=JSON.stringify(result);
           $("#responseArea").val(resultStr);
            console.log(resultStr);
        },
        error:function(result){
			alert("error!");
            var resultStr=JSON.stringify(result);
            console.log(resultStr);
			$("#responseArea").val(resultStr);
        }
    });
}
//测试put data操作
function testPutData(request){	
	var token = $("#token").val();
	var baseUrl = $("#baseUrl").val();
	request["api"]=baseUrl + request["api"]+"?token="+token;
	
    try{
		loadUserRequest(request);
	}catch(err){
		alert(err);
		return;
	}

    var requestStr = JSON.stringify(request)
	$("#requestArea").val(requestStr);
	
    $.ajax({
        url: eval(JSON.stringify(request["api"])),
        data: JSON.stringify(request["data"]),
        type: 'put',
        contentType: 'application/json',
        success:function(result){
            var result=JSON.stringify(result);
            $("#responseArea").html(resultStr);
            console.log(resultStr);
        },
        error:function(result){
			alert("error!");
            var resultStr=JSON.stringify(result);
            console.log(resultStr);
			$("#responseArea").html(resultStr);
        }
    });
}
//测试delete data操作
function testDeleteData(request){
	var token = $("#token").val();
	var baseUrl = $("#baseUrl").val();
	request["api"]=baseUrl + request["api"]+"?token="+token;
	
	try{
		loadUserRequest(request);
	}catch(err){
		alert(err);
		return;
	}

    var requestStr = JSON.stringify(request)
	$("#requestArea").val(requestStr);
	
    $.ajax({
        url: eval(JSON.stringify(request["api"])),
        data: JSON.stringify(request["data"]),
        type: 'delete',
        contentType: 'application/json',
        success:function(result){
            var result=JSON.stringify(result);
            $("#responseArea").html(resultStr);
            console.log(resultStr);
        },
        error:function(result){
			alert("error!");
            var resultStr=JSON.stringify(result);
            console.log(resultStr);
			$("#responseArea").html(resultStr);
        }
    });
}
