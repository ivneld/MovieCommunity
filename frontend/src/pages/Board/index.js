import React, { useEffect } from 'react';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import { Body } from './styles';

const Board = () => {
    const { data : boardData } = useSWR('http://localhost:8080/boards', fetcher, {
        dedupingInterval: 100000,
    });
    if (!boardData){
        return <div>데이터가 없습니다</div>
    }
    console.log(boardData)

    return(
        <>
            <Body>
                <div>게시판</div>
                <hr/>
                {boardData.content.map((data)=>{
                    return(
                        <div>
                            {Object.entries(data).map(([key,value])=>(
                                <span>{key}:{value}&nbsp;&nbsp;&nbsp;</span>
                            ))}
                       </div>
                    )}
                )
                }
                <hr/>
                <div>pageable 일단 생략&nbsp;&nbsp;&nbsp;
                    last:{boardData.last}&nbsp;&nbsp;&nbsp;
                    totalPages:{boardData.totalPages}&nbsp;&nbsp;&nbsp;
                    size:{boardData.size}&nbsp;&nbsp;&nbsp;
                    number:{boardData.number}&nbsp;&nbsp;&nbsp;
                    {Object.entries(boardData.sort).map(([key,value])=>(
                        <span>
                            {key}:{value}&nbsp;&nbsp;&nbsp;
                        </span>
                    ))}&nbsp;&nbsp;&nbsp;
                    first:{boardData.first}&nbsp;&nbsp;&nbsp;
                    numberOfElements:{boardData.numberOfElements}&nbsp;&nbsp;&nbsp;
                    empty:{boardData.empty}&nbsp;&nbsp;&nbsp;</div>
                <hr/>
                <div>- 최신글 순이면, 시간 표시할 건지? 그러면 게시글 작성 시 시간데이터 넘겨줘야 되는지? -</div>
                <div>- 로그인을 메인 페이지에 넣을건지, 로그인페이지 따로 할건지? -</div>
                <div>- 회원가입도 마찬가지 -</div>
                <div>- pageable의 sort 데이터 빼기 -</div>
            </Body>
        </>
    )
};

export default Board;

// 타입 object, boolean, number 등
// {
//     "content":[
//                 {
//                     "id":1,
//                     "title":"123,1231242",
//                     "like":213,
//                     "movieId":318,
//                     "memberId":1,
//                     "movieNm":"궁지에 몰린 쥐는 치즈 꿈을 꾼다",
//                     "memberNm":"123"
//                 },
//                ],
//     "pageable":{
//                 "sort":{
//                         "empty":true,
//                         "unsorted":true,
//                         "sorted":false
//                        },
//                 "offset":0,
//                 "pageSize":20,
//                 "pageNumber":0,
//                 "unpaged":false,
//                 "paged":true},
//     "last":true,
//     "totalElements":5,
//     "totalPages":1,
//     "size":20,
//     "number":0,
//     "sort":{
//             "empty":true,
//             "unsorted":true,
//             "sorted":false
//             },
//     "first":true,
//     "numberOfElements":5,
//     "empty":false
// }



    // const fetchedata = async () => {
    //     const res = await axios.get("http://localhost:8080/boards");
    //     console.log(res.data);
    // }
    // useEffect(() => {
    //     fetchedata()
    // },[])