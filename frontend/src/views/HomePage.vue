<template>
  <div class="home">
    <h1>Welcome to the Configuration Panel</h1>
    <p>You are successfully signed in!</p>
  </div>
</template>

<script>
export default {
  name: "HomePage",
};
</script>

<style scoped>
@media screen and (max-width: 768px) {
  .header {
    text-align: center;
    flex-direction: column;
  }
}
.home {
  text-align: center;
  margin-top: 50px;
}
</style>
