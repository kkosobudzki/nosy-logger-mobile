import { NativeModules, Platform } from 'react-native';

type Config = {
  apiKey: string;
};

type Level = 'debug' | 'info' | 'warn' | 'error';

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
  const { COLLECTOR_URL } = process.env;

  return NosyLogger.init({ ...config, url: COLLECTOR_URL });
}

export function log(message: string, level: Level = 'info'): Promise<void> {
  // TODO batch messages here
  const messages = [
    { date: new Date().toISOString(), message: `${message} one}`, level },
    { date: new Date().toISOString(), message: `${message} second`, level },
    { date: new Date().toISOString(), message: `${message} third`, level },
  ];

  return NosyLogger.log(messages);
}

export function debug(message: string): Promise<void> {
  return log(message, 'debug');
}

export function info(message: string): Promise<void> {
  return log(message, 'info');
}

export function warning(message: string): Promise<void> {
  return log(message, 'warn');
}

export function error(message: string): Promise<void> {
  return log(message, 'error');
}
