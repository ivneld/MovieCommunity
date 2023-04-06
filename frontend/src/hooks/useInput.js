import { useCallback, useState } from 'react';


const useInput = (initialData) => {
  const [value, setValue] = useState(initialData);
  const handler = useCallback((e) => {
    setValue((e.target.value));
  }, []);
  return [value, handler, setValue];
};

// useState와 useCallback을 합친 커스텀훅

// const [email,setEmail] = useState('');
//                   +
// const onChangeEmail = useCallback((e) => {
//     setEmail(e.target.value);
// }, []);
//                   =
// const [email, onChangeEmail] = useInput('');

export default useInput;
