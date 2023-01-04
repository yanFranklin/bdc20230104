package cn.gtmap.realestate.exchange.service.sw;

import com.nkstar.lsjkclient.bean.nklsjkResponse;
import com.nkstar.lsjkclient.exception.NKLsjkException;
import com.nkstar.lsjkclient.util.DesCryptBase64;
import com.nkstar.lsjkclient.util.WSHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
/**
 * @description 税务客户端工具类
 *              原有NKlsjkClient读取nklsjkclient.properties，每次打包都要修改jar包中的配置文件，不够人性化，故重新写个Client读取apollo配置
 * @author: jinfei
 * @email: jinfei@gtmap.cn
 * @date: 2022/10/13 20:26
 **/
@Service
public class NKlsjkClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NKlsjkClientService.class);

    private nklsjkResponse m_resp;

    @Value("${nklsjk.server_url:http://2.139.95.8:8101/open/yc-api/fdcythzs?masterKey=zYadMYveyqdL9QbVdLPd}")
    private String serverUrl;
    @Value("${nklsjk.client_id:lsjk.services.JSSW.FCJY}")
    private String clientId;

    public NKlsjkClientService() {
    }

    public nklsjkResponse getResponse(String serviceMethod, String postData) {

        LOGGER.info("nklsjk配置，serviceUrl={},clientId={}", serverUrl, clientId);
        String url = serverUrl;
        String id = clientId;
        if (this.m_resp == null) {
            this.m_resp = new nklsjkResponse();
        }

        this.m_resp.clear();
        this.m_resp.setServiceMethod(serviceMethod);
        if (url != null && url.length() != 0) {
            if (id != null && id.length() != 0) {
                try {
                    boolean isb64 = true;

                    try {
                        postData = (new BASE64Encoder()).encode(postData.getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException var7) {
                        isb64 = false;
                        var7.printStackTrace();
                    }

                    this.m_resp.setRequestData(DesCryptBase64.encrypt(postData, id));
                    this.m_resp.setResponseData(WSHttpClient.postData(url, this.m_resp, isb64));
                    this.m_resp.setResponseMessage("获取数据成功");
                } catch (NKLsjkException var8) {
                    var8.printStackTrace();
                    this.m_resp.setResponseCode(var8.getErrorCode());
                    this.m_resp.setResponseMessage(var8.getErrorMessage());
                }

                return this.m_resp;
            } else {
                this.m_resp.setResponseCode(-11);
                this.m_resp.setResponseMessage("未设置客户端ID，没有权限。");
                return this.m_resp;
            }
        } else {
            this.m_resp.setResponseCode(-10);
            this.m_resp.setResponseMessage("未设置服务器地址");
            return this.m_resp;
        }
    }
}
