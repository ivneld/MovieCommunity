import React, { useCallback, useState, useContext, useRef, useEffect } from 'react';
import { AuthContext } from '../../context/AuthProvider';
import useSWR, { mutate } from 'swr';
import fetcherAccessToken from '../../utils/fetcherAccessToken';
import { ChartContainer } from './styles';
import { Doughnut, Bar } from 'react-chartjs-2';
import {  Chart as ChartJS, registerables  } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
ChartJS.register(ChartDataLabels)
ChartJS.register(...registerables)

const MyPage = () => {
    const { auth, setAuth } = useContext(AuthContext);

    const accessToken = localStorage.getItem('accessToken');
    const { data : interestData, error } = useSWR(`http://localhost:8080/mypage/1/movie`, fetcherAccessToken, {
        dedupingInterval: 100000,
    });
    const { data : genreData, error2 } = useSWR(`http://localhost:8080/mypage/1/genre`, fetcherAccessToken, {
        dedupingInterval: 100000,
    });
    const moveRef1 = useRef(null)
    const moveRef2 = useRef(null)
    const moveRef3 = useRef(null)
    const moveRef4 = useRef(null)

    if (error || error2) {
        return <div>Error occurred while fetching data.</div>;
      }
    // genreData가 undefined이거나 배열이 아닌 경우에 대해 오류를 처리하고 예외 상황을 처리
      if (!genreData || !Array.isArray(genreData)) {
        return <div>Loading genre data...</div>;
      }

    // 원본 배열을 변경하지 않고 복사본 생성
    const sortedData = [...genreData];
    // cnt 값을 기준으로 내림차순 정렬
    sortedData.sort((a, b) => b.cnt - a.cnt);
    // 상위 3개의 요소 선택
    const top1 = sortedData[0];
    const top2 = sortedData[1];
    const top3 = sortedData[2];

    const colorPalette = ['rgba(75, 192, 192, 0.6)', 'rgba(192, 75, 192, 0.6)', 'rgba(192, 192, 75, 0.6)', 'rgba(75, 75, 192, 0.6)', 'rgba(192, 75, 75, 0.6)','rgba(75, 192, 75, 0.6)', 'rgba(192, 192, 192, 0.6)', 'rgba(75, 75, 75, 0.6)', 'rgba(192, 128, 128, 0.6)', 'rgba(128, 192, 128, 0.6)','rgba(128, 128, 192, 0.6)', 'rgba(128, 128, 128, 0.6)', 'rgba(192, 128, 192, 0.6)', 'rgba(128, 192, 192, 0.6)', 'rgba(75, 128, 192, 0.6)',]; // 배경 색상 배열 (총 18개)
    const backgroundColors = genreData?.map((_, index) => colorPalette[index % colorPalette.length]); // 배경 색상 반복 설정
    const totalCnt = genreData?.reduce((sum, item) => sum + item.cnt, 0);
    
    const pieData = {
        labels: genreData?.map(item => item.genreNm),
        datasets: [
          {
            label: '장르별 영화 개수',
            data: genreData?.map(item => item.cnt),
            backgroundColor: backgroundColors,
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1,
            pointBackgroundColor: 'rgba(75, 192, 192, 1)',
            pointBorderColor: '#fff',
            pointBorderWidth: 1
          },
        ],
    }
    
    const barData = {
        labels: genreData?.map((item) => item.genreNm),
        datasets: [
          {
            label: '장르별 영화 개수',
            data: genreData?.map((item) => item.cnt),
            backgroundColor: backgroundColors,
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1,
            datalabels: {
                color: 'black', // 데이터 레이블 글자색을 흰색(white)로 설정합니다.
                font: {
                  weight: 'bold', // 데이터 레이블 글자를 굵게(bold) 설정합니다.
                  size: 18, // 데이터 레이블 글자 크기를 20으로 설정합니다.
                },
                formatter: (value, context) => {
                    const cnt = genreData[context.dataIndex].cnt;
                    return `${(cnt/totalCnt*100).toFixed(0)}% ` + '(' + cnt + '편)'; // 데이터 레이블 뒤에 '편'이라는 글자를 추가합니다.
                  },
              },
          },
        ],
        
      };
    const options = {
        plugins: {
            datalabels: {
                color: '#fff',
                anchor: 'end',
                align: 'start',
                offset: 4,
                font: {
                    weight: 'bold', // genreNm의 글자 굵기
                    size: 20, // genreNm의 글자 크기
                },
                formatter: function (value, context) {
                    const genreNm = genreData[context.dataIndex].genreNm;
                    const cnt = genreData[context.dataIndex].cnt;
                    return `${genreNm}\n${(cnt/totalCnt*100).toFixed(0)}%`;
                }
                
            }
        }
    };
    const options2 = {
        maintainAspectRatio: false,
        indexAxis: 'y', // x, y 축을 뒤집기 위해 indexAxis를 'y'로 설정합니다.
        scales: {
          y: {
            beginAtZero: true,
            stepSize: 1,
            ticks: {
                // color: 'white', // 글자색을 흰색(white)로 설정합니다.
                font: {
                  size: 16, // 글자 크기를 16px로 설정합니다.
                  weight: 'bold'
                },
              },
          },
          x: {
            display: false, // x 축 숫자를 표시하지 않도록 설정합니다.
          },
        },
        plugins: {
            datalabels: {
           
              offset: 5, // 레이블과 차트 요소 간의 여백을 조정하는 값을 입력합니다.
              anchor: 'end', // 데이터 레이블을 오른쪽에 위치시킵니다.
              align: 'end', // 데이터 레이블을 오른쪽에 정렬합니다.

            },
            
          },
          
      };


    const handleScroll = (num) => {
        let targetRef;
        switch (num) {
          case 2:
            targetRef = moveRef2;
            break;
          case 3:
            targetRef = moveRef3;
            break;
          case 4:
            targetRef = moveRef4;
            break;
          default:
            targetRef = moveRef1;
            break;
        }
        if (targetRef && targetRef.current) {
            const yOffset = -80; // 원하는 만큼의 위쪽 여백을 설정합니다.
            const y = targetRef.current.getBoundingClientRect().top + window.pageYOffset + yOffset;
            window.scrollTo({ top: y, behavior: 'smooth' });
          }
    }

    return(
        <>
        {(auth) ?
        
            <>
                <div style={{display:"flex"}}>
                    <div id='1' style={{ width: '200px', position: 'fixed', zIndex:'1' }}>
                        <div style={{fontSize:"25px", fontWeight:"bold", marginBottom:"20px"}}>마이페이지</div>
                        <div onClick={() => (handleScroll(1))} style={{cursor:"pointer"}}>내 취향 분석</div>
                        <div onClick={() => (handleScroll(2))} style={{cursor:"pointer"}}>좋아요 한 영화</div>
                        <div onClick={() => (handleScroll(3))} style={{cursor:"pointer"}}>내가 쓴 글</div>
                        <div onClick={() => (handleScroll(4))} style={{cursor:"pointer"}}>프로필 수정</div>
                    </div>
                    <div id='2' style={{backgroundColor:"rgb(230,230,230)", overflowY: 'auto', flex: '1', position: 'relative',  marginLeft: '200px' }}>
                        <div ref={moveRef1}>
                            {auth}님의 취향 분석 결과
                            <ChartContainer style={{display:"flex", width:"800px"}}>
                                <Doughnut data={pieData} options={options}/>
                                <div>
                                    <div style={{fontSize:"30px", fontWeight:"bold"}}>내 취향 장르 TOP 3</div>
                                    <div style={{fontSize:"20px", fontWeight:"bold"}}>1 {top1.genreNm}</div>
                                    <div style={{fontSize:"20px", fontWeight:"bold"}}>2 {top2.genreNm}</div>
                                    <div style={{fontSize:"20px", fontWeight:"bold"}}>3 {top3.genreNm}</div>
                                </div>
                            </ChartContainer>
                            <hr/>
                            <ChartContainer style={{}}>
                                <Bar data={barData} options={options2} />
                            </ChartContainer>
                        </div>
                        <hr/>
                        <div ref={moveRef2}>
                            <div>{auth}님이 좋아요 한 영화</div>
                            <div style={{display:"flex", justifyContent:"center"}}>
                                <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                                    {interestData?.contents?.map((obj, idx) => {
                                        return(
                                            <div key={idx}>
                                                <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                                <div>{obj.movieNm}</div>
                                            </div>
                                        )
                                    })}
                                </div>
                            </div>
                        </div>
                        <hr/>
                        <div ref={moveRef3}>
                            {auth}님이 쓴 글
                            <div>아직 x</div>
                        </div>
                        <hr/>
                        <div ref={moveRef4}>
                            프로필 수정
                            <div>아직 x</div>
                        </div>
                    </div>
                </div>
            </>
            :
            <>
                로그인이 필요한 기능입니다
            </>
        }
        </>
    )
};

export default MyPage;