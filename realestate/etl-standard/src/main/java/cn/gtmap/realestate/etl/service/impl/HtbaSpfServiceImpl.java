package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcWqbaLcGxDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaFwxxDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.dto.etl.HtbaSpfWqxxDTO;
import cn.gtmap.realestate.common.core.dto.etl.HtbaxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcWqbaLcGxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.etl.FgHtfyVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.etl.core.mapper.exchange.FgFyMapper;
import cn.gtmap.realestate.etl.core.mapper.exchange.HtbaSpfMapper;
import cn.gtmap.realestate.etl.service.HtbaSpfService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: realestate
 * @description: 合同备案商品房服务实现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-14 14:59
 **/
@Service
public class HtbaSpfServiceImpl implements HtbaSpfService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtbaSpfServiceImpl.class);

    @Qualifier("bdcEntityMapper")
    @Autowired(required = false)
    EntityMapper entityMapper;
    @Qualifier("bdcRepository")
    @Autowired(required = false)
    Repo repo;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    FwLjzFeginService fwLjzFeginService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired(required = false)
    HtbaSpfMapper htbaSpfMapper;
    @Autowired
    BdcBhFeignService bdcBhFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    BdcWqbaLcGxFeignService bdcWqbaLcGxFeignService;
    @Autowired(required = false)
    FgFyMapper fgFyMapper;

    /**
     * @param htbaxxQO 合同备案信息查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询合同备案信息
     * @date : 2020/12/14 15:10
     */
    @Override
    public Page<Map> listHtbaSpfxxByPage(Pageable pageable, HtbaxxQO htbaxxQO) {
        return repo.selectPaging("listHtbaSpfxxByPage", htbaxxQO, pageable);
    }

    /**
     * @param baids 备案id信息，多个逗号隔开
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/12/14 19:31
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> deleteBaxxList(String baids) {
        List<String> bdcdyhList = new ArrayList<>();
        if (StringUtils.isNotBlank(baids)) {
            List<HtbaFwxxDO> htbaFwxxList = new ArrayList<>();
            for (String baid : StringUtils.split(baids, CommonConstantUtils.ZF_YW_DH)) {
                entityMapper.deleteByPrimaryKey(HtbaSpfDO.class, baid);
                Example fwxxExampl = new Example(HtbaFwxxDO.class);
                fwxxExampl.createCriteria().andEqualTo("baid", baid);
                List<HtbaFwxxDO> htbaFwxxDOList = entityMapper.selectByExample(fwxxExampl);
                if (CollectionUtils.isNotEmpty(htbaFwxxDOList)) {
                    htbaFwxxList.addAll(htbaFwxxDOList);
                }
                entityMapper.deleteByExample(fwxxExampl);
                Example baqlrExample = new Example(HtbaQlrDO.class);
                baqlrExample.createCriteria().andEqualTo("baid", baid);
                entityMapper.deleteByExample(baqlrExample);
            }
            if(CollectionUtils.isNotEmpty(htbaFwxxList)) {
                for(HtbaFwxxDO htbaFwxxDO : htbaFwxxList) {
                    if(StringUtils.isNotBlank(htbaFwxxDO.getBdcdyh())) {
                        bdcdyhList.add(htbaFwxxDO.getBdcdyh());
                    }
                }
                if(CollectionUtils.isNotEmpty(bdcdyhList)) {
                    bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyhList,"");
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询备案信息
     * @date : 2020/12/15 10:09
     */
    @Override
    public HtbaxxDTO queryHtbaxx(String baid) {
        HtbaxxDTO htbaxxDTO = new HtbaxxDTO();
        if (StringUtils.isBlank(baid)) {
            return htbaxxDTO;
        }
        HtbaSpfDO htbaSpfDO = entityMapper.selectByPrimaryKey(HtbaSpfDO.class, baid);
        if (Objects.isNull(htbaSpfDO)) {
            htbaxxDTO.setHtbaSpfDO(new HtbaSpfDO());
        } else {
            htbaxxDTO.setHtbaSpfDO(htbaSpfDO);
        }
        Example baQlrExample = new Example(HtbaQlrDO.class);
        Example.Criteria qlrCrit = baQlrExample.createCriteria();
        qlrCrit.andEqualTo("baid", baid);
        qlrCrit.andEqualTo("qlrlb", CommonConstantUtils.QLRLB_QLR);
        List<HtbaQlrDO> htbaQlrDOList = entityMapper.selectByExample(baQlrExample);
        if (CollectionUtils.isEmpty(htbaQlrDOList)) {
            htbaxxDTO.setHtbaQlrDOList(new ArrayList<>(1));
        } else {
            htbaxxDTO.setHtbaQlrDOList(htbaQlrDOList);
        }
        Example baFwxxExample = new Example(HtbaFwxxDO.class);
        baFwxxExample.createCriteria().andEqualTo("baid", baid);
        List<HtbaFwxxDO> htbaFwxxDOList = entityMapper.selectByExample(baFwxxExample);
        if (CollectionUtils.isNotEmpty(htbaFwxxDOList)) {
            List<FwHsDO> fwxxList = new ArrayList<>(htbaFwxxDOList.size());
            for (HtbaFwxxDO htbaFwxx : htbaFwxxDOList) {
                if (StringUtils.isNotBlank(htbaFwxx.getFwid())) {
                    FwHsDO fwHsDO = fwHsFeignService.queryFwHsByFwHsIndex(htbaFwxx.getFwid(),"");
                    //户室查不到查预测户室
                    if (Objects.isNull(fwHsDO)) {
                        FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByFwHsIndex(htbaFwxx.getFwid(),"");
                        if (Objects.nonNull(fwYchsDO)) {
                            fwHsDO = new FwHsDO();
                            BeanUtils.copyProperties(fwYchsDO, fwHsDO);
                            fwxxList.add(fwHsDO);
                        }
                    } else {
                        fwxxList.add(fwHsDO);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(fwxxList)) {
                FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwxxList.get(0).getFwDcbIndex(),"");
                if (Objects.nonNull(fwLjzDO)) {
                    htbaxxDTO.setFwLjzDO(fwLjzDO);
                }
            }
            htbaxxDTO.setFwHsDOList(fwxxList);
        } else {
            List<FwHsDO> fwHsDOList = new ArrayList<>(1);
            fwHsDOList.add(new FwHsDO());
            htbaxxDTO.setFwHsDOList(fwHsDOList);
            htbaxxDTO.setFwLjzDO(new FwLjzDO());
        }
        return htbaxxDTO;
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/12/15 17:25
     */
    @Override
    public HtbaSpfDO saveOrUpdatHtbaxx(String json) {
        HtbaSpfDO htbaSpfDO = JSONObject.parseObject(json, HtbaSpfDO.class);
        if (StringUtils.isBlank(htbaSpfDO.getBaid())) {
            htbaSpfDO.setBaid(UUIDGenerator.generate16());
            entityMapper.insertSelective(htbaSpfDO);
        } else {
            bdcEntityFeignService.updateByJsonEntity(json, HtbaSpfDO.class.getName());
        }
        return htbaSpfDO;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合同备案房屋信息
     * @date : 2020/12/16 9:48
     */
    @Override
    public List<HtbaSpfDO> listHtbaxx(String bdcdyh) {
        HtbaxxQO htbaxxQO = new HtbaxxQO();
        htbaxxQO.setBdcdyh(bdcdyh);
        return htbaSpfMapper.listHtbaSpfxx(htbaxxQO);
    }

    /**
     * @param fwHsDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 从权籍获取数据保存合同备案信息
     * @date : 2020/12/16 9:48
     */
    @Override
    public HtbaSpfDO saveHtbaSpfFromFwhs(FwHsDO fwHsDO) {
        HtbaSpfDO htbaSpfDO = new HtbaSpfDO();
        htbaSpfDO.setBaid(UUIDGenerator.generate16());
        htbaSpfDO.setSlbh(bdcBhFeignService.queryCommonBh(CommonConstantUtils.SLBH,CommonConstantUtils.ZZSJFW_DAY,4,""));
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(Objects.isNull(userDto)) {
            LOGGER.error("合同备案未获取到用户信息");
        } else {
            htbaSpfDO.setBarmc(userDto.getAlias());
            htbaSpfDO.setBarid(userDto.getId());
        }
        htbaSpfDO.setBasj(new Date());
        entityMapper.insertSelective(htbaSpfDO);
        return htbaSpfDO;
    }

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据baid查询合同备案信息
     * @date : 2020/12/16 17:46
     */
    @Override
    public HtbaSpfDO queryHtbapf(String baid) {
        return entityMapper.selectByPrimaryKey(HtbaSpfDO.class,baid);
    }

    /**
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合同备案商品房信息
     * @date : 2020/12/17 17:57
     */
    @Override
    public List<HtbaSpfDO> listHtbaSpf(HtbaxxQO htbaxxQO) {
        return htbaSpfMapper.listHtbaSpfxx(htbaxxQO);
    }

    @Override
    public List<HtbaSpfWqxxDTO> querySpfWqHtxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失参数工作流实例ID");
        }
        //先用工作流查找关系表看是否存在关系数据，存在就用关系数据的htbh查找，否则继续用工作流实例id
        BdcWqbaLcGxDO bdcWqbaLcGxQO = new BdcWqbaLcGxDO();
        bdcWqbaLcGxQO.setGzlslid(gzlslid);
        BdcWqbaLcGxDO bdcWqbaLcGxDO = bdcWqbaLcGxFeignService.queryWqbaLcGx(bdcWqbaLcGxQO);
        if (Objects.nonNull(bdcWqbaLcGxDO) && StringUtils.isNotBlank(bdcWqbaLcGxDO.getHtbh())) {
            return htbaSpfMapper.listHtbaSpfWqxx(bdcWqbaLcGxDO.getHtbh());
        }
        return this.htbaSpfMapper.queryHtbaSpfWqxx(gzlslid);
    }


    /**
     * @param htbh
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存房屋信息
     * @date : 2021/3/8 20:38
     */
    @Override
    public List<HtbaSpfWqxxDTO> listSpfWqxx(String htbh, String gzlslid) {
        if (StringUtils.isBlank(htbh)) {
            throw new AppException("查询网签信息缺失合同编号");
        }
        //用输入的合同编号查找房管视图数据
        List<HtbaSpfWqxxDTO> htbaSpfWqxxDTOList = htbaSpfMapper.listHtbaSpfWqxx(htbh);
        if (CollectionUtils.isNotEmpty(htbaSpfWqxxDTOList)) {
            //如果不为空先根据gzlslid查找是否存在关系数据，存在更新，不存在新增一条流程和网签备案关系数据
            BdcWqbaLcGxDO bdcWqbaLcGxQO = new BdcWqbaLcGxDO();
            bdcWqbaLcGxQO.setGzlslid(gzlslid);
            BdcWqbaLcGxDO bdcWqbaLcGxDO = bdcWqbaLcGxFeignService.queryWqbaLcGx(bdcWqbaLcGxQO);
            if (Objects.nonNull(bdcWqbaLcGxDO) && StringUtils.isNotBlank(bdcWqbaLcGxDO.getGxid())) {
                bdcWqbaLcGxDO.setHtbh(htbh);
                bdcWqbaLcGxFeignService.updateWqbaLcGx(bdcWqbaLcGxDO);
            } else {
                bdcWqbaLcGxDO = new BdcWqbaLcGxDO();
                bdcWqbaLcGxDO.setHtbh(htbh);
                bdcWqbaLcGxDO.setGzlslid(gzlslid);
                bdcWqbaLcGxFeignService.insertWqbaLcGx(bdcWqbaLcGxDO);
            }
            return htbaSpfWqxxDTOList;
        }
        return htbaSpfWqxxDTOList;
    }

    /**
     * @param pageable
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询需要同步的数据
     * @date : 2021/7/6 15:09
     */
    @Override
    public Page<Map> listSyncHtbaSpfxxByPage(Pageable pageable, HtbaxxQO htbaxxQO) {
        return repo.selectPaging("selectFgHtfyxxByPage", htbaxxQO, pageable);
    }

    /**
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询需要同步的数据
     * @date : 2021/7/7 10:19
     */
    @Override
    public List<FgHtfyVO> listSyncBaxx(HtbaxxQO htbaxxQO) {
        return fgFyMapper.selectFgHtfyxxByPage(htbaxxQO);
    }

    /**
     * @param fgHtfyVOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 同步备案数据
     * @date : 2021/7/7 9:24
     */
    @Override
    public int syncBaxx(List<FgHtfyVO> fgHtfyVOList) {
        int count = 0;
        if (CollectionUtils.isNotEmpty(fgHtfyVOList)) {
            LOGGER.info("同步台账手动同步数据开始，数据量{},数据信息为{}", CollectionUtils.size(fgHtfyVOList), JSON.toJSONString(fgHtfyVOList));
            for (FgHtfyVO fgHtfyVO : fgHtfyVOList) {
                String bdcdyh = fgHtfyVO.getBdcdyh();
                HtbaxxQO htbaxxQO = new HtbaxxQO();
                htbaxxQO.setBdcdyh(bdcdyh);
                List<HtbaSpfDO> htbaSpfList = this.listHtbaSpf(htbaxxQO);
                //如果已经存在，不同步
                if (CollectionUtils.isNotEmpty(htbaSpfList)) {
                    continue;
                }
                //先查实测再查预测
                FwHsDO fwhs = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh,"");
                if (Objects.isNull(fwhs)) {
                    fwhs = new FwHsDO();
                    FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(bdcdyh,"");
                    if (Objects.nonNull(fwYchsDO)) {
                        BeanUtils.copyProperties(fwYchsDO, fwhs);
                    }
                }
                count += dealBaxx(fwhs, fgHtfyVO, bdcdyh);
            }
            LOGGER.info("手动同步数据结束，成功同步数据量{}", count);
        }
        return count;
    }

    /**
     * @param fwhs
     * @param fgHtfyVO
     * @param bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/7 9:39
     */
    @Override
    public int dealBaxx(FwHsDO fwhs, FgHtfyVO fgHtfyVO, String bdcdyh) {
        int count = 0;
        if (StringUtils.isNotBlank(fwhs.getFwHsIndex())) {
            HtbaSpfDO htbaSpf = new HtbaSpfDO();
            htbaSpf.setBaid(UUIDGenerator.generate16());
            htbaSpf.setSlbh(bdcBhFeignService.queryCommonBh(CommonConstantUtils.SLBH, CommonConstantUtils.ZZSJFW_DAY, 4, ""));
            htbaSpf.setBasj(DateUtils.formatDate(fgHtfyVO.getBasj()));
            htbaSpf.setHtbh(fgHtfyVO.getHtbabm());
            htbaSpf.setBazt(1);
            htbaSpf.setKfsmc(fgHtfyVO.getKfsmc());
            htbaSpf.setZzsyjzrq(fgHtfyVO.getZzsyjzrq());
            htbaSpf.setSysyjzrq(fgHtfyVO.getSysyjzrq());
            htbaSpf.setTdyt(fgHtfyVO.getTdyt());
            htbaSpf.setHtzjk(fgHtfyVO.getHtzjk());
            htbaSpf.setDkfs(fgHtfyVO.getDkfs());
            htbaSpf.setDksfkxx(fgHtfyVO.getDksfkxx());
            count = entityMapper.insertSelective(htbaSpf);
            HtbaFwxxDO htbaFwxx = new HtbaFwxxDO();
            htbaFwxx.setFwxxid(UUIDGenerator.generate16());
            htbaFwxx.setBaid(htbaSpf.getBaid());
            htbaFwxx.setFwid(fwhs.getFwHsIndex());
            htbaFwxx.setZl(fwhs.getZl());
            htbaFwxx.setBdcdyh(fwhs.getBdcdyh());
            htbaFwxx.setFwbm(fwhs.getFwbm());
            //房管局的坐落和权籍不太一致
            htbaFwxx.setCg(fgHtfyVO.getFycg());
            htbaFwxx.setFwdj(fgHtfyVO.getFwdj());
            htbaFwxx.setFwjg(fgHtfyVO.getFyjg());
            htbaFwxx.setFwxz(fgHtfyVO.getFwxz());
            htbaFwxx.setFwszc(fgHtfyVO.getMylc());
            htbaFwxx.setFwzl(fgHtfyVO.getXmldfh());
            htbaFwxx.setJzmj(fgHtfyVO.getJzmj());
            htbaFwxx.setFwzcs(fgHtfyVO.getCs());
            entityMapper.saveOrUpdate(htbaFwxx, htbaFwxx.getFwxxid());
            HtbaQlrDO htbaQlr = new HtbaQlrDO();
            htbaQlr.setBaid(htbaSpf.getBaid());
            htbaQlr.setQlrmc(fgHtfyVO.getMsr());
            htbaQlr.setZjh(fgHtfyVO.getGmrzjhm());
            htbaQlr.setLxdh(fgHtfyVO.getGmrlxdh());
            //之前读取的是第三方权利人的字典表，2是买受人
            //2021.5.21 修改跟权利人类别保持一致，买受人即权利人
            htbaQlr.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            htbaQlr.setQlrid(UUIDGenerator.generate16());
            entityMapper.saveOrUpdate(htbaQlr, htbaQlr.getQlrid());
            //回写状态到权籍
            List<String> bdcdyList = new ArrayList<>();
            bdcdyList.add(bdcdyh);
            if (CollectionUtils.isNotEmpty(bdcdyList)) {
                bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyList,"");
            }
        } else {
            LOGGER.error("未找到权籍数据，单元号为{}", bdcdyh);
        }
        return count;
    }
}
