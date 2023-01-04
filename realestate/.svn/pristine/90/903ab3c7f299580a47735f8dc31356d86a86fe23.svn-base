package cn.gtmap.realestate.init.service.zsxx;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmZsGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.*;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 初始化证书的基础服务
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
public abstract class InitBdcZsBaseAbstractService implements InitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcZsBaseAbstractService.class);
    @Autowired
    protected InitBeanFactory initBeanFactory;
    @Autowired
    protected EntityMapper entityMapper;
    @Autowired
    protected BdcQlrService bdcQlrService;
    @Autowired
    protected BdcZsService bdcZsService;
    @Autowired
    protected BdcXtZsmbPzService bdcXtZsmbPzService;
    @Autowired
    protected BdcQllxService bdcQllxService;
    @Autowired
    protected BdcXtQlqtzkFjPzService bdcXtQlqtzkFjPzService;
    @Autowired
    protected BdcZdCache bdcZdCache;
    @Autowired
    protected DozerUtils dozerUtils;
    @Autowired
    protected BdcLzrService bdcLzrService;

    @Override
//    @AuditLog(event = "初始化证书信息",response = true,names = {"initServiceQO","initServiceDTO"})
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        List<BdcZsDO> zsList = initZsxx(initServiceQO);
        if (initServiceDTO == null) {
            initServiceDTO = new InitServiceDTO();
        }
        initServiceDTO.setBdcZsList(zsList);
        //增加项目证书关系数据
        if (CollectionUtils.isNotEmpty(zsList)) {
            for (BdcZsDO bdcZsDO : zsList) {
                BdcXmZsGxDO bdcXmZsGxDO = new BdcXmZsGxDO();
                bdcXmZsGxDO.setGxid(UUIDGenerator.generate16());
                bdcXmZsGxDO.setXmid(initServiceQO.getXmid());
                bdcXmZsGxDO.setZsid(bdcZsDO.getZsid());
                initServiceDTO.getBdcXmZsGxList().add(bdcXmZsGxDO);
            }
        }
        //处理权利人数据  将修改后的qlr数据和没变的ywr整合
        if (CollectionUtils.isNotEmpty(initServiceQO.getBdcQlrList())) {
            List<BdcQlrDO> list = new ArrayList<>();
            list.addAll(initServiceQO.getBdcQlrList());
            if (CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrList())) {
                for (BdcQlrDO bdcQlrDO : initServiceDTO.getBdcQlrList()) {
                    if (StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_YWR)) {
                        list.add(bdcQlrDO);
                    }
                }
            }
            initServiceDTO.setBdcQlrList(list);
        }
        return initServiceDTO;
    }

    /**
     * 删除数据父接口
     *@param sfzqlpbsj 是否抽取楼盘表数据
     * @param plDel 是否批量业务删除
     * @param list 要删除的项目
     * @return 返回数据结构
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> delete(List<BdcXmDO> list,Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean plDel) throws Exception {
        List<Object> deleteList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            //批量删除
            if(plDel){
                String gzlslid=list.get(0).getGzlslid();
                LOGGER.info("批量删除证书操作，gzlslid:{}",gzlslid);
                if(StringUtils.isNotBlank(gzlslid)){
                    //删除证书
                    bdcZsService.deleteZs(gzlslid);
                    //删除证书关系数据
                    bdcZsService.deleteZsXmGx(gzlslid);
                }
            }else{
                LOGGER.info("删除证书操作，xmid:{}",list.get(0).getXmid());
                List<String> xmids = list.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                for (BdcXmDO bdcXmDO : list) {
                    BdcXmZsGxDO bdcXmZsGxDO = new BdcXmZsGxDO();
                    bdcXmZsGxDO.setXmid(bdcXmDO.getXmid());
                    List<BdcXmZsGxDO> bdcXmZsGxList = entityMapper.selectByObj(bdcXmZsGxDO);
                    if (CollectionUtils.isNotEmpty(bdcXmZsGxList)) {
                        //将证书项目关系增加到删除集合中
                        deleteList.addAll(bdcXmZsGxList);
                        //循环处理
                        for (BdcXmZsGxDO bdcXmZsGx : bdcXmZsGxList) {
                            //查询证书是否有其他项目关联
                            BdcXmZsGxDO bdcXmZs = new BdcXmZsGxDO();
                            bdcXmZs.setZsid(bdcXmZsGx.getZsid());
                            List<BdcXmZsGxDO> bdcXmZsList = entityMapper.selectByObj(bdcXmZs);
                            // 证书对应的所有 xmid 是否均包含在 xmids 中，包含则需要删除，用于处理一证多房的情况
                            List<String> zsXmidList = bdcXmZsList.stream().map(BdcXmZsGxDO::getXmid).collect(Collectors.toList());
                            if (bdcXmZsList.size() <= 1 || xmids.containsAll(zsXmidList)) {
                                BdcZsDO bdcZsDO = bdcZsService.queryBdcZsById(bdcXmZsGx.getZsid());
                                if (bdcZsDO != null) {
                                    //将证书增加到删除集合中
                                    deleteList.add(bdcZsDO);
                                }
                            }
                        }
                    }
                }
            }
        }
        return deleteList;
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
            BdcXmZsGxDO bdcXmZsGxDO = new BdcXmZsGxDO();
            bdcXmZsGxDO.setXmid(bdcXmDO.getXmid());
            List<BdcXmZsGxDO> bdcXmZsGxList = entityMapper.selectByObj(bdcXmZsGxDO);
            if (CollectionUtils.isNotEmpty(bdcXmZsGxList)) {
                //将证书项目关系增加到集合中
                initServiceDTO.setBdcXmZsGxList(bdcXmZsGxList);
                //证书集合
                List<BdcZsDO> listZs = new ArrayList<>();
                for (BdcXmZsGxDO bdcXmZsGx : bdcXmZsGxList) {
                    BdcZsDO bdcZsDO = bdcZsService.queryBdcZsById(bdcXmZsGx.getZsid());
                    if (bdcZsDO != null) {
                        //将证书增加到集合中
                        listZs.add(bdcZsDO);
                    }
                }
                initServiceDTO.setBdcZsList(listZs);
            }
        }
        return initServiceDTO;
    }


    /**
     * 初始化不动产权证接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回证信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    public abstract List<BdcZsDO> initZsxx(InitServiceQO initServiceQO) throws Exception;

    @Override
    public String getVersion() {
        return null;
    }
}
