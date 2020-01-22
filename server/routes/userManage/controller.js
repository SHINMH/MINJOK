const bcrypt = require('bcrypt');
const models = require('../../models');
const {userinfo} = require('../../models'); //sequelize 모듈을 통해 userinfo 테이블과 연동되는 변수
const saltRounds = 10; //bcrypt 해쉬 생성을 위한 변수

//회원가입
exports.register = function(request, response) {
  const userID = request.body.id;
  const userPassword = request.body.pw; //요청 body를 통해 id,pw설정
  var flag=0;
  let user;

  const encrypt = (foundResult) => { //user 테이블에서 userid를 검색한 결과를 foundResult 매개변수를 통해 접근
    if(foundResult != undefined){ //결과값이 1줄이상이면 해당 id가 이미 존재함
      response.json({
        registerStatus: 'existingID', //아이디가이미 있다는 메세지 전송
        code : 400
      });
      throw new Error('close'); //promise 호출을 중단하기 위해 에러를 throw 함
    }
    else
      return bcrypt.hash(userPassword, saltRounds); //아이디가 없을 경우 비밀번호를 bcrypt모듈을 통해 해쉬함
  }

  const create = (hashedPassword) => { //해쉬된 비밀번호를 매개변수를 받아 테이블row생성
    console.log('****creating process****');
    console.log(hashedPassword);
    return userinfo.create({
      id: userID,
      pw: hashedPassword,
    })
  }

  const respond = () => {
    response.json({
      registerStatus: 'registerSuccess',
      code: 200 //회원가입 처리가 완료되었다는 메세지와 코드 전송
    })
  }

//함수 호출 단계
  userinfo.findUserByID(userID) //sequelize 모델에 정의된 함수를 호출 id를 바탕으로 해당 회원정보를 탐색함
  .then(encrypt)
  .then(create)
  .then(respond)
  .catch(err=>console.log("***stop point***"))
}

//로그인
exports.login = (request, response) => {
  const userID = request.body.id;
  const userPassword = request.body.pw; //요청 body를 통해 id,pw설정
  let user; //조회결과가 담기는 변수

  const decrypt = (foundResult) => {//user 테이블에서 userid를 검색한 결과를 foundResult 매개변수를 통해 접근
    if(foundResult != undefined){//결과값이 1줄이상이면 해당 id가 존재하는 유저임을 나타냄
      user = foundResult;
      console.log('****decrypting process****');
      return bcrypt.compareSync(userPassword, foundResult.pw) //bcrypt 모듈을 통해 비밀번호 검사
    } else {
      response.json({
        loginStatus: "invalid ID", //아이디조회 결과가 undefined일 경우 해당 아이디가 없음을 전송
        code: 400
      })
      console.log('invalid ID');
    }
    throw new Error('close'); //promise 함수 호출 종료를 위한 error throw

  }


  const respond = (authenticated) => { //비밀번호 검사결과를 매개변수로 받아 실행
    if(authenticated){ //비밀번호가 일치 할 경우
      console.log('****login success****');
      response.json({
        loginStatus: "login success",
        code: 200,
        id: user.id,
        pw: user.pw
      }); //로그인결과를 응답함
      throw new Error('close'); //promise 함수호출 종료를 위한 error throw
    } else {
      return false; //비밀번호가 일치 하지않을 경우 false 리턴
    }
  }

  const errRespond = (loginflag) => {
    if(!loginflag){ //로그인 플래그가 false일 경우 에러 메세지를 응답
      console.log('****login err****');
      response.send({
        loginStatus: "inccorectPassword",
        code: 400
      });
    }
  }

//함수호출 단계
  userinfo.findUserByID(userID)
  .then(decrypt)
  .then(respond)
  .then(errRespond)
  .catch(err=>console.log("***stop point***"))

}
