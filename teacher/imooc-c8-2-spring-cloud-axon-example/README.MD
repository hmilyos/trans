



## 测试
1. 创建用户

curl -X POST "http://localhost:8888/user/customers?name=imooc1&password=123456"
curl -X GET http://localhost:8888/user/customers

curl -X PUT http://localhost:8888/user/customers/dc3233cf-4623-4072-8341-0664d1e40da2/deposit/1000


2. 创建票：

curl -X POST http://localhost:8888/ticket/tickets?name=t1

3. 创建订单

curl -X POST -d '{"customerId": "dc3233cf-4623-4072-8341-0664d1e40da2", "title": "order_1", "ticketId": "def33634-fe06-42e1-89f2-4fec0074dc97", "amount": 100}' -H 'Content-Type: application/json' http://localhost:8888/order/orders

4. 并发测试

ab -n 20 -c 10 -T 'application/json' -p post.txt http://localhost:8888/order/orders