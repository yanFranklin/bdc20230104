package cn.gtmap.realestate.common.core.dto.analysis;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description 不动产信息接口
 */
public class BdcResponseDTO {

    private BdcResponseHead head;

    private Object data;

    public BdcResponseHead getHead() {
        return head;
    }

    public void setHead(BdcResponseHead head) {
        this.head = head;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
