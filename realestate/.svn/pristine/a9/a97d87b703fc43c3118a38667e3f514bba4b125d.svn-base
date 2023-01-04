package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.bo.FttdMjConfigBO;
import cn.gtmap.realestate.building.core.bo.LjzJzmjConfigBO;
import cn.gtmap.realestate.building.core.mapper.CalculatedAreaMapper;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.ManageConfigUtil;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.dto.building.FttdmjRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.LjzJzmjRequestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/14
 * @description
 */
@Service
public class CalculatedAreaServiceImpl implements CalculatedAreaService {
    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    private CalculatedAreaMapper calculatedAreaMapper;
    @Autowired
    private FwYcHsService fwYcHsService;
    @Autowired
    private FwHsService fwHsService;
    @Autowired
    private ZdService zdService;

    @Value("${fwkTableName}")
    private String fwkTableName;

    /**
     * @param ljzJzmjRequestDTO
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算逻辑幢建筑面积
     */
    @Override
    public void calculatedLjzJzmj(LjzJzmjRequestDTO ljzJzmjRequestDTO) {
        if (StringUtils.isNotBlank(ljzJzmjRequestDTO.getZdh()) && CollectionUtils.isEmpty(ljzJzmjRequestDTO.getFwDcbIndex())) {
            //宗地维度计算
            zdLjzJzmj(ljzJzmjRequestDTO);
        } else {
            //逻辑幢维度计算
            ljzJzmj(ljzJzmjRequestDTO);
        }
    }

    /**
     * @param fttdmjRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算分摊土地面积
     */
    @Override
    public Integer calculatedFttdmj(FttdmjRequestDTO fttdmjRequestDTO) {
        int result = 0;
        if (StringUtils.isNotBlank(fttdmjRequestDTO.getZdh()) && CollectionUtils.isEmpty(fttdmjRequestDTO.getFwDcbIndex())) {
            result = fttdmjZd(fttdmjRequestDTO);
        } else {
            result = fttdmjljz(fttdmjRequestDTO);
        }
        return result;
    }

    /**
     * @param fttdmjRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算分摊土地面积(配置)
     */
    @Override
    public void calculatedFttdmjByConfig(FttdmjRequestDTO fttdmjRequestDTO) {
        FttdMjConfigBO config = ManageConfigUtil.getFttdmjConfig();
        if (config != null && StringUtils.isNotBlank(config.getJsgsxh())
                && config.getFtxs() != 0) {
            fttdmjRequestDTO.setJsgsxh(config.getJsgsxh());
            fttdmjRequestDTO.setDxs(config.isDxs());
            fttdmjRequestDTO.setZhs(config.isZhs());
            fttdmjRequestDTO.setFtxs(config.getFtxs());
            calculatedFttdmj(fttdmjRequestDTO);
        } else {
            throw new AppException("获取坐落刷新配置失败");
        }
    }

    /**
     * @param ljzJzmjRequestDTO
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算逻辑幢建筑面积(配置)
     */
    @Override
    public void calculatedLjzJzmjByConfig(LjzJzmjRequestDTO ljzJzmjRequestDTO) {
        LjzJzmjConfigBO config = ManageConfigUtil.getLjzJzmjConfig();
        if (config != null && StringUtils.isNotBlank(config.getMjlb())) {
            ljzJzmjRequestDTO.setMjlb(config.getMjlb());
            ljzJzmjRequestDTO.setDxs(config.isDxs());
            ljzJzmjRequestDTO.setZhs(config.isZhs());
            calculatedLjzJzmj(ljzJzmjRequestDTO);
        } else {
            throw new AppException("获取坐落刷新配置失败");
        }
    }
    /**
     * @param fttdmjRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByDTO(FttdmjRequestDTO fttdmjRequestDTO) {
        List<String> bdcdyhList = new ArrayList<>();
        if (fttdmjRequestDTO != null) {
            if (CollectionUtils.isNotEmpty(fttdmjRequestDTO.getFwDcbIndex())) {
                for (String fwDcbIndex : fttdmjRequestDTO.getFwDcbIndex()) {
                    if (StringUtils.isNotBlank(fwDcbIndex)) {
                        bdcdyhList.addAll(fwLjzService.listValidBdcdyhByFwDcbIndex(fwDcbIndex));
                    }
                }
            } else if (StringUtils.isNotBlank(fttdmjRequestDTO.getZdh())) {
                bdcdyhList = zdService.listValidBdcdyhByDjh(fttdmjRequestDTO.getZdh());
            }
        }
        return bdcdyhList;
    }
    /**
     * @param ljzJzmjRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByDTO(LjzJzmjRequestDTO ljzJzmjRequestDTO) {
        List<String> bdcdyhList = new ArrayList<>();
        if (ljzJzmjRequestDTO != null) {
            if (CollectionUtils.isNotEmpty(ljzJzmjRequestDTO.getFwDcbIndex())) {
                for (String fwDcbIndex : ljzJzmjRequestDTO.getFwDcbIndex()) {
                    if (StringUtils.isNotBlank(fwDcbIndex)) {
                        bdcdyhList.addAll(fwLjzService.listValidBdcdyhByFwDcbIndex(fwDcbIndex));
                    }
                }
            } else if (StringUtils.isNotBlank(ljzJzmjRequestDTO.getZdh())) {
                bdcdyhList = zdService.listValidBdcdyhByDjh(ljzJzmjRequestDTO.getZdh());
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fttdmjRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗地下分摊面积计算
     */
    private Integer fttdmjZd(FttdmjRequestDTO fttdmjRequestDTO) {
        int result = 0;
        List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByDjhAndZrzh(fttdmjRequestDTO.getZdh(), "");
        if (CollectionUtils.isNotEmpty(fwLjzDOList)) {
            List<String> fwDcbIndexList = new ArrayList<>();
            for (FwLjzDO fwLjzDO : fwLjzDOList) {
                if (StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())) {
                    fwDcbIndexList.add(fwLjzDO.getFwDcbIndex());
                }
            }
            fttdmjRequestDTO.setFwDcbIndex(fwDcbIndexList);
            result = fttdmjljz(fttdmjRequestDTO);
        }
        return result;
    }

    /**
     * @param fttdmjRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 逻辑幢下分摊面积计算
     */
    private Integer fttdmjljz(FttdmjRequestDTO fttdmjRequestDTO) {
        //1.获取所有户室
        List<FwHsDO> allFwHsDOList = queryFwHsListByFwDcbIndexList(fttdmjRequestDTO.getFwDcbIndex());
        //2.根据计算公式进行计算
        int result = 0;
        if (CollectionUtils.isNotEmpty(allFwHsDOList) && StringUtils.isNotBlank(fttdmjRequestDTO.getJsgsxh())) {
            if (StringUtils.equals(fttdmjRequestDTO.getJsgsxh(), "1")) {
                result = fttdMjByFtxs(fttdmjRequestDTO, allFwHsDOList);
            } else if (StringUtils.equals(fttdmjRequestDTO.getJsgsxh(), "2")) {
                result = fttdMjByLzzmj(fttdmjRequestDTO, allFwHsDOList);
            } else if (StringUtils.equals(fttdmjRequestDTO.getJsgsxh(), "3")) {
                result = fttdMjByZrzMj(fttdmjRequestDTO, allFwHsDOList);
            } else if (StringUtils.equals(fttdmjRequestDTO.getJsgsxh(), "4")) {
                result = fttdMjByLcs(fttdmjRequestDTO, allFwHsDOList);
            }
        }
        return result;
    }

    /**
     * @param fttdmjRequestDTO
     * @param fwHsDOList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算规则1.分局分摊系数
     */
    private Integer fttdMjByFtxs(FttdmjRequestDTO fttdmjRequestDTO, List<FwHsDO> fwHsDOList) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(fwHsDOList) && fttdmjRequestDTO.getFtxs() != 0) {
            for (FwHsDO fwHsDO : fwHsDOList) {
                //获取户室下分摊土地面积
                double mj = fwHsJzmj(fwHsDO, fttdmjRequestDTO);
                if (mj != 0) {
                    fwHsDO.setFttdmj(mj * fttdmjRequestDTO.getFtxs());
                    result = fwHsService.updateFwHs(fwHsDO, false);
                }
            }
        }
        return result;
    }

    /**
     * @param fttdmjRequestDTO
     * @param fwHsDOList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算规则2.根据宗地下户室总面积
     */
    private Integer fttdMjByLzzmj(FttdmjRequestDTO fttdmjRequestDTO, List<FwHsDO> fwHsDOList) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(fwHsDOList) && StringUtils.isNotBlank(fttdmjRequestDTO.getZdnlzzjzmj()) && StringUtils.isNotBlank(fttdmjRequestDTO.getMjlb())) {
            //获取宗地内楼幢建筑面积
            double zdnlzzjzmj = queryZdnlzjzmj(fttdmjRequestDTO.getZdh(), fttdmjRequestDTO.getZdnlzzjzmj());
            //获取宗地内楼幢（发证面积或者实测面积）
            double zdmj = queryZdmj(fttdmjRequestDTO.getZdh(), fttdmjRequestDTO.getMjlb());
            if (zdmj != 0 && zdnlzzjzmj != 0) {
                for (FwHsDO fwHsDO : fwHsDOList) {
                    //获取户室下分摊土地面积
                    double mj = fwHsJzmj(fwHsDO, fttdmjRequestDTO);
                    if (mj != 0) {
                        //获取宗地内楼幢面积
                        fwHsDO.setFttdmj((mj / zdnlzzjzmj) * zdmj);
                        result = fwHsService.updateFwHs(fwHsDO, false);
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param lszd
     * @param lzlx
     * @return double
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取宗地内楼幢总建筑面积
     */
    private double queryZdnlzjzmj(String lszd, String lzlx) {
        double mj = 0;
        if (StringUtils.isNotBlank(lszd)) {
            String lzmj = null;
            Map map = new HashMap();
            map.put("lszd", lszd);
            if (StringUtils.equals("ljz", lzlx)) {
                lzmj = calculatedAreaMapper.calculatedZdLjzJzmj(map);
            } else if (StringUtils.equals("zrz", lzlx)) {
                map.put("table", fwkTableName);
                lzmj = calculatedAreaMapper.calculatedZdZrzJzmj(map);
            }
            if (StringUtils.isNotBlank(lzmj)) {
                mj = Double.valueOf(lzmj);
            }
        }
        return mj;
    }

    /**
     * @param djh
     * @param lx
     * @return double
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取宗地内楼幢（发证面积或者实测面积）
     */
    private double queryZdmj(String djh, String lx) {
        double mj = 0;
        ZdDjdcbDO zdDjdcbDO = zdService.querZdByDjh(djh);
        if (StringUtils.equals("sc", lx) && zdDjdcbDO.getScmj() != null) {
            mj = zdDjdcbDO.getScmj();
        } else if (StringUtils.equals("fz", lx) && zdDjdcbDO.getFzmj() != null) {
            mj = zdDjdcbDO.getFzmj();
        }
        return mj;
    }

    /**
     * @param fttdmjRequestDTO
     * @param fwHsDOList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算规则3.根据自然幢总面积
     */
    private Integer fttdMjByZrzMj(FttdmjRequestDTO fttdmjRequestDTO, List<FwHsDO> fwHsDOList) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            //自然幢建筑面积
            for (FwHsDO fwHsDO : fwHsDOList) {
                double zrzZmj = 0;
                double zrzZdmj = 0;
                Map map = queryZrzZjzmjAndZdmj(fwHsDO.getFwDcbIndex());
                //自然幢总面积
                String zmj = MapUtils.getString(map, "zrzZmj");
                String zdmj = MapUtils.getString(map, "zrzZdmj");
                if (StringUtils.isNotBlank(zmj) && StringUtils.isNotBlank(zdmj)) {
                    zrzZmj = Double.valueOf(zmj);
                    //获取户室下分摊土地面积
                    zrzZdmj = Double.valueOf(zdmj);
                }
                if (zrzZmj != 0 && zrzZdmj != 0) {
                    double mj = fwHsJzmj(fwHsDO, fttdmjRequestDTO);
                    if (mj != 0) {
                        fwHsDO.setFttdmj((mj / zrzZmj) * zrzZdmj);
                        result = fwHsService.updateFwHs(fwHsDO, false);
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param fwDcbIndex
     * @return double
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取自然幢下幢建筑面积
     */
    private Map queryZrzZjzmjAndZdmj(String fwDcbIndex) {
        Map returnMap = new HashMap();
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
            if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getZrzh()) && StringUtils.isNotBlank(fwLjzDO.getLszd())) {
                Map map = new HashMap();
                map.put("lszd", fwLjzDO.getLszd());
                map.put("zrzh", fwLjzDO.getZrzh());
                map.put("fwk", fwkTableName);
                String zrzZmj = calculatedAreaMapper.calculatedZdZrzAllLjzJzmj(map);
                String zrzZdmj = calculatedAreaMapper.queryZrzZdmj(map);
                if (StringUtils.isNotBlank(zrzZmj) && StringUtils.isNotBlank(zrzZdmj)) {
                    returnMap.put("zrzZdmj", zrzZdmj);
                    returnMap.put("zrzZmj", zrzZmj);
                }

            }
        }
        return returnMap;
    }

    /**
     * @param fttdmjRequestDTO
     * @param fwHsDOList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算规则4.根据楼层数
     */
    private Integer fttdMjByLcs(FttdmjRequestDTO fttdmjRequestDTO, List<FwHsDO> fwHsDOList) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(fwHsDOList) && fttdmjRequestDTO.getLcs() != null && fttdmjRequestDTO.getLcs() != 0) {
            for (FwHsDO fwHsDO : fwHsDOList) {
                //获取户室下分摊土地面积
                double mj = fwHsJzmj(fwHsDO, fttdmjRequestDTO);
                if (mj != 0) {
                    fwHsDO.setFttdmj(mj / fttdmjRequestDTO.getLcs());
                    result = fwHsService.updateFwHs(fwHsDO, false);
                }
            }
        }
        return result;
    }

    /**
     * @param fwHsDO
     * @param fttdmjRequestDTO
     * @return double
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取户室下分摊土地面积
     */
    private double fwHsJzmj(FwHsDO fwHsDO, FttdmjRequestDTO fttdmjRequestDTO) {
        double mj = 0;
        if (fwHsDO.getScjzmj() != null) {
            mj = fwHsDO.getScjzmj();
            if (fttdmjRequestDTO.isDxs()) {
                if (fwHsDO.getScdxbfjzmj() != null) {
                    mj = mj + fwHsDO.getScdxbfjzmj();
                }
            }
            if (fttdmjRequestDTO.isZhs()) {
                Map map = new HashMap();
                map.put("fwHsIndex", fwHsDO.getFwHsIndex());
                String fwZhsmj = calculatedAreaMapper.calculatedljzJzmjZhs(map);
                if (StringUtils.isNotBlank(fwZhsmj)) {
                    mj = mj + Double.valueOf(fwZhsmj);
                }
            }
        }
        return mj;
    }

    /**
     * @param fwDcbIndexList
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋调查表主键集合，查询所有户室
     */
    private List<FwHsDO> queryFwHsListByFwDcbIndexList(List<String> fwDcbIndexList) {
        List<FwHsDO> allFwHsDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwDcbIndexList)) {
            for (String fwDcbIndex : fwDcbIndexList) {
                List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
                if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                    allFwHsDOList.addAll(fwHsDOList);
                }
            }
        }
        return allFwHsDOList;
    }

    /**
     * @param ljzJzmjRequestDTO
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗地维度计算
     */
    private void zdLjzJzmj(LjzJzmjRequestDTO ljzJzmjRequestDTO) {
        List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByDjhAndZrzh(ljzJzmjRequestDTO.getZdh(), "");
        if (CollectionUtils.isNotEmpty(fwLjzDOList)) {
            List<String> fwDcbIndexList = new ArrayList<>();
            for (FwLjzDO fwLjzDO : fwLjzDOList) {
                if (StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())) {
                    fwDcbIndexList.add(fwLjzDO.getFwDcbIndex());
                }
            }
            ljzJzmjRequestDTO.setFwDcbIndex(fwDcbIndexList);
            //调逻辑幢下计算方法
            ljzJzmj(ljzJzmjRequestDTO);
        }
    }

    /**
     * @param ljzJzmjRequestDTO
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 逻辑幢维度计算
     */
    private void ljzJzmj(LjzJzmjRequestDTO ljzJzmjRequestDTO) {
        double mj = 0;
        if (CollectionUtils.isNotEmpty(ljzJzmjRequestDTO.getFwDcbIndex())) {
            for (String fwDcbIndex : ljzJzmjRequestDTO.getFwDcbIndex()) {
                FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
                if (fwLjzDO != null) {
                    Map map = new HashMap();
                    if (StringUtils.equals(ljzJzmjRequestDTO.getMjlb(), Constants.FW_SCHS)) {
                        map.put("table", Constants.FW_HS_TABLE);
                    } else if (StringUtils.equals(ljzJzmjRequestDTO.getMjlb(), Constants.FW_YCHS)) {
                        map.put("table", Constants.FW_YCHS_TABLE);
                    }
                    map.put("fwDcbIndex", fwDcbIndex);
                    //计算户室
                    String selectMj = calculatedAreaMapper.calculatedLjzJzmj(map);
                    if (StringUtils.isNotBlank(selectMj)) {
                        mj = Double.valueOf(selectMj);
                        //计算子户室
                        if (ljzJzmjRequestDTO.isZhs()) {
                            mj = mj + ljzJzmjZhs(ljzJzmjRequestDTO, map);
                        }
                        //计算地下室
                        if (ljzJzmjRequestDTO.isDxs()) {
                            mj = mj + ljzJzmjDxs(map);
                        }
                        if (StringUtils.equals(ljzJzmjRequestDTO.getMjlb(), Constants.FW_SCHS)) {
                            fwLjzDO.setScjzmj(mj);
                        } else if (StringUtils.equals(ljzJzmjRequestDTO.getMjlb(), Constants.FW_YCHS)) {
                            fwLjzDO.setYcjzmj(mj);
                        }
                        fwLjzService.updateLjz(fwLjzDO, false);
                    }
                }
            }
        }
    }

    /**
     * @param ljzJzmjRequestDTO
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算子户室建筑面积
     */
    private double ljzJzmjZhs(LjzJzmjRequestDTO ljzJzmjRequestDTO, Map map) {
        double mj = 0;
        String fwDcbIndex = MapUtils.getString(map, "fwDcbIndex");
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            if (StringUtils.equals(ljzJzmjRequestDTO.getMjlb(), Constants.FW_SCHS)) {
                List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
                if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                    for (FwHsDO fwHsDO : fwHsDOList) {
                        Map fwHsIndexMap = new HashMap();
                        fwHsIndexMap.put("fwHsIndex", fwHsDO.getFwHsIndex());
                        String fwZhsmj = calculatedAreaMapper.calculatedljzJzmjZhs(fwHsIndexMap);
                        if (StringUtils.isNotBlank(fwZhsmj)) {
                            mj = mj + Double.valueOf(fwZhsmj);
                        }

                    }
                }
            } else if (StringUtils.equals(ljzJzmjRequestDTO.getMjlb(), Constants.FW_YCHS)) {
                List<FwYchsDO> fwYchsDOList = fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
                if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
                    for (FwYchsDO fwYchsDO : fwYchsDOList) {
                        Map fwHsIndexMap = new HashMap();
                        fwHsIndexMap.put("fwHsIndex", fwYchsDO.getFwHsIndex());
                        String ycFwZhsmj = calculatedAreaMapper.calculatedljzJzmjZhs(fwHsIndexMap);
                        if (StringUtils.isNotBlank(ycFwZhsmj)) {
                            mj = mj + Double.valueOf(ycFwZhsmj);
                        }
                    }
                }
            }
        }
        return mj;
    }

    /**
     * @param map
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 计算地下室建筑面积
     */
    private double ljzJzmjDxs(Map map) {
        double mj = 0;
        String dxsMj = calculatedAreaMapper.calculatedLjzJzmjDxs(map);
        if (StringUtils.isNotBlank(dxsMj)) {
            mj = Double.valueOf(dxsMj);
        }
        return mj;
    }

}