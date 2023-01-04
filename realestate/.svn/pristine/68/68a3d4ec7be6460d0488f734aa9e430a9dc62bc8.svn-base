package cn.gtmap.realestate.register.core.dto;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 待合并不动产单元相关信息
 */
public class BdcDhbBdcdyDTO {
    /**
     * 权利拆分标识
     */
    private String qlcfbs;
    /**
     * 待合并的项目数据
     */
    private List<BdcXmDO> dhbBdcXmDOList;

    /**
     * 待合并的房地产权数据
     */
    private List<BdcFdcqDO> dhbBdcFdcqDOList;

    /**
     * 待合并的证书数据
     */
    private List<BdcZsDO> dhbBdcZsDOList;

    /**
     * 待合并的权利人数据
     */
    private List<BdcQlrDO> dhbBdcQlrDOList;


    public BdcDhbBdcdyDTO(){

    }

    public BdcDhbBdcdyDTO(int size) {
        this.dhbBdcXmDOList = new ArrayList<>(size);
        this.dhbBdcFdcqDOList = new ArrayList<>(size);
        this.dhbBdcZsDOList = new ArrayList<>(size);
        this.dhbBdcQlrDOList = new ArrayList<>(size);
    }

    public String getQlcfbs() {
        return qlcfbs;
    }

    public void setQlcfbs(String qlcfbs) {
        this.qlcfbs = qlcfbs;
    }

    public List<BdcXmDO> getDhbBdcXmDOList() {
        return dhbBdcXmDOList;
    }

    public void setDhbBdcXmDOList(List<BdcXmDO> dhbBdcXmDOList) {
        this.dhbBdcXmDOList = dhbBdcXmDOList;
    }

    public List<BdcFdcqDO> getDhbBdcFdcqDOList() {
        return dhbBdcFdcqDOList;
    }

    public void setDhbBdcFdcqDOList(List<BdcFdcqDO> dhbBdcFdcqDOList) {
        this.dhbBdcFdcqDOList = dhbBdcFdcqDOList;
    }

    public List<BdcZsDO> getDhbBdcZsDOList() {
        return dhbBdcZsDOList;
    }

    public void setDhbBdcZsDOList(List<BdcZsDO> dhbBdcZsDOList) {
        this.dhbBdcZsDOList = dhbBdcZsDOList;
    }

    public List<BdcQlrDO> getDhbBdcQlrDOList() {
        return dhbBdcQlrDOList;
    }

    public void setDhbBdcQlrDOList(List<BdcQlrDO> dhbBdcQlrDOList) {
        this.dhbBdcQlrDOList = dhbBdcQlrDOList;
    }

    @Override
    public String toString() {
        return "BdcDhbBdcdyDTO{" +
                "dhbBdcXmDOList=" + dhbBdcXmDOList +
                ", dhbBdcFdcqDOList=" + dhbBdcFdcqDOList +
                ", dhbBdcZsDOList=" + dhbBdcZsDOList +
                ", dhbBdcQlrDOList=" + dhbBdcQlrDOList +
                '}';
    }
}
