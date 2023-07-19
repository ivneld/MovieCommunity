import React, { useCallback, useState, useContext, useRef, useEffect } from 'react';
import { AuthContext } from '../../context/AuthProvider';
import useSWR, { mutate } from 'swr';
import fetcherAccessToken from '../../utils/fetcherAccessToken';
import axios from 'axios';
import { useNavigate } from "react-router-dom";
import jwt_decode from "jwt-decode";

import { ChartContainer } from './styles';
import { Doughnut, Bar } from 'react-chartjs-2';
import {  Chart as ChartJS, registerables  } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
ChartJS.register(ChartDataLabels)
ChartJS.register(...registerables)

const MyPage = () => {
    const navigate = useNavigate();
    const { auth, setAuth } = useContext(AuthContext);
    const apiUrl = process.env.REACT_APP_API_URL;
    const accessToken = localStorage.getItem('accessToken');
    const decodedToken = jwt_decode(accessToken);
    const memberId = decodedToken.sub
    const { data : interestData, error } = useSWR(`${apiUrl}/mypage/${memberId}/movie`, fetcherAccessToken, {
        dedupingInterval: 100000,
    });
    const { data : genreData, error2 } = useSWR(`${apiUrl}/mypage/${memberId}/genre`, fetcherAccessToken, {
        dedupingInterval: 100000,
    });
    const [ page, SetPage ] = useState(1);
    const handlePrevYear = () => {
      if(page===1){
        alert('페이지 이동 불가!')
        return
      }
      SetPage(page - 1);
    };
    const handleNextYear = () => {
      if(page===3){
        alert('페이지 이동 불가!')
        return
      }
      SetPage(page + 1);
    };
    const { data : commentData, error3 } = useSWR(`${apiUrl}/mypage/${memberId}/comment?page=${page}`, fetcherAccessToken, {
        dedupingInterval: 100000,
    });

    console.log('commentdata',commentData)
    const moveRef1 = useRef(null)
    const moveRef2 = useRef(null)
    const moveRef3 = useRef(null)
    const moveRef4 = useRef(null)

    const [comment, setComment] = useState('');
    const handleChange = (event) => {
      setComment(event.target.value);
    };

    if (error || error2 || error3) {
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
                    const genreNm = genreData[context.dataIndex]?.genreNm;
                    const cnt = genreData[context.dataIndex]?.cnt;
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

    const handleSubmit = async (event) => {
      event.preventDefault();
      try{
          const config = {
              headers:{
                  Authorization : `Bearer ${accessToken}`
              }
          }
          const req = {
              name: comment
          }
          const response = await axios.put(`${apiUrl}/mypage/1/name`, req, config);
          localStorage.setItem('name', comment)
          setAuth(comment)

          console.log('프로필 수정이 완료되었습니다.')
          alert('프로필 수정이 완료되었습니다. 다시 로그인 해주세요!')
          navigate('/logout');
      } catch(error){
          console.error('프로필 수정 실패!:', error)
      }
  };

  const handleDelete = async (commentId) => { // 댓글 삭제
      try{
          const accessToken = localStorage.getItem('accessToken');
          const response = await axios.delete(`${apiUrl}/comment`, {
              headers: {
                  Authorization: `Bearer ${accessToken}`
              },
              data: {
                  commentId: commentId
              }
          })
          mutate('${apiUrl}/mypage/1/comment'); // 코멘트 가져오기 업데이트
          console.log('댓글 삭제가 완료되었습니다.');
          alert('댓글 삭제가 완료되었습니다.')
      }
      catch(error){
          console.error('댓글 삭제 실패!', error);
      }
    }

    return(
        <>
        {(auth) ?
        
            <>
                <div style={{display:"flex"}}>
                    <div id='1' style={{ width: '250px', position: 'fixed', zIndex:'1' }}>
                        <div style={{fontSize:"30px", fontWeight:"bold", marginBottom:"20px", display:"flex", justifyContent:"center", marginTop:"50px"}}>마이페이지</div>
                        <div onClick={() => (handleScroll(1))} style={{cursor:"pointer", fontSize:"20px", display:"flex", justifyContent:"center", marginBottom:"20px"}}>내 취향 분석</div>
                        <div onClick={() => (handleScroll(2))} style={{cursor:"pointer", fontSize:"20px", display:"flex", justifyContent:"center", marginBottom:"20px"}}>좋아요 한 영화</div>
                        <div onClick={() => (handleScroll(3))} style={{cursor:"pointer", fontSize:"20px", display:"flex", justifyContent:"center", marginBottom:"20px"}}>내가 쓴 글</div>
                        <div onClick={() => (handleScroll(4))} style={{cursor:"pointer", fontSize:"20px", display:"flex", justifyContent:"center", marginBottom:"20px"}}>프로필 수정</div>
                    </div>
                    <div id='2' style={{backgroundColor:"rgb(230,230,230)", overflowY: 'auto', flex: '1', position: 'relative',  marginLeft: '250px' }}>
                        <div ref={moveRef1}>
                            <div style={{fontSize:"30px", fontWeight:"bold", marginLeft:"100px", marginTop:"50px"}}>{auth}님의 취향 분석 결과</div>
                            <ChartContainer>
                                <Doughnut data={pieData} options={options} style={{maxWidth:"450px", width:"450px", maxHeight:"450px", height:"450px"}}/>
                                <div>
                                    <div style={{fontSize:"40px", fontWeight:"bold"}}>내 취향 장르 TOP 3</div>
                                    <div style={{fontSize:"30px", fontWeight:"bold"}}>1 {top1?.genreNm}</div>
                                    <div style={{fontSize:"30px", fontWeight:"bold"}}>2 {top2?.genreNm}</div>
                                    <div style={{fontSize:"30px", fontWeight:"bold"}}>3 {top3?.genreNm}</div>
                                </div>
                            </ChartContainer>
                            <hr/>
                            <ChartContainer style={{marginRight:"100px"}}>
                                <Bar data={barData} options={options2} style={{maxWidth:"800px", width:"800px", maxHeight:"340px", height:"340px"}}/>
                            </ChartContainer>
                        </div>
                        <hr/>
                        <div ref={moveRef2}>
                            <div style={{fontSize:"30px", fontWeight:"bold", marginLeft:"100px"}}>{auth}님이 좋아요 한 영화</div>
                            <div style={{display:"flex", justifyContent:"center"}}>
                                <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                                    {interestData?.content?.map((obj, idx) => {
                                        return(
                                            <div key={idx}>
                                                <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                                <div style={{display:"flex", justifyContent:"center", fontWeight:"bold", fontSize:"20px"}}>{obj.movieNm}</div>
                                            </div>
                                        )
                                    })}
                                </div>
                            </div>
                        </div>
                        <hr/>
                        <div ref={moveRef3} style={{marginLeft:"100px"}}>
                            <span style={{fontSize:"30px", fontWeight:"bold"}}>{auth}님이 쓴 코멘트</span>
                            <hr style={{borderWidth:"2px", borderColor: "#000000", marginTop:"30px", marginBottom:"30px"}}/>
                            <div>
                              {commentData?.content?.map((obj,idx)=>{
                                  const modifiedDt = new Date(obj.modifiedDt);
                                  const year = modifiedDt.getFullYear();
                                  const month = String(modifiedDt.getMonth() + 1).padStart(2, '0');
                                  const day = String(modifiedDt.getDate()).padStart(2, '0');
                                  const formattedDt = `${year}.${month}.${day}`;
                                  return(
                                  <div key={idx}>
                                    <div style={{display:"flex"}}>
                                      <div style={{fontWeight:"bold", fontSize:"20px"}}>{obj.movieNm}</div>
                                      <div style={{fontSize:"14px", marginLeft:"15px"}}>{formattedDt}</div>
                                      <div onClick={() => (handleDelete(obj.commentId))} style={{cursor:"pointer", marginLeft:"auto", marginRight:"100px"}}>삭제하기</div>
                                    </div>
                                    <div>
                                      {obj.content}
                                    </div>
                                    <hr style={{borderColor:"#000000", marginTop:"3px", marginBottom:"3px"}}/>
                                  </div>
                                )
                              })}
                            </div>
                            <div style={{display:"flex", justifyContent:"center"}}>
                              <button onClick={handlePrevYear}>&lt;</button>
                              <span>{page}페이지</span>
                              <button onClick={handleNextYear}>&gt;</button>
                            </div>
                            <div style={{fontSize:"30px", fontWeight:"bold", marginTop:"30px"}}>{auth}님이 쓴 리뷰</div>
                            <hr style={{borderWidth:"2px", borderColor: "#000000", marginTop:"30px", marginBottom:"30px"}}/>
                            <div>ㅇㅇㅇ</div>
                        </div>
                        <hr/>
                        <div ref={moveRef4} style={{marginLeft:"100px"}}>
                            <div style={{fontSize:"30px", fontWeight:"bold", marginBottom:"20px"}}>프로필 수정</div>
                            <form onSubmit={handleSubmit}>
                              <input
                              value={comment}
                              onChange={handleChange}
                              placeholder={auth}
                              />

                              <div style={{display:"flex"}}>
                                  <button type="submit" style={{margin: "20px 0px 20px 140px"}}>완료</button>
                              </div>
                            </form>
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