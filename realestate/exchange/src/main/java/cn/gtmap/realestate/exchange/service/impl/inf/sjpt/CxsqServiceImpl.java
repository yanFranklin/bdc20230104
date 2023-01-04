package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqqXm;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxsq.response.SjptCxsqResponseCxywcs;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxsq.response.SjptCxsqResponseData;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxsqFk.request.SjptCxsqFkRequestData;
import cn.gtmap.realestate.exchange.core.mapper.sjpt.GxCxqqMapper;
import cn.gtmap.realestate.exchange.service.inf.sjpt.CxsqService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-26
 * @description 省级平台查询申请服务
 */
@Service
public class CxsqServiceImpl implements CxsqService{


    private final Logger LOGGER = LoggerFactory.getLogger(CxsqServiceImpl.class);

    // 分页执行批量插入  每页执行数据
    private final static int BATCH_INSERT_PAGESIZE = 500;

    @Autowired
    private EntityMapper sjptEntityMapper;

    @Autowired
    private GxCxqqMapper gxCxqqMapper;

    private ExecutorService executor = new ThreadPoolExecutor(
            // 核心线程数量
            10,
            // 最大线程数
            50,
            // 超时30秒
            60, TimeUnit.SECONDS,
            // 最大允许等待200个任务
            new ArrayBlockingQueue<>(200),
            // 线程工厂
            new ThreadFactoryBuilder().setNameFormat("sjptcxsq-pool-%d").build(),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    class CxqqXmRecordThread implements Callable<Object> {

        private List cxsqList;

        List<String> cxsqdhList;

        public CxqqXmRecordThread(List cxsqList,List<String> cxsqdhList){
            this.cxsqList = cxsqList;
            this.cxsqdhList = cxsqdhList;
        }

        @Override
        public Object call() {
            batchInsertGxPeCxqqXm(cxsqList,cxsqdhList);
            return 1;
        }
    }
    /**
     * @param cxsqList
     * @return SjptCxsqFkRequestData
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存查询申请
     */
    @Override
    public SjptCxsqFkRequestData saveCxsq(JSONArray cxsqList) throws Exception {
        try {
            LOGGER.info("开始保存省厅查询申请，查询申请共{}条", CollectionUtils.size(cxsqList));
            List<Future<Object>> futureList = new ArrayList<>();

            List<String> cxsqdhList = Collections.synchronizedList(new ArrayList<>());
            SjptCxsqFkRequestData data = new SjptCxsqFkRequestData();
            data.setCxsqdhs(cxsqdhList);

            if(CollectionUtils.isNotEmpty(cxsqList)){
                // 多线程批量保存
                List<List> partList = ListUtils.subList(cxsqList, 500);
                for(List list : partList) {
                    futureList.add(executor.submit(new CxqqXmRecordThread(list,cxsqdhList)));
                }
            }

            for (Future<Object> future : futureList) {
                // 等待查询申请入库处理完成
                future.get();
            }
            return data;
        } catch (Exception e) {
            LOGGER.error("省厅查询申请处理异常：", e);
            throw e;
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gxPeCxqqList
     * @return void
     * @description 批量保存 查询申请
     */
    @Override
    @Transactional(value = "sjptTransactionManager", rollbackFor = Exception.class)
    public void batchInsertGxPeCxqq(List<GxPeCxqq> gxPeCxqqList) {
        if (CollectionUtils.isEmpty(gxPeCxqqList)) {
            return;
        }
        List<GxPeCxqq> resultList = new ArrayList<>();
        for (GxPeCxqq gxPeCxqq : gxPeCxqqList) {
            if (StringUtils.isNotBlank(gxPeCxqq.getCxsqdh())) {
                gxPeCxqq.setCjsj(new Date());
                gxPeCxqq.setGxsj(new Date());
                //判断是否已经抓取
                Example example = new Example(GxPeCxqq.class);
                example.createCriteria().andEqualTo("cxsqdh", gxPeCxqq.getCxsqdh());
                List<GxPeCxqq> oldGxPeCxqqList = sjptEntityMapper.selectByExample(example);
                if (CollectionUtils.isEmpty(oldGxPeCxqqList)) {
                    gxPeCxqq = initGxPeCxqq(gxPeCxqq);
                    gxPeCxqq.setZt(SjptConstants.CXQQ_ZT_WCL);
                    resultList.add(gxPeCxqq);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            insertBatchSelective(resultList);
        }
    }

    private void insertBatchSelective(List objList){
        if(CollectionUtils.isNotEmpty(objList)){
            List pageList = new ArrayList();
            for(Object temp : objList){
                pageList.add(temp);
                if(pageList.size() == BATCH_INSERT_PAGESIZE){
                    sjptEntityMapper.insertBatchSelective(pageList);
                    pageList.clear();
                }
            }
            if(CollectionUtils.isNotEmpty(pageList)){
                sjptEntityMapper.insertBatchSelective(pageList);
            }
        }
    }



    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param cxsqList
     * @return void
     * @description 批量保存 查询业务数据
     */
    @Override
    public void batchInsertGxPeCxqqXm(List cxsqList,List<String> cxsqdhList) {
        try {
            LOGGER.info("省厅查询分批次处理，当前处理{}条", CollectionUtils.size(cxsqList));
            // 循环响应文件 处理外层申请信息
            for (int i = 0; i < cxsqList.size(); i++) {
                List<GxPeCxqqXm> cxqqXmList = new ArrayList<>();
                SjptCxsqResponseData response =
                        JSONObject.parseObject(JSONObject.toJSONString(cxsqList.get(i)), SjptCxsqResponseData.class);
                GxPeCxqq cxqq = new GxPeCxqq();
                BeanUtils.copyProperties(response, cxqq);
                List<SjptCxsqResponseCxywcs> ywcsList = response.getCxywcs();
                if (CollectionUtils.isNotEmpty(ywcsList)) {
                    for (SjptCxsqResponseCxywcs ywcs : ywcsList) {
                        GxPeCxqqXm cxqqXm = new GxPeCxqqXm();
                        BeanUtils.copyProperties(ywcs, cxqqXm);
                        cxqqXm.setCxsqdh(response.getCxsqdh());
                        //设置默认为1
                        cxqqXm.setCxfw(StringUtils.isNotBlank(cxqq.getCxfw()) ? cxqq.getCxfw() : "1");
                        cxqqXmList.add(cxqqXm);
                    }
                }
                // 保存查询信息
                saveCxqqxx(cxqq, cxqqXmList);
                cxsqdhList.add(cxqq.getCxsqdh());
            }
        } catch (Exception e) {
            LOGGER.error("处理省厅查询业务数据异常：", e);
            throw e;
        }
    }

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param gxPeCxqqXmList
     * @return void
     * @description 批量保存 查询业务数据
     */
    @Override
    public void saveCxqqxx(GxPeCxqq cxqq,List<GxPeCxqqXm> gxPeCxqqXmList) {
        // 插入查询申请表
        if (cxqq != null && StringUtils.isNotBlank(cxqq.getCxsqdh())) {
            LOGGER.info("省厅查询处理单号：{}", cxqq.getCxsqdh());
            cxqq.setCjsj(new Date());
            cxqq.setGxsj(new Date());
            //判断是否已经抓取
            Example example = new Example(GxPeCxqq.class);
            example.createCriteria().andEqualTo("cxsqdh", cxqq.getCxsqdh());
            List<GxPeCxqq> oldGxPeCxqqList = sjptEntityMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(oldGxPeCxqqList)) {
                cxqq = initGxPeCxqq(cxqq);
                cxqq.setZt(SjptConstants.CXQQ_ZT_WCL);
                sjptEntityMapper.insertSelective(cxqq);
            }
        }
        // 批量插入业务数据
        if (CollectionUtils.isEmpty(gxPeCxqqXmList)) {
            LOGGER.info("当前单号{}无具体查询项目信息，处理结束", null == cxqq ? "【空】" : cxqq.getCxsqdh());
            return;
        }
        List<GxPeCxqqXm> resultList = new ArrayList<GxPeCxqqXm>();
        LinkedCaseInsensitiveMap<Integer> cxqqxmNumGroupByCxsqdh = new LinkedCaseInsensitiveMap<Integer>();
        LinkedCaseInsensitiveMap<Integer> insertCxqqxmNumGroupByCxsqdh = new LinkedCaseInsensitiveMap<Integer>();
        Integer num;
        for (GxPeCxqqXm gxPeCxqqXm : gxPeCxqqXmList) {
            if (StringUtils.isNotBlank(gxPeCxqqXm.getCxsqdh()) && StringUtils.isNotBlank(gxPeCxqqXm.getWsbh())) {
                //判断是否已经抓取
                Example example = new Example(GxPeCxqqXm.class);
                example.createCriteria().andEqualTo("cxsqdh", gxPeCxqqXm.getCxsqdh()).andEqualTo("wsbh", gxPeCxqqXm.getWsbh());
                List<GxPeCxqqXm> oldGxPeCxqqXmList = sjptEntityMapper.selectByExample(example);
                num = MapUtils.getInteger(cxqqxmNumGroupByCxsqdh, gxPeCxqqXm.getCxsqdh(), 0);
                // 查询申请单号 对应的 项目个数
                cxqqxmNumGroupByCxsqdh.put(gxPeCxqqXm.getCxsqdh(), ++num);
                if (CollectionUtils.isEmpty(oldGxPeCxqqXmList)) {
                    num = MapUtils.getInteger(insertCxqqxmNumGroupByCxsqdh, gxPeCxqqXm.getCxsqdh(), 0);
                    insertCxqqxmNumGroupByCxsqdh.put(gxPeCxqqXm.getCxsqdh(), ++num);
                    gxPeCxqqXm = initGxPeCxqqXm(gxPeCxqqXm);
                    resultList.add(gxPeCxqqXm);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            insertBatchSelective(resultList);
        }
        LOGGER.info("cxqqxmNumGroupByCxsqdh -- "+ JSONObject.toJSONString(cxqqxmNumGroupByCxsqdh));
        if (MapUtils.isNotEmpty(cxqqxmNumGroupByCxsqdh)
                && MapUtils.isNotEmpty(cxqqxmNumGroupByCxsqdh)) {
            List<String> cxsqdhList = new ArrayList<>();
            String cxsqdh;
            for (Map.Entry<String, Integer> cxsq : cxqqxmNumGroupByCxsqdh.entrySet()) {
                cxsqdh = cxsq.getKey();
                if (!insertCxqqxmNumGroupByCxsqdh.containsKey(cxsqdh)) {
                    continue;
                }
                if (cxsq.getValue() > insertCxqqxmNumGroupByCxsqdh.get(cxsqdh)) {
                    cxsqdhList.add(cxsqdh);
                }
            }
            try {
                if (CollectionUtils.isNotEmpty(cxsqdhList)){
                    Map param = new HashMap();
                    param.put("gxsj", DateUtil.getCurTime());
                    List<List> partList = ListUtils.subList(cxsqdhList, 1000);
                    for(List<String> list : partList){
                        param.put("cxsqdhList",list);
                        gxCxqqMapper.updateCxqqNotAllQueryed(param);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private GxPeCxqq initGxPeCxqq(GxPeCxqq gxPeCxqq) {
        if (StringUtils.isBlank(gxPeCxqq.getCxqqid())) {
            gxPeCxqq.setCxqqid(UUIDGenerator.generate());
        }
        if (gxPeCxqq.getCjsj() == null) {
            gxPeCxqq.setCjsj(new Date());
        }
        if (gxPeCxqq.getGxsj() == null) {
            gxPeCxqq.setGxsj(new Date());
        }
        return gxPeCxqq;
    }

    private GxPeCxqqXm initGxPeCxqqXm(GxPeCxqqXm gxPeCxqqXm) {
        if (StringUtils.isBlank(gxPeCxqqXm.getXmid())) {
            gxPeCxqqXm.setXmid(UUIDGenerator.generate());
        }
        return gxPeCxqqXm;
    }
}
