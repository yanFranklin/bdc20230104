package cn.gtmap.realestate.exchange.service.impl.inf.changzhou;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.core.domain.ViewJyQlrDO;
import cn.gtmap.realestate.exchange.core.dto.changzhou.jyj.ChangZhouJyjCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.changzhou.jyj.ChangZhouJyjCqcxDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.ViewMapper;
import cn.gtmap.realestate.exchange.core.qo.JyjCqcxDataQO;
import cn.gtmap.realestate.exchange.core.qo.JyjCqcxQO;
import cn.gtmap.realestate.exchange.service.inf.changzhou.ChangeZhouJyjService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ChangeZhouJyjServiceImpl implements ChangeZhouJyjService {

    private static final Logger logger = LoggerFactory.getLogger(ChangeZhouJyjService.class);

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ViewMapper viewMapper;

    @Override
    public ChangZhouJyjCommonResponse<String> checkDlcqValidity(List<JSONObject> changZhouJyjCqcxVOList) {
        logger.info("checkDlcqValidity入参:{}", JSON.toJSONString(changZhouJyjCqcxVOList));
        //校验入参
        if (CollectionUtils.isEmpty(changZhouJyjCqcxVOList) || changZhouJyjCqcxVOList.size() <= 1) {
            logger.info("入参有误");
            return ChangZhouJyjCommonResponse.fail("请至少填写两个权利人信息");
        }
        //判断是否独立产权
        Set<String> zlSet = new HashSet<>();
        changZhouJyjCqcxVOList.forEach(jsonObject -> {
            Example example = new Example(ViewJyQlrDO.class);
            example.createCriteria().andEqualTo("cqrXm", jsonObject.getString("cqrXm")).andEqualTo("cqrZjhm", jsonObject.getString("cqrZjhm"));
            List<ViewJyQlrDO> viewJyQlrDOS = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(viewJyQlrDOS)) {
                logger.info("viewJyQlrDOS:{}", JSON.toJSONString(viewJyQlrDOS));
                viewJyQlrDOS.forEach(viewJyQlrDO -> {
                    if (StringUtils.isNotBlank(viewJyQlrDO.getFwzl())) {
                        zlSet.add(viewJyQlrDO.getFwzl());
                    }
                });
            }
        });
        AtomicBoolean flag = new AtomicBoolean(false);
        if (CollectionUtils.isNotEmpty(zlSet)) {
            logger.info("zlSet:{}", JSON.toJSONString(zlSet));
            Iterator<String> iterator = zlSet.iterator();
            while (iterator.hasNext()){
                String fwzl = iterator.next();
                AtomicBoolean tempFlag = new AtomicBoolean(true);
                Set<String> cqrZjhmSet = new HashSet<>();
//                Example qlrExample = new Example(ViewJyQlrDO.class);
//                qlrExample.createCriteria().andLike("cqzh", cqzh);
                Map map = new HashMap(1);
                map.put("fwzl", fwzl);
//                map.put("fwzl",cqzh);
                logger.info("1===============1");
                List<ViewJyQlrDO> jyQlrDOS = viewMapper.getViewJyQlrByZl(map);
//                List<ViewJyQlrDO> jyQlrDOS = entityMapper.selectByExample(qlrExample);
                logger.info("1===============2");
                jyQlrDOS.forEach(viewJyQlrDO -> {
                    cqrZjhmSet.add(viewJyQlrDO.getCqrZjhm());
                });
                HashSet<String> collect = changZhouJyjCqcxVOList.stream().collect(HashSet::new, (item1, item2) -> item1.add(item2.getString("cqrZjhm")), HashSet::addAll);
                logger.info("collect:{}", JSON.toJSONString(collect));
                cqrZjhmSet.forEach(cqrZjhm -> {
                    if (!collect.contains(cqrZjhm)) {
                        tempFlag.set(false);
                    }
                });
                if (tempFlag.get()){
                    flag.set(true);
                    break;
                }
            }
        } else {
            logger.info("没查询到产权信息");
            return ChangZhouJyjCommonResponse.fail("否");
        }

        if (flag.get()) {
            return ChangZhouJyjCommonResponse.ok("是");
        } else {
            return ChangZhouJyjCommonResponse.fail("否");
        }
    }

    @Override
    public CommonResponse checkSfdlcq(JyjCqcxQO jyjCqcxQO) {
        //校验入参
        if (null == jyjCqcxQO || CollectionUtils.isEmpty(jyjCqcxQO.getData()) || jyjCqcxQO.getData().size() <= 1) {
            return CommonResponse.fail("参数传递为空或未传递两个权利人");
        }
        List<JyjCqcxDataQO> jyjCqcxDataQOList = jyjCqcxQO.getData();
        //判断是否独立产权
        Example example = new Example(ViewJyQlrDO.class);
        example.createCriteria().andEqualTo("cqzh", jyjCqcxDataQOList.get(0).getCqzh());
        List<ViewJyQlrDO> viewJyQlrDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(viewJyQlrDOList)) {
            HashSet<String> collect = jyjCqcxDataQOList.stream().collect(HashSet::new, (item1, item2) -> item1.add(item2.getCqrZjhm()), HashSet::addAll);
            Optional<ViewJyQlrDO> first = viewJyQlrDOList.stream().filter(item -> collect.contains(item.getCqrZjhm())).findFirst();
            if (first.isPresent()) {
                Map map = new HashMap(1);
                map.put("fwzl", first.get().getFwzl());
//                map.put("fwzl",cqzh);
                List<ViewJyQlrDO> jyjQlrDOS = viewMapper.getViewJyQlrByZl(map);
//                if (jyjQlrDOS.size() <= changZhouJyjCqcxVOList.size()) {
                AtomicBoolean tempFlag = new AtomicBoolean(true);
                jyjQlrDOS.forEach(viewJyQlrDO -> {
                    if (!collect.contains(viewJyQlrDO.getCqrZjhm())) {
                        tempFlag.set(false);
                    }
                });
                if (tempFlag.get()) {
                    return CommonResponse.ok(ChangZhouJyjCqcxDTO.ChangZhouJyjCqcxDTOBuilder.aChangZhouJyjCqcxDTO().withFwzl(jyjQlrDOS.get(0).getFwzl()).withGhyt(jyjQlrDOS.get(0).getGhyt()).withScjzmj(jyjQlrDOS.get(0).getScjzmj()).build());
                }
//                }
            }
        } else {
            return CommonResponse.fail("非家庭独有产权或证号有误");
        }
        return CommonResponse.fail("非家庭独有产权或证号有误");
    }

    @Override
    public CommonResponse checkSfycq(List<JSONObject> changZhouJyjCqcxVOList) {
        logger.info("checkDlcqValidity入参:{}", JSON.toJSONString(changZhouJyjCqcxVOList));
        //校验入参
        if (CollectionUtils.isEmpty(changZhouJyjCqcxVOList) || changZhouJyjCqcxVOList.size() <= 1) {
            return CommonResponse.fail("参数传递为空或未传递两个权利人");
        }
        //判断是否存在产权
        Example example = new Example(ViewJyQlrDO.class);
        example.createCriteria().andEqualTo("cqzh", changZhouJyjCqcxVOList.get(0).getString("cqzh"));
        List<ViewJyQlrDO> viewJyQlrDOList = entityMapper.selectByExample(example);
//        logger.info("viewJyQlrDOList:{}", JSON.toJSONString(viewJyQlrDOList));
        if (CollectionUtils.isNotEmpty(viewJyQlrDOList)) {
            HashSet<String> requestQlrZjhSet = changZhouJyjCqcxVOList.stream().collect(HashSet::new, (item1, item2) -> item1.add(item2.getString("cqrZjhm")), HashSet::addAll);
            Optional<ViewJyQlrDO> first = viewJyQlrDOList.stream().filter(item -> requestQlrZjhSet.contains(item.getCqrZjhm())).findFirst();
            if (first.isPresent()) {
//                logger.info("first.get():{}", JSON.toJSONString(first.get()));
                Map map = new HashMap(1);
                map.put("fwzl", first.get().getFwzl());
//                map.put("fwzl",cqzh);
                List<ViewJyQlrDO> jyjQlrDOS = viewMapper.getViewJyQlrByZl(map);
                if (CollectionUtils.isNotEmpty(jyjQlrDOS)) {
                    HashSet<String> collect = jyjQlrDOS.stream().collect(HashSet::new, (item1, item2) -> item1.add(item2.getCqrZjhm()), HashSet::addAll);
//                    logger.info("collect:{}", JSON.toJSONString(collect));
                    AtomicBoolean tempFlag = new AtomicBoolean(false);
                    for (JSONObject changZhouJyjCqcxVO : changZhouJyjCqcxVOList) {
                        if (collect.contains(changZhouJyjCqcxVO.getString("cqrZjhm"))) {
                            tempFlag.set(true);
                            break;
                        }
                    }
                    if (tempFlag.get()) {
                        return CommonResponse.ok(ChangZhouJyjCqcxDTO.ChangZhouJyjCqcxDTOBuilder.aChangZhouJyjCqcxDTO().withFwzl(jyjQlrDOS.get(0).getFwzl()).withGhyt(jyjQlrDOS.get(0).getGhyt()).withScjzmj(jyjQlrDOS.get(0).getScjzmj()).build());
                    }
                }
            }
        } else {
            return CommonResponse.fail("证号有误");
        }
        return CommonResponse.fail("证号有误");
    }
}
