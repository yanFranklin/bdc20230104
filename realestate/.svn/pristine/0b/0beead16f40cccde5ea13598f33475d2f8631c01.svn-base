package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.JyqDkQlrService;
import cn.gtmap.realestate.building.core.service.JyqDkService;
import cn.gtmap.realestate.building.core.service.LqService;
import cn.gtmap.realestate.building.service.InitDjxxService;
import cn.gtmap.realestate.building.service.JyqDkDjxxService;
import cn.gtmap.realestate.building.util.BdcdySqlConstants;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.TzmUtils;

import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020-11-6
 * @description 经营权地块 地籍信息相关服务
 */
@Service
public class JyqDkDjxxServiceImpl implements InitDjxxService, JyqDkDjxxService {

    @Autowired
    private JyqDkService jyqDkService;

    @Autowired
    private JyqDkQlrService jyqDkQlrService;

    @Autowired
    private EntityZdConvertUtils entityZdConvertUtils;

    @Autowired
    private DozerBeanMapper initDjxxDozerMapper;

    @Autowired
    private LqService lqService;



    /**
     * @param qlrDO 权利人实体
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description DO权利人实体  转换 成 DTO 后面改成使用dozer组件处理
     */
    @Override
    public DjxxQlrDTO revertQlrDOToDTO(Object qlrDO) {
        if (qlrDO != null) {
            JyqDkQlrDO jyqDkQlrDO = (JyqDkQlrDO) qlrDO;
            DjxxQlrDTO djxxQlrDTO = new DjxxQlrDTO();
            initDjxxDozerMapper.map(jyqDkQlrDO,djxxQlrDTO);
            return djxxQlrDTO;
        }
        return null;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return T
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号查询 地籍信息
     */
    @Override
    public DjxxResponseDTO getDjxxForInitByBdcdyh(String bdcdyh,String djqlrgxid) {
        String djh = BuildingUtils.getDjhByBdcdyh(bdcdyh);
        JyqDkDcbDO jyqDkDcbDO = jyqDkService.queryJyqDkDcbByBdcdyh(bdcdyh);
        if (jyqDkDcbDO != null && StringUtils.isNotBlank(jyqDkDcbDO.getJyqdkdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
            // 查询经营权调查表实体
            JyqDkDcbResponseDTO jyqDkDTO = new JyqDkDcbResponseDTO();
            BuildingUtils.copyPropertiesWithOutNull(jyqDkDcbDO, jyqDkDTO);

            // 查询 经营权地块权利人
            List<JyqDkQlrDO> jyqDkQlrDOList = jyqDkQlrService.listJyqDkQlrByDjdcbIndex(jyqDkDcbDO.getJyqdkdcbIndex());
            List<DjxxQlrDTO> qlrDTOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(jyqDkQlrDOList)) {
                jyqDkDTO.setJyqDkQlrDOList(jyqDkQlrDOList);
            }

            //查询流出方信息
            JyqDkLcfDO jyqDkLcfDO =jyqDkQlrService.queryJyqDkLcfByJyqDkLcfIndex(jyqDkDcbDO.getJyqdklcfIndex());
            if(jyqDkLcfDO != null){
                jyqDkDTO.setJyqDkLcfDO(jyqDkLcfDO);

            }


            // 保存 经营权地块权利人
            djxxResponseDTO.setQlrList(qlrDTOList);

            // 处理林权 地籍信息相关属性字段
            if (TzmUtils.hasLqDcb(bdcdyh)) {
                initLqDjxx(djh,jyqDkDTO);
            }

            djxxResponseDTO.setDjDcbResponseDTO(jyqDkDTO);
            return djxxResponseDTO;
        }
        return null;
    }


    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  处理林权 地籍信息相关属性字段
      */
    private void initLqDjxx(String djh,JyqDkDcbResponseDTO jyqDkDcbResponseDTO) {
        // 根据BDCDYH 查询 林权地籍信息
        LqDcbDO lqDcbDO = lqService.queryLqDcbByDjh(djh);
        if (lqDcbDO != null && StringUtils.isNotBlank(lqDcbDO.getLqDcbIndex())) {
            Map<String, Object> doMap = JSONObject.parseObject(JSONObject.toJSONString(lqDcbDO), Map.class);
            Map<String, Object> revertMap = revertDOToDTOMap(doMap, "lq");
            JyqDkDcbResponseDTO djxxDTOTemp = JSONObject.parseObject(JSONObject.toJSONString(revertMap), JyqDkDcbResponseDTO.class);
            BuildingUtils.copyPropertiesWithOutNull(djxxDTOTemp, jyqDkDcbResponseDTO);
        }
    }



    /**
     * @param doMap
     * @return java.util.Map<java.lang.String               ,               java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理 DO 转成  DTO Map
     */
    private static Map<String, Object> revertDOToDTOMap(Map<String, Object> doMap, String prefix) {
        Map<String, Object> revertMap = new HashMap<>();
        if (MapUtils.isNotEmpty(doMap)) {
            Iterator iterator = doMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = entry.getKey().toString();
                Object val = entry.getValue();
                if (val != null) {
                    if (!StringUtils.startsWith(key, prefix)) {
                        key = prefix + key;
                    }
                    revertMap.put(key, val);
                }
            }
        }
        return revertMap;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号查询 备份表的地籍信息
     */
    @Override
    public DjxxResponseDTO getHDjxxForInitByBdcdyh(String bdcdyh) {
        return null;
    }

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> beanName = new HashSet<>(1);
        beanName.add(Constants.DJXX_SERVICE_JYQDK);
        return beanName;
    }

   /**
    * @param bdcdyh 不动产单元号
    * @return 经营权调查表
    * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
    * @description 判断BDCDYH，如果构筑物特征码是W 则直接查询JYQDK_DCB，
    *      * 否则截取地籍号加上W00000000搜索 针对使用构筑物BDCDYH查询经营权基本信息场景
    */
    @Override
    public JyqDkDcbDO queryDjdcbByBdcdyhOrDjh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            if (!TzmUtils.isTdBdcdy(bdcdyh)) {
                bdcdyh =BuildingUtils.getDjhByBdcdyh(bdcdyh) +CommonConstantUtils.SUFFIX_ZD_BDCDYH;
            }
            return jyqDkService.queryJyqDkDcbByBdcdyh(bdcdyh);
        }
        return null;
    }
    /**
     * @param bdcdyh 不动产单元号
     * @param withQlr 是否包含权利人
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据不动产单元号查询权利人和调查表信息
     */
    @Override
    public DjDcbAndQlrResponseDTO queryDjdcbAndQlrByBdcdyh(String bdcdyh,boolean withQlr) {
        DjDcbAndQlrResponseDTO dto=new DjDcbAndQlrResponseDTO();
        if(StringUtils.isNotBlank(bdcdyh)){
            JyqDkDcbDO jyqDkDcbDO=queryDjdcbByBdcdyhOrDjh(bdcdyh);
            if(jyqDkDcbDO!=null) {
                entityZdConvertUtils.convertEntityToMc(jyqDkDcbDO);
                JyqDkDcbResponseDTO jyqDTO = new JyqDkDcbResponseDTO();
                BuildingUtils.copyPropertiesWithOutNull(jyqDkDcbDO, jyqDTO);
                if (withQlr) {
                    // 查询 经营权地块权利人
                    List<JyqDkQlrDO> jyqDkQlrDOList = jyqDkQlrService.listJyqDkQlrByDjdcbIndex(jyqDkDcbDO.getJyqdkdcbIndex());
                    if (CollectionUtils.isNotEmpty(jyqDkQlrDOList)) {
                        for (JyqDkQlrDO jyqDkQlrDO : jyqDkQlrDOList) {
                            entityZdConvertUtils.convertEntityToMc(jyqDkQlrDO);
                        }
                        jyqDTO.setJyqDkQlrDOList(jyqDkQlrDOList);
                    }

                    //查询流出方信息
                    JyqDkLcfDO jyqDkLcfDO =jyqDkQlrService.queryJyqDkLcfByJyqDkLcfIndex(jyqDkDcbDO.getJyqdklcfIndex());
                    if(jyqDkLcfDO != null){
                        jyqDTO.setJyqDkLcfDO(jyqDkLcfDO);

                    }

                }
                dto.setDjDcbResponseDTO(jyqDTO);
            }
        }
        return dto;
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据djh查询权属调查信息
     */
    @Override
    public ZdQsdcDO queryQsdcByDjh(String djh) {
        return null;

    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据BDCDYH查询 是否 需要查询JYQDK_DCB数据
     */
    @Override
    public boolean checkNeedDjdcb(String bdcdyh) {
       return StringUtils.isNotBlank(bdcdyh) && JyqDkDcbDO.class.equals(TzmUtils.getDjdcbDoWithBdcdyh(bdcdyh));
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询 是否需要查询 NYD_QLR数据
     */
    @Override
    public boolean checkNeedDjQlr(String bdcdyh) {
        return TzmUtils.isTdBdcdy(bdcdyh) && checkNeedDjdcb(bdcdyh);
    }

    /**
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 给 农用地权利人set外键值
     * BdcdyxxEnum.java 反射调用方法
     */
    @Override
    public void setDjQlrFkVal(String bdcdyh, JyqDkQlrDO jyqDkQlrDO) {
        // Do nothing
    }

    /**
     * @param withQlr
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取查询BDCDY的SQL
     */
    @Override
    public String getBdcdyQuerySql(boolean withQlr,String qlrmh) {
        String sql = BdcdySqlConstants.JYQDK_DJDCB_SQL;
        if(withQlr){
            sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.JYQDK_DJDCB_QLR_SQL,qlrmh);
        }
        return sql;
    }

    @Override
    public String getLqBdcdyQuerySql(boolean withQlr,String qlrmh){
        String sql =BdcdySqlConstants.JYQDK_DJDCB_SQL;
        if(withQlr){
            sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.JYQDK_DJDCB_QLR_SQL,qlrmh);
        }
        return sql;
    }
}
