package com.demo.kafka.eventSource.constant;

import com.demo.kafka.eventSource.serdes.AccountSerde;
import com.demo.kafka.eventSource.model.BankAccount;
import com.demo.kafka.eventSource.transformer.EventValueTransformer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import java.util.HashMap;
import java.util.Map;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class KafkaStreamHandler {

    public static void main(String args[]) {

        Map<String, Object> props = new HashMap();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, DemoConstants.applicationIdConfig);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, DemoConstants.bootstrapServer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        StreamsConfig config = new StreamsConfig(props);

        StreamsBuilder builder = new StreamsBuilder();

        StoreBuilder<KeyValueStore<String, BankAccount>> accountSnapshotStore = Stores
                .keyValueStoreBuilder(Stores.persistentKeyValueStore(DemoConstants.statestore), Serdes.String(), new AccountSerde())
                .withCachingEnabled();
//        KeyValueStore<String, BankAccount> store = accountSnapshotStore.build();
        builder.addStateStore(accountSnapshotStore);

        KStream<String, String> events = builder.stream(DemoConstants.topic);

//        events.process(() -> new EventProcessor(), DemoConstants.statestore);
        events.transformValues(() -> new EventValueTransformer(), DemoConstants.statestore).to("test");
        KafkaStreams streams = new KafkaStreams(builder.build(), config);

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        LOGGER.info("Stream started here...");
    }

}
