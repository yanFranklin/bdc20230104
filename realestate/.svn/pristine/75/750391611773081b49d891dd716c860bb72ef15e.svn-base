package cn.gtmap.realestate.init.service.qlxx;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.config.BdcDzgxConfig;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.mapper.BdcQlMapper;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcBdcdyService;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 权利基础服务
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
public abstract class InitBdcQlBaseAbstractService implements InitService {

    @Autowired
    protected InitBeanFactory initBeanFactory;
    @Autowired
    protected EntityMapper entityMapper;
    @Autowired
    protected DozerUtils dozerUtils;
    @Autowired
    protected BdcQllxService bdcQllxService;
    @Autowired
    protected DozerBeanMapper initDozerMapper;
    @Autowired
    protected BdcBdcdyService bdcBdcdyService;
    @Autowired
    protected BdcDzgxConfig bdcDzgxConfig;
    @Autowired
    protected BdcQlMapper bdcQlMapper;

    @Override
//    @AuditLog(event = "初始化权利信息", response = true, names = {"initServiceQO", "initServiceDTO"})
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        // 参数空值返回
        if (initServiceQO == null) {
            return initServiceDTO;
        }
        if (initServiceDTO == null) {
            initServiceDTO = new InitServiceDTO();
        }
        initServiceDTO = initQlxx(initServiceQO,initServiceDTO);
        return initServiceDTO;
    }

    /**
     * 删除数据父接口
     * @param sfzqlpbsj 是否抽取楼盘表数据
     * @param plDel 是否批量业务删除
     * @param list 要删除的项目
     * @return 返回数据结构
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> delete(List<BdcXmDO> list,Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean plDel) throws Exception {
        List<Object> qlList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            //批量删除
            if(plDel){
                String gzlslid=list.get(0).getGzlslid();
                Integer qllx=list.get(0).getQllx();
                if(StringUtils.isNotBlank(gzlslid) && qllx!=null){
                    //删除对应权利数据
                    String tableName=bdcDzgxConfig.getQllxMap().get(qllx.toString());
                    if(StringUtils.isNotBlank(tableName)){
                        //判定权利是否要删除子表
                        //bug43141,由于对比权籍时重新生成了多幢信息,所以原有多幢信息对比权籍需要删除（其他关联bug30595,39245）
                        BdcQl bdcQl = bdcQllxService.makeSureQllx(qllx.toString());
                        if (bdcQl instanceof BdcFdcqDO || bdcQl instanceof BdcFdcq3DO) {
                            //权利子表走普通删除逻辑
                            for (BdcXmDO bdcXmDO : list) {
                                bdcQl = bdcQllxService.queryQllxDO(bdcQl,bdcXmDO.getXmid());
                                // 房地产权的需要删除子户室和多幢需要删除子表
                                if (bdcQl instanceof BdcFdcqDO) {
                                    // 多幢时需要删除项目信息
                                    addFdcqxm(qlList, null,bdcQl);
                                    if(!sfdzbflpbsj) {
                                        //非对照楼盘表的做处理
                                        // 附属设施删除
                                        addBdcFwfsss(qlList, null, bdcXmDO);
                                    }
                                } else if (bdcQl instanceof BdcFdcq3DO &&!sfdzbflpbsj) {
                                    //非对照楼盘表的做处理
                                    // 建筑物区分业主共有部分需要删除子表
                                    addBdcFdcq3Gyxx(qlList,null, bdcQl);
                                }
                            }
                        }
                        bdcQlMapper.deleteQl(tableName,gzlslid);
                    }
                }
            }else{
                for (BdcXmDO bdcXmDO : list) {
                    BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXmDO.getXmid());
                    if (bdcQl != null) {
                        qlList.add(bdcQl);
                    }
                    //bug43141,由于对比权籍时重新生成了多幢信息,所以原有多幢信息对比权籍需要删除（其他关联bug30595,39245）
                    // 房地产权的需要删除子户室和多幢需要删除子表
                    if (bdcQl instanceof BdcFdcqDO) {
                        // 多幢时需要删除项目信息
                        addFdcqxm(qlList,null, bdcQl);
                        if(!sfdzbflpbsj) {
                            // 附属设施删除
                            addBdcFwfsss(qlList, null, bdcXmDO);
                        }
                    } else if (bdcQl instanceof BdcFdcq3DO) {
                        if(!sfdzbflpbsj) {
                            // 建筑物区分业主共有部分需要删除子表
                            addBdcFdcq3Gyxx(qlList, null, bdcQl);
                        }
                    }
                }
            }
        }
        return qlList;
    }

    /**
     * 当前项目业务数据查询接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcXmDO 查询的项目信息
     *@param initServiceDTO 返回数据
     *@return   返回数据结构
     *@description
     */
    @Override
    public InitServiceDTO query(BdcXmDO bdcXmDO,InitServiceDTO initServiceDTO) throws Exception{
        if(initServiceDTO == null){
            initServiceDTO = new InitServiceDTO();
        }
        if(bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getXmid())){
            BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXmDO.getXmid());
            if (bdcQl != null) {
                initServiceDTO.setBdcQl(bdcQl);
            }
            // 房地产权的需要查询子户室和多幢需要查询子表
            if (bdcQl instanceof BdcFdcqDO) {
                // 多幢时需要查询项目信息
                addFdcqxm(null,initServiceDTO, bdcQl);
                // 附属设施查询
                addBdcFwfsss(null,initServiceDTO, bdcXmDO);
            } else if (bdcQl instanceof BdcFdcq3DO) {
                // 建筑物区分业主共有部分需要查询子表
                addBdcFdcq3Gyxx(null,initServiceDTO, bdcQl);
            }
        }
        return initServiceDTO;
    }

    /**
     * 初始化权利信息接口
     * @param initServiceQO 初始化所需数据结构
     * @param initServiceDTO
     * @return 返回所有权利相关信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    public abstract InitServiceDTO initQlxx(InitServiceQO initServiceQO,InitServiceDTO initServiceDTO) throws Exception;


    /**
     * 多幢时 删除项目信息
     *@param initServiceDTO
     * @param qlList
     * @param bdcQl
     */
    private void addFdcqxm(List<Object> qlList,InitServiceDTO initServiceDTO, BdcQl bdcQl) {
        // 多幢时需要删除项目信息
        if (CommonConstantUtils.FWLX_DUOZH.equals(((BdcFdcqDO) bdcQl).getBdcdyfwlx())) {
            BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO = new BdcFdcqFdcqxmDO();
            bdcFdcqFdcqxmDO.setQlid(bdcQl.getQlid());
            List<BdcFdcqFdcqxmDO> listFdcqXm = entityMapper.selectByObj(bdcFdcqFdcqxmDO);
            if (CollectionUtils.isNotEmpty(listFdcqXm)) {
                if(qlList!=null){
                    qlList.addAll(listFdcqXm);
                }
                if(initServiceDTO!=null){
                    initServiceDTO.setBdcFdcqFdcqxmList(listFdcqXm);
                }
            }
        }
    }

    /**
     * 附属设施删除
     *
     * @param qlList
     * @param bdcXmDO
     */
    private void addBdcFwfsss(List<Object> qlList,InitServiceDTO initServiceDTO, BdcXmDO bdcXmDO) {
        // 附属设施删除
        BdcFwfsssDO bdcFwfsssDO = new BdcFwfsssDO();
        bdcFwfsssDO.setXmid(bdcXmDO.getXmid());
        List<BdcFwfsssDO> listFwfsss = entityMapper.selectByObj(bdcFwfsssDO);
        if (CollectionUtils.isNotEmpty(listFwfsss)) {
            if(qlList!=null){
                qlList.addAll(listFwfsss);
            }
            if(initServiceDTO!=null){
                initServiceDTO.setFwfsssList(listFwfsss);
            }
        }
    }

    /**
     * 建筑物区分业主共有部分 删除子表
     *
     * @param qlList
     * @param bdcQl
     */
    private void addBdcFdcq3Gyxx(List<Object> qlList,InitServiceDTO initServiceDTO, BdcQl bdcQl) {
        // 建筑物区分业主共有部分需要删除子表
        BdcFdcq3GyxxDO bdcFdcq3GyxxDO = new BdcFdcq3GyxxDO();
        bdcFdcq3GyxxDO.setQlid(bdcQl.getQlid());
        List<BdcFdcq3GyxxDO> listGyxx = entityMapper.selectByObj(bdcFdcq3GyxxDO);
        if (CollectionUtils.isNotEmpty(listGyxx)) {
            if(qlList!=null){
                qlList.addAll(listGyxx);
            }
            if(initServiceDTO!=null){
                initServiceDTO.setBdcFdcq3GyxxList(listGyxx);
            }
        }
    }

    @Override
    public String getVersion() {
        return null;
    }
}
