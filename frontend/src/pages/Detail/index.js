import React, { useCallback, useState, useContext, useRef } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import useSWR, { mutate } from 'swr';
import { MovieDiv, InterestSpan, CustomDiv, TextArea, Form } from "./styles";
import fetcher from '../../utils/fetcher';
import fetcherAccessToken from '../../utils/fetcherAccessToken';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './index.css';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
// import CommentModal from '../../components/CommentModal';
import { AuthContext } from "../../context/AuthProvider";
import axios from "axios";
import Modal2 from '../../components/Modal2';

const Detail = () => {
//    const { id } = useParams();
    const { auth, setAuth } = useContext(AuthContext);
    const location = useLocation();
    const detail = location.state.detail;
    const { data : detailData, error } = useSWR(`http://localhost:8080/movie/${detail}`, fetcherAccessToken, { // 좋아요 때문에 fetcher 말고 이거 써야함
        dedupingInterval: 100000,
    });
    if (error) console.log('데이터를 불러오는 중에 오류가 발생했습니다.')
    if (!detailData) console.log('데이터를 불러오는 중입니다...')
    console.log(detailData)
    const prevArrow = (
        <button className="slick-prev" aria-label="Previous" type="button"/>
    );
    
    const nextArrow = (
        <button className="slick-next" aria-label="Next" type="button"/>
    );
    const sliderSettings = {
        slidesToShow: 10,
        slidesToScroll: 1,
        infinite: true,
        dots: true,
        autoplay: true,
        autoplaySpeed: 5000,
        responsive: [
            {
            breakpoint: 768,
            settings: {
                slidesToShow: 2,
            },
            },
            {
            breakpoint: 480,
            settings: {
                slidesToShow: 1,
            },
            },
        ],
        prevArrow: prevArrow,
        nextArrow: nextArrow,
        };
    const [activeTab, setActiveTab] = useState(0);

    const handleTabChange = (index) => {
        setActiveTab(index);
    };

    const [showMovieDetailModal,setShowMovieDetailModal] = useState(false);
    const onCloseModal = useCallback(() => {
        setShowMovieDetailModal(false);
    }, []);
    const onClickModal = useCallback(() => {
        setShowMovieDetailModal(true);
    }, []);

    const moveRef = useRef(null)
    const handleScroll = () => {
        moveRef.current.scrollIntoView({behavior:"smooth"})
    }
    const onClickInterest = async () => {
        if (!auth){
            alert('로그인이 필요한 기능입니다.')
            return;
        }

        const accessToken = localStorage.getItem('accessToken');
        const config = {
            headers:{
                Authorization : `Bearer ${accessToken}`
            }
        }
        const req = {
            movieId: detail
        }
        try{
            if (detailData.myInterest==false) {
                console.log('좋아요 등록!')
            }
            else if (detail.myInterest==true) {
                console.log('좋아요 취소!')
            }
            const response = await axios.post(`http://localhost:8080/movie/${detail}/interest`, req, config);
            // mutate 함수는 SWR 캐시 갱신하고 컴포넌트 리렌더링
            // mutatae 함수를 호출 할 때, 적절한 키 값 필요 (updatedData)
            // updatedData는 detailData를 복제한 후, myInterest 값을 반전시킴
            const updatedData = { ...detailData, myInterest: !detailData.myInterest };
            mutate(`http://localhost:8080/movie/${detail}`, updatedData);
        } catch(error){
            console.error('좋아요 실패! :',error)
        }
    }

    return(
        <>
            {detailData && 
                <div style={{marginBottom:"100px"}}>
                    <div style={{backgroundColor:"black",height:"9vh"}}></div>
                    <div style={{display: "flex", backgroundImage:`url(${detailData.posterPath})`, backgroundSize:"cover", backgroundRepeat: 'no-repeat', backgroundPosition: 'center', width: '100%', height:'75vh'}}>
                        <div style={{fontWeight:"bold", fontSize:"72px", textShadow:"4px 2px 4px black", color:"white", marginTop:"auto", marginLeft:"100px", marginBottom:"80px"}}>{detailData.movieNm}</div>
                
                        <div style={{marginLeft: "auto", marginTop:"auto", marginBottom:"25px"}}>
                            <img style={{marginBottom: "10px"}} src={detailData.posterPath} width="400px" height="600px" alt="포스터주소" />
                            {detailData.myInterest === false &&
                            <InterestSpan onClick={onClickInterest}>🤍 {detailData.interest}</InterestSpan>
                            }
                            {detailData.myInterest === true &&
                            <InterestSpan onClick={onClickInterest}>💙 {detailData.interest}</InterestSpan>
                            }
                        </div>
                    </div>
                    <div ref={moveRef} style={{backgroundColor:"black", height:"9vh"}}>
                        <div onClick={handleScroll} style={{display:"flex", justifyContent:"center", fontSize:"55px", cursor:"pointer"}}>🔽</div>
                    </div>

                    <div style={{height:'100vh'}}>
                        <Tabs selectedIndex={activeTab} onSelect={handleTabChange}>
                            <TabList>
                            <Tab>기본정보</Tab>
                            <Tab>출연/제작</Tab>
                            <Tab>주간랭킹</Tab>
                            <Tab>코멘트/리뷰</Tab>
                            </TabList>

                            <TabPanel>
                                <div>
                                    <div style={{display:"flex"}}>
                                        <div style={{width:"350px"}}>
                                            <MovieDiv>개봉일 {detailData.openDt}</MovieDiv>
                                            <MovieDiv>등급 {detailData.watchGradeNm}</MovieDiv>
                                            <MovieDiv>국가 {detailData.nationNm}</MovieDiv>
                                            <MovieDiv>장르 {detailData.genres}</MovieDiv>
                                            {/* <MovieDiv style={{display:"flex"}}>예매하기 <Button type="submit">주위 극장 찾아보기</Button></MovieDiv> */}
                                        </div>
                                        <div>
                                            <MovieDiv>평점 {detailData.voteAverage}</MovieDiv>
                                            <MovieDiv>러닝타임 {detailData.showTm}분</MovieDiv>
                                            <MovieDiv>누적관객수 {detailData.audiAcc}명</MovieDiv>
                                            <MovieDiv>제작사 {detailData.company}</MovieDiv>
                                        </div>
                                    </div>
                                    <MovieDiv>줄거리 {detailData.overview}</MovieDiv>
                                    <MovieDiv>바로보기 {detailData.ott}</MovieDiv>
                                </div>
                            </TabPanel>

                            <TabPanel>
                                <CustomDiv>출연진</CustomDiv>
                                <Slider {...sliderSettings}>
                                    {detailData.credits.map((crd) => {
                                        if (crd.creditCategory=="ACTOR"){
                                            return(
                                                <div key={crd.id}>
                                                    <img src={crd.image} width="100px" height="150px" alt={crd.name} />
                                                    <p>{crd.name}</p>
                                                    <p style={{marginTop:"-15px"}}>{crd.cast} 역</p>
                                                </div>
                                            )
                                        }
                                    })}
                                </Slider>

                                <CustomDiv>제작진</CustomDiv>
                                {detailData.credits.map((crd) => {
                                    if (crd.creditCategory=="CREW"){
                                        return(
                                            <div key={crd.id}>
                                                <img src={crd.image} width="100px" height="150px" alt={crd.name} />
                                                <p>{crd.name}</p>
                                                <p style={{marginTop:"-15px"}}>{crd.cast}</p>
                                            </div>
                                        )
                                    }
                                })}
                            </TabPanel>

                            <TabPanel>
                                <CustomDiv>주간랭킹</CustomDiv>
                                <div>{detailData.weeklyRanks}</div>
                            </TabPanel>

                            <TabPanel>
                                <CustomDiv>한줄 코멘트</CustomDiv>
                                <div onClick={() => (onClickModal())} style={{cursor:"pointer"}}>✏️코멘트 남기기</div>
                                <Comment/>
                                <hr/>
                                <CustomDiv>커뮤니티 리뷰</CustomDiv>
                                <div>아직 x</div>
                            </TabPanel>
                        </Tabs>
                    </div>
                    <CommentModal
                            show={showMovieDetailModal}
                            onCloseModal={onCloseModal}
                            setShowMovieDetailModal={setShowMovieDetailModal}
                            data={detail}
                    />
                </div>
            }
        </>
    )
};

const CommentModal = ({show, onCloseModal, data}) => {
    const { auth, setAuth } = useContext(AuthContext);
    const [comment, setComment] = useState('');

    const handleChange = (event) => {
      setComment(event.target.value);
    };
    console.log(auth)
    const handleSubmit = async (event) => {
        event.preventDefault();
        if (!auth){
            setComment('');
            alert('로그인이 필요한 기능입니다.')
            return;
        }
        try{
            const accessToken = localStorage.getItem('accessToken');
            const name = localStorage.getItem('name');
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const req = {
                    movieId: data,
                    content: comment,
                    username: auth,
            }
            const response = await axios.post("http://localhost:8080/comment/new", req, config);
            console.log('댓글 등록 성공!')
            setComment('');
            onCloseModal()
        } catch(error){
            console.error('댓글 등록 실패:',error)
            setComment('');
        }
    };
    return(
        <>
            <Modal2 show={show} onCloseModal={onCloseModal}>
                <Form onSubmit={handleSubmit}>
      
                        <div style={{marginBottom:"20px"}}>
                            <span style={{fontSize:"27.5px", fontWeight:"bold"}}>코멘트 남기기</span>
                        </div>
            
                        <TextArea
                            value={comment}
                            onChange={handleChange}
                            placeholder="영화에 대한 의견을 자유롭게 남겨주세요"
                        />

                        <div style={{display:"flex"}}>
                            <button type="submit">등록</button>
                        </div>
          
                </Form>
            </Modal2>
        </>
    )
}

function Comment(){
    const location = useLocation();
    const detail = location.state.detail;
    const { data : commentData, error } = useSWR(`http://localhost:8080/comment/${detail}`, fetcher, {
        dedupingInterval: 100000,
    });

    const { data : moreData, error2 } = useSWR(`http://localhost:8080/comment/more/${detail}`, fetcher, {
        dedupingInterval: 100000,
    });

    const [isMoreData, setIsMoreData] = useState(false)
    console.log(commentData)
    console.log(moreData)

    return(
        <>
            <div>오래된순, 최신순, 추천순</div>
            <div style={{display:"flex", justifyContent:"center"}}>
                <div style={{ maxWidth:"1150px", display: "flex", flexWrap: "wrap" }}>
                    {!isMoreData &&
                    commentData?.map((obj, index) => {
                        if(obj.movieId == detail){
                        return (
                            <div key={index} style={{ width:"25%", marginBottom:"20px", border:"1px solid black" }}>
                                <div style={{display:"flex"}}>
                                    <div>이름 : {obj.username}</div>
                                    <div>좋아요 수 : {obj.likeCount}</div>
                                </div>
                                <div>내용 : {obj.content}</div>
                                <div style={{display:"flex"}}>
                                    <div>수정</div>
                                    <div>삭제</div>
                                </div>
                            </div>
                        )
                        }
                    return null;
                    })}
                </div>
            </div>

            <div style={{display:"flex", justifyContent:"center"}}>
                <div style={{ maxWidth:"1150px", display: "flex", flexWrap: "wrap" }}>
                    {isMoreData &&
                     moreData?.map((obj, index) => {
                        if(obj.movieId == detail){
                        return (
                            <div key={index} style={{ width:"25%", marginBottom:"20px", border:"1px solid black" }}>
                                <div style={{display:"flex"}}>
                                    <div>이름 : {obj.username}</div>
                                    <div>좋아요 수 : {obj.likeCount}</div>
                                </div>
                                <div>내용 : {obj.content}</div>
                                <div style={{display:"flex"}}>
                                    <div>수정</div>
                                    <div>삭제</div>
                                </div>
                            </div>
                        )
                        }
                    return null;
                    })}
                </div>
            </div>
            {!isMoreData &&
                <div onClick={() => (setIsMoreData(true))} style={{cursor:"pointer"}}>🔻더보기</div>
            }
            {isMoreData &&
                <div onClick={() => (setIsMoreData(false))} style={{cursor:"pointer"}}>🔺닫기</div>
            }
        </>
    )
}
export default Detail;