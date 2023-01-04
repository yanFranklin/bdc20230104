package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.msg.client.MessageClient;
import cn.gtmap.gtc.msg.domain.dto.ProduceMsgDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInstanceClient;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.domain.exchange.yth.QltQlXzxzDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryParamDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcDeleteYwxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYthYrcfFwxx;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcDsQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcYthYrcfQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYjxxFeginService;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NaturalResourcesFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.convert.YthConvert;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.*;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.*;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.request.YthYjxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.respones.YthCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.respones.YthQueryDzzzxxResponse;
import cn.gtmap.realestate.exchange.core.mapper.server.*;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.YthInitParamService;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.YthInitParamServiceChoose;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.FjclService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.GxWwSqxxService;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcShijiService;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcYthService;
import cn.gtmap.realestate.exchange.service.national.BdcExchangeZddzService;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2020-12-01
 * @description (盐城) 一体化相关服务处理
 */
@Service
public class BdcYthServiceImpl implements BdcYthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYthServiceImpl.class);

    private static final String YANCHENG_YTH_CANCEL_RESON = "盐城一体化系统删除";

    private static final String JSZT_YJS = "1";
    /**
     * 业务类型： 产权
     */
    private static final String JKYWLX_CQ = "1";
    /**
     * 业务类型： 抵押
     */
    private static final String JKYWLX_DYAQ = "2";

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;

    @Autowired
    private BdcInitFeignService bdcInitFeignService;

    @Autowired
    private YthInitParamServiceChoose ythInitParamServiceChoose;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private BdcExchangeZddzService bdcExchangeZddzService;

    @Autowired
    private AccessDefaultValueService accessDefaultValueService;

    @Autowired
    private TaskUtils taskUtils;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Autowired
    private FjclService fjclService;

    @Autowired
    private DozerBeanMapper exchangeDozerMapper;

    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    BdcSlFeignService bdcSlFeignService;

    @Autowired
    private GxWwSqxxService gxWwSqxxService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private BdcYthyrcfMapper bdcYthyrcfMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private KttZdjbxxMapper kttZdjbxxMapper;

    @Autowired
    private KttFwZrzMapper kttFwZrzMapper;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private KttFwLjzMapper kttFwLjzMapper;

    @Autowired
    private KttFwHMapper kttFwHMapper;

    @Autowired
    private KttFwCMapper kttFwCMapper;

    @Autowired
    private QlfQlTdsyqMapper qlfQlTdsyqMapper;

    @Autowired
    private QlfQlJsydsyqMapper jsydsyqMapperImpl;

    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private MessageClient messageClient;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    private RoleManagerClient roleManagerClient;

    @Autowired
    private FwLjzFeginService fwLjzFeginService;

    @Autowired
    private ZdFeignService zdFeignService;

    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;

    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;

    @Autowired
    private BdcCzrzFeignService bdcCzrzFeignService;

    @Autowired
    private ProcessInstanceClient processInstanceClient;

    @Autowired
    NaturalResourcesFeignService naturalResourcesFeignService;
    @Autowired
    BdcSlYjxxFeginService bdcSlYjxxFeginService;

    @Autowired
    BdcShijiService bdcShijiService;
    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    /**
     * 删除方法是否先执行登记删除方法(true|flase,默认 true)
     */
    @Value("${delete.cloud:true}")
    private boolean deleteCloud;

    /**
     * 非首节点可以删除
     */
    @Value("${fsjd.del:false}")
    private boolean fsjdDel;

    /**
     * 非首节点可以删除的受理编号规则
     */
    @Value("#{'${fsjd.tspz.slbh_startwith:}'.split(',')}")
    private List<String> fsjdtspz;

    /**
     * 一体化受理角色
     */
    @Value("${yth.sljs:wwsqsl}")
    private String ythsljs;

    /**
     * 一体化区县代码默认值
     */
    @Value("${yth.qxdmmrz:}")
    private String qxdmmrz;

    @Value("${msg.center.clientId:}")
    private String clientId;

    @Value("${yth.receive.yhqsp.msg:}")
    private String receiveMsgRole;

    @Value("#{'${yth.zdbj.gzldyidList:}'.split(',')}")
    private List<String> zdbjList;

    @Value("${yth.zdbj.slr:}")
    private String zdbjslr;
    @Value("${yth.sfxx.zdzfjd:登薄}")
    private String zdzfjd;
    /**
     * 自动转发，转发指定角色
     */
    @Value("${yth.sfxx.zfroleid:}")
    private String zfroleid;
    @Value("${yth.sfxx.enable:false}")
    private Boolean sfxxEnable;

    /**
     * 盐城创建时自动登薄的工作流
     */
    @Value("#{'${yth.zddb.yg.gzldyidList:}'.split(',')}")
    private List<String> zddbygList;
    /**
     * 盐城创建时转发节点
     */
    @Value("${yth.zddb.yg.zdzfjd:登薄}")
    private String cjYgzdzfjd;
    /**
     * 盐城创建时转发角色
     */
    @Value("${yth.zddb.yg.zdzfroleid:}")
    private String cjYgzdzfroleid;
    /**
     * 盐城创建时转发用户名
     */
    @Value("${yth.zddb.yg.zdzfusername:}")
    private String cjYgzdzfusername;
    /**
     * 盐城一体化是否更具税票更新受理时间
     */
    @Value("${yth.sp.gxslsj:false}")
    private boolean spsfgxslsj;

    /**
     * 盐城一体化邮寄信息推送保存附件，文件夹名称
     */
    @Value("${yth.yjxx.wjjmc:一体化EMS}")
    private String yjxxWjjmc;

    /**
     * 盐城_一体化撤件请求
     *
     * @param ythCancelDTO 盐城_一体化撤件请求参数
     * @return CommonResponse 返回参数
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    public CommonResponse ythCancel(YthCancelDTO ythCancelDTO) {
        //校验参数
        if (StringUtils.isNotBlank(ythCancelDTO.getYthywbh())) {
            List<BdcXmDO> bdcXmDOS = bdcXmMapper.queryBdcXmListBySpxtywh(ythCancelDTO.getYthywbh());
            if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                String processInstanceId = bdcXmDOS.get(0).getGzlslid();
                Integer qszt = bdcXmDOS.get(0).getQszt();
                LOGGER.info("当前一体化业务编号查询到相关项目qszt:{}", qszt);
                if (StringUtils.isNotBlank(processInstanceId)) {
                   /* //非首节点是否可以删除
                    boolean sjd = checkSjdDel(bdcXmDOS);
                    LOGGER.info("当前流程是否检查是首节点：{}, gzlslid:{}", sjd, processInstanceId);
                    if (sjd) {//是否是首节点
                        //是否是首节点
                        boolean issjd = flowableNodeClient.isStartUserTaskRunning(processInstanceId);
                        if (!issjd) {
                            return CommonResponse.fail("非受理节点无法删除");
                        }
                    }*/
                    //46400 【盐城】一体化主动撤件除登薄及办结节点均可撤件 未登簿均可删除,判断qszt=0才能删除
                    if (!CommonConstantUtils.QSZT_TEMPORY.equals(qszt)) {
                        return CommonResponse.fail("非临时状态！无法删除");
                    }
                    if (deleteCloud) {
                        try {
                            BdcDeleteYwxxDTO bdcDeleteYwxxDTO = new BdcDeleteYwxxDTO();
                            bdcDeleteYwxxDTO.setGzlslid(processInstanceId);
                            bdcDeleteYwxxDTO.setReason(YANCHENG_YTH_CANCEL_RESON);
                            bdcDeleteYwxxDTO.setSlzt(HlwSlztEnum.DELETE.getSlzt());
                            bdcInitFeignService.deleteYwxxAllSubsystem(bdcDeleteYwxxDTO);
                        } catch (Exception e) {
                            LOGGER.error("请求 init 删除接口异常:{}", processInstanceId, e);
                            return CommonResponse.fail("删除失败");
                        }
                    }
                    LOGGER.warn("portal deleteCloud：{}， 删除数据slbh:{}", deleteCloud, ythCancelDTO.getYthywbh());
                    taskUtils.deleteTask(processInstanceId, YANCHENG_YTH_CANCEL_RESON);
                }
            } else {
                LOGGER.info("当前一体化业务编号未查询到相关项目信息:{}", ythCancelDTO.getYthywbh());
                return CommonResponse.fail(CommonResponse.ERROR_CODE_WITHOUT_DATA, "当前一体化业务编号未查询到相关项目信息");
            }
        } else {
            LOGGER.info("当前入参没有传一体化业务编号字段;{}", JSON.toJSONString(ythCancelDTO));
            return CommonResponse.fail("当前入参没有传一体化业务编号字段");
        }
        return CommonResponse.ok();
    }

    @Override
    public CommonResponse ythJsSfxx(YthSfxxDTO ythSfxxDTO) {
        try {
            //校验参数
            if (StringUtils.isNotBlank(ythSfxxDTO.getYthywbh())) {
                List<BdcXmDO> bdcXmDOS = bdcXmMapper.queryBdcXmListBySpxtywh(ythSfxxDTO.getYthywbh());
                if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                    String gzlslid = bdcXmDOS.get(0).getGzlslid();
                    String xmid = bdcXmDOS.get(0).getXmid();
                    if (StringUtils.isNotBlank(gzlslid)) {
                        if (CollectionUtils.isNotEmpty(ythSfxxDTO.getDJF_DJ_SF())) {
                            // 项目ID 对应的权利类型，用于组合流程区分产权收费、抵押收费
                            Map<String, Integer> xmidAndQllxMap = bdcXmDOS.stream().collect(Collectors.toMap(BdcXmDO::getXmid, BdcXmDO::getQllx));
                            //更新收费状态为已缴费（因为不区分抵押和转移业务，现在根据项目id更新，原先根据工作流实例id）
                            for(DjfDjSfOldDO djfDjSfOldDO:ythSfxxDTO.getDJF_DJ_SF()){
                                if(StringUtils.isNotBlank(djfDjSfOldDO.getYwh())){
                                    List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
                                    if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                                        for (BdcSlSfxxDO sfxxDO : bdcSlSfxxDOList) {
                                            this.addFsJfsbmAndFph(sfxxDO, xmidAndQllxMap, ythSfxxDTO.getDJF_DJ_SF());
                                        }
                                    }
                                    LOGGER.info("一体化接收收费信息更新收费状态,项目ID:{}", djfDjSfOldDO.getYwh());
                                }
                            }
                            LOGGER.info("一体化接收收费信息更新收费状态,工作流实例ID:{}", gzlslid);
                        }

                        //上传税票
                        if (CollectionUtils.isNotEmpty(ythSfxxDTO.getFJ_F_100())) {
                            List<FjclDTO> fjclDTOList = new ArrayList<>();
                            for (FjFOldDO fjFDO : ythSfxxDTO.getFJ_F_100()) {
                                FjclDTO fjclDTO = new FjclDTO();
                                fjclDTO.setClmc(fjFDO.getFjmc());
                                fjclDTO.setFjlx(fjFDO.getFjlx());
                                exchangeDozerMapper.map(fjFDO, fjclDTO, "ythjssfxx_fj");
                                fjclDTOList.add(fjclDTO);

                            }
                            fjclService.uploadAndSaveFjcl(gzlslid, fjclDTOList);

                            String slbh = bdcXmFeignService.querySlbh(gzlslid);
                            List<BdcSlHsxxDO> listHsxx = bdcSlHsxxFeignService.listBdcSlHsxxBySlbh(slbh);
                            //更新税票时，同步更新项目表受理时间
                            this.modifyYthBdcXmSlsjAndSyncCloud(listHsxx, bdcXmDOS);

                            // 当包含附件信息的时候，要更新wszt为已完税
                            if (CollectionUtils.isEmpty(listHsxx)) {
                                // 当没有hsxx的时候 要插入一条空数据 并更新wszt
                                BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                                bdcSlHsxxDO.setXmid(xmid);
                                bdcSlHsxxDO.setWszt(Integer.parseInt(CommonConstantUtils.WSZT_YWS));
                                bdcSlHsxxFeignService.insertBdcSlHsxxDO(bdcSlHsxxDO);
                            } else {
                                for (BdcSlHsxxDO bdcSlHsxxDO : listHsxx) {
                                    bdcSlHsxxDO.setWszt(Integer.parseInt(CommonConstantUtils.WSZT_YWS));
                                    bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);
                                }
                            }

                            //38875【盐城】待办业务台账增加一体化业务接受税票信息提示
                            // FJ_F_100不为空则判定为已缴税，回写大云缴税状态为 ‘已缴税’
                            Map<String, Object> processInsExtendDto = new HashMap<>();
                            processInsExtendDto.put("JSZT", JSZT_YJS);
                            bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, processInsExtendDto);
                            LOGGER.info("一体化接收税票信息，成功回写大云缴税状态,工作流实例ID:{}", gzlslid);
                            // 发送提示信息
                            sendMessage(slbh);
                            if (sfxxEnable) {
                                // 自动转发
                                AutoForwardTaskDTO autoForwardTaskDTO = new AutoForwardTaskDTO();
                                autoForwardTaskDTO.setJdmc(zdzfjd);
                                autoForwardTaskDTO.setRoleid(zfroleid);
                                LOGGER.info("税费信息自动转发：{}, 转发到节点：{} 角色：{}", gzlslid, zdzfjd, zfroleid);
                                bdcSlFeignService.autoturnZfyz(gzlslid, autoForwardTaskDTO);
                            }
                        }

                    } else {
                        return CommonResponse.fail("一体化业务编号未找到关联的登记项目");
                    }
                } else {
                    return CommonResponse.fail("一体化业务编号未找到关联的登记项目");
                }
            } else {
                return CommonResponse.fail("一体化业务编号不能为空");
            }
        } catch (Exception e) {
            LOGGER.error("盐城_一体化接收收费信息异常:{}", e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }

        return CommonResponse.ok();
    }

    /**
     * 更新项目表受理时间
     * 1、spsfgxslsj 开关控制是否更新受理时间
     * 2、已获取税票后，不在更新受理时间
     */
    private void modifyYthBdcXmSlsjAndSyncCloud(List<BdcSlHsxxDO> listHsxx, List<BdcXmDO> bdcXmDOList) {
        // 判断是否已获取税票，根据完税状态判断，完税状态不为空则以获取税票
        boolean canModify = true;
        if (CollectionUtils.isNotEmpty(listHsxx)) {
            for (BdcSlHsxxDO bdcSlHsxxDO : listHsxx) {
                if (Objects.nonNull(bdcSlHsxxDO.getWszt())) {
                    canModify = false;
                    break;
                }
            }
        }
        if (spsfgxslsj && canModify) {
            bdcXmDOList = bdcXmDOList.stream().filter(t -> CommonConstantUtils.SPLY_YCSL.equals(t.getSply())).collect(Collectors.toList());
            Date slsj = new Date();
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                this.addModifySlsjCzrz(bdcXmDO, slsj);
                bdcXmDO.setSlsj(slsj);
            }
            this.entityMapper.batchSaveSelective(bdcXmDOList);
            // 同步大云更新受理时间
            this.syncCloudSlsj(bdcXmDOList.get(0).getGzlslid(), slsj);
        }
    }

    /**
     * 同步大云受理时间
     */
    private void syncCloudSlsj(String gzlslid, Date slsj) {
        processInstanceClient.updateStartNodeTime(gzlslid, DateUtils.formateTime(slsj, DateUtils.DATE_FORMAT));
    }

    /**
     * 添加更新受理时间操作日志
     */
    private void addModifySlsjCzrz(BdcXmDO bdcXmDO, Date slsj) {
        // 构建操作日志实体类
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        BeanUtils.copyProperties(bdcXmDO, bdcCzrzDO);
        bdcCzrzDO.setLcmc(bdcXmDO.getGzldymc());
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (userDto != null) {
            bdcCzrzDO.setCzr(userDto.getAlias());
        }
        bdcCzrzDO.setCzsj(new Date());
        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_UPDATE.key());
        bdcCzrzDO.setCzzt(CommonConstantUtils.SF_S_DM);
        String czyy = "一体化税票获取更新受理时间，原受理时间：" + DateUtils.formateTime(bdcXmDO.getSlsj(), DateUtils.DATE_FORMAT)
                + "，更新后时间：" + DateUtils.formateTime(slsj, DateUtils.DATE_FORMAT);
        bdcCzrzDO.setCzyy(czyy);
        this.bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
    }

    /**
     * 添加缴费书编码和发票号信息
     * 组合流程：根据权利类型判断抵押还是产权，根据接口中JKYWLX 参数（1：所有权，2：抵押权），更新对应收费信息的电子发票号和缴费书编码
     */
    private void addFsJfsbmAndFph(BdcSlSfxxDO sfxxDO, Map<String, Integer> xmidAndQllxMap, List<DjfDjSfOldDO> djfDjSfOldDOList) {
        if (StringUtils.isNotBlank(sfxxDO.getXmid()) && MapUtils.isNotEmpty(xmidAndQllxMap)) {
            Integer qllx = xmidAndQllxMap.get(sfxxDO.getXmid());
            if (Objects.nonNull(qllx)) {
                if (ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, qllx)) {
                    for (DjfDjSfOldDO djfDjSfOldDO : djfDjSfOldDOList) {
                        if (djfDjSfOldDO.getJkywlx().equals(JKYWLX_CQ)) {
                            sfxxDO.setFph(djfDjSfOldDO.getFsdzph());
                            sfxxDO.setJfsbm(djfDjSfOldDO.getFsjkm());
                            sfxxDO.setSfzt(CommonConstantUtils.SFZT_YJF);
                            bdcSlSfxxFeignService.updateBdcSlSfxx(sfxxDO);

                        }
                    }
                }
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(qllx)) {
                    for (DjfDjSfOldDO djfDjSfOldDO : djfDjSfOldDOList) {
                        if (djfDjSfOldDO.getJkywlx().equals(JKYWLX_DYAQ)) {
                            sfxxDO.setFph(djfDjSfOldDO.getFsdzph());
                            sfxxDO.setJfsbm(djfDjSfOldDO.getFsjkm());
                            sfxxDO.setSfzt(CommonConstantUtils.SFZT_YJF);
                            bdcSlSfxxFeignService.updateBdcSlSfxx(sfxxDO);
                        }
                    }
                }
            }
        }
    }


    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [slbh]
     * @description 发送已获取税票提示信息
     */
    private void sendMessage(String slbh) {
        ProduceMsgDto produceMsgDto = new ProduceMsgDto();
        produceMsgDto.setClientId(clientId);
        produceMsgDto.setMsgCode("YHQSPXX");
        produceMsgDto.setMsgType("YHQSPXX");
        produceMsgDto.setMsgTypeName("自定义消息");
        produceMsgDto.setMsgTitle("无");
        if (StringUtils.isBlank(receiveMsgRole)) {
            LOGGER.warn("接收一体化税票提示的角色为空，无法发送提示信息！");
            return;
        }
        RoleDto roleDto = roleManagerClient.queryRoleByRoleName(receiveMsgRole);
        if (roleDto == null || StringUtils.isBlank(roleDto.getId())) {
            LOGGER.warn("未查询到角色信息，无法发送提示信息！");
            return;
        }
        List<UserDto> userDtoList = roleManagerClient.listEnableUsersByRoleId(roleDto.getId());
        if (CollectionUtils.isEmpty(userDtoList)) {
            LOGGER.warn("未查询到接收一体化税票提示的用户，无法发送提示信息！");
            return;
        }
        List<String> usernames = userDtoList.stream().filter(Objects::nonNull).
                filter(userDto -> StringUtils.isNotBlank(userDto.getUsername()))
                .collect(Collectors.mapping(userDto -> userDto.getUsername(), Collectors.toList()));

        if (CollectionUtils.isEmpty(usernames)) {
            LOGGER.warn("用户名列表为空，无法发送提示信息！");
            return;
        }
        produceMsgDto.setConsumer(StringUtils.join(usernames, CommonConstantUtils.ZF_YW_DH));
        produceMsgDto.setConsumerType("batch");
        produceMsgDto.setProducer(userManagerUtils.getCurrentUserName());
        produceMsgDto.setProducerType("personal");
        produceMsgDto.setNotifyType("rabbitmq");
        produceMsgDto.setMsgContent("一体化推送业务 " + slbh + "已获取税票信息");
        produceMsgDto.setRead(0);
        produceMsgDto.setOptions("save");
        messageClient.saveProduceMsg(produceMsgDto);
    }

    @Override
    public CommonResponse ythJsSqxxCj(YthYwsqxxDTO ythYwsqxxDTO) {
        try {
            //校验参数
            if (StringUtils.isNoneBlank(ythYwsqxxDTO.getYthywbh(), ythYwsqxxDTO.getSjywmc()) && CollectionUtils.isNotEmpty(ythYwsqxxDTO.getDJT_DJ_SLSQ()) && CollectionUtils.isNotEmpty(ythYwsqxxDTO.getDJF_DJ_SJ())) {
                //对照
                WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
                exchangeDozerMapper.map(ythYwsqxxDTO, wwsqCjBdcXmRequestDTO, "ythjssqxxcj_sqxx");
                //获取受理人和工作流定义id，用于判断自动转发
                String gzldyid = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getGzldyid();
                LOGGER.info("一体化创建,gzldyid:{}", gzldyid);
                //赋值角色
                wwsqCjBdcXmRequestDTO.setSlRoleCode(ythsljs);
                //直接为配置角色,不增加区县代码过滤
                wwsqCjBdcXmRequestDTO.setSljsbglqxdm(true);
                //区县代码
                if (wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx() != null && StringUtils.isNotBlank(qxdmmrz)) {
                    wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().setQxdm(qxdmmrz);
                }
                List<BdcSlXmDTO> bdcSlXmDTOList = new ArrayList<>();
                //将一体化申请信息组织成一个个完整申请项目信息进行对照
                List<YthYwsqBdcdyxxDTO> ythYwsqBdcdyxxDTOS = initYthYwsqBdcdyxxDTO(ythYwsqxxDTO);
                if (CollectionUtils.isNotEmpty(ythYwsqBdcdyxxDTOS)) {
                    for (YthYwsqBdcdyxxDTO ythYwsqBdcdyxxDTO : ythYwsqBdcdyxxDTOS) {
                        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                        exchangeDozerMapper.map(ythYwsqBdcdyxxDTO, bdcSlXmDTO, "ythjssqxxcj_bdcdyxx");
                        //处理权利人
                        if (CollectionUtils.isNotEmpty(ythYwsqBdcdyxxDTO.getZttGyQlrDTOList())) {
                            bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                            bdcSlXmDTO.setBdcDsQlrDOList(new ArrayList<>());

                            for (ZttGyQlrDTO zttGyQlrDTO : ythYwsqBdcdyxxDTO.getZttGyQlrDTOList()) {
                                if (StringUtils.equals(CommonConstantUtils.DSQLR_QLRLB_ZWR, zttGyQlrDTO.getQlrlb())) {
                                    BdcDsQlrDO dsfQlr = new BdcDsQlrDO();
                                    exchangeDozerMapper.map(zttGyQlrDTO, dsfQlr, "ythjssqxxcj_dsfQlrxx");
                                    if (CheckParameter.checkAnyParameter(dsfQlr)) {
                                        bdcSlXmDTO.getBdcDsQlrDOList().add(dsfQlr);
                                    }
                                } else {
                                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                    exchangeDozerMapper.map(zttGyQlrDTO, bdcSlSqrDO, "ythjssqxxcj_sqr");
                                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                                    }
                                }
                            }
                        }
                        bdcSlXmDTOList.add(bdcSlXmDTO);
                    }

                }
                wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().setBdcSlXmList(bdcSlXmDTOList);

                //申请创建
                WwsqCjBdcXmResponseDTO wwsqCjBdcXmResponseDTO = bdcSlFeignService.wwsqCjBdcXm(wwsqCjBdcXmRequestDTO);

                //附件对照
                List<FjclDTO> fjclDTOList = new ArrayList<>();
                for (DjfDjSjOldDO sjDO : ythYwsqxxDTO.getDJF_DJ_SJ()) {
                    FjclDTO fjclDTO = new FjclDTO();
                    exchangeDozerMapper.map(sjDO, fjclDTO, "ythjssqxxcj_sjcl");
                    if (CollectionUtils.isNotEmpty(fjclDTO.getFjclmxDTOList()) && StringUtils.isNotBlank(sjDO.getBz())) {
                        //取最后一个斜杠后面内容作为附件名称
                        fjclDTO.getFjclmxDTOList().get(0).setFjmc(sjDO.getBz().split(CommonConstantUtils.ZF_YW_XG)[sjDO.getBz().split(CommonConstantUtils.ZF_YW_XG).length - 1]);
                    }
                    fjclDTOList.add(fjclDTO);
                }
                //处理创建后结果
                Map<String, Object> resultMap = gxWwSqxxService.revertCjResponse(ythYwsqxxDTO.getYthywbh(), wwsqCjBdcXmResponseDTO, fjclDTOList, wwsqCjBdcXmRequestDTO, "false");
                if (resultMap.get("gzlslid") == null) {
                    return CommonResponse.fail(resultMap.get("msg") != null ? resultMap.get("msg").toString() : "");
                }
                //根据参数条件，判断自动办结
                if (zdbjList.contains(gzldyid)) {
                    LOGGER.info("该流程需要自动办结，工作流定义id：{},配置的自动办结slr为：{}", gzldyid, zdbjslr);
                    wwsqCjBdcXmResponseDTO.setSfzbbj(true);
                    gxWwSqxxService.zdbjWithCanBj(wwsqCjBdcXmResponseDTO, zdbjslr, true);
                    LOGGER.info("自动办结结束");
                }
                //盐城：52598 根据条件判断是否自动登簿
                if (CollectionUtils.isNotEmpty(zddbygList)
                        && zddbygList.contains(gzldyid)
                        && CommonConstantUtils.SF_S_DM.toString().equals(ythYwsqxxDTO.getSfkyzddb())
                        && Objects.nonNull(resultMap.get("gzlslid"))
                ) {
                    LOGGER.info("该流程需要自动登簿，工作流定义id：{}", gzldyid);
                    try {
                        ygcjzddb((String) resultMap.get("gzlslid"));
                    } catch (Exception e) {
                        LOGGER.info("该流程需要自动登簿，工作流定义id：{},出现错误：{}", gzldyid, e.getMessage());
                    }
                    LOGGER.info("自动登簿结束");
                }
            } else {
                return CommonResponse.fail("检查必要参数ythywbh,sjywmc,DJT_DJ_SLSQ,DJF_DJ_SJ是否为空");
            }

        } catch (Exception e) {
            LOGGER.error("盐城_一体化平台申请创建业务接口异常:{}", e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        return CommonResponse.ok();


    }

    /**
     * 盐城预告自动登簿
     *
     * @param gzlslid
     * @throws Exception
     */
    public void ygcjzddb(String gzlslid) throws Exception {
        Boolean needDb = false;
        if (StringUtils.isBlank(gzlslid)) {
            return;
        }
        List<BdcYgDO> ygList = bdcXmMapper.queryYgxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(ygList)) {
            needDb = false;
        } else {
            LOGGER.info("盐城自动登簿：{}, 权利列表：{}", gzlslid, JSON.toJSONString(ygList));
            if (CollectionUtils.isNotEmpty(ygList)) {
                needDb = ygList.stream().allMatch(this::getNeedZdDb);
            } else {
                needDb = false;
            }
        }
        //如果满足自动登簿条件
        if (needDb) {
            AutoForwardTaskDTO autoForwardTaskDTO = new AutoForwardTaskDTO();
            autoForwardTaskDTO.setJdmc(cjYgzdzfjd);

            if (StringUtils.isNotBlank(cjYgzdzfroleid)) {
                autoForwardTaskDTO.setRoleid(cjYgzdzfroleid);
            }
            if (StringUtils.isNotBlank(cjYgzdzfusername)) {
                autoForwardTaskDTO.setUsername(cjYgzdzfusername);
            }
            LOGGER.info("盐城自动登簿：{}, 转发到节点：{} 角色：{},用户{}", gzlslid, cjYgzdzfjd, cjYgzdzfroleid, cjYgzdzfusername);
            bdcSlFeignService.autoturnZfyz(gzlslid, autoForwardTaskDTO);
        }
    }

    /**
     * ①共有方式为共同共有，权利人数量为2，并且通过接口验证权利人为夫妻关系，或者共有方式为单独所有、按份共有
     * ②借款人与抵押人一致
     * 预告只需要满足条件①，抵押要同时满足条件②
     * 要验证所有的权利，有任意一个不行就放弃
     *
     * @param bdcYgDO
     * @return
     */
    private Boolean getNeedZdDb(BdcYgDO bdcYgDO) {
        Boolean needDb = false;
        if (StringUtils.isBlank(bdcYgDO.getXmid())) {
            return false;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcYgDO.getXmid());
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            return false;
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        LOGGER.info("盐城自动登簿判断 权利：{}", JSON.toJSONString(bdcYgDO));
        //预告预抵押
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        if (CommonConstantUtils.YGDJZL_YSSPFDYYG.equals(bdcYgDO.getYgdjzl())
                || CommonConstantUtils.YGDJZL_QTDYYG.equals(bdcYgDO.getYgdjzl())) {
            //抵押人
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
            bdcQlrQO.setXmid(bdcYgDO.getXmid());
            List<BdcQlrDO> bdcYwrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            //第三方权利人--债权人
            BdcDsQlrQO bdcDsQlrQO = new BdcDsQlrQO();
            bdcDsQlrQO.setXmid(bdcYgDO.getXmid());
            List<BdcDsQlrDO> bdcDsQlrDOS = bdcQlrFeignService.listBdcDsQlr(bdcDsQlrQO);
            LOGGER.info("盐城自动登簿判断 权利,判断债权人和抵押人一致：{},{},{}", JSON.toJSONString(bdcYgDO)
                    , JSON.toJSONString(bdcYwrDOS), JSON.toJSONString(bdcDsQlrDOS));
            if (CollectionUtils.isNotEmpty(bdcYwrDOS) && CollectionUtils.isNotEmpty(bdcDsQlrDOS)) {
                Set<String> dyrZjh = bdcYwrDOS.stream().map(BdcQlrDO::getZjh).collect(Collectors.toSet());
                Set<String> zqrZjh = bdcDsQlrDOS.stream().map(BdcDsQlrDO::getZjh).collect(Collectors.toSet());
                //判断债权人和抵押人一致
                if (dyrZjh.containsAll(zqrZjh) && zqrZjh.containsAll(dyrZjh)) {
                    needDb = true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            //预告
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrQO.setXmid(bdcYgDO.getXmid());
            List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            //从权利人和项目表尝试获取共有方式
            Integer gyfs = 999999;
            if (Objects.nonNull(bdcXmDO.getGyfs())) {
                gyfs = bdcXmDO.getGyfs();
            } else if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                if (Objects.nonNull(bdcQlrDOS.get(0).getGyfs())) {
                    gyfs = bdcQlrDOS.get(0).getGyfs();
                }
            }
            LOGGER.info("盐城自动登簿判断 {} 权利,判断 gyfs：{}", JSON.toJSONString(bdcYgDO)
                    , JSON.toJSONString(gyfs));
            if (Objects.nonNull(gyfs) && gyfs.equals(CommonConstantUtils.GYFS_GTGY) && (bdcQlrDOS.size() == 2)) {
                //判断两个权利人的关系
                CivilMarriageQueryRequestDTO civilMarriageQueryRequestDTO = new CivilMarriageQueryRequestDTO();
                List<CivilMarriageQueryParamDTO> paramDTOList = new ArrayList<>();
                List<String> qlrMcList = new ArrayList<>();
                for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                    paramDTOList.add(new CivilMarriageQueryParamDTO(bdcQlrDO.getZjh(), bdcQlrDO.getQlrmc()));
                    qlrMcList.add(bdcQlrDO.getQlrmc());
                }
                civilMarriageQueryRequestDTO.setParamDTOList(paramDTOList);
                civilMarriageQueryRequestDTO.setSlbh(bdcXmDO.getSlbh());
                CivilMarriageQueryResponseDTO civilMarriageQueryResponseDTO = null;
                try {
                    LOGGER.info("盐城自动登簿判断权利人关系错误：xm列表:{} 权利人列表：{},请求参数{}", bdcXmDO.getXmid(), JSON.toJSONString(bdcQlrDOS), civilMarriageQueryRequestDTO);
                    civilMarriageQueryResponseDTO = naturalResourcesFeignService.marriageQuery(civilMarriageQueryRequestDTO);
                } catch (Exception e) {
                    LOGGER.info("盐城自动登簿判断权利人关系错误：xm列表:{} 权利人列表：{},请求参数{},错误{}", bdcXmDO.getXmid(), JSON.toJSONString(bdcQlrDOS), civilMarriageQueryRequestDTO, e.getMessage());
                }
                LOGGER.info("盐城自动登簿判断权利人关系返回：xm列表:{} 权利人列表：{},请求参数{},返回{}", bdcXmDO.getXmid(), JSON.toJSONString(bdcQlrDOS), civilMarriageQueryRequestDTO, JSON.toJSONString(civilMarriageQueryResponseDTO));
                if (civilMarriageQueryResponseDTO != null && CollectionUtils.isNotEmpty(civilMarriageQueryResponseDTO.getDataDTOList())) {
                    CivilMarriageQueryDataDTO civilMarriageQueryDataDTO = civilMarriageQueryResponseDTO.getDataDTOList().get(0);
                    if (qlrMcList.contains(civilMarriageQueryDataDTO.getNameMan())
                            && qlrMcList.contains(civilMarriageQueryDataDTO.getNameWoman())
                    ) {
                        needDb = true;
                    }
                } else {
                    return false;
                }
            } else if ((Objects.nonNull(gyfs) && gyfs.equals(CommonConstantUtils.GYFS_DDSY))
                    || (Objects.nonNull(gyfs) && gyfs.equals(CommonConstantUtils.GYFS_AFGY))) {
                needDb = true;
            } else {
                return false;
            }
        }
        return needDb;
    }

    /**
     * 盐城_一体化发证信息同步
     *
     * @param gzlslid 盐城_一体化发证信息同步请求参数
     * @return CommonResponse 返回参数
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    public CommonResponse ythTsFzxx(String gzlslid) {
        //校验参数
        try {
            if (StringUtils.isNotBlank(gzlslid)) {
                List<BdcXmDO> bdcXmDOS = bdcXmMapper.queryBdcXmList(gzlslid);
                if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                    if (StringUtils.isBlank(bdcXmDOS.get(0).getSpxtywh())) {
                        return CommonResponse.fail("未获取到一体化业务编号。");
                    }
                    YthYwxxDTO requestObject = new YthYwxxDTO();
                    requestObject = requestObject.addCsxx(bdcXmDOS.get(0));
                    //获取登记类型和权利类型 区分 转移类业务 抵押业类务 预告登记业务 查封类业务 注销类业务
                    for (BdcXmDO bdcXmDO : bdcXmDOS) {
                        //不同的业务获取不同的同步数据
                        YthInitParamService service = ythInitParamServiceChoose.getService(ythInitParamServiceChoose.getHandleServiceKey(bdcXmDO));
                        if (service != null) {
                            service.initYthQlRequestParam(requestObject, bdcXmDO);
                            service.initIssueCertificateCommonRequestParam(requestObject, bdcXmDO);
                            service.initYthParamZddzAndDefaultValue(requestObject, bdcXmDO, bdcExchangeZddzService.getBdcExchangeZddzMap(), accessDefaultValueService.getBdcExchangeDefaultValueDOList());
                        } else {
                            LOGGER.info("未匹配到相关处理的实现类！");
                            return CommonResponse.fail("未匹配到相关处理的实现类");
                        }
                    }
                    String requestStr = JSON.toJSONString(requestObject, SerializerFeature.WriteNullStringAsEmpty);
                    Object response = exchangeBeanRequestService.request("ts_fzsqxx", JSON.parseObject(requestStr));
                    return resolveResponse(response);
                } else {
                    return CommonResponse.fail("根据工作流id:{" + gzlslid + "}未查询到相关项目信息！");
                }
            } else {
                return CommonResponse.fail("缺少gzlslid参数");
            }
        } catch (Exception e) {
            LOGGER.error("盐城_一体化发证信息同步异常:{}" + e.getMessage());
            return CommonResponse.fail(e.getMessage());
        }
    }

    /**
     * 判断节点是否需要判断是首节点
     *
     * @return boolean 是否需要判断是首节点
     * @author <a href="mailto:foxfocus@163.com">fox</a>
     */
    private boolean checkSjdDel(List<BdcXmDO> bdcXmDOS) {
        if (fsjdDel) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(fsjdtspz) && StringUtils.isNotBlank(fsjdtspz.get(0))) {
            if (CollectionUtils.isNotEmpty(bdcXmDOS) && null != bdcXmDOS.get(0)) {
                String slbh = bdcXmDOS.get(0).getSlbh();
                for (String s : fsjdtspz) {
                    return !StringUtils.startsWith(slbh, s);
                }
            }
        }
        return true;
    }

    @Override
    public CommonResponse ythTsSlxx(String gzlslid) {
        LOGGER.info("请求盐城一体化推送受理信息接口：{}", gzlslid);
        if (StringUtils.isBlank(gzlslid)) {
            return CommonResponse.fail("未获取到工作流实例ID。");
        }
        List<BdcXmDO> bdcXmDOList = bdcXmMapper.queryBdcXmList(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return CommonResponse.fail(String.format("根据工作流id: %s 未查询到相关项目信息！", gzlslid));
        }
        if (StringUtils.isBlank(bdcXmDOList.get(0).getSpxtywh())) {
            return CommonResponse.fail("未获取到一体化业务编号。");
        }
        try {
            YthYwxxDTO ythYwxxDTO = new YthYwxxDTO().addCsxx(bdcXmDOList.get(0));
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                // 获取对应的获取权利信息的实现类
                YthInitParamService service = ythInitParamServiceChoose.getService(ythInitParamServiceChoose.getHandleServiceKey(bdcXmDO));
                // 受理申请信息
                service.initYthSlxxRequestParam(ythYwxxDTO, bdcXmDO);
                // 受理收费信息
                service.initYthSfxxRequestParam(ythYwxxDTO, bdcXmDO, true);
                service.initYthParamZddzAndDefaultValue(ythYwxxDTO, bdcXmDO, bdcExchangeZddzService.getBdcExchangeZddzMap(), accessDefaultValueService.getBdcExchangeDefaultValueDOList());
            }
            String requestStr = JSON.toJSONString(ythYwxxDTO, SerializerFeature.WriteNullStringAsEmpty);
            Object response = exchangeBeanRequestService.request("ts_slsqxx", JSON.parseObject(requestStr));
            LOGGER.info("盐城一体化推送受理信息接口返回值：{}", response);
            return resolveResponse(response);
        } catch (Exception e) {
            LOGGER.error("盐城一体化推送受理信息接口异常:{}", e.getMessage());
            return CommonResponse.fail(e.getMessage());
        }
    }

    @Override
    public CommonResponse ythTsShxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return CommonResponse.fail("未获取到工作流实例ID。");
        }
        List<BdcXmDO> bdcXmDOList = bdcXmMapper.queryBdcXmList(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return CommonResponse.fail(String.format("根据工作流实例id: %s 未查询到相关项目信息！", gzlslid));
        }
        if (StringUtils.isBlank(bdcXmDOList.get(0).getSpxtywh())) {
            return CommonResponse.fail("未获取到一体化业务编号。");
        }
        try {
            YthYwxxDTO ythYwxxDTO = new YthYwxxDTO().addCsxx(bdcXmDOList.get(0));
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                // 获取对应的获取权利信息的实现类
                YthInitParamService service = ythInitParamServiceChoose.getService(ythInitParamServiceChoose.getHandleServiceKey(bdcXmDO));
                // 获取审核信息
                service.initYthShxxRequestParam(ythYwxxDTO, bdcXmDO);
                // 获取权利信息
//                service.initYthQlRequestParam(ythYwxxDTO, bdcXmDO);
                service.initYthParamZddzAndDefaultValue(ythYwxxDTO, bdcXmDO, bdcExchangeZddzService.getBdcExchangeZddzMap(), accessDefaultValueService.getBdcExchangeDefaultValueDOList());
            }
            String requestStr = JSON.toJSONString(ythYwxxDTO, SerializerFeature.WriteNullStringAsEmpty);
            Object response = exchangeBeanRequestService.request("ts_shxx", JSON.parseObject(requestStr));
            LOGGER.info("盐城一体化推送审核信息返回值：{}", response);
            return resolveResponse(response);
        } catch (Exception e) {
            LOGGER.error("盐城一体化推送审核信息异常:{}", e.getMessage());
            return CommonResponse.fail(String.format("盐城一体化推送审核信息异常:%s", e.getMessage()));
        }
    }

    private CommonResponse resolveResponse(Object response) {
        if (Objects.isNull(response)) {
            return CommonResponse.fail("未获取到接口返回值信息");
        }
        Map result = (Map) response;
        if ("success".equals(result.get("type").toString())) {
            return CommonResponse.ok();
        } else {
            return CommonResponse.fail(result.get("data").toString());
        }
    }

    /**
     * 登记删除业务，通知一体化
     *
     * @param gzlslid@Date 2020/12/4
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public CommonResponse deleteTzYth(String gzlslid, String reason) {
        //判断参数
        if (StringUtils.isBlank(gzlslid)) {
            LOGGER.error("未获取到工作流实例ID");
            throw new AppException("未获取到工作流实例ID");
        }
        LOGGER.info("删除业务通知一体化");
        List<BdcXmDO> bdcXmDOList = bdcXmMapper.queryBdcXmList(gzlslid);

        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.info("未获取到项目数据");
            return CommonResponse.fail(String.format("根据工作流实例id: %s 未查询到相关项目信息！", gzlslid));
        }
        YthYwxxDTO ythYwxxDTO = new YthYwxxDTO().addCsxx(bdcXmDOList.get(0));
        // sql查询参数
        Map<String, String> queryParam = new HashMap<>(2);
        queryParam.put("gzlslid", gzlslid);

        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            // 获取对应的获取权利信息的实现类
            YthInitParamService service = ythInitParamServiceChoose.getService(ythInitParamServiceChoose.getHandleServiceKey(bdcXmDO));
            // 退件查询受理申请信息
            service.initYthSlxxForTjRequestParam(ythYwxxDTO, bdcXmDO);
            // 受理收费信息
            service.initYthSfxxRequestParam(ythYwxxDTO, bdcXmDO, false);
            try {
                service.initYthParamZddzAndDefaultValue(ythYwxxDTO, bdcXmDO, bdcExchangeZddzService.getBdcExchangeZddzMap(), accessDefaultValueService.getBdcExchangeDefaultValueDOList());
            } catch (Exception e) {
                return CommonResponse.fail(e.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(ythYwxxDTO.getDjtDjSlsqDO())) {
            for (DjtDjSlsqOldDO slsqDO : ythYwxxDTO.getDjtDjSlsqDO()) {
                slsqDO.setSfwtaj("1");
                slsqDO.setBz(reason);
            }
        }
        String requestStr = JSON.toJSONString(ythYwxxDTO, SerializerFeature.WriteNullStringAsEmpty);
        LOGGER.info("tj_tjxx接口请求参数：" + requestStr);
        Object response = exchangeBeanRequestService.request("tj_tjxx", JSON.parseObject(requestStr));
        return resolveResponse(response);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 将一体化申请信息组织成一个个完整申请项目信息进行对照
     */
    private List<YthYwsqBdcdyxxDTO> initYthYwsqBdcdyxxDTO(YthYwsqxxDTO ythYwsqxxDTO) {
        List<YthYwsqBdcdyxxDTO> ythYwsqBdcdyxxDTOList = new ArrayList<>();
        Map<String, Integer> dyhSxhMap = new HashMap<>();
        //房地产权
        if (CollectionUtils.isNotEmpty(ythYwsqxxDTO.getQLT_FW_FDCQ_YZ())) {
            for (QltFwFdcqYzOldDO qltFwFdcqYzDO : ythYwsqxxDTO.getQLT_FW_FDCQ_YZ()) {
                if (StringUtils.isBlank(qltFwFdcqYzDO.getBdcdyh())) {
                    throw new AppException("检查QLT_FW_FDCQ_YZ必要参数bdcdyh是否为空");
                }
                YthYwsqBdcdyxxDTO ythYwsqBdcdyxxDTO = new YthYwsqBdcdyxxDTO();
                BeanUtils.copyProperties(ythYwsqxxDTO, ythYwsqBdcdyxxDTO);
                //申请信息
                ythYwsqBdcdyxxDTO.setDjtDjSlsqDO(ythYwsqxxDTO.getDJT_DJ_SLSQ().get(0));
                //权利信息
                ythYwsqBdcdyxxDTO.setQltFwFdcqYzDO(qltFwFdcqYzDO);
                //权利人信息
                YthPpQlrCsDTO ythPpQlrCsDTO = new YthPpQlrCsDTO();
                ythPpQlrCsDTO.setYthYwsqxxDTO(ythYwsqxxDTO);
                ythPpQlrCsDTO.setBdcdyh(qltFwFdcqYzDO.getBdcdyh());
                ythPpQlrCsDTO.setQllx(CommonConstantUtils.QLLX_FDCQ_DM);
                ythYwsqBdcdyxxDTO.setZttGyQlrDTOList(ppQlrxx(ythPpQlrCsDTO));
                if (StringUtils.isNotBlank(qltFwFdcqYzDO.getBdcqzh())) {
                    //根据产权证号获取yxmid
                    List<BdcXmDO> xmList = bdcXmMapper.getXsXmByBdcdyhAndBdcqzh(qltFwFdcqYzDO.getBdcdyh(), qltFwFdcqYzDO.getBdcqzh());
                    if (CollectionUtils.isEmpty(xmList)) {
                        throw new AppException("产权证号或不动产单元号不正确,未获取到对应的登记数据,无法创建业务");
                    }
                    ythYwsqBdcdyxxDTO.setYxmid(xmList.get(0).getXmid());
                }
                ythYwsqBdcdyxxDTO.setSxh(dealYthSxh(dyhSxhMap, qltFwFdcqYzDO.getBdcdyh()));
                ythYwsqBdcdyxxDTOList.add(ythYwsqBdcdyxxDTO);


            }
        }
        //抵押权
        if (CollectionUtils.isNotEmpty(ythYwsqxxDTO.getQLF_QL_DYAQ())) {
            for (QlfQlDyaqDOExpandExchange qlfQlDyaqDO : ythYwsqxxDTO.getQLF_QL_DYAQ()) {
                if (StringUtils.isBlank(qlfQlDyaqDO.getBdcdyh())) {
                    throw new AppException("检查QLF_QL_DYAQ必要参数bdcdyh是否为空");
                }
                YthYwsqBdcdyxxDTO ythYwsqBdcdyxxDTO = new YthYwsqBdcdyxxDTO();
                BeanUtils.copyProperties(ythYwsqxxDTO, ythYwsqBdcdyxxDTO);
                //申请信息
                DjtDjSlsqOldDO djtDjSlsqOldDO =new DjtDjSlsqOldDO();
                BeanUtils.copyProperties(ythYwsqxxDTO.getDJT_DJ_SLSQ().get(0), djtDjSlsqOldDO);
                ythYwsqBdcdyxxDTO.setDjtDjSlsqDO(djtDjSlsqOldDO);
                //抵押权分别持证不读取推送的来,目前一体化结构对于组合登记无法区分推送分别持证
                ythYwsqBdcdyxxDTO.getDjtDjSlsqDO().setSqfbcz(null);
                //权利信息
                ythYwsqBdcdyxxDTO.setQlfQlDyaqDO(qlfQlDyaqDO);
                //权利人信息
                YthPpQlrCsDTO ythPpQlrCsDTO = new YthPpQlrCsDTO();
                ythPpQlrCsDTO.setYthYwsqxxDTO(ythYwsqxxDTO);
                ythPpQlrCsDTO.setBdcdyh(qlfQlDyaqDO.getBdcdyh());
                ythPpQlrCsDTO.setQllx(CommonConstantUtils.QLLX_DYAQ_DM);
                ythYwsqBdcdyxxDTO.setZttGyQlrDTOList(ppQlrxx(ythPpQlrCsDTO));
                ythYwsqBdcdyxxDTO.setSxh(dealYthSxh(dyhSxhMap, qlfQlDyaqDO.getBdcdyh()));
                if (StringUtils.isNotBlank(qlfQlDyaqDO.getBdcqzh())) {
                    //根据产权证号获取yxmid
                    List<BdcXmDO> xmList = bdcXmMapper.getXsXmByBdcdyhAndBdcqzh(qlfQlDyaqDO.getBdcdyh(), qlfQlDyaqDO.getBdcqzh());
                    if (CollectionUtils.isEmpty(xmList)) {
                        throw new AppException("产权证号或不动产单元号不正确,未获取到对应的登记数据,无法创建业务");
                    }
                    ythYwsqBdcdyxxDTO.setYxmid(xmList.get(0).getXmid());
                }
                ythYwsqBdcdyxxDTOList.add(ythYwsqBdcdyxxDTO);


            }
        }
        //预告登记
        if (CollectionUtils.isNotEmpty(ythYwsqxxDTO.getQLF_QL_YGDJ())) {
            for (QlfQlYgdjOldDO qlfQlYgdjDO : ythYwsqxxDTO.getQLF_QL_YGDJ()) {
                if (StringUtils.isBlank(qlfQlYgdjDO.getBdcdyh())) {
                    throw new AppException("检查QLF_QL_YGDJ必要参数bdcdyh是否为空");
                }
                YthYwsqBdcdyxxDTO ythYwsqBdcdyxxDTO = new YthYwsqBdcdyxxDTO();
                BeanUtils.copyProperties(ythYwsqxxDTO, ythYwsqBdcdyxxDTO);
                //申请信息
                DjtDjSlsqOldDO djtDjSlsqOldDO =new DjtDjSlsqOldDO();
                BeanUtils.copyProperties(ythYwsqxxDTO.getDJT_DJ_SLSQ().get(0), djtDjSlsqOldDO);
                ythYwsqBdcdyxxDTO.setDjtDjSlsqDO(djtDjSlsqOldDO);
                //权利信息
                ythYwsqBdcdyxxDTO.setQlfQlYgdjDO(qlfQlYgdjDO);
                YthPpQlrCsDTO ythPpQlrCsDTO = new YthPpQlrCsDTO();
                ythPpQlrCsDTO.setYthYwsqxxDTO(ythYwsqxxDTO);
                ythPpQlrCsDTO.setBdcdyh(qlfQlYgdjDO.getBdcdyh());
                ythPpQlrCsDTO.setQllx(CommonConstantUtils.QLLX_YG_DM);
                if (qlfQlYgdjDO.getYgdjzl() != null) {
                    ythPpQlrCsDTO.setYgdjzl(Integer.parseInt(qlfQlYgdjDO.getYgdjzl()));
                    if(ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, ythPpQlrCsDTO.getYgdjzl())){
                        //预抵押分别持证不读取推送的来,目前一体化结构对于组合登记无法区分推送分别持证
                        ythYwsqBdcdyxxDTO.getDjtDjSlsqDO().setSqfbcz(null);
                    }
                }
                //权利人信息
                ythYwsqBdcdyxxDTO.setZttGyQlrDTOList(ppQlrxx(ythPpQlrCsDTO));
                ythYwsqBdcdyxxDTO.setSxh(dealYthSxh(dyhSxhMap, qlfQlYgdjDO.getBdcdyh()));
                ythYwsqBdcdyxxDTOList.add(ythYwsqBdcdyxxDTO);


            }

        }
        //异议登记
        if (CollectionUtils.isNotEmpty(ythYwsqxxDTO.getQLF_QL_YYDJ())) {
            for (QlfQlYydjOldDO qlfQlYydjDO : ythYwsqxxDTO.getQLF_QL_YYDJ()) {
                if (StringUtils.isBlank(qlfQlYydjDO.getBdcdyh())) {
                    throw new AppException("检查QLF_QL_YYDJ必要参数bdcdyh是否为空");
                }
                YthYwsqBdcdyxxDTO ythYwsqBdcdyxxDTO = new YthYwsqBdcdyxxDTO();
                BeanUtils.copyProperties(ythYwsqxxDTO, ythYwsqBdcdyxxDTO);
                //申请信息
                ythYwsqBdcdyxxDTO.setDjtDjSlsqDO(ythYwsqxxDTO.getDJT_DJ_SLSQ().get(0));
                //权利信息
                ythYwsqBdcdyxxDTO.setQlfQlYydjDO(qlfQlYydjDO);
                YthPpQlrCsDTO ythPpQlrCsDTO = new YthPpQlrCsDTO();
                ythPpQlrCsDTO.setYthYwsqxxDTO(ythYwsqxxDTO);
                ythPpQlrCsDTO.setBdcdyh(qlfQlYydjDO.getBdcdyh());
                ythPpQlrCsDTO.setQllx(CommonConstantUtils.QLLX_YY);
                //权利人信息
                ythYwsqBdcdyxxDTO.setZttGyQlrDTOList(ppQlrxx(ythPpQlrCsDTO));
                ythYwsqBdcdyxxDTO.setSxh(dealYthSxh(dyhSxhMap, qlfQlYydjDO.getBdcdyh()));
                ythYwsqBdcdyxxDTOList.add(ythYwsqBdcdyxxDTO);


            }

        }
        //查封登记
        if (CollectionUtils.isNotEmpty(ythYwsqxxDTO.getQLF_QL_CFDJ())) {
            for (QlfQlCfdjOldDO qlfQlCfdjDO : ythYwsqxxDTO.getQLF_QL_CFDJ()) {
                if (StringUtils.isBlank(qlfQlCfdjDO.getBdcdyh())) {
                    throw new AppException("检查QLF_QL_CFDJ必要参数bdcdyh是否为空");
                }
                YthYwsqBdcdyxxDTO ythYwsqBdcdyxxDTO = new YthYwsqBdcdyxxDTO();
                BeanUtils.copyProperties(ythYwsqxxDTO, ythYwsqBdcdyxxDTO);
                //申请信息
                ythYwsqBdcdyxxDTO.setDjtDjSlsqDO(ythYwsqxxDTO.getDJT_DJ_SLSQ().get(0));
                //权利信息
                ythYwsqBdcdyxxDTO.setQlfQlCfdjDO(qlfQlCfdjDO);
                YthPpQlrCsDTO ythPpQlrCsDTO = new YthPpQlrCsDTO();
                ythPpQlrCsDTO.setYthYwsqxxDTO(ythYwsqxxDTO);
                ythPpQlrCsDTO.setBdcdyh(qlfQlCfdjDO.getBdcdyh());
                ythPpQlrCsDTO.setQllx(CommonConstantUtils.QLLX_CF);
                //权利人信息
                ythYwsqBdcdyxxDTO.setZttGyQlrDTOList(ppQlrxx(ythPpQlrCsDTO));
                ythYwsqBdcdyxxDTO.setSxh(dealYthSxh(dyhSxhMap, qlfQlCfdjDO.getBdcdyh()));
                ythYwsqBdcdyxxDTOList.add(ythYwsqBdcdyxxDTO);


            }

        }
        //注销登记
        if (CollectionUtils.isNotEmpty(ythYwsqxxDTO.getQLF_QL_ZXDJ())) {
            for (QlfQlZxdjOldDO qlfQlZxdjDO : ythYwsqxxDTO.getQLF_QL_ZXDJ()) {
                YthYwsqBdcdyxxDTO ythYwsqBdcdyxxDTO = new YthYwsqBdcdyxxDTO();
                BeanUtils.copyProperties(ythYwsqxxDTO, ythYwsqBdcdyxxDTO);
                //申请信息
                ythYwsqBdcdyxxDTO.setDjtDjSlsqDO(ythYwsqxxDTO.getDJT_DJ_SLSQ().get(0));
                //权利信息
                ythYwsqBdcdyxxDTO.setQlfQlZxdjDO(qlfQlZxdjDO);
                YthPpQlrCsDTO ythPpQlrCsDTO = new YthPpQlrCsDTO();
                ythPpQlrCsDTO.setYthYwsqxxDTO(ythYwsqxxDTO);
                ythPpQlrCsDTO.setBdcdyh(qlfQlZxdjDO.getBdcdyh());
                //权利人信息
                ythYwsqBdcdyxxDTO.setZttGyQlrDTOList(ppQlrxx(ythPpQlrCsDTO));
                if (StringUtils.isNotBlank(qlfQlZxdjDO.getBdcqzh())) {
                    //根据产权证号获取yxmid
                    List<BdcXmDO> xmList = bdcXmMapper.getXsXmByBdcdyhAndBdcqzh(qlfQlZxdjDO.getBdcdyh(), qlfQlZxdjDO.getBdcqzh());
                    if (CollectionUtils.isEmpty(xmList)) {
                        throw new AppException("产权证号或不动产单元号不正确,未获取到对应的登记数据,无法创建业务");
                    }
                    ythYwsqBdcdyxxDTO.setYxmid(xmList.get(0).getXmid());
                    ythYwsqBdcdyxxDTO.setQllx(xmList.get(0).getQllx());
                }
                ythYwsqBdcdyxxDTO.setSxh(dealYthSxh(dyhSxhMap, qlfQlZxdjDO.getBdcdyh()));
                ythYwsqBdcdyxxDTOList.add(ythYwsqBdcdyxxDTO);


            }

        }


        return ythYwsqBdcdyxxDTOList;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 匹配当前项目对应权利人
     */
    private List<ZttGyQlrDTO> ppQlrxx(YthPpQlrCsDTO ythPpQlrCsDTO) {

        List<ZttGyQlrDTO> qlrList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ythPpQlrCsDTO.getYthYwsqxxDTO().getZTT_GY_QLR())) {
            List<ZttGyQlrDTO> qlrdlrList = new ArrayList<>();
            List<ZttGyQlrDTO> ywrdlrList = new ArrayList<>();
            for (ZttGyQlrDTO zttGyQlrDTO : ythPpQlrCsDTO.getYthYwsqxxDTO().getZTT_GY_QLR()) {
                //是否匹配
                boolean sfpp = false;
                if (StringUtils.equals(ythPpQlrCsDTO.getBdcdyh(), zttGyQlrDTO.getBdcdyh())) {
                    //权利人角色与权利类型进行对照
                    String dzqllx = commonService.dsfZdToBdcDm("DSF_ZD_JS_QLLX", "yth", zttGyQlrDTO.getJs());
                    LOGGER.info("第三方配置表读取qllx为：{}", dzqllx);
                    LOGGER.info("一体化角色为：{}", zttGyQlrDTO.getJs());
                    if (ythPpQlrCsDTO.getQllx() == null || (StringUtils.isNotBlank(dzqllx) && CommonUtil.indexOfStrs(dzqllx.split(CommonConstantUtils.ZF_YW_DH), ythPpQlrCsDTO.getQllx().toString()))) {
                        //预告业务需要根据预告登记种类进一步区分
                        if (CommonConstantUtils.QLLX_YG_DM.equals(ythPpQlrCsDTO.getQllx())) {
                            //权利人角色与预告登记种类进行对照
                            String dzygdjzl = commonService.dsfZdToBdcDm("DSF_ZD_JS_YGDJZL", "yth", zttGyQlrDTO.getJs());
                            if (ythPpQlrCsDTO.getYgdjzl() == null || (StringUtils.isNotBlank(dzygdjzl) && CommonUtil.indexOfStrs(dzygdjzl.split(CommonConstantUtils.ZF_YW_DH), ythPpQlrCsDTO.getYgdjzl().toString()))) {
                                sfpp = true;
                            }
                        } else {
                            sfpp = true;
                        }
                        if (sfpp) {
                            //权利人角色与权利人类别进行对照
                            String dzqlrlb = commonService.dsfZdToBdcDm("DSF_ZD_JS_QLRLB", "yth", zttGyQlrDTO.getJs());
                            if (StringUtils.equals("qlrdlr", dzqlrlb)) {
                                qlrdlrList.add(zttGyQlrDTO);
                            } else if (StringUtils.equals("ywrdlr", dzqlrlb)) {
                                ywrdlrList.add(zttGyQlrDTO);
                            } else if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, dzqlrlb) || StringUtils.equals(CommonConstantUtils.QLRLB_YWR, dzqlrlb)
                                    || StringUtils.equals(CommonConstantUtils.DSQLR_QLRLB_ZWR, dzqlrlb)) {
                                zttGyQlrDTO.setQlrlb(dzqlrlb);
                                qlrList.add(zttGyQlrDTO);
                            }
                        }
                    }
                }
            }

            //将代理人赋值到权利人实体中
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (ZttGyQlrDTO qlr : qlrList) {
                    ZttGyQlrDTO dlr = null;
                    if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, qlr.getQlrlb()) && CollectionUtils.isNotEmpty(qlrdlrList)) {
                        dlr = qlrdlrList.get(0);
                    } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, qlr.getQlrlb()) && CollectionUtils.isNotEmpty(ywrdlrList)) {
                        dlr = ywrdlrList.get(0);
                    }
                    if (dlr != null) {
                        qlr.setDlrmc(dlr.getQlrmc());
                        qlr.setDlrzjzl(dlr.getZjzl());
                        qlr.setDlrzjh(dlr.getZjh());
                        qlr.setDlrdh(dlr.getDh());
                    }
                }
            }
        }
        return qlrList;
    }

    /**
     * @param dyhSxhMap 单元号顺序号
     * @return 顺序号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理一体化顺序号
     */
    private Integer dealYthSxh(Map<String, Integer> dyhSxhMap, String bdcdyh) {
        if (dyhSxhMap.containsKey(bdcdyh)) {
            dyhSxhMap.put(bdcdyh, dyhSxhMap.get(bdcdyh) + 1);
        } else {
            dyhSxhMap.put(bdcdyh, 1);
        }
        return dyhSxhMap.get(bdcdyh);
    }

    /**
     * @return java.lang.Object
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [httpServletRequest]
     * @description 一体化以人查房
     */
    @Override
    public JSONArray queryYthYrcf(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            LOGGER.error("请求参数为空！");
            throw new AppException("请求参数为空！");
        }
        BdcYthYrcfQO bdcYthYrcfQO = convertParam(httpServletRequest);

        //身份证号转换
        if (StringUtils.isNotBlank(bdcYthYrcfQO.getZjh())) {
            bdcYthYrcfQO.setZjh(Stream.of(bdcYthYrcfQO.getZjh()).map(e ->
                    CardNumberTransformation.zjhTransformation(e)).collect(Collectors.joining(",")));
        }

        // 先根据查询参数查BDC_QLR,BDC_XM获取不动产单元号
        List<BdcXmDO> bdcXmDOS = bdcYthyrcfMapper.getBdcXmByBdcYthYrcfQO(bdcYthYrcfQO);
        List<String> bdcdyhList = bdcXmDOS.stream().filter(bdcXmDO -> !CommonConstantUtils.BDCLX_TZM_W.equals(BdcdyhToolUtils.getDzwTzm(bdcXmDO.getBdcdyh()))).collect(ArrayList<String>::new, (list, item) -> {
            list.add(item.getBdcdyh());
        }, ArrayList::addAll);
        JSONArray fwxx = new JSONArray();
        initFwxx(bdcYthYrcfQO, bdcdyhList, fwxx);
        List<String> tdbdcdyhList = bdcXmDOS.stream().filter(bdcXmDO -> CommonConstantUtils.BDCLX_TZM_W.equals(BdcdyhToolUtils.getDzwTzm(bdcXmDO.getBdcdyh()))).collect(ArrayList<String>::new, (list, item) -> {
            list.add(item.getBdcdyh());
        }, ArrayList::addAll);
        initFwxxWithTd(bdcYthYrcfQO, tdbdcdyhList, fwxx);
        return fwxx;
    }

    private boolean initFwxxWithTd(BdcYthYrcfQO bdcYthYrcfQO, List<String> bdcdyhList, JSONArray fwxx) {
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            LOGGER.warn("土地:未查询到符合条件的不动产单元号。查询参数为：" + bdcYthYrcfQO.toString());
            return true;
        }

        // 去除没有权利限制信息的不动产单元号
        List<String> bdcdyhs = new ArrayList<>();
        if (bdcdyhList.size() > 100) {
            int num = 0;
            do {
                List<String> subList = new ArrayList<>();
                if (bdcdyhList.size() < (num + 1) * 100) {
                    subList = bdcdyhList.subList(num * 100, bdcdyhList.size());
                } else {
                    subList = bdcdyhList.subList(num * 100, (num + 1) * 100);
                }
                bdcdyhs.addAll(bdcYthyrcfMapper.filterTdBdcdyh(subList));
                num = num + 1;
            } while (num * 100 < bdcdyhList.size());

        } else {
            bdcdyhs = bdcYthyrcfMapper.filterTdBdcdyh(bdcdyhList);
        }

        if (CollectionUtils.isEmpty(bdcdyhs)) {
            LOGGER.warn("土地:未查询到符合条件的不动产单元号。查询参数为：" + bdcYthYrcfQO.toString());
            return true;
        }

        // 不动产单元号分页
        List<String> bdcdyhPageList = bdcdyhPage(bdcdyhs, bdcYthYrcfQO);

        if (CollectionUtils.isEmpty(bdcdyhPageList)) {
            return true;
        }

        for (String bdcdyh : bdcdyhPageList) {
            // 根据bdcdyh查询登记库权利表
            BdcYthYrcfFwxx bdcYthYrcfFwxx = new BdcYthYrcfFwxx();
            bdcYthYrcfFwxx.setBdcDyaqDOList(queryBdcQl(bdcdyh, BdcDyaqDO.class, "0,1"));
            bdcYthYrcfFwxx.setBdcYyDOList(queryBdcQl(bdcdyh, BdcYyDO.class, "0,1"));
            bdcYthYrcfFwxx.setBdcCfDOList(queryBdcQl(bdcdyh, BdcCfDO.class, "0,1"));
            bdcYthYrcfFwxx.setBdcJsydsyqDOList(queryBdcQl(bdcdyh, BdcJsydsyqDO.class, "1"));
            // 查询权利所对应的权利人
            bdcYthYrcfFwxx.setBdcQlrDOList(queryBdcQlrDOList(bdcdyh, bdcYthYrcfFwxx));
            // 查询抵押和预抵押权利所对应的债务人人
            bdcYthYrcfFwxx.setBdcDsQlrDOList(queryBdcDsQlrDOList(bdcdyh, bdcYthYrcfFwxx));

            // 字段对照并处理特殊字段
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("QLT_FW_FDCQ_YZ", dozerMapList(bdcYthYrcfFwxx.getBdcFdcqDOList(), "fdcq", QltFwFdcqYzDO.class));
            jsonObject.put("QLF_QL_DYAQ", dozerMapList(bdcYthYrcfFwxx.getBdcDyaqDOList(), "dyaq", QlfQlDyaqDOExpandExchange.class));
//            jsonObject.put("QLF_QL_YGDJ", dozerMapList(bdcYthYrcfFwxx.getBdcYgDOList(), "ygdj", YthYgdjDTO.class));
            jsonObject.put("QLF_QL_YYDJ", dozerMapList(bdcYthYrcfFwxx.getBdcYyDOList(), "yydj", QlfQlYydjOldDO.class));
            jsonObject.put("QLF_QL_CFDJ", dozerMapList(bdcYthYrcfFwxx.getBdcCfDOList(), "cfdj", QlfQlCfdjOldDO.class));
            jsonObject.put("QLF_QL_JSYDSYQ", dozerMapList(bdcYthYrcfFwxx.getBdcJsydsyqDOList(), "jsydsyq", QlfQlJsydsyqOldDO.class));
            List<BdcQlrDO> allList = new ArrayList<>();
            allList.addAll(bdcYthYrcfFwxx.getBdcQlrDOList());
            allList.addAll(bdcYthYrcfFwxx.getBdcDsQlrDOList());
            jsonObject.put("ZTT_GY_QLR", dozerMapList(allList, "ythQlr", ZttGyQlrDTO.class));

            //处理QLF_QL_XZXZ
            List<QltQlXzxzDO> qltQlXzxzDOS = bdcdjMapper.queryXzxxForYthQltQlXzxzByBdcdyh(bdcdyh);
            JSONArray qltQlXzxzList = new JSONArray();
            for (Object sourceObject : qltQlXzxzDOS) {
                if (sourceObject == null) {
                    continue;
                }
                JSONObject upperCaseDestObject = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(sourceObject)));
                qltQlXzxzList.add(upperCaseDestObject);
            }
            jsonObject.put("QLF_QL_XZXZ", qltQlXzxzList);
            // 处理权籍数据
            jsonObject.put("KTT_ZDJBXX", queryZdjbxx(bdcdyh));

            fwxx.add(jsonObject);
        }
        return false;
    }

    private boolean initFwxx(BdcYthYrcfQO bdcYthYrcfQO, List<String> bdcdyhList, JSONArray fwxx) {
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            LOGGER.warn("未查询到符合条件的不动产单元号。查询参数为：" + bdcYthYrcfQO.toString());
            return true;
        }

        // 去除没有权利限制信息的不动产单元号
        List<String> bdcdyhs = new ArrayList<>();
        if (bdcdyhList.size() > 100) {
            int num = 0;
            do {
                List<String> subList = new ArrayList<>();
                if (bdcdyhList.size() < (num + 1) * 100) {
                    subList = bdcdyhList.subList(num * 100, bdcdyhList.size());
                } else {
                    subList = bdcdyhList.subList(num * 100, (num + 1) * 100);
                }
                bdcdyhs.addAll(bdcYthyrcfMapper.filterBdcdyh(subList));
                num = num + 1;
            } while (num * 100 < bdcdyhList.size());

        } else {
            bdcdyhs = bdcYthyrcfMapper.filterBdcdyh(bdcdyhList);
        }

        if (CollectionUtils.isEmpty(bdcdyhs)) {
            LOGGER.warn("未查询到符合条件的不动产单元号。查询参数为：" + bdcYthYrcfQO.toString());
            return true;
        }

        // 不动产单元号分页
        List<String> bdcdyhPageList = bdcdyhPage(bdcdyhs, bdcYthYrcfQO);

        if (CollectionUtils.isEmpty(bdcdyhPageList)) {
            return true;
        }

        for (String bdcdyh : bdcdyhPageList) {
            // 根据bdcdyh查询登记库权利表
            BdcYthYrcfFwxx bdcYthYrcfFwxx = new BdcYthYrcfFwxx();
            bdcYthYrcfFwxx.setBdcFdcqDOList(queryBdcQl(bdcdyh, BdcFdcqDO.class, "1,2"));
            bdcYthYrcfFwxx.setBdcDyaqDOList(queryBdcQl(bdcdyh, BdcDyaqDO.class, "0,1"));
            bdcYthYrcfFwxx.setBdcYgDOList(queryBdcQl(bdcdyh, BdcYgDO.class, "0,1"));
            bdcYthYrcfFwxx.setBdcYyDOList(queryBdcQl(bdcdyh, BdcYyDO.class, "0,1"));
            bdcYthYrcfFwxx.setBdcCfDOList(queryBdcQl(bdcdyh, BdcCfDO.class, "0,1"));
            bdcYthYrcfFwxx.setBdcJsydsyqDOList(queryBdcQl(bdcdyh, BdcJsydsyqDO.class, "1"));
            // 查询权利所对应的权利人
            bdcYthYrcfFwxx.setBdcQlrDOList(queryBdcQlrDOList(bdcdyh, bdcYthYrcfFwxx));
            // 查询抵押和预抵押权利所对应的债务人人
            bdcYthYrcfFwxx.setBdcDsQlrDOList(queryBdcDsQlrDOList(bdcdyh, bdcYthYrcfFwxx));
            // 字段对照并处理特殊字段
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("QLT_FW_FDCQ_YZ", dozerMapList(bdcYthYrcfFwxx.getBdcFdcqDOList(), "fdcq", QltFwFdcqYzOldDO.class));
            jsonObject.put("QLF_QL_DYAQ", dozerMapList(bdcYthYrcfFwxx.getBdcDyaqDOList(), "dyaq", QlfQlDyaqDOExpandExchange.class));
            jsonObject.put("QLF_QL_YGDJ", dozerMapList(bdcYthYrcfFwxx.getBdcYgDOList(), "ygdj", YthYgdjDTO.class));
            jsonObject.put("QLF_QL_YYDJ", dozerMapList(bdcYthYrcfFwxx.getBdcYyDOList(), "yydj", QlfQlYydjOldDO.class));
            jsonObject.put("QLF_QL_CFDJ", dozerMapList(bdcYthYrcfFwxx.getBdcCfDOList(), "cfdj", QlfQlCfdjOldDO.class));
            jsonObject.put("QLF_QL_JSYDSYQ", dozerMapList(bdcYthYrcfFwxx.getBdcJsydsyqDOList(), "jsydsyq", QlfQlJsydsyqOldDO.class));
            List<BdcQlrDO> allList = new ArrayList<>();
            allList.addAll(bdcYthYrcfFwxx.getBdcQlrDOList());
            allList.addAll(bdcYthYrcfFwxx.getBdcDsQlrDOList());
            jsonObject.put("ZTT_GY_QLR", dozerMapList(allList, "ythQlr", ZttGyQlrDTO.class));

            //处理QLF_QL_XZXZ
            List<QltQlXzxzDO> qltQlXzxzDOS = bdcdjMapper.queryXzxxForYthQltQlXzxzByBdcdyh(bdcdyh);
            JSONArray qltQlXzxzList = new JSONArray();
            for (Object sourceObject : qltQlXzxzDOS) {
                if (sourceObject == null) {
                    continue;
                }
                JSONObject upperCaseDestObject = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(sourceObject)));
                qltQlXzxzList.add(upperCaseDestObject);
            }
            jsonObject.put("QLF_QL_XZXZ", qltQlXzxzList);
            // 处理权籍数据
            jsonObject.put("KTT_ZDJBXX", queryZdjbxx(bdcdyh));
            jsonObject.put("KTT_FW_ZRZ", queryZrz(bdcdyh));
            jsonObject.put("KTT_FW_LJZ", queryLjz(bdcdyh));
            jsonObject.put("KTT_FW_H", queryFwH(bdcdyh));
            jsonObject.put("KTT_FW_C", queryFwC(bdcdyh));

            fwxx.add(jsonObject);
        }
        return false;
    }

    private List<String> bdcdyhPage(List<String> bdcdyhs, BdcYthYrcfQO bdcYthYrcfQO) {
        if (CollectionUtils.isEmpty(bdcdyhs)) {
            return null;
        }
        int pageNumber = bdcYthYrcfQO.getPageNumber();
        int size = bdcYthYrcfQO.getPageSize();
        int total = bdcdyhs.size();
        if (size * pageNumber > total) {
            return new ArrayList();
        }
        if (total > (pageNumber + 1) * size) {
            return bdcdyhs.subList(size * pageNumber, (pageNumber + 1) * size);
        } else {
            return bdcdyhs.subList(size * pageNumber, total);
        }
    }

    private List<BdcQlrDO> queryBdcQlrDOList(String bdcdyh, BdcYthYrcfFwxx bdcYthYrcfFwxx) {
        List<BdcQl> qlList = new ArrayList<>();
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcYthYrcfFwxx.getBdcFdcqDOList())) {
            qlList.addAll(bdcYthYrcfFwxx.getBdcFdcqDOList());
        }
        if (CollectionUtils.isNotEmpty(bdcYthYrcfFwxx.getBdcDyaqDOList())) {
            qlList.addAll(bdcYthYrcfFwxx.getBdcDyaqDOList());
        }
        if (CollectionUtils.isNotEmpty(bdcYthYrcfFwxx.getBdcYgDOList())) {
            qlList.addAll(bdcYthYrcfFwxx.getBdcYgDOList());
        }
        if (CollectionUtils.isNotEmpty(bdcYthYrcfFwxx.getBdcYyDOList())) {
            qlList.addAll(bdcYthYrcfFwxx.getBdcYyDOList());
        }
        if (CollectionUtils.isNotEmpty(bdcYthYrcfFwxx.getBdcCfDOList())) {
            qlList.addAll(bdcYthYrcfFwxx.getBdcCfDOList());
        }
        if (CollectionUtils.isNotEmpty(bdcYthYrcfFwxx.getBdcJsydsyqDOList())) {
            qlList.addAll(bdcYthYrcfFwxx.getBdcJsydsyqDOList());
        }
        if (CollectionUtils.isNotEmpty(qlList)) {
            for (BdcQl bdcQl : qlList) {
                if (StringUtils.isNotBlank(bdcQl.getXmid())) {
                    List<BdcQlrDO> qlrDOS = bdcYthyrcfMapper.getBdcQlrByBdcdyhAndXmid(bdcdyh, bdcQl.getXmid());
                    bdcQlrDOList.addAll(qlrDOS);
                }
            }
        }
        return bdcQlrDOList;
    }

    private List<BdcQlrDO> queryBdcDsQlrDOList(String bdcdyh, BdcYthYrcfFwxx bdcYthYrcfFwxx) {
        List<BdcQl> qlList = new ArrayList<>();
        List<BdcQlrDO> bdcDsQlrDOList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(bdcYthYrcfFwxx.getBdcDyaqDOList())) {
            //抵押增加债务人返回，先查dsfqlr表，没有在查义务人当债务人
            for (BdcDyaqDO dyaqDO : bdcYthYrcfFwxx.getBdcDyaqDOList()) {
                List<BdcQlrDO> dsQlr = bdcYthyrcfMapper.getBdcDsQlrByXmid(dyaqDO.getXmid());
                if (CollectionUtils.isNotEmpty(dsQlr)) {
                    bdcDsQlrDOList.addAll(dsQlr);
                } else {
                    List<BdcQlrDO> ywrList = commonService.listBdcQlrByXmid(dyaqDO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                    if (CollectionUtils.isNotEmpty(ywrList)) {
                        for (BdcQlrDO ywrQlr : ywrList) {
                            ywrQlr.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_ZWR);
                        }
                        bdcDsQlrDOList.addAll(ywrList);
                    }

                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcYthYrcfFwxx.getBdcYgDOList())) {
            //抵押增加债务人返回，先查dsfqlr表，没有在查义务人当债务人
            for (BdcYgDO ygDO : bdcYthYrcfFwxx.getBdcYgDOList()) {
                //预抵押需要债务人逻辑
                if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, ygDO.getYgdjzl())) {
                    List<BdcQlrDO> dsQlr = bdcYthyrcfMapper.getBdcDsQlrByXmid(ygDO.getXmid());
                    if (CollectionUtils.isNotEmpty(dsQlr)) {
                        bdcDsQlrDOList.addAll(dsQlr);
                    } else {
                        List<BdcQlrDO> ywrList = commonService.listBdcQlrByXmid(ygDO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                        if (CollectionUtils.isNotEmpty(ywrList)) {
                            for (BdcQlrDO ywrQlr : ywrList) {
                                ywrQlr.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_ZWR);
                            }
                            bdcDsQlrDOList.addAll(ywrList);
                        }
                    }
                }

            }
        }

        return bdcDsQlrDOList;
    }

    @Autowired
    private YthConvert ythConvert;
    @Autowired
    private BuildingZdConvertFeignService buildingZdConvertFeignService;

    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 一体化 推送预测楼盘信息
     */
    @Override
    public CommonResponse ythTsyclp(String fwDcbIndex,String qjgldm) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            //获取权籍库相关信息
            //逻辑幢信息
            FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwDcbIndex, qjgldm);
            //户室信息
            List<String> bdcdyhList = new ArrayList<>();
            List<FwYchsDO> fwYchsDOList = fwYcHsFeignService.listFwYchsByFwDcbIndex(fwDcbIndex, qjgldm);
            if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
                for (FwYchsDO fwYchsDO : fwYchsDOList) {
                    if (StringUtils.isNotBlank(fwYchsDO.getBdcdyh())) {
                        bdcdyhList.add(fwYchsDO.getBdcdyh());
                    }
                }
            }
            //获取地籍号
            if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getLszd()) && CollectionUtils.isNotEmpty(bdcdyhList)) {
                //获取区县代码
                String qxdm = bdcdyhList.get(0).substring(0, 6);
                String djh = fwLjzDO.getLszd();
                String zrzh = fwLjzDO.getZrzh();
                String ljzh = fwLjzDO.getLjzh();
                //宗地基本信息
                ZdDjdcbDO zdDjdcbDO = zdFeignService.queryZdByDjh(djh, qjgldm);
                Double zdmj = zdDjdcbDO.getFzmj() != null ? zdDjdcbDO.getFzmj() : zdDjdcbDO.getScmj();

                //宗地权利人信息
                List<ZdQlrDO> zdQlrDOS = zdFeignService.listZdQlrByDjh(djh, qjgldm);

                //查询字典表
                List<Map> zdDldm = buildingZdConvertFeignService.getZdTable("s_zd_dldm", SZdDldmDO.class);
                List<Map> zdList = buildingZdConvertFeignService.getZdTable("s_zd_fwyt", SZdFwytDO.class);
                //组织信息
                //获取用途名称字典项
                KttZdjbxxOldDO kttZdjbxxDO = ythConvert.getKttZdjbxxDOByZdDjdcbDO(zdDjdcbDO);
                jsonObject.put("KTT_ZDJBXX", initKttZdjbxxDO(kttZdjbxxDO, qxdm, zdmj, zdDldm));

                KttFwZrzDTO kttFwZrzDO = ythConvert.getKttFwZrzDOByFwLjzDO(fwLjzDO);
                jsonObject.put("KTT_FW_ZRZ", initKttFwZrzDO(kttFwZrzDO, qxdm, zdList));

                KttFwLjzOldDO kttFwLjzDO = ythConvert.getKttFwLjzDOByFwLjzDO(fwLjzDO);
                jsonObject.put("KTT_FW_LJZ", initKttFwLjzDO(kttFwLjzDO, qxdm, zdList));

                List<KttFwHDTO> kttFwHDTOS = ythConvert.getKttFwHDOListByFwHsDOList(fwYchsDOList);
                jsonObject.put("KTT_FW_H", initKttFwHDO(kttFwHDTOS, qxdm, kttZdjbxxDO.getZddm(), zrzh, ljzh, zdList));

                List<KttFwCOldDO> kttFwCDOS = ythConvert.getKttFwCDOListByFwHsDOList(fwYchsDOList);
                jsonObject.put("KTT_FW_C", initKttFwCDO(kttFwCDOS, qxdm, zrzh));

                List<ZttGyQlrDTO> zttGyQlr = ythConvert.getZttGyQlrListDTOByZdQlrDOList(zdQlrDOS);
                jsonObject.put("ZTT_GY_QLR", initZttGyQlrDTO(zttGyQlr));

                BdcXmQO bdcXmQO = new BdcXmQO();
                List<BdcXmDO> bdcXmDOS = new ArrayList<>();

                for (String bdcdyh : bdcdyhList) {
                    bdcXmQO.setBdcdyh(bdcdyh);
                    bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                    List<BdcXmDO> results = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(results)) {
                        bdcXmDOS.addAll(results);
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                    bdcXmDOS.forEach(
                            bdcXmDO -> {
                                //查询土地权利信息
                                List<QlfQlTdsyqOldDO> qlfQlTdsyqList = queryQlfQlTdsyq(bdcXmDO.getXmid());
                                if (CollectionUtils.isNotEmpty(qlfQlTdsyqList)) {
                                    if (jsonObject.containsKey("QLF_QL_TDSYQ")) {
                                        List tempList = jsonObject.getObject("QLF_QL_TDSYQ", List.class);
                                        tempList.addAll(qlfQlTdsyqList);
                                    } else {
                                        jsonObject.put("QLF_QL_TDSYQ", qlfQlTdsyqList);
                                    }
                                }
                                //建筑/宅基地使用权
                                List<QlfQlJsydsyqOldDO> qlfQlJsydsyqList = queryQlfQlJsydsyq(bdcXmDO.getXmid());
                                if (CollectionUtils.isNotEmpty(qlfQlJsydsyqList)) {
                                    if (jsonObject.containsKey("QLF_QL_JSYDSYQ")) {
                                        List tempList = jsonObject.getObject("QLF_QL_JSYDSYQ", List.class);
                                        tempList.addAll(qlfQlJsydsyqList);
                                    } else {
                                        jsonObject.put("QLF_QL_JSYDSYQ", qlfQlJsydsyqList);
                                    }
                                }
                            }
                    );
                }
            }
            Object response = exchangeBeanRequestService.request("yc_ythtsyclp", jsonObject);
            if (response != null) {
                LOGGER.info("推送一体化预测楼盘表信息返回内容:{}", JSON.toJSONString(response));
                YthCommonResponse ythCommonResponse = JSON.parseObject(JSON.toJSONString(response), YthCommonResponse.class);
                if (YthCommonResponse.SUCCESS.equals(ythCommonResponse.getType())) {
                    return CommonResponse.ok();
                } else {
                    return CommonResponse.fail(ythCommonResponse.getData());
                }
            } else {
                return CommonResponse.fail("调用一体化推送预测楼盘信息无返回结果");
            }
        }
        return CommonResponse.fail("入参异常");
    }

    @Autowired
    private FwHstFeignService fwHstFeignService;

    @Autowired
    private ECertificateFeignService eCertificateFeignService;

    @Autowired
    private StorageClientMatcher storageClient;

    /**
     * @param ythQueryDzzzxxDTO
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 一体化 查询电子证照信息
     */
    @Override
    public CommonResponse<JSONObject> ythQueyrDzzzxx(YthQueryDzzzxxDTO ythQueryDzzzxxDTO) {
        if (ythQueryDzzzxxDTO != null && StringUtils.isNotBlank(ythQueryDzzzxxDTO.getGrqch()) && StringUtils.isNotBlank(ythQueryDzzzxxDTO.getGrzjhm())) {
            //查询zzbs信息
            JSONObject responseJson = new JSONObject(2);
            List<BdcZsDO> bdcZsDOS = bdcdjMapper.queryZzbsByQlrmcAndZjh(ythQueryDzzzxxDTO);
            List<YthQueryDzzzxxResponse> responses = new ArrayList<>(bdcZsDOS.size() * 3);
            AtomicBoolean tempflag = new AtomicBoolean(true);
            if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
                try {
                    bdcZsDOS.forEach(bdcZsDO -> {
                        if (bdcZsDO != null && StringUtils.isNotBlank(bdcZsDO.getBdcdyh()) && StringUtils.isNotBlank(bdcZsDO.getZl())) {
                            //获取电子证照信息
                            if (StringUtils.isNotBlank(bdcZsDO.getZzbs()) && StringUtils.isNotBlank(bdcZsDO.getStorageid())) {
                                String base64Str = eCertificateFeignService.getECertificateContent(bdcZsDO.getZzbs());
                                StorageDto storageDto = storageClient.findById(bdcZsDO.getStorageid());
                                if (StringUtils.isNotBlank(base64Str) && storageDto != null && StringUtils.isNotBlank(storageDto.getDownUrl())) {
                                    responses.add(YthQueryDzzzxxResponse.YthQueryDzzzxxResponseBuilder.anYthQueryDzzzxxResponse().withFwzl(bdcZsDO.getZl())
                                            .withBdcdjdydm(bdcZsDO.getBdcdyh())
                                            .withBase64(base64Str)
                                            .withFileUrl(storageDto.getDownUrl())
                                            .withCertificateType("不动产权证")
                                            .withFileFormat("pdf")
                                            .withZzbm(bdcZsDO.getBdcqzh())
                                            .build());
                                }
                            }
                            //根据产权证号获取权籍管理代码
                            String qjgldm ="";
                            if(StringUtils.isNotBlank(bdcZsDO.getBdcqzh())) {
                                BdcQjgldmQO bdcQjgldmQO = new BdcQjgldmQO();
                                bdcQjgldmQO.setBdcqzh(bdcZsDO.getBdcqzh());
                                qjgldm = bdcXmfbFeignService.queryQjgldm(bdcQjgldmQO);
                            }

                            //获取宗地图
                            ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcZsDO.getBdcdyh(), qjgldm);
                            if (zdtResponseDTO != null && StringUtils.isNotBlank(zdtResponseDTO.getBase64())) {
                                responses.add(YthQueryDzzzxxResponse.YthQueryDzzzxxResponseBuilder.anYthQueryDzzzxxResponse().withFwzl(bdcZsDO.getZl())
                                        .withBdcdjdydm(bdcZsDO.getBdcdyh())
                                        .withBase64(zdtResponseDTO.getBase64())
                                        .withCertificateType("宗地图")
                                        .withFileFormat("jpg")
                                        .withZzbm(bdcZsDO.getBdcqzh())
                                        .build());
                            }
                            //获取户室图
                            String base64Hst = fwHstFeignService.queryFwHstBase64ByBdcdyh(bdcZsDO.getBdcdyh(), qjgldm);
                            if (StringUtils.isNotBlank(base64Hst)) {
                                responses.add(YthQueryDzzzxxResponse.YthQueryDzzzxxResponseBuilder.anYthQueryDzzzxxResponse().withFwzl(bdcZsDO.getZl())
                                        .withBdcdjdydm(bdcZsDO.getBdcdyh())
                                        .withBase64(base64Hst)
                                        .withCertificateType("房屋分层分户图")
                                        .withFileFormat("jpg")
                                        .withZzbm(bdcZsDO.getBdcqzh())
                                        .build());
                            }
                        } else {
                            throw new RuntimeException("查询到的bdczs信息关键字段缺失:" + JSON.toJSONString(bdcZsDO));
                        }
                    });
                    responseJson.put("total", responses.size());
                    responseJson.put("dataList", responses);
                    if (tempflag.get()) {
                        return CommonResponse.ok(responseJson);
                    } else {
                        return CommonResponse.fail("9998", "部分数据查询报错", responseJson);
                    }
                } catch (Exception e) {
                    LOGGER.error("组织图片数据失败:", e);
                    return CommonResponse.fail(e.getMessage());
                }

            } else {
                return CommonResponse.fail("未查询到相关的zs信息");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param ythYjxxRequestDTO
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 一体化 接收邮寄信息
     */
    @Override
    public JSONObject ythYjxx(YthYjxxRequestDTO ythYjxxRequestDTO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", "");
        jsonObject.put("type", "fail");

        //根据一体化业务编号查询登记业务信息，拿到gzlslid后查询和更新邮寄信息
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setSpxtywh(ythYjxxRequestDTO.getYwbh());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            String gzlslid = bdcXmDOList.get(0).getGzlslid();
            if (StringUtils.isNotBlank(gzlslid)) {
                //保存邮寄信息，已经存在邮寄信息，则返回推送失败，
                List<BdcSlYjxxDTO> bdcSlYjxxDTOS = bdcSlYjxxFeginService.queryBdcSlYjxxByGzlslid(gzlslid);
                if (CollectionUtils.isEmpty(bdcSlYjxxDTOS)) {
                    BdcSlYjxxDTO bdcSlYjxxDTO = new BdcSlYjxxDTO();
                    bdcSlYjxxDTO.setGzlslid(gzlslid);
                    bdcSlYjxxDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
                    bdcSlYjxxDTO.setWlddh(ythYjxxRequestDTO.getWaybillNo());
                    bdcSlYjxxDTO.setSjrxxdz(ythYjxxRequestDTO.getReceiverAddr());
                    String yjxxid = bdcSlYjxxFeginService.saveBdcSlYjxx(bdcSlYjxxDTO);
                    LOGGER.info("保存邮寄信息！yjxxid:{}", yjxxid);
                    //开始上传附件
                    if (CollectionUtils.isNotEmpty(ythYjxxRequestDTO.getFjxx())) {
                        StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, yjxxWjjmc, "");
                        if (storageDto != null) {
                            // 添加受理收件材料信息
                            if (StringUtils.isNotBlank(storageDto.getId())) {
                                bdcShijiService.addSjclxx(gzlslid, storageDto.getName(), storageDto.getId());
                            }
                            //上传附件
                            for (FileDTO fileDTO : ythYjxxRequestDTO.getFjxx()) {
                                LOGGER.info("开始上传邮寄附件！wjxx:{}", fileDTO.toString());
                                try {
                                    bdcShijiService.downAndUploadFjFromUrlThrowException(fileDTO.getFileUrl(), fileDTO.getFileName(), storageDto.getId());
                                    jsonObject.put("msg", "推送成功！");
                                    jsonObject.put("type", "success");
                                } catch (Exception e) {
                                    LOGGER.info("下载邮寄附件异常！{}", e.getMessage());
                                    jsonObject.put("msg", "下载文件异常！");
                                    jsonObject.put("type", "fail");
                                    return jsonObject;
                                }
                            }

                        }
                    }
                } else {
                    LOGGER.info("已存在邮寄信息，请核验数据！gzlslid:{}", gzlslid);
                    jsonObject.put("msg", "已存在邮寄信息，请核验数据！");
                    jsonObject.put("type", "fail");

                }
            } else {
                jsonObject.put("msg", "工作流实例id为空！请核验数据！");
                jsonObject.put("type", "fail");

            }
        } else {
            jsonObject.put("msg", "该一体化业务编号登记中不存在项目！");
            jsonObject.put("type", "fail");

        }

        return jsonObject;
    }

    private void initQueryDzzzErrorDto(List<YthQueryDzzzxxResponse> responses, BdcZsDO bdcZsDO, String errorMsg, String type) {
        responses.add(YthQueryDzzzxxResponse.YthQueryDzzzxxResponseBuilder.anYthQueryDzzzxxResponse().withFwzl(bdcZsDO.getZl())
                .withBdcdjdydm(bdcZsDO.getBdcdyh())
                .withBase64(errorMsg)
                .withCertificateType(type)
                .withFileFormat("pdf")
                .build());
    }

    private List<QlfQlTdsyqOldDO> queryQlfQlTdsyq(String xmid) {
        HashMap<String, String> map = new HashMap();
        map.put("ywh", xmid);
        List<QlfQlTdsyqOldDO> qlfQlTdsyqList = qlfQlTdsyqMapper.queryQlfQlTdsyqListOld(map);
        if (CollectionUtils.isEmpty(qlfQlTdsyqList)) {
            map.clear();
            map.put("yywh", xmid);
            qlfQlTdsyqList = qlfQlTdsyqMapper.queryQlfQlTdsyqListOld(map);
        }
        return qlfQlTdsyqList;
    }

    private List<QlfQlJsydsyqOldDO> queryQlfQlJsydsyq(String xmid) {
        HashMap<String, String> map = new HashMap();
        map.put("ywh", xmid);
        List<QlfQlJsydsyqOldDO> qlfQlJsydsyqList = jsydsyqMapperImpl.queryQlfQlJsydsyqListOld(map);
        if (CollectionUtils.isEmpty(qlfQlJsydsyqList)) {
            map.clear();
            map.put("yywh", xmid);
            qlfQlJsydsyqList = jsydsyqMapperImpl.queryQlfQlJsydsyqListOld(map);
        }
        return qlfQlJsydsyqList;
    }

    private JSONArray queryFwC(String bdcdyh) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        Map<String, String> map = new HashMap();
        map.put("ywh", bdcXmDOList.get(0).getXmid());
        map.put("bdcdyh", bdcdyh);
        List<KttFwCOldDO> kttFwCList = kttFwCMapper.queryKttFwCListForYrcf(map);
        JSONArray fwCJSONArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(kttFwCList)) {
            kttFwCList.forEach(KttFwCDO -> {
                JSONObject upperfwC = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(KttFwCDO)));
                fwCJSONArray.add(upperfwC);
            });
        }
        return fwCJSONArray;
    }

    private JSONArray queryFwH(String bdcdyh) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        Map<String, String> map = new HashMap();
        map.put("ywh", bdcXmDOList.get(0).getXmid());
        BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(bdcXmDOList.get(0).getXmid());
        if (bdcXmDO != null && CommonConstantUtils.BDCDYFWLX_DUZH.equals(bdcXmDO.getBdcdyfwlx())) {
            map.put("sfdz", "true");
        }
        List<KttFwHOldDO> kttFwHList = kttFwHMapper.queryKttFwHListForYrcf(map);
        for (KttFwHOldDO kttFwHDO : kttFwHList) {
            List<Map> zdMapList = bdcZdFeignService.queryBdcZd("fwyt");
            if (CollectionUtils.isNotEmpty(zdMapList)) {
                for (Map zdMap : zdMapList) {
                    if (StringUtils.equals(kttFwHDO.getFwyt1(), MapUtils.getString(zdMap, "DM"))) {
                        if (Objects.nonNull(zdMap.get("MC"))) {
                            kttFwHDO.setFwyt1(zdMap.get("MC").toString());
                        }
                    }
                    if (StringUtils.equals(kttFwHDO.getFwyt2(), MapUtils.getString(zdMap, "DM"))) {
                        if (Objects.nonNull(zdMap.get("MC"))) {
                            kttFwHDO.setFwyt2(zdMap.get("MC").toString());
                        }
                    }
                    if (StringUtils.equals(kttFwHDO.getFwyt3(), MapUtils.getString(zdMap, "DM"))) {
                        if (Objects.nonNull(zdMap.get("MC"))) {
                            kttFwHDO.setFwyt3(zdMap.get("MC").toString());
                        }
                    }
                }
            }
        }

        JSONArray fwHJSONArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(kttFwHList)) {
            kttFwHList.forEach(KttFwHOldDO -> {
                JSONObject upperFwh = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(KttFwHOldDO)));
                fwHJSONArray.add(upperFwh);
            });
        }
        return fwHJSONArray;
    }

    private JSONArray queryLjz(String bdcdyh) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        Map<String, String> map = new HashMap();
        map.put("ywh", bdcXmDOList.get(0).getXmid());
        map.put("bdcdyh", bdcdyh);
        // 先作为户室类型查询
        map.put("ishs", "true");
        List<KttFwLjzOldDO> kttFwLjzList = kttFwLjzMapper.queryKttFwLjzListForYrcf(map);
        if (CollectionUtils.isEmpty(kttFwLjzList)) {
            map.put("isychs", "true");
            map.remove("ishs");
            kttFwLjzList = kttFwLjzMapper.queryKttFwLjzListForYrcf(map);
        }
        if (CollectionUtils.isEmpty(kttFwLjzList)) {
            map.clear();
            map.put("ywh", bdcXmDOList.get(0).getXmid());
            // 在作为 独幢类型查询
            map.put("isyz", "true");
            kttFwLjzList = kttFwLjzMapper.queryKttFwLjzListForYrcf(map);

        }
        JSONArray ljzJSONArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(kttFwLjzList)) {
            kttFwLjzList.forEach(kttFwLjzOldDO -> {
                JSONObject upperLjz = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttFwLjzOldDO)));
                ljzJSONArray.add(convertKeyToUppercase(upperLjz));
            });
        }
        return ljzJSONArray;
    }

    private JSONArray queryZrz(String bdcdyh) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        Map map = new HashMap();
        map.put("ywh", bdcXmDOList.get(0).getXmid());
        List<Map> mapList = kttFwZrzMapper.queryKttFwZrzListForYrcf(map);
        JSONArray zrzJSONArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(mapList)) {
            for (Map zrzMap : mapList) {
                String jgsj = org.apache.commons.collections.MapUtils.getString(zrzMap, "JGRQ");
                Date jgsjDate = DateUtils.formatDate(jgsj);
                zrzMap.remove("JGRQ");
                KttFwZrzOldDO zrz = JSONObject.parseObject(JSONObject.toJSONString(zrzMap), KttFwZrzOldDO.class);
                zrz.setJgrq(jgsjDate);
                String bsm = bdcdjMapper.getBsm().toString();
                if (bsm.length() > 10) {
                    //大于十位取十位，不足则补全，作为唯一标识码固定为10位
                    bsm = bsm.substring(bsm.length() - 10);
                } else {
                    bsm = String.format("%010d", Integer.valueOf(bsm));
                }
                zrz.setBsm(Integer.valueOf(bsm));
                JSONObject upperZrz = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(zrz)));
                zrzJSONArray.add(upperZrz);
            }
        }
        return zrzJSONArray;
    }

    private JSONArray queryZdjbxx(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            return new JSONArray();
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        JSONArray zdjbxxArray = new JSONArray();
        Map paramMap = new HashMap();
        paramMap.put("bdcdyh", bdcdyh);
        paramMap.put("ywh", bdcXmDOList.get(0).getXmid());
        List<KttZdjbxxOldDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxListForYrcfOld(paramMap);
        if (CollectionUtils.isNotEmpty(kttZdjbxxList)) {
            for (KttZdjbxxOldDO kttZdjbxx : kttZdjbxxList) {
                String bsm = this.bdcdjMapper.getBsm().toString();
                if (bsm.length() > 10) {
                    //作为唯一标识码固定为10位
                    bsm = bsm.substring(bsm.length() - 10);
                } else {
                    bsm = String.format("%010d", Integer.valueOf(bsm));
                }
                kttZdjbxx.setBsm(Integer.valueOf(bsm));
                JSONObject upperZdjbxx = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttZdjbxx)));
                zdjbxxArray.add(upperZdjbxx);
            }
        }
        return zdjbxxArray;
    }

    private JSONArray initKttFwCDO(List<KttFwCOldDO> kttFwCDOList, String qxdm, String zrzh) {
        JSONArray fwCJSONArray = new JSONArray();
        Set<String> filterList = new HashSet<>(kttFwCDOList.size() / 2);
        if (CollectionUtils.isNotEmpty(kttFwCDOList)) {
            kttFwCDOList.forEach(kttFwCDO -> {
                if (filterList.add(kttFwCDO.getCh())) {
                    kttFwCDO.setQxdm(qxdm);
                    kttFwCDO.setZrzh(zrzh);
                    JSONObject upperfwC = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttFwCDO)));
                    upperfwC.remove("YSDM");
                    upperfwC.remove("BDCDYH");
                    fwCJSONArray.add(upperfwC);
                }
            });
        }
        return fwCJSONArray;
    }

    private JSONArray initZttGyQlrDTO(List<ZttGyQlrDTO> zttGyQlrDTOList) {
        JSONArray fwCJSONArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(zttGyQlrDTOList)) {
            zttGyQlrDTOList.forEach(kttFwCDO -> {
                JSONObject upperfwC = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttFwCDO)));
                upperfwC.remove("YSDM");
                fwCJSONArray.add(upperfwC);
            });
        }
        return fwCJSONArray;
    }

    private JSONArray initKttFwHDO(List<KttFwHDTO> kttFwHDOList, String qxdm, String zddm, String zrzh, String ljzh, List<Map> zdList) {
        JSONArray fwHJSONArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(kttFwHDOList)) {
            kttFwHDOList.forEach(kttFwHDO -> {
                kttFwHDO.setQxdm(qxdm);
                kttFwHDO.setZddm(zddm);
                kttFwHDO.setZrzh(zrzh);
                kttFwHDO.setLjzh(ljzh);
                if (StringUtils.isNotBlank(kttFwHDO.getFwyt1()) || StringUtils.isNotBlank(kttFwHDO.getFwyt2()) || StringUtils.isNotBlank(kttFwHDO.getFwyt3())) {
                    if (CollectionUtils.isNotEmpty(zdList)) {
                        LOGGER.info("开始转换s_zd_fwyt字典值");
                        zdList.forEach(map -> {
                            if (StringUtils.isNotBlank(kttFwHDO.getFwyt1()) && map.get("DM").equals(kttFwHDO.getFwyt1())) {
                                kttFwHDO.setFwyt1((String) map.get("MC"));
                            }

                            if (StringUtils.isNotBlank(kttFwHDO.getFwyt2()) && map.get("DM").equals(kttFwHDO.getFwyt2())) {
                                kttFwHDO.setFwyt2((String) map.get("MC"));
                            }

                            if (StringUtils.isNotBlank(kttFwHDO.getFwyt3()) && map.get("DM").equals(kttFwHDO.getFwyt3())) {
                                kttFwHDO.setFwyt3((String) map.get("MC"));
                            }

                        });
                    }
                }
                JSONObject upperFwh = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttFwHDO)));
                upperFwh.remove("YSDM");
                upperFwh.put("MJDW", "1");
                fwHJSONArray.add(upperFwh);
            });
        }
        return fwHJSONArray;
    }

    private JSONArray initKttFwLjzDO(KttFwLjzOldDO kttFwLjzDO, String qxdm, List<Map> zdList) {
        JSONArray ljzJSONArray = new JSONArray();
        if (kttFwLjzDO != null) {
            kttFwLjzDO.setQxdm(qxdm);
            if (StringUtils.isBlank(kttFwLjzDO.getJzwzt())) {
                kttFwLjzDO.setJzwzt("2");
            }
            if (StringUtils.isNotBlank(kttFwLjzDO.getFwyt1()) || StringUtils.isNotBlank(kttFwLjzDO.getFwyt2()) || StringUtils.isNotBlank(kttFwLjzDO.getFwyt3())) {
                if (CollectionUtils.isNotEmpty(zdList)) {
                    LOGGER.info("开始转换s_zd_fwyt字典值");
                    zdList.forEach(map -> {
                        if (StringUtils.isNotBlank(kttFwLjzDO.getFwyt1()) && map.get("DM").equals(kttFwLjzDO.getFwyt1())) {
                            kttFwLjzDO.setFwyt1((String) map.get("MC"));
                        }

                        if (StringUtils.isNotBlank(kttFwLjzDO.getFwyt2()) && map.get("DM").equals(kttFwLjzDO.getFwyt2())) {
                            kttFwLjzDO.setFwyt2((String) map.get("MC"));
                        }

                        if (StringUtils.isNotBlank(kttFwLjzDO.getFwyt3()) && map.get("DM").equals(kttFwLjzDO.getFwyt3())) {
                            kttFwLjzDO.setFwyt3((String) map.get("MC"));
                        }

                    });
                }
            }
            JSONObject upperLjz = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttFwLjzDO)));
            upperLjz.remove("YSDM");
            ljzJSONArray.add(convertKeyToUppercase(upperLjz));
        }
        return ljzJSONArray;
    }

    private JSONArray initKttFwZrzDO(KttFwZrzOldDO kttFwZrzDO, String qxdm, List<Map> zdList) {
        JSONArray zrzJSONArray = new JSONArray();
        if (kttFwZrzDO != null) {
            String bsm = bdcdjMapper.getBsm().toString();
            if (bsm.length() > 10) {
                //大于十位取十位，不足则补全，作为唯一标识码固定为10位
                bsm = bsm.substring(bsm.length() - 10);
            } else {
                bsm = String.format("%010d", Integer.valueOf(bsm));
            }
            kttFwZrzDO.setBsm(Integer.valueOf(bsm));
            kttFwZrzDO.setQxdm(qxdm);
            if (StringUtils.isNotBlank(kttFwZrzDO.getGhyt())) {
                if (CollectionUtils.isNotEmpty(zdList)) {
                    LOGGER.info("开始转换s_zd_fwyt字典值");
                    zdList.forEach(map -> {
                        if (map.get("DM").equals(kttFwZrzDO.getGhyt())) {
                            kttFwZrzDO.setJzwjbyt((String) map.get("MC"));
                            kttFwZrzDO.setGhyt((String) map.get("MC"));
                        }
                    });
                }
            }
            JSONObject upperZrz = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttFwZrzDO)));
            if (kttFwZrzDO.getJgrq() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String format = sdf.format(kttFwZrzDO.getJgrq());
                upperZrz.remove("JGRQ");
                upperZrz.put("JGRQ", format);
            }
            upperZrz.remove("YSDM");
            zrzJSONArray.add(upperZrz);
        }
        return zrzJSONArray;
    }

    private JSONArray initKttZdjbxxDO(KttZdjbxxOldDO kttZdjbxxDO, String qxdm, Double zdmj, List<Map> zdDldm) {
        JSONArray zdjbxxArray = new JSONArray();
        if (kttZdjbxxDO != null) {
            String bsm = this.bdcdjMapper.getBsm().toString();
            if (bsm.length() > 10) {
                //作为唯一标识码固定为10位
                bsm = bsm.substring(bsm.length() - 10);
            } else {
                bsm = String.format("%010d", Integer.valueOf(bsm));
            }
            kttZdjbxxDO.setBsm(Integer.valueOf(bsm));
            kttZdjbxxDO.setQxdm(qxdm);
            if (zdmj != null) {
                kttZdjbxxDO.setZdmj(zdmj.toString());
            }

            if (StringUtils.isNotBlank(kttZdjbxxDO.getYt())) {
                if (CollectionUtils.isNotEmpty(zdDldm)) {
                    LOGGER.info("开始转换s_zd_dldm字典值");
                    zdDldm.forEach(map -> {
                        if (map.get("DM").equals(kttZdjbxxDO.getYt())) {
                            kttZdjbxxDO.setYtmc((String) map.get("MC"));
                            kttZdjbxxDO.setYt((String) map.get("MC"));
                        }
                    });
                }

            }
            JSONObject upperZdjbxx = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttZdjbxxDO)));
            upperZdjbxx.remove("YSDM");
            zdjbxxArray.add(upperZdjbxx);
        }
        return zdjbxxArray;
    }

    /**
     * @return com.alibaba.fastjson.JSONArray
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [sourceList, mapId, clazz]
     * @description 对照权利，个别字段特殊处理
     */
    public <T> JSONArray dozerMapList(List sourceList, String mapId, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new JSONArray(0);
        }
        JSONArray destinationList = new JSONArray();
        try {
            for (Object sourceObject : sourceList) {
                if (sourceObject == null) {
                    continue;
                }
                T destinObject = clazz.newInstance();
                dozerBeanMapper.map(sourceObject, destinObject, mapId);
                JSONObject upperCaseDestObject = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(destinObject)));
                dealSpecialField(sourceObject, upperCaseDestObject);
                destinationList.add(upperCaseDestObject);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("字段对照时出现异常，mapId:" + mapId);
            return new JSONArray(0);
        }
        return destinationList;
    }

    /**
     * @return com.alibaba.fastjson.JSONObject
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [sourceObject, upperCaseDestObject]
     * @description 处理各种权利特殊字段
     */
    private JSONObject dealSpecialField(Object sourceObject, JSONObject upperCaseDestObject) {
        JSONObject sourceJsonObject = JSONObject.parseObject(JSONObject.toJSONString(sourceObject));
        if (StringUtils.isBlank(sourceJsonObject.getString("xmid"))) {
            LOGGER.warn("该对象缺少xmid,无法处理权利特殊字段！该对象为：" + JSONObject.toJSONString(sourceObject));
            return upperCaseDestObject;
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(sourceJsonObject.getString("xmid"));
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            // 添加业务号 为项目id
            upperCaseDestObject.put("YWH", bdcXmDO.getXmid());

            if (sourceObject instanceof BdcFdcqDO) {
                upperCaseDestObject.put("BDCQZH", bdcXmDO.getBdcqzh());
                List<Map> zdMapList = bdcZdFeignService.queryBdcZd("bdclx");
                String bdclx = "";
                if (CollectionUtils.isNotEmpty(zdMapList) && bdcXmDO != null && bdcXmDO.getBdclx() != null) {
                    for (Map map : zdMapList) {
                        if (StringUtils.equals(bdcXmDO.getBdclx().toString(), MapUtils.getString(map, "DM"))) {
                            if (Objects.nonNull(map.get("MC"))) {
                                bdclx = map.get("MC").toString();
                            }
                        }
                    }
                }
                upperCaseDestObject.put("BDCLX", bdclx);
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) sourceObject;
                if (bdcFdcqDO.getQszt() != null && CommonConstantUtils.QSZT_VALID.equals(bdcFdcqDO.getQszt())) {
                    upperCaseDestObject.put("QLXZ", bdcXmDO.getQlxz());
                }
            }

            if (sourceObject instanceof BdcDyaqDO) {
                upperCaseDestObject.put("BDCDJZMH", bdcXmDO.getBdcqzh());
                // 增加抵押人（"DYR"），为抵押的义务人。
                BdcQlrQO qlrQO = new BdcQlrQO();
                qlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                qlrQO.setXmid(bdcXmDO.getXmid());
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(qlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    bdcQlrDOList.forEach(bdcQlrDO -> stringBuilder.append(bdcQlrDO.getQlrmc()).append(","));
                    String dyr = stringBuilder.toString();
                    if (StringUtils.isNotBlank(dyr)) {
                        dyr = dyr.substring(0, dyr.length() - 1);
                        upperCaseDestObject.put("DYR", dyr);
                    }
                }
            }
            if (sourceObject instanceof BdcYgDO) {
                BdcYgDO bdcYgDO = (BdcYgDO) sourceObject;
                upperCaseDestObject.put("BDCDJZMH", bdcXmDO.getBdcqzh());
                upperCaseDestObject.put("ZXYGYY", bdcYgDO.getZxygyy());
                if (null != bdcYgDO.getZxygdjsj()) {
                    upperCaseDestObject.put("ZXSJ", DateFormatUtils.format(bdcYgDO.getZxygdjsj(), DateUtils.sdf_ymdhms));
                }
                upperCaseDestObject.put("ZXYGYWH", bdcYgDO.getZxygywh());
                upperCaseDestObject.put("YTMC", upperCaseDestObject.getString("GHYT"));
                upperCaseDestObject.put("YWR", bdcXmDO.getYwr());
                upperCaseDestObject.put("YWRZJZL", bdcXmDO.getYwrzjzl());
                upperCaseDestObject.put("YWRZJH", bdcXmDO.getYwrzjh());
                List<Map> zdMapList = bdcZdFeignService.queryBdcZd("bdclx");
                String bdclx = "";
                if (CollectionUtils.isNotEmpty(zdMapList) && bdcXmDO != null && bdcXmDO.getBdclx() != null) {
                    for (Map map : zdMapList) {
                        if (StringUtils.equals(bdcXmDO.getBdclx().toString(), MapUtils.getString(map, "DM"))) {
                            if (Objects.nonNull(map.get("MC"))) {
                                bdclx = map.get("MC").toString();
                            }
                        }
                    }
                }
                upperCaseDestObject.put("BDCLX", bdclx);
                // 当预告登记种类是预抵押（3和4）, 预告权利中增加返回ZWLXQSSJ，ZWLXJSSJ，DYFS三个字段
                if (bdcYgDO.getYgdjzl() != null && (bdcYgDO.getYgdjzl().equals(3) || bdcYgDO.getYgdjzl().equals(4))) {
                    upperCaseDestObject.put("DYFS", bdcYgDO.getDyfs());
                    if (null != bdcYgDO.getZwlxqssj()) {
                        upperCaseDestObject.put("ZWLXQSSJ", DateFormatUtils.format(bdcYgDO.getZwlxqssj(), DateUtils.sdf_ymdhms));
                    }
                    if (null != bdcYgDO.getZwlxjssj()) {
                        upperCaseDestObject.put("ZWLXJSSJ", DateFormatUtils.format(bdcYgDO.getZwlxjssj(), DateUtils.sdf_ymdhms));
                    }
                }
            }

            if (sourceObject instanceof BdcYyDO) {
                upperCaseDestObject.put("BDCDJZMH", bdcXmDO.getBdcqzh());
            }

            if (sourceObject instanceof BdcJsydsyqDO) {
                if (StringUtils.isNotBlank(upperCaseDestObject.getString("BDCDYH")) && upperCaseDestObject.getString("BDCDYH").length() >= 19) {
                    upperCaseDestObject.put("ZDDM", upperCaseDestObject.getString("BDCDYH").substring(0, 19));
                }
                List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(((BdcJsydsyqDO) sourceObject).getXmid());
                if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                    upperCaseDestObject.put("BDCQZH", bdcZsDOList.get(0).getBdcqzh());
                }
            }

            if (sourceObject instanceof BdcQlrDO) {
                BdcQlrDO bdcQlrDO = (BdcQlrDO) sourceObject;
                List<Map> zdMapList = bdcZdFeignService.queryBdcZd("qlrlb");
                List<Map> dszdMapList = bdcZdFeignService.queryBdcZd("dsqlrlb");
                if (ArrayUtils.contains(CommonConstantUtils.QLRLB_QLR_ARR, bdcQlrDO.getQlrlb())) {
                    if (CollectionUtils.isNotEmpty(zdMapList)) {
                        for (Map map : zdMapList) {
                            if (StringUtils.equals(bdcQlrDO.getQlrlb(), MapUtils.getString(map, "DM"))) {
                                if (Objects.nonNull(map.get("MC"))) {
                                    upperCaseDestObject.put("JS", map.get("MC").toString());
                                }
                            }
                        }
                    }
                } else {
                    if (CollectionUtils.isNotEmpty(dszdMapList)) {
                        for (Map map : dszdMapList) {
                            if (StringUtils.equals(bdcQlrDO.getQlrlb(), MapUtils.getString(map, "DM"))) {
                                if (Objects.nonNull(map.get("MC"))) {
                                    upperCaseDestObject.put("JS", map.get("MC").toString());
                                }
                            }
                        }
                    }
                }


                upperCaseDestObject.put("BDCDYH", bdcXmDO.getBdcdyh());

            }
        }
        return upperCaseDestObject;
    }

    /**
     * @return com.alibaba.fastjson.JSONObject
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [destinObject]
     * @description 将JSONObject的KEY改为大写
     */
    private JSONObject convertKeyToUppercase(JSONObject destinObject) {
        if (destinObject == null || CollectionUtils.isEmpty(destinObject.keySet())) {
            return new JSONObject();
        }
        Set<String> keys = destinObject.keySet();
        JSONObject upperCaseDestObject = new JSONObject();
        for (String key : keys) {
            upperCaseDestObject.put(key.toUpperCase(), destinObject.get(key));
        }
        return upperCaseDestObject;
    }

    /**
     * @return boolean
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcYthYrcfFwxx]
     * @description 过滤权利为空数据，当所有权利都为空时返回false
     */
    private boolean filterEmptyQl(BdcYthYrcfFwxx bdcYthYrcfFwxx) {
        if (CollectionUtils.isEmpty(bdcYthYrcfFwxx.getBdcFdcqDOList()) && CollectionUtils.isEmpty(bdcYthYrcfFwxx.getBdcDyaqDOList())
                && CollectionUtils.isEmpty(bdcYthYrcfFwxx.getBdcYgDOList()) && CollectionUtils.isEmpty(bdcYthYrcfFwxx.getBdcYyDOList())
                && CollectionUtils.isEmpty(bdcYthYrcfFwxx.getBdcCfDOList())) {
            return false;
        }
        return true;
    }

    /**
     * @return java.util.List<T>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcdyh, tClass, qsztString]
     * @description 查询权利表
     */
    public <T> List<T> queryBdcQl(String bdcdyh, Class<T> tClass, String qsztString) {
        if (StringUtils.isNotBlank(bdcdyh) && tClass != null) {
            Example example = new Example(tClass);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bdcdyh", bdcdyh);
            if (StringUtils.isNotBlank(qsztString)) {
                String[] qsztArr = qsztString.split(",");
                criteria.andIn("qszt", Arrays.asList(qsztArr));
            }
            List<T> resultList = entityMapper.selectByExample(example);
            return resultList;
        }
        return null;
    }

    /**
     * @return cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcYthYrcfQO
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [httpServletRequest]
     * @description 转化查询参数
     */
    private BdcYthYrcfQO convertParam(HttpServletRequest httpServletRequest) {
        BdcYthYrcfQO bdcYthYrcfQO = new BdcYthYrcfQO();
        bdcYthYrcfQO.setBdcdyh(httpServletRequest.getParameter("BDCDYH"));
        bdcYthYrcfQO.setBdcqzh(httpServletRequest.getParameter("BDCQZH"));
        bdcYthYrcfQO.setQlrmc(httpServletRequest.getParameter("QLRMC"));
        bdcYthYrcfQO.setZjh(httpServletRequest.getParameter("ZJH"));
        bdcYthYrcfQO.setZl(httpServletRequest.getParameter("ZL"));
        bdcYthYrcfQO.setPageNumber(Integer.parseInt(httpServletRequest.getParameter("PAGENUM")) - 1);
        bdcYthYrcfQO.setPageSize(Integer.parseInt(httpServletRequest.getParameter("PAGESIZE")));
        return bdcYthYrcfQO;
    }


}
