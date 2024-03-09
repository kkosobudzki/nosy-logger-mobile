import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'nosy-logger' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

type NativeLogFn = (message: string) => void;

type NativeLogger = {
  init: (apiKey: string) => void;
  debug: NativeLogFn;
  info: NativeLogFn;
  warning: NativeLogFn;
  error: NativeLogFn;
};

const module: NativeLogger =
  NativeModules.NosyLogger ??
  new Proxy(
    {},
    {
      get() {
        throw new Error(LINKING_ERROR);
      },
    }
  );

export default module;
