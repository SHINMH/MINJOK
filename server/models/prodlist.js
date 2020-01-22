module.exports = (sequelize, Datatypes) => {
  var prodlist = sequelize.define('prodlist', {
      prodNumber: {
        type: Datatypes.INTEGER,
        allowNull: false,
        autoIncrement: true,
        primaryKey: true
      },
      prodName: {
        type: Datatypes.STRING(45),
        allowNull: false
      },
      prodPrice: {
        type: Datatypes.STRING(45),
        allowNull: false
      },
      prodImage: {
        type: Datatypes.STRING(200),
        allowNull: false
      },
      prodCompany: {
        type: Datatypes.STRING(10),
        allowNull: false
      }

  },
  {
      classMethods: {},
      tableName: 'prodlist',
      freezeTableName: true,
      underscored: false,
      timestamps: false

  });

//sequelize의 orm지원 함수인 findALl 메소드를 통해 모든 항목을 반환한다.
  prodlist.getAllList = function(id) {
   console.log('****get all prod list process****');
   return prodlist.findAll()
 }

  return prodlist;

};
