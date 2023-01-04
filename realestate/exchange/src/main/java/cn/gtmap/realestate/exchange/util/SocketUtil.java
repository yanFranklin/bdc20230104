package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

public class SocketUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketUtil.class);


    public static void writeStr2Stream(String str, OutputStream out) throws IOException {

        // 创建写出buffer
        BufferedOutputStream writer = new BufferedOutputStream(out);
        try {
            writer.write(str.getBytes());
            writer.flush();
        } catch (IOException ex) {
            throw ex;
        }
    }

    // read
    public static String readStrFromStream(InputStream in) {
        StringBuilder builder = new StringBuilder();
        StringBuffer sbf = new StringBuffer(0);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 每次读取1024字节
        char[] chars = new char[1048000];
        int resLen = 1048000;
        int nowLen = 0;
        int len;
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("socket接受失败");
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("输入流关闭异常", e);
                }
            }
        }
        return builder.toString();

       /* try {
            while ((len = reader.read(chars,nowLen,resLen-nowLen)) != -1) {
                // 如果长度是1M
                if (len == 2048) {
                    // 然后添加数组的所有字符
                    sbf.append(chars);
                } else {
                    // 如果数组长度小于1M
                    for (int i = 0; i < len; i++) {
                        //然后添加所有的字符
                        sbf.append(chars[i]);
                    }
                    break;
                }
                nowLen += len;
            }


        } catch (IOException e) {
            System.out.println(e);
            try {
                throw e;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }*/
//        return sbf.toString();
    }

    public static String getNowTime() {
        return new Date().toString();
    }
}
