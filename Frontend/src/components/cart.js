import { React, Component } from 'react';
import { CircleToBlockLoading, WindMillLoading } from 'react-loadingg';
import { AppBar, Container, Toolbar, CardActions, Card, CardHeader, CardContent, Typography, Button, Grid } from '@material-ui/core';
import StarRatings from 'react-star-ratings';
import { Prompt, Redirect, Link } from 'react-router-dom';
import HomeIcon from '@material-ui/icons/Home';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import { withStyles } from "@material-ui/core/styles";
import SentimentSatisfiedIcon from '@material-ui/icons/SentimentSatisfied';
import LocalMallIcon from '@material-ui/icons/LocalMall';
import MuiAlert from '@material-ui/lab/Alert';
import s from '../media/logo.png';
import "../css/cart.css";


// styling material ui components

const useStyles = mS => ({
    ob: {
        textAlign: "center"
    },
    card: {
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


// mycart component but also checks authentication


class Cart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            success: null,
            loading: true,          // loading is for full component
            subloading: true,       // subloading is for various sub parts of the page if needed to reload
            auth: false,
            allitems: [],
            totalCost: 0
        }
        this.bookOrder = this.bookOrder.bind(this);
        this.sout = this.sout.bind(this);
        this.removeHandler = this.removeHandler.bind(this);
    }


    // function for booking order

    async bookOrder() {
        let tmp = this.state.allitems.map((val) => {
            return JSON.parse(val)["pid"];
        });
        let c = localStorage.getItem("res");
        let a = { "uid": JSON.parse(c)["uid"], "pids": tmp };
        this.setState({ subloading: true });                    // subloading is on
        try {
            const requestOptions = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(a)
            };
            console.log(requestOptions["body"]);
            await fetch('http://13.232.9.165:9900/purchase/userPurchase', requestOptions)       // fetches the api
                .then(function (response) {
                    return response.json();
                })
                .then(function (obj) {

                    a = obj["status"];
                })
            if (a === 201) {
                localStorage.removeItem("cart");
                localStorage.removeItem("qnt");
                this.setState({ allitems: [], success: "Order Booked" });       // order booked message shown
            }

        }
        catch (er) {
            this.setState({ error: er });
        }
        this.setState({ subloading: false });       //subloading template is turned off as and when the order is booked
    }

    removeHandler(e) {
        this.setState({ loading: true, subloading: true });         // subloading and loading is on
        let a = localStorage.getItem("cart");
        let tmp = 0;
        let all = JSON.parse(a);
        all = all.filter(function (val) {
            return e.target.name !== val["pid"].toString();
        });
        all = all.map((val) => {
            tmp += Number(val["price"]);
            return JSON.stringify(val);
        });
        localStorage.setItem("cart", "[" + all + "]");
        localStorage.setItem("qnt", all.length);
        a = localStorage.getItem("cart");
        all = [];
        if (a !== null) {
            all = JSON.parse(a).map((val) => {
                return JSON.stringify(val);
            });
        }
        this.setState({
            allitems: all,
            totalCost: tmp
        });
        this.setState({ loading: false, subloading: false });       // subloading and loading is off
    }

    // this is called everytime the component loads

    componentDidMount() {
        let c = localStorage.getItem("res");
        if (c !== null) {
            this.setState({ auth: true });
        }
        let a = localStorage.getItem("cart");
        let all = [], tmp = 0;
        if (a !== null) {               //fetches all the cart items
            all = JSON.parse(a).map((val) => {
                tmp += Number(val["price"])
                return JSON.stringify(val);
            });
        }
        this.setState({
            allitems: all,
            totalCost: tmp
        });
        this.setState({ loading: false, subloading: false });
    }


    // signout clears everything from local storage

    sout = (e) => {
        localStorage.clear();
        this.setState({ authtoken: false, logout: true });
    }

    render() {
        const w = window.innerWidth;
        const { classes } = this.props;
        let st = {
            height: '1.5px',
            background: 'rebeccapurple'
        };
        return this.state.loading ? (
            <CircleToBlockLoading speed="1.4" size="large" />
        ) : (
            <div className="cart">
                {this.state.auth ? (
                    <div>
                        <div>
                            <nav className="navbar">
                                <img src={s} alt="Future awaits you !!" width="50px" height="50px" title="Everything in your radar!" />
                                <h3> Radar Purchase </h3>
                                <div className="links" >
                                    <Button>
                                        <Link to="/">
                                            <HomeIcon /> Home
                                        </Link>
                                    </Button>
                                    <Button onClick={this.sout} >
                                        <Link to="/">
                                            <ExitToAppIcon /> Logout
                                        </Link>
                                    </Button>
                                </div>
                            </nav>
                        </div>
                        {this.state.subloading ? (
                            <WindMillLoading speed="1.4" size="large" />
                        ) : (
                            <div>
                                {this.state.allitems.length === 0 ? (
                                    <Card style={{ height: "40vw" }}>
                                        <br />
                                        <p style={{ marginLeft: (w / 2) - 55 }}>Cart Empty!</p>
                                        <SentimentSatisfiedIcon style={{ marginLeft: (w / 2) - 25 }} />
                                    </Card>

                                ) : (
                                    <div>
                                        <Grid container spacing={2} >
                                            {this.state.allitems.map((eachitem) => {
                                                let x = JSON.parse(eachitem);
                                                return (
                                                    <Grid item xs={12} sm={6} md={4} >
                                                        <Card className={classes.card}>
                                                            <CardHeader title={<h6><b>{x["pname"]}</b></h6>} subheader={x["company"]} />
                                                            <div className="container">
                                                                <img src={x["image_url"]} alt={x["pname"]} height="150px" width="150px" className="animated fadeInRight" />
                                                            </div>
                                                            <CardActions>
                                                                <div className="content">
                                                                    <div className="button">
                                                                        <Link>Rs {x["price"]}</Link><Link className="cart-btn" name={x["pid"]} onClick={this.removeHandler} ><i className="cart-icon"></i>Remove</Link>
                                                                    </div>
                                                                </div>
                                                            </CardActions>
                                                            <CardContent>
                                                                <Typography variant="body2" color="textSecondary" component="p">
                                                                    {x["description"]}
                                                                </Typography>
                                                            </CardContent>
                                                            <CardContent>
                                                                <StarRatings
                                                                    rating={x["user_review_stars"]}
                                                                    starRatedColor="green"
                                                                    starEmptyColor="grey"
                                                                    starDimension="30px"
                                                                    numberOfStars={5}
                                                                    starSpacing="3px"
                                                                    name='rating'
                                                                />
                                                            </CardContent>
                                                        </Card>
                                                    </Grid>
                                                );
                                            })}
                                        </Grid>
                                        <hr style={st} width="400" />
                                        <Grid container justify="center">
                                            <Grid item>
                                                <Button className={classes.ob} variant="contained" startIcon={<LocalMallIcon />} onClick={this.bookOrder} >
                                                    Total Cost: Rs. {this.state.totalCost}
                                                    <br />Place Order
                                                </Button>
                                            </Grid>
                                        </Grid>
                                    </div>
                                )}
                            </div>
                        )}
                        {this.state.error ? <MuiAlert elevation={6} variant="filled" severity="error" onClose={() => { this.setState({ error: null }) }} >{this.state.error}</MuiAlert> : null}
                        {this.state.success ? <MuiAlert elevation={6} variant="filled" severity="success" onClose={() => { this.setState({ success: null }) }} >{this.state.success}</MuiAlert> : null}
                    </div>
                ) : (
                    <div>
                        <Prompt message="You are not logged in!" />
                        <Redirect to="/login" />
                    </div>
                )
                }
                <br />
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
        );
    }
}

export default withStyles(useStyles)(Cart);