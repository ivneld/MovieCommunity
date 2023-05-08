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

// import Alert from 'react-s-alert';

function Login() {
	return (
	  <div className="login-container">
		<div className="login-content">
		  <h1 className="login-title">Sign In</h1>
		  <SocialLogin/>
		  <div className="or-separator">
			<span className="or-text">OR</span>
		  </div>
		  <LoginForm/>
		  <span className="signup-link">
			New user? <Link to="/auth/signup">Sign up!</Link>
		  </span>
		</div>
	  </div>
	);
  }
  
function SocialLogin() {
	return (
	  <div className="social-login">
		<a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
		  <img src={googleLogo} alt="Google" /> Sign in with Google
		</a>
		<a className="btn btn-block social-btn facebook" href={FACEBOOK_AUTH_URL}>
		  <img src={fbLogo} alt="Facebook" /> Sign in with Facebook
		</a>
		<a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
		  <img src={githubLogo} alt="Github" /> Sign in with Github
		</a>
		<a className="btn btn-block social-btn kakao" href={KAKAO_AUTH_URL}>
		  <img src={kakaoLogo} alt="Kakao" /> Sign in with Kakao
		</a>
		<a className="btn btn-block social-btn kakao" href={NAVER_AUTH_URL}>
		  <img src={naverLogo} alt="Naver" /> Sign in with Naver
		</a>
	  </div>
	);
  }
  
function LoginForm() {
	const { auth, setAuth } = useContext(AuthContext);
	const { headers, setHeaders } = useContext(HttpHeadersContext);

	const navigate = useNavigate();

	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");

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

		await axios.post("http://localhost:8080/auth/signin", req) // user/login
		.then((resp) => {
			console.log("[Login.js] login() success :D");
			console.log(resp.data);

				// alert(resp.data.email + "님, 성공적으로 로그인 되었습니다 🔐");
				alert(email + "님, 성공적으로 로그인 되었습니다 🔐");

				// JWT 토큰 저장
				localStorage.setItem("bbs_access_token", resp.data.accessToken);
				localStorage.setItem("bbs_refresh_token", resp.data.refreshToken);
				localStorage.setItem("email", resp.data.email);

				setAuth(resp.data.email); // 사용자 인증 정보(아이디 저장)
				setHeaders({"Authorization": `Bearer ${resp.data.accessToken}`}); // 헤더 Authorization 필드 저장
				setHeaders({"Authorization_refresh": `Bearer ${resp.data.refreshToken}`}); // 헤더 Authorization 필드 저장

				navigate("/");
                window.location.reload();

		}).catch((err) => {
			console.log("[Login.js] login() error :<");
			console.log(err);

			// alert("⚠️ " + err.response.data);
			alert("⚠️ 로그인 정보가 일치하지 않습니다");
		});
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

	// function Login(props) {
	// 	const [error, setError] = useState(null);
	// 	const navigate = useNavigate();
		
	// 	useEffect(() => {
	// 	  if (props.location?.state?.error) {
	// 		setTimeout(() => {
	// 		//   Alert.error(props.location.state.error, {
	// 		// 	timeout: 5000
	// 		//   });
	// 		navigate(props.location.pathname, { state: {} });
	// 		}, 100);
	// 	  }
	// 	}, [props.location?.state?.error, props.location?.pathname]);
	  
	// 	if (props.authenticated) {
	// 		navigate('/', { state: { from: props.location } });
	// 	}
	
	// 	return (
	// 	  <div className="login-container">
	// 		<div className="login-content">
	// 		  <h1 className="login-title">Sign In</h1>
	// 		  <SocialLogin />
	// 		  <div className="or-separator">
	// 			<span className="or-text">OR</span>
	// 		  </div>
	// 		  <LoginForm {...props} />
	// 		  <span className="signup-link">
	// 			New user? <Link to="/auth/signup">Sign up!</Link>
	// 		  </span>
	// 		</div>
	// 	  </div>
	// 	);
	//   }
	  
	//   function SocialLogin() {
	// 	return (
	// 	  <div className="social-login">
	// 		<a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
	// 		  <img src={googleLogo} alt="Google" /> Sign in with Google
	// 		</a>
	// 		<a className="btn btn-block social-btn facebook" href={FACEBOOK_AUTH_URL}>
	// 		  <img src={fbLogo} alt="Facebook" /> Sign in with Facebook
	// 		</a>
	// 		<a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
	// 		  <img src={githubLogo} alt="Github" /> Sign in with Github
	// 		</a>
	// 		<a className="btn btn-block social-btn kakao" href={KAKAO_AUTH_URL}>
	// 		  <img src={kakaoLogo} alt="Kakao" /> Sign in with Kakao
	// 		</a>
	// 		<a className="btn btn-block social-btn kakao" href={NAVER_AUTH_URL}>
	// 		  <img src={naverLogo} alt="Naver" /> Sign in with Naver
	// 		</a>
	// 	  </div>
	// 	);
	//   }
	  
	//   function LoginForm(props) {
	// 	  const [email, setEmail] = useState('');
	// 	  const [password, setPassword] = useState('');
		
	// 	  const handleEmailChange = (event) => {
	// 		setEmail(event.target.value);
	// 	  };
		
	// 	  const handlePasswordChange = (event) => {
	// 		setPassword(event.target.value);
	// 	  };
		
	// 	  const handleSubmit = (event) => {
	// 		event.preventDefault();
	// 		const loginRequest = { email, password };
	// 		login(loginRequest)
	// 		  .then(response => {
	// 			localStorage.setItem(ACCESS_TOKEN, response.accessToken);
	// 			localStorage.setItem(REFRESH_TOKEN, response.refreshToken);
	// 			// Alert.success("로그인에 성공하였습니다.");
	// 			props.history.push("/");
	// 		  })
	// 		  .catch(error => {
	// 			// Alert.error((error && error.message) || '로그인에 실패하였습니다.');
	// 		  });
	// 	  };
		  
	// 	  return (
	// 		<form onSubmit={handleSubmit}>
	// 		  <div className="form-item">
	// 			<input 
	// 			  type="email" 
	// 			  name="email" 
	// 			  className="form-control" 
	// 			  placeholder="Email"
	// 			  value={email} 
	// 			  onChange={handleEmailChange} 
	// 			  required
	// 			/>
	// 		  </div>
	// 		  <div className="form-item">
	// 			<input 
	// 			  type="password" 
	// 			  name="password" 
	// 			  className="form-control" 
	// 			  placeholder="Password"
	// 			  value={password} 
	// 			  onChange={handlePasswordChange} 
	// 			  required
	// 			/>
	// 		  </div>
	// 		  <div className="form-item">
	// 			<button type="submit" className="btn btn-block btn-primary">Login</button>
	// 		  </div>
	// 		</form>
	// 	  );
	// 	}
	  
	// 	export default Login