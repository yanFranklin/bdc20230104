package cn.gtmap.realestate.exchange.core.dto.nantong.sw.fjxxDistinguishSfts;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/6/23
 * @description 提供给税务附件
 */
public class FjxxDistinguishSftsDTO {

    /**
     * slbh :
     * fjxx : [{"clmc":"申请书","ys":"2","fs":"1","clnr":[{"fjid":"ff8080816da4d23f016da5cd4865018f","fjurl":"http://127.0.0.1:8080/fileCenter/file/get.do?token=whosyourdaddy&fid=2929865","fjnr":"","fjmc":"申请书.jpg"}],"mrfjys":"1","fjlx":"复印件"}]
     */

    private String slbh;
    private List<FjxxBeanSfts> fjxx;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<FjxxBeanSfts> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<FjxxBeanSfts> fjxx) {
        this.fjxx = fjxx;
    }


}
