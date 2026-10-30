<script setup lang="ts">
import { ref } from 'vue';
import {
  ChevronRight,
  House,
  Image as ImageIcon,
  SquarePlus,
  UserRound,
} from '@lucide/vue';
import Button from 'primevue/button';

const navItems = [
  { id: 'overview', label: 'Обзор', icon: House },
  { id: 'images', label: 'Изображения', icon: ImageIcon },
  { id: 'add', label: 'Добавить', icon: SquarePlus },
  { id: 'profile', label: 'Профиль', icon: UserRound },
] as const;

const activeItem = ref<(typeof navItems)[number]['id']>('overview');
</script>

<template>
  <aside
    class="app-sidebar"
    aria-label="Боковая панель"
  >
    <span class="app-sidebar__brand">Scan Shuffle</span>
    <nav
      class="app-sidebar__nav"
      aria-label="Основная навигация"
    >
      <Button
        v-for="item in navItems"
        :key="item.id"
        text
        class="app-sidebar__nav-button"
        :class="{ 'app-sidebar__nav-button--active': activeItem === item.id }"
        :aria-label="item.label"
        :aria-current="activeItem === item.id ? 'page' : undefined"
        @click="activeItem = item.id"
      >
        <component
          :is="item.icon"
          :size="20"
          :stroke-width="1.8"
          aria-hidden="true"
        />
        <span class="app-sidebar__nav-label">{{ item.label }}</span>
      </Button>
    </nav>

    <Button
      text
      class="app-sidebar__profile"
      aria-label="Открыть профиль"
      :aria-current="activeItem === 'profile' ? 'page' : undefined"
      @click="activeItem = 'profile'"
    >
      <span
        class="app-sidebar__avatar"
        aria-hidden="true"
        >А</span
      >
      <span class="app-sidebar__profile-info">
        <span class="app-sidebar__profile-name">Александр Иванов</span>
        <span class="app-sidebar__profile-email">alex@example.com</span>
      </span>
      <ChevronRight
        class="app-sidebar__profile-arrow"
        :size="16"
        :stroke-width="1.8"
        aria-hidden="true"
      />
    </Button>
  </aside>
</template>

<style scoped>
.app-sidebar {
  display: flex;
  flex: 0 0 18rem;
  flex-direction: column;
  min-height: calc(100dvh - 2.5rem);
  padding: 1.75rem 0.75rem 0.75rem;
  border: 1px solid var(--color-border);
  border-radius: 1rem;
  background-color: var(--color-sidebar);
}

.app-sidebar__brand {
  padding-left: 1rem;
  color: var(--color-text);
  font-size: 1.5rem;
  font-weight: 700;
  line-height: 2rem;
}

.app-sidebar__nav {
  display: flex;
  flex-direction: column;
  gap: 0.1rem;
  margin-top: 3.75rem;
}

.app-sidebar__nav-button.p-button {
  position: relative;
  display: flex;
  justify-content: flex-start;
  gap: 0.75rem;
  width: 100%;
  min-height: 3rem;
  padding: 0 1rem;
  background: transparent;
  color: var(--color-text);
  font-size: 0.9375rem;
  font-weight: 500;
  text-align: left;
  overflow: visible;
}

.app-sidebar__nav-button.p-button:hover {
  background: var(--color-muted-surface);
}

.app-sidebar__nav-button--active.p-button,
.app-sidebar__nav-button--active.p-button:hover {
  background: var(--color-accent-surface);
  color: var(--color-accent);
  font-weight: 700;
}

.app-sidebar__nav-button--active::before {
  content: '';
  position: absolute;
  left: -0.75rem;
  width: 0.25rem;
  height: 100%;
  border-radius: 0 0.25rem 0.25rem 0;
  background-color: var(--color-accent);
}

.app-sidebar__profile.p-button {
  display: flex;
  gap: 0.625rem;
  width: 100%;
  min-height: 4.5rem;
  margin-top: auto;
  padding: 0.625rem;
  border-radius: 0.75rem;
  background: var(--color-surface);
  color: var(--color-text);
  text-align: left;
}

.app-sidebar__profile.p-button:hover {
  border-color: var(--color-accent);
  background: var(--color-surface);
}

.app-sidebar__avatar {
  display: grid;
  flex: 0 0 2.25rem;
  place-items: center;
  width: 2.25rem;
  height: 2.25rem;
  border-radius: 50%;
  background-color: var(--color-accent-surface);
  color: var(--color-text);
  font-size: 1rem;
  font-weight: 700;
}

.app-sidebar__profile-info {
  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 0.125rem;
  min-width: 0;
}

.app-sidebar__profile-name,
.app-sidebar__profile-email {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.app-sidebar__profile-name {
  font-size: 0.8125rem;
  font-weight: 700;
}

.app-sidebar__profile-email {
  color: var(--color-text-secondary);
  font-size: 0.75rem;
}

.app-sidebar__profile-arrow {
  flex-shrink: 0;
  color: var(--color-text);
}

@media (width <= 720px) {
  .app-sidebar {
    flex-basis: 4.75rem;
    min-height: calc(100dvh - 1.5rem);
    padding: 6rem 0.5rem 0.5rem;
  }

  .app-sidebar__nav-button.p-button {
    justify-content: center;
    padding: 0;
  }

  .app-sidebar__brand {
    display: none;
  }

  .app-sidebar__nav {
    margin-top: 0;
  }

  .app-sidebar__nav-button--active::before {
    left: -0.5rem;
  }

  .app-sidebar__nav-label,
  .app-sidebar__profile-info,
  .app-sidebar__profile-arrow {
    display: none;
  }

  .app-sidebar__profile.p-button {
    justify-content: center;
    min-height: 3rem;
    padding: 0;
  }
}
</style>
