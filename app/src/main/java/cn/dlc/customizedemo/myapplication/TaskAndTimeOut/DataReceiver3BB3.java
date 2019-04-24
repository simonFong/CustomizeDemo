package cn.dlc.customizedemo.myapplication.TaskAndTimeOut;

import com.dlc.serialportlibrary.ReceiveCallback;
import com.licheedev.myutils.LogPlus;

import java.nio.ByteBuffer;

import cn.dlc.customizedemo.myapplication.SerialPort.ByteUtil;

/**
 * 数据接收器实现类
 * author: John
 * create time: 2018/7/5 14:15
 * description:
 */
public abstract class DataReceiver3BB3 implements ReceiveCallback {

    private final ByteBuffer mByteBuffer;

    public DataReceiver3BB3() {
        mByteBuffer = ByteBuffer.allocate(1024);
        mByteBuffer.clear();
    }

    public abstract void onReceiveValidData(byte[] allPack, byte[] data, byte command);

    public void resetCache() {
        mByteBuffer.clear();
    }

    @Override
    public void onReceive(String devicePath, String baudrateString, byte[] received, int size) {

        LogPlus.i("DataReceiver", "接收数据=" + ByteUtil.bytes2HexStr(received, 0, size));

        mByteBuffer.put(received, 0, size);
        mByteBuffer.flip();
        byte b;
        int readable;
        while ((readable = mByteBuffer.remaining()) >= Protocol.MIN_PACK_LEN) {
            mByteBuffer.mark(); // 标记一下开始的位置
            int frameStart = mByteBuffer.position();

            b = mByteBuffer.get();
            if (b != Protocol.FRAME_HEAD_0) { // 第1个byte要3B
                continue;
            }

            b = mByteBuffer.get();
            if (b != Protocol.FRAME_HEAD_1) { // 第2个byte要B3
                continue;
            }

            b = mByteBuffer.get();
            if (b != Protocol.TO_ADDRESS) { //帧类型要01
                mByteBuffer.position(frameStart + 2);
                continue;
            }
            // 数据长度
            final int cmdDataLen = 0xff & mByteBuffer.get(frameStart + 7);
            // 总数据长度
            int total = Protocol.PACK_LEN + cmdDataLen;
            // 如果可读数据小于总数据长度，表示不够,还有数据没接收
            if (readable < total) {
                // 重置一下要处理的位置,并跳出循环
                mByteBuffer.reset();
                break;
            }

            // 回到头
            mByteBuffer.reset();
            // 拿到整个包
            byte[] allPack = new byte[total];
            mByteBuffer.get(allPack);

            String myCrc16 = CRC16Utils.getCRC16(allPack, 0, total-2);
            String reciveCrc16 = ByteUtil.bytes2HexStr(allPack, total-2, 2);
            // 校验通过
            if (myCrc16.equals(reciveCrc16)) {
                final byte[] data = new byte[cmdDataLen];
                System.arraycopy(allPack, 8, data, 0, data.length);
                byte command = allPack[6];
                // 收到有效数据
                onReceiveValidData(allPack, data, command);
            } else {
                // 不一致则回到“第二位”，继续找到下一个3BB3
                mByteBuffer.position(frameStart + 2);
            }
        }

        // 最后清掉之前处理过的不合适的数据
        mByteBuffer.compact();
    }
}
