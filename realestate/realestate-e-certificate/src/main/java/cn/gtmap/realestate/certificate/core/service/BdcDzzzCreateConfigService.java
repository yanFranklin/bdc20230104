package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.service.create.BdcDzzzCreateService;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/20 电子证照创建配置接口
 */
public interface BdcDzzzCreateConfigService {

    /**
     * @param o
     * @return
     * @description 根据dwdm选择对应证照创建service
     */
    DzzzResponseModel createDzzz(Object o);

    /**
     * @param o
     * @return
     * @description 创建电子签章
     */
    DzzzResponseModel createDzqz(Object o);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 非登记业务数据无证书相关信息创建电子签章数据
     * @date : 2022/8/24 9:50
     */
    DzzzResponseModel createFdjywDzqz(Object o);

    /**
     * @param o
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送公告电子签章服务
     * @date : 2022/3/14 15:15
     */
    DzzzResponseModel createGgDzqz(Object o);

    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 通过配置的单位代码获取跳转的service
     */
    BdcDzzzCreateService getCreateDzzzService(String dwdm);


    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 上传请求参数中的图片到文件中心
     */
    DzzzResponseModel uploadFlImg(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上传到大云文件中心
     * @date : 2022/3/10 11:22
     */
    DzzzResponseModel uploadFlImgToStorage(BdcDzzzZzxx bdcDzzzZzxx);

    DzzzResponseModel updateDzzzSignAndUploadInfo(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * @param zzbs
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据签章标识获取文件信息
     * @date : 2022/3/15 14:31
     */
    StorageDto queryDzqzfj(String zzbs);

    /**
     * @param zzid
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据签章id查询
     * @date : 2022/3/15 14:31
     */
    String getQzxx(String zzid);

}
