package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcNtZhcxPzDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/18
 * @description （南通）综合查询配置项获取服务 （此地方需求更改大，直接全部可配置，避免代码来回改动）
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcNtZhcxPzController {
    /**
     * 有房证明模板名称
     */
    @Value("${nantong.zhcxzmmb.yfzm:yfzm.fr3}")
    private String zhcxzmmbYfzm;
    /**
     * 无房证明模板名称
     */
    @Value("${nantong.zhcxzmmb.wfzm:wfzm.fr3}")
    private String zhcxzmmbWfzm;
    /**
     * 海门 迁户口证明 有房模板名称
     */
    @Value("${haimen.qhkzm.yfzm:qhkzmyfzm.fr3}")
    private String zhcxzmmbQhkzmYfzm;
    /**
     * 海门 迁户口证明 有房模板名称
     */
    @Value("${haimen.qhkzm.wfzm:qhkzmwfzm.fr3}")
    private String zhcxzmmbQhkzmWfzm;
    /**
     * 有房证明（企业）模板名称
     */
    @Value("${nantong.zhcxzmmb.yfzmqy:yfzmqy.fr3}")
    private String zhcxzmmbYfzmqy;
    /**
     * 无房证明（企业）模板名称
     */
    @Value("${nantong.zhcxzmmb.wfzmqy:wfzmqy.fr3}")
    private String zhcxzmmbWfzmqy;
    /**
     * 不为申请人所有证明
     */
    @Value("${nantong.zhcxzmmb.bwsqrsyzm:bwsqrsyzm.fr3}")
    private String zhcxzmmbBwsqrsyzm;
    /**
     * 已出售证明
     */
    @Value("${nantong.zhcxzmmb.ycszm:ycszm.fr3}")
    private String zhcxzmmbYcszm;
    /**
     * 无不动产登记资料证明
     */
    @Value("${nantong.zhcxzmmb.wbdcdjzlzm:wbdcdjzlzm.fr3}")
    private String zhcxzmmbWbdcdjzlzm;
    /**
     * 贷款证明
     */
    @Value("${nantong.zhcxzmmb.dkzm:dkzm.fr3}")
    private String zhcxzmmbDkzm;
    /**
     * 座落变更证明（原座落）
     */
    @Value("${nantong.zhcxzmmb.zlbgzmyzl:zlbgzmyzl.fr3}")
    private String zhcxzmmbZlbgzmyzl;
    /**
     * 座落变更证明（现座落）
     */
    @Value("${nantong.zhcxzmmb.zlbgzmxzl:zlbgzmxzl.fr3}")
    private String zhcxzmmbZlbgzmxzl;
    /**
     * 其他证明
     */
    @Value("${nantong.zhcxzmmb.qtzm:qtzm.fr3}")
    private String zhcxzmmbQtzm;

    /**
     * 证明内容显示部分1  不为申请人所有证明
     */
    @Value("${nantong.zmnr1.bwsqrsyzm:}")
    private String zmnr1Bwsqrsyzm;
    /**
     * 证明内容显示部分1  已出售证明
     */
    @Value("${nantong.zmnr1.ycszm:}")
    private String zmnr1Ycszm;
    /**
     * 证明内容显示部分1  无不动产登记资料证明
     */
    @Value("${nantong.zmnr1.wbdcdjzlzm:}")
    private String zmnr1Wbdcdjzlzm;
    /**
     * 证明内容显示部分1  贷款证明
     */
    @Value("${nantong.zmnr1.dkzm:}")
    private String zmnr1Dkzm;
    /**
     * 证明内容显示部分1  座落变更证明（原座落）
     */
    @Value("${nantong.zmnr1.zlbgzmyzl:}")
    private String zmnr1Zlbgzmyzl;
    /**
     * 证明内容显示部分1  座落变更证明（现座落）
     */
    @Value("${nantong.zmnr1.zlbgzmxzl:}")
    private String zmnr1Zlbgzmxzl;
    /**
     * 证明内容显示部分1  其他证明
     */
    @Value("${nantong.zmnr1.qtzm:}")
    private String zmnr1Qtzm;

    /**
     * 证明内容显示部分2  不为申请人所有证明
     */
    @Value("${nantong.zmnr2.bwsqrsyzm:}")
    private String zmnr2Bwsqrsyzm;
    /**
     * 证明内容显示部分2  已出售证明
     */
    @Value("${nantong.zmnr2.ycszm:}")
    private String zmnr2Ycszm;
    /**
     * 证明内容显示部分2  无不动产登记资料证明
     */
    @Value("${nantong.zmnr2.wbdcdjzlzm:}")
    private String zmnr2Wbdcdjzlzm;
    /**
     * 证明内容显示部分2  贷款证明
     */
    @Value("${nantong.zmnr2.dkzm:}")
    private String zmnr2Dkzm;
    /**
     * 证明内容显示部分2  座落变更证明（原座落）
     */
    @Value("${nantong.zmnr2.zlbgzmyzl:}")
    private String zmnr2Zlbgzmyzl;
    /**
     * 证明内容显示部分2  座落变更证明（现座落）
     */
    @Value("${nantong.zmnr2.zlbgzmxzl:}")
    private String zmnr2Zlbgzmxzl;
    /**
     * 证明内容显示部分2  其他证明
     */
    @Value("${nantong.zmnr2.qtzm:}")
    private String zmnr2Qtzm;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取（南通）综合查询配置项
     */
    @GetMapping("/zhcx/pz")
    public BdcNtZhcxPzDTO getZhcxPz(){
        BdcNtZhcxPzDTO bdcNtZhcxPzDTO = new BdcNtZhcxPzDTO();
        bdcNtZhcxPzDTO.setZhcxzmmbYfzm(zhcxzmmbYfzm);
        bdcNtZhcxPzDTO.setZhcxzmmbWfzm(zhcxzmmbWfzm);
        bdcNtZhcxPzDTO.setZhcxzmmbQhkzmYfzm(zhcxzmmbQhkzmYfzm);
        bdcNtZhcxPzDTO.setZhcxzmmbQhkzmWfzm(zhcxzmmbQhkzmWfzm);
        bdcNtZhcxPzDTO.setZhcxzmmbYfzmqy(zhcxzmmbYfzmqy);
        bdcNtZhcxPzDTO.setZhcxzmmbWfzmqy(zhcxzmmbWfzmqy);
        bdcNtZhcxPzDTO.setZhcxzmmbBwsqrsyzm(zhcxzmmbBwsqrsyzm);
        bdcNtZhcxPzDTO.setZhcxzmmbYcszm(zhcxzmmbYcszm);
        bdcNtZhcxPzDTO.setZhcxzmmbWbdcdjzlzm(zhcxzmmbWbdcdjzlzm);
        bdcNtZhcxPzDTO.setZhcxzmmbDkzm(zhcxzmmbDkzm);
        bdcNtZhcxPzDTO.setZhcxzmmbZlbgzmyzl(zhcxzmmbZlbgzmyzl);
        bdcNtZhcxPzDTO.setZhcxzmmbZlbgzmxzl(zhcxzmmbZlbgzmxzl);
        bdcNtZhcxPzDTO.setZhcxzmmbQtzm(zhcxzmmbQtzm);
        bdcNtZhcxPzDTO.setZmnr1Bwsqrsyzm(zmnr1Bwsqrsyzm);
        bdcNtZhcxPzDTO.setZmnr1Ycszm(zmnr1Ycszm);
        bdcNtZhcxPzDTO.setZmnr1Wbdcdjzlzm(zmnr1Wbdcdjzlzm);
        bdcNtZhcxPzDTO.setZmnr1Dkzm(zmnr1Dkzm);
        bdcNtZhcxPzDTO.setZmnr1Zlbgzmyzl(zmnr1Zlbgzmyzl);
        bdcNtZhcxPzDTO.setZmnr1Zlbgzmxzl(zmnr1Zlbgzmxzl);
        bdcNtZhcxPzDTO.setZmnr1Qtzm(zmnr1Qtzm);
        bdcNtZhcxPzDTO.setZmnr2Bwsqrsyzm(zmnr2Bwsqrsyzm);
        bdcNtZhcxPzDTO.setZmnr2Ycszm(zmnr2Ycszm);
        bdcNtZhcxPzDTO.setZmnr2Wbdcdjzlzm(zmnr2Wbdcdjzlzm);
        bdcNtZhcxPzDTO.setZmnr2Dkzm(zmnr2Dkzm);
        bdcNtZhcxPzDTO.setZmnr2Zlbgzmyzl(zmnr2Zlbgzmyzl);
        bdcNtZhcxPzDTO.setZmnr2Zlbgzmxzl(zmnr2Zlbgzmxzl);
        bdcNtZhcxPzDTO.setZmnr2Qtzm(zmnr2Qtzm);
        return bdcNtZhcxPzDTO;
    }

}
