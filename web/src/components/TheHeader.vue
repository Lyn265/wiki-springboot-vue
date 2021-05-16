<template>
    <a-layout-header class="header">
        <div class="logo"/>
        <a-menu
                theme="dark"
                mode="horizontal"
                :style="{ lineHeight: '64px' }">
            <a-menu-item key="/">
                <router-link to="/">首页</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/user" :style="user.id ? {}:{display:'none'}">
                <router-link to="/admin/user">用户管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/ebook" :style="user.id ? {}:{display:'none'}">
                <router-link to="/admin/ebook">电子书管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/category">
                <router-link to="/admin/category">电子书目录</router-link>
            </a-menu-item>
            <a-menu-item key="/about">
                <router-link to="/about">关于我们</router-link>
            </a-menu-item>
            <p class="login-menu" v-show="!!user.id">
                <span>{{user.name}}</span>
            </p>
            <a class="login-menu" @click="showLoginModel" v-show="!user.id">
                <span>登录</span>
            </a>
            <a-popconfirm
                    class="login-menu"
                    title="确实要退出登陆吗?"
                    ok-text="Yes"
                    cancel-text="No"
                    @confirm="handleLogout()">
                    <span v-show="!!user.id">退出登录</span>
            </a-popconfirm>
        </a-menu>
        <a-modal
                title="登录表单"
                v-model:visible="loginModelVisible"
                :confirm-loading="loginModelLoading"
                @ok="handleLogin">
            <a-form :model="loginUser" :label-col="{span:6}" :wrapper-col="{span:18}">
                <a-form-item label="登陆名：">
                    <a-input v-model:value="loginUser.loginName" />
                </a-form-item>
                <a-form-item label="密码：">
                    <a-input v-model:value="loginUser.password" type="password"/>
                </a-form-item>
            </a-form>
        </a-modal>
    </a-layout-header>
</template>

<script lang="ts">
    import {defineComponent,ref,computed} from "vue";
    import axios from 'axios';
    import {message} from "ant-design-vue";
    import store from "@/store";
    import { useRouter } from "vue-router";
    declare let hexMd5:any;
    declare let KEY:any;

    export default defineComponent({

        name: "the-header",
        setup(){
            const router = useRouter();
            const loginUser = ref({
                loginName:'',
                password:''
            });
            const user = computed(() =>{
                return store.state.user;
            });
            const loginModelVisible = ref<boolean>(false);
            const loginModelLoading = ref<boolean>(false);

            const showLoginModel = ()=>{
                loginModelVisible.value = true;
            };
            const handleLogout = () =>{
                loginModelLoading.value = true;
                axios.get('/user/logout/'+ user.value.token).then(resp =>{
                    loginModelLoading.value = false;
                    const data = resp.data;
                    if(data.success){
                        //放入全局store
                        store.commit('SET_USER',{});
                        message.success("退出登录成功");
                        router.replace('/');
                    }else{
                        message.error("退出登录失败");
                    }
                });
            };
            const handleLogin = () =>{
                loginModelLoading.value = true;
                loginUser.value.password = hexMd5(loginUser.value.password + KEY);
                axios.post('/user/login',loginUser.value).then(resp =>{
                    loginModelLoading.value = false;
                    const data = resp.data;
                    if(data.success){
                        loginModelVisible.value = false;
                        //放入全局store
                        store.commit('SET_USER',data.content);
                        router.push("/");
                        message.success("登录成功");
                    }else{
                        message.error("登录失败");
                    }
                });
            };
            return {
                user,
                loginUser,
                loginModelVisible,
                loginModelLoading,

                showLoginModel,
                handleLogin,
                handleLogout
            }

        }

    })
</script>

<style scoped>
    .login-menu {
        float: right;
        color: white;
        padding-left: 10px;
        cursor: pointer;
    }

</style>
