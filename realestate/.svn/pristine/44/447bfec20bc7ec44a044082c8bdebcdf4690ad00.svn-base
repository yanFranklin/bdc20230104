(function(root){
	function getContainsCharNum(str,char1,num){
		var newNum = num;
		var newStr = str;
		if(str.indexOf(char1)>=0 && newNum<4){
			newNum++;
			newStr = str.substr(str.indexOf(char1)+1,str.length);
			newNum = getContainsCharNum(newStr,char1,newNum);
		}
		return newNum;
	}
	
	var iWebAssist = function(_conf){
		this.kg = new KG('IWEBASSIST.iWebAssistCtrl.1' , '4240FB41-A213-42B6-8CB5E6705C99B319');
		var capability = createCapability();
		this.promise = capability.promise;
		var self = this;
		this.kg.init().then(function(){
			var conf = _conf || {};
			if(conf.cilentType === "0"){
				self.kg.invoke("SetParamByName","SOFTTYPE","0").then(function(){
					capability.resolve();
				});
			} else {
				capability.resolve();
			}
		});
	};

	iWebAssist.prototype = {
		getCspName : function(){
			var kg = this.kg;
			var capability = createCapability();
			kg.invoke('KGGetPublicParm',"Public","CSPName","").then(function(strCspName){
				if(strCspName.indexOf("M")==0 && getContainsCharNum(strCspName,"W",0)>0){
					strCspName = strCspName.replace(strCspName.substring(strCspName.indexOf("M")+1,
													strCspName.indexOf("W")),"&");	
				}
				capability.resolve(strCspName);
			});
			return capability.promise;
		},
		
		getKeyInfo:function(){
			return this.kg.invoke('KGGetKeyInfo');
		},
		
		getCert : function(){
			var kg = this.kg;
			var capability = createCapability();
			kg.invoke('KGGetCerInfo','').then(function(data){
				capability.resolve(data);
			});
			return capability.promise;
		},
		
		sig_pkcs7 : function(pwd,message,type){
			if(type === "Base64"){
				return this.kg.invoke('KGCrySignMessageEX',pwd,message,message.length,"Base64");
			} else {
				return this.kg.invoke('KGCrySignMessage',pwd,message,message.length);
			}
		}
		
	};
	root.iWebAssist = iWebAssist;
})(window)