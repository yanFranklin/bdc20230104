package cn.gtmap.realestate.common.core.dto.certificate;

import java.util.List;
import java.util.Map;

/**
 * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description  发证记录领证人签字信息DTO
 */
public class BdcFzjlLzrQzxxDTO {
    /**
     * 项目ID
     */
    private String xmid;
    /**
     * 证书ID
     */
    private String zsid;

    /**
     * 领证人签字图片Base64编码内容
     */
    private String signStreamData;

    /**
     * 领证人分别签字内容， key: 领证人顺序号 value: 签字图片Base64编码内容
     */
    private Map<Integer, String> signStreamData2;

    /**
     * 工作流实例ID集合（用于批量更新发证记录领证人签字信息）
     */
    private List<String> gzlslidList;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSignStreamData() {
        return signStreamData;
    }

    public void setSignStreamData(String signStreamData) {
        this.signStreamData = signStreamData;
    }

    public Map<Integer, String> getSignStreamData2() {
        return signStreamData2;
    }

    public void setSignStreamData2(Map<Integer, String> signStreamData2) {
        this.signStreamData2 = signStreamData2;
    }

    public List<String> getGzlslidList() {
        return gzlslidList;
    }

    public void setGzlslidList(List<String> gzlslidList) {
        this.gzlslidList = gzlslidList;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }
}
