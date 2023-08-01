import { NativeModules, Platform } from 'react-native';

type Config = {
  apiKey: string;
};

const LINKING_ERROR =
  `The package 'nosy-logger' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const NosyLogger = NativeModules.NosyLogger
  ? NativeModules.NosyLogger
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function init(config: Config): Promise<void> {
  return NosyLogger.init(config);
};

export function log(message: string): Promise<void> {
  return NosyLogger.log(new Date().toISOString(), message);
}
