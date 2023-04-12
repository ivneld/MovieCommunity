import { useContext } from "react";
import { AuthContext } from "../context/AuthProvider";
import { Link } from "react-router-dom";

function Nav() {

	const { auth, setAuth } = useContext(AuthContext);

	return (
		<nav className="navbar navbar-expand-md navbar-dark bg-dark sticky-top">
			<div className="container">

				<div className="navbar-collapse collapse justify-content-between" id="navbar-content">
					<ul className="navbar-nav mr-auto">

						{/* 메인 화면 */}
						<li className="nav-item">
							<Link className="nav-link" to="/"><i className="fas fa-home"></i> Home</Link>
						</li>

						{/* 장르별 */}
						<li className="nav-item dropdown">

							<div className="nav-link dropdown-toggle" id="navbarDropdown"
								role="button" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">장르별</div>

							<div className="dropdown-menu" aria-labelledby="navbarDropdown">
								<Link className="dropdown-item" to="/bbslist">로맨스</Link>
								<Link className="dropdown-item" to="/bbswrite">액션</Link>
								<Link className="dropdown-item" to="/bbswrite">틀만 구현한거임</Link>
							</div>
						</li>

						{/* 게시판  */}
						<li className="nav-item">
							<Link className="nav-link" to="/boards"><i className="fas"></i>게시판</Link>
						</li>
						
						{/* 마이페이지  */}
						<li className="nav-item">
							<Link className="nav-link" to="/mypage"><i className="fas"></i>마이페이지</Link>
						</li>
					</ul>
					<ul className="navbar-nav ml-auto">

						{							
							(auth) ?
								<>
									{/* 회원 정보 */}
									<li className="nav-item">
										<span className="nav-link"> {auth} 님 반갑습니다 <i className="fab fa-ello"></i> &nbsp; </span>
									</li>

									{/* 로그아웃 */}
									<li className="nav-item">
										<Link className="nav-link" to="/logout"><i className="fas fa-sign-out-alt"></i> 로그아웃</Link>
									</li>

								</>
								:
								<>
									{/* 로그인 */}
									<li className="nav-item">
										<Link className="nav-link" to="/auth/login">로그인</Link>
									</li>

									{/* 회원가입 */}
									<li className="nav-item">
										<Link className="nav-link" to="/auth/signup">회원가입</Link>
									</li>
								</>
						}

					</ul>
				</div>
			</div>
		</nav>
	);
}

export default Nav;