package cn.gtmap.realestate.natural.service;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/2
 * @description 登簿信息服务接口
 */
public interface ZrzyDbxxService {
    /**
     * 登簿时更新当前项目（现势）
     * @param gzlslid         工作流实例ID
     * @param currentUserName 当前账户
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）
     */
    void updateZrzyDbxx(String gzlslid, String currentUserName);


    /**
     * 更新项目案件状态为2已完成状态，并更新项目结束时间
     * @param xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新项目案件状态为2已完成状态，并更新项目结束时间
     */
    void changeAjzt(String xmid);

}
