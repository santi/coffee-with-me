import './Signup.scss';
import React, { useState, useEffect, Component } from 'react';
import TextField from '@material-ui/core/TextField';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import { signup } from '../../../utils/loginUtils';
import { Link, Redirect } from 'react-router-dom'
 import fbLogo from '../../../img/fb-logo.png';
import googleLogo from '../../../img/google-logo.png';
import Alert from 'react-s-alert';
import Button from '@material-ui/core/Button';

const useStylesLogin = makeStyles((theme) =>
  createStyles({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
      margin: `${theme.spacing(0)} auto`
    },
    loginBtn: {
      marginTop: theme.spacing(2),
      flexGrow: 1
    },
    card: {
      marginTop: theme.spacing(10),
      width: '100%',
    }

  }),
);


function SignupForm() {
    const classes = useStylesLogin();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');

    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [helperText, setHelperText] = useState('');
    const [error, setError] = useState(false);
  
    useEffect(() => {
      if (email.trim() && password.trim()) {
        setIsButtonDisabled(false);
      } else {
        setIsButtonDisabled(true);
      }
    }, [email, password]);
  
    const handleSignup = async () => {
      if (password.length > 5) {
        setError(false);
        setHelperText('Signed up successfully');
        const signuprequest = {email, password, name}
        const signupResponse = await signup(signuprequest)
      } else {
        setError(true);
        setHelperText('Password is not secure enough, must be at least six characters')
      }
    };
    // Handle enter press to login
    const handleKeyPress = (e) => {
      if (e.keyCode === 13 || e.which === 13) {
        isButtonDisabled || handleSignup();
      }
    };
  
    return (
        <div className={classes.container}>
          <Card className={classes.card}>
            <CardContent>
              <div>
              <TextField
                  error={error}
                  fullWidth
                  id="name"
                  type="name"
                  label="Name"
                  placeholder="Name"
                  margin="normal"
                  onChange={(e)=>setName(e.target.value)}
                  onKeyPress={(e)=>handleKeyPress(e)}
                />
                <TextField
                  error={error}
                  fullWidth
                  id="username"
                  type="email"
                  label="Email"
                  placeholder="Email"
                  margin="normal"
                  onChange={(e)=>setEmail(e.target.value)}
                  onKeyPress={(e)=>handleKeyPress(e)}
                />
                <TextField
                  error={error}
                  fullWidth
                  id="password"
                  type="password"
                  label="Password"
                  placeholder="Password"
                  margin="normal"
                  helperText={helperText}
                  onChange={(e)=>setPassword(e.target.value)}
                  onKeyPress={(e)=>handleKeyPress(e)}
                />
                
              </div>
            </CardContent>
            <CardActions>
              <Button
                variant="contained"
                size="large"
                color="secondary"
                className={classes.loginBtn}
                onClick={()=>handleSignup()}
                disabled={isButtonDisabled}>
                Sign Up
              </Button>
            </CardActions>
            <span className="signup-link">Already have a user? <Link to="/login">Log in!</Link></span>

          </Card>
        </div>
    );
}

export default SignupForm