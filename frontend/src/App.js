import React from "react";
import loadable from '@loadable/component';
import { Routes, Route } from 'react-router-dom';

const Board = loadable(() => import('./pages/Board'));
const Main = loadable(() => import('./pages/Main'));
const Korea = loadable(() => import('./pages/Korea'));
const Foreign = loadable(() => import('./pages/Foreign'));
const BoardDetail = loadable(() => import('./pages/BoardDetail'));
// const MovieTitle = loadable(() => import('./pages/MovieTitle'));

function App({}) {
  return (
    <Routes>
      <Route path="/" element={<Main/>}/>
      <Route path="/boards" element={<Board/>}/>
      <Route path="/korea" element={<Korea/>}/>
      <Route path="/foreign" element={<Foreign/>}/>
      <Route path="/boards/:id/comment" element={<BoardDetail/>}/>
      <Route path="/movienm/:title" element={<Main/>}/>
    </Routes>
  )
}

export default App;