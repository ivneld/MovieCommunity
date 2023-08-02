import { useContext, useState } from "react";
import { AuthContext } from "../context/AuthProvider";
import { Link, useNavigate } from "react-router-dom";
import jwt_decode from "jwt-decode";
import { InputArea, Form } from "./styles";
import fetcher from "../utils/fetcher";
import fetcherAccessToken from "../utils/fetcherAccessToken";
import useSWR from 'swr';
import "./nav.css"

function Nav(props) {
	const navigate = useNavigate();
	// const { auth, setAuth } = useContext(AuthContext);
	const apiUrl = process.env.REACT_APP_API_URL;

	const accessToken = localStorage.getItem('accessToken');
	console.log("accessToken",accessToken)
	let memberId = null;
	if (accessToken) {
		try {
			const decodedToken = jwt_decode(accessToken);
			memberId = decodedToken?.sub;
		  } catch (error) {
			console.error('Invalid Token:', error);
		  }
	}
	const { data: currentUserData, error } = useSWR(
	  memberId ? `${apiUrl}/mypage/${memberId}/profile` : null,
	  fetcherAccessToken
	);

	// const { data: currentUserData,error} = useSWR(`${apiUrl}/auth/`, fetcherAccessToken)
	console.log('currentUserData',currentUserData)


	const handleLogout = () => {
		localStorage.removeItem("accessToken");
		localStorage.removeItem("refreshToken");
		localStorage.removeItem("name")
		// setAuth(null)
		console.log('로그아웃!')
	  };

	const [searchQuery, setSearchQuery] = useState("");
	const { data: searchResults } = useSWR(`${apiUrl}/movie/search?movieNm=${searchQuery}`, fetcher);

	// 검색목록 엔터 시 (영화이름 끝까지 안쳐도 가장 위에 있는 영화로 검색됨)
	const handleSearchSubmit = (e) => {
		e.preventDefault();
		if (searchQuery && searchResults && searchResults.length > 0) {
			// navigate(`/movie/${searchResults[0].id}`, { state: { detail: searchResults[0].id } });
			navigate('/movie/search/detail', {state: {search : searchQuery}})
		}
		setSearchQuery("");
	};

	// 검색목록 클릭 시
	const clickSearchSubmit = () => {
		if (searchQuery && searchResults && searchResults.length > 0) {
			// navigate(`/movie/${id}`, { state: { detail: id } });
			navigate('/movie/search/detail', {state: {search: searchQuery}})
		}
		setSearchQuery("");
	};

	return (
		<nav className="navbar navbar-expand-md navbar-light sticky-top font-weight-bold" style={{ backgroundColor: 'rgb(145, 205, 220)'}}>
			<div className="container">
				<div className="navbar-collapse collapse justify-content-between" id="navbar-content">
					<ul className="navbar-nav mr-auto" style={{minWidth:"55%"}}>
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

						{/* OTT  */}
						<li className="nav-item">
							<Link className="nav-link" to="/ott"><i className="fas"></i>OTT</Link>
						</li>
	
						{/* 커뮤니티  */}
						<li className="nav-item">
							<Link className="nav-link" to="/community"><i className="fas"></i>커뮤니티</Link>
						</li>
						
						{/* 마이페이지  */}
						<li className="nav-item">
							<Link className="nav-link" to="/mypage"><i className="fas"></i>마이페이지</Link>
						</li>
					</ul>

					<div style={{display:"flex", alignItems:"center"}}>
						{/* <Form onSubmit={handleSubmit}> */}
						<Form className="search-container" onSubmit={handleSearchSubmit} style={{}}>
							<InputArea
								value={searchQuery}
								onChange={(e) => setSearchQuery(e.target.value)}
								placeholder="원하는 영화를 발견해보세요"
							/>
							  {searchResults && searchResults.length > 0 && searchQuery && (
									<ul className="autocomplete-results">
									{searchResults.map((result) => (
										<li key={result.id} onClick={() => (clickSearchSubmit())}>
											<div style={{display:"flex"}}>
												<div>
													<div>{result.movieNm}</div>
													<div>{result.openDt}</div>
												</div>
												<img src={result.posterPath} style={{marginLeft:"auto", marginRight:"10px"}} height="50px" alt='포스터주소'/>
											</div>
										</li>
									))}
									</ul>
								)}
						</Form>
					</div>

					<ul className="navbar-nav ml-auto" style={{minWidth:"60%"}}>

						{							
							(currentUserData || props.authenticated) ?
								<>
									{/* 회원 정보 */}
									<li className="nav-item">
										<span className="nav-link"> {currentUserData?.nickName} 님 반갑습니다 <i className="fab fa-ello"></i> &nbsp; </span>
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