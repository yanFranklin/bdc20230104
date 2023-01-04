package cn.gtmap.realestate.init.core.thread;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.zsxx.InitBdcqzAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 初始化证书多线程
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/11.
 * @description
 */
public class InitZsThread extends CommonThread{

    /** 项目初始化开关数据*/
    private BdcCshFwkgSlDO bdcCshFwkgSl;
    /**批量组合业务 挂接证书的项目id和zsxh等数据*/
    private List<BdcCshFwkgSlDO> zhXmGjzsList;
    /**存储zsxh和证书对象的值*/
    private Map<String, List<BdcZsDO>> zsidMap;
    //存储zsid和证书模板对象的关系
    private Map<String, BdcZsDO> xmidZsmbMap;
    /**工厂*/
    private InitBeanFactory initBeanFactory;
    /**组合需生成证书的项目*/
    private Map<Integer, String> sczsMap;
    /**证书和权利数据集合*/
    private Map<String, InitServiceDTO> zsDataMap;
    /**是否查询证书数量*/
    private boolean zssl;
    /**是否证书预览*/
    private boolean zsyl;
    /**是否数据补录*/
    private boolean sjbl;
    /**
     * 构造函数
     * @param bdcCshFwkgSl
     * @param zhXmGjzsList
     * @param zsidMap
     * @param sczsMap
     * @param zsDataMap
     * @param initBeanFactory
     */
    public InitZsThread(BdcCshFwkgSlDO bdcCshFwkgSl,
                        List<BdcCshFwkgSlDO> zhXmGjzsList,
                        Map<String, List<BdcZsDO>> zsidMap,
                        Map<Integer, String> sczsMap,
                        Map<String, InitServiceDTO> zsDataMap,
                        InitBeanFactory initBeanFactory,
                        boolean zssl,
                        boolean zsyl,
                        boolean sjbl,
                        Map<String, BdcZsDO> xmidZsmbMap){
        this.bdcCshFwkgSl=bdcCshFwkgSl;
        this.zhXmGjzsList=zhXmGjzsList;
        this.zsidMap=zsidMap;
        this.initBeanFactory=initBeanFactory;
        this.sczsMap=sczsMap;
        this.zsDataMap=zsDataMap;
        this.zssl=zssl;
        this.zsyl=zsyl;
        this.sjbl=sjbl;
        this.xmidZsmbMap=xmidZsmbMap;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        InitServiceDTO initServiceDTO = new InitServiceDTO();
        InitServiceQO initServiceQO = new InitServiceQO();
        //初始化实例赋值
        initServiceQO.setBdcCshFwkgSl(bdcCshFwkgSl);
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(bdcCshFwkgSl.getId());
        //赋值id  初始化证书服务需要
        initServiceQO.setBdcXm(bdcXmDO);
        //是否查询证书数量
        initServiceQO.setZssl(zssl);
        //是否证书预览
        initServiceQO.setZsyl(zsyl);
        //是否数据补录
        initServiceQO.setSjbl(sjbl);
        //初始化证书信息
        Boolean plzhOne = false;
        //有值就是组合批量生成一本证书
        if (bdcCshFwkgSl.getZsxh() != null) {
            plzhOne = true;
        }
        //批量组合非主项目生成证书的跳出循环  是否主房
        if (plzhOne && !StringUtils.equals(sczsMap.get(bdcCshFwkgSl.getZsxh()), bdcCshFwkgSl.getId())) {
            zhXmGjzsList.add(bdcCshFwkgSl);
            return;
        }
        List<InitService> list = initBeanFactory.getTrafficMode(bdcCshFwkgSl, InitBdcqzAbstractService.class);
        //对应实现循环处理
        for (InitService service : list) {
            service.init(initServiceQO, initServiceDTO);
        }
        //批量组合生成一本证的获取生成的zsid
        if (plzhOne && CollectionUtils.isNotEmpty(initServiceDTO.getBdcZsList())) {
            zsidMap.put(bdcCshFwkgSl.getZsxh().toString(), initServiceDTO.getBdcZsList());
        }
        //循环存储模板和证书的关系
        for(BdcZsDO zs:initServiceDTO.getBdcZsList()){
            if(StringUtils.isNotBlank(zs.getZsid()) && initServiceQO.getBdcZsMbDO()!=null){
                xmidZsmbMap.put(zs.getZsid(),initServiceQO.getBdcZsMbDO());
            }
        }
        //存储xmid和初始化数据关系
        zsDataMap.put(bdcCshFwkgSl.getId(), initServiceDTO);
    }
}
