//모듈 불러오기
const express = require('express');
const bodyParser = require('body-parser');
const morgan = require('morgan');
const models = require('./models');
var mysql = require('mysql');
var dbconfig = require('./config/dbconfig.js');
const path = require('path');
var fs =require('fs');

//라우팅 불러우기
const prodManageRouter = require('./routes/prodManage/index.js');
const userManageRouter = require('./routes/userManage/index.js');
const reviewManageRouter = require('./routes/reviewManage/index.js');

const app = express();

//시퀄라이즈럴 통한 DB연결
models.sequelize.sync()
    .then(() => {
        console.log('✓ DB connection success.');
        console.log(' Press CTRL-C to stop\n');
    })
    .catch(err => {
        console.error(err);
        console.log('✗ DB connection error. Please make sure DB is running');
        process.exit();
    });


app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());


app.use(function(req, res, next) {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
    next();
});
app.use(morgan('dev'));

// test route
app.get('/', function(req, res) {
    res.send(`
    <html>
    <head>
        <title>default page</title>
    </head>
    <body>
    <p>Welcome to MINJOKK</p>
    </body>
    </html>
    `)
    //res.json({ message: 'welcome to Vocie Paper!' });
});

//route
app.use('/user',userManageRouter);
app.use('/prod',prodManageRouter);
app.use('/review',reviewManageRouter);

app.listen(8080);
console.log('server start');
