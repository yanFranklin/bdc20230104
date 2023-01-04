package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/4/4
 * @description 收件材料DTO
 */
public class BdcSlSjclDTO extends BdcSlSjclDO implements Serializable {
    private static final long serialVersionUID = -675837498014654821L;
    /**
     * 收件材料下载地址
     */
    private String sjcldz;

    private String sjlxmc;

    List<BdcSlSjclDTO> childrenList;

    public String getSjcldz() {
        return sjcldz;
    }

    public void setSjcldz(String sjcldz) {
        this.sjcldz = sjcldz;
    }

    public String getSjlxmc() {
        return sjlxmc;
    }

    public void setSjlxmc(String sjlxmc) {
        this.sjlxmc = sjlxmc;
    }

    public List<BdcSlSjclDTO> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<BdcSlSjclDTO> childrenList) {
        this.childrenList = childrenList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcSlSjclDTO.class.getSimpleName() + "[", "]")
                .add("sjcldz='" + sjcldz + "'")
                .add("sjlxmc='" + sjlxmc + "'")
                .add("childrenList=" + childrenList)
                .toString();
    }
}
