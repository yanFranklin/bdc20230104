package cn.gtmap.realestate.exchange.core.thread;

import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.exchange.core.dto.yzw.YzwCheckAndTsResultDTO;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwCheckService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 *  一张网验证
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020/11/27.
 * @description
 */
public class YzwCheckThread extends CommonThread{


    /**
     * 参数 一张网编号
     */
    private String yzwbh;

    /**
     * 参数 验证类型
     */
    private List<String> yzlxList;

    /**
     * 服务
     */
    private YzwCheckService yzwCheckService;

    /**
     * 返回结果
     */
    private YzwCheckAndTsResultDTO yzwCheckAndTsResultDTO;

    /**
     * 构造函数
     * @param yzwCheckService
     * @param yzwbh
     */
    public YzwCheckThread(YzwCheckService yzwCheckService, String yzwbh,List<String> yzlxList){
        this.yzwCheckService=yzwCheckService;
        this.yzwbh=yzwbh;
        this.yzlxList=yzlxList;
    }


    public YzwCheckAndTsResultDTO getYzwCheckAndTsResultDTO() {
        return yzwCheckAndTsResultDTO;
    }

    public void setYzwCheckAndTsResultDTO(YzwCheckAndTsResultDTO yzwCheckAndTsResultDTO) {
        this.yzwCheckAndTsResultDTO = yzwCheckAndTsResultDTO;
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
            yzwCheckAndTsResultDTO = yzwCheckService.checkYzwData(yzwbh,yzlxList);
        }
    }


}
