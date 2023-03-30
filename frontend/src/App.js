import React from "react";
import loadable from '@loadable/component';
import { Routes, Route } from 'react-router-dom';

const Board = loadable(() => import('./pages/Board'));
const Main = loadable(() => import('./pages/Main'));

function App({}) {
  return (
    <Routes>
      <Route path="/" element={<Main/>}/>
      <Route path="/boards" element={<Board/>}/>
    </Routes>
  )
}

export default App;