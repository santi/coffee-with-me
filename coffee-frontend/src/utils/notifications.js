const pushServerPublicKey = "BLy5nZ2IRcNLzHEvXIpPLh0o5lVb68d0NxlWzdFgQNoTeSWT1wJaPzF5Z87tLWV-IRvX9VLTystq08a1sVUCLjE";

function isPushNotificationSupported() {
    return "serviceWorker" in navigator && "PushManager" in window;
  }


  function registerServiceWorker() {
    return navigator.serviceWorker.register("/sw.js");
  }

  async function askUserPermission() {
    return await Notification.requestPermission();
  }


  function sendNotification() {
    const img = "/images/test.jpg";
    const text = "Take a look at this brand new t-shirt!";
    const title = "New Product Available";
    const options = {
      body: text,
      icon: "/images/test.jpg",
      vibrate: [200, 100, 200],
      tag: "new-product",
      image: img,
      badge: "https://spyna.it/icons/android-icon-192x192.png",
      actions: [{ action: "Detail", title: "View", icon: "https://via.placeholder.com/128/ff0000" }]
    };
    navigator.serviceWorker.ready.then(function(serviceWorker) {
      serviceWorker.showNotification(title, options);
    });
  }

  async function createNotificationSubscription() {
    //wait for service worker installation to be ready
    const serviceWorker = await navigator.serviceWorker.ready;
    // subscribe and return the subscription
    return await serviceWorker.pushManager.subscribe({
      userVisibleOnly: true,
      applicationServerKey: pushServerPublicKey
    });
  }


  async function postSubscription(subscription) {
    const response = await fetch(`https://push-notification-demo-server.herokuapp.com/subscription`, {
      credentials: "omit",
      headers: { "content-type": "application/json;charset=UTF-8", "sec-fetch-mode": "cors" },
      body: JSON.stringify(subscription),
      method: "POST",
      mode: "cors"
    });
    return await response.json();
  }



/**
 * returns the subscription if present or nothing
 */
function getUserSubscription() {
  //wait for service worker installation to be ready, and then
  return navigator.serviceWorker.ready
    .then(function(serviceWorker) {
      return serviceWorker.pushManager.getSubscription();
    })
    .then(function(pushSubscription) {
      return pushSubscription;
    });
}

export {
  isPushNotificationSupported,
  askUserPermission,
  registerServiceWorker,
  sendNotification,
  createNotificationSubscription,
  getUserSubscription
};
