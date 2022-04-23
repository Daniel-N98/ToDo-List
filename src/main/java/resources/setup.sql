create database if not exists sql4486328;
use sql4486328;
create table if not exists ToDoList
(
    id INT auto_increment,
    title VARCHAR(40),
    description VARCHAR(500),
    timestamp VARCHAR(25),
    dueDate VARCHAR(25),
    status VARCHAR(10),
    primary key (id)
);
