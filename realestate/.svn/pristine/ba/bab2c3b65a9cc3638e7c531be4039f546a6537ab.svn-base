package cn.gtmap.realestate.exchange.core.dto.yctb;

import cn.gtmap.realestate.common.util.ParamUtil;

import java.io.Serializable;

public class YctbZxcdSqrRequest implements Serializable {

    private static final long serialVersionUID = -9033557312989473461L;

    private String name; //string Y 姓名
    private String zjh; //string Y 证件号

    public void checkParam(){
        ParamUtil.nonNull(this.name,"姓名不能为空");
        ParamUtil.nonNull(this.zjh,"证件号不能为空");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }
}
