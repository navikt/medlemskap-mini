package no.nav.medlemskap.services.kafka

import no.nav.medlemskap.config.Configuration
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class Producer {

    fun createProducer(producerConfig: Configuration.KafkaConfig): KafkaProducer<String, String> {
        val properties = Properties()
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, producerConfig.bootstrapServers)
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, producerConfig.securityProtocol)
        properties.put(CommonClientConfigs.CLIENT_ID_CONFIG, producerConfig.clientId)
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, producerConfig.trustStorePath)
        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, producerConfig.keystorePassword)
        properties.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, producerConfig.keystoreType)
        properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, producerConfig.keystoreLocation)
        properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, producerConfig.keystorePassword)

        return KafkaProducer(properties)
    }
}
