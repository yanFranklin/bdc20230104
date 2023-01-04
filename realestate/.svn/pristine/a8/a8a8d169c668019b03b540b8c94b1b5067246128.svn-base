package cn.gtmap.realestate.engine.config;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzService;
import cn.gtmap.realestate.engine.service.BdcGzDmService;
import cn.gtmap.realestate.engine.util.ResourceUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/02/18
 * @description  定时任务处理基本子规则
 *
 *  1、基本子规则：系统提供的基础子规则，不允许修改
 *  2、定时检测数据库是否存在基础子规则，没有则将缺失的插入，避免人为操作数据库子规则丢失
 */
@Component
public class BdcGzZgzTask {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzZgzTask.class);
    /**
     * 文件资源处理
     */
    @Autowired
    private ResourceUtil resourceUtil;
    /**
     * 子规则逻辑操作
     */
    @Autowired
    private BdcGzZgzService bdcGzZgzService;
    /**
     * 固化的基础子规则内容
     */
    private static String content;
    /**
     * 子规则动态代码缓存操作
     */
    @Autowired
    private BdcGzDmService bdcGzDmService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  每十分钟处理一次，且异步执行
     */
    @Async
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void resolveBdcZgz(){
        // 1、获取基础子规则（固化在文件中）
        if(StringUtils.isBlank(content)){
            content = resourceUtil.getBaseZgzContent();
            if(StringUtils.isBlank(content)){
                LOGGER.warn("规则子系统：未获取到基础子规则，处理任务终止！");
                return;
            }
        }

        // 2、转换为实体对象
        List<BdcGzZgzDTO> bdcGzZgzDTOList = JSON.parseArray(content, BdcGzZgzDTO.class);
        if(CollectionUtils.isEmpty(bdcGzZgzDTOList)){
            LOGGER.warn("规则子系统：未获取到基础子规则，处理任务终止！");
            return;
        }

        // 3、判断是否存在，进行子规则恢复操作
        bdcGzZgzDTOList.stream().forEach(baseZgzDTO -> {
            // 获取库中的对应ID子规则记录
            BdcGzZgzDTO dbZgzDTO = bdcGzZgzService.queryBdcGzZgzDTOByGzid(baseZgzDTO.getGzid());

            if(isZgzChanged(baseZgzDTO, dbZgzDTO)){
                if(null != dbZgzDTO) {
                    // 存在但改动，需要先删除
                    this.deleteBdcZgzDO(baseZgzDTO);
                }
                LOGGER.warn("规则子系统：基础子规则--{}-{}，进行新增操作！", baseZgzDTO.getGzid(), baseZgzDTO.getGzmc());
                bdcGzZgzService.insertBdcGzZgz(baseZgzDTO);
                // 刷新当前规则的动态代码预编译缓存
                bdcGzDmService.updateJavaCodeCompileCache(baseZgzDTO);
            }
        });
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param baseZgzDTO 基础子规则
     * @param dbZgzDTO 数据库中对应子规则
     * @return {boolean} 改动过：true，未改动：false
     * @description  判断基础子规则是否被修改了
     */
    private boolean isZgzChanged(BdcGzZgzDTO baseZgzDTO, BdcGzZgzDTO dbZgzDTO){
        if(null == dbZgzDTO){
            return true;
        }

        if(!StringUtils.equals(JSON.toJSONString(baseZgzDTO), JSON.toJSONString(dbZgzDTO))){
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param baseZgzDTO 基础子规则
     * @description  删除指定子规则
     */
    private void deleteBdcZgzDO(BdcGzZgzDTO baseZgzDTO) {
        LOGGER.warn("规则子系统：基础子规则--{}-{}-被改动过，先进行删除处理！", baseZgzDTO.getGzid(), baseZgzDTO.getGzmc());

        BdcGzZgzDO bdcGzZgzDO = new BdcGzZgzDO();
        bdcGzZgzDO.setGzid(baseZgzDTO.getGzid());
        bdcGzZgzService.deleteBdcGzZgz(bdcGzZgzDO);
    }
}
