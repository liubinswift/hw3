var READY_STATE_UNINITIALIZED=0;
var READY_STATE_LOADING=1;
var READY_STATE_LOADED=2;
var READY_STATE_INTERACTIVE=3;
var READY_STATE_COMPLETE=4;
var jmask = new Object;
var runError=0;
var runErrorTime;
var maxRunError=5;
jmask.seqId = 0;

jmask.cmdQueue = new Object;

Runtime = function(){
	
}
Runtime.prototype._XMLHTTP_PROGIDS = ['Msxml2.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.4.0'];
Runtime.prototype.getXmlHttpObject = function(){
    var http = null;
	var last_e = null;
	try{ http = new XMLHttpRequest(); }catch(e){}
    if(!http){
		for(var i=0; i<3; ++i){
			var progid = jmask.runtime._XMLHTTP_PROGIDS[i];
			try{
				http = new ActiveXObject(progid);
			}catch(e){
				last_e = e;
				alert("error1:"+E);
			}
			if(http){
				jmask.runtime._XMLHTTP_PROGIDS = [progid];  // so faster next time
				break;
			}
		}
	}
	return http;
}
function setCookie(name,value){
	var days=1;
	var exp=new Date();
	exp.setTime(exp.getTime()+days*24*60*60*1000);
	document.cookie=name+"="+escape(value)+";expires="+exp.toGMTString();
}
function getCookie(name){
	var arr=document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	if(arr!=null)return unescape(arr[2]);return null;
}
function delCookie(name){
	var exp=new Date();
	exp.setTime(exp.getTime()-1);
	var cval=getCookie(name);
	if(cval!=null)document.cookie=name+"="+cval+";expires="+exp.toGMTString();
}
Runtime.prototype.sendFlexXmlCmd = function(uri,msg,callerObj,func){
	//alert(uri)
	var obj = new Object;
	obj['uri'] = "jmaskservlet";//uri;
	obj['msg'] = msg.replace("</msg>","<seqId>"+jmask.seqId+"</seqId>\r</msg>");
	obj['caller'] = callerObj;
	obj['func'] = func;
	obj['seqId'] = jmask.seqId;
	//jmask.cmdQueue.push(obj);
	jmask.cmdQueue[jmask.seqId]=obj;
	jmask.seqId++;
	jmask.runtime.sendXmlCmdAsyc(obj);
}

Runtime.prototype.sendXmlCmdAsyc = function(obj){

 	var http = this.getXmlHttpObject();
 	var uri = obj['uri'];
 	var msg = obj['msg'];
 	var sqi=obj['seqId'];
	http.open('POST', uri, true);
	try{
		http.onreadystatechange = 
		function(){
			var ready=http.readyState;
			var data=null;
			
			if (ready==READY_STATE_COMPLETE){
				try{
					if(http.status!=200){
						if(runError>maxRunError){
							runError=0;
							var time=Math.round(((new Date).getTime()-runErrorTime)/1000);
							alert("网络故障或后台停止服务超过"+time+"秒,请于故障解除后刷新系统。\n");//+http.responseText);
						}else{
							if(runError==0){
								runErrorTime=(new Date).getTime();
							}
							runError++;
							setTimeout(function(){jmask.runtime.sendXmlCmdAsyc({"uri":uri,"msg":msg,"sqi":sqi});},100);
							//jmask.runtime.sendXmlCmdAsyc(obj);
						}
						return;
					}
					runError=0;
					if(http.responseXML==null){
						alert("请求XML:\n"+msg+"\n返回格式可能有误，请通知开发人员处理，谢谢。\n"+http.responseText);
						return;
					}
					data=http.responseXML;	
					var data_text=http.responseText;
					if(data.childNodes.length==0||data.childNodes[data.childNodes.length-1].nodeName.toLowerCase()!="msg"){
						if(data.xml){
							alert("返回有误:"+sqi+data.xml);
						}else{
							var oSerializer = new XMLSerializer();
							var data_xml = oSerializer.serializeToString(data);
							alert("返回有误:"+sqi+data_xml);
						}
						return;
					}	
				}catch(e){
					alert("error0"+e.message+"\n当前请求"+msg+"\n返回结果:"+data_text);
					return;
				}finally{
					if(http)http.abort();
					http=null;
				}
				try{
					var func = "";
					var caller = "";
					var seqId = "";
					var msgNode = new Object;
					try{
						if(data.getElementsByTagName("Msg")!=null){
							msgNode = data.getElementsByTagName("Msg")[0];
						}else{
							msgNode = data.getElementsByTagName("msg")[0];
						}

						if(msgNode!=null){
							
							var list = msgNode.attributes;
							for(var i=0;i<list.length;i++){								
								if(list[i].nodeName.toLowerCase()=="seqid"){
									//alert("list[i]>>>>"+list[i].value);
									seqId = list[i].value;
								}
							}
					  }	
					}catch(e){
						var msgNode = data.childNodes[data.childNodes.length-1];
						seqId = msgNode.getAttribute("seqId");
					}	
			  	try{
			  		if(seqId===""){
						  alert("服务器返回结果格式有误,请通知开发人员处理\n当前请求"+msg+"\n返回结果:"+data_text);
							return;
						}
						var obj=jmask.cmdQueue[seqId];
					 	func = obj['func'];
					 	caller = obj['caller'];
					 	jmask.cmdQueue[seqId]=null;
					}catch(E){
						alert("error2.1"+E.message+func);
					}	 	
					try{	 	
					 	if(data.xml!=null){
								eval('caller.'+func+'(data.xml)');
						}else{
							var oSerializer = new XMLSerializer();
							var data_xml = oSerializer.serializeToString(data);
							//alert("test>>>xml="+data_xml);
							eval('caller.'+func+'(data_xml)');
						}
					}catch(E){
						alert("error2.2"+E.message+"\n返回:"+data_text+"\ndata.xml==null:"+(data.xml==null)+"\ncaller:"+caller+"\nfun:"+func);
					}		
				}catch(E){
					alert("error2"+E.message);
				}finally{
					if(http)http.abort();
					http=null;
				}
			}
		};
		http.send(msg);
	}catch(e){
		if(http)http.abort();
		http=null;
		alert("error3"+e.message);
	}

}
Runtime.prototype.sendFlexCmd = function(serverUri,xmlText,func){
		//var obj =document.getElementById(serverUri);
		var obj =jmask.runtime.getFlashObj(serverUri);
		jmask.runtime.sendFlexXmlCmd(serverUri,xmlText,obj,func);
}
Runtime.prototype.getFlashObj= function(objname){
	if(window.document[objname]){
		return window.document[objname];
	}
	if(navigator.appName.indexOf("Microsoft Internet")==-1){
		if(document.embeds&&document.embeds[objname])
			return document.embeds[objname];
	}else{
		return document.getElementById(objname);
	}
}

jmask.runtime = new Runtime();

