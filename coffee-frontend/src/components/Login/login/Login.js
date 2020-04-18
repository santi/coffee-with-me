import React, { useState, useEffect, Component } from 'react';
import TextField from '@material-ui/core/TextField';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import './Login.scss';
import { GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL, ACCESS_TOKEN } from '../../../utils/constants';
import { login } from '../../../utils/loginUtils';
import { Link, Redirect } from 'react-router-dom'
 import fbLogo from '../../../img/fb-logo.png';
import googleLogo from '../../../img/google-logo.png';
import Button from '@material-ui/core/Button';
import useRouter from "use-react-router";





function Login() {

    return (
        <div className="login-wrapper">
            <SocialLogin />
            <LoginForm />
        </div>
    ) 
}


const useStyles = makeStyles((theme) =>
  createStyles({
    button: {
      marginBottom: theme.spacing(2),
    },

  }),
);


function SocialLogin() {
    const classes = useStyles();



        return (
            <div className="social-login">
            <Button
        variant="contained"
        color="primary"
        href={FACEBOOK_AUTH_URL}
        className={classes.button}
            >
        Log in with Facebook
      </Button>
      <Button
        variant="contained"
        color="secondary"
        href={GOOGLE_AUTH_URL}
        className="social-login-button"

            >
        Log in with google
      </Button>
            </div>
        );
    
}

function FacebookImage() {
    return (
        <img src={fbLogo} alt="Facebook" className="social-btn facebook"/>
    )
}


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


function LoginForm() {

    const classes = useStylesLogin();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [helperText, setHelperText] = useState('');
    const [error, setError] = useState(false);
    const {history} = useRouter();

    useEffect(() => {
      if (email.trim() && password.trim()) {
        setIsButtonDisabled(false);
      } else {
        setIsButtonDisabled(true);
      }
    }, [email, password]);
  
    const handleLogin = async () => {
      if (email === 'test@test.no' && password === 'test') {
        setError(false);
        setHelperText('Login Successfully');
        const loginRequest = {email, password}
        const loginResponse = await login(loginRequest)
        console.log(loginResponse)
        localStorage.setItem(ACCESS_TOKEN, loginResponse.data)
        history.push('/')


      } else {
        setError(true);
        setHelperText('Incorrect username or password')
      }
    };
    // Handle enter press to login
    const handleKeyPress = (e) => {
      if (e.keyCode === 13 || e.which === 13) {
        isButtonDisabled || handleLogin();
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
                  id="username"
                  type="email"
                  label="Username"
                  placeholder="Username"
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
                onClick={()=>handleLogin()}
                disabled={isButtonDisabled}>
                Login
              </Button>
            </CardActions>
            <span className="signup-link">New user? <Link to="/signup">Sign up!</Link></span>

          </Card>
        </div>
    );
}

export default Login
