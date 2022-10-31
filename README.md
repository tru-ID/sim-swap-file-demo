# sim-swap-file-demo

Will peform SIM Swap check on phone numbers included in the provided csv file , using tru.ID [SIMCheck API](https://developer.tru.id/docs/reference/products#tag/sim_check_v0.1)  


### To run with Spring 

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
mvn clean install -DskipTests=true
```

and run

```
java -jar ./target/sim-swap-file-0.0.1.jar sample.csv --tru.url=https://{data_residency}.api.tru.id --tru.client=change_me --tru.secret=change_me
```

### Example

`sample.csv`
```
33621770000,
33621770001,
33621770002,
336212,
33621770099,
```

Run the program using your tru.ID project credentials

`output` using tru.ID [sandbox](https://developer.tru.id/docs/basics#sandbox-mode)
```
33621770000,COMPLETED,true
33621770001,COMPLETED,true
33621770002,COMPLETED,true
336212,ERROR,A valid phone number is required
33621770099,ERROR,mno_server_error

Processed file: sample.csv rows 5 in 6s
```
