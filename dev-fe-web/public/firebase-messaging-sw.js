importScripts(
  'https://www.gstatic.com/firebasejs/9.2.0/firebase-app-compat.js',
);
importScripts(
  'https://www.gstatic.com/firebasejs/9.2.0/firebase-messaging-compat.js',
);
firebase.initializeApp({
  apiKey: 'AIzaSyC9tDqum9l-vDpj4Wh9_H1VzzYqS5dRV3w',
  authDomain: 'roomx-61d9a.firebaseapp.com',
  projectId: 'roomx-61d9a',
  storageBucket: 'roomx-61d9a.firebasestorage.app',
  messagingSenderId: '1029781988276',
  appId: '1:1029781988276:web:a9a0df0c3f1a069c6f88e1',
});

const messaging = firebase.messaging();
messaging.onBackgroundMessage(function (e) {
  console.log(
    '[firebase-messaging-sw.js] Received background message ',
    payload,
  );
  const notificationTitle = 'Background Message Title';
  const notificationOptions = {
    body: 'Background Message body.',
    icon: '/firebase-logo.png',
  };
  self.registration.showNotification(notificationTitle, notificationOptions);
});
