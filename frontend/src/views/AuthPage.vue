<script setup lang="ts">
import {ref} from 'vue';
import {ArrowRight, Eye, EyeOff, LockKeyhole, Mail} from '@lucide/vue';

const email = ref('');
const password = ref('');
const rememberMe = ref(true);
const showPassword = ref(false);
</script>

<template>
  <div class="auth-page">
    <section
      class="auth-card"
      aria-labelledby="auth-title"
    >
      <div class="auth-card__brand">
        <span
          class="auth-card__logo"
          aria-hidden="true"
        ></span>
        <span>Scan Shuffle</span>
      </div>

      <header class="auth-card__heading">
        <h1 id="auth-title">С возвращением</h1>
        <p>Войдите, чтобы управлять коллекцией</p>
      </header>

      <form
        class="auth-form"
        @submit.prevent
      >
        <div class="auth-form__field">
          <label for="auth-email">Электронная почта</label>
          <div class="auth-form__input-wrap">
            <Mail
              :size="21"
              :stroke-width="1.8"
              aria-hidden="true"
            />
            <input
              id="auth-email"
              v-model="email"
              type="email"
              name="email"
              placeholder="example@mail.ru"
              autocomplete="email"
              required
            />
          </div>
        </div>

        <div class="auth-form__field">
          <label for="auth-password">Пароль</label>
          <div class="auth-form__input-wrap">
            <LockKeyhole
              :size="21"
              :stroke-width="1.8"
              aria-hidden="true"
            />
            <input
              id="auth-password"
              v-model="password"
              :type="showPassword ? 'text' : 'password'"
              name="password"
              placeholder="Введите пароль"
              autocomplete="current-password"
              required
            />
            <button
              class="auth-form__visibility"
              type="button"
              :aria-label="showPassword ? 'Скрыть пароль' : 'Показать пароль'"
              :aria-pressed="showPassword"
              @click="showPassword = !showPassword"
            >
              <Eye
                v-if="showPassword"
                :size="21"
                :stroke-width="1.8"
                aria-hidden="true"
              />
              <EyeOff
                v-else
                :size="21"
                :stroke-width="1.8"
                aria-hidden="true"
              />
            </button>
          </div>
        </div>

        <div class="auth-form__options">
          <label class="auth-form__remember">
            <input
              v-model="rememberMe"
              type="checkbox"
              name="remember"
            />
            <span>Запомнить меня</span>
          </label>
          <span class="auth-card__link">Забыли пароль?</span>
        </div>

        <button
          class="auth-form__submit"
          type="submit"
        >
          Войти
          <ArrowRight
            :size="22"
            :stroke-width="1.8"
            aria-hidden="true"
          />
        </button>
      </form>

<!--      <div class="auth-card__divider"><span>или</span></div>-->
<!--      <p class="auth-card__footer">-->
<!--        Нет аккаунта? <span class="auth-card__link">Зарегистрироваться</span>-->
<!--      </p>-->
    </section>
  </div>
</template>

<style scoped>
.auth-page {
  display: grid;
  min-height: calc(100dvh - 2.5rem);
  place-items: center;
}

.auth-card {
  width: min(100%, 560px);
  padding: clamp(2rem, 6vw, 3.5rem);
  border: 1px solid var(--color-border);
  border-radius: 24px;
  background: var(--color-surface);
  box-shadow: 0 12px 40px rgb(25 28 59 / 4%);
}

.auth-card__brand {
  display: flex;
  align-items: center;
  gap: 16px;
  font-size: 1.5rem;
  font-weight: 700;
  white-space: nowrap;
}

.auth-card__logo {
  position: relative;
  display: block;
  width: 36px;
  height: 36px;
}

.auth-card__logo::before,
.auth-card__logo::after {
  content: '';
  position: absolute;
  width: 24px;
  height: 24px;
  border: 3px solid var(--color-accent);
  border-radius: 4px;
}

.auth-card__logo::before {
  top: 0;
  left: 0;
}

.auth-card__logo::after {
  right: 0;
  bottom: 0;
}

.auth-card__heading {
  margin: 56px 0 36px;
}

.auth-card__heading h1 {
  margin: 0 0 8px;
  font-size: clamp(2.25rem, 5vw, 3rem);
  font-weight: 700;
  line-height: 1.12;
}

.auth-card__heading p {
  margin: 0;
  color: var(--color-text-secondary);
  font-size: 1.125rem;
}

.auth-form {
  display: grid;
  gap: 22px;
}

.auth-form__field {
  display: grid;
  gap: 9px;
}

.auth-form__field label {
  font-weight: 500;
}

.auth-form__input-wrap {
  display: flex;
  align-items: center;
  gap: 14px;
  height: 54px;
  padding: 0 16px;
  border: 1px solid #d7d9e6;
  border-radius: 9px;
  color: #777e9d;
  transition: border-color 150ms ease,
  box-shadow 150ms ease;
}

.auth-form__input-wrap:focus-within {
  border-color: var(--color-accent);
  box-shadow: 0 0 0 3px rgb(102 86 232 / 12%);
}

.auth-form__input-wrap input {
  width: 100%;
  min-width: 0;
  height: 100%;
  padding: 0;
  border: 0;
  outline: 0;
  background: transparent;
  color: var(--color-text);
}

.auth-form__input-wrap input::placeholder {
  color: #9a9fba;
}

.auth-form__visibility {
  display: grid;
  flex: 0 0 auto;
  padding: 3px;
  border: 0;
  background: transparent;
  color: inherit;
  cursor: pointer;
  place-items: center;
}

.auth-form__options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  margin: 2px 0 4px;
  font-size: 0.9375rem;
}

.auth-form__remember {
  display: inline-flex;
  align-items: center;
  gap: 9px;
  cursor: pointer;
}

.auth-form__remember input {
  width: 19px;
  height: 19px;
  margin: 0;
  accent-color: var(--color-accent);
  cursor: pointer;
}

.auth-card__link {
  color: var(--color-accent);
  font-weight: 500;
}

.auth-form__submit {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 12px;
  min-height: 56px;
  border: 0;
  border-radius: 9px;
  background: var(--color-accent);
  color: #fff;
  font-size: 1.125rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 150ms ease;
}

.auth-form__submit:hover {
  background: var(--color-accent-hover);
}

.auth-card__divider {
  display: flex;
  align-items: center;
  gap: 18px;
  margin: 28px 0 20px;
  color: var(--color-text-secondary);
}

.auth-card__divider::before,
.auth-card__divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background: var(--color-border);
}

.auth-card__footer {
  margin: 0;
  text-align: center;
}

@media (width <= 720px) {
  .auth-page {
    min-height: calc(100dvh - 1.5rem);
  }

  .auth-card__heading {
    margin: 40px 0 30px;
  }
}

@media (width <= 390px) {
  .auth-form__options {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
