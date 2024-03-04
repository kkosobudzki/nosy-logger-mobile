# Nosy Logger React Native

## Installation

```sh
npm install @nosytools/logger-mobile
```

## Usage

```js
import * as logger from '@nosytools/logger-mobile';

// (...)

logger.init('api key for your project environment');

logger.info('info message');
logger.debug('debug message');
logger.warning('warning message');
logger.error('error message');
```
