package cn.gtmap.realestate.exchange.service.impl.national.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.GmxaDzzzDataParam;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse.GmxaDzzzKeySignReponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request.GmxaDzzzKeyWordSignReq;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.service.national.dzzz.DzqzGmxaKeySignService;
import com.alibaba.fastjson.JSON;

/**
 * 文件签章
 * @author wangyinghao
 */
public class DzqzGmxaKeySignServiceImpl implements DzqzGmxaKeySignService {
    public static final String TYPE = "文件关键字签章";
    public static final String PATH = "/openKeySign";
    private DzqzGmxaData dzqzGmxaData;
    private GmxaDzzzKeySignReponse gmxaDzzzKeySignReponse;
    private GmxaDzzzKeyWordSignReq gmxaDzzzKeyWordSignReq;
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
        //生成配置
        GmxaDzzzDataParam gmxaDzzzDataParam = dzqzGmxaData.getGmxaDzzzConfig();
        this.url = dzqzGmxaData.getUrl() + PATH;
        GmxaDzzzKeyWordSignReq gmxaDzzzKeyWordSignReq = new GmxaDzzzKeyWordSignReq();
        gmxaDzzzKeyWordSignReq.setTimestamp(gmxaDzzzDataParam.getTimestamp());
        gmxaDzzzKeyWordSignReq.setDocumentName(gmxaDzzzDataParam.getDocumentName());
        gmxaDzzzKeyWordSignReq.setDocumentPath(gmxaDzzzDataParam.getDocumentPath());
        gmxaDzzzKeyWordSignReq.setSiteId(gmxaDzzzDataParam.getSiteId());
        gmxaDzzzKeyWordSignReq.setOutUserId(gmxaDzzzDataParam.getOutUserId());
        gmxaDzzzKeyWordSignReq.setTitle(gmxaDzzzDataParam.getTitle());
        gmxaDzzzKeyWordSignReq.setSignatoriesList(gmxaDzzzDataParam.getSignatoriesList());
        this.gmxaDzzzKeyWordSignReq = gmxaDzzzKeyWordSignReq;
    }

    /**
     * 发送请求
     */
    @Override
    public GmxaDzzzKeySignReponse send() {
        DzqzGmxaService dzqzGmxaService = (DzqzGmxaService) Container.getBean("dzqzGmxaService");
        try {
            String responseStr = dzqzGmxaService.commonPost(gmxaDzzzKeyWordSignReq, buildRequestUrl(), TYPE);
            gmxaDzzzKeySignReponse = JSON.parseObject(responseStr, GmxaDzzzKeySignReponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            GmxaDzzzKeySignReponse gmxaDzzzKeySignReponse = new GmxaDzzzKeySignReponse();
            gmxaDzzzKeySignReponse.setSuccess(false);
            gmxaDzzzKeySignReponse.setErrorMsg(e.getMessage());
            this.gmxaDzzzKeySignReponse = gmxaDzzzKeySignReponse;
        }
        return gmxaDzzzKeySignReponse;
    }

    /**
     * @return
     */
    @Override
    public Boolean isSuccess() {
        return gmxaDzzzKeySignReponse.isSuccess();
    }

    /**
     * 拼接请求地址
     * @return String
     */
    private String buildRequestUrl() {
        String urlWithParam = this.url + "?";
        urlWithParam += "siteId=" + this.gmxaDzzzKeyWordSignReq.getSiteId() ;
        return urlWithParam;
    }
}
