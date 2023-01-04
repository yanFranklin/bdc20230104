package cn.gtmap.realestate.exchange.service.impl.national.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.GmxaDzzzDataParam;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse.GmxaDzzzVerifyReponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request.GmxaDzzzSianValReq;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.service.national.dzzz.DzqzGmxaVerifyService;
import com.alibaba.fastjson.JSON;

/**
 * 文件验证
 *
 * @author wangyinhao
 */
public class DzqzGmxaVerifyServiceImpl implements DzqzGmxaVerifyService {
    public static final String TYPE = "文件验证";
    public static final String PATH = "/verifyDoc";
    private DzqzGmxaData dzqzGmxaData;
    private GmxaDzzzVerifyReponse gmxaDzzzVerifyReponse;
    private GmxaDzzzSianValReq gmxaDzzzSianValReq;
    private String url;

    /**
     * 初始化
     * @param dzqzGmxaData 配置
     */
    @Override
    public void init(DzqzGmxaData dzqzGmxaData) {
        this.dzqzGmxaData = dzqzGmxaData;
    }

    /**
     * 构建请求
     */
    @Override
    public void buildRequest() {
        GmxaDzzzDataParam gmxaDzzzDataParam = dzqzGmxaData.getGmxaDzzzConfig();
        this.url = dzqzGmxaData.getUrl() + PATH;
        GmxaDzzzSianValReq gmxaDzzzSianValReq = new GmxaDzzzSianValReq();
        gmxaDzzzSianValReq.setSiteId(gmxaDzzzDataParam.getSiteId());
        gmxaDzzzSianValReq.setFile(gmxaDzzzDataParam.getFile());
        gmxaDzzzSianValReq.setTimestamp(gmxaDzzzDataParam.getTimestamp());
        this.gmxaDzzzSianValReq = gmxaDzzzSianValReq;
    }

    /**
     * 发送请求
     */
    @Override
    public GmxaDzzzVerifyReponse send() {
        DzqzGmxaService dzqzGmxaService = (DzqzGmxaService) Container.getBean("dzqzGmxaService");
        try {
            String responseStr = dzqzGmxaService.commonPost(gmxaDzzzSianValReq, buildRequestUrl(), TYPE);
            gmxaDzzzVerifyReponse = JSON.parseObject(responseStr, GmxaDzzzVerifyReponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            GmxaDzzzVerifyReponse gmxaDzzzVerifyReponse = new GmxaDzzzVerifyReponse();
            gmxaDzzzVerifyReponse.setSuccess(false);
            gmxaDzzzVerifyReponse.setErrorMsg(e.getMessage());
            this.gmxaDzzzVerifyReponse = gmxaDzzzVerifyReponse;
        }
        return gmxaDzzzVerifyReponse;
    }

    /**
     * @return
     */
    @Override
    public Boolean isSuccess() {
        return gmxaDzzzVerifyReponse.isSuccess();
    }

    /**
     * 拼接请求地址
     * @return String
     */
    private String buildRequestUrl() {
        String urlWithParam = this.url + "?";
        urlWithParam += "siteId=" + this.gmxaDzzzSianValReq.getSiteId() ;
        return urlWithParam;
    }
}
