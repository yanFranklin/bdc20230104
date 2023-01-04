package cn.gtmap.realestate.exchange.service.national;


import cn.gtmap.realestate.common.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;

/**
 * 国家级平台接入数据-业务报文头信息
 */
public interface NationalAccessHeadModelService {

    /**
     * zdd 根据项目业务号获取HeadModel
     *
     * @param ywh    业务号
     * @param sfwlxm 是否外联项目
     * @return
     */
    HeadModel getAccessHeadModel(String ywh, boolean sfwlxm);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 为生产xmid 数据的情况下组织headModel进行xsd校验
     * @date : 2022/11/21 16:50
     */
    HeadModel generateHeadModel(QjsjjcDTO qjsjjcDTO);
}
