import React, { Component } from 'react';
import '../css/login.css';
import s from '../media/logo.png';
import { Link, Redirect } from 'react-router-dom';
import { Button, Card, Grid } from '@material-ui/core'
import { CircleToBlockLoading } from 'react-loadingg';
import HomeIcon from '@material-ui/icons/Home';
import MuiAlert from '@material-ui/lab/Alert';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      username: "",
      password: "",
      loading: false,
      auth: false
    };
    this.cp = this.cp.bind(this);
  }


  ctxt = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  }

  // called when login button is clicked

  async cp(e) {
    e.preventDefault();

    this.setState({ error: '', loading: true });
    let a, status;

    // framing the request for login
    try {
      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: "username=" + this.state.username + "&password=" + this.state.password,

      };
      await fetch('http://13.232.9.165:9900/user/authenticate', requestOptions)
        .then(function (response) {
          status = response.status;
          return response.json();
        })
        .then(function (obj) {
          a = obj;            
        })
      if (status === 200) {       
        this.setState({ auth: true });
        localStorage.setItem("res", JSON.stringify(a));
      }
      else {
        this.setState({ error: "Invalid Credentials" });
      }
      this.setState({ loading: false });
    }
    catch (error) {
      this.setState({ error: error.message, loading: false });
    }
  }

  render() {
    var cardStyle = {
      width: "100%",
      height: "100%",
      boxShadow: "0 4px 8px 0 rgba(0,0,0,0.2)"
    }
    return this.state.loading === true ? (
      <CircleToBlockLoading speed="1.4" size="large" />
    ) : (
      <div>
        {
          this.state.auth ? (
            <Redirect to="/products" />
          ) : (
            <div className="Login">
              <nav className="navbar">
                <h3>Radar Purchase </h3>
                <div className="links">
                  <Button>
                    <Link to="/">
                      <HomeIcon /> Home
                    </Link>
                  </Button>
                </div>
              </nav>
              <br />
              <Grid container justify="center">
                <Grid item>
                  <Card className="Logincard" style={cardStyle} >
                    <br /><br />
                    <img src={s} alt="Everything in your radar" title="Everything in your radar!" />
                    <br /><br /><br />
                    <div>
                      Login
                    </div>
                    <hr width='450px' color="Cream" />
                    <br />
                    <form className="Fo" onSubmit={this.cp}>
                      <input name="username" type='text' className="us" onChange={this.ctxt} placeholder="Username" required /><br /><br />
                      <input name="password" type='password' className="pa" onChange={this.ctxt} placeholder="Password" required /><br /><br />
                      <input type='submit' className='bu' value='Login' /><br />
                    </form>
                    {this.state.error ? <MuiAlert elevation={6} variant="filled" severity="error" onClose={() => { this.setState({error: null}) }} >{this.state.error}</MuiAlert> : null}
                    <br />
                    <h4>No account ? <Link to="/signup">Sign Up</Link></h4>
                    <br />
                  </Card>
                </Grid>
              </Grid>
            </div>
          )
        }
      </div>
    );
  }
}

export default Login;