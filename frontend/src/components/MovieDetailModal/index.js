import Modal from "../Modal";
import React, { useCallback } from "react";
import { Label, Button, Form, MovieDiv } from "./styles";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

const MovieDetailModal = ({show, onCloseModal, modalData, postingBoardMovieId}) => {
    const navigate = useNavigate();

    const onSubmit = useCallback(
        (e) => {
          e.preventDefault();
          console.log(postingBoardMovieId);
          navigate(`/boards/create/${postingBoardMovieId}`);
        },
        [postingBoardMovieId],
      );
    return(
        <>
        {modalData &&
            <Modal show={show} onCloseModal={onCloseModal}>
                <Form onSubmit={onSubmit}>
                    <Label>
                        <img src={modalData.posterPath} height="300px" alt='포스터주소' />
                        <div>
                            <div style={{marginBottom:"20px"}}>
                                <span style={{fontSize:"27.5px", fontWeight:"bold"}}>{modalData.movieNm}</span>
                            </div>
                            <div style={{display:"flex"}}>
                                <div>
                                    <MovieDiv>개봉일 : {modalData.openDt}</MovieDiv>
                                    <MovieDiv>등급 : {modalData.watchGradeNm}</MovieDiv>
                                </div>
                                <div>
                                    <MovieDiv>평점 : {modalData.voteAverage}</MovieDiv>
                                    <MovieDiv>러닝타임 : {modalData.showTm}분</MovieDiv>
                                </div>
                            </div>
                            <div>
                                <MovieDiv>줄거리 : {modalData.overview}</MovieDiv>
                            </div>
                            {/* <div>
                                <MovieDiv>{modalData.prdtStatNm}</MovieDiv>
                            </div> */}
                            <div style={{display:"flex"}}>
                                <MovieDiv style={{marginRight:"175px"}}>⭐ {modalData.interest}</MovieDiv>
                                <MovieDiv><Link to={`/movie/${modalData.id}`} key={modalData.id} state={{detail : modalData.id}}>+ 더보기</Link></MovieDiv>
                            </div>
                        </div>
                </Label>
                    <Button type="submit">게시글 작성</Button>
                </Form>
            </Modal>
        }
        </>
    )
}

export default MovieDetailModal;