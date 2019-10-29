import java.nio.*;

import com.amazonaws.services.kinesis.producer.*;

public class Main {

    public static void mainz(String... args) throws Exception {

        final String streamName = "TryKinesis-MyFirstStream63B28502-8AWS9GENXKPB";

        KinesisProducerConfiguration kinesisProducerConfiguration = new KinesisProducerConfiguration();

        kinesisProducerConfiguration.setRegion("us-east-1");

        final KinesisProducer kinesis = new KinesisProducer(kinesisProducerConfiguration);
        try {
            while (true) {
                try {
                    log("addUserRecord[1]");
                    log(kinesis.addUserRecord(streamName, "myPartitionKey", ByteBuffer.wrap("myData".getBytes("UTF-8"))));
                    log("addUserRecord[2]");
                } finally {
                    Thread.sleep(50);
                }
            }
        } finally {
            kinesis.destroy();
        }
    }

//    // https://docs.aws.amazon.com/streams/latest/dev/kcl2-standard-consumer-java-example.html
//    public static void main(String... args) throws Exception {
//
//        final Region region = Region.US_EAST_1;
//        
//        KinesisAsyncClient kinesisClient = KinesisClientUtil.createKinesisAsyncClient(KinesisAsyncClient.builder().region(region));
//        DynamoDbAsyncClient dynamoClient = DynamoDbAsyncClient.builder().region(region).build();
//        CloudWatchAsyncClient cloudWatchClient = CloudWatchAsyncClient.builder().region(region).build();
//        ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName, streamName, kinesisClient, dynamoClient,
//                cloudWatchClient, UUID.randomUUID().toString(), new SampleRecordProcessorFactory());
//
//        Scheduler scheduler = new Scheduler(
//                configsBuilder.checkpointConfig(),
//                configsBuilder.coordinatorConfig(),
//                configsBuilder.leaseManagementConfig(),
//                configsBuilder.lifecycleConfig(),
//                configsBuilder.metricsConfig(),
//                configsBuilder.processorConfig(),
//                configsBuilder.retrievalConfig().retrievalSpecificConfig(new PollingConfig(streamName, kinesisClient))
//        );
//
//        try {
//            while (true) {
//                try {
//                    log("addUserRecord[1]");
//                    log(kinesis.addUserRecord(streamName, "myPartitionKey", ByteBuffer.wrap("myData".getBytes("UTF-8"))));
//                    log("addUserRecord[2]");
//                } finally {
//                    // Thread.sleep(2000);
//                }
//            }
//        } finally {
//            kinesis.destroy();
//        }
//    }

    static void log(Object... args) {
    	new LogHelper(Main.class).log(args);
    }
}
