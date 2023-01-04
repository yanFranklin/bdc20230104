package cn.gtmap.realestate.common.core.service.rest.natural;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description
 */
public interface ZrzyEntityRestService {

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param json  实体数据JSON
     * @param className 对应实体类限定名
     * @return  {int} 更新记录数量
     * @description
     *  <p>
     *      更新实体部分属性数据值 <br>
     *      1、这里需要注意json数据采用@RequestBody方式提交，不用@RequestParam，因为URL后缀参数长度有限制 <br>
     *      2、因为FeignClient不能有多个@RequestBody，所以class改为传名称，再逻辑处理一下获取类限定名 <br>
     *  </p>
     */
    @PutMapping(value= "/realestate-natural/rest/v1.0/entity")
    int updateByJsonEntity(@RequestBody String json, @RequestParam("className") String className);
}
