package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.service.*;
import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxZdResponseDTO;
import cn.gtmap.realestate.common.util.InterfaceCodeBeanFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-17
 * @description 地籍信息封装DTO服务
 */
@Service
public class DjxxServiceImpl extends InterfaceCodeBeanFactory implements DjxxService {

    @Autowired
    private Set<InitDjxxService> initDjxxServiceSet;

    @Autowired
    private Set<DjxxDozerService> djxxDozerServices;


    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号 构造 DJXX DTO 包括（djdcb dcb qlr）
     */
    @Override
    public DjxxResponseDTO getDjxxDTOForInitByBdcdyh(String bdcdyh,String djqlrgxid) {
        // 根据BDCDYH 获取 beanName
        String[] beanCode = TzmUtils.getBeanNameByBdcdyh(bdcdyh);
        if (beanCode !=null &&beanCode.length>0) {
            for (int i = 0; i < beanCode.length; i++) {
                InitDjxxService djxxService = getBean(initDjxxServiceSet, beanCode[i]);
                if (djxxService != null) {
                    DjxxResponseDTO djxxResponseDTO =djxxService.getDjxxForInitByBdcdyh(bdcdyh,djqlrgxid);
                    if(djxxResponseDTO != null){
                        return djxxResponseDTO;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description [备份] 根据不动产单元号 构造 DJXX DTO 包括（djdcb dcb qlr）
     */
    @Override
    public DjxxResponseDTO getHDjxxDTOForInitByBdcdyh(String bdcdyh) {
        // 根据BDCDYH 获取 beanName
        String[] beanCode = TzmUtils.getBeanNameByBdcdyh(bdcdyh);
        if (beanCode !=null &&beanCode.length>0) {
            for (int i = 0; i < beanCode.length; i++) {
                InitDjxxService djxxService = getBean(initDjxxServiceSet, beanCode[i]);
                if (djxxService != null) {
                    DjxxResponseDTO djxxResponseDTO= djxxService.getHDjxxForInitByBdcdyh(bdcdyh);
                    if(djxxResponseDTO != null){
                        return djxxResponseDTO;
                    }
                }

            }
        }

        return null;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号查询DJDCB
     */
    @Override
    public DjDcbResponseDTO getDjdcbDTOByBdcdyh(String bdcdyh) {
        // 根据BDCDYH 获取 beanName
        String[] beanCode = TzmUtils.getBeanNameByBdcdyh(bdcdyh);
        if (beanCode !=null &&beanCode.length>0) {
            for (int i = 0; i < beanCode.length; i++) {
                DjxxDozerService djxxService = getBean(djxxDozerServices, beanCode[i]);
                if (djxxService != null) {
                    DjDcbAndQlrResponseDTO dto = djxxService.queryDjdcbAndQlrByBdcdyh(bdcdyh, false);
                    if(dto != null&& dto.getDjDcbResponseDTO() != null){
                        return dto.getDjDcbResponseDTO();
                    }
                }
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO
     * @description 根据BDCDYH查询地籍信息 包含DJDCB、权利人
     */
    @Override
    public DjDcbAndQlrResponseDTO getDjdcbAndQlrByBdcdyh(String bdcdyh) {
        // 根据BDCDYH 获取 beanName
        String[] beanCode = TzmUtils.getBeanNameByBdcdyh(bdcdyh);
        if (beanCode !=null &&beanCode.length>0) {
            for (int i = 0; i < beanCode.length; i++) {
                DjxxDozerService djxxService = getBean(djxxDozerServices, beanCode[i]);
                if (djxxService != null) {
                    DjDcbAndQlrResponseDTO djDcbAndQlrResponseDTO= djxxService.queryDjdcbAndQlrByBdcdyh(bdcdyh, true);
                    if(djDcbAndQlrResponseDTO != null &&djDcbAndQlrResponseDTO.getDjDcbResponseDTO() !=null){
                        return djDcbAndQlrResponseDTO;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return ZdQsdcDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据不动产单元号 查询权属调查信息
     */
    @Override
    public ZdQsdcDO queryZdQsdcByBdcdyh(String bdcdyh) {
        // 根据BDCDYH 获取 beanName
        String[] beanCode = TzmUtils.getBeanNameByBdcdyh(bdcdyh);
        if (beanCode !=null &&beanCode.length>0) {
            for (int i = 0; i < beanCode.length; i++) {
                DjxxDozerService djxxService = getBean(djxxDozerServices, beanCode[i]);
                if (djxxService != null && StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
                    ZdQsdcDO zdQsdcDO= djxxService.queryQsdcByDjh(bdcdyh.substring(0, 19));
                    if(zdQsdcDO != null){
                        return zdQsdcDO;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据zl查询地籍信息（多个）
     * @Date 2022/5/9
     **/
    @Override
    public DjxxZdResponseDTO queryBdcjsydsyqByzl(String zl) {
        String[] beanCode = TzmUtils.getBeanNameWithTzm("BW");
        if (beanCode !=null &&beanCode.length>0) {
            for (int i = 0; i < beanCode.length; i++) {
                ZdDjxxService djxxService = (ZdDjxxService) getBean(djxxDozerServices, beanCode[i]);
                if (djxxService != null) {
                    DjxxZdResponseDTO djxxZdResponseDTO= djxxService.queryDjdcbAndQlrByzl(zl);
                    if(djxxZdResponseDTO != null){
                        return djxxZdResponseDTO;
                    }
                }
            }
        }
        return null;
    }




}
