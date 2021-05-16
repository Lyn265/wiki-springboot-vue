import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '@/views/Home.vue'
import About from '@/views/About.vue'
import Doc from '@/views/Doc.vue'
import AdminUser from '@/views/admin/admin-user.vue'
import AdminEbook from '@/views/admin/admin-ebook.vue'
import AdminCategory from '@/views/admin/admin-category.vue'
import AdminDoc from '@/views/admin/admin-doc.vue'
import store from "@/store";
import {Tool} from "@/utils/tool";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/doc',
    name: 'Doc',
    component: Doc
  },
  {
    path: '/admin/user',
    name: 'AdminUser',
    component: AdminUser,
    meta:{
      loginRequire:true
    }
  },

  {
    path: '/admin/ebook',
    name: 'AdminEbook',
    component: AdminEbook,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/admin/category',
    name: 'AdminCategory',
    component: AdminCategory,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/admin/doc',
    name: 'AdminDoc',
    component: AdminDoc,
    meta:{
      loginRequire:true
    }
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});
// //是否对meta里的属性做拦截校验
// router.beforeEach((to,from,next) =>{
//   if(to.matched.some((item) =>{
//     return item.meta.loginFlg;
//   })){
//     if(Tool.isEmpty(store.state.user)){
//       next('/');
//     }else{
//       next();
//     }
//   }else{
//     next();
//   }
// });
// 路由登录拦截
router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
    return item.meta.loginRequire
  })) {
    const loginUser = store.state.user;
    if (Tool.isEmpty(loginUser)) {
      next('/');
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router
