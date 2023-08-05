import React, { useCallback, useState, useContext, useRef } from 'react';
import useSWR, { mutate } from 'swr';
import fetcher from '../../utils/fetcher';

import { Link } from 'react-router-dom';

const Community = () => {
    const apiUrl = process.env.REACT_APP_API_URL;
    
    const [currentPage, setCurrentPage] = useState({ // 객체 상태관리 (효율적)
        newest: 0, // 최신순
        viewOrder: 0, // 조회순
        likeOrder: 0, // 좋아요순
    });

    const { data: postNewestData, error1 } = useSWR(`${apiUrl}/posts/new?page=${currentPage.newest}&size=${5}`, fetcher); // 게시글 최신순
    console.log("postNewestData",postNewestData)
    const { data: postViewOrderData, error2 } = useSWR(`${apiUrl}/posts/view?page=${currentPage.viewOrder}&size=${5}`, fetcher); // 게시글 조회순
    console.log("postViewOrderData",postViewOrderData)
    const { data: postLikeOrderData, error3 } = useSWR(`${apiUrl}/posts/like?page=${currentPage.likeOrder}&size=${5}`, fetcher); // 게시글 좋아요순
    console.log("postLikeOrderData",postLikeOrderData)

    const handlePageChange = (page, category) => {
        setCurrentPage((prevPages) => ({
          ...prevPages, // 예를 들어, 최신순 페이지 넘겼을 경우, 조회순이랑 좋아요순이 그대로 있어야 하기 떄문! ...prevPages 없으면 최신순 페이지 넘기면 조회순, 좋아요순 사라짐!
          [category]: page,
        }));
      };

    return(
        <>
            <Link to="/communitypost" style={{cursor:"pointer", fontSize:"24px", fontWeight:"bold"}}>
                ✏️글쓰기
            </Link>
            {postNewestData && 
                <>
                    <div style={{fontSize:"40px", fontWeight:"bold"}}>최근 작성된 리뷰</div>
                    <div style={{display:"flex", justifyContent:"center"}}>
                        {postNewestData?.postsList?.map((obj,idx)=>{
                            return(
                                <div key={idx} style={{ margin:"0 15px 20px"}}>
                                    <div style={{display:"flex"}}>
                                        <div>{obj.view} views</div>
                                    </div>
                                    <Link to="/communitydetail" state={{postId : obj.id}}>
                                        <div style={{ position: 'relative', border:"2px solid black", width:"266.66px", height:"400px" }}>
                                            {/* <img src={'https://'+obj.galleries?.[0]?.filePath} width="100%" height="100%" alt="포스터주소"/> */}
                                            <img src={obj.moviePosterPath} width="100%" height="100%" alt="포스터주소"/>
                                            <div style={{ position: 'absolute', bottom:0, width:'100%', zIndex: 1, color: 'white', background:"rgba(0, 0, 0, 0.5", fontWeight: 'bold'}}>
                                                <div>제목 : {obj.title}</div>
                                                <div>내용 : {obj.content}</div>
                                                <div>좋아요 {obj.likeCount}</div>
                                                <div>댓글 {obj.commentsCount}</div>
                                            </div>
                                        </div>
                                    </Link>
                                </div>
                            )
                        })}
                    </div>
                    <div style={{display:"flex", justifyContent:"center"}}>
                        <button onClick={() => handlePageChange(currentPage.newest-1,'newest')}>&lt;</button>
                        <span>{parseInt(currentPage.newest+1)}페이지</span>
                        <button onClick={() => handlePageChange(currentPage.newest+1,'newest')}>&gt;</button>
                    </div>
                </>
            }
            {postViewOrderData &&
            <>
                <div style={{fontSize:"40px", fontWeight:"bold"}}>조회순</div>
                <div style={{display:"flex", justifyContent:"center"}}>
                {postViewOrderData?.postsList?.map((obj,idx)=>{
                            return(
                                <div key={idx} style={{ margin:"0 15px 20px"}}>
                                    <div style={{display:"flex"}}>
                                        <div>{obj.view} views</div>
                                    </div>
                                    <Link to="/communitydetail" state={{postId : obj.id}}>
                                        <div style={{ position: 'relative', border:"2px solid black", width:"266.66px", height:"400px" }}>
                                            {/* <img src={'https://'+obj.galleries?.[0]?.filePath} width="100%" height="100%" alt="포스터주소"/> */}
                                            <img src={obj.moviePosterPath} width="100%" height="100%" alt="포스터주소"/>
                                            <div style={{ position: 'absolute', bottom:0, width:'100%', zIndex: 1, color: 'white', background:"rgba(0, 0, 0, 0.5", fontWeight: 'bold'}}>
                                                <div>제목 : {obj.title}</div>
                                                <div>내용 : {obj.content}</div>
                                                <div>좋아요 {obj.likeCount}</div>
                                                <div>댓글 {obj.commentsCount}</div>
                                            </div>
                                        </div>
                                    </Link>
                                </div>
                            )
                        })}
                </div>
                <div style={{display:"flex", justifyContent:"center"}}>
                    <button onClick={() => handlePageChange(currentPage.viewOrder-1,'viewOrder')}>&lt;</button>
                    <span>{parseInt(currentPage.viewOrder+1)}페이지</span>
                    <button onClick={() => handlePageChange(currentPage.viewOrder+1,'viewOrder')}>&gt;</button>
                </div>
            </>
            }
            {postLikeOrderData &&
            <>
                <div style={{fontSize:"40px", fontWeight:"bold"}}>좋아요순</div>
                <div style={{display:"flex", justifyContent:"center"}}>
                {postLikeOrderData?.postsList?.map((obj,idx)=>{
                            return(
                                <div key={idx} style={{ margin:"0 15px 20px"}}>
                                    <div style={{display:"flex"}}>
                                        <div>{obj.view} views</div>
                                    </div>
                                    <Link to="/communitydetail" state={{postId : obj.id}}>
                                        <div style={{ position: 'relative', border:"2px solid black", width:"266.66px", height:"400px" }}>
                                            {/* <img src={'https://'+obj.galleries?.[0]?.filePath} width="100%" height="100%" alt="포스터주소"/> */}
                                            <img src={obj.moviePosterPath} width="100%" height="100%" alt="포스터주소"/>
                                            <div style={{ position: 'absolute', bottom:0, width:'100%', zIndex: 1, color: 'white', background:"rgba(0, 0, 0, 0.5", fontWeight: 'bold'}}>
                                                <div>제목 : {obj.title}</div>
                                                <div>내용 : {obj.content}</div>
                                                <div>좋아요 {obj.likeCount}</div>
                                                <div>댓글 {obj.commentsCount}</div>
                                            </div>
                                        </div>
                                    </Link>
                                </div>
                            )
                        })}
                </div>
                <div style={{display:"flex", justifyContent:"center", marginBottom:"30px"}}>
                    <button onClick={() => handlePageChange(currentPage.likeOrder-1,'likeOrder')}>&lt;</button>
                    <span>{parseInt(currentPage.likeOrder+1)}페이지</span>
                    <button onClick={() => handlePageChange(currentPage.likeOrder+1,'likeOrder')}>&gt;</button>
                </div>
            </>
            }
        </>
    )
};

export default Community;