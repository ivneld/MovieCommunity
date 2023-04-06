import React, { useCallback } from 'react';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import { Body, LinkContainer, Label, Form, Input, Button } from './styles';
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
        return <div>데이터가 없습니다</div>
    }

    return(
        <>
            <Body>
                {/* <SearchMovie chat={chat} onChangeChat={onChangeChat}/> */}
                
                <Form onSubmit={onSubmit}>
                    <Label>
                    <div>
                        <Input type="text" placeholder='영화 제목 검색' value={title} onChange={onChangeTitle} />
                    </div>
                    </Label>
                    <Button type="submit">검색</Button>
                </Form>
                <hr/>

                {boardData.content.map((data)=>{
                        return(
                            <LinkContainer>
                                <Link to={`/boards/${data.id}/comment`} key={data.id} state={{detail : data}}>게시글 : {data.title}</Link>
                            </LinkContainer>
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
                <div>- pageable의 sort 데이터 빼기 -</div>
            </Body>
        </>
    )
};

export default Board;