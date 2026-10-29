import './assets/main.css';

import App from './App.vue';
import router from './router';

import { createApp } from 'vue';
import { installPrimeVue } from './plugins/primevue';

const app = createApp(App);

installPrimeVue(app);

app.use(router).use(createPinia()).mount('#app');
