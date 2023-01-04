package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.qo.inquiry.BdcHzdytjQO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.inquiry.service.BdcHzdytjService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 汇总抵押统计
 */
@Service
public class BdcHzdytjServiceImpl implements BdcHzdytjService {

    @Autowired
    private Repo repo;

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * List
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计
     */
    @Override
    public List listBdcHzdytj(String bdcHzdytjQOJson) {

        return repo.selectList("listBdcHzdytj", JSON.parseObject(bdcHzdytjQOJson, BdcHzdytjQO.class));
    }

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * List
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计
     */
    @Override
    public List listBdcHzdytjBb(String bdcHzdytjQOJson) {

        return repo.selectList("listBdcHzdytjBb", JSON.parseObject(bdcHzdytjQOJson, BdcHzdytjQO.class));
    }


    /**
     * @param bdcHzdytjQOJson 查询Qo
     * List
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计count值统计
     */
    @Override
    public List listBdcCount(String bdcHzdytjQOJson) {

        return repo.selectList("listBdcDytjCount", JSON.parseObject(bdcHzdytjQOJson, BdcHzdytjQO.class));
    }

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * List
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计count值统计 蚌埠版
     */
    @Override
    public List listBdcCountBb(String bdcHzdytjQOJson) {

        return repo.selectList("listBdcDytjCountBb", JSON.parseObject(bdcHzdytjQOJson, BdcHzdytjQO.class));
    }

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计已竣工统计值
     */
    @Override
    public List listYjgCount(String bdcHzdytjQOJson) {

        return repo.selectList("listYjgCount", JSON.parseObject(bdcHzdytjQOJson, BdcHzdytjQO.class));
    }

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计已竣工统计值 蚌埠版
     */
    @Override
    public List listYjgCountBb(String bdcHzdytjQOJson) {

        return repo.selectList("listYjgCountBb", JSON.parseObject(bdcHzdytjQOJson, BdcHzdytjQO.class));
    }



    /**
     * @param bdcHzdytjQOJson 查询Qo
     * List
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ybb抵押统计
     */
    @Override
    public List listBdcYbbdytj(String bdcHzdytjQOJson) {

        return repo.selectList("listBdcYbbdytj", JSON.parseObject(bdcHzdytjQOJson, BdcHzdytjQO.class));
    }

}
