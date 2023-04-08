import Modal from "../Modal";
import React, { useCallback } from "react";
import { Label, Button, Form } from "./styles";
import { useNavigate } from "react-router-dom";

const MovieDetailModal = ({show, onCloseModal, content, postingBoardMovieId}) => {
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
        <Modal show={show} onCloseModal={onCloseModal}>
            <Form onSubmit={onSubmit}>
                <Label>
                <span>모달</span>
                <div>상세정보 : {content}</div>
                </Label>
                <Button type="submit">게시글 작성</Button>
            </Form>
        </Modal>

        
    )
}

export default MovieDetailModal;