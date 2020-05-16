import { API_BASE_URL } from './constants';
import axios from 'axios'



export const drinkCoffee = () =>  {

    return axios.post(API_BASE_URL + "/meetings/")
}