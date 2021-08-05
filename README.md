# Wildfly POC

This is a case study, how Wildfly could be used in modern (cloud native) application development.
Therefore I like to test several simple use cases with Wildfly-Jar-Maven-Plugin, Galleon Provisioning, JakartaEE8, MicroProfile, Kafka, Reactive Programming.

## Local

Locally you can start a test instance of Kafka/Zookeeper/React-Frontend with `docker compose up`.
Then you can develop the Wildfly JEE application locally with `mvn wildfly-jar:dev-watch [-pDebug]`.

## Remote

`helm` charts are used to deploy the app on Openshift. As an Openshift Cluster is needed for this test I use the [Openshift Sandbox provided by RedHat](https://developers.redhat.com/developer-sandbox).

Login to your cluster via `oc login ...`

To deploy kafka I use bitnami kafka chart:

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install mail bitnami/kafka -f infra/kafka-chart-values.yml
```

To deploy the wildfly server I use the official RedHat Wildfly Chart:

```bash
helm repo add wildfly https://docs.wildfly.org/wildfly-charts/
helm install app wildfly/wildfly -f infra/wildfly-chart-values.yml
```

## Further Reading

- [bootable jar docs](https://docs.wildfly.org/bootablejar/)
- [galleon provisioning guide](https://docs.wildfly.org/24/Galleon_Guide.html)
- [bitnami kafka chart docs](https://github.com/bitnami/charts/tree/master/bitnami/kafka/)
- [wildfly chart docs](https://github.com/wildfly/wildfly-charts)
- [MicroProfile](https://microprofile.io)
- [SmallRye Projects](https://smallrye.io/projects/)
- [MicroProfile/SmallRye Reactive Messaging](https://smallrye.io/smallrye-reactive-messaging/smallrye-reactive-messaging/3.8/index.html)
- [MicroProfile Rest Client](https://github.com/eclipse/microprofile-rest-client)
- [create react app](https://create-react-app.dev)
