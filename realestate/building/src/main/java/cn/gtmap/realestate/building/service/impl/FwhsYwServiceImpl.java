package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.mapper.FwHsMapper;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwYcHsService;
import cn.gtmap.realestate.building.service.FwhsYwService;
import cn.gtmap.realestate.building.util.LpbUtils;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsBatchUpdateRequestDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-04
 * @description 房屋户室业务服务
 */
@Service
public class FwhsYwServiceImpl implements FwhsYwService {

    @Autowired
    private FwHsMapper fwHsMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwYcHsService fwYcHsService;

    private final static int BATCH_UPDATE_MAX = 50;

    /**
     * @param dto
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量更新
     */
    @Override
    @Transactional
    public void batchUpdate(FwhsBatchUpdateRequestDTO dto) {
        List<String> fwhsIndexList = dto.getFwHsIndexList();
        if(CollectionUtils.isNotEmpty(fwhsIndexList) || StringUtils.isNotBlank(dto.getFwDcbIndex())){
            // 判断更新属性参数是否为空
            if(MapUtils.isNotEmpty(dto.getParamMap())){
                batchUpdateSx(dto);
            }
            // 判断是否有替换操作
            if(StringUtils.equals("true", dto.getReplace())){
                batchReplace(dto);
            }
        }else{
            throw new MissingArgumentException("fwhsIndexList or fwDcbIndex");
        }
    }

    /**
     * @param fwYchsDO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询预测户室关联的房屋户室
     */
    @Override
    public List<FwHsDO> listGlFwhs(FwYchsDO fwYchsDO) {
        if(fwYchsDO != null
                && StringUtils.isNotBlank(fwYchsDO.getFwbm())){
            Example example = new Example(FwHsDO.class);
            if(fwYchsDO.getFwbm().contains(";")){
                String[] ycFwbmArr = fwYchsDO.getFwbm().split(";");
                example.createCriteria().andIn("ycfwbm", Arrays.asList(ycFwbmArr));
                return entityMapper.selectByExample(example);
            }else{
                Map map=new HashMap();
                List<String> ycfwbms=new ArrayList<>();
                ycfwbms.add(fwYchsDO.getFwbm());
                map.put("ycfwbms",ycfwbms);
                return fwHsMapper.listFwHsByPageOrder(map);
            }
        }
        return null;
    }

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 关联户室分页查询
     */
    @Override
    public Page<Map> glListHsByPage(Pageable pageable, Map map) {
        Page<FwHsDO> result = fwHsService.listFwHsByPage(pageable,map);
        List<Map> newResultList = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(result.getContent())){
            List<FwHsDO> content = result.getContent();
            for(FwHsDO temp : content){
                Map mapTemp = LpbUtils.parseObjectToMap(temp);
                // 查询关联户室
                List<FwYchsDO> glYcHsList = fwYcHsService.listGlYchs(temp);
                if(CollectionUtils.isNotEmpty(glYcHsList)){
                    String glhs = "";
                    for(FwYchsDO fwYchsDO : glYcHsList){
                        if(StringUtils.isNotBlank(fwYchsDO.getFjh())){
                            glhs += fwYchsDO.getFjh() + ",";
                        }
                    }
                    if(StringUtils.isNotBlank(glhs)){
                        glhs = glhs.substring(0,glhs.length()-1);
                    }
                    mapTemp.put("glhs",glhs);
                }
                newResultList.add(mapTemp);
            }
        }
        return new PageImpl(newResultList,pageable,result.getTotalElements());
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param dto
     * @return void
     * @description 批量更新属性
     */
    private void batchUpdateSx(FwhsBatchUpdateRequestDTO dto){
        Map paramMap = dto.getParamMap();
        List<String> fwhsIndexList = dto.getFwHsIndexList();
        if(MapUtils.isNotEmpty(paramMap)){
            Iterator<Map.Entry<String,Object>> iterator = paramMap.entrySet().iterator();
            while (iterator.hasNext()) {
                // 构造每一个需要更新属性的 更新SQL
                Map.Entry<String,Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                Map<String,Object> updateParamMap = new HashMap<>();
                if(value == null || StringUtils.equals("null",value.toString())){
                    updateParamMap.put("statment", key + "= null");
                }else{
                    updateParamMap.put("statment", key + "='" + value+"'");
                }
                // 是否只刷新 空值
                if(StringUtils.equals("true", dto.getUpdateNullOnly())){
                    updateParamMap.put("updateNullOnly",key + " is null");
                }
                if(CollectionUtils.isNotEmpty(fwhsIndexList)){
                    // 执行分页批量更新
                    batchUpdateByPage(fwhsIndexList,updateParamMap);
                }else if(StringUtils.isNotBlank(dto.getFwDcbIndex())){
                    updateParamMap.put("fwDcbIndex",dto.getFwDcbIndex());
                    fwHsMapper.batchUpdateFwhs(updateParamMap);
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param dto
     * @return void
     * @description 批量替换操作
     */
    private void batchReplace(FwhsBatchUpdateRequestDTO dto){
        // 替换属性
        String column = dto.getReplaceColumn();
        // 替换值
        String thz = dto.getReplaceThz();
        // 目标值
        String mbz = dto.getReplaceMbz();
        List<String> fwhsIndexList = dto.getFwHsIndexList();
        if(StringUtils.isNoneBlank(column,thz)){
            // 目标值处理空值
            if(mbz == null || StringUtils.equals("null",mbz)){
                mbz = "";
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("column",column);
            paramMap.put("thz",thz);
            paramMap.put("mbz",mbz);
            if(CollectionUtils.isNotEmpty(fwhsIndexList)){
                batchReplaceByPage(fwhsIndexList,paramMap);
            }else if(StringUtils.isNotBlank(dto.getFwDcbIndex())){
                paramMap.put("fwDcbIndex",dto.getFwDcbIndex());
                fwHsMapper.replaceUpdateFwhs(paramMap);
            }
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwhsIndexList
     * @param paramMap
     * @return void
     * @description 分页执行批量替换
     */
    private void batchReplaceByPage(List<String> fwhsIndexList,Map<String,Object> paramMap){
        List<String> updateList = new ArrayList<>();
        for(String fwhsIndex : fwhsIndexList){
            updateList.add(fwhsIndex);
            if(updateList.size() == BATCH_UPDATE_MAX){
                paramMap.put("fwhsIndexList",updateList);
                fwHsMapper.replaceUpdateFwhs(paramMap);
                updateList = new ArrayList<>();
            }
        }
        if(CollectionUtils.isNotEmpty(updateList)){
            paramMap.put("fwhsIndexList",updateList);
            fwHsMapper.replaceUpdateFwhs(paramMap);
        }
    }



    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwhsIndexList
     * @param updateParamMap
     * @return void
     * @description 分页执行批量更新
     */
    private void batchUpdateByPage(List<String> fwhsIndexList,Map<String,Object> updateParamMap){
        List<String> updateList = new ArrayList<>();
        for(String fwhsIndex : fwhsIndexList){
            updateList.add(fwhsIndex);
            if(updateList.size() == BATCH_UPDATE_MAX){
                updateParamMap.put("fwhsIndexList",updateList);
                fwHsMapper.batchUpdateFwhs(updateParamMap);
                updateList = new ArrayList<>();
            }
        }
        if(CollectionUtils.isNotEmpty(updateList)){
            updateParamMap.put("fwhsIndexList",updateList);
            fwHsMapper.batchUpdateFwhs(updateParamMap);
        }
    }
}
