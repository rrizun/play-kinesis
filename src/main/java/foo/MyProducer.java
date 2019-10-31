package foo;

import java.nio.*;

import com.amazonaws.services.kinesis.producer.*;

public class MyProducer {

    public static void main(String... args) throws Exception {

        final String streamName = "TryKinesis-MyFirstStream63B28502-8AWS9GENXKPB";

        KinesisProducerConfiguration kinesisProducerConfiguration = new KinesisProducerConfiguration();

        kinesisProducerConfiguration.setRegion("us-east-1");

        final KinesisProducer kinesis = new KinesisProducer(kinesisProducerConfiguration);
        try {
            while (true) {
                try {
                    log("addUserRecord[1]");
                    log(kinesis.addUserRecord(streamName, "myPartitionKey", ByteBuffer.wrap("myData".getBytes("UTF-8"))));
                    // log(kinesis.addUserRecord(streamName, "myPartitionKey", ByteBuffer.wrap("myData".getBytes("UTF-8"))).get());
                    log("addUserRecord[2]");
                } finally {
                    // Thread.sleep(50);
                }
            }
        } finally {
            kinesis.destroy();
        }
    }

    static void log(Object... args) {
    	new LogHelper(MyProducer.class).log(args);
    }
}
