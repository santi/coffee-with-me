import React, { useEffect, useState } from 'react';
import {
  Route,
  Switch,
  Redirect
} from 'react-router-dom';
import Login from '../Login/login/Login'
import Signup from '../Login/signup/Signup'
import { getCurrentUser } from '../../utils/loginUtils'; 
import Profile from '../Login/profile/Profile'
import OAuth2RedirectHandler from '../Login/oauth2/OAuth2RedirectHandler'
import DrinkCoffee from "../Coffee/DrinkCoffee"
import {AuthContext} from '../../../utils/auth'


function Content() {
    const [currentUser, setCurrentUser] = useState('')
    const {state} = React.useContext(AuthContext);
    useEffect(() => {
      const currentUser = getCurrentUser();
      setCurrentUser(currentUser);
  }, []);

    return (
        <div>
          <Switch>
            <Route exact path="/" component={Login}></Route>           
            <Route path="/profile" currentUser={currentUser}
              component={Profile}></Route>
            <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route>  
            <Route path="/signup" component={Signup}></Route>   
            <Route  path="/login" component={Login}></Route>  
            <Route path="/drink" render={() => (
              state.authenticated ? <DrinkCoffee /> : <Redirect to="/" />
            )}></Route>    


          </Switch>
          </div>
    );
  }

export default Content;
