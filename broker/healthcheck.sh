#!/bin/sh

kafka-topics --bootstrap-server broker:29092 --list # blocks
kafka-topics --bootstrap-server broker:29092 --create --if-not-exists --topic vehicles-integer-json --replication-factor 1 --partitions 1
kafka-topics --bootstrap-server broker:29092 --list
