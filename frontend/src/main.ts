import './assets/main.css';

import App from './App.vue';
import apiConf from './api/api.conf';
import router from './router';

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { installPrimeVue } from './plugins/primevue';

const app = createApp(App);

installPrimeVue(app, apiConf.primeUILicense);

app.use(router).use(createPinia()).mount('#app');
