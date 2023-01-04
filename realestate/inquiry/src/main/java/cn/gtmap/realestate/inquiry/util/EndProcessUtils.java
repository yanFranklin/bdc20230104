package cn.gtmap.realestate.inquiry.util;


import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlLzrQzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzNtRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzNtResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.qo.init.BdcLzrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcFzjlFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcYzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtZsyzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLzrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
 * @version 1.0  2018/12/10.
 * @description
 */
@Component
public class EndProcessUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndProcessUtils.class);

    @Autowired
    private Repo repo;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcXtZsyzhFeignService bdcXtZsyzhFeignService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcYzhFeignService bdcYzhFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    private BdcFzjlFeignService bdcFzjlFeignService;

    /**
     * 自助打证机相关接口，增加对于用户角色的权限配置
     */
    @Value("${zzdz.roleName:}")
    private String roleName;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BdcZzdzNtResponseDTO reWriteYzh(BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO){
        BdcZzdzNtResponseDTO dto = new BdcZzdzNtResponseDTO();
        String yzhTemp = bdcZzdzNtRequestDTO.getZsh();// 印制号
        String zsidTemp = bdcZzdzNtRequestDTO.getZsid();// 证书id
        String fzrqTemp = bdcZzdzNtRequestDTO.getFzrq();// 发证人
        //String fzridTemp = bdcZzdzNtRequestDTO.getZzjId();// 发证人id
        String username = StringUtils.isBlank(roleName) ? Constants.ZZDZ_USERNAME : bdcZzdzNtRequestDTO.getZzjUsername();
        UserDto currentUser = userManagerUtils.getUserByName(username);
        String fzridTemp = currentUser.getId();// 发证人
        String fzrTemp = currentUser.getUsername();// 发证人
        String fzrName = currentUser.getAlias();// 发证人
        String lzrzjh = "";// 领证人证件号
        Integer lzrzjzl = 0;// 领证人证件种类
        String lzr = "";// 领证人
        String sybmmc = userManagerUtils.getOrganizationByUserName(fzrTemp);// 使用部门名称

        String xmid = "";
        List<BdcXmDTO> listxm = bdcXmFeignService.listBdcXmBfxxBySlbh(bdcZzdzNtRequestDTO.getTranstionId());
        if (CollectionUtils.isNotEmpty(listxm)) {
            xmid = listxm.get(0).getXmid();
        } else {
            throw new RuntimeException("根据受理编号未查询到项目信息："+bdcZzdzNtRequestDTO.getTranstionId());
        }

        // 查询权利人
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setXmid(xmid);
        List<BdcLzrDO> list  =  bdcLzrFeignService.listBdcLzr(bdcLzrQO);
        LOGGER.info("查询lzr信息：{}",list);

        if(CollectionUtils.isNotEmpty(list)){
            lzrzjh = list.get(0).getLzrzjh();
            lzrzjzl = list.get(0).getLzrzjzl();
            lzr = list.get(0).getLzrmc();

            if(StringUtils.isEmpty(lzr)){
                // lzr为空则取权利人信息为lzr
                // 查询权利人
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> listQlr  =  bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if(CollectionUtils.isNotEmpty(listQlr)) {
                    lzrzjh = listQlr.get(0).getZjh();
                    lzrzjzl = listQlr.get(0).getZjzl();
                    lzr = listQlr.get(0).getQlrmc();
                }
            }
        }
        if(StringUtils.isEmpty(yzhTemp) && StringUtils.isEmpty(zsidTemp)){
            throw new RuntimeException("参数不完整请检查！"+dto.toString());
        }
        Map map = new HashMap<>();
        map.put("yzh",yzhTemp);
        map.put("zsid",zsidTemp);
        map.put("fzrq",fzrqTemp);
        map.put("fzr",fzrName);
        map.put("fzrid",fzridTemp);
        map.put("lzrzjh",lzrzjh);
        map.put("lzr",lzr);
        map.put("lzrzjzl",lzrzjzl);
        map.put("slbh",bdcZzdzNtRequestDTO.getTranstionId());
        map.put("sybmmc",sybmmc);
        map.put("dyzt", CommonConstantUtils.SF_S_DM);

        // 预防回写的印制号是已使用的，这里需要加一个判断逻辑
        int isUsedQzysxlhCount = repo.selectOne("getCountByQzysxlh", map);
        if (isUsedQzysxlhCount > 0) {
            throw new RuntimeException("此次打证存在已使用过的印制号：" + bdcZzdzNtRequestDTO.getZsh());
        }

        String lqfs = bdcXtZsyzhFeignService.getZsyzhLqfs(CommonConstantUtils.QXDM_NT);
        map.put("lqfs", lqfs);
        if (currentUser != null && CollectionUtils.isNotEmpty(currentUser.getOrgRecordList())) {
            map.put("bmid", currentUser.getOrgRecordList().get(0).getId());
        } else {
            map.put("bmid", Constants.ZZDZ_BMID);
        }
        LOGGER.info("回写印制号参数：{}", map);
        int queryYzhByLqfs = repo.selectOne("queryYzhByLqfs", map);
        if (queryYzhByLqfs == 0) {
            throw new RuntimeException("当前领取方式下，该印制号不可使用！");
        }

        // 更新印制号的syqk为 已使用
        int yzhCount = repo.update("updateYzh",map);
        if(yzhCount == 0){// 返回0，没有这条印制号
            throw new RuntimeException("此次打证的印制号不存在！");
        }

        BdcYzhQO yzhQO = new BdcYzhQO();
        yzhQO.setQzysxlh(yzhTemp);
        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhFeignService.queryListBdcYzh(yzhQO);


        //更新成功后需要新增一条使用明细数据
        // 生成使用明细
        BdcYzhsymxDO bdcYzhsymxDO = new BdcYzhsymxDO();
        bdcYzhsymxDO.setSyr(fzrName);
        bdcYzhsymxDO.setSyrid(fzridTemp);
        bdcYzhsymxDO.setSyyy(CommonConstantUtils.YZH_SYYY);
        bdcYzhsymxDO.setSyqk(CommonConstantUtils.SYQK_YSY);
        bdcYzhsymxDO.setSysj(new Date());
        if(CollectionUtils.isNotEmpty(bdcYzhDTOList)){
            bdcYzhsymxDO.setYzhid(bdcYzhDTOList.get(0).getYzhid());
        }else{
            throw new AppException("自助打证机印制号查询不到登记印制号信息！yzh:"+yzhTemp);
        }
        bdcYzhsymxDO.setSlbh(bdcZzdzNtRequestDTO.getTranstionId());
        bdcYzhsymxDO.setSybmmc(sybmmc);
        bdcYzhsymxDO.setYzhsymxid(UUIDGenerator.generate16());
        entityMapper.insertSelective(bdcYzhsymxDO);

        // 更新证书表之前先查询 szr和zssj 如果都有值 则不回写yzh 直接办结
        BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zsidTemp);
        LOGGER.info("自主打证回写查询证书信息：{}",JSONObject.toJSONString(bdcZsDO));
        if(StringUtils.isBlank(bdcZsDO.getSzr()) && null == bdcZsDO.getSzsj()){
            // 更新证书表
            int updatezsCount =  repo.update("reWriteYzhToBdcZs",map);
            if(updatezsCount > 0){
                dto.setMessage("操作成功");
                dto.setSuccess(true);
                dto.setStatusCode(0);
            }else{
                dto.setMessage("操作失败");
                dto.setSuccess(false);
                dto.setStatusCode(1);
                throw new RuntimeException("打印失败，请到窗口打印！");
            }
        }else{
            dto.setMessage("操作成功");
            dto.setSuccess(true);
            dto.setStatusCode(0);
        }

        // 保存领证人签字信息
        this.saveLzrQzxx(bdcZzdzNtRequestDTO, xmid);

        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setQqsj(simpleDateFormat.format(today));
        dto.setData(null);
        return dto;
    }

    /**
     * 保存自助打证机领证人签字图片
     * @param bdcZzdzNtRequestDTO 自助打证机回传参数
     * @param xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void saveLzrQzxx(BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO, String xmid) {
        if (StringUtils.isBlank(bdcZzdzNtRequestDTO.getLzrqz())) {
            LOGGER.info("自助打证机未传领证人签字图片，对应项目{}，证书{}", xmid, bdcZzdzNtRequestDTO.getZsid());
            return;
        }

        BdcFzjlLzrQzxxDTO lzrQzxxDTO = new BdcFzjlLzrQzxxDTO();
        lzrQzxxDTO.setXmid(xmid);
        //39387 【盐城】合并登记类流程接收证书信息回传接口时未成功保存签字图片,页面查询时传了zsid,不保存的话，查不到
        lzrQzxxDTO.setZsid(bdcZzdzNtRequestDTO.getZsid());
        lzrQzxxDTO.setSignStreamData(bdcZzdzNtRequestDTO.getLzrqz());
        bdcFzjlFeignService.saveFzjlLzrQzxx(lzrQzxxDTO);
        LOGGER.info("自助打证机回传领证人签字图片保存成功，对应项目{}，证书{}", xmid, bdcZzdzNtRequestDTO.getZsid());
    }
}
