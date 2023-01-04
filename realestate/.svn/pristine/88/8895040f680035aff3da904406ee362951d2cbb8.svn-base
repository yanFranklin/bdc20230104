package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.FwXmxxService;
import cn.gtmap.realestate.building.service.DcxxService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.dto.building.DcxxDTO;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-27
 * @description 调查信息服务
 */
@Service
public class DcxxServiceImpl implements DcxxService {

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private FwXmxxService fwXmxxService;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param bdcdyfwlx
     * @param fwIndex
     * @return cn.gtmap.realestate.common.core.dto.building.DcxxDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FW_INDEX 和 bdcdyfwlx 查询房屋户室调查信息
     */
    @Override
    public DcxxDTO queryFwDcxx(String bdcdyfwlx,String fwIndex) {
        if(StringUtils.isNotBlank(fwIndex)){
            Object fwObj = queryFwDO(bdcdyfwlx,fwIndex);
            if(fwObj != null){
                DcxxDTO dcxxDTO = new DcxxDTO();
                // 对象赋值
                BeanUtils.copyProperties(fwObj, dcxxDTO);
                return dcxxDTO;
            }
        }
        return null;
    }

    /**
     * @param dcxxDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存调查信息
     */
    @Override
    public Integer updateFwDcxx(DcxxDTO dcxxDTO) {
        if(dcxxDTO != null
                && StringUtils.isNotBlank(dcxxDTO.getBdcdyfwlx())
                && StringUtils.isNotBlank(dcxxDTO.getFwIndex())){
            Object fwObj = queryFwDO(dcxxDTO.getBdcdyfwlx(),dcxxDTO.getFwIndex());
            if(fwObj != null){
                BeanUtils.copyProperties(dcxxDTO,fwObj);
                return entityMapper.updateByPrimaryKeyNull(fwObj);
            }else{
                throw new EntityNotFoundException("房屋实体不存在:" + dcxxDTO.toString());
            }
        }else{
            throw new MissingArgumentException("fwHsIndex");
        }
    }

    /**
     * @param fwIndex
     * @param bdcdyfwlx
     * @return Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除房屋户室调查信息
     */
    @Override
    public Integer deleteFwDcxx(String bdcdyfwlx,String fwIndex) {
        if(StringUtils.isNotBlank(bdcdyfwlx)
                && StringUtils.isNotBlank(fwIndex)){
            Object fwObj = queryFwDO(bdcdyfwlx,fwIndex);
            if(fwObj != null){
                DcxxDTO dcxxDTO = new DcxxDTO();
                dcxxDTO.setFwIndex(fwIndex);
                dcxxDTO.setBdcdyfwlx(bdcdyfwlx);
                // 对象赋值 空属性
                BeanUtils.copyProperties(dcxxDTO,fwObj);
                return entityMapper.updateByPrimaryKeyNull(fwObj);
            }
        }
        return 0;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyfwlx
     * @param fwIndex
     * @return java.lang.Object
     * @description 查询房屋实体
     */
    private Object queryFwDO(String bdcdyfwlx,String fwIndex){
        Object fwObj = null;

        // 户室
        if(StringUtils.equals(Constants.BDCDYFWLX_H,bdcdyfwlx)){
            fwObj = fwHsService.queryFwHsByIndex(fwIndex);
        }

        // 独幢
        if(StringUtils.equals(Constants.BDCDYFWLX_DZ,bdcdyfwlx)){
            fwObj = fwLjzService.queryLjzByFwDcbIndex(fwIndex);
        }

        // 项目内多幢
        if(StringUtils.equals(Constants.BDCDYFWLX_XMNDZ,bdcdyfwlx)){
            fwObj = fwXmxxService.queryXmxxByPk(fwIndex);
        }

        return fwObj;
    }

}
