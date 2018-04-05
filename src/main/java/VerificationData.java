import java.util.Date;

public class VerificationData {

    String verificationData;
    Date   lastPollResTime;
    Date   smsRequestTime;
    String status;
    String smsStatus;
    String mobileDeviceBinding;
    String mobileStoreSms;

    public String getMobileDeviceBinding() {
        return mobileDeviceBinding;
    }

    public void setMobileDeviceBinding(String mobileDeviceBinding) {
        this.mobileDeviceBinding = mobileDeviceBinding;
    }

    public String getMobileStoreSms() {
        return mobileStoreSms;
    }

    public void setMobileStoreSms(String mobileStoreSms) {
        this.mobileStoreSms = mobileStoreSms;
    }

    public String getVerificationData() {
        return verificationData;
    }

    public void setVerificationData(String verificationData) {
        this.verificationData = verificationData;
    }

    public Date getLastPollResTime() {
        return lastPollResTime;
    }

    public void setLastPollResTime(Date lastPollResTime) {
        this.lastPollResTime = lastPollResTime;
    }

    public Date getSmsRequestTime() {
        return smsRequestTime;
    }

    public void setSmsRequestTime(Date smsRequestTime) {
        this.smsRequestTime = smsRequestTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }

}
