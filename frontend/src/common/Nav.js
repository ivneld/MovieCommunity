import { useContext } from "react";
import { AuthContext } from "../context/AuthProvider";
import { Link } from "react-router-dom";
import jwt_decode from "jwt-decode";

function Nav(props) {

	const { auth, setAuth } = useContext(AuthContext);
	
	const handleLogout = () => {
		localStorage.removeItem("accessToken");
		localStorage.removeItem("refreshToken");
		localStorage.removeItem("name")
		setAuth(null)
		console.log('로그아웃!')
	  };

	return (
		<nav className="navbar navbar-expand-md navbar-dark bg-dark sticky-top">
			<div className="container">

				<div className="navbar-collapse collapse justify-content-between" id="navbar-content">
					<ul className="navbar-nav mr-auto">

						{/* 메인 화면 */}
						<li className="nav-item">
							<Link className="nav-link" to="/"><i className="fas fa-home"></i> SPRING movies</Link>
						</li>

						{/* 랭킹  */}
						<li className="nav-item">
							<Link className="nav-link" to="/ranking"><i className="fas"></i>랭킹</Link>
						</li>
						
						{/* 상영예정작  */}
						<li className="nav-item">
							<Link className="nav-link" to="/upcomingmovies"><i className="fas"></i>상영예정작</Link>
						</li>

						{/* 장르별  */}
						<li className="nav-item">
							<Link className="nav-link" to="/genre"><i className="fas"></i>장르별</Link>
						</li>

						{/* 게시판  */}
						<li className="nav-item">
							<Link className="nav-link" to="/boards"><i className="fas"></i>OTT</Link>
						</li>
						
						{/* 마이페이지  */}
						<li className="nav-item">
							<Link className="nav-link" to="/mypage"><i className="fas"></i>마이페이지</Link>
						</li>
					</ul>
					<ul className="navbar-nav ml-auto">

						{							
							(auth || props.authenticated) ?
								<>
									{/* 회원 정보 */}
									<li className="nav-item">
										<span className="nav-link"> {auth} 님 반갑습니다 <i className="fab fa-ello"></i> &nbsp; </span>
									</li>

									{/* 로그아웃 */}
									<li className="nav-item">
										<Link className="nav-link" to="/logout" onClick={handleLogout}><i className="fas fa-sign-out-alt"></i> 로그아웃</Link>
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