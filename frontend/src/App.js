import { BrowserRouter } from "react-router-dom";

import Header from "./common/Header"
import Nav from "./common/Nav"
import Main from "./common/Main"
import Footer from "./common/Footer"
import AuthProvider from "./context/AuthProvider"
import HttpHeadersProvider from "./context/HttpHeadersProvider"
import "./style.css"

function App() {

  return (
    <div>
      <BrowserRouter>
        
        <Header />
        
        <AuthProvider>
          <HttpHeadersProvider>
            <Nav />
            <Main />
          </HttpHeadersProvider>
        </AuthProvider>

        <Footer />

      </BrowserRouter>
    </div>
  );
}

export default App;
