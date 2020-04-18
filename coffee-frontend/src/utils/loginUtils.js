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
    axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem(ACCESS_TOKEN);

    return axios.get(API_BASE_URL + "/user/me")

}


