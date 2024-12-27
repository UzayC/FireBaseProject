<template>
  <div v-if="visible" class="notification" :class="type">
    <p>{{ message }}</p>
    <button @click="hideNotification">X</button>
  </div>
</template>

<script>
export default {
  name: "ErrorNotification",
  props: {
    message: {
      type: String,
      required: true,
    },
    type: {
      type: String,
      default: "error", // "success", "error", "info"
    },
  },
  data() {
    return {
      visible: true,
    };
  },
  methods: {
    hideNotification() {
      this.visible = false;
    },
  },
};
</script>

<style scoped>
.notification {
  position: fixed;
  top: 20px;
  right: 20px;
  padding: 15px;
  border-radius: 5px;
  color: white;
  z-index: 1000;
}
.notification.success {
  background-color: #4caf50;
}
.notification.error {
  background-color: #f44336;
}
.notification.info {
  background-color: #2196f3;
}
button {
  background: none;
  border: none;
  color: white;
  font-size: 16px;
  margin-left: 10px;
  cursor: pointer;
}
</style>
