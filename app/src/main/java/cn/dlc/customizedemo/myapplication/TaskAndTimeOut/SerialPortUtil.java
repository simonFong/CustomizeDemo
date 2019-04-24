package cn.dlc.customizedemo.myapplication.TaskAndTimeOut;

import android.util.Log;

import com.dlc.serialportlibrary.DLCSerialPortUtil;
import com.dlc.serialportlibrary.SerialPortManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.dlc.customizedemo.myapplication.SerialPort.ByteUtil;

/**
 * Created by Administrator on 2019/1/9/009.
 */

public class SerialPortUtil {
    private SerialPortManager manager;
    private TimerTask commandTask;
    private Timer timer;
    private int timeOut = 0;
    private volatile List<String> cmdPacks = new ArrayList<>();
    private volatile String lastPackHexStr = "";
    private operationResult mResult;
    private final StringBuilder stringBuilder =
            new StringBuilder().append(Protocol.HEAD_0)
                    .append(Protocol.HEAD_1)
                    .append(Protocol.S_TO_ADDRESS)
                    .append(Protocol.FROM_ADDRESS)
                    .append(Protocol.TYPE);
    private int maxTime;
    private int flowingWater;
    public volatile String heartbeatWater;
    public boolean openSerialPort;


    private String getFlowingWater() {
        if (flowingWater >= 255) {
            flowingWater = 0;
        } else {
            flowingWater++;
        }
        return ByteUtil.decimal2fitHex(flowingWater, 2);
    }

    public static SerialPortUtil getInstance() {
        return new SerialPortUtil();
    }

    private SerialPortUtil() {
        try {
            manager = DLCSerialPortUtil.getInstance().open("/dev/ttyS1", "115200");
            if (manager.isOpenSuccess()) {
                openSerialPort = true;
                startTimer();
                manager.setReceiveCallback(new DataReceiver3BB3() {
                    @Override
                    public void onReceiveValidData(byte[] allPack, byte[] data, byte command) {
                        cmdPacks.remove(lastPackHexStr);
                        timeOut = 0;
                        if (mResult != null) {
                            mResult.receiveResult(ByteUtil.bytes2HexStr(allPack), ByteUtil.bytes2HexStr(data), command);
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            openSerialPort = false;
        }
    }

    public void sendA4(String type) {
        StringBuilder sb = new StringBuilder();
        sb.append(stringBuilder).append(getFlowingWater()).append(Protocol.A4).append(Protocol.A4_LENGTH).append(type);
        String crc = CRC16Utils.getCRC16(sb.toString());
        sb.append(crc);
        addCmd(0, sb.toString());
    }

    public void sendA6(int weight) {
        StringBuilder sb = new StringBuilder();
        sb.append(stringBuilder)
                .append(getFlowingWater())
                .append(Protocol.A6)
                .append(Protocol.A6_LENGTH)
                .append(ByteUtil.decimal2fitHex(weight, 4));
        String crc = CRC16Utils.getCRC16(sb.toString());
        sb.append(crc);
        addCmd(-1, sb.toString());
    }

    public void sendA9(int type, int state) {
        StringBuilder sb = new StringBuilder();
        sb.append(stringBuilder)
                .append(getFlowingWater())
                .append(Protocol.A9)
                .append(Protocol.A9_LENGTH)
                .append(ByteUtil.decimal2fitHex(type, 2))
                .append(ByteUtil.decimal2fitHex(state, 2));
        String crc = CRC16Utils.getCRC16(sb.toString());
        sb.append(crc);
        addCmd(-1, sb.toString());
    }

    public void sendAA() {
        StringBuilder sb = new StringBuilder();
        sb.append(stringBuilder).append(getFlowingWater()).append(Protocol.AA).append(Protocol.AA_LENGTH).append("00");
        String crc = CRC16Utils.getCRC16(sb.toString());
        sb.append(crc);
        addCmd(0, sb.toString());
    }

    public void sendAB(int type) {
        StringBuilder sb = new StringBuilder();
        sb.append(stringBuilder)
                .append(getFlowingWater())
                .append(Protocol.AB)
                .append(Protocol.AB_LENGTH)
                .append(ByteUtil.decimal2fitHex(type, 2));
        String crc = CRC16Utils.getCRC16(sb.toString());
        sb.append(crc);
        addCmd(-1, sb.toString());
    }

    public void send55(int time) {
        StringBuilder sb = new StringBuilder();
        sb.append(stringBuilder)
                .append(heartbeatWater)
                .append(Protocol.HEARTBEAT)
                .append(Protocol.HEARTBEAT_LENGTH)
                .append(ByteUtil.decimal2fitHex(time, 2))
                .append("00000000");
        String crc = CRC16Utils.getCRC16(sb.toString());
        sb.append(crc);
        addCmd(-1, sb.toString());
    }

    public void sendAc(int state) {
        StringBuilder sb = new StringBuilder();
        sb.append(stringBuilder).append(getFlowingWater()).append("AC").append("01").append(ByteUtil.decimal2fitHex(state, 2));
        String crc = CRC16Utils.getCRC16(sb.toString());
        sb.append(crc);
        addCmd(1, sb.toString());
    }

    private void addCmd(int index, String s) {
        if (index == 0) {
            cmdPacks.add(0, s);
        } else {
            cmdPacks.add(s);
        }
    }

    private void sendCmd() {
        if (!cmdPacks.isEmpty() && !cmdPacks.contains(lastPackHexStr)) {
            try {
                lastPackHexStr = cmdPacks.get(0);
                timeOut = 0;
                manager.sendData(lastPackHexStr);
                if ("55".equals(lastPackHexStr.substring(12, 14))) {
                    cmdPacks.remove(lastPackHexStr);
                    lastPackHexStr = "";
                }
                Log.e("))))))))))))", "发送: " + lastPackHexStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        if (commandTask == null) {
            commandTask = new TimerTask() {
                @Override
                public void run() {
                    if (cmdPacks.size() > 0) {
                        if (cmdPacks.contains(lastPackHexStr)) {
                            String code = lastPackHexStr.substring(12, 14);
                            if (code.equals("A4") || code.equals("AA")) {//开关门超时时间是10s
                                maxTime = 20;
                            } else {
                                maxTime = 6;
                            }
                            if (timeOut > maxTime) {
                                if (mResult != null) {
                                    mResult.sendErrorListener(lastPackHexStr);
                                }
                                lastPackHexStr = "";
                                cmdPacks.remove(lastPackHexStr);
                                timeOut = 0;
                            } else {
                                synchronized (SerialPortUtil.class) {
                                    timeOut++;
                                }
                            }
                        } else {
                            if (timeOut > 0) {
                                sendCmd();
                            } else {
                                timeOut++;
                            }
                        }
                    }
                }
            };
            timer.schedule(commandTask, 0, 500);
        }
    }

    public void setmResult(operationResult result) {
        this.mResult = result;
    }

    public void onDestory() {
        timer.cancel();
        commandTask.cancel();
    }

    public interface operationResult {
        void receiveResult(String allResult, String data, byte synType);

        void sendErrorListener(String sendCmd);
    }

}
