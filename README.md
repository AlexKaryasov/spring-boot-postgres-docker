# How to build it

```
gradle clean bootJar
```

# How to run it

```
docker compose up --build
```

# How to insert analytics events

````shell
Invoke-RestMethod -Method Post `
>>   -Uri "http://localhost:8080/analytics/event?source=api&message=query+executed+2"
````