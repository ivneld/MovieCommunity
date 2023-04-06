import { Mention, SuggestionDataItem } from 'react-mentions';
import { MentionsTextarea } from './styles';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import { useParams } from 'react-router-dom';
import autosize from 'autosize';
import React, { useCallback, useEffect, useRef } from 'react';

const SearchMovie = ({chat, onChangeChat}) => {
    const { data : boardData } = useSWR('http://localhost:8080/boards', fetcher, {
        dedupingInterval: 100000,
    });
    const textareaRef = useRef<HTMLTextAreaElement>(null);
    useEffect(() => {
        if (textareaRef.current) {
          autosize(textareaRef.current);
        }
      }, []);

    return (
              <MentionsTextarea
                id="editor-chat"
                value={chat}
                onChange={onChangeChat}
                placeholder={'영화 제목 검색'}
                inputRef={textareaRef}
                allowSuggestionsAboveCursor
              >
                <Mention
                    appendSpaceOnAdd
                    trigger="@"
                    data={boardData.content?.map((v) => ({ id: v.id, display: v.movieNm })) || []}
                />
              </MentionsTextarea>
  
    )
}

export default SearchMovie;

