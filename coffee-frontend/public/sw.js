function receivePushNotification(event) {
    console.log("[Service Worker] Push Received.", event);

    const title = event.data.text(); // TODO: Send JSON from server with more data

    const options = {
      // data: url,
      // body: text,
      // icon: image,
      vibrate: [200, 100, 200],
      // tag: tag,
      // image: image,
      badge: "https://spyna.it/icons/favicon.ico",
      actions: [{ action: "Detail", title: "View", icon: "https://via.placeholder.com/128/ff0000" }]
    };
    event.waitUntil(self.registration.showNotification(title, options));
  }
  
  function openPushNotification(event) {
    console.log("[Service Worker] Notification click Received.", event);
    
    event.notification.close();
    event.waitUntil(self.clients.openWindow("/")); // TODO: Open URL from received event
  }
  
  self.addEventListener("push", receivePushNotification);
  self.addEventListener("notificationclick", openPushNotification);
