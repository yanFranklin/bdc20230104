package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description:
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-31 10:57
 **/
public class DzzzJyclxxResponseDTO implements Serializable {
    private static final long serialVersionUID = -3566527708138073342L;

    private List<DzzzJyclxxDTO> data;

    private String flag;

    private String result;

    private BdcQlrDO bdcQlrDO;

    public BdcQlrDO getBdcQlrDO() {
        return bdcQlrDO;
    }

    public void setBdcQlrDO(BdcQlrDO bdcQlrDO) {
        this.bdcQlrDO = bdcQlrDO;
    }

    public List<DzzzJyclxxDTO> getData() {
        return data;
    }

    public void setData(List<DzzzJyclxxDTO> data) {
        this.data = data;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DzzzJyclxxResponseDTO{" +
                "data=" + data +
                ", flag='" + flag + '\'' +
                ", result='" + result + '\'' +
                ", bdcQlrDO=" + bdcQlrDO +
                '}';
    }
}
