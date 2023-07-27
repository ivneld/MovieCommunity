import React, { useCallback, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import useSWR, { mutate } from 'swr';
import fetcher from '../../utils/fetcher';
import fetcherAccessToken from '../../utils/fetcherAccessToken';
import { InputArea } from "./styles";
import axios from "axios";

const CommunityDetail = () => {
    const location = useLocation();
    const postId = location.state.postId;

    const apiUrl = process.env.REACT_APP_API_URL;
    const { data: detailData, error } = useSWR(`${apiUrl}/posts/read/${postId}`, fetcherAccessToken);
    console.log('detailData',detailData)
    const { data: commentData, error2 } = useSWR(`${apiUrl}/api/posts/${postId}/comments`, fetcher);
    console.log('commentData',commentData)
    const { data: currentUserData, error3 } = useSWR(`${apiUrl}/auth/`, fetcherAccessToken);
    console.log('currentUserData',currentUserData)
    const [comment, setComment] = useState('')

    const handleSubmit = async (e)=>{
        try{
            const accessToken = localStorage.getItem('accessToken');
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const req = {
                    comment: comment,
            }
            const response = await axios.post(`${apiUrl}/api/posts/${postId}/comments`, req, config);
            console.log('submit 완료', response.data)
            setComment('')
        } catch (error){
            console.log('submit 실패', error)
            setComment('')
        }
    }

    const handlePostLike = async (e)=>{
        try{
            const accessToken = localStorage.getItem('accessToken');
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const response = await axios.post(`${apiUrl}/heart/${postId}`, config);
            console.log('게시글 좋아요 완료', response.data)
        } catch (error){
            console.log('게시글 좋아요 실패', error)
        }
    }

    return(
        <>
            {detailData &&
            <>
                <div>{detailData.post?.title}</div>
                <div style={{display:"flex"}}>
                    <div>{detailData.post?.writer}</div>
                    <div>{detailData.post?.createdDate}</div>
                </div>
                <img src={`https://`+detailData.post?.galleries[0]?.filePath} width="266.66px" height="400px" alt="포스터주소"/>
               
                <div>{detailData.post?.content}</div>
                <div style={{display:"flex"}}>
                    <div onClick={handlePostLike}>좋아요 {detailData.post?.likeCount}</div>
                    <div>댓글 {detailData.post?.comments?.length}</div>
                </div>
                <InputArea
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    placeholder="영화에 대한 의견을 자유롭게 남겨주세요."
                />
                <div>{comment.length}/100</div>
                <div style={{display:"flex"}}>
                    <div>{currentUserData?.information?.nickname}</div>
                    <div style={{marginLeft:'auto'}} onClick={handleSubmit}>댓글남기기</div>
                </div>
                <hr/>
                {commentData?.map((obj,index)=>{
                    return(
                    <div>
                        <div style={{display:"flex"}}>
                            <div>{obj.nickname}</div>
                            <div>{obj.createdDate}</div>
                            <div>좋아요 속성이 없는데??</div>
                        </div>
                        <div>{obj.comment}</div>
                    </div>
                    )
                })
                }
            </>
            }
        </>
    )
};

export default CommunityDetail;