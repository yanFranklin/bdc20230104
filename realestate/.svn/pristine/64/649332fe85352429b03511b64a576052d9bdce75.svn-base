package cn.gtmap.realestate.exchange.util;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @description UUID工具类
 */
public final class UuidUtils {
    private UuidUtils() {

    }

    private static final AtomicInteger counter;
    private static final int JVM;
    private static final int IP;
    private static final Logger LOG = Logger.getLogger(UuidUtils.class.getName());
    private static final String identifier = getIP() + getFileNum();

    static {
        counter = new AtomicInteger(0);
        JVM = (int) (System.currentTimeMillis() >>> 8);
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    private static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    private static short getCount() {
        counter.compareAndSet(Short.MAX_VALUE, 0);
        return (short) counter.incrementAndGet();
    }


    private static String format(int intValue) {
        return StringUtils.leftPad(Integer.toHexString(intValue), 8, "0");
    }

    private static String format(short shortValue) {
        return StringUtils.leftPad(Integer.toHexString(shortValue), 4, "0");
    }

    public static String generate() throws Exception {
        // 因为之前生成UUID的方式多线程时会生成36位的UUID 所以改成util包的UUID生成方法  ccx 2019-10-02
        UUID uuid = UUID.randomUUID();
        String id = null;
        if (uuid != null) {
            id = StringUtils.replace(uuid.toString(), "-", "");
        } else {
            throw new Exception("generate方法生成UUID异常");
        }
        return id;
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 16位UUID
     */
    public static String generate16() {
        return String.valueOf(getLngTime() + identifier + generate16GetCount());
    }

    private static String generate16GetCount() {
        counter.compareAndSet(36 * 36 * 36, 0);
        return formatString(getHex36String(counter.incrementAndGet()), 3);
    }

    private static String getFileNum() {
        return formatString(String.valueOf(getInstanceSeq()), 1);
    }

    private static int BytestoInt(byte[] bytes) {
        int result = 0;

        for (int i = 0; i < 4; ++i) {
            result = (result << 8) - -128 + bytes[i];
        }

        return result;
    }

    private static byte[] getInetAddress() {
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

            while (true) {
                NetworkInterface ni;
                String name;
                do {
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        if (!interfaces.hasMoreElements()) {
                                            return null;
                                        }

                                        ni = (NetworkInterface) interfaces.nextElement();
                                    } while (ni.isLoopback());
                                } while (ni.isVirtual());
                            } while (ni.isPointToPoint());
                        } while (!ni.isUp());

                        name = ni.getDisplayName().toLowerCase();
                    } while (name.contains("convnet"));
                } while (name.contains("vmnet"));

                Enumeration addresses = ni.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    byte[] addr = ((InetAddress) addresses.nextElement()).getAddress();
                    if (addr.length == 4) {
                        return addr;
                    }
                }
            }
        } catch (Exception var5) {
            LOG.warning("Error to get ip address");
            return null;
        }
    }

    private static String getIP() {
        int ip = 0;

        try {
            ip = BytestoInt(getInetAddress());
        } catch (Exception var2) {
            ;
        }

        return formatString(getHex36String(ip), 2);
    }

    private static byte getInstanceSeq() {
        Preferences prefs = Preferences.userRoot().node("egov");
        int seq = prefs.getInt("uuid-seq", 0);
        if (seq > 36) {
            seq = 0;
        }

        ++seq;
        prefs.putInt("uuid-seq", seq);

        try {
            prefs.flush();
        } catch (BackingStoreException var3) {
            LOG.warning("Error to save uuid-seq");
        }

        return (byte) seq;
    }

    private static String getHex36String(int value) {
        return Integer.toString(value, 36).toUpperCase();
    }

    private static String getHex36String(long value) {
        return Long.toString(value, 36).toUpperCase();
    }

    private static String formatString(String str, int length) {
        if (str.length() == length) {
            return str;
        } else if (str.length() > length) {
            return str.substring(str.length() - length, str.length());
        } else {
            StringBuffer buf = new StringBuffer();

            for (int i = 0; i < length - str.length(); ++i) {
                buf.append("0");
            }

            return buf.append(str).toString();
        }
    }

    private static String getLngTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int day = calendar.get(5);
        int hour = calendar.get(11);
        int minute = calendar.get(12);
        int second = calendar.get(13);
        int millSecond = calendar.get(14);
        return formatString(getHex36String(year), 1) + getHex36String(month) + getHex36String(day) + getHex36String(hour) + formatString(String.valueOf(minute), 2) + formatString(String.valueOf(second), 2) + formatString(getHex36String(millSecond), 2);
    }

}
