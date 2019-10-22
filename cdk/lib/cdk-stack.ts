import cdk = require('@aws-cdk/core');
import kinesis = require('@aws-cdk/aws-kinesis');

export class CdkStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    // The code that defines your stack goes here
    new kinesis.Stream(this, 'MyFirstStream');
  }
}
