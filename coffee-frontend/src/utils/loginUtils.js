import { API_BASE_URL, ACCESS_TOKEN } from './constants';
import axios from 'axios'
import React, { useState, useEffect, useContext, createContext } from "react";



export const login = (loginRequest) =>  {
    return axios.post(API_BASE_URL + "/auth/login", loginRequest)
}

export const signup = (signupRequest) =>  {
    return axios.post(API_BASE_URL + "/auth/signup", signupRequest)
}



const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);
    return fetch(options.url, options)
    .then(response => {
        return response.json().then(json => {
            if(!response.ok) {
                return Promise.reject(json);
            }
            return json;
        })
});
};

export function getCurrentUser() {


    return axios.get(API_BASE_URL + "/user/me")

}


const authContext = createContext();

// Provider component that wraps your app and makes auth object ...
// ... available to any child component that calls useAuth().
export function ProvideAuth({ children }) {
  const auth = useProvideAuth();
  return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}

// Hook for child components to get the auth object ...
// ... and re-render when it changes.
export const useAuth = () => {
  return useContext(authContext);
};

// Provider hook that creates auth object and handles state
function useProvideAuth() {
  const [user, setUser] = useState(null);
  
  // Wrap any Firebase methods we want to use making sure ...
  // ... to save the user to state.
  const signin = (loginRequest) => {
    return login(loginRequest)
      .then(response => {
        localStorage.setItem(ACCESS_TOKEN, response)
        return getCurrentUser(response);
      });
  };

  const signup = (signupRequest) => {
    return signup(signupRequest)
      .then(response => {
        return response;
      });
  };

  const currentUser =  (token) => {
 
    axios.defaults.headers.common['Authorization'] = 'BEARER ' + localStorage.getItem(token);

    return axios.get(API_BASE_URL + "/user/me")
  }

  const signout = () => {
    
  };

  const sendPasswordResetEmail = email => {
    
  };

  const confirmPasswordReset = (code, password) => {
   
  };
  
  // Return the user object and auth methods
  return {
    user,
    signin,
    signup,
//    signout,
//    sendPasswordResetEmail,
//    confirmPasswordReset
  };
}
