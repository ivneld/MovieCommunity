import React from "react";
import loadable from '@loadable/component';
import { Routes, Route } from 'react-router-dom';
import Navbar from "./components/NavBar";
import Home from './pages/Home';
import Login from './pages/Login';
import Profile from './pages/Profile';
import SignUp from './pages/SignUp';

// const Board = loadable(() => import('./pages/Board'));
// const Main = loadable(() => import('./pages/Main'));
// const BoardDetail = loadable(() => import('./pages/BoardDetail'));
// const PostingBoard = loadable(() => import('./pages/PostingBoard'));

// const Login = loadable(() => import('./pages/Login'));
// const SignUp = loadable(() => import('./pages/SignUp'));

function App({}) {
  return (
    <>
      <Navbar/>
      <Routes>
        {/* <Route path="/" element={<Main/>}/>
        <Route path="/boards" element={<Board/>}/>
        <Route path="/boards/:id/comment" element={<BoardDetail/>}/>
        <Route path="/movienm/:title" element={<Main/>}/>
        <Route path="/boards/create/:movieid" element={<PostingBoard/>}/> */}

        {/* <Route path="/auth/login" element={<Login/>}/>
        <Route path="/auth/signup" element={<SignUp/>}/> */}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
    </>
  )
}

export default App;