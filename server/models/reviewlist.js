module.exports = (sequelize, Datatypes) => {
  var reviewlist = sequelize.define('reviewlist', {
      reviewNumber: {
        type: Datatypes.INTEGER,
        allowNull: false,
        autoIncrement: true,
        primaryKey: true
      },
      prodNumber: {
        type: Datatypes.INTEGER
      },
      reviewUser: {
        type: Datatypes.STRING(45),
        allowNull: false
      },
      reviewTitle: {
        type: Datatypes.STRING(45),
        allowNull: false
      },
      reviewContent: {
        type: Datatypes.STRING(200),
        allowNull: false
      }
    },

  {
      classMethods: {},
      tableName: 'reviewlist',
      freezeTableName: true,
      underscored: false,
      timestamps: false

  });

  //sequelize의 orm지원 함수인 findALl 메소드를 통해 모든 항목을 반환한다.
  reviewlist.getAllList = function() {
   console.log('****get all review list process****');
   return reviewlist.findAll()
 }

 //sequelize의 orm지원 함수인 findALl 메소드를 통해 매개변수로 받은 id가 작성한 모든 항목을 반환한다.
 reviewlist.getMyList = function(id) {
  console.log('****get my review list process****');
  return reviewlist.findAll({
    where : { reviewUser : id }
  })
}

//sequelize의 orm지원 함수인 findALl 메소드를 통해 매개변수로 받은 상품번호에 달린 항목을 반환한다.
reviewlist.getProdReviewList = function(prodNumber) {
 console.log('****get my review list process****');
 return reviewlist.findAll({
   where : { prodNumber : prodNumber }
 })
}

//sequelize의 orm지원 함수인 destroy 메소드를 통해 매개변수로 받은 리뷰번호 항목을 삭제한다.
reviewlist.deleteReview = function(reviewNumber) {
 console.log('****delete review process****');
 return reviewlist.destroy({
   where : { reviewNumber : reviewNumber }
 })
}
  return reviewlist;

};
