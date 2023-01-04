package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.ZdJzwsuqgydcService;
import cn.gtmap.realestate.building.core.service.ZdQlrService;
import cn.gtmap.realestate.building.core.service.ZdQsdcService;
import cn.gtmap.realestate.building.core.service.ZdService;
import cn.gtmap.realestate.building.service.InitDjxxService;
import cn.gtmap.realestate.building.service.ZdDjxxService;
import cn.gtmap.realestate.building.util.BdcdySqlConstants;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-16
 * @description 宗地 地籍信息处理服务
 */
@Service
public class ZdDjxxServiceImpl implements InitDjxxService, ZdDjxxService {

    private Logger LOGGER = LoggerFactory.getLogger(ZdDjxxServiceImpl.class);

    @Autowired
    private ZdService zdService;

    @Autowired
    private ZdQsdcService zdQsdcService;

    @Autowired
    private ZdQlrService zdQlrService;

    @Autowired
    private ZdJzwsuqgydcService zdJzwsuqgydcService;

    @Autowired
    private EntityZdConvertUtils entityZdConvertUtils;

    @Autowired
    private DozerBeanMapper initDjxxDozerMapper;


    /**
     * @param bdcdyh
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号查询 地籍信息
     */
    @Override
    public DjxxResponseDTO getDjxxForInitByBdcdyh(String bdcdyh,String djqlrgxid) {
        // 使用DJH 查询 DJDCB 信息 和 QLR 信息
        String djh = BuildingUtils.getDjhByBdcdyh(bdcdyh);
        ZdDjdcbDO zdDjdcbDO = zdService.queryZdDjdcbByDjh(djh);
        if (zdDjdcbDO != null && StringUtils.isNotBlank(zdDjdcbDO.getZdDjdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
            // ZD_DJDCB 实体
            ZdDjdcbResponseDTO zdDTO = new ZdDjdcbResponseDTO();
            BeanUtils.copyProperties(zdDjdcbDO, zdDTO);
            djxxResponseDTO.setDjDcbResponseDTO(zdDTO);

            // 处理权利人
            List<ZdQlrDO> zdQlrDOList = zdQlrService.listZdQlrByDjdcbIndex(zdDjdcbDO.getZdDjdcbIndex());
            if (CollectionUtils.isNotEmpty(zdQlrDOList)) {
                DjxxQlrDTO djxxQlrDTO;
                List<DjxxQlrDTO> djxxQlrDTOList = new ArrayList<>();
                for (ZdQlrDO qlrDO : zdQlrDOList) {
                    djxxQlrDTO = revertQlrDOToDTO(qlrDO);
                    if (djxxQlrDTO != null) {
                        djxxQlrDTOList.add(djxxQlrDTO);
                    }
                }
                if (CollectionUtils.isNotEmpty(djxxQlrDTOList)) {
                    djxxResponseDTO.setQlrList(djxxQlrDTOList);
                }
            }

            // 查询 宗地建筑物所有权共有调查实体List
            List<ZdJzwsuqgydcDO> jzwgyList = zdJzwsuqgydcService.listZdJzwgyByDjh(djh);
            if (CollectionUtils.isNotEmpty(jzwgyList)) {
                djxxResponseDTO.setZdJzwsuqgydcDOList(jzwgyList);
            }
            return djxxResponseDTO;
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号查询 备份表的地籍信息
     */
    @Override
    public DjxxResponseDTO getHDjxxForInitByBdcdyh(String bdcdyh) {
        // 使用DJH 查询 DJDCB 信息 和 QLR 信息
        String djh = BuildingUtils.getDjhByBdcdyh(bdcdyh);
        ZdDjdcbDO zdDjdcbDO = zdService.queryHZdDjdcbByDjh(djh);
        if (zdDjdcbDO != null && StringUtils.isNotBlank(zdDjdcbDO.getZdDjdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
            // ZD_DJDCB 实体
            ZdDjdcbResponseDTO zdDTO = new ZdDjdcbResponseDTO();
            BeanUtils.copyProperties(zdDjdcbDO, zdDTO);
            djxxResponseDTO.setDjDcbResponseDTO(zdDTO);

            // 处理权利人
            List<HZdQlrDO> zdQlrDOList = zdQlrService.listHZdQlrByDjdcbIndex(zdDjdcbDO.getZdDjdcbIndex());
            if (CollectionUtils.isNotEmpty(zdQlrDOList)) {
                DjxxQlrDTO djxxQlrDTO;
                List<DjxxQlrDTO> djxxQlrDTOList = new ArrayList<>();
                for (ZdQlrDO qlrDO : zdQlrDOList) {
                    djxxQlrDTO = revertQlrDOToDTO(qlrDO);
                    if (djxxQlrDTO != null) {
                        djxxQlrDTOList.add(djxxQlrDTO);
                    }
                }
                if (CollectionUtils.isNotEmpty(djxxQlrDTOList)) {
                    djxxResponseDTO.setQlrList(djxxQlrDTOList);
                }
            }

            // 查询 宗地建筑物所有权共有调查实体List
            List<HZdJzwsuqgydcDO> hJzwgyList = zdJzwsuqgydcService.listHZdJzwgyByDjh(djh);
            if (CollectionUtils.isNotEmpty(hJzwgyList)) {
                List<ZdJzwsuqgydcDO> jzwgyList = new ArrayList<>();
                for(HZdJzwsuqgydcDO hgy : hJzwgyList){
                    ZdJzwsuqgydcDO zdJzwsuqgydcDO = new ZdJzwsuqgydcDO();
                    BeanUtils.copyProperties(hgy,zdJzwsuqgydcDO);
                    jzwgyList.add(zdJzwsuqgydcDO);
                }
                djxxResponseDTO.setZdJzwsuqgydcDOList(jzwgyList);
            }
            return djxxResponseDTO;
        }
        return null;
    }


    /**
     * @param qlrDO
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description DO权利人实体  转换 成 DTO 后面改成使用dozer组件处理
     */
    @Override
    public DjxxQlrDTO revertQlrDOToDTO(Object qlrDO) {
        if (qlrDO != null) {
            ZdQlrDO zdQlrDO = (ZdQlrDO) qlrDO;
            DjxxQlrDTO djxxQlrDTO = new DjxxQlrDTO();
            initDjxxDozerMapper.map(zdQlrDO,djxxQlrDTO);
            return djxxQlrDTO;
        }
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
        beanName.add(Constants.DJXX_SERVICE_ZD);
        return beanName;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 判断BDCDYH，如果构筑物特征码是W 则直接查询ZD_DJDCB，
     * 否则截取地籍号搜索 针对使用构筑物BDCDYH查询宗地基本信息场景
     */
    @Override
    public ZdDjdcbDO queryDjdcbByBdcdyhOrDjh(String bdcdyh) {
        ZdDjdcbDO zdDjdcbDO = null;
        if (StringUtils.isNotBlank(bdcdyh)) {
            if (TzmUtils.isTdBdcdy(bdcdyh)) {
                zdDjdcbDO = zdService.queryZdDjdcbByBdcdyh(bdcdyh);
            } else {
                zdDjdcbDO = zdService.queryZdDjdcbByDjh(BuildingUtils.getDjhByBdcdyh(bdcdyh));
            }
        }
        if(zdDjdcbDO != null){
            LOGGER.error("zdDjdcb:{}",JSONObject.toJSONString(zdDjdcbDO));
        }
        return zdDjdcbDO;
    }


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO
     * @description 根据不动产单元号查询权利人和调查表信息
     */
    @Override
    public DjDcbAndQlrResponseDTO queryDjdcbAndQlrByBdcdyh(String bdcdyh,boolean withQlr) {
        DjDcbAndQlrResponseDTO dto=new DjDcbAndQlrResponseDTO();
        if(StringUtils.isNotBlank(bdcdyh)){
            ZdDjdcbDO zdDjdcbDO=queryDjdcbByBdcdyhOrDjh(bdcdyh);
            if(zdDjdcbDO!=null){
                entityZdConvertUtils.convertEntityToMc(zdDjdcbDO);
                ZdDjdcbResponseDTO zdDTO = new ZdDjdcbResponseDTO();
                BeanUtils.copyProperties(zdDjdcbDO, zdDTO);
                dto.setDjDcbResponseDTO(zdDTO);
            }
            if(withQlr){
                List<ZdQlrDO> qlrDO=zdQlrService.listZdQlrByDjh(bdcdyh.substring(0,19));
                List<DjdcbQlrResponseDTO> qlrResponseDTOS=new ArrayList<>();
                if(qlrDO!=null){
                    for (ZdQlrDO zdQlrDO:qlrDO){
                        entityZdConvertUtils.convertEntityToMc(zdQlrDO);
                        DjdcbQlrResponseDTO djdcbQlrResponseDTO=new DjdcbQlrResponseDTO();
                        BeanUtils.copyProperties(zdQlrDO, djdcbQlrResponseDTO);
                        qlrResponseDTOS.add(djdcbQlrResponseDTO);
                    }
                    dto.setQlrResponseDTOList(qlrResponseDTOS);
                }
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
        return zdQsdcService.queryQsdcByDjh(djh,"zd_qsdc");
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询 是否 需要查询 ZD_DJDCB数据
     */
    @Override
    public boolean checkNeedDjdcb(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh) && ZdDjdcbDO.class.equals(TzmUtils.getDjdcbDoWithBdcdyh(bdcdyh))) {
            return true;
        }
        return false;
    }

    /**
     * @param bdcdyh
     * @param zdQlrDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 存放 zdQlr 的外键
     */
    @Override
    public void setDjQlrFkVal(String bdcdyh, ZdQlrDO zdQlrDO) {
        if (StringUtils.isNotBlank(bdcdyh) && zdQlrDO != null) {
            // 根据BDCDYH 查询 ZD_DJDCB
            ZdDjdcbDO zdDjdcbDO = zdService.queryZdDjdcbByBdcdyh(bdcdyh);
            if (zdDjdcbDO != null) {
                zdQlrDO.setDjdcbIndex(zdDjdcbDO.getZdDjdcbIndex());
                zdQlrDO.setDjh(zdDjdcbDO.getDjh());
            }
        }
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证是否需要查询ZDQLR
     */
    @Override
    public boolean checkNeedDjQlr(String bdcdyh) {
        return TzmUtils.isTdBdcdy(bdcdyh) && checkNeedDjdcb(bdcdyh);
    }

    /**
     * @param withQlr
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取查询BDCDY的SQL
     */
    @Override
    public String getBdcdyQuerySql(boolean withQlr,String qlrmh) {
        String sql = BdcdySqlConstants.ZD_DJDCB_SQL;
        if(withQlr){
            if(BdcdySqlConstants.relationDjxxQlrWithFk()){
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.ZD_DJDCB_QLR_SQL_WITHFK,qlrmh);
            }else{
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.ZD_DJDCB_QLR_SQL,qlrmh);
            }
        }
        return sql;
    }

    @Override
    public String getLqBdcdyQuerySql(boolean withQlr,String qlrmh){
        return "";
    }

    @Override
    public String getLsBdcdyQuerySql(boolean withQlr, String qlrmh) {
        return BdcdySqlConstants.ZD_LS_DJDCB_SQL;
    }

    /**
     * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据zl查询地籍信息（多个）
     * @Date 2022/5/9
     **/
    @Override
    public DjxxZdResponseDTO queryDjdcbAndQlrByzl(String zl) {
        DjxxZdResponseDTO djxxZdResponseDTO = new DjxxZdResponseDTO();
        djxxZdResponseDTO.setList(new ArrayList<>());
        // 查询zd_djdcb信息
        List<ZdDjdcbDO> zdDjdcbDOList = zdService.queryZdDjdcbByzl(zl);
        if (CollectionUtils.isNotEmpty(zdDjdcbDOList)) {
            // 获取地籍号的djhs
            List<Object> djhs = zdDjdcbDOList.stream()
                    .map(ZdDjdcbDO::getDjh)
                    .filter(Objects::nonNull)
                    .distinct().collect(Collectors.toList());
            if (CollectionUtils.isEmpty(djhs)) {
                return djxxZdResponseDTO;
            }
            // 查询权利人信息
            List<ZdQlrDO> zdQlrDOS = subsection(djhs, 1000);
            Map<String, List<ZdQlrDO>> qlrMap = zdQlrDOS.stream()
                    .collect(Collectors.groupingBy(ZdQlrDO::getDjh));
            for (ZdDjdcbDO zdDjdcbDO : zdDjdcbDOList) {
                DjxxZdResponseDTO.ZdxxResponseDTO dto = new DjxxZdResponseDTO.ZdxxResponseDTO();
                List<ZdQlrDO> zdQlrDOList = qlrMap.get(zdDjdcbDO.getDjh());
                dto.setZdDjdcbDO(zdDjdcbDO);
                dto.setZdQlrDO(CollectionUtils.isNotEmpty(zdQlrDOList) ? zdQlrDOList.get(0) : new ZdQlrDO());
                djxxZdResponseDTO.getList().add(dto);
            }
        }
        return djxxZdResponseDTO;
    }

    /**
     * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据分段设置值，进行分段查询
     * @Date 2022/5/9
     **/
    private List<ZdQlrDO> subsection(List<Object> djhs, Integer subsectionSize) {
        List<ZdQlrDO> zdQlrDOList = new ArrayList<>();
        if (djhs.size() > subsectionSize) {
            int num = djhs.size() / subsectionSize + 1;
            int residual = djhs.size() % subsectionSize;
            for (int i = 0; i < num; i++) {
                List<Object> subList = djhs.subList(i * subsectionSize, i == num - 1 ? i * subsectionSize + residual : (i + 1) * subsectionSize);
                List<ZdQlrDO> zdQlrDOS = zdQlrService.listZdQlrByDjhs(subList);
                zdQlrDOList.addAll(zdQlrDOS);
            }
            return zdQlrDOList;
        } else {
            return Optional.ofNullable(zdQlrService.listZdQlrByDjhs(djhs)).orElse(new ArrayList<>());
        }
    }
}
