(function(root){
	var R_FAIL      = -1;         //函数执行失败
	var R_OK        = 0x00000000; //函数执行成功
	var R_NOKEY     = 0x00000001; //没有检测到电子钥匙盘
	var R_NOUNIT    = 0x00000002; //没有进行正确初始化的电子钥匙盘
	var R_NODRIVER  = 0x00000003; //没有找到电子钥匙盘的驱动文件
	var R_KEYLONG   = 0x00000004; //电脑上插入的电子钥匙盘太多
	var R_NOUSERKEY = 0x00000005; //没有插入用户的电子钥匙盘
	var R_NOSOKEY   = 0x00000006; //没有插入管理员电子钥匙盘
	var R_ERRORPIN  = 0x00000007; //错误的PIN码
	var R_NOTLAW    = 0x00000008; //非法的钥匙盘
	var R_NOSIGNIMG = 0x00000009; //没有签章图片可以制作
	var R_SAVEERROR = 0x0000000A; //写入签章图片数据失败
	var R_NOSIGN    = 0x0000000B; //钥匙盘里不存在签章
	var R_LOADERROR = 0x0000000C; //读取签章图片数据失败
	var R_DELERROR  = 0x0000000D; //删除签章图片数据失败
	var R_NOSAMESIGN= 0x0000000E; //钥匙盘里不存在该签章
	var R_NOSPACE   = 0x0000000F; //钥匙盘剩余空间不足

	//获取密钥盘读取错误  
	function GetKeyError(mResult){
		var key = mResult;
		if(typeof mResult === 'string') key = parseInt(mResult);
	    switch(key)   {
	      	case R_FAIL:
	        	return "函数执行失败";
	      	case R_OK:
	        	return "函数执行成功";
	      	case R_NOKEY:
	        	return "没有检测到电子钥匙盘";
	      	case R_NOUNIT:
	        	return "没有进行正确初始化的电子钥匙盘";
	      	case R_NODRIVER:
	        	return "没有找到电子钥匙盘的驱动文件";
	      	case R_KEYLONG:
	        	return "电脑上插入的电子钥匙盘太多";
	      	case R_NOSOKEY:
	        	return "没有插入管理员电子钥匙盘";
	      	case R_NOUSERKEY:
	        	return "没有插入用户电子钥匙盘";
	      	case R_ERRORPIN:
	        	return "密码错误！";
	      	case R_NOTLAW:
	        	return "非法的钥匙盘";
	      	case R_NOSIGNIMG:
	        	return "没有签章图片可以制作";
	      	case R_SAVEERROR:
	        	return "写入签章图片数据失败";
	      	case R_NOSIGN:
	        	return "钥匙盘里不存在签章";
	      	case R_LOADERROR:
	        	return "读取签章图片数据失败";
	      	case R_DELERROR:
	        	return "删除签章图片数据失败";
	      	case R_NOSAMESIGN:
	        	return "钥匙盘里不存在该签章";
	      	case R_NOSPACE:
	        	return "钥匙盘剩余空间不足";
	      	default:
	        	return "未知信息类型";
	    }
	}	

	var iSignatureManage = function(){
		this.kg = new KG('iSignatureManage.SignatureManageCtrl' , '6f30ecd2-bd70-4496-9f788d9ed014724e');
		this.promise = this.kg.init();
	};

	iSignatureManage.prototype = {
		getKeysn : function(){
			var kg = this.kg; 
			var capability = createCapability();
			var obj = {};
			kg.invoke("WebCreateKeyObject").then(function(){
				return kg.invoke("WebOpenKeyObject");
			}).then(function(){
				return kg.invoke("WebGetKeyCount");
			}).then(function(nKeyCount){
				if(nKeyCount == 0){
					kg.invoke('WebDeleteKeyObject').then(function(){
						obj.result = false;
						obj.message = "请插入密钥盘！";
						capability.resolve(obj);
					});
					return;
				}
				if (nKeyCount > 1) {
					kg.invoke('WebDeleteKeyObject').then(function(){
						obj.result = false;
						obj.message = "只允许插入一个密钥盘！";
						capability.resolve(obj);
					});
					return;
				} 
				kg.invoke("WebConnectKey", 0).then(function(){
					return kg.invoke("WebGetKeySerialNumber")
				}).then(function(data){
					kg.invoke('WebDeleteKeyObject').then(function(){
						obj.result = true;
						obj.message = data;
						capability.resolve(obj);
					});
				});
			});
			return capability.promise;
		},
		
		getSeal:function(capability,size,obj,index){
			/*
			16、HeadInformations (接口)
			功能描述：调用WebLoadSign 后，获取单个签章扩展数据接口。
			参数：Index: INT；获取签章索引号（从0 开始）
			返回值：IHeadInformations 接口
			IHeadInformations 接口属性如下：
			Position：BSTR； 签章在KEY 内存储的位置信息
			SerialNumber：BSTR；签章的序列号
			UserName： BSTR；签章的持有人用户名称
			SignName： BSTR；签章的签章名称
			SignPass：BSTR；签章密码
			SignExt：BSTR；签章图片扩展名
			Width：BSTR；签章图片宽度
			Height：BSTR；签章图片高度
			ImgValue：BSTR；签章图片信息
			KeySN： BSTR；签章KEY 序列号
			UnitName： BSTR；签章KEY 单位名称
			ImgTag : 公私章标识，0公章 1私章
			调 用 ：.HeadInformations(0).ImgValue；
			*/
			var kg = this.kg;
			var self = this;
			kg.invoke("HeadInformations",index).then(function(sealObj){
				obj.message.push(sealObj);
				if(index == size-1){
					capability.resolve(obj);
				} else {
					self.getSeal(capability,size,obj,index++);
				}
			});
		},
		
		getSeals : function(){
			var kg = this.kg; 
			var capability = createCapability();
			var obj = {};
			var self = this;
			kg.invoke("WebLoadSign",true).then(function(mResult){
				if(mResult != 0){
					obj.message = GetKeyError(mResult);
					obj.result = false;
					capability.resolve(obj);
					return;
				}
				kg.get("SignCount").then(function(size){
					obj.result = true;
					obj.message = [];
					self.getSeal(capability,size,obj,0);
				});
			});
			return capability.promise;
		}
	};
	
	root.iSignatureManage = iSignatureManage;
})(window)