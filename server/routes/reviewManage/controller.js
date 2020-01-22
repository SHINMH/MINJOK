const bcrypt = require('bcrypt');
const models = require('../../models');
const {reviewlist} = require('../../models'); //sequelize 모듈을 통해 reviewlist 테이블과 연동되는 변수

//모든 리뷰 리스트 반환
exports.getAllReviewList = function(request, response) {

  const respond = (reviewList) => {
    response.json(reviewList);
  }
  //sequelize모듈을 통해 model부분에 정의된 reviewlist테이블에서 getAllList함수 호출
  reviewlist.getAllList()
  .then(respond) //결과를 응답함
}

//내리뷰 리스트 반환
exports.getMyReviewList = function(request, response) {

  const respond = (reviewList) => {
    response.json(reviewList);
  }

  //sequelize모듈을 통해 model부분에 정의된 reviewlist테이블에서 getMyList 호출
  reviewlist.getMyList(request.body.id)//요청받은 id를 매개변수로 넘김
  .then(respond) //결과를 응답함.
}

//상품에 담긴 리뷰리스트를 반환함
exports.getProdReviewList = function(request, response) {

  const respond = (reviewList) => {
    response.json(reviewList);
  }

  //sequelize모듈을 통해 model부분에 정의된 reviewlist테이블에서 getProdReviewList 호출
  reviewlist.getProdReviewList(request.body.prodNumber) //요청받은 제품 번호를 매개변수로 넘김
  .then(respond)
}

//리뷰 업로드
exports.uploadReview = function (request, response) {
  var reviewUser=request.body.id;
  var prodNumber=request.body.prodNumber;
  var reviewTitle=request.body.reviewTitle;
  var reviewContent=request.body.reviewContent;
 //요청받은 항목들을 각각 변수에 담음

  const create = () => {
    console.log('****upload review process****');
    return reviewlist.create({ //새로운 리뷰를 테이블에 insert함
      reviewUser : reviewUser,
      prodNumber : prodNumber,
      reviewTitle : reviewTitle,
      reviewContent : reviewContent
    }).catch(function(err) {
         console.log(err);
    });
  }

  const respond = () => {
    response.json({
      registerStatus: 'upload Success', //업로드 결과를 반환함
      code: 200
    })
  }

  create()
  .then(respond)
}

//리뷰 삭제
exports.deleteReview = function (request, response) {
  const respond = () => {
    response.json({
      registerStatus: 'delete Success',
      code: 200
    })
  }
  //sequelize모듈을 통해 model부분에 정의된 reviewlist테이블에서 deleteReview 호출
  reviewlist.deleteReview(request.body.reviewNumber) //요청받은 리뷰 번호를 매개변수로 넘김
  .then(respond)
}

exports.updateReview = function (request, response) {
  var reviewTitle=request.body.reviewTitle;
  var reviewContent=request.body.reviewContent;
  var reviewNumber=request.body.reviewNumber;
  //요청받은 항목들을 각각 변수에 담음

    const update=()=>{
      return reviewlist.update({ // 요청받은 항목들을 update함
                        reviewTitle : reviewTitle,
                        reviewContent : reviewContent
                      },
                     { where: {reviewNumber: reviewNumber}})
        }

      const respond = () => {
        response.json({
          registerStatus: 'update Success',
          code: 200
        })
      }

      update()
      .then(respond)

}
