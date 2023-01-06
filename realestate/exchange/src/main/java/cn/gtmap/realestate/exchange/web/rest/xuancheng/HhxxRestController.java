package cn.gtmap.realestate.exchange.web.rest.xuancheng;


import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhSdk.HhxxSdk;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx.HhxxResponseBody;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.exchange.XuanchengHhxxRestService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0  2022-12-19
 * @description 宣城火化信息
 */
@RestController
@Api(tags = "宣城火化信息")
public class HhxxRestController implements XuanchengHhxxRestService {

    private static Logger LOGGER = LoggerFactory.getLogger(HhxxRestController.class);


    @Override
    public String hhxx(@RequestBody String jsonStr) {
        try {
            HhxxResponseBody responseDTO = new HhxxResponseBody();
            // 创建 app对象
            HhxxSdk app = new HhxxSdk();

            //调用api
            ApiResponse apiResponse = app.hhxx(jsonStr);

            // 处理执行结果
            if (apiResponse != null && apiResponse.getBody() != null) {
                LOGGER.info("宣城火化信息, 执行结果响应结果apiResponse: {}", JSONObject.toJSONString(apiResponse));
                String response = new String(apiResponse.getBody(), "UTF-8");
//                String response = new String("{\"resultCode\":\"00\",\"resultData\":[{\"SWRQ\":\"2020-09-03\",\"SZXB\":\"男\",\"SZHJDZ\":\"袁家庄组\",\"HHKSSJ\":\"2020-09-07 07:44:00\",\"SZZJH\":\"342524195602023236\",\"SZXM\":\"汤小春\"}],\"resultInfo\":\"请求成功\",\"resultTotal\":999}");

            	LOGGER.info("宣城火化信息, 执行结果响应数据:{}", response);
            	responseDTO = JSONObject.parseObject(response, HhxxResponseBody.class);
            }
            return JSON.toJSONString(responseDTO);

        } catch (Exception e){
            LOGGER.info("宣城火化信息出错了:{},{}", e.getMessage(),e);
            throw new AppException(e.getMessage());
        }
    }
}
