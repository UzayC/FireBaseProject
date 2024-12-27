import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyA8h5tqsdNxOGj6DkLwQRPPnTxIWDSp2MI",
  authDomain: "codep-1e4be.firebaseapp.com",
  projectId: "codep-1e4be",
  storageBucket: "codep-1e4be.firebasestorage.app",
  messagingSenderId: "1075249465548",
  appId: "1:1075249465548:web:ab888c4bdffefe7fb99a69",
};

const firebaseApp = initializeApp(firebaseConfig);
const auth = getAuth(firebaseApp);

console.log("Firebase App Config:", firebaseApp.options);

export { firebaseApp, auth };
