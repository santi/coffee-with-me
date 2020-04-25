import { API_BASE_URL, ACCESS_TOKEN } from './constants';
import axios from 'axios'
import React, { useState, useEffect, useContext, createContext } from "react";



export const login = (loginRequest) =>  {

    return axios.post(API_BASE_URL + "/auth/login", loginRequest)
}

export const signup = (signupRequest) =>  {
    return axios.post(API_BASE_URL + "/auth/signup", signupRequest)
}


export function getCurrentUser() {
    axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem(ACCESS_TOKEN);

    return axios.get(API_BASE_URL + "/user/me")

}


