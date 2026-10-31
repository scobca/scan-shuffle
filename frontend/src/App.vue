<script setup lang="ts">
import AppSidebar from './components/AppSidebar.vue';
import {useRoute} from "vue-router";

const route = useRoute();
</script>

<template>
  <div class="app-shell">
    <AppSidebar v-if="route.meta.isSidebarVisible == true"/>
    <main class="app-shell__content">
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
.app-shell {
  display: flex;
  gap: 1.5rem;
  min-height: 100dvh;
  padding: 1.25rem;
}

.app-shell__content {
  flex: 1;
  min-width: 0;
}

@media (width <= 720px) {
  .app-shell {
    gap: 0.75rem;
    padding: 0.75rem;
  }
}
</style>
