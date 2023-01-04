package cn.gtmap.realestate.common.core.dto.register;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/3/17
 * @description 使用期限批量更新实体
 */
public class BdcSyqxPlDTO {

    /**
     * 更新实体
     */
    private JSONObject jsonObject;

    /**
     * 工作流实例ID
     */
    private String gzlslid;

    /**
     * 项目ID集合
     */
    private List<String> xmidList;

    /**
     * 权利类名
     */
    private String qlclassName;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public String getQlclassName() {
        return qlclassName;
    }

    public void setQlclassName(String qlclassName) {
        this.qlclassName = qlclassName;
    }
}
