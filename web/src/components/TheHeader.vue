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
            <a-menu-item key="/admin/ebook">
                <router-link to="/admin/ebook">电子书管理</router-link>
                </a-menu-item>
            <a-menu-item key="/admin/category">
                <router-link to="/admin/category">电子书目录</router-link>
            </a-menu-item>
            <a-menu-item key="/about">
                <router-link to="/about">关于我们</router-link>
                </a-menu-item>
        </a-menu>
    </a-layout-header>
</template>

<script>

    export default {
        name: "the-header",
        components: {
        },
    }
</script>

<style scoped>

</style>
