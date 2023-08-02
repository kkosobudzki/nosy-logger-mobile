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
  return NosyLogger.init({ ...config, url: '127.0.0.1:8080' }); // TODO move url to env variable
}

export function log(message: string): Promise<void> {
  // TODO batch messages here
  const messages = [
    { date: new Date().toISOString(), message: 'first log' },
    { date: new Date().toISOString(), message: 'second log' },
    { date: new Date().toISOString(), message: 'third log' },
  ];

  return NosyLogger.log(messages);
}
