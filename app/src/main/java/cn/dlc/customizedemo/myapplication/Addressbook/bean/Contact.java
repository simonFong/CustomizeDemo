package cn.dlc.customizedemo.myapplication.Addressbook.bean;

import java.util.List;

/**
 * Created by fengzimin  on  2018/4/11.
 * interface by
 */
public class Contact {
    private String contactName;
    private String contactId;
    private List<String> contactPhoneNum;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public List<String> getContactPhoneNum() {
        return contactPhoneNum;
    }

    public void setContactPhoneNum(List<String> contactPhoneNum) {
        this.contactPhoneNum = contactPhoneNum;
    }

    @Override
    public String toString() {
        return "Contact{" + "contactName='" + contactName + '\'' + ", contactId='" + contactId + '\'' + ", contactPhoneNum=" + contactPhoneNum + '}';
    }
}
