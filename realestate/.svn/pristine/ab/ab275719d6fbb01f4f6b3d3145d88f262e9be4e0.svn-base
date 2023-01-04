package cn.gtmap.realestate.exchange.service.impl.national.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.GmxaDzzzDataParam;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse.GmxaDzzzUploadReponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request.GmxaDzzzUploadReq;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.service.national.dzzz.DzqzGmxaUploadService;
import com.alibaba.fastjson.JSON;

/**
 * 签章文件上传
 *
 * @author wangyinghao
 */
public class DzqzGmxaUploadServiceImpl implements DzqzGmxaUploadService {
    public static final String TYPE = "文件上传";
    public static final String PATH = "/fileUpload";
    private DzqzGmxaData dzqzGmxaData;
    private GmxaDzzzUploadReponse gmxaDzzzUploadReponse;
    private GmxaDzzzUploadReq gmxaDzzzUploadReq;
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
        GmxaDzzzUploadReq gmxaDzzzUploadReq = new GmxaDzzzUploadReq();
        this.url = dzqzGmxaData.getUrl() + PATH;
        gmxaDzzzUploadReq.setUserId(gmxaDzzzDataParam.getUserId());
        gmxaDzzzUploadReq.setSiteId(gmxaDzzzDataParam.getSiteId());
        gmxaDzzzUploadReq.setFileType(gmxaDzzzDataParam.getFileType());
        gmxaDzzzUploadReq.setFileDatas(gmxaDzzzDataParam.getFileDatas());
        this.gmxaDzzzUploadReq = gmxaDzzzUploadReq;
    }

    /**
     * 发送请求
     */
    @Override
    public GmxaDzzzUploadReponse send() {
        DzqzGmxaService dzqzGmxaService = (DzqzGmxaService) Container.getBean("dzqzGmxaService");
        try {
            String responseStr = dzqzGmxaService.commonPost(gmxaDzzzUploadReq, buildRequestUrl(), TYPE);
            gmxaDzzzUploadReponse = JSON.parseObject(responseStr,GmxaDzzzUploadReponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            GmxaDzzzUploadReponse gmxaDzzzUploadReponse = new GmxaDzzzUploadReponse();
            gmxaDzzzUploadReponse.setSuccess(false);
            gmxaDzzzUploadReponse.setErrorMsg(e.getMessage());
            this.gmxaDzzzUploadReponse = gmxaDzzzUploadReponse;
        }
        return gmxaDzzzUploadReponse;
    }

    /**
     * @return
     */
    @Override
    public Boolean isSuccess() {
        return gmxaDzzzUploadReponse.isSuccess();
    }

    /**
     * 拼接请求地址
     * @return String
     */
    private String buildRequestUrl() {
        String urlWithParam = this.url + "?";
        urlWithParam += "siteId=" + this.gmxaDzzzUploadReq.getSiteId();
        return urlWithParam;
    }
}
