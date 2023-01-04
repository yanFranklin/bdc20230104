package cn.gtmap.realestate.exchange.service.impl.inf.yzw.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.exchange.core.domain.yzw.GxYzwTsjlDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.GxYzwYzsbxxDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCaseacceptDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCasebaseinfoDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCaseprocessDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCaseresultDO;
import cn.gtmap.realestate.exchange.core.dto.yzw.YzwCheckAndTsResultDTO;
import cn.gtmap.realestate.exchange.core.mapper.exchange.YzwCheckMapper;
import cn.gtmap.realestate.exchange.core.mapper.exchange.YzwMapper;
import cn.gtmap.realestate.exchange.core.thread.YzwCheckThread;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwCheckService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwService;
import cn.gtmap.realestate.exchange.service.inf.yzw.yancheng.YzwDataService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/23
 * @description 一张网验证相关服务(盐城)
 */
@Service("yzwCheckServiceImpl_yancheng")
public class YzwCheckServiceImpl implements YzwCheckService {

    private static final Logger logger = LoggerFactory.getLogger(YzwCheckServiceImpl.class);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 超期未办结验证预留天数
     */
    @Value("${yzw.cqwbjyz.ylts:5}")
    private  String ylts;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  一张网推送验证规则
     */
    @Value("#{'${yzw.tsCheck.yzlxList:1,2,3,4,5,6}'.split(',')}")
    private  List<String> yzlxList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网证件种类为身份证对应的代码值
     */
    @Value("${yzw.sfzzjzl:}")
    private  String sfzzjzl;






    /**
     * 常用属性名
     */
    private static final String CASENO = "caseno";

    @Autowired
    private YzwService yzwService;

    @Autowired
    private YzwDataService yzwDataService;

    @Autowired
    private YzwCheckMapper yzwCheckMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    ThreadEngine threadEngine;

    @Autowired
    private YzwMapper yzwMapper;

    @Override
    public YzwCheckAndTsResultDTO checkYzwData(String yzwbh, List<String> yzlxList){
        YzwCheckAndTsResultDTO yzwCheckAndTsResultDTO =new YzwCheckAndTsResultDTO();
        yzwCheckAndTsResultDTO.setYzwbh(yzwbh);
        yzwCheckAndTsResultDTO.setYzzt(Constants.YZW_YZZT_YZSB);
        List<GxYzwYzsbxxDO> gxYzwYzsbxxDOList =new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(yzlxList)) {
                for (String yzlx : yzlxList) {
                    GxYzwYzsbxxDO gxYzwYzsbxxDO = null;
                    switch (yzlx) {
                        case Constants.YZWYZLX_SJCS:
                            gxYzwYzsbxxDO = checkSjcs(yzwbh);
                            break;
                        case Constants.YZWYZLX_XMCQBJ:
                            gxYzwYzsbxxDO = checkXmCqbj(yzwbh);
                            break;
                        case Constants.YZWYZLX_XMCQWBJ:
                            gxYzwYzsbxxDO = checkXmCqwbj(yzwbh);
                            break;
                        case Constants.YZWYZLX_TJWTSJG:
                            gxYzwYzsbxxDO = checkTjwtsjg(yzwbh);
                            break;
                        case Constants.YZWYZLX_QSGCXX:
                            gxYzwYzsbxxDO = checkQsgcxx(yzwbh);
                            break;
                        case Constants.YZWYZLX_ZJHMBGH:
                            gxYzwYzsbxxDO = checkZjhmbgh(yzwbh);
                            break;
                        default:

                    }
                    if (gxYzwYzsbxxDO != null) {
                        gxYzwYzsbxxDOList.add(gxYzwYzsbxxDO);
                    }

                }
                if(CollectionUtils.isNotEmpty(gxYzwYzsbxxDOList)) {
                    yzwCheckAndTsResultDTO.setSblx(Constants.SBLX_YZSB);
                }else{
                    yzwCheckAndTsResultDTO.setYzzt(Constants.YZW_YZZT_YZCG);
                }
                yzwCheckAndTsResultDTO.setGxYzwYzsbxxDOList(gxYzwYzsbxxDOList);
            }
        }catch (Exception e){
            yzwCheckAndTsResultDTO.setSblx(Constants.SBLX_YC);
            yzwCheckAndTsResultDTO.setSbycxx(e.getMessage());

        }

        return yzwCheckAndTsResultDTO;

    }

    @Override
    public List<String> checkYzwDataPl(List<String> yzwbhList, boolean isAll){
        if(isAll){
            yzwbhList=listYzsbYzwbh();
        }
        if(CollectionUtils.isEmpty(yzwbhList)){
            throw new AppException("没有获取到需要验证的数据");
        }
        logger.info("需要验证的一张网数据共：{}条",yzwbhList.size());
        //判断是否不走多线程计数限制
        boolean sfbjs = (yzwbhList.size() <= 1);
        //循环线程
        List<YzwCheckThread> listThread = new ArrayList<>();
        for(String yzwbh:yzwbhList){
            YzwCheckThread yzwCheckThread =new YzwCheckThread(this,yzwbh,yzlxList);
            yzwCheckThread.setSfbjs(sfbjs);
            listThread.add(yzwCheckThread);
        }
        //多线程处理操作
        threadEngine.excuteThread(listThread, true);
        //获取对应返回值
        List<String> failyzwbhList=new ArrayList<>();
        List<YzwCheckAndTsResultDTO> yzwCheckAndTsResultDTOList =new ArrayList<>();
        for(YzwCheckThread yzwCheckThread:listThread){
            YzwCheckAndTsResultDTO yzwCheckAndTsResultDTO =yzwCheckThread.getYzwCheckAndTsResultDTO();
            if(yzwCheckAndTsResultDTO != null && yzwCheckAndTsResultDTO.getSblx() != null){
                yzwCheckAndTsResultDTO.setTszt(Constants.YZW_TSZT_TSSB);
                failyzwbhList.add(yzwCheckAndTsResultDTO.getYzwbh());
            }else if(yzwCheckAndTsResultDTO != null){
                yzwCheckAndTsResultDTO.setTszt(Constants.YZW_TSZT_WTS);
            }
            yzwCheckAndTsResultDTOList.add(yzwCheckAndTsResultDTO);

        }

        //保存
        yzwService.saveYzwCheckAndTsResultDTO(yzwCheckAndTsResultDTOList,true);

        logger.info("验证一张网问题数据共：{}条,具体一张网编号如下:{}",failyzwbhList.size(),failyzwbhList);
        return failyzwbhList;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  超时24小时收件
     */
    private GxYzwYzsbxxDO checkSjcs(String yzwbh){
        GxYzwYzsbxxDO gxYzwYzxx =null;
        List<TBmCaseacceptDO> tBmCaseacceptDOList=yzwService.listGxYzwDataByYzwbh(yzwbh,TBmCaseacceptDO.class,CASENO);
        if(CollectionUtils.isNotEmpty(tBmCaseacceptDOList)){
            for(TBmCaseacceptDO tBmCaseacceptDO:tBmCaseacceptDOList){
                //受理信息表同步时间与受理时间差大于24小时
                if(!DateUtils.isSameDay(tBmCaseacceptDO.getSyncdatetime(), tBmCaseacceptDO.getAcceptdate())){
                    gxYzwYzxx = new GxYzwYzsbxxDO();
                    gxYzwYzxx.setYzlx(Constants.YZWYZLX_SJCS);
                    break;
                }
            }
        }
        return gxYzwYzxx;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 超期办结
     */
    private GxYzwYzsbxxDO checkXmCqbj(String yzwbh){
        GxYzwYzsbxxDO gxYzwYzxx =null;
        List<TBmCaseresultDO> tBmCaseresultDOList=yzwService.listGxYzwDataByYzwbh(yzwbh,TBmCaseresultDO.class,CASENO);
        TBmCasebaseinfoDO tBmCasebaseinfoDO =yzwDataService.queryTBmCasebaseinfo(yzwbh);
        if(CollectionUtils.isNotEmpty(tBmCaseresultDOList) &&tBmCasebaseinfoDO != null){
            for(TBmCaseresultDO tBmCaseresultDO:tBmCaseresultDOList) {
                //办结时间大于承诺期限-超期
                if (DateUtils.truncatedCompareTo(tBmCaseresultDO.getCaseenddatetime(), tBmCasebaseinfoDO.getCasepromisedate(), Calendar.MILLISECOND) > 0) {
                    gxYzwYzxx = new GxYzwYzsbxxDO();
                    gxYzwYzxx.setYzlx(Constants.YZWYZLX_XMCQBJ);
                    break;
                }
            }
        }
        return gxYzwYzxx;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 超期未办结
     */
    private GxYzwYzsbxxDO checkXmCqwbj(String yzwbh){
        GxYzwYzsbxxDO gxYzwYzxx =null;

        Map cqxx=getCqWjgxx(yzwbh);
        if(cqxx != null){
            //存在业务数据
            List<BdcXmDO> bdcXmDOList =commonService.listBdcXmByZfxzspbh(yzwbh);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                gxYzwYzxx = new GxYzwYzsbxxDO();
                gxYzwYzxx.setYzlx(Constants.YZWYZLX_XMCQWBJ);

            }

        }
        return gxYzwYzxx;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 退件未推送退件结果
     */
    private GxYzwYzsbxxDO checkTjwtsjg(String yzwbh){
        GxYzwYzsbxxDO gxYzwYzxx =null;

        Map cqxx=getCqWjgxx(yzwbh);
        if(cqxx != null){
            //没有业务数据
            List<BdcXmDO> bdcXmDOList =commonService.listBdcXmByZfxzspbh(yzwbh);
            if(CollectionUtils.isEmpty(bdcXmDOList)){
                gxYzwYzxx = new GxYzwYzsbxxDO();
                gxYzwYzxx.setYzlx(Constants.YZWYZLX_TJWTSJG);

            }
        }
        return gxYzwYzxx;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取超期并且没有结果信息
     */
    private Map getCqWjgxx(String yzwbh){
        Map param = new HashMap();
        param.put(CASENO,yzwbh);
        //超期未办结验证预留天数
        param.put("ylts",ylts);
        param.put("checkDate",DateUtil.formateTimeYmdWithSplit(new Date()));
        return yzwCheckMapper.getCqWjgxx(param);
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 缺过程信息收件
     */
    private GxYzwYzsbxxDO checkQsgcxx(String yzwbh){
        GxYzwYzsbxxDO gxYzwYzxx =null;
        List<TBmCaseprocessDO> tBmCaseprocessDOList=yzwService.listGxYzwDataByYzwbh(yzwbh,TBmCaseprocessDO.class,CASENO);
        if(CollectionUtils.isEmpty(tBmCaseprocessDOList)){
            //过程信息为空
            gxYzwYzxx = new GxYzwYzsbxxDO();
            gxYzwYzxx.setYzlx(Constants.YZWYZLX_QSGCXX);
        }
        return gxYzwYzxx;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 证件号码不规范办件
     */
    private GxYzwYzsbxxDO checkZjhmbgh(String yzwbh){
        GxYzwYzsbxxDO gxYzwYzxx =null;
        //证件号码是否规范
        boolean zjhmgf =false;
        TBmCasebaseinfoDO tBmCasebaseinfoDO =yzwDataService.queryTBmCasebaseinfo(yzwbh);
        if(tBmCasebaseinfoDO != null &&StringUtils.isNoneBlank(tBmCasebaseinfoDO.getCaseapplicantpapertype(),tBmCasebaseinfoDO.getCaseapplicantpapercode())){
            //1.申请人证件类型和证件号码不为空
            List<String> yzwzjzlList =yzwMapper.listGxYzwZdZjzlDm();
            if(StringUtils.equals(tBmCasebaseinfoDO.getCaseapplicantpapertype(),sfzzjzl)){
                //验证身份证号
                zjhmgf =CardNumberTransformation.isIdCard(tBmCasebaseinfoDO.getCaseapplicantpapercode());
            }else if(CollectionUtils.isNotEmpty(yzwzjzlList) &&yzwzjzlList.indexOf(tBmCasebaseinfoDO.getCaseapplicantpapertype()) >-1){
                zjhmgf =true;
            }

        }
        if(!zjhmgf){
            gxYzwYzxx = new GxYzwYzsbxxDO();
            gxYzwYzxx.setYzlx(Constants.YZWYZLX_ZJHMBGH);
        }
        return gxYzwYzxx;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取验证失败的一张网编号
     */
    private List<String> listYzsbYzwbh(){
        List<String> yzwbhList =new ArrayList<>();
        Map param =new HashMap();
        List<Integer> tszts =new ArrayList<>();
        List<Integer> yzzts =new ArrayList<>();
        tszts.add(Constants.YZW_TSZT_TSSB);
        yzzts.add(Constants.YZW_YZZT_YZSB);
        param.put("tszts", tszts);
        param.put("yzzts", yzzts);
        List<GxYzwTsjlDO> gxYzwTsjlDOList=yzwCheckMapper.listTsjl(param);
        if(CollectionUtils.isNotEmpty(gxYzwTsjlDOList)){
            for(GxYzwTsjlDO gxYzwTsjlDO:gxYzwTsjlDOList){
                yzwbhList.add(gxYzwTsjlDO.getYzwbh());
            }

        }
        return yzwbhList;

    }


}
