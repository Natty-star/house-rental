Name of members
- Esmelealem Mihretu: 614695
- Selamawit Woldeyohannes: 614647
- Zedagem Demelash: 614742

## How to run our project
Make sure you have docker setup on your machine

1.	First make sure you are in project folder and then run the docker-compose to deploy the containers
>> docker-compose up
2.	Then login using this admin account, username= admin, password = 123456
>> http://localhost:8081/authenticate Method POST\
> { \
> "username":"admin", \
> "password":"123456" \
> }
3. To populate the data add the token to the request header and make a POST request to
>> http://localhost:8081/load

4. To view the data populated on the database. Add the token in the header and send a GET request to
>> http://localhost:8081/getData

- or connect to mysql database using the following parameters and read table named 'student'
>>port= 3307 \
Username= sa \
password= cs590 \
Schema name = batch

## NOTE: When building the image it may restart more than once just give it time for it to run 


 