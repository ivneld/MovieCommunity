import React, { useCallback, useState } from 'react';
import { Link } from 'react-router-dom';
import { LinkContainer, MovieSpan, CategorySpan, PosterContainer1, PosterContainer2, OpendtApiContainer, TimeStamp } from './styles';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import MovieDetailModal from '../../components/MovieDetailModal';
import { useSearchParams } from 'react-router-dom';

const Ranking = () => {
    const [currentYear, setCurrentYear] = useState(2023);

    const [showMovieDetailModal,setShowMovieDetailModal] = useState(false);
    const [modalData, setModalData] = useState(null);
    const apiUrl = process.env.REACT_APP_API_URL;
    const handlePrevYear = () => {
      setCurrentYear(currentYear - 1);
    };
    const handleNextYear = () => {
      setCurrentYear(currentYear + 1);
    };

    const onCloseModal = useCallback(() => {
        setShowMovieDetailModal(false);
    }, []);
    const onClickModal = useCallback((e) => {
        setShowMovieDetailModal(true);
        setModalData(e);
    }, []);
    const { data : opendtData, error } = useSWR(`${apiUrl}/movie/year?openDt=${currentYear}`, fetcher, {
        dedupingInterval: 100000,
    });
    
    if (error) console.log('데이터를 불러오는 중에 오류가 발생했습니다.')
    if (!opendtData) console.log('데이터를 불러오는 중입니다...')
    const url = opendtData?.[0]?.url; // ex) https://www.youtube.com/watch?v=6KCJ7T9yrBc
    if (url === undefined) return <div>url이 undefined임</div>
    const criteriaIndex = url.indexOf('=') + 1;
    const extractedText = url.substring(criteriaIndex); // 6KCJ7T9yrBc
    const targetUrl = `https://www.youtube.com/embed/${extractedText}?autoplay=1&controls=0&loop=1&playlist=${extractedText}&mute=1`;
    return (
        <OpendtApiContainer>
            <TimeStamp>
                <button onClick={handlePrevYear}>&lt;</button>
                <span>{currentYear}년</span>
                <button onClick={handleNextYear}>&gt;</button>
            </TimeStamp>
            
            <div style={{display:"flex", justifyContent:"center"}}>
                <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                {opendtData?.map((obj, index) => {
                    return (
                        <div key={index} style={{ width: "25%", marginBottom:"20px" }}>
                        {/* <MovieSpan onClick={() => onClickModal(opendtData[index])}> */}
                            <Link to={`/movie/${obj.id}`} key={obj.id} state={{detail : obj.id}}>
                                <div style={{ position: "relative" }}>
                                    <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                    <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>{obj.rank}</span>
                                </div>
                            </Link>
                        {/* </MovieSpan> */}
                        </div>
                    );
                })}
                </div>
            </div>
            <MovieDetailModal
                    show={showMovieDetailModal}
                    onCloseModal={onCloseModal}
                    setShowMovieDetailModal={setShowMovieDetailModal}
                    modalData={modalData}
                    // postingBoardMovieId={postingBoardMovieId}
            />
        </OpendtApiContainer>
    )

};

export default Ranking;