package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy;

import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitFjxxDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitSjrxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitWlxx;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-06
 * @description
 */
public class InitRequestDTO {

    private List<InitRequestData> sqxx;

    private List<InitFjxxDTO> fjxx;

    private List<InitSjrxx> wlxx;

    public List<InitRequestData> getSqxx() {
        return sqxx;
    }

    public void setSqxx(List<InitRequestData> sqxx) {
        this.sqxx = sqxx;
    }

    public List<InitFjxxDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<InitFjxxDTO> fjxx) {
        this.fjxx = fjxx;
    }

    public List<InitSjrxx> getWlxx() {
        return wlxx;
    }

    public void setWlxx(List<InitSjrxx> wlxx) {
        this.wlxx = wlxx;
    }
}
