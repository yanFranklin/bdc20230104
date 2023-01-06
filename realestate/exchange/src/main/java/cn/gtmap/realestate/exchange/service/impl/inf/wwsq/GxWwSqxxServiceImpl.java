package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.gtc.workflow.utils.BeanUtil;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.SZdTdsyqlxDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.common.core.domain.exchange.yth.QltQlXzxzDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.etl.HtbaxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.FwxxbybdcdyhResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclmxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.InitZwrXxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.dydjxxcx.request.DydjxxcxCxQO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.dydjxxcx.response.WwsqDydjfxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.request.BdcdyxxCxQO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.response.WwsqBdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.request.GzwxxcxQO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.response.GzwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.cfjfdj.CfjfdjResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.cfjfdj.CourtCfResponseDTo;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.cfjfdj.CourtCfxmxxResponseDTo;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmLsgxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFwqlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYthYrcfFwxx;
import cn.gtmap.realestate.common.core.dto.portal.WorkFlowCommonDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhSyqkQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcGdxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcYzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.portal.BdcWorkFlowFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcShxxRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.bo.grdacx.GrdacxData;
import cn.gtmap.realestate.exchange.core.bo.grdacx.GrdacxModel;
import cn.gtmap.realestate.exchange.core.bo.wwsq.ParamBody;
import cn.gtmap.realestate.exchange.core.domain.GxRygfqkDO;
import cn.gtmap.realestate.exchange.core.domain.GxWwSqxxDO;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.changzhou.dv.DvDyzx;
import cn.gtmap.realestate.exchange.core.dto.changzhou.wwsq.response.ZwbzshyDTO;
import cn.gtmap.realestate.exchange.core.dto.common.*;
import cn.gtmap.realestate.exchange.core.dto.hefei.fcjyxgxx.response.FcXgxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.hefei.fcjyxgxx.response.FcXgxxResponseModel;
import cn.gtmap.realestate.exchange.core.dto.wwsq.QlrlistResponse;
import cn.gtmap.realestate.exchange.core.dto.wwsq.cxqlrcqxx.request.QlrRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.cxqlrcqxx.response.CqxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dycxcq.request.DycxcqRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dycxcq.response.DycxcqResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dyizm.request.DyicxQO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dyizm.response.DyizmDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dyxxAndYgdyxx.BdcFdcqDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dyxxAndYgdyxx.BdcJsydsyqDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.fwxxwithqxdmzl.request.FwxxwithqxdmzlRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.gdxx.request.BdcGdxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getDzzzzip.request.BdcDzzzzipRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getDzzzzip.response.BdcDzzzzipResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqCqzxx.response.GetWwsqCqzxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqCqzxx.response.GetWwsqCqzxxResponseQlr;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqCqzxxFy.response.GetWwsqCqzxxFyResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqCqzxxFy.response.GetWwsqCqzxxResponseCqxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.request.GetYgxxRequestData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.response.DvYdy;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.response.GetYgxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.response.GetYgxxResponseQlr;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.response.GetYgxxResponseYgxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.gfxx.request.QlrGfxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.gfxx.response.QlrGfxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.gzwsyqcx.request.GzwsyqcxQO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.gzwsyqcx.response.GzwsqyDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitSjrxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj.*;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.diyi.InitZyDyRequestDyixx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.dydj.InitRequestSfxmxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.dydj.InitRequestSfxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.gzwxx.InitZyDyGzwxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.lq.InitZyDyRequestLqxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.tdsyq.InitZyDyRequestTdsyqxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.yy.InitZydyRequestYyxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy.InitRequestLzr;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.zydy.*;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxx.request.JtcyxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxx.response.JtBdcxxRespDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxx.response.JtBdcxxRespVO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxx.response.JtQlrRespDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxxTycx.request.JtBdcxxTycxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxxTycx.response.JtBdcxxTycxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.queryPpgx.response.PpgxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.querySpfHtbaxx.request.SpfHtbaxxQO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.querySpfHtbaxx.response.SpfHtbaxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.rygfqk.request.RygfqkRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.sfdpdf.request.SfdPdfRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.spfBaxx.response.SpfBaxxQlrResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.spfBaxx.response.SpfBaxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.tdsyqcx.response.TdsyqDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.wwsqdeltask.request.WwsqdeltaskRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ycfw.YcfwCxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ychsbyysfwbm.response.YchsByYsfwbmResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.yyzmcx.request.YycxQO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.yyzmcx.response.YyzmDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.updatefjxx.fjxxRequest;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.QlfQlDyaqDOExpandExchange;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYgdjDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.ZttGyQlrDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlCfdjOldDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlJsydsyqOldDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlYydjOldDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QltFwFdcqYzOldDO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestQlr;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.*;
import cn.gtmap.realestate.exchange.core.mapper.server.*;
import cn.gtmap.realestate.exchange.service.impl.BdcGjbwServiceImpl;
import cn.gtmap.realestate.exchange.service.impl.inf.hefei.FcjyServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.FjclService;
import cn.gtmap.realestate.exchange.service.inf.GrdacxService;
import cn.gtmap.realestate.exchange.service.inf.hefei.BdcFyService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.GxWwSqxxService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.SendMsgService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.HtmlToPdf;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.enums.ResponseCodeEnum;
import cn.gtmap.realestate.exchange.util.enums.SjptCxqlEnum;
import cn.gtmap.realestate.exchange.web.rest.ExchangeInterfaceRestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.open.common.util.Base64Utils;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 共享外网信息实现类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/15 15:04
 */
@Service
@Validated
public class GxWwSqxxServiceImpl implements GxWwSqxxService {
    /**
     * dyxxAndYgdyxx接口涉及字段定义
     */
    public static final String CQXMID = "CQXMID";
    public static final String CQZH = "CQZH";
    public static final String CQMJ = "CQMJ";
    public static final String TYPE = "TYPE";
    public static final String CQDY = "cqdy";
    public static final String TDZH = "TDZH";

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Value("${slRoleCode.sfcz:}")
    private String slRoleCode;

    @Value("${wwsq.zdzfyz:true}")
    private Boolean zdzfyz;

    // 查询产权区县代码集合
    @Value("#{'${cxqlrcqxx.qxdmjh:}'.split(',')}")
    private List<String> cxCqQxdmjh;

    // 查询产权权利类型集合
    @Value("#{'${cxqlrcqxx.qllxjh:3,4,5,6,7,8,96}'.split(',')}")
    private List<Integer> cxCqQllxjh;

    // 查询产权权利类型集合
    @Value("#{'${queryJtBdcdjXx.qllxjh:4}'.split(',')}")
    private List<Integer> cxJtBdcQllxjh;

    @Autowired
    private BdcDjbxxFeignService djbxxFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    private DjxxFeignService djxxFeignService;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    BdcGdxxFeignService bdcGdxxFeignService;

    @Autowired
    private BdcFyService bdcFyService;

    @Autowired
    BdcGjbwServiceImpl bdcQjService;

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GxWwSqxxServiceImpl.class);
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    private DozerBeanMapper exchangeDozerMapper;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;

    @Autowired
    private BdcInitFeignService bdcInitFeignService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcZfxxCxFeignService bdcZfxxCxFeignService;
    @Autowired
    private BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    private Repo repository;
    @Autowired
    private TaskHandleClientMatcher taskHandleClient;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcSlFeignService bdcSlFeignService;
    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private WwsqMapper wwsqMapper;
    @Autowired
    private BdcDjbxxFeignService bdcDjbxxFeignService;

    @Autowired
    private BdcSlJyxxFeignService bdcSlJyxxFeignService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private CommonService commonService;

    @Resource(name = "standardGrdacx")
    private GrdacxService grdacxService;

    @Autowired
    private BdcSlPrintFeignService bdcSlPrintFeignService;

    @Autowired
    private FjclService fjclService;

    @Autowired
    private HtmlToPdf htmlToPdf;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    private BdcPpFeignService bdcPpFeignService;

    @Autowired
    private ViewMapper viewMapper;

    @Value("${wwsq.sczmd.gzldyid:}")
    private String sczmdGzldyid;
    @Value("${data.version:}")
    private String dataversion;
    @Value("${ykq.swsys.version:dk}")
    private String swsysVersion;
    @Value("${dyzx.zdbj:true}")
    private boolean canZdbj;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private FcjyServiceImpl fcjyService;

    @Autowired
    private HfXzqFeignService hfXzqFeignService;

    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcShxxRestService bdcShxxRestService;
    @Autowired
    BdcYzhFeignService bdcYzhFeignService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    FwHsFeignService fwHsFeignService;

    @Autowired
    FwHstFeignService fwHstFeignService;

    @Autowired
    ZdFeignService zdFeignService;

    @Autowired
    BdcWorkFlowFeignService bdcWorkFlowFeignService;

    @Autowired
    private BdcSwFeignService bdcSwFeignService;

    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;

    @Autowired
    private SendMsgService sendMsgService;

    @Autowired
    BdcYthyrcfMapper bdcYthyrcfMapper;

    @Value("${wwsq.zdzf.jdmc:}")
    private String zdzfJdmc;

    /**
     * 组合贷款工作流定义id
     */
    @Value("#{'${zhdk.gzldyid:}'.split(',')}")
    private List<String> zhdkGzldyidList;


    /**
     * 自动办结工作流定义id
     */
    @Value("#{'${initwwsq.zdbjlist:}'.split(',')}")
    private List<String> initwwsqZdbjList;
    @Value("${initwwsq.zdbjslr:}")
    private String zdbjslr;

    @Value("${fjxx.sljdmc: 受理}")
    private String fjxxSljdmc;
    @Value("${fjxx.shjdmc: 审核}")
    private String fjxxShjdmc;

    /**
     * 组合流程是否使用推送的受理人
     */
    @Value("${zhlc.tsslr: false}")
    private Boolean zhlcTsslr;

    @Value("${wwsq.turnworkbyslr:true}")
    private Boolean turnWorkBySlr;

    /**
     * 首次抵押工作流定义id
     */
    @Value("#{'${dydj.scdy.gzldyid:}'.split(',')}")
    private List<String> scdyGzldyidList;

    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    BdcdjMapper bdcdjMapper;

    // 抵押信息和预抵押信息查询产权权利类型集合
    @Value("#{'${dyxxandydyxx.xmcqqllx: 4,6,8}'.split(',')}")
    private List<Integer> xmcqQllx;

    @Autowired
    private StorageClientMatcher storageClient;
    /**
     * 附件异步下载线程池
     */
    private ThreadPoolExecutor fjThreadPool = new ThreadPoolExecutor(
            // 核心线程数量
            2,
            // 最大线程数量
            2,
            // 空闲等待
            60L, TimeUnit.SECONDS,
            // 阻塞队列100
            new ArrayBlockingQueue<>(100),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 获取办件节点接口
     *
     * @param slbh
     * @return 活动节点  办结显示已办结
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public String getWwsqzt(@NotBlank(message = "受理编号不能为空") String slbh) {
        String jdzt = "";
        List<BdcXmDO> listXm = commonService.listBdcXmBySlbh(slbh);
        if (CollectionUtils.isNotEmpty(listXm)) {
            BdcXmDO bdcXmDO = listXm.get(0);
            if (bdcXmDO != null) {
                if (CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDO.getAjzt())) {
                    jdzt = "已办结";
                } else if (StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
                    List<TaskData> list = processTaskClient.processLastTasks(bdcXmDO.getGzlslid());
                    if (CollectionUtils.isNotEmpty(list)) {
                        jdzt = list.get(0).getTaskName();
                    }
                }
            }
        }
        return jdzt;
    }

    /**
     * @param dto
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除任务
     */
    @Override
    public Map<String, Object> deleteTask(WwsqdeltaskRequestDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "1000");
        resultMap.put("msg", "删除失败");

        if (StringUtils.isNotBlank(dto.getProcessInstanceId())) {
            // 验证是否已登簿 如果登簿不给删除
            if (checkSlzt(dto.getProcessInstanceId())) {
                resultMap.put("msg", "项目已被认领，不允许删除");
            } else {
                // 获取工作流定义 id 对比判断是否属于 特殊流程，直接执行大云删除方法。
                ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(dto.getProcessInstanceId());
                if (processInstanceData != null) {
                    try {
                        WorkFlowCommonDTO workFlowCommonDTO = new WorkFlowCommonDTO();
                        workFlowCommonDTO.setProcessInstanceId(dto.getProcessInstanceId());
                        workFlowCommonDTO.setReason(dto.getReason());
                        List<WorkFlowCommonDTO> workFlowCommonDTOS = new ArrayList<>(1);
                        workFlowCommonDTOS.add(workFlowCommonDTO);
                        bdcWorkFlowFeignService.feignDeleteTask(workFlowCommonDTOS);

                        resultMap.put("code", "0000");
                        resultMap.put("msg", "删除成功");
                    } catch (Exception e) {
                        resultMap.put("msg", "删除异常：");
                        LOGGER.error("删除登记数据错误:{}", dto.getProcessInstanceId(), e);
                    }
                } else {
                    resultMap.put("msg", "工作流实例为空");
                }
            }
        } else {
            resultMap.put("msg", "工作流实例ID不能为空");
        }
        return resultMap;
    }

    /**
     * @param gzlslid
     * @return boolea
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 验证是否处在受理节点并无受理人：false 否则：true
     */
    private boolean checkSlzt(String gzlslid) {
        // 查询登簿状态
        List<BdcXmDO> bdcXmDOList = commonService.listBdcXmByGzlslid(gzlslid);
        boolean issljd = flowableNodeClient.isStartUserTaskRunning(gzlslid);
        if (!issljd) {
            return true;
        }
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (StringUtils.isNotBlank(bdcXmDO.getSlr())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 抵押首次获取产权证信息
     *
     * @param cqzh
     * @param dyr
     * @param zl
     * @param dyrzjh
     * @return 响应结构
     */
    @Override
    public List<Map> getBdczsxx(String cqzh, String dyr, String zl, String dyrzjh, String xmid) {
        //参数封装
        Map paramMap = new HashMap();
        paramMap.put("qlrmc", dyr);
        if (StringUtils.isNotBlank(dyrzjh)) {
            // 增加15  18位 QLR 转换
            String revertZjhs = CardNumberTransformation.zjhTransformation(dyrzjh);
            String[] zjhArr = revertZjhs.split(",");
            paramMap.put("zjhArr", Arrays.asList(zjhArr));
        }
        paramMap.put("zl", zl);
        paramMap.put("likebdcqzh", cqzh);
        paramMap.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
        paramMap.put("ajzt", CommonConstantUtils.AJZT_YB_DM);
        paramMap.put("qszt", CommonConstantUtils.QSZT_VALID);
        paramMap.put("xmid", xmid);
        //查询
        List<Map> listCqxx = wwsqMapper.getBdczsxx(paramMap);
        if (CollectionUtils.isNotEmpty(listCqxx)) {
            for (Map map : listCqxx) {
                if (StringUtils.isNotBlank(MapUtils.getString(map, "xmid"))) {
                    //查找关联的土地证信息
                    map.put("gltdzxx", getGltdzxx(MapUtils.getString(map, "xmid")));

                    map.put("zsxmid", MapUtils.getString(map, "xmid"));
                    //处理交易价格为空
                    if(StringUtils.isBlank(MapUtils.getString(map, "jyjg"))){
                        map.put("jyjg", "");
                    }else{
                        map.put("jyjg", MapUtils.getString(map, "jyjg"));
                    }
                    //权利人处理数据
                    map.put("qlr", qlrsjConvert(MapUtils.getString(map, "xmid"), CommonConstantUtils.QLRLB_QLR, "CommonQlr"));
                    if (StringUtils.isNotBlank(MapUtils.getString(map, "bdcdyh"))) {
                        //处理抵押
                        String bdcdyh = map.get("bdcdyh").toString();
                        List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());

                        if (CollectionUtils.isNotEmpty(listDyaq)) {
                            map.put("dyzt", Constants.WWSQ_SF_S);
                        } else {
                            map.put("dyzt", Constants.WWSQ_SF_F);
                        }
                        //处理查封
                        List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                        if (CollectionUtils.isNotEmpty(listCf)) {
                            map.put("cfzt", Constants.WWSQ_SF_S);
                        } else {
                            map.put("cfzt", Constants.WWSQ_SF_F);
                        }
                        if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_HF, dataversion)) {
                            // 03-31 增加查询行政区
                            String xzq = hfXzqFeignService.queryXzqByBdcdyh(bdcdyh, null, "");
                            map.put("xzqhszdm", xzq);
                        }

                        //37562 【南通大市】互联网+不动产交互需求  增加返回fdcq表中tdsyqmj
                        List<BdcFdcqDO> fdcqDOList = wwsqMapper.fdcqList(bdcdyh);
                        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
                        FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh, "");
                        fdcqMap(map, fdcqDOList, zdMap, fwHsDO);

                        //宗地宗海权利性质、户室权利性质
                        // 查询宗地基本信息
                        //字典信息
                        List<Map> qlxzZdMap = bdcZdFeignService.queryBdcZd("qlxz");
                        String zdbdcdyh = StringUtils.substring(MapUtils.getString(map, "bdcdyh"), 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                        BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(zdbdcdyh);
                        if (zdjbxxDO != null) {
                            map.put("zdzhqlxz", StringToolUtils.convertBeanPropertyValueOfZd(zdjbxxDO.getQlxz(), qlxzZdMap));
                        }
                        if (Objects.nonNull(fwHsDO) && Objects.nonNull(fwHsDO.getTdsyqlx())) {
                            map.put("qlxz", StringToolUtils.convertBeanPropertyValueOfZdByString(fwHsDO.getTdsyqlx(), qlxzZdMap));
                        } else {
                            if (zdjbxxDO != null) {
                                map.put("qlxz", StringToolUtils.convertBeanPropertyValueOfZd(zdjbxxDO.getQlxz(), qlxzZdMap));
                            }
                        }
                    }

                    map.remove("xmid");
                }
            }
        }
        return listCqxx;
    }

    /**
     * 房地产权表相关信息带入
     *
     * @return
     * @Date 2021/3/2
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private Map fdcqMap(Map map, List<BdcFdcqDO> fdcqDOList, Map<String, List<Map>> zdMap, FwHsDO fwHsDO) {
        if (CollectionUtils.isNotEmpty(fdcqDOList)) {
            if (null != fdcqDOList.get(0).getTdsyqmj()) {
                map.put("tdsyqmj", String.valueOf(fdcqDOList.get(0).getTdsyqmj()));
            } else if (null != fwHsDO && null != fwHsDO.getFttdmj()) {
                map.put("tdsyqmj", String.valueOf(fwHsDO.getFttdmj()));
            } else {
                map.put("tdsyqmj", "");
            }
            if (null != fdcqDOList.get(0).getGhyt()) {
                map.put("ghyt", String.valueOf(fdcqDOList.get(0).getGhyt()));
                if (fdcqDOList.get(0).getGhyt() == 80) {
                    map.put("ghytmc", fdcqDOList.get(0).getYtmc());
                } else {
                    map.put("ghytmc", StringToolUtils.convertBeanPropertyValueOfZd(fdcqDOList.get(0).getGhyt(), zdMap.get("fwyt")));
                }
            } else {
                map.put("ghytmc", "");
                map.put("ghyt", "");
            }
            if (null != fdcqDOList.get(0).getFwxz()) {
                map.put("fwxz", String.valueOf(fdcqDOList.get(0).getFwxz()));
                if (fdcqDOList.get(0).getFwxz() == 99) {
                    map.put("fwxzmc", fdcqDOList.get(0).getFwxzmc());
                } else {
                    map.put("fwxzmc", StringToolUtils.convertBeanPropertyValueOfZd(fdcqDOList.get(0).getFwxz(), zdMap.get("fwxz")));
                }
            } else {
                map.put("fwxzmc", "");
                map.put("fwxz", "");
            }
            if (null != fdcqDOList.get(0).getFwjg()) {
                map.put("fwjg", String.valueOf(fdcqDOList.get(0).getFwjg()));
                if (fdcqDOList.get(0).getFwjg() == 6) {
                    map.put("fwjgmc", fdcqDOList.get(0).getFwjgmc());
                } else {
                    map.put("fwjgmc", StringToolUtils.convertBeanPropertyValueOfZd(fdcqDOList.get(0).getFwjg(), zdMap.get("fwjg")));
                }
            } else {
                map.put("fwjgmc", "");
                map.put("fwjg", "");
            }
        }
        return map;
    }

    /**
     * 根据fcxmid匹配土地xmid
     *
     * @param
     * @return list
     * @Date 2021/1/20
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public List<Map> getGltdzxx(String fcxmid) {
    /*
    36696 【盐城】抵押首次创建接口关联土地证信息节点内容调整
    根据xmid查询BDC_FCTD_PPGX表，查询土地相关信息
     */
        List<BdcFctdPpgxDO> ppgxDOS = bdcPpFeignService.queryBdcFctdPpgx(fcxmid);
        List<Map> gltszxxlist = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(ppgxDOS)) {
            String tdxmid = ppgxDOS.get(0).getTdcqxmid();
            //查询bdcxm表，主要获取土地证号
//                       BdcXmDO bdcXmDO = bdcXmFeignService.
            BdcXmDO tdxm = entityMapper.selectByPrimaryKey(BdcXmDO.class, tdxmid);
            //查询建设用地使用权
            Example example = new Example(BdcJsydsyqDO.class);
            example.setOrderByClause("djsj ASC,slbh ASC");
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", tdxmid);
            List<BdcJsydsyqDO> bdcJsydsyqDOList = entityMapper.selectByExampleNotNull(example);
            Map gltdzxxMap = new HashMap();
            gltdzxxMap.put("tdzl", bdcJsydsyqDOList.get(0).getZl());
            gltdzxxMap.put("tdzh", tdxm.getBdcqzh());
            gltdzxxMap.put("xmid", tdxmid);
            gltdzxxMap.put("tdsyqmj", bdcJsydsyqDOList.get(0).getSyqmj());
            gltdzxxMap.put("bdcdyh", bdcJsydsyqDOList.get(0).getBdcdyh());
            //权利人处理数据
            gltdzxxMap.put("qlr", qlrsjConvert(tdxmid, CommonConstantUtils.QLRLB_QLR, "CommonQlr"));
            gltszxxlist.add(gltdzxxMap);

        }
        return gltszxxlist;
    }

    /**
     * 抵押注销流程获取抵押产权信息
     *
     * @param bdcdjzmh
     * @param dyqr
     * @param dyrmc
     * @param dyqrzjh
     * @param dyrzjh
     * @return 响应结构
     */
    @Override
    public List<Map> getBdczmxx(String bdcdjzmh, String dyqr, String dyrmc, String dyqrzjh, String dyrzjh) {
        List<Map> resultList = new ArrayList<>();

        //常州版本增加查询dv_dyzx视图，增加roomid返回,其他数据从事图中取
        if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ, dataversion)) {
            Example example = new Example(DvDyzx.class);
            example.createCriteria().andEqualTo("bdcdjzmh", bdcdjzmh).andEqualTo("dyqrmc", dyqr);
            List<DvDyzx> dyzxList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(dyzxList)) {
                for (DvDyzx dyzx : dyzxList) {
                    Map mapResult = new HashMap();
                    exchangeDozerMapper.map(dyzx, mapResult, "BdczmxxXm_changzhou");
                    //义务人
                    List<Map> qlrlist = new ArrayList<>();
                    BdcQlrDO qlrDO = new BdcQlrDO();
                    Map qlrMap = exchangeDozerMapper.map(dyzx, Map.class, "BdczmxxQlr_changzhou");
                    if (MapUtils.isNotEmpty(qlrMap)) {
                        qlrlist.add(qlrMap);
                    }
                    mapResult.put("qlr", qlrlist);
                    //抵押权人
                    List<Map> dyqrlist = new ArrayList<>();
                    BdcQlrDO dyqrDo = new BdcQlrDO();
                    Map dyqrMap = exchangeDozerMapper.map(dyzx, Map.class, "BdczmxxDyqr_changzhou");
                    if (MapUtils.isNotEmpty(dyqrMap)) {
                        dyqrlist.add(dyqrMap);
                    }
                    mapResult.put("dyqlr", dyqrlist);
                    resultList.add(mapResult);

                }
                return resultList;
            }
            return resultList;
        }
        //参数封装
        Map paramMap = new HashMap(7);
        paramMap.put("dyqr", dyqr);
        paramMap.put("dyrmc", dyrmc);
        if (StringUtils.isNotBlank(dyqrzjh)) {
            // 增加15  18位 QLR 转换
            String revertZjhs = CardNumberTransformation.zjhTransformation(dyqrzjh);
            String[] zjhArr = revertZjhs.split(",");
            paramMap.put("dyqrzjhArr", Arrays.asList(zjhArr));
        }
        if (StringUtils.isNotBlank(dyrzjh)) {
            // 增加15  18位 QLR 转换
            String revertZjhs = CardNumberTransformation.zjhTransformation(dyrzjh);
            String[] zjhArr = revertZjhs.split(",");
            paramMap.put("dyrzjhArr", Arrays.asList(zjhArr));
        }
        paramMap.put("likebdcqzh", bdcdjzmh);
        paramMap.put("ajzt", CommonConstantUtils.AJZT_YB_DM);
        paramMap.put("qszt", CommonConstantUtils.QSZT_VALID);
        //查询
        List<Map> listCqxx = wwsqMapper.getWwsqCqzxx(paramMap);
        if (CollectionUtils.isNotEmpty(listCqxx)) {
            for (Map map : listCqxx) {
                if (StringUtils.isNotBlank(MapUtils.getString(map, "xmid"))) {
                    String xmid = MapUtils.getString(map, "xmid");
                    Map mapResult = new HashMap();
                    BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);

                    if (bdcXmDO != null) {
                        exchangeDozerMapper.map(bdcXmDO, mapResult, "BdczmxxXm");

                        BdcDyaqDO bdcDyaqDO = new BdcDyaqDO();
                        bdcDyaqDO.setXmid(xmid);
                        List<BdcDyaqDO> listDyaq = entityMapper.selectByObj(bdcDyaqDO);
                        if (CollectionUtils.isNotEmpty(listDyaq)) {
                            BdcDyaqDO dyaqDO = listDyaq.get(0);
                            if (null == dyaqDO.getJzzr()) {
                                mapResult.put("sfjzdyqjdywzrmc", "");
                                mapResult.put("sfjzdyqjdywzrdm", "");
                            }
                            exchangeDozerMapper.map(dyaqDO, mapResult, "BdczmxxQl");
                            if (null != dyaqDO.getFwdymj()) {
                                mapResult.put("jzmj", dyaqDO.getFwdymj().toString());
                            } else if (null != dyaqDO.getTddymj()){
                                mapResult.put("jzmj", dyaqDO.getTddymj().toString());
                            } else {
                                mapResult.put("jzmj", "");
                            }

                            //权利人处理数据
                            mapResult.put("dyqlr", qlrsjConvert(xmid, CommonConstantUtils.QLRLB_QLR, "BdczmxxQlr"));
                            //义务人处理数据
                            mapResult.put("qlr", getDyaqYwr(xmid, dyaqDO.getBdcdyh()));
                            // 03-31 增加查询行政区
                            if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_HF, dataversion) && StringUtils.isNotBlank(bdcXmDO.getBdcdyh())) {
                                String xzq = hfXzqFeignService.queryXzqByBdcdyh(bdcXmDO.getBdcdyh(), null, "");
                                mapResult.put("xzqhszdm", xzq);
                            }
                            resultList.add(mapResult);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 抵押查询产权列表
     *
     * @param
     * @param requestDTO
     * @return 响应结构
     */
    @Override
    public List<DycxcqResponseDTO> getWwsqDyCqxx(DycxcqRequestDTO requestDTO) {
        // 判断请求参数是否为空
        if (StringUtil.isEmpty(requestDTO.getDyqr()) && StringUtil.isEmpty(requestDTO.getDyzmh())) {
            throw new MissingArgumentException("dyqr和dyzmh不能同时为空");
        }
        // 查询抵押权项目信息
        List<BdcXmDO> listDyXmxx = wwsqMapper.getWwsqDyXmxx(requestDTO);

        // 查询产权信息
        List<DycxcqResponseDTO> listCqxx = new ArrayList<DycxcqResponseDTO>();
        for (BdcXmDO bdcXmDO : listDyXmxx) {
            if (StringUtil.isNotBlank(bdcXmDO.getBdcdyh())) {
                List<DycxcqResponseDTO> cqxx = wwsqMapper.getWwsqDyCqxx(bdcXmDO.getBdcdyh());
                if (StringUtil.isNotBlank(bdcXmDO.getYwr()) && cqxx.size() > 0) {
                    for (DycxcqResponseDTO dycxcqResponseDTO : cqxx) {
                        dycxcqResponseDTO.setDyr(bdcXmDO.getYwr());
                        dycxcqResponseDTO.setDyqr(bdcXmDO.getQlr());
                        dycxcqResponseDTO.setSlbh(bdcXmDO.getSlbh());
                    }
                    listCqxx.addAll(cqxx);
                }

            }
        }
        for (DycxcqResponseDTO dycxcqResponseDTO : listCqxx) {
            if (StringUtil.isNotBlank(dycxcqResponseDTO.getBdcdyh())) {
                //查询查封状态
                List<Integer> qsztList = new ArrayList<>();
                qsztList.add(Constants.QSZT_XS);
                //查询查封
                List<BdcQl> cfList = djbxxFeignService.listBdcQlxx(dycxcqResponseDTO.getBdcdyh(), Constants.CF_QLLX_DM, qsztList);
                dycxcqResponseDTO.setCfzt(CollectionUtils.isEmpty(cfList) ? "0" : "1");
            }
        }
        return listCqxx;
    }

    /**
     * @param xmid
     * @param bdcdyh
     * @return java.util.List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取带产权证号的 抵押权义务人列表
     */
    private List<Map> getDyaqYwr(String xmid, String bdcdyh) {
        List<Map> list = new ArrayList<>();
        // 获取抵押权义务人
        List<BdcQlrDO> dyaqYwrList = commonService.listBdcQlrByXmid(xmid, CommonConstantUtils.QLRLB_YWR);
        // 获取房地产权权利人
        List<BdcQlrDO> cqQlrList = listCqBdcQlr(bdcdyh);
        // 给抵押权义务人填充 产权证号
        fillCqzhToDyaYwr(dyaqYwrList, cqQlrList);
        // 做对照
        if (CollectionUtils.isNotEmpty(dyaqYwrList)) {
            for (BdcQlrDO dyaqYwr : dyaqYwrList) {
                Map map = exchangeDozerMapper.map(dyaqYwr, Map.class, "BdczmxxYwr");
                if (MapUtils.isNotEmpty(map)) {
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * @param dyaqYwrList
     * @param cqQlrList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 给 抵押权义务人 添加 产权证号
     */
    private void fillCqzhToDyaYwr(List<BdcQlrDO> dyaqYwrList, List<BdcQlrDO> cqQlrList) {
        if (CollectionUtils.isNotEmpty(dyaqYwrList)
                && CollectionUtils.isNotEmpty(cqQlrList)) {
            dyqqlr:
            for (BdcQlrDO dyaqYwr : dyaqYwrList) {
                dyaqYwr.setBdcqzh(null);
                cqqlr:
                for (BdcQlrDO cqQlr : cqQlrList) {
                    if (StringUtils.equals(cqQlr.getQlrmc(), dyaqYwr.getQlrmc()) &&
                            StringUtil.isNotEmpty(dyaqYwr.getZjh())) {
                        dyaqYwr.setBdcqzh(cqQlr.getBdcqzh());
                        break cqqlr;
                    }
                }
            }
        }
    }


    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询 所有产权的 权利人 列表
     */
    private List<BdcQlrDO> listCqBdcQlr(String bdcdyh) {
        List<String> qllxList = new ArrayList<>();
        qllxList.addAll(Arrays.asList(SjptCxqlEnum.FDCQ.getQllxArr()));
        qllxList.addAll(Arrays.asList(SjptCxqlEnum.JSYDSYQ.getQllxArr()));
        qllxList.addAll(Arrays.asList(SjptCxqlEnum.TDSYQ.getQllxArr()));
        qllxList.addAll(Arrays.asList(SjptCxqlEnum.NYDSYQ.getQllxArr()));
        qllxList.addAll(Arrays.asList(SjptCxqlEnum.GJZWSYQ.getQllxArr()));
        List<BdcQlrDO> bdcQlrList = new ArrayList<>();
        qllx:
        for (String qllx : qllxList) {
            List<BdcQl> cqTempList = commonService.listXsQlByBdcdyh(bdcdyh, qllx);
            if (CollectionUtils.isNotEmpty(cqTempList)) {
                bdcql:
                for (BdcQl bdcQl : cqTempList) {
                    List<BdcQlrDO> qlrtemp = commonService.listBdcQlrByXmid(bdcQl.getXmid(), CommonConstantUtils.QLRLB_QLR);
                    if (CollectionUtils.isNotEmpty(qlrtemp)) {
                        bdcQlrList.addAll(qlrtemp);
                        break qllx;
                    }
                }
            }
        }
        return bdcQlrList;
    }

    /**
     * 获取产权证号下所有抵押信息
     *
     * @param cqzh
     * @return
     */
    @Override
    public List<Map> querycqzdyxx(@NotBlank(message = "参数不能为空") String cqzh) {
        List<Map> resultList = new ArrayList<>();
        // 根据 产权证号查询 证书信息
        BdcZsQO zsQO = new BdcZsQO();
        zsQO.setBdcqzh(cqzh);
        List<BdcZsDO> zsList = bdcZsFeignService.listBdcZs(zsQO);
        //查询
        if (CollectionUtils.isNotEmpty(zsList)) {
            for (BdcZsDO zsDO : zsList) {
                // 需要验证 ZS 的 权属状态
                Example example = new Example(BdcXmZsGxDO.class);
                example.createCriteria().andEqualTo("zsid", zsDO.getZsid());
                List<BdcXmZsGxDO> bdcXmZsGxDOList = entityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(bdcXmZsGxDOList)) {
                    for (BdcXmZsGxDO bdcXmZsGxDO : bdcXmZsGxDOList) {
                        if (StringUtils.isNotBlank(bdcXmZsGxDO.getXmid())) {
                            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(bdcXmZsGxDO.getXmid());
                            if (bdcXmDO != null && bdcXmDO.getQszt() != null
                                    && StringUtils.isNotBlank(bdcXmDO.getBdcdyh())
                                    && Constants.QSZT_XS == bdcXmDO.getQszt()) {

                                List<Integer> qsztList = new ArrayList<>();
                                qsztList.add(Constants.QSZT_XS);
                                List<BdcQl> dyaqList = bdcDjbxxFeignService.listBdcQlxx(bdcXmDO.getBdcdyh(), CommonConstantUtils.QLLX_DYAQ_DM.toString(), qsztList);
                                if (CollectionUtils.isNotEmpty(dyaqList)) {
                                    for (BdcQl qltemp : dyaqList) {
                                        Map mapResult = new HashMap();
                                        BdcXmDO dyXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, qltemp.getXmid());
                                        // 抵押项目 信息对照
                                        exchangeDozerMapper.map(dyXmDO, mapResult, "BdczmxxXm");
                                        // 抵押权利 信息对照
                                        exchangeDozerMapper.map(qltemp, mapResult, "BdczmxxQl");
                                        //权利人处理数据
                                        mapResult.put("dyqlr", qlrsjConvert(qltemp.getXmid(), CommonConstantUtils.QLRLB_QLR, "BdczmxxQlr"));
                                        //义务人处理数据
                                        mapResult.put("qlr", qlrsjConvert(qltemp.getXmid(), CommonConstantUtils.QLRLB_YWR, "BdczmxxYwr"));

                                        //宗地宗海权利性质、户室权利性质
                                        // 查询宗地基本信息
                                        List<Map> qlxzZdMap = bdcZdFeignService.queryBdcZd("qlxz");
                                        String zdbdcdyh = StringUtils.substring(dyXmDO.getBdcdyh(), 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                                        BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(zdbdcdyh);
                                        if (zdjbxxDO != null) {
                                            mapResult.put("zdzhqlxz", StringToolUtils.convertBeanPropertyValueOfZd(zdjbxxDO.getQlxz(), qlxzZdMap));
                                        }
                                        BdcQjgldmQO qjgldmQO = new BdcQjgldmQO();
                                        qjgldmQO.setBdcdyh(dyXmDO.getBdcdyh());
                                        String qjgldm = bdcXmfbFeignService.queryQjgldm(qjgldmQO);
                                        FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(dyXmDO.getBdcdyh(), qjgldm);
                                        if (Objects.nonNull(fwHsDO) && Objects.nonNull(fwHsDO.getTdsyqlx())) {
                                            mapResult.put("qlxz", StringToolUtils.convertBeanPropertyValueOfZdByString(fwHsDO.getTdsyqlx(), qlxzZdMap));
                                        } else {
                                            if (zdjbxxDO != null) {
                                                mapResult.put("qlxz", StringToolUtils.convertBeanPropertyValueOfZd(zdjbxxDO.getQlxz(), qlxzZdMap));
                                            }
                                        }
                                        resultList.add(mapResult);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * @param responseData
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询产权证信息 填充限制权利信息
     */
    @Override
    public GetWwsqCqzxxFyResponseData getWwsqCqzxxPageXzql(GetWwsqCqzxxFyResponseData responseData) {
        if (responseData != null && CollectionUtils.isNotEmpty(responseData.getCqxx())) {
            for (GetWwsqCqzxxResponseCqxx cqxxTemp : responseData.getCqxx()) {
                if (StringUtils.isNotBlank(cqxxTemp.getBdcdyh())) {
                    String bdcdyh = cqxxTemp.getBdcdyh();
                    //处理抵押
                    List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());
                    if (CollectionUtils.isNotEmpty(listDyaq)) {
                        cqxxTemp.setSfdy(Constants.WWSQ_SF_S + "");
                    } else {
                        cqxxTemp.setSfdy(Constants.WWSQ_SF_F + "");
                    }
                    //处理查封
                    List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    if (CollectionUtils.isNotEmpty(listCf)) {
                        cqxxTemp.setSfcf(Constants.WWSQ_SF_S + "");
                    } else {
                        cqxxTemp.setSfcf(Constants.WWSQ_SF_F + "");
                    }
                }
                //盐城需求，添加证书类型
                if (StringUtils.isNoneBlank(cqxxTemp.getXmid())) {
                    Integer zslx = bdcXmMapper.getBdcZslxByXmid(cqxxTemp.getXmid());
                    BdcXmDO xmDO = bdcXmMapper.queryBdcXm(cqxxTemp.getXmid());
                    if (null != xmDO) {
                        cqxxTemp.setXzqdm(xmDO.getQxdm());
                    }
                    cqxxTemp.setZslx(zslx);
                }
            }
        }
        return responseData;
    }

    /**
     * @param rollbackParam
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 创建项目时 发生异常 需要回滚 项目 和流程
     */
    @Override
    public void rollbackInitXm(Map<String, String> rollbackParam) {
        // 删除项目
        if (StringUtils.isNotBlank(rollbackParam.get("xmid"))) {
            String[] xmids = new String[]{rollbackParam.get("xmid")};
            try {
                bdcInitFeignService.deleteYwxx(xmids);
            } catch (Exception e1) {
                LOGGER.error("初始化流程异常后，回滚项目信息异常", e1);
            }
        }
        // 删除任务
        try {
            if (StringUtils.isNotBlank(rollbackParam.get("taskid"))) {
                taskHandleClient.deleteTask(rollbackParam.get("taskid"));
            }
        } catch (Exception e1) {
            LOGGER.error("初始化流程异常后，回滚任务信息异常", e1);
        }

        // 删除交易信息
        try {
            if (StringUtils.isNotBlank(rollbackParam.get("jyxxid"))) {
                bdcSlJyxxFeignService.deleteBdcSlJyxxByJyxxid(rollbackParam.get("jyxxid"));
            }
        } catch (Exception e1) {
            LOGGER.error("初始化流程异常后，回滚交易信息异常", e1);
        }
    }

    /**
     * @param methodName
     * @param gxWwSqxxDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据共享外网申请实体 获取工作流定义ID
     */
    @Override
    public void setGzldyidInSqxx(String methodName, GxWwSqxxDO gxWwSqxxDO) {
        // 如果是抵押权登记  返回固定抵押权流程ID
        if (StringUtils.equals(methodName, Constants.WWSQ_INIT_DYDJ)) {
            gxWwSqxxDO.setSqlx(Constants.WWSQ_INIT_DYDJ_GZLDYID);
        }
        // 如果是抵押权注销登记  返回固定的抵押权注销流程定义ID
        if (StringUtils.equals(methodName, Constants.WWSQ_INIT_DYZX)) {
            gxWwSqxxDO.setSqlx(Constants.WWSQ_INIT_DYZX_GZLDYID);
        }
    }

    /**
     * @param gxWwSqxxDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据共享外网申请实体 获取工作流定义ID
     */
    @Override
    public String getGzldyidByWwSqxx(GxWwSqxxDO gxWwSqxxDO) {
        if (gxWwSqxxDO != null) {
            return gxWwSqxxDO.getSqlx();
        }
        return null;
    }


    /**
     * @param processDefKey
     * @param bdcdyh
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证BDCDYH 是否能创建流程
     */
    @Override
    public String gzyzBdcdy(String processDefKey, String bdcdyh) {
        //验证人；
        UserDto yzr = userManagerUtils.getCurrentUser();
        String yzrid = null == yzr ? null : yzr.getId();
        String yzrzh = null == yzr ? null : yzr.getUsername();

        String yzResultMsg = "";
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.isNotBlank(processDefKey)) {
            BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
            bdcGzYzQO.setZhbs(processDefKey + "_XZBDCDY");
            // 获取规则验证的参数列表
            List<Map<String, Object>> gzyzParamList = new ArrayList<>();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("bdcdyh", bdcdyh);
            gzyzParamList.add(paramMap);
            bdcGzYzQO.setParamList(gzyzParamList);
            bdcGzYzQO.setYzrid(yzrid);
            bdcGzYzQO.setYzrzh(yzrzh);
            // 验证组合规则
            List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
            if (CollectionUtils.isNotEmpty(listBdcGzYzTsxx)) {
                StringBuilder tsxxSb = new StringBuilder();
                BdcGzYzTsxxDTO dto = listBdcGzYzTsxx.get(0);
                if (CollectionUtils.isNotEmpty(dto.getZgzTsxxDTOList())) {
                    // 循环 拼接提示信息
                    for (BdcGzZgzTsxxDTO zgzTsxx : dto.getZgzTsxxDTOList()) {
                        tsxxSb.append(zgzTsxx.getTsxx()).append(",");
                    }
                    yzResultMsg = tsxxSb.toString();
                    if (StringUtils.isNotBlank(yzResultMsg) && yzResultMsg.endsWith(",")) {
                        return yzResultMsg.substring(0, yzResultMsg.length() - 1);
                    }
                }
            }
        }
        return yzResultMsg;
    }

    /**
     * @param sqrList
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 权利人限购验证
     */
    @Override
    public String qlrxgyz(List<BdcSlSqrDO> sqrList) {
        if (CollectionUtils.isNotEmpty(sqrList)) {
            for (BdcSlSqrDO sqr : sqrList) {
                if (StringUtil.equals(CommonConstantUtils.QLRLB_QLR, sqr.getSqrlb())
                        && StringUtils.isNotBlank(sqr.getSqrmc())
                        && StringUtils.isNotBlank(sqr.getZjh())) {
                    Map<String, Object> paramMap = new HashMap();
                    paramMap.put("cardNo", sqr.getZjh());
                    paramMap.put("name", sqr.getSqrmc());
                    Object response = exchangeBeanRequestService.request("hfFcjyXgxx_http", paramMap);
                    FcXgxxResponseModel responseModel = JSONObject.parseObject(JSONObject.toJSONString(response), FcXgxxResponseModel.class);
                    if (responseModel != null && responseModel.getData() != null) {
                        FcXgxxResponseData data = responseModel.getData();
                        if (StringUtils.equals(Constants.FCXGXX_F, data.getIsCanBuy())) {
                            throw new AppException("权利人：" + sqr.getSqrmc() + ",证件号：" + sqr.getZjh() + "，限购验证不通过");
                        } else {
                            return data.getIsCanBuy();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param bdcqzh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据证号 获取BDCDYH (一证多房场景，只取其中一个就可以，
     * 主要用于判断所有权类型，获取登记配置)
     */
    @Override
    public String queryBdcdyByBdcqzh(String bdcqzh) {
        if (StringUtils.isNotBlank(bdcqzh)) {
            BdcZsQO zsQO = new BdcZsQO();
            zsQO.setBdcqzh(bdcqzh);
            List<BdcZsDO> zsList = bdcZsFeignService.listBdcZs(zsQO);
            if (CollectionUtils.isNotEmpty(zsList)) {
                for (BdcZsDO bdcZsDO : zsList) {
                    if (StringUtils.isNotBlank(bdcZsDO.getBdcdyh())) {
                        return bdcZsDO.getBdcdyh();
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换 外网申请创建结果
     */
    @Override
    public ExchangeDsfCommonResponse<CfjfdjResponseDTO> revertCjResponseForYanchengCourt(String spxtywh, Object responseDTO,
                                                                                         List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf) {
        try {
            if (responseDTO instanceof ExchangeDsfCommonResponse) {
                return (ExchangeDsfCommonResponse) responseDTO;
            }
            WwsqCjBdcXmResponseDTO tempResponseDTO = (WwsqCjBdcXmResponseDTO) responseDTO;
            Map<String, Object> result = revertCjResponse(spxtywh, tempResponseDTO, fjclList, wwsqCjBdcXmRequestDTO, zdzf);
            if (result.containsKey("msg") && "success".equals(result.get("msg")) && result.containsKey("gzlslid") && result.get("gzlslid") != null) {
                //查询TaskId
                CfjfdjResponseDTO cfjfdjResponseDTO = new CfjfdjResponseDTO();
                cfjfdjResponseDTO.setProid((String) result.get("proid"));
                cfjfdjResponseDTO.setYwslbh((String) result.get("ywslbh"));
                List<TaskData> taskDataList = processTaskClient.listProcessTask((String) result.get("gzlslid"));
                if (taskDataList != null && taskDataList.size() > 0) {
                    for (int i = 0; i < taskDataList.size(); i++) {
                        if (StringUtils.equals(taskDataList.get(i).getTaskName(), CommonConstantUtils.JD_SL) && StringUtils.isNotBlank(taskDataList.get(i).getTaskId())) {
                            cfjfdjResponseDTO.setTaskId(taskDataList.get(i).getTaskId());
                            break;
                        }
                    }
                    if (StringUtils.isBlank(cfjfdjResponseDTO.getTaskId())) {
                        return ExchangeDsfCommonResponse.fail("创建成功，获取taskId失败", cfjfdjResponseDTO);
                    }
                    return ExchangeDsfCommonResponse.ok(cfjfdjResponseDTO);
                } else {
                    LOGGER.info("---外网创建自动转发异常,获取taskId失败:{},获取taskId入参：{}", result, JSON.toJSONString(taskDataList));
                    return ExchangeDsfCommonResponse.fail("外网创建自动转发异常,获取taskId失败");
                }
            } else {
                LOGGER.info("---外网创建自动转发异常:{}", result);
                return ExchangeDsfCommonResponse.fail("外网创建自动转发异常");
            }
        } catch (Exception e) {
            LOGGER.info("---外网创建自动转发异常:{}", e.getMessage());
            return ExchangeDsfCommonResponse.fail("外网创建自动转发异常");
        }
    }

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换 外网申请创建结果
     */
    @Override
    public Map<String, Object> revertCjResponse(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                                List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf) {
        // 验证不通过的处理
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getGzyzList())) {
            StringBuilder msgSb = new StringBuilder();
            List<Map<String, Object>> yzResult = responseDTO.getGzyzList();
            for (Map<String, Object> map : yzResult) {
                String bdcdyh = StringUtils.defaultString(MapUtils.getString(map, "bdcdyh"));
                String yzmsg = StringUtils.defaultString(MapUtils.getString(map, "msg"));
                msgSb.append(bdcdyh).append(yzmsg).append(",");
            }
            String msg = msgSb.toString();
            if (msg.endsWith(",")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("proid", spxtywh);
            resultMap.put("msg", msg);
            return resultMap;
        }

        String zdzf1 = wwsqCjBdcXmRequestDTO.getZdzf();
        String zdbj = wwsqCjBdcXmRequestDTO.getSfzdbj();
//        String slr = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getSlr();
        //初审人有值则取初审人，无值取受理人
        String slr = StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getZdzfslr()) ? wwsqCjBdcXmRequestDTO.getZdzfslr() : wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getSlr();

        String gzldyid = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getGzldyid();
        // 异步上传附件
        fjclService.asynUploadAndSaveFjcl(responseDTO, fjclList);
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            // 回写大云平台
            try {
                Map<String, Object> paramMap = new HashMap<>();
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getFsr())) {
                    redisUtils.addHashValue(CommonConstantUtils.ZDZF_SLR + "FSR", gzlslid, wwsqCjBdcXmRequestDTO.getFsr(), 24 * 60 * 60);
                }
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getDbr())) {
                    redisUtils.addHashValue(CommonConstantUtils.ZDZF_SLR + "DBR", gzlslid, wwsqCjBdcXmRequestDTO.getDbr(), 24 * 60 * 60);
                }


                // 预约分中心，开始时间，结束时间
                DsfSlxxDTO dsfSlxxDTO = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList().get(0).getDsfSlxxDTO();
                Date yykssj = dsfSlxxDTO.getYykssj();
                Date yyjssj = dsfSlxxDTO.getYyjssj();
                String yyfzx = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getQxdm();
                if (StringUtils.isNotBlank(yyfzx) || null != yykssj || null != yyjssj) {
                    paramMap.put("YYKSSJ", dsfSlxxDTO.getYykssj());
                    paramMap.put("YYJSSJ", dsfSlxxDTO.getYyjssj());
                    paramMap.put("YYFZX", yyfzx);
                }
                // 是否自动转发登簿
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getSfzdzfdb())) {
                    paramMap.put("SFZDZFDB", wwsqCjBdcXmRequestDTO.getSfzdzfdb());
                }
                //缴费状态
                List<BdcSfxxDTO> bdcSfxxDTOS = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList().get(0).getBdcSfxxDTOList();
                if (CollectionUtils.isNotEmpty(bdcSfxxDTOS) && bdcSfxxDTOS.get(0).getBdcSlSfxxDO() != null) {
                    Integer sfzt = bdcSfxxDTOS.get(0).getBdcSlSfxxDO().getSfzt();
                    if (sfzt != null) {
                        paramMap.put("JFZT", sfzt);
                    }
                }
                //申请部门名称
                if (StringUtils.isNotBlank(dsfSlxxDTO.getSqbmmc())) {
                    paramMap.put("SQBMMC", dsfSlxxDTO.getSqbmmc());
                }
                //业务类型
                if (StringUtils.isNotBlank(dsfSlxxDTO.getYwlx())) {
                    paramMap.put("YWLX", dsfSlxxDTO.getYwlx());
                }
                // 执行回写
                if (MapUtils.isNotEmpty(paramMap)) {
                    LOGGER.info("大云回写字段异常参数,paramMap:{}", JSON.toJSONString(paramMap));
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                }
            } catch (Exception e) {
                LOGGER.error("大云回写字段异常,slbh:{}", responseDTO.getBdcXmDOList().get(0).getSlbh(), e);
            }
            //如果传递自动转发，就直接请求转发
            if (Objects.nonNull(zdzf) && StringUtils.equals(Constants.WWSQ_CJXM_AUTOTURN, zdzf)) {
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                LOGGER.info("---外网创建自动转发:{},工作流实例id:{},自动转发人员：{}", zdzf, gzlslid, wwsqCjBdcXmRequestDTO.getZdzfslr());
                autoTurnWorkflow(gzlslid, zdzf, wwsqCjBdcXmRequestDTO.getZdzfslr());
            }
            //配置自动办结gzldyid
            if (initwwsqZdbjList.contains(gzldyid)) {
                LOGGER.info("通用创建接口，该流程需要自动办结，工作流定义id：{},配置的自动办结slr为：{}", gzldyid, zdbjslr);
                responseDTO.setSfzbbj(true);
                zdbjOrzdzf(responseDTO, zdbjslr, "1", zdzf1);
                LOGGER.info("通用创建接口,自动办结结束");
            } else {
                //上面是配置自动转发，下面是入参要求自动转发或办结
                LOGGER.info("通用创建接口，该流程配置自动转发，工作流定义id：{},配置的自动转发slr为：{},自动转发值：{}", gzldyid, slr, zdzf1);
                zdbjOrzdzf(responseDTO, slr, zdbj, zdzf1);

            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("proid", spxtywh);
            resultMap.put("gzlslid", responseDTO.getBdcXmDOList().get(0).getGzlslid());
            resultMap.put("ywslbh", responseDTO.getBdcXmDOList().get(0).getSlbh());
            resultMap.put("msg", "success");
            if (Boolean.TRUE.equals(wwsqCjBdcXmRequestDTO.isSendmsg())) {
                LOGGER.info("外网申请创建执行发送短信功能:{}", responseDTO.getBdcXmDOList().get(0).getGzlslid());
                //发送短信通知
                sendMsgService.sendMessage(resultMap, "wwsqcj");
            }
            return resultMap;
        } else {
            throw new AppException("没有生成登记项目");
        }
    }

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换 外网申请创建结果
     */
    @Override
    public Map<String, Object> revertCzCjResponseForZhlc(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                                         List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf) {
        // 验证不通过的处理
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getGzyzList())) {
            StringBuilder msgSb = new StringBuilder();
            List<Map<String, Object>> yzResult = responseDTO.getGzyzList();
            for (Map<String, Object> map : yzResult) {
                String bdcdyh = StringUtils.defaultString(MapUtils.getString(map, "bdcdyh"));
                String yzmsg = StringUtils.defaultString(MapUtils.getString(map, "msg"));
                msgSb.append(bdcdyh).append(yzmsg).append(",");
            }
            String msg = msgSb.toString();
            if (msg.endsWith(",")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("proid", spxtywh);
            resultMap.put("msg", msg);
            return resultMap;
        }


        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            // 异步上传附件,1:如果是合并上传附件，走原先逻辑即可；2：如果是分开上传，需要取受理返回关联好的登记小类和附件信息
            if (CollectionUtils.isNotEmpty(wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getHbfjxx())) {
                List<FjclDTO> fjclDTOS = new ArrayList<>();
                for (FjclDTOForZhlc zhlc : wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getHbfjxx()) {
                    FjclDTO fjclDTO = new FjclDTO();
                    BeanUtils.copyProperties(zhlc, fjclDTO);
                    if (CollectionUtils.isNotEmpty(zhlc.getClnr())) {
                        fjclDTO.setFjclmxDTOList(zhlc.getClnr());
                    }
                    fjclDTOS.add(fjclDTO);
                }
                fjclService.asynUploadAndSaveFjcl(responseDTO, fjclDTOS);
            } else {
                if (CollectionUtils.isNotEmpty(responseDTO.getWwsqZhlcSjclDTOList())) {
                    List<WwsqZhlcSjclDTO> zhlcSjclDTOList = responseDTO.getWwsqZhlcSjclDTOList();
                    fjclService.asynUploadAndSaveFjclForZhlc(zhlcSjclDTOList);
                }
            }
            // 回写大云平台
            try {
                Map<String, Object> paramMap = new HashMap<>();
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();

                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getFsr())) {
                    redisUtils.addHashValue(CommonConstantUtils.ZDZF_SLR + "FSR", gzlslid, wwsqCjBdcXmRequestDTO.getFsr(), 24 * 60 * 60);
                }
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getDbr())) {
                    redisUtils.addHashValue(CommonConstantUtils.ZDZF_SLR + "DBR", gzlslid, wwsqCjBdcXmRequestDTO.getDbr(), 24 * 60 * 60);
                }
                // 预约分中心，开始时间，结束时间
                DsfSlxxDTO dsfSlxxDTO = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList().get(0).getDsfSlxxDTO();
                Date yykssj = dsfSlxxDTO.getYykssj();
                Date yyjssj = dsfSlxxDTO.getYyjssj();
                String yyfzx = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getQxdm();
                String sqbmmc = dsfSlxxDTO.getSqbmmc();
                if (StringUtils.isNotBlank(yyfzx) || null != yykssj || null != yyjssj) {
                    paramMap.put("YYKSSJ", dsfSlxxDTO.getYykssj());
                    paramMap.put("YYJSSJ", dsfSlxxDTO.getYyjssj());
                    paramMap.put("YYFZX", yyfzx);
                }
                //申请部门名称回写
                if (StringUtils.isNotBlank(sqbmmc)) {
                    paramMap.put("SQBMMC", sqbmmc);
                }
                //业务类型
                if (StringUtils.isNotBlank(dsfSlxxDTO.getYwlx())) {
                    paramMap.put("YWLX", dsfSlxxDTO.getYwlx());
                }
                // 是否自动转发登簿
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getSfzdzfdb())) {
                    paramMap.put("SFZDZFDB", wwsqCjBdcXmRequestDTO.getSfzdzfdb());
                }
                // 执行回写
                if (MapUtils.isNotEmpty(paramMap)) {
                    LOGGER.info("大云回写字段异常参数,paramMap:{}", JSON.toJSONString(paramMap));
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                }
            } catch (Exception e) {
                LOGGER.error("大云回写字段异常,slbh:{}", responseDTO.getBdcXmDOList().get(0).getSlbh(), e);
            }
            String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
            //初审人有值则取初审人，无值取受理人
            String slr = StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getZdzfslr()) ? wwsqCjBdcXmRequestDTO.getZdzfslr() : wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getSlr();
            String zdbj = wwsqCjBdcXmRequestDTO.getSfzdbj();
            // 入参要求自动办结
            LOGGER.info("通用创建接口，该流程入参配置自动办结，工作流实例id：{},配置的自动办结slr为：{},自动办结值：{}", gzlslid, slr, zdbj);
            zdbjOrzdzf(responseDTO, slr, zdbj, null);
            //如果传递自动转发，就直接请求转发
            if (Objects.nonNull(zdzf) && StringUtils.equals(Constants.WWSQ_CJXM_AUTOTURN, zdzf)) {
                LOGGER.info("---外网创建自动转发:{},工作流实例id:{},自动转发人员：{}", zdzf, gzlslid, wwsqCjBdcXmRequestDTO.getZdzfslr());
                autoTurnWorkflow(gzlslid, zdzf, wwsqCjBdcXmRequestDTO.getZdzfslr());
            } else if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getZdzf())) {
                //上面是配置自动转发，下面是入参要求自动转发或办结
                LOGGER.info("---外网创建入参要求自动转发:{},工作流实例id:{},自动转发人：{}", wwsqCjBdcXmRequestDTO.getZdzf(), gzlslid, wwsqCjBdcXmRequestDTO.getZdzfslr());
                autoTurnWorkflow(gzlslid, wwsqCjBdcXmRequestDTO.getZdzf(), wwsqCjBdcXmRequestDTO.getZdzfslr());
            }
            //如果传递自动转发，就直接请求转发
           /* if (Objects.nonNull(zdzf) && StringUtils.equals(Constants.WWSQ_CJXM_AUTOTURN, zdzf)) {
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                LOGGER.info("---外网创建自动转发:{},工作流实例id:{},自动转发人：{}", zdzf, gzlslid, wwsqCjBdcXmRequestDTO.getZdzfslr());
                autoTurnWorkflow(gzlslid, zdzf, wwsqCjBdcXmRequestDTO.getZdzfslr());
            }*/
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("proid", spxtywh);
            resultMap.put("gzlslid", responseDTO.getBdcXmDOList().get(0).getGzlslid());
            resultMap.put("ywslbh", responseDTO.getBdcXmDOList().get(0).getSlbh());
            resultMap.put("msg", "success");
            return resultMap;
        } else {
            throw new AppException("没有生成登记项目");
        }
    }


    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 转换 外网申请创建结果
     */
    @Override
    public Map<String, Object> revertCzCjResponse(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                                  List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf) {
        // 验证不通过的处理
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getGzyzList())) {
            StringBuilder msgSb = new StringBuilder();
            List<Map<String, Object>> yzResult = responseDTO.getGzyzList();
            for (Map<String, Object> map : yzResult) {
                String bdcdyh = MapUtils.getString(map, "bdcdyh");
                String yzmsg = MapUtils.getString(map, "msg");
                msgSb.append(bdcdyh).append(yzmsg).append(",");
            }
            String msg = msgSb.toString();
            if (msg.endsWith(",")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("proid", spxtywh);
            resultMap.put("msg", msg);
            return resultMap;
        }
        // 处理附件材料 先执行初始化默认收件材料
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList()) && StringUtils.isNotBlank(responseDTO.getBdcXmDOList().get(0).getGzlslid())) {
            bdcSlFeignService.cshSjcl(responseDTO.getBdcXmDOList().get(0).getGzlslid());
        }
        // 异步上传附件
        LOGGER.info("附件数量:{}", CollectionUtils.isEmpty(fjclList) ? 0 : fjclList.size());
        fjclService.asynUploadAndSaveFjcl(responseDTO, fjclList);
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            //如果传递自动转发，就直接请求转发
            autoTurnWorkflow(responseDTO.getBdcXmDOList().get(0).getGzlslid(), zdzf, "");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("proid", spxtywh);
            resultMap.put("gzlslid", responseDTO.getBdcXmDOList().get(0).getGzlslid());
            resultMap.put("ywslbh", responseDTO.getBdcXmDOList().get(0).getSlbh());
            resultMap.put("msg", "success");
            return resultMap;
        } else {
            throw new AppException("没有生成登记项目");
        }
    }

    /**
     * @param responseDTO
     * @param fjclList
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 南通一体化流程创建处理响应
     */
    @Override
    public Map<String, Object> revertNtCjResponse(WwsqCjBdcXmResponseDTO responseDTO, List<FjclDTO> fjclList, String zdzf) {
        // 验证不通过的处理
        Map<String, Object> resultMap = new HashMap<>();
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getGzyzList())) {
            StringBuilder msgSb = new StringBuilder();
            List<Map<String, Object>> yzResult = responseDTO.getGzyzList();
            for (Map<String, Object> map : yzResult) {
                String bdcdyh = MapUtils.getString(map, "bdcdyh");
                String yzmsg = MapUtils.getString(map, "msg");
                msgSb.append(bdcdyh).append(yzmsg).append(",");
            }
            String msg = msgSb.toString();
            if (msg.endsWith(",")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            resultMap.put("msg", msg);
            resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
            return resultMap;
        }
        resultMap.put("msg", ResponseCodeEnum.SERVER_ERROR.msg);
        resultMap.put("code", ResponseCodeEnum.SERVER_ERROR.code);
        //非登记流程返回的受理信息
        if (responseDTO != null && responseDTO.getBdcSlxxInitDTO() != null
                && CollectionUtils.isNotEmpty(responseDTO.getBdcSlxxInitDTO().getBdcSlJbxxDOList())) {
            List<BdcSlJbxxDO> jbxxList = responseDTO.getBdcSlxxInitDTO().getBdcSlJbxxDOList();
            BdcSlJbxxDO jbxxDO = jbxxList.get(0);
            if (StringUtils.isNotBlank(jbxxDO.getGzlslid()) && StringUtils.isNotBlank(jbxxDO.getSlbh())) {
                String gzlslid = jbxxDO.getGzlslid();
                // 处理附件材料 先执行初始化默认收件材料
                bdcSlFeignService.cshSjcl(gzlslid);
                // 同步上传收件材料
                fjclService.uploadAndSaveFjcl(gzlslid, fjclList);

                // 处理不动产登记流程的附件
                if (CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
                    String bdcGzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                    //不相等才做处理不动产附件处理
                    if (StringUtils.isNotBlank(bdcGzlslid) && !StringUtils.equals(bdcGzlslid, gzlslid)) {
                        // 处理附件材料 先执行初始化默认收件材料
                        bdcSlFeignService.cshSjcl(bdcGzlslid);
                        // 同步上传收件材料
                        fjclService.uploadAndSaveFjcl(bdcGzlslid, fjclList);
                    }
                }
                //如果传递自动转发，就直接请求转发
                if (CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
                    autoTurnWorkflow(responseDTO.getBdcXmDOList().get(0).getGzlslid(), zdzf, "");
                }
                resultMap.put("slbh", jbxxDO.getSlbh());
                resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
                resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
            }
        } else if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            //登记流程返回的信息
            BdcXmDO bdcXmDO = responseDTO.getBdcXmDOList().get(0);
            // 处理附件材料 先执行初始化默认收件材料
            bdcSlFeignService.cshSjcl(bdcXmDO.getGzlslid());
            // 同步上传收件材料
            fjclService.uploadAndSaveFjcl(bdcXmDO.getGzlslid(), fjclList);
            //如果传递自动转发，就直接请求转发
            autoTurnWorkflow(bdcXmDO.getGzlslid(), zdzf, "");
            resultMap.put("slbh", bdcXmDO.getSlbh());
            resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
            resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
        }
        return resultMap;
    }


    /**
     * @param responseDTO 外网申请请求创建项目后的响应
     * @param fjclList    附件材料信息
     * @param zdzf        自动转发标识
     * @return {Map} 返回信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 南通一体化流程创建处理响应 (异步处理附件)  (为避免原逻辑变动造成其它影响，这个逻辑直接复制上一个方法revertNtCjResponse，只是将附件处理改为异步)
     */
    @Override
    public Map<String, Object> revertNtCjResponseAsync(WwsqCjBdcXmResponseDTO responseDTO, List<FjclDTO> fjclList, String zdzf) {
        // 验证不通过的处理
        Map<String, Object> resultMap = new HashMap<>();
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getGzyzList())) {
            StringBuilder msgSb = new StringBuilder();
            List<Map<String, Object>> yzResult = responseDTO.getGzyzList();
            for (Map<String, Object> map : yzResult) {
                String bdcdyh = MapUtils.getString(map, "bdcdyh");
                String yzmsg = MapUtils.getString(map, "msg");
                msgSb.append(bdcdyh).append(yzmsg).append(",");
            }
            String msg = msgSb.toString();
            if (msg.endsWith(",")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            resultMap.put("msg", msg);
            resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
            return resultMap;
        }
        resultMap.put("msg", ResponseCodeEnum.SERVER_ERROR.msg);
        resultMap.put("code", ResponseCodeEnum.SERVER_ERROR.code);

        //非登记流程返回的受理信息
        if (responseDTO != null && responseDTO.getBdcSlxxInitDTO() != null
                && CollectionUtils.isNotEmpty(responseDTO.getBdcSlxxInitDTO().getBdcSlJbxxDOList())) {
            List<BdcSlJbxxDO> jbxxList = responseDTO.getBdcSlxxInitDTO().getBdcSlJbxxDOList();
            BdcSlJbxxDO jbxxDO = jbxxList.get(0);
            if (StringUtils.isNotBlank(jbxxDO.getGzlslid()) && StringUtils.isNotBlank(jbxxDO.getSlbh())) {
                String gzlslid = jbxxDO.getGzlslid();
                // 处理附件材料 先执行初始化默认收件材料
                bdcSlFeignService.cshSjcl(gzlslid);
                // 异步下载附件
                fjThreadPool.execute(() -> this.downloadFjclAsync(gzlslid, fjclList));

                // 处理不动产登记流程的附件
                if (CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
                    String bdcGzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                    //不相等才做处理不动产附件处理
                    if (StringUtils.isNotBlank(bdcGzlslid) && !StringUtils.equals(bdcGzlslid, gzlslid)) {
                        // 处理附件材料 先执行初始化默认收件材料
                        bdcSlFeignService.cshSjcl(bdcGzlslid);
                        // 异步下载附件
                        fjThreadPool.execute(() -> this.downloadFjclAsync(bdcGzlslid, fjclList));
                    }
                }
                //如果传递自动转发，就直接请求转发
                if (CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
                    autoTurnWorkflow(responseDTO.getBdcXmDOList().get(0).getGzlslid(), zdzf, "");
                }
                resultMap.put("slbh", jbxxDO.getSlbh());
                resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
                resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
            }
        } else if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            //登记流程返回的信息
            BdcXmDO bdcXmDO = responseDTO.getBdcXmDOList().get(0);
            // 处理附件材料 先执行初始化默认收件材料
            bdcSlFeignService.cshSjcl(bdcXmDO.getGzlslid());
            // 异步下载附件
            fjThreadPool.execute(() -> this.downloadFjclAsync(bdcXmDO.getGzlslid(), fjclList));

            //如果传递自动转发，就直接请求转发
            autoTurnWorkflow(bdcXmDO.getGzlslid(), zdzf, "");
            resultMap.put("slbh", bdcXmDO.getSlbh());
            resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
            resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
        }

        return resultMap;
    }

    /**
     * @param gzlslid  工作流实例ID
     * @param fjclList 附件材料信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 线程异步下载附件逻辑
     */
    private void downloadFjclAsync(String gzlslid, List<FjclDTO> fjclList) {
        LOGGER.info("南通互联网+系统创建3.0一体化系统业务流程 异步下载附件，流程实例ID：{}，附件材料类型数：{}", gzlslid, CollectionUtils.isEmpty(fjclList) ? 0 : fjclList.size());

        // 生成附件材料文件夹逻辑 bdcSlFeignService.cshSjcl() 理论上也应该提取到这里，
        // 不过因为内部有获取用户信息逻辑，而异步线程导致权限无法传递，因此还是放在外面

        // 同步上传收件材料
        fjclService.uploadAndSaveFjcl(gzlslid, fjclList);
    }

    /**
     * @param slbh
     * @param fjclList
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 南通根据受理编号初始化附件信息
     */
    @Override
    public Map<String, Object> cshfjxx(String slbh, List<FjclDTO> fjclList) {
        // 验证不通过的处理
        Map<String, Object> resultMap = new HashMap<>();

        if (StringUtils.isEmpty(slbh) || CollectionUtils.isEmpty(fjclList)) {
            resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
            resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
            return resultMap;
        }

        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
        LOGGER.info("---项目表gzlslid:{},查询参数:{}", gzlslid, slbh);
        if (StringUtils.isEmpty(gzlslid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh, "");

            if (Objects.isNull(bdcSlJbxxDO)) {
                resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
                return resultMap;
            }

            gzlslid = bdcSlJbxxDO.getGzlslid();
            LOGGER.info("---受理库gzlslid:{},查询参数:{}", gzlslid, slbh);
        }
        //50048 【南通大市】更新附件信息接口限制需求:如果是受理节点都可以更新，如果是审核节点，且没有办理人员，则可以更新，其他情况不允许更新
        if (CommonConstantUtils.SYSTEM_VERSION_NT.equals(dataversion)) {
            LOGGER.info("南通更新附件特殊判断开始！");
            List<TaskData> processRunningTasks = processTaskClient.processRunningTasks(gzlslid);
            if (CollectionUtils.isNotEmpty(processRunningTasks)) {
                String taskName = processRunningTasks.get(0).getTaskName();
                LOGGER.info("南通更新附件特殊判断，节点名称：{}，办理人员：{}", taskName, processRunningTasks.get(0).getTaskAssName());
                if (fjxxSljdmc.equals(taskName) || (fjxxShjdmc.equals(taskName) && StringUtils.isBlank(processRunningTasks.get(0).getTaskAssName()))) {
                    // 处理受理附件材料 先执行删除所有附件材料操作
                    bdcSlSjclFeignService.deleteAllSjcl(gzlslid);
                    // 同步上传收件材料
                    fjclService.uploadAndSaveFjcl(gzlslid, fjclList);
                    resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
                    resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
                    if (MapUtils.isEmpty(resultMap)) {
                        resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                        resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
                    }
                } else {
                    resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                    resultMap.put("code", "该流程不在审核节点认领前，无法更新附件");
                }

            } else {
                LOGGER.info("未查询到当前流程，请注意。查询条件gzlslid:{}", gzlslid);
            }
        } else {
            // 处理受理附件材料 先执行删除所有附件材料操作
            bdcSlSjclFeignService.deleteAllSjcl(gzlslid);
            // 同步上传收件材料
            fjclService.uploadAndSaveFjcl(gzlslid, fjclList);
            resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
            resultMap.put("code", ResponseCodeEnum.SUCCESS.code);

            if (MapUtils.isEmpty(resultMap)) {
                resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
            }
        }

        return resultMap;
    }

    /**
     * @param gzlslid
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 自动转发
     */
    @Override
    public void autoTurnWorkflow(String gzlslid, String zdzf, String zdzfr) {
        try {
            if (StringUtils.isNotBlank(gzlslid) && StringUtils.equals(Constants.WWSQ_CJXM_AUTOTURN, zdzf)) {
                AutoForwardTaskDTO autoForwardTaskDTO = new AutoForwardTaskDTO();
                autoForwardTaskDTO.setJdmc(zdzfJdmc);
                autoForwardTaskDTO.setUsername(zdzfr);
                if (Boolean.TRUE.equals(zdzfyz)) {
                    bdcSlFeignService.autoTurnWithGzyz(gzlslid, autoForwardTaskDTO);
                } else {
                    bdcSlFeignService.wwsqAutoTurn(gzlslid, autoForwardTaskDTO);
                }
            }
        } catch (Exception e) {
            LOGGER.error("创建成功，自动转发失败，失败原因：{}", e.getMessage(), e);
        }
    }

    /**
     * @param responseDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 自动办结
     */
    @Override
    public void zdbj(WwsqCjBdcXmResponseDTO responseDTO, String slr) {
        zdbjWithCanBj(responseDTO, slr, canZdbj);
    }

    @Override
    public void zdbjWithCanBj(WwsqCjBdcXmResponseDTO responseDTO, String slr, boolean canbj) {
//        if (canbj) {
        //根据互联网传参判断是否自动办结
        LOGGER.info("---自动办结参数:{},slr:{},canbj:{}", JSONObject.toJSONString(responseDTO), slr, canbj);
        if (null != responseDTO.getSfzbbj() && responseDTO.getSfzbbj()) {
            LOGGER.info("自动办结参数为：{}", responseDTO.toString());
            List<BdcXmDO> bdcXmDOList = responseDTO.getBdcXmDOList();
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                LOGGER.info("自动办结参数slr:{}", slr);
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                if (StringUtils.isNotBlank(slr)
                        && StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                LOGGER.info("开始执行自动办结：gzlslid:{} ,slr:{}", bdcXmDO.getGzlslid(), slr);
                                taskHandleClient.autoComplete(bdcXmDO.getGzlslid(), slr);
                                //合肥自动办结流程，审核信息在工作流事件配置中已经生成
                                if (!CommonConstantUtils.SYSTEM_VERSION_HF.equals(dataversion)) {
                                    // 生成办结节点审核信息 意见取值默认意见
                                    LOGGER.info("生成办结节点审核信息：gzlslid:{}", bdcXmDO.getGzlslid());
                                    bdcShxxRestService.generateShxxOfProInsId(bdcXmDO.getGzlslid());

                                } else {
                                    // 合肥 生成查解封回执单
                                    if (CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx())){
                                        bdcFyService.scCjfhzdPdf(bdcXmDO.getGzlslid());
                                    }
                                }
                            } catch (Exception e) {
                                LOGGER.error("自动办结异常：gzlslid:{} ,slr:{}", bdcXmDO.getGzlslid(), slr, e);
                            }
                        }
                    }.start();
                }
            }
        }
    }

    @Override
    public void zdbjAndUpdateZsyzh(WwsqCjBdcXmResponseDTO responseDTO, String slr, List<JSONObject> zslist) {
        //自动办结
        zdbjWithCanBj(responseDTO, slr, true);
        //更新印制号
        if (CollectionUtils.isNotEmpty(zslist)) {
            for (JSONObject obj : zslist) {
                if (StringUtils.isNoneBlank(MapUtils.getString(obj, "qzysxlh"), MapUtils.getString(obj, "zsid"))) {
                    LOGGER.info("互联网+更新印制号，印制号：{}，证书id：{}", MapUtils.getString(obj, "qzysxlh"), MapUtils.getString(obj, "zsid"));
                    BdcYzhQO bdcYzhQO = new BdcYzhQO();
                    bdcYzhQO.setQzysxlh(MapUtils.getString(obj, "qzysxlh"));
                    List<BdcYzhDTO> bdcYzhDTOList = bdcYzhFeignService.queryListBdcYzh(bdcYzhQO);
                    if (CollectionUtils.isNotEmpty(bdcYzhDTOList)) {
                        BdcYzhSyqkQO bdcYzhSyqkQO = new BdcYzhSyqkQO();
                        bdcYzhSyqkQO.setQzysxlh(MapUtils.getString(obj, "qzysxlh"));
                        bdcYzhSyqkQO.setZsid(MapUtils.getString(obj, "zsid"));
                        bdcYzhSyqkQO.setYzhid(bdcYzhDTOList.get(0).getYzhid());
                        bdcYzhSyqkQO.setSyqk(CommonConstantUtils.SYQK_YSY);
                        bdcYzhSyqkQO.setSyr(slr);
                        LOGGER.info("互联网+更新印制号，bdcYzhSyqkQO：{}", JSONObject.toJSONString(bdcYzhSyqkQO));
                        bdcYzhFeignService.updateBdcYzhSyqk(bdcYzhSyqkQO);
                    }
                }
            }
        }
    }


    /**
     * @param responseDTO
     * @param qlrzjh
     * @param qlrmc
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 像产权证信息中的CQZH 赋值
     */
    @Override
    public void setGetWwsqCqzxxCqzhByQlrxx(GetWwsqCqzxxResponseDTO responseDTO, String qlrzjh, String qlrmc) {
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getCqxx())) {
            for (cn.gtmap.realestate.exchange.core.dto.
                    wwsq.getWwsqCqzxx.response.
                    GetWwsqCqzxxResponseCqxx cqzxxResponseCqxx : responseDTO.getCqxx()) {
                List<GetWwsqCqzxxResponseQlr> responseQlrList = cqzxxResponseCqxx.getQlr();
                if (CollectionUtils.isNotEmpty(responseQlrList)) {
                    for (GetWwsqCqzxxResponseQlr qlr : responseQlrList) {
                        if (StringUtil.equals(qlr.getQlrmc(), qlrmc)) {
                            cqzxxResponseCqxx.setFczh(qlr.getCqzh());
                            break;
                        }
                    }
                }
            }
        }

    }

    /**
     * @param request
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换DTO
     */
    @Override
    public WwsqCjBdcXmRequestDTO initZhlcRevertDTO(InitZyDyRequestDTO request) {
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        // 受理角色控制 需要使用 固定的外网角色编码
        if (StringUtils.equals("false", slRoleCode)) {
            wwsqCjBdcXmRequestDTO.setSlRoleCode("");
        } else {
            wwsqCjBdcXmRequestDTO.setSlRoleCode("wwsqsl");

        }
        if (request.getData() != null) {
            // 增加是否进行规则验证
            wwsqCjBdcXmRequestDTO.setGzyz(true);
            // 增加是否涉税
            if (StringUtils.equals(CommonConstantUtils.SF_S_DM + "", request.getData().getSfss())) {
                wwsqCjBdcXmRequestDTO.setSfss(true);
            }
            if (StringUtils.isNotBlank(request.getData().getXzqdm())) {
                wwsqCjBdcXmRequestDTO.setYhxzqdm(request.getData().getXzqdm());
            }
            if (StringUtils.isNotBlank(request.getData().getSfzdbj())) {
                wwsqCjBdcXmRequestDTO.setSfzdbj(request.getData().getSfzdbj());
            }

            InitZyDyRequestData data = request.getData();
            BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
            wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);
            wwsqCjBdcXmRequestDTO.setSfzdzfdb(Objects.nonNull(data) ? data.getTgjds() : "");
            if (StringUtils.isNotBlank(data.getYsr())) {
                wwsqCjBdcXmRequestDTO.setZdzfslr(data.getYsr());
            }
            if (StringUtils.isNotBlank(data.getFsr())) {
                wwsqCjBdcXmRequestDTO.setFsr(data.getFsr());
            }
            if (StringUtils.isNotBlank(data.getDbr())) {
                wwsqCjBdcXmRequestDTO.setDbr(data.getDbr());
            }

            BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
            exchangeDozerMapper.map(data, bdcSlJbxxDO, "wwsq_zhlc_jbxx");
            if ((StringUtils.isNotBlank(dataversion) && StringUtils.equals(dataversion, CommonConstantUtils.SYSTEM_VERSION_NT)) || zhlcTsslr) {
                if (StringUtils.isNotBlank(data.getSlr())) {
                    bdcSlJbxxDO.setSlr(data.getSlr());
                }
            }
            bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
            if (CollectionUtils.isNotEmpty(data.getWlxx()) && data.getWlxx().get(0) != null) {
                // 添加邮寄信息
                BdcSlYjxxDO bdcSlYjxxDO = new BdcSlYjxxDO();
                exchangeDozerMapper.map(data.getWlxx().get(0), bdcSlYjxxDO, "wwsq_zhlc_slyjxx");
                bdcSlxxDTO.setBdcSlYjxxDO(bdcSlYjxxDO);
            }
            List<BdcSlXmDTO> xmDTOList = new ArrayList<>();
            bdcSlxxDTO.setBdcSlXmList(xmDTOList);

            if (CollectionUtils.isNotEmpty(data.getBdcdyxx())) {
                for (InitZyDyRequestBdcdyxx bdcdyxx : data.getBdcdyxx()) {
                    //最外层附件信息不为空时，传给受理
                    if (CollectionUtils.isNotEmpty(bdcdyxx.getFjxx())) {
                        bdcSlxxDTO.setHbfjxx(bdcdyxx.getFjxx());
                    }
                    // 产权列表
                    if (CollectionUtils.isNotEmpty(bdcdyxx.getCqxx())) {
                        for (InitZyDyRequestCqxx cqxx : bdcdyxx.getCqxx()) {

                            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                            BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
                            exchangeDozerMapper.map(bdcdyxx, bdcSlXmDO, "wwsq_zhlc_bdcdyxx_cqslxm");
                            exchangeDozerMapper.map(cqxx, bdcSlXmDO, "wwsq_zhlc_bdcdyxx_cqxxslxm");
                            exchangeDozerMapper.map(data, bdcSlXmDO, "wwsq_zhlc_data_cqslxm");

                            bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);

                            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                            exchangeDozerMapper.map(bdcdyxx, dsfSlxxDTO, "wwsq_zhlc_bdcdyxx_dsfslxx_cq");
                            exchangeDozerMapper.map(cqxx, dsfSlxxDTO, "wwsq_zhlc_cqxx_dsfslxx");
                            exchangeDozerMapper.map(data, dsfSlxxDTO, "wwsq_zhlc_data_dsfslxx");
                            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);


                            BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
                            exchangeDozerMapper.map(cqxx, bdcSlJyxxDO, "wwsq_zhlc_cqxx_sljyxx");
                            bdcSlXmDTO.setBdcSlJyxxDO(bdcSlJyxxDO);

                            //处理更正信息
                            if (CollectionUtils.isNotEmpty(cqxx.getGzxx())) {
                                BdcGzdjDO bdcGzdjDO = new BdcGzdjDO();
                                exchangeDozerMapper.map(cqxx.getGzxx().get(0), bdcGzdjDO, "wwsq_zhlc_cqxx_gzxx");
                                bdcSlXmDTO.setBdcGzdjDO(bdcGzdjDO);

                            }

                            //处理完税状态
                            if (StringUtils.isNotBlank(cqxx.getSwzt())) {
                                List<BdcSwxxDTO> bdcSwxxDTOList = new ArrayList<>();
                                BdcSwxxDTO swxxDTO = new BdcSwxxDTO();
                                BdcSlHsxxDO slHsxxDO = new BdcSlHsxxDO();
                                exchangeDozerMapper.map(cqxx, slHsxxDO, "wwsq_zhlc_cqxx_swzt");
                                swxxDTO.setBdcSlHsxxDO(slHsxxDO);
                                bdcSwxxDTOList.add(swxxDTO);
                                bdcSlXmDTO.setBdcSwxxDTOList(bdcSwxxDTOList);
                            }
                            //如果有单独附件，给受理传附件，进行登记小类和附件关联
                            if (CollectionUtils.isNotEmpty(cqxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(cqxx.getFjxx());
                            }

                            // 处理项目历史关系
                            if (StringUtils.isNotBlank(bdcdyxx.getYxmid())) {
                                initBdcXmLsgx(bdcdyxx.getYxmid(), bdcSlXmDTO);
                            } else if (StringUtils.isNoneBlank(cqxx.getRoomid())) {
                                //常州：无历史项目信息，通过roomid处理
                                Object bean = Container.getBean(ExchangeInterfaceRestController.class);
                                if (bean != null) {
                                    ExchangeInterfaceRestController restController = (ExchangeInterfaceRestController) bean;
                                    //组织查询产权信息参数
                                    JSONObject object = new JSONObject();
                                    object.put("roomid", cqxx.getRoomid());
                                    Object result = restController.requestInterface("initParam", object);
                                    if (result != null) {
                                        initBdcXmLsgx((String) result, bdcSlXmDTO);
                                    }
                                }
                            }

                            // 处理权利人
                            if (CollectionUtils.isNotEmpty(cqxx.getQlrxx())) {
                                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                                bdcSlXmDTO.setBdcSlSqrDTOList(new ArrayList<>());
                                for (InitZyDyRequestQlrxx qlrxx : cqxx.getQlrxx()) {
                                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                    BdcSlSqrDTO slSqrDTO = new BdcSlSqrDTO();
                                    exchangeDozerMapper.map(qlrxx, bdcSlSqrDO, "wwsq_zhlc_qlrxx_slsqr");
                                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                                        slSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                                    }
                                    if (null != qlrxx.getJtcy()) {
                                        List jtcylist = JSONArray.parseArray(qlrxx.getJtcy().toString());
                                        for (Object jtcy : jtcylist) {
                                            BdcSlJtcyDO jtcyDO = new BdcSlJtcyDO();
                                            exchangeDozerMapper.map(jtcy, jtcyDO, "jtcyxx");
                                            List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
                                            if (CheckParameter.checkAnyParameter(jtcyDO)) {
                                                jtcyDOList.add(jtcyDO);
                                                slSqrDTO.setBdcSlJtcyDOList(jtcyDOList);
                                            }
                                        }

                                    }
                                    bdcSlXmDTO.getBdcSlSqrDTOList().add(slSqrDTO);
                                }
                            }
                            //处理领证人 物流信息
                            if (CollectionUtils.isNotEmpty(cqxx.getWlxx())) {
                                bdcSlXmDTO.setBdcSlLzrDOList(new ArrayList<>());
                                for (InitSjrxx lzr : cqxx.getWlxx()) {
                                    BdcSlLzrDO lzrDO = new BdcSlLzrDO();
                                    exchangeDozerMapper.map(lzr, lzrDO, "wwsq_zhlc_lzrxx");
                                    if (CheckParameter.checkAnyParameter(lzrDO)) {
                                        bdcSlXmDTO.getBdcSlLzrDOList().add(lzrDO);
                                    }
                                }
                                // 添加邮寄信息
                                BdcSlYjxxDO bdcSlYjxxDO = new BdcSlYjxxDO();
                                exchangeDozerMapper.map(cqxx.getWlxx().get(0), bdcSlYjxxDO, "wwsq_zhlc_slyjxx");
                                bdcSlXmDTO.setBdcSlYjxxDO(bdcSlYjxxDO);
                            }
                            //处理领证人 盐城产权信息中的领证人
                            if (CollectionUtils.isNotEmpty(cqxx.getLzrxx())) {
                                bdcSlXmDTO.setBdcSlLzrDOList(new ArrayList<>());
                                for (InitRequestLzr lzr : cqxx.getLzrxx()) {
                                    BdcSlLzrDO lzrDO = new BdcSlLzrDO();
                                    exchangeDozerMapper.map(lzr, lzrDO, "wwsq_zhlc_cqxx_lzrxx");
                                    if (CheckParameter.checkAnyParameter(lzrDO)) {
                                        bdcSlXmDTO.getBdcSlLzrDOList().add(lzrDO);
                                    }
                                }
                            }

                            //处理收费信息
                            List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>();
                            if (CollectionUtils.isNotEmpty(cqxx.getSfxx())) {
                                for (InitRequestSfxx sfxx : cqxx.getSfxx()) {
                                    BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
                                    BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                                    exchangeDozerMapper.map(sfxx, bdcSlSfxxDO, "wwsq_zhlc_sfxx");
                                    List<BdcSlSfxmDO> bdcSlSfxmDOS = new ArrayList<>();

                                    if (CollectionUtils.isNotEmpty(sfxx.getSfxmxx())) {

                                        for (InitRequestSfxmxx sfxmxx : sfxx.getSfxmxx()) {
                                            BdcSlSfxmDO bdcSlSfxmDO = new BdcSlSfxmDO();
                                            exchangeDozerMapper.map(sfxmxx, bdcSlSfxmDO, "wwsq_zhlc_sfxmxx");
                                            bdcSlSfxmDOS.add(bdcSlSfxmDO);
                                        }
                                    }
                                    bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
                                    bdcSfxxDTO.setBdcSlSfxmDOS(bdcSlSfxmDOS);
                                    bdcSfxxDTOList.add(bdcSfxxDTO);

                                }
                                bdcSlXmDTO.setBdcSfxxDTOList(bdcSfxxDTOList);
                            }

                            //过户信息
                            if(cqxx.getGhxx() != null&& CheckParameter.checkAnyParameter(cqxx.getGhxx())){
                                BdcSdqghDTO bdcSdqghDTO = new BdcSdqghDTO();
                                exchangeDozerMapper.map(cqxx.getGhxx(), bdcSdqghDTO, "wwsq_zhlc_ghxx");
                                bdcSlxxDTO.setBdcSdqghDTO(bdcSdqghDTO);
                            }
                            xmDTOList.add(bdcSlXmDTO);
                        }
                    }


                    // 抵押列表
                    if (CollectionUtils.isNotEmpty(bdcdyxx.getDyxx())) {
                        boolean zhdy = false;
                        if (bdcdyxx.getDyxx().size() == 2) {
                            zhdy = true;
                        }
                        for (InitZyDyRequestDyxx dyxx : bdcdyxx.getDyxx()) {
                            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                            BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
                            exchangeDozerMapper.map(bdcdyxx, bdcSlXmDO, "wwsq_zhlc_bdcdyxx_dyslxm");
                            exchangeDozerMapper.map(data, bdcSlXmDO, "wwsq_zhlc_data_dyslxm");

                            if (Boolean.TRUE.equals(zhdy)) {
                                //组合抵押流程，需要区分商业和公积金贷款
                                exchangeDozerMapper.map(dyxx.getQlrxx().get(0), bdcSlXmDO, "wwsq_zhlc_bdcdyxx_cqxx_qlrxx_djxl");
                            }

                            //根据申请登记类型判断抵押登记权利类型,如果是预告登记对照到预告登记里
                            if (valYgQlByGzldyid(data.getSqdjlx())) {
                                exchangeDozerMapper.map(dyxx, bdcSlXmDO, "wwsq_zhlc_ygdyxx_slxm");
                            } else {
                                exchangeDozerMapper.map(dyxx, bdcSlXmDO, "wwsq_zhlc_dyxx_slxm");
                            }
                            bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);

                            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                            exchangeDozerMapper.map(bdcdyxx, dsfSlxxDTO, "wwsq_zhlc_bdcdyxx_dsfslxx_dy");
                            exchangeDozerMapper.map(dyxx, dsfSlxxDTO, "wwsq_zhlc_dyxx_dsfslxx");
                            exchangeDozerMapper.map(data, dsfSlxxDTO, "wwsq_zhlc_data_dsfslxx");
                            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                            //如果有单独附件，给受理传附件，进行登记小类和附件关联
                            if (CollectionUtils.isNotEmpty(dyxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(dyxx.getFjxx());
                            }

                            // 处理项目历史关系 ,如果是组合贷款，生成项目历史关系
                            if (StringUtils.isNotBlank(bdcdyxx.getYxmid()) && zhdkGzldyidList.contains(bdcSlJbxxDO.getGzldyid())) {
                                LOGGER.warn("组合贷款流程，生成项目历史关系");
                                initBdcXmLsgx(bdcdyxx.getYxmid(), bdcSlXmDTO);
                            }

                            // 处理权利人
                            if (CollectionUtils.isNotEmpty(dyxx.getQlrxx())) {
                                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                                bdcSlXmDTO.setBdcSlSqrDTOList(new ArrayList<>());
                                for (InitZyDyRequestQlrxx qlrxx : dyxx.getQlrxx()) {
                                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                    BdcSlSqrDTO slSqrDTO = new BdcSlSqrDTO();
                                    exchangeDozerMapper.map(qlrxx, bdcSlSqrDO, "wwsq_zhlc_qlrxx_slsqr");
                                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                                        slSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                                    }
                                    if (null != qlrxx.getJtcy()) {
                                        List jtcylist = JSONArray.parseArray(qlrxx.getJtcy().toString());
                                        for (Object jtcy : jtcylist) {
                                            BdcSlJtcyDO jtcyDO = new BdcSlJtcyDO();
                                            exchangeDozerMapper.map(jtcy, jtcyDO, "jtcyxx");
                                            List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
                                            if (CheckParameter.checkAnyParameter(jtcyDO)) {
                                                jtcyDOList.add(jtcyDO);
                                                slSqrDTO.setBdcSlJtcyDOList(jtcyDOList);
                                            }
                                        }

                                    }
                                    bdcSlXmDTO.getBdcSlSqrDTOList().add(slSqrDTO);
                                }
                            }
                            //处理领证人
                            if (CollectionUtils.isNotEmpty(dyxx.getWlxx())) {
                                bdcSlXmDTO.setBdcSlLzrDOList(new ArrayList<>());
                                for (InitSjrxx lzr : dyxx.getWlxx()) {
                                    BdcSlLzrDO lzrDO = new BdcSlLzrDO();
                                    exchangeDozerMapper.map(lzr, lzrDO, "wwsq_zhlc_lzrxx");
                                    if (CheckParameter.checkAnyParameter(lzrDO)) {
                                        bdcSlXmDTO.getBdcSlLzrDOList().add(lzrDO);
                                    }
                                }
                                // 添加邮寄信息
                                BdcSlYjxxDO bdcSlYjxxDO = new BdcSlYjxxDO();
                                exchangeDozerMapper.map(dyxx.getWlxx().get(0), bdcSlYjxxDO, "wwsq_zhlc_slyjxx");
                                bdcSlXmDTO.setBdcSlYjxxDO(bdcSlYjxxDO);
                            }
                            //处理领证人 盐城抵押信息中的领证人，与产权信息的领证人一致
                            if (CollectionUtils.isNotEmpty(dyxx.getLzrxx())) {
                                bdcSlXmDTO.setBdcSlLzrDOList(new ArrayList<>());
                                for (InitRequestLzr lzr : dyxx.getLzrxx()) {
                                    BdcSlLzrDO lzrDO = new BdcSlLzrDO();
                                    exchangeDozerMapper.map(lzr, lzrDO, "wwsq_zhlc_cqxx_lzrxx");
                                    if (CheckParameter.checkAnyParameter(lzrDO)) {
                                        bdcSlXmDTO.getBdcSlLzrDOList().add(lzrDO);
                                    }
                                }
                            }
                            //债务人
                            List<BdcDsQlrDO> bdcDsQlrDOList = new ArrayList<>();
                            if (CollectionUtils.isNotEmpty(dyxx.getZwrxx())) {
                                for (InitZwrXxDTO initZwrXxDTO : dyxx.getZwrxx()) {
                                    BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
                                    exchangeDozerMapper.map(initZwrXxDTO, bdcDsQlrDO, "wwsq_zhlc_zwrxx");
                                    bdcDsQlrDOList.add(bdcDsQlrDO);
                                }
                                bdcSlXmDTO.setBdcDsQlrDOList(bdcDsQlrDOList);
                            }

                            //处理收费信息
                            List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>();
                            if (CollectionUtils.isNotEmpty(dyxx.getSfxx())) {
                                for (InitRequestSfxx sfxx : dyxx.getSfxx()) {
                                    BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
                                    BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                                    exchangeDozerMapper.map(sfxx, bdcSlSfxxDO, "wwsq_zhlc_sfxx");
                                    List<BdcSlSfxmDO> bdcSlSfxmDOS = new ArrayList<>();

                                    if (CollectionUtils.isNotEmpty(sfxx.getSfxmxx())) {

                                        for (InitRequestSfxmxx sfxmxx : sfxx.getSfxmxx()) {
                                            BdcSlSfxmDO bdcSlSfxmDO = new BdcSlSfxmDO();
                                            exchangeDozerMapper.map(sfxmxx, bdcSlSfxmDO, "wwsq_zhlc_sfxmxx");
                                            bdcSlSfxmDOS.add(bdcSlSfxmDO);
                                        }
                                    }
                                    bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
                                    bdcSfxxDTO.setBdcSlSfxmDOS(bdcSlSfxmDOS);
                                    bdcSfxxDTOList.add(bdcSfxxDTO);

                                }
                                bdcSlXmDTO.setBdcSfxxDTOList(bdcSfxxDTOList);
                            }

                            xmDTOList.add(bdcSlXmDTO);

                        }
                    }

                    if (CollectionUtils.isNotEmpty(bdcdyxx.getCfxx())) {
                        bdcSlJbxxDO.setSlr(data.getSlr());
                        for (InitCfjfdjRequestCfxxDTO cfxx : bdcdyxx.getCfxx()) {
                            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                            BdcSlCfjfDO bdcSlCfjfDO = new BdcSlCfjfDO();
                            exchangeDozerMapper.map(bdcdyxx, bdcSlXmDTO, "wwsq_zhlc_cfjfdj_slxm");
                            exchangeDozerMapper.map(cfxx, bdcSlCfjfDO, "wwsq_zhlc_cfjfdj_cfjfxm");
                            if (StringUtils.isNotBlank(cfxx.getCffw())) {
                                bdcSlCfjfDO.setCfyy(cfxx.getDjyymc());
                            } else {
                                bdcSlCfjfDO.setJfyy(cfxx.getDjyymc());
                            }
                            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                            exchangeDozerMapper.map(data, dsfSlxxDTO, "wwsq_zhlc_cfjfdj_dsfslxx");
                            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                            bdcSlXmDTO.setBdcSlQl(bdcSlCfjfDO);
                            //如果有单独附件，给受理传附件，进行登记小类和附件关联
                            if (CollectionUtils.isNotEmpty(cfxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(cfxx.getFjxx());
                            }
                            // 处理权利人
                            if (CollectionUtils.isNotEmpty(cfxx.getQlrxx())) {
                                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                                bdcSlXmDTO.setBdcSlSqrDTOList(new ArrayList<>());
                                for (InitZyDyRequestQlrxx qlrxx : cfxx.getQlrxx()) {
                                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                    BdcSlSqrDTO slSqrDTO = new BdcSlSqrDTO();
                                    exchangeDozerMapper.map(qlrxx, bdcSlSqrDO, "wwsq_zhlc_qlrxx_slsqr");
                                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                                        slSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                                    }
                                    if (null != qlrxx.getJtcy()) {
                                        List jtcylist = JSONArray.parseArray(qlrxx.getJtcy().toString());
                                        for (Object jtcy : jtcylist) {
                                            BdcSlJtcyDO jtcyDO = new BdcSlJtcyDO();
                                            exchangeDozerMapper.map(jtcy, jtcyDO, "jtcyxx");
                                            List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
                                            if (CheckParameter.checkAnyParameter(jtcyDO)) {
                                                jtcyDOList.add(jtcyDO);
                                                slSqrDTO.setBdcSlJtcyDOList(jtcyDOList);
                                            }
                                        }

                                    }
                                    bdcSlXmDTO.getBdcSlSqrDTOList().add(slSqrDTO);
                                }
                            }
                            xmDTOList.add(bdcSlXmDTO);


                        }
                    }
                    //地役信息
                    if (CollectionUtils.isNotEmpty(bdcdyxx.getDiyixx())) {
                        for (InitZyDyRequestDyixx dyixx : bdcdyxx.getDiyixx()) {
                            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                            BdcSlDyiqDTO bdcDyiqDO = new BdcSlDyiqDTO();

                            exchangeDozerMapper.map(bdcdyxx, bdcSlXmDTO, "wwsq_bdcdyxx_slxm");
                            exchangeDozerMapper.map(dyixx, bdcSlXmDTO, "wwsq_diyi_xmxx");
                            exchangeDozerMapper.map(dyixx, bdcDyiqDO, "wwsq_dyi_dyixx");
                            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                            exchangeDozerMapper.map(data, dsfSlxxDTO, "wwsq_ql_dsfslxx");
                            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                            bdcSlXmDTO.setBdcSlQl(bdcDyiqDO);

                            //如果有单独附件，给受理传附件，进行登记小类和附件关联
                            if (CollectionUtils.isNotEmpty(dyixx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(dyixx.getFjxx());
                            }
                            // 处理权利人
                            // 处理权利人
                            if (CollectionUtils.isNotEmpty(dyixx.getQlrxx())) {
                                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                                bdcSlXmDTO.setBdcSlSqrDTOList(new ArrayList<>());
                                for (InitZyDyRequestQlrxx qlrxx : dyixx.getQlrxx()) {
                                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                    BdcSlSqrDTO slSqrDTO = new BdcSlSqrDTO();
                                    exchangeDozerMapper.map(qlrxx, bdcSlSqrDO, "wwsq_zhlc_qlrxx_slsqr");
                                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                                        slSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                                    }
                                    if (null != qlrxx.getJtcy()) {
                                        List jtcylist = JSONArray.parseArray(qlrxx.getJtcy().toString());
                                        for (Object jtcy : jtcylist) {
                                            BdcSlJtcyDO jtcyDO = new BdcSlJtcyDO();
                                            exchangeDozerMapper.map(jtcy, jtcyDO, "jtcyxx");
                                            List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
                                            if (CheckParameter.checkAnyParameter(jtcyDO)) {
                                                jtcyDOList.add(jtcyDO);
                                                slSqrDTO.setBdcSlJtcyDOList(jtcyDOList);
                                            }
                                        }

                                    }
                                    bdcSlXmDTO.getBdcSlSqrDTOList().add(slSqrDTO);
                                }
                            }
                            xmDTOList.add(bdcSlXmDTO);
                        }

                    }
                    //土地使用权
                    if (CollectionUtils.isNotEmpty(bdcdyxx.getTdsyqxx())) {
                        for (InitZyDyRequestTdsyqxx tdsyqxx : bdcdyxx.getTdsyqxx()) {
                            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                            BdcSlTdsyqDTO bdcSlTdsyqDTO = new BdcSlTdsyqDTO();

                            exchangeDozerMapper.map(bdcdyxx, bdcSlXmDTO, "wwsq_bdcdyxx_slxm");
                            exchangeDozerMapper.map(tdsyqxx, bdcSlXmDTO, "wwsq_tdsyq_xmxx");
                            exchangeDozerMapper.map(tdsyqxx, bdcSlTdsyqDTO, "wwsq_tdsyq_tdsyqxx");
                            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                            exchangeDozerMapper.map(data, dsfSlxxDTO, "wwsq_ql_dsfslxx");
                            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                            bdcSlXmDTO.setBdcSlQl(bdcSlTdsyqDTO);

                            //如果有单独附件，给受理传附件，进行登记小类和附件关联
                            if (CollectionUtils.isNotEmpty(tdsyqxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(tdsyqxx.getFjxx());
                            }
                            // 处理权利人
                            // 处理权利人
                            if (CollectionUtils.isNotEmpty(tdsyqxx.getQlrxx())) {
                                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                                bdcSlXmDTO.setBdcSlSqrDTOList(new ArrayList<>());
                                for (InitZyDyRequestQlrxx qlrxx : tdsyqxx.getQlrxx()) {
                                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                    BdcSlSqrDTO slSqrDTO = new BdcSlSqrDTO();
                                    exchangeDozerMapper.map(qlrxx, bdcSlSqrDO, "wwsq_zhlc_qlrxx_slsqr");
                                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                                        slSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                                    }
                                    if (null != qlrxx.getJtcy()) {
                                        List jtcylist = JSONArray.parseArray(qlrxx.getJtcy().toString());
                                        for (Object jtcy : jtcylist) {
                                            BdcSlJtcyDO jtcyDO = new BdcSlJtcyDO();
                                            exchangeDozerMapper.map(jtcy, jtcyDO, "jtcyxx");
                                            List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
                                            if (CheckParameter.checkAnyParameter(jtcyDO)) {
                                                jtcyDOList.add(jtcyDO);
                                                slSqrDTO.setBdcSlJtcyDOList(jtcyDOList);
                                            }
                                        }

                                    }
                                    bdcSlXmDTO.getBdcSlSqrDTOList().add(slSqrDTO);
                                }
                            }
                            xmDTOList.add(bdcSlXmDTO);
                        }

                    }
                    //构筑物信息
                    if (CollectionUtils.isNotEmpty(bdcdyxx.getGzwxx())) {
                        for (InitZyDyGzwxx gzwxx : bdcdyxx.getGzwxx()) {
                            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                            BdcSlGjzwxxDTO bdcSlGjzwxxDTO = new BdcSlGjzwxxDTO();

                            exchangeDozerMapper.map(bdcdyxx, bdcSlXmDTO, "wwsq_bdcdyxx_slxm");
                            exchangeDozerMapper.map(gzwxx, bdcSlXmDTO, "wwsq_gzw_xmxx");
                            exchangeDozerMapper.map(gzwxx, bdcSlGjzwxxDTO, "wwsq_gzw_gzwxx");
                            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                            exchangeDozerMapper.map(data, dsfSlxxDTO, "wwsq_ql_dsfslxx");
                            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                            bdcSlXmDTO.setBdcSlQl(bdcSlGjzwxxDTO);

                            //如果有单独附件，给受理传附件，进行登记小类和附件关联
                            if (CollectionUtils.isNotEmpty(gzwxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(gzwxx.getFjxx());
                            }
                            // 处理权利人
                            // 处理权利人
                            if (CollectionUtils.isNotEmpty(gzwxx.getQlrxx())) {
                                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                                bdcSlXmDTO.setBdcSlSqrDTOList(new ArrayList<>());
                                for (InitZyDyRequestQlrxx qlrxx : gzwxx.getQlrxx()) {
                                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                    BdcSlSqrDTO slSqrDTO = new BdcSlSqrDTO();
                                    exchangeDozerMapper.map(qlrxx, bdcSlSqrDO, "wwsq_zhlc_qlrxx_slsqr");
                                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                                        slSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                                    }
                                    if (null != qlrxx.getJtcy()) {
                                        List jtcylist = JSONArray.parseArray(qlrxx.getJtcy().toString());
                                        for (Object jtcy : jtcylist) {
                                            BdcSlJtcyDO jtcyDO = new BdcSlJtcyDO();
                                            exchangeDozerMapper.map(jtcy, jtcyDO, "jtcyxx");
                                            List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
                                            if (CheckParameter.checkAnyParameter(jtcyDO)) {
                                                jtcyDOList.add(jtcyDO);
                                                slSqrDTO.setBdcSlJtcyDOList(jtcyDOList);
                                            }
                                        }

                                    }
                                    bdcSlXmDTO.getBdcSlSqrDTOList().add(slSqrDTO);
                                }
                            }
                            xmDTOList.add(bdcSlXmDTO);
                        }

                    }
                    //异议信息
                    if (CollectionUtils.isNotEmpty(bdcdyxx.getYyxx())) {
                        for (InitZydyRequestYyxx yyxx : bdcdyxx.getYyxx()) {
                            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                            BdcSlYyDTO bdcSlYyDTO = new BdcSlYyDTO();

                            exchangeDozerMapper.map(bdcdyxx, bdcSlXmDTO, "wwsq_bdcdyxx_slxm");
                            exchangeDozerMapper.map(yyxx, bdcSlYyDTO, "wwsq_yy_yyxx");
                            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                            exchangeDozerMapper.map(data, dsfSlxxDTO, "wwsq_ql_dsfslxx");
                            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                            bdcSlXmDTO.setBdcSlQl(bdcSlYyDTO);

                            //如果有单独附件，给受理传附件，进行登记小类和附件关联
                            if (CollectionUtils.isNotEmpty(yyxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(yyxx.getFjxx());
                            }
                            // 处理权利人
                            // 处理权利人
                            if (CollectionUtils.isNotEmpty(yyxx.getQlrxx())) {
                                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                                bdcSlXmDTO.setBdcSlSqrDTOList(new ArrayList<>());
                                for (InitZyDyRequestQlrxx qlrxx : yyxx.getQlrxx()) {
                                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                    BdcSlSqrDTO slSqrDTO = new BdcSlSqrDTO();
                                    exchangeDozerMapper.map(qlrxx, bdcSlSqrDO, "wwsq_zhlc_qlrxx_slsqr");
                                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                                        slSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                                    }
                                    if (null != qlrxx.getJtcy()) {
                                        List jtcylist = JSONArray.parseArray(qlrxx.getJtcy().toString());
                                        for (Object jtcy : jtcylist) {
                                            BdcSlJtcyDO jtcyDO = new BdcSlJtcyDO();
                                            exchangeDozerMapper.map(jtcy, jtcyDO, "jtcyxx");
                                            List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
                                            if (CheckParameter.checkAnyParameter(jtcyDO)) {
                                                jtcyDOList.add(jtcyDO);
                                                slSqrDTO.setBdcSlJtcyDOList(jtcyDOList);
                                            }
                                        }

                                    }
                                    bdcSlXmDTO.getBdcSlSqrDTOList().add(slSqrDTO);
                                }
                            }
                            xmDTOList.add(bdcSlXmDTO);
                        }

                    }
                    //林权信息
                    if (CollectionUtils.isNotEmpty(bdcdyxx.getLqxx())) {
                        for (InitZyDyRequestLqxx lqxx : bdcdyxx.getLqxx()) {
                            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                            BdcSlLqDTO bdcSlLqDTO = new BdcSlLqDTO();

                            exchangeDozerMapper.map(bdcdyxx, bdcSlXmDTO, "wwsq_bdcdyxx_slxm");
                            exchangeDozerMapper.map(lqxx, bdcSlXmDTO, "wwsq_lq_xmxx");
                            exchangeDozerMapper.map(lqxx, bdcSlLqDTO, "wwsq_lq_lqxx");
                            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                            exchangeDozerMapper.map(data, dsfSlxxDTO, "wwsq_ql_dsfslxx");
                            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                            bdcSlXmDTO.setBdcSlQl(bdcSlLqDTO);

                            //如果有单独附件，给受理传附件，进行登记小类和附件关联
                            if (CollectionUtils.isNotEmpty(lqxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(lqxx.getFjxx());
                            }
                            // 处理权利人
                            if (CollectionUtils.isNotEmpty(lqxx.getQlrxx())) {
                                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                                bdcSlXmDTO.setBdcSlSqrDTOList(new ArrayList<>());
                                for (InitZyDyRequestQlrxx qlrxx : lqxx.getQlrxx()) {
                                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                    BdcSlSqrDTO slSqrDTO = new BdcSlSqrDTO();
                                    exchangeDozerMapper.map(qlrxx, bdcSlSqrDO, "wwsq_zhlc_qlrxx_slsqr");
                                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                                        slSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                                    }
                                    if (null != qlrxx.getJtcy()) {
                                        List jtcylist = JSONArray.parseArray(qlrxx.getJtcy().toString());
                                        for (Object jtcy : jtcylist) {
                                            BdcSlJtcyDO jtcyDO = new BdcSlJtcyDO();
                                            exchangeDozerMapper.map(jtcy, jtcyDO, "jtcyxx");
                                            List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
                                            if (CheckParameter.checkAnyParameter(jtcyDO)) {
                                                jtcyDOList.add(jtcyDO);
                                                slSqrDTO.setBdcSlJtcyDOList(jtcyDOList);
                                            }
                                        }

                                    }
                                    bdcSlXmDTO.getBdcSlSqrDTOList().add(slSqrDTO);
                                }
                            }
                            xmDTOList.add(bdcSlXmDTO);
                        }

                    }
                }
            }


        }
        LOGGER.info(JSON.toJSONString(wwsqCjBdcXmRequestDTO));
        return wwsqCjBdcXmRequestDTO;
    }

    private void initBdcXmLsgx(String yxmid, BdcSlXmDTO bdcSlXmDTO) {
        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO.setYxmid(yxmid);
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOS = new ArrayList<>();
        bdcSlXmLsgxDOS.add(bdcSlXmLsgxDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOS);
    }

    private boolean valYgQlByGzldyid(String sqdjlx) {
        List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyid(sqdjlx);
        if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
            return CommonConstantUtils.QLLX_YG_DM.equals(bdcDjxlPzDOList.get(0).getQllx());
        }
        return false;
    }

    /**
     * @param model
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 预告查询结果做处理（填充是否查封）
     */
    @Override
    public List<GetYgxxResponseData> dealGetWwsqYgxxResponse(GrdacxModel model) {
        List<GetYgxxResponseData> ygxxResponseDataList = new ArrayList<>();

        if (model != null && CollectionUtils.isNotEmpty(model.getData())) {
            for (GrdacxData data : model.getData()) {
                List<YgQlWithXmQlrDTO> ygList = data.getYgList();
                // 如果 本身不存在查封  则 调用房产接口查询查封
                if (CollectionUtils.isNotEmpty(data.getCfList())) {
                    data.setHasCf(true);
//                } else if (CollectionUtils.isNotEmpty(ygList) && !data.isHasCf()) {
//                    YgQlWithXmQlrDTO ygTemp = ygList.get(0);
//                    if (ygTemp != null && ygTemp.getBdcXmDO() != null
//                            && StringUtils.isNotBlank(ygTemp.getBdcXmDO().getYsfwbm())) {
//                        try {
//                            String ysfwbm = ygTemp.getBdcXmDO().getYsfwbm();
//                            Map<String, String> ycfParam = new HashMap<>();
//                            ycfParam.put("fwbm", ysfwbm);
//                            FcjyYcfByFwbmResponseDTO responseDTO
//                                    = exchangeBeanRequestService.request("hffcjyycfbyfwbm", ycfParam, FcjyYcfByFwbmResponseDTO.class);
//                            if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getData())) {
//                                ycfDatafor:
//                                for (FcjyYcfByFwbmResponseData ycfDataTemp : responseDTO.getData()) {
//                                    if (StringUtils.equals("限制", ycfDataTemp.getFwzt())) {
//                                        data.setHasCf(true);
//                                        break ycfDatafor;
//                                    }
//                                }
//                            }
//                        } catch (Exception e) {
//                            LOGGER.error("外网查询预告是否存在预查封接口异常", e);
//                        }
//                    }
                }
                /*
                只用不动产单元号查询时，组织数据结构
                 */
                if (data.onlyBdcdyhQuery) {
                    GetYgxxResponseData getYgxxResponseData = new GetYgxxResponseData();
                    exchangeDozerMapper.map(data, getYgxxResponseData, "getYgxxBybdcdyh");
                    for (GetYgxxResponseYgxx ygxx : getYgxxResponseData.getYgxx()) {
                        if (null == ygxx.getSfjzdyqjdywzrdm()) {
                            ygxx.setSfjzdyqjdywzrmc("");
                            ygxx.setSfjzdyqjdywzrdm("");
                        }
                    }
                    ygxxResponseDataList.add(getYgxxResponseData);
                    LOGGER.info(getYgxxResponseData.toString());
                } else {
                    //不是只用bdcdyh查询时，开始组织返回参数，原dozer对照不可用
                    for (YgQlWithXmQlrDTO ygQlWithXmQlrDTO : ygList) {
                        GetYgxxResponseData getYgxxResponseData = new GetYgxxResponseData();
                        String bdcdyh = ygQlWithXmQlrDTO.getBdcXmDO().getBdcdyh();
                        QueryQlRequestDTO requestDTO = new QueryQlRequestDTO();
                        requestDTO.setBdcdyh(bdcdyh);
                        //查询是否有抵押，查封
                        List<DyQlWithXmQlrDTO> dyaqList = commonService.listDyaqByBdcdyh(requestDTO);
                        List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(requestDTO);
                        if (CollectionUtils.isNotEmpty(dyaqList)) {
                            getYgxxResponseData.setSfdy("0");
                        } else {
                            getYgxxResponseData.setSfdy("1");
                        }
                        ;
                        if (CollectionUtils.isNotEmpty(cfList)) {
                            getYgxxResponseData.setSfcf("0");
                        } else {
                            getYgxxResponseData.setSfcf("1");
                        }
                        List<GetYgxxResponseYgxx> ygxxList = new ArrayList<>();
                        GetYgxxResponseYgxx ygxx = new GetYgxxResponseYgxx();
                        exchangeDozerMapper.map(ygQlWithXmQlrDTO.getBdcXmDO(), getYgxxResponseData, "xmxxToJbxx");
                        exchangeDozerMapper.map(ygQlWithXmQlrDTO, ygxx, "ygToYgxxV11");
                        if (null == ygQlWithXmQlrDTO.getBdcql().getJzzr()) {
                            ygxx.setSfjzdyqjdywzrdm("");
                            ygxx.setSfjzdyqjdywzrmc("");
                        }
                        ygxxList.add(ygxx);
                        getYgxxResponseData.setYgxx(ygxxList);
                        ygxxResponseDataList.add(getYgxxResponseData);
                        LOGGER.info(getYgxxResponseData.toString());

                    }
                }
            }
        }
        return ygxxResponseDataList;
    }

    /**
     * @param requestDTO
     * @return java.util.List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网请求 PDF 格式 SFXX
     */
    @Override
    public List<Map> sfxxPdf(SfdPdfRequestDTO requestDTO) {
        List<Map> resultList = new ArrayList<>();
        String spxtywh = requestDTO.getSpxtywh();
        List<BdcXmDO> bdcXmList = commonService.listBdcXmBySpxtywh(spxtywh);
        if (CollectionUtils.isEmpty(bdcXmList)) {
            throw new AppException("审批系统业务号：" + requestDTO.getSpxtywh() + "，在不动产登记系统中查询不到项目信息");
        }
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(bdcXmList.get(0).getGzlslid());
        // 组合流程类型
        if (xmlx == 2) {
            for (BdcXmDO temp : bdcXmList) {
                String xmlData = bdcSlPrintFeignService.packPrintDatas(temp.getGzlslid(),
                        requestDTO.getDylx(), requestDTO.getQlrlb(), temp.getXmid(), "");
                String baseCode = getPdfBaseCode(xmlData, requestDTO.getDylx());
                if (StringUtils.isNotBlank(baseCode)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("base64Code", baseCode);
                    resultList.add(map);
                }
            }
        } else {
            String gzlslid = bdcXmList.get(0).getGzlslid();
            String xmid = bdcXmList.get(0).getXmid();
            String xmlData = bdcSlPrintFeignService.packPrintDatas(gzlslid,
                    requestDTO.getDylx(), requestDTO.getQlrlb(), xmid, "");
            String baseCode = getPdfBaseCode(xmlData, requestDTO.getDylx());
            if (StringUtils.isNotBlank(baseCode)) {
                Map<String, String> map = new HashMap<>();
                map.put("base64Code", baseCode);
                resultList.add(map);
            }
        }
        return resultList;
    }

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 合肥 分页查询首次证明单
     */
    @Override
    public Page<Map> querySczmdByPage(Pageable pageable, Map<String, Object> paramMap) {
        // 工作流定义ID 集合
        String[] gzldyidArr = StringUtils.split(sczmdGzldyid, CommonConstantUtils.ZF_YW_DH);
        paramMap.put("gzldyidList", Arrays.asList(gzldyidArr));
        return repository.selectPaging("querySczmdByPageOrder", paramMap, pageable);
    }

    @Override
    public Page<Map> queryDyxxAndYgdyxxByPage(Pageable pageable, Map<String, Object> paramMap) {
        LOGGER.info("查询querDyAndYdyByPageOrder的参数为：{}", JSONObject.toJSONString(paramMap));
        Page<Map> dyxxAndYgdyxxPage = repository.selectPaging("querDyAndYdyByPageOrder", paramMap, pageable);
        if (null == dyxxAndYgdyxxPage || CollectionUtils.isEmpty(dyxxAndYgdyxxPage.getContent())) {
            return dyxxAndYgdyxxPage;
        }

        try {
            List<Map> content = dyxxAndYgdyxxPage.getContent();
            List<String> bdcdyhList = content.stream().map(e -> String.valueOf(e.get("BDCDYH"))).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(bdcdyhList)) {
                return dyxxAndYgdyxxPage;
            }

            List<List> bdcdyhLists = ListUtils.subList(bdcdyhList, 500);

            // 查询指定单元房地产权、建设用地使用权信息
            List<BdcFdcqDTO> fdcqList = new ArrayList<>();
            List<BdcJsydsyqDTO> jsydsyqList = new ArrayList<>();
            for (List subList : bdcdyhLists) {
                List<BdcFdcqDTO> subFdcqList = wwsqMapper.listBdcFdcqxx(subList);
                if (CollectionUtils.isNotEmpty(subFdcqList)) {
                    fdcqList.addAll(subFdcqList);
                }

                List<BdcJsydsyqDTO> subJsydsyqList = wwsqMapper.listBdcJsydsyqxx(subList);
                if (CollectionUtils.isNotEmpty(subJsydsyqList)) {
                    jsydsyqList.addAll(subJsydsyqList);
                }
            }

            // 转换数据
            Map<String, List<BdcFdcqDTO>> fdcqListMap = null;
            if (CollectionUtils.isNotEmpty(fdcqList)) {
                fdcqListMap = fdcqList.stream().collect(Collectors.groupingBy(BdcFdcqDTO::getBdcdyh));
            }

            Map<String, List<BdcJsydsyqDTO>> jsydsyqListMap = null;
            if (CollectionUtils.isNotEmpty(jsydsyqList)) {
                jsydsyqListMap = jsydsyqList.stream().collect(Collectors.groupingBy(BdcJsydsyqDTO::getBdcdyh));
            }

            // 匹配设置每条记录的产权证号、产权面积信息
            for (Map item : content) {
                BdcFdcqDTO itemFdcq = null;
                if (MapUtils.isNotEmpty(fdcqListMap)) {
                    List<BdcFdcqDTO> itemFdcqList = fdcqListMap.get(String.valueOf(item.get("BDCDYH")));
                    if (CollectionUtils.isNotEmpty(itemFdcqList) && null != itemFdcqList.get(0)) {
                        itemFdcq = itemFdcqList.get(0);
                    }
                }

                BdcJsydsyqDTO itemJsydsyq = null;
                if (MapUtils.isNotEmpty(jsydsyqListMap)) {
                    List<BdcJsydsyqDTO> itemJsydsyqList = jsydsyqListMap.get(String.valueOf(item.get("BDCDYH")));
                    if (CollectionUtils.isNotEmpty(itemJsydsyqList) && null != itemJsydsyqList.get(0)) {
                        itemJsydsyq = itemJsydsyqList.get(0);
                    }
                }

                if (null != itemFdcq) {
                    item.put(CQMJ, itemFdcq.getCqmj());
                    if (CQDY.equals(String.valueOf(item.get(TYPE)))) {
                        item.put(CQXMID, itemFdcq.getCqxmid());
                        item.put(CQZH, itemFdcq.getCqzh());
                    }
                }

                if (null != itemJsydsyq) {
                    if (CQDY.equals(String.valueOf(item.get(TYPE)))) {
                        if(null == item.get(CQXMID)) {
                            item.put(CQXMID, itemJsydsyq.getCqxmid());
                        }
                        if(null == item.get(CQZH)) {
                            item.put(CQZH, itemJsydsyq.getCqzh());
                        }
                    }
                    item.put(TDZH, itemJsydsyq.getCqzh());
                }
            }
        } catch (Exception e) {
            LOGGER.error("处理接口dyxxAndYgdyxx产权数据异常", e);
        }
        return dyxxAndYgdyxxPage;
    }

    @Override
    public Page<Map> queryDyxxYgdyxxForBdcdyhByPage(Pageable pageable, Map<String, Object> paramMap) {
        LOGGER.info("queryDyxxYgdyxxForBdcdyhByPage：{}", JSONObject.toJSONString(paramMap));
        Page<Map> mapPage = repository.selectPaging("queryDyxxYgdyxxForBdcdyhByPage", paramMap, pageable);
        if (null != mapPage && CollectionUtils.isNotEmpty(mapPage.getContent())) {
            for (Map data : mapPage.getContent()) {
                //开始判断bdcdyh是否存在多个现势产权
                if (Objects.nonNull(data.get("BDCDYH"))) {
                    Map queryMap = new HashMap(2);
                    queryMap.put("bdcdyh", data.get("BDCDYH"));
                    queryMap.put("qllxList",xmcqQllx);
                    List<BdcXmDO> xmDOList = repository.selectList("queryXmByBdcdyh", queryMap);
                    if (CollectionUtils.isNotEmpty(xmDOList) && xmDOList.size() > 1) {
                        LOGGER.info("数据存在多条现势产权!bdcdyh为：{}",data.get("BDCDYH"));
                        throw new AppException("数据存在多条现势产权！请检查数据！");
                    }
                }else {
                    throw new AppException("数据缺失bdcdyh！请检查数据！");
                }
            }
        }
        return mapPage;
    }


    @Override
    public Page<Map> queryDyxxOrYgdyxxByPage(Pageable pageable, Map<String, Object> paramMap) {
        LOGGER.info("查询queryDyxxOrYgdyxxByPage的参数为：{}", JSONObject.toJSONString(paramMap));
        if (Objects.nonNull(paramMap) && paramMap.containsKey("sfydy") && "1".equals(paramMap.get("sfydy"))) {
            return repository.selectPaging("querYdyByPageOrder", paramMap, pageable);
        } else {
            return repository.selectPaging("querDyByPageOrder", paramMap, pageable);
        }
    }

    /**
     * @param pageable
     * @param fwxxwithqxdmzlRequestDTO
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 合肥 用坐落 分页查询 业务系统的  房屋信息
     */
    @Override
    public Page<Map> queryFwxxWithZlByPage(Pageable pageable, FwxxwithqxdmzlRequestDTO fwxxwithqxdmzlRequestDTO) {
        if (fwxxwithqxdmzlRequestDTO == null) {
            throw new MissingArgumentException("");
        }
        return repository.selectPaging("queryFwxxWithZlByPageOrder", fwxxwithqxdmzlRequestDTO, pageable);
    }

    /**
     * @param responseDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询FWXX处理共有状态、抵押状态、查封状态、异议状态
     */
    @Override
    public FwxxbybdcdyhResponseDTO setZtTofwxxByBdcdyhResponse(FwxxbybdcdyhResponseDTO responseDTO) {
        if (StringUtils.isNotBlank(responseDTO.getBdcdyh())) {
            String bdcdyh = responseDTO.getBdcdyh();
            // 查询共有状态
            QueryQlRequestDTO fdcqReq = new QueryQlRequestDTO();
            fdcqReq.setWithXm(true);
            fdcqReq.setBdcdyh(bdcdyh);
            List<FdcqQlWithXmQlrDTO> fdcqList = commonService.listFdcqByBdcdyh(fdcqReq);
            if (CollectionUtils.isNotEmpty(fdcqList)) {
                BdcXmDO temp = fdcqList.get(0).getBdcXmDO();
                if (temp != null) {
                    if (CommonConstantUtils.GYFS_DDSY.equals(temp.getGyfs())) {
                        responseDTO.setSfgy(CommonConstantUtils.SF_F_DM + "");
                    } else if (CommonConstantUtils.GYFS_GTGY.equals(temp.getGyfs())
                            || CommonConstantUtils.GYFS_AFGY.equals(temp.getGyfs())
                            || CommonConstantUtils.GYFS_QTGY.equals(temp.getGyfs())) {
                        responseDTO.setSfgy(CommonConstantUtils.SF_S_DM + "");
                    }
                }
            }

            QueryQlRequestDTO xzqlReq = new QueryQlRequestDTO();
            xzqlReq.setBdcdyh(bdcdyh);
            // 查询 抵押状态
            List<DyQlWithXmQlrDTO> dyaqList = commonService.listDyaqByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(dyaqList)) {
                responseDTO.setSfdy(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfdy(CommonConstantUtils.SF_F_DM + "");
            }

            // 查询 查封状态
            List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(cfList)) {
                responseDTO.setSfcf(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfcf(CommonConstantUtils.SF_F_DM + "");
            }

            // 查询异议状态
            List<YyQlWithXmQlrDTO> yyList = commonService.listYyByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(yyList)) {
                responseDTO.setSfyy(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfyy(CommonConstantUtils.SF_F_DM + "");
            }
        }
        return responseDTO;
    }

    /**
     * @param responseDTO
     * @return cn.gtmap.realestate.exchange.core.dto.wwsq.ychsbyysfwbm.response.YchsByYsfwbmResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据预售房屋编码查询预测户室信息 处理限制权利状态
     */
    @Override
    public YchsByYsfwbmResponseDTO setZtToYchsByYsfwbmResponse(YchsByYsfwbmResponseDTO responseDTO) {
        if (StringUtils.isNotBlank(responseDTO.getBdcdyh())) {
            String bdcdyh = responseDTO.getBdcdyh();
            QueryQlRequestDTO xzqlReq = new QueryQlRequestDTO();
            xzqlReq.setBdcdyh(bdcdyh);
            // 查询 抵押状态
            List<DyQlWithXmQlrDTO> dyaqList = commonService.listDyaqByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(dyaqList)) {
                responseDTO.setSfdy(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfdy(CommonConstantUtils.SF_F_DM + "");
            }

            // 查询 查封状态
            List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(cfList)) {
                responseDTO.setSfcf(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfcf(CommonConstantUtils.SF_F_DM + "");
            }

            // 查询异议状态
            List<YyQlWithXmQlrDTO> yyList = commonService.listYyByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(yyList)) {
                responseDTO.setSfyy(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfyy(CommonConstantUtils.SF_F_DM + "");
            }
            if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_HF, dataversion)) {
                // 03-31 增加查询行政区
                String xzq = hfXzqFeignService.queryXzqByBdcdyh(bdcdyh, "yc", "");
                responseDTO.setXzqhszdm(xzq);
            }
        }
        return responseDTO;
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询现势产权的XMID
     */
    @Override
    public String getCqXmidByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<String> qllxList = new ArrayList<>();
            qllxList.addAll(Arrays.asList(SjptCxqlEnum.FDCQ.getQllxArr()));
            qllxList.addAll(Arrays.asList(SjptCxqlEnum.JSYDSYQ.getQllxArr()));
            qllxList.addAll(Arrays.asList(SjptCxqlEnum.TDSYQ.getQllxArr()));
            qllxList.addAll(Arrays.asList(SjptCxqlEnum.NYDSYQ.getQllxArr()));
            qllxList.addAll(Arrays.asList(SjptCxqlEnum.GJZWSYQ.getQllxArr()));
            for (String qllx : qllxList) {
                List<BdcQl> qlList = commonService.listXsQlByBdcdyh(bdcdyh, qllx);
                if (CollectionUtils.isNotEmpty(qlList)) {
                    return qlList.get(0).getXmid();
                }
            }
        }
        return null;
    }

    /**
     * @param jsonObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网申请 税务三要素
     */
    @Override
    public JSONObject wwsqCxSwsys(JSONObject jsonObject) {
        if (StringUtils.isBlank(jsonObject.getString("slbh"))) {
            throw new MissingArgumentException("slbh");
        }
        String slbh = jsonObject.getString("slbh");
        String sfyj = jsonObject.getString("sfyj");
        String fwuuid = jsonObject.getString("fwuuid");
        List<BdcSlHsxxDO> bdcSlHsxxDOList;
        List<BdcSlSysxxDO> bdcSlSysxxDOList;
        //是否月结为是,没有税务信息
        if (StringUtils.equals(CommonConstantUtils.SF_S_DM.toString(), sfyj)) {
            bdcSlSysxxDOList = Collections.emptyList();
        } else {
            // 先根据SLBH 查询 受理子系统 的 三要素
            bdcSlSysxxDOList = bdcSwFeignService.listSysxxBySlbh(slbh);
        }
        JSONObject resultJson = new JSONObject();
        if (CollectionUtils.isNotEmpty(bdcSlSysxxDOList)) {
            // 如果存在 直接返回
            List<BdcSlSysxxDO> sysxxDOList = new ArrayList<>();
            //每一条核税信息三要素需完整
            for (BdcSlSysxxDO bdcSlHsxxDO : bdcSlSysxxDOList) {
                if (StringUtils.isNotBlank(bdcSlHsxxDO.getDzsphm()) && StringUtils.isNotBlank(bdcSlHsxxDO.getNsrsbh()) && StringUtils.isNotBlank(bdcSlHsxxDO.getSwjgdm())) {
                    sysxxDOList.add(bdcSlHsxxDO);
                }
            }
            if (CollectionUtils.isNotEmpty(sysxxDOList)) {
                resultJson.put("result", JSONArray.parseArray(JSONObject.toJSONString(sysxxDOList)));
                return resultJson;
            }
        }

        if (StringUtils.isBlank(fwuuid)) {
            //查询基本信息表获取fwuuid
            BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh, "");
            if (Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getFwuuid())) {
                fwuuid = bdcSlJbxxDO.getFwuuid();
            } else {
                LOGGER.error("获取税务fwuuid异常，未获取到受理基本信息，受理编号：{}", slbh);
            }
        }
        Map<String, Object> slbhMap = new HashMap<>();
        slbhMap.put("slbh", slbh);
        slbhMap.put("fwuuid", fwuuid);
        slbhMap.put("tdbz", getTdbzBySlbh(slbh));
        //获取三要素接口
        String beanid = "swsys_dk";
        if (StringUtils.equals(swsysVersion, "yt")) {
            beanid = "swsys";
        }
        Object sysResult = exchangeBeanRequestService.request(beanid, JSONObject.parseObject(JSONObject.toJSONString(slbhMap)));
        if (sysResult == null) {
            LOGGER.error("税务三要素接口查询结果为空");
        } else {
            // 将sysResult 保存到 受理库
            JSONObject sysxxJsonObject = new JSONObject();
            sysxxJsonObject.put("slbh", slbh);
            sysxxJsonObject.put("sysxx", sysResult);
            LOGGER.info("开始保存税务三要素信息：{}", sysxxJsonObject.toString());
            exchangeBeanRequestService.request("saveSwsys", sysxxJsonObject);

            // 组织税费三要素
            List<BdcSlSysxxDO> bdcSlSysxxDOS = bdcSwFeignService.listSysxxBySlbh(slbh);
            if(CollectionUtils.isNotEmpty(bdcSlSysxxDOS)){
                resultJson.put("result", bdcSlSysxxDOS);
            }else{
                resultJson.put("result", sysResult);
            }
        }
        return resultJson;
    }


    /**
     * @param rygfqkRequestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.domain.GxRygfqkDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询人员购房情况
     */
    @Override
    public List<GxRygfqkDO> listGxRygfqk(RygfqkRequestDTO rygfqkRequestDTO) {
        if (rygfqkRequestDTO != null && CheckParameter.checkAnyParameter(rygfqkRequestDTO)) {
            List<GxRygfqkDO> list = rygfqkRequestDTO.getRyxx();
            if (CollectionUtils.isEmpty(list)) {
                throw new AppException("人员信息不能为空");
            }
            for (GxRygfqkDO gxRygfqkDO : list) {
                if (StringUtils.isBlank(gxRygfqkDO.getZjh())) {
                    throw new AppException("证件号参数不能为空");
                }
            }
            return wwsqMapper.listRygfqk(rygfqkRequestDTO);
        }
        return null;
    }


    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    private String getPdfBaseCode(String xmlData, String dylx) {
        if (StringUtils.isBlank(xmlData)) {
            return "";
        }
        // 临时pdf文件名
        String pdfFileName = UUIDGenerator.generate16() + ".pdf";
        FileOutputStream fileOutStream = null;
        FileInputStream pdfInputStream = null;
        String ftlName = dylx + ".ftl";
        byte[] data = null;
        try {
            LOGGER.info("导出pdf文件中间临时pdf文件：{}", pdfFileName);
            fileOutStream = new FileOutputStream(new File(pdfFileName));
            String html = htmlToPdf.getHtmlString(getDataList(xmlData), ftlName);
            htmlToPdf.createPDF(fileOutStream, html);
            // 读取pdf文件
            pdfInputStream = new FileInputStream(pdfFileName);
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = pdfInputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
            String base64 = Base64Utils.encode(data);
            LOGGER.info("导出pdf文件base64码成功！");
            return base64;
        } catch (Exception e) {
            LOGGER.error("系统导出PDF报错：{}", e.getMessage());
        } finally {
            if (null != fileOutStream) {
                try {
                    fileOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != pdfInputStream) {
                try {
                    pdfInputStream.close();
                    File pdfFile = new File(pdfFileName);
                    if (pdfFile.exists()) {
                        pdfFile.delete();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 根据mapid做转换权利人数据
     *
     * @param xmid
     * @param qlrlb
     * @param mapId
     * @return 整体结构
     */
    public List<Map> qlrsjConvert(String xmid, String qlrlb, String mapId) {
        List<Map> list = new ArrayList<>();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(qlrlb);
        if (StringUtils.isNotBlank(bdcQlrQO.getXmid())) {
            List<BdcQlrDO> qlrList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (BdcQlrDO bdcQlrDO : qlrList) {
                    Map map = exchangeDozerMapper.map(bdcQlrDO, Map.class, mapId);
                    if (MapUtils.isNotEmpty(map)) {
                        list.add(map);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 根据mapid做转换权利人数据
     *
     * @param xmid
     * @param qlrlb
     * @param mapId
     * @return 整体结构
     */
    public List<QlrlistResponse> qlrsjConvertQlrBean(String xmid, String qlrlb, String mapId) {
        List<QlrlistResponse> list = new ArrayList<>();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(qlrlb);
        if (StringUtils.isNotBlank(bdcQlrQO.getXmid())) {
            List<BdcQlrDO> qlrList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (BdcQlrDO bdcQlrDO : qlrList) {
                    QlrlistResponse object = exchangeDozerMapper.map(bdcQlrDO, QlrlistResponse.class, mapId);
                    if (Objects.nonNull(object)) {
                        list.add(object);
                    }
                }
            }
        }
        return list;
    }


    /**
     * 获取打印的模板值
     *
     * @param xmlData
     * @return
     * @throws DocumentException
     */
    private Map<String, Object> getDataList(String xmlData) throws DocumentException {
        Map<String, Object> result = new HashMap<>();
        Document document = DocumentHelper.parseText(xmlData);
        Element element = document.getRootElement();
        if (null == element) {
            return null;
        }
        // 获取所有的<datas>节点
        List<Element> datasList = element.elements("datas");
        if (CollectionUtils.isNotEmpty(datasList) && null != datasList.get(0)) {
            // 获取所有的<data>节点
            List<Element> dataList = datasList.get(0).elements("data");
            if (CollectionUtils.isNotEmpty(dataList)) {
                for (Element data : dataList) {
                    String name = data.attribute("name").getValue();
                    result.put(name, data.getText());
                }
            }
        }
        // 获取所有的<detail>节点
        List<Element> detalList = element.elements("detail");
        if (CollectionUtils.isNotEmpty(detalList) && detalList.get(0) != null) {
            List<Element> rowList = detalList.get(0).elements("row");
            if (CollectionUtils.isNotEmpty(rowList)) {
                List<Map> list = new ArrayList<>();
                result.put("recordList", list);
                for (Element ele : rowList) {
                    // 获取所有的<data>节点
                    List<Element> dataList = ele.elements("data");
                    if (CollectionUtils.isNotEmpty(dataList)) {
                        // 获取
                        Map<String, Object> map = new HashMap<>(dataList.size());
                        for (Element data : dataList) {
                            String name = data.attribute("name").getValue();
                            map.put(name, data.getText());
                        }
                        list.add(map);
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param bdcZfxxQO
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 根据权利人名称、证件号查询住房信息
     */
    @Override
    public List<BdcZfxxDTO> getWwsqZfxx(BdcZfxxQO bdcZfxxQO) {
        if (null != bdcZfxxQO) {
            bdcZfxxQO.setSfghyt("N");
        }
        List<BdcZfxxDTO> zfxxDTOList = bdcZfxxCxFeignService.listBdcZfxxDTO(bdcZfxxQO);
        if (CollectionUtils.isNotEmpty(zfxxDTOList)) {
            Set<BdcZfxxDTO> set = new TreeSet<>(new Comparator<BdcZfxxDTO>() {
                @Override
                public int compare(BdcZfxxDTO o1, BdcZfxxDTO o2) {
                    // 根据bdcdyh去重
                    return o1.getBdcdyh().compareTo(o2.getBdcdyh());
                }
            });
            set.addAll(zfxxDTOList);
            return new ArrayList<>(set);
        }
        return new ArrayList<>();
    }


    /**
     * 根据受理编号（必填），不动产权证号（选填）查询套打证书信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<BdcZsDO> queryZsxx(JSONObject paramMap) {
        if (StringUtils.isBlank(MapUtils.getString(paramMap, "slbh"))) {
            throw new MissingArgumentException("受理编号查询不能为空！");
        }
        return wwsqMapper.listBdcZs(paramMap);
    }


    /**
     * @param paramJob
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 合肥根据xmid获合同备案信息接口
     */
    public Object wwsqFcjyBaxxByXmid(JSONObject paramJob) {
        if (null != paramJob) {
            JSONObject paramData = paramJob.getJSONObject("data");
            if (null != paramData) {
                List<Map<String, String>> mapList = bdcXmMapper.getQlrAndBdcqzhByXmid((String) paramData.get("xmid"));
                if (CollectionUtils.isNotEmpty(mapList)) {
                    for (Map<String, String> map : mapList) {
                        Object obj = getBaxxByZjhAndCqzh(map.get("ZJH"), map.get("BDCQZH"), map.get("YXTCQZH"), "wwsqFcjyBaxx");
                        if (getBaxxByZjhAndCqzh(obj)) {
                            JSONObject result = new JSONObject();
                            result.put("data", obj);
                            return result;
                        }
                    }
                }
            }

        }
        return new JSONObject();
    }

    private boolean getBaxxByZjhAndCqzh(Object obj) {
        boolean flag = false;
        if (null != obj) {
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(obj));
            if (CollectionUtils.isNotEmpty(jsonArray)) {
                JSONObject fcjyClfHtxx = jsonArray.getJSONObject(0);
                if (null != fcjyClfHtxx && StringUtils.isNotBlank(fcjyClfHtxx.getString("fcbahth"))) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public Object getBaxxByZjhAndCqzh(String zjh, String bdcqzh, String yxtcqzh, String beanName) {
        Object obj = fcjyService.getBaxxByZjhAndCqzh(zjh, bdcqzh, beanName);
        if (getBaxxByZjhAndCqzh(obj)) {
            return obj;
        }
        return fcjyService.getBaxxByZjhAndCqzh(zjh, yxtcqzh, beanName);
    }


    /**
     * 合肥-互联网+银行  推送附件材料
     *
     * @param paramMap
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:42 2020/10/14
     */
    @Override
    public Map<String, Object> tsfjcl(JSONObject paramMap) {
        JSONObject data = paramMap.getJSONObject("data");

        Map<String, Object> resultMap = new HashMap<>();

        String spxtywh = data.getString("spxtywh");
        String slbh = data.getString("slbh");
        //57417 【标准版】登记3.0 tsWwsqFjxx接口修改    互联网结构调整，2个格式都要支持
        if (StringUtils.isBlank(spxtywh)) {
            spxtywh = data.getString("proid");
        }
        List<BdcDsfSjclDTO> sjclDTOList = JSONObject.parseArray(data.getString("fjxx").toString(), BdcDsfSjclDTO.class);
        String gzlslid = null;
        if (StringUtils.isNotBlank(spxtywh)) {
            LOGGER.info("审批系统业务号：{}", spxtywh);
            List<BdcXmDO> xmDOList = commonService.listBdcXmBySpxtywh(spxtywh);
            if (CollectionUtils.isEmpty(xmDOList)) {
                throw new AppException("审批系统业务号：" + spxtywh + "，在不动产登记系统中查询不到项目信息");
            }
            gzlslid = xmDOList.get(0).getGzlslid();

        } else {
            LOGGER.info("受理编号：{}", slbh);
            List<BdcXmDO> xmDOList = commonService.listBdcXmBySlbh(slbh);
            if (CollectionUtils.isEmpty(xmDOList)) {
                throw new AppException("受理编号：" + slbh + "，在不动产登记系统中查询不到项目信息");
            }
            gzlslid = xmDOList.get(0).getGzlslid();
        }
        //验证收件材料名称不能为空
        for (BdcDsfSjclDTO dsfSjclDTO : sjclDTOList) {
            if (StringUtils.isBlank(dsfSjclDTO.getClmc())) {
                throw new MissingArgumentException("材料名称不能为空！");
            }
        }
        try {
            bdcSlSjclFeignService.saveDsfSjcl(gzlslid, sjclDTOList);
            resultMap.put("msg", ResponseCodeEnum.SUCCESS_YW.msg);
            resultMap.put("code", ResponseCodeEnum.SUCCESS_YW.code);
            resultMap.put("spxtywh", spxtywh);

        } catch (IOException e) {
            LOGGER.error("附件上传失败！spxtywh为：{}， gzlsid是{}", spxtywh, gzlslid);
            e.printStackTrace();
        }
        if (MapUtils.isEmpty(resultMap)) {
            resultMap.put("spxtywh", spxtywh);
            resultMap.put("msg", ResponseCodeEnum.DATA_LACK.msg);
            resultMap.put("code", ResponseCodeEnum.DATA_LACK.code);
        }

        return resultMap;
    }

    @Override
    public String getWwsqCqzxxXzxx(FwqlCxRequestQlr fwqlCxRequestQlr) {
        StringBuffer sb = new StringBuffer();
        BdcFwqlQO bdcFwqlQO = new BdcFwqlQO();
        bdcFwqlQO.setQlrxx(new ArrayList<>());
        BdcFwqlQlrQO fwqlQlrQO = new BdcFwqlQlrQO();
        // 此处修改为只用证件号查询 考虑有做过人名变更的情况
        fwqlQlrQO.setZjh(fwqlCxRequestQlr.getSfzh());
        fwqlQlrQO.setBdcdyh(fwqlCxRequestQlr.getBdcdyh());
        fwqlQlrQO.setBdcqzh(fwqlCxRequestQlr.getCqzh());
        fwqlQlrQO.setZl(fwqlCxRequestQlr.getZl());
        bdcFwqlQO.getQlrxx().add(fwqlQlrQO);

        List<BdcFwqlDTO> bdcFwqlList = bdcZfxxCxFeignService.listBdcFwqlDTO(bdcFwqlQO);
        if (CollectionUtils.isEmpty(bdcFwqlList)) {
            return "";
        }
        List<FwqlCxResponseCfxx> cfxxlist = new ArrayList<>();
        List<FwqlCxResponseDyaqxx> dyaqxxlist = new ArrayList<>();
        List<FwqlCxResponseSdxx> sdxxlist = new ArrayList<>();

        for (BdcFwqlDTO bdcFwqlDTO : bdcFwqlList) {
            FwqlCxResponseQl fwqlCxResponseQl = new FwqlCxResponseQl();
            // 处理限制状态
            QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
            queryQlRequestDTO.setBdcdyh(bdcFwqlDTO.getBdcdyh());
            queryQlRequestDTO.setWithXm(true);
            Map<String, Object> xzztMap = getXzzt(queryQlRequestDTO, true);
            if (xzztMap != null) {
                fwqlCxResponseQl.setXzzt(MapUtils.getString(xzztMap, "xzzt"));
                // 查封信息
                cfxxlist = (List<FwqlCxResponseCfxx>) MapUtils.getObject(xzztMap, "cfxxList");
                // 抵押信息
                dyaqxxlist = (List<FwqlCxResponseDyaqxx>) MapUtils.getObject(xzztMap, "dyaqxxList");
            }
            // 锁定信息
            sdxxlist = getSdxx(bdcFwqlDTO.getBdcdyh(), bdcFwqlDTO.getXmid());
        }
        if (CollectionUtils.isNotEmpty(cfxxlist)) {
            sb.append("查封");
        }
        if (CollectionUtils.isNotEmpty(dyaqxxlist)) {
            if (StringUtils.isNotBlank(sb.toString())) {
                sb.append("、");
            }
            sb.append("抵押");
        }
        if (CollectionUtils.isNotEmpty(sdxxlist)) {
            if (StringUtils.isNotBlank(sb.toString())) {
                sb.append("、");
            }
            sb.append("锁定");
        }
        return sb.toString();
    }

    /**
     * 获取限制状态信息
     *
     * @param queryQlRequestDTO
     * @param xzxx              是否返回限制信息具体数据
     * @return
     */
    private Map<String, Object> getXzzt(QueryQlRequestDTO queryQlRequestDTO, boolean xzxx) {
        Map<String, Object> resultMap = new HashMap<>();
        String xzzt = "";
        List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(cfList)) {
            xzzt += "查封";
            if (xzxx) {
                List<FwqlCxResponseCfxx> cfxxList = new ArrayList<>();
                for (CfQlWithXmQlrDTO dto : cfList) {
                    FwqlCxResponseCfxx cf = new FwqlCxResponseCfxx();
                    dozerBeanMapper.map(dto.getBdcql(), cf, "bdcCfToResponseCf");
                    if (dto.getBdcXmDO() != null) {
                        dozerBeanMapper.map(dto.getBdcXmDO(), cf, "bdcXmToResponseCf");
                    }
                    String cfqx = "";
                    if (dto.getBdcql().getCfqssj() != null) {
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfqssj()) + "起";
                    }
                    if (dto.getBdcql().getCfjssj() != null) {
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfjssj()) + "止";
                    }
                    cf.setCfqx(cfqx);
                    cfxxList.add(cf);
                }
                resultMap.put("cfxxList", cfxxList);
            }

        }
        List<DyQlWithXmQlrDTO> dyaqList = commonService.listDyaqByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(dyaqList)) {
            if (xzzt.length() == 0) {
                xzzt += "抵押";
            } else {
                xzzt += "/抵押";
            }
            List<FwqlCxResponseDyaqxx> dyaqxxList = new ArrayList<>();
            for (DyQlWithXmQlrDTO dto : dyaqList) {
                FwqlCxResponseDyaqxx dyaqxx = new FwqlCxResponseDyaqxx();
                dozerBeanMapper.map(dto.getBdcql(), dyaqxx, "bdcDyaqToResponseDyaq");
                if (dto.getBdcXmDO() != null) {
                    dozerBeanMapper.map(dto.getBdcXmDO(), dyaqxx, "bdcXmToResponseDyaq");
                }
                List<BdcQlrDO> bdcQlrDOList = commonService.listBdcQlrByXmid(dto.getBdcql().getXmid(), CommonConstantUtils.QLRLB_YWR);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    dyaqxx.setDyaqlr(bdcQlrDOList.get(0).getQlrmc());
                    dyaqxx.setDjzmh(bdcQlrDOList.get(0).getBdcqzh());
                }
                String zwlxqx = "";
                if (dto.getBdcql().getZwlxqssj() != null) {
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxqssj()) + "起";
                }
                if (dto.getBdcql().getZwlxjssj() != null) {
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxjssj()) + "止";
                }
                dyaqxx.setZwlxqx(zwlxqx);
                if (dyaqxx != null) {
                    dyaqxxList.add(dyaqxx);
                }
            }
            resultMap.put("dyaqxxList", dyaqxxList);
        }
        resultMap.put("xzzt", xzzt);
        return resultMap;
    }

    /**
     * 查询锁定信息  包括不动产单元锁定和证书锁定
     *
     * @param bdcdyh
     * @param xmid
     * @return
     */
    private List<FwqlCxResponseSdxx> getSdxx(String bdcdyh, String xmid) {
        List<FwqlCxResponseSdxx> responseSdxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            // 单元锁定
            List<BdcDysdDO> dysdDOList = commonService.listDysdByBdcdyh(bdcdyh, CommonConstantUtils.SDZT_SD);
            if (CollectionUtils.isNotEmpty(dysdDOList)) {
                for (BdcDysdDO dysdDO : dysdDOList) {
                    FwqlCxResponseSdxx sdxx = new FwqlCxResponseSdxx();
                    dozerBeanMapper.map(dysdDO, sdxx, "bdcDysdToResponseSdxx");
                    responseSdxxList.add(sdxx);
                }
            }
        }
        if (StringUtils.isNotBlank(xmid)) {
            // 证书锁定
            List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(xmid, CommonConstantUtils.SDZT_SD);
            if (CollectionUtils.isNotEmpty(zssdDOList)) {
                for (BdcZssdDO zssdDO : zssdDOList) {
                    FwqlCxResponseSdxx sdxx = new FwqlCxResponseSdxx();
                    dozerBeanMapper.map(zssdDO, sdxx, "bdcZssdToResponseSdxx");
                    responseSdxxList.add(sdxx);
                }
            }
        }
        return responseSdxxList;
    }

    /**
     * 查询异议信息
     *
     * @param queryQlRequestDTO
     * @return
     */
    private List<FwqlCxResponseYyxx> getYyxx(QueryQlRequestDTO queryQlRequestDTO) {
        List<FwqlCxResponseYyxx> yyxxList = new ArrayList<>();
        List<YyQlWithXmQlrDTO> yyList = commonService.listYyByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(yyList)) {
            for (YyQlWithXmQlrDTO dto : yyList) {
                FwqlCxResponseYyxx yy = new FwqlCxResponseYyxx();
                dozerBeanMapper.map(dto.getBdcql(), yy, "bdcYyToResponseYy");
                if (dto.getBdcXmDO() != null) {
                    dozerBeanMapper.map(dto.getBdcXmDO(), yy, "bdcXmToResponseYy");
                }
                String qlqx = "";
                if (dto.getBdcql().getYydjksrq() != null) {
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjksrq()) + "起";
                }
                if (dto.getBdcql().getYydjjsrq() != null) {
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjjsrq()) + "止";
                }
                yy.setQlqx(qlqx);
                List<BdcQlrDO> yyQlrs = commonService.listBdcQlrByXmid(dto.getBdcql().getXmid(), CommonConstantUtils.QLRLB_QLR);
                if (CollectionUtils.isNotEmpty(yyQlrs)) {
                    StringBuffer sqr = new StringBuffer();
                    StringBuffer yydjzmh = new StringBuffer();
                    for (BdcQlrDO bdcQlr : yyQlrs) {
                        sqr.append(bdcQlr.getQlrmc() + ",");
                        yydjzmh.append(bdcQlr.getBdcqzh() + ",");
                    }
                    yy.setSqr(sqr.substring(0, sqr.length() - 1));
                    yy.setYydjzmh(yydjzmh.substring(0, yydjzmh.length() - 1));
                }
                yyxxList.add(yy);
            }
        }
        return yyxxList;
    }

    /**
     * 查询预告信息
     *
     * @param queryQlRequestDTO
     * @return
     */
    private List<FwqlCxResponseYgxx> getYgxx(QueryQlRequestDTO queryQlRequestDTO) {
        List<FwqlCxResponseYgxx> ygxxList = new ArrayList<>();
        List<YgQlWithXmQlrDTO> ygList = commonService.listYgByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(ygList)) {
            for (YgQlWithXmQlrDTO dto : ygList) {
                FwqlCxResponseYgxx yg = new FwqlCxResponseYgxx();
                dozerBeanMapper.map(dto.getBdcql(), yg, "bdcYgToResponseYg");
                if (dto.getBdcXmDO() != null) {
                    dozerBeanMapper.map(dto.getBdcXmDO(), yg, "bdcXmToResponseYg");
                }
                ygxxList.add(yg);
            }
        }
        return ygxxList;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh创建登记流程
     */
    @Override
    public Map<String, Object> bdcdycj(BdcCshSlxmDTO bdcCshSlxmDTO) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("gzlslid", "");
        dataMap.put("ywslbh", "");
        String msg = "success";
        try {
            //首先进行规则验证
            BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
            exchangeDozerMapper.map(bdcCshSlxmDTO, bdcSlxxDTO, "bdcdycj_toBdcSlxxDTO");
            List<Map<String, Object>> yzResult = bdcSlFeignService.wwsqCjBdcXmGzyz(bdcSlxxDTO);
            if (CollectionUtils.isNotEmpty(yzResult)) {
                StringBuilder msgSb = new StringBuilder();
                for (Map<String, Object> map : yzResult) {
                    String bdcdyh = StringUtils.defaultString(MapUtils.getString(map, "bdcdyh"));
                    String yzmsg = StringUtils.defaultString(MapUtils.getString(map, "msg"));
                    if (StringUtils.isNotBlank(yzmsg) && yzmsg.contains(bdcdyh)) {
                        msgSb.append(yzmsg).append(",");
                    } else {
                        msgSb.append(bdcdyh).append(yzmsg).append(",");
                    }
                }
                msg = msgSb.toString();
                if (msg.endsWith(",")) {
                    msg = msg.substring(0, msg.length() - 1);
                }
            }
        } catch (Exception e) {
            msg = "规则验证异常";
            LOGGER.error("基本信息ID:{}根据bdcdyh创建登记流程规则验证异常:{}", bdcCshSlxmDTO.getJbxxid(), e);
        }
        if (StringUtils.equals("success", msg)) {
            try {
                //添加不动产单元
                bdcSlXmFeignService.cshYxxm(bdcCshSlxmDTO.getCzrid(), bdcCshSlxmDTO);
                BdcSlCshDTO bdcSlCshDTO = new BdcSlCshDTO();
                BeanUtil.copyBean(bdcCshSlxmDTO, bdcSlCshDTO);
                //初始化创建
                Map<String, String> cjResult = bdcSlFeignService.cshCjBdcXm(bdcSlCshDTO);
                if (StringUtils.isNotBlank(cjResult.get("processInsId"))) {
                    dataMap.put("gzlslid", cjResult.get("processInsId"));
                    dataMap.put("ywslbh", cjResult.get("slbh"));
                } else {
                    msg = "没有创建登记项目";
                }
            } catch (Exception e) {

                LOGGER.error("基本信息ID:{}根据bdcdyh创建登记流程异常:{}", bdcCshSlxmDTO.getJbxxid(), e);
                msg = ExceptionUtils.getFeignErrorMsg(e);
            }
        }
        dataMap.put("msg", msg);

        resultMap.put("data", dataMap);
        return resultMap;


    }

    @Override
    public WwsqCjBdcXmRequestDTO initCfjfdjParamDTO(InitCfjfdjRequestDTO initCfjfdjRequestDTO) {
        LOGGER.info("---查封解封登记入参:{}", JSONObject.toJSONString(initCfjfdjRequestDTO));
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        // 受理角色控制 需要使用 固定的外网角色编码
        wwsqCjBdcXmRequestDTO.setSlRoleCode("wwsqsl");

        List<InitCfjfdjRquestDataDTO> dataDTOS = initCfjfdjRequestDTO.getData();
        if (CollectionUtils.isEmpty(dataDTOS)) {
            return wwsqCjBdcXmRequestDTO;
        }
        if (Objects.isNull(dataDTOS.get(0))) {
            throw new AppException("参数不能为空!");
        }
        if (StringUtils.isEmpty(dataDTOS.get(0).getSqdjlx())) {
            throw new AppException("sqdjlx不能为空!");
        }
        // 增加是否进行规则验证
        wwsqCjBdcXmRequestDTO.setGzyz(true);
        dataDTOS.forEach(data -> {

            BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();

            /**
             * 不动产受理基本信息
             */
            BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
            exchangeDozerMapper.map(data, bdcSlJbxxDO, "court_cfjfdj_jbxx");

            bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);

            /**
             * 项目类模型集合
             */
            List<BdcSlXmDTO> xmDTOList = new ArrayList<>();
            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
            exchangeDozerMapper.map(data, bdcSlXmDTO, "court_cfjfdj_slxm");
            exchangeDozerMapper.map(data.getCfxx(), bdcSlXmDTO, "court_cfjfdj_cfjfxm");
            xmDTOList.add(bdcSlXmDTO);

            bdcSlxxDTO.setBdcSlXmList(xmDTOList);

            wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);
        });
        LOGGER.info("---查封解封登记出参:{}", JSONObject.toJSONString(wwsqCjBdcXmRequestDTO));
        return wwsqCjBdcXmRequestDTO;
    }

    @Override
    public void zdbjOrzdzf(WwsqCjBdcXmResponseDTO responseDTO, String slr, String sfzdbj, String zdzf) {
        LOGGER.info("---自动办结或自动转发:{},slr:{},zdbj:{},zdzf:{}", responseDTO, slr, sfzdbj, zdzf);
        /**
         * 【37141】银行系统推送抵押注销的办件（slly": "6"），若传参“zdzf”为1，需自动转发至初审节点
         *
         * 当自动办结和自动转发同时传参优先自动办结
         */
        if ("1".equals(sfzdbj)) {
            LOGGER.info("---开始自动办结:{},工作流实例id:{}", zdzf, responseDTO.getBdcXmDOList().get(0).getGzlslid());
            zdbjWithCanBj(responseDTO, slr, true);
        } else if (StringUtils.equals(Constants.WWSQ_CJXM_AUTOTURN, zdzf)) {
            if (CollectionUtils.isEmpty(responseDTO.getGzyzList())) {
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                LOGGER.info("---自动转发:{},工作流实例id:{},自动转发人：{}", zdzf, gzlslid, slr);
                if (turnWorkBySlr) {
                    autoTurnWorkflow(gzlslid, zdzf, slr);
                } else {
                    autoTurnWorkflow(gzlslid, zdzf, "");
                }
            }

        }
    }

    /**
     * @param spxtywh
     * @param responseDTO
     * @param fjclList
     * @param wwsqCjBdcXmRequestDTO
     * @param zdzf
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description Court 转换 外网申请创建结果
     */
    @Override
    public ExchangeDsfCommonResponse<CourtCfResponseDTo> revertCjResponseCourt(String spxtywh, Object responseDTO, List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf) {
        try {
            if (responseDTO instanceof ExchangeDsfCommonResponse) {
                return (ExchangeDsfCommonResponse) responseDTO;
            }
            WwsqCjBdcXmResponseDTO tempResponseDTO = (WwsqCjBdcXmResponseDTO) responseDTO;
            Map<String, Object> responseMap = revertCjResponse(spxtywh, tempResponseDTO, fjclList, wwsqCjBdcXmRequestDTO, zdzf);
            if (responseMap.containsKey("msg") && "success".equals(responseMap.get("msg"))) {
                CourtCfResponseDTo cfResponseDTo = new CourtCfResponseDTo();
                List<CourtCfxmxxResponseDTo> cfxmxx = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(tempResponseDTO.getBdcXmDOList())) {
                    for (BdcXmDO xmxx : tempResponseDTO.getBdcXmDOList()) {
                        if (Objects.nonNull(xmxx)) {
                            CourtCfxmxxResponseDTo courtCfxmxxResponseDTo = new CourtCfxmxxResponseDTo();
                            courtCfxmxxResponseDTo.setBdcdyh(xmxx.getBdcdyh());
                            courtCfxmxxResponseDTo.setBdcqzh(xmxx.getBdcqzh());
                            courtCfxmxxResponseDTo.setXmid(xmxx.getXmid());
                            cfResponseDTo.setSlbh(xmxx.getSlbh());
                            cfxmxx.add(courtCfxmxxResponseDTo);
                        }
                    }
                }
                cfResponseDTo.setXmxx(cfxmxx);

                List<TaskData> taskDataList = processTaskClient.listProcessTask((String) responseMap.get("gzlslid"));
                if (taskDataList != null && taskDataList.size() > 0) {
                    for (int i = 0; i < taskDataList.size(); i++) {
                        if (StringUtils.equals(taskDataList.get(i).getTaskName(), CommonConstantUtils.JD_SL) && StringUtils.isNotBlank(taskDataList.get(i).getTaskId())) {
                            cfResponseDTo.setTaskId(taskDataList.get(i).getTaskId());
                            break;
                        }
                    }
                    if (StringUtils.isBlank(cfResponseDTo.getTaskId())) {
                        return ExchangeDsfCommonResponse.fail("创建成功，获取taskId失败", cfResponseDTo);
                    }
                    return ExchangeDsfCommonResponse.ok(cfResponseDTo);
                } else {
                    LOGGER.info("---外网创建自动转发异常,获取taskId失败:{},获取taskId入参：{}", responseMap, JSON.toJSONString(taskDataList));
                    return ExchangeDsfCommonResponse.fail("外网创建自动转发异常,获取taskId失败");
                }
            } else {
                LOGGER.info("---法院创建失败:{}", responseMap);

                return ExchangeDsfCommonResponse.fail("0", (String) responseMap.get("msg"), new CourtCfResponseDTo());
            }
        } catch (Exception e) {
            LOGGER.error("---法院创建异常:{}", e.getMessage());
            return ExchangeDsfCommonResponse.fail(e.getMessage());
        }
    }

    /**
     * @param wwsqCjBdcXmRequestDTO
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网申请创建项目
     */
    @Override
    public Object acceptCjServiceForCourt(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception {
        try {
            return bdcSlFeignService.wwsqCjBdcXm(wwsqCjBdcXmRequestDTO);
        } catch (Exception e) {
            LOGGER.error("---外网申请创建项目异常:{}", e.getMessage());
            return ExchangeDsfCommonResponse.fail(e.getMessage());
        }
    }

    /**
     * 盐城-档案够公司扫描附件上传
     *
     * @param fjxxRequest fjxxRequest
     * @return Map
     * @Date 2020/12/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Map<String, Object> dagsUpdatafjxx(fjxxRequest fjxxRequest) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        if (null != fjxxRequest) {
            LOGGER.info("slbh,{}", fjxxRequest.toString());

            if (StringUtils.isNotBlank(fjxxRequest.getSlbh()) && CollectionUtils.isNotEmpty(fjxxRequest.getFjxx())) {
                List<BdcXmDTO> xmDO = bdcXmFeignService.listBdcXmBfxxBySlbh(fjxxRequest.getSlbh());
                if (CollectionUtils.isNotEmpty(xmDO)) {
                    //获取附件信息-附件明细-附件内容；
                    List<FjclDTO> fjxx = fjxxRequest.getFjxx();
                    //判断fjurl和fjnr字段，其中一个必填；
                    boolean resultBt = this.cheakBt(fjxx);
                    if (Boolean.FALSE.equals(resultBt)) {
                        resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                        resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
                        return resultMap;
                    }
                    //判断附件内容是否全部符合base64编码方式，不符合返回异常信息；
                    boolean result = this.cheakBase64(fjxx);
                    if (Boolean.FALSE.equals(result)) {
                        resultMap.put("msg", ResponseCodeEnum.PARAM_WRONG.msg);
                        resultMap.put("code", ResponseCodeEnum.PARAM_WRONG.code);
                        return resultMap;
                    }

                    // 同步上传收件材料
                    fjclService.uploadAndSaveFjcl(xmDO.get(0).getGzlslid(), fjxxRequest.getFjxx());
                    resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
                    resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
                } else {
                    resultMap.put("msg", ResponseCodeEnum.DATA_LACK.msg);
                    resultMap.put("code", ResponseCodeEnum.DATA_LACK.code);
                }


            } else {
                resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
            }
        }
        if (MapUtils.isEmpty(resultMap)) {
            resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
            resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
        }
        return resultMap;
    }

    /***
     * 校验附件信息中的附件内容是否为base64编码格式
     * @param fjxx 附件信息
     * @Date 2021/6/22
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    private boolean cheakBase64(List<FjclDTO> fjxx) throws IOException {
        for (FjclDTO fjclDTO : fjxx) {
            List<FjclmxDTO> fjclmxDTOList = fjclDTO.getFjclmxDTOList();
            for (FjclmxDTO fjclmxDTO : fjclmxDTOList) {
                String fjnr = fjclmxDTO.getFjnr();//不包含文件头部编码，只有内容编码
                if (StringUtils.isBlank(fjnr)) { //如果fjnr为空,不验证；
                    continue;
                }
                //校验是非为base64编码
                if (Boolean.FALSE.equals(this.isBase64(fjnr))) {
                    return false;
                }
            }
        }
        return true;
    }

    /***
     * fjurl和fjnr字段，其中一个必填
     * @param fjxx 附件信息
     * @Date 2021/6/22
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    private boolean cheakBt(List<FjclDTO> fjxx) {
        for (FjclDTO fjclDTO : fjxx) {
            List<FjclmxDTO> fjclmxDTOList = fjclDTO.getFjclmxDTOList();
            for (FjclmxDTO fjclmxDTO : fjclmxDTOList) {
                String fjnr = fjclmxDTO.getFjnr();
                String fjurl = fjclmxDTO.getFjurl();
                //fjurl和fjnr字段，其中一个必填。
                if (StringUtils.isBlank(fjnr) && StringUtils.isBlank(fjurl)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 校验是否为base64编码
     *
     * @param str
     * @Date 2021/6/22
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    private boolean isBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, str);
    }

    /**
     * 判断是否需要匹配
     *
     * @param
     * @Date 2021/1/21
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void fctdSfpp(Object dydj) {
        LOGGER.info(dydj.toString());
        JSONArray data = (JSONArray) dydj;

        for (Object object : data) {
            JSONObject jsonObject = (JSONObject) object;
            String fcxmid = jsonObject.getString("zsxmid");
            Object gltdzxx = jsonObject.get("gltdzxx");
            if (null != gltdzxx) {
                //如果关联土地信息不为空，则以fcxmid为主，先查询匹配关系，若果有匹配关系，则不处理；没有匹配关系，进行匹配
                List<BdcFctdPpgxDO> fctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(fcxmid);
                if (CollectionUtils.isEmpty(fctdPpgxDOList)) {
                    JSONArray gltdzxxArray = (JSONArray) gltdzxx;
                    LOGGER.info("-=-=", gltdzxxArray.toJSONString());
                    for (Object tdzxx : gltdzxxArray) {
                        JSONObject tdzxxJson = (JSONObject) tdzxx;
                        String tdzh = tdzxxJson.getString("tdzh");
                        String tdxmid = tdzxxJson.getString("xmid");
                        LOGGER.info(tdzh);
                        LOGGER.info(tdxmid);
                        if (StringUtils.isNotBlank(tdzh) && StringUtils.isNotBlank(tdxmid)) {
                            //对房产和土地进行匹配
                            try {
                                bdcPpFeignService.pptd(fcxmid, tdxmid);
                                LOGGER.info("房产证和土地证进行匹配记录关系成功！");
                            } catch (Exception e) {
                                LOGGER.info("房产证和土地证进行匹配记录关系失败！");
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }

    }


    /**
     * 抵押注销回写相关字段操作
     *
     * @param
     * @return
     * @Date 2021/3/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void writebackDyzx(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                              List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf) {
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            try {
                Map<String, Object> paramMap = new HashMap<>();
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                // 抵押注销产权证号
                String zxcqzh = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList().get(0).getBdcSlXm().getYbdcqz();
                if (StringUtils.isNotBlank(zxcqzh)) {
                    paramMap.put("ZXCQZH", zxcqzh);
                }
                // 执行回写
                if (MapUtils.isNotEmpty(paramMap)) {
                    LOGGER.info("抵押注销大云回写字段异常参数,paramMap:{}", JSON.toJSONString(paramMap));
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                }
            } catch (Exception e) {
                LOGGER.error("大云回写字段异常,slbh:{}", responseDTO.getBdcXmDOList().get(0).getSlbh(), e);
            }

        } else {
            throw new AppException("没有生成登记项目");
        }
    }

    /**
     * @return cn.gtmap.realestate.exchange.core.dto.wwsq.ychsbyysfwbm.response.YchsByYsfwbmResponseDTO
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [queryParam]
     * @description 预测房屋查询
     */
    public YchsByYsfwbmResponseDTO ycfwxxCx(YcfwCxRequestDTO queryParam) {
        if (queryParam == null || StringToolUtils.isAllNullOrEmpty(queryParam.getZl(), queryParam.getRoomid())) {
            LOGGER.error("房屋坐落或roomid为空，无法查询预测房屋信息！");
            throw new AppException("房屋坐落或roomid为空，无法查询预测房屋信息！");
        }
        YchsByYsfwbmResponseDTO ychsByYsfwbmResponseDTOS = viewMapper.queryViewDvYgDO(queryParam);
        return ychsByYsfwbmResponseDTOS;
    }


    /**
     * @param createData @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 转换DTO
     */
    @Override

    public WwsqCjBdcXmRequestDTO initCfParamDTO(CreateData createData) {
        LOGGER.info("---查封解封登记入参:{}", JSONObject.toJSONString(createData));
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        // 受理角色控制 需要使用 固定的外网角色编码
        wwsqCjBdcXmRequestDTO.setSlRoleCode("wwsqsl");

        CfRequestDTO dataDTOS = createData.getData();

        if (Objects.isNull(dataDTOS)) {
            return wwsqCjBdcXmRequestDTO;
        }

        if (StringUtils.isEmpty(dataDTOS.getSqdjlx())) {
            throw new AppException("sqdjlx不能为空!");
        }
        // 增加是否进行规则验证
        wwsqCjBdcXmRequestDTO.setGzyz(true);

        List<CfCfxxRequestDTO> cfCfxxRequestDTOS = dataDTOS.getCfxx();
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        exchangeDozerMapper.map(dataDTOS, bdcSlJbxxDO, "court_cfjfdj_jbxx_v1");


        cfCfxxRequestDTOS.forEach(data -> {

            BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();

            /**
             * 不动产受理基本信息
             */
            bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);

            /**
             * 项目类模型集合
             */
            List<BdcSlXmDTO> xmDTOList = new ArrayList<>();
            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
            exchangeDozerMapper.map(data, bdcSlXmDTO, "court_cfjfdj_slxm_v1");
            exchangeDozerMapper.map(dataDTOS, bdcSlXmDTO, "court_cfjfdj_slxm_dsf_v1");
            exchangeDozerMapper.map(data, bdcSlXmDTO, "court_cfjfdj_cfjfxm_v1");
            xmDTOList.add(bdcSlXmDTO);

            bdcSlxxDTO.setBdcSlXmList(xmDTOList);

            wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);
        });
        LOGGER.info("---查封解封登记出参:{}", JSONObject.toJSONString(wwsqCjBdcXmRequestDTO));
        return wwsqCjBdcXmRequestDTO;
    }

    @Override
    public List<GetYgxxResponseData> getYdyxxChangzhou(List<GetYgxxRequestData> ygxxRequestDatas) {
        List<GetYgxxResponseData> ygxxResponseDataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ygxxRequestDatas)) {
            for (Object param : ygxxRequestDatas) {
                GetYgxxRequestData ygxxRequestData = new GetYgxxRequestData();

                if (param instanceof GetYgxxRequestData) {
                    ygxxRequestData = (GetYgxxRequestData) param;
                } else if (param instanceof JSONObject) {
                    ygxxRequestData = JSONObject.parseObject(((JSONObject) param).toJSONString(), GetYgxxRequestData.class);
                }
                GrdacxRequestBody requestBody = new GrdacxRequestBody();

                if (param instanceof GrdacxRequestBody) {
                    requestBody = (GrdacxRequestBody) param;
                } else {
                    dozerBeanMapper.map(ygxxRequestData, requestBody, "ygxxRequest_changzhou");
                }
                List<GetYgxxResponseData> temp = getYdyxxForChangzhou(requestBody);
                if (CollectionUtils.isNotEmpty(temp)) {
                    ygxxResponseDataList.addAll(temp);
                }
            }
        }
        return ygxxResponseDataList;
    }

    private List<GetYgxxResponseData> getYdyxxForChangzhou(GrdacxRequestBody requestBody) {
        LOGGER.info("常州预抵押接口查询入参", requestBody.toString());
        if (StringUtils.isNotBlank(requestBody.getQlrmc())
                || StringUtils.isNotBlank(requestBody.getQlrmcmh())
                || StringUtils.isNotBlank(requestBody.getSlbh())
                || StringUtils.isNotBlank(requestBody.getLikeBdcdyh())) {
            DvYdy dvYdy = new DvYdy();

            List<DvYdy> dvYdyList = bdcXmMapper.queryYdyChangzhou(requestBody);
            List<GetYgxxResponseData> ygxxResponseData = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(dvYdyList)) {
                for (DvYdy ydy : dvYdyList) {
                    //开始对照
                    List<GetYgxxResponseQlr> qlrList = new ArrayList<>();
                    List<GetYgxxResponseYgxx> ydyxxList = new ArrayList<>();
                    GetYgxxResponseQlr qlr = new GetYgxxResponseQlr();
                    GetYgxxResponseYgxx ydyxx = new GetYgxxResponseYgxx();
                    GetYgxxResponseData data = new GetYgxxResponseData();
                    dozerBeanMapper.map(ydy, qlr, "ydyxxQlr_changzhou");
                    dozerBeanMapper.map(ydy, ydyxx, "ydyxxYdy_changzhou");
                    dozerBeanMapper.map(ydy, data, "ydyxxData_changzhou");
                    qlrList.add(qlr);
                    ydyxx.setQlr(qlrList);
                    ydyxxList.add(ydyxx);
                    data.setYgxx(ydyxxList);
                    ygxxResponseData.add(data);

                }
            }
            return ygxxResponseData;
        }
        return new ArrayList<>();
    }

    /**
     * @param slbh
     * @return json 证明附表打印数据
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 批量抵押, 证明附表打印数据
     */
    @Override
    public JSONObject getZmfb(String slbh) throws DocumentException {
        if (cn.gtmap.realestate.common.util.StringUtil.isEmpty(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        //根据是slbh获得gzlslid
        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);

        Map<String, Object> map = new HashMap<>(1);
        map.put("gzlslid", gzlslid);
        bdcdyhListMap.add(map);
        paramMap.put("zmfbyl", bdcdyhListMap);

        //获得证明附表xml打印数据
        String xmlStr = bdcPrintFeignService.print(paramMap);
        //xml格式转换
        JSONObject json = this.xmlParse(xmlStr);
        return json;
    }


    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description 将实体转换
     */
    @Override
    public List<FwxxbybdcdyhResponseDTO> covertDate(List<BdcdyResponseDTO> bdcdyResponseDTOS, List<Map> djDcbResponseMap) {
        List<FwxxbybdcdyhResponseDTO> fwxxbybdcdyhResponseDTOS = new ArrayList<>();
        for (BdcdyResponseDTO bdcdyResponseDTO : bdcdyResponseDTOS) {
            FwxxbybdcdyhResponseDTO dto = new FwxxbybdcdyhResponseDTO();
            Map map = djDcbResponseMap.stream().filter(o -> bdcdyResponseDTO.getBdcdyh().equals(o.get("bdcdyh")))
                    .findFirst().orElse(null);
            dto.setZdmj(MapUtils.getString(map, "scmj"));
            dto.setTdsyqlx(bdcdyResponseDTO.getTdsyqlx());
            dto.setFjh(bdcdyResponseDTO.getFjh());
            if ("yc".equals(bdcdyResponseDTO.getHslx())) {
                dto.setZcs(bdcdyResponseDTO.getDycs());
            } else {
                dto.setZcs(String.valueOf(bdcdyResponseDTO.getFwcs()));
            }
            dto.setFwlx(bdcdyResponseDTO.getFwlx());
            dto.setZl(bdcdyResponseDTO.getZl());
            dto.setBdcdyh(bdcdyResponseDTO.getBdcdyh());
            dto.setTdyt(bdcdyResponseDTO.getTdyt());
            if (bdcdyResponseDTO.getScjzmj() != null && bdcdyResponseDTO.getScjzmj() > 0.0) {
                dto.setJzmj(String.valueOf(bdcdyResponseDTO.getScjzmj()));
            } else {
                dto.setJzmj(String.valueOf(bdcdyResponseDTO.getYcjzmj()));
            }

            dto.setZh(bdcdyResponseDTO.getDh());
            dto.setFwxzdm(bdcdyResponseDTO.getFwxz());
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            List<SZdTdsyqlxDO> sZdTdsyqlxDOS = bdcdyFeignService.getTdsyqlxZd(bdcdyResponseDTO.getTdsyqlx());
            dto.setTdsyqlxmc(sZdTdsyqlxDOS.get(0).getMc());
            if (StringUtils.isNotBlank(bdcdyResponseDTO.getFwxz())) {
                dto.setFwxzmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcdyResponseDTO.getFwxz()), zdMap.get("fwxz")));
            }
            if (StringUtils.isNotBlank(bdcdyResponseDTO.getFwjg())) {
                dto.setFwjgmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcdyResponseDTO.getFwjg()), zdMap.get(Constants.FWJG)));
            }
            if (StringUtils.isNotBlank(bdcdyResponseDTO.getFwlx())) {
                dto.setFwlxmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcdyResponseDTO.getFwlx()), zdMap.get("fwlx")));
            }
            if (StringUtils.isNotBlank(bdcdyResponseDTO.getTdyt())) {
                dto.setTdytmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcdyResponseDTO.getTdyt()), zdMap.get(Constants.TDYT)));
            }
            if (StringUtils.isNotBlank(bdcdyResponseDTO.getGhyt())) {
                dto.setGhytmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcdyResponseDTO.getGhyt()), zdMap.get(Constants.FWYT)));
            }
            if (StringUtils.isNotBlank(bdcdyResponseDTO.getBdclx())) {
                dto.setBdclxmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcdyResponseDTO.getBdclx()), zdMap.get(Constants.BDCLX)));
            }
            dto.setFwjgdm(bdcdyResponseDTO.getFwjg());
            dto.setQxdm(bdcdyResponseDTO.getBdcdyh().substring(0, 6));
            dto.setQsrq(DateUtil.formateTime(bdcdyResponseDTO.getQsrq()));
            dto.setJsrq(DateUtil.formateTime(bdcdyResponseDTO.getZzrq()));
            dto.setJgrq(DateUtil.formateTime(bdcdyResponseDTO.getJgrq()));
            dto.setGhyt(bdcdyResponseDTO.getGhyt());
            dto.setSzc(bdcdyResponseDTO.getWlcs().toString());
            dto.setBdclx(bdcdyResponseDTO.getBdclx());
            fwxxbybdcdyhResponseDTOS.add(dto);
        }
        return fwxxbybdcdyhResponseDTOS;
    }

    /**
     * @param xmlStr
     * @return json
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 证明附表xml打印数据转换为JSON格式
     */
    private JSONObject xmlParse(String xmlStr) throws DocumentException {
        Map<String, Object> zmfbMap = new HashMap<>();
        List<Map<String, String>> cqxx = new ArrayList<>();
        try {
            Document dc = DocumentHelper.parseText(xmlStr);
            //获取根节点；
            Element rootElement = dc.getRootElement();
            Iterator iterator = rootElement.elementIterator();
            while (iterator.hasNext()) {
                Element fir = (Element) iterator.next();
                Iterator iterator1 = fir.elementIterator();
                while (iterator1.hasNext()) {
                    Element sec = (Element) iterator1.next();
                    if (sec.getName().equals("data")) {
                        String key = StringUtils.lowerCase(sec.attributeValue("name"));
                        zmfbMap.put(key, sec.getStringValue());
                    } else if (sec.getName().equals("row")) {
                        Map<String, String> bdcCqxx = new HashMap<>();
                        Iterator iterator2 = sec.elementIterator();
                        while (iterator2.hasNext()) {
                            Element third = (Element) iterator2.next();
                            String str = StringUtils.lowerCase(third.attributeValue("name"));
                            bdcCqxx.put(str, third.getStringValue());
                        }
                        cqxx.add(bdcCqxx);
                    }
                }
            }
            zmfbMap.put("cqxx", cqxx);
            JSONObject json = new JSONObject(zmfbMap);
            return json;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 构（建）筑物信息查询（不动产单元信息）
     *
     * @param gzwxxcxQO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<GzwxxResponseDTO> queryGzwxx(Pageable pageable, GzwxxcxQO gzwxxcxQO) {
        if (!CheckParameter.checkAnyParameter(gzwxxcxQO)) {
            LOGGER.info("参数为空！请检查参数！gzwxxcxQO:{}", gzwxxcxQO.toString());
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        return acceptBdcdyFeignService.listWwsqGzwxxDTOByPage(pageable, JSON.toJSONString(gzwxxcxQO));

    }

    /**
     * 构（建）筑物所有权查询
     *
     * @param gzwxxcxQO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<GzwsqyDTO> queryGzwsyq(Pageable pageable, GzwsyqcxQO gzwxxcxQO) {
        if (!CheckParameter.checkAnyParameter(gzwxxcxQO)) {
            LOGGER.info("参数为空！请检查参数！");
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        LOGGER.info(gzwxxcxQO.toString());

        Page<GzwsqyDTO> gzwxxPage = repository.selectPaging("querGzwsyqByPageOrder", gzwxxcxQO, pageable);
        if (CollectionUtils.isNotEmpty(gzwxxPage.getContent())) {
            for (GzwsqyDTO gzwsqyDTO : gzwxxPage.getContent()) {
                //根据xmid权利人表，组织权利人相关数据
                gzwsqyDTO.setQlrlist(qlrsjConvertQlrBean(gzwsqyDTO.getXmid(), CommonConstantUtils.QLRLB_QLR, "CommonQlrxx"));
                //根据bdcdyh查限制状态
                if (StringUtils.isNotBlank(gzwsqyDTO.getBdcdyh())) {
                    //处理抵押
                    String bdcdyh = gzwsqyDTO.getBdcdyh();
                    List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());

                    if (CollectionUtils.isNotEmpty(listDyaq)) {
                        gzwsqyDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        gzwsqyDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理查封
                    List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    if (CollectionUtils.isNotEmpty(listCf)) {
                        gzwsqyDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        gzwsqyDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理异议
                    List<BdcQl> listYy = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YY.toString());
                    if (CollectionUtils.isNotEmpty(listYy)) {
                        gzwsqyDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        gzwsqyDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理锁定  单元锁定 与证书锁定
                    List<BdcQl> listSd = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    // 证书锁定
                    List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(gzwsqyDTO.getXmid(), CommonConstantUtils.SDZT_SD);
                    if (CollectionUtils.isNotEmpty(listSd) || CollectionUtils.isNotEmpty(zssdDOList)) {
                        gzwsqyDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        gzwsqyDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理预告
                    List<BdcQl> listYg = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YG_DM.toString());
                    if (CollectionUtils.isNotEmpty(listYg)) {
                        gzwsqyDTO.setSfyg(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        gzwsqyDTO.setSfyg(String.valueOf(Constants.WWSQ_SF_F));
                    }
                }
            }
        }


        return gzwxxPage;
    }

    /**
     * 土地所有权查询
     *
     * @param pageable
     * @param gzwxxcxQO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<TdsyqDTO> queryTdsyq(Pageable pageable, GzwsyqcxQO gzwxxcxQO) {
        if (!CheckParameter.checkAnyParameter(gzwxxcxQO)) {
            LOGGER.info("参数为空！请检查参数！");
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        LOGGER.info(gzwxxcxQO.toString());
        Page<TdsyqDTO> tdsyqDTOPage = repository.selectPaging("querTdsyqByPageOrder", gzwxxcxQO, pageable);
        if (CollectionUtils.isNotEmpty(tdsyqDTOPage.getContent())) {
            for (TdsyqDTO tdsyqDTO : tdsyqDTOPage.getContent()) {
                //根据xmid权利人表，组织权利人相关数据
                tdsyqDTO.setQlrlist(qlrsjConvertQlrBean(tdsyqDTO.getXmid(), CommonConstantUtils.QLRLB_QLR, "CommonQlrxx"));
                //根据bdcdyh查限制状态
                if (StringUtils.isNotBlank(tdsyqDTO.getBdcdyh())) {
                    //处理抵押
                    String bdcdyh = tdsyqDTO.getBdcdyh();
                    List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());

                    if (CollectionUtils.isNotEmpty(listDyaq)) {
                        tdsyqDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        tdsyqDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理查封
                    List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    if (CollectionUtils.isNotEmpty(listCf)) {
                        tdsyqDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        tdsyqDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理异议
                    List<BdcQl> listYy = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YY.toString());
                    if (CollectionUtils.isNotEmpty(listYy)) {
                        tdsyqDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        tdsyqDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理锁定  单元锁定 与证书锁定
                    List<BdcQl> listSd = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    // 证书锁定
                    List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(tdsyqDTO.getXmid(), CommonConstantUtils.SDZT_SD);
                    if (CollectionUtils.isNotEmpty(listSd) || CollectionUtils.isNotEmpty(zssdDOList)) {
                        tdsyqDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        tdsyqDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理预告
                    List<BdcQl> listYg = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YG_DM.toString());
                    if (CollectionUtils.isNotEmpty(listYg)) {
                        tdsyqDTO.setSfyg(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        tdsyqDTO.setSfyg(String.valueOf(Constants.WWSQ_SF_F));
                    }
                }

            }

        }
        return tdsyqDTOPage;

    }

    /**
     * 地役证明查询
     *
     * @param pageable
     * @param dyicxQO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<DyizmDTO> queryDyzm(Pageable pageable, DyicxQO dyicxQO) {
//        LOGGER.info(dyicxQO.toString());
        if (!CheckParameter.checkAnyParameter(dyicxQO)) {
            LOGGER.info("参数为空！请检查参数！");
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        Page<DyizmDTO> dyizmDTOPage = repository.selectPaging("querDyizmByPageOrder", dyicxQO, pageable);
        if (CollectionUtils.isNotEmpty(dyizmDTOPage.getContent())) {
            for (DyizmDTO dyizmDTO : dyizmDTOPage.getContent()) {
                //根据xmid权利人表，组织权利人相关数据
                dyizmDTO.setQlrlist(qlrsjConvertQlrBean(dyizmDTO.getXmid(), CommonConstantUtils.QLRLB_QLR, "CommonQlrxx"));
            }

        }
        return dyizmDTOPage;
    }

    /**
     * 异议证明查询
     *
     * @param pageable
     * @param yycxQOQ
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<YyzmDTO> queryYyzm(Pageable pageable, YycxQO yycxQOQ) {
        if (!CheckParameter.checkAnyParameter(yycxQOQ)) {
            LOGGER.info("参数为空！请检查参数！");
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        Page<YyzmDTO> yyzmDTOPage = repository.selectPaging("querYyzmByPageOrder", yycxQOQ, pageable);
        if (CollectionUtils.isNotEmpty(yyzmDTOPage.getContent())) {
            for (YyzmDTO yyzmDTO : yyzmDTOPage.getContent()) {
                //根据xmid权利人表，组织权利人相关数据
                yyzmDTO.setQlrlist(qlrsjConvertQlrBean(yyzmDTO.getXmid(), CommonConstantUtils.QLRLB_QLR, "CommonQlrxx"));
                if (StringUtils.isNotBlank(yyzmDTO.getBdcdyh())) {
                    //处理抵押
                    String bdcdyh = yyzmDTO.getBdcdyh();
                    List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());

                    if (CollectionUtils.isNotEmpty(listDyaq)) {
                        yyzmDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        yyzmDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理查封
                    List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    if (CollectionUtils.isNotEmpty(listCf)) {
                        yyzmDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        yyzmDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理异议
                    List<BdcQl> listYy = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YY.toString());
                    if (CollectionUtils.isNotEmpty(listYy)) {
                        yyzmDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        yyzmDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理锁定  单元锁定 与证书锁定
                    List<BdcQl> listSd = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    // 证书锁定
                    List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(yyzmDTO.getXmid(), CommonConstantUtils.SDZT_SD);
                    if (CollectionUtils.isNotEmpty(listSd) || CollectionUtils.isNotEmpty(zssdDOList)) {
                        yyzmDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        yyzmDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //处理预告
                    List<BdcQl> listYg = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YG_DM.toString());
                    if (CollectionUtils.isNotEmpty(listYg)) {
                        yyzmDTO.setSfyg(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        yyzmDTO.setSfyg(String.valueOf(Constants.WWSQ_SF_F));
                    }
                }
            }

        }
        return yyzmDTOPage;
    }

    /**
     * @param
     * @param paramMap qlrJsonArr
     * @return cn.gtmap.realestate.common.core.dto.BdcCommonResponse
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/7/13 21:35
     * @description 根据权利人查询产权信息
     **/
    @Override
    public BdcCommonResponse queryCqxxByQlr(JSONObject paramMap) {
        List<QlrRequestDTO> qlrRequestDTOS = JSONObject.parseArray(paramMap.getString("qlrJsonArr"), QlrRequestDTO.class);
        if (CollectionUtils.isEmpty(qlrRequestDTOS)) {
            return BdcCommonResponse.fail("缺少必填项");
        }
        List<String> qlrmcList = new ArrayList<>(qlrRequestDTOS.size());
        List<String> qlrzjhList = new ArrayList<>(qlrRequestDTOS.size());
        for (QlrRequestDTO requestDTO : qlrRequestDTOS) {
            qlrmcList.add(requestDTO.getQlrmc());
            qlrzjhList.add(requestDTO.getQlrzjh());
        }
        JtBdcxxTycxRequestDTO jtBdcxxTycxRequestDTO = new JtBdcxxTycxRequestDTO();
        jtBdcxxTycxRequestDTO.setQlrmcList(qlrmcList);
        jtBdcxxTycxRequestDTO.setQlrzjhList(qlrzjhList);
        jtBdcxxTycxRequestDTO.setQxdmList(cxCqQxdmjh);
        jtBdcxxTycxRequestDTO.setQllxList(cxCqQllxjh);
        List<JtBdcxxTycxResponseDTO> jtBdcxxTycxResponseDTOList = queryJtBdcXx(jtBdcxxTycxRequestDTO);
        if (CollectionUtils.isEmpty(jtBdcxxTycxResponseDTOList)) {
            return BdcCommonResponse.ok();
        }
        List<CqxxResponseDTO> results = new ArrayList<>(jtBdcxxTycxResponseDTOList.size());
        for (JtBdcxxTycxResponseDTO jtBdcxxTycxResponseDTO : jtBdcxxTycxResponseDTOList) {
            CqxxResponseDTO cqxxResponseDTO = exchangeDozerMapper.map(jtBdcxxTycxResponseDTO, CqxxResponseDTO.class, "queryCqxxByQlr");
            results.add(cqxxResponseDTO);
        }
        return BdcCommonResponse.ok(results);
    }

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description 查询购房信息
     */
    @Override
    public BdcCommonResponse queryGfxxByQlr(QlrGfxxRequestDTO gfxxRequestDTO) {
        if (null == gfxxRequestDTO) {
            return BdcCommonResponse.fail("缺少权利人信息");
        }
        String[] qlrmc = gfxxRequestDTO.getQlrmc().split(CommonConstantUtils.ZF_YW_DH);
        String[] qlrzjh = gfxxRequestDTO.getQlrzjh().split(CommonConstantUtils.ZF_YW_DH);
        if (qlrmc.length > 1 && qlrzjh.length > 1 && qlrmc.length != qlrzjh.length) {
            return BdcCommonResponse.fail("权利人与证件号需对应");
        }
        LOGGER.info("购房信息查询入参:{}", gfxxRequestDTO);
        List<QlrGfxxResponseDTO> gfxxResponseDTOList = repository.selectList("getGfxxByQlrxx", gfxxRequestDTO);
        return BdcCommonResponse.ok(gfxxResponseDTOList);
    }

    /**
     * @return
     * @author <a href = "mailto:wutao@gtmap.cn">wutao</a>
     * @description 删除归档信息
     */
    @Override
    public BdcCommonResponse deleteGdxx(BdcGdxxRequestDTO bdcGdxxRequestDTO) {
        if (null == bdcGdxxRequestDTO || StringUtils.isBlank(bdcGdxxRequestDTO.getXmid())) {
            return BdcCommonResponse.fail("缺少项目id信息");
        }
        LOGGER.info("删除归档信息入参:{}", bdcGdxxRequestDTO);
        int count = 0;
        try {
            count = bdcGdxxFeignService.deleteBdcGdxxByXmid(bdcGdxxRequestDTO.getXmid());
        }catch (Exception e){
            return BdcCommonResponse.fail("删除归档信息失败，失败原因：" + e);
        }
        LOGGER.info("删除归档信息成功一共删除{}条数据", count);
        return BdcCommonResponse.ok(count);
    }

    /**
     * @return
     * @author <a href = "mailto:wutao@gtmap.cn">wutao</a>
     * @description 获取电子证照压缩包
     */
    @Override
    public BdcCommonResponse getDzzzzip(BdcDzzzzipRequestDTO bdcDzzzzipRequestDTO) {
        if (null == bdcDzzzzipRequestDTO || StringUtils.isBlank(bdcDzzzzipRequestDTO.getSlbh())) {
            return BdcCommonResponse.fail("缺少受理编号信息");
        }
        LOGGER.info("获取电子证照压缩包入参:{}", bdcDzzzzipRequestDTO);
        BdcDzzzzipResponseDTO bdcDzzzzipResponseDTO = new BdcDzzzzipResponseDTO();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(bdcDzzzzipRequestDTO.getSlbh());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            String gzlslid = bdcXmDOList.get(0).getGzlslid();
            String clmc = "电子证照压缩包" + gzlslid + ".zip";
            List<BdcSlSjclDO> bdcSlSjclDOS = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid,clmc);
            if(CollectionUtils.isNotEmpty(bdcSlSjclDOS)){
                if(StringUtils.isNotBlank(bdcSlSjclDOS.get(0).getWjzxid())){
                    MultipartDto multipartDto = storageClient.download(bdcSlSjclDOS.get(0).getWjzxid());
                    if(null != multipartDto){
                        bdcDzzzzipResponseDTO.setDzzzzip(multipartDto.getData());
                    }
                }
            }
        }
        return BdcCommonResponse.ok(bdcDzzzzipResponseDTO);
    }

    /**
     * @param paramMap
     * @return cn.gtmap.realestate.common.core.dto.BdcCommonResponse
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/3 17:27
     * @description 查询家庭不动产登记信息
     **/
    @Override
    public BdcCommonResponse queryJtBdcdjXx(JSONObject paramMap) {
        List<JtcyxxRequestDTO> jtcyList = JSONObject.parseArray(paramMap.getString("jtcyxx"), JtcyxxRequestDTO.class);
        if (CollectionUtils.isEmpty(jtcyList)) {
            return BdcCommonResponse.fail("缺少家庭成员信息参数");
        }
        List<String> qlrmcList = new ArrayList<>(jtcyList.size());
        List<String> qlrzjhList = new ArrayList<>(jtcyList.size());
        List<String> zjzlList = new ArrayList<>(jtcyList.size());
        for (JtcyxxRequestDTO jtcyxxRequestDTO : jtcyList) {
            qlrmcList.add(jtcyxxRequestDTO.getXm());
            qlrzjhList.add(jtcyxxRequestDTO.getZjhm());
            zjzlList.add(jtcyxxRequestDTO.getZjlx());
        }
        JtBdcxxTycxRequestDTO jtBdcxxTycxRequestDTO = new JtBdcxxTycxRequestDTO();
        jtBdcxxTycxRequestDTO.setQlrmcList(qlrmcList);
        jtBdcxxTycxRequestDTO.setQlrzjhList(qlrzjhList);
        jtBdcxxTycxRequestDTO.setQlzjzlList(zjzlList);
        jtBdcxxTycxRequestDTO.setQllxList(cxJtBdcQllxjh);
        List<JtBdcxxTycxResponseDTO> jtBdcxxTycxResponseDTOList = queryJtBdcXx(jtBdcxxTycxRequestDTO);
        JtBdcxxRespVO jtBdcxxRespVO = new JtBdcxxRespVO();
        if (CollectionUtils.isEmpty(jtBdcxxTycxResponseDTOList)) {
            return BdcCommonResponse.ok(jtBdcxxRespVO);
        }
        List<JtBdcxxRespDTO> results = new ArrayList<>(jtBdcxxTycxResponseDTOList.size());
        for (JtBdcxxTycxResponseDTO jtBdcxxTycxResponseDTO : jtBdcxxTycxResponseDTOList) {
            JtBdcxxRespDTO jtBdcxxRespDTO = exchangeDozerMapper.map(jtBdcxxTycxResponseDTO, JtBdcxxRespDTO.class, "queryJtBdcdjXx");
            List<JtQlrRespDTO> qlrlist = new ArrayList<>();
            String qlrs = jtBdcxxTycxResponseDTO.getQlr();
            String qlrzjhs = jtBdcxxTycxResponseDTO.getQlrzjh();
            if (StringUtils.isNotBlank(qlrs) && StringUtils.isNotBlank(qlrzjhs)) {
                String[] qlrArr = qlrs.split(CommonConstantUtils.ZF_YW_DH);
                String[] qlrzjhArr = qlrzjhs.split(CommonConstantUtils.ZF_YW_DH);
                if (qlrArr.length == qlrzjhArr.length) {
                    for (int i = 0; i < qlrArr.length; i++) {
                        JtQlrRespDTO jtQlrRespDTO = new JtQlrRespDTO();
                        jtQlrRespDTO.setFwsyqr(qlrArr[i]);
                        jtQlrRespDTO.setQlrzjh(qlrzjhArr[i]);
                        qlrlist.add(jtQlrRespDTO);
                    }
                    jtBdcxxRespDTO.setQlrlist(qlrlist);
                }
            }
            // 当规划用途为其他时，新增用途字段返回
            if ("其他".equals(jtBdcxxRespDTO.getGhyt())) {
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(jtBdcxxTycxResponseDTO.getXmid());
                if (bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    jtBdcxxRespDTO.setGhytmc(bdcFdcqDO.getYtmc());
                }
                if (bdcQl instanceof BdcYgDO) {
                    BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                    jtBdcxxRespDTO.setGhytmc(bdcYgDO.getYtmc());
                }
            } else {
                jtBdcxxRespDTO.setGhytmc(jtBdcxxRespDTO.getGhyt());
            }
            results.add(jtBdcxxRespDTO);
        }
        jtBdcxxRespVO.setHouseList(results);
        return BdcCommonResponse.ok(jtBdcxxRespVO);
    }

    /**
     * 权籍不动产单元查询
     *
     * @param pageable
     * @param bdcdyxxCxQO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<WwsqBdcdyxxDTO> getWwsqBdcdyxxFy(Pageable pageable, BdcdyxxCxQO bdcdyxxCxQO) {
        if (!CheckParameter.checkAnyParameter(bdcdyxxCxQO)) {
            LOGGER.info("参数为空！请检查参数！");
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        return acceptBdcdyFeignService.listWwsqBdcdyxxDTOByPage(pageable, JSON.toJSONString(bdcdyxxCxQO));
    }

    /**
     * 抵押登记查询
     *
     * @param pageable
     * @param dydjxxcxCxQO
     * @return
     * @Date 2022/9/15
     * @author wangyinghao
     */
    @Override
    public Page<WwsqDydjfxxDTO> getDydjxxcx(Pageable pageable, DydjxxcxCxQO dydjxxcxCxQO) {
        if (!CheckParameter.checkAnyParameter(dydjxxcxCxQO)) {
            LOGGER.info("参数为空！请检查参数！");
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        if (CollectionUtils.isNotEmpty(scdyGzldyidList)) {
            scdyGzldyidList.remove("");
            dydjxxcxCxQO.setGzldyids(scdyGzldyidList);
        }
        Page<WwsqDydjfxxDTO> querySczmdByPageOrder = repository
                .selectPaging("querDydjByPageOrder", dydjxxcxCxQO, pageable);

        if (CollectionUtils.isNotEmpty(querySczmdByPageOrder.getContent())) {
            for (WwsqDydjfxxDTO wwsqDydjfxxDTO : querySczmdByPageOrder.getContent()) {
                //处理权利人
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(wwsqDydjfxxDTO.getXmid());
                List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    String qlrmc = qlrDOList.stream()
                            .filter(bdcQlrDO -> CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb()))
                            .map(BdcQlrDO::getQlrmc)
                            .collect(Collectors.joining(","));
                    String qlrzjh = qlrDOList.stream()
                            .filter(bdcQlrDO -> CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb()))
                            .map(BdcQlrDO::getZjh)
                            .collect(Collectors.joining(","));
                    String ywrmc = qlrDOList.stream()
                            .filter(bdcQlrDO -> CommonConstantUtils.QLRLB_YWR.equals(bdcQlrDO.getQlrlb()))
                            .map(BdcQlrDO::getQlrmc)
                            .collect(Collectors.joining(","));
                    String ywrzjh = qlrDOList.stream()
                            .filter(bdcQlrDO -> CommonConstantUtils.QLRLB_YWR.equals(bdcQlrDO.getQlrlb()))
                            .map(BdcQlrDO::getZjh)
                            .collect(Collectors.joining(","));

                    //设置抵押人,抵押权人,债务人
                    if (StringUtils.isNotBlank(qlrzjh) && StringUtils.isNotBlank(qlrmc)) {
                        wwsqDydjfxxDTO.setDyrmc(qlrmc);
                        wwsqDydjfxxDTO.setDyrzjh(qlrzjh);
                        wwsqDydjfxxDTO.setDyqrmc(qlrmc);
                    }
                    if (StringUtils.isNotBlank(ywrzjh) && StringUtils.isNotBlank(ywrmc)) {
                        wwsqDydjfxxDTO.setZwrmc(ywrmc);
                        wwsqDydjfxxDTO.setZwrzjh(ywrzjh);
                    }

                    //设置登记费
                    List<BdcSlSfxxDO> bdcSlSfxxDOS = bdcSlSfxxFeignService
                            .listBdcSlSfxxByGzlslidHjfk(wwsqDydjfxxDTO.getGzlslid());
                    if (CollectionUtils.isNotEmpty(bdcSlSfxxDOS)) {
                        double sum = bdcSlSfxxDOS.stream().mapToDouble(BdcSlSfxxDO::getHj).sum();
                        wwsqDydjfxxDTO.setDjf(sum);
                        wwsqDydjfxxDTO.setJfzt("1");
                        //循环完，有一个未缴费，则返回未缴费
                        for (BdcSlSfxxDO sfxxDo : bdcSlSfxxDOS) {
                            if (!CommonConstantUtils.SFZT_YJF.equals(sfxxDo.getSfzt())) {
                                wwsqDydjfxxDTO.setJfzt("0");
                            }
                        }
                    }
                    //设置发票号码
                    List<BdcSlJyxxDO> bdcSlJyxxDOS = bdcSlJyxxFeignService
                            .listBdcSlJyxxByXmid(wwsqDydjfxxDTO.getXmid());
                    if (CollectionUtils.isNotEmpty(bdcSlJyxxDOS)) {
                        wwsqDydjfxxDTO.setFphm(bdcSlJyxxDOS.get(0).getFphm());
                    }

                    if (StringUtils.isNotBlank(wwsqDydjfxxDTO.getSlrmc())) {
                        wwsqDydjfxxDTO.setCsrmc(wwsqDydjfxxDTO.getSlrmc());
                    }

                }
            }
        }

        return querySczmdByPageOrder;
    }


    /**
     * 登记系统政务办住所核验接口
     *
     * @param cqzh
     * @param qlrmc
     * @return
     */
    @Override
    public ZwbzshyDTO getZwbzshy(String cqzh, String qlrmc) {
        ZwbzshyDTO zwbzshyDTO = new ZwbzshyDTO();
        if (StringUtils.isBlank(cqzh) || StringUtils.isBlank(qlrmc)) {
            zwbzshyDTO.setCheckjg("");
            zwbzshyDTO.setMsg("参数为空");
            return zwbzshyDTO;
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzh(cqzh);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            zwbzshyDTO.setCheckjg("产权证号不存在");
            zwbzshyDTO.setMsg("查询成功");
            return zwbzshyDTO;
        }
        BdcZsDO bdcZsDO = bdcZsDOS.get(0);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.queryXmByZsBdcqzh(bdcZsDO.getBdcqzh());
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            zwbzshyDTO.setCheckjg("产权证项目不存在");
            zwbzshyDTO.setMsg("查询成功");
            return zwbzshyDTO;
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        String tdyt = commonService.getBdcZdMcFromDm("tdyt", bdcXmDO.getZdzhyt());
        List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listBdcQlrByXmidList(
                bdcXmDOS.stream().map(BdcXmDO::getXmid).collect(Collectors.toList()),
                CommonConstantUtils.QLRLB_QLR
        );
        List<String> qlrmcList = bdcQlrDOS
                .stream()
                .map(BdcQlrDO::getQlrmc)
                .collect(Collectors.toList());
        if (!qlrmcList.contains(qlrmc)) {
            zwbzshyDTO.setCheckjg("产权证没有该权利人");
            zwbzshyDTO.setMsg("查询成功");
            return zwbzshyDTO;
        }
        zwbzshyDTO.setCheckjg("核验通过");
        zwbzshyDTO.setMsg("查询成功");
        zwbzshyDTO.setZl(bdcXmDO.getZl());
        zwbzshyDTO.setQlrmc(String.join(",", qlrmcList));
        //查询权利
        QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
        queryQlRequestDTO.setBdcdyh(bdcXmDO.getBdcdyh());
        queryQlRequestDTO.setQszt("1");
        queryQlRequestDTO.setWithXm(false);
        queryQlRequestDTO.setWithQlr(false);
        List<FdcqQlWithXmQlrDTO> fdcqQlWithXmQlrDTOS = commonService.listFdcqByBdcdyh(queryQlRequestDTO);
        String fwyt = "";
        if (CollectionUtils.isNotEmpty(fdcqQlWithXmQlrDTOS) && Objects.nonNull(fdcqQlWithXmQlrDTOS.get(0).getBdcql().getGhyt())) {
            FdcqQlWithXmQlrDTO fdcqQlWithXmQlrDTO = fdcqQlWithXmQlrDTOS.get(0);
            BdcFdcqDO bdcql = fdcqQlWithXmQlrDTO.getBdcql();
            fwyt = commonService.getBdcZdMcFromDm("fwyt", bdcql.getGhyt().toString());
        } else {
            fwyt = commonService.getBdcZdMcFromDm("fwyt", bdcXmDO.getDzwyt().toString());
        }
        zwbzshyDTO.setFwyt(tdyt + "," + fwyt);
        return zwbzshyDTO;
    }

    /**
     * @description 连云港查询新建商品房网签合同数据
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/26 15:53
     * @param paramMap
     * @return List<SpfHtbaxxResponseDTO>
     */
    @Override
    public List<SpfHtbaxxResponseDTO> querySpfHtbaxx(JSONObject paramMap) {
        SpfHtbaxxQO spfHtbaxxQO = JSONObject.parseObject(paramMap.getString("data"), SpfHtbaxxQO.class);
        if (!CheckParameter.checkAnyParameter(spfHtbaxxQO)) {
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        LOGGER.info("querySpfHtbaxx查询参数：{}", spfHtbaxxQO);
        List<HtbaSpfDO> htbaxxDTOList = repository.selectList("querySpfHtbaxx", spfHtbaxxQO);
        LOGGER.info("querySpfHtbaxx查询HtbaSpf结果：{}", htbaxxDTOList);
        if (CollectionUtils.isEmpty(htbaxxDTOList)) {
            return null;
        }
        List<SpfHtbaxxResponseDTO> spfHtbaxxResponseDTOList = new ArrayList<>(htbaxxDTOList.size());
        for (HtbaSpfDO htbaSpfDO : htbaxxDTOList) {
            HtbaxxDTO htbaxxDTO = new HtbaxxDTO();
            htbaxxDTO.setHtbaSpfDO(htbaSpfDO);
            String baid = htbaSpfDO.getBaid();
            if (StringUtils.isNotBlank(baid)) {
                Map<String, Object> queryMap = new HashMap(2);
                queryMap.put("baid", baid);
                List<HtbaQlrDO> htbaQlrDOList = repository.selectList("querySpfHtbaQlrxx", queryMap);
                htbaxxDTO.setHtbaQlrDOList(htbaQlrDOList);
            }
            SpfHtbaxxResponseDTO spfHtbaxxResponseDTO = exchangeDozerMapper.map(htbaxxDTO, SpfHtbaxxResponseDTO.class, "querySpfHtbaxx");
            spfHtbaxxResponseDTOList.add(spfHtbaxxResponseDTO);
        }
        LOGGER.info("querySpfHtbaxx响应数据：{}", htbaxxDTOList);
        return spfHtbaxxResponseDTOList;
    }

    /**
     * @description 根据房产证号查询匹配关系信息、分户图和宗地图
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/16 15:05
     * @param fczh
     * @return List<PpgxResponseDTO>
     */
    @Override
    public List<PpgxResponseDTO> queryPpgx(String fczh) {
        if (StringUtils.isBlank(fczh)) {
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        List<PpgxResponseDTO> ppgxResponseDTOList = new ArrayList<>(4);

        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.queryXmByZsBdcqzh(fczh);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcQjgldmQO qjgldmQO = new BdcQjgldmQO();
            qjgldmQO.setXmid(bdcXmDOList.get(0).getXmid());
            String qjgldm = bdcXmfbFeignService.queryQjgldm(qjgldmQO);
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                PpgxResponseDTO ppgxResponseDTO = new PpgxResponseDTO();
                ppgxResponseDTO.setFczh(fczh);
                String fwzl = bdcXmDO.getZl();
                String bdcdywybh = bdcXmDO.getBdcdywybh();
                ppgxResponseDTO.setFwzl(fwzl);
                ppgxResponseDTO.setFwid(bdcdywybh);
                // 匹配的土地证号
                String fcxmid = bdcXmDO.getXmid();
                List<BdcFctdPpgxDO> bdcFctdPpgxDOS = bdcPpFeignService.queryBdcFctdPpgx(fcxmid);
                if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOS)) {
                    String tdcqxmid = bdcFctdPpgxDOS.get(0).getTdcqxmid();
                    BdcXmDO tdBdcXmDO = bdcXmFeignService.queryBdcXmByXmid(tdcqxmid);
                    if (Objects.nonNull(tdBdcXmDO)) {
                        ppgxResponseDTO.setTdzh(tdBdcXmDO.getBdcqzh());
                    }
                }

                List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOS = bdcPpFeignService.queryBdcXnbdcdyhGxByXmid(fcxmid);
                if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOS)) {
                    // 匹配的不动产单元号
                    String bdcdyh = bdcXnbdcdyhGxDOS.get(0).getBdcdyh();
                    if (StringUtils.isNotBlank(bdcdyh)) {
                        ppgxResponseDTO.setBdcdyh(bdcdyh);
                        // 不动产单元号坐落
                        FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh, qjgldm);
                        if (Objects.nonNull(fwHsDO)) {
                            String bdcdyhzl = fwHsDO.getZl();
                            ppgxResponseDTO.setBdcdyhzl(bdcdyhzl);
                        }
                        // 户室图
                        String hst = fwHstFeignService.queryFwHstBase64ByBdcdyh(bdcdyh, qjgldm);
                        ppgxResponseDTO.setHst(hst);
                        // 宗地图
                        ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcdyh, qjgldm);
                        if (Objects.nonNull(zdtResponseDTO)) {
                            String zdt = zdtResponseDTO.getBase64();
                            ppgxResponseDTO.setZdt(zdt);
                        }
                    }
                }
                ppgxResponseDTOList.add(ppgxResponseDTO);
            }
        }
        return ppgxResponseDTOList;
    }

    /**
     * @param qlrmc
     * @param zjh
     * @return
     */
    @Override
    public JSONArray queryFwqsxx(String qlrmc, String zjh,Integer pageNumber,Integer pageSize) {
        if (StringUtils.isBlank(qlrmc) || StringUtils.isBlank(zjh)) {
            LOGGER.error("入参不能同时为空！");
            throw new MissingArgumentException("入参不能同时为空");
        }
        JSONArray fwxx = new JSONArray();
        List<String> zjhList = Arrays.asList(zjh, CardNumberTransformation.zjhTransformation(zjh));
        // 先根据查询参数查BDC_QLR,BDC_XM获取不动产单元号
        List<BdcXmDO> bdcXmDOS = bdcXmMapper.getBdcXmByQlrAndZjh(
                qlrmc,
                zjhList
        );
        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
            List<String> bdcdyhList = bdcXmDOS
                    .stream()
                    .map(BdcXmDO::getBdcdyh)
                    .collect(Collectors.toList());

            if(CollectionUtils.isEmpty(bdcdyhList)){
                return fwxx;
            }
            initFwxx(bdcdyhList,fwxx,pageNumber,pageSize);
        }

        return fwxx;
    }


    /**
     * @description 商品房备案信息查询（宣城）
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @date 2022/12/16 11:43
     */
    @Override
    public SpfBaxxResponseData xcBdcspfbacx(ParamBody paramBody) {
        SpfBaxxResponseData responseData = new SpfBaxxResponseData();
        FcjyBaxxDTO fcjyBaxxDTO = new FcjyBaxxDTO();
        //先查接口1
        Object baxxResponse = exchangeBeanRequestService.request("xcFcjySpfQlr", JSONObject.parseObject(JSONObject.toJSONString(paramBody)));
        Map map1 = JSONObject.parseObject(JSONObject.toJSONString(baxxResponse),Map.class);
        String htbh  = String.valueOf(map1.get("htbh"));
        Map paramMap = new HashMap(1);
        if (StringUtils.isNotBlank(htbh)){
            paramMap.put("htbh",htbh);
        }
        //再查接口2组织参数
        Object o = exchangeBeanRequestService.request("xcFcjySpfBaxx", paramMap);
        if (o != null) {
            LOGGER.info("商品房备案信息查询,接口返回{}，", JSON.toJSONString(o));
            fcjyBaxxDTO = JSONObject.parseObject(JSONObject.toJSONString(o), FcjyBaxxDTO.class);
            List<BdcQlrDO> list = fcjyBaxxDTO.getBdcQlr();
            String xmid = fcjyBaxxDTO.getXmid();
            BdcSlJyxxDO bdcSlJyxxDO = fcjyBaxxDTO.getBdcSlJyxx();
            BdcSlFwxxDO bdcSlFwxxDO = fcjyBaxxDTO.getBdcSlFwxx();
            BdcSlXmDO bdcSlXmDO = fcjyBaxxDTO.getBdcSlXmDO();
            //组织参数
            if (CollectionUtils.isNotEmpty(list)){
                //去除返回值中的义务人和没有类型的数据，取上一手的义务人替代
                List<SpfBaxxQlrResponseData> qlr = new ArrayList<>();
                for (BdcQlrDO bdcQlrDO : list) {
                    SpfBaxxQlrResponseData spfBaxxQlrResponseData = new SpfBaxxQlrResponseData();
                    spfBaxxQlrResponseData.setQlrmc(bdcQlrDO.getQlrmc());
                    spfBaxxQlrResponseData.setQlrlx(bdcQlrDO.getQlrlb());
                    spfBaxxQlrResponseData.setQlrzjh(bdcQlrDO.getZjh());
                    spfBaxxQlrResponseData.setQlrzjzl(String.valueOf(bdcQlrDO.getZjzl()));
                    //共有人标记,现场确认，直接传默认0
                    spfBaxxQlrResponseData.setGyrbj("0");
                    qlr.add(spfBaxxQlrResponseData);

                }
                responseData.setQlr(qlr);
            }
            if (StringUtils.isNotBlank(xmid)){
                responseData.setZsxmid(xmid);
            }
            if (Objects.nonNull(bdcSlJyxxDO)){
                responseData.setHtqdrq(String.valueOf(bdcSlJyxxDO.getHtbasj()));
                responseData.setJydj(bdcSlJyxxDO.getDj());
                responseData.setBah(bdcSlJyxxDO.getHtbah());
                responseData.setJyjg(bdcSlJyxxDO.getJyje());
                responseData.setFkfs(bdcSlJyxxDO.getFkfs());
                responseData.setBarq(String.valueOf(bdcSlJyxxDO.getHtbasj()));
            }
            if (Objects.nonNull(bdcSlFwxxDO)){
                responseData.setTnmj(bdcSlFwxxDO.getTnmj());
                responseData.setSzc(bdcSlFwxxDO.getSzc());
                responseData.setJzmj(bdcSlFwxxDO.getJzmj());
                responseData.setFjh(bdcSlFwxxDO.getFjh());
                responseData.setDyzt(String.valueOf(bdcSlFwxxDO.getDyzt()));
                responseData.setZcs(bdcSlFwxxDO.getZcs());
                responseData.setFwjg(String.valueOf(bdcSlFwxxDO.getFwjg()));
                responseData.setYt(String.valueOf(bdcSlFwxxDO.getFwyt()));
                responseData.setXzqhszdm(bdcSlFwxxDO.getXzqh());
                responseData.setCfzt(String.valueOf(bdcSlFwxxDO.getXzzt()));
                responseData.setZrzh(bdcSlFwxxDO.getFwdh());
            }
            if (Objects.nonNull(bdcSlXmDO)){
                responseData.setZl(bdcSlXmDO.getZl());
                responseData.setBdcdyh(bdcSlXmDO.getBdcdyh());
            }
        }

        LOGGER.info("组织参数为:{}", responseData);
        return responseData;
    }

    private void initFwxx(List<String> bdcdyhList, JSONArray fwxx,Integer pageNumber,Integer pageSize) {
        List<String> bdcdyhs = new ArrayList<>();

        //土地
        List<String> tdbdcdyhList = bdcdyhList
                .stream()
                .filter(bdcdyh -> CommonConstantUtils.BDCLX_TZM_W.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh)))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(tdbdcdyhList)) {
            List<List<String>> partition = Lists.partition(tdbdcdyhList, 500);
            for (List<String> subBdcdyhs : partition) {
                bdcdyhs.addAll(bdcYthyrcfMapper.filterTdBdcdyh(subBdcdyhs));
            }
        }

        //非土地
        List<String> ftdbdcdyhList = bdcdyhList
                .stream()
                .filter(bdcdyh -> !CommonConstantUtils.BDCLX_TZM_W.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh)))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ftdbdcdyhList)) {
            List<List<String>> partition = Lists.partition(ftdbdcdyhList, 500);
            for (List<String> subBdcdyhs : partition) {
                bdcdyhs.addAll(bdcYthyrcfMapper.filterBdcdyh(subBdcdyhs));
            }
        }

        if (CollectionUtils.isEmpty(bdcdyhs)) {
            return;
        }
        //分页
        List<List<String>> bdcdyhPage = Lists.partition(bdcdyhs, pageSize);
        if (pageNumber > bdcdyhPage.size()) {
            bdcdyhs = bdcdyhPage.get(bdcdyhPage.size() - 1);
        } else {
            bdcdyhs = bdcdyhPage.get(pageNumber - 1);
        }

//        if (bdcdyhs.size() > (pageNumber + 1) * pageSize) {
//            bdcdyhs =  bdcdyhs.subList(pageSize * pageNumber, (pageNumber + 1) * pageSize - 1);
//        } else {
//            bdcdyhs =  bdcdyhs.subList(pageSize * pageNumber, bdcdyhs.size());
//        }

        for (String bdcdyh : bdcdyhs) {
            boolean tdDyh = CommonConstantUtils.BDCLX_TZM_W.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh));
            // 根据bdcdyh查询登记库权利表
            List<BdcQl> bdcQlFdcq = new ArrayList<>();
            List<BdcQl> bdcQlYg = new ArrayList<>();

            List<BdcQl> bdcQls = new ArrayList<>();
            if (!tdDyh) {
                //320602002014GB00100F00010017
                bdcQlFdcq = commonService.listQlByBdcdyh(
                        bdcdyh,
                        CommonConstantUtils.QLLX_FDCQ_DM.toString(),
                        Arrays.asList(1, 2)
                );

                //340104408001GB00004F00010002
                bdcQlYg = commonService.listQlByBdcdyh(
                        bdcdyh,
                        CommonConstantUtils.QLLX_YG_DM.toString(),
                        Arrays.asList(0, 1)
                );
                bdcQls.addAll(bdcQlYg);
            }
            bdcQls.addAll(bdcQlFdcq);
            bdcQls.addAll(bdcQlYg);

            //320602009002GB03600F00010014
            List<BdcQl> bdcQlDyaq = commonService.listQlByBdcdyh(
                    bdcdyh,
                    CommonConstantUtils.QLLX_DYAQ_DM.toString(),
                    Arrays.asList(0, 1)
            );
            bdcQls.addAll(bdcQlDyaq);


            List<BdcQl> bdcQlCf = commonService.listQlByBdcdyh(
                    bdcdyh,
                    CommonConstantUtils.QLLX_CF.toString(),
                    Arrays.asList(0, 1)
            );
            bdcQls.addAll(bdcQlCf);


            List<BdcQl> bdcQlJs = commonService.listQlByBdcdyh(
                    bdcdyh,
                    CommonConstantUtils.QLXX_QLLX_JSYDSYQ,
                    Arrays.asList(1)
            );
            bdcQls.addAll(bdcQlJs);


            List<BdcQl> bdcQlYy = commonService.listQlByBdcdyh(
                    bdcdyh,
                    CommonConstantUtils.QLLX_YY.toString(),
                    Arrays.asList(0, 1)
            );
            bdcQls.addAll(bdcQlYy);

            if (CollectionUtils.isEmpty(bdcQls)) {
                continue;
            }

            // 字段对照并处理特殊字段
            JSONObject fwxxResponse = new JSONObject();
            JSONArray fwqsxxFdcq = bdcQjService.dozerMapList(bdcQlFdcq,
                    "fwqsxxFdcq",
                    HashMap.class);
            fwxxResponse.put("QLT_FW_FDCQ_YZ", fwqsxxFdcq);


            JSONArray fwqsxxDyaq = bdcQjService.dozerMapList(bdcQlDyaq,
                    "fwqsxxDyaq",
                    HashMap.class);
            //获取抵押的上一手
            for (int i = 0; i < fwqsxxDyaq.size(); i++) {
                JSONObject dyaqObject = fwqsxxDyaq.getJSONObject(i);
                if(dyaqObject.containsKey("XMID")){
                    try {
                        List<BdcSlXmLsgxDO> xmLsgxDOS = bdcSlXmLsgxFeignService.listBdcSlXmLsgxByXmid(dyaqObject.getString("XMID"));
                        if(CollectionUtils.isNotEmpty(xmLsgxDOS)){
                            dyaqObject.put("SCYWH",xmLsgxDOS.get(0).getYxmid());
                        }
                    }catch (Exception e){

                    }
                }
            }
            fwxxResponse.put("QLF_QL_DYAQ",
                    fwqsxxDyaq
            );


            JSONArray fwqsxxYgdj = bdcQjService.dozerMapList(bdcQlYg,
                    "fwqsxxYgdj",
                    HashMap.class);
            for (int i = 0; i < fwqsxxYgdj.size(); i++) {
                JSONObject ygObject = fwqsxxYgdj.getJSONObject(i);
                if(ygObject.containsKey("XMID") && ygObject.containsKey("BDCDYH")){
                    List<BdcSlXmLsgxDO> xmLsgxDOS = bdcSlXmLsgxFeignService.listBdcSlXmLsgxByXmid(ygObject.getString("XMID"));
                    if(CollectionUtils.isNotEmpty(xmLsgxDOS)){
                        ygObject.put("SCYWH",xmLsgxDOS.get(0).getYxmid());
                    }

                    List<BdcQl> ygFdcq = commonService.listQlByBdcdyh(
                            ygObject.getString("BDCDYH"),
                            CommonConstantUtils.QLLX_FDCQ_DM.toString(),
                            Arrays.asList(0, 1)
                    );
                    if(CollectionUtils.isNotEmpty(ygFdcq)){
                        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) ygFdcq.get(0);
                        ygObject.put("FWJG",bdcFdcqDO.getFwjg());
                    }
                }
            }
            fwxxResponse.put("QLF_QL_YGDJ",fwqsxxYgdj);




            fwxxResponse.put("QLF_QL_YYDJ",
                    bdcQjService.dozerMapList(bdcQlYy,
                            "fwqsxxYydj",
                            HashMap.class)
            );


            fwxxResponse.put("QLF_QL_CFDJ",
                    bdcQjService.dozerMapList(bdcQlCf,
                            "fwqsxxCfdj",
                            HashMap.class)
            );


            fwxxResponse.put("QLF_QL_JSYDSYQ",
                    bdcQjService.dozerMapList(bdcQlJs,
                            "fwqsxxJsydsyq",
                            HashMap.class)
            );


            List<String> xmids = bdcQls.stream().map(BdcQl::getXmid).collect(Collectors.toList());
            List<ZttGyQlrDO> allBdcQlr = bdcQjService.queryZttGyQlr(xmids,CommonConstantUtils.QLRLB_QLR);
            JSONArray fwqsxxQlr = bdcQjService.dozerMapList(allBdcQlr, "fwqsxxQlr", HashMap.class);
            for (int i = 0; i < fwqsxxQlr.size(); i++) {
                JSONObject qlrObject = fwqsxxQlr.getJSONObject(i);
                qlrObject.put("JS","权利人");
            }
            List<ZttGyQlrDO> allBdcYwr = bdcQjService.queryZttGyQlr(xmids,CommonConstantUtils.QLRLB_YWR);
            JSONArray fwqsxxYwr = bdcQjService.dozerMapList(allBdcYwr, "fwqsxxQlr", HashMap.class);
            for (int i = 0; i < fwqsxxYwr.size(); i++) {
                JSONObject qlrObject = fwqsxxQlr.getJSONObject(i);
                qlrObject.put("JS","义务人");
            }
            fwqsxxQlr.addAll(allBdcYwr);
            fwxxResponse.put("ZTT_GY_QLR", fwqsxxQlr);

           // List<QltQlXzxzDO> qltQlXzxzDOS = bdcdjMapper.queryXzxxForYthQltQlXzxzByBdcdyh(bdcdyh);
            List<QltQlXzxzDO> qltQlXzxzDOS = bdcdjMapper.queryXzxxForYthQltQlXzxzByBdcdyh(bdcdyh);
            fwxxResponse.put("QLF_QL_XZXZ", qltQlXzxzDOS);
            // 处理权籍数据
            fwxxResponse.put("KTT_ZDJBXX", bdcQjService.queryZdjbxx(bdcdyh));
            if (!tdDyh) {
                fwxxResponse.put("KTT_FW_ZRZ", bdcQjService.queryZrz(bdcdyh));
                fwxxResponse.put("KTT_FW_LJZ", bdcQjService.queryLjz(bdcdyh));
                fwxxResponse.put("KTT_FW_H", bdcQjService.queryFwH(bdcdyh));
                fwxxResponse.put("KTT_FW_C", bdcQjService.queryFwC(bdcdyh));
            }
            fwxx.add(fwxxResponse);
        }
    }



    private List<JtBdcxxTycxResponseDTO> queryJtBdcXx(JtBdcxxTycxRequestDTO jtBdcxxTycxRequestDTO) {
        if (Objects.isNull(jtBdcxxTycxRequestDTO)) {
            throw new MissingArgumentException("参数为空！请检查参数！");
        }
        LOGGER.info("queryJtBdcXx查询参数：{}", jtBdcxxTycxRequestDTO);
        List<JtBdcxxTycxResponseDTO> jtBdcxxTycxResponseDTOList = repository.selectList("queryJtBdcXx", jtBdcxxTycxRequestDTO);
        LOGGER.info("queryJtBdcXx查询结果：{}", jtBdcxxTycxResponseDTOList);
        if (CollectionUtils.isEmpty(jtBdcxxTycxResponseDTOList)) {
            return null;
        }
        return jtBdcxxTycxResponseDTOList;
    }

    /**
     * 根据受理编号获取土地业务标识
     */
    private String getTdbzBySlbh(String slbh){
        if(StringUtils.isNotBlank(slbh)){
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setSlbh(slbh);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList) &&CommonConstantUtils.DYBDCLX_CTD.equals(bdcXmDOList.get(0).getBdclx())){
                return CommonConstantUtils.SF_S_DM.toString();
            }
        }
        return CommonConstantUtils.SF_F_DM.toString();
    }

}
