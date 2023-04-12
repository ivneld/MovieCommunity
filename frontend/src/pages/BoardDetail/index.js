import React, { useCallback, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { Label, Form, Input, InputWrapper, Button, Success, Error } from './styles';
import axios from 'axios';
import useInput from '../../hooks/useInput';

const BoardDetail = () => {
//    const { id } = useParams();
   const location = useLocation();
   const detail = location.state.detail;

   const [PostCommentError, setPostCommentError] = useState(false);
   const [PostCommentSuccess, setPostCommentSuccess] = useState(false);
   const [comment, onChangeComment] = useInput('');
   const onSubmit = useCallback(
    (e) => {
      e.preventDefault();
      setPostCommentError(false); // 초기화 (요청 연달아 날릴 때 남아있는 결과 다시가는거 방지)
      setPostCommentSuccess(false);

      const req = {'comments' : comment}
      const config = {"Content-Type": 'application/json'};

      axios
        .post('http://localhost:8080/comment/create', req, config)
        .then((response) => {
            console.log(response)
            setPostCommentSuccess(true);
        })
        .catch((error) => {
            console.log(error.response)
            setPostCommentError(true);
        });
    },
    [comment],
  );
    return(
        <>
            <div>게시글제목 : {detail.title}</div>
            <div>게시글Id : {detail.id}</div>
            <div>좋아요 : {detail.like}</div>
            <div>멤버Id : {detail.memberId}</div>
            <div>멤버Nm : {detail.memberNm}</div>
            <div>영화제목 : {detail.movieNm}</div>
            <div>영화Id : {detail.movieId}</div>

            <Form onSubmit={onSubmit}>
                <Label>
                {/* <span>댓글 작성</span> */}
                    <Input type="text" value={comment} onChange={onChangeComment} placeholder='댓글 작성'/>
                    <Button type="submit">등록</Button>
                </Label>
                {PostCommentSuccess && <Success>댓글 등록 성공!</Success>}
                {PostCommentError && <Error>댓글 등록 실패!</Error>}
            </Form>
        </>
    )
};

export default BoardDetail;