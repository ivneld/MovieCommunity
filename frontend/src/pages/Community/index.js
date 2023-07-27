import React, { useCallback, useState, useContext, useRef } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import useSWR, { mutate } from 'swr';
import { MovieDiv, InterestSpan, CustomDiv, TextArea, Form } from "./styles";
import fetcher from '../../utils/fetcher';
import fetcherAccessToken from '../../utils/fetcherAccessToken';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
// import CommentModal from '../../components/CommentModal';
import { AuthContext } from "../../context/AuthProvider";
import axios from "axios";
import Modal2 from '../../components/Modal2';
import { Link } from 'react-router-dom';

const Community = () => {
    const apiUrl = process.env.REACT_APP_API_URL;
    const { data: postData, error } = useSWR(`${apiUrl}/api/posts/4`, fetcher);
    console.log(postData)
    const { data: newPostData, error2 } = useSWR(`${apiUrl}/posts/new`, fetcher);
    console.log(newPostData)
    const accessToken = localStorage.getItem('accessToken');
    console.log('accessToken',accessToken)
    return(
        <>
            <Link to="/communitypost" style={{cursor:"pointer", fontSize:"24px", fontWeight:"bold"}}>
                ✏️글쓰기
            </Link>
            {
                newPostData && 
                <>
                    <div style={{fontSize:"40px", fontWeight:"bold"}}>최근 작성된 리뷰</div>
                    <div style={{display:"flex"}}>
                        {newPostData?.postsList?.content?.map((obj,index)=>{
                        console.log('경로',obj.galleries?.[0]?.filePath)
                            return(
                                <div key={index}>
                                    <div>{obj.view} views</div>
                                    <Link to="/communitydetail" state={{postId : obj.id}}>
                                        <div style={{ position: 'relative', background:"purple", width:"266.66px", height:"400px" }}>
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