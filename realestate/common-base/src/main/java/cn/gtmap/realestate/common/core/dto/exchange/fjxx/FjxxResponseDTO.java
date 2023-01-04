package cn.gtmap.realestate.common.core.dto.exchange.fjxx;

import java.util.List;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/4/7 13:56
 * @description
 */
public class FjxxResponseDTO {

    private String slbh;

    private String bdcqzh;

    private List<FjxxDTO> file;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public List<FjxxDTO> getFile() {
        return file;
    }

    public void setFile(List<FjxxDTO> file) {
        this.file = file;
    }
}
