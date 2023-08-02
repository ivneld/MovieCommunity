import React, { useCallback, useState } from 'react';
import { useParams, useLocation, Link } from 'react-router-dom';
import useSWR, { mutate } from 'swr';
import fetcher from '../../utils/fetcher';
import fetcherAccessToken from '../../utils/fetcherAccessToken';
import { InputArea, CommentInfo, UpdateDelete } from "./styles";
import axios from "axios";
import jwt_decode from "jwt-decode";

const CommunityDetail = () => {
    const location = useLocation();
    const postId = location.state.postId;
    const accessToken = localStorage.getItem('accessToken');
    const decodedToken = jwt_decode(accessToken);
    const memberId = decodedToken.sub

    const apiUrl = process.env.REACT_APP_API_URL;
    const { data: detailData, error } = useSWR(`${apiUrl}/posts/read/${postId}`, fetcherAccessToken); // 현재 게시글 상세 데이터
    console.log('detailData',detailData)
    const { data: commentData, error2 } = useSWR(`${apiUrl}/posts/${postId}/comments`, fetcher); // 현재 게시글 댓글/대댓글
    console.log('commentData',commentData)
    const { data: currentUserData, error3 } = useSWR(`${apiUrl}/mypage/${memberId}/profile`, fetcherAccessToken); // 현재 유저 데이터
    console.log('currentUserData',currentUserData)

    const [comment, setComment] = useState('') // 댓글
    const [replyComment, setReplyComment] = useState('') // 대댓글

    const [updatingComment, setUpdatingComment] = useState(''); // 댓글 수정
    const [updatingReplyComment, setUpdatingReplyComment] = useState(''); // 대댓글 수정

    const [updatingCommentId, setUpdatingCommentId] = useState(null); // 댓글 수정 시 댓글 id 선택
    const [updatingReplyCommentId, setUpdatingReplyCommentId] = useState(null); // 대댓글 수정 시 대댓글 id 선택
    
    const [replyingCommentId, setReplyingCommentId] = useState(null); // 대댓글 시 댓글 id 선택

    // ****************************** 게시글 관련 함수 ******************************
    const handlePostLike = async (e)=>{ // 게시글 좋아요
        try{
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const response = await axios.post(`${apiUrl}/heart/${postId}`, null, config);
            console.log('게시글 좋아요 완료', response.data)
        } catch (error){
            console.log('게시글 좋아요 실패', error)
        }
    }
    
    const handlePostDelete = async (postId) => { // 게시글 삭제
        try{
            const response = await axios.delete(`${apiUrl}/posts/${postId}`)
            console.log('게시글 삭제가 완료되었습니다.');
            alert('게시글 삭제가 완료되었습니다.')
        }
        catch(error){
            console.error('게시글 삭제 실패!', error);
        }
    }

    // ****************************** 댓글 관련 함수 ******************************
    const handleSubmit = async (e)=>{ // 댓글 생성
        if(comment == ''){
            alert('내용을 입력해주세요')
            return
        }
        try{
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const req = {
                    comment: comment,
            }
            const response = await axios.post(`${apiUrl}/posts/${postId}/comments`, req, config);
            console.log('댓글 생성 완료', response.data)
            setComment('')
        } catch (error){
            console.log('댓글 생성 실패', error)
            setComment('')
        }
    }

    const handleCommentLike = async (commentId) => { // 댓글 좋아요
        try{
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const response = await axios.post(`${apiUrl}/heart/comment/${commentId}`, null, config);
            console.log('댓글 좋아요 완료', response.data)
        } catch (error){
            console.log('댓글 좋아요 실패', error)
        }
    }

    const handleCommentUpdate = async (id) => { // 댓글 수정
        if(updatingComment===''){
            alert('댓글 내용을 입력해주세요')
            return
        }
        const postsId = postId 
        try{
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const req = {
                comment: updatingComment
            }
            const response = await axios.put(`${apiUrl}/posts/${postsId}/comments/${id}`, req, config)

            console.log('댓글 수정이 완료되었습니다.');
            alert('댓글 수정이 완료되었습니다.');
            // 수정 완료 후 상태 업데이트
            setUpdatingComment('')
            setUpdatingCommentId(null);
        }
        catch(error){
            console.error('댓글 수정 실패!', error);
        }
    }

    const handleCommentDelete = async (id) => { // 댓글 삭제
        const postsId = postId 
        try{
            const response = await axios.delete(`${apiUrl}/posts/${postsId}/comments/${id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                },
            })

            console.log('댓글 삭제가 완료되었습니다.');
            alert('댓글 삭제가 완료되었습니다.')
        }
        catch(error){
            console.error('댓글 삭제 실패!', error);
        }
    }

    // ****************************** 대댓글 관련 함수 ******************************
    const handleCommentReply = async (commentId) => { // 대댓글 생성
        if(replyComment===''){
            alert('대댓글 내용을 입력해주세요')
            return
        }
        try{
            const req = {
                commentId: commentId,
                subComment: replyComment
            }
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const response = await axios.post(`${apiUrl}/subcomment`, req, config);
            console.log('대댓글 생성 완료', response.data)
            setReplyingCommentId(null) // 대댓글 생성 진행 중 x
            setReplyComment('') // 대댓글 초기화
        } catch (error){
            console.log('대댓글 생성 실패', error)
            setReplyingCommentId(null)
            setReplyComment('')
        }
    }

    const handleReplyCommentLike = async (subCommentId) => { // 대댓글 좋아요
        try{
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const response = await axios.post(`${apiUrl}/heart/subComment/${subCommentId}`, null, config);
            console.log('대댓글 좋아요 완료', response.data)
        } catch (error){
            console.log('대댓글 좋아요 실패', error)
        }
    }

    const handleReplyCommentUpdate = async (commentId, subCommentId) => { // 대댓글 수정
        if(updatingReplyComment===''){
            alert('대댓글 내용을 입력해주세요')
            return
        }
        try{
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const req = {
                commentId: commentId,
                subComment: updatingReplyComment
            }
            const response = await axios.put(`${apiUrl}/subcomment/${subCommentId}`, req, config)

            console.log('대댓글 수정이 완료되었습니다.');
            alert('대댓글 수정이 완료되었습니다.');
            // 수정 완료 후 상태 업데이트
            setUpdatingReplyComment('')
            setUpdatingReplyCommentId(null);
        }
        catch(error){
            console.error('대댓글 수정 실패!', error);
        }
    }
    
    const handleReplyCommentDelete = async (subCommentId) => { // 대댓글 삭제
        try{
            const response = await axios.delete(`${apiUrl}/subcomment/${subCommentId}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
            console.log('대댓글 삭제가 완료되었습니다.');
            alert('대댓글 삭제가 완료되었습니다.')
        }
        catch(error){
            console.error('대댓글 삭제 실패!', error);
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
                {detailData.post?.galleries?.length!==0 && // 사용자가 첨부한 이미지가 존재할 경우에만 이미지 보여줌
                <img src={`https://`+detailData.post?.galleries[0]?.filePath} width="266.66px" height="400px" alt="이미지주소"/>
                }
               
                <div>{detailData.post?.content}</div>
                <div style={{display:"flex"}}>
                    <div style={{display:"flex"}}>
                        <div onClick={handlePostLike}>좋아요 {detailData.post?.likeCount}</div>
                        <div>댓글 {detailData.post?.comments?.length}</div>
                    </div>
                    {detailData.isPostWriter && (
                    <div style={{display:"flex", marginLeft:"auto"}}>
                        <Link to="/communityupdate" state={{postId : detailData.post?.id}} style={{cursor:"pointer"}}>
                            <div>수정</div>
                        </Link>
                        <div style={{cursor:"pointer"}} onClick={()=>(handlePostDelete(detailData.post?.id))}>삭제</div>
                    </div>
                    )
                    }
                </div>
                <InputArea
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    placeholder="영화에 대한 의견을 자유롭게 남겨주세요."
                />
                <div>{comment.length}/100</div>
                <div style={{display:"flex"}}>
                    <div>{currentUserData?.nickName}</div>
                    <div style={{marginLeft:'auto'}} onClick={handleSubmit}>댓글남기기</div>
                </div>
                <hr/>
                {commentData?.map((obj,idx)=>{ // 댓글
                    return(
                    <div>
                        <div></div>
                        <div style={{display:"flex"}}>
                            <CommentInfo>{obj.nickname}</CommentInfo>
                            <CommentInfo>{obj.createdDate}</CommentInfo>
                            <CommentInfo onClick={() => (handleCommentLike(obj.id))}>{obj.likeCount}</CommentInfo>
                            {detailData?.isCommentWriter?.map((obj4,idx4)=>{ // 본인이 단 댓글에만 수정,삭제 폼 보이도록
                                if(idx===obj4)
                                return(
                                <>
                                    <UpdateDelete onClick={() => (setUpdatingCommentId(obj.id))}>수정</UpdateDelete>
                                    <UpdateDelete onClick={() => (handleCommentDelete(obj.id))}>삭제</UpdateDelete>
                                </>
                                )
                            })}
                        </div>

                        {updatingCommentId !== obj.id && // 댓글 수정 클릭 x 경우
                        <div>
                            <div onClick={() => (setReplyingCommentId(obj.id))}>{obj.comment}</div>
                                {replyingCommentId === obj.id && // 대댓글 생성 폼
                                    <div style={{display:"flex"}}>
                                        <div>ㄴ</div>
                                        <input value={replyComment} onChange={(e) => setReplyComment(e.target.value)}></input>
                                        <button onClick={() => (handleCommentReply(obj.id))}>완료</button>
                                    </div>
                                }

                                {obj.subComments?.map((obj2,idx2)=>{ // 대댓글
                                    return(
                                        <div style={{backgroundColor:"#ccc", marginLeft: "20px" ,borderBottom:"1px solid black"}}>
                                            <div style={{display:"flex"}}>
                                                <CommentInfo>{obj2.author}</CommentInfo>
                                                <CommentInfo>{obj2.createdAt}</CommentInfo>
                                                <CommentInfo onClick={() => (handleReplyCommentLike(obj2.id))}>{obj2.cntLikes}</CommentInfo>
                                                {detailData?.isSubCommentWriter?.map((obj3,idx3)=>{ // 본인이 단 대댓글에만 수정,삭제 폼 보이도록
                                                    if(idx===obj3[0] && idx2===obj3[1])
                                                    return(
                                                    <>
                                                        <UpdateDelete onClick={() => (setUpdatingReplyCommentId(obj2.id))}>수정</UpdateDelete>
                                                        <UpdateDelete onClick={() => (handleReplyCommentDelete(obj2.id))}>삭제</UpdateDelete>
                                                    </>
                                                    )
                                                })}
                                            </div>
                                            {updatingReplyCommentId !== obj2.id &&
                                            <div>{obj2.subComment}</div>
                                            }
                                            {updatingReplyCommentId == obj2.id && // 대댓글 수정 클릭 o 경우
                                            <div style={{display:"flex"}}>
                                                <input placeholder={obj2.subComment} value={updatingReplyComment} onChange={(e) => setUpdatingReplyComment(e.target.value)}></input>
                                                <button onClick={() => (handleReplyCommentUpdate(obj.id, obj2.id))}>완료</button>
                                            </div>
                                            }
                                        </div>
                                    )
                                })}
                        </div>
                        }

                        {updatingCommentId === obj.id && // 댓글 수정 클릭 o 경우
                        <div style={{display:"flex"}}>
                            <input placeholder={obj.comment} value={updatingComment} onChange={(e) => setUpdatingComment(e.target.value)}></input>
                            <button onClick={() => (handleCommentUpdate(obj.id))}>완료</button>
                        </div>
                        }
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