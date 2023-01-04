package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcCjxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCjxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/8/11
 * @description 不动产持件信息查询
 */
@RestController
@RequestMapping(value = "/rest/v1.0/cjxx")
public class BdcCjxxController {

    private static final Logger logger = LoggerFactory.getLogger(BdcCjxxController.class);

    /**
     * 持件信息流水号 REDIS Key
     */
    public static final String CJXX_LSH_REDIS_KEY_PREFIX = "BDC_CJXX_LSH_";

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcCjxxFeignService bdcCjxxFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    /**
     * 初始化持件信息（持件人、持件批次号）
     */
    @GetMapping(value = "/init")
    @ResponseStatus(HttpStatus.OK)
    public Object cjxxInit() {
        Map<String, String> cjxxInitMap = new HashMap<>();
        cjxxInitMap.put("lsh", this.generateLsh());
        UserDto userDto = userManagerUtils.getCurrentUser();
        cjxxInitMap.put("cjr", userDto.getAlias());
        cjxxInitMap.put("cjrid", userDto.getId());
        return cjxxInitMap;
    }

    // 生成持件批次号
    @RedissonLock(lockKey = "BDC_CJXXCX_REDISSONLOCK", description = "持件信息查询流水号生成锁", waitTime = 10L, leaseTime = 30L)
    public String generateLsh(){
        String lshDatePrefix = DateUtils.formateDateToString(new Date(), DateUtils.DATE_FORMATYMDHMS);
        // 查看redis种是否已生成流水号 key: BDC_CJXX_LSH_20220811121010
        String redisKey = CJXX_LSH_REDIS_KEY_PREFIX + lshDatePrefix;
        String redisValue = redisUtils.getStringValue(CJXX_LSH_REDIS_KEY_PREFIX + lshDatePrefix);
        Integer num = 0;
        if(StringUtils.isNotBlank(redisValue)){
            num = Integer.parseInt(redisValue) + 1;
        }
        this.redisUtils.addStringValue(redisKey, String.valueOf(num), 5);

        DecimalFormat decimalFormat = new DecimalFormat("00");
        return lshDatePrefix + decimalFormat.format(num);
    }

    /**
     * 保存持件信息
     * @param bdcCjxxDO 不动产持件信息DO
     */
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.OK)
    public void saveCjxx(@RequestBody BdcCjxxDO bdcCjxxDO) {
        if(Objects.isNull(bdcCjxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产持件信息内容");
        }
        this.bdcCjxxFeignService.saveBdcCjxx(bdcCjxxDO);
    }

    /**
     * 查询项目信息并生成持件信息
     * @param bdcCjxxDO 不动产持件信息
     * @return 不动产持件信息
     */
    @PostMapping(value="/xmxx/save")
    @ResponseStatus(HttpStatus.OK)
    public BdcCjxxDO getBdcXmxxAndCjxx(@RequestBody BdcCjxxDO bdcCjxxDO) {
        if(StringUtils.isBlank(bdcCjxxDO.getSlbh())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少受理编号参数");
        }
        // 根据受理编号获取项目信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(bdcCjxxDO.getSlbh());
        List<BdcXmDO> listBdcXm = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        String qlr = "";
        String zl = "";
        if(CollectionUtils.isNotEmpty(listBdcXm)){
            qlr = listBdcXm.get(0).getQlr();
            for(BdcXmDO bdcXmDO : listBdcXm){
                boolean isxzql = isXzql(bdcXmDO);
                //抵押、查封、预抵押流程持件申请人取当前流程的义务人
                if(isxzql){
                    qlr = bdcXmDO.getYwr();
                    break;
                }
            }
            zl = listBdcXm.get(0).getZl();
        }else {
            //查询受理数据
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(bdcCjxxDO.getSlbh(), null);
            if(Objects.nonNull(bdcSlJbxxDO)) {
                List<BdcSlXmDO> bdcSlXmDOS = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                if (CollectionUtils.isNotEmpty(bdcSlXmDOS)) {
                    qlr = bdcSlXmDOS.get(0).getQlr();
                    if(StringUtils.isBlank(qlr)){
                        qlr = "全体业主";
                    }
                    zl = bdcSlXmDOS.get(0).getZl();
                }
            }
        }
        if(StringUtils.isNotBlank(qlr) && StringUtils.isBlank(bdcCjxxDO.getCjxxid())){
            // 生成持件信息
            bdcCjxxDO.setQlr(qlr);
            bdcCjxxDO.setZl(zl);
            return this.bdcCjxxFeignService.saveBdcCjxx(bdcCjxxDO);
        }
        return null;
    }

    /**
     * 批量删除不动产持件信息
     * @param ids 持件信息ID集合
     */
    @DeleteMapping(value="/delete")
    @ResponseStatus(HttpStatus.OK)
    public void plDeleteBdcCjxx(@RequestBody List<String> ids){
        if(CollectionUtils.isEmpty(ids)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到需要删除的持件信息ID");
        }
        this.bdcCjxxFeignService.plDeleteBdcCjxx(ids);
    }

    /**
     * @description 判断是否为抵押、查封、预抵押等限制权利
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/12/21 16:55
     * @param bdcXmDO
     * @return boolean
     */
    private boolean isXzql(BdcXmDO bdcXmDO) {
        // 是否为抵押和查封
        boolean isdycf = CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx()) || CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx());
        // 是否为预抵押
        boolean isydy = false;
        if (CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx())) {
            String xmid = bdcXmDO.getXmid();
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
            if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcYgDO) {
                BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                if (CommonConstantUtils.YGDJZL_YSSPFDYYG.equals(bdcYgDO.getYgdjzl()) || CommonConstantUtils.YGDJZL_QTDYYG.equals(bdcYgDO.getYgdjzl())) {
                    isydy = true;
                }
            }
        }
        return (isdycf || isydy);
    }
}
