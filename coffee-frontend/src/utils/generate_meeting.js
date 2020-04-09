import { useState, useEffect } from "react";

import {
  generateMeetingRoom
} from "./request_coffee_meeting";
//import all the function created to manage the meeting room


export default function usePushNotifications() {
   const [error, setError] = useState(null);
  //to manage errors
  const [loading, setLoading] = useState(false);
  //to manage async actions

  const meetingRoom = useState(null)



  const onClickGenerateMeetingRoom = async () => {
    setLoading(true);
    setError(false);
    generateMeetingRoom()
    .then(function(response) {
      console.log(response)

      setLoading(false);
    })
    .catch(err => {
      setLoading(false);
      setError(err);
    });
  }

  /**
   * returns all the stuff needed by a Component
   */
  return {
    onClickGenerateMeetingRoom,
    error,
    loading
  };
}
