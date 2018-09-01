package cn.dlc.customizedemo.myapplication.eventbus.bean;

/**
 * Created by fengzimin  on  2018/07/26.
 * interface by
 */
public class ComEventBean {

    private String from;
    private Object data;

    public ComEventBean() {
    }

    public ComEventBean(String from, Object data) {
        this.from = from;
        this.data = data;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
