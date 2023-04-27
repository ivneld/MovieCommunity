import loadable from '@loadable/component';
import { Routes, Route } from 'react-router-dom';

const Board = loadable(() => import('../pages/Board'));
const Main = loadable(() => import('../pages/Main'));
const Login = loadable(() => import('../pages/Login'));
const Logout = loadable(() => import('../pages/Logout'));

const SignUp = loadable(() => import('../pages/SignUp'));
const BoardDetail = loadable(() => import('../pages/BoardDetail'));
const PostingBoard = loadable(() => import('../pages/PostingBoard'));
const MyPage = loadable(() => import('../pages/MyPage'));

function Router({}) {
  return (
    <Routes>
        <Route path="/" element={<Main/>}/>
        <Route path="/boards" element={<Board/>}/>
        <Route path="/boards/:id/comment" element={<BoardDetail/>}/>
        <Route path="/boards/create/:movieid" element={<PostingBoard/>}/>
        <Route path="/movienm/:title" element={<Main/>}/>
        <Route path="/auth/login" element={<Login />}></Route>
        <Route path="/auth/signup" element={<SignUp />}></Route>
        <Route path="/logout" element={<Logout />}></Route>
        <Route path="/mypage" element={<MyPage />}></Route>
    </Routes>
  )
}

export default Router;