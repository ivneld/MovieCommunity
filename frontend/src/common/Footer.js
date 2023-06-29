import "./footer.css"

function Footer() {
	return (
		// <footer className="py-3 bg-dark text-light footer">
		<footer className="py-3 footer" style={{ backgroundColor: 'rgb(145, 205, 220)', fontSize:"18px"}}>
          <div className="container text-center">
            <p>
              <small>Copyright &copy;SPRING Movies</small>
            </p>
          </div>
        </footer>
	);
}

export default Footer;