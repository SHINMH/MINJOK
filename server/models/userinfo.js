module.exports = (sequelize, Datatypes) => {
  var userinfo = sequelize.define('userinfo', {
      id: {
        type: Datatypes.STRING(50),
        allowNull: false,
        primaryKey: true
      },
      pw: {
        type: Datatypes.STRING(200),
        allowNull: false
      }
  },
  {
      classMethods: {},
      tableName: 'userinfo',
      freezeTableName: true,
      underscored: false,
      timestamps: false

  });

  //sequelize의 orm지원 함수인 findOne 메소드를 통해 매개변수로 받은 id를 통해 테이블을 검사후 결과를 반환한다.
  userinfo.findUserByID = function(id) {
   console.log('****finding process****');
   return userinfo.findOne({
     where: { id: id}
   })
 }
  return userinfo;

};
