package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/11/19
 * @description 权利其他状况、附记操作对象
 */
public class BdcQlqtzkFjQO {
    @ApiModelProperty("结果Json字符串值")
    private String jsonStr;

    @ApiModelProperty("需要操作的模板类型，2 : 权利其他状况 3 : 附记")
    private List<String> modeList;

    @ApiModelProperty("是否批量更新")
    private Boolean plgx;

    public Boolean getPlgx() {
        return plgx;
    }

    public void setPlgx(Boolean plgx) {
        this.plgx = plgx;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public List<String> getModeList() {
        return modeList;
    }

    public void setModeList(List<String> modeList) {
        this.modeList = modeList;
    }

    @Override
    public String toString() {
        return "BdcQlqtzkFjQO{" +
                "jsonStr='" + jsonStr + '\'' +
                ", modeList=" + modeList +
                ", plgx=" + plgx +
                '}';
    }
}
