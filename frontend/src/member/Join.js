/* 회원가입 컴포넌트 */

import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router";

function Join() {

	// const [id, setId] = useState("");
	const [nickname, setNickname] = useState(""); //name
	const [password, setPassword] = useState("");
	// const [checkpassword, setCheckpassword] = useState("");
	const [email, setEmail] = useState("");

	const navigate = useNavigate();

	// const changeId = (event) => {
	// 	setId(event.target.value);
	// }

	const changeNickname = (event) => {
		setNickname(event.target.value);
	}

	const changePassword = (event) => {
		setPassword(event.target.value);
	}

	// const changeCheckpassword = (event) => {
	// 	setCheckpassword(event.target.value);
	// }

	const changeEmail = (event) => {
		setEmail(event.target.value);
	}

	/* 아이디 중복 체크 */
	const checkIdDuplicate = async () => {

		await axios.get("http://localhost:8080/auth/signup", { params: { email: email } }) //user
			.then((resp) => {
				console.log("[Join.js] checkIdDuplicate() success :D");
				console.log(resp.data);

				if (resp.status == 200) {
					alert("사용 가능한 아이디입니다.");
				}
				
			})
			.catch((err) => {
				console.log("[Join.js] checkIdDuplicate() error :<");
				console.log(err);

				const resp = err.response;
				if (resp.status == 400) {
					alert(resp.data);
				}
			});

	}

	/* 회원가입 */
	const join = async () => {

		const req = {
			// id: id,
			email: email,
			password: password,
			nickname: nickname,
			// checkpassword: checkpassword,
		}

		await axios.post("http://localhost:8080/auth/signup", req) // user/join
			.then((resp) => {
				console.log("[Join.js] join() success :D");
				console.log(resp.data);

				alert(resp.data.email + "님 회원가입을 축하드립니다 🎊");
				navigate("http://localost:3000/auth/login");

			}).catch((err) => {
				console.log("[Join.js] join() error :<");
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
			<table className="table">
				<tbody>
					<tr>
						<th className="col-2">아이디</th>
						<td>
							<input type="text" value={email} onChange={changeEmail} size="50px" /> &nbsp; &nbsp;
							<button className="btn btn-outline-danger" onClick={checkIdDuplicate}><i className="fas fa-check"></i> 아이디 중복 확인</button>
						</td>
					</tr>

					<tr>
						<th>이름</th>
						<td>
							<input type="text" value={nickname} onChange={changeNickname} size="50px" />
						</td>
					</tr>

					<tr>
						<th>비밀번호</th>
						<td>
							<input type="password" value={password} onChange={changePassword} size="50px" />
						</td>
					</tr>

					{/* <tr>
						<th>비밀번호 확인</th>
						<td>
							<input type="password" value={checkpassword} onChange={changeCheckpassword} size="50px" />
						</td>
					</tr> */}

					{/* <tr>
						<th>이메일</th>
						<td>
							<input type="text" value={email} onChange={changeEmail} size="100px" />
						</td>
					</tr> */}
				</tbody>
			</table><br />

			<div className="my-3 d-flex justify-content-center">
				<button className="btn btn-outline-secondary" onClick={join}><i className="fas fa-user-plus"></i> 회원가입</button>
			</div>

		</div>
	);
}

export default Join;