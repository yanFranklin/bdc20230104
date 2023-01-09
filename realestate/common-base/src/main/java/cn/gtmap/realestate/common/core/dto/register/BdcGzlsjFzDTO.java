package cn.gtmap.realestate.common.core.dto.register;

import java.util.List;

/**
 * @program: bdcdj3.0
 * @description: 工作流事件复制DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2023-01-04 11:06
 **/
public class BdcGzlsjFzDTO {

    private String fzlcdyid;

    private List<String> jdmcList;

    private List<String> addlcdyidList;

    public String getFzlcdyid() {
        return fzlcdyid;
    }

    public void setFzlcdyid(String fzlcdyid) {
        this.fzlcdyid = fzlcdyid;
    }

    public List<String> getJdmcList() {
        return jdmcList;
    }

    public void setJdmcList(List<String> jdmcList) {
        this.jdmcList = jdmcList;
    }

    public List<String> getAddlcdyidList() {
        return addlcdyidList;
    }

    public void setAddlcdyidList(List<String> addlcdyidList) {
        this.addlcdyidList = addlcdyidList;
    }

    @Override
    public String toString() {
        return "BdcGzlsjFzDTO{" +
                "fzlcdyid='" + fzlcdyid + '\'' +
                ", jdmcList=" + jdmcList +
                ", addlcdyidList=" + addlcdyidList +
                '}';
    }
}
