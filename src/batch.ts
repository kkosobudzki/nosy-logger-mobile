type Send<T> = (items: T[]) => Promise<void>;

type Seconds = number; // type alias to make things clear

type Config = {
  maxInterval?: Seconds;
  maxQueueLength?: number;
};

export default function Batch<T>(
  send: Send<T>,
  { maxInterval = 5_000, maxQueueLength = 20 }: Config = {}
) {
  let queue: T[] = [];
  let flushing: boolean = false;
  let timeoutId: ReturnType<typeof setTimeout> | undefined;

  async function push(item: T): Promise<void> {
    queue.push(item);

    if (queue.length >= maxQueueLength) {
      return flush();
    }

    return Promise.resolve().then(schedule);
  }

  async function flush(): Promise<void> {
    if (flushing) {
      return Promise.resolve();
    }

    flushing = true;

    const items = queue.splice(0);

    if (items.length === 0) {
      return Promise.resolve();
    }

    schedule();

    return send(items)
      .catch((e) => {
        console.error(e);

        queue.unshift(...items);
      })
      .finally(() => { flushing = false; });
  }

  function schedule() {
    if (timeoutId === undefined) {
      console.log('batch: scheduling');

      timeoutId = setTimeout(() => {
        timeoutId = undefined;

        flush();
      }, Date.now() + maxInterval);
    }
  }

  function stop() {
    flush();

    timeoutId && clearTimeout(timeoutId);

    timeoutId = undefined;
  }

  return { push, stop };
}
