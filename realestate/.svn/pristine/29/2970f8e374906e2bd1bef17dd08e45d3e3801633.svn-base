package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.LpbImportService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FttdmjRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.FwhsAndFwQlrRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.FwychsAndFwQlrRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.LjzJzmjRequestDTO;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/26
 * @description
 */
@Service
public class LpbImportServiceImpl implements LpbImportService {
    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    private FwHsService fwHsService;
    @Autowired
    private FwFcqlrService fwFcqlrService;
    @Autowired
    private BdcdyZtService bdcdyZtService;
    @Autowired
    private EntityZdConvertUtils entityZdConvertUtils;
    @Autowired
    private CalculatedAreaService calculatedAreaService;
    @Autowired
    private FwYcHsService fwYcHsService;

    @Override
    @Transactional
    public void lpbImport(FwLjzDO fwLjzDO, Boolean fgyyhs, List<FwhsAndFwQlrRequestDTO> fwhsAndFwQlrList) {
        if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())) {
            if (fgyyhs) {
                entityZdConvertUtils.convertEntityToDm(fwLjzDO);
                fwLjzService.updateFwLjzWithChangeBdcdyh(fwLjzDO, false);
            }
            List<FwHsDO> insertFwHsDOList = new ArrayList<>();
            List<FwHsDO> updateFwHsDOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(fwhsAndFwQlrList)) {
                String dcbIndex = fwLjzDO.getFwDcbIndex();
                Map<String, FwHsDO> nowExistFwHsByDyhWlcsFjh = getNowExistFwHsDOMap(dcbIndex);
                for (FwhsAndFwQlrRequestDTO fwhsAndFwQlrRequestDTO : fwhsAndFwQlrList) {
                    FwHsDO fwHsDO = getLpbThisFwHsDO(fwhsAndFwQlrRequestDTO, fwLjzDO.getFwDcbIndex());
                    FwHsDO existFwHs = getSameFwHsDOList(fwHsDO, nowExistFwHsByDyhWlcsFjh);
                    getFwHsList(existFwHs, fwhsAndFwQlrRequestDTO, fgyyhs, insertFwHsDOList, updateFwHsDOList);
                }
                List<FwHsDO> alreadySubmitList = fwHsService.batchInsertFwHs(insertFwHsDOList);
                fwHsService.batchUpdateFwHs(updateFwHsDOList, false);
                alreadySubmitList.addAll(updateFwHsDOList);
                bdcdyZtService.updateBdcdyZtByFwhsList(alreadySubmitList);
            }
        }
    }



    private void getFwHsList(FwHsDO existFwHs, FwhsAndFwQlrRequestDTO fwhsAndFwQlrRequestDTO, Boolean fgyyhs, List<FwHsDO> insertFwHsDOList, List<FwHsDO> updateFwHsDOList) {
        FwHsDO fwHsDO = fwhsAndFwQlrRequestDTO.getFwHsDO();
        if (existFwHs == null) {
            if (StringUtils.isBlank(fwHsDO.getFwHsIndex())) {
                fwHsDO.setFwHsIndex(UUIDGenerator.generate());
            }
            insertFwHsDOList.add(fwHsDO);
            if (CollectionUtils.isNotEmpty(fwhsAndFwQlrRequestDTO.getQlrList())) {
                reInsertFwfcQlrByFwhs(fwhsAndFwQlrRequestDTO);
            }
        } else if (BooleanUtils.isNotFalse(fgyyhs)) {
            fwHsDO.setFwHsIndex(existFwHs.getFwHsIndex());
            updateFwHsDOList.add(fwHsDO);
            if (CollectionUtils.isNotEmpty(fwhsAndFwQlrRequestDTO.getQlrList())) {
                reInsertFwfcQlrByFwhs(fwhsAndFwQlrRequestDTO);
            }
        }
    }

    private void getFwYcHsList(FwYchsDO existFwYcHs, FwychsAndFwQlrRequestDTO fwychsAndFwQlrRequestDTO, Boolean fgyyhs, List<FwYchsDO> insertFwYcHsDOList, List<FwYchsDO> updateFwYcHsDOList) {
        FwYchsDO fwYchsDO = fwychsAndFwQlrRequestDTO.getFwYchsDO();
        if (existFwYcHs == null) {
            if (StringUtils.isBlank(fwYchsDO.getFwHsIndex())) {
                fwYchsDO.setFwHsIndex(UUIDGenerator.generate());
            }
            insertFwYcHsDOList.add(fwYchsDO);
            if (CollectionUtils.isNotEmpty(fwychsAndFwQlrRequestDTO.getQlrList())) {
                reInsertFwfcQlrByFwYchs(fwychsAndFwQlrRequestDTO);
            }
        } else if (BooleanUtils.isNotFalse(fgyyhs)) {
            fwYchsDO.setFwHsIndex(existFwYcHs.getFwHsIndex());
            updateFwYcHsDOList.add(fwYchsDO);
            if (CollectionUtils.isNotEmpty(fwychsAndFwQlrRequestDTO.getQlrList())) {
                reInsertFwfcQlrByFwYchs(fwychsAndFwQlrRequestDTO);
            }
        }
    }

    private FwHsDO getLpbThisFwHsDO(FwhsAndFwQlrRequestDTO fwhsAndFwQlrRequestDTO, String fwDcbIndex) {
        FwHsDO fwHsDO = fwhsAndFwQlrRequestDTO.getFwHsDO();
        if (StringUtils.isBlank(fwHsDO.getFwDcbIndex())) {
            fwHsDO.setFwDcbIndex(fwDcbIndex);
        }
        //处理字典项
        entityZdConvertUtils.convertEntityToDm(fwHsDO);
        return fwHsDO;
    }

    private FwYchsDO getLpbThisFwYcHsDO(FwychsAndFwQlrRequestDTO fwychsAndFwQlrRequestDTO, String fwDcbIndex) {
        FwYchsDO fwYchsDO = fwychsAndFwQlrRequestDTO.getFwYchsDO();
        if (StringUtils.isBlank(fwYchsDO.getFwDcbIndex())) {
            fwYchsDO.setFwDcbIndex(fwDcbIndex);
        }
        //处理字典项
        entityZdConvertUtils.convertEntityToDm(fwYchsDO);
        return fwYchsDO;
    }

    @Override
    public void scmjImport(String fwDcbIndex, List<FwHsDO> fwHsDOList) {
        if (StringUtils.isNotBlank(fwDcbIndex) && CollectionUtils.isNotEmpty(fwHsDOList)) {
            Boolean hasExistFwhs = false;
            Map<String, FwHsDO> nowExistFwHsByDyhWlcsFjh = getNowExistFwHsDOMap(fwDcbIndex);
            for (FwHsDO fwHsDO : fwHsDOList) {
                FwHsDO existFwHs = getSameFwHsDOList(fwHsDO, nowExistFwHsByDyhWlcsFjh);
                if (existFwHs != null) {
                    hasExistFwhs = true;
                    fwHsDO.setFwHsIndex(existFwHs.getFwHsIndex());
                    fwHsDO.setFwDcbIndex(fwDcbIndex);
                    fwHsService.updateFwHs(fwHsDO, false);
                }
            }
            //修改当前逻辑幢面积
            if (hasExistFwhs) {
                LjzJzmjRequestDTO ljzJzmjRequestDTO = new LjzJzmjRequestDTO();
                List<String> fwDcbIndexList = new ArrayList<>(1);
                fwDcbIndexList.add(fwDcbIndex);
                ljzJzmjRequestDTO.setFwDcbIndex(fwDcbIndexList);
                ljzJzmjRequestDTO.setMjlb(Constants.FW_SCHS);
                calculatedAreaService.calculatedLjzJzmj(ljzJzmjRequestDTO);
                FttdmjRequestDTO fttdmjRequestDTO = new FttdmjRequestDTO();
                fttdmjRequestDTO.setFwDcbIndex(fwDcbIndexList);
                fttdmjRequestDTO.setJsgsxh("4");
                calculatedAreaService.calculatedFttdmj(fttdmjRequestDTO);
            }
        }
    }


    private Map<String, FwHsDO> getNowExistFwHsDOMap(String dcbIndex) {
        List<FwHsDO> nowExistFwHsList = fwHsService.listFwHsListByFwDcbIndex(dcbIndex);
        if (CollectionUtils.isNotEmpty(nowExistFwHsList)) {
            Map<String, FwHsDO> nowExistFwHsByDyhWlcsFjh = new HashMap<>(nowExistFwHsList.size());
            for (FwHsDO fwHsDO : nowExistFwHsList) {
                if (fwHsDO != null) {
                    nowExistFwHsByDyhWlcsFjh.put(getDyhWlcsFjhString(fwHsDO.getDyh(),fwHsDO.getWlcs(),fwHsDO.getFjh()), fwHsDO);
                }
            }
            return nowExistFwHsByDyhWlcsFjh;
        }
        return new HashMap<>(0);
    }



    /**
     * 获得库里与当前房屋户室相同的房屋户室
     *
     * @param fwHsDO
     * @return
     */
    private static FwHsDO getSameFwHsDOList(FwHsDO fwHsDO, Map<String, FwHsDO> existFwHsDoList) {
        if (MapUtils.isEmpty(existFwHsDoList)) {
            return null;
        }
        return existFwHsDoList.get(getDyhWlcsFjhString(fwHsDO.getDyh(),fwHsDO.getWlcs(),fwHsDO.getFjh()));
    }

    /**
     * 获得库里与当前房屋户室相同的房屋预测户室
     *
     * @param fwYchsDO
     * @return
     */
    private static FwYchsDO getSameFwYcHsDOList(FwYchsDO fwYchsDO, Map<String, FwYchsDO> existFwYcHsDoList) {
        if (MapUtils.isEmpty(existFwYcHsDoList)) {
            return null;
        }
        return existFwYcHsDoList.get(getDyhWlcsFjhString(fwYchsDO.getDyh(),fwYchsDO.getWlcs(),fwYchsDO.getFjh()));
    }

    private static String getDyhWlcsFjhString(String dyh,Integer wlcs,String fjh) {
        StringBuilder dyhWlcsFjh = new StringBuilder("");
        dyhWlcsFjh.append(dyh).append(";");
        dyhWlcsFjh.append(wlcs).append(";");
        dyhWlcsFjh.append(fjh);
        return dyhWlcsFjh.toString();
    }

    /**
     * 重新插入当前房屋户室的权利人信息
     *
     * @param fwhsAndFwQlrRequestDTO
     */
    private void reInsertFwfcQlrByFwhs(FwhsAndFwQlrRequestDTO fwhsAndFwQlrRequestDTO) {
        FwHsDO fwHsDO = fwhsAndFwQlrRequestDTO.getFwHsDO();
        if(CollectionUtils.isNotEmpty(fwhsAndFwQlrRequestDTO.getQlrList())){
            reInsertQlrByDTO(fwhsAndFwQlrRequestDTO.getQlrList(),fwHsDO.getFwHsIndex());
        }
    }

    /**
     * 通过FwhsAndFwQlrRequestDTO获取权利人信息
     *
     * @param fwFcqlrDOList
     */
    private void reInsertQlrByDTO(List<FwFcqlrDO> fwFcqlrDOList,String fwHsIndex){
        List<FwFcqlrDO> insertFwQlrList = new ArrayList<>();
        for (FwFcqlrDO fwFcqlrDO : fwFcqlrDOList) {
            if (fwFcqlrDO != null && StringUtils.isNotBlank(fwFcqlrDO.getQlr())) {
                entityZdConvertUtils.convertEntityToDm(fwFcqlrDO);
                fwFcqlrDO.setFwIndex(fwHsIndex);
                insertFwQlrList.add(fwFcqlrDO);
            }
        }
        if (CollectionUtils.isNotEmpty(insertFwQlrList)) {
            fwFcqlrService.deleteFcqlrByFwIndex(fwHsIndex);
            fwFcqlrService.batchInsertFwFcQlr(insertFwQlrList);
        }
    }


    /**
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 导入预测楼盘表数据
     */
    @Override
    @Transactional
    public void lpbYcImport(FwLjzDO fwLjzDO, Boolean fgyyhs, List<FwychsAndFwQlrRequestDTO> fwhsAndFwQlrList) {
        if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())) {
            if (fgyyhs) {
                entityZdConvertUtils.convertEntityToDm(fwLjzDO);
                fwLjzService.updateFwLjzWithChangeBdcdyh(fwLjzDO, false);
            }
            List<FwYchsDO> insertFwYcHsDOList = new ArrayList<>();
            List<FwYchsDO> updateFwYcHsDOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(fwhsAndFwQlrList)) {
                String fwDcbIndex = fwLjzDO.getFwDcbIndex();
                Map<String, FwYchsDO> nowExistFwYcHsByDyhWlcsFjh = getNowExistFwYcHsDOMap(fwDcbIndex);
                for (FwychsAndFwQlrRequestDTO fwychsAndFwQlrRequestDTO : fwhsAndFwQlrList) {
                    FwYchsDO fwYchsDO = getLpbThisFwYcHsDO(fwychsAndFwQlrRequestDTO, fwLjzDO.getFwDcbIndex());
                    FwYchsDO existFwYcHs = getSameFwYcHsDOList(fwYchsDO, nowExistFwYcHsByDyhWlcsFjh);
                    getFwYcHsList(existFwYcHs, fwychsAndFwQlrRequestDTO, fgyyhs, insertFwYcHsDOList, updateFwYcHsDOList);
                }
                List<FwYchsDO> alreadySubmitList= fwYcHsService.batchInsertFwYchs(insertFwYcHsDOList);
                fwYcHsService.batchUpdateFwYcHs(updateFwYcHsDOList,false);
                alreadySubmitList.addAll(updateFwYcHsDOList);
                bdcdyZtService.updateBdcdyZtByFwYchsList(alreadySubmitList);
            }
        }
    }
    private Map<String, FwYchsDO> getNowExistFwYcHsDOMap(String fwDcbIndex) {
        List<FwYchsDO> nowExistFwYcHsList = fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
        if (CollectionUtils.isNotEmpty(nowExistFwYcHsList)) {
            Map<String, FwYchsDO> nowExistFwYcHsByDyhWlcsFjh = new HashMap<>(nowExistFwYcHsList.size());
            for (FwYchsDO fwYchsDO : nowExistFwYcHsList) {
                if (fwYchsDO != null) {
                    nowExistFwYcHsByDyhWlcsFjh.put(getDyhWlcsFjhString(fwYchsDO.getDyh(),fwYchsDO.getWlcs(),fwYchsDO.getFjh()), fwYchsDO);
                }
            }
            return nowExistFwYcHsByDyhWlcsFjh;
        }
        return new HashMap<>(0);
    }

    /**
     * 重新插入当前房屋户室的权利人信息
     *
     * @param fwychsAndFwQlrRequestDTO
     */
    private void reInsertFwfcQlrByFwYchs(FwychsAndFwQlrRequestDTO fwychsAndFwQlrRequestDTO) {
        FwYchsDO fwYchsDO = fwychsAndFwQlrRequestDTO.getFwYchsDO();
        if(CollectionUtils.isNotEmpty(fwychsAndFwQlrRequestDTO.getQlrList())){
            reInsertQlrByDTO(fwychsAndFwQlrRequestDTO.getQlrList(),fwYchsDO.getFwHsIndex());
        }
    }
}
