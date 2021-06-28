import React, { Component } from 'react';
import '../css/Up.css';
import s from '../media/logo.png';
import { Link, Redirect } from 'react-router-dom';
import MuiAlert from '@material-ui/lab/Alert';
import { Grid, Card, Button } from '@material-ui/core';
import { CircleToBlockLoading } from 'react-loadingg';
import HomeIcon from '@material-ui/icons/Home';


class Up extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            success: null,
            auth: false,
            Name: "",
            Email: "",
            DOB: "",
            userid: "",
            Pass: "",
            loading: false
        };
        this.cp = this.cp.bind(this);
    }

    ctxt = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    dat = (e) => {
        e.target.type = "date";
    }

    place = (e) => {
        e.target.type = "text";
    }

    // called when signup button is clicked
    async cp(e) {
        e.preventDefault();
        this.setState({ error: '', loading: true });
        let a;

        // framing the request
        try {
            const ropt = {
                method: "POST",
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: "uname=" + this.state.Name + "&email=" + this.state.Email + "&dob=" + this.state.DOB + "&username=" + this.state.userid + "&password=" + this.state.Pass
            };
            await fetch('http://13.232.9.165:9900/user/signup', ropt)
                .then(function (response) {
                    return response.json();
                })
                .then(function (obj) {
                    a = obj;
                })

            // after fetching checks for the status and set the success and error accordingly
            if (a["status"] === 201) {
                this.setState({ auth: true, success: "Registered Successfully" });
            }
            else if (a["status"] === 209) {
                this.setState({ error: "Username Not Available" });
            }
            else if (a["status"] === 204) {
                this.setState({ error: "Email already registered" });
            }
            this.setState({ loading: false })
        }
        catch (error) {
            this.setState({ error: error.message, loading: false });
        }
    }
    render() {
        let st = {
            height: '1.5px',
            background: 'rebeccapurple'
        };
        var cardStyle = {
            width: "100%",
            height: "100%",
            boxShadow: "0 4px 8px 0 rgba(0,0,0,0.2)"
        }
        return this.state.loading === true ? (
            <CircleToBlockLoading size="large" speed="1.4" />
        ) : (
            <div>
                {
                    this.state.auth ? (
                        <Redirect to="/login" />
                    ) : (
                        <div className="Up">
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
                                        <img src={s} alt="Everything in your radar!" title="Everything in your radar!" />
                                        <br /><br /><br />
                                        <div>
                                            Create a new Account
                                        </div>
                                        <hr style={st} width='500px' />
                                        <br />
                                        <form onSubmit={this.cp} className="lp" >
                                            <input type='text' className="na" placeholder="Name" name="Name" onChange={this.ctxt} required /><br /><br />
                                            <input type='email' className="em" placeholder='Email' name='Email' onChange={this.ctxt} required /><br /><br />
                                            <input className='da' onFocus={this.dat} onBlur={this.place} name='DOB' placeholder="Date of Birth" onChange={this.ctxt} required /><br /><br />
                                            <input type='text' className='usr' placeholder='Username' name='userid' onChange={this.ctxt} required /><br /><br />
                                            <input type='password' className="pw" placeholder='Password' name='Pass' onChange={this.ctxt} required /><br /><br />
                                            <input type='submit' className='tu' value='Sign Up' />
                                        </form>
                                        <br />
                                        {this.state.error ? <MuiAlert elevation={6} variant="filled" severity="error" onClose={() => { this.setState({ error: null }) }}>{this.state.error}</MuiAlert> : null}
                                        {this.state.success ? <MuiAlert elevation={6} variant="filled" severity="success" onClose={() => { this.setState({ success: null }) }} >{this.state.success}</MuiAlert> : null}
                                        <h4><Link to='/login' className='lin'>Already Registered ?</Link></h4>
                                    </Card>
                                </Grid>
                            </Grid>
                            <br />
                        </div>
                    )
                }
            </div>
        );
    }
}

export default Up;