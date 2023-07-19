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

  const Main = loadable(() => import('./pages/Main'));
  const Login = loadable(() => import('./pages/Login'));
  const Logout = loadable(() => import('./pages/Logout'));
  const SignUp = loadable(() => import('./pages/SignUp'));
  const MyPage = loadable(() => import('./pages/MyPage'));
  const Ranking = loadable(() => import('./pages/Ranking'));
  const UpcomingMovies = loadable(() => import('./pages/UpcomingMovies'));
  const Detail = loadable(() => import('./pages/Detail'));
  const Genre = loadable(() => import('./pages/Genre'));
  const GenreMovies = loadable(() => import('./pages/GenreMovies'));
  const SearchResult = loadable(() => import('./pages/SearchResult'));
  
  const Ott = loadable(() => import('./pages/Ott'));

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

  // const handleLogout = () => {
  //   localStorage.removeItem("accessToken");
  //   localStorage.removeItem("refreshToken");
  //   setAuthenticated(false);
  //   setCurrentUser(null);
  //   // Alert.success("로그아웃 했습니다.");
  // };

  useEffect(() => {
    loadCurrentlyLoggedInUser();
  }, []);

  if (loading) {
    return <LoadingIndicator />;
  }
  console.log(localStorage)
  return (
    <div>
      <BrowserRouter>
        
        <Header />
        
        <AuthProvider>
          <HttpHeadersProvider>
            <RefreshToken/>
            <Nav/>
            <Routes>
                <Route path="/" element={<Main/>}/>
                <Route path="/auth/login" element={<Login authenticated={authenticated}/>}/>
                <Route path="/auth/signup" element={<SignUp authenticated={authenticated}/>}/>
                {/* <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler/>}/> */}
                <Route path="/logout" element={<Logout />}/>
                <Route path="/mypage" element={<MyPage />}/>
                <Route path="/ranking" element={<Ranking />}/>
                <Route path="/upcomingmovies" element={<UpcomingMovies />}/>
                <Route path="/movie/:id" element={<Detail />}/>
                <Route path="/genre" element={<Genre />}/>
                <Route path="/genre/:id" element={<GenreMovies />}/>
                <Route path="/movie/search/detail" element={<SearchResult />}/>
                <Route path="/ott" element={<Ott />}/>
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
  const apiUrl = process.env.REACT_APP_API_URL;
  const handleRefreshToken = async () => {
	  const refreshToken = localStorage.getItem("refreshToken");
	  try {
		  const response = await axios.post(`${apiUrl}/auth/refresh`, {refreshToken: refreshToken});
      const { accessToken } = response.data;
      console.log("토큰이 갱신되었습니다");
      localStorage.setItem("accessToken", accessToken);
      setHeaders({ "Authorization": `Bearer ${accessToken}` });
    } catch (error) {
      console.log("[Login.js] handleRefreshToken() error :<");
      console.log(error);
      console.log("토큰 갱신 실패");

      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("name");
      alert('refresh 토큰 만료로 로그아웃 됩니다')
      navigate('/logout');
	  }
	}

	useEffect(() => {
	  const accessToken = localStorage.getItem("accessToken");
	  if (accessToken) { // Check if the access token is expired
      let decodedToken;
      try {
        decodedToken = jwt_decode(accessToken);
      } catch (error) {
        console.log("토큰 디코드 오류:", error);
        return;
      }
      const interval = setInterval(() => {
        const currentTime = Math.floor(Date.now() / 1000);
        const timeRemaining = decodedToken.exp - currentTime;
        if (timeRemaining > 0){
          // console.log('토큰 만료까지 남은시간:', timeRemaining, '초');
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
