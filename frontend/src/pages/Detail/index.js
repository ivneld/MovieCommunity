import React, { useCallback, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import useSWR from 'swr';
import { MovieDiv, Button, CustomDiv } from "./styles";
import fetcher from '../../utils/fetcher';
import fetcherAccessToken from '../../utils/fetcherAccessToken';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './index.css';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import CommentModal from '../../components/CommentModal';

const Detail = () => {
//    const { id } = useParams();
    const location = useLocation();
    const detail = location.state.detail;
    const { data : detailData, error } = useSWR(`http://localhost:8080/movie/${detail}`, fetcher, {
        dedupingInterval: 100000,
    });
    if (error) console.log('데이터를 불러오는 중에 오류가 발생했습니다.')
    if (!detailData) console.log('데이터를 불러오는 중입니다...')
    
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
    return(
        <>
            {detailData && <div style={{marginBottom:"100px"}}>
            <div style={{fontWeight:"bold", fontSize:"30px"}}>{detailData.movieNm}</div>
            <div style={{display:"flex"}}>
                <div>
                    <img src={detailData.posterPath} width="199.995px" height="300px" alt="포스터주소" />
                </div>
                <div>
                    <div style={{display:"flex"}}>
                        <div style={{width:"300px"}}>
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
            </div>
            <div>
            <Tabs selectedIndex={activeTab} onSelect={handleTabChange}>
                <TabList>
                <Tab>출연/제작</Tab>
                <Tab>주간랭킹</Tab>
                <Tab>예고편/포토</Tab>
                <Tab>시리즈</Tab>
                <Tab>코멘트/리뷰</Tab>
                </TabList>

                <TabPanel>
                    <CustomDiv>출연진</CustomDiv>
                    <Slider {...sliderSettings}>
                        {detailData.credits.map((crd) => {
                            if (crd.creditCategory=="ACTOR"){
                                return(
                                    <div key={crd.id}>
                                        <img src={crd.image} width="100px" height="150px" alt={crd.name} />
                                        <p>{crd.name}</p>
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
                    <CustomDiv>예고편</CustomDiv>
                    <div>{detailData.videoUrl}</div>
                </TabPanel>

                <TabPanel>
                    <CustomDiv>시리즈</CustomDiv>
                    <div>{detailData.series}</div>
                </TabPanel>

                <TabPanel>
                    <CustomDiv>한줄 코멘트</CustomDiv>
                    <div onClick={() => (onClickModal())}>코멘트 남기기</div>
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

function Comment(){
    const { data : commentData, error } = useSWR('http://localhost:8080/comment', fetcherAccessToken, {
        dedupingInterval: 100000,
    });
    const location = useLocation();
    const detail = location.state.detail;
    console.log('Comment!')
    console.log(commentData);
    return(
        <>
            <div>오래된순, 최신순, 추천순</div>
            <div style={{display:"flex", justifyContent:"center"}}>
                <div style={{ maxWidth:"1150px", display: "flex", flexWrap: "wrap" }}>
                    {commentData?.map((obj, index) => {
                        if(obj.movieId == detail){
                        return (
                            <div key={index} style={{ width:"25%", marginBottom:"20px", border:"1px solid black" }}>
                                <div>이름 : {obj.username}</div>
                                <div>내용 : {obj.content}</div>
                                <div>좋아요 수 : {obj.likeCount}</div>
                            </div>
                        )
                        }
                    return null;
                    })}
                </div>
            </div>
            <div>더보기</div>
        </>
    )
}
export default Detail;