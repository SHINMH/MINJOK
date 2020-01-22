const express = require('express');
const router = express.Router();

const models = require('../../models');

const controller = require('./controller');

router.post('/all', controller.getAllReviewList);
router.post('/my', controller.getMyReviewList);
router.post('/prod', controller.getProdReviewList);
router.post('/upload', controller.uploadReview);
router.post('/modify', controller.updateReview);
router.post('/delete', controller.deleteReview);


module.exports = router;
