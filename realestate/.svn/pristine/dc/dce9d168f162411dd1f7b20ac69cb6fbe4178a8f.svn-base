package cn.gtmap.realestate.building.core.bo;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-27
 * @description
 */
public class FwBgBO<T> {

    /**
     * 变更类型
     */
    private String bglx;

    /**
     * 变更编号
     */
    private String bgbh;

    /**
     * 原实体List
     */
    private List<T> yList;

    /**
     * 新实体List
     */
    private List<T> newList;

    /**
     * 主实体
     */
    private T mainObject;

    public FwBgBO(){

    }

    public FwBgBO(String bgbh,String bglx, List<T> yList, List<T> newList) {
        this.bglx = bglx;
        this.bgbh = bgbh;
        this.yList = yList;
        this.newList = newList;
    }

    public FwBgBO(String bgbh,String bglx, List<T> yList, List<T> newList, T mainObject) {
        this.bglx = bglx;
        this.bgbh = bgbh;
        this.yList = yList;
        this.newList = newList;
        this.mainObject = mainObject;
    }

    public String getBglx() {
        return bglx;
    }

    public void setBglx(String bglx) {
        this.bglx = bglx;
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public List<T> getNewList() {
        return newList;
    }

    public void setNewList(List<T> newList) {
        this.newList = newList;
    }

    public T getMainObject() {
        return mainObject;
    }

    public void setMainObject(T mainObject) {
        this.mainObject = mainObject;
    }

    public List<T> getyList() {
        return yList;
    }

    public void setyList(List<T> yList) {
        this.yList = yList;
    }
}
