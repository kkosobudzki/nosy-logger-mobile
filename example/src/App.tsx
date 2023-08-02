import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { init, log } from 'nosy-logger';

init({ apiKey: 'some fake api key' });

export default function App() {
  const debug = () =>
    log('hello from nosy logger debug example!')
    .then(() => console.log('logged debug', 'debug'))
    .catch(e => console.error(e));

  const info = () =>
    log('hello from nosy logger example!')
    .then(() => console.log('logged info'))
    .catch(e => console.error(e));

  const warning = () =>
    log('hello from nosy logger warining example!')
    .then(() => console.log('logged warning', 'warn'))
    .catch(e => console.error(e));

  const error = () =>
    log('hello from nosy logger error example!')
      .then(() => console.log('logged error', 'error'))
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
