package com.bank.fraud_service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

import static com.bank.fraud_service.constants.Constants.Transaction_Topic_Name;

@Component
public class FraudConsumer {

    private static final Logger log = LoggerFactory.getLogger(FraudConsumer.class);

    @KafkaListener(topics = Transaction_Topic_Name)
    public void onMessage(ConsumerRecord<String, Map<String, Object>> record) {
        Map<String, Object> payload = record.value();
        log.info("Received transaction event: {}", payload);

        // Simple rule-based fraud detection demo:
        Object amountObj = payload.get("amount");
        if (amountObj != null) {
            try {
                BigDecimal amount = new BigDecimal(amountObj.toString());
                if (amount.compareTo(new BigDecimal("100000")) > 0) {
                    log.warn("⚠️ Potential fraud detected for event: {}", payload);
                }
            } catch (Exception ignored) {}
        }
    }

}
