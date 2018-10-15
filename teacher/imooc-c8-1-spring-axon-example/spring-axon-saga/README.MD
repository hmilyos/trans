

## 实例1测试

充值
ab -n 20 -c 10 -T 'application/json' -m PUT http://localhost:8080/accounts/43e28d0d-8736-4b4a-842a-a99481bd3981/deposit/100
提现
ab -n 20 -c 10 -T 'application/json' -m PUT http://localhost:8080/accounts/f55dc8ce-b960-4f20-a9df-04fc72878e06/withdraw/200


## 测试
1. 创建用户

curl -X POST "http://localhost:8080/customers?name=imooc1&password=123456"
curl -X GET http://localhost:8080/customers

curl -X PUT http://localhost:8080/customers/1080c5a1-a088-4f77-bfde-8d05c72eab46/deposit/1000


2. 创建票：

curl -X POST http://localhost:8080/tickets?name=t1

3. 创建订单

curl -X POST -d '{"customerId": "69ab921b-10a6-4ef4-a88e-f0cd3328f9bd", "title": "order_1", "ticketId": "0cd8adf3-5c11-4ffa-94d1-ad05d3d2d69f", "amount": 100}' -H 'Content-Type: application/json' http://localhost:8080/orders

超时：
curl -X POST -d '{"customerId": "f352dae2-3f0c-4298-b01b-7d43b6e885df", "title": "order_timeout", "ticketId": "29bf1d49-4a77-46dd-8ace-2fc469116f3e", "amount": 0}' -H 'Content-Type: application/json' http://localhost:8080/orders

4. 性能测试

充值
ab -n 20 -c 10 -T 'application/json' -m PUT http://localhost:8080/customers/f55dc8ce-b960-4f20-a9df-04fc72878e06/deposit/100
提现
ab -n 20 -c 10 -T 'application/json' -m PUT http://localhost:8080/customers/f55dc8ce-b960-4f20-a9df-04fc72878e06/withdraw/200

curl -X GET http://localhost:8080/orders/tickets
curl -X GET http://localhost:8080/orders/customers

ab -n 20 -c 10 -T 'application/json' -p oneUserAllTicket.txt http://localhost:8080/orders/test/oneUserAllTicket

ab -n 20 -c 10 -T 'application/json' -p allUserOneTicket.txt http://localhost:8080/orders/test/allUserOneTicket
