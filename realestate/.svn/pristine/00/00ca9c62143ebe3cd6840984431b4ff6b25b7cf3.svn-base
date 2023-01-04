package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGrzfCxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcGrzfQO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.service.BdcGrzfService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/10/4 16:41
 * @description 个人住房查询
 */
@Service
public class BdcGrzfServiceImpl implements BdcGrzfService {

    public static final Logger logger = LoggerFactory.getLogger(BdcGrzfServiceImpl.class);

    /**
     * ORM操作
     */
    @Autowired
    private Repo repo;

    /**
     * 个人住房查询
     *
     * @param grzfQO grzfQO
     * @return BdcGrzfCxDTO BdcGrzfCxDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<BdcGrzfCxDTO> listGrzfByPage(Pageable pageable, BdcGrzfQO grzfQO) {
        return repo.selectPaging("listGrzfByPageOrder", this.resolveParam(grzfQO), pageable);
    }


    /**
     * @param grzfQO 查询条件
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 处理查询参数
     */
    private BdcGrzfQO resolveParam(BdcGrzfQO grzfQO) {
        // 处理身份证号:18位和15位转换；其他证件号：统一返回大写
        /// 权利人证件号
        if (null != grzfQO.getZjh() && grzfQO.getZjh().length > 0) {
            grzfQO.setZjh(Stream.of(grzfQO.getZjh()).map(e ->
                    CardNumberTransformation.zjhTransformation(e)).collect(Collectors.joining(",")).split(","));
        }

        if (null != grzfQO.getQlrmc() && grzfQO.getQlrmc().length > 0) {
            for (int i = 0; i < grzfQO.getQlrmc().length; i++) {
                grzfQO.getQlrmc()[i] = StringToolUtils.replaceBracket(grzfQO.getQlrmc()[i]);
            }
        }
        if (null != grzfQO.getZjh() && grzfQO.getZjh().length > 0) {
            for (int i = 0; i < grzfQO.getZjh().length; i++) {
                grzfQO.getZjh()[i] = StringToolUtils.replaceBracket(grzfQO.getZjh()[i]);
            }
        }

        return grzfQO;
    }
}
