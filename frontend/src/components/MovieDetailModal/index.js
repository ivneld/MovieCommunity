import Modal from "../Modal";
import React from "react";
import { Label } from "./styles";

const MovieDetailModal = ({show, onCloseModal, content}) => {
    return(
        <Modal show={show} onCloseModal={onCloseModal}>
            <Label id='haha'>
                <span>모달</span>
                <div>상세정보 : {content}</div>
            </Label>
        </Modal>
    )
}

export default MovieDetailModal;