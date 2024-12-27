<template>
  <div class="configurations">
    <div class="header">
      <img src="@/assets/icon.png" alt="Logo" class="logo" />
      <h1>User</h1>
    </div>
    <table>
      <thead>
        <tr>
          <th>Parameter Key</th>
          <th>Value</th>
          <th>Description</th>
          <th>Create Date</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(config, index) in configurations" :key="index">
          <td>{{ config.id }}</td>
          <td>{{ config.data.version }}</td>
          <td>{{ config.data.description }}</td>
          <td>{{ new Date(config.data.lastUpdated).toLocaleString() }}</td>
          <td class="actions">
            <button class="edit-btn" @click="editConfiguration(index)">
              Edit
            </button>
            <button class="delete-btn" @click="deleteConfiguration(index)">
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="add-param">
      <input type="text" placeholder="New Parameter" v-model="newParam.key" />
      <input type="text" placeholder="Value" v-model="newParam.value" />
      <input
        type="text"
        placeholder="New Description"
        v-model="newParam.description"
      />
      <button @click="addConfiguration" class="add-btn">ADD</button>
    </div>

    <div v-if="isEditing" class="edit-form">
      <h2>Edit Configuration</h2>
      <div
        v-for="(value, key) in currentConfig.data"
        :key="key"
        class="edit-field"
      >
        <label :for="key">{{ key }}</label>
        <input
          :id="key"
          v-model="currentConfig.data[key]"
          type="text"
          :placeholder="`Enter value for ${key}`"
        />
      </div>
      <div class="edit-actions">
        <button class="save-btn" @click="saveConfiguration">Save</button>
        <button class="cancel-btn" @click="cancelEdit">Cancel</button>
      </div>
    </div>
  </div>
</template>

<script>
import { getAuth } from "firebase/auth";
import { getFirestore, collection, getDocs } from "firebase/firestore";
import api from "@/services/api";

export default {
  data() {
    return {
      configurations: [],
      isEditing: false,
      currentConfig: null,
      newParam: {
        key: "",
        value: "",
        description: "",
      },
    };
  },
  mounted() {
    api
      .get("/configurations")
      .then((response) => {
        this.configurations = response.data;
        console.log("Configurations:", this.configurations);
      })
      .catch((error) => {
        console.error("Error fetching configurations:", error);
      });
  },

  async created() {
    try {
      const db = getFirestore();
      const configsSnapshot = await getDocs(collection(db, "configurations"));
      this.configurations = configsSnapshot.docs.map((doc) => ({
        id: doc.id,
        data: doc.data(),
      }));
    } catch (error) {
      console.error("Error fetching configurations:", error);
    }
  },
  methods: {
    editConfiguration(index) {
      this.isEditing = true;
      this.currentConfig = { ...this.configurations[index] };
    },
    cancelEdit() {
      this.isEditing = false;
      this.currentConfig = null;
    },
    showNotification(message, type = "info") {
      this.notification = { message, type };
      setTimeout(() => {
        this.notification.message = "";
      }, 3000);
    },
    async saveConfiguration() {
      try {
        const parsedData = {
          ...(typeof this.currentConfig.data === "string"
            ? JSON.parse(this.currentConfig.data)
            : this.currentConfig.data),
          lastUpdated: Date.now(),
        };
        const auth = getAuth();
        const idToken = await auth.currentUser.getIdToken();

        const response = await fetch(
          `http://localhost:4567/configurations/${this.currentConfig.id}`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: idToken,
            },
            body: JSON.stringify(parsedData),
          }
        );

        if (response.ok) {
          alert("Configuration updated successfully!");
          this.isEditing = false;
          this.refreshConfigurations();
        } else {
          const error = await response.text();
          console.error("Error updating configuration:", error);
          alert("Failed to update configuration.");
        }
      } catch (error) {
        console.error("Unexpected error:", error);
        alert("An unexpected error occurred.");
      }
    },
    async refreshConfigurations() {
      const db = getFirestore();
      const configsSnapshot = await getDocs(collection(db, "configurations"));
      this.configurations = configsSnapshot.docs.map((doc) => ({
        id: doc.id,
        data: doc.data(),
      }));
    },
    async addConfiguration() {
      try {
        const newConfig = {
          id: this.newParam.key,
          version: this.newParam.value,
          description: this.newParam.description,
          lastUpdated: Date.now(),
        };

        const auth = getAuth();
        const idToken = await auth.currentUser.getIdToken();

        const response = await fetch(
          `http://localhost:4567/configurations/${newConfig.id}`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: idToken,
            },
            body: JSON.stringify(newConfig),
          }
        );

        if (response.ok) {
          this.showNotification("New parameter added successfully!", "success");
          this.newParam = { key: "", value: "", description: "" };
          await this.refreshConfigurations();
        } else {
          const error = await response.text();
          console.error("Error adding configuration:", error);
          this.showNotification(`Failed to add parameter: ${error}`, "error");
        }
      } catch (error) {
        console.error("Unexpected error:", error);
        this.showNotification("An unexpected error occurred.", "error");
      }
    },
    async deleteConfiguration(index) {
      try {
        const configToDelete = this.configurations[index];
        const auth = getAuth();
        const idToken = await auth.currentUser.getIdToken();

        const response = await fetch(
          `http://localhost:4567/configurations/${configToDelete.id}`,
          {
            method: "DELETE",
            headers: {
              Authorization: idToken,
              "Content-Type": "application/json",
            },
          }
        );

        if (response.ok) {
          // Silme başarılı, listeyi güncelle
          this.configurations.splice(index, 1);
          this.showNotification(
            "New parameter deleted successfully!",
            "success"
          );
        } else {
          const error = await response.text();
          this.showNotification(`Failed to delete: ${error}`, "error");
        }
      } catch (error) {
        console.error("Error deleting configuration:", error);
        this.showNotification("An unexpected error occurred.", "error");
      }
    },
  },
};
</script>

<style scoped>
@media screen and (max-width: 768px) {
  table {
    display: block;
    overflow-x: auto;
    width: 100%;
  }

  .add-param {
    flex-direction: column;
  }
}
body {
  margin: 0;
  background-color: #121212;
  color: #ffffff;
  font-family: "Arial", sans-serif;
}

.configurations {
  padding: 20px;
  background-color: #1e1e2f;
  color: #ffffff;
  font-family: "Arial", sans-serif;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 20px;
}

.logo {
  height: 60px;
}

h1 {
  color: #e91e63;
  margin: 0;
  font-size: 2rem;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th,
td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #33334a;
}

th {
  background-color: #2a2a3e;
  color: #e91e63;
}

tbody tr:nth-child(even) {
  background-color: #33334a;
}

tbody tr:hover {
  background-color: #44445c;
}

.add-param {
  display: flex;
  gap: 10px;
  justify-content: space-between;
  margin-top: 20px;
  width: 90%;
}

.add-param input {
  flex: 1;
  margin-right: 10px;
  padding: 10px;
  border: 1px solid #33334a;
  border-radius: 5px;
  background-color: #1e1e2f;
  color: #ffffff;
}

.add-btn {
  background-color: #2196f3;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  flex-shrink: 0;
}

.add-btn:hover {
  background-color: #1e88e5;
}

.actions button {
  margin-right: 5px;
}

.edit-btn {
  background-color: #4caf50;
  border: none;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.delete-btn {
  background-color: #f44336;
  border: none;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.add-param {
  display: flex;
  gap: 10px;
  width: 90%;
}

.add-btn {
  background-color: #2196f3;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.add-btn:hover {
  background-color: #1e88e5;
}

.edit-form {
  background-color: #33334a;
  padding: 20px;
  border-radius: 8px;
  margin-top: 20px;
}

.edit-field {
  margin-bottom: 15px;
}

.edit-field label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.edit-field input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.edit-actions {
  display: flex;
  justify-content: space-between;
}

.save-btn {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.cancel-btn {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.save-btn:hover {
  background-color: #45a049;
}

.cancel-btn:hover {
  background-color: #d32f2f;
}
.notification {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  padding: 10px 20px;
  border-radius: 5px;
  font-size: 1rem;
  z-index: 1000;
  animation: fadeInOut 3s ease;
}

.notification-success {
  background-color: #4caf50;
  color: #ffffff;
}

.notification-error {
  background-color: #f44336;
  color: #ffffff;
}

@keyframes fadeInOut {
  0% {
    opacity: 0;
    transform: translateX(-50%) translateY(-20px);
  }
  10%,
  90% {
    opacity: 1;
    transform: translateX(-50%) translateY(0);
  }
  100% {
    opacity: 0;
    transform: translateX(-50%) translateY(-20px);
  }
}
</style>
