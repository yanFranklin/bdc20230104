package cn.gtmap.realestate.exchange.service.impl.national.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.GmxaDzzzDataParam;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse.GmxaDzzzDownloadReponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request.GmxaDzzzDownloadReq;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.exchange.service.national.dzzz.DzqzGmxaDownloadService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 文件下载
 *
 * @author wangyinghao
 */
public class DzqzGmxaDownloadServiceImpl implements DzqzGmxaDownloadService {
    public static final String TYPE = "文件下载";
    public static final String PATH = "/downFile";
    private DzqzGmxaData dzqzGmxaData;
    private GmxaDzzzDownloadReponse gmxaDzzzDownloadReponse;
    private GmxaDzzzDownloadReq gmxaDzzzDownloadReq;
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
        //
        this.url = dzqzGmxaData.getUrl() + PATH;
        GmxaDzzzDataParam gmxaDzzzDataParam = dzqzGmxaData.getGmxaDzzzConfig();
        GmxaDzzzDownloadReq gmxaDzzzDownloadReq = new GmxaDzzzDownloadReq();
        gmxaDzzzDownloadReq.setDocumentPath(gmxaDzzzDataParam.getDocumentPath());
        gmxaDzzzDownloadReq.setSiteId(gmxaDzzzDataParam.getSiteId());
        this.gmxaDzzzDownloadReq = gmxaDzzzDownloadReq;
    }

    /**
     * 发送请求
     */
    @Override
    public GmxaDzzzDownloadReponse send() {
        DzqzGmxaService dzqzGmxaService = (DzqzGmxaService) Container.getBean("dzqzGmxaService");
        GmxaDzzzDownloadReponse gmxaDzzzDownloadReponse = new GmxaDzzzDownloadReponse();
        try {
            InputStream inputStream = dzqzGmxaService.sreramGet(buildRequestUrl(), TYPE);
            gmxaDzzzDownloadReponse.setSuccess(true);
            gmxaDzzzDownloadReponse.setFileContent(Base64Utils.encodeByteToBase64Str(IOUtils.toByteArray(inputStream)));
        } catch (Exception e) {
            e.printStackTrace();
            gmxaDzzzDownloadReponse.setSuccess(false);
            gmxaDzzzDownloadReponse.setErrorCode(e.getMessage());
        }
        this.gmxaDzzzDownloadReponse = gmxaDzzzDownloadReponse;
        return gmxaDzzzDownloadReponse;
    }

    /**
     * 是否成功
     * @return Boolean
     */
    @Override
    public Boolean isSuccess() {
        return gmxaDzzzDownloadReponse.isSuccess();
    }

    /**
     * 拼接请求地址
     * @return String
     */
    private String buildRequestUrl() {
        String urlWithParam = this.url + "?";
        urlWithParam += "siteId=" + this.gmxaDzzzDownloadReq.getSiteId() + "&";
        urlWithParam += "documentPath=" + this.gmxaDzzzDownloadReq.getDocumentPath() + "&";
        return urlWithParam;
    }
}
