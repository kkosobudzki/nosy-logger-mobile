import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { log } from 'nosy-logger';

export default function App() {
  return (
    <View style={styles.container}>
      <Text onPress={() => log('hello from nosy logger example!')}>Press to log message</Text>
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
