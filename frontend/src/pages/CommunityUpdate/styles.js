import styled from '@emotion/styled';

//여기부터 CommunityPostModal
export const InputArea = styled.input`
  width: 100%;
  height: 30px;
  max-height: 30px;
`;

export const InputTextArea = styled.input`
  width: 100%;
  height: 300px;
  max-height: 300px;
`;

export const Form = styled.form`
  margin: 0 auto;
  width: 700px;
  max-width: 700px;
`;

export const Button = styled.button`
  margin-bottom: 12px;

  color: #fff;
  background-color: #4a154b;
  border: none;
  font-size: 18px;
  font-weight: 900;
  height: 35px;
  min-width: 60px;
  transition: all 80ms linear;
  user-select: none;
  outline: none;
  cursor: pointer;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.3);
  &:hover {
    background-color: rgba(74, 21, 75, 0.9);
    border: none;
  }
  &:focus {
    --saf-0: rgba(var(--sk_highlight, 18, 100, 163), 1);
    box-shadow: 0 0 0 1px var(--saf-0), 0 0 0 5px rgba(29, 155, 209, 0.3);
  }
`;