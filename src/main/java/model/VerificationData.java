package model;


import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name="upi_user")
public class VerificationData {
    @Column(name = "verification_data")
    String verificationData;
    @Column(name = "poll_time")
    Date   lastPollResTime;
    @Column(name= "sms_time")
    Date   smsRequestTime;
    @Column(name = "device_binding_call_status")
    String status;
    @Column(name = "store_sms_call_status")
    String smsStatus;

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
