import React, { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { LinkContainer, MovieSpan, CategorySpan, PosterContainer1, PosterContainer2, OpendtApiContainer, TimeStamp } from './styles';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import fetcherAccessToken from '../../utils/fetcherAccessToken';
import MovieDetailModal from '../../components/MovieDetailModal';
import { useSearchParams } from 'react-router-dom';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './index.css';
import axios from 'axios';

const Main = () => {
    const apiUrl = process.env.REACT_APP_API_URL;
    const [movieRanking, setMovieRanking] = useState([]); // 영화 월간랭킹
    const [moviePropose, setMoviePropose] = useState([]); // 영화 추천

    const [showMovieDetailModal,setShowMovieDetailModal] = useState(false);
    const [modalData, setModalData] = useState(null);

    
    const onCloseModal = useCallback(() => {
        setShowMovieDetailModal(false);
    }, []);
    const onClickModal = useCallback((e) => {
        setShowMovieDetailModal(true);
        setModalData(e);
    }, []);

    const handleMonthMovieRanking = async ()=>{ // 영화 월간랭킹 테스트 
        try{
            const req = {
                    year: 2023,
                    month: 6,
                    day: 1,
            }
            const response = await axios.post(`${apiUrl}/movie/weeklytest/member4`, req);
            console.log('월간 영화 랭킹 성공', response.data)
            setMovieRanking(response.data);
        } catch (error){
            console.log('월간 영화 랭킹 실패', error)
        }
    }

    const handleMoviePropse = async ()=>{ // 영화 추천
        try{
            const accessToken = localStorage.getItem('accessToken');
            const config = {
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            }
            const req = {
                    year: 2023,
                    month: 6,
                    day: 1,
            }
            const response = await axios.post(`${apiUrl}/movie/proposetest`, req, config);
            console.log('영화 추천 성공', response.data)
            setMoviePropose(response.data);
        } catch (error){
            console.log('영화 추천 실패', error)
        }
    }

    const sliderSettings = {
        slidesToShow: 7,
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
      };

    const url = movieRanking?.[0]?.videos?.[0]?.url // ex) https://www.youtube.com/watch?v=6KCJ7T9yrBc
    const criteriaIndex = url?.indexOf('=') + 1;
    const extractedText = url?.substring(criteriaIndex); // 6KCJ7T9yrBc
    const targetUrl = `https://www.youtube.com/embed/${extractedText}?autoplay=1&controls=0&loop=1&playlist=${extractedText}&mute=1`;

    return(
        <>
            {/* <OpendtApi/> */}
    
            <div style={{fontSize:"30px", fontWeight:"bold", padding:"20px 0 0 20px", backgroundColor:"black", color:"white"}}>이번주 인기영화 확인해보세요</div>
            <PosterContainer1 style={{backgroundColor:'black', position:'relative'}} zIndex="2" >
                <iframe zIndex="1" width="800" height="450" src={targetUrl} title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
                <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>1</span>
            </PosterContainer1>
            <Slider {...sliderSettings}>
            {movieRanking.map((movie, index)=>{
                if(index >= 1){
                    return(
                <div key={movie.id}>
                        <MovieSpan onClick={() => (onClickModal(movieRanking[index]))}>
                            <div style={{ position: 'relative' }}>
                                <img src={movie.posterPath} height="400px" alt='포스터주소' />
                                <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>{movie.rank}</span>
                            </div>
                        </MovieSpan>
                </div>
                )}
                return null;
            })}
            </Slider>
            
            <div style={{fontSize:"30px", fontWeight:"bold", padding:"20px 0 0 20px", backgroundColor:"black", color:"white"}}>이런 영화는 어떠세요?</div>
            <div style={{display:"flex", justifyContent:"center"}}>
                {moviePropose.map((obj, index) => (
                    <div style={{display:"flex"}}>
                        {obj.movies.map((movie) => (
                            <div key={movie.movieId}>
                                <img src={movie.posterPath} height="400px" alt={`${movie.movieNm} Poster`} />
                                <div>{movie.movieNm}</div>
                            </div>
                        ))}
                    </div>
                ))}
            </div>
            
            <div onClick={() => (handleMonthMovieRanking())}>영화월간랭킹</div>
            <div onClick={() => (handleMoviePropse())}>영화추천</div>

            <MovieDetailModal
                    show={showMovieDetailModal}
                    onCloseModal={onCloseModal}
                    setShowMovieDetailModal={setShowMovieDetailModal}
                    modalData={modalData}
            />
        </>
    )
};

function OpendtApi() {
    const [showMovieDetailModal,setShowMovieDetailModal] = useState(false);
    const [modalData, setModalData] = useState(null);
    const apiUrl = process.env.REACT_APP_API_URL;

    const prevArrow = (
        <button className="slick-prev" aria-label="Previous" type="button"/>
      );
    
      const nextArrow = (
        <button className="slick-next" aria-label="Next" type="button"/>
      );
      const sliderSettings = {
        slidesToShow: 7,
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

    const onCloseModal = useCallback(() => {
        setShowMovieDetailModal(false);
    }, []);
    const onClickModal = useCallback((e) => {
        setShowMovieDetailModal(true);
        setModalData(e);
    }, []);

    const time = {
        year: 2023,
        month: 7,
        day: 1
      };

    const [postData, setPostData] = useState(null)
    useEffect(()=>{
        const fetchData = async () => {
            try{
                const response = await axios.post(`${apiUrl}/movie/weeklytest`, time)
                console.log('response:', response.data)
                setPostData(response.data)
            } catch(error){
                console.log('error',error)
                setPostData(null)
            }
        }
        fetchData();
    },[])

    // if (!opendtData) console.log('데이터를 불러오는 중입니다...')
    // const url = opendtData?.[0]?.url; // ex) https://www.youtube.com/watch?v=6KCJ7T9yrBc
    // if (url === undefined) return (
    // <>
    //     <div>url이 undefined임</div>
    // </>
    // )
    // const criteriaIndex = url.indexOf('=') + 1;
    // const extractedText = url.substring(criteriaIndex); // 6KCJ7T9yrBc
    // const targetUrl = `https://www.youtube.com/embed/${extractedText}?autoplay=1&controls=0&loop=1&playlist=${extractedText}&mute=1`;
    return (
        <OpendtApiContainer>
            <div style={{fontSize:"30px", fontWeight:"bold", padding:"20px 0 0 20px", backgroundColor:"black", color:"white"}}>이번주 인기영화 확인해보세요</div>
            {/* <PosterContainer1 style={{backgroundColor:'black', position:'relative'}} zIndex="2" onClick={() => (onClickModal(opendtData[0]))}>
                <iframe zIndex="1" width="800" height="450" src={targetUrl} title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
                <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>1</span>
            </PosterContainer1> */}
            <Slider {...sliderSettings}>
                    {postData?.map((obj, index) => {
                        if(index >= 1){
                        return (
                            <div key={index} style={{ marginRight:"20px" }}>
                                <MovieSpan onClick={() => (onClickModal(postData[index]))}>
                                    <div style={{ position: 'relative' }}>
                                        <img src={obj.posterPath} height="400px" alt='포스터주소' />
                                        <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>{obj.rank}</span>
                                    </div>
                                </MovieSpan>
                            </div>
                        )
                    }
                    return null;
                    })}
            </Slider>
            <MovieDetailModal
                    show={showMovieDetailModal}
                    onCloseModal={onCloseModal}
                    setShowMovieDetailModal={setShowMovieDetailModal}
                    modalData={modalData}
                    // postingBoardMovieId={postingBoardMovieId}
            />
        </OpendtApiContainer>
    )
}

export default Main;