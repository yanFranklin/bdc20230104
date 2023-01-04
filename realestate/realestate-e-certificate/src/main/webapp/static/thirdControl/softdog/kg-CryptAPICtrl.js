(function(root){
	
	var cryptAPICtrl = function(){
		this.kg = new KG('KG_Crypt_COM_API.CryptAPICtrl.1','FD5FA7DB-CBF7-4804-8147DE5A56CC8ADF');
		this.promise = this.kg.init();
	};

	cryptAPICtrl.prototype = {
		getCert : function(cspName){
			var kg = this.kg;
			var capability = createCapability();
			var self = this;
			this.getCtnArray(cspName).then(function(ctnArray){
				self.begin(cspName).then(function(keselect){
					return kg.invoke('KGCryptExportKey',6, 1, 0);
				}).then(function(pubkey){
					kg.invoke('KGCryptDestroyKey').then(function(){
						return kg.invoke('KGCryptFinalize',0);
					}).then(function(){
						capability.resolve(pubkey);
					});
				})
			});
			return capability.promise;
		},
		
		begin: function(cspName){
			var kg = this.kg;
			var capability = createCapability();
			this.getCtnArray(cspName).then(function(ctnArray){
				return kg.invoke('KGCryptInitialize',cspName, ctnArray[0], 1, 0);
			}).then(function(){
				alert("2");
				return kg.invoke('KGCryptGetUserKey',2);
			}).then(function(ret){
				if(ret == 0){
					alert("3");
					capability.resolve(2);
				} else {
					alert("4");
					kg.invoke('KGCryptGetUserKey',1).then(function(ret2){
						if(ret2 == 0) {
							alert("5");
							capability.resolve(1);
						} else {
							alert("6");
							capability.resolve(-1);
						}
					});
				}
			});
			return capability.promise;
		},
		
		sig: function(key,needSigData){
			var kg = this.kg;
			var capability = createCapability();
			kg.invoke('KGCryptCreateHash',0x00008004).then(function(){
				return kg.invoke('KGCryptSetHashValue',needSigData,needSigData.length);
			}).then(function(){
				kg.time = 45000;
				return kg.invoke('KGCryptSignHash',key,0);
			}).then(function(sigData){
				kg.invoke('KGCryptDestroyHash').then(function(){
					if(sigData === "none"){
						// 取消签名
						capability.reject();
					} else {
						capability.resolve(sigData);
					}
				})
			});
			return capability.promise;
		},
		
		exportCert: function(){
			return this.kg.invoke('KGCryptExportKey', 0, 1, 0);
		},
		
		end: function(){
			var kg = this.kg;
			var capability = createCapability();
			kg.invoke('KGCryptFinalize',0).then(function(){
				capability.resolve(0);
			});
			return capability.promise;
		},
		
		setKeyPwd : function(pwd){
			if(pwd === undefined) pwd = "";
			var hexPwd = toHex(pwd) + "00";
			return this.kg.invoke('KGCryptSetPin',hexPwd,hexPwd.length,32);
		},
		
		getCtnArray : function(cspName){
			var kg = this.kg;
			var self = this;
			var capability = createCapability();
			alert(1);
			kg.invoke('KGCryptInitialize',cspName, "", 1, 0xF0000000).then(function(){
				return kg.invoke('KGCryptGetContainers',0);
			}).then(function(ctns){
				kg.invoke('KGCryptFinalize' ,0).then(function(){
					self.end().then(function(){
						capability.resolve(ctns.split("$n"));
					});
				});
			});
			alert("1-1"+capability.promise.toString());
			return capability.promise;
		}
	};
	
	function toHex(str) {
		var hex = '';
		for(var i=0;i<str.length;i++) {
			hex += str.charCodeAt(i).toString(16);
		}
		return hex;
	}

	/*
	 * cspName ： 密钥盘的cspName
	 * keyPwd : 密钥盘密码，值为undefined时，驱动弹出密码框输入密码。
	 * funcHash : 函数，必须返回promise对象。
	 */
	var KGSig = function (cspName,keyPwd,funcHash){
		var cryptAPI = new cryptAPICtrl();
		var capability = createCapability();
		var obj = {result:false};
		Promise.all([cryptAPI.promise]).then(function(){
			return cryptAPI.begin(cspName);
		}).then(function(keyNumber){
			capability.promise.then(function(){
				cryptAPI.end();
			},function(){
				cryptAPI.end();
			});
			if(keyNumber == -1){
				obj.message = "请插入密钥盘或者密钥盘中不存在证书！";
				capability.resolve(obj);
				return;
			}
			
			var p;
			if(keyPwd === undefined){
				p = Promise.resolve(0);
			} else {
				p = cryptAPI.setKeyPwd(keyPwd);
			}
			p.then(function(pwdret){
				if(pwdret != 0){
					obj.message = "密钥盘密码错误或密钥盘被锁！";
					capability.resolve(obj);
					return;
				} 
				cryptAPI.exportCert().then(function(cert){
					if(cert == "none"){
						obj.message = "获取签名证书失败！";
						capability.resolve(obj);
						return;
					} 
					funcHash(cert).then(function(pdfObjects){
						var _capability = createCapability();
						var recursion = function (pdfarray,index){
							var pdfobj = pdfarray[index];
							cryptAPI.sig(keyNumber,pdfobj.needSigMessage).then(function(sigData){
								pdfobj.sigData = sigData;
								if(index == pdfarray.length-1){
									_capability.resolve(pdfarray);
								} else {
									recursion(pdfarray,index+1);
								}
							},function(){
								obj.warnning = "取消签名！";
								capability.resolve(obj);
							});
						};
						recursion(pdfObjects,0);
						_capability.promise.then(function(sigedData){
							obj.result = true;
							obj.message = {
									"cert" : cert,
									"sigedData" : sigedData
							};
							capability.resolve(obj);
						});
					},function(msg){
						obj.message = msg;
						capability.resolve(obj);
					});
				});
			});
		});
		return capability.promise;
	}

	root.cryptAPISig = KGSig;
	root.cryptAPICtrl = cryptAPICtrl;
})(window)