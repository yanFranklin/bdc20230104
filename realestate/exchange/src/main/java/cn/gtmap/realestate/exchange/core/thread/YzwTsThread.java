package cn.gtmap.realestate.exchange.core.thread;

import cn.gtmap.realestate.common.core.domain.exchange.yzw.InfApplyDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwTsZjkService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 *  一张网推送
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020/11/27.
 * @description
 */
public class YzwTsThread extends CommonThread{


    /**
     * 参数 一张网编号
     */
    private String yzwbh;


    /**
     * 服务
     */
    private YzwTsZjkService yzwTsZjkService;

    /**
     * 返回结果
     */
    private List<String> yzwbhList;

    /**
     * 构造函数
     * @param yzwTsZjkService
     * @param yzwbh
     */
    public YzwTsThread(YzwTsZjkService yzwTsZjkService, String yzwbh){
        this.yzwTsZjkService=yzwTsZjkService;
        this.yzwbh=yzwbh;
    }


    public List<String> getYzwbhList() {
        return yzwbhList;
    }

    public void setYzwbhList(List<String> yzwbhList) {
        this.yzwbhList = yzwbhList;
    }

    public String getYzwbh() {
        return yzwbh;
    }

    public void setYzwbh(String yzwbh) {
        this.yzwbh = yzwbh;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        if(StringUtils.isNotBlank(yzwbh)){
            InfApplyDO infApplyDO =new InfApplyDO();
            infApplyDO.setInternalNo(yzwbh);
            yzwbhList =yzwTsZjkService.checkTsqzk(infApplyDO);
        }
    }


}
