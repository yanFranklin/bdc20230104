package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.FwLjzMapper;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.BgJbsxDTO;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/2
 * @description 逻辑幢服务接口实现
 */
@Service
public class FwLjzServiceImpl implements FwLjzService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description EntityMapper
     */
    @Autowired
    private EntityMapper entityMapper;
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室接口
     */
    @Autowired
    private FwHsService fwHsService;
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询Repo
     */
    @Autowired
    private Repo repo;
    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 权利人接口
     */
    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Autowired
    private BdcdyhService bdcdyhService;

    @Autowired
    private FwXmxxService fwXmxxService;

    @Autowired
    private  FwHstService fwHstService;

    @Autowired
    private FwLjzMapper fwLjzMapper;

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除逻辑幢信息
     */
    @Override
    public Integer deleteLjzByFwDcbIndex(String fwDcbIndex, boolean withRelated) {
        Integer deleteResult = 0;
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            FwLjzDO fwLjzDO = queryLjzByFwDcbIndex(fwDcbIndex);
            deleteResult=deleteLjz(fwLjzDO, withRelated);
        }
        return deleteResult;
    }

    /**
     * @param fwLjzDO
     * @return int
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据数据记录删除逻辑幢信息
     */
    @Override
    @Transactional
    public int deleteLjz(FwLjzDO fwLjzDO, boolean withRelated) {
        int result = 0;
        if (fwLjzDO != null) {
            result = entityMapper.delete(fwLjzDO);
            if (withRelated) {
                // 删除权利人数据//和户室图数据
                if (StringUtils.isNotBlank(fwLjzDO.getBdcdyfwlx())
                        && StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_DZ)) {
                    fwFcqlrService.deleteFcqlrByFwIndex(fwLjzDO.getFwDcbIndex());
                    //删除户室图数据
                    fwHstService.deleteFwHstByFwHstIndex(fwLjzDO.getFwDcbIndex());
                }
                // 删除户室数据
                List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwLjzDO.getFwDcbIndex());
                if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                    for (FwHsDO fwHsDO : fwHsDOList) {
                        fwHsService.deleteHsByFwHsIndex(fwHsDO.getFwHsIndex(), true);
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键查询逻辑幢
     */
    @Override
    public FwLjzDO queryLjzByFwDcbIndex(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            return entityMapper.selectByPrimaryKey(FwLjzDO.class, fwDcbIndex);
        }
        return null;
    }

    /**
     * @param fwLjzDO
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 新增逻辑幢信息
     */
    @Override
    public FwLjzDO insertLjz(FwLjzDO fwLjzDO) {
        if (fwLjzDO != null && (!CheckEntityUtils.checkPk(fwLjzDO))) {
            fwLjzDO.setFwDcbIndex(UUIDGenerator.generate());
        }
        if (StringUtils.isBlank(fwLjzDO.getBdcdyh())
                && StringUtils.equals(Constants.BDCDYFWLX_DZ, fwLjzDO.getBdcdyfwlx())
                && StringUtils.isNotBlank(fwLjzDO.getLszd())
                && StringUtils.isNotBlank(fwLjzDO.getZrzh())) {
            fwLjzDO.setBdcdyh(bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(), fwLjzDO.getZrzh()));
            fwLjzDO.setBdczt(Constants.BDCZT_KY);
        }
        entityMapper.insertSelective(fwLjzDO);
        return fwLjzDO;
    }

    /**
     * @param fwLjzDO
     * @param updateNull true表示空字段更新，false，表示空字段不更新
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 修改逻辑幢信息
     */
    @Override
    public Integer updateLjz(FwLjzDO fwLjzDO, boolean updateNull) {
        Integer updateResult = 0;
        if (fwLjzDO != null && CheckEntityUtils.checkPk(fwLjzDO)) {
            if (updateNull) {
                updateResult = entityMapper.updateByPrimaryKeyNull(fwLjzDO);
            } else {
                updateResult = entityMapper.updateByPrimaryKeySelective(fwLjzDO);
            }
        }
        return updateResult;
    }

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.LjzResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description
     */
    @Override
    public Page<Map> listLjzByPage(Pageable pageable, Map map) {
        Page<Map> result = repo.selectPaging("listLjzByPageOrder", map, pageable);
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            // 循环 key值 为 下划线
            List<Map> contentList = result.getContent();
            for (Map dataMap : contentList) {
                BuildingUtils.convertKeyToLowerCase(dataMap);
                //总层数
                if(StringUtils.isNotBlank(MapUtils.getString(dataMap,"dxcs"))&&StringUtils.isNotBlank(MapUtils.getString(dataMap,"dscs"))){
                    dataMap.put("zcs",Integer.valueOf(MapUtils.getString(dataMap,"dxcs"))+Integer.valueOf(MapUtils.getString(dataMap,"dscs")));
                }
                //实测预测状态
                String fwDcbIndex = MapUtils.getString(dataMap, "fw_dcb_index");
                if (StringUtils.isNotBlank(fwDcbIndex)) {
                    List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
                    if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                        dataMap.put("syczt", "实测");
                    } else {
                        dataMap.put("syczt", "预测");
                    }
                }
                //权籍管理代码
                if(map.get("qjgldm") != null){
                    dataMap.put("qjgldm",map.get("qjgldm"));
                }
            }
        }
        return result;
    }

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询预测楼盘表
     * @date : 2020/12/28 17:16
     */
    @Override
    public Page<Map> listYcLjzByPage(Pageable pageable, Map map) {
        Page<Map> result = repo.selectPaging("listYcLjzByPageOrder", map, pageable);
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            // 循环 key值 为 下划线
            List<Map> contentList = result.getContent();
            for (Map dataMap : contentList) {
                BuildingUtils.convertKeyToLowerCase(dataMap);
                //总层数
                if (StringUtils.isNotBlank(MapUtils.getString(dataMap, "dxcs")) && StringUtils.isNotBlank(MapUtils.getString(dataMap, "dscs"))) {
                    dataMap.put("zcs", Integer.valueOf(MapUtils.getString(dataMap, "dxcs")) + Integer.valueOf(MapUtils.getString(dataMap, "dscs")));
                }
                //权籍管理代码
                if(map.get("qjgldm") != null){
                    dataMap.put("qjgldm",map.get("qjgldm"));
                }
            }
        }
        return result;
    }

    /**
     * @param djh
     * @param zrzh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据宗地号  或 宗地号+自然幢号 查询逻辑幢列表
     */
    @Override
    public List<FwLjzDO> listLjzByDjhAndZrzh(String djh, String zrzh) {
        if (StringUtils.isNotBlank(djh)) {
            Example example = new Example(FwLjzDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("lszd", djh);

            if (StringUtils.isNotBlank(zrzh)) {
                criteria.andEqualTo("zrzh", zrzh);
            }
            example.setOrderByClause("fwmc, ljzh");
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwXmxxIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键查询逻辑幢信息
     */
    @Override
    public List<FwLjzDO> listLjzByFwXmxxIndex(String fwXmxxIndex) {
        if (StringUtils.isNotBlank(fwXmxxIndex)) {
            Example example = new Example(FwLjzDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("fwXmxxIndex", fwXmxxIndex);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询逻辑幢基本信息
     */
    @Override
    public FwLjzDO queryLjzByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(FwLjzDO.class);
            example.createCriteria().andEqualTo("bdcdyh", bdcdyh);
            List<FwLjzDO> fwLjzDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(fwLjzDOList)) {
                return fwLjzDOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据bdcdyhList查询房逻辑幢流程状态
     */
    @Override
    public List<String> queryLjzLcztByBdcdyh(List<String> bdcdyhList) {
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            return fwLjzMapper.queryLjzLcztByBdcdyh(bdcdyhList);
        }

        return null;
    }

    /**
     * @param fwDcbIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据逻辑幢主键 查询 不动产单元FWLX
     */
    @Override
    public String queryBdcdyFwlxByFwDcbIndex(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            FwLjzDO fwLjzDO = queryLjzByFwDcbIndex(fwDcbIndex);
            if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getBdcdyfwlx())) {
                return fwLjzDO.getBdcdyfwlx();
            }
        }
        return null;
    }

    /**
     * @param lszd
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 生成逻辑幢号
     */
    @Override
    public String creatLjzh(String lszd, Integer cs) {
        if (StringUtils.isNotBlank(lszd)) {
            String ljzh = String.valueOf(System.currentTimeMillis()) + String.valueOf((int) (Math.random() * 10000));
            Example example = new Example(FwLjzDO.class);
            example.createCriteria().andEqualTo("ljzh", ljzh).andEqualTo("lszd", lszd);
            List<FwLjzDO> fwLjzDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(fwLjzDOList)) {
                return ljzh;
            } else {
                if (cs < 5) {
                    cs++;
                    return creatLjzh(lszd, cs);
                }
            }
        }
        return null;
    }

    /**
     * @param fwLjzDO
     * @param updateNull
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 FW_HS表 有BDCDYH变化，并且不知道原有BDCDYH情况
     */
    @Override
    public Integer updateFwLjzWithChangeBdcdyh(FwLjzDO fwLjzDO, boolean updateNull) {
        int result = 0;
        if (fwLjzDO != null
                && StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())) {
            // 根据BDCDYH 更新 BDCZT
            fwLjzDO.setBdczt(StringUtils.isNotBlank(fwLjzDO.getBdcdyh()) ? Constants.BDCZT_KY : Constants.BDCZT_BKY);
            result = updateLjz(fwLjzDO, updateNull);
        }
        return result;
    }

    /**
     * @param fwLjzDO
     * @param updateNull
     * @param ybdcdyh
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 FW_HS表 有BDCDYH变化，且知道原有BDCDYH情况（包括空的情况）
     */
    @Override
    public Integer updateFwLjzWithChangeBdcdyh(FwLjzDO fwLjzDO, boolean updateNull, String ybdcdyh) {
        int result = 0;
        if (fwLjzDO != null
                && StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())) {
            // 根据BDCDYH 更新 BDCZT
            fwLjzDO.setBdczt(StringUtils.isNotBlank(fwLjzDO.getBdcdyh()) ? Constants.BDCZT_KY : Constants.BDCZT_BKY);
            result = updateLjz(fwLjzDO, updateNull);
        }
        return result;
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询逻辑幢列表
     */
    @Override
    public List<FwLjzDO> listLjzByDjhOrderByZrzh(String djh) {
        if (StringUtils.isNotBlank(djh)) {
            Example example = new Example(FwLjzDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("lszd", djh);
            criteria.andIsNotNull("zrzh");
            example.setOrderByClause("zrzh desc");
            return entityMapper.selectByExample(example);
        }
        return null;
    }


    /**
     * @param bgJbsxDTO
     * @param index
     * @param yIndex
     * @return cn.gtmap.realestate.common.core.dto.building.BgJbsxDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 初始化处理户室变更数据
     */
    private BgJbsxDTO initHsBgDTO(BgJbsxDTO bgJbsxDTO, String index, String yIndex) {
        BgJbsxDTO hsBgJbsxDTO = new BgJbsxDTO();
        BeanUtils.copyProperties(bgJbsxDTO, hsBgJbsxDTO);
        if (StringUtils.isNotBlank(index)) {
            hsBgJbsxDTO.setIndex(index);
        }
        hsBgJbsxDTO.setyIndex(yIndex);
        return hsBgJbsxDTO;
    }

    /**
     * @param fwLjzDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢实体查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwLjzDO(FwLjzDO fwLjzDO) {
        List<String> bdcdyhList = new ArrayList<>();
        if (fwLjzDO != null) {
            if (StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_XMNDZ)) {
                FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwLjzDO.getFwXmxxIndex());
                if (fwXmxxDO != null && StringUtils.isNotBlank(fwXmxxDO.getBdcdyh())) {
                    bdcdyhList.add(fwXmxxDO.getBdcdyh());
                }
            } else if (StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_DZ)) {
                if(StringUtils.isNotBlank(fwLjzDO.getBdcdyh())){
                    bdcdyhList.add(fwLjzDO.getBdcdyh());
                }
            } else if (StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_H)) {
                List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwLjzDO.getFwDcbIndex());
                if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                    for (FwHsDO fwHsDO : fwHsDOList) {
                        if (fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getBdcdyh())) {
                            bdcdyhList.add(fwHsDO.getBdcdyh());
                        }
                    }
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fwDcbIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢主键集合查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwLjzIndexList(List<String> fwDcbIndexList) {
        List<String> bdcdyhList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(fwDcbIndexList)){
            for(String fwDcbIndex:fwDcbIndexList){
                FwLjzDO fwLjzDO=queryLjzByFwDcbIndex(fwDcbIndex);
                if(fwLjzDO!=null){
                    bdcdyhList.addAll(listValidBdcdyhByFwLjzDO(fwLjzDO));
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fwDcbIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwDcbIndex查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwDcbIndex(String fwDcbIndex) {
        List<String> bdcdyhList = new ArrayList<>();
        if (StringUtils.isNotBlank(fwDcbIndex)) {
           FwLjzDO fwLjzDO=queryLjzByFwDcbIndex(fwDcbIndex);
            if (fwLjzDO != null) {
                bdcdyhList.addAll(listValidBdcdyhByFwLjzDO(fwLjzDO));
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fwlxBgRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋类型变更dto查询有效的不动产单元号（因为逻辑幢变更所以写这里）
     */
    @Override
    public List<String> listValidBdcdyhByFwlxBgRequestDTO(FwlxBgRequestDTO fwlxBgRequestDTO) {
        List<String> bdcdyhList = new ArrayList<>();
        if(fwlxBgRequestDTO!=null&&StringUtils.isNotBlank(fwlxBgRequestDTO.getFwDcbIndex())){
            bdcdyhList= listValidBdcdyhByFwDcbIndex(fwlxBgRequestDTO.getFwDcbIndex());
        }
        return bdcdyhList;
    }

    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByJson(String jsonData) {
        List<String> bdcdyhList = new ArrayList<>();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(jsonData)) {
            bdcdyhList.addAll(listValidBdcdyhByFwLjzDO((FwLjzDO) BuildingUtils.gnqyzGetDO(jsonData)));
        }
        return bdcdyhList;
    }

    /**
     * @param ljzh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据LJZH查询逻辑幢列表
     */
    @Override
    public List<FwLjzDO> listKyFwljzByLjzh(String ljzh) {
        if (StringUtils.isNotBlank(ljzh)) {
            Example example = new Example(FwLjzDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("ljzh", ljzh)
                    .andNotEqualNvlTo("bdczt", Constants.BDCZT_BKY, Constants.BDCZT_KY);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param ybdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据YBDCDYH 查询 独幢数据
     */
    @Override
    public List<FwLjzDO> listKyFwLjzByYbdcdyh(String ybdcdyh) {
        if(StringUtils.isNotBlank(ybdcdyh)){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("ybdcdyh",ybdcdyh);
            paramMap.put("bdczt",Constants.BDCZT_KY);
            return fwLjzMapper.listFwLjz(paramMap);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @param slbh
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [slbh] 受理编号
     * @return: String 匹配过后的受理编号
     * @description 当受理编号为CH开头时，通过fw_ljz和s_sj_bgsh关联查询，采用s_sj_bgsh的slbh
     */
    @Override
    public String queryfwljzLinkBgsh(String slbh) {
        return fwLjzMapper.selectFwLjzLeftJoin(slbh);
    }

}