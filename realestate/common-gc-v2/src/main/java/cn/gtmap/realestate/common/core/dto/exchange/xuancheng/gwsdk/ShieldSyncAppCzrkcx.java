package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gwsdk;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.enums.SignType;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.http.BaseApp;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;

public class ShieldSyncAppCzrkcx extends BaseApp {

    public ShieldSyncAppCzrkcx() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        // 管理平台应用查看处获取并修改
        this.appId = "7def46fd557944e4aad2bc75052c40d0";
         // 管理平台应用查看处获取并修改
        this.appSecret = "C926036B5DA0085E623FD3AC09F06318";
         // 核心层ip
        this.host = "59.203.5.92";
        // 核心层暴露的http端口
        this.httpPort = 80;
        // 核心层暴露的https端口
        this.httpsPort = 6443;
         // sdk生成时选择的环境 RELEASE=线上  TEST=测试 PRE=预生产
        this.stage = "RELEASE";
         // 此参数暂时无用
        this.equipmentNo = "XXX";
         // 此参数暂时无用
        this.signStrategyUrl = "/getSignStrategy";
         // 此参数暂时无用
        this.tokenUrl = "/getTokenUrl";
        // 管理平台应用查看处获取并修改
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100A7086C8509830ADF3CA7279C652A4CD6E1FCFED1C09E189310CCA50E020B0D5021346E315DFABF3D95BCE2515E784003FE5A9F7DEB3700447CCF78A49A1EA0AD0203010001";
        // 关闭云锁验证
        this.icloudlockEnabled = false;
    }


  
    /**
    * Version:202007291119561910
    */
    public ApiResponse Czrkcx(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP,
                Method.POST,
                "/api/6ADDAF46C16E4E299E383CF2D3227D04",
                SdkConstant.AUTH_TYPE_ENCRYPT,
                SignType.DEFAULT.name(),
                "1");
        apiRequest.setBody(body);
        
        return syncInvoke(apiRequest);
    }
   
};