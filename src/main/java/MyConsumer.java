import java.util.UUID;

import com.google.common.collect.Lists;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.ConfigsBuilder;
import software.amazon.kinesis.common.InitialPositionInStream;
import software.amazon.kinesis.common.InitialPositionInStreamExtended;
import software.amazon.kinesis.common.KinesisClientUtil;
import software.amazon.kinesis.coordinator.Scheduler;
import software.amazon.kinesis.lifecycle.events.InitializationInput;
import software.amazon.kinesis.lifecycle.events.LeaseLostInput;
import software.amazon.kinesis.lifecycle.events.ProcessRecordsInput;
import software.amazon.kinesis.lifecycle.events.ShardEndedInput;
import software.amazon.kinesis.lifecycle.events.ShutdownRequestedInput;
import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.processor.ShardRecordProcessorFactory;
import software.amazon.kinesis.retrieval.polling.PollingConfig;

class MyShardRecordProcessor implements ShardRecordProcessor {

    @Override
    public void initialize(InitializationInput initializationInput) {
        log("initialize");
    }

    @Override
    public void processRecords(ProcessRecordsInput processRecordsInput) {
        log("processRecords", processRecordsInput.records().size());

    }

    @Override
    public void leaseLost(LeaseLostInput leaseLostInput) {
        log("leaseLost");

    }

    @Override
    public void shardEnded(ShardEndedInput shardEndedInput) {
        log("shardEnded");

    }

    @Override
    public void shutdownRequested(ShutdownRequestedInput shutdownRequestedInput) {
        log("shutdownRequested");

    }

    private void log(Object... args) {
        new LogHelper(this).log(args);
    }

}

// class SampleRecordProcessorFactory implements ShardRecordProcessorFactory {
//     public ShardRecordProcessor shardRecordProcessor() {
//         return new MyShardRecordProcessor();
//     }
// }

public class MyConsumer {

    // https://docs.aws.amazon.com/streams/latest/dev/kcl2-standard-consumer-java-example.html
    public static void main(String... args) throws Exception {

        final Region region = Region.US_EAST_1;
        final String streamName = "TryKinesis-MyFirstStream63B28502-8AWS9GENXKPB";

        KinesisAsyncClient kinesisClient = KinesisClientUtil
                .createKinesisAsyncClient(KinesisAsyncClient.builder().region(region));
        DynamoDbAsyncClient dynamoClient = DynamoDbAsyncClient.builder().region(region).build();
        CloudWatchAsyncClient cloudWatchClient = CloudWatchAsyncClient.builder().region(region).build();
        ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName, streamName, kinesisClient, dynamoClient,
                cloudWatchClient, UUID.randomUUID().toString(), ()->new MyShardRecordProcessor());

                // InitialPositionInStream

                configsBuilder.retrievalConfig().initialPositionInStreamExtended(InitialPositionInStreamExtended.newInitialPosition(InitialPositionInStream.TRIM_HORIZON)); // = ;

        Scheduler scheduler = new Scheduler(
                //
                configsBuilder.checkpointConfig(),
                //
                configsBuilder.coordinatorConfig(),
                //
                configsBuilder.leaseManagementConfig(),
                //
                configsBuilder.lifecycleConfig(),
                //
                configsBuilder.metricsConfig(),
                //
                configsBuilder.processorConfig(),
                //
                configsBuilder.retrievalConfig()
                        //
                        .retrievalSpecificConfig(new PollingConfig(streamName, kinesisClient))
                        //
                        // .initialPositionInStreamExtended(InitialPositionInStreamExtended.newInitialPosition(InitialPositionInStream.TRIM_HORIZON))
                        .initialPositionInStreamExtended(InitialPositionInStreamExtended.newInitialPosition(InitialPositionInStream.AT_TIMESTAMP))
        //
        );

        new Thread(scheduler).start();

        Thread.sleep(5000);
    }

    static void log(Object... args) {
        System.out.println(Lists.newArrayList(args));
    }
}
