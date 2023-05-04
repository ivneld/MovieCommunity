import { BrowserRouter } from "react-router-dom";

import Header from "./common/Header"
import Nav from "./common/Nav"
// import Main from "./common/Main"
import Footer from "./common/Footer"
import AuthProvider from "./context/AuthProvider"
import HttpHeadersProvider from "./context/HttpHeadersProvider"
import "./style.css"

import { useState, useEffect } from 'react';
import LoadingIndicator from './utils/LoadingIndicator';
import OAuth2RedirectHandler from "./utils/OAuth2RedirectHandler2";
// import Alert from 'react-s-alert';
import { ACCESS_TOKEN, REFRESH_TOKEN } from './context/index';
import { getCurrentUser } from './utils/APIUtils2';

import loadable from '@loadable/component';
import { Routes, Route } from 'react-router-dom';

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
  
  useEffect(() => {
    loadCurrentlyLoggedInUser();
  }, []);

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
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
    setAuthenticated(false);
    setCurrentUser(null);
    // Alert.success("로그아웃 했습니다.");
  };

  if (loading) {
    return <LoadingIndicator />;
  }

  return (
    <div>
      <BrowserRouter>
        
        <Header />
        
        <AuthProvider>
          <HttpHeadersProvider>
            <Nav authenticated={authenticated} onLogout={handleLogout}/>
            <Routes>
                <Route path="/" element={<Main/>}/>
                <Route path="/boards" element={<Board/>}/>
                <Route path="/boards/:id/comment" element={<BoardDetail/>}/>
                <Route path="/boards/create/:movieid" element={<PostingBoard/>}/>
                <Route path="/movienm/:title" element={<Main/>}/>
                <Route path="/auth/login" element={<Login authenticated={authenticated}/>}/>
                <Route path="/auth/signup" element={<SignUp authenticated={authenticated}/>}/>
                <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler/>}/>
                <Route path="/logout" element={<Logout />}/>
                <Route path="/mypage" element={<MyPage />}/>
            </Routes>
            {/* <Alert
              stack={{ limit: 3 }}
              timeout={3000}
              position="top-right"
              effect="slide"
              offset={65}
            /> */}
          </HttpHeadersProvider>
        </AuthProvider>

        <Footer />

      </BrowserRouter>
    </div>
  );
}

export default App;
