import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Grid, Card, CardHeader, CardContent, Typography, CardActions, Button, Menu, MenuItem, AppBar, Container, Toolbar } from '@material-ui/core';
import { CircleToBlockLoading, WindMillLoading } from 'react-loadingg';
import { withStyles } from "@material-ui/core/styles";
import "../css/prod.css";
import Badge from '@material-ui/core/Badge';
import s from '../media/logo.png';
import StarRatings from 'react-star-ratings';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import LockOpenIcon from '@material-ui/icons/LockOpen';
import PersonAddIcon from '@material-ui/icons/PersonAdd';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import FaceIcon from '@material-ui/icons/Face';


// styling material ui components

const useStyles = mS => ({
    gridContainer: {
        paddingLeft: "30px",
        paddingRight: "30px",
        
    },
    cards: {
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


// product component

class Product extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,         // if true then loading template is rendered
            subloading: true,       // it is rendered only for sub parts if true
            auth: false,
            prods: [],              // this stores all the products that are shown based on the filters
            anchor: null,
            brandanchor: null,
            category: [],           // stores the category of all items
            company: [],           // this stores the brand of all products 
            currcategory: "All Items",
            currcategoryid: -1,
            reviewanchor: null,
            review: [1, 2, 3, 4],
            pricefilter: 1,
            cartitem: 0,
            cart: []            // stores the product inside the cart
        };
        this.additems = this.additems.bind(this);
        this.sout = this.sout.bind(this);
        this.ctxt = this.ctxt.bind(this);
        this.selection = this.selection.bind(this);
        this.brandFilter = this.brandFilter.bind(this);
        this.reviewFilter = this.reviewFilter.bind(this);
        this.priceFilter = this.priceFilter.bind(this);
        this.cartHandler = this.cartHandler.bind(this);
    }

    // handling sign out
    sout = (e) => {
        localStorage.clear();
        this.setState({ auth: false, data: "" });
    }


    ctxt = (e) => {
        if (e.target.value === "" || e.target.value[0] === "-" || Number(e.target.value) === "NaN") {
            alert("Invalid Filter type");
            e.target.blur();
            e.target.value = "";
        }
        else {
            this.setState({ [e.target.name]: e.target.value });
        }
    }


    // called when cart button is clicked
    additems() {
        localStorage.setItem("cart", "[" + this.state.cart + "]");
    }


    // called when ADD TO CART button is clicked
    cartHandler(e) {
        let x = e.target.getAttribute('name');
        let ob = this.state.cart;
        ob.push(x);
        this.setState({ cart: ob });
        let a = Number(localStorage.getItem("qnt"));
        localStorage.setItem("qnt", a + 1);
        this.setState({ cartitem: a + 1 });
        return false;
    }


    // handling price filter on pressing enter
    async priceFilter(e) {
        if (e.key === 'Enter') {
            this.setState({ subloading: true });
            let prd = [], cmp = [];

            // framing the request
            try {
                const requestOptions = {
                    method: 'GET',
                };
                await fetch('http://13.232.9.165:9900/products/filter/price?price=' + this.state.pricefilter + '&categoryid=' + this.state.currcategoryid, requestOptions)
                    .then(function (response) {
                        return response.json();
                    })
                    .then(function (obj) {

                        // getting all the fetched products and their brand
                        prd = obj.map(function (val) {
                            return JSON.stringify(val);
                        })
                        cmp = obj.map(function (val) {
                            return val["company"]
                        })
                    })

                // to remove duplicate items set is used
                cmp = new Set(cmp);
                cmp = Array.from(cmp);

                // setting the relevant state variables 
                this.setState({
                    prods: prd,
                    company: cmp
                })
            }
            catch (error) {
                console.log(error);
            }

            this.setState({ subloading: false });
        }
    }


    // handling review filter
    async reviewFilter(e) {
        this.setState({
            subloading: true,
            reviewanchor: null
        });
        let prd = [], cmp = [];

        // framing the request
        try {
            const requestOptions = {
                method: 'GET',
            };
            await fetch('http://13.232.9.165:9900/products/filter/rating?review_stars=' + e.target.value + '&categoryid=' + this.state.currcategoryid, requestOptions)
                .then(function (response) {
                    return response.json();
                })
                .then(function (obj) {
                    // getting all products returned from the api
                    prd = obj.map(function (val) {
                        return JSON.stringify(val);
                    })
                    cmp = obj.map(function (val) {
                        return val["company"]
                    })
                })

                // removing duplicates
            cmp = new Set(cmp);
            cmp = Array.from(cmp);

            // setting the relevant state variable with fetched data
            this.setState({
                prods: prd,
                company: cmp
            })
        }
        catch (error) {
            console.log(error);
        }

        this.setState({ subloading: false });

    }


    // handling brand filter
    async brandFilter(e) {
        this.setState({ subloading: true, brandanchor: null });
        let prd = [], cmp = [];

        // request framing
        try {
            const requestOptions = {
                method: 'GET',
            };
            await fetch('http://13.232.9.165:9900/products/filter/company?company=' + e.target.getAttribute('value') + '&categoryid=' + this.state.currcategoryid, requestOptions)
                .then(function (response) {
                    return response.json();
                })
                .then(function (obj) {
                    // all the products returned from Brand api
                    prd = obj.map(function (val) {
                        return JSON.stringify(val);
                    })
                    cmp = obj.map(function (val) {
                        return val["company"]
                    })
                })

                // removing duplicates
            cmp = new Set(cmp);
            cmp = Array.from(cmp);

            // setting state variables
            this.setState({
                prods: prd,
                company: cmp
            })
        }
        catch (error) {
            console.log(error);
        }

        this.setState({ subloading: false });

    }

    // handling the category filter
    async selection(e) {
        let prd = [], cmp = [];
        this.setState({ subloading: true, anchor: null });
        try {
            const requestOptions = {
                method: 'GET',
            };
            if (e.target.value === -1) {
                await fetch('http://13.232.9.165:9900/products/', requestOptions)
                    .then(function (response) {
                        return response.json();
                    })
                    .then(function (obj) {
                        prd = obj.map(function (val) {
                            return JSON.stringify(val);
                        })
                        cmp = obj.map(function (val) {
                            return val["company"]
                        })
                    })
                cmp = new Set(cmp);
                cmp = Array.from(cmp);
                this.setState({
                    prods: prd,
                    company: cmp
                })
            }
            else {
                await fetch('http://13.232.9.165:9900/products/categoryid/' + e.target.value, requestOptions)
                    .then(function (response) {
                        return response.json();
                    })
                    .then(function (obj) {
                        prd = obj.map(function (val) {
                            return JSON.stringify(val);
                        })
                        cmp = obj.map(function (val) {
                            return val["company"]
                        })
                    })
                cmp = new Set(cmp);
                cmp = Array.from(cmp);
                this.setState({
                    prods: prd,
                    company: cmp
                })
            }
        }
        catch (error) {
            console.log(error);
        }
        if (e.target.value === -1) {
            this.setState({ currcategory: "All Items", currcategoryid: -1 });
        }
        else {
            for (var i = 0; i < this.state.category.length; i++) {
                let x = JSON.parse(this.state.category[i]);
                if (x["categoryid"] === e.target.value) {
                    this.setState({ currcategoryid: e.target.value, currcategory: x["categoryname"] });
                    break;
                }
            }
        }
        this.setState({ subloading: false });
    }

    async componentDidMount() {
        let c = localStorage.getItem("qnt");    // handling cart quantity
        if (c !== null) {
            this.setState({ cartitem: c });
        }
        else {
            localStorage.setItem("qnt", 0);
        }
        let b = localStorage.getItem("cart");       // handling cart items
        if (b !== null) {
            let tmp = JSON.parse(b).map((val) => {
                return JSON.stringify(val);
            });
            this.setState({ cart: tmp });
        }
        let cmp = [], cat = [];
        let a = localStorage.getItem("res");        // checking for auth
        if (a !== null) {
            this.setState({ auth: true });
        }
        let prd = [];
        this.setState({ subloading: true });
        try {
            const requestOptions = {
                method: 'GET',
            };

            // fetching all the products by default
            await fetch('http://13.232.9.165:9900/products/', requestOptions)
                .then(function (response) {
                    return response.json();
                })
                .then(function (obj) {
                    prd = obj.map(function (val) {
                        return JSON.stringify(val);
                    })
                    cmp = obj.map(function (val) {
                        return val["company"];
                    })
                })

                // api for fetching all the categories
            await fetch('http://13.232.9.165:9900/products/categories', requestOptions)
                .then(function (response) {
                    return response.json();
                })
                .then(function (obj) {
                    cat = obj.map(function (val) {
                        return JSON.stringify(val);
                    })
                })
            cmp = new Set(cmp);
            cmp = Array.from(cmp);
            this.setState({
                prods: prd,
                company: cmp,
                category: cat
            })
        }
        catch (error) {
            console.log(error);
        }
        this.setState({ subloading: false });
    }

    render() {
        const { classes } = this.props;
        return this.state.loading === true ? (
            <CircleToBlockLoading speed="1.4" size="large" />
        ) : (
            <div className="prod" >
                {this.state.auth ? (
                    <nav className="navbar">
                        <img src={s} alt="Everything in your radar!" width="50px" height="50px" title="Everything in your radar!" />
                        <h3> Radar Purchase </h3>
                        <div className="links">
                            <Button>
                                <Link to="/myprofile"><FaceIcon /> Account</Link>
                            </Button>
                            <Button onClick={this.sout} >
                                <Link to="/">
                                    <ExitToAppIcon /> Logout
                                </Link>
                            </Button>
                        </div>
                    </nav>
                ) : (
                    <nav className="navbar">
                        <img src={s} alt="Everything in your radar!" width="50px" height="50px" title="Everything in your radar!" />
                        <h3> Radar Purchase </h3>
                        <div className="links">
                            <Button>
                                <Link to="/login"><LockOpenIcon /> Login</Link>
                            </Button>
                            <Button>
                                <Link to="/signup"><PersonAddIcon />Sign Up</Link>
                            </Button>
                        </div>
                    </nav>
                )}
                <div style={{ backgroundColor: "lightgrey" }}>
                    <nav className="product-filter">

                        <h3>{this.state.currcategory}</h3>

                        <div className="sort">
                            
                            {/* buttons for adding filters */}
                            <Grid container>
                                <Grid item xs={12} sm={6} md={3}>
                                    <Button >
                                        <input style={{width: "100%"}} type="number" min="1" max="100000" placeholder="Price Filter" onChange={this.ctxt} onKeyPress={this.priceFilter} name="pricefilter" />
                                    </Button>
                                </Grid>
                                <Button aria-controls="simple-menu" aria-haspopup="true" onClick={(e) => { this.setState({ reviewanchor: e.currentTarget }) }} >
                                    Reviews
                                </Button>
                                <Menu
                                    id="simple-menu"
                                    anchorEl={this.state.reviewanchor}
                                    keepMounted
                                    open={Boolean(this.state.reviewanchor)}
                                    onClose={() => this.setState({ reviewanchor: null })}
                                >
                                    {this.state.review.map((review) => {
                                        return (
                                            <MenuItem onClick={this.reviewFilter} value={review}>{review}</MenuItem>
                                        );
                                    })}
                                </Menu>

                                <Button aria-controls="simple-menu" aria-haspopup="true" onClick={(e) => { this.setState({ brandanchor: e.currentTarget }) }} >
                                    Brand
                                </Button>
                                <Menu
                                    id="simple-menu"
                                    anchorEl={this.state.brandanchor}
                                    keepMounted
                                    open={Boolean(this.state.brandanchor)}
                                    onClose={() => this.setState({ brandanchor: null })}
                                >
                                    {this.state.company.map((brand) => {
                                        return (
                                            <MenuItem onClick={this.brandFilter} value={brand}>{brand}</MenuItem>
                                        )
                                    })}
                                </Menu>

                                <Button aria-controls="simple-menu" aria-haspopup="true" onClick={(e) => { this.setState({ anchor: e.currentTarget }) }} >
                                    Category
                                </Button>
                                <Menu
                                    id="simple-menu"
                                    anchorEl={this.state.anchor}
                                    keepMounted
                                    open={Boolean(this.state.anchor)}
                                    onClose={() => this.setState({ anchor: null })}
                                >
                                    <MenuItem onClick={this.selection} value="-1">All Items</MenuItem>
                                    {this.state.category.map((cate) => {
                                        let x = JSON.parse(cate);
                                        return (
                                            <MenuItem onClick={this.selection} value={x["categoryid"]}>{x["categoryname"]}</MenuItem>
                                        );
                                    })}
                                </Menu>
                                <Button onClick={this.additems} >
                                    <Link to="/mycart" style={{ color: "black" }} ><Badge badgeContent={this.state.cartitem} color="primary" ><ShoppingCartIcon /></Badge></Link>
                                </Button>
                            </Grid>

                        </div>

                    </nav>
                    {this.state.subloading ? (
                        <WindMillLoading speed="1.4" size="large" />
                    ) : (
                        <Grid container spacing={3} className={classes.gridContainer} >
                            
                            {/* displaying all the products returned as per user request */}
                            {this.state.prods.map((eachprod) => {                                
                                let x = JSON.parse(eachprod);
                                return (
                                    <Grid item xs={12} sm={6} md={4} >
                                        <Card className={classes.cards}>
                                            <CardHeader title={<h6><b>{x["pname"]}</b></h6>} subheader={x["company"]} />
                                            <div className="container">
                                                <img align="center" src={x["image_url"]} alt={x["pname"]} height="150px" width="150px" className="animated fadeInRight" />
                                            </div>
                                            <CardActions>
                                                <div className="content">
                                                    <div className="button">
                                                        <Link>Rs {x["price"]}</Link><Link className="cart-btn" name={eachprod} onClick={this.cartHandler}><i className="cart-icon"></i>ADD TO CART</Link>
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
                                                    starDimension="25px"
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
                    )}
                </div>
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

export default withStyles(useStyles)(Product);