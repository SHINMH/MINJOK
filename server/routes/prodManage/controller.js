const bcrypt = require('bcrypt');
const models = require('../../models');
const {prodlist} = require('../../models'); //sequelize 모듈을 통해 prodlist 테이블과 연동되는 변수

//모든 상품 정보 반환
exports.getAllProdList = function(request, response) {

  const respond = (prodList) => {
    response.json(prodList);
  }

  prodlist.getAllList() //  //sequelize모듈을 통해 model부분에 정의된 reviewlist테이블에서 deleteReview 호출
  .then(respond)
}
