

-- 查看全局和当前session的事务隔离级别
SELECT @@GLOBAL.tx_isolation, @@tx_isolation;

-- 设置隔离级别
SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
-- level: READ UNCOMMITTED, READ COMMITTED, REPEATABLE READ,  SERIALIZABLE

-- session 1:
START TRANSACTION;
UPDATE t_user SET amount = amount + 100 WHERE username = 'BatMan';
UPDATE t_user SET amount = amount - 100 WHERE username = 'SuperMan';
COMMIT;
-- ROLLBACK;



-- session 2:
START TRANSACTION;
SELECT * FROM t_user
COMMIT;