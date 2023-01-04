package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.core.mapper.BdcZsMapper;
import cn.gtmap.realestate.certificate.core.service.BdcQlrService;
import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.BdcFzjlService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.ErrorCodeConstants;
import cn.gtmap.realestate.common.config.accept.QcjdConfig;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlLzrQzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlZsDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcFzjlPdfDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.register.BdcQzxxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLzrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcQzxxFeginService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/28
 * @description 发证记录业务实现类
 */
@Service
public class BdcFzjlServiceImpl implements BdcFzjlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFzjlServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcZsMapper bdcZsMapper;
    @Autowired
    BdcZsService bdcZsService;
    @Autowired
    BdcXmService bdcXmService;
    @Autowired
    BdcQlrService bdcQlrService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcQzxxFeginService bdcQzxxFeginService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    QcjdConfig qcjdConfig;


    @Value("${showRealLzrFlag:false}")
    protected String showRealLzrFlag;

    @Value("${version.hx:standard}")
    protected String hxVersion;

    @Value("${certificate.version:null}")
    private String certificateVersion;
    //standard

    /**
     * @param zsidList
     * @param sfhb     是否合并显示
     * @return List<BdcFzjlZsDTO> 发证记录证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成BdcZsDTO
     */
    private List<BdcFzjlZsDTO> generateBdcZsDTO(List<BdcZsDO> bdcZsDOList, List<String> zsidList, boolean sfhb) {
        List<BdcFzjlZsDTO> bdcFzjlZsDTOList = new ArrayList(CollectionUtils.size(bdcZsDOList));
        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            BdcFzjlZsDTO bdcFzjlZsDTO = new BdcFzjlZsDTO();
            // 需要更新领证人信息的证书ID（即修改一个领证人信息，需要更新的对应的zsid）
            List<String> updateLzrZsidList = new ArrayList();

            BeanUtils.copyProperties(bdcZsDO, bdcFzjlZsDTO);
            if (sfhb) {
                // 合并显示一个证书的信息，但是更新的时候更新所有的证书信息
                updateLzrZsidList = zsidList;

                String bdcqzh = bdcZsDO.getBdcqzh();
                String qzysxlh = bdcZsDO.getQzysxlh();
                if (CollectionUtils.size(updateLzrZsidList) > 1 && !qcjdConfig.qcjdByZsid(bdcZsDO.getZsid())) {
                    bdcFzjlZsDTO.setBdcqzh(StringUtils.isBlank(bdcqzh) ? "" : bdcqzh + CommonConstantUtils.SUFFIX_PL);
                    bdcFzjlZsDTO.setQzysxlh(StringUtils.isBlank(qzysxlh) ? "" : qzysxlh + CommonConstantUtils.SUFFIX_PL);
                }
                bdcFzjlZsDTO.setZsidList(updateLzrZsidList);

                // 初始化证书领证人信息
                this.initZsCommonLzrxx(bdcZsDO, bdcFzjlZsDTO);
                bdcFzjlZsDTOList.add(bdcFzjlZsDTO);
                break;
            } else {
                // 每个证书更新自己的领证人信息
                updateLzrZsidList.add(bdcZsDO.getZsid());

                bdcFzjlZsDTO.setZsidList(updateLzrZsidList);
                // 初始化证书领证人信息
                this.initZsCommonLzrxx(bdcZsDO, bdcFzjlZsDTO);

                bdcFzjlZsDTOList.add(bdcFzjlZsDTO);
            }
        }
        return bdcFzjlZsDTOList;
    }

    /**
     * @return List<BdcFzjlZsDTO> 发证记录证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成BdcZsDTO
     */
    private List<BdcFzjlZsDTO> generateBdcZsDTOForStandard(List<BdcZsDO> bdcZsDOList) {
        List<BdcFzjlZsDTO> bdcFzjlZsDTOList = new ArrayList(CollectionUtils.size(bdcZsDOList));
        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            BdcFzjlZsDTO bdcFzjlZsDTO = new BdcFzjlZsDTO();
            // 需要更新领证人信息的证书ID（即修改一个领证人信息，需要更新的对应的zsid）
            List<String> updateLzrZsidList = new ArrayList();

            BeanUtils.copyProperties(bdcZsDO, bdcFzjlZsDTO);
            // 每个证书更新自己的领证人信息
            updateLzrZsidList.add(bdcZsDO.getZsid());

            bdcFzjlZsDTO.setZsidList(updateLzrZsidList);
            // 初始化证书领证人信息
            this.initZsCommonLzrxxForStandard(bdcZsDO, bdcFzjlZsDTO);

            bdcFzjlZsDTOList.add(bdcFzjlZsDTO);
        }
        return bdcFzjlZsDTOList;
    }

    /**
     * @param bdcZsDO      证书信息
     * @param bdcFzjlZsDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 初始化证书的领证人信息(蚌埠需要一物多人持证时发证记录页面显示对应持证人信息)
     */
    private int initZsCommonLzrxxForStandard(BdcZsDO bdcZsDO, BdcFzjlZsDTO bdcFzjlZsDTO) {
        // 如果配置的参数为true，表示领证人信息只获取受理保存在领证人表的领证人信息，不再给领证人给其他值
        if ("true".equals(showRealLzrFlag)) {
            return 0;
        }
        int result = 0;
        List<String> zsidList = bdcFzjlZsDTO.getZsidList();
        if (StringUtils.isNotBlank(bdcZsDO.getZsid()) && StringUtils.isBlank(bdcZsDO.getLzr())) {
            // 蚌埠需要一物多人持证时发证记录页面显示对应持证人信息，默认获取权利人信息，权利人即为领证人
            Example example = new Example(BdcQlrDO.class);
            example.setOrderByClause("sxh ASC");
            example.createCriteria().andEqualTo("qlrlb", CommonConstantUtils.QLRLB_QLR)
                    .andEqualTo("zsid", bdcZsDO.getZsid());
            List<BdcQlrDO> bdcQlrDOList = entityMapper.selectByExampleNotNull(example);
            // 将权利人信息更新到证书领证人中
            this.initQlrToLzr(bdcFzjlZsDTO, bdcQlrDOList.get(0));
            bdcFzjlZsDTO.setLzsj(new Date());
            List<List> zsidLists = ListUtils.subList(zsidList, 1000);
            for (List zsids : zsidLists) {
                bdcFzjlZsDTO.setZsidList(zsids);
                result += bdcZsMapper.updateZsCommonLzrxx(bdcFzjlZsDTO);
            }
        }
        return result;
    }

    /**
     * @param bdcZsDO      证书信息
     * @param bdcFzjlZsDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 初始化证书的领证人信息(多本证书同一个领证人信息)
     */
    private int initZsCommonLzrxx(BdcZsDO bdcZsDO, BdcFzjlZsDTO bdcFzjlZsDTO) {
        // 如果配置的参数为true，表示领证人信息只获取受理保存在领证人表的领证人信息，不再给领证人给其他值
        if ("true".equals(showRealLzrFlag)) {
            return 0;
        }

        int result = 0;
        List<String> zsidList = bdcFzjlZsDTO.getZsidList();
        if (StringUtils.isNotBlank(bdcZsDO.getZsid()) && StringUtils.isBlank(bdcZsDO.getLzr())) {
            // 获取证书领证人信息
            List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.getAllZsXmLzrByZsid(bdcZsDO.getZsid());
            if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                this.initSlLzrToLzr(bdcFzjlZsDTO, bdcLzrDOList.get(0));
            } else {
                // 受理未保存领证人信息时，默认获取权利人信息
                Example example = new Example(BdcQlrDO.class);
                example.setOrderByClause("sxh ASC");
                example.createCriteria().andEqualTo("qlrlb", CommonConstantUtils.QLRLB_QLR)
                        .andEqualTo("zsid", bdcZsDO.getZsid());
                List<BdcQlrDO> bdcQlrDOList = entityMapper.selectByExampleNotNull(example);
                if (CollectionUtils.isEmpty(bdcQlrDOList)) {
                    return result;
                }
                // 将权利人信息更新到证书领证人中
                this.initQlrToLzr(bdcFzjlZsDTO, bdcQlrDOList.get(0));
            }

            bdcFzjlZsDTO.setLzsj(new Date());

            List<List> zsidLists = ListUtils.subList(zsidList, 1000);
            for (List zsids : zsidLists) {
                bdcFzjlZsDTO.setZsidList(zsids);
                result += bdcZsMapper.updateZsCommonLzrxx(bdcFzjlZsDTO);
            }
        }
        return result;
    }

    /**
     * @param bdcFzjlZsDTO 证书发证记录信息
     * @param bdcLzrDO     领证人对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将受理的领证人信息更新到证书领证人信息中
     */
    private void initSlLzrToLzr(BdcFzjlZsDTO bdcFzjlZsDTO, BdcLzrDO bdcLzrDO) {
        bdcFzjlZsDTO.setLzr(bdcLzrDO.getLzrmc());
        bdcFzjlZsDTO.setLzrzjzl(bdcLzrDO.getLzrzjzl());
        bdcFzjlZsDTO.setLzrdh(bdcLzrDO.getLzrdh());
        bdcFzjlZsDTO.setLzrzjh(bdcLzrDO.getLzrzjh());
    }

    /**
     * @param bdcFzjlZsDTO 证书发证记录信息
     * @param bdcQlrDO     权利人信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将权利人信息初始化为领证人
     */
    private void initQlrToLzr(BdcFzjlZsDTO bdcFzjlZsDTO, BdcQlrDO bdcQlrDO) {
        bdcFzjlZsDTO.setLzr(bdcQlrDO.getQlrmc());
        bdcFzjlZsDTO.setLzrzjzl(bdcQlrDO.getZjzl());
        bdcFzjlZsDTO.setLzrdh(bdcQlrDO.getDh());
        bdcFzjlZsDTO.setLzrzjh(bdcQlrDO.getZjh());
    }

    /**
     * @param bdcFzjlZsDTOList 发证记录证书DTO对象，用于接收发证记录信息
     * @return int 更新记录的数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将发证记录的领证人更新到证书表中(如果没有传默认值 ， 并且bdc_zs没有领证人信息 ， 系统将初始化领证人信息)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateFzjlLzr(List<BdcFzjlZsDTO> bdcFzjlZsDTOList) {
        if (CollectionUtils.isEmpty(bdcFzjlZsDTOList) || null == bdcFzjlZsDTOList.get(0)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        int result = 0;
        for (BdcFzjlZsDTO bdcFzjlZsDTO : bdcFzjlZsDTOList) {
            if (null != bdcFzjlZsDTO) {
                if (StringUtils.isBlank(bdcFzjlZsDTO.getLzr()) && CollectionUtils.isNotEmpty(bdcFzjlZsDTO.getZsidList())) {
                    List<String> zsidListTemp = bdcFzjlZsDTO.getZsidList();
                    for (String zsid : zsidListTemp) {
                        BdcZsDO bdcZsDO = bdcZsService.queryBdcZs(zsid);
                        List<String> zsidList = new ArrayList();
                        zsidList.add(zsid);
                        bdcFzjlZsDTO.setZsidList(zsidList);

                        result += this.initZsCommonLzrxx(bdcZsDO, bdcFzjlZsDTO);
                    }
                } else {
                    result += bdcZsService.updateLzr(bdcFzjlZsDTO);
                }
            }
        }
        return result;
    }

    @Override
    public void plUpdateLzrxx(BdcFzjlZsDTO bdcFzjlZsDTO) {
        if(CollectionUtils.isEmpty(bdcFzjlZsDTO.getGzlslidList())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        List<BdcXmZsGxDO> bdcXmZsGxDOList = this.bdcXmService.listBdcXmZsGxByGzlslidList(bdcFzjlZsDTO.getGzlslidList());
        if(CollectionUtils.isNotEmpty(bdcXmZsGxDOList)){
            List<String> zsidList = bdcXmZsGxDOList.stream().map(BdcXmZsGxDO::getZsid).collect(Collectors.toList());
            zsidList = zsidList.stream().distinct().collect(Collectors.toList());
            bdcFzjlZsDTO.setZsidList(zsidList);
            bdcZsMapper.plUpdateZsLzrxxByZsid(bdcFzjlZsDTO);
        }
    }

    /**
     * @param zsid 证书ID
     * @return BdcFzjlDTO 发证记录信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的发证记录
     */
    @Override
    public BdcFzjlDTO queryBdcZsFzjl(String zsid) {
        if (StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        List<BdcXmZsGxDO> bdcXmZsGxDOList = bdcXmService.queryBdcXmZsgxByZsid(zsid);

        // 多个项目发一本证，取xmid最小的项目信息
        if (CollectionUtils.isNotEmpty(bdcXmZsGxDOList) && null != bdcXmZsGxDOList.get(0)) {
            String xmid = bdcXmZsGxDOList.get(0).getXmid();
            BdcFzjlDTO bdcFzjlDTO = queryFzjl(xmid, zsid);
            if (null != bdcFzjlDTO) {
                if (CollectionUtils.size(bdcXmZsGxDOList) > 1) {
                    // 多个项目发一本证，合并展示
                    bdcFzjlDTO.setSfhb(true);
                } else {
                    bdcFzjlDTO.setSfhb(false);
                }
                return bdcFzjlDTO;
            }
        }
        return new BdcFzjlDTO();
    }

    /**
     * @param xmid 项目ID
     * @param zsid 指定证书的ID
     * @return BdcFzjlDTO 发证记录信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询项目的发证记录
     */
    @Override
    public BdcFzjlDTO queryFzjl(String xmid, String zsid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, xmid);
        List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(xmid);

        if (CollectionUtils.isNotEmpty(bdcZsDOList) && null != bdcZsDOList.get(0)) {
            BdcZsDO bdcZsDOTemp = bdcZsDOList.get(0);
            List<String> zsidList = bdcZsService.queryXmZsid(xmid);

            // 根据项目和证书信息生成发证记录的基本信息
            BdcFzjlDTO bdcFzjlDTO = generateFzjl(bdcXmDO, bdcZsDOTemp);

            // 非空校验，项目附表是后加的表，所以以前的项目没有项目附表数据
            if(null != bdcXmFbDO){
                bdcFzjlDTO.setFzyj(bdcXmFbDO.getFzyj());
            }

            // 生成发证记录所需的证书信息,如果zsid的值不为空时，只生成该zsid的信息
            if (StringUtils.isNotBlank(zsid)) {
                resetZsDOAndZsid(bdcZsDOList, zsidList, zsid);
            }
            if (CommonConstantUtils.SYSTEM_VERSION_BB.equals(certificateVersion) || CommonConstantUtils.SYSTEM_VERSION_STD.equals(certificateVersion)){
                // List<BdcFzjlZsDTO> bdcFzjlZsDTOList = generateBdcZsDTOForStandard(bdcZsDOList);

                // 蚌埠或者标准版：发证记录按照合并逻辑处理，即领证人展示一个权利人、证号等信息拼接，保存时候多个证书的领证人一致
                // 上面领证人分开处理的逻辑暂时保留，避免后期用户需求再次改动
                List<BdcFzjlZsDTO> bdcFzjlZsDTOList = generateBdcZsDTO(bdcZsDOList, zsidList, true);
                bdcFzjlDTO.setBdcFzjlZsDTOList(bdcFzjlZsDTOList);
            }else {
                List<BdcFzjlZsDTO> bdcFzjlZsDTOList = generateBdcZsDTO(bdcZsDOList, zsidList, false);
                bdcFzjlDTO.setBdcFzjlZsDTOList(bdcFzjlZsDTOList);
            }

            // 生成sqr和sqrlxdh
            generateFzjlSqrxx(xmid, bdcFzjlDTO);
            return bdcFzjlDTO;
        }
        return new BdcFzjlDTO();
    }

    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXmDOList 流程所有的项目信息
     * @return 发证记录信息（一个发证记录）
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程发一个发证记录（基本信息取一个项目，证书信息获取流程所有的证书信息）
     */
    @Override
    public BdcFzjlDTO queryLcOneFzjl(String gzlslid, List<BdcXmDO> bdcXmDOList) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        BdcXmDO bdcXmDO = new BdcXmDO();
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && null != bdcXmDOList.get(0)) {
            bdcXmDO = bdcXmDOList.get(0);
        }
        // 查询当前流程生成的所有的证书信息
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        List<BdcZsDO> bdcZsDOList = bdcZsService.listBdcZs(bdcZsQO);
        BdcZsDO bdcZsDOTemp = new BdcZsDO();
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            bdcZsDOTemp = bdcZsDOList.get(0);
        }
        // 获取当前流程中所有证书的ID
        List<String> zsidList = bdcZsService.queryGzlZsid(gzlslid);

        // 根据项目和证书信息生成发证记录的基本信息（坐落信息取自证书的坐落，批量生成一本证书时，对坐落已经加了“等”）
        BdcFzjlDTO bdcFzjlDTO = generateFzjl(bdcXmDO, bdcZsDOTemp);

        String zl = bdcFzjlDTO.getZl();
        if (CollectionUtils.size(zsidList) > 1 && StringUtils.indexOf(zl, CommonConstantUtils.SUFFIX_PL) == -1
                && !qcjdConfig.qcjd(bdcXmDO.getGzldyid())) {
            bdcFzjlDTO.setZl(StringUtils.isBlank(zl) ? "" : zl + CommonConstantUtils.SUFFIX_PL);
        }
        // 生成发证记录所需的证书信息
        List<BdcFzjlZsDTO> bdcFzjlZsDTOList = generateBdcZsDTO(bdcZsDOList, zsidList, false);
        bdcFzjlDTO.setBdcFzjlZsDTOList(bdcFzjlZsDTOList);

        // 生成sqr和sqrlxdh
        generateFzjlSqrxx(bdcXmDO.getXmid(), bdcFzjlDTO);
        return bdcFzjlDTO;
    }


    /**
     * @param zsidList    项目的zsidList
     * @param bdcZsDOList 项目的证书信息
     * @param zsid        指定的zsid
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取指定证书的信息
     */
    private void resetZsDOAndZsid(List<BdcZsDO> bdcZsDOList, List<String> zsidList, String zsid) {
        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            if (StringUtils.equals(zsid, bdcZsDO.getZsid())) {
                bdcZsDOList.clear();
                bdcZsDOList.add(bdcZsDO);
                break;
            }
        }

        for (String zsidTemp : zsidList) {
            if (StringUtils.equals(zsid, zsidTemp)) {
                zsidList.clear();
                zsidList.add(zsid);
                break;
            }
        }
    }

    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXmDOList
     * @return BdcFzjlDTO 发证记录信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 合并展示流程发证记录（合并展示的情况：1.批量生成一本证，2.首次等生成多本证书，但是证书的权利人都是同一个人）
     */
    @Override
    public BdcFzjlDTO queryHbFzjl(String gzlslid, List<BdcXmDO> bdcXmDOList) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        BdcXmDO bdcXmDO = new BdcXmDO();
        BdcXmFbDO bdcXmFbDO = new BdcXmFbDO();
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && null != bdcXmDOList.get(0)) {
            bdcXmDO = bdcXmDOList.get(0);
            bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXmDO.getXmid());
        }
        // 证书项目关系
        List<String> gzlslidList = new ArrayList<>();
        gzlslidList.add(gzlslid);
        List<BdcXmZsGxDO> bdcXmZsGxDOList = bdcXmService.listBdcXmZsGxByGzlslidList(gzlslidList);
        // 合并展示的项目，默认获取一个项目的证书信息即可
//        List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(bdcXmDO.getXmid());
        /**
         * 【28248】bugfix 根据ximd查询证书表,有查不到数据的情况
         */
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        List<BdcZsDO> bdcZsDOList = bdcZsService.listBdcZs(bdcZsQO);

        BdcZsDO bdcZsDOTemp = new BdcZsDO();
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            bdcZsDOTemp = bdcZsDOList.get(0);
        }
        // 获取当前流程中所有证书的ID
        List<String> zsidList = bdcZsService.queryGzlZsid(gzlslid);

        // 根据项目和证书信息生成发证记录的基本信息（坐落信息取自证书的坐落，批量生成一本证书时，对坐落已经加了“等”）
        BdcFzjlDTO bdcFzjlDTO = generateFzjl(bdcXmDO, bdcZsDOTemp);
        // 非空校验，项目附表是后加的表，所以以前的项目没有项目附表数据
        if(null != bdcXmFbDO){
            bdcFzjlDTO.setFzyj(bdcXmFbDO.getFzyj());
        }
        if (CollectionUtils.isNotEmpty(bdcXmZsGxDOList)){
            List<String> xmids = bdcXmZsGxDOList.stream().map(BdcXmZsGxDO::getXmid).collect(Collectors.toList());
            bdcFzjlDTO.setXmidList(xmids);
        }
        // 处理坐落加“等”
        String zl = bdcFzjlDTO.getZl();
        if (CommonConstantUtils.SYSTEM_VERSION_BB.equals(certificateVersion) || CommonConstantUtils.SYSTEM_VERSION_STD.equals(certificateVersion)){
            // 坐落没有拼接等情况下再去判断是否需要加等
            if(StringUtils.indexOf(zl, CommonConstantUtils.SUFFIX_PL) == -1) {
                // 蚌埠按照不动产单元坐落数据判断是否需要多个等形式
                List<BdcXmDO> xmList = bdcXmService.listBdcXmByProInsID(gzlslid);
                if(CollectionUtils.isNotEmpty(xmList)) {
                    List<String> zlList = xmList.stream().map(BdcXmDO::getZl).distinct().collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(zlList) && zlList.size() > 1 && !qcjdConfig.qcjd(bdcXmDO.getGzldyid())) {
                        bdcFzjlDTO.setZl(StringUtils.isBlank(zl) ? "" : zl + CommonConstantUtils.SUFFIX_PL);
                    }
                }
            }
        } else {
            if (CollectionUtils.size(zsidList) > 1 && StringUtils.indexOf(zl, CommonConstantUtils.SUFFIX_PL) == -1
                    && !qcjdConfig.qcjd(bdcXmDO.getGzldyid())) {
                bdcFzjlDTO.setZl(StringUtils.isBlank(zl) ? "" : zl + CommonConstantUtils.SUFFIX_PL);
            }
        }

        // 生成发证记录所需的证书信息
        List<BdcFzjlZsDTO> bdcFzjlZsDTOList = generateBdcZsDTO(bdcZsDOList, zsidList, true);
        bdcFzjlDTO.setBdcFzjlZsDTOList(bdcFzjlZsDTOList);

        // 生成sqr和sqrlxdh
        generateFzjlSqrxx(bdcXmDO.getXmid(), bdcFzjlDTO);
        return bdcFzjlDTO;
    }

    /**
     * @param bdcXmDO 项目信息
     * @param bdcZsDO 证书信息
     * @return BdcFzjlDTO 发证记录的基本信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据项目和证书信息生成发证记录的基本信息
     */
    private BdcFzjlDTO generateFzjl(BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        BdcFzjlDTO bdcFzjlDTO = new BdcFzjlDTO();
        bdcFzjlDTO.setSlbh(bdcXmDO.getSlbh());
        bdcFzjlDTO.setXmid(bdcXmDO.getXmid());
        bdcFzjlDTO.setBz(bdcXmDO.getBz());
        bdcFzjlDTO.setSzr(bdcZsDO.getSzr());
        bdcFzjlDTO.setFzr(bdcZsDO.getFzr());
        bdcFzjlDTO.setFzrq(bdcZsDO.getFzsj());
        bdcFzjlDTO.setZl(bdcZsDO.getZl());
        return bdcFzjlDTO;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param sfhb    是否合并显示
     * @return List<BdcFzjlDTO> 发证记录信息List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程所有项目的发证记录
     */
    @Override
    public List<BdcFzjlDTO> queryListBdcFzjl(String gzlslid, boolean sfhb) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        List<BdcFzjlDTO> bdcFzjlDTOList = new ArrayList();
        List<BdcXmDO> bdcXmDOList = bdcXmService.queryLcAllBdcXm(null, gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            if (sfhb) {
                BdcFzjlDTO bdcFzjlDTO = queryHbFzjl(gzlslid, bdcXmDOList);
                bdcFzjlDTOList.add(bdcFzjlDTO);
            } else {
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    BdcFzjlDTO bdcFzjlDTO = queryFzjl(bdcXmDO.getXmid(), null);
                    bdcFzjlDTOList.add(bdcFzjlDTO);
                }
            }
        }

        return bdcFzjlDTOList;
    }

    /**
     * @param xmid 项目ID
     * @param sfhb 是否批量更新
     * @param bz   领证备注
     * @return int 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新发证记录的备注信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateFzjlBz(String xmid, boolean sfhb, String bz) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        int result = 0;
        if (sfhb) {
            List<BdcXmDO> bdcXmDOList = bdcXmService.queryLcAllBdcXm(xmid, null);
            for (BdcXmDO bdcXmDOTemp : bdcXmDOList) {
                result += bdcXmService.updateXmBz(bdcXmDOTemp.getXmid(), bz);
            }
        } else {
            return bdcXmService.updateXmBz(xmid, bz);
        }
        return result;
    }

    /**
     * @param xmid 项目ID
     * @param sfhb 是否批量更新
     * @param fzyj 发证意见
     * @return int 更新数量
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 更新发证记录的发证意见信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateFzjlFzyj(String xmid, boolean sfhb, String fzyj) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        int result = 0;
        if (sfhb) {
            List<BdcXmFbDO> bdcXmFbDOList = bdcXmService.queryLcAllBdcXmFb(xmid, null);
            for (BdcXmFbDO bdcXmFbDOTemp : bdcXmFbDOList) {
                result += bdcXmService.updateXmFbFzyj(bdcXmFbDOTemp.getXmid(), fzyj);
            }
        } else {
            return bdcXmService.updateXmFbFzyj(xmid, fzyj);
        }
        return result;
    }

    /**
     * @param gzlslid      工作流实例ID
     * @param fzr          发证人姓名
     * @param isNullUpdate 只有当发证人为空的时候更新（true则做发证人是否为空的判断，否则直接更新发证信息）
     * @return int 更新记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新流程发证人
     */
    @Override
    public int updateFzr(String gzlslid, UserDto fzr, Boolean isNullUpdate) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        List<String> zsidList = bdcZsService.queryGzlZsid(gzlslid);
        return bdcZsService.updateFzr(zsidList, fzr, isNullUpdate);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param szr     缮证人信息
     * @return int 更新记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新流程证书的缮证人
     */
    @Override
    public BdcSzxxVO updateSzr(String gzlslid, UserDto szr) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        List<String> zsidList = bdcZsService.queryGzlZsid(gzlslid);
        BdcSzxxVO bdcSzxxVO = bdcZsService.updateSzr(zsidList, szr);
        //根据版本控制是否回写，南通版本回写缮证人字段
        if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_NT, hxVersion)) {
            try {
                Map<String, Object> paramMap = new HashMap<>(1);
                paramMap.put("SZR", szr.getAlias());
                bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
            } catch (Exception e) {
                LOGGER.error("回写大云字段异常！gzlslid={},回写字段szr={}", gzlslid, szr.getAlias());
            }
        }

        return bdcSzxxVO;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param szr     缮证人信息
     * @return int 更新记录
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 更新流程证书的缮证人(不管证书表中是否存在缮证人信息都更新)
     */
    @Override
    public BdcSzxxVO updateSzrByGzlslid(String gzlslid, UserDto szr) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        List<String> zsidList = bdcZsService.queryGzlZsid(gzlslid);
        BdcSzxxVO bdcSzxxVO = bdcZsService.updateSzrByGzlslid(zsidList, szr);
        return bdcSzxxVO;
    }

    /**
     * @param bdcPrintDTO 打印参数
     * @return 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取发证记录的打印XMl
     */
    @Override
    public String fzjlPrintXml(BdcPrintDTO bdcPrintDTO) {
        // 设置打印参数
        List<Map> maps = generateFzjlDyMaps(bdcPrintDTO);

        Map<String, List<Map>> paramMap = new HashMap(1);
        // 设置打印类型
        paramMap.put(CommonConstantUtils.FZJL_DYLX_XM, maps);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 检查领证人信息（南通）
     * <p>
     * （1）受理时无领证人信息，如果发证节点领证人信息与权利人信息不符合。
     * 需提示“XX（证件号XX）与权利人XX（证件号XX）不符”。示例：“张三（320611...）与权利人李四（320601...）不一致”。
     * （2）受理时有领证人信息，如果发证节点领证人信息与权利人、还有受理页面的领证人信息不符合。
     * 需提示“XX（证件号XX）与权利人及受理时领证人XX（证件号XX）不符”。示例：“张三（320611...）与权利人及受理时领证人李四（320601...）不一致”。
     *
     * @param xmid       项目ID
     * @param bdcFzjlDTO 发证记录对象
     * @return String 提示信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public String checkLzr(String xmid, BdcFzjlDTO bdcFzjlDTO) {
        if (StringUtils.isBlank(xmid) || bdcFzjlDTO == null) {
            throw new MissingArgumentException("检查领证人信息，缺失查询参数!");
        }
        List<BdcFzjlZsDTO> bdcFzjlZsDTOList = bdcFzjlDTO.getBdcFzjlZsDTOList();
        if (CollectionUtils.isEmpty(bdcFzjlZsDTOList)) {
            return "";
        }
        for (BdcFzjlZsDTO bdcFzjlZsDTO : bdcFzjlZsDTOList) {
            if (CollectionUtils.isEmpty(bdcFzjlZsDTO.getZsidList())) {
                throw new MissingArgumentException("数据缺失zsid信息！");
            }
            String lzr = bdcFzjlZsDTO.getLzr();
            String lzrzjh = bdcFzjlZsDTO.getLzrzjh();
            // 判断是否领证人信息是否均为空
            if (StringUtils.isBlank(lzr) && StringUtils.isBlank(lzrzjh)) {
                continue;
            }
            List<BdcQlrDO> bdcQlrDOList = bdcQlrService.queryListBdcQlr(xmid, CommonConstantUtils.QLRLB_QLR_DM);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                boolean ifContinue = false;
                // 比较权利人和领证人信息是否相同（如果权利人类别中包含权利人类型不等于 1 的情况则不验证）
                for (BdcQlrDO qlrDO : bdcQlrDOList) {
                    // 匹配上权利人直接返回
                    if(Objects.nonNull(qlrDO) &&
                            ( !Objects.equals(qlrDO.getQlrlx(), 1) || compareQlr(qlrDO.getQlrmc(), qlrDO.getZjh(), lzr, lzrzjh) )){
                        ifContinue = true;
                        break;
                    }
                }
                if (ifContinue) {
                    continue;
                }
            }
            // 取任意一个权利人用于以下判断提示
            BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
            // 根据证书id 获取受理领证人信息
            List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.getAllZsXmLzrByZsid(bdcFzjlZsDTO.getZsidList().get(0));
            if (CollectionUtils.isEmpty(bdcLzrDOList)) {
                // 受理时无领证人信息
                return getCheckLzrTipForDiffient(lzr, lzrzjh, bdcQlrDO,bdcQlrDOList);
            } else {
                // 受理时有领证人信息
                String tip = "";
                Integer sameLzr = 0;
                for (BdcLzrDO bdcLzrDO : bdcLzrDOList) {
                    //南通 发证记录无法保存问题 非空判断
                    if( Objects.nonNull(bdcLzrDO) && StringUtils.isNotBlank(bdcLzrDO.getLzrmc()) && StringUtils.isNotBlank(bdcLzrDO.getLzrzjh()) ){
                        boolean flag = compareQlr(bdcLzrDO.getLzrmc(), bdcLzrDO.getLzrzjh(), lzr, lzrzjh);
                        if (flag) {
                            // 验证到有相同信息则跳出本循环
                            sameLzr++;
                            break;
                        } else {
                            tip = lzr + "(" + lzrzjh + ") 与权利人及受理时领证人" + bdcLzrDO.getLzrmc() + "(" + bdcLzrDO.getLzrzjh() + ")不一致";
                        }
                    } else {
                        //南通 发证记录无法保存问题 bdc_lzr表未查到数据先按受理时无领证人信息处理
                        return getCheckLzrTipForDiffient(lzr, lzrzjh, bdcQlrDO,bdcQlrDOList);
                    }
                }
                if (0 == sameLzr) {
                    return tip;
                }
            }

        }
        return "";
    }

    /**
     * 受理时无领证人信息时当前台证书保存页面录入的领证人和权利人不一致时前台提示信息组装
     * @param lzr 前台录入领证人名称
     * @param lzrzjh 前台录入领证人证件号
     * @param bdcQlrDO 权利人DTO对象
     * @param bdcQlrDOList 权利人DTO对象列表
     * @return
     */
    private String getCheckLzrTipForDiffient(String lzr, String lzrzjh, BdcQlrDO bdcQlrDO,List<BdcQlrDO> bdcQlrDOList) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(lzr)
                .append("(")
                .append(lzrzjh)
                .append(") 与权利人")
                .append(bdcQlrDO.getQlrmc())
                .append("(")
                .append(bdcQlrDO.getZjh())
                .append(")")
                .append(bdcQlrDOList.size() > 1 ? "等均不一致" : "不一致").toString();
//        return lzr + "(" + lzrzjh + ") 与权利人" + bdcQlrDO.getQlrmc() + "(" + bdcQlrDO.getZjh() + ")" + (bdcQlrDOList.size() > 1 ? "等均不一致" : "不一致");
    }

    /**
     * @param bdcPrintDTO 打印DTO对象
     * @return 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按流程生成发证记录, 获取发证记录的打印XMl
     */
    @Override
    public String fzjlOnePrintXml(BdcPrintDTO bdcPrintDTO) {
        // 设置打印参数
        List<Map> maps = generateFzjlOneDyMaps(bdcPrintDTO);

        Map<String, List<Map>> paramMap = new HashMap(1);
        // 设置打印类型
        paramMap.put(bdcPrintDTO.getDylx(), maps);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlLzrQzxxDTO 领证人签字信息
     * @return {BdcQzxxDO} 入库签字信息
     * @description  保存领证人签字图片信息
     */
    @Override
    public BdcQzxxDO saveFzjlLzrQzxx(BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO) {
        if(null == bdcFzjlLzrQzxxDTO ||  StringUtils.isBlank(bdcFzjlLzrQzxxDTO.getXmid())) {
            throw new AppException("保存领证人签字图片信息操作失败,原因: 未定义参数信息!");
        }

        if(StringUtils.isBlank(bdcFzjlLzrQzxxDTO.getSignStreamData()) && MapUtils.isEmpty(bdcFzjlLzrQzxxDTO.getSignStreamData2())) {
            throw new AppException("保存领证人签字图片信息操作失败,原因: 未定义签字图片信息!");
        }

        // 先清空下当前项目签字信息
        Example example = new Example(BdcQzxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("xmid", bdcFzjlLzrQzxxDTO.getXmid());
        if(StringUtils.isNotBlank(bdcFzjlLzrQzxxDTO.getZsid())){
            criteria.andEqualTo("zsid", bdcFzjlLzrQzxxDTO.getZsid());
        }
        criteria.andEqualTo("bdlx", Constants.FZJL_LZR_QZ);
        int count = entityMapper.deleteByExampleNotNull(example);
        LOGGER.info("保存前清空项目{}发证记录签字信息{}条", bdcFzjlLzrQzxxDTO.getXmid(), count);

        BdcQzxxDO bdcQzxxDO = null;
        int recordNums = 0;
        // 保存通用签字内容
        if(StringUtils.isNotBlank(bdcFzjlLzrQzxxDTO.getSignStreamData())) {
            bdcQzxxDO = this.saveQzxx(bdcFzjlLzrQzxxDTO.getXmid(), bdcFzjlLzrQzxxDTO.getZsid(), bdcFzjlLzrQzxxDTO.getSignStreamData(), null);
            recordNums++;
        }

        // 保存单独的领证人签字
        if(MapUtils.isNotEmpty(bdcFzjlLzrQzxxDTO.getSignStreamData2())) {
            for(Map.Entry<Integer, String> entry : bdcFzjlLzrQzxxDTO.getSignStreamData2().entrySet()) {
                this.saveQzxx(bdcFzjlLzrQzxxDTO.getXmid(), bdcFzjlLzrQzxxDTO.getZsid(), entry.getValue(), entry.getKey());
                recordNums++;
            }
        }

        LOGGER.info("保存项目{}发证记录签字信息{}条", bdcFzjlLzrQzxxDTO.getXmid(), recordNums);
        return bdcQzxxDO;
    }

    @Override
    public List<BdcQzxxDO> plSaveFzjlLzrQzxx(BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO) {
        if(null == bdcFzjlLzrQzxxDTO ||  CollectionUtils.isEmpty(bdcFzjlLzrQzxxDTO.getGzlslidList())
                || (StringUtils.isBlank(bdcFzjlLzrQzxxDTO.getSignStreamData()))) {
            throw new AppException("保存领证人签字图片信息操作失败,原因: 未定义参数信息!");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmService.listBdcXmBfxxByProInsIDList(bdcFzjlLzrQzxxDTO.getGzlslidList());
        List<BdcQzxxDO> bdcQzxxDOList = new ArrayList<>(bdcXmDTOList.size());
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
            StorageDto storageDto = this.uploadQzxx(bdcFzjlLzrQzxxDTO.getSignStreamData(), bdcXmDTOList.get(0).getXmid());
            if(Objects.isNull(storageDto)){
                throw new AppException("保存发证记录签字人签字图片信息失败");
            }

            for(BdcXmDTO xmxx : bdcXmDTOList){
                // 1、先清空下当前项目签字信息
                Example example = new Example(BdcQzxxDO.class);
                example.createCriteria().andEqualTo("xmid", xmxx.getXmid()).andEqualTo("bdlx", Constants.FZJL_LZR_QZ);
                int count = entityMapper.deleteByExampleNotNull(example);
                LOGGER.info("保存前清空项目{}发证记录签字信息{}条", xmxx.getXmid(), count);

                // 2、保存签字信息
                BdcQzxxDO bdcQzxxDO = new BdcQzxxDO();
                bdcQzxxDO.setXmid(xmxx.getXmid());
                bdcQzxxDO.setBdlx(Constants.FZJL_LZR_QZ);
                bdcQzxxDO.setQznr(bdcFzjlLzrQzxxDTO.getSignStreamData());
                bdcQzxxDO.setFid(storageDto.getId());
                bdcQzxxDOList.add(bdcQzxxDO);
                bdcQzxxFeginService.SaveOrUpdateQzxx(bdcQzxxDO);
            }
        }
        return bdcQzxxDOList;
    }

    /**
     * 保存签字信息
     * @param xmid 项目ID
     * @param zsid 项目ID
     * @param streamData 签名图片
     * @param index 领证人顺序号
     */
    private BdcQzxxDO saveQzxx(String xmid, String zsid, String streamData, Integer index) {
        StorageDto storageDto = this.uploadQzxx(streamData, xmid);
        if(Objects.isNull(storageDto)){
            throw new AppException("保存发证记录签字人签字图片信息失败");
        }
        BdcQzxxDO bdcQzxxDO = new BdcQzxxDO();
        bdcQzxxDO.setXmid(xmid);
        if(StringUtils.isNotBlank(zsid)){
            bdcQzxxDO.setZsid(zsid);
        }
        bdcQzxxDO.setBdlx(Constants.FZJL_LZR_QZ);
        bdcQzxxDO.setQznr(streamData);
        bdcQzxxDO.setFid(storageDto.getId());
        bdcQzxxDO.setSxh(index);
        bdcQzxxFeginService.SaveOrUpdateQzxx(bdcQzxxDO);
        return bdcQzxxDO;
    }

    private StorageDto uploadQzxx(String streamData, String id){
        StorageDto storageDto;
        try {
            String fileName = String.format("项目%s对应证书领证人签字", id);
            UserDto userDto = userManagerUtils.getCurrentUser();
            String userId = null == userDto ? "" : userDto.getId();

            MultipartDto multipartDto = new MultipartDto();
            multipartDto.setName(fileName);
            multipartDto.setNodeId("");
            multipartDto.setClientId("clientId");
            multipartDto.setContentType("image/jpg;charset=utf-8");
            multipartDto.setData(streamData.getBytes("UTF-8"));
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setOwner(userId);

            storageDto = storageClient.multipartUpload(multipartDto);
        } catch (Exception e) {
            LOGGER.error("保存发证记录签字人签字图片信息失败!{}", e.getMessage());
            throw new AppException("保存发证记录签字人签字图片信息失败");
        }
        return storageDto;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {String} 领证人签字图片Base64字符串
     * @description  获取领证人签字图片信息
     */
    @Override
    public String getFzjlLzrQzxx(String xmid, String zsid) {
        if(StringUtils.isBlank(xmid)) {
            return null;
        }

        Example example = new Example(BdcQzxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("xmid", xmid);
        criteria.andEqualTo("bdlx", Constants.FZJL_LZR_QZ);
        if(StringUtils.isNotBlank(zsid)){
            criteria.andEqualTo("zsid", zsid);
        }
        List<BdcQzxxDO> qzxxList = entityMapper.selectByExampleNotNull(example);

        if(CollectionUtils.isEmpty(qzxxList) || null == qzxxList.get(0) || StringUtils.isBlank(qzxxList.get(0).getFid())) {
            return null;
        }

        MultipartDto multipartDto = storageClient.download(qzxxList.get(0).getFid());
        if(null == multipartDto || null == multipartDto.getData()) {
            return null;
        }
        return new String(multipartDto.getData());
    }

    /**
     * 获取领证人签字图片信息（如果指定顺序号则查询项目指定顺序号签字信息，否则查询项目所有签字信息）
     *
     * @param bdcQzxxQO 签字信息查询参数
     * @return {List} 发证记录领证人签字信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<BdcQzxxDO> getFzjlLzrQzxxs(BdcQzxxQO bdcQzxxQO) {
        if(null == bdcQzxxQO || StringToolUtils.isAllNullOrEmpty(bdcQzxxQO.getXmid(), bdcQzxxQO.getSlbh(), bdcQzxxQO.getGzlslid())) {
            throw new MissingArgumentException("未定义查询领证人签字信息项目参数");
        }

        List<BdcQzxxDO> qzxxDOList = bdcZsMapper.queryFzjlLzrQzxxs(bdcQzxxQO);
        if(CollectionUtils.isEmpty(qzxxDOList)) {
            LOGGER.info("未查询到领证人签字图片信息：{}", bdcQzxxQO);
            return qzxxDOList;
        }

        for(BdcQzxxDO bdcQzxxDO : qzxxDOList) {
            if(StringUtils.isBlank(bdcQzxxDO.getQznr()) && StringUtils.isNotBlank(bdcQzxxDO.getFid())) {
                // 签字内容没保存但是上传了对应图片附件，重新获取下
                MultipartDto multipartDto = storageClient.download(bdcQzxxDO.getFid());
                if(null != multipartDto && null != multipartDto.getData()) {
                    bdcQzxxDO.setQznr(new String(multipartDto.getData()));
                }
            }
        }
        return qzxxDOList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlPdfDTO PDF及项目信息
     * @return {String} 文件存储ID
     * @description  保存发证记录文件到大云并关联到当前项目作为附件
     */
    @Override
    public String saveFzjlPdfFile(BdcFzjlPdfDTO bdcFzjlPdfDTO) {
        if(null == bdcFzjlPdfDTO || StringUtils.isAnyBlank(bdcFzjlPdfDTO.getGzlslid(), bdcFzjlPdfDTO.getPdfFilePath())) {
            throw new AppException("保存发证记录PDF文件操作失败,原因: 未定义参数信息!");
        }

        File pdfFile = new File(bdcFzjlPdfDTO.getPdfFilePath());
        if(!pdfFile.exists()) {
            throw new AppException("保存发证记录PDF文件操作失败,原因: 不存在PDF文件!");
        }

        try {
            String base64 = "data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(pdfFile));
            return bdcUploadFileUtils.uploadBase64File(base64, bdcFzjlPdfDTO.getGzlslid(), "发证记录", "发证记录", ".pdf");
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new AppException("保存发证记录PDF文件操作失败!");
        }
        finally {
            if(pdfFile.exists()) {
                pdfFile.delete();
                LOGGER.info("删除发证记录临时PDF文件成功，工作流实例ID：{}，文件路径：{}", bdcFzjlPdfDTO.getGzlslid(), bdcFzjlPdfDTO.getPdfFilePath());
            }
        }
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlPdfDTO PDF及项目信息
     * @param userId 用户ID
     * @description  删除现有的发证记录PDF文件
     */
    private void deleteExistFzjlFile(BdcFzjlPdfDTO bdcFzjlPdfDTO, String userId) {
        // 查
        List<StorageDto> existPdfFiles = storageClient.listStoragesByName("clientId", bdcFzjlPdfDTO.getGzlslid(), userId, "发证记录.pdf", null, null);
        // 删
        if(CollectionUtils.isNotEmpty(existPdfFiles) && StringUtils.isNotBlank(existPdfFiles.get(0).getId())) {
            List<String> fids = existPdfFiles.stream().map(StorageDto::getId).collect(Collectors.toList());
            storageClient.deleteStorages(fids);
            LOGGER.info("删除现有发证记录，避免重复，工作流实例ID：{}，文件ID ：{}", bdcFzjlPdfDTO.getGzlslid(), fids);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlPdfDTO PDF及项目信息
     * @param userId 用户ID
     * @description  新建发证记录文件夹
     */
    private StorageDto createFzjlFolder(BdcFzjlPdfDTO bdcFzjlPdfDTO, String userId) {
        return storageClient.createRootFolder(Constants.WJZX_CLIENTID, bdcFzjlPdfDTO.getGzlslid(), "发证记录", userId);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pdfFile PDF文件路径
     * @description  发证记录PDF文件转为Base64码
     */
    private MultipartFile toBase64(File pdfFile) throws IOException {
        String pdfBase64Str = "data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(pdfFile));
        MultipartFile multipartFile = Base64Utils.base64ToMultipart(pdfBase64Str);
        if(null == multipartFile) {
            throw new AppException("保存发证记录PDF文件操作失败,原因: PDF文件转换Base64失败!");
        }
        return multipartFile;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlPdfDTO PDF及项目信息
     * @param userId 用户ID
     * @param folder 所在文件夹
     * @param multipartFile 文件
     * @description  保存发证记录PDF文件
     */
    private String savePdfFile(BdcFzjlPdfDTO bdcFzjlPdfDTO, String userId, StorageDto folder, MultipartFile multipartFile) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setName("发证记录");
        multipartDto.setNodeId(folder.getId());
        multipartDto.setSpaceCode(bdcFzjlPdfDTO.getGzlslid());
        multipartDto.setClientId("clientId");
        multipartDto.setContentType("application/octet-stream");
        multipartDto.setData(multipartFile.getBytes());
        multipartDto.setSize(multipartFile.getSize());
        multipartDto.setOriginalFilename("发证记录.pdf");
        multipartDto.setOwner(userId);

        StorageDto storageDto = storageClient.multipartUpload(multipartDto);
        if(null == storageDto || StringUtils.isBlank(storageDto.getId())) {
            throw new AppException("保存发证记录PDF文件操作失败,原因: 大云未成功保存PDF文件!");
        }

        LOGGER.info("保存发证记录PDF文件操作，工作流实例ID：{}，保存的附件信息{}", bdcFzjlPdfDTO.getGzlslid(), JSON.toJSONString(storageDto));
        return storageDto.getId();
    }

    /**
     * @param bdcPrintDTO 不动产打印DTO
     * @return List<Map> 打印参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按流程生成发证记录，根据dylx生成发证记录的打印参数
     */
    private List<Map> generateFzjlOneDyMaps(BdcPrintDTO bdcPrintDTO) {
        String gzlslid = bdcPrintDTO.getGzlslid();
        String dylx = bdcPrintDTO.getDylx();
        if (StringUtils.isBlank(dylx)) {
            throw new MissingArgumentException("打印缺失dylx字段!");
        }
        List<Map> maps = new ArrayList();

        if (StringUtils.equals(CommonConstantUtils.FZJL_DYLX_XM, dylx)) {
            generateFzjlAllXmDyMaps(maps, gzlslid, bdcPrintDTO);
            return maps;
        }
        if (StringUtils.equals(CommonConstantUtils.FZJL_DYLX_HB, dylx)) {
            generateFzjlHbDyMaps(maps, gzlslid, bdcPrintDTO);
            return maps;
        }
        return maps;
    }

    /**
     * @param maps        打印参数
     * @param gzlslid     工作流实例ID
     * @param bdcPrintDTO 打印参数对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按流程合并生成发证记录，参数组装
     */
    private void generateFzjlAllXmDyMaps(List<Map> maps, String gzlslid, BdcPrintDTO bdcPrintDTO) {
        String xmid = bdcPrintDTO.getXmid();
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("打印缺失xmid!");
        }
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        Map<String, String> map = new HashMap();
        map.put(Constants.XMID, xmid);
        map.put(Constants.EWM, bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcXmDO.getSlbh());
        map.put(Constants.ZL_SFX, " ");
        map.put(Constants.ZS_SFX, " ");
        map.put(CommonConstantUtils.GZLSLID, gzlslid);
        map.put(Constants.LZRQZ, bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/fzjl/print/" + xmid + "/lzrqz");

        // 为坐落后缀重新设值
        List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            String zl = bdcZsDOList.get(0).getZl();
            if (StringUtils.isNotBlank(zl) && StringUtils.indexOf(zl, CommonConstantUtils.SUFFIX_PL) == -1 && !qcjdConfig.qcjd(bdcXmDO.getGzldyid())) {
                map.put(Constants.ZL_SFX, CommonConstantUtils.SUFFIX_PL);
            }
        }
        maps.add(map);
    }

    /**
     * @param bdcPrintDTO 不动产打印DTO
     * @return List<Map> 打印参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据dylx生成发证记录的打印参数
     */
    private List<Map> generateFzjlDyMaps(BdcPrintDTO bdcPrintDTO) {
        String gzlslid = bdcPrintDTO.getGzlslid();
        String dylx = bdcPrintDTO.getDylx();
        if (StringUtils.isBlank(dylx)) {
            throw new MissingArgumentException("打印缺失dylx字段!");
        }
        List<Map> maps = new ArrayList();

        if (StringUtils.equals(CommonConstantUtils.FZJL_DYLX_LIST, dylx)) {
            generateFzjlListDyMaps(maps, gzlslid, bdcPrintDTO.getGzlslids(), bdcPrintDTO.getBdcUrlDTO());
            return maps;
        }
        if (StringUtils.equals(CommonConstantUtils.FZJL_DYLX_XM, dylx)) {
            generateFzjlXmDyMaps(maps, gzlslid, bdcPrintDTO);
            return maps;
        }
        if (StringUtils.equals(CommonConstantUtils.FZJL_DYLX_HB, dylx)) {
            generateFzjlHbDyMaps(maps, gzlslid, bdcPrintDTO);
            return maps;
        }
        return maps;
    }

    /**
     * @param maps        打印参数
     * @param gzlslid     工作流实例ID
     * @param bdcPrintDTO 打印参数对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按流程合并生成发证记录，参数组装
     */
    private void generateFzjlHbDyMaps(List<Map> maps, String gzlslid, BdcPrintDTO bdcPrintDTO) {
        String xmid = bdcPrintDTO.getXmid();
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("打印缺失xmid!");
        }
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        Map<String, String> map = new HashMap();
        map.put(Constants.XMID, xmid);
        map.put(Constants.EWM, bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcXmDO.getSlbh());
        map.put(Constants.QZNR, bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/pjqqm?gzlslid=" + gzlslid
                + "&amp;xmid=" + xmid + "&amp;bdlx=3&amp;qzrlb=5&amp;zsid=" + bdcPrintDTO.getZsid());
        map.put(Constants.ZL_SFX, " ");
        map.put(Constants.ZS_SFX, " ");
        map.put(Constants.LZRQZ, bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/fzjl/print/" + xmid + "/lzrqz?zsid="+ bdcPrintDTO.getZsid());

        // 为坐落和证书后缀重新设值
        List<String> zsidList = bdcZsService.queryGzlZsid(gzlslid);
        List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(xmid);
        // 批量多抵一的证书，证书已做过处理，不用再加“等”。同一个权利人的批量流程，需要和打印数据源相结合
        if (CollectionUtils.isNotEmpty(zsidList) && CollectionUtils.size(zsidList) > 1 && !qcjdConfig.qcjd(bdcXmDO.getGzldyid())) {
            map.put(Constants.ZS_SFX, CommonConstantUtils.SUFFIX_PL);
        }
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            String zl = bdcZsDOList.get(0).getZl();
            if (StringUtils.isNotBlank(zl) && StringUtils.indexOf(zl, CommonConstantUtils.SUFFIX_PL) == -1 && !qcjdConfig.qcjd(bdcXmDO.getGzldyid())) {
                map.put(Constants.ZL_SFX, CommonConstantUtils.SUFFIX_PL);
            }
        }
        maps.add(map);
    }

    /**
     * @param maps        打印参数
     * @param gzlslid     工作流实例ID
     * @param bdcPrintDTO 打印参数对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按项目生成发证记录，参数组装
     */
    private void generateFzjlXmDyMaps(List<Map> maps, String gzlslid, BdcPrintDTO bdcPrintDTO) {
        String xmid = bdcPrintDTO.getXmid();
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("打印缺失xmid!");
        }
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        Map<String, String> map = new HashMap();
        map.put(Constants.XMID, xmid);
        map.put(Constants.EWM, bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcXmDO.getSlbh());
        map.put(Constants.QZNR, bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/pjqqm?gzlslid=" + gzlslid
                + "&amp;xmid=" + xmid + "&amp;bdlx=3&amp;qzrlb=5&amp;zsid=" + bdcPrintDTO.getZsid());
        map.put(Constants.LZRQZ, bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/fzjl/print/" + xmid + "/lzrqz?zsid=" + bdcPrintDTO.getZsid() );
        map.put(Constants.ZL_SFX, " ");
        map.put(Constants.ZS_SFX, " ");
        maps.add(map);
    }

    /**
     * @param maps      打印参数
     * @param bdcUrlDTO 打印所需url对象
     * @param gzlslid   工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 发证记录列表打印功能，参数组装
     */
    private void generateFzjlListDyMaps(List<Map> maps, String gzlslid, String gzlslids, BdcUrlDTO bdcUrlDTO) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(gzlslids)) {
            throw new MissingArgumentException("打印缺失gzlslid或gzlslids!");
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        if (StringUtils.isNotBlank(gzlslids)) {
            List<String> gzlslidList = Arrays.asList(gzlslids.split(CommonConstantUtils.ZF_YW_DH));
            bdcZsQO.setGzlslidList(gzlslidList);
        }
        bdcZsQO.setGzlslid(gzlslid);
        List<String> listZsid = bdcZsService.queryZsid(bdcZsQO);
        if (CollectionUtils.isNotEmpty(listZsid)) {
            String slbh = "";
            for (String zsid : listZsid) {
                List<BdcXmZsGxDO> bdcXmZsGxDOList = bdcXmService.queryBdcXmZsgxByZsid(zsid);
                if (CollectionUtils.isNotEmpty(bdcXmZsGxDOList) && null != bdcXmZsGxDOList.get(0)) {
                    String xmid = bdcXmZsGxDOList.get(0).getXmid();
                    // 循环取一次受理编号
                    BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
                    if (StringUtils.isBlank(slbh)) {
                        slbh = bdcXmDO.getSlbh();
                    }
                    Map<String, String> map = new HashMap();
                    map.put(Constants.XMID, xmid);
                    map.put(Constants.EWM, bdcUrlDTO.getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + slbh);
                    map.put(Constants.QZNR, bdcUrlDTO.getRegisterUiUrl() + "/rest/v1.0/print/pjqqm?gzlslid=" + bdcXmDO.getGzlslid()
                            + "&amp;xmid=" + xmid + "&amp;bdlx=3&amp;qzrlb=5&amp;zsid="+ zsid);
                    map.put(Constants.LZRQZ, bdcUrlDTO.getRegisterUiUrl() + "/rest/v1.0/fzjl/print/" + xmid + "/lzrqz?zsid=" + zsid);
                    map.put(Constants.ZS_SFX, " ");
                    if (bdcXmZsGxDOList.size() > 1 && !qcjdConfig.qcjdByZsid(zsid)) {
                        // 多个项目，坐落需要加“等”做后缀
                        map.put(Constants.ZL_SFX, CommonConstantUtils.SUFFIX_PL);
                    } else {
                        map.put(Constants.ZL_SFX, " ");
                    }
                    maps.add(map);
                }
            }
        }
    }


    /**
     * @param
     * @param xmid
     * @param bdcFzjlDTO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成发证记录申请人信息
     */
    private void generateFzjlSqrxx(String xmid, BdcFzjlDTO bdcFzjlDTO) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.queryListBdcQlr(xmid, CommonConstantUtils.QLRLB_QLR_DM);
        StringBuilder sqr = new StringBuilder();
        StringBuilder sqrlxdh = new StringBuilder();
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                if (null != bdcQlrDO) {
                    sqr = sqr.append(StringUtils.isNotBlank(bdcQlrDO.getQlrmc()) ? bdcQlrDO.getQlrmc() : "").append(" ");
                    sqrlxdh = sqrlxdh.append(StringUtils.isNotBlank(bdcQlrDO.getDh()) ? bdcQlrDO.getDh() : "").append(" ");
                }
            }
        }
        bdcFzjlDTO.setSqr(sqr.toString());
        bdcFzjlDTO.setSqrlxdh(sqrlxdh.toString());
    }

    /**
     * 比较两个权利人的信息
     *
     * @return 相同返回 true
     */
    private boolean compareQlr(String qlrmcA, String qlrzjhA, String qlrmcB, String qlrzjhB) {
        return StringUtils.equals(qlrmcA, qlrmcB) && StringUtils.equals(CardNumberTransformation.idCard15to18(qlrzjhA), CardNumberTransformation.idCard15to18(qlrzjhB));
    }
}
