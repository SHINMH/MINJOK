const express = require('express');
const router = express.Router();

const models = require('../../models');

const controller = require('./controller');

router.post('/all', controller.getAllProdList);

module.exports = router;
