# currency-converter
> An application for fetching currency exchange rate from https://api.exchangeratesapi.io and return the converted amount


## Used technologies

* [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Maven](https://maven.apache.org/)

### Prerequisites

* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (version 1.8 or higher)

### Let's get started,

* Clone this repository.
* Install maven dependencies
* Check the locale config
```
    ├── src/main/resources/
    │     ├── application.properties
```
* When running the app (frontend and backend), the application can be accessed with two default urls

```
    http://localhost:8080
```

To run the application please follow below commands 

```
    mvn spring-boot:run
```

Can be ran directly from IntelliJ IDEA by going to `com.saikat.task.currencyconverter.CurrencyConverterApplication` and clicking run.

Build Spring Boot Project with Maven

   ```maven package```

Or
    
    mvn install / mvn clean install


REST API 

``` 
/api/convert

body JSON :
{
     	"sourceCurrency": "USD",
     	"targetCurrency": "EUR",
     	"amount": 10
}
```
