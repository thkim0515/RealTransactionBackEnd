//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import java.util.Map;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Autowired
//    private AwsSecretsManagerConfig secretManagerUtil;
//
//    @Bean
//    public DataSource dataSource() {
//        Map<String, String> secret = secretManagerUtil.getSecret();
//
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://finaldatabase.cha4igmusvsy.ap-northeast-2.rds.amazonaws.com:3306/KostamanageDB")
//                .username(secret.get("sql_id"))
//                .password(secret.get("sql_password"))
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//    }
//}

package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private AwsSecretsManagerConfig secretManagerUtil;

    @Bean
    public Map<String, String> secrets() {
        return secretManagerUtil.getSecret();
    }

    @Bean
    public DataSource dataSource(Map<String, String> secrets) {
        return DataSourceBuilder.create()
                .url(secrets.get("sql_addr"))
                .username(secrets.get("sql_id"))
                .password(secrets.get("sql_password"))
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean
    public TwilioConfig twilioConfig(Map<String, String> secrets) {
        return new TwilioConfig(
                secrets.get("twilio.accountSid"),
                secrets.get("twilio.authToken"),
                secrets.get("twilio.phoneNumber")
        );
    }
//
//    @Bean
//    public S3Config s3Config(Map<String, String> secrets) {
//
//        return new S3Config(
//                secrets.get("cloud.aws.region.static"),
//                secrets.get("cloud.aws.s3.bucket")
//        );
//    }
}