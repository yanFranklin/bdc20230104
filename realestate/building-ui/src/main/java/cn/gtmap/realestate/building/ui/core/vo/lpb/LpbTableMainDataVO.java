package cn.gtmap.realestate.building.ui.core.vo.lpb;

import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-30
 * @description
 */
public class LpbTableMainDataVO {

    private String wlcs;

    private String dycs;

    private List[] dyFwList;

    public LpbTableMainDataVO(String wlcs,String dycs){
        this.wlcs = wlcs;
        this.dycs = dycs;
        this.dyFwList = new List[2];
        this.dyFwList[0] = new ArrayList();
        this.dyFwList[1] = new ArrayList();
    }

    public String getWlcs() {
        return wlcs;
    }

    public void setWlcs(String wlcs) {
        this.wlcs = wlcs;
    }

    public String getDycs() {
        return dycs;
    }

    public void setDycs(String dycs) {
        this.dycs = dycs;
    }

    public List[] getDyFwList() {
        return dyFwList;
    }

    public void setDyFwList(List[] dyFwList) {
        this.dyFwList = dyFwList;
    }
}
