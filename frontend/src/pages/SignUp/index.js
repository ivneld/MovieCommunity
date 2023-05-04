import React, { useState } from 'react';
import './Signup.css';
import { Link, useNavigate } from 'react-router-dom'
import { NAVER_AUTH_URL ,KAKAO_AUTH_URL, GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL, GITHUB_AUTH_URL } from '../../context/index';
import { signup } from '../../utils/APIUtils2';
import fbLogo from '../../img/fb-logo.png';
import googleLogo from '../../img/google-logo.png';
import githubLogo from '../../img/github-logo.png';
import kakaoLogo from '../../img/kakao-logo.png';
import naverLogo from '../../img/naver-logo.png';

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
          <h1 className="signup-title">Sign Up</h1>
          <SocialSignup />
          <div className="or-separator">
            <span className="or-text">OR</span>
          </div>
          <SignupForm
            name={name}
            handleInputChange={handleInputChange}
            handleSubmit={handleSubmit}
          />
          <span className="login-link">
            Already have an account? <Link to="/auth/login">Login!</Link>
          </span>
        </div>
      </div>
    );
  }
  
  function SocialSignup() {
    return (
      <div className="social-signup">
        <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
          <img src={googleLogo} alt="Google" /> Sign up with Google
        </a>
        <a className="btn btn-block social-btn facebook" href={FACEBOOK_AUTH_URL}>
            <img src={fbLogo} alt="Facebook" /> Sign up with Facebook
        </a>
        <a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
            <img src={githubLogo} alt="Github" /> Sign up with Github
        </a>
        <a className="btn btn-block social-btn kakao" href={KAKAO_AUTH_URL}>
            <img src={kakaoLogo} alt="Kakao" /> Sign up with Kakao
        </a>
        <a className="btn btn-block social-btn kakao" href={NAVER_AUTH_URL}>
            <img src={naverLogo} alt="Naver" /> Sign up with Naver
        </a>
      </div>
    );
  }
  
  function SignupForm(props) {
    return (
      <form onSubmit={props.handleSubmit}>
        <div className="form-item">
          <input
            type="text"
            name="name"
            className="form-control"
            placeholder="Name"
            value={props.name}
            onChange={props.handleInputChange}
            required
          />
        </div>
        <div className="form-item">
          <input
            type="email"
            name="email"
            className="form-control"
            placeholder="Email"
            value={props.email}
            onChange={props.handleInputChange}
            required
          />
        </div>
        <div className="form-item">
          <input
            type="password"
            name="password"
            className="form-control"
            placeholder="Password"
            value={props.password}
            onChange={props.handleInputChange}
            required
          />
        </div>
        <div className="form-item">
          <button type="submit" className="btn btn-block btn-primary">
            Sign Up
          </button>
        </div>
      </form>
    );
  }
  
  export default Signup;

// /* 회원가입 컴포넌트 */

// import axios from "axios";
// import { useState, useEffect } from "react";
// import { useNavigate } from "react-router";
// import { Success, Error } from './styles';

// function SignUp() {

// 	// const [id, setId] = useState("");
// 	const [nickname, setNickname] = useState(""); //name
// 	const [password, setPassword] = useState("");
// 	const [checkpassword, setCheckpassword] = useState("");

// 	const [email, setEmail] = useState("");
// 	const [isDuplicate, setIsDuplicate] = useState(true);


// 	const navigate = useNavigate();

// 	// const changeId = (event) => {
// 	// 	setId(event.target.value);
// 	// }

// 	const changeNickname = (event) => {
// 		setNickname(event.target.value);
// 	}

// 	const changePassword = (event) => {
// 		setPassword(event.target.value);
// 	}

// 	const changeCheckpassword = (event) => {
// 		setCheckpassword(event.target.value);
// 	}

// 	const changeEmail = (event) => {
// 		setEmail(event.target.value);
// 		setIsDuplicate(true);
// 	}

// 	// /* 아이디 중복 체크 */
// 	// const checkIdDuplicate = async () => {

// 	// 	await axios.get("http://localhost:8080/auth/checkidduplicate", { params: { email: email } }) //user
// 	// 		.then((resp) => {
// 	// 			console.log("[SignUp.js] checkIdDuplicate() success :D");
// 	// 			console.log(resp.data);

// 	// 			if (resp.status == 200) {
// 	// 				alert("사용 가능한 아이디입니다.");
// 	// 			}
				
// 	// 		})
// 	// 		.catch((err) => {
// 	// 			console.log("[SignUp.js] checkIdDuplicate() error :<");
// 	// 			console.log(err);

// 	// 			const resp = err.response;
// 	// 			if (resp.status == 400) {
// 	// 				alert(resp.data);
// 	// 			}
// 	// 		});
// 	// }

// 	/* 아이디 중복 체크 */
// 	const checkIdDuplicate = async () => {
//         const req = {'email': email}
//         const config = {"Content-Type": 'application/json'};
// 		await axios.post("http://localhost:8080/auth/checkIdDuplicate", req, config) //user
// 			.then((resp) => {
// 				console.log("[SignUp.js] checkIdDuplicate() success :D");
// 				console.log(resp.data);

// 				if (resp.status == 200) {
// 					alert("사용 가능한 아이디입니다.");
// 					setIsDuplicate(false);					
// 				}
				
// 			})
// 			.catch((err) => {
// 				console.log("[SignUp.js] checkIdDuplicate() error :<");
// 				console.log(err);

// 				const resp = err.response;
// 				if (resp.status == 400) {
// 					alert("사용할 수 없는 아이디입니다.");
// 					setIsDuplicate(true);
// 				}
// 			});
// 	}

// 	/* 회원가입 */
// 	const signup = async () => {		
// 		if (!email){
// 			alert("아이디를 입력해주세요")
// 			return;
// 		}
// 		if (isDuplicate){
// 			alert("아이디 중복 확인이 필요합니다");		
// 			return;
// 		}
// 		else if (!nickname){
// 			alert("이름을 입력해주세요");
// 			return;
// 		}
// 		else if (!password || !checkpassword){
// 			alert("비밀번호를 입력해주세요")
// 			return;
// 		}
// 		else if (password && checkpassword && password !== checkpassword){
// 			alert("비밀번호가 서로 일치하지 않습니다")
// 			return;
// 		}

// 		const req = {
// 			// id: id,
// 			'email': email,
// 			'password': password,
// 			'nickname': nickname,
// 			// checkpassword: checkpassword,
// 		}
// 		const config = {"Content-Type": 'application/json'};
		
// 		await axios.post("http://localhost:8080/auth/signup", req, config) // user/signup
// 			.then((resp) => {
// 				console.log("[SignUp.js] signup() success :D");
// 				console.log(resp.data);

// 				alert(resp.data.email + "님 회원가입을 축하드립니다 🎊");
// 				navigate("/auth/login");

// 			}).catch((err) => {
// 				console.log("[SignUp.js] signup() error :<");
// 				console.log(err.response.data);

// 				// alert(err.response.data);

// 				const resp = err.response;
// 				if (resp.status == 400) {
// 					alert(resp.data);
// 				}
// 			});		
// 	}


// 	return (
// 		<div>
// 			<table className="table">
// 				<tbody>
// 					<tr>
// 						<th className="col-2">아이디</th>
// 						<td>
// 							<input type="text" value={email} onChange={changeEmail} size="50px" /> &nbsp; &nbsp;
// 							<button className="btn btn-outline-danger" onClick={checkIdDuplicate}><i className="fas fa-check"></i> 아이디 중복 확인</button>
// 						</td>
// 					</tr>

// 					<tr>
// 						<th>이름</th>
// 						<td>
// 							<input type="text" value={nickname} onChange={changeNickname} size="50px" />
// 						</td>
// 					</tr>

// 					<tr>
// 						<th>비밀번호</th>
// 						<td>
// 							<input type="password" value={password} onChange={changePassword} size="50px" />
// 						</td>
// 					</tr>

// 					<tr>
// 						<th>비밀번호 확인</th>
// 						<td>
// 							<input type="password" value={checkpassword} onChange={changeCheckpassword} size="50px" />
// 						</td>
// 						{password && checkpassword && password !== checkpassword &&
// 						<Error>비밀번호가 일치하지 않습니다!</Error>
// 						}
// 						{password && checkpassword && password === checkpassword &&
// 						<Success>비밀번호가 일치합니다!</Success>
// 						}
// 					</tr>

// 					{/* <tr>
// 						<th>이메일</th>
// 						<td>
// 							<input type="text" value={email} onChange={changeEmail} size="100px" />
// 						</td>
// 					</tr> */}
// 				</tbody>
// 			</table><br />

// 			<div className="my-3 d-flex justify-content-center">
// 				<button className="btn btn-outline-secondary" onClick={signup}><i className="fas fa-user-plus"></i> 회원가입</button>
// 			</div>

// 		</div>
// 	);
// }

// export default SignUp;