import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { firebaseApp } from "./firebase";

const app = createApp(App);
app.use(router); // Router'ı uygulamaya bağlıyoruz
app.mount("#app"); // Uygulamayı DOM'a bağlıyoruz

console.log("Firebase initialized:", firebaseApp);
