#!/bin/bash
mkdir /home/rishirammeel/Downloads/OnBoardingStatus/$(date -d "1 days ago" +"%d_%m_%Y")/
scp -r rishiram.meel@10.100.104.45:/data/log/upigateway-service/archive/*_onboarding-$(date -d "1 days ago" +"%Y-%m-%d")-1.log.gz /home/rishirammeel/Downloads/OnBoardingStatus/$(date -d "1 days ago" +"%d_%m_%Y")/
scp -r rishiram.meel@10.100.104.47:/data/log/upigateway-service/archive/*_onboarding-$(date -d "1 days ago" +"%Y-%m-%d")-1.log.gz /home/rishirammeel/Downloads/OnBoardingStatus/$(date -d "1 days ago" +"%d_%m_%Y")/
scp -r rishiram.meel@10.100.104.48:/data/log/upigateway-service/archive/*_onboarding-$(date -d "1 days ago" +"%Y-%m-%d")-1.log.gz /home/rishirammeel/Downloads/OnBoardingStatus/$(date -d "1 days ago" +"%d_%m_%Y")/
scp -r rishiram.meel@10.100.104.49:/data/log/upigateway-service/archive/*_onboarding-$(date -d "1 days ago" +"%Y-%m-%d")-1.log.gz /home/rishirammeel/Downloads/OnBoardingStatus/$(date -d "1 days ago" +"%d_%m_%Y")/
#cd /home/rishirammeel/Downloads/OnBoardingStatus/$(date -d "1 days ago" +"%d_%m_%Y")/)
#ls -lrth | cut -d " " -f10 >> FileName.text
echo 
	

