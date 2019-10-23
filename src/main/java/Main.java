import java.nio.ByteBuffer;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.google.common.collect.Lists;

public class Main {

    public static void main(String... args) throws Exception {

            // ((ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME).setLevel(level);


        // org.slf4j.LoggerFactory.getILoggerFactory()

        


        final String streamName = "TryKinesis-MyFirstStream63B28502-8AWS9GENXKPB";

        // kinesisClient.putRecord(PutRecordRequest.builder()
        //         //
        //         .streamName(streamName)
        //         //
        //         .partitionKey("myPartitionKey")
        //         //
        //         .data(SdkBytes.fromUtf8String("hello"))
        //         //
        //         .build()).responseMetadata();

        // System.exit(0);

        KinesisProducerConfiguration kinesisProducerConfiguration = new KinesisProducerConfiguration();
            // AWSCredentials credentials = DefaultAWSCredentialsProviderChain.getInstance().getCredentials();
        // kinesisProducerConfiguration.setCredentialsProvider(DefaultAWSCredentialsProviderChain.getInstance());
        // kinesisProducerConfiguration.setVerifyCertificate(false);
        kinesisProducerConfiguration.setRegion("us-east-1");

        final KinesisProducer kinesis = new KinesisProducer(kinesisProducerConfiguration);
        try {
            while (true) {
                try {
                    log("addUserRecord[1]");
                    log(kinesis.addUserRecord(streamName, "myPartitionKey", ByteBuffer.wrap("myData".getBytes("UTF-8"))));
                    log("addUserRecord[2]");
                } finally {
                    // Thread.sleep(2000);
                }
            }
        } finally {
            kinesis.destroy();
        }
    }

    static void log(Object... args) {
        System.out.println(Lists.newArrayList(args));
    }
}