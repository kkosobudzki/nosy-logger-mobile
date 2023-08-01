import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { init, log } from 'nosy-logger';

init({ apiKey: 'some fake api key' });

export default function App() {
  const send = () =>
    log('hello from nosy logger example!')
      .then(() => console.log('logged'))
      .catch(e => console.error(e));

  return (
    <View style={styles.container}>
      <Text onPress={send}>Press to log message</Text>
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
