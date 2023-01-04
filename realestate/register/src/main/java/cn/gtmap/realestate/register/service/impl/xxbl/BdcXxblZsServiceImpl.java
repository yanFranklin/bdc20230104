package cn.gtmap.realestate.register.service.impl.xxbl;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsQsztDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcXzZsVO;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblZsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 补录证书实现类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1:56 下午 2020/3/13
 */
@Service
public class BdcXxblZsServiceImpl implements BdcXxblZsService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXxblZsServiceImpl.class);
    /**
     * 当前类名
     */
    private static final String CLASS_NAME = BdcXxblZsServiceImpl.class.getName();
    // 英文逗号
    private static final String COMMA_EN = ",";
    /**
     * 冗余字段处理服务
     */
    @Autowired
    private BdcRyzdFeignService bdcRyzdFeignService;
    /**
     * 证书业务服务
     */
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    /**
     * 初始化证书服务
     */
    @Autowired
    private BdcZsInitFeignService bdcZsInitFeignService;
    /**
     * 锁定服务
     */
    @Autowired
    private BdcSdFeignService bdcSdFeignService;

    /**
     * @param xmid   xmid
     * @param bdcqzh bdcqzh 多个证号，英文逗号隔开
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 信息补录更新不动产权证号 </br>
     * 1. 保存证书表中产权证号 </br>
     * 2. 更新相关表中冗余字段 </br>
     * 3. 更新权利其他状况和附记 </br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBlBdcqzh(String xmid, String bdcqzh) {
        if (StringUtils.isAnyBlank(xmid, bdcqzh)) {
            LOGGER.info("{}：补录处理产权证号中止，原因：无产权证号、参数项目ID均不能为空！", CLASS_NAME);
            return;
        }

        List<BdcZsDO> bdcZsDOS = queryZsByXmid(xmid);

        String[] bdcqzhArray = bdcqzh.split(COMMA_EN);
        for (int i = 0; i < bdcZsDOS.size(); i++) {
            // 证号和证书情况为少对多时，其他证书就不更新证号
            if (bdcqzhArray.length - 1 >= i) {
                bdcZsDOS.get(i).setBdcqzh(bdcqzhArray[i]);
                // 证书表保存产权证号
                bdcZsFeignService.updateBdcZs(bdcZsDOS.get(i));
            }
        }

        // 更新产权证号冗余字段
        this.updateRyzdBdcqzh(xmid, bdcZsDOS);
        // 更新权利其他状况和附记
        bdcZsInitFeignService.updateQlqtzkFj(xmid, "1");
    }

    /**
     * @param zsid   证书 id
     * @param xmid   项目 id
     * @param qlqtzk 权利其他状况
     * @param bdcqzh 不动产权证号
     * @param fj     附记
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 更新部分证书信息，同时处理冗余字段 </br>
     * 权利其他状况、附记以及不动产权证号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBfZsxx(String zsid, String xmid, String qlqtzk, String bdcqzh, String fj) {
        // 更新证书信息
        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setZsid(zsid);
        bdcZsDO.setQlqtzk(qlqtzk);
        bdcZsDO.setFj(fj);
        bdcZsDO.setBdcqzh(bdcqzh);
        bdcZsFeignService.updateBdcZs(bdcZsDO);
        // 查询证书信息
        List<BdcZsDO> bdcZsDOS = queryZsByXmid(xmid);

        // 更新产权证号冗余字段
        this.updateRyzdBdcqzh(xmid, bdcZsDOS);
        // 更新权利其他状况和附记
        bdcZsInitFeignService.updateQlqtzkFj(xmid, "1");
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [zsid, xmid, bdcqzh]
     * @return void
     * @description 更新不动产权证号
     */
    @Override
    public void updateBdcqzh(String zsid, String xmid, String bdcqzh) {
        // 更新证书信息
        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setZsid(zsid);
        bdcZsDO.setBdcqzh(bdcqzh);
        bdcZsFeignService.updateBdcZs(bdcZsDO);
        // 查询证书信息
        List<BdcZsDO> bdcZsDOS = queryZsByXmid(xmid);

        // 更新产权证号冗余字段
        this.updateRyzdBdcqzh(xmid, bdcZsDOS);
    }

    /**
     * 更新证书锁定 <br>
     * <p>
     * 由于重新生成证书，所以先对于将原证书锁定信息关联到新生成的证书上 <br/>
     *
     * @param xmid       xmid
     * @param bdcZssdDOS 证书锁定
     * @return 更新数据条数
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public int updateZssd(String xmid, List<BdcZssdDO> bdcZssdDOS) {
        List<BdcZsDO> bdcZsDOS = queryZsByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcZsDOS) && CollectionUtils.isNotEmpty(bdcZssdDOS) && bdcZsDOS.size() == bdcZssdDOS.size()) {
            for (int i = 0; i < bdcZssdDOS.size(); i++) {
                bdcZssdDOS.get(i).setZsid(bdcZsDOS.get(i).getZsid());
//                bdcZssdDOS.get(i).setCqzh(bdcZsDOS.get(i).getBdcqzh());
            }
        }
        return CollectionUtils.isNotEmpty(bdcZssdDOS) ? bdcSdFeignService.updateSdZs(bdcZssdDOS) : 0;
    }

    /**
     * @param xmid     xmid
     * @param bdcZsDOS 证书集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 更新产权证号冗余字段
     */
    public void updateRyzdBdcqzh(String xmid, List<BdcZsDO> bdcZsDOS) {
        Map<String, List<BdcBdcqzhDTO>> bdcqzhMap = Maps.newHashMap();
        List<BdcBdcqzhDTO> bdcBdcqzhDTOS = Lists.newArrayList();
        for (BdcZsDO zsDO : bdcZsDOS) {
            BdcBdcqzhDTO bdcBdcqzhDTO = new BdcBdcqzhDTO();
            bdcBdcqzhDTO.setBdcqzh(zsDO.getBdcqzh());
            bdcBdcqzhDTO.setXmid(xmid);
            bdcBdcqzhDTO.setZsid(zsDO.getZsid());
            bdcBdcqzhDTOS.add(bdcBdcqzhDTO);
        }
        bdcqzhMap.put(xmid, bdcBdcqzhDTOS);
        bdcRyzdFeignService.updateRyzdBdcqzh(bdcqzhMap);
    }

    /**
     * 根据 xmid 查询证书
     *
     * @param xmid xmid
     * @return {List} 证书集合
     * @throws {EntityNotFoundException} 当前项目无证书抛出异常
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<BdcZsDO> queryZsByXmid(String xmid) {
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(xmid);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            LOGGER.warn("{}：获取项目对应的证书为空，对应项目ID：{}", CLASS_NAME, xmid);
            throw new EntityNotFoundException("当前项目对应的证书为空");
        }
        return bdcZsDOS;
    }


    /**
     * 获取证书锁定数据
     *
     * @param xmid
     * @return java.util.List<BdcZssdDO>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcZssdDO> listZssdByXmid(String xmid, Integer sdzt) {
        List<BdcZssdDO> listZssd = Lists.newArrayList();
        List<BdcZsDO> listzs = bdcZsInitFeignService.queryBdcqz(xmid);
        if (CollectionUtils.isNotEmpty(listzs)) {
            BdcZssdDO bdcZssdDO = new BdcZssdDO();
            bdcZssdDO.setSdzt(sdzt);
            for (BdcZsDO bdcZsDO : listzs) {
                if (StringUtils.isBlank(bdcZsDO.getBdcqzh())) {
                    continue;
                }
//                bdcZssdDO.setCqzh(bdcZsDO.getBdcqzh());
                bdcZssdDO.setZsid(bdcZsDO.getZsid());
                List<BdcZssdDO> list = bdcSdFeignService.queryBdczsSd(bdcZssdDO);
                if (CollectionUtils.isNotEmpty(list)) {
                    listZssd.addAll(list);
                }
            }
        }
        return listZssd;
    }

    /**
     * @param bdcXzZsVO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 修正流程更新证书信息
     * @date : 2021/11/25 9:47
     */
    @Override
    public void updateXzBdcZs(BdcXzZsVO bdcXzZsVO) {
        if (StringUtils.isBlank(bdcXzZsVO.getBdcqzh())) {
            throw new MissingArgumentException("bdcqzh");
        }
        if (StringUtils.isBlank(bdcXzZsVO.getXmid())) {
            throw new MissingArgumentException("xmid");
        }
        // 处理英文逗号
        String bdcqzh = StringToolUtils.replaceBracket(bdcXzZsVO.getBdcqzh());

        List<BdcZsDO> bdcZsDOS = queryZsByXmid(bdcXzZsVO.getXmid());

        String[] bdcqzhArray = bdcqzh.split(COMMA_EN);
        for (int i = 0; i < bdcZsDOS.size(); i++) {
            // 证号和证书情况为少对多时，其他证书就不更新证号
            if (bdcqzhArray.length - 1 >= i) {
                bdcZsDOS.get(i).setBdcqzh(bdcqzhArray[i]);
                bdcZsDOS.get(i).setNf(bdcXzZsVO.getNf());
                bdcZsDOS.get(i).setZhlsh(bdcXzZsVO.getZhlsh());
                bdcZsDOS.get(i).setSqsjc(bdcXzZsVO.getSqsjc());
                bdcZsDOS.get(i).setSzsxqc(bdcXzZsVO.getSzsxqc());
                bdcZsDOS.get(i).setBdcqzhjc(bdcXzZsVO.getBdcqzhjc());
                bdcZsDOS.get(i).setZhxlh(bdcXzZsVO.getZhxlh());
                // 证书表保存产权证号
                bdcZsFeignService.updateBdcZs(bdcZsDOS.get(i));
            }
        }

        // 更新产权证号冗余字段
        this.updateRyzdBdcqzh(bdcXzZsVO.getXmid(), bdcZsDOS);
        // 更新权利其他状况和附记
        bdcZsInitFeignService.updateQlqtzkFj(bdcXzZsVO.getXmid(), "1");
    }

    /**
     * 添加项目和已有证书的关联关系
     *
     * @param bdcZsQO
     * @author <a href="mailto:liaoxiang@gtmap.cn">wangyinghao</a>
     * @description 添加项目和已有证书的关联关系
     */
    @Override
    public void addXmZsConnection(BdcZsQO bdcZsQO) {
        //查询证书信息
        if(StringUtils.isBlank(bdcZsQO.getZsid())) {
            String xmid = bdcZsQO.getXmid();
            bdcZsQO.setXmid("");
            List<BdcZsQsztDTO> bdcZsQsztDTOS = bdcZsFeignService.queryBdcZsQszt(bdcZsQO);
            if (CollectionUtils.isEmpty(bdcZsQsztDTOS)) {
                throw new AppException("未查到产权证号对应证书");
            }
            BdcZsQsztDTO bdcZsQsztDTO = bdcZsQsztDTOS.get(0);
            bdcZsFeignService.generateXmZsConnection(bdcZsQsztDTO.getZsid(),xmid);
        }else {
            bdcZsFeignService.generateXmZsConnection(bdcZsQO.getZsid(),bdcZsQO.getXmid());
        }
    }
}
