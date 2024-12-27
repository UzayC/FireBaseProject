<template>
  <div class="signin-container">
    <div class="signin-logo">
      <img src="@/assets/icon.png" alt="Logo" />
    </div>
    <h2 class="signin-title">Please sign in</h2>
    <form class="signin-form" @submit.prevent="handleSubmit">
      <input
        type="email"
        placeholder="E-mail address"
        v-model="email"
        class="signin-input"
      />
      <input
        type="password"
        placeholder="Password"
        v-model="password"
        class="signin-input"
      />
      <button type="button" class="signin-button" @click="signIn">
        Sign In
      </button>
    </form>
    <footer class="signin-footer">Codeway © 2021</footer>
  </div>
</template>

<script>
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";
import api from "@/services/api";

export default {
  name: "SignIn",
  data() {
    return {
      email: "",
      password: "",
    };
  },
  mounted() {
    api
      .get("/signin")
      .then((response) => {
        this.configurations = response.data;
        console.log("SignIn:", this.signin);
      })
      .catch((error) => {
        console.error("Error fetching signin:", error);
      });
  },

  methods: {
    async signIn() {
      try {
        const auth = getAuth();
        const userCredential = await signInWithEmailAndPassword(
          auth,
          this.email,
          this.password
        );
        await signInWithEmailAndPassword(auth, this.email, this.password);

        console.log("Sign in successful!", userCredential.user);

        // Check if the user is signed in correctly
        if (userCredential.user) {
          console.log("Redirecting to ConfigurationsPage...");
          this.$router.push({ name: "ConfigurationsPage" });
        } else {
          console.error("Sign in failed: No user data returned.");
        }
      } catch (error) {
        console.error("Error signing in:", error.message);

        // Show user-friendly notification if sign-in fails
        alert("Failed to sign in. Please check your credentials.");
      }
    },
  },
};
</script>

<style scoped>
@media screen and (max-width: 768px) {
  .signin-container {
    padding: 20px;
    text-align: center;
  }

  .signin-input {
    width: 100%;
  }
}
.signin-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #1d1d27, #25253c);
  color: #fff;
}

.signin-logo img {
  width: 100px;
  height: auto;
  margin-bottom: 20px;
}

.signin-title {
  font-size: 1.5rem;
  font-weight: 400;
  color: #8f8ee3;
  margin-bottom: 20px;
}

.signin-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 400px;
}

.signin-input {
  width: 100%;
  padding: 10px 15px;
  margin: 10px 0;
  border: 1px solid #6c63ff;
  border-radius: 5px;
  background: transparent;
  color: #fff;
  font-size: 1rem;
}

.signin-input:focus {
  outline: none;
  border-color: #8f8ee3;
  box-shadow: 0 0 8px rgba(143, 142, 227, 0.6);
}

.signin-button {
  width: 100%;
  padding: 10px 15px;
  margin-top: 20px;
  border: none;
  border-radius: 5px;
  background: #6c63ff;
  color: #fff;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.3s;
}

.signin-button:hover {
  background: #8f8ee3;
}

.signin-footer {
  margin-top: 30px;
  font-size: 0.875rem;
  color: #aaa;
}

.error {
  color: red;
  font-size: 0.9rem;
}
</style>
