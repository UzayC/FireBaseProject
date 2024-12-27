import { createRouter, createWebHistory } from "vue-router";
import HomePage from "@/views/HomePage.vue";
import SignIn from "@/views/SignIn.vue";
import ConfigurationsPage from "@/views/ConfigurationsPage.vue";
import { getAuth, onAuthStateChanged } from "firebase/auth";

const routes = [
  {
    path: "/",
    name: "HomePage",
    component: HomePage,
  },
  {
    path: "/signin",
    name: "SignIn",
    component: SignIn,
  },
  {
    path: "/configurations",
    name: "ConfigurationsPage",
    component: ConfigurationsPage,
    meta: { requiresAuth: true },
  },
  { path: "/", redirect: "/signin" },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const auth = getAuth();
  const user = auth.currentUser;

  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);

  onAuthStateChanged(auth, (user) => {
    if (user) {
      console.log("User is signed in:", user.email);
    } else {
      console.log("No user is signed in.");
    }
  });

  console.log("Route Guard triggered");

  if (requiresAuth && !user) {
    console.log("User not authenticated. Redirecting to /signin");
    next({ name: "SignIn" });
  } else if (to.path === "/" && !user) {
    next({ name: "SignIn" });
  } else {
    next();
  }
});

export default router;
