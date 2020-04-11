const axios = require('axios');



function generateMeetingRoom() {
    axios.post('https://api.join.skype.com/v1/meetnow/createjoinlinkguest', {
    title: 'Have a coffee with me',
  })
  .then(function (response) {
    console.log(response);
    return response;
  })
  .catch(function (error) {
    console.log(error);
    return error;
  });
}

export {
    generateMeetingRoom
}



