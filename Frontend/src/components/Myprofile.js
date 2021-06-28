import { React, Component } from 'react';
import { CircleToBlockLoading, WindMillLoading } from 'react-loadingg';
import { AppBar, Container, Paper, Toolbar, Typography, Grid, Card, Button, Divider, ButtonBase } from '@material-ui/core';
import { Prompt, Redirect, Link } from 'react-router-dom';
import { withStyles } from "@material-ui/core/styles";
import HomeIcon from '@material-ui/icons/Home';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import s from '../media/logo.png';


// for styling material ui components

const useStyles = mS => ({
    card: {
        boxShadow: "1px 1px 10px lightblue",
        height: "100%",
        '&:hover': {
            boxShadow: "1px 2px 10px 0px black",
        }
    },
    footer: {
        position: "static",
        backgroundColor: "rgb(128, 103, 103)"
    }
});



class Myprofile extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            name: "",
            dob: "",
            email: "",
            authtoken: false,
            subloading: true,
            logout: false,
            orders: []
        }
        this.sout = this.sout.bind(this);
    }


    // called when the component loads
    async componentDidMount() {
        let a = localStorage.getItem("res");
        if (a !== null) {               // checks for authentication
            a = JSON.parse(a);
            this.setState({ authtoken: true });
            this.setState({ loading: false });
            let ord = [];
            this.setState({ name: a["uname"], dob: a["dob"], email: a["email"]});       // sets the user details 
            try {
                const ropt = {
                    method: "GET",
                    headers: { 'Authorization': 'Bearer '+a["token"]}   
                }
                await fetch("http://13.232.9.165:9900/purchase/orderHistory?uid="+a["uid"], ropt)           // fetches order history
                    .then(function (response) {
                        return response.json();
                    })
                    .then(function (obj) {
                        console.log(obj);
                        ord = obj.map(function (val) {
                            return JSON.stringify(val);
                        })
                    })
                this.setState({
                    orders: ord
                });
            }
            catch (error) {
                console.log(error);
            }
    
        }
        
        this.setState({ loading: false });

        this.setState({ subloading: false });
    }


    // handling signout
    sout = (e) => {
        localStorage.clear();
        this.setState({ authtoken: false, logout: true });
    }


    render() {
        const { classes } = this.props;
        var cardStyle = {
            width: "45vw",
            height: "auto",
            boxShadow: "0 4px 8px 0 rgba(0,0,0,0.2)",
            textAlign: "center"
        }
        return this.state.loading ? (
            <CircleToBlockLoading speed="1.4" size="large" />       
        ) : (
            <div className="myprofile">
                {this.state.authtoken ? (
                    <div>
                        <nav className="navbar">
                            <img src={s} alt="Future awaits you !!" width="50px" height="50px" title="Everything in your radar!" />
                            <h3> Radar Purchase </h3>
                            <div className="links">
                                <Button>
                                    <Link to="/">
                                        <HomeIcon /> Home
                                    </Link>
                                </Button>
                                <Button onClick={this.sout}>
                                    <Link to="/">
                                        <ExitToAppIcon /> Logout
                                    </Link>
                                </Button>
                            </div>
                        </nav><br />
                        {this.state.subloading ? (
                            <WindMillLoading speed="1.4" size="large" />
                        ) : (
                            <div>
                                <Grid container justify="center" >
                                    <AccountCircleIcon />
                                </Grid>
                                <h4 align="center" >Profile</h4>
                                <Grid container justify="center">
                                    <Card style={cardStyle}>
                                        <Grid item>
                                            Name : {this.state.name}<br /><Divider variant="middle" />
                                            Dob : {this.state.dob}<br /><Divider variant="middle" />
                                            Email : {this.state.email}<br /><Divider variant="middle" />
                                        </Grid>
                                    </Card>
                                </Grid>
                                <br />
                                <Divider variant="middle" />
                                <br />
                                <h4>Order History</h4>
                                <Paper elevation={24}>
                                    {this.state.orders.length === 0 ? (
                                        <Grid container justify="center">
                                            <Grid item xs={12} sm={12} md={12}>
                                                No orders
                                            </Grid>
                                        </Grid>
                                    ) : (
                                        <Grid container justify="center">
                                            <Grid item xs={12} sm={12} md={12} style={{ textAlign: "center" }} >
                                                {this.state.orders.map((val) => {
                                                    let x = JSON.parse(val);
                                                    
                                                    return (                                            
                                                        <Paper elevation={24}>
                                                            
                                                            Purchased on : {new Date(Number(x["time"])).toLocaleString()}
                                                            <br />
                                                            <Divider variant="middle" />
                                                            <br />
                                                            <Grid container spacing={1} style={{ paddingLeft: "10px", paddingRight: "10px" }} >
                                                                {x["product"].map((eachprod) => {
                                                                    return (
                                                                        <Grid item xs={6} md={4} sm={3} >
                                                                            <Card className={classes.card} >
                                                                                <ButtonBase>
                                                                                <img src={eachprod["image_url"]} width="50px" height="50px" alt={eachprod["pname"]} /><br/>
                                                                                <br/><small>{eachprod["pname"]}</small>
                                                                                </ButtonBase>
                                                                            </Card>
                                                                        </Grid>

                                                                    );
                                                                })}
                                                            </Grid>
                                                            <br />
                                                            <Divider variant="middle" />
                                                            <br />
                                                        </Paper>


                                                    );
                                                })}
                                            </Grid>
                                        </Grid>
                                    )}

                                </Paper>
                            </div>
                        )}
                        <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
                        <AppBar className={classes.footer}>
                            <Container maxWidth="xl">
                                <Toolbar>
                                    <Typography variant="body2" color="inherit">
                                        © 2021 Hupr
                                    </Typography>
                                </Toolbar>
                            </Container>
                        </AppBar>
                    </div>
                ) : (
                    <div>
                        {!this.state.logout ? (
                            <Prompt message="You are not logged in!" />
                        ) : (
                            <h2>Good Bye</h2>
                        )}
                        <Redirect to="/login" />
                    </div>
                )
                }

            </div>
        );
    }
}

export default withStyles(useStyles)(Myprofile);