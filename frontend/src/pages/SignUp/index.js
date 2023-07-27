import React, { useState, useEffect } from 'react';
import './Signup.css';
import { Link, useNavigate } from 'react-router-dom'
import { NAVER_AUTH_URL ,KAKAO_AUTH_URL, GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL, GITHUB_AUTH_URL } from '../../context/index';
import { signup } from '../../utils/APIUtils2';
import fbLogo from '../../img/fb-logo.png';
import googleLogo from '../../img/google-logo.png';
import githubLogo from '../../img/github-logo.png';
import kakaoLogo from '../../img/kakao-logo.png';
import naverLogo from '../../img/naver-logo.png';

import axios from "axios";
import { Success, Error } from './styles';
// import Alert from 'react-s-alert';

function Signup(props) {
    const [name, setName] = useState("");
    const [authenticated, setAuthenticated] = useState(false);
	const navigate = useNavigate();

    const handleInputChange = (event) => {
      const target = event.target;
      const inputName = target.name;
      const inputValue = target.value;
      setName({
        [inputName]: inputValue,
      });
    };
  
    const handleSubmit = (event) => {
      event.preventDefault();
      const signUpRequest = Object.assign({}, name);
      signup(signUpRequest)
        .then((response) => {
        //   Alert.success("회원가입에 성공하셨습니다.");
          setAuthenticated(true);
        })
        .catch((error) => {
        //   Alert.error((error && error.message) || "예기치 않은 문제가 발생하였습니다.");
        });
    };
  
    if (authenticated) {
		navigate('/', { state: { from: props.location } });
    }
  
    return (
      <div className="signup-container">
        <div className="signup-content">
          <h1 className="signup-title">회원가입</h1>
          <SocialSignup />
          <div className="or-separator">
            <span className="or-text">OR</span>
          </div>
          <SignupForm/>
          <span className="login-link">
            이미 계정이 있다면? <Link to="/auth/login">로그인!</Link>
          </span>
        </div>
      </div>
    );
  }
  
  function SocialSignup() {
    return (
      <div className="social-signup">
        <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
          <img src={googleLogo} alt="Google" />Google 회원가입
        </a>
        <a className="btn btn-block social-btn facebook" href={FACEBOOK_AUTH_URL}>
            <img src={fbLogo} alt="Facebook" />Facebook 회원가입
        </a>
        <a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
            <img src={githubLogo} alt="Github" />Github 회원가입
        </a>
        <a className="btn btn-block social-btn kakao" href={KAKAO_AUTH_URL}>
            <img src={kakaoLogo} alt="Kakao" />Kakao 회원가입
        </a>
        <a className="btn btn-block social-btn kakao" href={NAVER_AUTH_URL}>
            <img src={naverLogo} alt="Naver" />Naver 회원가입
        </a>
      </div>
    );
  }
  
  function SignupForm() {
    const [name, setName] = useState(""); //name
    const [nickname, setNickname] = useState(""); //name
    const [password, setPassword] = useState("");
    const [checkpassword, setCheckpassword] = useState("");
    const [email, setEmail] = useState("");
    const [isDuplicate, setIsDuplicate] = useState(true);
    const apiUrl = process.env.REACT_APP_API_URL;
    const navigate = useNavigate();

    const changeName = (event) => {
      setName(event.target.value);
    }

    const changeNickname = (event) => {
      setNickname(event.target.value);
    }

    const changePassword = (event) => {
      setPassword(event.target.value);
    }

    const changeCheckpassword = (event) => {
      setCheckpassword(event.target.value);
    }

    const changeEmail = (event) => {
      setEmail(event.target.value);
      setIsDuplicate(true);
    }

    /* 아이디 중복 체크 */
    const checkIdDuplicate = async () => {
          const req = {'email': email}
          const config = {"Content-Type": 'application/json'};
      await axios.post(`${apiUrl}/auth/checkIdDuplicate`, req, config) //user
        .then((resp) => {
          console.log("[SignUp.js] checkIdDuplicate() success :D");
          console.log(resp.data);

          if (resp.status == 200) {
            alert("사용 가능한 아이디입니다.");
            setIsDuplicate(false);					
          }
          
        })
        .catch((err) => {
          console.log("[SignUp.js] checkIdDuplicate() error :<");
          console.log(err);

          const resp = err.response;
          if (resp.status == 400) {
            alert("사용할 수 없는 아이디입니다.");
            setIsDuplicate(true);
          }
        });
    }

    /* 회원가입 */
    const signup = async () => {		
      if (!email){
        alert("아이디를 입력해주세요")
        return;
      }
      if (isDuplicate){
        alert("아이디 중복 확인이 필요합니다");		
        return;
      }
      else if (!name){
        alert("이름을 입력해주세요");
        return;
      }
      else if (!nickname){
        alert("닉네임을 입력해주세요");
        return;
      }
      else if (!password || !checkpassword){
        alert("비밀번호를 입력해주세요")
        return;
      }
      else if (password && checkpassword && password !== checkpassword){
        alert("비밀번호가 서로 일치하지 않습니다")
        return;
      }

      const req = {
        // id: id,
        'name': name,
        'nickName': nickname,
        'email': email,
        'password': password,
        // checkpassword: checkpassword,
      }
      const config = {"Content-Type": 'application/json'};
      
      await axios.post(`${apiUrl}/auth/signup`, req, config) // user/signup
        .then((resp) => {
          console.log("[SignUp.js] signup() success :D");
          console.log(resp.data);

          alert(name + "님 회원가입을 축하드립니다 🎊");
          navigate("/auth/login");

        }).catch((err) => {
          console.log("[SignUp.js] signup() error :<");
          console.log(err.response.data);

          // alert(err.response.data);

          const resp = err.response;
          if (resp.status == 400) {
            alert(resp.data);
          }
        });		
    }

    return (
      <div>
        <div className="form-item">
          <input type="text" name="name" value={name} onChange={changeName} className="form-control" placeholder="이름" required/>
        </div>
        <div className="form-item">
          <input type="text" name="nickname" value={nickname} onChange={changeNickname} className="form-control" placeholder="닉네임" required/>
        </div>
        <div className="form-item">
          <input type="email" name="email" value={email} onChange={changeEmail} className="form-control" placeholder="이메일" required/>
          <button id="duplicate" className="btn btn-outline-danger" onClick={checkIdDuplicate}><i className="fas fa-check"></i> 중복 확인</button>
        </div>
        <div className="form-item">
          <input type="password" name="password" value={password} onChange={changePassword} className="form-control" placeholder="비밀번호" required/>
        </div>
        <div className="form-item">
          <input type="password" name="password" value={checkpassword} onChange={changeCheckpassword} className="form-control" placeholder="비밀번호 확인" required/>
        </div>
        {password && checkpassword && password !== checkpassword &&
        <Error>비밀번호가 일치하지 않습니다!</Error>
        }
        {password && checkpassword && password === checkpassword &&
        <Success>비밀번호가 일치합니다!</Success>
        }
        <div className="form-item">
          <button onClick={signup} className="btn btn-block btn-primary">
            회원가입
          </button>
        </div>
      </div>

    );
  }

  export default Signup;

  // function SignupForm(props) {
  //   return (
  //     <form onSubmit={props.handleSubmit}>
  //       <div className="form-item">
  //         <input
  //           type="text"
  //           name="name"
  //           className="form-control"
  //           placeholder="Name"
  //           value={props.name}
  //           onChange={props.handleInputChange}
  //           required
  //         />
  //       </div>
  //       <div className="form-item">
  //         <input
  //           type="email"
  //           name="email"
  //           className="form-control"
  //           placeholder="Email"
  //           value={props.email}
  //           onChange={props.handleInputChange}
  //           required
  //         />
  //       </div>
  //       <div className="form-item">
  //         <input
  //           type="password"
  //           name="password"
  //           className="form-control"
  //           placeholder="Password"
  //           value={props.password}
  //           onChange={props.handleInputChange}
  //           required
  //         />
  //       </div>
  //       <div className="form-item">
  //         <button type="submit" className="btn btn-block btn-primary">
  //           Sign Up
  //         </button>
  //       </div>
  //     </form>
  //   );
  // }