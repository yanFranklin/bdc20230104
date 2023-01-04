package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.ZdQsdcService;
import cn.gtmap.realestate.building.core.service.ZhQlrService;
import cn.gtmap.realestate.building.core.service.ZhService;
import cn.gtmap.realestate.building.service.InitDjxxService;
import cn.gtmap.realestate.building.service.ZhDjxxService;
import cn.gtmap.realestate.building.util.BdcdySqlConstants;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-16
 * @description
 */
@Service
public class ZhDjxxServiceImpl implements InitDjxxService, ZhDjxxService {
    @Autowired
    private ZhService zhService;

    @Autowired
    private ZhQlrService zhQlrService;

    @Autowired
    private EntityZdConvertUtils entityZdConvertUtils;

    @Autowired
    private DozerBeanMapper initDjxxDozerMapper;

    @Autowired
    private ZdQsdcService zdQsdcService;
    /**
     * @param bdcdyh
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号查询 地籍信息
     */
    @Override
    public DjxxResponseDTO getDjxxForInitByBdcdyh(String bdcdyh,String djqlrgxid) {
        // 使用BDCDYH 查询 DJDCB 信息 和 QLR 信息
        String zhdm = BuildingUtils.getDjhByBdcdyh(bdcdyh);
        ZhDjdcbDO zhDjdcbDO = zhService.queryZhDjdcbByZhdm(zhdm);
        if (zhDjdcbDO != null && StringUtils.isNotBlank(zhDjdcbDO.getZhDjdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
            // 查询调查部实体
            ZhDjdcbResponseDTO zhDjdcbResponseDTO = new ZhDjdcbResponseDTO();
            BeanUtils.copyProperties(zhDjdcbDO, zhDjdcbResponseDTO);
            djxxResponseDTO.setDjDcbResponseDTO(zhDjdcbResponseDTO);
            //查询权利人
            List<ZhQlrDO> zhQlrDoList = zhQlrService.listZhQlrByZhDcbIndex(zhDjdcbDO.getZhDjdcbIndex());
            List<DjxxQlrDTO> qlrDTOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(zhQlrDoList)) {
                for (ZhQlrDO zhQlrDo : zhQlrDoList) {
                    qlrDTOList.add(revertQlrDOToDTO(zhQlrDo));
                }
            }
            // 查询宗海及内部单元记录表属性结构描述
            List<ZhZhjnbdyjlb> zhZhjnbdyjlbList = zhService.listZhZhjnbdyjlb(zhDjdcbDO.getZhDjdcbIndex());
            if (CollectionUtils.isNotEmpty(zhZhjnbdyjlbList)) {
                zhDjdcbResponseDTO.setZhZhjnbdyjlbList(zhZhjnbdyjlbList);
            }
            //查询界址信息
            List<ZhJzbsb> zhJzbsbs = zhService.listZhJzbsb(zhDjdcbDO.getZhDjdcbIndex());
            if (CollectionUtils.isNotEmpty(zhJzbsbs)) {
                zhDjdcbResponseDTO.setZhJzbsbList(zhJzbsbs);
            }
            
            // 查询宗海权属调查
            ZhQsdcDO zhQsdcDO = zhService.queryZhQsdcDO(zhDjdcbDO.getZhdm());
            if (zhDjdcbDO != null){
                zhDjdcbResponseDTO.setZhQsdcDO(zhQsdcDO);
            }

            djxxResponseDTO.setQlrList(qlrDTOList);
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
        // 使用BDCDYH 查询 DJDCB 信息 和 QLR 信息
        ZhDjdcbDO zhDjdcbDO = zhService.queryHZhDjdcbByBdcdyh(bdcdyh);
        if (zhDjdcbDO != null && StringUtils.isNotBlank(zhDjdcbDO.getZhDjdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
            // 查询调查部实体
            ZhDjdcbResponseDTO zhDjdcbResponseDTO = new ZhDjdcbResponseDTO();
            BeanUtils.copyProperties(zhDjdcbDO, zhDjdcbResponseDTO);
            djxxResponseDTO.setDjDcbResponseDTO(zhDjdcbResponseDTO);
            //查询权利人
            List<HZhQlrDO> zhQlrDoList = zhQlrService.listHZhQlrByZhDcbIndex(zhDjdcbDO.getZhDjdcbIndex());
            List<DjxxQlrDTO> qlrDTOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(zhQlrDoList)) {
                for (ZhQlrDO zhQlrDo : zhQlrDoList) {
                    qlrDTOList.add(revertQlrDOToDTO(zhQlrDo));
                }
            }
            // 查询宗海及内部单元记录表属性结构描述
            List<HZhZhjnbdyjlb> hzhZhjnbdyjlbList = zhService.listHZhZhjnbdyjlb(zhDjdcbDO.getZhDjdcbIndex());
            if (CollectionUtils.isNotEmpty(hzhZhjnbdyjlbList)) {
                List<ZhZhjnbdyjlb> zhZhjnbdyjlbList = new ArrayList<>();
                for(HZhZhjnbdyjlb htemp:hzhZhjnbdyjlbList){
                    ZhZhjnbdyjlb jlb = new ZhZhjnbdyjlb();
                    BeanUtils.copyProperties(htemp,jlb);
                    zhZhjnbdyjlbList.add(jlb);
                }
                zhDjdcbResponseDTO.setZhZhjnbdyjlbList(zhZhjnbdyjlbList);
            }
            //查询界址信息
            List<HZhJzbsbDO> hZhJzbsbs = zhService.listHZhJzbsb(zhDjdcbDO.getZhDjdcbIndex());
            if (CollectionUtils.isNotEmpty(hZhJzbsbs)) {
                List<ZhJzbsb> zhJzbsbs = new ArrayList<>();
                for(HZhJzbsbDO temp:hZhJzbsbs){
                    ZhJzbsb zhJzbsb = new ZhJzbsb();
                    BeanUtils.copyProperties(temp,zhJzbsb);
                    zhJzbsbs.add(zhJzbsb);
                }
                zhDjdcbResponseDTO.setZhJzbsbList(zhJzbsbs);
            }
            djxxResponseDTO.setQlrList(qlrDTOList);
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
            ZhQlrDO zhQlrDo = (ZhQlrDO) qlrDO;
            DjxxQlrDTO djxxQlrDTO = new DjxxQlrDTO();
            initDjxxDozerMapper.map(zhQlrDo,djxxQlrDTO);
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
        beanName.add(Constants.DJXX_SERVICE_ZH);
        return beanName;
    }

    /**
     * @param bdcdyh
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 判断BDCDYH，如果构筑物特征码是W 则直接查询DJDCB，
     * 否则截取地籍号搜索 针对使用构筑物BDCDYH查询宗地调查表的场景
     */
    @Override
    public ZhDjdcbDO queryDjdcbByBdcdyhOrDjh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            return zhService.queryZhDjdcbByBdcdyh(bdcdyh);
        }
        return null;
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
            ZhDjdcbDO zhDjdcbDO=queryDjdcbByBdcdyhOrDjh(bdcdyh);
            if(zhDjdcbDO!=null){
                entityZdConvertUtils.convertEntityToMc(zhDjdcbDO);
                ZhDjdcbResponseDTO zhDjdcbResponseDTO = new ZhDjdcbResponseDTO();
                BuildingUtils.copyPropertiesWithOutNull(zhDjdcbDO, zhDjdcbResponseDTO);
                dto.setDjDcbResponseDTO(zhDjdcbResponseDTO);
            }
            if(withQlr){
                List<ZhQlrDO> qlrDO=zhQlrService.listZhQlrByBdcdyh(bdcdyh);
                List<DjdcbQlrResponseDTO> qlrResponseDTOS=new ArrayList<>();
                if(qlrDO!=null){
                    for (ZhQlrDO zhQlrDO:qlrDO){
                        entityZdConvertUtils.convertEntityToMc(zhQlrDO);
                        DjdcbQlrResponseDTO djdcbQlrResponseDTO=new DjdcbQlrResponseDTO();
                        BuildingUtils.copyPropertiesWithOutNull(zhQlrDO, djdcbQlrResponseDTO);
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
        return zdQsdcService.queryQsdcByDjh(djh,"zh_qsdc");
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH判断是否需要查询DJDCB
     */
    @Override
    public boolean checkNeedDjdcb(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh) && ZhDjdcbDO.class.equals(TzmUtils.getDjdcbDoWithBdcdyh(bdcdyh))) {
            return true;
        }
        return false;
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证是否需要查询DJ QLR
     */
    @Override
    public boolean checkNeedDjQlr(String bdcdyh) {
        return TzmUtils.isTdBdcdy(bdcdyh) && checkNeedDjdcb(bdcdyh);
    }

    /**
     * @param bdcdyh
     * @param qlr
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 给权利人set外键
     */
    @Override
    public void setDjQlrFkVal(String bdcdyh, ZhQlrDO qlr) {
        if (StringUtils.isNotBlank(bdcdyh) && qlr != null) {
            ZhDjdcbDO zhDjdcbDO = zhService.queryZhDjdcbByBdcdyh(bdcdyh);
            if (zhDjdcbDO != null) {
                qlr.setDjdcbIndex(zhDjdcbDO.getZhDjdcbIndex());
            }
        }
    }

    /**
     * @param withQlr
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取查询BDCDY的SQL
     */
    @Override
    public String getBdcdyQuerySql(boolean withQlr,String qlrmh) {
        String sql = BdcdySqlConstants.ZH_DJDCB_SQL;
        if(withQlr){
            if(BdcdySqlConstants.relationDjxxQlrWithFk()){
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.ZH_DJDCB_QLR_SQL_WITHFK,qlrmh);
            }else{
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.ZH_DJDCB_QLR_SQL,qlrmh);
            }
        }
        return sql;
    }

    @Override
    public String getLqBdcdyQuerySql(boolean withQlr,String qlrmh){
        return "";
    }
}
