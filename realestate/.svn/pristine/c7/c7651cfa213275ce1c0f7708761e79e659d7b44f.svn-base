package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.FwKMapper;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.QszdQlrService;
import cn.gtmap.realestate.building.core.service.ZdQlrService;
import cn.gtmap.realestate.building.core.service.ZdService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.service.ZrzService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.DjdcbJzxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdTreeZrzResponseDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description 宗地服务接口实现
 */
@Service
public class ZdServiceImpl implements ZdService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询Repo
     */
    @Autowired
    private Repo repo;

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private FwKMapper fwKMapper;
    @Autowired
    private BdcdyService bdcdyService;

    @Autowired
    private ZdQlrService zdQlrService;

    @Autowired
    private ZrzService zrzService;

    @Autowired
    private QszdQlrService qszdQlrService;

    @Value("${tbqj.table.zdlczt: zd_k_3401}")
    private String zdTable;

    /**
     * @param
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询宗地信息List(搁置功能方法)
     */
    @Override
    public List<ZdDjdcbDO> queryZdList() {
        return new ArrayList<>(0);
    }

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.ZdResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询宗地信息
     */
    @Override
    public Page<Map> listZdByPage(Pageable pageable, Map map) {
        Page<Map> result = repo.selectPaging("listZdByPageOrder", map, pageable);
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map data : result.getContent()) {
                setQlr(data);
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        return result;
    }

    /**
     * @param dataMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @description 保存宗地相关权利人
     */
    private void setQlr(Map dataMap) {
        List<String> zdQlrList = new ArrayList<>();
        if (dataMap != null && StringUtils.isNotBlank(MapUtils.getString(dataMap,"QJID"))) {

            // 宗地查询  区分 宗地权利人 与 权属宗地权利人
            if(StringUtils.equals("QSZD",MapUtils.getString(dataMap,"LX"))){
                List<QszdQlrDO> qlrDOList = qszdQlrService.listQszdQlrByQszdDjdcbIndex(MapUtils.getString(dataMap,"QJID"));
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    for (QszdQlrDO qlr : qlrDOList) {
                        if(StringUtils.isNotBlank(qlr.getQlrmc())){
                            zdQlrList.add(qlr.getQlrmc());
                        }

                    }
                }
            }else{
                List<ZdQlrDO> qlrList = zdQlrService.listZdQlrByDjdcbIndex(MapUtils.getString(dataMap,"QJID"));
                if (CollectionUtils.isNotEmpty(qlrList)) {
                    for (ZdQlrDO qlr : qlrList) {
                        if(StringUtils.isNotBlank(qlr.getQlrmc())){
                            zdQlrList.add(qlr.getQlrmc());
                        }
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(zdQlrList)){
                dataMap.put("QLR",BuildingUtils.wmQlrMcWithList(zdQlrList));
            }
        }
    }


    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 逻辑幢主键 初始化宗地树
     */
    @Override
    public ZdTreeResponseDTO initZdTreeByFwDcbIndex(String fwDcbIndex) {
        ZdTreeResponseDTO zdTreeResponseDTO = new ZdTreeResponseDTO();
        // 1. 根据fwDcbIndex 查询 fw_ljz
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);

        if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getLszd())) {
            String lszd = fwLjzDO.getLszd();
            // 2. 根据LSZD 查询 ZDDJDCB
            ZdDjdcbDO zdDo = querZdByDjh(lszd);
            if (zdDo != null) {
                zdTreeResponseDTO.setZdDjdcbDO(zdDo);

                List<ZdTreeZrzResponseDTO> zrzList = new ArrayList<>();
                // 3. 根据LSZD 查询 FW_K
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("lszd", lszd);
                List<FwKDO> fwKList = fwKMapper.queryFwKList(paramMap);
                if (CollectionUtils.isNotEmpty(fwKList)) {
                    ZdTreeZrzResponseDTO zrz;
                    for(FwKDO fwKDO : fwKList){
                        zrz = new ZdTreeZrzResponseDTO();
                        BeanUtils.copyProperties(fwKDO,zrz);
                        // 只获取当前逻辑幢挂接的自然幢下的逻辑幢列表
                        if(StringUtils.equals(zrz.getZrzh(),fwLjzDO.getZrzh())){
                            zrz.setFwLjzDOList(fwLjzService.listLjzByDjhAndZrzh(fwLjzDO.getLszd(),fwLjzDO.getZrzh()));
                        }
                        zrzList.add(zrz);
                    }
                }
                zdTreeResponseDTO.setZrzList(zrzList);
            }
        }
        return zdTreeResponseDTO;
    }

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 逻辑幢主键 初始化宗地树 (查FW_LJZ表，获取所有ZRZH)
     */
    @Override
    public ZdTreeResponseDTO initZdTreeByFwDcbIndexAndAllZrzh(String fwDcbIndex) {
        ZdTreeResponseDTO zdTreeResponseDTO = new ZdTreeResponseDTO();
        // 1. 根据fwDcbIndex 查询 fw_ljz
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
        if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getLszd())) {
            zdTreeResponseDTO = initZdTreeByDjhAndAllZrzh(fwLjzDO.getLszd(),fwLjzDO.getZrzh());
        }
        return zdTreeResponseDTO;
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 地籍号 初始化宗地树 (查FW_LJZ表，获取所有ZRZH)
     */
    @Override
    public ZdTreeResponseDTO initZdTreeByDjhAndAllZrzh(String djh,String zrzh) {
        ZdTreeResponseDTO dto = new ZdTreeResponseDTO();
        if (StringUtils.isNotBlank(djh)) {
            // 2. 根据LSZD 查询 ZDDJDCB
            ZdDjdcbDO zdDo = querZdByDjh(djh);
            if (zdDo != null) {
                dto.setZdDjdcbDO(zdDo);
                List<String> zrzhList = zrzService.listZrzhByDjh(djh);
                List<ZdTreeZrzResponseDTO> zrzList = new ArrayList<>();
                // 3. 根据LSZD 查询 所有zrzh
                if (CollectionUtils.isNotEmpty(zrzhList)) {
                    ZdTreeZrzResponseDTO zrz;
                    for(int i = 0 ; i < zrzhList.size() ; i++){
                        zrz = new ZdTreeZrzResponseDTO();
                        zrz.setZrzh(zrzhList.get(i));
                        // 只获取当前逻辑幢挂接的自然幢下的逻辑幢列表
                        // 或者当前ZRZH为空时 获取 第一个ZRZH
                        if((StringUtils.isBlank(zrzh) && i== 0)
                                || StringUtils.equals(zrzh,zrzhList.get(i))){
                            zrz.setFwLjzDOList(fwLjzService.listLjzByDjhAndZrzh(djh,zrzhList.get(i)));
                        }
                        zrzList.add(zrz);
                    }
                }
                dto.setZrzList(zrzList);
            }
        }
        return dto;
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH 查询 宗地 信息
     */
    @Override
    public ZdDjdcbDO querZdByDjh(String djh) {
        if (StringUtils.isNotBlank(djh)) {
            Example example = new Example(ZdDjdcbDO.class);
            example.createCriteria().andEqualTo("djh", djh);
            List<ZdDjdcbDO> zdList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(zdList)) {
                return zdList.get(0);
            }
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询宗地宗地
     */
    @Override
    public ZdDjdcbDO queryZdDjdcbByBdcdyh(String bdcdyh) {
        return bdcdyService.queryDjxxByBdcdyh(bdcdyh, ZdDjdcbDO.class);
    }

    /**
     * @param djhList
     * @return lczt
     * @author
     * @description 根据地籍号查询宗地流程状态
     */
    @Override
    public List<String> queryZdLcztByBdcdyh(List<String> djhList) {
        if (CollectionUtils.isNotEmpty(djhList)) {
            Map<String,Object> map = new HashMap<>();
            map.put("djhList", djhList);
            map.put("tableName", zdTable);
            List<String> result = repo.selectList("listZdLczt", map);
            if (CollectionUtils.isNotEmpty(result)) {
                return result;
            }
        }
        return null;
    }




    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH 查询宗地 DCB
     */
    @Override
    public ZdDjdcbDO queryZdDjdcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjh(djh,ZdDjdcbDO.class);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH 查询备份宗地 DCB
     */
    @Override
    public HZdDjdcbDO queryHZdDjdcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjhWithOrder(djh,HZdDjdcbDO.class,"gxrq desc");
    }

    @Override
    public Page<DjdcbJzxxResponseDTO> listZdzjxxByPage(Pageable pageable, Map map) {
        return repo.selectPaging("listZdzjxxByPageOrder", map, pageable);
    }

    /**
     * @param djh
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据地籍号查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByDjh(String djh) {
        List<String> bdcdyhList = new ArrayList<>();
        if(StringUtils.isNotBlank(djh)){
            List<FwLjzDO> fwLjzDOList=fwLjzService.listLjzByDjhAndZrzh(djh,null);
            if(CollectionUtils.isNotEmpty(fwLjzDOList)){
                for(FwLjzDO fwLjzDO:fwLjzDOList){
                    if(fwLjzDO!=null){
                        bdcdyhList.addAll(fwLjzService.listValidBdcdyhByFwLjzDO(fwLjzDO));
                    }
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * 根据地籍号和自然幢号查询有效的房屋户室不动产单元号
     * @param djh  地籍号
     * @param zrzh 自然幢号
     * @return List<String> 房屋户室不动产单元号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<String> listValidBdcdyhByDjhZrzh(String djh, String zrzh) {
        if(StringUtils.isBlank(djh)) {
            return Collections.emptyList();
        }

        List<String> bdcdyhList = new ArrayList<>();
        List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByDjhAndZrzh(djh,zrzh);
        if(CollectionUtils.isNotEmpty(fwLjzDOList)){
            for(FwLjzDO fwLjzDO:fwLjzDOList){
                if(fwLjzDO != null){
                    bdcdyhList.addAll(fwLjzService.listValidBdcdyhByFwLjzDO(fwLjzDO));
                }
            }
        }
        return bdcdyhList;
    }

    @Override
    public Page<Map> listZdxxByPageJson(Pageable pageable, Map map) {
        Page<Map> result = repo.selectPaging("listZdxxByPageJson", map, pageable);
        return result;
    }

    /**
     * @param zl
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据ZL 查询 宗地 信息
     */
    @Override
    public ZdDjdcbDO queryZdDjdcbByZlAndBdcdyh(String zl,String bdcdyh) {
        Example example = new Example(ZdDjdcbDO.class);
        if (StringUtils.isNotBlank(zl)) {
            example.createCriteria().andEqualTo("tdzl", zl);

        }
        if (StringUtils.isNotBlank(bdcdyh)) {

            example.createCriteria().andEqualTo("djh",bdcdyh.substring(0,19));
        }
        List<ZdDjdcbDO> zdList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(zdList)) {
            return zdList.get(0);
        }
        return null;
    }

    @Override
    public List<ZdDjdcbDO> queryZdDjdcbByzl(String zl) {
        Example example = new Example(ZdDjdcbDO.class);
        if (StringUtils.isNotBlank(zl)) {
            example.createCriteria().andLike("tdzl", "%" + zl + "%");
        }
        return entityMapper.selectByExample(example);
    }
}
