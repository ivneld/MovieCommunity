import React, { useCallback, useState } from 'react';
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
    const [postingBoardMovieId, setPostingBoardMovieId] = useState('');

	const [searchParams, setSearchParams] = useSearchParams(); // 쿼리 스트링을 searchParams 형태로 가져오고
	const movienm = searchParams.get('movienm'); // movienm 값 변수에 저장
    const [posts, setPosts] = useState([]);

    const { data : mainData } = useSWR('http://localhost:8080', fetcher, {
        dedupingInterval: 100000,
    });

    if (movienm){ // 게시판에서 영화 제목 검색 O 경우
        const result = mainData.filter(data => data.movieNm === `${movienm}`) // 검색한 영화 제목 filter해서 해당 영화 정보만 불러오기
        return (
            <div>
                {result.length === 1 && result.map((data)=>{ // 검색한 영화가 존재 O 경우
                        return(
                            <>
                                <div>{data.movieNm}</div>
                                <div>{data.ranking}</div>
                                <div>{data.rankInten}</div>
                                <div>{data.openDt}</div>
                                <div>{data.audiAcc}</div>
                            </>
                        )}
                    )
                }
                {result.length !== 1 && // 검색한 영화가 존재 X 경우
                    <div>검색하신 영화가 존재하지 않습니다.</div>
                }
            </div>
        )
    }
    if (!mainData){
        return <div>데이터가 없거나, 불러올 수 없습니다</div>
    }   
    return(
        <>
            {!movienm && // 메인페이지 (게시판에서 영화 제목 검색 X 경우)
            <>
                <OpendtApi/>
                <hr/>
            </>
            }
        </>
    )
};

function OpendtApi() {
    const [showMovieDetailModal,setShowMovieDetailModal] = useState(false);
    const [modalData, setModalData] = useState(null);

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
    
    const onClickTest = async () => {
        const req = {
            year: 2023,
            month: 6,
            day: 1,
        }
        await axios.post('http://localhost:8080/movie/weeklytest', req)
            .then((response) => {
                console.log(response.data);
                if (response.status == 200){
                    console.log('성공')
                }
            })
            .catch((error) => {
                console.log(error)
            })
    }

    const { data : opendtData, error } = useSWR(`http://localhost:8080/movie/weekly`, fetcher, {
        dedupingInterval: 100000,
    });
    const year = 2023
    const month = 5
    const day = 1
    const { data : opendtData2, error2 } = useSWR(`http://localhost:8080/movie/propose`, fetcherAccessToken, {
        dedupingInterval: 100000,
    });
    console.log('opendtData',opendtData)
    console.log('opendtData2',opendtData2)
    if (error) console.log('데이터를 불러오는 중에 오류가 발생했습니다.')
    if (!opendtData) console.log('데이터를 불러오는 중입니다...')
    const url = opendtData?.[0]?.url; // ex) https://www.youtube.com/watch?v=6KCJ7T9yrBc
    if (url === undefined) return (
    <>
        <div>url이 undefined임</div>
        <button onClick={onClickTest}>주간랭킹테스트</button>
    </>
    )
    const criteriaIndex = url.indexOf('=') + 1;
    const extractedText = url.substring(criteriaIndex); // 6KCJ7T9yrBc
    const targetUrl = `https://www.youtube.com/embed/${extractedText}?autoplay=1&controls=0&loop=1&playlist=${extractedText}&mute=1`;
    return (
        <OpendtApiContainer>
            <div style={{fontSize:"30px", fontWeight:"bold", padding:"20px 0 0 20px", backgroundColor:"black", color:"white"}}>이번주 인기영화 확인해보세요</div>
            <PosterContainer1 style={{backgroundColor:'black', position:'relative'}} zIndex="2" onClick={() => (onClickModal(opendtData[0]))}>
                <iframe zIndex="1" width="800" height="450" src={targetUrl} title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
                <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>1</span>
            </PosterContainer1>
            <Slider {...sliderSettings}>
                    {opendtData?.map((obj, index) => {
                        if(index >= 1){
                        return (
                            <div key={index} style={{ marginRight:"20px" }}>
                                <MovieSpan onClick={() => (onClickModal(opendtData[index]))}>
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