# Order-System


```
   ____          _           _____           _                 
  / __ \        | |         / ____|         | |                
 | |  | |_ __ __| | ___ _ _| (___  _   _ ___| |_ ___ _ __ ___  
 | |  | | '__/ _` |/ _ \ '__\___ \| | | / __| __/ _ \ '_ ` _ \ 
 | |__| | | | (_| |  __/ |  ____) | |_| \__ \ ||  __/ | | | | |
  \____/|_|  \__,_|\___|_| |_____/ \__, |___/\__\___|_| |_| |_|
                                    __/ |                      
                                   |___/                       
```

![](https://img.shields.io/badge/java-1.8-blue.svg) ![](https://img.shields.io/badge/springboot-2.0.4-blue.svg)

测试地址: http://dengqlbq.xyz:8080/order_system/seller/user/index

测试账号: man，haha | women，hehe (登录页加载慢)

---

### How to use

```
git clone https://github.com/Dengqlbq/Order-System.git
```

Override the application.yml

```
spring.datasource.*
spring.redis.*
```

Override the templates/order/list.ftl

```
websocket = new WebSocket('ws://{OVERRIDE}/order_system/webSocket');
```

```
mvn clean package -Dmaven.test.skip=true
java -jar target/order_system.jar
```

---

### Show

#### Seller :

![](https://github.com/Dengqlbq/Order-System/blob/master/image/login.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/orderlist.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/orderdetail.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/productlist.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/productmodify.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/productadd.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/category.png)

####  Buyer :

![](https://github.com/Dengqlbq/Order-System/blob/master/image/b_order_list.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/b_order_detail.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/b_order_create.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/b_order_cancel.png)<br>
![](https://github.com/Dengqlbq/Order-System/blob/master/image/b_productlist.png)







