package com.zikeyang;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;

public class Main {
    public static void main(String[] args) throws PulsarClientException, MalformedURLException {
        String issuerUrl = "https://auth.test.cloud.gcp.streamnative.dev/";
        String credentialsUrl = "file:///Users/rbt/bin/pulsarctl-3.3.0/o-5om91-io-test.json"; // Absolute path of your downloaded key file
        String audience = "urn:sn:pulsar:o-5om91:myins";

        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar+ssl://pc-2bb83142.aws-use2-dixie-snc.streamnative.test.aws.sn2.dev:6651")
                .authentication(
                        AuthenticationFactoryOAuth2.clientCredentials(new URL(issuerUrl),
                                new URL(credentialsUrl),
                                audience))
                .build();

        Producer<byte[]> producer = client.newProducer()
                .topic("persistent://public/default/test-s3")
                .create();

        for (int i = 0; i < 100; i++) {
            String message = "{\"test-message\": \"test-value\"}";
            MessageId msgID = producer.send(message.getBytes());
            System.out.println("Publish " + "my-message-" + i
                    + " and message ID " + msgID);
        }

        producer.close();
        client.close();
    }
}
