import { NativeModules, Platform } from 'react-native';

import Batch from './batch';

type Config = {
  apiKey: string;
};

type Level = 'debug' | 'info' | 'warn' | 'error';

type Log = {
  date: string;
  message: string;
  level: Level;
};

type LogBatch = {
  push: (log: Log) => Promise<void>;
  stop: () => void;
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

let batch: LogBatch | undefined;

async function init(config: Config): Promise<void> {
  if (batch) {
    return Promise.reject('Already initialized');
  }

  return NosyLogger.init(config).then(() => {
    batch = Batch<Log>(NosyLogger.log);
  });
}

function log(message: string, level: Level = 'info'): Promise<void> {
  if (batch) {
    return batch.push({ date: new Date().toISOString(), message, level });
  }

  return Promise.reject(
    'Not initialized - make sure to call init() before start logging'
  );
}

function debug(message: string): Promise<void> {
  return log(message, 'debug');
}

function info(message: string): Promise<void> {
  return log(message, 'info');
}

function warning(message: string): Promise<void> {
  return log(message, 'warn');
}

function error(message: string): Promise<void> {
  return log(message, 'error');
}

export default { init, log, debug, info, warning, error };
