import React, { Component } from 'react';
import './Profile.css';
import {AuthContext} from '../../../utils/auth'

function Profile() {
    const { state } = React.useContext(AuthContext);
    console.log(state);

        return (
            <div className="profile-container">
                <div className="container">
                    <div className="profile-info">
                        <div className="profile-avatar">
                            {state.user && ( 
                                state.user.imageUrl ? (
                                    <img src={state.user.imageUrl} alt={state.user.name}/>
                                ) : (
                                    <div className="text-avatar">
                                        <span>{state.user.name && state.user.name}</span>
                                    </div>
                                ))
                            }
                        </div>
                        <div className="profile-name">
                           {state.user && <h2>{state.user.name}</h2>}
                           {state.user && <p className="profile-email">{state.user.email}</p>}
                        </div>
                    </div>
                </div>    
            </div>
        );
}

export default Profile