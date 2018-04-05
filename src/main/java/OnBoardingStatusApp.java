import com.fasterxml.jackson.databind.ObjectMapper;
import model.RequestData;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class OnBoardingStatusApp  {
    private static final String SAMPLE_CSV_FILE_PATH = "/home/rishirammeel/Downloads/OnBoardingStatus/28_03_2018/";
    private static final String INPUT_FILE_NAMES="upigateway-service-deployment1-2011244920-xkkn3_onboarding.log upigateway-service-deployment-815337361-8n0sn_onboarding.log upigateway-service-deployment1-2011244920-6zqpm_onboarding.log upigateway-service-deployment1-2011244920-v60b2_onboarding.log upigateway-service-deployment-815337361-hvbzw_onboarding.log";
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "Mobile,VerificationData,PollTime,SmsTime,Device-Binding-Status,Store-Sms-Status,SMS-FROM-Mobile";

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String args[]) throws IOException, Exception {
        // do logic here
        HashMap<String, VerificationData> hashMap = new HashMap<String, VerificationData>();
        FileWriter fw = null;
        int count =0 ;
        long countForDelayed = 0;
        try {
            String[] files = INPUT_FILE_NAMES.split(" ");
            SimpleDateFormat formatter5=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String[] nextRecord;
            for(int i=0; i < files.length ; i++){
                FileReader fileReader =
                        new FileReader(SAMPLE_CSV_FILE_PATH + files[i]);
                BufferedReader bufferedReader =
                        new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    RequestData requestData = objectMapper.readValue(line, RequestData.class);
                    String[] message = requestData.getMessage().split(",");
                    String api = message[0];;
                    String status = message[1].split(": ")[1];
                    String time = requestData.getDate();
                    String vd = message[2].split(": ")[1];
                    //String mobile = message[3].split(": ");
                    String mobile = message[3].split(":")[1];
                    if("API : Device-Binding".equals(api)){
                        if(hashMap.containsKey((vd))){
                            VerificationData verificationData =  hashMap.get((vd));
                            Date pollTime = formatter5.parse(time);
                            if (verificationData.getLastPollResTime() == null || (pollTime).compareTo(verificationData.getLastPollResTime()) > 0) {
                                verificationData.setLastPollResTime(pollTime);
                                verificationData.setStatus(status);
                                verificationData.setMobileDeviceBinding(mobile);
                            }
                        }else{
                            VerificationData verificationData = new VerificationData();
                            verificationData.setLastPollResTime(formatter5.parse(time));
                            verificationData.setVerificationData(vd);
                            verificationData.setStatus(status);
                            verificationData.setMobileDeviceBinding(mobile);
                            hashMap.put(vd,verificationData);
                        }
                    }else if("API : Store-SMS".equals(api)){
                        if(hashMap.containsKey((vd))){
                            VerificationData verificationData =  hashMap.get((vd));
                            Date smsTime = formatter5.parse(time);
                            if (verificationData.getSmsRequestTime() == null || (smsTime).compareTo(verificationData.getSmsRequestTime()) > 0) {
                                verificationData.setSmsRequestTime(smsTime);
                                verificationData.setMobileStoreSms(mobile);
                                verificationData.setSmsStatus(status);
                            }
                        }else{
                            VerificationData verificationData = new VerificationData();
                            verificationData.setVerificationData(vd);
                            verificationData.setSmsRequestTime(formatter5.parse(time));
                            verificationData.setSmsStatus(status);
                            verificationData.setMobileStoreSms(mobile);
                            hashMap.put(vd,verificationData);
                        }
                    }
                }

            }
            // loop ends here
            for(int i=0 ; i< 4 ; i++){
                String STATUS_REPORT = "/home/rishirammeel/Downloads/OnBoardingStatus/28_03_2018/";
                count = 0;
                if(i == 0){
                    STATUS_REPORT = STATUS_REPORT + "OnboardingFailedTransaction.csv";
                }else if(i==1){
                    STATUS_REPORT = STATUS_REPORT + "SMSDelayedFromTelco.csv";
                }else if(i==2){
                    STATUS_REPORT = STATUS_REPORT + "SMSNotReceviedFromTelco.csv";
                }else if(i==3){
                    STATUS_REPORT = STATUS_REPORT + "MobileNotSameForDeviceBindingAndSMS.csv";
                }

                fw = new FileWriter(STATUS_REPORT);
                fw.append(FILE_HEADER.toString());
                fw.append(NEW_LINE_SEPARATOR);
                int countConsider = 0;
                for (Map.Entry<String, VerificationData> entry : hashMap.entrySet()) {
                    count ++;
                    VerificationData verificationData = entry.getValue();
                    boolean condition = false;
                    if(i == 0){
                        condition =  !"Success".equals(verificationData.getStatus());
                    }else if(i==1){
                        condition = !"Success".equals(verificationData.getStatus()) && verificationData.getSmsRequestTime()!= null && verificationData.getLastPollResTime()!= null && (verificationData.getSmsRequestTime()).compareTo(verificationData.getLastPollResTime()) > 0;
                    }else if(i==2){
                        condition = !"Success".equals(verificationData.getStatus()) && verificationData.getSmsStatus() == null;
                    }else if(i==3){
                        condition = !"Success".equals(verificationData.getStatus()) && verificationData.getMobileDeviceBinding() != null && verificationData.getMobileStoreSms() != null && !verificationData.getMobileDeviceBinding().equals(verificationData.getMobileStoreSms());
                    }

                    if(condition){
                        countConsider++;
                        fw.append(String.valueOf(verificationData.getMobileDeviceBinding()));
                        fw.append(COMMA_DELIMITER);
                        fw.append(String.valueOf(verificationData.getVerificationData()));
                        fw.append(COMMA_DELIMITER);
                        fw.append(String.valueOf(verificationData.getLastPollResTime()));
                        fw.append(COMMA_DELIMITER);
                        fw.append(String.valueOf(verificationData.getSmsRequestTime()));
                        fw.append(COMMA_DELIMITER);
//                        long diff = verificationData.getSmsRequestTime().getTime() - verificationData.getLastPollResTime().getTime();
//                        if( diff > 60){
//                            countForDelayed ++;
//                        }
//                        fw.append(String.valueOf(diff / 1000 % 60));
//                        fw.append(COMMA_DELIMITER);
                        fw.append(String.valueOf(verificationData.getStatus()));
                        fw.append(COMMA_DELIMITER);
                        fw.append(String.valueOf(verificationData.getSmsStatus()));
                        fw.append(COMMA_DELIMITER);
                        fw.append(String.valueOf(verificationData.getMobileStoreSms()));
                        fw.append(COMMA_DELIMITER);
                        fw.append(NEW_LINE_SEPARATOR);
                    }
                }
                System.out.println("Unique record found at " + i + " : " + count + "cases considerable is " + countConsider);
            }
        } catch (IOException e) {
            //
        }finally {
            if (fw != null)
                fw.close();
            //System.out.println("Record found taking more then one minute : " + countForDelayed);
        }


    }
}
