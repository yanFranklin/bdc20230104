package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.service.FwlxBgService;
import cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg.BdcdyfwlxBgService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/1/17
 * @description
 */
@Service
public class FwlxBgServiceImpl implements FwlxBgService {

    @Resource(name = "bdcdyfwlxBgXmToHsServiceImpl")
    private BdcdyfwlxBgService bdcdyfwlxBgXmToHsServiceImpl;

    @Resource(name = "bdcdyfwlxBgXmToDzServiceImpl")
    private BdcdyfwlxBgService bdcdyfwlxBgXmToDzServiceImpl;

    @Resource(name = "bdcdyfwlxBgDzToHsServiceImpl")
    private BdcdyfwlxBgService bdcdyfwlxBgDzToHsServiceImpl;

    @Resource(name = "bdcdyfwlxBgDzToXmServiceImpl")
    private BdcdyfwlxBgService bdcdyfwlxBgDzToXmServiceImpl;

    @Resource(name = "bdcdyfwlxBgHsToDzServiceImpl")
    private BdcdyfwlxBgService bdcdyfwlxBgHsToDzServiceImpl;

    @Resource(name = "bdcdyfwlxBgHsToXmServiceImpl")
    private BdcdyfwlxBgService bdcdyfwlxBgHsToXmServiceImpl;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg.BdcdyfwlxBgService
     * @description 根据前后BDCDYFWLX 判断使用哪个实现
     */
    @Override
    public BdcdyfwlxBgService getBean(FwlxBgRequestDTO requestDTO){

        String ylx = requestDTO.getYfwlx();
        String nlx = requestDTO.getFwlx();
        // 项目转独幢
        if(StringUtils.equals(ylx, Constants.BDCDYFWLX_XMNDZ)
                && StringUtils.equals(nlx,Constants.BDCDYFWLX_DZ)){
            return bdcdyfwlxBgXmToDzServiceImpl;
        }

        // 项目转户室
        if(StringUtils.equals(ylx, Constants.BDCDYFWLX_XMNDZ)
                && StringUtils.equals(nlx,Constants.BDCDYFWLX_H)){
            return bdcdyfwlxBgXmToHsServiceImpl;
        }

        // 独幢转项目
        if(StringUtils.equals(ylx, Constants.BDCDYFWLX_DZ)
                && StringUtils.equals(nlx,Constants.BDCDYFWLX_XMNDZ)){
            return bdcdyfwlxBgDzToXmServiceImpl;
        }

        // 独幢转户室
        if(StringUtils.equals(ylx, Constants.BDCDYFWLX_DZ)
                && StringUtils.equals(nlx,Constants.BDCDYFWLX_H)){
            return bdcdyfwlxBgDzToHsServiceImpl;
        }

        // 户室转独幢
        if(StringUtils.equals(ylx, Constants.BDCDYFWLX_H)
                && StringUtils.equals(nlx,Constants.BDCDYFWLX_DZ)){
            return bdcdyfwlxBgHsToDzServiceImpl;
        }

        // 户室转项目
        if(StringUtils.equals(ylx, Constants.BDCDYFWLX_H)
                && StringUtils.equals(nlx,Constants.BDCDYFWLX_XMNDZ)){
            return bdcdyfwlxBgHsToXmServiceImpl;
        }

        return null;
    }
}