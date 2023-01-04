package cn.gtmap.realestate.common.core.qo.engine;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/4/8
 * @description  流程关联的组合规则验证QO对象
 */
public class BdcGzLcQO {
    /**
     * 流程定义Key
     */
    private String processDefKey;
    /**
     * 不动产单元号集合
     */
    private List<String> bdcdyhList;


    public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }

    public List<String> getBdcdyhList() {
        return bdcdyhList;
    }

    public void setBdcdyhList(List<String> bdcdyhList) {
        this.bdcdyhList = bdcdyhList;
    }

    @Override
    public String toString() {
        return "BdcGzLcQO{" +
                "processDefKey='" + processDefKey + '\'' +
                ", bdcdyhList=" + bdcdyhList +
                '}';
    }
}
