(function(root){
	var TIEMOUT = 45000;
	function createCapability(){
		var capability = {};
		capability.promise = new Promise(function(resolve,reject){
			capability.resolve = resolve;
			capability.reject = reject;
		});
		return capability;
	}
	
	if(!window.kgAlert){
		var kgAlert = function(msg){
			alert(msg);
		};
		window.kgAlert = kgAlert;
	}

	var KG = function(progid,inter){
		this.url = "http://127.0.0.1:9581";
		this.progid = progid;
		this.inter = inter;
		this.caller = 0;
		this.time = TIEMOUT;
		this.instance = false;
	};
	
	KG.prototype = {
		init: function(){
			var self = this;
			var val = JSON.stringify({GetInterface:{progid:this.progid,interface:this.inter,instance:this.instance}});
			var capability = createCapability();
			$.ajax({  
				type: "get",  
				url: self.url + "/GetInterface?value=" + val, 
				timeout: self.time,
				jsonp: "hookback",
				dataType: "jsonp",
				success: function(data){
					self.caller = data.value;
					if(self.caller == 0){
						kgAlert(self.progid + "控价初始化失败！");
						return;
					}
					capability.resolve("加载成功");
				},  
				error: function(XMLHttpRequest, textStatus, errorThrown){ 
					if("timeout" === errorThrown){
						kgAlert("请启动金格组件服务！");
					} else {
						kgAlert(self.progid + "控价加载出现异常：" + errorThrown);
					}
					capability.reject();
				}  
			}); 
			return capability.promise;
		},
		
		invoke: function(method){
			var self = this;
			var params = [];
			if(arguments.length >= 2){
				for(var i=1;i<arguments.length;i++){
					var typeVal = typeof arguments[i];
					var val = arguments[i] != null ? arguments[i].toString() : "null";
					var data = null;
					if(typeVal === 'boolean'){
						data = {"type":"BOOL", "value": val};
					} else if(typeVal === 'number'){
						data = {"type":"LONG", "value": val};
					} else {
						data = {"type":"BSTR", "value": encodeURIComponent(val)};
					}
					params.push(data);
				}
			}

			var val = JSON.stringify( { MethodCall : { 'caller' : this.caller,
				                                       'function': method,
				                                       'params' : params }});
			var capability = createCapability();
			$.ajax({  
				type: "get",  
				url: self.url + "/MethodCall?value=" + val, 
				timeout: self.time,
				jsonp: "hookback",
				dataType: "jsonp",
				success: function(data){
					capability.resolve(data.value);
					self.time = TIEMOUT;
				},  
				error: function(XMLHttpRequest, textStatus, errorThrown){  
					var error = self.progid + "的方法(" + method + ")执行出现异常 : " + errorThrown;
					if("timeout" === errorThrown){
						error = "金格服务通讯超时，请尽快进行相关操作！";
					}
					self.time = TIEMOUT;
					kgAlert(error);
					capability.reject(error);
				}  
			}); 
			return capability.promise;
		},
		
		get: function(property){
			var val = JSON.stringify({PropertyCall : { 'caller' : this.caller,
				                                       'property' : property }});
			var capability = createCapability();
			var self = this;
			$.ajax({  
				type: "get",  
				url: self.url + "/PropCall?value=" + val, 
				timeout: self.time,
				jsonp: "hookback",
				dataType: "jsonp",
				success: function(data){
					capability.resolve(data.value);
				},  
				error: function(XMLHttpRequest, textStatus, errorThrown){  
					kgAlert(self.progid + "获取属性(" + property + ")出现异常 : " + errorThrown);
					capability.reject();
				}  
			}); 
			return capability.promise;
		},
		
		put:function(property,value){
			var self = this;
			var typeVal = typeof value;
			var _value = (value != undefined ? value.toString() : "null");
			var data = null;
			if(typeVal === 'boolean'){
				data = {"type":"BOOL", "value": _value};
			} else if(typeVal === 'number'){
				data = {"type":"LONG", "value": _value};
			} else {
				data = {"type":"BSTR", "value": encodeURIComponent(_value)};
			}
			var val = JSON.stringify({PropertyCallPut : { 'caller' : this.caller,
				                                          'property' : property,
				                                          'params' : [data]	}});
			var capability = createCapability();
			$.ajax({  
				type: "get",  
				url: self.url + "/PropCall?value=" + val, 
				timeout: self.time,
				jsonp: "hookback",
				dataType: "jsonp",
				success: function(data){
					capability.resolve(data.value);
				},  
				error: function(XMLHttpRequest, textStatus, errorThrown){  
					kgAlert(self.progid + "设置属性(" + property + ")出现异常 : " + errorThrown);
					capability.reject();
				}  
			}); 
			return capability.promise;
		},

		setUrl: function(url){
			this.url = url;
		}
		
	};

	window.KG = KG;
	window.createCapability = createCapability;
})(window)