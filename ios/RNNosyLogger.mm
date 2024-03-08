#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNNosyLogger, NSObject)

RCT_EXTERN_METHOD(start:(String)apiKey)
RCT_EXTERN_METHOD(debug:(String)message)
RCT_EXTERN_METHOD(info:(String)message)
RCT_EXTERN_METHOD(warning:(String)message)
RCT_EXTERN_METHOD(error:(String)message)

+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

@end
