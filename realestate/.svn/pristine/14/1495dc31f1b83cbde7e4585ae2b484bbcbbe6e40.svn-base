package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.QszdQlrService;
import cn.gtmap.realestate.building.core.service.QszdService;
import cn.gtmap.realestate.building.core.service.ZdQsdcService;
import cn.gtmap.realestate.building.service.InitDjxxService;
import cn.gtmap.realestate.building.service.QszdDjxxService;
import cn.gtmap.realestate.building.util.BdcdySqlConstants;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
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
public class QszdDjxxServiceImpl implements InitDjxxService, QszdDjxxService {

    @Autowired
    private QszdService qszdService;

    @Autowired
    private QszdQlrService qszdQlrService;

    @Autowired
    private EntityMapper entityMapper;
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
        String djh = BuildingUtils.getDjhByBdcdyh(bdcdyh);
        // 使用DJH 查询 DJDCB 信息 和 QLR 信息
        QszdDjdcbDO qszdDjdcbDO = qszdService.queryQszdDjdcbByDjh(djh);
        if (qszdDjdcbDO != null && StringUtils.isNotBlank(qszdDjdcbDO.getQszdDjdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();

            // DJDCB 实体
            QszdDjdcbResponseDTO qszdDTO = new QszdDjdcbResponseDTO();
            BeanUtils.copyProperties(qszdDjdcbDO, qszdDTO);

            //查询地类面积
            Example example=new Example(QszdZdmjDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("djdcbIndex",qszdDjdcbDO.getQszdDjdcbIndex());
            List<QszdZdmjDO> qszdZdmjDOList=entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(qszdZdmjDOList)){
                qszdDTO.setQszdZdmjDOList(qszdZdmjDOList);
            }

            djxxResponseDTO.setDjDcbResponseDTO(qszdDTO);

            // QLR 信息
            List<QszdQlrDO> qszdQlrDOList = qszdQlrService.listQszdQlrByQszdDjdcbIndex(qszdDjdcbDO.getQszdDjdcbIndex());
            if (CollectionUtils.isNotEmpty(qszdQlrDOList)) {
                DjxxQlrDTO djxxQlrDTO;
                List<DjxxQlrDTO> djxxQlrDTOList = new ArrayList<>();
                for (QszdQlrDO qlrDO : qszdQlrDOList) {
                    djxxQlrDTO = revertQlrDOToDTO(qlrDO);
                    if (djxxQlrDTO != null) {
                        djxxQlrDTOList.add(djxxQlrDTO);
                    }
                }
                if (CollectionUtils.isNotEmpty(djxxQlrDTOList)) {
                    djxxResponseDTO.setQlrList(djxxQlrDTOList);
                }
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
        String djh = BuildingUtils.getDjhByBdcdyh(bdcdyh);
        // 使用DJH 查询 DJDCB 信息 和 QLR 信息
        QszdDjdcbDO qszdDjdcbDO = qszdService.queryHQszdDjdcbByDjh(djh);
        if (qszdDjdcbDO != null && StringUtils.isNotBlank(qszdDjdcbDO.getQszdDjdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();

            // DJDCB 实体
            QszdDjdcbResponseDTO qszdDTO = new QszdDjdcbResponseDTO();
            BeanUtils.copyProperties(qszdDjdcbDO, qszdDTO);

            //查询地类面积
            Example example=new Example(QszdZdmjDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("djdcbIndex",qszdDjdcbDO.getQszdDjdcbIndex());
            List<HQszdZdmjDO> hQszdZdmjDOList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(hQszdZdmjDOList)){
                List<QszdZdmjDO> qszdZdmjDOS = new ArrayList<>();
                for(HQszdZdmjDO hzdmj : hQszdZdmjDOList){
                    QszdZdmjDO qszdZdmjDO = new QszdZdmjDO();
                    BeanUtils.copyProperties(hzdmj,qszdZdmjDO);
                    qszdZdmjDOS.add(qszdZdmjDO);
                }
                qszdDTO.setQszdZdmjDOList(qszdZdmjDOS);
            }

            djxxResponseDTO.setDjDcbResponseDTO(qszdDTO);

            // QLR 信息
            List<HQszdQlrDO> qszdQlrDOList = qszdQlrService.listHQszdQlrByQszdDjdcbIndex(qszdDjdcbDO.getQszdDjdcbIndex());
            if (CollectionUtils.isNotEmpty(qszdQlrDOList)) {
                DjxxQlrDTO djxxQlrDTO;
                List<DjxxQlrDTO> djxxQlrDTOList = new ArrayList<>();
                for (QszdQlrDO qlrDO : qszdQlrDOList) {
                    djxxQlrDTO = revertQlrDOToDTO(qlrDO);
                    if (djxxQlrDTO != null) {
                        djxxQlrDTOList.add(djxxQlrDTO);
                    }
                }
                if (CollectionUtils.isNotEmpty(djxxQlrDTOList)) {
                    djxxResponseDTO.setQlrList(djxxQlrDTOList);
                }
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
            QszdQlrDO qszdQlrDO = (QszdQlrDO) qlrDO;
            DjxxQlrDTO djxxQlrDTO = new DjxxQlrDTO();
            initDjxxDozerMapper.map(qszdQlrDO,djxxQlrDTO);
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
        beanName.add(Constants.DJXX_SERVICE_QSZD);
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
    public QszdDjdcbDO queryDjdcbByBdcdyhOrDjh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            if (TzmUtils.isTdBdcdy(bdcdyh)) {
                return qszdService.queryQszdDjdcbByBdcdyh(bdcdyh);
            } else {
                return qszdService.queryQszdDjdcbByDjh(BuildingUtils.getDjhByBdcdyh(bdcdyh));
            }
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
    public DjDcbAndQlrResponseDTO queryDjdcbAndQlrByBdcdyh(String bdcdyh, boolean withQlr) {
        DjDcbAndQlrResponseDTO dto=new DjDcbAndQlrResponseDTO();
        if(StringUtils.isNotBlank(bdcdyh)){
            QszdDjdcbDO qszdDjdcbDO=queryDjdcbByBdcdyhOrDjh(bdcdyh);
            if(qszdDjdcbDO!=null){
                entityZdConvertUtils.convertEntityToMc(qszdDjdcbDO);
                QszdDjdcbResponseDTO qszdDTO = new QszdDjdcbResponseDTO();
                BuildingUtils.copyPropertiesWithOutNull(qszdDjdcbDO, qszdDTO);
                dto.setDjDcbResponseDTO(qszdDTO);
            }
            if(withQlr){
                List<QszdQlrDO> qlrDO=qszdQlrService.listQszdQlrByDjh(bdcdyh.substring(0,19));
                List<DjdcbQlrResponseDTO> qlrResponseDTOS=new ArrayList<>();
                if(qlrDO!=null){
                    for (QszdQlrDO qszdQlrDO:qlrDO){
                        entityZdConvertUtils.convertEntityToMc(qszdQlrDO);
                        DjdcbQlrResponseDTO djdcbQlrResponseDTO=new DjdcbQlrResponseDTO();
                        BuildingUtils.copyPropertiesWithOutNull(qszdQlrDO, djdcbQlrResponseDTO);
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
        return zdQsdcService.queryQsdcByDjh(djh,"qszd_qsdc");
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH判断是否需要查询DJDCB
     */
    @Override
    public boolean checkNeedDjdcb(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh) && QszdDjdcbDO.class.equals(TzmUtils.getDjdcbDoWithBdcdyh(bdcdyh))) {
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
    public void setDjQlrFkVal(String bdcdyh, QszdQlrDO qlr) {
        if (StringUtils.isNotBlank(bdcdyh) && qlr != null) {
            QszdDjdcbDO qszdDjdcbDO = qszdService.queryQszdDjdcbByBdcdyh(bdcdyh);
            if (qszdDjdcbDO != null) {
                qlr.setDjdcbIndex(qszdDjdcbDO.getQszdDjdcbIndex());
                qlr.setDjh(qszdDjdcbDO.getDjh());
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
        String sql = BdcdySqlConstants.QSZD_DJDCB_SQL;
        if(withQlr){
            if(BdcdySqlConstants.relationDjxxQlrWithFk()){
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.QSZD_DJDCB_QLR_SQL_WITHFK,qlrmh);
            }else{
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.QSZD_DJDCB_QLR_SQL,qlrmh);
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
        return BdcdySqlConstants.QSZD_LS_DJDCB_SQL;
    }
}
