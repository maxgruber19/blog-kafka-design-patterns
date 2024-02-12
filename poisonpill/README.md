# Poison Pill
When using Apache Kafka the life of consumers can be harder than the one of producers. Producers may write incompatible messages to topics they don't know who is listening to. Using a schema registry can increase compatibility but it's not able to do the whole job. The consequences incompatibility can break down consuming applications by creating an endless loop if consumers don't handle the poison pill accordingly.

This short guide will show you how to deal with garbage read from Apache Kafka by using the spring-kafka library which is very powerful in decoupled microservice scenarios.

Inspiration and a very good example of how to do it the right way is a blogpost by ING you can find here https://www.confluent.io/blog/spring-kafka-can-your-kafka-consumers-handle-a-poison-pill/

This guide will deliver further information and a ready to run code example for a simple order management with automatically generated testdata.

## Prerequisites

We'll need a Apache Kafka Cluster with 