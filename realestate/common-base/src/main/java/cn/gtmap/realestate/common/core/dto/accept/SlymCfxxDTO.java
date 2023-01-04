package cn.gtmap.realestate.common.core.dto.accept;

import java.util.List;

/**
 * @program: 3.0
 * @description: 手里页面展现查封信息DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-30 15:27
 **/
public class SlymCfxxDTO {
    /**
     * 不动产单元号 （一证多房的可能只是其中一个单元）
     */
    private String bdcdyh;

    /*
     * 查封信息
     * */
    private List<BdcCfDTO> bdcCfDOList;

    /*
     * 续封信息
     * */
    private List<BdcCfDTO> bdcXfxxList;

    /*
     * 轮候查封信息
     * */
    private List<BdcCfDTO> bdcLhcfList;

    /*
     * 轮候查封的续封信息
     * */
    private List<BdcCfDTO> bdcLhXfList;


    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public List<BdcCfDTO> getBdcCfDOList() {
        return bdcCfDOList;
    }

    public void setBdcCfDOList(List<BdcCfDTO> bdcCfDOList) {
        this.bdcCfDOList = bdcCfDOList;
    }

    public List<BdcCfDTO> getBdcXfxxList() {
        return bdcXfxxList;
    }

    public void setBdcXfxxList(List<BdcCfDTO> bdcXfxxList) {
        this.bdcXfxxList = bdcXfxxList;
    }

    public List<BdcCfDTO> getBdcLhcfList() {
        return bdcLhcfList;
    }

    public void setBdcLhcfList(List<BdcCfDTO> bdcLhcfList) {
        this.bdcLhcfList = bdcLhcfList;
    }

    public List<BdcCfDTO> getBdcLhXfList() {
        return bdcLhXfList;
    }

    public void setBdcLhXfList(List<BdcCfDTO> bdcLhXfList) {
        this.bdcLhXfList = bdcLhXfList;
    }

    @Override
    public String toString() {
        return "SlymCfxxDTO{" +
                "bdcCfDOList=" + bdcCfDOList +
                ", bdcXfxxList=" + bdcXfxxList +
                ", bdcLhcfList=" + bdcLhcfList +
                ", bdcLhXfList=" + bdcLhXfList +
                '}';
    }
}
