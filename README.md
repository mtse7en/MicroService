# MicroService
 Spring boot & Micro Service & ActiveMQ
 
# To Start All Project :

# For use ActiveMq

1 - install ActiveMq
2 - Go to bin path
3 - open powershell
4 - Run command "activemq.bat start" at bin path.

# For DB 

docker pull mongo:4.0.4     --- Docker Mongo DB Image download
docker run -d -p 27017-27019:27017-27019 --name notedb mongo:4.0.4     --- notedb is running on 27019 port.
docker ps -a   ve ya docker container ls -a       --- To show running instance 
docker container rm 17bde2d227a7     ---to remove running instanc

# For server side :

1 - Maven update all projects.
2 - Run spring-eureka-server
3 - Run pring-eureka-note-service, pring-eureka-email-service, pring-eureka-note-service
4 - pring-eureka-aut, pring-eureka-common
5 - pring-eureka-zuul service.
6 - to show all running services http://localhost:8761/ 


# For client side
 
1 - Open ClientApp with VSCode
2 - run "npm install" at ClientApp path. (need node.js if you havent download)
3 - Open terminal on VSCode
4 - run command "npm run start"
5 - To solve cors problem you should cors disable chrome. So run command "npm run open" and it will starts 4200 ports.

# REFERENCE : 
Docker and Mongo DB instal : https://hub.docker.com/_/mongo

Spring Boot & Micro Services : https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-intro-to-microservices-part-1-c0d24cd422c3

Mongo DB Using : https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i

ActiveMq Using : https://activemq.apache.org/hello-world

