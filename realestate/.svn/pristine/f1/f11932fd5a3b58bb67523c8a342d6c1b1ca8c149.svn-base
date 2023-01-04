package cn.gtmap.realestate.init.core.thread;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 初始化的多线程服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/11.
 * @description
 */
public class InitThread extends CommonThread{
    /**
     * 初始化的项目集合
     */
    private Collection<InitServiceQO> groupList;
    /**
     * 初始化工厂类
     */
    private InitBeanFactory initBeanFactory;
    /**
     * 存储初始化后的项目ID对应的初始化数据
     */
    private Map<String, InitServiceDTO> serviceDataMap;
    /**
     * 初始化的相关服务
     */
    private List<Class> clzList;

    /**
     * 构造函数
     * @param groupList
     * @param serviceDataMap
     * @param initBeanFactory
     */
    public  InitThread(Collection<InitServiceQO> groupList, Map<String, InitServiceDTO> serviceDataMap,InitBeanFactory initBeanFactory,List<Class> clzList){
        this.groupList=groupList;
        this.serviceDataMap=serviceDataMap;
        this.initBeanFactory=initBeanFactory;
        this.clzList=clzList;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        if(CollectionUtils.isNotEmpty(groupList)){
            //做排序
            List<InitServiceQO> listQO=new ArrayList<>(groupList);
            listQO.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
            for(InitServiceQO initServiceQO:listQO){
                InitServiceDTO initServiceDTO = new InitServiceDTO();
                //放到集合里
                serviceDataMap.put(initServiceQO.getXmid(), initServiceDTO);
                //将初始化后的数据放入QO中  有读取上一手数据的会从里边取
                initServiceQO.setServiceDataMap(serviceDataMap);
                //开关放到对象中
                initServiceDTO.setBdcCshFwkgSlDO(initServiceQO.getBdcCshFwkgSl());
                //新建对象 如果受理传递了权利人或者义务人数据，并且开关没配置的给予默认值
                BdcCshFwkgSlDO bdcCshFwkgSlDO=new BdcCshFwkgSlDO();
                BeanUtils.copyProperties(initServiceQO.getBdcCshFwkgSl(),bdcCshFwkgSlDO);
                if(CollectionUtils.isNotEmpty(initServiceQO.getBdcQlrList()) && bdcCshFwkgSlDO.getQlrsjly()==null){
                    bdcCshFwkgSlDO.setQlrsjly(Constants.QLRSJLY_YQLR);
                }
                if(CollectionUtils.isNotEmpty(initServiceQO.getBdcYwrList()) && bdcCshFwkgSlDO.getYwrsjly()==null){
                    bdcCshFwkgSlDO.setYwrsjly(Constants.QLRSJLY_YQLR);
                }
                //初始化加载类循环
                for (Class clz : clzList) {
                    List<InitService> list = initBeanFactory.getTrafficMode(bdcCshFwkgSlDO, clz);
                    //对应实现循环处理
                    for (InitService service : list) {
                        service.init(initServiceQO, initServiceDTO);
                    }
                }
                //抓取楼盘表数据不做实例表的处理
                if(initServiceQO.isSfzqlpbsj()){
                    //开关放到对象中
                    initServiceDTO.setBdcCshFwkgSlDO(null);
                }
            }
        }
    }
}
