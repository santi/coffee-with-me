import React from "react";
import getMeeting from "../utils/generate_meeting";
 

export default function MeetingDemo() {
  const {
    error,
    loading,
    onClickGenerateMeetingRoom
  } = getMeeting();
 
  if (error) {
    return  (
      <section className="app-error">
        <h2>{error.name}</h2>
        <p>Error message : {error.message}</p>
        <p>Error code : {error.code}</p>
      </section>)
  }
  
  if (loading) {
    return "Loading, please stand by";
  }
 
  return (
    <div>

      <button onClick={onClickGenerateMeetingRoom}>Generate meeting room</button>
    </div>
  );
}

