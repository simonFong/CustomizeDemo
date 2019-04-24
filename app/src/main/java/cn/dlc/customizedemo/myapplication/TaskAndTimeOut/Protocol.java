package cn.dlc.customizedemo.myapplication.TaskAndTimeOut;

/**
 * 协议
 */
public interface Protocol {

    /**
     * 帧头0
     */
    byte FRAME_HEAD_0 = 0x3B;
    String HEAD_0 = "3B";
    /**
     * 帧头1
     */
    byte FRAME_HEAD_1 = (byte) 0xB3;
    String HEAD_1 = "B3";
    /**
     * 目的地址
     */
    byte TO_ADDRESS = 0x00;

    String S_TO_ADDRESS = "00";

    /**
     * 源地址
     */
    String FROM_ADDRESS = "00";

    /**
     * 类型
     */
    String TYPE = "00";

    /**
     * 开锁命令码 长度
     */
    String A4 = "A4";
    String A4_LENGTH = "01";

    /**
     * 称重校准命令码 长度
     */
    String A6 = "A6";
    String A6_LENGTH = "02";

    /**
     * IO输出命令码
     */
    String A9 = "A9";
    String A9_LENGTH = "02";

    /**
     * IO输出命令码
     */
    String AB = "AB";
    String AB_LENGTH = "01";

    /**
     * 关门命令码
     */
    String AA = "AA";
    String AA_LENGTH = "01";
    /**
     * 心跳
     */
    String HEARTBEAT = "55";
    String HEARTBEAT_LENGTH = "05";

    /**
     * 数据长度包含一个数据位
     */
    byte MIN_PACK_LEN = 0x0B;

    /**
     * 减去数据的长度
     */
    byte PACK_LEN = 0x0A;

    /**
     * 投递门
     */
    String DELIVERY_DOOR = "00";
    /**
     * 清运门
     */
    String CLEAN_DOOR = "01";

}
