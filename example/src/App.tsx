import React, { useEffect } from 'react';

import { StyleSheet, View, Text } from 'react-native';
import logger from '@nosytools/logger-mobile';

export default function App() {
  useEffect(() => {
    logger.init({
      apiKey:
        '2lRuZT3YCrauY7rFiZMLXx8bGr36wYKd1BoHKGHk8zc+OsntaIjAhd8tFjZ3qX/bHzQKqrsxiYlTdoyOP4Arrg==',
    });
  }, []);

  const debug = () => logger.debug('hello from nosy logger debug example!');

  const info = () => logger.info('hello from nosy logger example!');

  const warning = () =>
    logger.warning('hello from nosy logger warining example!');

  const error = () => logger.error('hello from nosy logger error example!');

  return (
    <View style={styles.container}>
      <Text onPress={debug}>Press to log debug message</Text>
      <Text onPress={info}>Press to log info message</Text>
      <Text onPress={warning}>Press to log warning message</Text>
      <Text onPress={error}>Press to log error message</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
