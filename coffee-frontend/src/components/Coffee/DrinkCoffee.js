import React, { useState, useEffect, Component } from 'react';
import Button from '@material-ui/core/Button';
import {AuthContext} from '../../utils/auth'
import { drinkCoffee } from '../../utils/coffee-utils';
import coffeeCup from '../../img/coffee_cup.svg';
import { createStyles, makeStyles } from '@material-ui/core/styles';
import { useHistory } from "react-router-dom";



const useStyles = makeStyles((theme) =>
  createStyles({
    drinkButton: {
      position: 'absolute',
      top: '52%',
      left: '33%',

    },
    image: {
        width: '100%',
    },

  }),
);

function DrinkCoffee() {
    const classes = useStyles();
    const history = useHistory();

    const handleDrink = async () => {
        try {
        const response = await drinkCoffee();
        history.push('/meeting-room')

        }
        catch (err) {

        }
    }

    const { state, dispatch } = React.useContext(AuthContext);
    console.log(state);
    return (
        <div className={classes.container}>
        <img src={coffeeCup} alt="drinkCoffee" className={classes.image}/>
        <Button
                variant="contained"
                size="small"
                color="primary"
                className={classes.drinkButton}
                onClick={()=>handleDrink()}>
                Drink Coffee
              </Button>
        </div>
    )


}

export default DrinkCoffee
