package org.example.weatherproducer.service;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.event.WeatherEvent;
import org.example.weatherproducer.enums.WeatherCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
@EmbeddedKafka(topics = {
        "${weather-topic}"
}, partitions = 1)
@SpringBootTest(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
})
class WeatherSenderServiceImplTest {
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ConsumerFactory<String, Object> consumerFactory;
    @MockitoBean
    private WeatherSenderService weatherSenderService;

    @Value("${weather-topic}")
    private String topic;

    @BeforeEach
    public void setup() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
                "weather-test", "true", embeddedKafka);
        consumerProps.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );
        consumerProps.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class
        );
        consumerProps.put(
                JsonDeserializer.VALUE_DEFAULT_TYPE,
                WeatherEvent.class.getName()
        );
        consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
    }

    @Test
    @SneakyThrows
    void sendWeatherEventToKafka_DataIsCorrect_TheSentEvent() {
        Consumer<String, Object> consumer = consumerFactory.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, topic);

        WeatherEvent event = new WeatherEvent(
                "Москва",
                30,
                WeatherCondition.SUNNY.getDescription(),
                LocalDateTime.now()
        );
        kafkaTemplate.send(topic, event.getCity(), event).get();


        ConsumerRecords<String, Object> records = KafkaTestUtils.getRecords(consumer);
        assertEquals(1, records.count());

        ConsumerRecord<String, Object> record = records.iterator().next();
        assertNotNull(record);
        assertEquals(event.getCity(), record.key());
        assertEquals(event, record.value());
    }
}