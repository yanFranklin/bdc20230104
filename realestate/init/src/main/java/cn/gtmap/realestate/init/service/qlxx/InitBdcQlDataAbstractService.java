package cn.gtmap.realestate.init.service.qlxx;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
public abstract class InitBdcQlDataAbstractService extends InitBdcQlBaseAbstractService {
    @Autowired
    BdcZdCache bdcZdCache;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  同步权籍数据同步附记字段的流程
      */
    @Value("#{'${synclpb.fj.gzldyid:}'.split(',')}")
    private List<String> synclpbFj;

    @Override
    public String getCode() {
        return "qlsjly";
    }


    /**
     * 初始化权利信息接口
     * @param initServiceQO  初始化所需数据结构
     * @param initServiceDTO
     * @return 返回所有权利相关信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO initQlxx(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        BdcQl initQl=initQlxx(initServiceQO);
        if(initQl!=null){
            //空的话读取权利人数据
            if(StringUtils.isBlank(initQl.getGyqk())){
                List<BdcQlrDO> list=initServiceQO.getBdcQlrList();
                if(CollectionUtils.isNotEmpty(list)){
                    Integer gyfs = list.get(0).getGyfs();
                    if(gyfs!=null){
                        String gyfsmc=bdcZdCache.getFeildValue(BdcZdGyfsDO.class.getSimpleName(),gyfs,BdcZdGyfsDO.class);
                        if(StringUtils.isNotBlank(gyfsmc)){
                            initQl.setGyqk(gyfsmc);
                        }
                    }
                }
            }
            String dqQlid =initQl.getQlid();
            //如果是重新读取权籍数据的操作
            if(initServiceQO.isSfzqlpbsj()){
                BdcQl dqQl=bdcQllxService.queryQllxDO(initQl,initServiceQO.getXmid());
                if(dqQl!=null){
                    //将对象保存到dto里
                    initServiceDTO.setBdcQl(dqQl);
                    //获取原qlid,重新读取权籍数据保持qlid不变，防止相关联的子表关联不上
                    dqQlid =dqQl.getQlid();
                }
            }
            BdcQl bdcQl = initServiceDTO.getBdcQl();
            if (bdcQl != null) {
                // 转换
                dozerUtils.sameBeanDateConvert(initQl, bdcQl, false);
                if(StringUtils.isNotBlank(dqQlid)){
                    bdcQl.setQlid(dqQlid);
                    // 子表同步
                    if (bdcQl instanceof BdcFdcqDO) {
                        // 多幢项目信息
                        if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcFdcqFdcqxmList())) {
                            for(BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO:initServiceDTO.getBdcFdcqFdcqxmList()){
                                bdcFdcqFdcqxmDO.setQlid(dqQlid);
                            }
                        }
                    } else if (bdcQl instanceof BdcFdcq3DO) {
                        // 建筑物区分业主共有部分
                        if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcFdcq3GyxxList())) {
                            for(BdcFdcq3GyxxDO bdcFdcq3GyxxDO:initServiceDTO.getBdcFdcq3GyxxList()){
                                bdcFdcq3GyxxDO.setQlid(dqQlid);
                            }
                        }
                    }
                }

                if(initServiceQO.isSfdbqj()){
                    // 1、场景
                    //      如果为受理比对权籍功能（contractLpb.html页面） 单独处理下
                    // 2、修改原因：
                    //      上面 sameBeanDateConvert 传false会导致权籍字段为空而登记非空情况下，权籍字段被登记值覆盖，造成页面展示时候权籍为空数据展示了登记数据的问题；
                    //      如果传true会导致权籍字段为空而登记非空情况下，页面保存后登记信息权利数据被清空；
                    // 3、修改思路
                    //      获取权籍对象字段值为空，但是登记字段有值的字段集合，返回给权籍比对页面加载查询时候处理 BdcSynchServiceImpl.queryLpbDataDzxx，
                    //      不能直接在这里进行字段值处理，避免保存时候走当前逻辑导致数据清空。不传查询、保存标志参数到这边处理是因为前后传参复杂，修改影响大
                    Set<String> fieldNameList = BeansResolveUtils.getSourceNullButTargetedNotNullField(initQl, bdcQl);
                    initServiceDTO.setFieldNameSet(fieldNameList);
                }
            } else {
                initServiceDTO.setBdcQl(initQl);
            }
            //受理项目不为空的话做对照
            if(initServiceQO.getBdcSlXmDO()!=null){
                dozerUtils.initBeanDateConvert(initServiceQO.getBdcSlXmDO(),initServiceDTO.getBdcQl());
            }
            //第三方信息不为空的话做对照
            if(initServiceQO.getDsfSlxxDTO()!=null){
                dozerUtils.initBeanDateConvert(initServiceQO.getDsfSlxxDTO(),initServiceDTO.getBdcQl());
            }
            //受理权利不为空的话做对照
            if(initServiceQO.getBdcSlQl()!=null){
                dozerUtils.initBeanDateConvert(initServiceQO.getBdcSlQl(),initServiceDTO.getBdcQl());
            }
            //附记对照，同步权籍时处理,宗地读取附加说明,房屋读取户室表备注
            if(initServiceQO.isSfzqlpbsj() &&CollectionUtils.isNotEmpty(synclpbFj) &&StringUtils.isNotBlank(initServiceQO.getBdcXm().getGzldyid()) &&synclpbFj.contains(initServiceQO.getBdcXm().getGzldyid())){
                Boolean isFw=bdcBdcdyService.judgeIsFwByBdcdyh(initServiceQO.getBdcdyh());
                String lpbfj ="";
                if(isFw) {
                    if(initServiceQO.getBdcdyDTO() instanceof BdcdyResponseDTO &&CommonConstantUtils.BDCDYFWLX_HS.equals(initServiceQO.getBdcXm().getBdcdyfwlx())) {
                        lpbfj =((BdcdyResponseDTO) initServiceQO.getBdcdyDTO()).getBz();
                    }
                }else{
                    DjxxResponseDTO djxxResponseDTO =initServiceQO.getDjxxResponseDTO();
                    if(djxxResponseDTO.getDjDcbResponseDTO() instanceof ZdDjdcbResponseDTO){
                        lpbfj =((ZdDjdcbResponseDTO) djxxResponseDTO.getDjDcbResponseDTO()).getQtsm();
                    }else if(djxxResponseDTO.getDjDcbResponseDTO() instanceof QszdDjdcbResponseDTO){
                        lpbfj =((QszdDjdcbResponseDTO) djxxResponseDTO.getDjDcbResponseDTO()).getQtsm();
                    }else if(djxxResponseDTO.getDjDcbResponseDTO() instanceof NydDjdcbResponseDTO){
                        lpbfj =((NydDjdcbResponseDTO) djxxResponseDTO.getDjDcbResponseDTO()).getQtsm();
                    }
                }
                if(StringUtils.isNotBlank(lpbfj)){
                    initServiceDTO.getBdcQl().setFj(lpbfj);
                }
            }
        }
        // 部分权利数据在上述处理后才有值，但是可能还需要在此基础上再处理
        qlxxPostProcess(initServiceDTO);
        return initServiceDTO;
    }

    /**
     * 初始化权利信息接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回权利信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    public abstract BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception;

    /**
     * 权利信息后置处理
     * @param initServiceDTO
     */
    public void qlxxPostProcess(InitServiceDTO initServiceDTO) {
        // 需要处理的子类覆写
    }

    /**
     * 读取原权利和项目信息到当前权利
     *
     * @param initServiceQO
     * @param entityClass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T initFromYxm(InitServiceQO initServiceQO, Class<T> entityClass) throws IllegalAccessException, InstantiationException {
        return initFromYxm(initServiceQO,entityClass,initServiceQO.getYxmid());
    }


    /**
     * 读取原权利和项目信息到当前权利
     *
     * @param initServiceQO
     * @param entityClass
     * @param  yxmid  指定原项目Id
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T initFromYxm(InitServiceQO initServiceQO, Class<T> entityClass,String yxmid) throws IllegalAccessException, InstantiationException {
        BdcQl yBdcQl = null;
        T bdcQl = entityClass.newInstance();
        // 通过项目信息对权利赋值
        initDozerMapper.map(initServiceQO.getBdcXm(), bdcQl);

        // 原项目id不为空时 查询原权利信息
        if (StringUtils.isNotBlank(yxmid)) {
            //通过yxmid去取QO中是否有初始化后的数据
            InitServiceDTO initServiceDTO = initServiceQO.getServiceDataMap().get(yxmid);
            if (initServiceDTO != null) {
                yBdcQl = initServiceDTO.getBdcQl();
            } else {
                yBdcQl = bdcQllxService.queryQllxDO(yxmid);
            }
        }
        // 若原权利信息不为空时 对权利赋值
        if (yBdcQl != null) {
            initDozerMapper.map(yBdcQl, bdcQl);
        }
        return bdcQl;
    }


    /**
     * 初始化房屋类权利数据从权籍
     *
     * @param initServiceQO
     * @param entityClass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T initFwFromLpb(InitServiceQO initServiceQO, Class<T> entityClass) throws IllegalAccessException, InstantiationException {
        T obj = entityClass.newInstance();
        initFromLpbDjDcb(initServiceQO, entityClass, obj);
        initFromLpbBdcdy(initServiceQO, entityClass, obj);
        initFromLpbXm(initServiceQO, entityClass, obj);
        return obj;
    }

    /**
     * 初始化宗地类权利数据从权籍
     *
     * @param initServiceQO
     * @param entityClass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T initTdFromLpb(InitServiceQO initServiceQO, Class<T> entityClass) throws IllegalAccessException, InstantiationException {
        T obj = entityClass.newInstance();
        initFromLpbDjDcb(initServiceQO, entityClass, obj);
        initFromLpbXm(initServiceQO, entityClass, obj);
        return obj;
    }


    /**
     * 初始化权利数据从权籍调查表
     *
     * @param initServiceQO
     * @param entityClass
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T initFromLpbDjDcb(InitServiceQO initServiceQO, Class<T> entityClass, T obj) throws IllegalAccessException, InstantiationException {
        //空的话初始化实例
        if (obj == null) {
            obj = entityClass.newInstance();
        }
        // 通过地籍信息为对应权利数据赋值
        DjxxResponseDTO djxxResponseDTO=initServiceQO.getDjxxResponseDTO();
        if (djxxResponseDTO.getDjDcbResponseDTO() != null) {
            initDozerMapper.map(djxxResponseDTO.getDjDcbResponseDTO(), obj);
            //获取发包方信息
            DjDcbResponseDTO djDcbResponseDTO = djxxResponseDTO.getDjDcbResponseDTO();
            if(djDcbResponseDTO instanceof JyqDkDcbResponseDTO && ((JyqDkDcbResponseDTO) djDcbResponseDTO).getJyqDkLcfDO() != null){
                initDozerMapper.map(((JyqDkDcbResponseDTO) djDcbResponseDTO).getJyqDkLcfDO(),obj);
            }
            if(djDcbResponseDTO instanceof NydDjdcbResponseDTO && CollectionUtils.isNotEmpty(((NydDjdcbResponseDTO) djDcbResponseDTO).getCbzdFbfDOList()) && ((NydDjdcbResponseDTO) djDcbResponseDTO).getCbzdFbfDOList().get(0) != null){
                initDozerMapper.map(((NydDjdcbResponseDTO) djDcbResponseDTO).getCbzdFbfDOList().get(0),obj);
            }
            //获取承包方信息
            if(djDcbResponseDTO instanceof NydDjdcbResponseDTO && CollectionUtils.isNotEmpty(((NydDjdcbResponseDTO) djDcbResponseDTO).getCbzdCbfDOList()) && ((NydDjdcbResponseDTO) djDcbResponseDTO).getCbzdCbfDOList().get(0) != null){
                initDozerMapper.map(((NydDjdcbResponseDTO) djDcbResponseDTO).getCbzdCbfDOList().get(0),obj);
            }
            //宗地权利人对照  同步权籍时不做处理
            if(!initServiceQO.isSfzqlpbsj() && CollectionUtils.isNotEmpty(djxxResponseDTO.getQlrList())){
                initDozerMapper.map(djxxResponseDTO.getQlrList().get(0), obj);
            }
        }
        return obj;
    }


    /**
     * 初始化权利数据从权籍不动产单元信息
     *
     * @param initServiceQO
     * @param entityClass
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T initFromLpbBdcdy(InitServiceQO initServiceQO, Class<T> entityClass, T obj) throws IllegalAccessException, InstantiationException {
        //空的话初始化实例
        if (obj == null) {
            obj = entityClass.newInstance();
        }
        Boolean isFw=bdcBdcdyService.judgeIsFwByBdcdyh(initServiceQO.getBdcdyh());
        if(isFw){
            // 通过不动产单元信息为权利数据赋值
            if(initServiceQO.getBdcdyDTO() instanceof GzwDcbResponseDTO &&initServiceQO.getGzwDcbResponseDTO().getGzwDcbDO() != null){
                initDozerMapper.map(initServiceQO.getGzwDcbResponseDTO().getGzwDcbDO(),obj);
            }else {
                initDozerMapper.map(initServiceQO.getBdcdyDTO(), obj);
            }
        }
        return obj;
    }


    /**
     * 初始化权利数据从不动产项目表
     *
     * @param initServiceQO
     * @param entityClass
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T initFromLpbXm(InitServiceQO initServiceQO, Class<T> entityClass, T obj) throws IllegalAccessException, InstantiationException {
        //空的话初始化实例
        if (obj == null) {
            obj = entityClass.newInstance();
        }
        // 通过项目信息为权利数据赋值
        if (initServiceQO != null && initServiceQO.getBdcXm() != null) {
            initDozerMapper.map(initServiceQO.getBdcXm(), obj);
        }
        return obj;
    }
}
