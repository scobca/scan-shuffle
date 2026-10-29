import './assets/main.css';

import App from './App.vue';
import { createApp } from 'vue';
import { installPrimeVue } from './plugins/primevue';

const app = createApp(App);

installPrimeVue(app);

app.mount('#app');
