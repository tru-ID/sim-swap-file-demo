# sim-swap-file-demo


### To run

```
mvn spring-boot:run -Dspring-boot.run.arguments="sample.csv --tru.url=https://{data_residency}.api.tru.id --tru.client=change_me --tru.secret=change_me"
```

where
- sample.csv: name of the csv file include all the phone numbers
- tru.url: the API endpoint associated with your tru.ID account, to register goes to https://tru.id/signup 
- tru.client: the client id of your tru.ID project
- tru.secret: the client secret of your tru.ID project



### To install a "fat jar"

```
mvn clean install -DskipTests=tru
```

and run

```
java -jar ./target/simcheck-0.0.1-SNAPSHOT.jar sample.csv --tru.url=https://{data_residency}.api.tru.id --tru.client=change_me --tru.secret=change_me
```

