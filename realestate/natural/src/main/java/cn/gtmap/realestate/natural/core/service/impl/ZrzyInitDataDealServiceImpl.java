package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitResultDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitServiceDTO;
import cn.gtmap.realestate.natural.core.service.ZrzyInitDataDealService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/27
 * @description
 */
@Service
public class ZrzyInitDataDealServiceImpl implements ZrzyInitDataDealService {

    @Override
    public ZrzyInitResultDTO dealServiceDTO(ZrzyInitServiceDTO zrzyInitServiceDTO, ZrzyInitResultDTO zrzyInitResultDTO) throws IllegalAccessException{
        //要封装的结构
        Field[] fields = ZrzyInitResultDTO.class.getDeclaredFields();
        for (Field resultField : fields) {
            resultField.setAccessible(true);
            Field[] dtoFields = ZrzyInitServiceDTO.class.getDeclaredFields();
            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);
                Object val = dtoField.get(zrzyInitServiceDTO);
                if (val != null) {
                    List list = (List) resultField.get(zrzyInitResultDTO);
                    //空值的话进行赋值
                    if (list == null) {
                        resultField.set(zrzyInitResultDTO, new ArrayList<>());
                    }
                    //比对转换
                    if (dtoField.getType() == List.class) {
                        if (StringUtils.equals(resultField.getGenericType().toString(), dtoField.getGenericType().toString())) {
                            ((List) resultField.get(zrzyInitResultDTO)).addAll((List) val);
                            break;
                        }
                    } else {
                        //实体对象名有相近的 通过>去判断
                        if (StringUtils.contains(resultField.getGenericType().getTypeName(), dtoField.getType().getName() + ">")) {
                            ((List) resultField.get(zrzyInitResultDTO)).add(val);
                            break;
                        }
                    }
                }
            }
        }
        return zrzyInitResultDTO;
    }
}
