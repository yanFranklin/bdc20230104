package cn.gtmap.realestate.certificate.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/19
 */
public class JaxbUtil {

    public static String javaToxml(Object o){
        // 构建输出环境
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            // 获取JAXB的上下文环境，需要传入具体的 Java bean -> 这里使用Student
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            // 创建 Marshaller 实例
            Marshaller marshaller = context.createMarshaller();
            // 设置转换参数 -> 这里举例是告诉序列化器是否格式化输出
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 将所需对象序列化 -> 该方法没有返回值
            marshaller.marshal(o, out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return StringUtil.byteToStrUtf8(out.toByteArray());
    }

    public static Object xmlTojava(Class<?> c, byte[] data){
        Object o = null;
        try {
            // 获取JAXB的上下文环境，需要传入具体的 Java bean ->
            JAXBContext context = JAXBContext.newInstance(c);
            // 创建 UnMarshaller 实例
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // 加载需要转换的XML数据 -> 这里使用InputStream，还可以使用File，Reader等
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            // 将XML数据序列化 -> 该方法的返回值为Object基类，需要强转类型
            o = unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return o;
    }
}
