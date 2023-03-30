import React from 'react';
import { Link } from 'react-router-dom';
import { LinkContainer } from './styes';
const Main = () => {
    return(
        <>
            <div>
                <span>My Page</span>
                <button>로그인</button>
            </div>
            <div>장르별</div>
            <LinkContainer>
                <Link to='/boards'>게시판</Link>
            </LinkContainer>
            <div>
                top 10 movie list (영화제목 click 시, 게시판 글 작성)
            </div>
        </>
    )
};

export default Main;