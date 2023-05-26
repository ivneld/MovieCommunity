import React, { useState, useEffect, useContext } from 'react';
import './Login.css';
import { NAVER_AUTH_URL ,KAKAO_AUTH_URL ,GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL, GITHUB_AUTH_URL, ACCESS_TOKEN, REFRESH_TOKEN } from '../../context/index';
import { login } from '../../utils/APIUtils2';
import { Link, useNavigate } from 'react-router-dom';
import fbLogo from '../../img/fb-logo.png';
import googleLogo from '../../img/google-logo.png';
import githubLogo from '../../img/github-logo.png';
import kakaoLogo from '../../img/kakao-logo.png';
import naverLogo from '../../img/naver-logo.png';

import axios from "axios";
import { AuthContext } from '../../context/AuthProvider';
import { HttpHeadersContext } from '../../context/HttpHeadersProvider';
import jwt_decode from "jwt-decode";
// import Alert from 'react-s-alert';

function Login() {
	return (
	  <div className="login-container">
		<div className="login-content">
		  <h1 className="login-title">로그인</h1>
		  <SocialLogin/>
		  <div className="or-separator">
			<span className="or-text">OR</span>
		  </div>
		  <LoginForm/>
		  <span className="signup-link">
			새로운 유저라면? <Link to="/auth/signup">회원가입!</Link>
		  </span>
		</div>
	  </div>
	);
  }
  
function SocialLogin() {
	return (
	  <div className="social-login">
		<a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
		  <img src={googleLogo} alt="Google" /> Google 로그인
		</a>
		<a className="btn btn-block social-btn facebook" href={FACEBOOK_AUTH_URL}>
		  <img src={fbLogo} alt="Facebook" /> Facebook 로그인
		</a>
		<a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
		  <img src={githubLogo} alt="Github" /> Github 로그인
		</a>
		<a className="btn btn-block social-btn kakao" href={KAKAO_AUTH_URL}>
		  <img src={kakaoLogo} alt="Kakao" /> Kakao 로그인
		</a>
		<a className="btn btn-block social-btn kakao" href={NAVER_AUTH_URL}>
		  <img src={naverLogo} alt="Naver" /> Naver 로그인
		</a>
	  </div>
	);
  }
  
  function LoginForm() {
	const { auth, setAuth } = useContext(AuthContext);
	const { headers, setHeaders } = useContext(HttpHeadersContext);
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const navigate = useNavigate();

	const changeEmail = (event) => {
	  setEmail(event.target.value);
	}
  
	const changePassword = (event) => {
	  setPassword(event.target.value);
	}
  
	const login = async () => {
	  const req = {
		email: email,
		password: password
	  }
  
	  try {
		const response = await axios.post("http://localhost:8080/auth/signin", req);
		const { accessToken, refreshToken } = response.data;
  
		console.log("성공적으로 로그인 되었습니다 🔐");
  
		localStorage.setItem("bbs_access_token", accessToken);
		localStorage.setItem("bbs_refresh_token", refreshToken);
		setHeaders({ "Authorization": `Bearer ${accessToken}` });
  
		navigate("/");
		window.location.reload();
	  } catch (error) {
		console.log("[Login.js] login() error :<");
		console.log(error);
  
		console.log("⚠️ 로그인 정보가 일치하지 않습니다");
	  }
	}
	  
	return (
	<>
		<div className="form-item">
		<input 
			type="email" 
			name="email" 
			className="form-control" 
			placeholder="이메일"
			value={email} 
			onChange={changeEmail} 
			required
		/>
		</div>
		<div className="form-item">
		<input 
			type="password" 
			name="password" 
			className="form-control" 
			placeholder="비밀번호"
			value={password} 
			onChange={changePassword} 
			required
		/>
		</div>
		<div className="form-item">
		<button className="btn btn-block btn-primary" onClick={login}>로그인</button>
		</div>
	</>
	);
}
  
	export default Login