import styled from '@emotion/styled';
import { MentionsInput } from 'react-mentions';

export const MentionsTextarea = styled(MentionsInput)`
  font-family: Slack-Lato, appleLogo, sans-serif;
  font-size: 15px;
  padding: 8px 9px;
  width: 20%;
  margin-left: auto;
  margin-right: auto;
  background: white;

  & textarea {
    height: 44px;
    padding: 9px 10px !important;
    outline: none !important;
    border-radius: 4px !important;
    resize: none !important;
    line-height: 22px;
    border: none;
  }
`;
