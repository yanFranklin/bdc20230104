package cn.gtmap.realestate.building.ui.core.vo;

import cn.gtmap.realestate.building.ui.core.vo.lpb.LpbTableMainDataVO;
import cn.gtmap.realestate.building.ui.core.vo.lpb.LpbTableMergeDataVO;
import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-26
 * @description
 */
public class LpbViewVO {


    /**
     * 单元数 列 单元号 和最大户数
     */
    private List<Map<String,Object>> dyList = new ArrayList<>();

    /**
     * 层数 行
     */
    private List<Map<String,String>> cList = new ArrayList<>();

    /**
     * 每一层 房屋数据
     */
    private List<LpbChVO> cFwList = new ArrayList<>();

    /**
     * 上下合并房屋列表
     */
    private List<ResourceDTO> upDownMergeFwList = new ArrayList<>();

    /**
     * 其他资源属性
     */
    private ResourceDTO resource;

    /**
     * 最大列数
     */
    private Integer maxHs = 0;

    /**
     * 车库
     */
    private LpbCkVo lpbCkVo;

    // 房屋层数
    private Integer fwcs;

    private List<LpbTableMainDataVO> mainData;

    private List<LpbTableMergeDataVO> mergeData;

    /**
     * 需要过滤的属性列表
     */
    private Map<String, Set<String>> filterList;

    /**
     * 单个单元格高度
     */
    private int cellHeight;

    // 默认显示的列
    private List<String> defaultShows;

    //每层的户室数量
    private Integer chsCount;

    public List<Map<String, Object>> getDyList() {
        return dyList;
    }

    public void setDyList(List<Map<String, Object>> dyList) {
        this.dyList = dyList;
    }

    public List<Map<String, String>> getcList() {
        return cList;
    }

    public void setcList(List<Map<String, String>> cList) {
        this.cList = cList;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    public List<LpbChVO> getcFwList() {
        return cFwList;
    }

    public void setcFwList(List<LpbChVO> cFwList) {
        this.cFwList = cFwList;
    }

    public Integer getMaxHs() {
        return maxHs;
    }

    public void setMaxHs(Integer maxHs) {
        this.maxHs = maxHs;
    }

    public LpbCkVo getLpbCkVo() {
        return lpbCkVo;
    }

    public void setLpbCkVo(LpbCkVo lpbCkVo) {
        this.lpbCkVo = lpbCkVo;
    }

    public List<LpbTableMainDataVO> getMainData() {
        return mainData;
    }

    public void setMainData(List<LpbTableMainDataVO> mainData) {
        this.mainData = mainData;
    }

    public List<LpbTableMergeDataVO> getMergeData() {
        return mergeData;
    }

    public void setMergeData(List<LpbTableMergeDataVO> mergeData) {
        this.mergeData = mergeData;
    }

    public List<ResourceDTO> getUpDownMergeFwList() {
        return upDownMergeFwList;
    }

    public void setUpDownMergeFwList(List<ResourceDTO> upDownMergeFwList) {
        this.upDownMergeFwList = upDownMergeFwList;
    }

    public Map<String, Set<String>> getFilterList() {
        return filterList;
    }

    public void setFilterList(Map<String, Set<String>> filterList) {
        this.filterList = filterList;
    }

    public Integer getFwcs() {
        return fwcs;
    }

    public void setFwcs(Integer fwcs) {
        this.fwcs = fwcs;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public List<String> getDefaultShows() {
        return defaultShows;
    }

    public void setDefaultShows(List<String> defaultShows) {
        this.defaultShows = defaultShows;
    }

    public Integer getChsCount() {
        return chsCount;
    }

    public void setChsCount(Integer chsCount) {
        this.chsCount = chsCount;
    }
}
