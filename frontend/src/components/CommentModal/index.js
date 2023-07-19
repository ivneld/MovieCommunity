// import Modal2 from "../Modal2";
// import React, { useState, useContext } from "react";
// import { Label, TextArea, Form, MovieDiv } from "./styles";
// import { AuthContext } from "../../context/AuthProvider";
// import axios from "axios";

// const CommentModal = ({show, onCloseModal, data}) => {
//     const { auth, setAuth } = useContext(AuthContext);
//     const [comment, setComment] = useState('');

//     const handleChange = (event) => {
//       setComment(event.target.value);
//     };
//     console.log(auth)
//     const handleSubmit = async (event) => {
//         event.preventDefault();
//         if (!auth){
//             setComment('');
//             alert('로그인이 필요한 기능입니다.')
//             return;
//         }
//         try{
//             const accessToken = localStorage.getItem('accessToken');
//             const name = localStorage.getItem('name');
//             const config = {
//                 headers:{
//                     Authorization : `Bearer ${accessToken}`
//                 }
//             }
//             const req = {
//                     movieId: data,
//                     content: comment,
//                     username: auth,
//             }
//             const response = await axios.post("http://localhost:8080/comment/new", req, config);
//             console.log('댓글 등록 성공!')
//             setComment('');
//         } catch(error){
//             console.error('댓글 등록 실패:',error)
//             setComment('');
//         }
//     };
//     return(
//         <>
//             <Modal2 show={show} onCloseModal={onCloseModal}>
//                 <Form onSubmit={handleSubmit}>
      
//                         <div style={{marginBottom:"20px"}}>
//                             <span style={{fontSize:"27.5px", fontWeight:"bold"}}>코멘트 남기기</span>
//                         </div>
            
//                         <TextArea
//                             value={comment}
//                             onChange={handleChange}
//                             placeholder="영화에 대한 의견을 자유롭게 남겨주세요"
//                         />

//                         <div style={{display:"flex"}}>
//                             <button type="submit">등록</button>
//                         </div>
          
//                 </Form>
//             </Modal2>
//         </>
//     )
// }

// export default CommentModal;