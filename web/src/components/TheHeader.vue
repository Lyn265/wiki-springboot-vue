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
            <a-menu-item key="/admin/user">
                <router-link to="/admin/user">用户管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/ebook">
                <router-link to="/admin/ebook">电子书管理</router-link>
                </a-menu-item>
            <a-menu-item key="/admin/category">
                <router-link to="/admin/category">电子书目录</router-link>
            </a-menu-item>
            <a-menu-item key="/about">
                <router-link to="/about">关于我们</router-link>
                </a-menu-item>
                <a class="login-menu" @click="showLoginModel">
                    <span>登录</span>
                </a>
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

    import {defineComponent,ref} from "vue";
    import axios from 'axios';
    import {message} from "ant-design-vue";
    declare let hexMd5:any;
    declare let KEY:any;

    export default defineComponent({

        name: "the-header",
        setup(){

            const loginUser = ref({
                loginName:'',
                password:''
            });
            const loginModelVisible = ref<boolean>(false);
            const loginModelLoading = ref<boolean>(false);

            const showLoginModel = ()=>{
                loginModelVisible.value = true;
            };
            const handleLogin = () =>{
                loginModelLoading.value = true;
                loginUser.value.password = hexMd5(loginUser.value.password + KEY);
                axios.post('/user/login',loginUser.value).then(resp =>{
                    loginModelLoading.value = false;
                    const data = resp.data;
                    if(data.success){
                        loginModelVisible.value = false;
                        message.success("登录成功");
                    }else{
                        message.error("登录失败");
                    }
                });
            };

            return {
                loginUser,
                loginModelVisible,
                loginModelLoading,
                showLoginModel,
                handleLogin
            }

        }


    })
</script>

<style scoped>
    .login-menu {
        float: right;
        color: white;
    }

</style>
