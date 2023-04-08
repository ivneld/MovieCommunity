import loadable from '@loadable/component';
import { Routes, Route } from 'react-router-dom';

// const Board = loadable(() => import('./pages/Board'));
// const Main = loadable(() => import('./pages/Main'));
// const BoardDetail = loadable(() => import('./pages/BoardDetail'));
// const PostingBoard = loadable(() => import('./pages/PostingBoard'));

// const Login = loadable(() => import('./pages/Login'));
// const SignUp = loadable(() => import('./pages/SignUp'));

import Home from "../common/Home"
import Join from "../member/Join"
import Login from "../member/Login"
import Logout from "../member/Logout"

function Router({}) {
  return (
    <Routes>
        {/* <Route path="/" element={<Main/>}/>
        <Route path="/boards" element={<Board/>}/>
        <Route path="/boards/:id/comment" element={<BoardDetail/>}/>
        <Route path="/movienm/:title" element={<Main/>}/>
        <Route path="/boards/create/:movieid" element={<PostingBoard/>}/> */}

        {/* <Route path="/auth/login" element={<Login/>}/>
        <Route path="/auth/signup" element={<SignUp/>}/> */}


        <Route path="/" element={<Home />}></Route>
        <Route path="/auth/login" element={<Login />}></Route>
        <Route path="/auth/signup" element={<Join />}></Route>
        <Route path="/logout" element={<Logout />}></Route>
    </Routes>
  )
}

export default Router;