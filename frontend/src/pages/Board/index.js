import React, { useCallback } from 'react';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import { Body, LinkContainer, Label, Form, Input, Button, LikeSpan } from './styles';
import useInput from '../../hooks/useInput';
import { Link, useNavigate } from 'react-router-dom';


const Board = () => {
    const [title, onChangeTitle] = useInput('');
    const navigate = useNavigate();

    const { data : boardData } = useSWR('http://localhost:8080/boards', fetcher, {
        dedupingInterval: 100000,
    });
    const onSubmit = useCallback(
        (e) => {
          e.preventDefault();
          navigate(`/?movienm=${title}`);
        },
        [title],
      );

    if (!boardData){
        return <div>데이터가 없거나, 불러올 수 없습니다</div>
    }

    return(
        <>
            <Body>
                {/* <SearchMovie chat={chat} onChangeChat={onChangeChat}/> */}
                
                <Form onSubmit={onSubmit}>
                    <Label>
                        <Input type="text" placeholder='영화 제목 검색' value={title} onChange={onChangeTitle} />
                        <Button type="submit">검색</Button>
                    </Label>
                </Form>
                <hr/>
                {boardData.content.map((data)=>{
                        return(
                            <LinkContainer>
                                <Link to={`/boards/${data.id}/comment`} key={data.id} state={{detail : data}}>{data.title}</Link>
                                <LikeSpan>&nbsp;추천 {data.like}</LikeSpan>
                            </LinkContainer>
                        )}
                    )
                }
                {/* <hr/>
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
                    empty:{boardData.empty}&nbsp;&nbsp;&nbsp;</div> */}
            </Body>
        </>
    )
};

export default Board;