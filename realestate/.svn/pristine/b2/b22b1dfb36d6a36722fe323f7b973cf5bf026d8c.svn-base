package cn.gtmap.realestate.register.service.impl.hbbdcdy;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcBdcdyHbjlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcHbBdcdyDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcHbBdcdyQO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.register.core.dto.BdcDhbBdcdyDTO;
import cn.gtmap.realestate.register.core.dto.BdcXhbBdcdyDTO;
import cn.gtmap.realestate.register.core.mapper.BdcBdcdyMapper;
import cn.gtmap.realestate.register.service.BdcHbBdcdyService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/08/12
 * @description  合并不动产单元服务处理Service
 */
@Service
public class BdcHbBdcdyServiceImpl implements BdcHbBdcdyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcHbBdcdyServiceImpl.class);

    /**
     * ORM操作
     */
    @Autowired
    private Repo repo;
    /**
     * 数据库操作
     */
    @Autowired
    private EntityMapper entityMapper;
    /**
     * 不动产单元mapper
     */
    @Autowired
    private BdcBdcdyMapper bdcBdcdyMapper;
    /**
     * 不动产单元状态查询
     */
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    /**
     * 不动产单元信息查询
     */
    @Autowired
    private BdcdyFeignService bdcdyFeignService;
    /**
     * 项目表服务
     */
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    /**
     * 证书服务
     */
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    /**
     * 权利人服务
     */
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    /**
     * 备注默认值
     */
    @Value("${hbbdcdy.bz:}")
    public String hbBdcdyBz;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  分页参数
     * @param bdcHbBdcdyQO  查询参数
     * @return {Page} 产权信息列表
     * @description  （海门）查询待合并不动产单元信息
     */
    @Override
    public Page<BdcHbBdcdyDTO> queryDhbBdcdyXx(Pageable pageable, BdcHbBdcdyQO bdcHbBdcdyQO) {
        if(null == bdcHbBdcdyQO) {
            throw new MissingArgumentException("查询待合并不动产单元信息缺失查询参数信息");
        }

        Page<BdcHbBdcdyDTO> hbBdcdyDTOPage = repo.selectPaging("queryDhbBdcdyXxByPageOrder", bdcHbBdcdyQO, pageable);
        if (null == hbBdcdyDTOPage || CollectionUtils.isEmpty(hbBdcdyDTOPage.getContent())) {
            return hbBdcdyDTOPage;
        }

        //去重
        List<BdcHbBdcdyDTO> hbBdcdyDTOList = hbBdcdyDTOPage.getContent().stream().distinct().collect(Collectors.toList());

        // 获取不动产单元状态
        /// 1、先获取不动产单元号集合
        List<String> bdcdyhList = new ArrayList<>(hbBdcdyDTOList.size());
        for (BdcHbBdcdyDTO hbBdcdy : hbBdcdyDTOList) {
            if (null == hbBdcdy || null == hbBdcdy.getQszt()) {
                continue;
            }
            bdcdyhList.add(hbBdcdy.getBdcdyh());
        }
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            return hbBdcdyDTOPage;
        }

        /// 2、调用权籍获取状态
        List<BdcdyhZtResponseDTO> bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtBybdcdyh(bdcdyhList,"");
        if (CollectionUtils.isEmpty(bdcdyhZtDTOList)) {
            return hbBdcdyDTOPage;
        }

        /// 3、匹配设置不动产单元状态
        for (BdcHbBdcdyDTO hbBdcdyDTO : hbBdcdyDTOList) {
            for (BdcdyhZtResponseDTO bdcdyhZtDTO : bdcdyhZtDTOList) {
                if (StringUtils.equals(hbBdcdyDTO.getBdcdyh(), bdcdyhZtDTO.getBdcdyh())) {
                    hbBdcdyDTO.setBdcdyZtDTO(bdcdyhZtDTO);
                }
            }
        }

        /// 4、判断当前记录是否已经被合并生成新的记录
        List<String> xmidList = hbBdcdyDTOPage.getContent().stream().map(BdcHbBdcdyDTO::getXmid).collect(Collectors.toList());
        Map<String, List> param = new HashMap<>();
        param.put("xmidList", xmidList);
        List<BdcBdcdyHbjlDTO> bdcdyHbjlDTOList = bdcBdcdyMapper.queryBdcdyHbjl(param);

        if(CollectionUtils.isNotEmpty(bdcdyHbjlDTOList)) {
            for (BdcHbBdcdyDTO hbBdcdyDTO : hbBdcdyDTOList) {
                // 当前查询出的房地产权项目作为原项目
                BdcBdcdyHbjlDTO hbjlDTO = bdcdyHbjlDTOList.stream().filter(hbjl -> StringUtils.equals(hbjl.getYxmid(), hbBdcdyDTO.getXmid())).findAny().orElse(null);

                if(null != hbjlDTO && StringUtils.isNotBlank(hbjlDTO.getXmid())) {
                    // 当前房地产权项目有下一手合并项目记录
                    hbBdcdyDTO.setSfyhb(true);
                } else {
                    hbBdcdyDTO.setSfyhb(false);
                }
            }
        }

        return hbBdcdyDTOPage;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyDTOList 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @return {BdcHbBdcdyDTO} 新合并生成的不动产单元记录
     * @description 执行不动产单元合并操作
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BdcHbBdcdyDTO hbBdcdy(List<BdcHbBdcdyDTO> bdcdyDTOList, String ppBdcdyh) {
        if(StringUtils.isBlank(ppBdcdyh) || CollectionUtils.isEmpty(bdcdyDTOList)){
            throw new AppException("操作异常：未指定需要进行合并的数据");
        }

        if(null == bdcdyDTOList.get(0) || StringUtils.isBlank(bdcdyDTOList.get(0).getQlcfbs())) {
            throw new AppException("操作异常：未指定合并数据权利拆分标识");
        }

        // 1、查询待合并的项目证书等数据
        BdcDhbBdcdyDTO bdcDhbBdcdyDTO = this.queryDhbBdcdy(bdcdyDTOList);
        if(null == bdcDhbBdcdyDTO || CollectionUtils.isEmpty(bdcDhbBdcdyDTO.getDhbBdcXmDOList()) || CollectionUtils.isEmpty(bdcDhbBdcdyDTO.getDhbBdcFdcqDOList())) {
            LOGGER.warn("权利拆分标识：{}，没有待合并项目、房地产权表数据，执行不动产单元合并操作处理中止", bdcDhbBdcdyDTO.getQlcfbs());
            throw new AppException("操作异常：没有待合并项目、房地产权表数据");
        }

        try {
            // 2、现势数据合并成一条新记录
            BdcXhbBdcdyDTO xhbBdcdyDTO = this.newBdcdyFdcqXm(bdcDhbBdcdyDTO, ppBdcdyh);
            if (null == xhbBdcdyDTO || null == xhbBdcdyDTO.getNewBdcXmDO()) {
                LOGGER.warn("权利拆分标识：{}，没有生成新的合并项目记录，执行不动产单元合并操作处理中止", bdcDhbBdcdyDTO.getQlcfbs());
                throw new AppException("操作异常：没有生成新的合并项目记录");
            }

            // 3、更新所有合并记录的不动产单元号及证书锁定表的证号
            this.updateHbBdcdyBdcdyh(bdcdyDTOList, ppBdcdyh, xhbBdcdyDTO);

            // 4、注销合并的记录状态并设置备注字段信息
            this.cancelHbBdcdy(bdcdyDTOList, xhbBdcdyDTO.getNewBdcXmDO());

            // 5、更新合并后新的不动产单元权籍现势状态
            this.updateQjBdcdyXszt(bdcdyDTOList, xhbBdcdyDTO.getNewBdcXmDO());

            BdcHbBdcdyDTO result = new BdcHbBdcdyDTO();
            BeansResolveUtils.clonePropertiesValue(xhbBdcdyDTO.getNewBdcXmDO(), result);
            LOGGER.info("权利拆分标识：{}，合并操作结束，生成的新数据对应项目ID：{}", bdcDhbBdcdyDTO.getQlcfbs(), result.getXmid());

            return result;
        } catch (Exception exception) {
            LOGGER.error("权利拆分标识：{}，合并操作发生异常，待生成数据回滚！", bdcdyDTOList.get(0).getQlcfbs());
            throw exception;
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDhbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @description 生成新的一条房地产权项目数据
     */
    private BdcXhbBdcdyDTO newBdcdyFdcqXm(BdcDhbBdcdyDTO bdcDhbBdcdyDTO, String ppBdcdyh) {
        BdcXhbBdcdyDTO bdcXhbBdcdyDTO = new BdcXhbBdcdyDTO();

        // 生成项目表数据
        BdcXmDO newBdcXmDO = this.newBdcXm(bdcDhbBdcdyDTO, ppBdcdyh);
        bdcXhbBdcdyDTO.setNewBdcXmDO(newBdcXmDO);

        // 生成房地产权表数据
        BdcFdcqDO bdcFdcqDO = this.newBdcFdcq(newBdcXmDO, bdcDhbBdcdyDTO);

        // 判断是否是项目内多幢
        boolean isXmndz = this.isXmndz(bdcDhbBdcdyDTO);
        // 生成房地产权项目内多幢数据
        if(isXmndz) {
            this.newBdcFdcqFdcqXm(bdcFdcqDO, bdcDhbBdcdyDTO);
        }

        // 生成证书记录
        BdcZsDO newBdcZs = this.newBdcZs(newBdcXmDO, bdcDhbBdcdyDTO);
        bdcXhbBdcdyDTO.setNewBdcZsDO(newBdcZs);

        // 生成权利人数据
        this.newBdcQlr(bdcXhbBdcdyDTO, bdcDhbBdcdyDTO);

        return bdcXhbBdcdyDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyDTOList 待合并不动产单元信息
     * @description 查询详细的待合并不动产单元项目等信息
     */
    private BdcDhbBdcdyDTO queryDhbBdcdy(List<BdcHbBdcdyDTO> bdcdyDTOList) {
        // 1、查询待合并的项目数据
        BdcDhbBdcdyDTO bdcDhbBdcdyDTO = new BdcDhbBdcdyDTO(bdcdyDTOList.size());
        bdcDhbBdcdyDTO.setQlcfbs(bdcdyDTOList.get(0).getQlcfbs());

        for(BdcHbBdcdyDTO bdcHbBdcdyDTO : bdcdyDTOList) {
            // 查询项目表数据
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(bdcHbBdcdyDTO.getXmid());
            List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(list)) {
                bdcDhbBdcdyDTO.getDhbBdcXmDOList().addAll(list);
            }

            // 查询房地产权数据
            BdcFdcqDO dhbBdcFdcqDO = entityMapper.selectByPrimaryKey(BdcFdcqDO.class, bdcHbBdcdyDTO.getQlid());
            if(null != dhbBdcFdcqDO) {
                bdcDhbBdcdyDTO.getDhbBdcFdcqDOList().add(dhbBdcFdcqDO);
            }

            // 查询证书表记录
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setXmid(bdcHbBdcdyDTO.getXmid());
            List<BdcZsDO> dhbBdcZs = bdcZsFeignService.listBdcZs(bdcZsQO);
            if(CollectionUtils.isNotEmpty(dhbBdcZs)) {
                bdcDhbBdcdyDTO.getDhbBdcZsDOList().addAll(dhbBdcZs);
            }

            // 查询权利人数据
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcHbBdcdyDTO.getXmid());
            List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if(CollectionUtils.isNotEmpty(qlrDOList)) {
                bdcDhbBdcdyDTO.getDhbBdcQlrDOList().addAll(qlrDOList);
            }
        }

        String qlcfbs = bdcdyDTOList.get(0).getQlcfbs();
        LOGGER.info("权利拆分标识：{}，待合并项目表数据：{}", qlcfbs, this.strJsonTrim(bdcDhbBdcdyDTO.getDhbBdcXmDOList()));
        LOGGER.info("权利拆分标识：{}，待合并房地产权表数据：{}", qlcfbs, this.strJsonTrim(bdcDhbBdcdyDTO.getDhbBdcFdcqDOList()));
        LOGGER.info("权利拆分标识：{}，待合并证书表数据：{}", qlcfbs, this.strJsonTrim(bdcDhbBdcdyDTO.getDhbBdcZsDOList()));
        LOGGER.info("权利拆分标识：{}，待合并权利人表数据：{}", qlcfbs, this.strJsonTrim(bdcDhbBdcdyDTO.getDhbBdcQlrDOList()));

        return bdcDhbBdcdyDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDhbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @return 项目信息
     * @description 生成项目数据
     */
    private BdcXmDO newBdcXm(BdcDhbBdcdyDTO bdcDhbBdcdyDTO, String ppBdcdyh) {
        if(null == bdcDhbBdcdyDTO || CollectionUtils.isEmpty(bdcDhbBdcdyDTO.getDhbBdcXmDOList())) {
            LOGGER.warn("没有待合并项目表数据，生成新合并项目处理中止");
            return null;
        }

        List<BdcXmDO> dhbBdcXmDOList = bdcDhbBdcdyDTO.getDhbBdcXmDOList();

        // 1、生成项目表数据
        BdcXmDO newBdcXmDO = null;
        String qlrmc = null, qlrzjh = null;
        Integer dzwyt = null;
        for(BdcXmDO bdcXmDO : dhbBdcXmDOList) {
            // 新生成的项目信息直接继承原合并记录的任意一条，优先取不是虚拟不动产单元号的
            if(!BdcdyhToolUtils.checkXnbdcdyh(bdcXmDO.getBdcdyh())) {
                newBdcXmDO = JSON.parseObject(JSON.toJSONString(bdcXmDO), BdcXmDO.class);
            }

            if(StringUtils.isNotBlank(bdcXmDO.getQlr())) {
                qlrmc = bdcXmDO.getQlr();
            }

            if(StringUtils.isNotBlank(bdcXmDO.getQlrzjh())) {
                qlrzjh = bdcXmDO.getQlrzjh();
            }

            if(null != bdcXmDO.getDzwyt()) {
                dzwyt = bdcXmDO.getDzwyt();
            }
        }
        if(null == newBdcXmDO) {
            newBdcXmDO = JSON.parseObject(JSON.toJSONString(dhbBdcXmDOList.get(0)), BdcXmDO.class);
        }

        // 不动产单元号
        newBdcXmDO.setBdcdyh(ppBdcdyh);
        // xmid取随机值
        newBdcXmDO.setXmid(UUIDGenerator.generate16());
        // yqzh所有合并的记录的yqzh去重拼接
        newBdcXmDO.setYcqzh(dhbBdcXmDOList.stream().map(BdcXmDO::getYcqzh).distinct().collect(Collectors.joining(",")));
        // slbh任务取其中一条记录的受理编号
        newBdcXmDO.setSlbh(dhbBdcXmDOList.get(0).getSlbh());
        // gzlslid取随机值
        newBdcXmDO.setGzlslid(UUIDGenerator.generate16());
        // bdcdywybm取djh
        newBdcXmDO.setBdcdywybh(this.getDjhByBdcdyh(ppBdcdyh));
        // 坐落整体去重拼接（合并界面上允许编辑整体坐落）
        newBdcXmDO.setZl(dhbBdcXmDOList.stream().map(BdcXmDO::getZl).distinct().collect(Collectors.joining(",")));
        // dzwmj取所有物dzwmj之和
        newBdcXmDO.setDzwmj(dhbBdcXmDOList.stream().map(BdcXmDO::getDzwmj).reduce(Double::sum).get());
        // qlr及qlrzjh随机取一个不为空的qlr记录
        newBdcXmDO.setQlr(qlrmc);
        newBdcXmDO.setQlrzjh(qlrzjh);
        // bdcqzh取所有记录的bdcqzh去重拼接（证号由小到大排序拼接)
        List<String> bdcqzhList = dhbBdcXmDOList.stream().map(BdcXmDO::getBdcqzh).distinct().collect(Collectors.toList());
        if(CollectionUtils.isEmpty(bdcqzhList)) {
            newBdcXmDO.setBdcqzh(null);
        } else {
            bdcqzhList.sort((bdcqzh1, bdcqzh2) -> bdcqzh1.compareTo(bdcqzh2));
            newBdcXmDO.setBdcqzh(bdcqzhList.stream().collect(Collectors.joining(",")));
        }
        // dzwyt随机取一条
        newBdcXmDO.setDzwyt(dzwyt);
        // bdc_xm的bdcdyfwlx合并的那条现势记录更新为1
        newBdcXmDO.setBdcdyfwlx(CommonConstantUtils.FWLX_DUOZH);

        // 2、生成项目历史关系数据
        List<BdcXmLsgxDO> bdcXmLsgxDOList = new ArrayList<>(dhbBdcXmDOList.size());
        for(BdcXmDO bdcXmDO : dhbBdcXmDOList) {
            BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
            bdcXmLsgxDO.setGxid(UUIDGenerator.generate16());
            bdcXmLsgxDO.setXmid(newBdcXmDO.getXmid());
            bdcXmLsgxDO.setYxmid(bdcXmDO.getXmid());
            bdcXmLsgxDOList.add(bdcXmLsgxDO);
        }

        // 3、生成项目附表数据
        BdcXmFbDO newBdcXmFbDO = new BdcXmFbDO();
        newBdcXmFbDO.setXmid(newBdcXmDO.getXmid());
        newBdcXmFbDO.setGzlslid(newBdcXmDO.getGzlslid());
        newBdcXmFbDO.setQlcfbs(CommonConstantUtils.BDCDY_HBJL);

        // 4、执行新增数据
        entityMapper.insertSelective(newBdcXmDO);
        LOGGER.info("权利拆分标识：{}，新合并记录项目数据：{}", bdcDhbBdcdyDTO.getQlcfbs(), this.strJsonTrim(newBdcXmDO));

        entityMapper.insertSelective(newBdcXmFbDO);
        LOGGER.info("权利拆分标识：{}，新合并记录项目附表数据：{}", bdcDhbBdcdyDTO.getQlcfbs(), newBdcXmFbDO);

        entityMapper.insertBatchSelective(bdcXmLsgxDOList);
        LOGGER.info("权利拆分标识：{}，新合并记录项目历史关系数据：{}", bdcDhbBdcdyDTO.getQlcfbs(), bdcXmLsgxDOList);

        return newBdcXmDO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param newBdcXmDO 项目数据
     * @param bdcDhbBdcdyDTO 待合并不动产单元信息
     * @return 房地产权数据
     * @description 生成房地产权数据
     */
    private BdcFdcqDO newBdcFdcq(BdcXmDO newBdcXmDO, BdcDhbBdcdyDTO bdcDhbBdcdyDTO) {
        if(null == bdcDhbBdcdyDTO || CollectionUtils.isEmpty(bdcDhbBdcdyDTO.getDhbBdcFdcqDOList())) {
            LOGGER.warn("权利拆分标识：{}，没有待合并房地产权表数据，生成新合并房地产权处理中止", bdcDhbBdcdyDTO.getQlcfbs());
            return null;
        }

        List<BdcFdcqDO> dhbBdcFdcqDOList = bdcDhbBdcdyDTO.getDhbBdcFdcqDOList();

        BdcFdcqDO newBdcFdcqDO = null;
        Integer fwjg = null, zcs = 0;

        for(BdcFdcqDO dhbBdcFdcqDO : dhbBdcFdcqDOList) {
            // 新生成的房地产权先复制一个原房地产权记录
            if(!BdcdyhToolUtils.checkXnbdcdyh(dhbBdcFdcqDO.getBdcdyh())) {
                newBdcFdcqDO = JSON.parseObject(JSON.toJSONString(dhbBdcFdcqDO), BdcFdcqDO.class);
            }

            if(null != dhbBdcFdcqDO.getFwjg()) {
                fwjg = dhbBdcFdcqDO.getFwjg();
            }

            if(null != dhbBdcFdcqDO.getZcs() && dhbBdcFdcqDO.getZcs() > zcs) {
                zcs = dhbBdcFdcqDO.getZcs();
            }
        }
        if(null == newBdcFdcqDO) {
            newBdcFdcqDO = JSON.parseObject(JSON.toJSONString(dhbBdcFdcqDOList.get(0)), BdcFdcqDO.class);
        }

        // 复制项目表相同属性字段值
        BeansResolveUtils.clonePropertiesValue(newBdcXmDO, newBdcFdcqDO);

        //qlid随机取值
        newBdcFdcqDO.setQlid(UUIDGenerator.generate16());
        // tdsyqr取权利人
        newBdcFdcqDO.setTdsyqr(newBdcXmDO.getQlr());
        // ghyt取项目表的dzwyt
        newBdcFdcqDO.setGhyt(newBdcXmDO.getDzwyt());
        // fwjg随机取其中一个的有值的记录
        newBdcFdcqDO.setFwjg(fwjg);
        // zcs取所有记录最大值
        newBdcFdcqDO.setZcs(zcs);
        // jzmj和zyjzmj取所有值加和
        newBdcFdcqDO.setJzmj(dhbBdcFdcqDOList.stream().map(BdcFdcqDO::getJzmj).reduce(Double::sum).get());
        newBdcFdcqDO.setZyjzmj(dhbBdcFdcqDOList.stream().map(BdcFdcqDO::getZyjzmj).reduce(Double::sum).get());
        // bdc_fdcq的bdcdyfwlx合并的那条现势记录更新为1
        newBdcFdcqDO.setBdcdyfwlx(CommonConstantUtils.FWLX_DUOZH);

        entityMapper.insertSelective(newBdcFdcqDO);
        LOGGER.info("权利拆分标识：{}，新合并记录房地产权数据：{}", bdcDhbBdcdyDTO.getQlcfbs(), this.strJsonTrim(newBdcFdcqDO));

        return newBdcFdcqDO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDhbBdcdyDTO 待合并不动产单元信息
     * @return 是 ：true 否 : false
     * @description 判断不动产单元房屋类型是否是项目内多幢
     */
    private boolean isXmndz(BdcDhbBdcdyDTO bdcDhbBdcdyDTO) {
        if(null == bdcDhbBdcdyDTO.getDhbBdcXmDOList().get(0)) {
            throw new AppException("操作异常，没有不动产单元信息");
        }

        String bdcdyh = bdcDhbBdcdyDTO.getDhbBdcXmDOList().get(0).getBdcdyh();
        if(StringUtils.isBlank(bdcdyh)) {
            throw new AppException("操作异常，没有不动产单元信息");
        }

        BdcdyResponseDTO bdcdyXx = bdcdyFeignService.queryBdcdy(bdcdyh, null,"");
        if(null != bdcdyXx && String.valueOf(CommonConstantUtils.FWLX_DUOZH).equals(bdcdyXx.getBdcdyfwlx())) {
            LOGGER.info("权利拆分标识：{}，查询权籍待合并不动产单元{}房屋类型为项目内多幢", bdcDhbBdcdyDTO.getQlcfbs(), bdcdyh);
            return true;
        }

        LOGGER.info("权利拆分标识：{}，查询权籍待合并不动产单元{}房屋类型不是项目内多幢", bdcDhbBdcdyDTO.getQlcfbs(), bdcdyh);
        return false;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param newBdcXmDO 新生成的项目数据
     * @param bdcDhbBdcdyDTO 待合并不动产单元信息
     * @return 房地产权项目内多幢数据
     * @description 生成房地产权项目内多幢数据
     */
    private List<BdcFdcqFdcqxmDO> newBdcFdcqFdcqXm(BdcFdcqDO newBdcXmDO, BdcDhbBdcdyDTO bdcDhbBdcdyDTO) {
        if(null == bdcDhbBdcdyDTO || CollectionUtils.isEmpty(bdcDhbBdcdyDTO.getDhbBdcFdcqDOList()) || CollectionUtils.isEmpty(bdcDhbBdcdyDTO.getDhbBdcXmDOList())) {
            LOGGER.warn("没有待合并项目表或房地产权数据，生成新合并项目内多幢处理中止");
            return null;
        }

        List<BdcXmDO> dhbBdcXmDOList = bdcDhbBdcdyDTO.getDhbBdcXmDOList();
        List<BdcFdcqDO> dhbBdcFdcqDOList = bdcDhbBdcdyDTO.getDhbBdcFdcqDOList();

        List<BdcFdcqFdcqxmDO> newFdcqXmList = new ArrayList<>(dhbBdcFdcqDOList.size());
        for(BdcFdcqDO dhbBdcFdcqDO : dhbBdcFdcqDOList) {
            BdcFdcqFdcqxmDO newFdcqXmDO = new BdcFdcqFdcqxmDO();

            // 先复制合并前bdc_fdcq每条记录的对应字段值
            BeansResolveUtils.clonePropertiesValue(dhbBdcFdcqDO, newFdcqXmDO);

            //fzid取每条记录的xmid
            newFdcqXmDO.setFzid(dhbBdcFdcqDO.getXmid());
            // qlid取fdcq的qlid
            newFdcqXmDO.setQlid(newBdcXmDO.getQlid());
            // xmmc每条记录的坐落
            newFdcqXmDO.setXmmc(dhbBdcFdcqDO.getZl());
            // 新增的bz字段（每条记录对应的证号）
            BdcXmDO dhbXmDO = dhbBdcXmDOList.stream().filter(xmDO -> StringUtils.equals(xmDO.getXmid(), dhbBdcFdcqDO.getXmid())).findAny().orElse(null);
            if(null != dhbXmDO) {
                newFdcqXmDO.setBz(dhbXmDO.getBdcqzh());
            } else {
                newFdcqXmDO.setBz(null);
            }

            newFdcqXmList.add(newFdcqXmDO);
        }

        entityMapper.insertBatchSelective(newFdcqXmList);
        LOGGER.info("权利拆分标识：{}，新合并记录房地产权项目内多幢数据：{}", bdcDhbBdcdyDTO.getQlcfbs(), this.strJsonTrim(newFdcqXmList));
        return newFdcqXmList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param newBdcXmDO 新项目数据
     * @param bdcDhbBdcdyDTO 待合并不动产单元信息
     * @return 证书数据
     * @description 生成证书数据
     */
    private BdcZsDO newBdcZs(BdcXmDO newBdcXmDO, BdcDhbBdcdyDTO bdcDhbBdcdyDTO) {
        if(null == bdcDhbBdcdyDTO || CollectionUtils.isEmpty(bdcDhbBdcdyDTO.getDhbBdcZsDOList())) {
            LOGGER.warn("权利拆分标识：{}，没有待合并证书数据，生成新合并证书处理中止", bdcDhbBdcdyDTO.getQlcfbs());
            return null;
        }

        List<BdcZsDO> dhbBdcZsDOList = bdcDhbBdcdyDTO.getDhbBdcZsDOList();

        // 1、新合并证书数据
        BdcZsDO newBdcZsDO = null;
        for(BdcZsDO dhbBdcZsDO : dhbBdcZsDOList) {
            // 新生成的证书先复制一个原证书记录, 优先非虚拟不动产单元
            if(!BdcdyhToolUtils.checkXnbdcdyh(dhbBdcZsDO.getBdcdyh())) {
                // 原来按照xmid查询的证书信息不全，这里重新查询下
                BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(dhbBdcZsDO.getZsid());
                if(null != bdcZsDO) {
                    newBdcZsDO = JSON.parseObject(JSON.toJSONString(bdcZsDO), BdcZsDO.class);
                }
                break;
            }
        }
        if(null == newBdcZsDO) {
            // 原来按照xmid查询的证书信息不全，这里重新查询下
            BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(dhbBdcZsDOList.get(0).getZsid());
            if(null != bdcZsDO) {
                newBdcZsDO = JSON.parseObject(JSON.toJSONString(bdcZsDO), BdcZsDO.class);
            }
        }

        newBdcZsDO.setZsid(UUIDGenerator.generate16());
        newBdcZsDO.setBdcqzh(newBdcXmDO.getBdcqzh());
        newBdcZsDO.setZl(newBdcXmDO.getZl());
        newBdcZsDO.setMj(newBdcXmDO.getDzwmj().toString());
        newBdcZsDO.setYt(String.valueOf(newBdcXmDO.getDzwyt()));
        newBdcZsDO.setBdcdyh(newBdcXmDO.getBdcdyh());

        // 2、新合并证书项目关系数据
        BdcXmZsGxDO bdcXmZsGxDO = new BdcXmZsGxDO();
        bdcXmZsGxDO.setGxid(UUIDGenerator.generate16());
        bdcXmZsGxDO.setXmid(newBdcXmDO.getXmid());
        bdcXmZsGxDO.setZsid(newBdcZsDO.getZsid());

        // 3、判断证号是否是单个，则需要判断证号是否已经存在，需要将原来的证书记录证号注销，避免因为唯一约束导致新记录和原记录冲突
        this.cancelBdcqzh(newBdcXmDO.getBdcqzh(), bdcDhbBdcdyDTO.getQlcfbs());

        entityMapper.insertSelective(newBdcZsDO);
        LOGGER.info("权利拆分标识：{}，新合并记录证书数据：{}", bdcDhbBdcdyDTO.getQlcfbs(), this.strJsonTrim(newBdcZsDO));
        entityMapper.insertSelective(bdcXmZsGxDO);
        LOGGER.info("权利拆分标识：{}，新合并记录证书项目关系数据：{}", bdcDhbBdcdyDTO.getQlcfbs(), bdcXmZsGxDO);

        return newBdcZsDO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcqzh 不动产权证号
     * @description 注销待合并记录的不动产权证号
     */
    private void cancelBdcqzh(String bdcqzh, String qlcfbs) {
        if(StringUtils.isBlank(bdcqzh) || bdcqzh.contains(",")) {
            // 空或者多个证号拼接的不会造成唯一冲突
            return;
        }

        // 先查询证号记录是否存在
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzh(bdcqzh);
        List<BdcZsDO>  bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isEmpty(bdcZsDOList) || null == bdcZsDOList.get(0)) {
            // 不存在无需更新
            return;
        }

        // 再更新证号为注销状态
        String zsid = bdcZsDOList.get(0).getZsid();
        BdcZsDO zsDO = new BdcZsDO();
        zsDO.setZsid(zsid);
        zsDO.setBdcqzh(bdcqzh + CommonConstantUtils.BDCQZH_ZX);
        bdcZsFeignService.updateBdcZs(zsDO);
        LOGGER.info("权利拆分标识：{}，更新合并记录的证书记录不动产权证号为注销状态，zsid：{}，bdcqzh：{}", qlcfbs, zsid, bdcqzh);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xhbBdcdyDTO 新合并记录信息
     * @param bdcDhbBdcdyDTO 待合并不动产单元信息
     * @return {List} 权利人数据
     * @description 生成权利人数据
     */
    private BdcQlrDO newBdcQlr(BdcXhbBdcdyDTO xhbBdcdyDTO, BdcDhbBdcdyDTO bdcDhbBdcdyDTO) {
        if(null == xhbBdcdyDTO || null == xhbBdcdyDTO.getNewBdcXmDO()) {
            LOGGER.error("未生成项目信息，处理权利人操作中止");
            return null;
        }

        if(null == xhbBdcdyDTO.getNewBdcZsDO() || StringUtils.isAnyBlank(xhbBdcdyDTO.getNewBdcZsDO().getZsid(), xhbBdcdyDTO.getNewBdcZsDO().getBdcqzh())) {
            LOGGER.error("未生成证书信息，处理权利人操作中止");
            return null;
        }

        if(null == bdcDhbBdcdyDTO || CollectionUtils.isEmpty(bdcDhbBdcdyDTO.getDhbBdcQlrDOList()) || null == bdcDhbBdcdyDTO.getDhbBdcQlrDOList().get(0)) {
            LOGGER.warn("权利拆分标识：{}，没有待合并权利人数据，生成新合并权利人处理中止", bdcDhbBdcdyDTO.getQlcfbs());
            return null;
        }
        List<BdcQlrDO> dhbBdcQlrDOList = bdcDhbBdcdyDTO.getDhbBdcQlrDOList();

        BdcQlrDO newBdcQlr = JSON.parseObject(JSON.toJSONString(dhbBdcQlrDOList.get(0)), BdcQlrDO.class);
        newBdcQlr.setQlrid(UUIDGenerator.generate16());
        newBdcQlr.setXmid(xhbBdcdyDTO.getNewBdcXmDO().getXmid());
        newBdcQlr.setBdcqzh(xhbBdcdyDTO.getNewBdcZsDO().getBdcqzh());
        newBdcQlr.setZsid(xhbBdcdyDTO.getNewBdcZsDO().getZsid());

        String qlrmc = null, qlrzjh = null;
        for(BdcQlrDO dhbBdcqlr : bdcDhbBdcdyDTO.getDhbBdcQlrDOList()) {
            if(null != dhbBdcqlr && StringUtils.isNotBlank(dhbBdcqlr.getQlrmc())) {
                qlrmc = dhbBdcqlr.getQlrmc();
            }

            if(null != dhbBdcqlr && StringUtils.isNotBlank(dhbBdcqlr.getZjh())) {
                qlrzjh = dhbBdcqlr.getZjh();
            }
        }
        newBdcQlr.setQlrmc(qlrmc);
        newBdcQlr.setZjh(qlrzjh);

        entityMapper.insertSelective(newBdcQlr);
        LOGGER.info("权利拆分标识：{}，新合并记录权利人数据：{}", bdcDhbBdcdyDTO.getQlcfbs(), newBdcQlr);
        return newBdcQlr;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyDTOList 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @param xhbBdcdyDTO 新合并记录信息
     * @description 更新所有合并记录的不动产单元号为新的不动产单元号及锁定证书记录
     */
    private void updateHbBdcdyBdcdyh(List<BdcHbBdcdyDTO> bdcdyDTOList, String ppBdcdyh, BdcXhbBdcdyDTO xhbBdcdyDTO) {
        for(BdcHbBdcdyDTO bdcHbBdcdyDTO : bdcdyDTOList) {
            // 更新项目表
            this.updateHbXmBdcdyh(ppBdcdyh, bdcHbBdcdyDTO);

            // 更新证书表
            this.updateHbZsBdcdyh(ppBdcdyh, bdcHbBdcdyDTO);

            // 更新房地产权表
            this.updateHbFdcqBdcdyh(ppBdcdyh, bdcHbBdcdyDTO);

            // 更新查封表
            this.updateHbCfBdcdyh(ppBdcdyh, bdcHbBdcdyDTO);

            // 更新抵押表
            this.updateHbDyaBdcdyh(ppBdcdyh, bdcHbBdcdyDTO);

            // 更新预告表
            this.updateHbYgBdcdyh(ppBdcdyh, bdcHbBdcdyDTO);

            // 更新异议表
            this.updateHbYyBdcdyh(ppBdcdyh, bdcHbBdcdyDTO);

            // 更新单元锁定表
            this.updateHbSdBdcdyh(ppBdcdyh, bdcHbBdcdyDTO);

            // 更新证书锁定表
            this.updateHbZssdBdcdyh(xhbBdcdyDTO, bdcHbBdcdyDTO);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcHbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @description 更新待合并项目的不动产单元号为新的不动产单元号
     */
    private void updateHbXmBdcdyh(String ppBdcdyh, BdcHbBdcdyDTO bdcHbBdcdyDTO) {
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setBdcdyh(ppBdcdyh);
        Example example = new Example(BdcXmDO.class);
        example.createCriteria().andEqualTo("bdcdyh", bdcHbBdcdyDTO.getBdcdyh());
        int count = entityMapper.updateByExampleSelectiveNotNull(bdcXmDO, example);
        LOGGER.info("权利拆分标识：{}，更新项目表的不动产单元号，原项目不动产单元号：{}，实际更新数量：{}", bdcHbBdcdyDTO.getQlcfbs(), bdcHbBdcdyDTO.getBdcdyh(), count);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcHbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @description 更新待合并证书的不动产单元号为新的不动产单元号
     */
    private void updateHbZsBdcdyh(String ppBdcdyh, BdcHbBdcdyDTO bdcHbBdcdyDTO) {
        // 查询待合并项目原先关联的证书记录（可能一个单元发了多本证）
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(bdcHbBdcdyDTO.getXmid());
        List<BdcZsDO> dhbBdcZs = bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isEmpty(dhbBdcZs)) {
            return;
        }

        List<BdcZsDO> zsDOList = new ArrayList<>(dhbBdcZs.size());
        for(BdcZsDO dhbBdcZsDO : dhbBdcZs) {
            BdcZsDO zsDO = new BdcZsDO();
            zsDO.setZsid(dhbBdcZsDO.getZsid());
            zsDO.setBdcdyh(ppBdcdyh);

            zsDOList.add(zsDO);
            entityMapper.updateByPrimaryKeySelective(zsDO);
        }
        LOGGER.info("权利拆分标识：{}，更新待合并证书的不动产单元号，证书表：{}，实际更新数量：{}", bdcHbBdcdyDTO.getQlcfbs(), zsDOList, zsDOList.size());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcHbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @description 更新待合并房地产权的不动产单元号为新的不动产单元号
     */
    private void updateHbFdcqBdcdyh(String ppBdcdyh, BdcHbBdcdyDTO bdcHbBdcdyDTO) {
        BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();
        bdcFdcqDO.setQlid(bdcHbBdcdyDTO.getQlid());
        bdcFdcqDO.setBdcdyh(ppBdcdyh);
        int count = entityMapper.updateByPrimaryKeySelective(bdcFdcqDO);
        LOGGER.info("权利拆分标识：{}，更新待合并房地产权的不动产单元号，房地产权权利ID：{}，实际更新数量：{}", bdcHbBdcdyDTO.getQlcfbs(), bdcHbBdcdyDTO.getQlid(), count);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcHbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @description 更新待合并项目不动产单元关联的查封的不动产单元号为新的不动产单元号
     */
    private void updateHbCfBdcdyh(String ppBdcdyh, BdcHbBdcdyDTO bdcHbBdcdyDTO) {
        BdcCfDO bdcCfDO = new BdcCfDO();
        bdcCfDO.setBdcdyh(ppBdcdyh);
        Example example = new Example(BdcCfDO.class);
        example.createCriteria().andEqualTo("bdcdyh", bdcHbBdcdyDTO.getBdcdyh());
        int count = entityMapper.updateByExampleSelectiveNotNull(bdcCfDO, example);
        LOGGER.info("权利拆分标识：{}，更新查封不动产单元号，查封表原不动产单元号：{}，实际更新数量：{}", bdcHbBdcdyDTO.getQlcfbs(), bdcHbBdcdyDTO.getBdcdyh(), count);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcHbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @description 更新待合并项目不动产单元关联的异议的不动产单元号为新的不动产单元号
     */
    private void updateHbYyBdcdyh(String ppBdcdyh, BdcHbBdcdyDTO bdcHbBdcdyDTO) {
        BdcYyDO bdcYyDO = new BdcYyDO();
        bdcYyDO.setBdcdyh(ppBdcdyh);
        Example example = new Example(BdcYyDO.class);
        example.createCriteria().andEqualTo("bdcdyh", bdcHbBdcdyDTO.getBdcdyh());
        int count = entityMapper.updateByExampleSelectiveNotNull(bdcYyDO, example);
        LOGGER.info("权利拆分标识：{}，更新异议不动产单元号，异议表原不动产单元号：{}，实际更新数量：{}", bdcHbBdcdyDTO.getQlcfbs(), bdcHbBdcdyDTO.getBdcdyh(), count);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcHbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @description 更新待合并项目不动产单元关联的预告的不动产单元号为新的不动产单元号
     */
    private void updateHbYgBdcdyh(String ppBdcdyh, BdcHbBdcdyDTO bdcHbBdcdyDTO) {
        BdcYgDO bdcYgDO = new BdcYgDO();
        bdcYgDO.setBdcdyh(ppBdcdyh);
        Example example = new Example(BdcYgDO.class);
        example.createCriteria().andEqualTo("bdcdyh", bdcHbBdcdyDTO.getBdcdyh());
        int count = entityMapper.updateByExampleSelectiveNotNull(bdcYgDO, example);
        LOGGER.info("权利拆分标识：{}，更新预告不动产单元号，预告表原不动产单元号：{}，实际更新数量：{}", bdcHbBdcdyDTO.getQlcfbs(), bdcHbBdcdyDTO.getBdcdyh(), count);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcHbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @description 更新待合并项目不动产单元关联的抵押的不动产单元号为新的不动产单元号
     */
    private void updateHbDyaBdcdyh(String ppBdcdyh, BdcHbBdcdyDTO bdcHbBdcdyDTO) {
        BdcDyaqDO bdcDyaqDO = new BdcDyaqDO();
        bdcDyaqDO.setBdcdyh(ppBdcdyh);
        Example example = new Example(BdcDyaqDO.class);
        example.createCriteria().andEqualTo("bdcdyh", bdcHbBdcdyDTO.getBdcdyh());
        int count = entityMapper.updateByExampleSelectiveNotNull(bdcDyaqDO, example);
        LOGGER.info("权利拆分标识：{}，更新抵押不动产单元号，抵押表原不动产单元号：{}，实际更新数量：{}", bdcHbBdcdyDTO.getQlcfbs(), bdcHbBdcdyDTO.getBdcdyh(), count);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcHbBdcdyDTO 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @description 更新待合并项目不动产单元关联的锁定的不动产单元号为新的不动产单元号
     */
    private void updateHbSdBdcdyh(String ppBdcdyh, BdcHbBdcdyDTO bdcHbBdcdyDTO) {
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        bdcDysdDO.setBdcdyh(ppBdcdyh);
        Example example = new Example(BdcDysdDO.class);
        example.createCriteria().andEqualTo("bdcdyh", bdcHbBdcdyDTO.getBdcdyh());
        int count = entityMapper.updateByExampleSelectiveNotNull(bdcDysdDO, example);
        LOGGER.info("权利拆分标识：{}，更新锁定不动产单元号，锁定表原不动产单元号：{}，实际更新数量：{}", bdcHbBdcdyDTO.getQlcfbs(), bdcHbBdcdyDTO.getBdcdyh(), count);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xhbBdcdyDTO 新合并记录信息
     * @param bdcHbBdcdyDTO 待合并不动产单元信息
     * @description 更新证书锁定表证号为新的证号
     */
    private void updateHbZssdBdcdyh(BdcXhbBdcdyDTO xhbBdcdyDTO, BdcHbBdcdyDTO bdcHbBdcdyDTO) {
        // 查询新证书ID
        if(null == xhbBdcdyDTO || null == xhbBdcdyDTO.getNewBdcZsDO() || StringUtils.isAnyBlank(xhbBdcdyDTO.getNewBdcZsDO().getZsid(), xhbBdcdyDTO.getNewBdcZsDO().getBdcqzh())) {
            LOGGER.error("根据证号未找到新生成的证书信息");
            return;
        }

        // 查询待合并项目原先关联的证书记录（可能一个单元发了多本证）
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(bdcHbBdcdyDTO.getXmid());
        List<BdcZsDO> dhbBdcZs = bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isEmpty(dhbBdcZs)) {
            return;
        }

        int count = 0;
        for(BdcZsDO dhbBdcZsDO : dhbBdcZs) {
            BdcZssdDO bdcZssdDO = new BdcZssdDO();
            bdcZssdDO.setCqzh(xhbBdcdyDTO.getNewBdcZsDO().getBdcqzh());
            bdcZssdDO.setZsid(xhbBdcdyDTO.getNewBdcZsDO().getZsid());

            Example example = new Example(BdcZssdDO.class);
            example.createCriteria().andEqualTo("cqzh", dhbBdcZsDO.getBdcqzh());

            count += entityMapper.updateByExampleSelectiveNotNull(bdcZssdDO, example);
        }
        LOGGER.info("权利拆分标识：{}，更新证书锁定表的证号，原证书锁定表证号：{}，新的证号：{}, 实际更新数量：{}", bdcHbBdcdyDTO.getQlcfbs(), bdcHbBdcdyDTO.getBdcqzh(), xhbBdcdyDTO.getNewBdcZsDO().getBdcqzh(), count);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyDTOList 待合并不动产单元信息
     * @param newBdcXmDO 新生成项目数据
     * @description 注销合并的记录状态并设置备注字段信息
     */
    private void cancelHbBdcdy(List<BdcHbBdcdyDTO> bdcdyDTOList, BdcXmDO newBdcXmDO) {
        if(StringUtils.isNotBlank(hbBdcdyBz)) {
            if(hbBdcdyBz.contains("{bdcqzh}")) {
                hbBdcdyBz = hbBdcdyBz.replaceAll("\\{bdcqzh\\}", newBdcXmDO.getBdcqzh());
            }
            if(hbBdcdyBz.contains("{bdcdyh}")) {
                hbBdcdyBz = hbBdcdyBz.replaceAll("\\{bdcdyh\\}", newBdcXmDO.getBdcdyh());
            }
            if(hbBdcdyBz.contains("{xmid}")) {
                hbBdcdyBz = hbBdcdyBz.replaceAll("\\{xmid\\}", newBdcXmDO.getBdcdyh());
            }
        }

        String qlcfbs = bdcdyDTOList.get(0).getQlcfbs();
        for(BdcHbBdcdyDTO bdcHbBdcdyDTO : bdcdyDTOList) {
            // 注销项目
            BdcXmDO bdcXmDO = new BdcXmDO();
            bdcXmDO.setXmid(bdcHbBdcdyDTO.getXmid());
            bdcXmDO.setQszt(CommonConstantUtils.QSZT_HISTORY);
            bdcXmDO.setBz(hbBdcdyBz);
            entityMapper.updateByPrimaryKeySelective(bdcXmDO);
            LOGGER.info("权利拆分标识：{}，注销原项目数据：{}", qlcfbs, bdcXmDO);

            // 注销房地产权
            BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();
            bdcFdcqDO.setQlid(bdcHbBdcdyDTO.getQlid());
            bdcFdcqDO.setQszt(CommonConstantUtils.QSZT_HISTORY);
            bdcFdcqDO.setBz(hbBdcdyBz);
            entityMapper.updateByPrimaryKeySelective(bdcFdcqDO);
            LOGGER.info("权利拆分标识：{}，注销原房地产权数据：{}", qlcfbs,bdcFdcqDO);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyDTOList 待合并不动产单元信息
     * @param newBdcXmDO 新生成项目数据
     * @description  更新合并后新的不动产单元权籍现势状态
     */
    private void updateQjBdcdyXszt(List<BdcHbBdcdyDTO> bdcdyDTOList, BdcXmDO newBdcXmDO) {
        List<String> bdcdyhList = bdcdyDTOList.stream().map(BdcHbBdcdyDTO::getBdcdyh).collect(Collectors.toList());
        bdcdyZtFeignService.commonUpdateBdcdyZtByPlBdcdyh(newBdcXmDO.getBdcdyh(), bdcdyhList,"");
        LOGGER.info("权利拆分标识：{}，同步更新权籍新生成记录不动产单元现势状态，合并前不动产单元号{}，合并后不动产单元号{}", bdcdyDTOList.get(0).getQlcfbs(), bdcdyhList, newBdcXmDO.getBdcdyh());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @description 获取地籍号
     */
    private String getDjhByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
            return bdcdyh.substring(0, 19);
        }
        return "";
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param obj 目标实体
     * @description 实体转JSON字符串并去除空格
     */
    private String strJsonTrim(Object obj) {
        if(null == obj) {
            return "";
        }
        return JSON.toJSONString(obj).trim().replaceAll("\\n", " ");
    }
}
