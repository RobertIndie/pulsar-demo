import time
from pulsar import Client, ConsumerType, Timeout, InitialPosition
import os

TOPIC = 'python-test'
SUBSCRIPTION = 'THESUBSCRIPTION'
# SUBSCRIPTION =  f'testconsumer-{os.getpid()}'

def main():
    client = Client(service_url='pulsar://localhost:6650')
    sub = client.subscribe(
        topic=TOPIC,
        subscription_name=SUBSCRIPTION,
        consumer_type=ConsumerType.Shared,
        max_total_receiver_queue_size_across_partitions=1,
        consumer_name=f'testconsumer-{os.getpid()}',
        receiver_queue_size=1
    )
    while True:
        try:
            msg = sub.receive(100)
            mid = msg.message_id()
            print("partition:", mid.partition(), "ledger:", mid.ledger_id(), "entry:", mid.entry_id(), "batch:", mid.batch_index())
            break
        except Timeout:
            pass
    print("Got message, sleeping forever")
    while True:
        time.sleep(1)


if __name__ == '__main__':
    main()