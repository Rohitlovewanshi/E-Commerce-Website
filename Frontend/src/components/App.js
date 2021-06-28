import React, { Component } from 'react';
import { Route, Switch, Redirect } from 'react-router-dom';
import { CircleLoading } from 'react-loadingg';
import Up from './signup';
import Login from './login';
import Product from './products';
import Myprofile from './Myprofile';
import Mycart from './cart';


function SignupRoute({ component: Component, ...rest }) {

  //signup route checking if authenticated then routed to products else routed to /products

  let a = localStorage.getItem("res");
  if (a !== null) {
    return (
      <Route
        {...rest}
        render={props =>
          <Redirect to="/products" />
        }
      />
    );
  }
  else {
    return (
      <Route
        {...rest}
        render={props =>
          <Component/>
        }
      />
    );
  }

}

function LoginRoute({ component: Component, ...rest }) {

  //login route checking if authenticated then routed to /products else routed to /login

  let a = localStorage.getItem("res");
  if (a !== null) {
    return (
      <Route
        {...rest}
        render={props =>
          <Redirect to="/products" />
        }
      />
    );
  }
  else {
    return (
      <Route
        {...rest}
        render={props =>
          <Component/>
        }
      />
    );
  }

}

function HomeRoute({ component: Component, ...rest }) {
  //default route set to /products

  return (
    <Route
      {...rest}
      render={props =>
        <Redirect to="/products"/>
      }
    />
  );
}

// /products page component

function ProductRoute({ component: Component, ...rest }) {
  return (
    <Route
      {...rest}
      render={props =>
        <Component/>
      }
    />
  );
}


// First Component to load when accessed, handles routing

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      auth: false,
      loading: false,
      logout: false
    };
  }
  render() {
    return this.state.loading === true ? (
      <CircleLoading speed="1.4" size="large" />      //loading button
    ) : (
      <div className="App">

        {/* All the routes are mentioned here */}

        <Switch>
          <LoginRoute path="/login" component={Login} />
          <SignupRoute path="/signup" component={Up} />
          <ProductRoute path="/products" component={Product} />
          <Route path="/mycart" component={Mycart} />
          <Route path="/myprofile" component={Myprofile} />
          <HomeRoute path="/" component={Product} />
        </Switch>
      </div>
    );
  }
}

export default App;