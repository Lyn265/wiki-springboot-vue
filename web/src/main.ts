import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import * as Icons from '@ant-design/icons-vue';
import 'ant-design-vue/dist/antd.css';
import axios from 'axios';
import {Tool} from "@/utils/tool";

axios.defaults.baseURL=process.env.VUE_APP_SERVER;

axios.interceptors.request.use(config => {
    const token = store.state.user.token;
    if (Tool.isNotEmpty(token)) {
        config.headers.Authorization= token;
    }
    return config;
}, error => {
    return Promise.reject(error);
});
axios.interceptors.response.use(response =>{
    return response;
},error => {
    const code = error.response.data.status;
    if(code === 401){
        store.commit('SET_USER','');
        router.replace("/");
    }
    return Promise.reject(error);
});



const app = createApp(App);
    app.use(store).use(router).use(Antd).mount('#app');

    const icons:any = Icons;
    for (const i in icons) {
        app.component(i,icons[i]);
    }

