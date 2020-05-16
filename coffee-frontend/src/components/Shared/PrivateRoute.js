import React from 'react';
import {
    Route,
    Redirect
  } from "react-router-dom";
import {AuthContext} from '../../utils/auth'


const PrivateRoute = ({ component: Component, authenticated, ...rest }) => (

    render() {
    if (!authenticated) {
        return (
        <Redirect
            to={{
              pathname: '/login',
            }}
          /> 
        )
    }
    
        return (
            <Route path={path}
          render={props => <Component {...props}/>} />
        )
    
    }

}
/*
const PrivateRoute = ({ component: Component, authenticated, ...rest }) => (
    <Route
      {...rest}
      render={props =>
        authenticated ? (
          <Component {...rest} {...props} />
        ) : (
          <Redirect
            to={{
              pathname: '/login',
              state: { from: props.location }
            }}
          />
        )
      }
    />
);
*/
  
export default PrivateRoute