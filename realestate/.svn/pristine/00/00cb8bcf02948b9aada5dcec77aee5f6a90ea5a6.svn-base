package cn.gtmap.realestate.register.core.thread;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.register.service.BdcPrintService;
import groovy.util.Node;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/15
 * @description
 */
public class PageStringThread extends CommonThread {
    /**
     * 打印业务类
     */
    BdcPrintService bdcPrintService;
    /**
     * 主表打印配置
     */
    List<BdcDysjPzDO> bdcDysjPzDOS;
    /**
     * 字表打印配置
     */
    List<BdcDysjZbPzDO> bdcDysjZbPzDOS;
    /**
     * xml节点
     */
    Node xmlNode;
    /**
     * 需要生成打印xml的参数
     */
    Map configParam;
    /**
     * 需要返回的参数
     */
    String pageString;


    public PageStringThread(BdcPrintService bdcPrintService,List<BdcDysjPzDO> bdcDysjPzDOS, List<BdcDysjZbPzDO> bdcDysjZbPzDOS, Node xmlNode, Map configParam) {
        this.bdcPrintService=bdcPrintService;
        this.bdcDysjPzDOS = bdcDysjPzDOS;
        this.bdcDysjZbPzDOS = bdcDysjZbPzDOS;
        this.xmlNode = xmlNode;
        this.configParam = configParam;

    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        pageString = bdcPrintService.getPage(bdcDysjPzDOS, bdcDysjZbPzDOS, xmlNode, configParam,Boolean.TRUE);
    }

    public String getPageString() {
        return pageString;
    }
}
