package cn.gtmap.realestate.certificate.util;


import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/22
 */
public class NativeUtil {
    public static abstract interface PCLibrary extends Library
    {
        public static final PCLibrary INSTANCE4 = (PCLibrary) Native.loadLibrary(BdcDzzzPdfUtil.SIGN_IDEABANK_LIBTRUSPDFSEAL, PCLibrary.class);

        public abstract int IBES_affixESealToPDF(
                String pdfFile,
                int pageNo,
                float posLeft,
                float posTop,
                String containerName,
                String accessCode
        );

        public abstract int IBES_verifyESealFromPDF(
                String pdfFile,
                String containerName,
                String accessCode
        );
    }
}
