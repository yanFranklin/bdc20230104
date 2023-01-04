package cn.gtmap.realestate.inquiry.service.impl.yancheng;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqDjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqZxsqDO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqJsDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdlxEnum;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZqZxsqMapper;
import cn.gtmap.realestate.inquiry.service.yancheng.BdcZqSdService;
import cn.gtmap.realestate.inquiry.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2021/01/08
 * @description 盐城征迁单元证书锁定、解锁逻辑
 */
@Service
public class BdcZqSdServiceImpl implements BdcZqSdService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZqSdServiceImpl.class);

    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcSdFeignService sdFeignService;
    @Autowired
    private BdcZsFeignService zsFeignService;
    @Autowired
    private BdcZqZxsqMapper bdcZqZxsqMapper;
    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;


    /**
     * 注销时候锁定不动产单元和证书
     * @param bdcZqZxsqDO 注销申请信息
     */
    @Override
    public void sdBdcdyZs4Zx(BdcZqZxsqDO bdcZqZxsqDO) {
       this.sdBdcdy(bdcZqZxsqDO.getSqxxid(), bdcZqZxsqDO.getBdcdyh(), Constants.YC_ZQZX_SDYY);

       this.sdZs(bdcZqZxsqDO.getSqxxid(), bdcZqZxsqDO.getBdcqzh(), Constants.YC_ZQZX_SDYY);
    }

    /**
     * 冻结时候同步锁定单元、证书
     * @param bdcZqDjDO
     */
    @Override
    public void sdBdcdyZs4Dj(BdcZqDjDO bdcZqDjDO) {
        String sdyy = getDjsdyy(bdcZqDjDO);

        this.sdBdcdy(bdcZqDjDO.getDjxxid(), bdcZqDjDO.getBdcdyh(), sdyy);
        this.sdZs(bdcZqDjDO.getDjxxid(), bdcZqDjDO.getBdcqzh(), sdyy);
    }

    /**
     * 冻结时候批量同步锁定单元、证书
     * @param zqDjDOList 多个单元冻结信息
     */
    @Override
    public void sdBdcdyZs4Dj(List<BdcZqDjDO> zqDjDOList) {
        this.sdBdcdyList(zqDjDOList);
        this.sdZsList(zqDjDOList);
    }

    /**
     * 解锁单元、证书
     * @param bdcZqJsDTO 解锁参数信息
     */
    @Override
    public void jsBdcdyZs(BdcZqJsDTO bdcZqJsDTO) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        bdcZqJsDTO.setJsr(null == userDto ? "" : userDto.getAlias());
        bdcZqJsDTO.setJsrid(null == userDto ? "" : userDto.getId());
        bdcZqJsDTO.setJssj(new Date());

        // 解锁不动产单元
        this.jsBdcdy(bdcZqJsDTO);
        // 解锁证书
        this.jsBdcZs(bdcZqJsDTO);

        // 同步修改权籍单元状态
        List<String> bdcdyList = new ArrayList<>();
        bdcdyList.add(bdcZqJsDTO.getBdcdyh());
        bdcDbxxFeignService.synQjBdcdyztSd(bdcdyList, CommonConstantUtils.SDZT_JS);
        LOGGER.info("征迁解冻、流程退回、删除时同步修改权籍单元状态，单元号:{}", bdcZqJsDTO.getBdcdyh());
    }

    /**
     * 锁定不动产单元
     * @param id 对应场景实体信息ID
     * @param bdcdyh 不动产单元号
     * @param sdyy 锁定原因
     */
    private void sdBdcdy(String id, String bdcdyh, String sdyy) {
        if(StringUtils.isBlank(bdcdyh)) {
            LOGGER.warn("{}同步锁定单元因单元号不存在，处理中止，对应信息ID：{}", sdyy, id);
            return;
        }

        List<BdcDysdDO> bdcDysdDOList = new ArrayList<>();
        bdcDysdDOList.add(new BdcDysdDO());
        bdcDysdDOList.get(0).setBdcdyh(bdcdyh);
        bdcDysdDOList.get(0).setSdyy(sdyy);
        bdcDysdDOList.get(0).setSdlx(BdcSdlxEnum.ZSSD.getCode());
        sdFeignService.sdBdcdy(bdcDysdDOList);
        LOGGER.info("{}同步锁定单元，单元号{}，信息ID{}", sdyy, bdcdyh, id);
    }

    /**
     * 批量锁定单元
     * @param zqDjDOList 征迁冻结信息
     */
    private void sdBdcdyList(List<BdcZqDjDO> zqDjDOList) {
        if(CollectionUtils.isEmpty(zqDjDOList)) {
            LOGGER.warn("征迁冻结同步锁定单元未定义单元信息，处理中止");
            return;
        }

        Set<String> bdcdyhSet = new HashSet<>();
        List<BdcDysdDO> bdcDysdDOList = new ArrayList<>();
        for(BdcZqDjDO bdcZqDjDO : zqDjDOList) {
            if(bdcdyhSet.contains(bdcZqDjDO.getBdcdyh())) {
                continue;
            }

            BdcDysdDO bdcDysdDO = new BdcDysdDO();
            bdcDysdDO.setBdcdyh(bdcZqDjDO.getBdcdyh());
            bdcDysdDO.setBdcdywybh(bdcZqDjDO.getBdcdywybh());
            bdcDysdDO.setSdyy(getDjsdyy(bdcZqDjDO));
            bdcDysdDO.setSdlx(BdcSdlxEnum.ZSSD.getCode());

            bdcDysdDOList.add(bdcDysdDO);
            bdcdyhSet.add(bdcZqDjDO.getBdcdyh());
        }

        int count = sdFeignService.sdBdcdy(bdcDysdDOList);
        LOGGER.info("征迁冻结同步锁定单元，记录数：{}，冻结文号：{}", count, zqDjDOList.get(0).getDjwh());
    }

    /**
     * 锁定证书
     * @param id 对应场景实体信息ID
     * @param bdcqzhs 不动产权证号
     * @param sdyy 锁定原因
     */
    private void sdZs(String id, String bdcqzhs, String sdyy) {
        if(StringUtils.isBlank(bdcqzhs)) {
            LOGGER.warn("{}同步锁定证书因单元号不存在，处理中止，信息ID：{}", sdyy, id);
            return;
        }

        String[] bdcqzhArray = bdcqzhs.split(",");
        for(String bdcqzh : bdcqzhArray) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setBdcqzh(bdcqzh);
            List<BdcZsDO> zsDOList = zsFeignService.listBdcZs(bdcZsQO);
            if(CollectionUtils.isEmpty(zsDOList) || null == zsDOList.get(0)) {
                return;
            }

            List<BdcZssdDO> bdcZssdDOList = new ArrayList<>();
            BdcZssdDO bdcZssdDO = new BdcZssdDO();
            bdcZssdDO.setZsid(zsDOList.get(0).getZsid());
            bdcZssdDO.setCqzh(bdcqzh);
            bdcZssdDO.setSdlx(BdcSdlxEnum.ZSSD.getCode());
            bdcZssdDOList.add(bdcZssdDO);
            sdFeignService.sdBdczs(bdcZssdDOList, sdyy);
            LOGGER.info("{}同步锁定证书，证号{}，信息ID{}", sdyy, bdcqzh, id);
        }
    }

    /**
     * 批量锁定证书
     * @param zqDjDOList 征迁冻结信息
     */
    private void sdZsList(List<BdcZqDjDO> zqDjDOList) {
        if(CollectionUtils.isEmpty(zqDjDOList)) {
            LOGGER.warn("征迁冻结同步锁定证书未定义冻结信息，处理中止");
            return;
        }

        List<String> bdcqzhList = new ArrayList<>();
        for(BdcZqDjDO bdcZqDjDO : zqDjDOList) {
            if(StringUtils.isBlank(bdcZqDjDO.getBdcqzh())) {
                continue;
            }

            for(String bdcqzh : bdcZqDjDO.getBdcqzh().split(",")) {
                bdcqzhList.add(bdcqzh);
            }
        }

        // 批量获取证号对应的证书信息
        List<BdcZsDO> bdcZsDOList = bdcZqZxsqMapper.queryBdcZsByZhList(bdcqzhList);
        if(CollectionUtils.isEmpty(bdcZsDOList)) {
            return;
        }

        List<BdcZssdDO> bdcZssdDOList = new ArrayList<>();
        for(BdcZsDO zsDO : bdcZsDOList) {
            BdcZssdDO bdcZssdDO = new BdcZssdDO();
            bdcZssdDO.setZsid(zsDO.getZsid());
            bdcZssdDO.setCqzh(zsDO.getBdcqzh());
            bdcZssdDO.setSdlx(BdcSdlxEnum.ZSSD.getCode());
            bdcZssdDOList.add(bdcZssdDO);
        }
        int count = sdFeignService.sdBdczs(bdcZssdDOList, getDjsdyy(zqDjDOList.get(0)));
        LOGGER.info("征迁冻结同步锁定证书，记录数：{}，冻结文号：{}", count, zqDjDOList.get(0).getDjwh());
    }

    /**
     * 解锁不动产单元
     * @param bdcZqJsDTO 解锁参数信息
     */
    private void jsBdcdy(BdcZqJsDTO bdcZqJsDTO) {
        if(StringUtils.isBlank(bdcZqJsDTO.getBdcdyh())) {
            LOGGER.error("{}同步解锁不动产单元因为单元号为空，处理中止", bdcZqJsDTO.getJsyy());
            return;
        }

        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_JS);
        bdcDysdDO.setJsr(bdcZqJsDTO.getJsr());
        bdcDysdDO.setJsrid(bdcZqJsDTO.getJsrid());
        bdcDysdDO.setJssj(bdcZqJsDTO.getJssj());
        bdcDysdDO.setJsyy(bdcZqJsDTO.getJsyy());

        Example example = new Example(BdcDysdDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bdcdyh", bdcZqJsDTO.getBdcdyh());
        criteria.andEqualTo("sdlx", BdcSdlxEnum.ZSSD.getCode());
        criteria.andEqualTo("sdzt", CommonConstantUtils.SDZT_SD);

        // 只解锁因为征迁冻结的单元
        entityMapper.updateByExampleSelectiveNotNull(bdcDysdDO, example);
        LOGGER.info("征迁解冻、流程退回、删除时同步解锁不动产单元，单元号:{}，对应原信息ID：{}", bdcZqJsDTO.getBdcdyh(), bdcZqJsDTO.getId());
    }

    /**
     * 解锁证书
     * @param bdcZqJsDTO 解锁参数信息
     */
    private void jsBdcZs(BdcZqJsDTO bdcZqJsDTO) {
        if(StringUtils.isBlank(bdcZqJsDTO.getBdcqzh())) {
            LOGGER.info("{}同步解锁证书，未指定不动产权证号，操作中止", bdcZqJsDTO.getJsyy());
            return;
        }

        BdcZssdDO zssdDO = new BdcZssdDO();
        zssdDO.setJsr(bdcZqJsDTO.getJsr());
        zssdDO.setJsrid(bdcZqJsDTO.getJsrid());
        zssdDO.setJssj(bdcZqJsDTO.getJssj());
        zssdDO.setJsyy(bdcZqJsDTO.getJsyy());
        zssdDO.setSdzt(CommonConstantUtils.SDZT_JS);

        String[] bdcqzhArray = bdcZqJsDTO.getBdcqzh().split(",");
        for(String bdcqzh : bdcqzhArray) {
            Example example = new Example(BdcZssdDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("cqzh", bdcqzh);
            criteria.andEqualTo("sdlx", BdcSdlxEnum.ZSSD.getCode());
            criteria.andEqualTo("sdzt", CommonConstantUtils.SDZT_SD);
            // 只解锁因为征迁冻结的单元
            entityMapper.updateByExampleSelectiveNotNull(zssdDO, example);
            LOGGER.info("征迁解冻、流程退回、删除时同步解锁证书，证号:{}，对应信息ID：{}", bdcqzh, bdcZqJsDTO.getId());
        }
    }

    public static String getDjsdyy(BdcZqDjDO bdcZqDjDO) {
        if(null == bdcZqDjDO || StringUtils.isBlank(bdcZqDjDO.getDjyy())) {
            return Constants.YC_ZQDJ_SDYY;
        }
        return bdcZqDjDO.getDjyy();
    }

    public static String getDjjsyy(BdcZqDjDO bdcZqDjDO) {
        if(null == bdcZqDjDO || StringUtils.isBlank(bdcZqDjDO.getJdyy())) {
            return Constants.YC_ZQDJ_JSYY;
        }
        return bdcZqDjDO.getJdyy();
    }
}
