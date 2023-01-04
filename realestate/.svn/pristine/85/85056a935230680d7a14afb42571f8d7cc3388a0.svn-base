package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.clients.OrganizationManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxmMapper;
import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxxMapper;
import cn.gtmap.realestate.accept.core.service.BdcLcTsjfGxService;
import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.accept.service.BdcSfService;
import cn.gtmap.realestate.accept.service.BdcYcslManageService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxCzrzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxmQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxWithDepartmentQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.accept.ui.*;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收费信息数据服务
 */
@Service
public class BdcSlSfxxServiceImpl implements BdcSlSfxxService {
    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcSlSfxxMapper bdcSlSfxxMapper;
    @Autowired
	private BdcXtJgFeignService bdcXtJgFeignService;
    @Autowired
    private BdcSlSfxmMapper bdcSlSfxmMapper;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlSfxmService bdcSlSfxmService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;
    @Autowired
    BdcLcTsjfGxService bdcLcTsjfGxService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    BdcSfService bdcSfService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    BdcYcslManageService bdcYcslManageService;
    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;
    /**
     * 接收到互联网推送的已缴税后需要办结的工作流定义ID
     **/
    @Value("#{'${ycsl.yjsbjgzldyid:}'.split(',')}")
    protected List<String> yjsbjgzldyidList;

    /**
     * 汇总收费统计时 收费标准名称和代码对照关系
     */
    @Value("${sfxx.hzsfxmbz:}")
    private String hzsfxmbz;

    /**
     * 缴费人为月结银行，转发时不验证收费状态
     */
    @Value("${sfxx.yjyhyzsfzt:true}")
    private boolean yjyhyzsfzt;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlSfxxServiceImpl.class);

    /**
     * @param sfxxid 收费信息ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID获取不动产受理收费信息
     */
    @Override
    public BdcSlSfxxDO queryBdcSlSfxxBySfxxid(String sfxxid) {
        if (StringUtils.isBlank(sfxxid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlSfxxDO.class, sfxxid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取不动产受理收费信息
     */
    @Override
    public List<BdcSlSfxxDO> listBdcSlSfxxByGzlslid(String gzlslid) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlSfxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("gzlslid", gzlslid);
            // 去除收费信息作废的数据
            criteria.andNotEqualNvlTo("sfzf", "1", "0");
            bdcSlSfxxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            bdcSlSfxxDOList = Collections.emptyList();
        }
        return bdcSlSfxxDOList;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取不动产受理收费信息（包含作废数据）
     */
    @Override
    public List<BdcSlSfxxDO> listBdcSlSfxxByGzlslidBhzf(String gzlslid) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlSfxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("gzlslid", gzlslid);
            bdcSlSfxxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            bdcSlSfxxDOList = Collections.emptyList();
        }
        return bdcSlSfxxDOList;
    }

    /**
     * 根据不动产收费信息DO获取不动产受理收费信息
     * @param bdcSlSfxxDO 收费信息DO
     * @return 不动产受理收费信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public List<BdcSlSfxxDO> queryBdcSlSfxx(BdcSlSfxxDO bdcSlSfxxDO) {
        Example example = new Example(BdcSlSfxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getGzlslid())) {
            criteria.andEqualTo("gzlslid", bdcSlSfxxDO.getGzlslid());
        }
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getXmid())) {
            criteria.andEqualTo("xmid", bdcSlSfxxDO.getXmid());
        }
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getQlrlb())) {
            criteria.andEqualTo("qlrlb", bdcSlSfxxDO.getQlrlb());
        }
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getSfxxid())) {
            criteria.andEqualTo("sfxxid", bdcSlSfxxDO.getSfxxid());
        }
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getJfsbm())) {
            criteria.andEqualTo("jfsbm", bdcSlSfxxDO.getJfsbm());
        }
        if (null != bdcSlSfxxDO.getSfzt()) {
            criteria.andEqualTo("sfzt", bdcSlSfxxDO.getSfzt());
        }
        if (bdcSlSfxxDO.getSfyj() != null) {
            criteria.andEqualNvlTo("sfyj", bdcSlSfxxDO.getSfyj(), CommonConstantUtils.SF_F_DM);
        }
        if (bdcSlSfxxDO.getHyzfddid() != null){
            criteria.andEqualTo("hyzfddid", bdcSlSfxxDO.getHyzfddid());
        }
        if (bdcSlSfxxDO.getJfsewmurl() != null){
            criteria.andEqualTo("jfsewmurl", bdcSlSfxxDO.getJfsewmurl());
        }
        criteria.andNotEqualNvlTo("sfzf", "1", "0");
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param pageable        bdcSlSfxxDOJSON
     * @param bdcSlSfxxQOJSON
     * @return Page<BdcSlSfxxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页查询受理收费信息
     */
    @Override
    public Page<BdcSlSfxxVO> listBdcSlSfxxByPage(Pageable pageable, String bdcSlSfxxQOJSON) {
        BdcSlSfxxWithDepartmentQO bdcSlSfxxQOWithDepartment = JSON.parseObject(bdcSlSfxxQOJSON, BdcSlSfxxWithDepartmentQO.class);
        BdcSlSfxxQO bdcSlSfxxQO = bdcSlSfxxQOWithDepartment.getData()!=null? bdcSlSfxxQOWithDepartment.getData() :new BdcSlSfxxQO();
    	if (StringUtils.isNotBlank(bdcSlSfxxQO.getJfyh())){
			List<String> jfrxmList = new ArrayList<>();
            LOGGER.info("查询银行缴费人list入参:{}",JSON.toJSONString(bdcSlSfxxQO));
            List<BdcXtJgDO> jfyhList = bdcXtJgFeignService.listAyjsBdcXtJgYhmcLike(bdcSlSfxxQO.getJfyh());
            LOGGER.info("查询银行缴费人出参:{}",JSON.toJSONString(jfyhList));
			if (CollectionUtils.isNotEmpty(jfyhList)){
				jfyhList.forEach(bdcXtJgDO -> {
					jfrxmList.add(bdcXtJgDO.getJgmc());
				});
			}
            bdcSlSfxxQOWithDepartment.getData().setJfrxmList(jfrxmList);
		}
        LOGGER.info("查询收费信息入参:{}",JSON.toJSONString(bdcSlSfxxQOWithDepartment));
        return repo.selectPaging("listBdcSlSfxxByPage", bdcSlSfxxQOWithDepartment, pageable);
    }

    /**
     * @param bdcSlSfxxQO
     * @return List<BdcSlSfxxVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询 收费信息
     */
    @Override
    public List<BdcSlSfxxVO> listBdcSlSfxx(BdcSlSfxxQO bdcSlSfxxQO) {
        if (bdcSlSfxxQO == null) {
            throw new AppException("查询收费信息的参数不能为空！");
        }
        List<BdcSlSfxxVO> bdcSlSfxxVOList = bdcSlSfxxMapper.listBdcSlSfxx(bdcSlSfxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxVOList)){
        	for (BdcSlSfxxVO bdcSlSfxxVO : bdcSlSfxxVOList){
        		List<BdcSlSfxmVO> bdcSlSfxmDOList = bdcSlSfxmMapper.listBdcSlSfxm(bdcSlSfxxVO.getSfxxid());
        		bdcSlSfxxVO.setBdcSlSfxmVOList(bdcSlSfxmDOList);
			}
		}
        return bdcSlSfxxVOList;
    }

    @Override
    public List<BdcSlSfxxDO> listBdcSlSfxxByGzlslidHjFk(String gzlslid) {
        Example example = new Example(BdcSlSfxxDO.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("gzlslid", gzlslid);
        criteria.andIsNotNull("hj");
        criteria.andNotEqualTo("hj", 0);
        // 去除收费信息作废的数据
        criteria.andNotEqualNvlTo("sfzf", "1", "0");
        return entityMapper.selectByExample(example);
    }

    /**
	 * @param bdcSlSfxxQO
	 * @return List<BdcSlSfxxVO>
	 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
	 * @description 查询 收费信息
	 */
	@Override
	public List<BdcSlSfxxVO> listBdcSlSfxxYh(BdcSlSfxxQO bdcSlSfxxQO) {
		if (bdcSlSfxxQO == null) {
			throw new AppException("查询收费信息的参数不能为空！");
		}
		// 获取缴费银行名称
		List<BdcXtJgDO> jfyhList = bdcXtJgFeignService.listAyjsBdcXtJgYhmcLike(bdcSlSfxxQO.getJfyh());
        if(CollectionUtils.isNotEmpty(jfyhList)){
            bdcSlSfxxQO.setJfrxmList(jfyhList.stream().map(BdcXtJgDO::getJgmc).collect(Collectors.toList()));
        }

		// 获取收费项目信息
        List<BdcSlSfxxVO> bdcSlSfxxVOList = bdcSlSfxxMapper.listBdcSlSfxxYh(bdcSlSfxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxVOList)){
            List<String> sfxxidList = bdcSlSfxxVOList.stream().map(BdcSlSfxxVO::getSfxxid).collect(Collectors.toList());
            BdcSlSfxmQO bdcSlSfxmQO = new BdcSlSfxmQO();
            bdcSlSfxmQO.setSfxxidList(sfxxidList);
            List<BdcSlSfxmVO> bdcSlSfxmDOList = bdcSlSfxmMapper.listBdcSlSfxmBysfxxidList(bdcSlSfxmQO);
            if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                Map<String, List<BdcSlSfxmVO>> sfxmMap = bdcSlSfxmDOList.stream().collect(Collectors.groupingBy(BdcSlSfxmVO::getSfxxid));
                for (BdcSlSfxxVO bdcSlSfxxVO : bdcSlSfxxVOList) {
                    List<BdcSlSfxmVO> bdcSlSfxmVOList = sfxmMap.get(bdcSlSfxxVO.getSfxxid());
                    if(CollectionUtils.isNotEmpty(bdcSlSfxmVOList)){
                        bdcSlSfxxVO.setBdcSlSfxmVOList(bdcSlSfxmVOList);
                    }
                }
            }
        }

        // 根据工作流ID查询所有项目，得到项目的坐落字段；
        if (CollectionUtils.isNotEmpty(bdcSlSfxxVOList)){
            List<String> slbhList = bdcSlSfxxVOList.stream().map(BdcSlSfxxVO::getSlbh).distinct().collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(slbhList)){
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setSlbhList(slbhList);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);//查询不动产项目所有字段信息;
                if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                    Map<String, List<BdcXmDO>> bdcXmListMap = bdcXmDOList.stream().collect(Collectors.groupingBy(BdcXmDO::getSlbh));
                    for(BdcSlSfxxVO bdcSlSfxxVO : bdcSlSfxxVOList){
                        List<BdcXmDO> xmxxList = bdcXmListMap.get(bdcSlSfxxVO.getSlbh());
                        if(CollectionUtils.isNotEmpty(xmxxList)){
                            //获得该工作流ID对应的不动产项目表中的所有坐落集合
                            Set<String> zlList = xmxxList.stream().map(BdcXmDO::getZl).distinct().collect(Collectors.toSet());
                            bdcSlSfxxVO.setZl(String.join(",", zlList));
                            bdcSlSfxxVO.setDjyy(xmxxList.get(0).getDjyy());
                            bdcSlSfxxVO.setYt(xmxxList.get(0).getDzwyt());
                            // 57974 【南通大市】月结银行导出新增不动产证明号需求
                            bdcSlSfxxVO.setBdcqzh(xmxxList.get(0).getBdcqzh());
                        }
                    }
                }
            }
        }
        return bdcSlSfxxVOList;
	}

	@Override
    public List<BdcSlSfxxDO> listBdcSlSfxxByXmid(String xmid) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlSfxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            // 去除收费信息作废的数据
            criteria.andNotEqualNvlTo("sfzf", "1", "0");
            bdcSlSfxxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            bdcSlSfxxDOList = Collections.emptyList();
        }
        return bdcSlSfxxDOList;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO>
     * @description 根据项目id获取不动产受理收费信息（包含作废数据）
     */
    @Override
    public List<BdcSlSfxxDO> listBdcSlSfxxByXmidBhzf(String xmid) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlSfxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            bdcSlSfxxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            bdcSlSfxxDOList = Collections.emptyList();
        }
        return bdcSlSfxxDOList;
    }

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理收费信息
     */
    @Override
    public BdcSlSfxxDO insertBdcSlSfxx(BdcSlSfxxDO bdcSlSfxxDO) {
        if (bdcSlSfxxDO != null) {
            if (StringUtils.isBlank(bdcSlSfxxDO.getSfxxid())) {
                bdcSlSfxxDO.setSfxxid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlSfxxDO);
        }
        return bdcSlSfxxDO;
    }

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理收费信息
     */
    @Override
    public Integer updateBdcSlSfxx(BdcSlSfxxDO bdcSlSfxxDO) {
        int result;
        if (bdcSlSfxxDO != null && StringUtils.isNotBlank(bdcSlSfxxDO.getSfxxid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlSfxxDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    @Override
    public void batchUpdateBdcSlSfxx(List<BdcSlSfxxDO> bdcSlSfxxDOList) {
        if(CollectionUtils.isEmpty(bdcSlSfxxDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产收费信息List");
        }
        this.entityMapper.batchSaveSelective(bdcSlSfxxDOList);
    }

    @Override
    public void batchUpdateBdcSlSfxxBySfxxidList(BdcSlSfxxQO bdcSlSfxxQO) {
        if(CollectionUtils.isEmpty(bdcSlSfxxQO.getSfxxidList())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产收费信息List");
        }
        this.bdcSlSfxxMapper.modifyBdcSlSfxxPl(bdcSlSfxxQO);
    }

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存或更新不动产受理收费信息
     */
    @Override
    public Integer saveOrUpdateBdcSlSfxx(BdcSlSfxxDO bdcSlSfxxDO) {
        int result;
        if (bdcSlSfxxDO != null) {
            if(StringUtils.isBlank(bdcSlSfxxDO.getSfxxid())){
                bdcSlSfxxDO.setSfxxid(UUIDGenerator.generate16());
            }
            result = entityMapper.saveOrUpdate(bdcSlSfxxDO,bdcSlSfxxDO.getSfxxid());

            //如果更新了状态则同步更新大云状态
            if(Objects.nonNull(bdcSlSfxxDO.getSfzt())) {
                bdcSlSfxxDO = entityMapper.selectByPrimaryKey(BdcSlSfxxDO.class, bdcSlSfxxDO.getSfxxid());
                LOGGER.info("不动产受理收费信息,更新大云回写字段:{}",bdcSlSfxxDO);
                //更新大云回写字段
                Map<String, Object> processInsExtendDto;
                List<Map<String, Object>> list = processInsCustomExtendClient.getProcessInsCustomExtend(bdcSlSfxxDO.getGzlslid());
                LOGGER.info("不动产受理收费信息,更新大云回写字段,当前数据查询:{}",list);
                if (CollectionUtils.isNotEmpty(list)) {
                    processInsExtendDto = list.get(0);
                    processInsExtendDto.put("JFZT", bdcSlSfxxDO.getSfzt());
                    LOGGER.info("不动产受理收费信息,更新大云回写字段,请求数据:{}",processInsExtendDto);
                    processInsCustomExtendClient.updateProcessInsCustomExtend(bdcSlSfxxDO.getGzlslid(), processInsExtendDto);
                }
            }
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;

    }

    @Override
    public void updateBdcSlSfxxIdWithSfxm(String sfxxid, BdcSlSfxxDO bdcSlSfxxDO) {
        if(StringUtils.isBlank(sfxxid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        if(Objects.isNull(bdcSlSfxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到更新信息");
        }
        // 更新收费信息ID
        Example example = new Example(BdcSlSfxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sfxxid", sfxxid);
        entityMapper.updateByExampleSelectiveNotNull(bdcSlSfxxDO, example);
        // 更新收费信息关联的收费项目的 sfxxid
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            for(BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList){
                bdcSlSfxmDO.setSfxxid(bdcSlSfxxDO.getSfxxid());
            }
        }
        this.entityMapper.batchSaveSelective(bdcSlSfxmDOList);
    }

    /**
     * @param param
     * @param value
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: parm 不动产受理收费信息参数
     * @param: value 不动产受理需要更新的值
     * @return: Integer
     * @description 根据不动产受理收费参数更新收费信息
     */
    @Override
    public void updateBdcSlSfxx(BdcSlSfxxDO param, BdcSlSfxxDO value) {
        if(param!=null && value!=null){
            Example example= new Example(BdcSlSfxxDO.class,false);
            Example.Criteria criteria = example.createCriteria();
            //用于接口只支持 gzlslid, sfxxid,xmid参数
            boolean containsParam = false;
            if(StringUtils.isNotBlank(param.getGzlslid())){
                containsParam = true;
                criteria.andEqualTo("gzlslid",param.getGzlslid());
            }
            if(StringUtils.isNotBlank(param.getSfxxid())){
                containsParam = true;
                criteria.andEqualTo("sfxxid",param.getSfxxid());
            }
            if(StringUtils.isNotBlank(param.getXmid())){
                containsParam = true;
                criteria.andEqualTo("xmid",param.getXmid());
            }
            if(!containsParam){
                throw new NullPointerException("修改受理收费信息，未传入条件参数。");
            }
            entityMapper.updateByExampleSelectiveNotNull(value,example);
        }
    }

    /**
     * @param sfxxid 收费信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID删除不动产受理收费信息
     */
    @Override
    public Integer deleteBdcSlSfxxBySfxxid(String sfxxid) {
        int result = 0;
        if (StringUtils.isNotBlank(sfxxid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlSfxxDO.class, sfxxid);
        }
        return result;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID删除不动产受理收费信息
     */
    @Override
    public Integer deleteBdcSlSfxxByGzlslid(String gzlslid) {
        int result = 0;
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlSfxxDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    @Override
    public Integer deleteBdcSlSfxxByGzlslidAndXmid(String gzlslid, String xmid) {
        int result = 0;
        if(StringUtils.isNotBlank(gzlslid)){
            Example example = new Example(BdcSlSfxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(gzlslid)) {
                criteria.andEqualTo("gzlslid", gzlslid);
            }
            if (StringUtils.isNotBlank(xmid)) {
                criteria.andEqualTo("xmid", xmid);
            }
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     * @param processInsId 工作流实例ID
     * @return 规则验证信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证流程的收费状态
     */
    @Override
    public BdcGzyzVO checkLcSfzt(String processInsId) {
        BdcGzyzVO bdcGzyzVO = null;
        List<BdcSlSfxxDO> bdcSlSfxxDOList = listBdcSlSfxxByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            // 未缴费人姓名
            List<String> wJfrxmList = new ArrayList<>();
            // 部分缴费人姓名
            List<String> bfJfrxmList = new ArrayList<>();
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                // 收费金额合计为0的情况下，无需缴费，允许办结
                if(null != bdcSlSfxxDO.getHj() && 0 == BigDecimal.ZERO.compareTo(BigDecimal.valueOf(bdcSlSfxxDO.getHj()))) {
                    continue;
                }
                //月结缴费流程不验证
                if(CommonConstantUtils.SF_S_DM.equals(bdcSlSfxxDO.getSfyj())){
                    continue;
                }
                //缴费人为月结银行不验证
                if(!yjyhyzsfzt){
                    List<BdcXtJgDO> bdcXtJgDOS = bdcXtJgFeignService.listAyjsBdcXtJgYhmcLike("");
                    List<String> ayjsyhList = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(bdcXtJgDOS)){
                        bdcXtJgDOS.forEach(bdcXtJgDO -> {
                            ayjsyhList.add(bdcXtJgDO.getJgmc());
                        });
                    }
                    if(ayjsyhList.contains(bdcSlSfxxDO.getJfrxm())){
                        continue;
                    }
                }
                if (null == bdcSlSfxxDO.getSfzt() || BdcSfztEnum.WJF.key().equals(bdcSlSfxxDO.getSfzt())) {
                    // 收费状态为空或者未缴费，均为未缴费
                    wJfrxmList.add(bdcSlSfxxDO.getJfrxm());
                } else if (BdcSfztEnum.BFJF.key().equals(bdcSlSfxxDO.getSfzt())) {
                    bfJfrxmList.add(bdcSlSfxxDO.getJfrxm());
                }
            }

            String tsxx = "";
            if (CollectionUtils.isNotEmpty(wJfrxmList)) {
                tsxx = tsxx.concat(StringToolUtils.join(wJfrxmList, CommonConstantUtils.ZF_YW_FH)).concat("未缴费。");
            }
            if (CollectionUtils.isNotEmpty(bfJfrxmList)) {
                tsxx = tsxx.concat(StringToolUtils.join(bfJfrxmList, CommonConstantUtils.ZF_YW_FH)).concat("部分缴费");
            }
            if (StringUtils.isNotBlank(tsxx)) {
                bdcGzyzVO = new BdcGzyzVO();
                bdcGzyzVO.setYzlx(CommonConstantUtils.YZLX_ALERT);
                bdcGzyzVO.setTsxx(tsxx);
            }
        }
        return bdcGzyzVO;
    }

    /**
     * 根据xmid分组查询项目的收费金额（权利人、义务人收费金额求和）
     *
     * @param xmids 项目ID集合
     * @return map key: xmid  value: 收费合计金额
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public Map<String,Object> queryHjGroupByXmids(List<String> xmids) {
        if(CollectionUtils.isEmpty(xmids)){
            throw new MissingArgumentException("未获取到项目ID信息。");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxMapper.queryHjGroupByXmids(xmids);
        if(CollectionUtils.isEmpty(bdcSlSfxxDOList)){
            return MapUtils.EMPTY_MAP;
        }
        Map<String,Object> resultMap = new HashMap<>(bdcSlSfxxDOList.size());
        for (BdcSlSfxxDO bdcSlSfxxDO: bdcSlSfxxDOList){
            resultMap.put(bdcSlSfxxDO.getXmid(), bdcSlSfxxDO.getHj());
        }
        return resultMap;
    }

    @Override
    public List<BdcSlSfxxDO> listBdcSlSfxxPl(String xmid, String sqrlb,List<String> sfxxidList,Integer sfyj,boolean hjfk){
        if(CollectionUtils.isNotEmpty(sfxxidList) ||StringUtils.isNotBlank(xmid)) {
            return bdcSlSfxxMapper.listBdcSlSfxxPl(xmid, sqrlb, sfxxidList,sfyj,hjfk);
        }else{
            return Collections.emptyList();
        }

    }

    @Override
    public void modifyBdcSlSfxxSfztPl(List<String> sfxxIdList, Integer sfzt) {
        if(CollectionUtils.isNotEmpty(sfxxIdList)){
            if(sfxxIdList.size()>500){
                List<List> subList = ListUtils.subList(sfxxIdList, 500);
                for (List data : subList) {
                    List copyList = new ArrayList(data);
                    this.bdcSlSfxxMapper.modifyBdcSlSfxxSfztPl(sfzt,copyList);
                }
            }else{
                this.bdcSlSfxxMapper.modifyBdcSlSfxxSfztPl(sfzt, sfxxIdList);
            }
        }
    }

    /**
     * 提供自助查询机查询收费信息接口（盐城）
     *
     * @param zzcxjSfxxDTO 收费信息查询接口
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    public List<BdcSlSfxxZzcxjResponseDTO> zzcxjQuerySfxx(ZzcxjSfxxDTO zzcxjSfxxDTO) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();

        if (null == zzcxjSfxxDTO) {
            throw new AppException("缺失自助查询查询实体！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        List<BdcXmDO> listxm = null;
        // 受理编号优先级最高 如果有slbh 则优先使用slbh查询
        if (StringUtils.isNotBlank(zzcxjSfxxDTO.getSlbh())) {
            bdcXmQO.setSlbh(zzcxjSfxxDTO.getSlbh());
            // 受理编号只查登记系统的
            bdcXmQO.setSply(CommonConstantUtils.SPLY_DJXT);
            listxm = bdcXmFeignService.listBdcXm(bdcXmQO);

            if(CollectionUtils.isNotEmpty(listxm)){
                List<BdcXmDO> bdcXmWithOutDya = listxm.stream().filter(bdcXmDO -> !CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(bdcXmWithOutDya)){
                    bdcXmWithOutDya.forEach(bdcXmDO -> {
                        List<BdcSlSfxxDO> listSfxx = listBdcSlSfxxByXmid(bdcXmDO.getXmid());
                        if(CollectionUtils.isNotEmpty(listSfxx)){
                            bdcSlSfxxDOList.addAll(listSfxx);
                        }
                    });
                }else{
                    LOGGER.info("根据slbh和sply未查询到登记系统的流程:{}",zzcxjSfxxDTO.getSlbh());
                    return Collections.emptyList();
                }
            }
        // 当slbh没有值的时候，则使用权利人和证件号得查询模式 权利人和证件号则查询外网的数据 且是未缴费的
        }else if(StringUtils.isNotBlank(zzcxjSfxxDTO.getQlrmc()) && StringUtils.isNotBlank(zzcxjSfxxDTO.getQlrzjh())){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrmc(zzcxjSfxxDTO.getQlrmc());
            bdcQlrQO.setZjh(zzcxjSfxxDTO.getQlrzjh());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)){
                for(BdcQlrDO bdcQlrDO : bdcQlrDOList){
                    String xmid = bdcQlrDO.getXmid();
                    bdcXmQO.setSlbh(null);
                    bdcXmQO.setSply(null);
                    bdcXmQO.setXmid(xmid);
                    listxm = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if(CollectionUtils.isNotEmpty(listxm)){
                        BdcXmDO bdcXmDO = listxm.get(0);
                        if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())){
                            continue;
                        }
                        if(!CommonConstantUtils.SPLY_DJXT.equals(bdcXmDO.getSply())){
                            BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                            bdcSlSfxxDO.setXmid(bdcXmDO.getXmid());
                            bdcSlSfxxDO.setSfzt(CommonConstantUtils.SFZT_WJF);
                            List<BdcSlSfxxDO> listSfxx = this.queryBdcSlSfxx(bdcSlSfxxDO);
                            if(CollectionUtils.isNotEmpty(listSfxx)){
                                bdcSlSfxxDOList.addAll(listSfxx);
                            }
                        }
                    }
                }
            }else {
                LOGGER.info("根据zjh和qlrmc未查询到权利人信息");
                return Collections.emptyList();
            }
        } else {
            throw new AppException(" 提供自助查询机查询收费信息接口,缺失查询条件");
        }

        // 封装接口返参数据结构
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            return dealResponseDto(bdcSlSfxxDOList,zzcxjSfxxDTO);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 返回的参数需要重新组织
     * @param resultList
     */
    public List dealResponseDto(List<BdcSlSfxxDO> resultList,ZzcxjSfxxDTO zzcxjSfxxDTO){
        LOGGER.info("自主查询机开始处理数据结构----------");
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        int count  = 0;
        List<BdcSlSfxxZzcxjResponseDTO> bdcSlSfxxZzcxjResponseDTOList = new ArrayList();
        for(BdcSlSfxxDO BdcSlSfxxDO : resultList){
            BdcSlSfxxZzcxjResponseDTO bdcSlSfxxZzcxjResponseDTO = new BdcSlSfxxZzcxjResponseDTO();

            // TODO 这里可以在resultList参数里面再加入bdcXmDO 这里就可以不用再次查询了。因为上面查询数据的时候
            // TODO 已经查询过bdcXmDO了，这里再查询一次，重复操作且降低性能，后面如果再修改此处逻辑，可以考虑优化一下
            String xmid = BdcSlSfxxDO.getXmid();
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(list)){
                BdcXmDO bdcXmDO = list.get(0);
                String gzlslid = bdcXmDO.getGzlslid();

                // 查询fdqc表，获取实测面积和分摊土地面积
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
                if(bdcQl instanceof BdcFdcqDO){
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO)bdcQl;
                    bdcSlSfxxZzcxjResponseDTO.setSCJZMJ(bdcFdcqDO.getJzmj());
                    bdcSlSfxxZzcxjResponseDTO.setFTTDMJ(bdcFdcqDO.getFttdmj());
                }else{
                    LOGGER.info("未查询到fdcq信息，请注意。查询条件xmid:{}",xmid);
                }

                // 查询节点信息获取当前节点
                // TODO 批量流程可以判断是否已查询过，如果已查询过，则不需要再次查询，提高效率。考虑优化
                List<TaskData> processRunningTasks = processTaskClient.processRunningTasks(gzlslid);
                if (CollectionUtils.isNotEmpty(processRunningTasks)) {
                    String taskName = processRunningTasks.get(0).getTaskName();
                    bdcSlSfxxZzcxjResponseDTO.setJDMC(taskName);
                }else{
                    LOGGER.info("未查询到当前流程，请注意。查询条件gzlslid:{}",gzlslid);
                }

                bdcSlSfxxZzcxjResponseDTO.setBDCDYH(bdcXmDO.getBdcdyh());
                bdcSlSfxxZzcxjResponseDTO.setQLLX(bdcXmDO.getQllx() + "");
                bdcSlSfxxZzcxjResponseDTO.setQLR(bdcXmDO.getQlr());
                bdcSlSfxxZzcxjResponseDTO.setROWNUM_(count+1);

                // 受理时间
                String dateStr = DateFormatUtils.format(bdcXmDO.getSlsj(), "yyyy-MM-dd");
                bdcSlSfxxZzcxjResponseDTO.setSJSJ(dateStr);

                bdcSlSfxxZzcxjResponseDTO.setSLDMC(bdcXmDO.getGzljdmc());
                bdcSlSfxxZzcxjResponseDTO.setSLH(bdcXmDO.getSlbh());
                bdcSlSfxxZzcxjResponseDTO.setYT(bdcXmDO.getDzwyt()+"");
                String djxl = StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcXmDO.getDjxl()), zdMap.get("djxl"));
                bdcSlSfxxZzcxjResponseDTO.setSQLXMC(djxl);
                bdcSlSfxxZzcxjResponseDTO.setYWH(bdcXmDO.getXmid());
                bdcSlSfxxZzcxjResponseDTO.setYWR(bdcXmDO.getYwr());
                bdcSlSfxxZzcxjResponseDTO.setZL(bdcXmDO.getZl());

                String sfxxid = BdcSlSfxxDO.getSfxxid();
                List<BdcSlSfxmDO> listSfxm = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
                List<BdcSfxmxxDTO> listSfxmDTO = new ArrayList<>();
                boolean checkSfztflag = true;
                if(CollectionUtils.isNotEmpty(listSfxm)){
                    for(BdcSlSfxmDO bdcSlSfxmDO : listSfxm){

                        BdcSfxmxxDTO bdcSfxmxxDTO = new BdcSfxmxxDTO();
                        bdcSfxmxxDTO.setSFKMMC(bdcSlSfxmDO.getSfxmmc());

                        //盐城要求 已交费的不返回
                        if (zzcxjSfxxDTO.getSfzt() == null){
                            if (CommonConstantUtils.SFZT_YJF.equals(BdcSlSfxxDO.getSfzt())){
                                checkSfztflag = false;
                                continue;
                            }
                        }else {
                            if (!CommonConstantUtils.SFZT_YJF.equals(zzcxjSfxxDTO.getSfzt())){
                                checkSfztflag = false;
                                continue;
                            }
                        }

                        bdcSfxmxxDTO.setJFZT(BdcSlSfxxDO.getSfzt()!=null ? BdcSlSfxxDO.getSfzt().toString() : null);

                        bdcSfxmxxDTO.setSFBZ(bdcSlSfxmDO.getSfxmbz());
                        bdcSfxmxxDTO.setSFJS(bdcSlSfxmDO.getSfbl());
                        bdcSfxmxxDTO.setFKRQC(bdcXmDO.getQlr());
                        bdcSfxmxxDTO.setSFLX(bdcSlSfxmDO.getSflx());
                        bdcSfxmxxDTO.setSFXMID(bdcSlSfxmDO.getSfxmid());
                        bdcSfxmxxDTO.setSL(bdcSlSfxmDO.getSl());
                        bdcSfxmxxDTO.setSSJE(bdcSlSfxmDO.getSsje());
                        bdcSfxmxxDTO.setYSJE(bdcSlSfxmDO.getYsje());
                        listSfxmDTO.add(bdcSfxmxxDTO);
                    }
                }
                if (!checkSfztflag){
                    continue;
                }
                count++;
                bdcSlSfxxZzcxjResponseDTO.setSFXMXX(listSfxmDTO);
                bdcSlSfxxZzcxjResponseDTOList.add(bdcSlSfxxZzcxjResponseDTO);
            }
        }
        return bdcSlSfxxZzcxjResponseDTOList;
    }

    /**
     * @param bdcSlSfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 发票查询收费信息
     * @date : 2020/11/27 15:17
     */
    @Override
    public List<BdcSlSfxxDO> listFpcxSfxx(BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxMapper.listFphSfxx(bdcSlSfxxQO);
    }

    /**
     * @param bdcSlSfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 发票查询收费信息-批量查询返回收费信息和收费项目信息
     * @date : 2020/11/27 15:17
     */
    @Override
    public List<BdcSfxxDTO> listFpcxSfxxPl(BdcSlSfxxQO bdcSlSfxxQO) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxMapper.listFphSfxx(bdcSlSfxxQO);
        List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>(5);
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            //根据收费信息id去重
            bdcSlSfxxDOList = bdcSlSfxxDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcSlSfxxDO::getSfxxid))), ArrayList::new));
            for(BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
                BdcSlSfxmQO bdcSlSfxmQO = new BdcSlSfxmQO(bdcSlSfxxDO.getSfxxid(),bdcSlSfxxQO.getSfxmdm());
                List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmMapper.listSfxmBySfxmdm(bdcSlSfxmQO);
                bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
                bdcSfxxDTO.setBdcSlSfxmDOS(bdcSlSfxmDOList);
                bdcSfxxDTOList.add(bdcSfxxDTO);
            }
        }
        return bdcSfxxDTOList;
    }

    /**
     * 登记缴费管理
     *
     * @param pageable
     * @param bdcSlSfxxQOJson
     * @return
     */
    @Override
    public Page<BdcdjjfglVO> djjfgl(Pageable pageable, String bdcSlSfxxQOJson) {
        BdcSlSfxxQO bdcSlSfxxQO = null;
        if(StringUtils.isNotBlank(bdcSlSfxxQOJson)){
            bdcSlSfxxQO = JSONObject.parseObject(bdcSlSfxxQOJson, BdcSlSfxxQO.class);
        }
        if(null == bdcSlSfxxQO){
            throw new AppException("缺失查询条件实体");
        }
        bdcSlSfxxQO.setJfrxm(bdcSlSfxxQO.getQlrmc());

        // 页面的三个查询条件 筛选xm表的xmid和gzlslid
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(bdcSlSfxxQO.getSlbh());
        bdcXmQO.setZl(bdcSlSfxxQO.getZl());

        if(StringUtils.isNotBlank(bdcSlSfxxQO.getSlbh()) || StringUtils.isNotBlank(bdcSlSfxxQO.getZl())){
            List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(listXm)){
                List<String> xmids = new ArrayList<>();
                Set<String> gzlslids = new HashSet<>();
                for(BdcXmDO bdcXmDO : listXm){
                    xmids.add(bdcXmDO.getXmid());
                    gzlslids.add(bdcXmDO.getGzlslid());
                }
                if(gzlslids.size() > 1000){
                    LOGGER.info("查询条件匹配数据过多:{}，请输入更精确的查询条件",gzlslids.size());
                    return new PageImpl(new ArrayList(), pageable, 0);
                }
                // 先通过gzlslid查询收费信息
                bdcSlSfxxQO.setGzlslidList(new ArrayList<>(gzlslids));
            }else{
                return new PageImpl(new ArrayList(), pageable, 0);
            }
        }
        Page<BdcdjjfglVO> page = repo.selectPaging("listBdcDjjfglByPage", bdcSlSfxxQO, pageable);

        if(null != page){
            // 查询到sfxx之后，在处理权利人zl等登记信息
            dealDjxx(page);
        }
        return page;
    }

    /**
     * @param jfList 查询条件
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 缴费
     * @date : 2020/11/27 15:15
     */
    @Override
    public void jfcz(List<BdcdjjfglVO> jfList,String type) {
        if(CollectionUtils.isNotEmpty(jfList)){
            List<String> sfxxidList = new ArrayList<>();
            String userName = "";
            String userId = "";
            UserDto user = userManagerUtils.getCurrentUser();
            if(null != user){
                userName = user.getAlias();
                userId = user.getId();
            }
            Date czsj = new Date();
            // 根据现场要求，即使批量缴费，取消缴费日志记录要记录多条，不能批量记录日志
            for(BdcdjjfglVO  bdcdjjfglVO : jfList){
                sfxxidList.add(bdcdjjfglVO.getSfxxid());
                BdcSlSfxxCzrzDO bdcSlSfxxCzrzDO = new BdcSlSfxxCzrzDO();
                // 循环插入收费操作日志表
                BeanUtils.copyProperties(bdcdjjfglVO,bdcSlSfxxCzrzDO);
                bdcSlSfxxCzrzDO.setCzr(userName);
                bdcSlSfxxCzrzDO.setId(UUIDGenerator.generate16());

                if(StringUtils.equals("jf",type)){// 缴费
                    bdcSlSfxxCzrzDO.setSfsj(czsj);
                }else if(StringUtils.equals("qxjf",type)){// 取消缴费
                    bdcSlSfxxCzrzDO.setQxsfsj(czsj);
                    // 这个sfsj是作为修改缴费的收费时间，不能copy sfxx表里面的sfsj
                    bdcSlSfxxCzrzDO.setSfsj(null);
                }else{ // 修改缴费
                    // 修改缴费前台没有传输全部数据，需要重新查一下sfxx表，拿到要保存日志的数据
                    String sfxxid = bdcdjjfglVO.getSfxxid();
                    BdcSlSfxxDO bdcSlSfxxDO = queryBdcSlSfxxBySfxxid(sfxxid);
                    BeanUtils.copyProperties(bdcSlSfxxDO,bdcSlSfxxCzrzDO);
                    bdcSlSfxxCzrzDO.setXgjfr(userName);
                    bdcSlSfxxCzrzDO.setXgjfrid(userId);
                    bdcSlSfxxCzrzDO.setXgjfsj(czsj);
                    // 这个sfsj是作为修改缴费的收费时间，不能copy sfxx表里面的sfsj
                    bdcSlSfxxCzrzDO.setSfsj(null);
                }

                // 逐条插入日志表
                entityMapper.insertSelective(bdcSlSfxxCzrzDO);
            }
            // 批量更新收费状态
            if(CollectionUtils.isNotEmpty(sfxxidList)) {
                BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                Map<String, Object> map = new HashMap<>();
                map.put("sfxxidList",sfxxidList);
                bdcDjxxUpdateQO.setWhereMap(map);
                JSONObject obj =new JSONObject();
                String sfrxm = "";
                if (StringUtils.equals("jf", type)) {
                    UserDto userDto = userManagerUtils.getCurrentUser();
                    if (userDto != null) {
                        sfrxm = userDto.getAlias();
                    }
                    obj.put("sfzt",CommonConstantUtils.SFZT_YJF);
                    obj.put("sfsj",czsj);
                } else if (StringUtils.equals("qxjf", type)) {
                    obj.put("sfzt",CommonConstantUtils.SFZT_WJF);
                    obj.put("sfsj",null);
                }
                obj.put("sfrxm",sfrxm);
                bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj,SerializerFeature.WriteMapNullValue));
                updateBatchBdcSlSfxx(bdcDjxxUpdateQO);
            }

        }
    }

    /**
     * 处理登记数据 区分流程类型
     * @param page
     */
    public void dealDjxx(Page<BdcdjjfglVO> page){
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        List<BdcdjjfglVO> list = page.getContent();
        if(CollectionUtils.isNotEmpty(list)){
            for(BdcdjjfglVO bdcdjjfglVO : list){
                String gzlslid = bdcdjjfglVO.getGzlslid();
                int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
                List<BdcXmDO> listXm = null;
                BdcXmQO bdcXmQO = new BdcXmQO();
                // 当前流程类型是组合和批量组合的时候 查询登记的信息 用xmid 是单个和批量的时候 用gzlslid
                if(lclx == CommonConstantUtils.LCLX_ZH || lclx == CommonConstantUtils.LCLX_PLZH){
                    String xmid = bdcdjjfglVO.getXmid();
                    bdcXmQO.setXmid(xmid);
                    listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
                }else{
                    bdcXmQO.setGzlslid(gzlslid);
                    listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
                }
                if(CollectionUtils.isNotEmpty(listXm)){
                    String qlr = "";
                    String ywr = "";
                    String djxl = "";
                    String zl = "";
                    String bdcdyh = "";
                    // 批量流程拼接qlr ywr djxl zl
                    for(BdcXmDO bdcXmDO : listXm){
                        if(StringUtils.isNotBlank(bdcXmDO.getQlr()) && qlr.indexOf(bdcXmDO.getQlr()) == -1){
                            qlr += bdcXmDO.getQlr() + ",";
                        }
                        if(StringUtils.isNotBlank(bdcXmDO.getYwr()) && ywr.indexOf(bdcXmDO.getYwr()) == -1){
                            ywr += bdcXmDO.getYwr() + ",";
                        }
                        if(StringUtils.isNotBlank(bdcXmDO.getDjxl()) && djxl.indexOf(bdcXmDO.getDjxl()) == -1){
                            String djxlmc = StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcXmDO.getDjxl()), zdMap.get("djxl"));
                            djxl += djxlmc + ",";
                        }
                        if(StringUtils.isNotBlank(bdcXmDO.getZl()) && zl.indexOf(bdcXmDO.getZl()) == -1){
                            zl += bdcXmDO.getZl() + ",";
                        }
                        if(StringUtils.isNotBlank(bdcXmDO.getBdcdyh()) && bdcdyh.indexOf(bdcXmDO.getBdcdyh()) == -1){
                            bdcdyh += bdcXmDO.getBdcdyh() + ",";
                        }
                        bdcdjjfglVO.setSlbh(bdcXmDO.getSlbh());
                        bdcdjjfglVO.setXmly(bdcXmDO.getXmly());
                    }

                    if(qlr.length() > 0){
                        qlr = qlr.substring(0,qlr.length() - 1);
                        bdcdjjfglVO.setQlr(qlr);
                    }
                    if(zl.length() > 0){
                        zl = zl.substring(0,zl.length() - 1);
                        bdcdjjfglVO.setZl(zl);
                    }
                    if(djxl.length() > 0){
                        djxl = djxl.substring(0,djxl.length() - 1);
                        bdcdjjfglVO.setDjxl(djxl);
                    }
                    if(ywr.length() > 0){
                        ywr = ywr.substring(0,ywr.length() - 1);
                        bdcdjjfglVO.setYwr(ywr);
                    }
                    if(bdcdyh.length() > 0){
                        bdcdyh = bdcdyh.substring(0,bdcdyh.length() - 1);
                        bdcdjjfglVO.setBdcdyh(bdcdyh);
                    }
                    if(bdcdjjfglVO.getSfzt() != null){
                        String sfztmc = StringToolUtils.convertBeanPropertyValueOfZd(bdcdjjfglVO.getSfzt(), zdMap.get("sfzt"));
                        bdcdjjfglVO.setSfztmc(sfztmc);
                    }
                }
            }
        }
    }

    /**
     * 非税开票
     *
     * @param pageable
     * @param gzlslid
     * @return
     */
    @Override
    public Page<BdcSlSfxxDO> fskp(Pageable pageable, String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("缺失查询参数");
        }
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        bdcSlSfxxQO.setGzlslid(gzlslid);
        return repo.selectPaging("listBdcFskpByPage", bdcSlSfxxQO, pageable);
    }

    @Override
    public List<BdcSlSfxxDO> queryYjfAndWjkSfxx() {
        return this.bdcSlSfxxMapper.listYjfAndWjkSfxx();
    }

    /**
     * （南通）汇总缴费数据
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return {BdcSfxxhzVO} 汇总缴费信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public BdcSfxxhzVO hzjfsj(BdcSlSfxxQO bdcSlSfxxQO) {
        // 查询条件为：1. 开始时间； 2截止时间； 3. 线上缴费为是 （SQL里面写死，此处不判断）
        if(null == bdcSlSfxxQO || StringUtils.isAnyBlank(bdcSlSfxxQO.getKssj(), bdcSlSfxxQO.getJzsj())) {
            throw new MissingArgumentException("汇总缴费数据异常：参数为空或未定义开始结束时间参数");
        }

        if(StringUtils.isBlank(hzsfxmbz)) {
            throw new AppException("汇总缴费数据异常：未定义收费标准名称和代码对照关系");
        }

        // 要统计的收费内容和收费代码对照关系
        Map<String, String> sfxmMap = JSON.parseObject(hzsfxmbz, Map.class);
        String bdcdjf = sfxmMap.get("不动产登记费");
        String tdsyqjyfwf = sfxmMap.get("土地使用权交易服务费");
        if(StringUtils.isAnyBlank(bdcdjf, tdsyqjyfwf)) {
            throw new AppException("汇总缴费数据异常：未定义收费标准名称和代码对照关系");
        }

        // 查询汇总信息
        Map<String, String> param = new HashMap<>();
        param.put("kssj", bdcSlSfxxQO.getKssj());
        param.put("jzsj", bdcSlSfxxQO.getJzsj());
        param.put("bdcdjf", bdcdjf);
        param.put("tdsyqjyfwf", tdsyqjyfwf);
        param.put("sfdm", bdcdjf + "," + tdsyqjyfwf);
        param.put("djbmdmList", StringUtils.join(bdcSlSfxxQO.getDjbmdmList(), CommonConstantUtils.ZF_YW_DH));
        List<BdcSfxxhzDTO> shxxhz = bdcSlSfxxMapper.hzjfsj(param);
        if(CollectionUtils.isEmpty(shxxhz)) {
            return null;
        }

        // 整理导出Excel数据
        /// 获取当前选择时间范围内有缴费记录的时间
        List<String> sfsjList = shxxhz.stream().map(BdcSfxxhzDTO::getSfsj).distinct().collect(Collectors.toList());
        /// 统计所有部门，没有的金额为0
        Map<String, List<String>> orgInfo = userManagerUtils.getAllOrgInfo();
        // 根据已选的部门代码，过滤部门信息
        orgInfo = this.filterOrgInfo(bdcSlSfxxQO.getDjbmdmList(), orgInfo);
        if(MapUtils.isEmpty(orgInfo)) {
            throw new AppException("汇总缴费数据异常：未获取到机构信息");
        }

        /// 按照 费用明细：对应汇总金额记录 组织数据
        Map<String, List<BdcSfxxhzDTO>> fymxMap = shxxhz.stream().collect(Collectors.groupingBy(BdcSfxxhzDTO::getFymx));
        /// 匹配每天、每个部门、每个收费项目对应的缴费金额
        List<LinkedHashMap<String, Object>> data = new ArrayList<>();
        /// 记录缴费时间
        List<String> jfsjList = new ArrayList<>();

        for(int i = 0; i < sfsjList.size(); i++) {
            // 每天的统计信息对应一行记录
            LinkedHashMap<String, Object> dataItem = new LinkedHashMap<>();
            dataItem.put("xh", i + 1);
            dataItem.put("sfsj", sfsjList.get(i));
            jfsjList.add(sfsjList.get(i));

            for (String orgCode : orgInfo.get("orgCode")) {
                for(String sfbz : CommonConstantUtils.JFHZFYMC.split(",")) {
                    String key = sfsjList.get(i) + "_" + orgCode + "_" + sfbz;

                    String sfbzCode = CommonConstantUtils.JFHZFYMC_A.equals(sfbz) ? CommonConstantUtils.JFHZFYDM_A : CommonConstantUtils.JFHZFYDM_B;
                    List<BdcSfxxhzDTO> list = fymxMap.get(key);
                    if(CollectionUtils.isEmpty(list) || null == list.get(0)) {
                        dataItem.put(orgCode + "_" + sfbzCode, "¥0");
                    } else {
                        dataItem.put(orgCode + "_" + sfbzCode, "¥" + list.get(0).getSsje());
                    }
                }
            }
            data.add(dataItem);
        }

        BdcSfxxhzVO bdcSfxxhzVO = new BdcSfxxhzVO();
        bdcSfxxhzVO.setJfsj(jfsjList);
        bdcSfxxhzVO.setOrgName(orgInfo.get("orgName"));
        bdcSfxxhzVO.setOrgCode(orgInfo.get("orgCode"));
        bdcSfxxhzVO.setData(data);
        return bdcSfxxhzVO;
    }

    /**
     * 根据已选的部门，过滤部门信息
     */
    private Map<String, List<String>> filterOrgInfo(List<String> djbmdmList,  Map<String, List<String>> orgInfoMap){
        if(CollectionUtils.isEmpty(djbmdmList) || MapUtils.isEmpty(orgInfoMap)){
            return orgInfoMap;
        }
        List<String> newOrgCodeList = new ArrayList(djbmdmList.size());
        List<String> newOrgNameList = new ArrayList(djbmdmList.size());

        List<String> oldOrgCodeList = orgInfoMap.get("orgCode");
        List<String> oldOrgNameList = orgInfoMap.get("orgName");
        for(int i = 0 , j = oldOrgCodeList.size(); i < j ; i++){
            if(djbmdmList.contains(oldOrgCodeList.get(i))){
                newOrgCodeList.add(oldOrgCodeList.get(i));
                newOrgNameList.add(oldOrgNameList.get(i));
            }
        }
        if(CollectionUtils.isNotEmpty(newOrgCodeList) && CollectionUtils.isNotEmpty(newOrgNameList)){
            orgInfoMap.put("orgCode", newOrgCodeList);
            orgInfoMap.put("orgName", newOrgNameList);
        }
        return orgInfoMap;
    }

    /**
     * 查询缴费数据明细
     *
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return {BdcSfxxmxVO} 缴费数据明细
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public BdcSfxxmxVO mxjfsj(BdcSlSfxxQO bdcSlSfxxQO) {
        // 查询条件为：1. 部门； 2. 开始时间； 3. 截止时间； 4. 线上缴费为是 （SQL里面写死，此处不判断）
        if(null == bdcSlSfxxQO || CollectionUtils.isEmpty(bdcSlSfxxQO.getDjbmdmList()) || StringUtils.isAnyBlank(bdcSlSfxxQO.getKssj(), bdcSlSfxxQO.getJzsj())) {
            throw new MissingArgumentException("缴费数据明细异常：参数为空或未定义部门、开始、结束时间参数");
        }

        if(StringUtils.isBlank(hzsfxmbz)) {
            throw new AppException("缴费数据明细异常：未定义收费标准名称和代码对照关系");
        }

        // 要统计的收费内容和收费代码对照关系
        Map<String, String> sfxmMap = JSON.parseObject(hzsfxmbz, Map.class);
        String bdcdjf = sfxmMap.get("不动产登记费");
        String tdsyqjyfwf = sfxmMap.get("土地使用权交易服务费");
        if(StringUtils.isAnyBlank(bdcdjf, tdsyqjyfwf)) {
            throw new AppException("缴费数据明细异常：未定义收费标准名称和代码对照关系");
        }

        // 查询汇总信息
        Map<String, String> param = new HashMap<>();
        param.put("kssj", bdcSlSfxxQO.getKssj());
        param.put("jzsj", bdcSlSfxxQO.getJzsj());
        param.put("bdcdjf", bdcdjf);
        param.put("tdsyqjyfwf", tdsyqjyfwf);
        param.put("sfdm", bdcdjf + "," + tdsyqjyfwf);
        param.put("bmdm", StringUtils.join(bdcSlSfxxQO.getDjbmdmList().toArray(), ","));

        BdcSfxxmxVO bdcSfxxmxVO = new BdcSfxxmxVO();
        if("MXZS".equals(bdcSlSfxxQO.getCxlx())) {
            int count = bdcSlSfxxMapper.countMxjfsj(param);
            bdcSfxxmxVO.setCount(count);
        } else {
            List<BdcSfxxmxDTO> sfxxmxList = bdcSlSfxxMapper.mxjfsj(param);

            // 匹配下字典项（因为字典表跨库，没有SQL直接处理）
            if(CollectionUtils.isNotEmpty(sfxxmxList)) {
                List<Map> sfztZdMap = bdcZdFeignService.queryBdcZd("sfzt");
                List<OrganizationDto> organizationDtos = organizationManagerClient.listOrgs(1);
                if(CollectionUtils.isNotEmpty(organizationDtos)){
                    Map<String, OrganizationDto> organizationDtoMap = organizationDtos
                            .stream()
                            .collect(Collectors.toMap(OrganizationDto::getCode, o -> o,(old,newone)->newone));

                    for(BdcSfxxmxDTO sfxxmxDTO : sfxxmxList) {
                        if(organizationDtoMap.containsKey(sfxxmxDTO.getSfdwmc())){
                            sfxxmxDTO.setSfdwmc(organizationDtoMap.get(sfxxmxDTO.getSfdwmc()).getName());
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(sfztZdMap)) {
                    Map<String, String> zdMap = this.zdMap(sfztZdMap);
                    int index = 1;
                    for(BdcSfxxmxDTO sfxxmxDTO : sfxxmxList) {
                        sfxxmxDTO.setXh(String.valueOf(index++));
                        sfxxmxDTO.setSfztmc(zdMap.get(sfxxmxDTO.getSfzt()));
                    }
                }
            }
            bdcSfxxmxVO.setSfxxmxList(sfxxmxList);
        }
        return bdcSfxxmxVO;
    }

    @Override
    public List<BdcSlSfxxDO> queryNotJkrkSfxx(BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxMapper.listNotJkrkSfxx(bdcSlSfxxQO);
    }

    /**
     * @param bdcSlSfxxCzrzDO 不动产受理收费操作人信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 新增不动产受理收费操作人信息
     */
    @Override
    public BdcSlSfxxCzrzDO insertBdcSlSfxxCzrz(BdcSlSfxxCzrzDO bdcSlSfxxCzrzDO) {
        if (bdcSlSfxxCzrzDO != null) {
            if (StringUtils.isBlank(bdcSlSfxxCzrzDO.getId())){
                bdcSlSfxxCzrzDO.setId(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlSfxxCzrzDO);
        }
        return bdcSlSfxxCzrzDO;
    }

    private Map<String, String> zdMap(List<Map> sfztZdMapList) {
        Map<String, String> zdMap = new HashMap<>();
        for(Map sfztZd : sfztZdMapList) {
            zdMap.put(String.valueOf(sfztZd.get("DM")), String.valueOf(sfztZd.get("MC")));
        }
        return zdMap;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理收费信息
     */
    private int updateBatchBdcSlSfxx(BdcDjxxUpdateQO bdcDjxxUpdateQO) {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
        }
        String jsonStr = bdcDjxxUpdateQO.getJsonStr();
        //获取更新json对象
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        Map map = new HashMap();
        //获取批量更新语句
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcSlSfxxDO.class.getName());
        if(!statement.contains("SET")) {
            return 0;
        }
        map.put("statement", statement);
        //where 条件
        map.putAll(bdcDjxxUpdateQO.getWhereMap());
        //获取实体对象
        BdcSlSfxxDO bdcSlSfxxDO = JSON.parseObject(jsonStr, BdcSlSfxxDO.class);
        map.put("record", bdcSlSfxxDO);
        return bdcSlSfxxMapper.updateBatchBdcSlSfxx(map);
    }

    /**
     * 更新收费信息表中的登簿时间
     * @param processInsId 工作流实例ID
     */
    @Override
    public void modifySlSfxxDbsj(String processInsId) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.listBdcSlSfxxByGzlslid(processInsId);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList) && CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
            Date dbsj = bdcXmDOList.get(0).getDjsj();
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                if(Objects.isNull(bdcSlSfxxDO.getDbsj())){
                    if(Objects.nonNull(dbsj)){
                        bdcSlSfxxDO.setDbsj(dbsj);
                    }else{
                        bdcSlSfxxDO.setDbsj(new Date());
                    }
                    this.updateBdcSlSfxx(bdcSlSfxxDO);
                }
            }
        }
    }

    /**
     * @param pageable
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据相关查询条件查询推送收费信息管理台账数据
     * @date : 2021/9/13 14:36
     */
    @Override
    public Page<BdcdjjfglVO> listTssfByPage(Pageable pageable, String json) {
        //解析json
        BdcSlSfxxQO bdcSlSfxxQO = JSON.parseObject(json, BdcSlSfxxQO.class);
        return repo.selectPaging("listBdcSfxxByPage", bdcSlSfxxQO, pageable);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询需要推送的信息
     * @date : 2021/9/15 8:48
     */
    @Override
    public List<BdcdjjfglVO> listTssfxx(BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxMapper.listBdcSfxxByPage(bdcSlSfxxQO);
    }

    /**
     * @param pageable
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据相关查询条件查询未缴费数据 fph 不为空，缴费书编码不为空且未缴费的数据
     * @date : 2021/9/13 14:36
     */
    @Override
    public Page<BdcSlWjfDTO> listWjftzByPage(Pageable pageable, String json) {
        BdcSlSfxxQO bdcSlSfxxQO = JSON.parseObject(json, BdcSlSfxxQO.class);
        Page<BdcSlWjfDTO> listWjfTzByPage = repo.selectPaging("listWjfTzByPage", bdcSlSfxxQO, pageable);
        return listWjfTzByPage;
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/11/4 23:15
     */
    @Override
    public Map countWjfhj(String json) {
        BdcSlSfxxQO bdcSlSfxxQO = JSON.parseObject(json, BdcSlSfxxQO.class);
        return bdcSlSfxxMapper.countWjfhj(bdcSlSfxxQO);
    }

    /**
     * @param json@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/11/5 11:19
     */
    @Override
    public List<BdcSlWjfDTO> listWjfxxByPage(String json) {
        BdcSlSfxxQO bdcSlSfxxQO = JSON.parseObject(json, BdcSlSfxxQO.class);
        return bdcSlSfxxMapper.listWjfTzByPage(bdcSlSfxxQO);
    }

    @Override
    public List<BdcdjjfglVO> listPlTsJfxx(BdcSlSfxxQO bdcSlSfxxQO) {
        if(Objects.isNull(bdcSlSfxxQO)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失查询参数");
        }
        LOGGER.info("批量缴费查询参数：{}", JSON.toJSONString(bdcSlSfxxQO));
        return bdcSlSfxxMapper.listPlTsJfxx(bdcSlSfxxQO);
    }

    @Override
    public List<String> listWjfXmidByQlrxx(BdcQlrQO bdcQlrQO) {
        if(Objects.isNull(bdcQlrQO)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失查询参数");
        }
        return bdcSlSfxxMapper.listWjfXmidByQlrxx(bdcQlrQO);
    }

    @Override
    public void yjyhUpdateSfztAndSfsj(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数，工作流实例ID");
        }
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setGzlslid(gzlslid);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.queryBdcSlSfxx(bdcSlSfxxDO);
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
            Map<String, List<BdcSlSfxxDO>> resultMap = bdcSlSfxxDOList.stream().collect(Collectors.groupingBy(BdcSlSfxxDO::getJfrxm));
            resultMap.forEach((k, v) ->{
                if(StringUtils.isNotBlank(k) && CollectionUtils.isNotEmpty(v)){
                    List<BdcXtJgDO> yjyhList =  this.bdcXtJgFeignService.listAyjsBdcXtJgYhmc(k);
                    if(CollectionUtils.isNotEmpty(yjyhList)){
                        v.forEach(sfxx ->{
                            sfxx.setSfzt(BdcSfztEnum.YJF.key());
                            sfxx.setSfsj(new Date());
                            sfxx.setSfztczsj(new Date());
                        });
                        this.batchUpdateBdcSlSfxx(v);
                    }
                }
            });
        }
    }

    /**
     * @param bdcSlSfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号查询收费信息
     * @date : 2022/8/1 10:18
     */
    @Override
    public List<BdcSlSfxxDO> listBdcSlSfxxBySlbh(BdcSlSfxxQO bdcSlSfxxQO) {
        //以下参数至少一个存在值
        if (CheckParameter.checkAnyParameter(bdcSlSfxxQO, "sfxxid", "slbh",
                "qlrlb", "jfsbm", "xmid", "gzlslid", "gzlslidList")) {
            return bdcSlSfxxMapper.listSfxxByQo(bdcSlSfxxQO);
        }
        return Collections.emptyList();
    }

    @Override
    public Integer updateBdcSlSfxxAndTsdjlc(BdcSlSfxxDO bdcSlSfxxDO) {
        Integer result = this.updateBdcSlSfxx(bdcSlSfxxDO);
        if (result > 0 && bdcSlSfxxDO.getSfzt().equals(CommonConstantUtils.SFZT_YJF)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(bdcSlSfxxDO.getGzlslid());
            if (bdcSlJbxxDO != null && CollectionUtils.isNotEmpty(yjsbjgzldyidList) && yjsbjgzldyidList.contains(bdcSlJbxxDO.getGzldyid())) {
                    String jbxxid = bdcSlJbxxDO.getJbxxid();
                    String slbh = bdcSlJbxxDO.getSlbh();
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setSlbh(slbh);
                    List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isEmpty(listXm)) {
                        BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO = new BdcTsDjxxRequestDTO();
                        bdcTsDjxxRequestDTO.setGzlslid(bdcSlSfxxDO.getGzlslid());
                        bdcTsDjxxRequestDTO.setJbxxid(jbxxid);
                        BdcYcslPzDTO bdcYcslPzDTO = new BdcYcslPzDTO();
                        bdcYcslPzDTO.setAutoComplete(true);
                        bdcYcslPzDTO.setGyslbh(true);
                        bdcYcslPzDTO.setAutoTurn(false);
                        bdcTsDjxxRequestDTO.setBdcYcslPzDTO(bdcYcslPzDTO);
                        try {
                            bdcYcslManageService.initTsBdcDjxx(bdcTsDjxxRequestDTO);
                        } catch (Exception e) {
                            LOGGER.error("登记流程创建失败，收费xxid为：{},jbxxid:{},slbh:{},错误信息:{}", bdcSlSfxxDO.getSfxxid(), jbxxid, slbh, e.getMessage());
                        }
                    }
            }
        }
        return result;
    }

    @Override
    public List<BdcSfxxDTO> queryBdcSlSfxxAndSfxm(BdcSlSfxxQO bdcSlSfxxQO) {
        // 查询收费信息
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxMapper.listBdcSlSfxxDO(bdcSlSfxxQO);
        List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>(5);
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            //根据收费信息id去重
            bdcSlSfxxDOList = bdcSlSfxxDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcSlSfxxDO::getSfxxid))), ArrayList::new));
            for(BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
                BdcSlSfxmQO bdcSlSfxmQO = new BdcSlSfxmQO(bdcSlSfxxDO.getSfxxid(),bdcSlSfxxQO.getSfxmdm());
                List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmMapper.listSfxmBySfxmdm(bdcSlSfxmQO);
                bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
                bdcSfxxDTO.setBdcSlSfxmDOS(bdcSlSfxmDOList);
                bdcSfxxDTOList.add(bdcSfxxDTO);
            }
        }
        return bdcSfxxDTOList;
    }
}
