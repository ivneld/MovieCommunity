import React, { useCallback, useState, useContext, useRef } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import useSWR, { mutate } from 'swr';
import { InputArea, InputTextArea, Button } from "./styles";
import fetcher from '../../utils/fetcher';
import fetcherAccessToken from '../../utils/fetcherAccessToken';

import { AuthContext } from "../../context/AuthProvider";
import axios from "axios";
import Modal2 from '../../components/Modal2';

const CommunityUpdate = () => {
    const location = useLocation();
    const postId = location.state.postId;
    const apiUrl = process.env.REACT_APP_API_URL;
    const navigate = useNavigate();
    const [showMovieDetailModal,setShowMovieDetailModal] = useState(false); // 댓글 생성
    const [movieId, setMovieId] = useState(null)
    const [movieNm, setMovieNm] = useState('')
    const onCloseModal = useCallback((id, movieNm) => {
        setShowMovieDetailModal(false);
        setMovieId(id)
        setMovieNm(movieNm)
    }, []);

    const onClickModal = useCallback(() => {
        setShowMovieDetailModal(true);
    }, []);
    const [title, setTitle] = useState('')
    const [content, setContent] = useState('')
    const [galleryId, setGalleryId] = useState(null)

    const { data: everyImages } = useSWR(`${apiUrl}/gallery`, fetcher);
    console.log('everyImages',everyImages)


    const handleFile = async (e)=>{
        try{
            const formData = new FormData();
            formData.append("file", e.target.files[0]);
            const response = await axios.post(`${apiUrl}/gallery`, formData, {
                headers:{
                    'Content-Type': 'multipart/form-data',
                    'Accept': '*/*',
                }
            })
            console.log('이미지 업로드 완료', response.data)
            setGalleryId(response.data)
        } catch (error){
            console.log('이미지 업로드 실패', error)
        }
    }

    const handlePostUpdate = async () => { // 게시글 수정
        try{
            const req = {
                title: title,
                content: content,
                galleryIds: galleryId ? [galleryId] : [], // galleryId가 null이면 빈 배열로 설정
                movieId: movieId
            }
            const response = await axios.put(`${apiUrl}/posts/${postId}`, req)

            console.log('게시글 수정이 완료되었습니다.');
            alert('게시글 수정이 완료되었습니다.');
            navigate(`${apiUrl}/community`)
        }
        catch(error){
            console.error('게시글 수정 실패!', error);
        }
    }
    return(
        <>
            {!movieId &&
                <div onClick={() => (onClickModal())} style={{cursor:"pointer", fontSize:"24px", fontWeight:"bold", border:"1px solid black", width:"300px"}}>영화제목을 검색해주세요</div>
            }
            {movieId &&
                <div>{movieNm}</div>
            }
            <InputArea
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="제목"
            />
            <hr/>
            <InputTextArea
                value={content}
                onChange={(e) => setContent(e.target.value)}
                placeholder="영화에 대해 나누고 싶은 이야기를 자유롭게 나눠주세요."
            />
            <CommunityPostModal
                show={showMovieDetailModal}
                onCloseModal={onCloseModal}
                setShowMovieDetailModal={setShowMovieDetailModal}
            />

            <input type="file" onChange={handleFile}/>

     
            <Button onClick={handlePostUpdate}>완료</Button>
        </>
    )
};

export default CommunityUpdate;

const CommunityPostModal = ({show, onCloseModal, data}) => {
    const apiUrl = process.env.REACT_APP_API_URL;
    const [searchQuery, setSearchQuery] = useState('')
	const { data: searchResults } = useSWR(`${apiUrl}/movie/search?movieNm=${searchQuery}`, fetcher);

	// 검색목록 클릭 시
	const clickSearchSubmit = ({id, movieNm}) => {
		if (searchQuery && searchResults && searchResults.length > 0) {
            onCloseModal(id, movieNm)
		}
		setSearchQuery("");
	};

    return(
        <>
            <Modal2 show={show} onCloseModal={onCloseModal}>

                        <div style={{marginBottom:"20px"}}>
                            <span style={{fontSize:"27.5px", fontWeight:"bold"}}>영화 검색</span>
                        </div>			

                        <InputArea
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            placeholder="원하는 영화를 발견해보세요"
                        />
                            {searchResults && searchResults.length > 0 && searchQuery && (
                                <div style={{display:"flex", justifyContent:"center"}}>
                                    <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                                    {searchResults.map((obj, index) => {
                                            return (
                                            <div key={obj.id} onClick={() => (clickSearchSubmit({id:obj.id, movieNm:obj.movieNm}))} style={{ width: "25%", marginBottom:"20px" }}>
                                                <img src={obj.posterPath} width="133.33px" height="200px" alt="포스터주소"/>
                                                <div>{obj.movieNm}</div>
                                                <div>{obj.openDt}</div>
                                            </div>
                                        );
                                    })}
                                    </div>
                                </div>
                            )}

                        <div style={{display:"flex"}}>
                            <button type="submit">확인</button>
                        </div>
            </Modal2>
        </>
    )
}