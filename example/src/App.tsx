import React, { useEffect } from 'react';

import { StyleSheet, View, Text } from 'react-native';
import NosyLogger from 'nosy-logger';

export default function App() {
  useEffect(() => {
    NosyLogger.init({ apiKey: 'some fake api key' })
      .then(() => console.log('initialized!'))
      .catch(console.error);
  }, []);

  const debug = () =>
    NosyLogger.log('hello from nosy logger debug example!', 'debug')
    .then(() => console.log('logged debug'))
    .catch(e => console.error(e));

  const info = () =>
    NosyLogger.log('hello from nosy logger example!')
    .then(() => console.log('logged info'))
    .catch(e => console.error(e));

  const warning = () =>
    NosyLogger.warning('hello from nosy logger warining example!')
    .then(() => console.log('logged warning'))
    .catch(e => console.error(e));

  const error = () =>
    NosyLogger.error('hello from nosy logger error example!')
      .then(() => console.log('logged error'))
      .catch(e => console.error(e));


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
