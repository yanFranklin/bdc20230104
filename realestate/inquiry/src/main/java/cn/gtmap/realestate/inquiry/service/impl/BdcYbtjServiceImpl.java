package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZhcxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcYbtjMapper;
import cn.gtmap.realestate.inquiry.service.BdcYbtjService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/12/28
 * @description 月报统计查询服务
 */
@Service
public class BdcYbtjServiceImpl implements BdcYbtjService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYbtjServiceImpl.class);

    @Autowired
    private BdcYbtjMapper bdcYbtjMapper;

    /**
     * 不动产综合查询日志接口
     */
    @Autowired
    private BdcZhcxFeignService bdcZhcxFeignService;

    // 首次登记的工作流定义名称
    @Value("${ybtj.gzldymc.scdj:}")
    private String gzldymcscdj;

    // 转移登记的工作流定义名称
    @Value("${ybtj.gzldymc.zydj:}")
    private String gzldymczydj;

    // 抵押登记的工作流定义名称
    @Value("${ybtj.gzldymc.dydj:}")
    private String gzldymcdydj;

    // 预告登记的工作流定义名称
    @Value("${ybtj.gzldymc.ygdj:}")
    private String gzldymcygdj;

    // 查封登记的工作流定义名称
    @Value("${ybtj.gzldymc.cfdj:}")
    private String gzldymccfdj;

    /**
     * 获取业务统计信息
     * @param bdcYbtjQO
     * @return
     */
    @Override
    public List bdcywtj(BdcYbtjQO bdcYbtjQO) {
        if (null == bdcYbtjQO || StringUtils.isBlank(bdcYbtjQO.getDjjg())) {
            throw  new MissingArgumentException("请求参数不能都为空");
        }
        List<BdcYbtjDTO> listBdcYbtjDTO = bdcYbtjMapper.listBdcywtj(bdcYbtjQO);
        if(CollectionUtils.isNotEmpty(listBdcYbtjDTO)){
            for (BdcYbtjDTO bdcYbtjDTO : listBdcYbtjDTO){
                BdcYbtjQO bdcYbtj =new BdcYbtjQO();
                if(StringUtils.isNotBlank(bdcYbtjQO.getKssj())){
                    bdcYbtj.setKssj(bdcYbtjQO.getKssj());
                }
                if(StringUtils.isNotBlank(bdcYbtjQO.getJzsj())){
                    bdcYbtj.setJzsj(bdcYbtjQO.getJzsj());
                }
                if(StringUtils.isNotBlank(bdcYbtjDTO.getDjjg())){
                    bdcYbtj.setDjjg(bdcYbtjDTO.getDjjg());
                }
                // 计算证书
                bdcYbtj.setZslx(CommonConstantUtils.ZSLX_ZS);
                Integer zszl =  bdcYbtjMapper.countBdcZs(bdcYbtj);
                bdcYbtjDTO.setZszl(zszl);
                //计算电子证书
                Integer dzzszl =  bdcYbtjMapper.countBdcDzzz(bdcYbtj);
                bdcYbtjDTO.setDzzszl(dzzszl);
                // 计算证明
                bdcYbtj.setZslx(CommonConstantUtils.ZSLX_ZM);
                Integer zmzl =  bdcYbtjMapper.countBdcZs(bdcYbtj);
                bdcYbtjDTO.setZmzl(zmzl);
                // 计算电子证明
                Integer dzzmzl =  bdcYbtjMapper.countBdcDzzz(bdcYbtj);
                bdcYbtjDTO.setDzzmzl(dzzmzl);
                // 计算预告登记颁发证明量
                bdcYbtj.setDjlx(CommonConstantUtils.DJLX_YGDJ_DM);
                Integer ygdjzmzl =  bdcYbtjMapper.countBdcZs(bdcYbtj);
                bdcYbtjDTO.setYgdjzmzl(ygdjzmzl);
                Integer cdl = bdcZhcxFeignService.countYbtjZhcx(bdcYbtj);
                bdcYbtjDTO.setCdl(cdl);
            }
        }
        return listBdcYbtjDTO;
    }

    /**
     * 获取业务统计信息
     * @param bdcYbtjQO
     * @return
     */
    @Override
    public List bdcyzbwtj(BdcYbtjQO bdcYbtjQO) {
        if (null == bdcYbtjQO || StringUtils.isBlank(bdcYbtjQO.getDjjg())) {
            throw  new MissingArgumentException("请求参数登记机构不能为空");
        }
        List<String> djjgList= Arrays.asList(bdcYbtjQO.getDjjg().split(","));
        BdcYbtjQO bdcYbtj = new BdcYbtjQO();
        if(StringUtils.isNotBlank(bdcYbtjQO.getKssj())){
            bdcYbtj.setKssj(bdcYbtjQO.getKssj());
        }
        if(StringUtils.isNotBlank(bdcYbtjQO.getJzsj())){
            bdcYbtj.setJzsj(bdcYbtjQO.getJzsj());
        }
        List<BdcYbzbtjDTO> listBdcYbzbtjDTO = new ArrayList<BdcYbzbtjDTO>();
        for(String djjg : djjgList){
            bdcYbtj.setDjjg(djjg);
            int sumzl = 0;
            int sumxs = 0;
            //计算首次登记占比
            //总的
            bdcYbtj.setGzldymc(gzldymcscdj);
            bdcYbtj.setSfxs(CommonConstantUtils.SF_F_DM);
            Integer scdjzl =  bdcYbtjMapper.countYwzbScdj(bdcYbtj);
            sumzl+=scdjzl;
            //线上
            bdcYbtj.setSfxs(CommonConstantUtils.SF_S_DM);
            Integer scdjxs =  bdcYbtjMapper.countYwzbScdj(bdcYbtj);
            sumxs+=scdjxs;

            //计算转移登记占比
            bdcYbtj.setGzldymc(gzldymczydj);
            bdcYbtj.setDjlx(CommonConstantUtils.DJLX_ZYDJ_DM);
            bdcYbtj.setSfxs(CommonConstantUtils.SF_F_DM);
            Integer zydjzl =  bdcYbtjMapper.countYwzb(bdcYbtj);
            sumzl+=zydjzl;
            bdcYbtj.setSfxs(CommonConstantUtils.SF_S_DM);
            Integer zydjxs =  bdcYbtjMapper.countYwzb(bdcYbtj);
            sumxs+=zydjxs;

            //计算抵押登记占比
            bdcYbtj.setGzldymc(gzldymcdydj);
            bdcYbtj.setDjlx(null);
            bdcYbtj.setQllx(CommonConstantUtils.QLLX_DYAQ_DM);
            bdcYbtj.setSfxs(CommonConstantUtils.SF_F_DM);
            Integer dydjzl =  bdcYbtjMapper.countYwzb(bdcYbtj);
            sumzl+=dydjzl;
            bdcYbtj.setSfxs(CommonConstantUtils.SF_S_DM);
            Integer dydjxs =  bdcYbtjMapper.countYwzb(bdcYbtj);
            sumxs+=dydjxs;

            //计算预告登记占比
            bdcYbtj.setGzldymc(gzldymcygdj);
            bdcYbtj.setDjlx(CommonConstantUtils.DJLX_YGDJ_DM);
            bdcYbtj.setQllx(null);
            bdcYbtj.setSfxs(CommonConstantUtils.SF_F_DM);
            Integer ygdjzl =  bdcYbtjMapper.countYwzb(bdcYbtj);
            sumzl+=ygdjzl;
            bdcYbtj.setSfxs(CommonConstantUtils.SF_S_DM);
            Integer ygdjxs =  bdcYbtjMapper.countYwzb(bdcYbtj);
            sumxs+=ygdjxs;

            //计算查封登记占比
            bdcYbtj.setGzldymc(gzldymccfdj);
            bdcYbtj.setDjlx(CommonConstantUtils.DJLX_CFDJ_DM);
            bdcYbtj.setSfxs(CommonConstantUtils.SF_F_DM);
            Integer cfdjzl =  bdcYbtjMapper.countYwzb(bdcYbtj);
            sumzl+=cfdjzl;
            bdcYbtj.setSfxs(CommonConstantUtils.SF_S_DM);
            Integer cfdjxs =  bdcYbtjMapper.countYwzb(bdcYbtj);
            sumxs+=cfdjxs;
            BdcYbzbtjDTO bdcYbzbtjDTO = new BdcYbzbtjDTO();
            bdcYbzbtjDTO.setDjjg(djjg);
            bdcYbzbtjDTO.setWsywzb(sumxs + "/" + sumzl);
            bdcYbzbtjDTO.setScdjywzb(scdjxs + "/" + scdjzl);
            bdcYbzbtjDTO.setZydjywzb(zydjxs + "/" + zydjzl);
            bdcYbzbtjDTO.setDydjywzb(dydjxs + "/" + dydjzl);
            bdcYbzbtjDTO.setYgdjywzb(ygdjxs + "/" + ygdjzl);
            bdcYbzbtjDTO.setCfdjywzb(cfdjxs + "/" + cfdjzl);
            listBdcYbzbtjDTO.add(bdcYbzbtjDTO);
        }
        return listBdcYbzbtjDTO;
    }

}
