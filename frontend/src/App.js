import { BrowserRouter } from "react-router-dom";

import Header from "./common/Header"
import Nav from "./common/Nav"
// import Main from "./common/Main"
import Footer from "./common/Footer"
import AuthProvider from "./context/AuthProvider"
import HttpHeadersProvider from "./context/HttpHeadersProvider"
import "./style.css"

import { useState, useEffect, useContext } from 'react';
import LoadingIndicator from './utils/LoadingIndicator';
import OAuth2RedirectHandler from "./utils/OAuth2RedirectHandler2";
// import Alert from 'react-s-alert';
import { ACCESS_TOKEN, REFRESH_TOKEN } from './context/index';
import { getCurrentUser } from './utils/APIUtils2';

import loadable from '@loadable/component';
import { Routes, Route } from 'react-router-dom';

import { AuthContext } from "./context/AuthProvider";
import { HttpHeadersContext } from "./context/HttpHeadersProvider";

import axios from "axios";
import jwt_decode from "jwt-decode";
import { useNavigate } from "react-router-dom";

function App() {
  const [authenticated, setAuthenticated] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const Board = loadable(() => import('./pages/Board'));
  const Main = loadable(() => import('./pages/Main'));
  const Login = loadable(() => import('./pages/Login'));
  const Logout = loadable(() => import('./pages/Logout'));
  const SignUp = loadable(() => import('./pages/SignUp'));
  const BoardDetail = loadable(() => import('./pages/BoardDetail'));
  const PostingBoard = loadable(() => import('./pages/PostingBoard'));
  const MyPage = loadable(() => import('./pages/MyPage'));
  const Ranking = loadable(() => import('./pages/Ranking'));
  const UpcomingMovies = loadable(() => import('./pages/UpcomingMovies'));
  const Detail = loadable(() => import('./pages/Detail'));
  
  const loadCurrentlyLoggedInUser = () => {
    getCurrentUser()
      .then(response => {
        setCurrentUser(response);
        setAuthenticated(true);
        setLoading(false);
      })
      .catch(error => {
        setLoading(false);
      });
  };

  const handleLogout = () => {
    // localStorage.removeItem(ACCESS_TOKEN);
    // localStorage.removeItem(REFRESH_TOKEN);
    setAuthenticated(false);
    setCurrentUser(null);
    // Alert.success("로그아웃 했습니다.");
  };

  useEffect(() => {
    loadCurrentlyLoggedInUser();
  }, []);

  if (loading) {
    return <LoadingIndicator />;
  }

  return (
    <div>
      <BrowserRouter>
        
        <Header />
        
        <AuthProvider>
          <HttpHeadersProvider>
            <RefreshToken/>
            <Nav authenticated={authenticated} onLogout={handleLogout}/>
            <Routes>
                <Route path="/" element={<Main/>}/>
                {/* <Route path="/boards" element={<Board/>}/>
                <Route path="/boards/:id/comment" element={<BoardDetail/>}/>
                <Route path="/boards/create/:movieid" element={<PostingBoard/>}/> */}
                {/* <Route path="/movienm/:title" element={<Main/>}/> */}
                <Route path="/auth/login" element={<Login authenticated={authenticated}/>}/>
                <Route path="/auth/signup" element={<SignUp authenticated={authenticated}/>}/>
                <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler/>}/>
                <Route path="/logout" element={<Logout />}/>
                <Route path="/mypage" element={<MyPage />}/>
                <Route path="/ranking" element={<Ranking />}/>
                <Route path="/upcomingmovies" element={<UpcomingMovies />}/>
                <Route path="/detail" element={<Detail />}/>
            </Routes>
          </HttpHeadersProvider>
        </AuthProvider>

        <Footer />

      </BrowserRouter>
    </div>
  );
}

function RefreshToken() {
  const { auth, setAuth } = useContext(AuthContext) || {};
  const { headers, setHeaders } = useContext(HttpHeadersContext) || {};
  const navigate = useNavigate();

  const handleRefreshToken = async () => {
	  const refreshToken = localStorage.getItem("bbs_refresh_token");
	  try {
		  const response = await axios.post("http://localhost:8080/auth/refresh", {refreshToken: refreshToken});
      const { accessToken } = response.data;
      console.log("토큰이 갱신되었습니다");
      localStorage.setItem("bbs_access_token", accessToken);
      setHeaders({ "Authorization": `Bearer ${accessToken}` });
    } catch (error) {
      console.log("[Login.js] handleRefreshToken() error :<");
      console.log(error);
      console.log("토큰 갱신 실패");

      localStorage.removeItem("bbs_access_token");
      localStorage.removeItem("bbs_refresh_token");
      localStorage.removeItem("name");
      alert('refresh 토큰 만료로 로그아웃 됩니다')
      navigate('/logout');
	  }
	}

	useEffect(() => {
	  const accessToken = localStorage.getItem("bbs_access_token");
	  if (accessToken) { // Check if the access token is expired
      const decodedToken = jwt_decode(accessToken);
      const interval = setInterval(() => {
        const currentTime = Math.floor(Date.now() / 1000);
        const timeRemaining = decodedToken.exp - currentTime;
        if (timeRemaining > 0){
          console.log('토큰 만료까지 남은시간:', timeRemaining, '초');
        }
        if (timeRemaining <= 0) {
          clearInterval(interval)
          console.log("토큰이 만료되었습니다");
          handleRefreshToken();
          return
        }
      }, 1000);

      localStorage.setItem("name", decodedToken.aud);
      if (auth){
        setAuth(decodedToken.aud);
      }
      // console.log("닉네임:", decodedToken.aud);
	  }
	}, [handleRefreshToken]);
}
export default App;
