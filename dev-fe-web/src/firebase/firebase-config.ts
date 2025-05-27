import { initializeApp } from "firebase/app";
import { getMessaging, Messaging } from "firebase/messaging";

// TypeScript sẽ check kỹ nên bạn cần đảm bảo các biến môi trường đều tồn tại
const firebaseConfig = {
  apiKey: 'AIzaSyC9tDqum9l-vDpj4Wh9_H1VzzYqS5dRV3w',
  authDomain: 'roomx-61d9a.firebaseapp.com',
  projectId: 'roomx-61d9a',
  storageBucket: 'roomx-61d9a.firebasestorage.app',
  messagingSenderId: '1029781988276',
  appId: '1:1029781988276:web:a9a0df0c3f1a069c6f88e1',
};

// Initialize Firebase
const firebaseApp = initializeApp(firebaseConfig);

// Get messaging instance
const messaging: Messaging = getMessaging(firebaseApp);

export { firebaseApp, messaging };
