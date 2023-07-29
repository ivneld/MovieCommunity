import React, { useCallback, useState, useContext, useRef } from 'react';
import useSWR, { mutate } from 'swr';
import fetcher from '../../utils/fetcher';
import { Link } from 'react-router-dom';

const Community = () => {
    const apiUrl = process.env.REACT_APP_API_URL;

    const { data: postNewestData, error1 } = useSWR(`${apiUrl}/posts/new`, fetcher); // 게시글 최신순
    console.log("postNewestData",postNewestData)
    const { data: postViewOrderData, error2 } = useSWR(`${apiUrl}/posts/view`, fetcher); // 게시글 조회순
    console.log("postViewOrderData",postViewOrderData)
    const { data: postLikeOrderData, error3 } = useSWR(`${apiUrl}/posts/like`, fetcher); // 게시글 좋아요순
    console.log("postLikeOrderData",postLikeOrderData)

    return(
        <>
            <Link to="/communitypost" style={{cursor:"pointer", fontSize:"24px", fontWeight:"bold"}}>
                ✏️글쓰기
            </Link>
            {postNewestData && 
                <>
                    <div style={{fontSize:"40px", fontWeight:"bold"}}>최근 작성된 리뷰</div>
                    <div style={{display:"flex"}}>
                        {postNewestData?.postsList?.content?.map((obj,idx)=>{
                            return(
                                <div key={idx}>
                                    <div style={{display:"flex"}}>
                                        <div>{obj.view} views</div>
                                    </div>
                                    <Link to="/communitydetail" state={{postId : obj.id}}>
                                        <div style={{ position: 'relative', border:"2px solid black", width:"266.66px", height:"400px" }}>
                                            <img src={'https://'+obj.galleries?.[0]?.filePath} width="100%" height="100%" alt="포스터주소"/>
                                            <div style={{ position: 'absolute', bottom:0, width:'100%', zIndex: 1, color: 'white', background:"rgba(0, 0, 0, 0.5", fontWeight: 'bold'}}>
                                                <div>제목 : {obj.title}</div>
                                                <div>내용 : {obj.content}</div>
                                                <div>좋아요 {obj.likeCount}</div>
                                                <div>댓글 {obj.comments.length}</div>
                                            </div>
                                        </div>
                                    </Link>
                                </div>
                            )
                        })}
                    </div>
                </>
            }
            {postViewOrderData &&
            <>
                <div style={{fontSize:"40px", fontWeight:"bold"}}>조회순</div>
                <div style={{display:"flex"}}>
                {postViewOrderData?.postsList?.content?.map((obj,idx)=>{
                            return(
                                <div key={idx}>
                                    <div style={{display:"flex"}}>
                                        <div>{obj.view} views</div>
                                    </div>
                                    <Link to="/communitydetail" state={{postId : obj.id}}>
                                        <div style={{ position: 'relative', border:"2px solid black", width:"266.66px", height:"400px" }}>
                                            <img src={'https://'+obj.galleries?.[0]?.filePath} width="100%" height="100%" alt="포스터주소"/>
                                            <div style={{ position: 'absolute', bottom:0, width:'100%', zIndex: 1, color: 'white', background:"rgba(0, 0, 0, 0.5", fontWeight: 'bold'}}>
                                                <div>제목 : {obj.title}</div>
                                                <div>내용 : {obj.content}</div>
                                                <div>좋아요 {obj.likeCount}</div>
                                                <div>댓글 {obj.comments.length}</div>
                                            </div>
                                        </div>
                                    </Link>
                                </div>
                            )
                        })}
                </div>
            </>
            }
            {postLikeOrderData &&
            <>
                <div style={{fontSize:"40px", fontWeight:"bold"}}>좋아요순</div>
                <div style={{display:"flex"}}>
                {postLikeOrderData?.postsList?.content?.map((obj,idx)=>{
                            return(
                                <div key={idx}>
                                    <div style={{display:"flex"}}>
                                        <div>{obj.view} views</div>
                                    </div>
                                    <Link to="/communitydetail" state={{postId : obj.id}}>
                                        <div style={{ position: 'relative', border:"2px solid black", width:"266.66px", height:"400px" }}>
                                            <img src={'https://'+obj.galleries?.[0]?.filePath} width="100%" height="100%" alt="포스터주소"/>
                                            <div style={{ position: 'absolute', bottom:0, width:'100%', zIndex: 1, color: 'white', background:"rgba(0, 0, 0, 0.5", fontWeight: 'bold'}}>
                                                <div>제목 : {obj.title}</div>
                                                <div>내용 : {obj.content}</div>
                                                <div>좋아요 {obj.likeCount}</div>
                                                <div>댓글 {obj.comments.length}</div>
                                            </div>
                                        </div>
                                    </Link>
                                </div>
                            )
                        })}
                </div>
            </>
            }
        </>
    )
};

export default Community;