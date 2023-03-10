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
 * ???????????????????????????
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/15 15:04
 */
@Service
@Validated
public class GxWwSqxxServiceImpl implements GxWwSqxxService {
    /**
     * dyxxAndYgdyxx????????????????????????
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

    // ??????????????????????????????
    @Value("#{'${cxqlrcqxx.qxdmjh:}'.split(',')}")
    private List<String> cxCqQxdmjh;

    // ??????????????????????????????
    @Value("#{'${cxqlrcqxx.qllxjh:3,4,5,6,7,8,96}'.split(',')}")
    private List<Integer> cxCqQllxjh;

    // ??????????????????????????????
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
     * ????????????
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
     * ???????????????????????????id
     */
    @Value("#{'${zhdk.gzldyid:}'.split(',')}")
    private List<String> zhdkGzldyidList;


    /**
     * ???????????????????????????id
     */
    @Value("#{'${initwwsq.zdbjlist:}'.split(',')}")
    private List<String> initwwsqZdbjList;
    @Value("${initwwsq.zdbjslr:}")
    private String zdbjslr;

    @Value("${fjxx.sljdmc: ??????}")
    private String fjxxSljdmc;
    @Value("${fjxx.shjdmc: ??????}")
    private String fjxxShjdmc;

    /**
     * ??????????????????????????????????????????
     */
    @Value("${zhlc.tsslr: false}")
    private Boolean zhlcTsslr;

    @Value("${wwsq.turnworkbyslr:true}")
    private Boolean turnWorkBySlr;

    /**
     * ???????????????????????????id
     */
    @Value("#{'${dydj.scdy.gzldyid:}'.split(',')}")
    private List<String> scdyGzldyidList;

    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    BdcdjMapper bdcdjMapper;

    // ????????????????????????????????????????????????????????????
    @Value("#{'${dyxxandydyxx.xmcqqllx: 4,6,8}'.split(',')}")
    private List<Integer> xmcqQllx;

    @Autowired
    private StorageClientMatcher storageClient;
    /**
     * ???????????????????????????
     */
    private ThreadPoolExecutor fjThreadPool = new ThreadPoolExecutor(
            // ??????????????????
            2,
            // ??????????????????
            2,
            // ????????????
            60L, TimeUnit.SECONDS,
            // ????????????100
            new ArrayBlockingQueue<>(100),
            // ?????????????????????????????????
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * ????????????????????????
     *
     * @param slbh
     * @return ????????????  ?????????????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public String getWwsqzt(@NotBlank(message = "????????????????????????") String slbh) {
        String jdzt = "";
        List<BdcXmDO> listXm = commonService.listBdcXmBySlbh(slbh);
        if (CollectionUtils.isNotEmpty(listXm)) {
            BdcXmDO bdcXmDO = listXm.get(0);
            if (bdcXmDO != null) {
                if (CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDO.getAjzt())) {
                    jdzt = "?????????";
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
     * @description ????????????
     */
    @Override
    public Map<String, Object> deleteTask(WwsqdeltaskRequestDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "1000");
        resultMap.put("msg", "????????????");

        if (StringUtils.isNotBlank(dto.getProcessInstanceId())) {
            // ????????????????????? ????????????????????????
            if (checkSlzt(dto.getProcessInstanceId())) {
                resultMap.put("msg", "????????????????????????????????????");
            } else {
                // ????????????????????? id ???????????????????????? ????????????????????????????????????????????????
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
                        resultMap.put("msg", "????????????");
                    } catch (Exception e) {
                        resultMap.put("msg", "???????????????");
                        LOGGER.error("????????????????????????:{}", dto.getProcessInstanceId(), e);
                    }
                } else {
                    resultMap.put("msg", "?????????????????????");
                }
            }
        } else {
            resultMap.put("msg", "???????????????ID????????????");
        }
        return resultMap;
    }

    /**
     * @param gzlslid
     * @return boolea
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description ????????????????????????????????????????????????false ?????????true
     */
    private boolean checkSlzt(String gzlslid) {
        // ??????????????????
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
     * ?????????????????????????????????
     *
     * @param cqzh
     * @param dyr
     * @param zl
     * @param dyrzjh
     * @return ????????????
     */
    @Override
    public List<Map> getBdczsxx(String cqzh, String dyr, String zl, String dyrzjh, String xmid) {
        //????????????
        Map paramMap = new HashMap();
        paramMap.put("qlrmc", dyr);
        if (StringUtils.isNotBlank(dyrzjh)) {
            // ??????15  18??? QLR ??????
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
        //??????
        List<Map> listCqxx = wwsqMapper.getBdczsxx(paramMap);
        if (CollectionUtils.isNotEmpty(listCqxx)) {
            for (Map map : listCqxx) {
                if (StringUtils.isNotBlank(MapUtils.getString(map, "xmid"))) {
                    //??????????????????????????????
                    map.put("gltdzxx", getGltdzxx(MapUtils.getString(map, "xmid")));

                    map.put("zsxmid", MapUtils.getString(map, "xmid"));
                    //????????????????????????
                    if(StringUtils.isBlank(MapUtils.getString(map, "jyjg"))){
                        map.put("jyjg", "");
                    }else{
                        map.put("jyjg", MapUtils.getString(map, "jyjg"));
                    }
                    //?????????????????????
                    map.put("qlr", qlrsjConvert(MapUtils.getString(map, "xmid"), CommonConstantUtils.QLRLB_QLR, "CommonQlr"));
                    if (StringUtils.isNotBlank(MapUtils.getString(map, "bdcdyh"))) {
                        //????????????
                        String bdcdyh = map.get("bdcdyh").toString();
                        List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());

                        if (CollectionUtils.isNotEmpty(listDyaq)) {
                            map.put("dyzt", Constants.WWSQ_SF_S);
                        } else {
                            map.put("dyzt", Constants.WWSQ_SF_F);
                        }
                        //????????????
                        List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                        if (CollectionUtils.isNotEmpty(listCf)) {
                            map.put("cfzt", Constants.WWSQ_SF_S);
                        } else {
                            map.put("cfzt", Constants.WWSQ_SF_F);
                        }
                        if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_HF, dataversion)) {
                            // 03-31 ?????????????????????
                            String xzq = hfXzqFeignService.queryXzqByBdcdyh(bdcdyh, null, "");
                            map.put("xzqhszdm", xzq);
                        }

                        //37562 ???????????????????????????+?????????????????????  ????????????fdcq??????tdsyqmj
                        List<BdcFdcqDO> fdcqDOList = wwsqMapper.fdcqList(bdcdyh);
                        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
                        FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh, "");
                        fdcqMap(map, fdcqDOList, zdMap, fwHsDO);

                        //?????????????????????????????????????????????
                        // ????????????????????????
                        //????????????
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
     * ?????????????????????????????????
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
     * ??????fcxmid????????????xmid
     *
     * @param
     * @return list
     * @Date 2021/1/20
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public List<Map> getGltdzxx(String fcxmid) {
    /*
    36696 ???????????????????????????????????????????????????????????????????????????
    ??????xmid??????BDC_FCTD_PPGX??????????????????????????????
     */
        List<BdcFctdPpgxDO> ppgxDOS = bdcPpFeignService.queryBdcFctdPpgx(fcxmid);
        List<Map> gltszxxlist = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(ppgxDOS)) {
            String tdxmid = ppgxDOS.get(0).getTdcqxmid();
            //??????bdcxm??????????????????????????????
//                       BdcXmDO bdcXmDO = bdcXmFeignService.
            BdcXmDO tdxm = entityMapper.selectByPrimaryKey(BdcXmDO.class, tdxmid);
            //???????????????????????????
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
            //?????????????????????
            gltdzxxMap.put("qlr", qlrsjConvert(tdxmid, CommonConstantUtils.QLRLB_QLR, "CommonQlr"));
            gltszxxlist.add(gltdzxxMap);

        }
        return gltszxxlist;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param bdcdjzmh
     * @param dyqr
     * @param dyrmc
     * @param dyqrzjh
     * @param dyrzjh
     * @return ????????????
     */
    @Override
    public List<Map> getBdczmxx(String bdcdjzmh, String dyqr, String dyrmc, String dyqrzjh, String dyrzjh) {
        List<Map> resultList = new ArrayList<>();

        //????????????????????????dv_dyzx???????????????roomid??????,???????????????????????????
        if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ, dataversion)) {
            Example example = new Example(DvDyzx.class);
            example.createCriteria().andEqualTo("bdcdjzmh", bdcdjzmh).andEqualTo("dyqrmc", dyqr);
            List<DvDyzx> dyzxList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(dyzxList)) {
                for (DvDyzx dyzx : dyzxList) {
                    Map mapResult = new HashMap();
                    exchangeDozerMapper.map(dyzx, mapResult, "BdczmxxXm_changzhou");
                    //?????????
                    List<Map> qlrlist = new ArrayList<>();
                    BdcQlrDO qlrDO = new BdcQlrDO();
                    Map qlrMap = exchangeDozerMapper.map(dyzx, Map.class, "BdczmxxQlr_changzhou");
                    if (MapUtils.isNotEmpty(qlrMap)) {
                        qlrlist.add(qlrMap);
                    }
                    mapResult.put("qlr", qlrlist);
                    //????????????
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
        //????????????
        Map paramMap = new HashMap(7);
        paramMap.put("dyqr", dyqr);
        paramMap.put("dyrmc", dyrmc);
        if (StringUtils.isNotBlank(dyqrzjh)) {
            // ??????15  18??? QLR ??????
            String revertZjhs = CardNumberTransformation.zjhTransformation(dyqrzjh);
            String[] zjhArr = revertZjhs.split(",");
            paramMap.put("dyqrzjhArr", Arrays.asList(zjhArr));
        }
        if (StringUtils.isNotBlank(dyrzjh)) {
            // ??????15  18??? QLR ??????
            String revertZjhs = CardNumberTransformation.zjhTransformation(dyrzjh);
            String[] zjhArr = revertZjhs.split(",");
            paramMap.put("dyrzjhArr", Arrays.asList(zjhArr));
        }
        paramMap.put("likebdcqzh", bdcdjzmh);
        paramMap.put("ajzt", CommonConstantUtils.AJZT_YB_DM);
        paramMap.put("qszt", CommonConstantUtils.QSZT_VALID);
        //??????
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

                            //?????????????????????
                            mapResult.put("dyqlr", qlrsjConvert(xmid, CommonConstantUtils.QLRLB_QLR, "BdczmxxQlr"));
                            //?????????????????????
                            mapResult.put("qlr", getDyaqYwr(xmid, dyaqDO.getBdcdyh()));
                            // 03-31 ?????????????????????
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
     * ????????????????????????
     *
     * @param
     * @param requestDTO
     * @return ????????????
     */
    @Override
    public List<DycxcqResponseDTO> getWwsqDyCqxx(DycxcqRequestDTO requestDTO) {
        // ??????????????????????????????
        if (StringUtil.isEmpty(requestDTO.getDyqr()) && StringUtil.isEmpty(requestDTO.getDyzmh())) {
            throw new MissingArgumentException("dyqr???dyzmh??????????????????");
        }
        // ???????????????????????????
        List<BdcXmDO> listDyXmxx = wwsqMapper.getWwsqDyXmxx(requestDTO);

        // ??????????????????
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
                //??????????????????
                List<Integer> qsztList = new ArrayList<>();
                qsztList.add(Constants.QSZT_XS);
                //????????????
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
     * @description ???????????????????????? ????????????????????????
     */
    private List<Map> getDyaqYwr(String xmid, String bdcdyh) {
        List<Map> list = new ArrayList<>();
        // ????????????????????????
        List<BdcQlrDO> dyaqYwrList = commonService.listBdcQlrByXmid(xmid, CommonConstantUtils.QLRLB_YWR);
        // ???????????????????????????
        List<BdcQlrDO> cqQlrList = listCqBdcQlr(bdcdyh);
        // ??????????????????????????? ????????????
        fillCqzhToDyaYwr(dyaqYwrList, cqQlrList);
        // ?????????
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
     * @description ??? ?????????????????? ?????? ????????????
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
     * @description ??????BDCDYH ?????? ??????????????? ????????? ??????
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
     * ???????????????????????????????????????
     *
     * @param cqzh
     * @return
     */
    @Override
    public List<Map> querycqzdyxx(@NotBlank(message = "??????????????????") String cqzh) {
        List<Map> resultList = new ArrayList<>();
        // ?????? ?????????????????? ????????????
        BdcZsQO zsQO = new BdcZsQO();
        zsQO.setBdcqzh(cqzh);
        List<BdcZsDO> zsList = bdcZsFeignService.listBdcZs(zsQO);
        //??????
        if (CollectionUtils.isNotEmpty(zsList)) {
            for (BdcZsDO zsDO : zsList) {
                // ???????????? ZS ??? ????????????
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
                                        // ???????????? ????????????
                                        exchangeDozerMapper.map(dyXmDO, mapResult, "BdczmxxXm");
                                        // ???????????? ????????????
                                        exchangeDozerMapper.map(qltemp, mapResult, "BdczmxxQl");
                                        //?????????????????????
                                        mapResult.put("dyqlr", qlrsjConvert(qltemp.getXmid(), CommonConstantUtils.QLRLB_QLR, "BdczmxxQlr"));
                                        //?????????????????????
                                        mapResult.put("qlr", qlrsjConvert(qltemp.getXmid(), CommonConstantUtils.QLRLB_YWR, "BdczmxxYwr"));

                                        //?????????????????????????????????????????????
                                        // ????????????????????????
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
     * @description ??????????????????????????? ????????????????????????
     */
    @Override
    public GetWwsqCqzxxFyResponseData getWwsqCqzxxPageXzql(GetWwsqCqzxxFyResponseData responseData) {
        if (responseData != null && CollectionUtils.isNotEmpty(responseData.getCqxx())) {
            for (GetWwsqCqzxxResponseCqxx cqxxTemp : responseData.getCqxx()) {
                if (StringUtils.isNotBlank(cqxxTemp.getBdcdyh())) {
                    String bdcdyh = cqxxTemp.getBdcdyh();
                    //????????????
                    List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());
                    if (CollectionUtils.isNotEmpty(listDyaq)) {
                        cqxxTemp.setSfdy(Constants.WWSQ_SF_S + "");
                    } else {
                        cqxxTemp.setSfdy(Constants.WWSQ_SF_F + "");
                    }
                    //????????????
                    List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    if (CollectionUtils.isNotEmpty(listCf)) {
                        cqxxTemp.setSfcf(Constants.WWSQ_SF_S + "");
                    } else {
                        cqxxTemp.setSfcf(Constants.WWSQ_SF_F + "");
                    }
                }
                //?????????????????????????????????
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
     * @description ??????????????? ???????????? ???????????? ?????? ?????????
     */
    @Override
    public void rollbackInitXm(Map<String, String> rollbackParam) {
        // ????????????
        if (StringUtils.isNotBlank(rollbackParam.get("xmid"))) {
            String[] xmids = new String[]{rollbackParam.get("xmid")};
            try {
                bdcInitFeignService.deleteYwxx(xmids);
            } catch (Exception e1) {
                LOGGER.error("???????????????????????????????????????????????????", e1);
            }
        }
        // ????????????
        try {
            if (StringUtils.isNotBlank(rollbackParam.get("taskid"))) {
                taskHandleClient.deleteTask(rollbackParam.get("taskid"));
            }
        } catch (Exception e1) {
            LOGGER.error("???????????????????????????????????????????????????", e1);
        }

        // ??????????????????
        try {
            if (StringUtils.isNotBlank(rollbackParam.get("jyxxid"))) {
                bdcSlJyxxFeignService.deleteBdcSlJyxxByJyxxid(rollbackParam.get("jyxxid"));
            }
        } catch (Exception e1) {
            LOGGER.error("???????????????????????????????????????????????????", e1);
        }
    }

    /**
     * @param methodName
     * @param gxWwSqxxDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????????????? ?????????????????????ID
     */
    @Override
    public void setGzldyidInSqxx(String methodName, GxWwSqxxDO gxWwSqxxDO) {
        // ????????????????????????  ???????????????????????????ID
        if (StringUtils.equals(methodName, Constants.WWSQ_INIT_DYDJ)) {
            gxWwSqxxDO.setSqlx(Constants.WWSQ_INIT_DYDJ_GZLDYID);
        }
        // ??????????????????????????????  ??????????????????????????????????????????ID
        if (StringUtils.equals(methodName, Constants.WWSQ_INIT_DYZX)) {
            gxWwSqxxDO.setSqlx(Constants.WWSQ_INIT_DYZX_GZLDYID);
        }
    }

    /**
     * @param gxWwSqxxDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????????????? ?????????????????????ID
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
     * @description ??????BDCDYH ?????????????????????
     */
    @Override
    public String gzyzBdcdy(String processDefKey, String bdcdyh) {
        //????????????
        UserDto yzr = userManagerUtils.getCurrentUser();
        String yzrid = null == yzr ? null : yzr.getId();
        String yzrzh = null == yzr ? null : yzr.getUsername();

        String yzResultMsg = "";
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.isNotBlank(processDefKey)) {
            BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
            bdcGzYzQO.setZhbs(processDefKey + "_XZBDCDY");
            // ?????????????????????????????????
            List<Map<String, Object>> gzyzParamList = new ArrayList<>();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("bdcdyh", bdcdyh);
            gzyzParamList.add(paramMap);
            bdcGzYzQO.setParamList(gzyzParamList);
            bdcGzYzQO.setYzrid(yzrid);
            bdcGzYzQO.setYzrzh(yzrzh);
            // ??????????????????
            List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
            if (CollectionUtils.isNotEmpty(listBdcGzYzTsxx)) {
                StringBuilder tsxxSb = new StringBuilder();
                BdcGzYzTsxxDTO dto = listBdcGzYzTsxx.get(0);
                if (CollectionUtils.isNotEmpty(dto.getZgzTsxxDTOList())) {
                    // ?????? ??????????????????
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
     * @description ?????????????????????
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
                            throw new AppException("????????????" + sqr.getSqrmc() + ",????????????" + sqr.getZjh() + "????????????????????????");
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
     * @description ???????????? ??????BDCDYH (???????????????????????????????????????????????????
     * ??????????????????????????????????????????????????????)
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
     * @description ?????? ????????????????????????
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
                //??????TaskId
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
                        return ExchangeDsfCommonResponse.fail("?????????????????????taskId??????", cfjfdjResponseDTO);
                    }
                    return ExchangeDsfCommonResponse.ok(cfjfdjResponseDTO);
                } else {
                    LOGGER.info("---??????????????????????????????,??????taskId??????:{},??????taskId?????????{}", result, JSON.toJSONString(taskDataList));
                    return ExchangeDsfCommonResponse.fail("??????????????????????????????,??????taskId??????");
                }
            } else {
                LOGGER.info("---??????????????????????????????:{}", result);
                return ExchangeDsfCommonResponse.fail("??????????????????????????????");
            }
        } catch (Exception e) {
            LOGGER.info("---??????????????????????????????:{}", e.getMessage());
            return ExchangeDsfCommonResponse.fail("??????????????????????????????");
        }
    }

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ????????????????????????
     */
    @Override
    public Map<String, Object> revertCjResponse(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                                List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf) {
        // ????????????????????????
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
        //???????????????????????????????????????????????????
        String slr = StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getZdzfslr()) ? wwsqCjBdcXmRequestDTO.getZdzfslr() : wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getSlr();

        String gzldyid = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getGzldyid();
        // ??????????????????
        fjclService.asynUploadAndSaveFjcl(responseDTO, fjclList);
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            // ??????????????????
            try {
                Map<String, Object> paramMap = new HashMap<>();
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getFsr())) {
                    redisUtils.addHashValue(CommonConstantUtils.ZDZF_SLR + "FSR", gzlslid, wwsqCjBdcXmRequestDTO.getFsr(), 24 * 60 * 60);
                }
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getDbr())) {
                    redisUtils.addHashValue(CommonConstantUtils.ZDZF_SLR + "DBR", gzlslid, wwsqCjBdcXmRequestDTO.getDbr(), 24 * 60 * 60);
                }


                // ?????????????????????????????????????????????
                DsfSlxxDTO dsfSlxxDTO = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList().get(0).getDsfSlxxDTO();
                Date yykssj = dsfSlxxDTO.getYykssj();
                Date yyjssj = dsfSlxxDTO.getYyjssj();
                String yyfzx = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getQxdm();
                if (StringUtils.isNotBlank(yyfzx) || null != yykssj || null != yyjssj) {
                    paramMap.put("YYKSSJ", dsfSlxxDTO.getYykssj());
                    paramMap.put("YYJSSJ", dsfSlxxDTO.getYyjssj());
                    paramMap.put("YYFZX", yyfzx);
                }
                // ????????????????????????
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getSfzdzfdb())) {
                    paramMap.put("SFZDZFDB", wwsqCjBdcXmRequestDTO.getSfzdzfdb());
                }
                //????????????
                List<BdcSfxxDTO> bdcSfxxDTOS = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList().get(0).getBdcSfxxDTOList();
                if (CollectionUtils.isNotEmpty(bdcSfxxDTOS) && bdcSfxxDTOS.get(0).getBdcSlSfxxDO() != null) {
                    Integer sfzt = bdcSfxxDTOS.get(0).getBdcSlSfxxDO().getSfzt();
                    if (sfzt != null) {
                        paramMap.put("JFZT", sfzt);
                    }
                }
                //??????????????????
                if (StringUtils.isNotBlank(dsfSlxxDTO.getSqbmmc())) {
                    paramMap.put("SQBMMC", dsfSlxxDTO.getSqbmmc());
                }
                //????????????
                if (StringUtils.isNotBlank(dsfSlxxDTO.getYwlx())) {
                    paramMap.put("YWLX", dsfSlxxDTO.getYwlx());
                }
                // ????????????
                if (MapUtils.isNotEmpty(paramMap)) {
                    LOGGER.info("??????????????????????????????,paramMap:{}", JSON.toJSONString(paramMap));
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                }
            } catch (Exception e) {
                LOGGER.error("????????????????????????,slbh:{}", responseDTO.getBdcXmDOList().get(0).getSlbh(), e);
            }
            //????????????????????????????????????????????????
            if (Objects.nonNull(zdzf) && StringUtils.equals(Constants.WWSQ_CJXM_AUTOTURN, zdzf)) {
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                LOGGER.info("---????????????????????????:{},???????????????id:{},?????????????????????{}", zdzf, gzlslid, wwsqCjBdcXmRequestDTO.getZdzfslr());
                autoTurnWorkflow(gzlslid, zdzf, wwsqCjBdcXmRequestDTO.getZdzfslr());
            }
            //??????????????????gzldyid
            if (initwwsqZdbjList.contains(gzldyid)) {
                LOGGER.info("??????????????????????????????????????????????????????????????????id???{},?????????????????????slr??????{}", gzldyid, zdbjslr);
                responseDTO.setSfzbbj(true);
                zdbjOrzdzf(responseDTO, zdbjslr, "1", zdzf1);
                LOGGER.info("??????????????????,??????????????????");
            } else {
                //????????????????????????????????????????????????????????????????????????
                LOGGER.info("??????????????????????????????????????????????????????????????????id???{},?????????????????????slr??????{},??????????????????{}", gzldyid, slr, zdzf1);
                zdbjOrzdzf(responseDTO, slr, zdbj, zdzf1);

            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("proid", spxtywh);
            resultMap.put("gzlslid", responseDTO.getBdcXmDOList().get(0).getGzlslid());
            resultMap.put("ywslbh", responseDTO.getBdcXmDOList().get(0).getSlbh());
            resultMap.put("msg", "success");
            if (Boolean.TRUE.equals(wwsqCjBdcXmRequestDTO.isSendmsg())) {
                LOGGER.info("??????????????????????????????????????????:{}", responseDTO.getBdcXmDOList().get(0).getGzlslid());
                //??????????????????
                sendMsgService.sendMessage(resultMap, "wwsqcj");
            }
            return resultMap;
        } else {
            throw new AppException("????????????????????????");
        }
    }

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ????????????????????????
     */
    @Override
    public Map<String, Object> revertCzCjResponseForZhlc(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                                         List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf) {
        // ????????????????????????
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
            // ??????????????????,1:??????????????????????????????????????????????????????2???????????????????????????????????????????????????????????????????????????????????????
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
            // ??????????????????
            try {
                Map<String, Object> paramMap = new HashMap<>();
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();

                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getFsr())) {
                    redisUtils.addHashValue(CommonConstantUtils.ZDZF_SLR + "FSR", gzlslid, wwsqCjBdcXmRequestDTO.getFsr(), 24 * 60 * 60);
                }
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getDbr())) {
                    redisUtils.addHashValue(CommonConstantUtils.ZDZF_SLR + "DBR", gzlslid, wwsqCjBdcXmRequestDTO.getDbr(), 24 * 60 * 60);
                }
                // ?????????????????????????????????????????????
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
                //????????????????????????
                if (StringUtils.isNotBlank(sqbmmc)) {
                    paramMap.put("SQBMMC", sqbmmc);
                }
                //????????????
                if (StringUtils.isNotBlank(dsfSlxxDTO.getYwlx())) {
                    paramMap.put("YWLX", dsfSlxxDTO.getYwlx());
                }
                // ????????????????????????
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getSfzdzfdb())) {
                    paramMap.put("SFZDZFDB", wwsqCjBdcXmRequestDTO.getSfzdzfdb());
                }
                // ????????????
                if (MapUtils.isNotEmpty(paramMap)) {
                    LOGGER.info("??????????????????????????????,paramMap:{}", JSON.toJSONString(paramMap));
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                }
            } catch (Exception e) {
                LOGGER.error("????????????????????????,slbh:{}", responseDTO.getBdcXmDOList().get(0).getSlbh(), e);
            }
            String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
            //???????????????????????????????????????????????????
            String slr = StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getZdzfslr()) ? wwsqCjBdcXmRequestDTO.getZdzfslr() : wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getSlr();
            String zdbj = wwsqCjBdcXmRequestDTO.getSfzdbj();
            // ????????????????????????
            LOGGER.info("????????????????????????????????????????????????????????????????????????id???{},?????????????????????slr??????{},??????????????????{}", gzlslid, slr, zdbj);
            zdbjOrzdzf(responseDTO, slr, zdbj, null);
            //????????????????????????????????????????????????
            if (Objects.nonNull(zdzf) && StringUtils.equals(Constants.WWSQ_CJXM_AUTOTURN, zdzf)) {
                LOGGER.info("---????????????????????????:{},???????????????id:{},?????????????????????{}", zdzf, gzlslid, wwsqCjBdcXmRequestDTO.getZdzfslr());
                autoTurnWorkflow(gzlslid, zdzf, wwsqCjBdcXmRequestDTO.getZdzfslr());
            } else if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getZdzf())) {
                //????????????????????????????????????????????????????????????????????????
                LOGGER.info("---????????????????????????????????????:{},???????????????id:{},??????????????????{}", wwsqCjBdcXmRequestDTO.getZdzf(), gzlslid, wwsqCjBdcXmRequestDTO.getZdzfslr());
                autoTurnWorkflow(gzlslid, wwsqCjBdcXmRequestDTO.getZdzf(), wwsqCjBdcXmRequestDTO.getZdzfslr());
            }
            //????????????????????????????????????????????????
           /* if (Objects.nonNull(zdzf) && StringUtils.equals(Constants.WWSQ_CJXM_AUTOTURN, zdzf)) {
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                LOGGER.info("---????????????????????????:{},???????????????id:{},??????????????????{}", zdzf, gzlslid, wwsqCjBdcXmRequestDTO.getZdzfslr());
                autoTurnWorkflow(gzlslid, zdzf, wwsqCjBdcXmRequestDTO.getZdzfslr());
            }*/
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("proid", spxtywh);
            resultMap.put("gzlslid", responseDTO.getBdcXmDOList().get(0).getGzlslid());
            resultMap.put("ywslbh", responseDTO.getBdcXmDOList().get(0).getSlbh());
            resultMap.put("msg", "success");
            return resultMap;
        } else {
            throw new AppException("????????????????????????");
        }
    }


    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description ?????? ????????????????????????
     */
    @Override
    public Map<String, Object> revertCzCjResponse(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                                  List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf) {
        // ????????????????????????
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
        // ?????????????????? ????????????????????????????????????
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList()) && StringUtils.isNotBlank(responseDTO.getBdcXmDOList().get(0).getGzlslid())) {
            bdcSlFeignService.cshSjcl(responseDTO.getBdcXmDOList().get(0).getGzlslid());
        }
        // ??????????????????
        LOGGER.info("????????????:{}", CollectionUtils.isEmpty(fjclList) ? 0 : fjclList.size());
        fjclService.asynUploadAndSaveFjcl(responseDTO, fjclList);
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            //????????????????????????????????????????????????
            autoTurnWorkflow(responseDTO.getBdcXmDOList().get(0).getGzlslid(), zdzf, "");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("proid", spxtywh);
            resultMap.put("gzlslid", responseDTO.getBdcXmDOList().get(0).getGzlslid());
            resultMap.put("ywslbh", responseDTO.getBdcXmDOList().get(0).getSlbh());
            resultMap.put("msg", "success");
            return resultMap;
        } else {
            throw new AppException("????????????????????????");
        }
    }

    /**
     * @param responseDTO
     * @param fjclList
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????????????????????
     */
    @Override
    public Map<String, Object> revertNtCjResponse(WwsqCjBdcXmResponseDTO responseDTO, List<FjclDTO> fjclList, String zdzf) {
        // ????????????????????????
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
        //????????????????????????????????????
        if (responseDTO != null && responseDTO.getBdcSlxxInitDTO() != null
                && CollectionUtils.isNotEmpty(responseDTO.getBdcSlxxInitDTO().getBdcSlJbxxDOList())) {
            List<BdcSlJbxxDO> jbxxList = responseDTO.getBdcSlxxInitDTO().getBdcSlJbxxDOList();
            BdcSlJbxxDO jbxxDO = jbxxList.get(0);
            if (StringUtils.isNotBlank(jbxxDO.getGzlslid()) && StringUtils.isNotBlank(jbxxDO.getSlbh())) {
                String gzlslid = jbxxDO.getGzlslid();
                // ?????????????????? ????????????????????????????????????
                bdcSlFeignService.cshSjcl(gzlslid);
                // ????????????????????????
                fjclService.uploadAndSaveFjcl(gzlslid, fjclList);

                // ????????????????????????????????????
                if (CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
                    String bdcGzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                    //??????????????????????????????????????????
                    if (StringUtils.isNotBlank(bdcGzlslid) && !StringUtils.equals(bdcGzlslid, gzlslid)) {
                        // ?????????????????? ????????????????????????????????????
                        bdcSlFeignService.cshSjcl(bdcGzlslid);
                        // ????????????????????????
                        fjclService.uploadAndSaveFjcl(bdcGzlslid, fjclList);
                    }
                }
                //????????????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
                    autoTurnWorkflow(responseDTO.getBdcXmDOList().get(0).getGzlslid(), zdzf, "");
                }
                resultMap.put("slbh", jbxxDO.getSlbh());
                resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
                resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
            }
        } else if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            //???????????????????????????
            BdcXmDO bdcXmDO = responseDTO.getBdcXmDOList().get(0);
            // ?????????????????? ????????????????????????????????????
            bdcSlFeignService.cshSjcl(bdcXmDO.getGzlslid());
            // ????????????????????????
            fjclService.uploadAndSaveFjcl(bdcXmDO.getGzlslid(), fjclList);
            //????????????????????????????????????????????????
            autoTurnWorkflow(bdcXmDO.getGzlslid(), zdzf, "");
            resultMap.put("slbh", bdcXmDO.getSlbh());
            resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
            resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
        }
        return resultMap;
    }


    /**
     * @param responseDTO ??????????????????????????????????????????
     * @param fjclList    ??????????????????
     * @param zdzf        ??????????????????
     * @return {Map} ????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ??????????????????????????????????????? (??????????????????)  (????????????????????????????????????????????????????????????????????????????????????revertNtCjResponse????????????????????????????????????)
     */
    @Override
    public Map<String, Object> revertNtCjResponseAsync(WwsqCjBdcXmResponseDTO responseDTO, List<FjclDTO> fjclList, String zdzf) {
        // ????????????????????????
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

        //????????????????????????????????????
        if (responseDTO != null && responseDTO.getBdcSlxxInitDTO() != null
                && CollectionUtils.isNotEmpty(responseDTO.getBdcSlxxInitDTO().getBdcSlJbxxDOList())) {
            List<BdcSlJbxxDO> jbxxList = responseDTO.getBdcSlxxInitDTO().getBdcSlJbxxDOList();
            BdcSlJbxxDO jbxxDO = jbxxList.get(0);
            if (StringUtils.isNotBlank(jbxxDO.getGzlslid()) && StringUtils.isNotBlank(jbxxDO.getSlbh())) {
                String gzlslid = jbxxDO.getGzlslid();
                // ?????????????????? ????????????????????????????????????
                bdcSlFeignService.cshSjcl(gzlslid);
                // ??????????????????
                fjThreadPool.execute(() -> this.downloadFjclAsync(gzlslid, fjclList));

                // ????????????????????????????????????
                if (CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
                    String bdcGzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                    //??????????????????????????????????????????
                    if (StringUtils.isNotBlank(bdcGzlslid) && !StringUtils.equals(bdcGzlslid, gzlslid)) {
                        // ?????????????????? ????????????????????????????????????
                        bdcSlFeignService.cshSjcl(bdcGzlslid);
                        // ??????????????????
                        fjThreadPool.execute(() -> this.downloadFjclAsync(bdcGzlslid, fjclList));
                    }
                }
                //????????????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
                    autoTurnWorkflow(responseDTO.getBdcXmDOList().get(0).getGzlslid(), zdzf, "");
                }
                resultMap.put("slbh", jbxxDO.getSlbh());
                resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
                resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
            }
        } else if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            //???????????????????????????
            BdcXmDO bdcXmDO = responseDTO.getBdcXmDOList().get(0);
            // ?????????????????? ????????????????????????????????????
            bdcSlFeignService.cshSjcl(bdcXmDO.getGzlslid());
            // ??????????????????
            fjThreadPool.execute(() -> this.downloadFjclAsync(bdcXmDO.getGzlslid(), fjclList));

            //????????????????????????????????????????????????
            autoTurnWorkflow(bdcXmDO.getGzlslid(), zdzf, "");
            resultMap.put("slbh", bdcXmDO.getSlbh());
            resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
            resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
        }

        return resultMap;
    }

    /**
     * @param gzlslid  ???????????????ID
     * @param fjclList ??????????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ??????????????????????????????
     */
    private void downloadFjclAsync(String gzlslid, List<FjclDTO> fjclList) {
        LOGGER.info("???????????????+????????????3.0??????????????????????????? ?????????????????????????????????ID???{}???????????????????????????{}", gzlslid, CollectionUtils.isEmpty(fjclList) ? 0 : fjclList.size());

        // ????????????????????????????????? bdcSlFeignService.cshSjcl() ????????????????????????????????????
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????

        // ????????????????????????
        fjclService.uploadAndSaveFjcl(gzlslid, fjclList);
    }

    /**
     * @param slbh
     * @param fjclList
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description ?????????????????????????????????????????????
     */
    @Override
    public Map<String, Object> cshfjxx(String slbh, List<FjclDTO> fjclList) {
        // ????????????????????????
        Map<String, Object> resultMap = new HashMap<>();

        if (StringUtils.isEmpty(slbh) || CollectionUtils.isEmpty(fjclList)) {
            resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
            resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
            return resultMap;
        }

        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
        LOGGER.info("---?????????gzlslid:{},????????????:{}", gzlslid, slbh);
        if (StringUtils.isEmpty(gzlslid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh, "");

            if (Objects.isNull(bdcSlJbxxDO)) {
                resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
                return resultMap;
            }

            gzlslid = bdcSlJbxxDO.getGzlslid();
            LOGGER.info("---?????????gzlslid:{},????????????:{}", gzlslid, slbh);
        }
        //50048 ??????????????????????????????????????????????????????:????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (CommonConstantUtils.SYSTEM_VERSION_NT.equals(dataversion)) {
            LOGGER.info("???????????????????????????????????????");
            List<TaskData> processRunningTasks = processTaskClient.processRunningTasks(gzlslid);
            if (CollectionUtils.isNotEmpty(processRunningTasks)) {
                String taskName = processRunningTasks.get(0).getTaskName();
                LOGGER.info("????????????????????????????????????????????????{}??????????????????{}", taskName, processRunningTasks.get(0).getTaskAssName());
                if (fjxxSljdmc.equals(taskName) || (fjxxShjdmc.equals(taskName) && StringUtils.isBlank(processRunningTasks.get(0).getTaskAssName()))) {
                    // ???????????????????????? ???????????????????????????????????????
                    bdcSlSjclFeignService.deleteAllSjcl(gzlslid);
                    // ????????????????????????
                    fjclService.uploadAndSaveFjcl(gzlslid, fjclList);
                    resultMap.put("msg", ResponseCodeEnum.SUCCESS.msg);
                    resultMap.put("code", ResponseCodeEnum.SUCCESS.code);
                    if (MapUtils.isEmpty(resultMap)) {
                        resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                        resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
                    }
                } else {
                    resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                    resultMap.put("code", "?????????????????????????????????????????????????????????");
                }

            } else {
                LOGGER.info("???????????????????????????????????????????????????gzlslid:{}", gzlslid);
            }
        } else {
            // ???????????????????????? ???????????????????????????????????????
            bdcSlSjclFeignService.deleteAllSjcl(gzlslid);
            // ????????????????????????
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
     * @description ????????????
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
            LOGGER.error("???????????????????????????????????????????????????{}", e.getMessage(), e);
        }
    }

    /**
     * @param responseDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????
     */
    @Override
    public void zdbj(WwsqCjBdcXmResponseDTO responseDTO, String slr) {
        zdbjWithCanBj(responseDTO, slr, canZdbj);
    }

    @Override
    public void zdbjWithCanBj(WwsqCjBdcXmResponseDTO responseDTO, String slr, boolean canbj) {
//        if (canbj) {
        //?????????????????????????????????????????????
        LOGGER.info("---??????????????????:{},slr:{},canbj:{}", JSONObject.toJSONString(responseDTO), slr, canbj);
        if (null != responseDTO.getSfzbbj() && responseDTO.getSfzbbj()) {
            LOGGER.info("????????????????????????{}", responseDTO.toString());
            List<BdcXmDO> bdcXmDOList = responseDTO.getBdcXmDOList();
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                LOGGER.info("??????????????????slr:{}", slr);
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                if (StringUtils.isNotBlank(slr)
                        && StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                LOGGER.info("???????????????????????????gzlslid:{} ,slr:{}", bdcXmDO.getGzlslid(), slr);
                                taskHandleClient.autoComplete(bdcXmDO.getGzlslid(), slr);
                                //??????????????????????????????????????????????????????????????????????????????
                                if (!CommonConstantUtils.SYSTEM_VERSION_HF.equals(dataversion)) {
                                    // ?????????????????????????????? ????????????????????????
                                    LOGGER.info("?????????????????????????????????gzlslid:{}", bdcXmDO.getGzlslid());
                                    bdcShxxRestService.generateShxxOfProInsId(bdcXmDO.getGzlslid());

                                } else {
                                    // ?????? ????????????????????????
                                    if (CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx())){
                                        bdcFyService.scCjfhzdPdf(bdcXmDO.getGzlslid());
                                    }
                                }
                            } catch (Exception e) {
                                LOGGER.error("?????????????????????gzlslid:{} ,slr:{}", bdcXmDO.getGzlslid(), slr, e);
                            }
                        }
                    }.start();
                }
            }
        }
    }

    @Override
    public void zdbjAndUpdateZsyzh(WwsqCjBdcXmResponseDTO responseDTO, String slr, List<JSONObject> zslist) {
        //????????????
        zdbjWithCanBj(responseDTO, slr, true);
        //???????????????
        if (CollectionUtils.isNotEmpty(zslist)) {
            for (JSONObject obj : zslist) {
                if (StringUtils.isNoneBlank(MapUtils.getString(obj, "qzysxlh"), MapUtils.getString(obj, "zsid"))) {
                    LOGGER.info("?????????+??????????????????????????????{}?????????id???{}", MapUtils.getString(obj, "qzysxlh"), MapUtils.getString(obj, "zsid"));
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
                        LOGGER.info("?????????+??????????????????bdcYzhSyqkQO???{}", JSONObject.toJSONString(bdcYzhSyqkQO));
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
     * @description ????????????????????????CQZH ??????
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
     * @description ??????DTO
     */
    @Override
    public WwsqCjBdcXmRequestDTO initZhlcRevertDTO(InitZyDyRequestDTO request) {
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        // ?????????????????? ???????????? ???????????????????????????
        if (StringUtils.equals("false", slRoleCode)) {
            wwsqCjBdcXmRequestDTO.setSlRoleCode("");
        } else {
            wwsqCjBdcXmRequestDTO.setSlRoleCode("wwsqsl");

        }
        if (request.getData() != null) {
            // ??????????????????????????????
            wwsqCjBdcXmRequestDTO.setGzyz(true);
            // ??????????????????
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
                // ??????????????????
                BdcSlYjxxDO bdcSlYjxxDO = new BdcSlYjxxDO();
                exchangeDozerMapper.map(data.getWlxx().get(0), bdcSlYjxxDO, "wwsq_zhlc_slyjxx");
                bdcSlxxDTO.setBdcSlYjxxDO(bdcSlYjxxDO);
            }
            List<BdcSlXmDTO> xmDTOList = new ArrayList<>();
            bdcSlxxDTO.setBdcSlXmList(xmDTOList);

            if (CollectionUtils.isNotEmpty(data.getBdcdyxx())) {
                for (InitZyDyRequestBdcdyxx bdcdyxx : data.getBdcdyxx()) {
                    //????????????????????????????????????????????????
                    if (CollectionUtils.isNotEmpty(bdcdyxx.getFjxx())) {
                        bdcSlxxDTO.setHbfjxx(bdcdyxx.getFjxx());
                    }
                    // ????????????
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

                            //??????????????????
                            if (CollectionUtils.isNotEmpty(cqxx.getGzxx())) {
                                BdcGzdjDO bdcGzdjDO = new BdcGzdjDO();
                                exchangeDozerMapper.map(cqxx.getGzxx().get(0), bdcGzdjDO, "wwsq_zhlc_cqxx_gzxx");
                                bdcSlXmDTO.setBdcGzdjDO(bdcGzdjDO);

                            }

                            //??????????????????
                            if (StringUtils.isNotBlank(cqxx.getSwzt())) {
                                List<BdcSwxxDTO> bdcSwxxDTOList = new ArrayList<>();
                                BdcSwxxDTO swxxDTO = new BdcSwxxDTO();
                                BdcSlHsxxDO slHsxxDO = new BdcSlHsxxDO();
                                exchangeDozerMapper.map(cqxx, slHsxxDO, "wwsq_zhlc_cqxx_swzt");
                                swxxDTO.setBdcSlHsxxDO(slHsxxDO);
                                bdcSwxxDTOList.add(swxxDTO);
                                bdcSlXmDTO.setBdcSwxxDTOList(bdcSwxxDTOList);
                            }
                            //??????????????????????????????????????????????????????????????????????????????
                            if (CollectionUtils.isNotEmpty(cqxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(cqxx.getFjxx());
                            }

                            // ????????????????????????
                            if (StringUtils.isNotBlank(bdcdyxx.getYxmid())) {
                                initBdcXmLsgx(bdcdyxx.getYxmid(), bdcSlXmDTO);
                            } else if (StringUtils.isNoneBlank(cqxx.getRoomid())) {
                                //???????????????????????????????????????roomid??????
                                Object bean = Container.getBean(ExchangeInterfaceRestController.class);
                                if (bean != null) {
                                    ExchangeInterfaceRestController restController = (ExchangeInterfaceRestController) bean;
                                    //??????????????????????????????
                                    JSONObject object = new JSONObject();
                                    object.put("roomid", cqxx.getRoomid());
                                    Object result = restController.requestInterface("initParam", object);
                                    if (result != null) {
                                        initBdcXmLsgx((String) result, bdcSlXmDTO);
                                    }
                                }
                            }

                            // ???????????????
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
                            //??????????????? ????????????
                            if (CollectionUtils.isNotEmpty(cqxx.getWlxx())) {
                                bdcSlXmDTO.setBdcSlLzrDOList(new ArrayList<>());
                                for (InitSjrxx lzr : cqxx.getWlxx()) {
                                    BdcSlLzrDO lzrDO = new BdcSlLzrDO();
                                    exchangeDozerMapper.map(lzr, lzrDO, "wwsq_zhlc_lzrxx");
                                    if (CheckParameter.checkAnyParameter(lzrDO)) {
                                        bdcSlXmDTO.getBdcSlLzrDOList().add(lzrDO);
                                    }
                                }
                                // ??????????????????
                                BdcSlYjxxDO bdcSlYjxxDO = new BdcSlYjxxDO();
                                exchangeDozerMapper.map(cqxx.getWlxx().get(0), bdcSlYjxxDO, "wwsq_zhlc_slyjxx");
                                bdcSlXmDTO.setBdcSlYjxxDO(bdcSlYjxxDO);
                            }
                            //??????????????? ?????????????????????????????????
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

                            //??????????????????
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

                            //????????????
                            if(cqxx.getGhxx() != null&& CheckParameter.checkAnyParameter(cqxx.getGhxx())){
                                BdcSdqghDTO bdcSdqghDTO = new BdcSdqghDTO();
                                exchangeDozerMapper.map(cqxx.getGhxx(), bdcSdqghDTO, "wwsq_zhlc_ghxx");
                                bdcSlxxDTO.setBdcSdqghDTO(bdcSdqghDTO);
                            }
                            xmDTOList.add(bdcSlXmDTO);
                        }
                    }


                    // ????????????
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
                                //?????????????????????????????????????????????????????????
                                exchangeDozerMapper.map(dyxx.getQlrxx().get(0), bdcSlXmDO, "wwsq_zhlc_bdcdyxx_cqxx_qlrxx_djxl");
                            }

                            //??????????????????????????????????????????????????????,?????????????????????????????????????????????
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
                            //??????????????????????????????????????????????????????????????????????????????
                            if (CollectionUtils.isNotEmpty(dyxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(dyxx.getFjxx());
                            }

                            // ???????????????????????? ,????????????????????????????????????????????????
                            if (StringUtils.isNotBlank(bdcdyxx.getYxmid()) && zhdkGzldyidList.contains(bdcSlJbxxDO.getGzldyid())) {
                                LOGGER.warn("?????????????????????????????????????????????");
                                initBdcXmLsgx(bdcdyxx.getYxmid(), bdcSlXmDTO);
                            }

                            // ???????????????
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
                            //???????????????
                            if (CollectionUtils.isNotEmpty(dyxx.getWlxx())) {
                                bdcSlXmDTO.setBdcSlLzrDOList(new ArrayList<>());
                                for (InitSjrxx lzr : dyxx.getWlxx()) {
                                    BdcSlLzrDO lzrDO = new BdcSlLzrDO();
                                    exchangeDozerMapper.map(lzr, lzrDO, "wwsq_zhlc_lzrxx");
                                    if (CheckParameter.checkAnyParameter(lzrDO)) {
                                        bdcSlXmDTO.getBdcSlLzrDOList().add(lzrDO);
                                    }
                                }
                                // ??????????????????
                                BdcSlYjxxDO bdcSlYjxxDO = new BdcSlYjxxDO();
                                exchangeDozerMapper.map(dyxx.getWlxx().get(0), bdcSlYjxxDO, "wwsq_zhlc_slyjxx");
                                bdcSlXmDTO.setBdcSlYjxxDO(bdcSlYjxxDO);
                            }
                            //??????????????? ?????????????????????????????????????????????????????????????????????
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
                            //?????????
                            List<BdcDsQlrDO> bdcDsQlrDOList = new ArrayList<>();
                            if (CollectionUtils.isNotEmpty(dyxx.getZwrxx())) {
                                for (InitZwrXxDTO initZwrXxDTO : dyxx.getZwrxx()) {
                                    BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
                                    exchangeDozerMapper.map(initZwrXxDTO, bdcDsQlrDO, "wwsq_zhlc_zwrxx");
                                    bdcDsQlrDOList.add(bdcDsQlrDO);
                                }
                                bdcSlXmDTO.setBdcDsQlrDOList(bdcDsQlrDOList);
                            }

                            //??????????????????
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
                            //??????????????????????????????????????????????????????????????????????????????
                            if (CollectionUtils.isNotEmpty(cfxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(cfxx.getFjxx());
                            }
                            // ???????????????
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
                    //????????????
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

                            //??????????????????????????????????????????????????????????????????????????????
                            if (CollectionUtils.isNotEmpty(dyixx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(dyixx.getFjxx());
                            }
                            // ???????????????
                            // ???????????????
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
                    //???????????????
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

                            //??????????????????????????????????????????????????????????????????????????????
                            if (CollectionUtils.isNotEmpty(tdsyqxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(tdsyqxx.getFjxx());
                            }
                            // ???????????????
                            // ???????????????
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
                    //???????????????
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

                            //??????????????????????????????????????????????????????????????????????????????
                            if (CollectionUtils.isNotEmpty(gzwxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(gzwxx.getFjxx());
                            }
                            // ???????????????
                            // ???????????????
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
                    //????????????
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

                            //??????????????????????????????????????????????????????????????????????????????
                            if (CollectionUtils.isNotEmpty(yyxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(yyxx.getFjxx());
                            }
                            // ???????????????
                            // ???????????????
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
                    //????????????
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

                            //??????????????????????????????????????????????????????????????????????????????
                            if (CollectionUtils.isNotEmpty(lqxx.getFjxx())) {
                                bdcSlXmDTO.setFjxx(lqxx.getFjxx());
                            }
                            // ???????????????
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
     * @description ???????????????????????????????????????????????????
     */
    @Override
    public List<GetYgxxResponseData> dealGetWwsqYgxxResponse(GrdacxModel model) {
        List<GetYgxxResponseData> ygxxResponseDataList = new ArrayList<>();

        if (model != null && CollectionUtils.isNotEmpty(model.getData())) {
            for (GrdacxData data : model.getData()) {
                List<YgQlWithXmQlrDTO> ygList = data.getYgList();
                // ?????? ?????????????????????  ??? ??????????????????????????????
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
//                                    if (StringUtils.equals("??????", ycfDataTemp.getFwzt())) {
//                                        data.setHasCf(true);
//                                        break ycfDatafor;
//                                    }
//                                }
//                            }
//                        } catch (Exception e) {
//                            LOGGER.error("???????????????????????????????????????????????????", e);
//                        }
//                    }
                }
                /*
                ??????????????????????????????????????????????????????
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
                    //????????????bdcdyh??????????????????????????????????????????dozer???????????????
                    for (YgQlWithXmQlrDTO ygQlWithXmQlrDTO : ygList) {
                        GetYgxxResponseData getYgxxResponseData = new GetYgxxResponseData();
                        String bdcdyh = ygQlWithXmQlrDTO.getBdcXmDO().getBdcdyh();
                        QueryQlRequestDTO requestDTO = new QueryQlRequestDTO();
                        requestDTO.setBdcdyh(bdcdyh);
                        //??????????????????????????????
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
     * @description ???????????? PDF ?????? SFXX
     */
    @Override
    public List<Map> sfxxPdf(SfdPdfRequestDTO requestDTO) {
        List<Map> resultList = new ArrayList<>();
        String spxtywh = requestDTO.getSpxtywh();
        List<BdcXmDO> bdcXmList = commonService.listBdcXmBySpxtywh(spxtywh);
        if (CollectionUtils.isEmpty(bdcXmList)) {
            throw new AppException("????????????????????????" + requestDTO.getSpxtywh() + "??????????????????????????????????????????????????????");
        }
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(bdcXmList.get(0).getGzlslid());
        // ??????????????????
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
     * @description ?????? ???????????????????????????
     */
    @Override
    public Page<Map> querySczmdByPage(Pageable pageable, Map<String, Object> paramMap) {
        // ???????????????ID ??????
        String[] gzldyidArr = StringUtils.split(sczmdGzldyid, CommonConstantUtils.ZF_YW_DH);
        paramMap.put("gzldyidList", Arrays.asList(gzldyidArr));
        return repository.selectPaging("querySczmdByPageOrder", paramMap, pageable);
    }

    @Override
    public Page<Map> queryDyxxAndYgdyxxByPage(Pageable pageable, Map<String, Object> paramMap) {
        LOGGER.info("??????querDyAndYdyByPageOrder???????????????{}", JSONObject.toJSONString(paramMap));
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

            // ????????????????????????????????????????????????????????????
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

            // ????????????
            Map<String, List<BdcFdcqDTO>> fdcqListMap = null;
            if (CollectionUtils.isNotEmpty(fdcqList)) {
                fdcqListMap = fdcqList.stream().collect(Collectors.groupingBy(BdcFdcqDTO::getBdcdyh));
            }

            Map<String, List<BdcJsydsyqDTO>> jsydsyqListMap = null;
            if (CollectionUtils.isNotEmpty(jsydsyqList)) {
                jsydsyqListMap = jsydsyqList.stream().collect(Collectors.groupingBy(BdcJsydsyqDTO::getBdcdyh));
            }

            // ????????????????????????????????????????????????????????????
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
            LOGGER.error("????????????dyxxAndYgdyxx??????????????????", e);
        }
        return dyxxAndYgdyxxPage;
    }

    @Override
    public Page<Map> queryDyxxYgdyxxForBdcdyhByPage(Pageable pageable, Map<String, Object> paramMap) {
        LOGGER.info("queryDyxxYgdyxxForBdcdyhByPage???{}", JSONObject.toJSONString(paramMap));
        Page<Map> mapPage = repository.selectPaging("queryDyxxYgdyxxForBdcdyhByPage", paramMap, pageable);
        if (null != mapPage && CollectionUtils.isNotEmpty(mapPage.getContent())) {
            for (Map data : mapPage.getContent()) {
                //????????????bdcdyh??????????????????????????????
                if (Objects.nonNull(data.get("BDCDYH"))) {
                    Map queryMap = new HashMap(2);
                    queryMap.put("bdcdyh", data.get("BDCDYH"));
                    queryMap.put("qllxList",xmcqQllx);
                    List<BdcXmDO> xmDOList = repository.selectList("queryXmByBdcdyh", queryMap);
                    if (CollectionUtils.isNotEmpty(xmDOList) && xmDOList.size() > 1) {
                        LOGGER.info("??????????????????????????????!bdcdyh??????{}",data.get("BDCDYH"));
                        throw new AppException("???????????????????????????????????????????????????");
                    }
                }else {
                    throw new AppException("????????????bdcdyh?????????????????????");
                }
            }
        }
        return mapPage;
    }


    @Override
    public Page<Map> queryDyxxOrYgdyxxByPage(Pageable pageable, Map<String, Object> paramMap) {
        LOGGER.info("??????queryDyxxOrYgdyxxByPage???????????????{}", JSONObject.toJSONString(paramMap));
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
     * @description ?????? ????????? ???????????? ???????????????  ????????????
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
     * @description ??????BDCDYH??????FWXX???????????????????????????????????????????????????????????????
     */
    @Override
    public FwxxbybdcdyhResponseDTO setZtTofwxxByBdcdyhResponse(FwxxbybdcdyhResponseDTO responseDTO) {
        if (StringUtils.isNotBlank(responseDTO.getBdcdyh())) {
            String bdcdyh = responseDTO.getBdcdyh();
            // ??????????????????
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
            // ?????? ????????????
            List<DyQlWithXmQlrDTO> dyaqList = commonService.listDyaqByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(dyaqList)) {
                responseDTO.setSfdy(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfdy(CommonConstantUtils.SF_F_DM + "");
            }

            // ?????? ????????????
            List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(cfList)) {
                responseDTO.setSfcf(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfcf(CommonConstantUtils.SF_F_DM + "");
            }

            // ??????????????????
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
     * @description ???????????????????????????????????????????????? ????????????????????????
     */
    @Override
    public YchsByYsfwbmResponseDTO setZtToYchsByYsfwbmResponse(YchsByYsfwbmResponseDTO responseDTO) {
        if (StringUtils.isNotBlank(responseDTO.getBdcdyh())) {
            String bdcdyh = responseDTO.getBdcdyh();
            QueryQlRequestDTO xzqlReq = new QueryQlRequestDTO();
            xzqlReq.setBdcdyh(bdcdyh);
            // ?????? ????????????
            List<DyQlWithXmQlrDTO> dyaqList = commonService.listDyaqByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(dyaqList)) {
                responseDTO.setSfdy(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfdy(CommonConstantUtils.SF_F_DM + "");
            }

            // ?????? ????????????
            List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(cfList)) {
                responseDTO.setSfcf(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfcf(CommonConstantUtils.SF_F_DM + "");
            }

            // ??????????????????
            List<YyQlWithXmQlrDTO> yyList = commonService.listYyByBdcdyh(xzqlReq);
            if (CollectionUtils.isNotEmpty(yyList)) {
                responseDTO.setSfyy(CommonConstantUtils.SF_S_DM + "");
            } else {
                responseDTO.setSfyy(CommonConstantUtils.SF_F_DM + "");
            }
            if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_HF, dataversion)) {
                // 03-31 ?????????????????????
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
     * @description ??????BDCDYH?????????????????????XMID
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
     * @description ???????????? ???????????????
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
        //??????????????????,??????????????????
        if (StringUtils.equals(CommonConstantUtils.SF_S_DM.toString(), sfyj)) {
            bdcSlSysxxDOList = Collections.emptyList();
        } else {
            // ?????????SLBH ?????? ??????????????? ??? ?????????
            bdcSlSysxxDOList = bdcSwFeignService.listSysxxBySlbh(slbh);
        }
        JSONObject resultJson = new JSONObject();
        if (CollectionUtils.isNotEmpty(bdcSlSysxxDOList)) {
            // ???????????? ????????????
            List<BdcSlSysxxDO> sysxxDOList = new ArrayList<>();
            //???????????????????????????????????????
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
            //???????????????????????????fwuuid
            BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh, "");
            if (Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getFwuuid())) {
                fwuuid = bdcSlJbxxDO.getFwuuid();
            } else {
                LOGGER.error("????????????fwuuid?????????????????????????????????????????????????????????{}", slbh);
            }
        }
        Map<String, Object> slbhMap = new HashMap<>();
        slbhMap.put("slbh", slbh);
        slbhMap.put("fwuuid", fwuuid);
        slbhMap.put("tdbz", getTdbzBySlbh(slbh));
        //?????????????????????
        String beanid = "swsys_dk";
        if (StringUtils.equals(swsysVersion, "yt")) {
            beanid = "swsys";
        }
        Object sysResult = exchangeBeanRequestService.request(beanid, JSONObject.parseObject(JSONObject.toJSONString(slbhMap)));
        if (sysResult == null) {
            LOGGER.error("???????????????????????????????????????");
        } else {
            // ???sysResult ????????? ?????????
            JSONObject sysxxJsonObject = new JSONObject();
            sysxxJsonObject.put("slbh", slbh);
            sysxxJsonObject.put("sysxx", sysResult);
            LOGGER.info("????????????????????????????????????{}", sysxxJsonObject.toString());
            exchangeBeanRequestService.request("saveSwsys", sysxxJsonObject);

            // ?????????????????????
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
     * @description ????????????????????????
     */
    @Override
    public List<GxRygfqkDO> listGxRygfqk(RygfqkRequestDTO rygfqkRequestDTO) {
        if (rygfqkRequestDTO != null && CheckParameter.checkAnyParameter(rygfqkRequestDTO)) {
            List<GxRygfqkDO> list = rygfqkRequestDTO.getRyxx();
            if (CollectionUtils.isEmpty(list)) {
                throw new AppException("????????????????????????");
            }
            for (GxRygfqkDO gxRygfqkDO : list) {
                if (StringUtils.isBlank(gxRygfqkDO.getZjh())) {
                    throw new AppException("???????????????????????????");
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
        // ??????pdf?????????
        String pdfFileName = UUIDGenerator.generate16() + ".pdf";
        FileOutputStream fileOutStream = null;
        FileInputStream pdfInputStream = null;
        String ftlName = dylx + ".ftl";
        byte[] data = null;
        try {
            LOGGER.info("??????pdf??????????????????pdf?????????{}", pdfFileName);
            fileOutStream = new FileOutputStream(new File(pdfFileName));
            String html = htmlToPdf.getHtmlString(getDataList(xmlData), ftlName);
            htmlToPdf.createPDF(fileOutStream, html);
            // ??????pdf??????
            pdfInputStream = new FileInputStream(pdfFileName);
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = pdfInputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
            String base64 = Base64Utils.encode(data);
            LOGGER.info("??????pdf??????base64????????????");
            return base64;
        } catch (Exception e) {
            LOGGER.error("????????????PDF?????????{}", e.getMessage());
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
     * ??????mapid????????????????????????
     *
     * @param xmid
     * @param qlrlb
     * @param mapId
     * @return ????????????
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
     * ??????mapid????????????????????????
     *
     * @param xmid
     * @param qlrlb
     * @param mapId
     * @return ????????????
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
     * ????????????????????????
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
        // ???????????????<datas>??????
        List<Element> datasList = element.elements("datas");
        if (CollectionUtils.isNotEmpty(datasList) && null != datasList.get(0)) {
            // ???????????????<data>??????
            List<Element> dataList = datasList.get(0).elements("data");
            if (CollectionUtils.isNotEmpty(dataList)) {
                for (Element data : dataList) {
                    String name = data.attribute("name").getValue();
                    result.put(name, data.getText());
                }
            }
        }
        // ???????????????<detail>??????
        List<Element> detalList = element.elements("detail");
        if (CollectionUtils.isNotEmpty(detalList) && detalList.get(0) != null) {
            List<Element> rowList = detalList.get(0).elements("row");
            if (CollectionUtils.isNotEmpty(rowList)) {
                List<Map> list = new ArrayList<>();
                result.put("recordList", list);
                for (Element ele : rowList) {
                    // ???????????????<data>??????
                    List<Element> dataList = ele.elements("data");
                    if (CollectionUtils.isNotEmpty(dataList)) {
                        // ??????
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
     * @description ???????????????????????????????????????????????????
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
                    // ??????bdcdyh??????
                    return o1.getBdcdyh().compareTo(o2.getBdcdyh());
                }
            });
            set.addAll(zfxxDTOList);
            return new ArrayList<>(set);
        }
        return new ArrayList<>();
    }


    /**
     * ???????????????????????????????????????????????????????????????????????????????????????
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<BdcZsDO> queryZsxx(JSONObject paramMap) {
        if (StringUtils.isBlank(MapUtils.getString(paramMap, "slbh"))) {
            throw new MissingArgumentException("?????????????????????????????????");
        }
        return wwsqMapper.listBdcZs(paramMap);
    }


    /**
     * @param paramJob
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description ????????????xmid???????????????????????????
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
     * ??????-?????????+??????  ??????????????????
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
        //57417 ?????????????????????3.0 tsWwsqFjxx????????????    ????????????????????????2?????????????????????
        if (StringUtils.isBlank(spxtywh)) {
            spxtywh = data.getString("proid");
        }
        List<BdcDsfSjclDTO> sjclDTOList = JSONObject.parseArray(data.getString("fjxx").toString(), BdcDsfSjclDTO.class);
        String gzlslid = null;
        if (StringUtils.isNotBlank(spxtywh)) {
            LOGGER.info("????????????????????????{}", spxtywh);
            List<BdcXmDO> xmDOList = commonService.listBdcXmBySpxtywh(spxtywh);
            if (CollectionUtils.isEmpty(xmDOList)) {
                throw new AppException("????????????????????????" + spxtywh + "??????????????????????????????????????????????????????");
            }
            gzlslid = xmDOList.get(0).getGzlslid();

        } else {
            LOGGER.info("???????????????{}", slbh);
            List<BdcXmDO> xmDOList = commonService.listBdcXmBySlbh(slbh);
            if (CollectionUtils.isEmpty(xmDOList)) {
                throw new AppException("???????????????" + slbh + "??????????????????????????????????????????????????????");
            }
            gzlslid = xmDOList.get(0).getGzlslid();
        }
        //????????????????????????????????????
        for (BdcDsfSjclDTO dsfSjclDTO : sjclDTOList) {
            if (StringUtils.isBlank(dsfSjclDTO.getClmc())) {
                throw new MissingArgumentException("???????????????????????????");
            }
        }
        try {
            bdcSlSjclFeignService.saveDsfSjcl(gzlslid, sjclDTOList);
            resultMap.put("msg", ResponseCodeEnum.SUCCESS_YW.msg);
            resultMap.put("code", ResponseCodeEnum.SUCCESS_YW.code);
            resultMap.put("spxtywh", spxtywh);

        } catch (IOException e) {
            LOGGER.error("?????????????????????spxtywh??????{}??? gzlsid???{}", spxtywh, gzlslid);
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
        // ???????????????????????????????????? ????????????????????????????????????
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
            // ??????????????????
            QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
            queryQlRequestDTO.setBdcdyh(bdcFwqlDTO.getBdcdyh());
            queryQlRequestDTO.setWithXm(true);
            Map<String, Object> xzztMap = getXzzt(queryQlRequestDTO, true);
            if (xzztMap != null) {
                fwqlCxResponseQl.setXzzt(MapUtils.getString(xzztMap, "xzzt"));
                // ????????????
                cfxxlist = (List<FwqlCxResponseCfxx>) MapUtils.getObject(xzztMap, "cfxxList");
                // ????????????
                dyaqxxlist = (List<FwqlCxResponseDyaqxx>) MapUtils.getObject(xzztMap, "dyaqxxList");
            }
            // ????????????
            sdxxlist = getSdxx(bdcFwqlDTO.getBdcdyh(), bdcFwqlDTO.getXmid());
        }
        if (CollectionUtils.isNotEmpty(cfxxlist)) {
            sb.append("??????");
        }
        if (CollectionUtils.isNotEmpty(dyaqxxlist)) {
            if (StringUtils.isNotBlank(sb.toString())) {
                sb.append("???");
            }
            sb.append("??????");
        }
        if (CollectionUtils.isNotEmpty(sdxxlist)) {
            if (StringUtils.isNotBlank(sb.toString())) {
                sb.append("???");
            }
            sb.append("??????");
        }
        return sb.toString();
    }

    /**
     * ????????????????????????
     *
     * @param queryQlRequestDTO
     * @param xzxx              ????????????????????????????????????
     * @return
     */
    private Map<String, Object> getXzzt(QueryQlRequestDTO queryQlRequestDTO, boolean xzxx) {
        Map<String, Object> resultMap = new HashMap<>();
        String xzzt = "";
        List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(cfList)) {
            xzzt += "??????";
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
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfqssj()) + "???";
                    }
                    if (dto.getBdcql().getCfjssj() != null) {
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfjssj()) + "???";
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
                xzzt += "??????";
            } else {
                xzzt += "/??????";
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
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxqssj()) + "???";
                }
                if (dto.getBdcql().getZwlxjssj() != null) {
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxjssj()) + "???";
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
     * ??????????????????  ??????????????????????????????????????????
     *
     * @param bdcdyh
     * @param xmid
     * @return
     */
    private List<FwqlCxResponseSdxx> getSdxx(String bdcdyh, String xmid) {
        List<FwqlCxResponseSdxx> responseSdxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            // ????????????
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
            // ????????????
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
     * ??????????????????
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
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjksrq()) + "???";
                }
                if (dto.getBdcql().getYydjjsrq() != null) {
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjjsrq()) + "???";
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
     * ??????????????????
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
     * @description ??????bdcdyh??????????????????
     */
    @Override
    public Map<String, Object> bdcdycj(BdcCshSlxmDTO bdcCshSlxmDTO) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("gzlslid", "");
        dataMap.put("ywslbh", "");
        String msg = "success";
        try {
            //????????????????????????
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
            msg = "??????????????????";
            LOGGER.error("????????????ID:{}??????bdcdyh????????????????????????????????????:{}", bdcCshSlxmDTO.getJbxxid(), e);
        }
        if (StringUtils.equals("success", msg)) {
            try {
                //?????????????????????
                bdcSlXmFeignService.cshYxxm(bdcCshSlxmDTO.getCzrid(), bdcCshSlxmDTO);
                BdcSlCshDTO bdcSlCshDTO = new BdcSlCshDTO();
                BeanUtil.copyBean(bdcCshSlxmDTO, bdcSlCshDTO);
                //???????????????
                Map<String, String> cjResult = bdcSlFeignService.cshCjBdcXm(bdcSlCshDTO);
                if (StringUtils.isNotBlank(cjResult.get("processInsId"))) {
                    dataMap.put("gzlslid", cjResult.get("processInsId"));
                    dataMap.put("ywslbh", cjResult.get("slbh"));
                } else {
                    msg = "????????????????????????";
                }
            } catch (Exception e) {

                LOGGER.error("????????????ID:{}??????bdcdyh????????????????????????:{}", bdcCshSlxmDTO.getJbxxid(), e);
                msg = ExceptionUtils.getFeignErrorMsg(e);
            }
        }
        dataMap.put("msg", msg);

        resultMap.put("data", dataMap);
        return resultMap;


    }

    @Override
    public WwsqCjBdcXmRequestDTO initCfjfdjParamDTO(InitCfjfdjRequestDTO initCfjfdjRequestDTO) {
        LOGGER.info("---????????????????????????:{}", JSONObject.toJSONString(initCfjfdjRequestDTO));
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        // ?????????????????? ???????????? ???????????????????????????
        wwsqCjBdcXmRequestDTO.setSlRoleCode("wwsqsl");

        List<InitCfjfdjRquestDataDTO> dataDTOS = initCfjfdjRequestDTO.getData();
        if (CollectionUtils.isEmpty(dataDTOS)) {
            return wwsqCjBdcXmRequestDTO;
        }
        if (Objects.isNull(dataDTOS.get(0))) {
            throw new AppException("??????????????????!");
        }
        if (StringUtils.isEmpty(dataDTOS.get(0).getSqdjlx())) {
            throw new AppException("sqdjlx????????????!");
        }
        // ??????????????????????????????
        wwsqCjBdcXmRequestDTO.setGzyz(true);
        dataDTOS.forEach(data -> {

            BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();

            /**
             * ???????????????????????????
             */
            BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
            exchangeDozerMapper.map(data, bdcSlJbxxDO, "court_cfjfdj_jbxx");

            bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);

            /**
             * ?????????????????????
             */
            List<BdcSlXmDTO> xmDTOList = new ArrayList<>();
            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
            exchangeDozerMapper.map(data, bdcSlXmDTO, "court_cfjfdj_slxm");
            exchangeDozerMapper.map(data.getCfxx(), bdcSlXmDTO, "court_cfjfdj_cfjfxm");
            xmDTOList.add(bdcSlXmDTO);

            bdcSlxxDTO.setBdcSlXmList(xmDTOList);

            wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);
        });
        LOGGER.info("---????????????????????????:{}", JSONObject.toJSONString(wwsqCjBdcXmRequestDTO));
        return wwsqCjBdcXmRequestDTO;
    }

    @Override
    public void zdbjOrzdzf(WwsqCjBdcXmResponseDTO responseDTO, String slr, String sfzdbj, String zdzf) {
        LOGGER.info("---???????????????????????????:{},slr:{},zdbj:{},zdzf:{}", responseDTO, slr, sfzdbj, zdzf);
        /**
         * ???37141?????????????????????????????????????????????slly": "6"??????????????????zdzf??????1?????????????????????????????????
         *
         * ????????????????????????????????????????????????????????????
         */
        if ("1".equals(sfzdbj)) {
            LOGGER.info("---??????????????????:{},???????????????id:{}", zdzf, responseDTO.getBdcXmDOList().get(0).getGzlslid());
            zdbjWithCanBj(responseDTO, slr, true);
        } else if (StringUtils.equals(Constants.WWSQ_CJXM_AUTOTURN, zdzf)) {
            if (CollectionUtils.isEmpty(responseDTO.getGzyzList())) {
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                LOGGER.info("---????????????:{},???????????????id:{},??????????????????{}", zdzf, gzlslid, slr);
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
     * @description Court ?????? ????????????????????????
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
                        return ExchangeDsfCommonResponse.fail("?????????????????????taskId??????", cfResponseDTo);
                    }
                    return ExchangeDsfCommonResponse.ok(cfResponseDTo);
                } else {
                    LOGGER.info("---??????????????????????????????,??????taskId??????:{},??????taskId?????????{}", responseMap, JSON.toJSONString(taskDataList));
                    return ExchangeDsfCommonResponse.fail("??????????????????????????????,??????taskId??????");
                }
            } else {
                LOGGER.info("---??????????????????:{}", responseMap);

                return ExchangeDsfCommonResponse.fail("0", (String) responseMap.get("msg"), new CourtCfResponseDTo());
            }
        } catch (Exception e) {
            LOGGER.error("---??????????????????:{}", e.getMessage());
            return ExchangeDsfCommonResponse.fail(e.getMessage());
        }
    }

    /**
     * @param wwsqCjBdcXmRequestDTO
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????????????????
     */
    @Override
    public Object acceptCjServiceForCourt(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception {
        try {
            return bdcSlFeignService.wwsqCjBdcXm(wwsqCjBdcXmRequestDTO);
        } catch (Exception e) {
            LOGGER.error("---??????????????????????????????:{}", e.getMessage());
            return ExchangeDsfCommonResponse.fail(e.getMessage());
        }
    }

    /**
     * ??????-?????????????????????????????????
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
                    //??????????????????-????????????-???????????????
                    List<FjclDTO> fjxx = fjxxRequest.getFjxx();
                    //??????fjurl???fjnr??????????????????????????????
                    boolean resultBt = this.cheakBt(fjxx);
                    if (Boolean.FALSE.equals(resultBt)) {
                        resultMap.put("msg", ResponseCodeEnum.PARAM_ERROR.msg);
                        resultMap.put("code", ResponseCodeEnum.PARAM_ERROR.code);
                        return resultMap;
                    }
                    //????????????????????????????????????base64?????????????????????????????????????????????
                    boolean result = this.cheakBase64(fjxx);
                    if (Boolean.FALSE.equals(result)) {
                        resultMap.put("msg", ResponseCodeEnum.PARAM_WRONG.msg);
                        resultMap.put("code", ResponseCodeEnum.PARAM_WRONG.code);
                        return resultMap;
                    }

                    // ????????????????????????
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
     * ?????????????????????????????????????????????base64????????????
     * @param fjxx ????????????
     * @Date 2021/6/22
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    private boolean cheakBase64(List<FjclDTO> fjxx) throws IOException {
        for (FjclDTO fjclDTO : fjxx) {
            List<FjclmxDTO> fjclmxDTOList = fjclDTO.getFjclmxDTOList();
            for (FjclmxDTO fjclmxDTO : fjclmxDTOList) {
                String fjnr = fjclmxDTO.getFjnr();//????????????????????????????????????????????????
                if (StringUtils.isBlank(fjnr)) { //??????fjnr??????,????????????
                    continue;
                }
                //???????????????base64??????
                if (Boolean.FALSE.equals(this.isBase64(fjnr))) {
                    return false;
                }
            }
        }
        return true;
    }

    /***
     * fjurl???fjnr???????????????????????????
     * @param fjxx ????????????
     * @Date 2021/6/22
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    private boolean cheakBt(List<FjclDTO> fjxx) {
        for (FjclDTO fjclDTO : fjxx) {
            List<FjclmxDTO> fjclmxDTOList = fjclDTO.getFjclmxDTOList();
            for (FjclmxDTO fjclmxDTO : fjclmxDTOList) {
                String fjnr = fjclmxDTO.getFjnr();
                String fjurl = fjclmxDTO.getFjurl();
                //fjurl???fjnr??????????????????????????????
                if (StringUtils.isBlank(fjnr) && StringUtils.isBlank(fjurl)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ???????????????base64??????
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
     * ????????????????????????
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
                //??????????????????????????????????????????fcxmid?????????????????????????????????????????????????????????????????????????????????????????????????????????
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
                            //??????????????????????????????
                            try {
                                bdcPpFeignService.pptd(fcxmid, tdxmid);
                                LOGGER.info("??????????????????????????????????????????????????????");
                            } catch (Exception e) {
                                LOGGER.info("??????????????????????????????????????????????????????");
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }

    }


    /**
     * ????????????????????????????????????
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
                // ????????????????????????
                String zxcqzh = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList().get(0).getBdcSlXm().getYbdcqz();
                if (StringUtils.isNotBlank(zxcqzh)) {
                    paramMap.put("ZXCQZH", zxcqzh);
                }
                // ????????????
                if (MapUtils.isNotEmpty(paramMap)) {
                    LOGGER.info("??????????????????????????????????????????,paramMap:{}", JSON.toJSONString(paramMap));
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                }
            } catch (Exception e) {
                LOGGER.error("????????????????????????,slbh:{}", responseDTO.getBdcXmDOList().get(0).getSlbh(), e);
            }

        } else {
            throw new AppException("????????????????????????");
        }
    }

    /**
     * @return cn.gtmap.realestate.exchange.core.dto.wwsq.ychsbyysfwbm.response.YchsByYsfwbmResponseDTO
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [queryParam]
     * @description ??????????????????
     */
    public YchsByYsfwbmResponseDTO ycfwxxCx(YcfwCxRequestDTO queryParam) {
        if (queryParam == null || StringToolUtils.isAllNullOrEmpty(queryParam.getZl(), queryParam.getRoomid())) {
            LOGGER.error("???????????????roomid??????????????????????????????????????????");
            throw new AppException("???????????????roomid??????????????????????????????????????????");
        }
        YchsByYsfwbmResponseDTO ychsByYsfwbmResponseDTOS = viewMapper.queryViewDvYgDO(queryParam);
        return ychsByYsfwbmResponseDTOS;
    }


    /**
     * @param createData @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description ??????DTO
     */
    @Override

    public WwsqCjBdcXmRequestDTO initCfParamDTO(CreateData createData) {
        LOGGER.info("---????????????????????????:{}", JSONObject.toJSONString(createData));
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        // ?????????????????? ???????????? ???????????????????????????
        wwsqCjBdcXmRequestDTO.setSlRoleCode("wwsqsl");

        CfRequestDTO dataDTOS = createData.getData();

        if (Objects.isNull(dataDTOS)) {
            return wwsqCjBdcXmRequestDTO;
        }

        if (StringUtils.isEmpty(dataDTOS.getSqdjlx())) {
            throw new AppException("sqdjlx????????????!");
        }
        // ??????????????????????????????
        wwsqCjBdcXmRequestDTO.setGzyz(true);

        List<CfCfxxRequestDTO> cfCfxxRequestDTOS = dataDTOS.getCfxx();
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        exchangeDozerMapper.map(dataDTOS, bdcSlJbxxDO, "court_cfjfdj_jbxx_v1");


        cfCfxxRequestDTOS.forEach(data -> {

            BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();

            /**
             * ???????????????????????????
             */
            bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);

            /**
             * ?????????????????????
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
        LOGGER.info("---????????????????????????:{}", JSONObject.toJSONString(wwsqCjBdcXmRequestDTO));
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
        LOGGER.info("?????????????????????????????????", requestBody.toString());
        if (StringUtils.isNotBlank(requestBody.getQlrmc())
                || StringUtils.isNotBlank(requestBody.getQlrmcmh())
                || StringUtils.isNotBlank(requestBody.getSlbh())
                || StringUtils.isNotBlank(requestBody.getLikeBdcdyh())) {
            DvYdy dvYdy = new DvYdy();

            List<DvYdy> dvYdyList = bdcXmMapper.queryYdyChangzhou(requestBody);
            List<GetYgxxResponseData> ygxxResponseData = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(dvYdyList)) {
                for (DvYdy ydy : dvYdyList) {
                    //????????????
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
     * @return json ????????????????????????
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description ????????????, ????????????????????????
     */
    @Override
    public JSONObject getZmfb(String slbh) throws DocumentException {
        if (cn.gtmap.realestate.common.util.StringUtil.isEmpty(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        // ????????????
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        //?????????slbh??????gzlslid
        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);

        Map<String, Object> map = new HashMap<>(1);
        map.put("gzlslid", gzlslid);
        bdcdyhListMap.add(map);
        paramMap.put("zmfbyl", bdcdyhListMap);

        //??????????????????xml????????????
        String xmlStr = bdcPrintFeignService.print(paramMap);
        //xml????????????
        JSONObject json = this.xmlParse(xmlStr);
        return json;
    }


    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description ???????????????
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
     * @description ????????????xml?????????????????????JSON??????
     */
    private JSONObject xmlParse(String xmlStr) throws DocumentException {
        Map<String, Object> zmfbMap = new HashMap<>();
        List<Map<String, String>> cqxx = new ArrayList<>();
        try {
            Document dc = DocumentHelper.parseText(xmlStr);
            //??????????????????
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
     * ?????????????????????????????????????????????????????????
     *
     * @param gzwxxcxQO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<GzwxxResponseDTO> queryGzwxx(Pageable pageable, GzwxxcxQO gzwxxcxQO) {
        if (!CheckParameter.checkAnyParameter(gzwxxcxQO)) {
            LOGGER.info("?????????????????????????????????gzwxxcxQO:{}", gzwxxcxQO.toString());
            throw new MissingArgumentException("?????????????????????????????????");
        }
        return acceptBdcdyFeignService.listWwsqGzwxxDTOByPage(pageable, JSON.toJSONString(gzwxxcxQO));

    }

    /**
     * ?????????????????????????????????
     *
     * @param gzwxxcxQO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<GzwsqyDTO> queryGzwsyq(Pageable pageable, GzwsyqcxQO gzwxxcxQO) {
        if (!CheckParameter.checkAnyParameter(gzwxxcxQO)) {
            LOGGER.info("?????????????????????????????????");
            throw new MissingArgumentException("?????????????????????????????????");
        }
        LOGGER.info(gzwxxcxQO.toString());

        Page<GzwsqyDTO> gzwxxPage = repository.selectPaging("querGzwsyqByPageOrder", gzwxxcxQO, pageable);
        if (CollectionUtils.isNotEmpty(gzwxxPage.getContent())) {
            for (GzwsqyDTO gzwsqyDTO : gzwxxPage.getContent()) {
                //??????xmid??????????????????????????????????????????
                gzwsqyDTO.setQlrlist(qlrsjConvertQlrBean(gzwsqyDTO.getXmid(), CommonConstantUtils.QLRLB_QLR, "CommonQlrxx"));
                //??????bdcdyh???????????????
                if (StringUtils.isNotBlank(gzwsqyDTO.getBdcdyh())) {
                    //????????????
                    String bdcdyh = gzwsqyDTO.getBdcdyh();
                    List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());

                    if (CollectionUtils.isNotEmpty(listDyaq)) {
                        gzwsqyDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        gzwsqyDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
                    List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    if (CollectionUtils.isNotEmpty(listCf)) {
                        gzwsqyDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        gzwsqyDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
                    List<BdcQl> listYy = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YY.toString());
                    if (CollectionUtils.isNotEmpty(listYy)) {
                        gzwsqyDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        gzwsqyDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????  ???????????? ???????????????
                    List<BdcQl> listSd = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    // ????????????
                    List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(gzwsqyDTO.getXmid(), CommonConstantUtils.SDZT_SD);
                    if (CollectionUtils.isNotEmpty(listSd) || CollectionUtils.isNotEmpty(zssdDOList)) {
                        gzwsqyDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        gzwsqyDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
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
     * ?????????????????????
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
            LOGGER.info("?????????????????????????????????");
            throw new MissingArgumentException("?????????????????????????????????");
        }
        LOGGER.info(gzwxxcxQO.toString());
        Page<TdsyqDTO> tdsyqDTOPage = repository.selectPaging("querTdsyqByPageOrder", gzwxxcxQO, pageable);
        if (CollectionUtils.isNotEmpty(tdsyqDTOPage.getContent())) {
            for (TdsyqDTO tdsyqDTO : tdsyqDTOPage.getContent()) {
                //??????xmid??????????????????????????????????????????
                tdsyqDTO.setQlrlist(qlrsjConvertQlrBean(tdsyqDTO.getXmid(), CommonConstantUtils.QLRLB_QLR, "CommonQlrxx"));
                //??????bdcdyh???????????????
                if (StringUtils.isNotBlank(tdsyqDTO.getBdcdyh())) {
                    //????????????
                    String bdcdyh = tdsyqDTO.getBdcdyh();
                    List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());

                    if (CollectionUtils.isNotEmpty(listDyaq)) {
                        tdsyqDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        tdsyqDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
                    List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    if (CollectionUtils.isNotEmpty(listCf)) {
                        tdsyqDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        tdsyqDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
                    List<BdcQl> listYy = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YY.toString());
                    if (CollectionUtils.isNotEmpty(listYy)) {
                        tdsyqDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        tdsyqDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????  ???????????? ???????????????
                    List<BdcQl> listSd = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    // ????????????
                    List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(tdsyqDTO.getXmid(), CommonConstantUtils.SDZT_SD);
                    if (CollectionUtils.isNotEmpty(listSd) || CollectionUtils.isNotEmpty(zssdDOList)) {
                        tdsyqDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        tdsyqDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
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
     * ??????????????????
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
            LOGGER.info("?????????????????????????????????");
            throw new MissingArgumentException("?????????????????????????????????");
        }
        Page<DyizmDTO> dyizmDTOPage = repository.selectPaging("querDyizmByPageOrder", dyicxQO, pageable);
        if (CollectionUtils.isNotEmpty(dyizmDTOPage.getContent())) {
            for (DyizmDTO dyizmDTO : dyizmDTOPage.getContent()) {
                //??????xmid??????????????????????????????????????????
                dyizmDTO.setQlrlist(qlrsjConvertQlrBean(dyizmDTO.getXmid(), CommonConstantUtils.QLRLB_QLR, "CommonQlrxx"));
            }

        }
        return dyizmDTOPage;
    }

    /**
     * ??????????????????
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
            LOGGER.info("?????????????????????????????????");
            throw new MissingArgumentException("?????????????????????????????????");
        }
        Page<YyzmDTO> yyzmDTOPage = repository.selectPaging("querYyzmByPageOrder", yycxQOQ, pageable);
        if (CollectionUtils.isNotEmpty(yyzmDTOPage.getContent())) {
            for (YyzmDTO yyzmDTO : yyzmDTOPage.getContent()) {
                //??????xmid??????????????????????????????????????????
                yyzmDTO.setQlrlist(qlrsjConvertQlrBean(yyzmDTO.getXmid(), CommonConstantUtils.QLRLB_QLR, "CommonQlrxx"));
                if (StringUtils.isNotBlank(yyzmDTO.getBdcdyh())) {
                    //????????????
                    String bdcdyh = yyzmDTO.getBdcdyh();
                    List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());

                    if (CollectionUtils.isNotEmpty(listDyaq)) {
                        yyzmDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        yyzmDTO.setSfdy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
                    List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    if (CollectionUtils.isNotEmpty(listCf)) {
                        yyzmDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        yyzmDTO.setSfcf(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
                    List<BdcQl> listYy = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YY.toString());
                    if (CollectionUtils.isNotEmpty(listYy)) {
                        yyzmDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        yyzmDTO.setSfyy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????  ???????????? ???????????????
                    List<BdcQl> listSd = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    // ????????????
                    List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(yyzmDTO.getXmid(), CommonConstantUtils.SDZT_SD);
                    if (CollectionUtils.isNotEmpty(listSd) || CollectionUtils.isNotEmpty(zssdDOList)) {
                        yyzmDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        yyzmDTO.setSfsd(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
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
     * @description ?????????????????????????????????
     **/
    @Override
    public BdcCommonResponse queryCqxxByQlr(JSONObject paramMap) {
        List<QlrRequestDTO> qlrRequestDTOS = JSONObject.parseArray(paramMap.getString("qlrJsonArr"), QlrRequestDTO.class);
        if (CollectionUtils.isEmpty(qlrRequestDTOS)) {
            return BdcCommonResponse.fail("???????????????");
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
     * @description ??????????????????
     */
    @Override
    public BdcCommonResponse queryGfxxByQlr(QlrGfxxRequestDTO gfxxRequestDTO) {
        if (null == gfxxRequestDTO) {
            return BdcCommonResponse.fail("?????????????????????");
        }
        String[] qlrmc = gfxxRequestDTO.getQlrmc().split(CommonConstantUtils.ZF_YW_DH);
        String[] qlrzjh = gfxxRequestDTO.getQlrzjh().split(CommonConstantUtils.ZF_YW_DH);
        if (qlrmc.length > 1 && qlrzjh.length > 1 && qlrmc.length != qlrzjh.length) {
            return BdcCommonResponse.fail("??????????????????????????????");
        }
        LOGGER.info("????????????????????????:{}", gfxxRequestDTO);
        List<QlrGfxxResponseDTO> gfxxResponseDTOList = repository.selectList("getGfxxByQlrxx", gfxxRequestDTO);
        return BdcCommonResponse.ok(gfxxResponseDTOList);
    }

    /**
     * @return
     * @author <a href = "mailto:wutao@gtmap.cn">wutao</a>
     * @description ??????????????????
     */
    @Override
    public BdcCommonResponse deleteGdxx(BdcGdxxRequestDTO bdcGdxxRequestDTO) {
        if (null == bdcGdxxRequestDTO || StringUtils.isBlank(bdcGdxxRequestDTO.getXmid())) {
            return BdcCommonResponse.fail("????????????id??????");
        }
        LOGGER.info("????????????????????????:{}", bdcGdxxRequestDTO);
        int count = 0;
        try {
            count = bdcGdxxFeignService.deleteBdcGdxxByXmid(bdcGdxxRequestDTO.getXmid());
        }catch (Exception e){
            return BdcCommonResponse.fail("??????????????????????????????????????????" + e);
        }
        LOGGER.info("????????????????????????????????????{}?????????", count);
        return BdcCommonResponse.ok(count);
    }

    /**
     * @return
     * @author <a href = "mailto:wutao@gtmap.cn">wutao</a>
     * @description ???????????????????????????
     */
    @Override
    public BdcCommonResponse getDzzzzip(BdcDzzzzipRequestDTO bdcDzzzzipRequestDTO) {
        if (null == bdcDzzzzipRequestDTO || StringUtils.isBlank(bdcDzzzzipRequestDTO.getSlbh())) {
            return BdcCommonResponse.fail("????????????????????????");
        }
        LOGGER.info("?????????????????????????????????:{}", bdcDzzzzipRequestDTO);
        BdcDzzzzipResponseDTO bdcDzzzzipResponseDTO = new BdcDzzzzipResponseDTO();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(bdcDzzzzipRequestDTO.getSlbh());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            String gzlslid = bdcXmDOList.get(0).getGzlslid();
            String clmc = "?????????????????????" + gzlslid + ".zip";
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
     * @description ?????????????????????????????????
     **/
    @Override
    public BdcCommonResponse queryJtBdcdjXx(JSONObject paramMap) {
        List<JtcyxxRequestDTO> jtcyList = JSONObject.parseArray(paramMap.getString("jtcyxx"), JtcyxxRequestDTO.class);
        if (CollectionUtils.isEmpty(jtcyList)) {
            return BdcCommonResponse.fail("??????????????????????????????");
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
            // ??????????????????????????????????????????????????????
            if ("??????".equals(jtBdcxxRespDTO.getGhyt())) {
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
     * ???????????????????????????
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
            LOGGER.info("?????????????????????????????????");
            throw new MissingArgumentException("?????????????????????????????????");
        }
        return acceptBdcdyFeignService.listWwsqBdcdyxxDTOByPage(pageable, JSON.toJSONString(bdcdyxxCxQO));
    }

    /**
     * ??????????????????
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
            LOGGER.info("?????????????????????????????????");
            throw new MissingArgumentException("?????????????????????????????????");
        }
        if (CollectionUtils.isNotEmpty(scdyGzldyidList)) {
            scdyGzldyidList.remove("");
            dydjxxcxCxQO.setGzldyids(scdyGzldyidList);
        }
        Page<WwsqDydjfxxDTO> querySczmdByPageOrder = repository
                .selectPaging("querDydjByPageOrder", dydjxxcxCxQO, pageable);

        if (CollectionUtils.isNotEmpty(querySczmdByPageOrder.getContent())) {
            for (WwsqDydjfxxDTO wwsqDydjfxxDTO : querySczmdByPageOrder.getContent()) {
                //???????????????
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

                    //???????????????,????????????,?????????
                    if (StringUtils.isNotBlank(qlrzjh) && StringUtils.isNotBlank(qlrmc)) {
                        wwsqDydjfxxDTO.setDyrmc(qlrmc);
                        wwsqDydjfxxDTO.setDyrzjh(qlrzjh);
                        wwsqDydjfxxDTO.setDyqrmc(qlrmc);
                    }
                    if (StringUtils.isNotBlank(ywrzjh) && StringUtils.isNotBlank(ywrmc)) {
                        wwsqDydjfxxDTO.setZwrmc(ywrmc);
                        wwsqDydjfxxDTO.setZwrzjh(ywrzjh);
                    }

                    //???????????????
                    List<BdcSlSfxxDO> bdcSlSfxxDOS = bdcSlSfxxFeignService
                            .listBdcSlSfxxByGzlslidHjfk(wwsqDydjfxxDTO.getGzlslid());
                    if (CollectionUtils.isNotEmpty(bdcSlSfxxDOS)) {
                        double sum = bdcSlSfxxDOS.stream().mapToDouble(BdcSlSfxxDO::getHj).sum();
                        wwsqDydjfxxDTO.setDjf(sum);
                        wwsqDydjfxxDTO.setJfzt("1");
                        //???????????????????????????????????????????????????
                        for (BdcSlSfxxDO sfxxDo : bdcSlSfxxDOS) {
                            if (!CommonConstantUtils.SFZT_YJF.equals(sfxxDo.getSfzt())) {
                                wwsqDydjfxxDTO.setJfzt("0");
                            }
                        }
                    }
                    //??????????????????
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
     * ???????????????????????????????????????
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
            zwbzshyDTO.setMsg("????????????");
            return zwbzshyDTO;
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzh(cqzh);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            zwbzshyDTO.setCheckjg("?????????????????????");
            zwbzshyDTO.setMsg("????????????");
            return zwbzshyDTO;
        }
        BdcZsDO bdcZsDO = bdcZsDOS.get(0);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.queryXmByZsBdcqzh(bdcZsDO.getBdcqzh());
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            zwbzshyDTO.setCheckjg("????????????????????????");
            zwbzshyDTO.setMsg("????????????");
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
            zwbzshyDTO.setCheckjg("???????????????????????????");
            zwbzshyDTO.setMsg("????????????");
            return zwbzshyDTO;
        }
        zwbzshyDTO.setCheckjg("????????????");
        zwbzshyDTO.setMsg("????????????");
        zwbzshyDTO.setZl(bdcXmDO.getZl());
        zwbzshyDTO.setQlrmc(String.join(",", qlrmcList));
        //????????????
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
     * @description ????????????????????????????????????????????????
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/26 15:53
     * @param paramMap
     * @return List<SpfHtbaxxResponseDTO>
     */
    @Override
    public List<SpfHtbaxxResponseDTO> querySpfHtbaxx(JSONObject paramMap) {
        SpfHtbaxxQO spfHtbaxxQO = JSONObject.parseObject(paramMap.getString("data"), SpfHtbaxxQO.class);
        if (!CheckParameter.checkAnyParameter(spfHtbaxxQO)) {
            throw new MissingArgumentException("?????????????????????????????????");
        }
        LOGGER.info("querySpfHtbaxx???????????????{}", spfHtbaxxQO);
        List<HtbaSpfDO> htbaxxDTOList = repository.selectList("querySpfHtbaxx", spfHtbaxxQO);
        LOGGER.info("querySpfHtbaxx??????HtbaSpf?????????{}", htbaxxDTOList);
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
        LOGGER.info("querySpfHtbaxx???????????????{}", htbaxxDTOList);
        return spfHtbaxxResponseDTOList;
    }

    /**
     * @description ??????????????????????????????????????????????????????????????????
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/16 15:05
     * @param fczh
     * @return List<PpgxResponseDTO>
     */
    @Override
    public List<PpgxResponseDTO> queryPpgx(String fczh) {
        if (StringUtils.isBlank(fczh)) {
            throw new MissingArgumentException("?????????????????????????????????");
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
                // ?????????????????????
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
                    // ???????????????????????????
                    String bdcdyh = bdcXnbdcdyhGxDOS.get(0).getBdcdyh();
                    if (StringUtils.isNotBlank(bdcdyh)) {
                        ppgxResponseDTO.setBdcdyh(bdcdyh);
                        // ????????????????????????
                        FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh, qjgldm);
                        if (Objects.nonNull(fwHsDO)) {
                            String bdcdyhzl = fwHsDO.getZl();
                            ppgxResponseDTO.setBdcdyhzl(bdcdyhzl);
                        }
                        // ?????????
                        String hst = fwHstFeignService.queryFwHstBase64ByBdcdyh(bdcdyh, qjgldm);
                        ppgxResponseDTO.setHst(hst);
                        // ?????????
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
            LOGGER.error("???????????????????????????");
            throw new MissingArgumentException("????????????????????????");
        }
        JSONArray fwxx = new JSONArray();
        List<String> zjhList = Arrays.asList(zjh, CardNumberTransformation.zjhTransformation(zjh));
        // ????????????????????????BDC_QLR,BDC_XM????????????????????????
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
     * @description ???????????????????????????????????????
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @date 2022/12/16 11:43
     */
    @Override
    public SpfBaxxResponseData xcBdcspfbacx(ParamBody paramBody) {
        SpfBaxxResponseData responseData = new SpfBaxxResponseData();
        FcjyBaxxDTO fcjyBaxxDTO = new FcjyBaxxDTO();
        //????????????1
        Object baxxResponse = exchangeBeanRequestService.request("xcFcjySpfQlr", JSONObject.parseObject(JSONObject.toJSONString(paramBody)));
        Map map1 = JSONObject.parseObject(JSONObject.toJSONString(baxxResponse),Map.class);
        String htbh  = String.valueOf(map1.get("htbh"));
        Map paramMap = new HashMap(1);
        if (StringUtils.isNotBlank(htbh)){
            paramMap.put("htbh",htbh);
        }
        //????????????2????????????
        Object o = exchangeBeanRequestService.request("xcFcjySpfBaxx", paramMap);
        if (o != null) {
            LOGGER.info("???????????????????????????,????????????{}???", JSON.toJSONString(o));
            fcjyBaxxDTO = JSONObject.parseObject(JSONObject.toJSONString(o), FcjyBaxxDTO.class);
            List<BdcQlrDO> list = fcjyBaxxDTO.getBdcQlr();
            String xmid = fcjyBaxxDTO.getXmid();
            BdcSlJyxxDO bdcSlJyxxDO = fcjyBaxxDTO.getBdcSlJyxx();
            BdcSlFwxxDO bdcSlFwxxDO = fcjyBaxxDTO.getBdcSlFwxx();
            BdcSlXmDO bdcSlXmDO = fcjyBaxxDTO.getBdcSlXmDO();
            //????????????
            if (CollectionUtils.isNotEmpty(list)){
                //???????????????????????????????????????????????????????????????????????????????????????
                List<SpfBaxxQlrResponseData> qlr = new ArrayList<>();
                for (BdcQlrDO bdcQlrDO : list) {
                    SpfBaxxQlrResponseData spfBaxxQlrResponseData = new SpfBaxxQlrResponseData();
                    spfBaxxQlrResponseData.setQlrmc(bdcQlrDO.getQlrmc());
                    spfBaxxQlrResponseData.setQlrlx(bdcQlrDO.getQlrlb());
                    spfBaxxQlrResponseData.setQlrzjh(bdcQlrDO.getZjh());
                    spfBaxxQlrResponseData.setQlrzjzl(String.valueOf(bdcQlrDO.getZjzl()));
                    //???????????????,??????????????????????????????0
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

        LOGGER.info("???????????????:{}", responseData);
        return responseData;
    }

    private void initFwxx(List<String> bdcdyhList, JSONArray fwxx,Integer pageNumber,Integer pageSize) {
        List<String> bdcdyhs = new ArrayList<>();

        //??????
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

        //?????????
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
        //??????
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
            // ??????bdcdyh????????????????????????
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

            // ?????????????????????????????????
            JSONObject fwxxResponse = new JSONObject();
            JSONArray fwqsxxFdcq = bdcQjService.dozerMapList(bdcQlFdcq,
                    "fwqsxxFdcq",
                    HashMap.class);
            fwxxResponse.put("QLT_FW_FDCQ_YZ", fwqsxxFdcq);


            JSONArray fwqsxxDyaq = bdcQjService.dozerMapList(bdcQlDyaq,
                    "fwqsxxDyaq",
                    HashMap.class);
            //????????????????????????
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
                qlrObject.put("JS","?????????");
            }
            List<ZttGyQlrDO> allBdcYwr = bdcQjService.queryZttGyQlr(xmids,CommonConstantUtils.QLRLB_YWR);
            JSONArray fwqsxxYwr = bdcQjService.dozerMapList(allBdcYwr, "fwqsxxQlr", HashMap.class);
            for (int i = 0; i < fwqsxxYwr.size(); i++) {
                JSONObject qlrObject = fwqsxxQlr.getJSONObject(i);
                qlrObject.put("JS","?????????");
            }
            fwqsxxQlr.addAll(allBdcYwr);
            fwxxResponse.put("ZTT_GY_QLR", fwqsxxQlr);

           // List<QltQlXzxzDO> qltQlXzxzDOS = bdcdjMapper.queryXzxxForYthQltQlXzxzByBdcdyh(bdcdyh);
            List<QltQlXzxzDO> qltQlXzxzDOS = bdcdjMapper.queryXzxxForYthQltQlXzxzByBdcdyh(bdcdyh);
            fwxxResponse.put("QLF_QL_XZXZ", qltQlXzxzDOS);
            // ??????????????????
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
            throw new MissingArgumentException("?????????????????????????????????");
        }
        LOGGER.info("queryJtBdcXx???????????????{}", jtBdcxxTycxRequestDTO);
        List<JtBdcxxTycxResponseDTO> jtBdcxxTycxResponseDTOList = repository.selectList("queryJtBdcXx", jtBdcxxTycxRequestDTO);
        LOGGER.info("queryJtBdcXx???????????????{}", jtBdcxxTycxResponseDTOList);
        if (CollectionUtils.isEmpty(jtBdcxxTycxResponseDTOList)) {
            return null;
        }
        return jtBdcxxTycxResponseDTOList;
    }

    /**
     * ??????????????????????????????????????????
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
